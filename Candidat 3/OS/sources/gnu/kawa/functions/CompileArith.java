package gnu.kawa.functions;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.IgnoreTarget;
import gnu.expr.InlineCalls;
import gnu.expr.Inlineable;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.lispexpr.LangPrimType;
import gnu.mapping.Procedure;
import gnu.math.IntNum;

public class CompileArith implements Inlineable {
    public static CompileArith $Mn = new CompileArith(AddOp.$Mn, 2);
    public static CompileArith $Pl = new CompileArith(AddOp.$Pl, 1);
    int op;
    Procedure proc;

    CompileArith(Object proc2, int op2) {
        this.proc = (Procedure) proc2;
        this.op = op2;
    }

    public static CompileArith forMul(Object proc2) {
        return new CompileArith(proc2, 3);
    }

    public static CompileArith forDiv(Object proc2) {
        return new CompileArith(proc2, ((DivideOp) proc2).op);
    }

    public static CompileArith forBitwise(Object proc2) {
        return new CompileArith(proc2, ((BitwiseOp) proc2).op);
    }

    public static boolean appropriateIntConstant(Expression[] args, int iarg, InlineCalls visitor) {
        Expression exp = visitor.fixIntValue(args[iarg]);
        if (exp == null) {
            return false;
        }
        args[iarg] = exp;
        return true;
    }

    public static boolean appropriateLongConstant(Expression[] args, int iarg, InlineCalls visitor) {
        Expression exp = visitor.fixLongValue(args[iarg]);
        if (exp == null) {
            return false;
        }
        args[iarg] = exp;
        return true;
    }

