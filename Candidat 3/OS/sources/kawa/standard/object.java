package kawa.standard;

import gnu.bytecode.Type;
import gnu.expr.BeginExp;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.ObjectExp;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.expr.ThisExp;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Namespace;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Lambda;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

public class object extends Syntax {
    public static final Keyword accessKeyword = Keyword.make("access");
    public static final Keyword allocationKeyword = Keyword.make("allocation");
    public static final Keyword classNameKeyword = Keyword.make("class-name");
    static final Symbol coloncolon = Namespace.EmptyNamespace.getSymbol("::");
    static final Keyword initKeyword = Keyword.make("init");
    static final Keyword init_formKeyword = Keyword.make("init-form");
    static final Keyword init_keywordKeyword = Keyword.make("init-keyword");
    static final Keyword init_valueKeyword = Keyword.make("init-value");
    static final Keyword initformKeyword = Keyword.make("initform");
    public static final Keyword interfaceKeyword = Keyword.make("interface");
    public static final object objectSyntax = new object(SchemeCompilation.lambda);
    public static final Keyword throwsKeyword = Keyword.make("throws");
    static final Keyword typeKeyword = Keyword.make("type");
    Lambda lambda;

    static {
        objectSyntax.setName("object");
    }

    public object(Lambda lambda2) {
        this.lambda = lambda2;
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        if (!(form.getCdr() instanceof Pair)) {
            return tr.syntaxError("missing superclass specification in object");
        }
        Pair pair = (Pair) form.getCdr();
        ObjectExp oexp = new ObjectExp();
        if (pair.getCar() instanceof FString) {
            if (!(pair.getCdr() instanceof Pair)) {
                return tr.syntaxError("missing superclass specification after object class name");
            }
            pair = (Pair) pair.getCdr();
        }
        Object[] saved = scanClassDef(pair, oexp, tr);
        if (saved == null) {
            return oexp;
        }
        rewriteClassDef(saved, tr);
        return oexp;
    }

