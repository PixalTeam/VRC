package kawa.lang;

import gnu.bytecode.ClassType;
import gnu.bytecode.Member;
import gnu.bytecode.Type;
import gnu.bytecode.ZipLoader;
import gnu.expr.ApplyExp;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.LangExp;
import gnu.expr.Language;
import gnu.expr.LetExp;
import gnu.expr.ModuleExp;
import gnu.expr.ModuleInfo;
import gnu.expr.NameLookup;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.Special;
import gnu.expr.ThisExp;
import gnu.kawa.functions.AppendValues;
import gnu.kawa.functions.CompileNamedPart;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.kawa.reflect.ClassMethods;
import gnu.kawa.reflect.FieldLocation;
import gnu.kawa.reflect.SlotGet;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.kawa.xml.MakeAttribute;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.Location;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.text.SourceLocator;
import gnu.text.SourceMessages;
import gnu.xml.NamespaceBinding;
import java.util.Stack;
import java.util.Vector;
import kawa.standard.begin;
import kawa.standard.object;
import kawa.standard.require;

public class Translator extends Compilation {
    private static Expression errorExp = new ErrorExp("unknown syntax error");
    public static final Declaration getNamedPartDecl = Declaration.getDeclarationFromStatic("gnu.kawa.functions.GetNamedPart", "getNamedPart");
    public LambdaExp curMethodLambda;
    public Macro currentMacroDefinition;
    Syntax currentSyntax;
    private Environment env = Environment.getCurrent();
    public int firstForm;
    public Stack formStack = new Stack();
    Declaration macroContext;
    public Declaration matchArray;
    Vector notedAccess;
    public PatternScope patternScope;
    public Object pendingForm;
    PairWithPosition positionPair;
    Stack renamedAliasStack;
    public Declaration templateScopeDecl;
    public NamespaceBinding xmlElementNamespaces = NamespaceBinding.predefinedXML;

    static {
        LispLanguage.getNamedPartLocation.setDeclaration(getNamedPartDecl);
    }

    public Translator(Language language, SourceMessages messages, NameLookup lexical) {
        super(language, messages, lexical);
    }

    public final Environment getGlobalEnvironment() {
        return this.env;
    }

    public Expression parse(Object input) {
        return rewrite(input);
    }

    public final Expression rewrite_car(Pair pair, SyntaxForm syntax) {
        if (syntax == null || syntax.getScope() == this.current_scope || (pair.getCar() instanceof SyntaxForm)) {
            return rewrite_car(pair, false);
        }
        ScopeExp save_scope = this.current_scope;
        try {
            setCurrentScope(syntax.getScope());
            return rewrite_car(pair, false);
        } finally {
            setCurrentScope(save_scope);
        }
    }

    public final Expression rewrite_car(Pair pair, boolean function) {
        Object car = pair.getCar();
        if (pair instanceof PairWithPosition) {
            return rewrite_with_position(car, function, (PairWithPosition) pair);
        }
        return rewrite(car, function);
    }

    public Syntax getCurrentSyntax() {
        return this.currentSyntax;
    }

    /* access modifiers changed from: 0000 */
    public Expression apply_rewrite(Syntax syntax, Pair form) {
        Expression expression = errorExp;
        Syntax saveSyntax = this.currentSyntax;
        this.currentSyntax = syntax;
        try {
            return syntax.rewriteForm(form, this);
        } finally {
            this.currentSyntax = saveSyntax;
        }
    }

    static ReferenceExp getOriginalRef(Declaration decl) {
        if (decl != null && decl.isAlias() && !decl.isIndirectBinding()) {
            Expression value = decl.getValue();
            if (value instanceof ReferenceExp) {
                return (ReferenceExp) value;
            }
        }
        return null;
    }

    public final boolean selfEvaluatingSymbol(Object obj) {
        return ((LispLanguage) getLanguage()).selfEvaluatingSymbol(obj);
    }

    public final boolean matches(Object form, String literal) {
        return matches(form, (SyntaxForm) null, literal);
    }

