package gnu.kawa.functions;

import gnu.mapping.Procedure;
import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.math.RatNum;
import gnu.math.RealNum;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class DivideOp extends ArithOp {
    public static final DivideOp $Sl = new DivideOp("/", 4);
    public static final DivideOp div = new DivideOp("div", 6);
    public static final DivideOp div0 = new DivideOp("div0", 6);
    public static final DivideOp idiv = new DivideOp("idiv", 7);
    public static final DivideOp mod = new DivideOp("mod", 8);
    public static final DivideOp mod0 = new DivideOp("mod0", 8);
    public static final DivideOp modulo = new DivideOp("modulo", 8);
    public static final DivideOp quotient = new DivideOp("quotient", 6);
    public static final DivideOp remainder = new DivideOp("remainder", 8);
    int rounding_mode;

    public int getRoundingMode() {
        return this.rounding_mode;
    }

    static {
        idiv.rounding_mode = 3;
        quotient.rounding_mode = 3;
        remainder.rounding_mode = 3;
        modulo.rounding_mode = 1;
        div.rounding_mode = 5;
        mod.rounding_mode = 5;
        div0.rounding_mode = 4;
        mod0.rounding_mode = 4;
    }

    public DivideOp(String name, int op) {
        super(name, op);
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileArith:validateApplyArithOp");
        Procedure.compilerKey.set(this, "*gnu.kawa.functions.CompileArith:forDiv");
    }

    /* JADX WARNING: type inference failed for: r27v1, types: [java.lang.Object, java.lang.Number] */
    /* JADX WARNING: type inference failed for: r27v2, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r24v0, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r27v3, types: [gnu.math.DFloNum] */
    /* JADX WARNING: type inference failed for: r27v4, types: [gnu.math.IntNum] */
    /* JADX WARNING: type inference failed for: r27v5, types: [java.lang.Double] */
    /* JADX WARNING: type inference failed for: r27v6, types: [gnu.math.DFloNum] */
    /* JADX WARNING: type inference failed for: r27v7, types: [java.math.BigDecimal] */
    /* JADX WARNING: type inference failed for: r27v8, types: [java.math.BigInteger] */
    /* JADX WARNING: type inference failed for: r27v9, types: [java.math.BigDecimal] */
    /* JADX WARNING: type inference failed for: r27v10, types: [java.math.BigDecimal] */
    /* JADX WARNING: type inference failed for: r27v11, types: [gnu.math.IntNum] */
    /* JADX WARNING: type inference failed for: r27v12, types: [gnu.math.IntNum] */
    /* JADX WARNING: type inference failed for: r27v13, types: [gnu.math.RatNum] */
    /* JADX WARNING: type inference failed for: r0v25 */
    /* JADX WARNING: type inference failed for: r27v14, types: [java.lang.Long] */
    /* JADX WARNING: type inference failed for: r27v15, types: [java.lang.Object, java.lang.Number] */
    /* JADX WARNING: type inference failed for: r27v16 */
    /* JADX WARNING: type inference failed for: r27v17, types: [java.math.BigInteger] */
    /* JADX WARNING: type inference failed for: r27v18, types: [java.lang.Double] */
    /* JADX WARNING: type inference failed for: r27v19, types: [java.lang.Float] */
    /* JADX WARNING: type inference failed for: r27v20, types: [java.lang.Long] */
    /* JADX WARNING: type inference failed for: r27v21, types: [java.lang.Integer] */
    /* JADX WARNING: type inference failed for: r27v22, types: [java.lang.Integer] */
    /* JADX WARNING: type inference failed for: r26v0, types: [gnu.math.Numeric] */
    /* JADX WARNING: type inference failed for: r26v1, types: [gnu.math.Numeric] */
    /* JADX WARNING: type inference failed for: r27v23, types: [gnu.math.Numeric] */
    /* JADX WARNING: type inference failed for: r27v24, types: [gnu.math.RealNum] */
    /* JADX WARNING: type inference failed for: r27v25, types: [gnu.math.IntNum] */
    /* JADX WARNING: type inference failed for: r27v26 */
    /* JADX WARNING: type inference failed for: r26v5, types: [gnu.math.Numeric] */
    /* JADX WARNING: type inference failed for: r2v3, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r27v27 */
    /* JADX WARNING: type inference failed for: r27v28 */
    /* JADX WARNING: type inference failed for: r27v29 */
    /* JADX WARNING: type inference failed for: r27v30 */
    /* JADX WARNING: type inference failed for: r27v31 */
    /* JADX WARNING: type inference failed for: r27v32 */
    /* JADX WARNING: type inference failed for: r27v33 */
    /* JADX WARNING: type inference failed for: r27v34 */
    /* JADX WARNING: type inference failed for: r27v35 */
    /* JADX WARNING: type inference failed for: r27v36 */
    /* JADX WARNING: type inference failed for: r27v37 */
    /* JADX WARNING: type inference failed for: r27v38 */
    /* JADX WARNING: type inference failed for: r27v39 */
    /* JADX WARNING: type inference failed for: r27v40 */
    /* JADX WARNING: type inference failed for: r27v41 */
    /* JADX WARNING: type inference failed for: r27v42 */
    /* JADX WARNING: type inference failed for: r27v43 */
    /* JADX WARNING: type inference failed for: r27v44 */
    /* JADX WARNING: type inference failed for: r27v45 */
    /* JADX WARNING: type inference failed for: r27v46 */
    /* JADX WARNING: type inference failed for: r27v47 */
    /* JADX WARNING: type inference failed for: r27v48 */
    /* JADX WARNING: type inference failed for: r27v49 */
    /* JADX WARNING: type inference failed for: r27v50 */
    /* JADX WARNING: type inference failed for: r27v51 */
    /* JADX WARNING: type inference failed for: r26v6 */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0174, code lost:
        r23 = java.math.RoundingMode.HALF_EVEN;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x0176, code lost:
        r21 = new java.math.MathContext(0, r23);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0181, code lost:
        switch(r32.op) {
            case 4: goto L_0x0186;
            case 5: goto L_0x0184;
            case 6: goto L_0x01a1;
            case 7: goto L_0x01a9;
            case 8: goto L_0x01b9;
            default: goto L_0x0184;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0186, code lost:
        r27 = r5.divide(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x01a1, code lost:
        r27 = r5.divideToIntegralValue(r6, r21);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x01a9, code lost:
        r28 = 3;
        r7 = 3;
        r27 = r5.divideToIntegralValue(r6, r21).toBigInteger();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x01b9, code lost:
        r27 = r5.remainder(r6, r21);
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r27v16
  assigns: []
  uses: []
  mth insns count: 254
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
    /* JADX WARNING: Unknown variable types count: 29 */
    public Object applyN(Object[] args) throws Throwable {
        ? r27;
        RoundingMode mround;
        long l1;
        ? r272;
        int i1;
        int len = args.length;
        if (len == 0) {
            return IntNum.one();
        }
        ? r273 = (Number) args[0];
        if (len == 1) {
            return apply2(IntNum.one(), r273);
        }
        int code = Arithmetic.classifyValue(r273);
        int i = 1;
        ? valueOf = r273;
        while (i < len) {
            Object arg2 = args[i];
            int code2 = Arithmetic.classifyValue(arg2);
            if (code < code2) {
                code = code2;
            }
            int scode = code;
            if (code < 4) {
                switch (this.op) {
                    case 4:
                    case 5:
                        code = 4;
                        scode = 4;
                        break;
                    default:
                        if (!(this.rounding_mode == 3 && (code == 1 || code == 2))) {
                            scode = 4;
                            break;
                        }
                }
            }
            if (this.op == 5 && code <= 10) {
                scode = 10;
                if (!(code == 8 || code == 7)) {
                    code = 9;
                }
            } else if (scode == 8 || scode == 7) {
                scode = 9;
                if (this.op == 7) {
                    code = 9;
                }
            }
            switch (scode) {
                case 1:
                    int i12 = Arithmetic.asInt(r27);
                    int i2 = Arithmetic.asInt(arg2);
                    switch (this.op) {
                        case 8:
                            i1 = i12 % i2;
                            break;
                        default:
                            i1 = i12 / i2;
                            break;
                    }
                    r27 = Integer.valueOf(i1);
                    break;
                case 2:
                    long l12 = Arithmetic.asLong(r27);
                    long l2 = Arithmetic.asLong(arg2);
                    switch (this.op) {
                        case 8:
                            l1 = l12 % l2;
                            break;
                        default:
                            l1 = l12 / l2;
                            break;
                    }
                    r27 = Long.valueOf(l1);
                    break;
                case 4:
                    switch (this.op) {
                        case 4:
                            ? make = RatNum.make(Arithmetic.asIntNum((Object) r27), Arithmetic.asIntNum(arg2));
                            code = make instanceof IntNum ? 4 : 6;
                            scode = code;
                            r27 = make;
                            break;
                        case 6:
                        case 7:
                            r27 = IntNum.quotient(Arithmetic.asIntNum((Object) r27), Arithmetic.asIntNum(arg2), getRoundingMode());
                            break;
                        case 8:
                            r27 = IntNum.remainder(Arithmetic.asIntNum((Object) r27), Arithmetic.asIntNum(arg2), getRoundingMode());
                            break;
                    }
                case 5:
                    BigDecimal bd1 = Arithmetic.asBigDecimal(r27);
                    BigDecimal bd2 = Arithmetic.asBigDecimal(arg2);
                    switch (getRoundingMode()) {
                        case 1:
                            mround = RoundingMode.FLOOR;
                            break;
                        case 2:
                            mround = RoundingMode.CEILING;
                            break;
                        case 3:
                            mround = RoundingMode.DOWN;
                            break;
                        case 5:
                            if (bd2.signum() >= 0) {
                                RoundingMode mround2 = RoundingMode.FLOOR;
                                break;
                            } else {
                                RoundingMode roundingMode = RoundingMode.CEILING;
                                break;
                            }
                    }
                case 9:
                    double d1 = Arithmetic.asDouble(r27);
                    double d2 = Arithmetic.asDouble(arg2);
                    switch (this.op) {
                        case 4:
                        case 5:
                            r27 = DFloNum.make(d1 / d2);
                            break;
                        case 6:
                            r27 = Double.valueOf(RealNum.toInt(d1 / d2, getRoundingMode()));
                            break;
                        case 7:
                            scode = 4;
                            code = 4;
                            r27 = RealNum.toExactInt(d1 / d2, getRoundingMode());
                            break;
                        case 8:
                            if (d2 != 0.0d) {
                                d1 -= RealNum.toInt(d1 / d2, getRoundingMode()) * d2;
                            }
                            r27 = DFloNum.make(d1);
                            break;
                    }
                default:
                    Numeric num1 = Arithmetic.asNumeric(r27);
                    Numeric num2 = Arithmetic.asNumeric(arg2);
                    if (this.op != 8 || !num2.isZero()) {
                        ? div2 = num1.div(num2);
                        if (this.op == 8) {
                            div2 = num1.sub(((RealNum) div2).toInt(getRoundingMode()).mul(num2));
                        }
                        switch (this.op) {
                            case 5:
                                r27 = div2.toInexact();
                                break;
                            case 6:
                                r27 = ((RealNum) div2).toInt(this.rounding_mode);
                                break;
                            case 7:
                                code = 4;
                                scode = 4;
                                r27 = ((RealNum) div2).toExactInt(this.rounding_mode);
                                break;
                            default:
                                r27 = div2;
                                break;
                        }
                    } else if (!num2.isExact()) {
                        return num1.toInexact();
                    } else {
                        return num1;
                    }
            }
            if (code != scode) {
                switch (code) {
                    case 1:
                        r272 = Integer.valueOf(r27.intValue());
                        break;
                    case 2:
                        r272 = Long.valueOf(r27.longValue());
                        break;
                    case 3:
                        r272 = Arithmetic.asBigInteger(r27);
                        break;
                    case 7:
                        r272 = Float.valueOf(r27.floatValue());
                        break;
                    case 8:
                        r272 = Double.valueOf(r27.doubleValue());
                        break;
                    default:
                        r272 = r27;
                        break;
                }
            } else {
                r272 = r27;
            }
            i++;
            valueOf = r272;
        }
        return r27;
    }

    public int numArgs() {
        return this.op == 4 ? -4095 : 8194;
    }
}