    /* JADX WARNING: type inference failed for: r43v4, types: [gnu.lists.Pair] */
    /* JADX WARNING: type inference failed for: r19v0, types: [gnu.expr.Declaration] */
    /* JADX WARNING: type inference failed for: r0v17, types: [gnu.expr.Declaration] */
    /* JADX WARNING: type inference failed for: r0v18, types: [gnu.expr.Declaration] */
    /* JADX WARNING: type inference failed for: r0v19, types: [gnu.expr.Declaration] */
    /* JADX WARNING: type inference failed for: r1v3, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r16v0, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r19v1 */
    /* JADX WARNING: type inference failed for: r16v1 */
    /* JADX WARNING: type inference failed for: r16v2 */
    /* JADX WARNING: type inference failed for: r0v20 */
    /* JADX WARNING: type inference failed for: r16v3, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r0v21 */
    /* JADX WARNING: type inference failed for: r0v24, types: [gnu.expr.Declaration] */
    /* JADX WARNING: type inference failed for: r0v25, types: [gnu.expr.Declaration] */
    /* JADX WARNING: type inference failed for: r0v26, types: [gnu.expr.Declaration] */
    /* JADX WARNING: type inference failed for: r0v27, types: [gnu.expr.Declaration] */
    /* JADX WARNING: type inference failed for: r0v29, types: [gnu.expr.Declaration] */
    /* JADX WARNING: type inference failed for: r5v40, types: [java.lang.Boolean] */
    /* JADX WARNING: type inference failed for: r5v41, types: [java.lang.Boolean] */
    /* JADX WARNING: type inference failed for: r5v42, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r5v43 */
    /* JADX WARNING: type inference failed for: r16v4 */
    /* JADX WARNING: type inference failed for: r5v44 */
    /* JADX WARNING: type inference failed for: r43v5 */
    /* JADX WARNING: type inference failed for: r16v5, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r0v36 */
    /* JADX WARNING: type inference failed for: r0v37 */
    /* JADX WARNING: type inference failed for: r16v6 */
    /* JADX WARNING: type inference failed for: r43v7 */
    /* JADX WARNING: type inference failed for: r16v7, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r16v8 */
    /* JADX WARNING: type inference failed for: r0v39 */
    /* JADX WARNING: type inference failed for: r43v9 */
    /* JADX WARNING: type inference failed for: r16v9, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r16v10, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r19v2 */
    /* JADX WARNING: type inference failed for: r16v11 */
    /* JADX WARNING: type inference failed for: r19v4 */
    /* JADX WARNING: type inference failed for: r16v12 */
    /* JADX WARNING: type inference failed for: r16v13 */
    /* JADX WARNING: type inference failed for: r5v98 */
    /* JADX WARNING: type inference failed for: r5v99 */
    /* JADX WARNING: type inference failed for: r16v14 */
    /* JADX WARNING: type inference failed for: r16v15 */
    /* JADX WARNING: type inference failed for: r16v16 */
    /* JADX WARNING: type inference failed for: r16v17 */
    /* JADX WARNING: type inference failed for: r16v18 */
    /* JADX WARNING: type inference failed for: r16v19 */
    /* JADX WARNING: type inference failed for: r16v20 */
    /* JADX WARNING: type inference failed for: r16v21 */
    /* JADX WARNING: type inference failed for: r16v22 */
    /* JADX WARNING: type inference failed for: r16v23 */
    /* JADX WARNING: type inference failed for: r16v24 */
    /* JADX WARNING: type inference failed for: r16v25 */
    /* JADX WARNING: type inference failed for: r16v26 */
    /* JADX WARNING: type inference failed for: r16v27 */
    /* JADX WARNING: type inference failed for: r16v28 */
    /* JADX WARNING: type inference failed for: r16v29 */
    /* JADX WARNING: type inference failed for: r19v5 */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x0327, code lost:
        r16 = 0;
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r16v1
  assigns: []
  uses: []
  mth insns count: 436
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
    /* JADX WARNING: Unknown variable types count: 33 */
    public Object[] scanClassDef(Pair pair, ClassExp oexp, Translator tr) {
        ? r19;
        ? r16;
        ? r162;
        ? r5;
        ? r163;
        tr.mustCompileHere();
        Object superlist = pair.getCar();
        Object components = pair.getCdr();
        Object classNamePair = null;
        LambdaExp method_list = null;
        LambdaExp last_method = null;
        long classAccessFlag = 0;
        Vector vector = new Vector(20);
        Object obj = components;
        while (obj != LList.Empty) {
            while (obj instanceof SyntaxForm) {
                obj = ((SyntaxForm) obj).getDatum();
            }
            if (!(obj instanceof Pair)) {
                tr.error('e', "object member not a list");
                return null;
            }
            Pair pair2 = (Pair) obj;
            Object pair_car = pair2.getCar();
            while (pair_car instanceof SyntaxForm) {
                pair_car = ((SyntaxForm) pair_car).getDatum();
            }
            obj = pair2.getCdr();
            Object savedPos1 = tr.pushPositionOf(pair2);
            if (pair_car instanceof Keyword) {
                while (obj instanceof SyntaxForm) {
                    obj = ((SyntaxForm) obj).getDatum();
                }
                if (obj instanceof Pair) {
                    if (pair_car == interfaceKeyword) {
                        if (((Pair) obj).getCar() == Boolean.FALSE) {
                            oexp.setFlag(65536);
                        } else {
                            oexp.setFlag(32768);
                        }
                        obj = ((Pair) obj).getCdr();
                        tr.popPositionOf(savedPos1);
                    } else if (pair_car == classNameKeyword) {
                        if (classNamePair != null) {
                            tr.error('e', "duplicate class-name specifiers");
                        }
                        classNamePair = obj;
                        obj = ((Pair) obj).getCdr();
                        tr.popPositionOf(savedPos1);
                    } else if (pair_car == accessKeyword) {
                        Object savedPos2 = tr.pushPositionOf(obj);
                        classAccessFlag = addAccessFlags(((Pair) obj).getCar(), classAccessFlag, Declaration.CLASS_ACCESS_FLAGS, "class", tr);
                        if (oexp.nameDecl == null) {
                            tr.error('e', "access specifier for anonymous class");
                        }
                        tr.popPositionOf(savedPos2);
                        obj = ((Pair) obj).getCdr();
                        tr.popPositionOf(savedPos1);
                    }
                }
            }
            if (!(pair_car instanceof Pair)) {
                tr.error('e', "object member not a list");
                return null;
            }
            ? r43 = (Pair) pair_car;
            Object pair_car2 = r43.getCar();
            while (pair_car2 instanceof SyntaxForm) {
                pair_car2 = ((SyntaxForm) pair_car2).getDatum();
            }
            if ((pair_car2 instanceof String) || (pair_car2 instanceof Symbol) || (pair_car2 instanceof Keyword)) {
                Pair typePair = null;
                Object sname = pair_car2;
                int allocationFlag = 0;
                long accessFlag = 0;
                if (sname instanceof Keyword) {
                    r16 = r43;
                    r19 = 0;
                } else {
                    ? addDeclaration = oexp.addDeclaration(sname);
                    addDeclaration.setSimple(false);
                    addDeclaration.setFlag(1048576);
                    Translator.setLine((Declaration) addDeclaration, (Object) r43);
                    r19 = addDeclaration;
                    r16 = r43.getCdr();
                }
                int nKeywords = 0;
                boolean seenInit = false;
                Pair initPair = null;
                ? datum = r16;
                while (true) {
                    if (r162 == LList.Empty) {
                        break;
                    }
                    while (true) {
                        r5 = r162;
                        if (!(r5 instanceof SyntaxForm)) {
                            break;
                        }
                        r162 = ((SyntaxForm) r5).getDatum();
                    }
                    Pair pair3 = (Pair) r5;
                    Pair keyPair = pair3;
                    Object key = pair3.getCar();
                    while (key instanceof SyntaxForm) {
                        key = ((SyntaxForm) key).getDatum();
                    }
                    Object savedPos22 = tr.pushPositionOf(pair3);
                    ? cdr = pair3.getCdr();
                    if ((key == coloncolon || (key instanceof Keyword)) && (cdr instanceof Pair)) {
                        nKeywords++;
                        Pair pair4 = (Pair) cdr;
                        Object value = pair4.getCar();
                        ? cdr2 = pair4.getCdr();
                        if (key == coloncolon || key == typeKeyword) {
                            typePair = pair4;
                            r163 = cdr2;
                        } else if (key == allocationKeyword) {
                            if (allocationFlag != 0) {
                                tr.error('e', "duplicate allocation: specification");
                            }
                            if (matches(value, "class", tr) || matches(value, "static", tr)) {
                                allocationFlag = 2048;
                                r163 = cdr2;
                            } else if (matches(value, "instance", tr)) {
                                allocationFlag = 4096;
                                r163 = cdr2;
                            } else {
                                tr.error('e', "unknown allocation kind '" + value + "'");
                                r163 = cdr2;
                            }
                        } else if (key == initKeyword || key == initformKeyword || key == init_formKeyword || key == init_valueKeyword) {
                            if (seenInit) {
                                tr.error('e', "duplicate initialization");
                            }
                            seenInit = true;
                            if (key != initKeyword) {
                                initPair = pair4;
                                r163 = cdr2;
                            } else {
                                r163 = cdr2;
                            }
                        } else if (key == init_keywordKeyword) {
                            if (!(value instanceof Keyword)) {
                                tr.error('e', "invalid 'init-keyword' - not a keyword");
                                r163 = cdr2;
                            } else if (((Keyword) value).getName() != sname.toString()) {
                                tr.error('w', "init-keyword option ignored");
                                r163 = cdr2;
                            } else {
                                r163 = cdr2;
                            }
                        } else if (key == accessKeyword) {
                            Object savedPos3 = tr.pushPositionOf(pair4);
                            accessFlag = addAccessFlags(value, accessFlag, Declaration.FIELD_ACCESS_FLAGS, "field", tr);
                            tr.popPositionOf(savedPos3);
                            r163 = cdr2;
                        } else {
                            tr.error('w', "unknown slot keyword '" + key + "'");
                            r163 = cdr2;
                        }
                    } else if (cdr != LList.Empty || seenInit) {
                        if (!(cdr instanceof Pair) || nKeywords != 0 || seenInit || typePair != null) {
                            break;
                        }
                        Pair pair5 = (Pair) cdr;
                        if (pair5.getCdr() != LList.Empty) {
                            break;
                        }
                        typePair = keyPair;
                        initPair = pair5;
                        seenInit = true;
                        r163 = pair5.getCdr();
                    } else {
                        initPair = keyPair;
                        seenInit = true;
                        r163 = cdr;
                    }
                    tr.popPositionOf(savedPos22);
                    datum = r163;
                }
                if (r162 != LList.Empty) {
                    tr.error('e', "invalid argument list for slot '" + sname + '\'' + " args:" + (r162 == 0 ? "null" : r162.getClass().getName()));
                    return null;
                }
                if (seenInit) {
                    ? r52 = r19 != 0 ? r19 : allocationFlag == 2048 ? Boolean.TRUE : Boolean.FALSE;
                    vector.addElement(r52);
                    vector.addElement(initPair);
                }
                if (r19 != 0) {
                    if (typePair != null) {
                        r19.setType(tr.exp2Type(typePair));
                    }
                    if (allocationFlag != 0) {
                        r19.setFlag((long) allocationFlag);
                    }
                    if (accessFlag != 0) {
                        r19.setFlag(accessFlag);
                    }
                    r19.setCanRead(true);
                    r19.setCanWrite(true);
                } else if (!seenInit) {
                    tr.error('e', "missing field name");
                    return null;
                }
            } else if (pair_car2 instanceof Pair) {
                Pair mpair = (Pair) pair_car2;
                Object mname = mpair.getCar();
                if ((mname instanceof String) || (mname instanceof Symbol)) {
                    LambdaExp lexp = new LambdaExp();
                    Translator.setLine(oexp.addMethod(lexp, mname), (Object) mpair);
                    if (last_method == null) {
                        method_list = lexp;
                    } else {
                        last_method.nextSibling = lexp;
                    }
                    last_method = lexp;
                } else {
                    tr.error('e', "missing method name");
                    return null;
                }
            } else {
                tr.error('e', "invalid field/method definition");
            }
            tr.popPositionOf(savedPos1);
        }
        if (classAccessFlag != 0) {
            oexp.nameDecl.setFlag(classAccessFlag);
        }
        return new Object[]{oexp, components, vector, method_list, superlist, classNamePair};
    }

