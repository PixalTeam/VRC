package gnu.kawa.reflect;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.Field;
import gnu.bytecode.Member;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.Keyword;
import gnu.expr.LetExp;
import gnu.expr.PairClassType;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.TypeValue;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;

public class CompileInvoke {
    /* JADX WARNING: type inference failed for: r66v0 */
    /* JADX WARNING: type inference failed for: r66v2, types: [gnu.bytecode.ObjectType] */
    /* JADX WARNING: type inference failed for: r66v3, types: [gnu.bytecode.ObjectType] */
    /* JADX WARNING: type inference failed for: r0v12 */
    /* JADX WARNING: type inference failed for: r5v0, types: [gnu.bytecode.Type, gnu.bytecode.ObjectType] */
    /* JADX WARNING: type inference failed for: r11v10, types: [gnu.bytecode.ObjectType] */
    /* JADX WARNING: type inference failed for: r22v0 */
    /* JADX WARNING: type inference failed for: r22v1 */
    /* JADX WARNING: type inference failed for: r1v11, types: [gnu.bytecode.Type] */
    /* JADX WARNING: type inference failed for: r22v2 */
    /* JADX WARNING: type inference failed for: r22v3 */
    /* JADX WARNING: type inference failed for: r58v0, types: [gnu.bytecode.Type] */
    /* JADX WARNING: type inference failed for: r0v82 */
    /* JADX WARNING: type inference failed for: r0v83 */
    /* JADX WARNING: type inference failed for: r0v84, types: [gnu.bytecode.Type] */
    /* JADX WARNING: type inference failed for: r1v14, types: [gnu.bytecode.Type] */
    /* JADX WARNING: type inference failed for: r22v4, types: [gnu.bytecode.Type] */
    /* JADX WARNING: type inference failed for: r22v5 */
    /* JADX WARNING: type inference failed for: r22v6 */
    /* JADX WARNING: type inference failed for: r22v7 */
    /* JADX WARNING: type inference failed for: r22v8 */
    /* JADX WARNING: type inference failed for: r22v9 */
    /* JADX WARNING: type inference failed for: r17v1 */
    /* JADX WARNING: type inference failed for: r0v96, types: [gnu.expr.ApplyExp] */
    /* JADX WARNING: type inference failed for: r29v1 */
    /* JADX WARNING: type inference failed for: r29v2 */
    /* JADX WARNING: type inference failed for: r0v97, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r17v2 */
    /* JADX WARNING: type inference failed for: r29v3 */
    /* JADX WARNING: type inference failed for: r1v18, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r29v4 */
    /* JADX WARNING: type inference failed for: r10v156 */
    /* JADX WARNING: type inference failed for: r0v144 */
    /* JADX WARNING: type inference failed for: r22v10 */
    /* JADX WARNING: type inference failed for: r0v175, types: [gnu.bytecode.ClassType] */
    /* JADX WARNING: type inference failed for: r66v4 */
    /* JADX WARNING: type inference failed for: r66v5 */
    /* JADX WARNING: type inference failed for: r66v6 */
    /* JADX WARNING: type inference failed for: r22v12 */
    /* JADX WARNING: type inference failed for: r22v13 */
    /* JADX WARNING: type inference failed for: r22v14 */
    /* JADX WARNING: type inference failed for: r22v15 */
    /* JADX WARNING: type inference failed for: r22v16 */
    /* JADX WARNING: type inference failed for: r22v17 */
    /* JADX WARNING: type inference failed for: r22v18 */
    /* JADX WARNING: type inference failed for: r22v19 */
    /* JADX WARNING: type inference failed for: r22v20 */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x029f, code lost:
        if ((gnu.kawa.reflect.ClassMethods.selectApplicable(r4, new gnu.bytecode.Type[]{gnu.expr.Compilation.typeClassType}) >> 32) == 1) goto L_0x02a1;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 28 */
    public static Expression validateApplyInvoke(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        ? r66;
        int objIndex;
        int argsStartIndex;
        int margsLength;
        int okCount;
        int maybeCount;
        char c;
        char c2;
        ? r22;
        int i;
        ? r222;
        ApplyExp ae;
        int sargs;
        Type stype;
        Invoke iproc = (Invoke) proc;
        char kind = iproc.kind;
        Compilation comp = visitor.getCompilation();
        Expression[] args = exp.getArgs();
        int nargs = args.length;
        if (!comp.mustCompile || nargs == 0 || ((kind == 'V' || kind == '*') && nargs == 1)) {
            exp.visitArgs(visitor);
            return exp;
        }
        Expression arg0 = visitor.visit(args[0], (Type) null);
        args[0] = arg0;
        Type type0 = (kind == 'V' || kind == '*') ? arg0.getType() : iproc.language.getTypeFor(arg0);
        if (!(type0 instanceof PairClassType) || kind != 'N') {
            r66 = type0 instanceof ObjectType ? (ObjectType) type0 : 0;
        } else {
            r66 = ((PairClassType) type0).instanceType;
        }
        String name = getMethodName(args, kind);
        if (kind == 'V' || kind == '*') {
            margsLength = nargs - 1;
            argsStartIndex = 2;
            objIndex = 0;
        } else if (kind == 'N') {
            margsLength = nargs;
            argsStartIndex = 0;
            objIndex = -1;
        } else if (kind == 'S' || kind == 's') {
            margsLength = nargs - 2;
            argsStartIndex = 2;
            objIndex = -1;
        } else if (kind == 'P') {
            margsLength = nargs - 2;
            argsStartIndex = 3;
            objIndex = 1;
        } else {
            exp.visitArgs(visitor);
            return exp;
        }
        if (kind != 'N' || !(r66 instanceof ArrayType)) {
            if (!(r66 == 0 || name == null)) {
                if ((r66 instanceof TypeValue) && kind == 'N') {
                    Procedure constructor = ((TypeValue) r66).getConstructor();
                    if (constructor != null) {
                        Expression[] xargs = new Expression[(nargs - 1)];
                        System.arraycopy(args, 1, xargs, 0, nargs - 1);
                        return visitor.visit((Expression) new ApplyExp(constructor, xargs), required);
                    }
                }
                ClassType caller = comp == null ? null : comp.curClass != null ? comp.curClass : comp.mainClass;
                ? r5 = r66;
                try {
                    PrimProcedure[] methods = getMethods(r5, name, caller, iproc);
                    int numCode = ClassMethods.selectApplicable(methods, margsLength);
                    int index = -1;
                    if (kind == 'N') {
                        int keywordStart = hasKeywordArgument(1, args);
                        if (keywordStart >= args.length) {
                            if (numCode <= 0) {
                            }
                        }
                        Object[] slots = checkKeywords(r5, args, keywordStart, caller);
                        if (slots.length * 2 == args.length - keywordStart || ClassMethods.selectApplicable(ClassMethods.getMethods(r5, "add", 'V', null, iproc.language), 2) > 0) {
                            StringBuffer errbuf = null;
                            for (int i2 = 0; i2 < slots.length; i2++) {
                                if (slots[i2] instanceof String) {
                                    if (errbuf == null) {
                                        errbuf = new StringBuffer();
                                        errbuf.append("no field or setter ");
                                    } else {
                                        errbuf.append(", ");
                                    }
                                    errbuf.append('`');
                                    errbuf.append(slots[i2]);
                                    errbuf.append('\'');
                                }
                            }
                            if (errbuf != null) {
                                errbuf.append(" in class ");
                                errbuf.append(r66.getName());
                                comp.error('w', errbuf.toString());
                                return exp;
                            }
                            if (keywordStart < args.length) {
                                Expression[] xargs2 = new Expression[keywordStart];
                                System.arraycopy(args, 0, xargs2, 0, keywordStart);
                                ae = (ApplyExp) visitor.visit((Expression) new ApplyExp(exp.getFunction(), xargs2), (Type) r5);
                            } else {
                                ApplyExp applyExp = new ApplyExp((Procedure) methods[0], arg0);
                                ae = applyExp;
                            }
                            ae.setType(r5);
                            ? r29 = ae;
                            if (args.length > 0) {
                                int i3 = 0;
                                ApplyExp ae2 = ae;
                                while (i3 < slots.length) {
                                    Object slot = slots[i3];
                                    if (slot instanceof Method) {
                                        stype = ((Method) slot).getParameterTypes()[0];
                                    } else if (slot instanceof Field) {
                                        stype = ((Field) slot).getType();
                                    } else {
                                        stype = null;
                                    }
                                    if (stype != null) {
                                        stype = iproc.language.getLangTypeFor(stype);
                                    }
                                    ApplyExp applyExp2 = new ApplyExp((Procedure) SlotSet.setFieldReturnObject, (Expression[]) new Expression[]{ae2, new QuoteExp(slot), visitor.visit(args[(i3 * 2) + keywordStart + 1], stype)});
                                    applyExp2.setType(r5);
                                    i3++;
                                    ae2 = applyExp2;
                                }
                                if (keywordStart == args.length) {
                                    sargs = 1;
                                } else {
                                    sargs = (slots.length * 2) + keywordStart;
                                }
                                r29 = ae2;
                                if (sargs < args.length) {
                                    LetExp letExp = new LetExp(new Expression[]{r29});
                                    Declaration adecl = letExp.addDeclaration(null, r5);
                                    adecl.noteValue(r29);
                                    BeginExp begin = new BeginExp();
                                    for (int i4 = sargs; i4 < args.length; i4++) {
                                        begin.add(visitor.visit((Expression) new ApplyExp((Procedure) Invoke.invoke, new ReferenceExp(adecl), QuoteExp.getInstance("add"), args[i4]), (Type) null));
                                    }
                                    begin.add(new ReferenceExp(adecl));
                                    letExp.body = begin;
                                    r29 = letExp;
                                }
                            }
                            return visitor.checkType(r29.setLine((Expression) exp), required);
                        }
                    }
                    if (numCode >= 0) {
                        int i5 = 1;
                        while (i5 < nargs) {
                            ? r223 = 0;
                            boolean last = i5 == nargs + -1;
                            if ((kind == 'P' && i5 == 2) || (kind != 'N' && i5 == 1)) {
                                r22 = 0;
                            } else if (kind == 'P' && i5 == 1) {
                                r22 = r5;
                            } else if (numCode <= 0) {
                                r22 = r223;
                            } else {
                                if (kind == 'N') {
                                    i = 1;
                                } else {
                                    i = argsStartIndex;
                                }
                                int pi = i5 - i;
                                int j = 0;
                                ? r224 = r223;
                                while (j < numCode) {
                                    PrimProcedure pproc = methods[j];
                                    int pii = pi + ((kind == 'S' || !pproc.takesTarget()) ? 0 : 1);
                                    if (!last || !pproc.takesVarArgs() || pii != pproc.minArgs()) {
                                        ? parameterType = pproc.getParameterType(pii);
                                        if (j == 0) {
                                            r222 = parameterType;
                                        } else {
                                            r222 = (parameterType instanceof PrimType) != (r224 instanceof PrimType) ? 0 : Type.lowestCommonSuperType(r224, parameterType);
                                        }
                                    } else {
                                        r222 = 0;
                                    }
                                    if (r222 == 0) {
                                        r22 = r222;
                                        break;
                                    }
                                    j++;
                                    r224 = r222;
                                }
                                r22 = r224;
                            }
                            args[i5] = visitor.visit(args[i5], (Type) r22);
                            i5++;
                        }
                        long num = selectApplicable(methods, r5, args, margsLength, argsStartIndex, objIndex);
                        okCount = (int) (num >> 32);
                        maybeCount = (int) num;
                    } else {
                        okCount = 0;
                        maybeCount = 0;
                    }
                    int nmethods = methods.length;
                    if (okCount + maybeCount == 0 && kind == 'N') {
                        methods = getMethods(r5, "valueOf", caller, Invoke.invokeStatic);
                        argsStartIndex = 1;
                        margsLength = nargs - 1;
                        long num2 = selectApplicable(methods, r5, args, margsLength, 1, -1);
                        okCount = (int) (num2 >> 32);
                        maybeCount = (int) num2;
                    }
                    if (okCount + maybeCount == 0) {
                        if (kind == 'P' || comp.warnInvokeUnknownMethod()) {
                            if (kind == 'N') {
                                name = name + "/valueOf";
                            }
                            StringBuilder sbuf = new StringBuilder();
                            if (methods.length + nmethods == 0) {
                                sbuf.append("no accessible method '");
                            } else if (numCode == -983040) {
                                sbuf.append("too few arguments for method '");
                            } else if (numCode == -917504) {
                                sbuf.append("too many arguments for method '");
                            } else {
                                sbuf.append("no possibly applicable method '");
                            }
                            sbuf.append(name);
                            sbuf.append("' in ");
                            sbuf.append(r66.getName());
                            if (kind == 'P') {
                                c2 = 'e';
                            } else {
                                c2 = 'w';
                            }
                            comp.error(c2, sbuf.toString());
                        }
                    } else if (okCount == 1 || (okCount == 0 && maybeCount == 1)) {
                        index = 0;
                    } else if (okCount > 0) {
                        index = MethodProc.mostSpecific((MethodProc[]) methods, okCount);
                        if (index < 0 && kind == 'S') {
                            int i6 = 0;
                            while (true) {
                                if (i6 >= okCount) {
                                    break;
                                }
                                if (methods[i6].getStaticFlag()) {
                                    if (index >= 0) {
                                        index = -1;
                                        break;
                                    }
                                    index = i6;
                                }
                                i6++;
                            }
                        }
                        if (index < 0 && (kind == 'P' || comp.warnInvokeUnknownMethod())) {
                            StringBuffer sbuf2 = new StringBuffer();
                            sbuf2.append("more than one definitely applicable method `");
                            sbuf2.append(name);
                            sbuf2.append("' in ");
                            sbuf2.append(r66.getName());
                            append(methods, okCount, sbuf2);
                            if (kind == 'P') {
                                c = 'e';
                            } else {
                                c = 'w';
                            }
                            comp.error(c, sbuf2.toString());
                        }
                    } else if (kind == 'P' || comp.warnInvokeUnknownMethod()) {
                        StringBuffer sbuf3 = new StringBuffer();
                        sbuf3.append("more than one possibly applicable method '");
                        sbuf3.append(name);
                        sbuf3.append("' in ");
                        sbuf3.append(r66.getName());
                        append(methods, maybeCount, sbuf3);
                        comp.error(kind == 'P' ? 'e' : 'w', sbuf3.toString());
                    }
                    if (index >= 0) {
                        Expression[] margs = new Expression[margsLength];
                        PrimProcedure method = methods[index];
                        boolean takesVarArgs = method.takesVarArgs();
                        int dst = 0;
                        if (objIndex >= 0) {
                            int dst2 = 0 + 1;
                            margs[0] = args[objIndex];
                            dst = dst2;
                        }
                        int src = argsStartIndex;
                        while (src < args.length && dst < margs.length) {
                            margs[dst] = args[src];
                            src++;
                            dst++;
                        }
                        ApplyExp applyExp3 = new ApplyExp((Procedure) method, margs);
                        applyExp3.setLine((Expression) exp);
                        return visitor.visitApplyOnly(applyExp3, required);
                    }
                } catch (Exception e) {
                    comp.error('w', "unknown class: " + r66.getName());
                    return exp;
                }
            }
            exp.visitArgs(visitor);
            return exp;
        }
        ArrayType atype = (ArrayType) r66;
        Type elementType = atype.getComponentType();
        Expression sizeArg = null;
        boolean lengthSpecified = false;
        if (args.length >= 3 && (args[1] instanceof QuoteExp)) {
            Object arg1 = ((QuoteExp) args[1]).getValue();
            if (arg1 instanceof Keyword) {
                String name2 = ((Keyword) arg1).getName();
                if ("length".equals(name2) || "size".equals(name2)) {
                    sizeArg = args[2];
                    lengthSpecified = true;
                }
            }
        }
        if (sizeArg == null) {
            sizeArg = QuoteExp.getInstance(new Integer(args.length - 1));
        }
        ApplyExp applyExp4 = new ApplyExp((Procedure) new ArrayNew(elementType), visitor.visit(sizeArg, (Type) Type.intType));
        applyExp4.setType(atype);
        if (lengthSpecified && args.length == 3) {
            return applyExp4;
        }
        LetExp letExp2 = new LetExp(new Expression[]{applyExp4});
        Declaration adecl2 = letExp2.addDeclaration(null, atype);
        adecl2.noteValue(applyExp4);
        BeginExp begin2 = new BeginExp();
        int index2 = 0;
        int i7 = lengthSpecified ? 3 : 1;
        while (i7 < args.length) {
            Expression arg = args[i7];
            if (lengthSpecified && i7 + 1 < args.length && (arg instanceof QuoteExp)) {
                Object key = ((QuoteExp) arg).getValue();
                if (key instanceof Keyword) {
                    String kname = ((Keyword) key).getName();
                    try {
                        index2 = Integer.parseInt(kname);
                        i7++;
                        arg = args[i7];
                    } catch (Throwable th) {
                        comp.error('e', "non-integer keyword '" + kname + "' in array constructor");
                        return exp;
                    }
                }
            }
            begin2.add(new ApplyExp((Procedure) new ArraySet(elementType), new ReferenceExp(adecl2), QuoteExp.getInstance(new Integer(index2)), visitor.visit(arg, elementType)));
            index2++;
            i7++;
        }
        begin2.add(new ReferenceExp(adecl2));
        letExp2.body = begin2;
        return letExp2;
    }