    public static Expression validateApplyArithOp(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc2) {
        int rkind;
        int op2 = ((ArithOp) proc2).op;
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length > 2) {
            return pairwise(proc2, exp.getFunction(), args, visitor);
        }
        Expression folded = exp.inlineIfConstant(proc2, visitor);
        if (folded != exp) {
            return folded;
        }
        int rkind2 = 0;
        if (args.length == 2 || args.length == 1) {
            int kind1 = Arithmetic.classifyType(args[0].getType());
            if (args.length != 2 || (op2 >= 9 && op2 <= 12)) {
                rkind = kind1;
            } else {
                int kind2 = Arithmetic.classifyType(args[1].getType());
                rkind = getReturnKind(kind1, kind2, op2);
                if (rkind == 4) {
                    if (kind1 == 1 && appropriateIntConstant(args, 1, visitor)) {
                        rkind = 1;
                    } else if (kind2 == 1 && appropriateIntConstant(args, 0, visitor)) {
                        rkind = 1;
                    } else if (kind1 == 2 && appropriateLongConstant(args, 1, visitor)) {
                        rkind = 2;
                    } else if (kind2 == 2 && appropriateLongConstant(args, 0, visitor)) {
                        rkind = 2;
                    }
                }
            }
            rkind2 = adjustReturnKind(rkind, op2);
            exp.setType(Arithmetic.kindType(rkind2));
        }
        if (!visitor.getCompilation().mustCompile) {
            return exp;
        }
        switch (op2) {
            case 1:
            case 2:
                return validateApplyAdd((AddOp) proc2, exp, visitor);
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return validateApplyDiv((DivideOp) proc2, exp, visitor);
            case 16:
                if (rkind2 > 0) {
                    return validateApplyNot(exp, rkind2, visitor);
                }
                return exp;
            default:
                return exp;
        }
    }

    /* JADX WARNING: type inference failed for: r21v0, types: [gnu.bytecode.Type] */
    /* JADX WARNING: type inference failed for: r23v0, types: [gnu.bytecode.PrimType] */
    /* JADX WARNING: type inference failed for: r23v1 */
    /* JADX WARNING: type inference failed for: r23v2, types: [gnu.bytecode.PrimType] */
    /* JADX WARNING: type inference failed for: r23v3, types: [gnu.bytecode.PrimType] */
    /* JADX WARNING: type inference failed for: r23v4, types: [gnu.bytecode.PrimType] */
    /* JADX WARNING: type inference failed for: r23v5, types: [gnu.bytecode.Type] */
    /* JADX WARNING: type inference failed for: r23v6 */
    /* JADX WARNING: type inference failed for: r2v0, types: [gnu.bytecode.Type] */
    /* JADX WARNING: type inference failed for: r23v7, types: [gnu.bytecode.Type] */
    /* JADX WARNING: type inference failed for: r23v8 */
    /* JADX WARNING: type inference failed for: r23v9, types: [gnu.kawa.lispexpr.LangObjType] */
    /* JADX WARNING: type inference failed for: r23v10, types: [gnu.bytecode.PrimType] */
    /* JADX WARNING: type inference failed for: r23v11, types: [gnu.bytecode.PrimType] */
    /* JADX WARNING: type inference failed for: r23v12 */
    /* JADX WARNING: type inference failed for: r23v13 */
    /* JADX WARNING: type inference failed for: r23v14 */
    /* JADX WARNING: type inference failed for: r23v15 */
    /* JADX WARNING: type inference failed for: r23v16 */
    /* JADX WARNING: type inference failed for: r23v17 */
    /* JADX WARNING: type inference failed for: r23v18 */
    /* JADX WARNING: type inference failed for: r23v19 */
    /* JADX WARNING: type inference failed for: r23v20 */
    /* JADX WARNING: type inference failed for: r23v21 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 12 */
    public void compile(ApplyExp exp, Compilation comp, Target target) {
        ? r23;
        ? r232;
        ? r233;
        Method meth;
        Expression[] args = exp.getArgs();
        int len = args.length;
        if (len == 0) {
            comp.compileConstant(((ArithOp) this.proc).defaultResult(), target);
        } else if (len == 1 || (target instanceof IgnoreTarget)) {
            ApplyExp.compile(exp, comp, target);
        } else {
            int kind1 = Arithmetic.classifyType(args[0].getType());
            int kind2 = Arithmetic.classifyType(args[1].getType());
            int kind = getReturnKind(kind1, kind2, this.op);
            ? kindType = Arithmetic.kindType(kind);
            if (kind == 0 || len != 2) {
                ApplyExp.compile(exp, comp, target);
                return;
            }
            int tkind = Arithmetic.classifyType(target.getType());
            if ((tkind == 1 || tkind == 2) && kind >= 1 && kind <= 4) {
                kind = tkind;
                r23 = tkind == 1 ? LangPrimType.intType : LangPrimType.longType;
            } else if ((tkind == 8 || tkind == 7) && kind > 2 && kind <= 10) {
                kind = tkind;
                r23 = tkind == 7 ? LangPrimType.floatType : LangPrimType.doubleType;
            } else if (kind == 7) {
                r23 = LangPrimType.floatType;
            } else if (kind == 8 || kind == 9) {
                kind = 8;
                r23 = LangPrimType.doubleType;
            } else {
                r23 = kindType;
            }
            if (this.op >= 4 && this.op <= 8) {
                DivideOp dproc = (DivideOp) this.proc;
                if (dproc.op != 4 || (kind > 4 && kind < 6 && kind > 9)) {
                    if ((dproc.op == 5 && kind <= 10 && kind != 7) || (dproc.op == 4 && kind == 10)) {
                        kind = 8;
                    } else if (((dproc.op != 7 && (dproc.op != 6 || kind > 4)) || !(dproc.getRoundingMode() == 3 || kind == 4 || kind == 7 || kind == 8)) && !(dproc.op == 8 && (dproc.getRoundingMode() == 3 || kind == 4))) {
                        ApplyExp.compile(exp, comp, target);
                        return;
                    }
                }
            }
            if (this.op == 4 && kind <= 10 && kind != 8 && kind != 7) {
                if (kind == 6 || kind > 4) {
                    LangObjType ctype = kind == 6 ? Arithmetic.typeRatNum : Arithmetic.typeRealNum;
                    r233 = ctype;
                    meth = ctype.getDeclaredMethod("divide", 2);
                } else {
                    ? r234 = Arithmetic.typeIntNum;
                    meth = Arithmetic.typeRatNum.getDeclaredMethod("make", 2);
                    r233 = r234;
                }
                Target wtarget = StackTarget.getInstance(r233);
                args[0].compile(comp, wtarget);
                args[1].compile(comp, wtarget);
                comp.getCode().emitInvokeStatic(meth);
                r232 = r233;
            } else if (kind == 4 && (this.op == 1 || this.op == 3 || this.op == 2 || this.op == 13 || this.op == 14 || this.op == 15 || this.op == 7 || this.op == 8 || (this.op >= 9 && this.op <= 11))) {
                compileIntNum(args[0], args[1], kind1, kind2, comp);
                r232 = r23;
            } else if (kind == 1 || kind == 2 || ((kind == 7 || kind == 8) && (this.op <= 8 || this.op >= 13))) {
                Target wtarget2 = StackTarget.getInstance(r23);
                CodeAttr code = comp.getCode();
                for (int i = 0; i < len; i++) {
                    if (i == 1 && this.op >= 9 && this.op <= 12) {
                        wtarget2 = StackTarget.getInstance(Type.intType);
                    }
                    args[i].compile(comp, wtarget2);
                    if (i != 0) {
                        switch (kind) {
                            case 1:
                            case 2:
                            case 7:
                            case 8:
                                if (this.op != 9) {
                                    code.emitBinop(primitiveOpcode(), (Type) (PrimType) r23.getImplementationType());
                                    break;
                                } else {
                                    code.emitInvokeStatic(ClassType.make("gnu.math.IntNum").getDeclaredMethod("shift", (Type[]) new Type[]{r23, Type.intType}));
                                    break;
                                }
                        }
                    }
                }
                r232 = r23;
            } else {
                ApplyExp.compile(exp, comp, target);
                return;
            }
            target.compileFromStack(comp, r232);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:65:0x011f, code lost:
        if (r11 != null) goto L_0x012a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0121, code lost:
        r11 = new gnu.bytecode.Type[]{r22, r23};
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x012a, code lost:
        r12.emitInvokeStatic(r15.getMethod(r19, r11));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0140, code lost:
        if (r19 != null) goto L_0x0144;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0142, code lost:
        r19 = "ior";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0144, code lost:
        if (r19 != null) goto L_0x0148;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x0146, code lost:
        r19 = "xor";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0148, code lost:
        r15 = gnu.bytecode.ClassType.make("gnu.math.BitOps");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:?, code lost:
        return true;
     */
    public boolean compileIntNum(Expression arg1, Expression arg2, int kind1, int kind2, Compilation comp) {
        Type type2;
        Type type1;
        boolean swap;
        String mname;
        boolean negateOk;
        long lval;
        if (this.op == 2 && (arg2 instanceof QuoteExp)) {
            Object val = arg2.valueIfConstant();
            if (kind2 <= 2) {
                lval = ((Number) val).longValue();
                if (lval <= -2147483648L || lval > 2147483647L) {
                    negateOk = false;
                } else {
                    negateOk = true;
                }
            } else if (val instanceof IntNum) {
                IntNum ival = (IntNum) val;
                lval = ival.longValue();
                negateOk = ival.inRange(-2147483647L, 2147483647L);
            } else {
                negateOk = false;
                lval = 0;
            }
            if (negateOk) {
                return $Pl.compileIntNum(arg1, QuoteExp.getInstance(Integer.valueOf((int) (-lval))), kind1, 1, comp);
            }
        }
        if (this.op == 1 || this.op == 3) {
            if (InlineCalls.checkIntValue(arg1) != null) {
                kind1 = 1;
            }
            if (InlineCalls.checkIntValue(arg2) != null) {
                kind2 = 1;
            }
            swap = kind1 == 1 && kind2 != 1;
            if (swap && (!arg1.side_effects() || !arg2.side_effects())) {
                return compileIntNum(arg2, arg1, kind2, kind1, comp);
            }
            type1 = kind1 == 1 ? Type.intType : Arithmetic.typeIntNum;
            if (kind2 == 1) {
                type2 = Type.intType;
            } else {
                type2 = Arithmetic.typeIntNum;
            }
        } else if (this.op < 9 || this.op > 12) {
            type2 = Arithmetic.typeIntNum;
            type1 = type2;
            swap = false;
        } else {
            type1 = Arithmetic.typeIntNum;
            type2 = Type.intType;
            swap = false;
        }
        arg1.compile(comp, type1);
        arg2.compile(comp, type2);
        CodeAttr code = comp.getCode();
        if (swap) {
            code.emitSwap();
            type1 = Arithmetic.typeIntNum;
            type2 = LangPrimType.intType;
        }
        String mname2 = null;
        Type[] argTypes = null;
        ObjectType mclass = Arithmetic.typeIntNum;
        switch (this.op) {
            case 1:
                mname = "add";
                break;
            case 2:
                mname = "sub";
                break;
            case 3:
                mname = "times";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                mname = this.op == 8 ? "remainder" : "quotient";
                DivideOp dproc = (DivideOp) this.proc;
                if (this.op != 8 || dproc.rounding_mode != 1) {
                    if (dproc.rounding_mode != 3) {
                        code.emitPushInt(dproc.rounding_mode);
                        argTypes = new Type[]{type1, type2, Type.intType};
                        break;
                    }
                } else {
                    mname = "modulo";
                    break;
                }
                break;
            case 9:
                mname = "shift";
                break;
            case 10:
            case 11:
                mname = this.op == 10 ? "shiftLeft" : "shiftRight";
                mclass = ClassType.make("gnu.kawa.functions.BitwiseOp");
                break;
            case 13:
                mname2 = "and";
                break;
            case 14:
                break;
            case 15:
                break;
            default:
                throw new Error();
        }
    }

    public static int getReturnKind(int kind1, int kind2, int op2) {
        if (op2 >= 9 && op2 <= 12) {
            return kind1;
        }
        if (kind1 <= 0 || (kind1 > kind2 && kind2 > 0)) {
            kind2 = kind1;
        }
        return kind2;
    }

    public int getReturnKind(Expression[] args) {
        int len = args.length;
        if (len == 0) {
            return 4;
        }
        ClassType classType = Type.pointer_type;
        int kindr = 0;
        for (int i = 0; i < len; i++) {
            int kind = Arithmetic.classifyType(args[i].getType());
            if (i == 0 || kind == 0 || kind > kindr) {
                kindr = kind;
            }
        }
        return kindr;
    }

    public Type getReturnType(Expression[] args) {
        return Arithmetic.kindType(adjustReturnKind(getReturnKind(args), this.op));
    }

    static int adjustReturnKind(int rkind, int op2) {
        if (op2 < 4 || op2 > 7 || rkind <= 0) {
            return rkind;
        }
        switch (op2) {
            case 4:
                if (rkind <= 4) {
                    return 6;
                }
                return rkind;
            case 5:
                if (rkind > 10 || rkind == 7) {
                    return rkind;
                }
                return 8;
            case 7:
                if (rkind <= 10) {
                    return 4;
                }
                return rkind;
            default:
                return rkind;
        }
    }

    public static Expression validateApplyAdd(AddOp proc2, ApplyExp exp, InlineCalls visitor) {
        Expression[] args = exp.getArgs();
        if (args.length != 1 || proc2.plusOrMinus >= 0) {
            return exp;
        }
        Type type0 = args[0].getType();
        if (!(type0 instanceof PrimType)) {
            return exp;
        }
        char sig0 = type0.getSignature().charAt(0);
        Type type = null;
        int opcode = 0;
        if (!(sig0 == 'V' || sig0 == 'Z' || sig0 == 'C')) {
            if (sig0 == 'D') {
                opcode = 119;
                type = LangPrimType.doubleType;
            } else if (sig0 == 'F') {
                opcode = 118;
                type = LangPrimType.floatType;
            } else if (sig0 == 'J') {
                opcode = 117;
                type = LangPrimType.longType;
            } else {
                opcode = 116;
                type = LangPrimType.intType;
            }
        }
        if (type != null) {
            return new ApplyExp((Procedure) PrimProcedure.makeBuiltinUnary(opcode, type), args);
        }
        return exp;
    }

    public static Expression validateApplyDiv(DivideOp proc2, ApplyExp exp, InlineCalls visitor) {
        Expression[] args = exp.getArgs();
        if (args.length != 1) {
            return exp;
        }
        Expression[] args2 = {QuoteExp.getInstance(IntNum.one()), args[0]};
        Expression[] expressionArr = args2;
        return new ApplyExp(exp.getFunction(), args2);
    }

    public static Expression validateApplyNot(ApplyExp exp, int kind, InlineCalls visitor) {
        String cname;
        if (exp.getArgCount() != 1) {
            return exp;
        }
        Expression arg = exp.getArg(0);
        if (kind == 1 || kind == 2) {
            return visitor.visitApplyOnly(new ApplyExp((Procedure) BitwiseOp.xor, arg, QuoteExp.getInstance(IntNum.minusOne())), null);
        }
        if (kind == 4) {
            cname = "gnu.math.BitOps";
        } else if (kind == 3) {
            cname = "java.meth.BigInteger";
        } else {
            cname = null;
        }
        if (cname != null) {
            return new ApplyExp(ClassType.make(cname).getDeclaredMethod("not", 1), exp.getArgs());
        }
        return exp;
    }

    public static Expression validateApplyNumberCompare(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc2) {
        exp.visitArgs(visitor);
        Expression folded = exp.inlineIfConstant(proc2, visitor);
        return folded != exp ? folded : exp;
    }

    public int primitiveOpcode() {
        switch (this.op) {
            case 1:
                return 96;
            case 2:
                return 100;
            case 3:
                return 104;
            case 4:
            case 5:
            case 6:
            case 7:
                return 108;
            case 8:
                return 112;
            case 10:
                return 120;
            case 11:
                return 122;
            case 12:
                return 124;
            case 13:
                return 126;
            case 14:
                return 128;
            case 15:
                return 130;
            default:
                return -1;
        }
    }

    /* JADX WARNING: type inference failed for: r11v0, types: [gnu.expr.Expression[]] */
    /* JADX WARNING: type inference failed for: r5v0 */
    /* JADX WARNING: type inference failed for: r5v1, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r0v0, types: [gnu.expr.Expression[]] */
    /* JADX WARNING: type inference failed for: r7v0 */
    /* JADX WARNING: type inference failed for: r4v0, types: [gnu.expr.ApplyExp] */
    /* JADX WARNING: type inference failed for: r2v0, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r5v2 */
    /* JADX WARNING: type inference failed for: r5v3 */
    /* JADX WARNING: type inference failed for: r5v4 */
    /* JADX WARNING: type inference failed for: r5v5 */
    /* JADX WARNING: type inference failed for: r5v6 */
    /* JADX WARNING: Incorrect type for immutable var: ssa=gnu.expr.Expression[], code=null, for r11v0, types: [gnu.expr.Expression[]] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r5v3
  assigns: []
  uses: []
  mth insns count: 17
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
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 8 */
    public static Expression pairwise(Procedure proc2, Expression rproc, Expression[] r11, InlineCalls visitor) {
        int len = r11.length;
        int i = 1;
        ? r5 = r11[0];
        while (i < len) {
            ? applyExp = new ApplyExp(rproc, (Expression[]) new Expression[]{r5, r11[i]});
            ? maybeInline = visitor.maybeInline(applyExp, null, proc2);
            i++;
            r5 = maybeInline != 0 ? maybeInline : applyExp;
        }
        return r5;
    }

    public static Expression validateApplyNumberPredicate(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc2) {
        int i = ((NumberPredicate) proc2).op;
        Expression[] args = exp.getArgs();
        args[0] = visitor.visit(args[0], (Type) LangObjType.integerType);
        exp.setType(Type.booleanType);
        return exp;
    }
}