    /* JADX WARNING: type inference failed for: r0v23, types: [gnu.lists.Pair] */
    /* JADX WARNING: type inference failed for: r39v2, types: [gnu.lists.Pair] */
    /* JADX WARNING: type inference failed for: r12v0, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r12v1 */
    /* JADX WARNING: type inference failed for: r12v2 */
    /* JADX WARNING: type inference failed for: r12v3 */
    /* JADX WARNING: type inference failed for: r0v35 */
    /* JADX WARNING: type inference failed for: r12v4, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r0v40 */
    /* JADX WARNING: type inference failed for: r12v6, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r12v7 */
    /* JADX WARNING: type inference failed for: r0v43 */
    /* JADX WARNING: type inference failed for: r12v8, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r0v52 */
    /* JADX WARNING: type inference failed for: r12v9, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r12v10 */
    /* JADX WARNING: type inference failed for: r0v58, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r12v11 */
    /* JADX WARNING: type inference failed for: r12v12 */
    /* JADX WARNING: type inference failed for: r12v13 */
    /* JADX WARNING: type inference failed for: r12v14 */
    /* JADX WARNING: type inference failed for: r12v15 */
    /* JADX WARNING: type inference failed for: r12v16 */
    /* JADX WARNING: type inference failed for: r12v17 */
    /* JADX WARNING: type inference failed for: r12v18 */
    /* JADX WARNING: type inference failed for: r12v19 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r12v1
  assigns: []
  uses: []
  mth insns count: 374
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
    /* JADX WARNING: Unknown variable types count: 15 */
    public void rewriteClassDef(Object[] saved, Translator tr) {
        ? r12;
        ? r122;
        ClassExp oexp = saved[0];
        Object components = saved[1];
        Vector inits = saved[2];
        LambdaExp method_list = saved[3];
        Object superlist = saved[4];
        Object classNamePair = saved[5];
        oexp.firstChild = method_list;
        int num_supers = Translator.listLength(superlist);
        if (num_supers < 0) {
            tr.error('e', "object superclass specification not a list");
            num_supers = 0;
        }
        Expression[] supers = new Expression[num_supers];
        for (int i = 0; i < num_supers; i++) {
            while (superlist instanceof SyntaxForm) {
                superlist = ((SyntaxForm) superlist).getDatum();
            }
            Pair superpair = (Pair) superlist;
            supers[i] = tr.rewrite_car(superpair, false);
            if (supers[i] instanceof ReferenceExp) {
                Declaration decl = Declaration.followAliases(((ReferenceExp) supers[i]).getBinding());
                if (decl != null) {
                    Expression svalue = decl.getValue();
                    if (svalue instanceof ClassExp) {
                        ((ClassExp) svalue).setFlag(131072);
                    }
                }
            }
            superlist = superpair.getCdr();
        }
        if (classNamePair != null) {
            Object classNameVal = tr.rewrite_car((Pair) classNamePair, false).valueIfConstant();
            if (classNameVal instanceof CharSequence) {
                String classNameSpecifier = classNameVal.toString();
                if (classNameSpecifier.length() > 0) {
                    oexp.classNameSpecifier = classNameSpecifier;
                }
            }
            Object savedPos = tr.pushPositionOf(classNamePair);
            tr.error('e', "class-name specifier must be a non-empty string literal");
            tr.popPositionOf(savedPos);
        }
        oexp.supers = supers;
        oexp.setTypes(tr);
        int len = inits.size();
        for (int i2 = 0; i2 < len; i2 += 2) {
            Object init = inits.elementAt(i2 + 1);
            if (init != null) {
                rewriteInit(inits.elementAt(i2), oexp, (Pair) init, tr, null);
            }
        }
        tr.push((ScopeExp) oexp);
        LambdaExp meth = method_list;
        int init_index = 0;
        SyntaxForm componentsSyntax = null;
        Object obj = components;
        while (obj != LList.Empty) {
            while (obj instanceof SyntaxForm) {
                componentsSyntax = (SyntaxForm) obj;
                obj = componentsSyntax.getDatum();
            }
            Pair pair = (Pair) obj;
            Object savedPos1 = tr.pushPositionOf(pair);
            Object pair_car = pair.getCar();
            SyntaxForm memberSyntax = componentsSyntax;
            while (pair_car instanceof SyntaxForm) {
                memberSyntax = (SyntaxForm) pair_car;
                pair_car = memberSyntax.getDatum();
            }
            try {
                obj = pair.getCdr();
                if (!(pair_car instanceof Keyword) || !(obj instanceof Pair)) {
                    ? r39 = (Pair) pair_car;
                    Object pair_car2 = r39.getCar();
                    SyntaxForm memberCarSyntax = memberSyntax;
                    while (pair_car2 instanceof SyntaxForm) {
                        memberCarSyntax = (SyntaxForm) pair_car2;
                        pair_car2 = memberCarSyntax.getDatum();
                    }
                    if ((pair_car2 instanceof String) || (pair_car2 instanceof Symbol) || (pair_car2 instanceof Keyword)) {
                        Object type = null;
                        int nKeywords = 0;
                        Pair initPair = null;
                        SyntaxForm initSyntax = null;
                        ? datum = pair_car2 instanceof Keyword ? r39 : r39.getCdr();
                        while (true) {
                            if (r12 == LList.Empty) {
                                break;
                            }
                            while (r12 instanceof SyntaxForm) {
                                memberSyntax = (SyntaxForm) r12;
                                r12 = memberSyntax.getDatum();
                            }
                            Pair pair2 = (Pair) r12;
                            Object key = pair2.getCar();
                            while (key instanceof SyntaxForm) {
                                key = ((SyntaxForm) key).getDatum();
                            }
                            Object savedPos2 = tr.pushPositionOf(pair2);
                            ? cdr = pair2.getCdr();
                            if ((key == coloncolon || (key instanceof Keyword)) && (cdr instanceof Pair)) {
                                nKeywords++;
                                Pair pair3 = (Pair) cdr;
                                Object value = pair3.getCar();
                                ? cdr2 = pair3.getCdr();
                                if (key == coloncolon || key == typeKeyword) {
                                    type = value;
                                    r122 = cdr2;
                                } else if (key == initKeyword || key == initformKeyword || key == init_formKeyword || key == init_valueKeyword) {
                                    initPair = pair3;
                                    initSyntax = memberSyntax;
                                    r122 = cdr2;
                                } else {
                                    r122 = cdr2;
                                }
                            } else if (cdr != LList.Empty || initPair != null) {
                                if (!(cdr instanceof Pair) || nKeywords != 0 || initPair != null || type != null) {
                                    break;
                                }
                                Pair pair4 = (Pair) cdr;
                                if (pair4.getCdr() != LList.Empty) {
                                    break;
                                }
                                type = key;
                                initPair = pair4;
                                initSyntax = memberSyntax;
                                r122 = pair4.getCdr();
                            } else {
                                initPair = pair2;
                                initSyntax = memberSyntax;
                                r122 = cdr;
                            }
                            tr.popPositionOf(savedPos2);
                            datum = r122;
                        }
                        if (initPair != null) {
                            int init_index2 = init_index + 1;
                            try {
                                Object d = inits.elementAt(init_index);
                                if (d instanceof Declaration) {
                                    boolean flag = ((Declaration) d).getFlag(2048);
                                } else if (d == Boolean.TRUE) {
                                }
                                init_index = init_index2 + 1;
                                if (inits.elementAt(init_index2) == null) {
                                    rewriteInit(d, oexp, initPair, tr, initSyntax);
                                }
                            } catch (Throwable th) {
                                th = th;
                                int i3 = init_index2;
                                tr.popPositionOf(savedPos1);
                                throw th;
                            }
                        }
                    } else if (pair_car2 instanceof Pair) {
                        ScopeExp save_scope = tr.currentScope();
                        if (memberSyntax != null) {
                            tr.setCurrentScope(memberSyntax.getScope());
                        }
                        if ("*init*".equals(meth.getName())) {
                            meth.setReturnType(Type.voidType);
                        }
                        Translator.setLine((Expression) meth, (Object) r39);
                        LambdaExp saveLambda = tr.curMethodLambda;
                        tr.curMethodLambda = meth;
                        this.lambda.rewrite(meth, ((Pair) pair_car2).getCdr(), r39.getCdr(), tr, (memberCarSyntax == null || (memberSyntax != null && memberCarSyntax.getScope() == memberSyntax.getScope())) ? null : memberCarSyntax.getScope());
                        tr.curMethodLambda = saveLambda;
                        if (memberSyntax != null) {
                            tr.setCurrentScope(save_scope);
                        }
                        meth = meth.nextSibling;
                    } else {
                        tr.syntaxError("invalid field/method definition");
                    }
                    tr.popPositionOf(savedPos1);
                } else {
                    obj = ((Pair) obj).getCdr();
                    tr.popPositionOf(savedPos1);
                }
            } catch (Throwable th2) {
                th = th2;
                tr.popPositionOf(savedPos1);
                throw th;
            }
        }
        if (oexp.initMethod != null) {
            oexp.initMethod.outer = oexp;
        }
        if (oexp.clinitMethod != null) {
            oexp.clinitMethod.outer = oexp;
        }
        tr.pop(oexp);
        oexp.declareParts(tr);
    }