    static Object[] checkKeywords(ObjectType type, Expression[] args, int start, ClassType caller) {
        int len = args.length;
        int npairs = 0;
        while ((npairs * 2) + start + 1 < len && (args[(npairs * 2) + start].valueIfConstant() instanceof Keyword)) {
            npairs++;
        }
        Object[] fields = new Object[npairs];
        for (int i = 0; i < npairs; i++) {
            String name = ((Keyword) args[(i * 2) + start].valueIfConstant()).getName();
            Member slot = SlotSet.lookupMember(type, name, caller);
            if (slot == null) {
                slot = type.getMethod(ClassExp.slotToMethodName("add", name), SlotSet.type1Array);
            }
            fields[i] = slot != null ? slot : name;
        }
        return fields;
    }

    private static String getMethodName(Expression[] args, char kind) {
        if (kind == 'N') {
            return "<init>";
        }
        int nameIndex = kind == 'P' ? 2 : 1;
        if (args.length >= nameIndex + 1) {
            return ClassMethods.checkName(args[nameIndex], false);
        }
        return null;
    }

    private static void append(PrimProcedure[] methods, int mcount, StringBuffer sbuf) {
        for (int i = 0; i < mcount; i++) {
            sbuf.append("\n  candidate: ");
            sbuf.append(methods[i]);
        }
    }

