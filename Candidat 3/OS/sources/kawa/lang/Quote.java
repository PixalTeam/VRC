package kawa.lang;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.kawa.functions.CompileNamedPart;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import java.util.IdentityHashMap;
import java.util.Vector;

public class Quote extends Syntax {
    private static final Object CYCLE = new String("(cycle)");
    protected static final int QUOTE_DEPTH = -1;
    private static final Object WORKING = new String("(working)");
    static final Method appendMethod = quoteType.getDeclaredMethod("append$V", 1);
    static final Method consXMethod = quoteType.getDeclaredMethod("consX$V", 1);
    static final Method makePairMethod = Compilation.typePair.getDeclaredMethod("make", 2);
    static final Method makeVectorMethod = ClassType.make("gnu.lists.FVector").getDeclaredMethod("make", 1);
    public static final Quote plainQuote = new Quote(LispLanguage.quote_sym, false);
    public static final Quote quasiQuote = new Quote(LispLanguage.quasiquote_sym, true);
    static final ClassType quoteType = ClassType.make("kawa.lang.Quote");
    static final Method vectorAppendMethod = ClassType.make("kawa.standard.vector_append").getDeclaredMethod("apply$V", 1);
    protected boolean isQuasi;

    public Quote(String name, boolean isQuasi2) {
        super(name);
        this.isQuasi = isQuasi2;
    }

    /* access modifiers changed from: protected */
    public Object expand(Object template, int depth, Translator tr) {
        return expand(template, depth, null, new IdentityHashMap(), tr);
    }

    public static Object quote(Object obj, Translator tr) {
        return plainQuote.expand(obj, -1, tr);
    }

    public static Object quote(Object obj) {
        return plainQuote.expand(obj, -1, (Translator) Compilation.getCurrent());
    }

    /* access modifiers changed from: protected */
    public Expression coerceExpression(Object val, Translator tr) {
        return val instanceof Expression ? (Expression) val : leaf(val, tr);
    }

    /* access modifiers changed from: protected */
    public Expression leaf(Object val, Translator tr) {
        return new QuoteExp(val);
    }

    /* access modifiers changed from: protected */
    public boolean expandColonForms() {
        return true;
    }

    public static Symbol makeSymbol(Namespace ns, Object local) {
        String name;
        if (local instanceof CharSequence) {
            name = ((CharSequence) local).toString();
        } else {
            name = (String) local;
        }
        return ns.getSymbol(name.intern());
    }