    private static void rewriteInit(Object d, ClassExp oexp, Pair initPair, Translator tr, SyntaxForm initSyntax) {
        Expression initValue;
        boolean isStatic = d instanceof Declaration ? ((Declaration) d).getFlag(2048) : d == Boolean.TRUE;
        LambdaExp initMethod = isStatic ? oexp.clinitMethod : oexp.initMethod;
        if (initMethod == null) {
            initMethod = new LambdaExp((Expression) new BeginExp());
            initMethod.setClassMethod(true);
            initMethod.setReturnType(Type.voidType);
            if (isStatic) {
                initMethod.setName("$clinit$");
                oexp.clinitMethod = initMethod;
            } else {
                initMethod.setName("$finit$");
                oexp.initMethod = initMethod;
                initMethod.add(null, new Declaration((Object) ThisExp.THIS_NAME));
            }
            initMethod.nextSibling = oexp.firstChild;
            oexp.firstChild = initMethod;
        }
        tr.push((ScopeExp) initMethod);
        LambdaExp saveLambda = tr.curMethodLambda;
        tr.curMethodLambda = initMethod;
        Expression initValue2 = tr.rewrite_car(initPair, initSyntax);
        if (d instanceof Declaration) {
            Declaration decl = (Declaration) d;
            SetExp sexp = new SetExp(decl, initValue2);
            sexp.setLocation(decl);
            decl.noteValue(null);
            initValue = sexp;
        } else {
            initValue = Compilation.makeCoercion(initValue2, (Expression) new QuoteExp(Type.voidType));
        }
        ((BeginExp) initMethod.body).add(initValue);
        tr.curMethodLambda = saveLambda;
        tr.pop(initMethod);
    }