    public boolean matches(Object form, SyntaxForm syntax, String literal) {
        if (syntax != null) {
        }
        if (form instanceof SyntaxForm) {
            form = ((SyntaxForm) form).getDatum();
        }
        if ((form instanceof SimpleSymbol) && !selfEvaluatingSymbol(form)) {
            ReferenceExp rexp = getOriginalRef(this.lexical.lookup(form, -1));
            if (rexp != null) {
                form = rexp.getSymbol();
            }
        }
        return (form instanceof SimpleSymbol) && ((Symbol) form).getLocalPart() == literal;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=gnu.mapping.Symbol, code=java.lang.Object, for r6v0, types: [gnu.mapping.Symbol, java.lang.Object] */
    public boolean matches(Object form, SyntaxForm syntax, Object literal) {
        if (syntax != null) {
        }
        if (form instanceof SyntaxForm) {
            form = ((SyntaxForm) form).getDatum();
        }
        if ((form instanceof SimpleSymbol) && !selfEvaluatingSymbol(form)) {
            ReferenceExp rexp = getOriginalRef(this.lexical.lookup(form, -1));
            if (rexp != null) {
                form = rexp.getSymbol();
            }
        }
        return form == literal;
    }

    public Object matchQuoted(Pair pair) {
        if (matches(pair.getCar(), LispLanguage.quote_sym) && (pair.getCdr() instanceof Pair)) {
            Pair pair2 = (Pair) pair.getCdr();
            if (pair2.getCdr() == LList.Empty) {
                return pair2.getCar();
            }
        }
        return null;
    }

    public Declaration lookup(Object name, int namespace) {
        Declaration decl = this.lexical.lookup(name, namespace);
        return (decl == null || !getLanguage().hasNamespace(decl, namespace)) ? currentModule().lookup(name, getLanguage(), namespace) : decl;
    }

    public Declaration lookupGlobal(Object name) {
        return lookupGlobal(name, -1);
    }

    public Declaration lookupGlobal(Object name, int namespace) {
        ModuleExp module = currentModule();
        Declaration decl = module.lookup(name, getLanguage(), namespace);
        if (decl != null) {
            return decl;
        }
        Declaration decl2 = module.getNoDefine(name);
        decl2.setIndirectBinding(true);
        return decl2;
    }

    /* access modifiers changed from: 0000 */
    public Syntax check_if_Syntax(Declaration decl) {
        Declaration d = Declaration.followAliases(decl);
        Object obj = null;
        Expression dval = d.getValue();
        if (dval != null && d.getFlag(32768)) {
            try {
                if (decl.getValue() instanceof ReferenceExp) {
                    Declaration context = ((ReferenceExp) decl.getValue()).contextDecl();
                    if (context != null) {
                        this.macroContext = context;
                    } else if (this.current_scope instanceof TemplateScope) {
                        this.macroContext = ((TemplateScope) this.current_scope).macroContext;
                    }
                } else if (this.current_scope instanceof TemplateScope) {
                    this.macroContext = ((TemplateScope) this.current_scope).macroContext;
                }
                obj = dval.eval(this.env);
            } catch (Throwable ex) {
                ex.printStackTrace();
                error('e', "unable to evaluate macro for " + decl.getSymbol());
            }
        } else if (decl.getFlag(32768) && !decl.needsContext()) {
            obj = StaticFieldLocation.make(decl).get(null);
        }
        if (obj instanceof Syntax) {
            return (Syntax) obj;
        }
        return null;
    }

    public Expression rewrite_pair(Pair p, boolean function) {
        Symbol symbol;
        Expression func = rewrite_car(p, true);
        if (func instanceof QuoteExp) {
            Object proc = func.valueIfConstant();
            if (proc instanceof Syntax) {
                return apply_rewrite((Syntax) proc, p);
            }
        }
        if (func instanceof ReferenceExp) {
            ReferenceExp ref = (ReferenceExp) func;
            Declaration decl = ref.getBinding();
            if (decl == null) {
                Object sym = ref.getSymbol();
                if (!(sym instanceof Symbol) || selfEvaluatingSymbol(sym)) {
                    symbol = this.env.getSymbol(sym.toString());
                } else {
                    symbol = (Symbol) sym;
                    String name = symbol.getName();
                }
                Object proc2 = this.env.get(symbol, getLanguage().hasSeparateFunctionNamespace() ? EnvironmentKey.FUNCTION : null, null);
                if (proc2 instanceof Syntax) {
                    return apply_rewrite((Syntax) proc2, p);
                }
                if (proc2 instanceof AutoloadProcedure) {
                    try {
                        Object proc3 = ((AutoloadProcedure) proc2).getLoaded();
                    } catch (RuntimeException e) {
                    }
                }
            } else {
                Declaration saveContext = this.macroContext;
                Syntax syntax = check_if_Syntax(decl);
                if (syntax != null) {
                    Expression apply_rewrite = apply_rewrite(syntax, p);
                    this.macroContext = saveContext;
                    return apply_rewrite;
                }
            }
            ref.setProcedureName(true);
            if (getLanguage().hasSeparateFunctionNamespace()) {
                func.setFlag(8);
            }
        }
        Object cdr = p.getCdr();
        int cdr_length = listLength(cdr);
        if (cdr_length == -1) {
            return syntaxError("circular list is not allowed after " + p.getCar());
        }
        if (cdr_length < 0) {
            return syntaxError("dotted list [" + cdr + "] is not allowed after " + p.getCar());
        }
        boolean mapKeywordsToAttributes = false;
        Stack vec = new Stack();
        ScopeExp save_scope = this.current_scope;
        int i = 0;
        while (i < cdr_length) {
            if (cdr instanceof SyntaxForm) {
                SyntaxForm sf = (SyntaxForm) cdr;
                cdr = sf.getDatum();
                setCurrentScope(sf.getScope());
            }
            Pair cdr_pair = (Pair) cdr;
            Expression arg = rewrite_car(cdr_pair, false);
            i++;
            if (mapKeywordsToAttributes) {
                if ((i & 1) == 0) {
                    arg = new ApplyExp((Procedure) MakeAttribute.makeAttribute, (Expression) vec.pop(), arg);
                } else {
                    if (arg instanceof QuoteExp) {
                        Object value = ((QuoteExp) arg).getValue();
                        if ((value instanceof Keyword) && i < cdr_length) {
                            arg = new QuoteExp(((Keyword) value).asSymbol());
                        }
                    }
                    mapKeywordsToAttributes = false;
                }
            }
            vec.addElement(arg);
            cdr = cdr_pair.getCdr();
        }
        Expression[] args = new Expression[vec.size()];
        vec.copyInto(args);
        if (save_scope != this.current_scope) {
            setCurrentScope(save_scope);
        }
        if (!(func instanceof ReferenceExp) || ((ReferenceExp) func).getBinding() != getNamedPartDecl) {
            return ((LispLanguage) getLanguage()).makeApply(func, args);
        }
        Expression part1 = args[0];
        Expression part2 = args[1];
        Symbol sym2 = namespaceResolve(part1, part2);
        if (sym2 != null) {
            return rewrite(sym2, function);
        }
        return CompileNamedPart.makeExp(part1, part2);
    }

    public Namespace namespaceResolvePrefix(Expression context) {
        Object val;
        if (context instanceof ReferenceExp) {
            ReferenceExp rexp = (ReferenceExp) context;
            Declaration decl = rexp.getBinding();
            if (decl == null || decl.getFlag(65536)) {
                Object rsym = rexp.getSymbol();
                val = this.env.get((EnvironmentKey) rsym instanceof Symbol ? (Symbol) rsym : this.env.getSymbol(rsym.toString()), (Object) null);
            } else if (decl.isNamespaceDecl()) {
                val = decl.getConstantValue();
            } else {
                val = null;
            }
            if (val instanceof Namespace) {
                Namespace ns = (Namespace) val;
                String uri = ns.getName();
                if (uri == null || !uri.startsWith("class:")) {
                    return ns;
                }
                return null;
            }
        }
        return null;
    }

    public Symbol namespaceResolve(Namespace ns, Expression member) {
        if (ns == null || !(member instanceof QuoteExp)) {
            return null;
        }
        return ns.getSymbol(((QuoteExp) member).getValue().toString().intern());
    }

    public Symbol namespaceResolve(Expression context, Expression member) {
        return namespaceResolve(namespaceResolvePrefix(context), member);
    }

    public static Object stripSyntax(Object obj) {
        while (obj instanceof SyntaxForm) {
            obj = ((SyntaxForm) obj).getDatum();
        }
        return obj;
    }

    public static Object safeCar(Object obj) {
        while (obj instanceof SyntaxForm) {
            obj = ((SyntaxForm) obj).getDatum();
        }
        if (!(obj instanceof Pair)) {
            return null;
        }
        return stripSyntax(((Pair) obj).getCar());
    }

    public static Object safeCdr(Object obj) {
        while (obj instanceof SyntaxForm) {
            obj = ((SyntaxForm) obj).getDatum();
        }
        if (!(obj instanceof Pair)) {
            return null;
        }
        return stripSyntax(((Pair) obj).getCdr());
    }

    public static int listLength(Object obj) {
        int n = 0;
        Object slow = obj;
        Object fast = obj;
        while (true) {
            if (fast instanceof SyntaxForm) {
                fast = ((SyntaxForm) fast).getDatum();
            } else {
                while (slow instanceof SyntaxForm) {
                    slow = ((SyntaxForm) slow).getDatum();
                }
                if (fast == LList.Empty) {
                    return n;
                }
                if (!(fast instanceof Pair)) {
                    return -1 - n;
                }
                int n2 = n + 1;
                Object next = ((Pair) fast).getCdr();
                while (next instanceof SyntaxForm) {
                    next = ((SyntaxForm) next).getDatum();
                }
                if (next == LList.Empty) {
                    return n2;
                }
                if (!(next instanceof Pair)) {
                    return -1 - n2;
                }
                slow = ((Pair) slow).getCdr();
                fast = ((Pair) next).getCdr();
                n = n2 + 1;
                if (fast == slow) {
                    return Integer.MIN_VALUE;
                }
            }
        }
    }

    public void rewriteInBody(Object exp) {
        if (exp instanceof SyntaxForm) {
            SyntaxForm sf = (SyntaxForm) exp;
            ScopeExp save_scope = this.current_scope;
            try {
                setCurrentScope(sf.getScope());
                rewriteInBody(sf.getDatum());
            } finally {
                setCurrentScope(save_scope);
            }
        } else if (exp instanceof Values) {
            Object[] vals = ((Values) exp).getValues();
            for (Object rewriteInBody : vals) {
                rewriteInBody(rewriteInBody);
            }
        } else {
            this.formStack.add(rewrite(exp, false));
        }
    }

    public Expression rewrite(Object exp) {
        return rewrite(exp, false);
    }

    public Object namespaceResolve(Object name) {
        if (!(name instanceof SimpleSymbol) && (name instanceof Pair)) {
            Pair p = (Pair) name;
            if (safeCar(p) == LispLanguage.lookup_sym && (p.getCdr() instanceof Pair)) {
                Pair p2 = (Pair) p.getCdr();
                if (p2.getCdr() instanceof Pair) {
                    Expression part1 = rewrite(p2.getCar());
                    Expression part2 = rewrite(((Pair) p2.getCdr()).getCar());
                    Symbol sym = namespaceResolve(part1, part2);
                    if (sym != null) {
                        return sym;
                    }
                    String combinedName = CompileNamedPart.combineName(part1, part2);
                    if (combinedName != null) {
                        return Namespace.EmptyNamespace.getSymbol(combinedName);
                    }
                }
            }
        }
        return name;
    }

    /* JADX WARNING: type inference failed for: r0v82, types: [gnu.expr.ThisExp] */
    /* JADX WARNING: type inference failed for: r23v1 */
    /* JADX WARNING: type inference failed for: r0v83, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r0v87, types: [gnu.expr.ReferenceExp] */
    /* JADX WARNING: type inference failed for: r0v103, types: [gnu.expr.ThisExp] */
    /* JADX WARNING: type inference failed for: r0v104, types: [gnu.expr.ReferenceExp] */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x02c6, code lost:
        if ((r16 instanceof gnu.bytecode.ArrayClassLoader) == false) goto L_0x011c;
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v103, types: [gnu.expr.ThisExp]
  assigns: [gnu.expr.ThisExp, gnu.expr.ReferenceExp]
  uses: [gnu.expr.ThisExp, ?[OBJECT, ARRAY], gnu.expr.ReferenceExp]
  mth insns count: 388
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x011e  */
    /* JADX WARNING: Unknown variable types count: 4 */
    public Expression rewrite(Object exp, boolean function) {
        String dname;
        Object nameToLookup;
        Object obj;
        ? r23;
        if (exp instanceof SyntaxForm) {
            SyntaxForm sf = (SyntaxForm) exp;
            ScopeExp save_scope = this.current_scope;
            try {
                setCurrentScope(sf.getScope());
                return rewrite(sf.getDatum(), function);
            } finally {
                setCurrentScope(save_scope);
            }
        } else if (exp instanceof PairWithPosition) {
            return rewrite_with_position(exp, function, (PairWithPosition) exp);
        } else {
            if (exp instanceof Pair) {
                return rewrite_pair((Pair) exp, function);
            }
            if ((exp instanceof Symbol) && !selfEvaluatingSymbol(exp)) {
                Declaration decl = this.lexical.lookup(exp, function);
                Declaration cdecl = null;
                ScopeExp scope = this.current_scope;
                int decl_nesting = decl == null ? -1 : ScopeExp.nesting(decl.context);
                if (!(exp instanceof Symbol) || !((Symbol) exp).hasEmptyNamespace()) {
                    dname = null;
                    scope = null;
                } else {
                    dname = exp.toString();
                }
                while (scope != null) {
                    if ((scope instanceof LambdaExp) && (scope.outer instanceof ClassExp) && ((LambdaExp) scope).isClassMethod()) {
                        if (decl_nesting >= ScopeExp.nesting(scope.outer)) {
                            break;
                        }
                        LambdaExp caller = (LambdaExp) scope;
                        ClassExp cexp = (ClassExp) scope.outer;
                        ClassType ctype = cexp.getClassType();
                        Member part = SlotGet.lookupMember(ctype, dname, ctype);
                        boolean contextStatic = caller == cexp.clinitMethod || (caller != cexp.initMethod && caller.nameDecl.isStatic());
                        if (part == null) {
                            if (ClassMethods.getMethods(ctype, dname, contextStatic ? 'S' : 'V', ctype, this.language).length == 0) {
                            }
                        }
                        if (contextStatic) {
                            ? referenceExp = new ReferenceExp(((ClassExp) caller.outer).nameDecl);
                            r23 = referenceExp;
                        } else {
                            ? thisExp = new ThisExp(caller.firstDecl());
                            r23 = thisExp;
                        }
                        return CompileNamedPart.makeExp((Expression) r23, (Expression) QuoteExp.getInstance(dname));
                    }
                    scope = scope.outer;
                }
                if (decl != null) {
                    nameToLookup = decl.getSymbol();
                    Object exp2 = null;
                    ReferenceExp rexp = getOriginalRef(decl);
                    if (rexp != null) {
                        decl = rexp.getBinding();
                        if (decl == null) {
                            exp2 = rexp.getSymbol();
                            nameToLookup = exp2;
                        }
                    }
                    obj = exp2;
                } else {
                    nameToLookup = exp;
                    obj = exp;
                }
                Symbol symbol = (Symbol) obj;
                boolean separate = getLanguage().hasSeparateFunctionNamespace();
                if (decl == null) {
                    Location loc = this.env.lookup(symbol, (!function || !separate) ? null : EnvironmentKey.FUNCTION);
                    if (loc != null) {
                        loc = loc.getBase();
                    }
                    if (loc instanceof FieldLocation) {
                        FieldLocation floc = (FieldLocation) loc;
                        try {
                            decl = floc.getDeclaration();
                            if (!inlineOk((Expression) null) && decl != getNamedPartDecl && (!"objectSyntax".equals(floc.getMemberName()) || !"kawa.standard.object".equals(floc.getDeclaringClass().getName()))) {
                                decl = null;
                            } else if (this.immediate) {
                                if (!decl.isStatic()) {
                                    Declaration cdecl2 = new Declaration((Object) "(module-instance)");
                                    try {
                                        cdecl2.setValue(new QuoteExp(floc.getInstance()));
                                        cdecl = cdecl2;
                                    } catch (Throwable th) {
                                        ex = th;
                                        cdecl = cdecl2;
                                        error('e', "exception loading '" + obj + "' - " + ex.getMessage());
                                        decl = null;
                                        if (decl != null) {
                                        }
                                        ReferenceExp referenceExp2 = new ReferenceExp(nameToLookup, decl);
                                        referenceExp2.setContextDecl(cdecl);
                                        referenceExp2.setLine((Compilation) this);
                                        referenceExp2.setFlag(8);
                                        return referenceExp2;
                                    }
                                }
                            } else if (decl.isStatic()) {
                                Class fclass = floc.getRClass();
                                if (fclass != null) {
                                    ClassLoader floader = fclass.getClassLoader();
                                    if (!(floader instanceof ZipLoader)) {
                                    }
                                }
                                decl = null;
                            } else {
                                decl = null;
                            }
                        } catch (Throwable th2) {
                            ex = th2;
                            error('e', "exception loading '" + obj + "' - " + ex.getMessage());
                            decl = null;
                            if (decl != null) {
                            }
                            ReferenceExp referenceExp22 = new ReferenceExp(nameToLookup, decl);
                            referenceExp22.setContextDecl(cdecl);
                            referenceExp22.setLine((Compilation) this);
                            referenceExp22.setFlag(8);
                            return referenceExp22;
                        }
                    } else if (loc == null || !loc.isBound()) {
                        Expression e = ((LispLanguage) getLanguage()).checkDefaultBinding(symbol, this);
                        if (e != null) {
                            return e;
                        }
                    }
                } else if ((this.current_scope instanceof TemplateScope) && decl.needsContext()) {
                    cdecl = ((TemplateScope) this.current_scope).macroContext;
                } else if (decl.getFlag(1048576) && !decl.isStatic()) {
                    ScopeExp scope2 = currentScope();
                    while (scope2 != null) {
                        if (scope2.outer == decl.context) {
                            cdecl = scope2.firstDecl();
                        } else {
                            scope2 = scope2.outer;
                        }
                    }
                    throw new Error("internal error: missing " + decl);
                }
                if (decl != null) {
                    if (!function && (decl.getConstantValue() instanceof object)) {
                        return QuoteExp.getInstance(Object.class);
                    }
                    if (decl.getContext() instanceof PatternScope) {
                        return syntaxError("reference to pattern variable " + decl.getName() + " outside syntax template");
                    }
                }
                ReferenceExp referenceExp222 = new ReferenceExp(nameToLookup, decl);
                referenceExp222.setContextDecl(cdecl);
                referenceExp222.setLine((Compilation) this);
                if (function && separate) {
                    referenceExp222.setFlag(8);
                }
                return referenceExp222;
            } else if (exp instanceof LangExp) {
                return rewrite(((LangExp) exp).getLangValue(), function);
            } else {
                if (exp instanceof Expression) {
                    return (Expression) exp;
                }
                if (exp == Special.abstractSpecial) {
                    return QuoteExp.abstractExp;
                }
                return QuoteExp.getInstance(Quote.quote(exp, this), this);
            }
        }
    }

    public static void setLine(Expression exp, Object location) {
        if (location instanceof SourceLocator) {
            exp.setLocation((SourceLocator) location);
        }
    }

    public static void setLine(Declaration decl, Object location) {
        if (location instanceof SourceLocator) {
            decl.setLocation((SourceLocator) location);
        }
    }

    public Object pushPositionOf(Object pair) {
        PairWithPosition saved;
        if (pair instanceof SyntaxForm) {
            pair = ((SyntaxForm) pair).getDatum();
        }
        if (!(pair instanceof PairWithPosition)) {
            return null;
        }
        PairWithPosition ppair = (PairWithPosition) pair;
        if (this.positionPair != null && this.positionPair.getFileName() == getFileName() && this.positionPair.getLineNumber() == getLineNumber() && this.positionPair.getColumnNumber() == getColumnNumber()) {
            saved = this.positionPair;
        } else {
            saved = new PairWithPosition(this, Special.eof, this.positionPair);
        }
        setLine(pair);
        this.positionPair = ppair;
        return saved;
    }

    public void popPositionOf(Object saved) {
        if (saved != null) {
            setLine(saved);
            this.positionPair = (PairWithPosition) saved;
            if (this.positionPair.getCar() == Special.eof) {
                this.positionPair = (PairWithPosition) this.positionPair.getCdr();
            }
        }
    }

    public void setLineOf(Expression exp) {
        if (!(exp instanceof QuoteExp)) {
            exp.setLocation(this);
        }
    }

    public Type exp2Type(Pair typeSpecPair) {
        Object saved = pushPositionOf(typeSpecPair);
        try {
            Expression texp = InlineCalls.inlineCalls(rewrite_car(typeSpecPair, false), this);
            if (texp instanceof ErrorExp) {
                return null;
            }
            Type type = getLanguage().getTypeFor(texp);
            if (type == null) {
                try {
                    Object t = texp.eval(this.env);
                    if (t instanceof Class) {
                        type = Type.make((Class) t);
                    } else if (t instanceof Type) {
                        type = (Type) t;
                    }
                } catch (Throwable th) {
                }
            }
            if (type == null) {
                if (texp instanceof ReferenceExp) {
                    error('e', "unknown type name '" + ((ReferenceExp) texp).getName() + '\'');
                } else {
                    error('e', "invalid type spec (must be \"type\" or 'type or <type>)");
                }
                Type type2 = Type.pointer_type;
                popPositionOf(saved);
                return type2;
            }
            popPositionOf(saved);
            return type;
        } finally {
            popPositionOf(saved);
        }
    }

    public Expression rewrite_with_position(Object exp, boolean function, PairWithPosition pair) {
        Expression result;
        Object saved = pushPositionOf(pair);
        if (exp == pair) {
            try {
                result = rewrite_pair(pair, function);
            } catch (Throwable th) {
                popPositionOf(saved);
                throw th;
            }
        } else {
            result = rewrite(exp, function);
        }
        setLineOf(result);
        popPositionOf(saved);
        return result;
    }

    public static Object wrapSyntax(Object form, SyntaxForm syntax) {
        return (syntax == null || (form instanceof Expression)) ? form : SyntaxForms.fromDatumIfNeeded(form, syntax);
    }

    public Object popForms(int first) {
        Object obj;
        int last = this.formStack.size();
        if (last == first) {
            return Values.empty;
        }
        if (last == first + 1) {
            obj = this.formStack.elementAt(first);
        } else {
            Values vals = new Values();
            for (int i = first; i < last; i++) {
                vals.writeObject(this.formStack.elementAt(i));
            }
            obj = vals;
        }
        this.formStack.setSize(first);
        return obj;
    }

    public void scanForm(Object st, ScopeExp defs) {
        if (st instanceof SyntaxForm) {
            SyntaxForm sf = (SyntaxForm) st;
            ScopeExp save_scope = currentScope();
            try {
                setCurrentScope(sf.getScope());
                int first = this.formStack.size();
                scanForm(sf.getDatum(), defs);
                this.formStack.add(wrapSyntax(popForms(first), sf));
            } finally {
                setCurrentScope(save_scope);
            }
        } else {
            if (st instanceof Values) {
                if (st == Values.empty) {
                    st = QuoteExp.voidExp;
                } else {
                    Object[] vals = ((Values) st).getValues();
                    for (int i = 0; i < vals.length; i++) {
                        scanForm(vals[i], defs);
                    }
                    return;
                }
            }
            if (st instanceof Pair) {
                Pair st_pair = (Pair) st;
                Declaration saveContext = this.macroContext;
                Syntax syntax = null;
                ScopeExp savedScope = this.current_scope;
                Object savedPosition = pushPositionOf(st);
                if ((st instanceof SourceLocator) && defs.getLineNumber() < 0) {
                    defs.setLocation((SourceLocator) st);
                }
                try {
                    Object obj = st_pair.getCar();
                    if (obj instanceof SyntaxForm) {
                        SyntaxForm sf2 = (SyntaxForm) st_pair.getCar();
                        setCurrentScope(sf2.getScope());
                        obj = sf2.getDatum();
                    }
                    if (obj instanceof Pair) {
                        Pair p = (Pair) obj;
                        if (p.getCar() == LispLanguage.lookup_sym && (p.getCdr() instanceof Pair)) {
                            Pair p2 = (Pair) p.getCdr();
                            if (p2.getCdr() instanceof Pair) {
                                Expression part1 = rewrite(p2.getCar());
                                Expression part2 = rewrite(((Pair) p2.getCdr()).getCar());
                                Object value1 = part1.valueIfConstant();
                                Object value2 = part2.valueIfConstant();
                                if (!(value1 instanceof Class) || !(value2 instanceof Symbol)) {
                                    obj = namespaceResolve(part1, part2);
                                } else {
                                    try {
                                        obj = GetNamedPart.getNamedPart(value1, (Symbol) value2);
                                        if (obj instanceof Syntax) {
                                            syntax = (Syntax) obj;
                                        }
                                    } catch (Throwable th) {
                                        obj = null;
                                    }
                                }
                            }
                        }
                    }
                    if ((obj instanceof Symbol) && !selfEvaluatingSymbol(obj)) {
                        Expression func = rewrite(obj, true);
                        if (func instanceof ReferenceExp) {
                            Declaration decl = ((ReferenceExp) func).getBinding();
                            if (decl != null) {
                                syntax = check_if_Syntax(decl);
                            } else {
                                Object obj2 = resolve(obj, true);
                                if (obj2 instanceof Syntax) {
                                    syntax = (Syntax) obj2;
                                }
                            }
                        }
                    } else if (obj == begin.begin) {
                        syntax = (Syntax) obj;
                    }
                    if (syntax != null) {
                        String save_filename = getFileName();
                        int save_line = getLineNumber();
                        int save_column = getColumnNumber();
                        try {
                            setLine((Object) st_pair);
                            syntax.scanForm(st_pair, defs, this);
                            return;
                        } finally {
                            this.macroContext = saveContext;
                            setLine(save_filename, save_line, save_column);
                        }
                    }
                } finally {
                    if (savedScope != this.current_scope) {
                        setCurrentScope(savedScope);
                    }
                    popPositionOf(savedPosition);
                }
            }
            this.formStack.add(st);
        }
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: type inference failed for: r5v0 */
    /* JADX WARNING: type inference failed for: r5v1 */
    /* JADX WARNING: type inference failed for: r4v0 */
    /* JADX WARNING: type inference failed for: r5v2, types: [gnu.lists.LList] */
    /* JADX WARNING: type inference failed for: r4v1, types: [gnu.lists.Pair] */
    /* JADX WARNING: type inference failed for: r5v6 */
    /* JADX WARNING: type inference failed for: r4v2 */
    /* JADX WARNING: type inference failed for: r5v7 */
    /* JADX WARNING: type inference failed for: r4v3, types: [gnu.lists.Pair] */
    /* JADX WARNING: type inference failed for: r6v0, types: [gnu.lists.Pair, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r5v8 */
    /* JADX WARNING: type inference failed for: r4v4 */
    /* JADX WARNING: type inference failed for: r5v9 */
    /* JADX WARNING: type inference failed for: r5v10, types: [gnu.lists.LList] */
    /* JADX WARNING: type inference failed for: r5v11 */
    /* JADX WARNING: type inference failed for: r5v12 */
    /* JADX WARNING: type inference failed for: r4v5 */
    /* JADX WARNING: type inference failed for: r5v13 */
    /* JADX WARNING: type inference failed for: r5v14 */
    /* JADX WARNING: type inference failed for: r4v6 */
    /* JADX WARNING: type inference failed for: r4v7 */
    /* JADX WARNING: type inference failed for: r5v15 */
    /* JADX WARNING: type inference failed for: r4v8 */
    /* JADX WARNING: type inference failed for: r5v16 */
    /* JADX WARNING: type inference failed for: r5v17 */
    /* JADX WARNING: type inference failed for: r4v9 */
    /* JADX WARNING: type inference failed for: r5v18 */
    /* JADX WARNING: type inference failed for: r5v19 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r5v1
  assigns: []
  uses: []
  mth insns count: 94
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 12 */
    public LList scanBody(Object body, ScopeExp defs, boolean makeList) {
        ? r5;
        ? r4;
        ? r52;
        ? r53 = makeList ? LList.Empty : 0;
        ? r42 = 0;
        while (body != LList.Empty) {
            if (body instanceof SyntaxForm) {
                SyntaxForm sf = (SyntaxForm) body;
                ScopeExp save_scope = this.current_scope;
                try {
                    setCurrentScope(sf.getScope());
                    int first = this.formStack.size();
                    LList f = scanBody(sf.getDatum(), defs, makeList);
                    if (makeList) {
                        LList f2 = (LList) SyntaxForms.fromDatumIfNeeded(f, sf);
                        if (r42 == 0) {
                            setCurrentScope(save_scope);
                            return f2;
                        }
                        r42.setCdrBackdoor(f2);
                        setCurrentScope(save_scope);
                        return r53;
                    }
                    this.formStack.add(wrapSyntax(popForms(first), sf));
                    setCurrentScope(save_scope);
                    return null;
                } catch (Throwable th) {
                    setCurrentScope(save_scope);
                    throw th;
                }
            } else if (body instanceof Pair) {
                Pair pair = (Pair) body;
                int first2 = this.formStack.size();
                scanForm(pair.getCar(), defs);
                if (getState() == 2) {
                    if (pair.getCar() != this.pendingForm) {
                        pair = makePair(pair, this.pendingForm, pair.getCdr());
                    }
                    this.pendingForm = new Pair(begin.begin, pair);
                    return LList.Empty;
                }
                int fsize = this.formStack.size();
                if (makeList) {
                    int i = first2;
                    ? r54 = r53;
                    ? r43 = r42;
                    while (i < fsize) {
                        ? makePair = makePair(pair, this.formStack.elementAt(i), LList.Empty);
                        if (r43 == 0) {
                            r52 = makePair;
                        } else {
                            r43.setCdrBackdoor(makePair);
                            r52 = r54;
                        }
                        r43 = makePair;
                        i++;
                        r54 = r52;
                    }
                    this.formStack.setSize(first2);
                    r5 = r54;
                    r4 = r43;
                } else {
                    r5 = r53;
                    r4 = r42;
                }
                body = pair.getCdr();
                r53 = r5;
                r42 = r4;
            } else {
                this.formStack.add(syntaxError("body is not a proper list"));
                return r53;
            }
        }
        return r53;
    }

    public static Pair makePair(Pair pair, Object car, Object cdr) {
        if (pair instanceof PairWithPosition) {
            return new PairWithPosition((PairWithPosition) pair, car, cdr);
        }
        return new Pair(car, cdr);
    }

    public Expression rewrite_body(Object exp) {
        Object saved = pushPositionOf(exp);
        LetExp defs = new LetExp(null);
        int first = this.formStack.size();
        defs.outer = this.current_scope;
        this.current_scope = defs;
        try {
            LList list = scanBody(exp, defs, true);
            if (list.isEmpty()) {
                this.formStack.add(syntaxError("body with no expressions"));
            }
            int ndecls = defs.countNonDynamicDecls();
            if (ndecls != 0) {
                Expression[] inits = new Expression[ndecls];
                int i = ndecls;
                while (true) {
                    i--;
                    if (i < 0) {
                        break;
                    }
                    inits[i] = QuoteExp.undefined_exp;
                }
                defs.inits = inits;
            }
            rewriteBody(list);
            Expression body = makeBody(first, null);
            setLineOf(body);
            if (ndecls == 0) {
                return body;
            }
            defs.body = body;
            setLineOf(defs);
            pop(defs);
            popPositionOf(saved);
            return defs;
        } finally {
            pop(defs);
            popPositionOf(saved);
        }
    }

    /* JADX INFO: finally extract failed */
    private void rewriteBody(LList forms) {
        while (forms != LList.Empty) {
            Pair pair = (Pair) forms;
            Object saved = pushPositionOf(pair);
            try {
                rewriteInBody(pair.getCar());
                popPositionOf(saved);
                forms = (LList) pair.getCdr();
            } catch (Throwable th) {
                popPositionOf(saved);
                throw th;
            }
        }
    }

    private Expression makeBody(int first, ScopeExp scope) {
        int nforms = this.formStack.size() - first;
        if (nforms == 0) {
            return QuoteExp.voidExp;
        }
        if (nforms == 1) {
            return (Expression) this.formStack.pop();
        }
        Expression[] exps = new Expression[nforms];
        for (int i = 0; i < nforms; i++) {
            exps[i] = (Expression) this.formStack.elementAt(first + i);
        }
        this.formStack.setSize(first);
        if (scope instanceof ModuleExp) {
            return new ApplyExp((Procedure) AppendValues.appendValues, exps);
        }
        return ((LispLanguage) getLanguage()).makeBody(exps);
    }

    public void noteAccess(Object name, ScopeExp scope) {
        if (this.notedAccess == null) {
            this.notedAccess = new Vector();
        }
        this.notedAccess.addElement(name);
        this.notedAccess.addElement(scope);
    }

    public void processAccesses() {
        if (this.notedAccess != null) {
            int sz = this.notedAccess.size();
            ScopeExp saveScope = this.current_scope;
            for (int i = 0; i < sz; i += 2) {
                Object name = this.notedAccess.elementAt(i);
                ScopeExp scope = (ScopeExp) this.notedAccess.elementAt(i + 1);
                if (this.current_scope != scope) {
                    setCurrentScope(scope);
                }
                Declaration decl = this.lexical.lookup(name, -1);
                if (decl != null && !decl.getFlag(65536)) {
                    decl.getContext().currentLambda().capture(decl);
                    decl.setCanRead(true);
                    decl.setSimple(false);
                    decl.setFlag(524288);
                }
            }
            if (this.current_scope != saveScope) {
                setCurrentScope(saveScope);
            }
        }
    }

    public void finishModule(ModuleExp mexp) {
        boolean moduleStatic = mexp.isStatic();
        for (Declaration decl = mexp.firstDecl(); decl != null; decl = decl.nextDecl()) {
            if (decl.getFlag(512)) {
                String msg1 = "'";
                String msg2 = decl.getFlag(1024) ? "' exported but never defined" : decl.getFlag(2048) ? "' declared static but never defined" : "' declared but never defined";
                error('e', decl, msg1, msg2);
            }
            if (mexp.getFlag(16384) || (this.generateMain && !this.immediate)) {
                if (!decl.getFlag(1024)) {
                    decl.setPrivate(true);
                } else if (decl.isPrivate()) {
                    if (decl.getFlag(16777216)) {
                        error('e', decl, "'", "' is declared both private and exported");
                    }
                    decl.setPrivate(false);
                }
            }
            if (moduleStatic) {
                decl.setFlag(2048);
            } else if ((mexp.getFlag(65536) && !decl.getFlag(2048)) || Compilation.moduleStatic < 0 || mexp.getFlag(131072)) {
                decl.setFlag(4096);
            }
        }
    }

    static void vectorReverse(Vector vec, int start, int count) {
        int j = count / 2;
        int last = (start + count) - 1;
        for (int i = 0; i < j; i++) {
            Object tmp = vec.elementAt(start + i);
            vec.setElementAt(vec.elementAt(last - i), start + i);
            vec.setElementAt(tmp, last - i);
        }
    }

    public void resolveModule(ModuleExp mexp) {
        int numPending;
        if (this.pendingImports == null) {
            numPending = 0;
        } else {
            numPending = this.pendingImports.size();
        }
        int i = 0;
        while (i < numPending) {
            int i2 = i + 1;
            ModuleInfo info = (ModuleInfo) this.pendingImports.elementAt(i);
            int i3 = i2 + 1;
            ScopeExp defs = (ScopeExp) this.pendingImports.elementAt(i2);
            int i4 = i3 + 1;
            Expression posExp = (Expression) this.pendingImports.elementAt(i3);
            i = i4 + 1;
            Integer savedSize = (Integer) this.pendingImports.elementAt(i4);
            if (mexp == defs) {
                ReferenceExp referenceExp = new ReferenceExp((Object) null);
                referenceExp.setLine((Compilation) this);
                setLine(posExp);
                int beforeSize = this.formStack.size();
                require.importDefinitions(null, info, null, this.formStack, defs, this);
                int desiredPosition = savedSize.intValue();
                if (savedSize.intValue() != beforeSize) {
                    int curSize = this.formStack.size();
                    int count = curSize - desiredPosition;
                    vectorReverse(this.formStack, desiredPosition, beforeSize - desiredPosition);
                    vectorReverse(this.formStack, beforeSize, curSize - beforeSize);
                    vectorReverse(this.formStack, desiredPosition, count);
                }
                setLine((Expression) referenceExp);
            }
        }
        this.pendingImports = null;
        processAccesses();
        setModule(mexp);
        Compilation save_comp = Compilation.setSaveCurrent(this);
        try {
            rewriteInBody(popForms(this.firstForm));
            mexp.body = makeBody(this.firstForm, mexp);
            if (!this.immediate) {
                this.lexical.pop((ScopeExp) mexp);
            }
        } finally {
            Compilation.restoreCurrent(save_comp);
        }
    }

    public Declaration makeRenamedAlias(Declaration decl, ScopeExp templateScope) {
        return templateScope == null ? decl : makeRenamedAlias(decl.getSymbol(), decl, templateScope);
    }

    public Declaration makeRenamedAlias(Object name, Declaration decl, ScopeExp templateScope) {
        Declaration alias = new Declaration(name);
        alias.setAlias(true);
        alias.setPrivate(true);
        alias.context = templateScope;
        ReferenceExp ref = new ReferenceExp(decl);
        ref.setDontDereference(true);
        alias.noteValue(ref);
        return alias;
    }

    public void pushRenamedAlias(Declaration alias) {
        Declaration decl = getOriginalRef(alias).getBinding();
        ScopeExp templateScope = alias.context;
        decl.setSymbol(null);
        Declaration old = templateScope.lookup(decl.getSymbol());
        if (old != null) {
            templateScope.remove(old);
        }
        templateScope.addDeclaration(alias);
        if (this.renamedAliasStack == null) {
            this.renamedAliasStack = new Stack();
        }
        this.renamedAliasStack.push(old);
        this.renamedAliasStack.push(alias);
        this.renamedAliasStack.push(templateScope);
    }

    public void popRenamedAlias(int count) {
        while (true) {
            count--;
            if (count >= 0) {
                ScopeExp templateScope = (ScopeExp) this.renamedAliasStack.pop();
                Declaration alias = (Declaration) this.renamedAliasStack.pop();
                getOriginalRef(alias).getBinding().setSymbol(alias.getSymbol());
                templateScope.remove(alias);
                Object old = this.renamedAliasStack.pop();
                if (old != null) {
                    templateScope.addDeclaration((Declaration) old);
                }
            } else {
                return;
            }
        }
    }

    public Declaration define(Object name, SyntaxForm nameSyntax, ScopeExp defs) {
        Object declName;
        boolean aliasNeeded = (nameSyntax == null || nameSyntax.getScope() == currentScope()) ? false : true;
        if (aliasNeeded) {
            declName = new String(name.toString());
        } else {
            declName = name;
        }
        Declaration decl = defs.getDefine(declName, 'w', this);
        if (aliasNeeded) {
            nameSyntax.getScope().addDeclaration(makeRenamedAlias(name, decl, nameSyntax.getScope()));
        }
        push(decl);
        return decl;
    }
}