    protected static PrimProcedure[] getMethods(ObjectType ctype, String mname, ClassType caller, Invoke iproc) {
        char c = 'P';
        int kind = iproc.kind;
        if (kind != 80) {
            c = (kind == 42 || kind == 86) ? 'V' : 0;
        }
        return ClassMethods.getMethods(ctype, mname, c, caller, iproc.language);
    }

    static int hasKeywordArgument(int argsStartIndex, Expression[] args) {
        for (int i = argsStartIndex; i < args.length; i++) {
            if (args[i].valueIfConstant() instanceof Keyword) {
                return i;
            }
        }
        return args.length;
    }

    private static long selectApplicable(PrimProcedure[] methods, ObjectType ctype, Expression[] args, int margsLength, int argsStartIndex, int objIndex) {
        Type[] atypes = new Type[margsLength];
        int dst = 0;
        if (objIndex >= 0) {
            int dst2 = 0 + 1;
            atypes[0] = ctype;
            dst = dst2;
        }
        int src = argsStartIndex;
        while (src < args.length && dst < atypes.length) {
            Expression arg = args[src];
            Type atype = null;
            if (InlineCalls.checkIntValue(arg) != null) {
                atype = Type.intType;
            } else if (InlineCalls.checkLongValue(arg) != null) {
                atype = Type.longType;
            } else if (0 == 0) {
                atype = arg.getType();
            }
            atypes[dst] = atype;
            src++;
            dst++;
        }
        return ClassMethods.selectApplicable(methods, atypes);
    }

    public static synchronized PrimProcedure getStaticMethod(ClassType type, String name, Expression[] args) {
        int index;
        PrimProcedure primProcedure;
        synchronized (CompileInvoke.class) {
            PrimProcedure[] methods = getMethods(type, name, null, Invoke.invokeStatic);
            long num = selectApplicable(methods, type, args, args.length, 0, -1);
            int okCount = (int) (num >> 32);
            int maybeCount = (int) num;
            if (methods == null) {
                index = -1;
            } else if (okCount > 0) {
                index = MethodProc.mostSpecific((MethodProc[]) methods, okCount);
            } else if (maybeCount == 1) {
                index = 0;
            } else {
                index = -1;
            }
            if (index < 0) {
                primProcedure = null;
            } else {
                primProcedure = methods[index];
            }
        }
        return primProcedure;
    }
}