    static boolean matches(Object exp, String tag, Translator tr) {
        String value;
        if (exp instanceof Keyword) {
            value = ((Keyword) exp).getName();
        } else if (exp instanceof FString) {
            value = ((FString) exp).toString();
        } else if (!(exp instanceof Pair)) {
            return false;
        } else {
            Object qvalue = tr.matchQuoted((Pair) exp);
            if (!(qvalue instanceof SimpleSymbol)) {
                return false;
            }
            value = qvalue.toString();
        }
        if (tag == null || tag.equals(value)) {
            return true;
        }
        return false;
    }

    static long addAccessFlags(Object value, long previous, long allowed, String kind, Translator tr) {
        long flags = matchAccess(value, tr);
        if (flags == 0) {
            tr.error('e', "unknown access specifier " + value);
        } else if (((-1 ^ allowed) & flags) != 0) {
            tr.error('e', "invalid " + kind + " access specifier " + value);
        } else if ((previous & flags) != 0) {
            tr.error('w', "duplicate " + kind + " access specifiers " + value);
        }
        return previous | flags;
    }

    static long matchAccess(Object value, Translator tr) {
        while (value instanceof SyntaxForm) {
            value = ((SyntaxForm) value).getDatum();
        }
        if (value instanceof Pair) {
            Pair pair = (Pair) value;
            value = tr.matchQuoted((Pair) value);
            if (value instanceof Pair) {
                return matchAccess2((Pair) value, tr);
            }
        }
        return matchAccess1(value, tr);
    }