    /* JADX WARNING: type inference failed for: r25v0 */
    /* JADX WARNING: type inference failed for: r25v1, types: [gnu.lists.Pair] */
    /* JADX WARNING: type inference failed for: r30v0 */
    /* JADX WARNING: type inference failed for: r4v0 */
    /* JADX WARNING: type inference failed for: r4v2 */
    /* JADX WARNING: type inference failed for: r0v9, types: [gnu.lists.Pair] */
    /* JADX WARNING: type inference failed for: r4v3 */
    /* JADX WARNING: type inference failed for: r25v3, types: [gnu.lists.Pair] */
    /* JADX WARNING: type inference failed for: r4v6 */
    /* JADX WARNING: type inference failed for: r4v7 */
    /* JADX WARNING: type inference failed for: r22v2, types: [gnu.lists.Pair] */
    /* JADX WARNING: type inference failed for: r0v27, types: [gnu.lists.Pair] */
    /* JADX WARNING: type inference failed for: r0v30 */
    /* JADX WARNING: type inference failed for: r1v20, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r4v8 */
    /* JADX WARNING: type inference failed for: r4v9 */
    /* JADX WARNING: type inference failed for: r4v10 */
    /* JADX WARNING: type inference failed for: r4v11 */
    /* JADX WARNING: type inference failed for: r4v12 */
    /* JADX WARNING: type inference failed for: r4v13 */
    /* JADX WARNING: type inference failed for: r25v4 */
    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x034b, code lost:
        r19 = r18 + 1;
        r27[r18] = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x0353, code lost:
        if (r22.getCdr() != r4) goto L_0x0370;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x0357, code lost:
        if ((r3 instanceof gnu.expr.Expression) == false) goto L_0x0379;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x0359, code lost:
        r31 = gnu.lists.LList.Empty;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x035b, code lost:
        r18 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x035d, code lost:
        r18 = r18 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x035f, code lost:
        if (r18 < 0) goto L_0x037c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x0361, code lost:
        r22 = r27[r18];
        r31 = kawa.lang.Translator.makePair(r22, r22.getCar(), r31);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x0370, code lost:
        r18 = r19;
        r27 = r27;
        r22 = (gnu.lists.Pair) r22.getCdr();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x0379, code lost:
        r31 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x037e, code lost:
        if ((r3 instanceof gnu.expr.Expression) == false) goto L_0x03bb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x0380, code lost:
        r11 = new gnu.expr.Expression[2];
        r11[1] = (gnu.expr.Expression) r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x038b, code lost:
        if (r18 != 1) goto L_0x03a5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x038d, code lost:
        r11[0] = leaf(r39.getCar(), r43);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x03a5, code lost:
        r11[0] = leaf(r31, r43);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:?, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:?, code lost:
        return new gnu.expr.ApplyExp(makePairMethod, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:?, code lost:
        return new gnu.expr.ApplyExp(appendMethod, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:?, code lost:
        return r31;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0075, code lost:
        if (r39 != r4) goto L_0x0327;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x0327, code lost:
        r22 = r39;
        r27 = new gnu.lists.Pair[20];
        r18 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x0336, code lost:
        if (r18 < r27.length) goto L_0x034b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x0338, code lost:
        r36 = new gnu.lists.Pair[(r18 * 2)];
        java.lang.System.arraycopy(r27, 0, r36, 0, r18);
        r27 = r36;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 9 */
    public Object expand_pair(Pair list, int depth, SyntaxForm syntax, Object seen, Translator tr) {
        ? r4;
        Object rewrite_car;
        ? r25 = list;
        while (true) {
            ? r30 = r25;
            if (expandColonForms() && r25 == list) {
                if (tr.matches(r25.getCar(), syntax, LispLanguage.lookup_sym) && (r25.getCdr() instanceof Pair)) {
                    Pair p1 = (Pair) r25.getCdr();
                    if (p1 instanceof Pair) {
                        Pair p2 = (Pair) p1.getCdr();
                        if ((p2 instanceof Pair) && p2.getCdr() == LList.Empty) {
                            Expression part1 = tr.rewrite_car(p1, false);
                            Expression part2 = tr.rewrite_car(p2, false);
                            Namespace ns = tr.namespaceResolvePrefix(part1);
                            Symbol sym = tr.namespaceResolve(ns, part2);
                            if (sym != null) {
                                r4 = r30;
                                rewrite_car = sym;
                            } else if (ns != null && depth == 1) {
                                r4 = r30;
                                rewrite_car = new ApplyExp(quoteType.getDeclaredMethod("makeSymbol", 2), QuoteExp.getInstance(ns), part2);
                            } else if (!(part1 instanceof ReferenceExp) || !(part2 instanceof QuoteExp)) {
                                String combinedName = CompileNamedPart.combineName(part1, part2);
                                if (combinedName != null) {
                                    r4 = r30;
                                    rewrite_car = tr.getGlobalEnvironment().getSymbol(combinedName);
                                } else {
                                    Object save = tr.pushPositionOf(r25);
                                    tr.error('e', "'" + p1.getCar() + "' is not a valid prefix");
                                    tr.popPositionOf(save);
                                    r4 = r30;
                                    rewrite_car = sym;
                                }
                            } else {
                                r4 = r30;
                                rewrite_car = tr.getGlobalEnvironment().getSymbol(((ReferenceExp) part1).getName() + ':' + ((QuoteExp) part2).getValue().toString());
                            }
                        }
                    }
                }
            }
            if (depth >= 0) {
                if (tr.matches(r25.getCar(), syntax, LispLanguage.quasiquote_sym)) {
                    depth++;
                } else {
                    if (tr.matches(r25.getCar(), syntax, LispLanguage.unquote_sym)) {
                        depth--;
                        if (!(r25.getCdr() instanceof Pair)) {
                            break;
                        }
                        Pair pair_cdr = (Pair) r25.getCdr();
                        if (pair_cdr.getCdr() == LList.Empty) {
                            if (depth == 0) {
                                r4 = r30;
                                rewrite_car = tr.rewrite_car(pair_cdr, syntax);
                                break;
                            }
                        } else {
                            break;
                        }
                    } else {
                        if (tr.matches(r25.getCar(), syntax, LispLanguage.unquotesplicing_sym)) {
                            return tr.syntaxError("invalid used of " + r25.getCar() + " in quasiquote template");
                        }
                    }
                }
            }
            if (depth == 1 && (r25.getCar() instanceof Pair)) {
                Object form = r25.getCar();
                SyntaxForm subsyntax = syntax;
                while (form instanceof SyntaxForm) {
                    subsyntax = (SyntaxForm) form;
                    form = subsyntax.getDatum();
                }
                int splicing = -1;
                if (form instanceof Pair) {
                    Object op = ((Pair) form).getCar();
                    if (tr.matches(op, subsyntax, LispLanguage.unquote_sym)) {
                        splicing = 0;
                    } else {
                        if (tr.matches(op, subsyntax, LispLanguage.unquotesplicing_sym)) {
                            splicing = 1;
                        }
                    }
                }
                if (splicing >= 0) {
                    Object form2 = ((Pair) form).getCdr();
                    Vector vec = new Vector();
                    while (true) {
                        if (form2 instanceof SyntaxForm) {
                            subsyntax = (SyntaxForm) form2;
                            form2 = subsyntax.getDatum();
                        }
                        if (form2 == LList.Empty) {
                            int nargs = vec.size() + 1;
                            Object cdr = expand(r25.getCdr(), 1, syntax, seen, tr);
                            if (nargs > 1) {
                                Expression[] args = new Expression[nargs];
                                vec.copyInto(args);
                                args[nargs - 1] = coerceExpression(cdr, tr);
                                cdr = new ApplyExp(splicing == 0 ? consXMethod : appendMethod, args);
                            }
                            r4 = r25;
                            rewrite_car = cdr;
                        } else if (form2 instanceof Pair) {
                            vec.addElement(tr.rewrite_car((Pair) form2, subsyntax));
                            form2 = ((Pair) form2).getCdr();
                        } else {
                            return tr.syntaxError("improper list argument to unquote");
                        }
                    }
                }
            }
            Object car = expand(r25.getCar(), depth, syntax, seen, tr);
            if (car == r25.getCar()) {
                Object rest = r25.getCdr();
                if (!(rest instanceof Pair)) {
                    rewrite_car = expand(rest, depth, syntax, seen, tr);
                    r4 = rest;
                    break;
                }
                r25 = (Pair) rest;
            } else {
                Object cdr2 = expand(r25.getCdr(), depth, syntax, seen, tr);
                if ((car instanceof Expression) || (cdr2 instanceof Expression)) {
                    r4 = r30;
                    rewrite_car = new ApplyExp(makePairMethod, coerceExpression(car, tr), coerceExpression(cdr2, tr));
                } else {
                    r4 = r30;
                    rewrite_car = Translator.makePair(r25, car, cdr2);
                }
            }
        }
        return tr.syntaxError("invalid used of " + r25.getCar() + " in quasiquote template");
    }

    /* JADX WARNING: type inference failed for: r21v1, types: [gnu.lists.FVector] */
    /* JADX WARNING: type inference failed for: r0v7, types: [gnu.expr.ApplyExp] */
    /* JADX WARNING: type inference failed for: r19v2, types: [gnu.expr.ApplyExp] */
    /* JADX WARNING: type inference failed for: r0v10, types: [gnu.lists.FVector] */
    /* JADX WARNING: type inference failed for: r19v4 */
    /* JADX WARNING: type inference failed for: r16v2 */
    /* JADX WARNING: type inference failed for: r19v5 */
    /* JADX WARNING: type inference failed for: r0v11, types: [gnu.lists.FVector] */
    /* JADX WARNING: type inference failed for: r0v21, types: [gnu.expr.ApplyExp] */
    /* JADX WARNING: type inference failed for: r19v8 */
    /* JADX WARNING: type inference failed for: r0v22, types: [gnu.lists.FVector] */
    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r19v5
  assigns: [?[OBJECT, ARRAY], gnu.expr.ApplyExp, gnu.lists.FVector]
  uses: [?[OBJECT, ARRAY], gnu.expr.ApplyExp, gnu.lists.FVector]
  mth insns count: 173
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
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0109  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x010b A[SYNTHETIC] */
    /* JADX WARNING: Unknown variable types count: 6 */
    public Object expand(Object template, int depth, SyntaxForm syntax, Object seen, Translator tr) {
        Object old;
        ? r19;
        IdentityHashMap map = (IdentityHashMap) seen;
        Object old2 = map.get(template);
        if (old2 == WORKING) {
            map.put(template, CYCLE);
            return old2;
        } else if (old2 == CYCLE || old2 != null) {
            return old2;
        } else {
            if (template instanceof Pair) {
                old = expand_pair((Pair) template, depth, syntax, seen, tr);
            } else if (template instanceof SyntaxForm) {
                SyntaxForm syntax2 = (SyntaxForm) template;
                old = expand(syntax2.getDatum(), depth, syntax2, seen, tr);
            } else if (template instanceof FVector) {
                ? r21 = (FVector) template;
                int n = r21.size();
                Object[] buffer = new Object[n];
                byte[] state = new byte[n];
                byte max_state = 0;
                for (int i = 0; i < n; i++) {
                    Object element = r21.get(i);
                    int element_depth = depth;
                    if ((element instanceof Pair) && depth > -1) {
                        Pair pair = (Pair) element;
                        if (tr.matches(pair.getCar(), syntax, LispLanguage.unquotesplicing_sym)) {
                            element_depth--;
                            if (element_depth == 0) {
                                if (pair.getCdr() instanceof Pair) {
                                    Pair pair_cdr = (Pair) pair.getCdr();
                                    if (pair_cdr.getCdr() == LList.Empty) {
                                        buffer[i] = tr.rewrite_car(pair_cdr, syntax);
                                        state[i] = 3;
                                        if (state[i] <= max_state) {
                                            max_state = state[i];
                                        }
                                    }
                                }
                                return tr.syntaxError("invalid used of " + pair.getCar() + " in quasiquote template");
                            }
                        }
                    }
                    buffer[i] = expand(element, element_depth, syntax, seen, tr);
                    if (buffer[i] == element) {
                        state[i] = 0;
                    } else if (buffer[i] instanceof Expression) {
                        state[i] = 2;
                    } else {
                        state[i] = 1;
                    }
                    if (state[i] <= max_state) {
                    }
                }
                if (max_state == 0) {
                    r19 = r21;
                } else if (max_state == 1) {
                    ? fVector = new FVector(buffer);
                    r19 = fVector;
                } else {
                    Expression[] args = new Expression[n];
                    for (int i2 = 0; i2 < n; i2++) {
                        if (state[i2] == 3) {
                            args[i2] = (Expression) buffer[i2];
                        } else if (max_state < 3) {
                            args[i2] = coerceExpression(buffer[i2], tr);
                        } else if (state[i2] < 2) {
                            args[i2] = leaf(new FVector(new Object[]{buffer[i2]}), tr);
                        } else {
                            args[i2] = makeInvokeMakeVector(new Expression[]{(Expression) buffer[i2]});
                        }
                    }
                    if (max_state < 3) {
                        r19 = makeInvokeMakeVector(args);
                    } else {
                        ? applyExp = new ApplyExp(vectorAppendMethod, args);
                        r19 = applyExp;
                    }
                }
                old = r19;
            } else {
                old = template;
            }
            if (template != old && map.get(template) == CYCLE) {
                tr.error('e', "cycle in non-literal data");
            }
            map.put(template, old);
            return old;
        }
    }

    private static ApplyExp makeInvokeMakeVector(Expression[] args) {
        return new ApplyExp(makeVectorMethod, args);
    }

    public Expression rewrite(Object obj, Translator tr) {
        if (obj instanceof Pair) {
            Pair pair = (Pair) obj;
            if (pair.getCdr() == LList.Empty) {
                return coerceExpression(expand(pair.getCar(), this.isQuasi ? 1 : -1, tr), tr);
            }
        }
        return tr.syntaxError("wrong number of arguments to quote");
    }

    public static Object consX$V(Object[] args) {
        return LList.consX(args);
    }

    /* JADX WARNING: type inference failed for: r12v0, types: [java.lang.Object[]] */
    /* JADX WARNING: type inference failed for: r8v0 */
    /* JADX WARNING: type inference failed for: r8v1 */
    /* JADX WARNING: type inference failed for: r10v1, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r5v0 */
    /* JADX WARNING: type inference failed for: r4v0 */
    /* JADX WARNING: type inference failed for: r8v2 */
    /* JADX WARNING: type inference failed for: r8v3 */
    /* JADX WARNING: type inference failed for: r5v1 */
    /* JADX WARNING: type inference failed for: r4v1, types: [gnu.lists.Pair] */
    /* JADX WARNING: type inference failed for: r8v4 */
    /* JADX WARNING: type inference failed for: r5v2 */
    /* JADX WARNING: type inference failed for: r4v2 */
    /* JADX WARNING: type inference failed for: r8v5 */
    /* JADX WARNING: type inference failed for: r8v6 */
    /* JADX WARNING: type inference failed for: r6v0 */
    /* JADX WARNING: type inference failed for: r7v0, types: [gnu.lists.Pair, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r1v1 */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r4v3 */
    /* JADX WARNING: type inference failed for: r5v3, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r8v7 */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r9v3 */
    /* JADX WARNING: type inference failed for: r5v4, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r8v8 */
    /* JADX WARNING: type inference failed for: r5v5 */
    /* JADX WARNING: type inference failed for: r4v4 */
    /* JADX WARNING: type inference failed for: r8v9 */
    /* JADX WARNING: type inference failed for: r8v10 */
    /* JADX WARNING: type inference failed for: r4v5 */
    /* JADX WARNING: type inference failed for: r8v11 */
    /* JADX WARNING: type inference failed for: r5v6 */
    /* JADX WARNING: type inference failed for: r4v6 */
    /* JADX WARNING: type inference failed for: r8v12 */
    /* JADX WARNING: type inference failed for: r5v7 */
    /* JADX WARNING: type inference failed for: r5v8 */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Object[], code=null, for r12v0, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r8v4
  assigns: []
  uses: []
  mth insns count: 53
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
    /* JADX WARNING: Unknown variable types count: 18 */
    public static Object append$V(Object[] r12) {
        ? r8;
        ? r5;
        ? r4;
        ? r82;
        ? r1;
        int count = r12.length;
        if (count == 0) {
            return LList.Empty;
        }
        int i = count - 1;
        ? r83 = r12[count - 1];
        while (true) {
            ? r10 = r83;
            i--;
            if (i < 0) {
                return r10;
            }
            SyntaxForm syntax = null;
            ? r84 = 0;
            ? r52 = r12[i];
            ? r42 = 0;
            while (true) {
                if (r52 instanceof SyntaxForm) {
                    syntax = (SyntaxForm) r52;
                    r8 = r84;
                    r4 = r42;
                    r5 = syntax.getDatum();
                } else if (r52 == LList.Empty) {
                    break;
                } else {
                    Pair list_pair = (Pair) r52;
                    Object car = list_pair.getCar();
                    if (syntax != null && !(car instanceof SyntaxForm)) {
                        car = SyntaxForms.makeForm(car, syntax.getScope());
                    }
                    ? pair = new Pair(car, null);
                    if (r42 == 0) {
                        r1 = pair;
                    } else {
                        r42.setCdr(pair);
                        r1 = r84;
                    }
                    r4 = pair;
                    r8 = r1;
                    r5 = list_pair.getCdr();
                }
                r84 = r8;
                r52 = r5;
                r42 = r4;
            }
            if (r42 != 0) {
                r42.setCdr(r10);
                r82 = r84;
            } else {
                r82 = r10;
            }
            r83 = r82;
        }
    }
}