    private static long matchAccess2(Pair pair, Translator tr) {
        long icar = matchAccess1(pair.getCar(), tr);
        Object cdr = pair.getCdr();
        if (cdr == LList.Empty || icar == 0) {
            return icar;
        }
        if (cdr instanceof Pair) {
            long icdr = matchAccess2((Pair) cdr, tr);
            if (icdr != 0) {
                return icar | icdr;
            }
        }
        return 0;
    }

    private static long matchAccess1(Object value, Translator tr) {
        if (value instanceof Keyword) {
            value = ((Keyword) value).getName();
        } else if (value instanceof FString) {
            value = ((FString) value).toString();
        } else if (value instanceof SimpleSymbol) {
            value = value.toString();
        }
        if ("private".equals(value)) {
            return 16777216;
        }
        if ("protected".equals(value)) {
            return 33554432;
        }
        if ("public".equals(value)) {
            return 67108864;
        }
        if ("package".equals(value)) {
            return 134217728;
        }
        if ("volatile".equals(value)) {
            return Declaration.VOLATILE_ACCESS;
        }
        if ("transient".equals(value)) {
            return Declaration.TRANSIENT_ACCESS;
        }
        if ("enum".equals(value)) {
            return Declaration.ENUM_ACCESS;
        }
        if ("final".equals(value)) {
            return Declaration.FINAL_ACCESS;
        }
        return 0;
    }
}
