package gnu.kawa.slib;

import android.support.v4.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.Special;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.ApplyToArgs;
import gnu.kawa.functions.DivideOp;
import gnu.kawa.functions.IsEqual;
import gnu.kawa.functions.IsEqv;
import gnu.kawa.functions.MultiplyOp;
import gnu.kawa.lispexpr.LangObjType;
import gnu.lists.CharSeq;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.Complex;
import gnu.math.IntNum;
import gnu.text.Char;
import kawa.lib.characters;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.numbers;
import kawa.lib.ports;
import kawa.lib.rnrs.unicode;
import kawa.lib.strings;
import kawa.lib.vectors;
import kawa.standard.Scheme;
import kawa.standard.append;

/* compiled from: printf.scm */
public class printf extends ModuleBody {
    public static final printf $instance = new printf();
    static final IntNum Lit0 = IntNum.make(-15);
    static final IntNum Lit1 = IntNum.make(0);
    static final PairWithPosition Lit10 = PairWithPosition.make(Lit13, PairWithPosition.make(Lit37, PairWithPosition.make(Lit25, PairWithPosition.make(Lit12, PairWithPosition.make(Lit30, PairWithPosition.make(Lit54, PairWithPosition.make(Lit38, PairWithPosition.make(Lit26, PairWithPosition.make(Lit41, PairWithPosition.make(Lit31, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 266284), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 266280), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 266276), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 266272), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 266268), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 266264), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 266260), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 266256), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 266252), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 266247);
    static final Char Lit11 = Char.make(46);
    static final Char Lit12;
    static final Char Lit13;
    static final IntNum Lit14 = IntNum.make(2);
    static final IntNum Lit15 = IntNum.make(5);
    static final IntNum Lit16 = IntNum.make(9);
    static final IntNum Lit17 = IntNum.make(-1);
    static final Char Lit18 = Char.make(92);
    static final Char Lit19 = Char.make(110);
    static final PairWithPosition Lit2 = PairWithPosition.make(Lit6, PairWithPosition.make(Lit5, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 446503), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 446498);
    static final Char Lit20 = Char.make(78);
    static final Char Lit21 = Char.make(10);
    static final Char Lit22 = Char.make(116);
    static final Char Lit23 = Char.make(84);
    static final Char Lit24 = Char.make(9);
    static final Char Lit25;
    static final Char Lit26 = Char.make(70);
    static final Char Lit27 = Char.make(12);
    static final Char Lit28 = Char.make(37);
    static final Char Lit29 = Char.make(32);
    static final Char Lit3;
    static final Char Lit30 = Char.make(108);
    static final Char Lit31 = Char.make(76);
    static final Char Lit32 = Char.make(104);
    static final PairWithPosition Lit33;
    static final SimpleSymbol Lit34 = ((SimpleSymbol) new SimpleSymbol("printf").readResolve());
    static final Char Lit35 = Char.make(99);
    static final Char Lit36 = Char.make(67);
    static final Char Lit37 = Char.make(115);
    static final Char Lit38 = Char.make(83);
    static final Char Lit39 = Char.make(97);
    static final Char Lit4 = Char.make(64);
    static final Char Lit40 = Char.make(65);
    static final Char Lit41 = Char.make(68);
    static final Char Lit42 = Char.make(73);
    static final Char Lit43 = Char.make(117);
    static final Char Lit44 = Char.make(85);
    static final IntNum Lit45 = IntNum.make(10);
    static final Char Lit46 = Char.make(111);
    static final Char Lit47 = Char.make(79);
    static final IntNum Lit48 = IntNum.make(8);
    static final Char Lit49 = Char.make(120);
    static final Char Lit5 = Char.make(45);
    static final IntNum Lit50 = IntNum.make(16);
    static final Char Lit51 = Char.make(88);
    static final Char Lit52 = Char.make(98);
    static final Char Lit53 = Char.make(66);
    static final Char Lit54 = Char.make(69);
    static final Char Lit55 = Char.make(103);
    static final Char Lit56 = Char.make(71);
    static final Char Lit57 = Char.make(107);
    static final Char Lit58 = Char.make(75);
    static final IntNum Lit59 = IntNum.make(6);
    static final Char Lit6 = Char.make(43);
    static final IntNum Lit60 = IntNum.make(-10);
    static final IntNum Lit61 = IntNum.make(3);
    static final FVector Lit62 = FVector.make("y", "z", "a", "f", "p", "n", "u", "m", "", "k", "M", "G", "T", "P", "E", "Z", "Y");
    static final PairWithPosition Lit63 = PairWithPosition.make("i", LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1634315);
    static final SimpleSymbol Lit64 = ((SimpleSymbol) new SimpleSymbol("format-real").readResolve());
    static final Char Lit65 = Char.make(63);
    static final Char Lit66 = Char.make(42);
    static final SimpleSymbol Lit67 = ((SimpleSymbol) new SimpleSymbol("pad").readResolve());
    static final SimpleSymbol Lit68 = ((SimpleSymbol) new SimpleSymbol("sprintf").readResolve());
    static final SimpleSymbol Lit69 = ((SimpleSymbol) new SimpleSymbol("stdio:parse-float").readResolve());
    static final IntNum Lit7 = IntNum.make(1);
    static final SimpleSymbol Lit70 = ((SimpleSymbol) new SimpleSymbol("stdio:round-string").readResolve());
    static final SimpleSymbol Lit71 = ((SimpleSymbol) new SimpleSymbol("stdio:iprintf").readResolve());
    static final SimpleSymbol Lit72 = ((SimpleSymbol) new SimpleSymbol("fprintf").readResolve());
    static final Char Lit8 = Char.make(35);
    static final Char Lit9 = Char.make(48);
    public static final ModuleMethod fprintf;
    public static final ModuleMethod printf;
    public static final ModuleMethod sprintf;
    public static final boolean stdio$Clhex$Mnupper$Mncase$Qu = false;
    public static final ModuleMethod stdio$Cliprintf;
    public static final ModuleMethod stdio$Clparse$Mnfloat;
    public static final ModuleMethod stdio$Clround$Mnstring;

    /* compiled from: printf.scm */
    public class frame extends ModuleBody {
        final ModuleMethod lambda$Fn1;
        int n;
        Object proc;
        Object str;

        public frame() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 12, null, 16388);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:106");
            this.lambda$Fn1 = moduleMethod;
        }

        public static Boolean lambda1parseError() {
            return Boolean.FALSE;
        }

        public Object lambda2sign(Object i, Object cont) {
            if (Scheme.numLss.apply2(i, Integer.valueOf(this.n)) == Boolean.FALSE) {
                return Values.empty;
            }
            Object obj = this.str;
            try {
                try {
                    char c = strings.stringRef((CharSequence) obj, ((Number) i).intValue());
                    Object x = Scheme.isEqv.apply2(Char.make(c), printf.Lit5);
                    if (x == Boolean.FALSE ? Scheme.isEqv.apply2(Char.make(c), printf.Lit6) != Boolean.FALSE : x != Boolean.FALSE) {
                        return Scheme.applyToArgs.apply3(cont, AddOp.$Pl.apply2(i, printf.Lit7), Char.make(c));
                    }
                    return Scheme.applyToArgs.apply3(cont, i, printf.Lit6);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-ref", 2, i);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-ref", 1, obj);
            }
        }

        public Object lambda3digits(Object i, Object cont) {
            Object substring;
            int i2 = 2;
            Object j = i;
            while (true) {
                Object apply2 = Scheme.numGEq.apply2(j, Integer.valueOf(this.n));
                try {
                    boolean x = ((Boolean) apply2).booleanValue();
                    if (x) {
                        if (x) {
                            break;
                        }
                    } else {
                        Object obj = this.str;
                        try {
                            try {
                                boolean x2 = unicode.isCharNumeric(Char.make(strings.stringRef((CharSequence) obj, ((Number) j).intValue())));
                                if (!x2) {
                                    Char charR = printf.Lit8;
                                    Object obj2 = this.str;
                                    try {
                                        try {
                                            if (!characters.isChar$Eq(charR, Char.make(strings.stringRef((CharSequence) obj2, ((Number) j).intValue())))) {
                                                break;
                                            }
                                        } catch (ClassCastException e) {
                                            throw new WrongType(e, "string-ref", i2, j);
                                        }
                                    } catch (ClassCastException e2) {
                                        throw new WrongType(e2, "string-ref", 1, obj2);
                                    }
                                } else if (!x2) {
                                    break;
                                }
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "string-ref", i2, j);
                            }
                        } catch (ClassCastException e4) {
                            throw new WrongType(e4, "string-ref", 1, obj);
                        }
                    }
                    j = AddOp.$Pl.apply2(j, printf.Lit7);
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "x", -2, apply2);
                }
            }
            ApplyToArgs applyToArgs = Scheme.applyToArgs;
            if (Scheme.numEqu.apply2(i, j) != Boolean.FALSE) {
                substring = "0";
            } else {
                Object obj3 = this.str;
                try {
                    try {
                        try {
                            substring = strings.substring((CharSequence) obj3, ((Number) i).intValue(), ((Number) j).intValue());
                        } catch (ClassCastException e6) {
                            throw new WrongType(e6, "substring", 3, j);
                        }
                    } catch (ClassCastException e7) {
                        throw new WrongType(e7, "substring", i2, i);
                    }
                } catch (ClassCastException e8) {
                    throw new WrongType(e8, "substring", 1, obj3);
                }
            }
            return applyToArgs.apply3(cont, j, substring);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:31:0x00a1, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(gnu.text.Char.make(r1), gnu.kawa.slib.printf.Lit11) == java.lang.Boolean.FALSE) goto L_0x00bb;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:60:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply2(r11, r10);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:61:?, code lost:
            return lambda1parseError();
         */
        public Object lambda4real(Object i, Object cont) {
            frame2 frame2 = new frame2();
            frame2.staticLink = this;
            frame2.cont = cont;
            Object cont2 = frame2.lambda$Fn5;
            while (true) {
                Object apply2 = Scheme.numLss.apply2(i, Integer.valueOf(this.n - 1));
                try {
                    boolean x = ((Boolean) apply2).booleanValue();
                    if (!x) {
                        if (!x) {
                            break;
                        }
                    } else {
                        Char charR = printf.Lit8;
                        Object obj = this.str;
                        try {
                            try {
                                if (!characters.isChar$Eq(charR, Char.make(strings.stringRef((CharSequence) obj, ((Number) i).intValue())))) {
                                    break;
                                }
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "string-ref", 2, i);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "string-ref", 1, obj);
                        }
                    }
                    Object obj2 = this.str;
                    try {
                        CharSequence charSequence = (CharSequence) obj2;
                        Object apply22 = AddOp.$Pl.apply2(i, printf.Lit7);
                        try {
                            char tmp = strings.stringRef(charSequence, ((Number) apply22).intValue());
                            Object x2 = Scheme.isEqv.apply2(Char.make(tmp), printf.Lit12);
                            if (x2 != Boolean.FALSE) {
                                if (x2 == Boolean.FALSE) {
                                    break;
                                }
                            } else {
                                Object x3 = Scheme.isEqv.apply2(Char.make(tmp), printf.Lit3);
                                if (x3 == Boolean.FALSE) {
                                    if (Scheme.isEqv.apply2(Char.make(tmp), printf.Lit13) == Boolean.FALSE) {
                                        break;
                                    }
                                } else if (x3 == Boolean.FALSE) {
                                    break;
                                }
                            }
                            i = AddOp.$Pl.apply2(i, printf.Lit14);
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "string-ref", 2, apply22);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "string-ref", 1, obj2);
                    }
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "x", -2, apply2);
                }
            }
            return Scheme.applyToArgs.apply2(cont2, i);
        }

        public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
            return moduleMethod.selector == 12 ? lambda5(obj, obj2, obj3, obj4) : super.apply4(moduleMethod, obj, obj2, obj3, obj4);
        }

        /* access modifiers changed from: 0000 */
        public Object lambda5(Object i, Object sgn, Object digs, Object ex) {
            frame0 frame0 = new frame0();
            frame0.staticLink = this;
            frame0.sgn = sgn;
            frame0.digs = digs;
            frame0.ex = ex;
            if (Scheme.numEqu.apply2(i, Integer.valueOf(this.n)) != Boolean.FALSE) {
                return Scheme.applyToArgs.apply4(this.proc, frame0.sgn, frame0.digs, frame0.ex);
            }
            Object obj = this.str;
            try {
                try {
                    if (lists.memv(Char.make(strings.stringRef((CharSequence) obj, ((Number) i).intValue())), printf.Lit2) != Boolean.FALSE) {
                        return lambda4real(i, frame0.lambda$Fn2);
                    }
                    IsEqv isEqv = Scheme.isEqv;
                    Object obj2 = this.str;
                    try {
                        try {
                            if (isEqv.apply2(Char.make(strings.stringRef((CharSequence) obj2, ((Number) i).intValue())), printf.Lit4) == Boolean.FALSE) {
                                return Boolean.FALSE;
                            }
                            Object obj3 = this.str;
                            try {
                                frame0.num = numbers.string$To$Number((CharSequence) obj3);
                                if (frame0.num == Boolean.FALSE) {
                                    return lambda1parseError();
                                }
                                Object obj4 = frame0.num;
                                try {
                                    return printf.stdio$ClParseFloat(numbers.number$To$String(numbers.realPart((Complex) obj4)), frame0.lambda$Fn3);
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "real-part", 1, obj4);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string->number", 1, obj3);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "string-ref", 2, i);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "string-ref", 1, obj2);
                    }
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "string-ref", 2, i);
                }
            } catch (ClassCastException e6) {
                throw new WrongType(e6, "string-ref", 1, obj);
            }
        }

        public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
            if (moduleMethod.selector != 12) {
                return super.match4(moduleMethod, obj, obj2, obj3, obj4, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.value4 = obj4;
            callContext.proc = moduleMethod;
            callContext.pc = 4;
            return 0;
        }
    }

    /* compiled from: printf.scm */
    public class frame0 extends ModuleBody {
        Object digs;
        Object ex;
        final ModuleMethod lambda$Fn2;
        final ModuleMethod lambda$Fn3;
        Object num;
        Object sgn;
        frame staticLink;

        public frame0() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 2, null, 16388);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:111");
            this.lambda$Fn2 = moduleMethod;
            ModuleMethod moduleMethod2 = new ModuleMethod(this, 3, null, 12291);
            moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:123");
            this.lambda$Fn3 = moduleMethod2;
        }

        public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
            return moduleMethod.selector == 2 ? lambda6(obj, obj2, obj3, obj4) : super.apply4(moduleMethod, obj, obj2, obj3, obj4);
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0038, code lost:
            if (kawa.lib.rnrs.unicode.isCharCi$Eq(r4, gnu.text.Char.make(kawa.lib.strings.stringRef((java.lang.CharSequence) r2, ((java.lang.Number) r9).intValue()))) != false) goto L_0x003a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0061, code lost:
            if (r1 == false) goto L_0x0063;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
            return gnu.kawa.slib.printf.frame.lambda1parseError();
         */
        public Object lambda6(Object j, Object im$Mnsgn, Object im$Mndigs, Object im$Mnex) {
            Object apply2 = Scheme.numEqu.apply2(j, Integer.valueOf(this.staticLink.n - 1));
            try {
                boolean x = ((Boolean) apply2).booleanValue();
                if (x) {
                    Char charR = printf.Lit3;
                    Object obj = this.staticLink.str;
                    try {
                        try {
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-ref", 2, j);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-ref", 1, obj);
                    }
                }
                return Scheme.applyToArgs.applyN(new Object[]{this.staticLink.proc, this.sgn, this.digs, this.ex, im$Mnsgn, im$Mndigs, im$Mnex});
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "x", -2, apply2);
            }
        }

        public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
            if (moduleMethod.selector != 2) {
                return super.match4(moduleMethod, obj, obj2, obj3, obj4, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.value4 = obj4;
            callContext.proc = moduleMethod;
            callContext.pc = 4;
            return 0;
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 3 ? lambda7(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        /* access modifiers changed from: 0000 */
        public Object lambda7(Object sgn2, Object digs2, Object ex2) {
            frame1 frame1 = new frame1();
            frame1.staticLink = this;
            frame1.sgn = sgn2;
            frame1.digs = digs2;
            frame1.ex = ex2;
            Object obj = this.num;
            try {
                return printf.stdio$ClParseFloat(numbers.number$To$String(numbers.imagPart((Complex) obj)), frame1.lambda$Fn4);
            } catch (ClassCastException e) {
                throw new WrongType(e, "imag-part", 1, obj);
            }
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 3) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }
    }

    /* compiled from: printf.scm */
    public class frame1 extends ModuleBody {
        Object digs;
        Object ex;
        final ModuleMethod lambda$Fn4;
        Object sgn;
        frame0 staticLink;

        public frame1() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 1, null, 12291);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:126");
            this.lambda$Fn4 = moduleMethod;
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 1 ? lambda8(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        /* access modifiers changed from: 0000 */
        public Object lambda8(Object im$Mnsgn, Object im$Mndigs, Object im$Mnex) {
            return Scheme.applyToArgs.applyN(new Object[]{this.staticLink.staticLink.proc, this.sgn, this.digs, this.ex, im$Mnsgn, im$Mndigs, im$Mnex});
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 1) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }
    }

    /* compiled from: printf.scm */
    public class frame10 extends ModuleBody {
        Object alternate$Mnform;
        Object args;
        Object blank;
        final ModuleMethod lambda$Fn13;
        final ModuleMethod lambda$Fn14;
        final ModuleMethod lambda$Fn15;
        final ModuleMethod lambda$Fn16;
        Object leading$Mn0s;
        Object left$Mnadjust;
        Object os;
        Procedure pad = new ModuleMethod(this, 15, printf.Lit67, -4095);
        Object pr;
        Object precision;
        Object signed;
        frame9 staticLink;
        Object width;

        public frame10() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 16, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:472");
            this.lambda$Fn13 = moduleMethod;
            ModuleMethod moduleMethod2 = new ModuleMethod(this, 17, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:476");
            this.lambda$Fn14 = moduleMethod2;
            ModuleMethod moduleMethod3 = new ModuleMethod(this, 18, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod3.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:484");
            this.lambda$Fn15 = moduleMethod3;
            ModuleMethod moduleMethod4 = new ModuleMethod(this, 19, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod4.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:494");
            this.lambda$Fn16 = moduleMethod4;
        }

        public Object lambda22readFormatNumber() {
            Object[] objArr;
            if (Scheme.isEqv.apply2(printf.Lit66, this.staticLink.fc) != Boolean.FALSE) {
                this.staticLink.lambda18mustAdvance();
                Object ans = lists.car.apply1(this.args);
                this.args = lists.cdr.apply1(this.args);
                return ans;
            }
            Object c = this.staticLink.fc;
            Object obj = printf.Lit1;
            while (true) {
                Object obj2 = this.staticLink.fc;
                try {
                    if (!unicode.isCharNumeric((Char) obj2)) {
                        return obj;
                    }
                    this.staticLink.lambda18mustAdvance();
                    Object c2 = this.staticLink.fc;
                    AddOp addOp = AddOp.$Pl;
                    Object apply2 = MultiplyOp.$St.apply2(obj, printf.Lit45);
                    if (c instanceof Object[]) {
                        objArr = (Object[]) c;
                    } else {
                        objArr = new Object[]{c};
                    }
                    obj = addOp.apply2(apply2, numbers.string$To$Number(strings.$make$string$(objArr)));
                    c = c2;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "char-numeric?", 1, obj2);
                }
            }
        }

        public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
            if (moduleMethod.selector != 15) {
                return super.applyN(moduleMethod, objArr);
            }
            Object obj = objArr[0];
            int length = objArr.length - 1;
            Object[] objArr2 = new Object[length];
            while (true) {
                length--;
                if (length < 0) {
                    return lambda23pad$V(obj, objArr2);
                }
                objArr2[length] = objArr[length + 1];
            }
        }

        public Object lambda23pad$V(Object pre, Object[] argsArray) {
            Object makeList = LList.makeList(argsArray, 0);
            try {
                Object valueOf = Integer.valueOf(strings.stringLength((CharSequence) pre));
                Object obj = makeList;
                while (Scheme.numGEq.apply2(valueOf, this.width) == Boolean.FALSE) {
                    if (!lists.isNull(obj)) {
                        AddOp addOp = AddOp.$Pl;
                        Object apply1 = lists.car.apply1(obj);
                        try {
                            valueOf = addOp.apply2(valueOf, Integer.valueOf(strings.stringLength((CharSequence) apply1)));
                            obj = lists.cdr.apply1(obj);
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-length", 1, apply1);
                        }
                    } else if (this.left$Mnadjust != Boolean.FALSE) {
                        Object[] objArr = new Object[2];
                        objArr[0] = makeList;
                        Object apply2 = AddOp.$Mn.apply2(this.width, valueOf);
                        try {
                            objArr[1] = LList.list1(strings.makeString(((Number) apply2).intValue(), printf.Lit29));
                            return lists.cons(pre, append.append$V(objArr));
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "make-string", 1, apply2);
                        }
                    } else if (this.leading$Mn0s != Boolean.FALSE) {
                        Object apply22 = AddOp.$Mn.apply2(this.width, valueOf);
                        try {
                            return lists.cons(pre, lists.cons(strings.makeString(((Number) apply22).intValue(), printf.Lit9), makeList));
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "make-string", 1, apply22);
                        }
                    } else {
                        Object apply23 = AddOp.$Mn.apply2(this.width, valueOf);
                        try {
                            return lists.cons(strings.makeString(((Number) apply23).intValue(), printf.Lit29), lists.cons(pre, makeList));
                        } catch (ClassCastException e4) {
                            throw new WrongType(e4, "make-string", 1, apply23);
                        }
                    }
                }
                return lists.cons(pre, makeList);
            } catch (ClassCastException e5) {
                throw new WrongType(e5, "string-length", 1, pre);
            }
        }

        public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
            if (moduleMethod.selector != 15) {
                return super.matchN(moduleMethod, objArr, callContext);
            }
            callContext.values = objArr;
            callContext.proc = moduleMethod;
            callContext.pc = 5;
            return 0;
        }

        public Object lambda24integerConvert(Object s, Object radix, Object fixcase) {
            Object obj;
            Object s2;
            String pre;
            String str;
            Object obj2;
            Object obj3 = this.precision;
            try {
                if (!numbers.isNegative(LangObjType.coerceRealNum(obj3))) {
                    this.leading$Mn0s = Boolean.FALSE;
                    Object obj4 = this.precision;
                    try {
                        boolean x = numbers.isZero((Number) obj4);
                        if (!x ? x : Scheme.isEqv.apply2(printf.Lit1, s) != Boolean.FALSE) {
                            s = "";
                        }
                        obj = s;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "zero?", 1, obj4);
                    }
                } else {
                    obj = s;
                }
                if (misc.isSymbol(obj)) {
                    try {
                        s2 = misc.symbol$To$String((Symbol) obj);
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "symbol->string", 1, obj);
                    }
                } else if (numbers.isNumber(obj)) {
                    try {
                        try {
                            s2 = numbers.number$To$String((Number) obj, ((Number) radix).intValue());
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "number->string", 2, radix);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "number->string", 1, obj);
                    }
                } else {
                    try {
                        boolean x2 = ((obj != Boolean.FALSE ? 1 : 0) + 1) & true;
                        s2 = (!x2 ? lists.isNull(obj) : x2) ? "0" : strings.isString(obj) ? obj : "1";
                    } catch (ClassCastException e5) {
                        throw new WrongType(e5, "x", -2, obj);
                    }
                }
                if (fixcase != Boolean.FALSE) {
                    s2 = Scheme.applyToArgs.apply2(fixcase, s2);
                }
                if (IsEqual.apply("", s2)) {
                    pre = "";
                } else {
                    try {
                        if (Scheme.isEqv.apply2(printf.Lit5, Char.make(strings.stringRef((CharSequence) s2, 0))) != Boolean.FALSE) {
                            try {
                                try {
                                    s2 = strings.substring((CharSequence) s2, 1, strings.stringLength((CharSequence) s2));
                                    pre = "-";
                                } catch (ClassCastException e6) {
                                    throw new WrongType(e6, "string-length", 1, s2);
                                }
                            } catch (ClassCastException e7) {
                                throw new WrongType(e7, "substring", 1, s2);
                            }
                        } else if (this.signed != Boolean.FALSE) {
                            pre = "+";
                        } else if (this.blank != Boolean.FALSE) {
                            pre = " ";
                        } else if (this.alternate$Mnform != Boolean.FALSE) {
                            if (Scheme.isEqv.apply2(radix, printf.Lit48) != Boolean.FALSE) {
                                str = "0";
                            } else if (Scheme.isEqv.apply2(radix, printf.Lit50) != Boolean.FALSE) {
                                str = "0x";
                            } else {
                                str = "";
                            }
                            pre = str;
                        } else {
                            pre = "";
                        }
                    } catch (ClassCastException e8) {
                        throw new WrongType(e8, "string-ref", 1, s2);
                    }
                }
                Object[] objArr = new Object[2];
                try {
                    if (Scheme.numLss.apply2(Integer.valueOf(strings.stringLength((CharSequence) s2)), this.precision) != Boolean.FALSE) {
                        try {
                            Object apply2 = AddOp.$Mn.apply2(this.precision, Integer.valueOf(strings.stringLength((CharSequence) s2)));
                            try {
                                obj2 = strings.makeString(((Number) apply2).intValue(), printf.Lit9);
                            } catch (ClassCastException e9) {
                                throw new WrongType(e9, "make-string", 1, apply2);
                            }
                        } catch (ClassCastException e10) {
                            throw new WrongType(e10, "string-length", 1, s2);
                        }
                    } else {
                        obj2 = "";
                    }
                    objArr[0] = obj2;
                    objArr[1] = s2;
                    return lambda23pad$V(pre, objArr);
                } catch (ClassCastException e11) {
                    throw new WrongType(e11, "string-length", 1, s2);
                }
            } catch (ClassCastException e12) {
                throw new WrongType(e12, "negative?", 1, obj3);
            }
        }

        /* access modifiers changed from: 0000 */
        public Object lambda25(Object s) {
            try {
                this.pr = AddOp.$Pl.apply2(this.pr, Integer.valueOf(strings.stringLength((CharSequence) s)));
                return Scheme.applyToArgs.apply2(this.staticLink.out, s);
            } catch (ClassCastException e) {
                throw new WrongType(e, "string-length", 1, s);
            }
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 16:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 17:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 18:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 19:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                default:
                    return super.match1(moduleMethod, obj, callContext);
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean lambda26(Object s) {
            Object obj;
            Special special = Special.undefined;
            try {
                Object sl = AddOp.$Mn.apply2(this.pr, Integer.valueOf(strings.stringLength((CharSequence) s)));
                try {
                    if (numbers.isNegative(LangObjType.coerceRealNum(sl))) {
                        ApplyToArgs applyToArgs = Scheme.applyToArgs;
                        Object obj2 = this.staticLink.out;
                        try {
                            CharSequence charSequence = (CharSequence) s;
                            Object obj3 = this.pr;
                            try {
                                applyToArgs.apply2(obj2, strings.substring(charSequence, 0, ((Number) obj3).intValue()));
                                obj = printf.Lit1;
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "substring", 3, obj3);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "substring", 1, s);
                        }
                    } else {
                        Scheme.applyToArgs.apply2(this.staticLink.out, s);
                        obj = sl;
                    }
                    this.pr = obj;
                    try {
                        return numbers.isPositive(LangObjType.coerceRealNum(sl));
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "positive?", 1, sl);
                    }
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "negative?", 1, sl);
                }
            } catch (ClassCastException e5) {
                throw new WrongType(e5, "string-length", 1, s);
            }
        }

        /* access modifiers changed from: 0000 */
        public Boolean lambda27(Object s) {
            try {
                this.pr = AddOp.$Mn.apply2(this.pr, Integer.valueOf(strings.stringLength((CharSequence) s)));
                if (this.os == Boolean.FALSE) {
                    Scheme.applyToArgs.apply2(this.staticLink.out, s);
                } else {
                    Object obj = this.pr;
                    try {
                        if (numbers.isNegative(LangObjType.coerceRealNum(obj))) {
                            Scheme.applyToArgs.apply2(this.staticLink.out, this.os);
                            this.os = Boolean.FALSE;
                            Scheme.applyToArgs.apply2(this.staticLink.out, s);
                        } else {
                            this.os = strings.stringAppend(this.os, s);
                        }
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "negative?", 1, obj);
                    }
                }
                return Boolean.TRUE;
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-length", 1, s);
            }
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            switch (moduleMethod.selector) {
                case 16:
                    return lambda25(obj);
                case 17:
                    return lambda26(obj) ? Boolean.TRUE : Boolean.FALSE;
                case 18:
                    return lambda27(obj);
                case 19:
                    return lambda28(obj) ? Boolean.TRUE : Boolean.FALSE;
                default:
                    return super.apply1(moduleMethod, obj);
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean lambda28(Object s) {
            Special special = Special.undefined;
            try {
                Object sl = AddOp.$Mn.apply2(this.pr, Integer.valueOf(strings.stringLength((CharSequence) s)));
                try {
                    if (numbers.isNegative(LangObjType.coerceRealNum(sl))) {
                        Object[] objArr = new Object[2];
                        objArr[0] = this.os;
                        try {
                            CharSequence charSequence = (CharSequence) s;
                            Object obj = this.pr;
                            try {
                                objArr[1] = strings.substring(charSequence, 0, ((Number) obj).intValue());
                                this.os = strings.stringAppend(objArr);
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "substring", 3, obj);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "substring", 1, s);
                        }
                    } else {
                        this.os = strings.stringAppend(this.os, s);
                    }
                    this.pr = sl;
                    try {
                        return numbers.isPositive(LangObjType.coerceRealNum(sl));
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "positive?", 1, sl);
                    }
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "negative?", 1, sl);
                }
            } catch (ClassCastException e5) {
                throw new WrongType(e5, "string-length", 1, s);
            }
        }
    }

    /* compiled from: printf.scm */
    public class frame11 extends ModuleBody {
        Object fc;
        Procedure format$Mnreal = new ModuleMethod(this, 13, printf.Lit64, -4092);
        final ModuleMethod lambda$Fn17;
        frame10 staticLink;

        public frame11() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 14, null, -4093);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:401");
            this.lambda$Fn17 = moduleMethod;
        }

        public Object lambda29f(Object digs, Object exp, Object strip$Mn0s) {
            Object x;
            IntNum i0;
            try {
                Object digs2 = printf.stdio$ClRoundString((CharSequence) digs, AddOp.$Pl.apply2(exp, this.staticLink.precision), strip$Mn0s != Boolean.FALSE ? exp : strip$Mn0s);
                if (Scheme.numGEq.apply2(exp, printf.Lit1) != Boolean.FALSE) {
                    try {
                        if (numbers.isZero((Number) exp)) {
                            i0 = printf.Lit1;
                        } else {
                            try {
                                if (characters.isChar$Eq(printf.Lit9, Char.make(strings.stringRef((CharSequence) digs2, 0)))) {
                                    i0 = printf.Lit7;
                                } else {
                                    i0 = printf.Lit1;
                                }
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "string-ref", 1, digs2);
                            }
                        }
                        Object i1 = numbers.max(printf.Lit7, AddOp.$Pl.apply2(printf.Lit7, exp));
                        try {
                            try {
                                try {
                                    CharSequence idigs = strings.substring((CharSequence) digs2, i0.intValue(), ((Number) i1).intValue());
                                    try {
                                        try {
                                            try {
                                                CharSequence fdigs = strings.substring((CharSequence) digs2, ((Number) i1).intValue(), strings.stringLength((CharSequence) digs2));
                                                boolean x2 = strings.isString$Eq(fdigs, "");
                                                return lists.cons(idigs, (!x2 ? x2 : this.staticLink.alternate$Mnform == Boolean.FALSE) ? LList.Empty : LList.list2(".", fdigs));
                                            } catch (ClassCastException e2) {
                                                throw new WrongType(e2, "string-length", 1, digs2);
                                            }
                                        } catch (ClassCastException e3) {
                                            throw new WrongType(e3, "substring", 2, i1);
                                        }
                                    } catch (ClassCastException e4) {
                                        throw new WrongType(e4, "substring", 1, digs2);
                                    }
                                } catch (ClassCastException e5) {
                                    throw new WrongType(e5, "substring", 3, i1);
                                }
                            } catch (ClassCastException e6) {
                                throw new WrongType(e6, "substring", 2, (Object) i0);
                            }
                        } catch (ClassCastException e7) {
                            throw new WrongType(e7, "substring", 1, digs2);
                        }
                    } catch (ClassCastException e8) {
                        throw new WrongType(e8, "zero?", 1, exp);
                    }
                } else {
                    Object obj = this.staticLink.precision;
                    try {
                        if (numbers.isZero((Number) obj)) {
                            return LList.list1(this.staticLink.alternate$Mnform != Boolean.FALSE ? "0." : "0");
                        }
                        if (strip$Mn0s != Boolean.FALSE) {
                            boolean x3 = strings.isString$Eq(digs2, "");
                            Object obj2 = x3 ? LList.list1("0") : x3 ? Boolean.TRUE : Boolean.FALSE;
                            x = obj2;
                        } else {
                            x = strip$Mn0s;
                        }
                        if (x != Boolean.FALSE) {
                            return x;
                        }
                        String str = "0.";
                        Object min = numbers.min(this.staticLink.precision, AddOp.$Mn.apply2(printf.Lit17, exp));
                        try {
                            return LList.list3(str, strings.makeString(((Number) min).intValue(), printf.Lit9), digs2);
                        } catch (ClassCastException e9) {
                            throw new WrongType(e9, "make-string", 1, min);
                        }
                    } catch (ClassCastException e10) {
                        throw new WrongType(e10, "zero?", 1, obj);
                    }
                }
            } catch (ClassCastException e11) {
                throw new WrongType(e11, "stdio:round-string", 0, digs);
            }
        }

        public Object lambda30formatReal$V(Object signed$Qu, Object sgn, Object digs, Object exp, Object[] argsArray) {
            String str;
            Object i;
            Object obj;
            Boolean bool;
            Object obj2;
            LList rest = LList.makeList(argsArray, 0);
            if (lists.isNull(rest)) {
                try {
                    String str2 = characters.isChar$Eq(printf.Lit5, (Char) sgn) ? "-" : signed$Qu != Boolean.FALSE ? "+" : this.staticLink.blank != Boolean.FALSE ? " " : "";
                    Object x = Scheme.isEqv.apply2(this.fc, printf.Lit13);
                    if (x == Boolean.FALSE ? Scheme.isEqv.apply2(this.fc, printf.Lit54) != Boolean.FALSE : x != Boolean.FALSE) {
                        obj = Boolean.FALSE;
                    } else {
                        Object x2 = Scheme.isEqv.apply2(this.fc, printf.Lit25);
                        if (x2 == Boolean.FALSE ? Scheme.isEqv.apply2(this.fc, printf.Lit26) != Boolean.FALSE : x2 != Boolean.FALSE) {
                            obj2 = lambda29f(digs, exp, Boolean.FALSE);
                            return lists.cons(str2, obj2);
                        }
                        Object x3 = Scheme.isEqv.apply2(this.fc, printf.Lit55);
                        if (x3 == Boolean.FALSE ? Scheme.isEqv.apply2(this.fc, printf.Lit56) == Boolean.FALSE : x3 == Boolean.FALSE) {
                            if (Scheme.isEqv.apply2(this.fc, printf.Lit57) != Boolean.FALSE) {
                                str = "";
                            } else if (Scheme.isEqv.apply2(this.fc, printf.Lit58) != Boolean.FALSE) {
                                str = " ";
                            } else {
                                obj2 = Values.empty;
                                return lists.cons(str2, obj2);
                            }
                            try {
                                if (numbers.isNegative(LangObjType.coerceRealNum(exp))) {
                                    i = DivideOp.quotient.apply2(AddOp.$Mn.apply2(exp, printf.Lit61), printf.Lit61);
                                } else {
                                    i = DivideOp.quotient.apply2(AddOp.$Mn.apply2(exp, printf.Lit7), printf.Lit61);
                                }
                                Object apply3 = Scheme.numLss.apply3(printf.Lit17, AddOp.$Pl.apply2(i, printf.Lit48), Integer.valueOf(vectors.vectorLength(printf.Lit62)));
                                try {
                                    boolean x4 = ((Boolean) apply3).booleanValue();
                                    Object uind = x4 ? i : x4 ? Boolean.TRUE : Boolean.FALSE;
                                    if (uind != Boolean.FALSE) {
                                        Object exp2 = AddOp.$Mn.apply2(exp, MultiplyOp.$St.apply2(printf.Lit61, uind));
                                        this.staticLink.precision = numbers.max(printf.Lit1, AddOp.$Mn.apply2(this.staticLink.precision, exp2));
                                        Object[] objArr = new Object[2];
                                        objArr[0] = lambda29f(digs, exp2, Boolean.FALSE);
                                        FVector fVector = printf.Lit62;
                                        Object apply2 = AddOp.$Pl.apply2(uind, printf.Lit48);
                                        try {
                                            objArr[1] = LList.list2(str, vectors.vectorRef(fVector, ((Number) apply2).intValue()));
                                            obj2 = append.append$V(objArr);
                                            return lists.cons(str2, obj2);
                                        } catch (ClassCastException e) {
                                            throw new WrongType(e, "vector-ref", 2, apply2);
                                        }
                                    }
                                } catch (ClassCastException e2) {
                                    throw new WrongType(e2, "x", -2, apply3);
                                }
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "negative?", 1, exp);
                            }
                        }
                        Object obj3 = this.staticLink.alternate$Mnform;
                        try {
                            boolean strip$Mn0s = ((obj3 != Boolean.FALSE ? 1 : 0) + 1) & true;
                            this.staticLink.alternate$Mnform = Boolean.FALSE;
                            if (Scheme.numLEq.apply3(AddOp.$Mn.apply2(printf.Lit7, this.staticLink.precision), exp, this.staticLink.precision) != Boolean.FALSE) {
                                this.staticLink.precision = AddOp.$Mn.apply2(this.staticLink.precision, exp);
                                if (strip$Mn0s) {
                                    bool = Boolean.TRUE;
                                } else {
                                    bool = Boolean.FALSE;
                                }
                                obj2 = lambda29f(digs, exp, bool);
                                return lists.cons(str2, obj2);
                            }
                            this.staticLink.precision = AddOp.$Mn.apply2(this.staticLink.precision, printf.Lit7);
                            obj = strip$Mn0s ? Boolean.TRUE : Boolean.FALSE;
                        } catch (ClassCastException e4) {
                            throw new WrongType(e4, "strip-0s", -2, obj3);
                        }
                    }
                    try {
                        CharSequence charSequence = (CharSequence) digs;
                        Object apply22 = AddOp.$Pl.apply2(printf.Lit7, this.staticLink.precision);
                        if (obj != Boolean.FALSE) {
                            obj = printf.Lit1;
                        }
                        Object digs2 = printf.stdio$ClRoundString(charSequence, apply22, obj);
                        try {
                            IntNum istrt = characters.isChar$Eq(printf.Lit9, Char.make(strings.stringRef((CharSequence) digs2, 0))) ? printf.Lit7 : printf.Lit1;
                            try {
                                try {
                                    CharSequence fdigs = strings.substring((CharSequence) digs2, istrt.intValue() + 1, strings.stringLength((CharSequence) digs2));
                                    if (!numbers.isZero(istrt)) {
                                        exp = AddOp.$Mn.apply2(exp, printf.Lit7);
                                    }
                                    try {
                                        try {
                                            Pair list1 = LList.list1(strings.substring((CharSequence) digs2, istrt.intValue(), istrt.intValue() + 1));
                                            boolean x5 = strings.isString$Eq(fdigs, "");
                                            String str3 = (!x5 ? x5 : this.staticLink.alternate$Mnform == Boolean.FALSE) ? "" : ".";
                                            Object obj4 = this.fc;
                                            try {
                                                try {
                                                    try {
                                                        LList.chain1(LList.chain1(LList.chain4(list1, str3, fdigs, unicode.isCharUpperCase((Char) obj4) ? "E" : "e", numbers.isNegative(LangObjType.coerceRealNum(exp)) ? "-" : "+"), Scheme.numLss.apply3(printf.Lit60, exp, printf.Lit45) != Boolean.FALSE ? "0" : ""), numbers.number$To$String(numbers.abs((Number) exp)));
                                                        obj2 = list1;
                                                        return lists.cons(str2, obj2);
                                                    } catch (ClassCastException e5) {
                                                        throw new WrongType(e5, "abs", 1, exp);
                                                    }
                                                } catch (ClassCastException e6) {
                                                    throw new WrongType(e6, "negative?", 1, exp);
                                                }
                                            } catch (ClassCastException e7) {
                                                throw new WrongType(e7, "char-upper-case?", 1, obj4);
                                            }
                                        } catch (ClassCastException e8) {
                                            throw new WrongType(e8, "substring", 2, (Object) istrt);
                                        }
                                    } catch (ClassCastException e9) {
                                        throw new WrongType(e9, "substring", 1, digs2);
                                    }
                                } catch (ClassCastException e10) {
                                    throw new WrongType(e10, "string-length", 1, digs2);
                                }
                            } catch (ClassCastException e11) {
                                throw new WrongType(e11, "substring", 1, digs2);
                            }
                        } catch (ClassCastException e12) {
                            throw new WrongType(e12, "string-ref", 1, digs2);
                        }
                    } catch (ClassCastException e13) {
                        throw new WrongType(e13, "stdio:round-string", 0, digs);
                    }
                } catch (ClassCastException e14) {
                    throw new WrongType(e14, "char=?", 2, sgn);
                }
            } else {
                return append.append$V(new Object[]{lambda30formatReal$V(signed$Qu, sgn, digs, exp, new Object[0]), Scheme.apply.apply3(this.format$Mnreal, Boolean.TRUE, rest), printf.Lit63});
            }
        }

        public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 13:
                    callContext.values = objArr;
                    callContext.proc = moduleMethod;
                    callContext.pc = 5;
                    return 0;
                case 14:
                    callContext.values = objArr;
                    callContext.proc = moduleMethod;
                    callContext.pc = 5;
                    return 0;
                default:
                    return super.matchN(moduleMethod, objArr, callContext);
            }
        }

        public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
            switch (moduleMethod.selector) {
                case 13:
                    Object obj = objArr[0];
                    Object obj2 = objArr[1];
                    Object obj3 = objArr[2];
                    Object obj4 = objArr[3];
                    int length = objArr.length - 4;
                    Object[] objArr2 = new Object[length];
                    while (true) {
                        length--;
                        if (length < 0) {
                            return lambda30formatReal$V(obj, obj2, obj3, obj4, objArr2);
                        }
                        objArr2[length] = objArr[length + 4];
                    }
                case 14:
                    Object obj5 = objArr[0];
                    Object obj6 = objArr[1];
                    Object obj7 = objArr[2];
                    int length2 = objArr.length - 3;
                    Object[] objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return lambda31$V(obj5, obj6, obj7, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 3];
                    }
                default:
                    return super.applyN(moduleMethod, objArr);
            }
        }

        /* access modifiers changed from: 0000 */
        public Object lambda31$V(Object sgn, Object digs, Object expon, Object[] argsArray) {
            LList imag = LList.makeList(argsArray, 0);
            return Scheme.apply.apply2(this.staticLink.pad, Scheme.apply.applyN(new Object[]{this.format$Mnreal, this.staticLink.signed, sgn, digs, expon, imag}));
        }
    }

    /* compiled from: printf.scm */
    public class frame12 extends ModuleBody {
        Object cnt;
        final ModuleMethod lambda$Fn18;
        Object port;

        public frame12() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 20, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:546");
            this.lambda$Fn18 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 20 ? lambda32(obj) : super.apply1(moduleMethod, obj);
        }

        /* access modifiers changed from: 0000 */
        public Boolean lambda32(Object x) {
            if (strings.isString(x)) {
                try {
                    this.cnt = AddOp.$Pl.apply2(Integer.valueOf(strings.stringLength((CharSequence) x)), this.cnt);
                    ports.display(x, this.port);
                    return Boolean.TRUE;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-length", 1, x);
                }
            } else {
                this.cnt = AddOp.$Pl.apply2(printf.Lit7, this.cnt);
                ports.display(x, this.port);
                return Boolean.TRUE;
            }
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 20) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: printf.scm */
    public class frame13 extends ModuleBody {
        Object cnt;
        Object end;
        final ModuleMethod lambda$Fn19;
        Object s;
        Object str;

        public frame13() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 21, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:564");
            this.lambda$Fn19 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 21) {
                return lambda33(obj) ? Boolean.TRUE : Boolean.FALSE;
            }
            return super.apply1(moduleMethod, obj);
        }

        /* access modifiers changed from: 0000 */
        public boolean lambda33(Object x) {
            Object x2;
            int i;
            char c;
            if (strings.isString(x)) {
                if (this.str == Boolean.FALSE) {
                    try {
                        if (Scheme.numGEq.apply2(AddOp.$Mn.apply2(this.end, this.cnt), Integer.valueOf(strings.stringLength((CharSequence) x))) == Boolean.FALSE) {
                            Object[] objArr = new Object[2];
                            Object obj = this.s;
                            try {
                                CharSequence charSequence = (CharSequence) obj;
                                Object obj2 = this.cnt;
                                try {
                                    objArr[0] = strings.substring(charSequence, 0, ((Number) obj2).intValue());
                                    objArr[1] = x;
                                    this.s = strings.stringAppend(objArr);
                                    Object obj3 = this.s;
                                    try {
                                        this.cnt = Integer.valueOf(strings.stringLength((CharSequence) obj3));
                                        this.end = this.cnt;
                                    } catch (ClassCastException e) {
                                        throw new WrongType(e, "string-length", 1, obj3);
                                    }
                                } catch (ClassCastException e2) {
                                    throw new WrongType(e2, "substring", 3, obj2);
                                }
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "substring", 1, obj);
                            }
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "string-length", 1, x);
                    }
                }
                Object[] objArr2 = new Object[2];
                try {
                    objArr2[0] = Integer.valueOf(strings.stringLength((CharSequence) x));
                    objArr2[1] = AddOp.$Mn.apply2(this.end, this.cnt);
                    Object lend = numbers.min(objArr2);
                    Object obj4 = printf.Lit1;
                    while (Scheme.numGEq.apply2(obj4, lend) == Boolean.FALSE) {
                        Object obj5 = this.s;
                        try {
                            CharSeq charSeq = (CharSeq) obj5;
                            Object obj6 = this.cnt;
                            try {
                                try {
                                    try {
                                        strings.stringSet$Ex(charSeq, ((Number) obj6).intValue(), strings.stringRef((CharSequence) x, ((Number) obj4).intValue()));
                                        this.cnt = AddOp.$Pl.apply2(this.cnt, printf.Lit7);
                                        obj4 = AddOp.$Pl.apply2(obj4, printf.Lit7);
                                    } catch (ClassCastException e5) {
                                        throw new WrongType(e5, "string-ref", 2, obj4);
                                    }
                                } catch (ClassCastException e6) {
                                    throw new WrongType(e6, "string-ref", 1, x);
                                }
                            } catch (ClassCastException e7) {
                                throw new WrongType(e7, "string-set!", 2, obj6);
                            }
                        } catch (ClassCastException e8) {
                            throw new WrongType(e8, "string-set!", 1, obj5);
                        }
                    }
                } catch (ClassCastException e9) {
                    throw new WrongType(e9, "string-length", 1, x);
                }
            } else {
                if (this.str != Boolean.FALSE) {
                    x2 = Scheme.numGEq.apply2(this.cnt, this.end);
                } else {
                    x2 = this.str;
                }
                if (x2 == Boolean.FALSE) {
                    Object obj7 = this.str;
                    try {
                        if (obj7 != Boolean.FALSE) {
                            i = 1;
                        } else {
                            i = 0;
                        }
                        boolean x3 = (i + 1) & true;
                        if (!x3 ? x3 : Scheme.numGEq.apply2(this.cnt, this.end) != Boolean.FALSE) {
                            this.s = strings.stringAppend(this.s, strings.makeString(100));
                            Object obj8 = this.s;
                            try {
                                this.end = Integer.valueOf(strings.stringLength((CharSequence) obj8));
                            } catch (ClassCastException e10) {
                                throw new WrongType(e10, "string-length", 1, obj8);
                            }
                        }
                        Object obj9 = this.s;
                        try {
                            CharSeq charSeq2 = (CharSeq) obj9;
                            Object obj10 = this.cnt;
                            try {
                                int intValue = ((Number) obj10).intValue();
                                if (characters.isChar(x)) {
                                    try {
                                        c = ((Char) x).charValue();
                                    } catch (ClassCastException e11) {
                                        throw new WrongType(e11, "string-set!", 3, x);
                                    }
                                } else {
                                    c = '?';
                                }
                                strings.stringSet$Ex(charSeq2, intValue, c);
                                this.cnt = AddOp.$Pl.apply2(this.cnt, printf.Lit7);
                            } catch (ClassCastException e12) {
                                throw new WrongType(e12, "string-set!", 2, obj10);
                            }
                        } catch (ClassCastException e13) {
                            throw new WrongType(e13, "string-set!", 1, obj9);
                        }
                    } catch (ClassCastException e14) {
                        throw new WrongType(e14, "x", -2, obj7);
                    }
                }
            }
            int i2 = this.str != Boolean.FALSE ? Scheme.numGEq.apply2(this.cnt, this.end) != Boolean.FALSE ? 1 : 0 : this.str != Boolean.FALSE ? 1 : 0;
            return (i2 + 1) & true;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 21) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: printf.scm */
    public class frame2 extends ModuleBody {
        Object cont;
        final ModuleMethod lambda$Fn5;
        final ModuleMethod lambda$Fn6;
        frame staticLink;

        public frame2() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 10, null, 8194);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:81");
            this.lambda$Fn6 = moduleMethod;
            ModuleMethod moduleMethod2 = new ModuleMethod(this, 11, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:78");
            this.lambda$Fn5 = moduleMethod2;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 11 ? lambda9(obj) : super.apply1(moduleMethod, obj);
        }

        /* access modifiers changed from: 0000 */
        public Object lambda9(Object i) {
            return this.staticLink.lambda2sign(i, this.lambda$Fn6);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 11) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 10 ? lambda10(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        /* access modifiers changed from: 0000 */
        public Object lambda10(Object i, Object sgn) {
            frame3 frame3 = new frame3();
            frame3.staticLink = this;
            frame3.sgn = sgn;
            return this.staticLink.lambda3digits(i, frame3.lambda$Fn7);
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 10) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: printf.scm */
    public class frame3 extends ModuleBody {
        final ModuleMethod lambda$Fn7;
        Object sgn;
        frame2 staticLink;

        public frame3() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 9, null, 8194);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:84");
            this.lambda$Fn7 = moduleMethod;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 9 ? lambda11(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0043, code lost:
            if (kawa.lib.characters.isChar$Eq(r5, gnu.text.Char.make(kawa.lib.strings.stringRef((java.lang.CharSequence) r2, ((java.lang.Number) r8).intValue()))) != false) goto L_0x0045;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0054, code lost:
            if (r1 == false) goto L_0x0056;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply2(r4, r8);
         */
        public Object lambda11(Object i, Object idigs) {
            frame4 frame4 = new frame4();
            frame4.staticLink = this;
            frame4.idigs = idigs;
            ModuleMethod moduleMethod = frame4.lambda$Fn8;
            Object apply2 = Scheme.numLss.apply2(i, Integer.valueOf(this.staticLink.staticLink.n));
            try {
                boolean x = ((Boolean) apply2).booleanValue();
                if (x) {
                    Char charR = printf.Lit11;
                    Object obj = this.staticLink.staticLink.str;
                    try {
                        try {
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-ref", 2, i);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-ref", 1, obj);
                    }
                }
                return Scheme.applyToArgs.apply2(moduleMethod, AddOp.$Pl.apply2(i, printf.Lit7));
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "x", -2, apply2);
            }
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 9) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: printf.scm */
    public class frame4 extends ModuleBody {
        Object idigs;
        final ModuleMethod lambda$Fn8;
        final ModuleMethod lambda$Fn9;
        frame3 staticLink;

        public frame4() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 7, null, 8194);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:90");
            this.lambda$Fn9 = moduleMethod;
            ModuleMethod moduleMethod2 = new ModuleMethod(this, 8, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:87");
            this.lambda$Fn8 = moduleMethod2;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 8 ? lambda12(obj) : super.apply1(moduleMethod, obj);
        }

        /* access modifiers changed from: 0000 */
        public Object lambda12(Object i) {
            return this.staticLink.staticLink.staticLink.lambda3digits(i, this.lambda$Fn9);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 8) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 7 ? lambda13(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        /* access modifiers changed from: 0000 */
        public Object lambda13(Object i, Object fdigs) {
            frame5 frame5 = new frame5();
            frame5.staticLink = this;
            frame5.fdigs = fdigs;
            ModuleMethod moduleMethod = frame5.lambda$Fn10;
            frame closureEnv = this.staticLink.staticLink.staticLink;
            frame6 frame6 = new frame6();
            frame6.staticLink = closureEnv;
            frame6.cont = moduleMethod;
            if (Scheme.numGEq.apply2(i, Integer.valueOf(this.staticLink.staticLink.staticLink.n)) != Boolean.FALSE) {
                return Scheme.applyToArgs.apply3(frame6.cont, i, printf.Lit1);
            }
            Object obj = this.staticLink.staticLink.staticLink.str;
            try {
                try {
                    if (lists.memv(Char.make(strings.stringRef((CharSequence) obj, ((Number) i).intValue())), printf.Lit10) != Boolean.FALSE) {
                        return this.staticLink.staticLink.staticLink.lambda2sign(AddOp.$Pl.apply2(i, printf.Lit7), frame6.lambda$Fn11);
                    }
                    return Scheme.applyToArgs.apply3(frame6.cont, i, printf.Lit1);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-ref", 2, i);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-ref", 1, obj);
            }
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 7) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: printf.scm */
    public class frame5 extends ModuleBody {
        Object fdigs;
        final ModuleMethod lambda$Fn10;
        frame4 staticLink;

        public frame5() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 6, null, 8194);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:92");
            this.lambda$Fn10 = moduleMethod;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 6 ? lambda14(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        /* access modifiers changed from: 0000 */
        public Object lambda14(Object i, Object ex) {
            FString digs = strings.stringAppend("0", this.staticLink.idigs, this.fdigs);
            int ndigs = strings.stringLength(digs);
            Object obj = printf.Lit7;
            AddOp addOp = AddOp.$Pl;
            Object obj2 = this.staticLink.idigs;
            try {
                Object ex2 = addOp.apply2(ex, Integer.valueOf(strings.stringLength((CharSequence) obj2)));
                while (Scheme.numGEq.apply2(obj, Integer.valueOf(ndigs)) == Boolean.FALSE) {
                    try {
                        if (characters.isChar$Eq(printf.Lit9, Char.make(strings.stringRef(digs, ((Number) obj).intValue())))) {
                            obj = AddOp.$Pl.apply2(obj, printf.Lit7);
                            ex2 = AddOp.$Mn.apply2(ex2, printf.Lit7);
                        } else {
                            ApplyToArgs applyToArgs = Scheme.applyToArgs;
                            Object[] objArr = new Object[5];
                            objArr[0] = this.staticLink.staticLink.staticLink.cont;
                            objArr[1] = i;
                            objArr[2] = this.staticLink.staticLink.sgn;
                            Object apply2 = AddOp.$Mn.apply2(obj, printf.Lit7);
                            try {
                                objArr[3] = strings.substring(digs, ((Number) apply2).intValue(), ndigs);
                                objArr[4] = ex2;
                                return applyToArgs.applyN(objArr);
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "substring", 2, apply2);
                            }
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-ref", 2, obj);
                    }
                }
                return Scheme.applyToArgs.applyN(new Object[]{this.staticLink.staticLink.staticLink.cont, i, this.staticLink.staticLink.sgn, "0", printf.Lit7});
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "string-length", 1, obj2);
            }
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 6) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: printf.scm */
    public class frame6 extends ModuleBody {
        Object cont;
        final ModuleMethod lambda$Fn11;
        frame staticLink;

        public frame6() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 5, null, 8194);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:67");
            this.lambda$Fn11 = moduleMethod;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 5 ? lambda15(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        /* access modifiers changed from: 0000 */
        public Object lambda15(Object i, Object sgn) {
            frame7 frame7 = new frame7();
            frame7.staticLink = this;
            frame7.sgn = sgn;
            return this.staticLink.lambda3digits(i, frame7.lambda$Fn12);
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 5) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: printf.scm */
    public class frame7 extends ModuleBody {
        final ModuleMethod lambda$Fn12;
        Object sgn;
        frame6 staticLink;

        public frame7() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 4, null, 8194);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:69");
            this.lambda$Fn12 = moduleMethod;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 4 ? lambda16(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        /* access modifiers changed from: 0000 */
        public Object lambda16(Object i, Object digs) {
            Object string$To$Number;
            ApplyToArgs applyToArgs = Scheme.applyToArgs;
            Object obj = this.staticLink.cont;
            Char charR = printf.Lit5;
            Object obj2 = this.sgn;
            try {
                if (characters.isChar$Eq(charR, (Char) obj2)) {
                    try {
                        string$To$Number = AddOp.$Mn.apply1(numbers.string$To$Number((CharSequence) digs));
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "string->number", 1, digs);
                    }
                } else {
                    try {
                        string$To$Number = numbers.string$To$Number((CharSequence) digs);
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string->number", 1, digs);
                    }
                }
                return applyToArgs.apply3(obj, i, string$To$Number);
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "char=?", 2, obj2);
            }
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 4) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: printf.scm */
    public class frame8 extends ModuleBody {
        CharSequence str;

        public Object lambda17dig(Object i) {
            try {
                char c = strings.stringRef(this.str, ((Number) i).intValue());
                if (!unicode.isCharNumeric(Char.make(c))) {
                    return printf.Lit1;
                }
                return numbers.string$To$Number(strings.$make$string$(Char.make(c)));
            } catch (ClassCastException e) {
                throw new WrongType(e, "string-ref", 2, i);
            }
        }
    }

    /* compiled from: printf.scm */
    public class frame9 extends ModuleBody {
        LList args;
        Object fc;
        int fl;
        Object format$Mnstring;
        Object out;
        Object pos;

        public Object lambda18mustAdvance() {
            this.pos = AddOp.$Pl.apply2(printf.Lit7, this.pos);
            if (Scheme.numGEq.apply2(this.pos, Integer.valueOf(this.fl)) != Boolean.FALSE) {
                return lambda20incomplete();
            }
            Object obj = this.format$Mnstring;
            try {
                CharSequence charSequence = (CharSequence) obj;
                Object obj2 = this.pos;
                try {
                    this.fc = Char.make(strings.stringRef(charSequence, ((Number) obj2).intValue()));
                    return Values.empty;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-ref", 2, obj2);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-ref", 1, obj);
            }
        }

        public boolean lambda19isEndOfFormat() {
            return ((Boolean) Scheme.numGEq.apply2(this.pos, Integer.valueOf(this.fl))).booleanValue();
        }

        public Object lambda20incomplete() {
            return misc.error$V(printf.Lit34, new Object[]{"conversion specification incomplete", this.format$Mnstring});
        }

        public Object lambda21out$St(Object strs) {
            if (strings.isString(strs)) {
                return Scheme.applyToArgs.apply2(this.out, strs);
            }
            while (true) {
                boolean x = lists.isNull(strs);
                if (x) {
                    return x ? Boolean.TRUE : Boolean.FALSE;
                }
                Object x2 = Scheme.applyToArgs.apply2(this.out, lists.car.apply1(strs));
                if (x2 == Boolean.FALSE) {
                    return x2;
                }
                strs = lists.cdr.apply1(strs);
            }
        }
    }

    static {
        Char charR = Lit35;
        Char charR2 = Lit37;
        Char charR3 = Lit39;
        Char make = Char.make(100);
        Lit12 = make;
        Char make2 = Char.make(105);
        Lit3 = make2;
        Char charR4 = Lit43;
        Char charR5 = Lit46;
        Char charR6 = Lit49;
        Char charR7 = Lit52;
        Char make3 = Char.make(102);
        Lit25 = make3;
        Char make4 = Char.make(101);
        Lit13 = make4;
        Lit33 = PairWithPosition.make(charR, PairWithPosition.make(charR2, PairWithPosition.make(charR3, PairWithPosition.make(make, PairWithPosition.make(make2, PairWithPosition.make(charR4, PairWithPosition.make(charR5, PairWithPosition.make(charR6, PairWithPosition.make(charR7, PairWithPosition.make(make3, PairWithPosition.make(make4, PairWithPosition.make(Lit55, PairWithPosition.make(Lit57, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1781780), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1781776), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1781772), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1781768), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1777704), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1777700), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1777696), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1777692), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1777688), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1777684), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1777680), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1777676), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1777671);
        printf printf2 = $instance;
        stdio$Clparse$Mnfloat = new ModuleMethod(printf2, 22, Lit69, 8194);
        stdio$Clround$Mnstring = new ModuleMethod(printf2, 23, Lit70, 12291);
        stdio$Cliprintf = new ModuleMethod(printf2, 24, Lit71, -4094);
        fprintf = new ModuleMethod(printf2, 25, Lit72, -4094);
        printf = new ModuleMethod(printf2, 26, Lit34, -4095);
        sprintf = new ModuleMethod(printf2, 27, Lit68, -4094);
        $instance.run();
    }

    public printf() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer consumer = $ctx.consumer;
        stdio$Clhex$Mnupper$Mncase$Qu = strings.isString$Eq("-F", numbers.number$To$String(Lit0, 16));
    }

    public static Object stdio$ClParseFloat(Object str, Object proc) {
        frame frame14 = new frame();
        frame14.str = str;
        frame14.proc = proc;
        Object obj = frame14.str;
        try {
            frame14.n = strings.stringLength((CharSequence) obj);
            return frame14.lambda4real(Lit1, frame14.lambda$Fn1);
        } catch (ClassCastException e) {
            throw new WrongType(e, "string-length", 1, obj);
        }
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        return moduleMethod.selector == 22 ? stdio$ClParseFloat(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        if (moduleMethod.selector != 22) {
            return super.match2(moduleMethod, obj, obj2, callContext);
        }
        callContext.value1 = obj;
        callContext.value2 = obj2;
        callContext.proc = moduleMethod;
        callContext.pc = 2;
        return 0;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0112, code lost:
        if (r8 != false) goto L_0x0114;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x017d, code lost:
        if ((((java.lang.Number) r1.lambda17dig(r15)).intValue() & 1) != 0) goto L_0x0114;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0195, code lost:
        if (r8 != false) goto L_0x0114;
     */
    public static Object stdio$ClRoundString(CharSequence str, Object ndigs, Object strip$Mn0s) {
        Object res;
        Object d;
        Object stringAppend;
        frame8 closureEnv = new frame8();
        closureEnv.str = str;
        int n = strings.stringLength(closureEnv.str) - 1;
        if (Scheme.numLss.apply2(ndigs, Lit1) != Boolean.FALSE) {
            res = "";
        } else if (Scheme.numEqu.apply2(Integer.valueOf(n), ndigs) != Boolean.FALSE) {
            res = closureEnv.str;
        } else if (Scheme.numLss.apply2(Integer.valueOf(n), ndigs) != Boolean.FALSE) {
            Object[] objArr = new Object[2];
            objArr[0] = Lit1;
            AddOp addOp = AddOp.$Mn;
            if (strip$Mn0s != Boolean.FALSE) {
                ndigs = strip$Mn0s;
            }
            objArr[1] = addOp.apply2(ndigs, Integer.valueOf(n));
            Object padlen = numbers.max(objArr);
            try {
                if (numbers.isZero((Number) padlen)) {
                    stringAppend = closureEnv.str;
                } else {
                    Object[] objArr2 = new Object[2];
                    objArr2[0] = closureEnv.str;
                    try {
                        objArr2[1] = strings.makeString(((Number) padlen).intValue(), unicode.isCharNumeric(Char.make(strings.stringRef(closureEnv.str, n))) ? Lit9 : Lit8);
                        stringAppend = strings.stringAppend(objArr2);
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "make-string", 1, padlen);
                    }
                }
                res = stringAppend;
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "zero?", 1, padlen);
            }
        } else {
            CharSequence charSequence = closureEnv.str;
            Object apply2 = AddOp.$Pl.apply2(ndigs, Lit7);
            try {
                res = strings.substring(charSequence, 0, ((Number) apply2).intValue());
                Object ldig = closureEnv.lambda17dig(AddOp.$Pl.apply2(Lit7, ndigs));
                Object apply22 = Scheme.numGrt.apply2(ldig, Lit15);
                try {
                    boolean x = ((Boolean) apply22).booleanValue();
                    if (!x) {
                        Object apply23 = Scheme.numEqu.apply2(ldig, Lit15);
                        try {
                            boolean x2 = ((Boolean) apply23).booleanValue();
                            if (x2) {
                                Object i = AddOp.$Pl.apply2(Lit14, ndigs);
                                while (true) {
                                    if (Scheme.numGrt.apply2(i, Integer.valueOf(n)) == Boolean.FALSE) {
                                        Object lambda17dig = closureEnv.lambda17dig(i);
                                        try {
                                            if (!numbers.isZero((Number) lambda17dig)) {
                                                break;
                                            }
                                            i = AddOp.$Pl.apply2(i, Lit7);
                                        } catch (ClassCastException e3) {
                                            throw new WrongType(e3, "zero?", 1, lambda17dig);
                                        }
                                    }
                                }
                            }
                        } catch (ClassCastException e4) {
                            throw new WrongType(e4, "x", -2, apply23);
                        }
                    }
                    Object i2 = ndigs;
                    while (true) {
                        d = closureEnv.lambda17dig(i2);
                        if (Scheme.numLss.apply2(d, Lit16) != Boolean.FALSE) {
                            break;
                        }
                        try {
                            try {
                                strings.stringSet$Ex((CharSeq) res, ((Number) i2).intValue(), '0');
                                i2 = AddOp.$Mn.apply2(i2, Lit7);
                            } catch (ClassCastException e5) {
                                throw new WrongType(e5, "string-set!", 2, i2);
                            }
                        } catch (ClassCastException e6) {
                            throw new WrongType(e6, "string-set!", 1, res);
                        }
                    }
                    try {
                        CharSeq charSeq = (CharSeq) res;
                        try {
                            int intValue = ((Number) i2).intValue();
                            Object apply24 = AddOp.$Pl.apply2(d, Lit7);
                            try {
                                strings.stringSet$Ex(charSeq, intValue, strings.stringRef(numbers.number$To$String((Number) apply24), 0));
                            } catch (ClassCastException e7) {
                                throw new WrongType(e7, "number->string", 1, apply24);
                            }
                        } catch (ClassCastException e8) {
                            throw new WrongType(e8, "string-set!", 2, i2);
                        }
                    } catch (ClassCastException e9) {
                        throw new WrongType(e9, "string-set!", 1, res);
                    }
                } catch (ClassCastException e10) {
                    throw new WrongType(e10, "x", -2, apply22);
                }
            } catch (ClassCastException e11) {
                throw new WrongType(e11, "substring", 3, apply2);
            }
        }
        if (strip$Mn0s == Boolean.FALSE) {
            return res;
        }
        try {
            Object valueOf = Integer.valueOf(strings.stringLength((CharSequence) res) - 1);
            while (true) {
                Object apply25 = Scheme.numLEq.apply2(valueOf, strip$Mn0s);
                try {
                    boolean x3 = ((Boolean) apply25).booleanValue();
                    if (!x3) {
                        try {
                            try {
                                if (characters.isChar$Eq(Lit9, Char.make(strings.stringRef((CharSequence) res, ((Number) valueOf).intValue())))) {
                                    valueOf = AddOp.$Mn.apply2(valueOf, Lit7);
                                }
                            } catch (ClassCastException e12) {
                                throw new WrongType(e12, "string-ref", 2, valueOf);
                            }
                        } catch (ClassCastException e13) {
                            throw new WrongType(e13, "string-ref", 1, res);
                        }
                    } else if (!x3) {
                        valueOf = AddOp.$Mn.apply2(valueOf, Lit7);
                    }
                    try {
                        CharSequence charSequence2 = (CharSequence) res;
                        Object apply26 = AddOp.$Pl.apply2(valueOf, Lit7);
                        try {
                            return strings.substring(charSequence2, 0, ((Number) apply26).intValue());
                        } catch (ClassCastException e14) {
                            throw new WrongType(e14, "substring", 3, apply26);
                        }
                    } catch (ClassCastException e15) {
                        throw new WrongType(e15, "substring", 1, res);
                    }
                } catch (ClassCastException e16) {
                    throw new WrongType(e16, "x", -2, apply25);
                }
            }
        } catch (ClassCastException e17) {
            throw new WrongType(e17, "string-length", 1, res);
        }
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        if (moduleMethod.selector != 23) {
            return super.apply3(moduleMethod, obj, obj2, obj3);
        }
        try {
            return stdio$ClRoundString((CharSequence) obj, obj2, obj3);
        } catch (ClassCastException e) {
            throw new WrongType(e, "stdio:round-string", 1, obj);
        }
    }

    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        if (moduleMethod.selector != 23) {
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }
        if (!(obj instanceof CharSequence)) {
            return -786431;
        }
        callContext.value1 = obj;
        callContext.value2 = obj2;
        callContext.value3 = obj3;
        callContext.proc = moduleMethod;
        callContext.pc = 3;
        return 0;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:136:0x0302, code lost:
        if (r7 == false) goto L_0x0304;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:167:0x038e, code lost:
        if (kawa.lib.numbers.isNegative(gnu.kawa.lispexpr.LangObjType.coerceRealNum(r10)) != false) goto L_0x0390;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:175:0x03a9, code lost:
        if (kawa.lib.numbers.isNegative(gnu.kawa.lispexpr.LangObjType.coerceRealNum(r9)) != false) goto L_0x03ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:191:0x0412, code lost:
        if (kawa.standard.Scheme.numGEq.apply2(r12.precision, java.lang.Integer.valueOf(kawa.lib.strings.stringLength((java.lang.CharSequence) r4))) == java.lang.Boolean.FALSE) goto L_0x0304;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:217:0x048f, code lost:
        if (r7 != java.lang.Boolean.FALSE) goto L_0x0491;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:223:0x04b9, code lost:
        if (r7 == java.lang.Boolean.FALSE) goto L_0x04bb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:225:0x04bf, code lost:
        if (r12.left$Mnadjust == java.lang.Boolean.FALSE) goto L_0x04c5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:226:0x04c1, code lost:
        r10 = r12.lambda$Fn14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:227:0x04c5, code lost:
        r10 = r12.pr;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:231:0x04cf, code lost:
        if (kawa.lib.numbers.isNegative(gnu.kawa.lispexpr.LangObjType.coerceRealNum(r10)) == false) goto L_0x04d9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:232:0x04d1, code lost:
        r12.pr = r12.width;
        r10 = r12.lambda$Fn15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:233:0x04d9, code lost:
        r10 = r12.lambda$Fn16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:235:0x04df, code lost:
        if (r7 == java.lang.Boolean.FALSE) goto L_0x04e1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:237:0x04e5, code lost:
        if (r12.left$Mnadjust == java.lang.Boolean.FALSE) goto L_0x0528;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:239:0x04fb, code lost:
        if (kawa.standard.Scheme.numGrt.apply2(r12.width, gnu.kawa.functions.AddOp.$Mn.apply2(r12.precision, r12.pr)) == java.lang.Boolean.FALSE) goto L_0x03d8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:240:0x04fd, code lost:
        r11 = kawa.standard.Scheme.applyToArgs;
        r13 = r2.out;
        r10 = gnu.kawa.functions.AddOp.$Mn.apply2(r12.width, gnu.kawa.functions.AddOp.$Mn.apply2(r12.precision, r12.pr));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:243:0x051c, code lost:
        r11.apply2(r13, kawa.lib.strings.makeString(((java.lang.Number) r10).intValue(), Lit29));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:244:0x0528, code lost:
        r9 = r12.os;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:247:0x052c, code lost:
        if (r9 == java.lang.Boolean.FALSE) goto L_0x0558;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:248:0x052e, code lost:
        r9 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:250:0x0533, code lost:
        if (((r9 + 1) & true) != false) goto L_0x03d8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:251:0x0535, code lost:
        r10 = kawa.standard.Scheme.numLEq;
        r11 = r12.width;
        r9 = r12.os;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:255:0x054b, code lost:
        if (r10.apply2(r11, java.lang.Integer.valueOf(kawa.lib.strings.stringLength((java.lang.CharSequence) r9))) == java.lang.Boolean.FALSE) goto L_0x055a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:256:0x054d, code lost:
        kawa.standard.Scheme.applyToArgs.apply2(r2.out, r12.os);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:257:0x0558, code lost:
        r9 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:258:0x055a, code lost:
        r11 = kawa.standard.Scheme.applyToArgs;
        r13 = r2.out;
        r10 = gnu.kawa.functions.AddOp.$Mn;
        r14 = r12.width;
        r9 = r12.os;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:261:0x0566, code lost:
        r10 = r10.apply2(r14, java.lang.Integer.valueOf(kawa.lib.strings.stringLength((java.lang.CharSequence) r9)));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:265:0x0586, code lost:
        if (r11.apply2(r13, kawa.lib.strings.makeString(((java.lang.Number) r10).intValue(), Lit29)) == java.lang.Boolean.FALSE) goto L_0x03d8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:266:0x0588, code lost:
        kawa.standard.Scheme.applyToArgs.apply2(r2.out, r12.os);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:270:0x05a1, code lost:
        if (r7 == java.lang.Boolean.FALSE) goto L_0x05a3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:281:0x05e3, code lost:
        if (r7 != java.lang.Boolean.FALSE) goto L_0x0491;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:285:0x05f5, code lost:
        if (r7 != java.lang.Boolean.FALSE) goto L_0x0491;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:329:0x06f9, code lost:
        if (r7 != java.lang.Boolean.FALSE) goto L_0x06fb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:349:0x075c, code lost:
        if (r7 == java.lang.Boolean.FALSE) goto L_0x075e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:356:0x0778, code lost:
        if (r7 != java.lang.Boolean.FALSE) goto L_0x06fb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:360:0x0789, code lost:
        if (r7 != java.lang.Boolean.FALSE) goto L_0x06fb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:364:0x079b, code lost:
        if (r7 != java.lang.Boolean.FALSE) goto L_0x06fb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:368:0x07ad, code lost:
        if (r7 != java.lang.Boolean.FALSE) goto L_0x06fb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:384:0x07e5, code lost:
        if (kawa.lib.rnrs.unicode.isCharCi$Eq((gnu.text.Char) r9, Lit55) != false) goto L_0x07e7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:386:0x07ed, code lost:
        if (r7 != false) goto L_0x07e7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:459:0x0904, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:461:0x090d, code lost:
        throw new gnu.mapping.WrongType(r9, "negative?", 1, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:468:0x0922, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:470:0x092b, code lost:
        throw new gnu.mapping.WrongType(r9, "make-string", 1, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:471:0x092c, code lost:
        r10 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:473:0x0935, code lost:
        throw new gnu.mapping.WrongType(r10, "x", -2, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:474:0x0936, code lost:
        r10 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:476:0x093f, code lost:
        throw new gnu.mapping.WrongType(r10, "string-length", 1, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:477:0x0940, code lost:
        r10 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:479:0x0949, code lost:
        throw new gnu.mapping.WrongType(r10, "string-length", 1, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:480:0x094a, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:482:0x0953, code lost:
        throw new gnu.mapping.WrongType(r9, "make-string", 1, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0226, code lost:
        if (r7 != java.lang.Boolean.FALSE) goto L_0x0228;
     */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x0272  */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x0286  */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0294  */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x02be  */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x033f  */
    /* JADX WARNING: Removed duplicated region for block: B:277:0x05cb  */
    /* JADX WARNING: Removed duplicated region for block: B:397:0x0816  */
    /* JADX WARNING: Removed duplicated region for block: B:506:0x0075 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:509:0x0075 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:514:0x0764 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x0233  */
    public static Object stdio$ClIprintf$V(Object out, Object formatString, Object[] argsArray) {
        Boolean bool;
        Object x;
        Object x2;
        Object str;
        Object obj;
        Object x3;
        Object s;
        Char charR;
        Object x4;
        Object x5;
        frame9 closureEnv = new frame9();
        closureEnv.out = out;
        closureEnv.format$Mnstring = formatString;
        closureEnv.args = LList.makeList(argsArray, 0);
        if (IsEqual.apply("", closureEnv.format$Mnstring)) {
            return Values.empty;
        }
        IntNum intNum = Lit17;
        Object obj2 = closureEnv.format$Mnstring;
        try {
            int stringLength = strings.stringLength((CharSequence) obj2);
            Object obj3 = closureEnv.format$Mnstring;
            try {
                closureEnv.fc = Char.make(strings.stringRef((CharSequence) obj3, 0));
                closureEnv.fl = stringLength;
                closureEnv.pos = intNum;
                Object obj4 = closureEnv.args;
                while (true) {
                    frame10 frame102 = new frame10();
                    frame102.staticLink = closureEnv;
                    frame102.args = obj4;
                    closureEnv.pos = AddOp.$Pl.apply2(Lit7, closureEnv.pos);
                    if (Scheme.numGEq.apply2(closureEnv.pos, Integer.valueOf(closureEnv.fl)) != Boolean.FALSE) {
                        closureEnv.fc = Boolean.FALSE;
                    } else {
                        Object obj5 = closureEnv.format$Mnstring;
                        try {
                            CharSequence charSequence = (CharSequence) obj5;
                            Object obj6 = closureEnv.pos;
                            try {
                                closureEnv.fc = Char.make(strings.stringRef(charSequence, ((Number) obj6).intValue()));
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "string-ref", 2, obj6);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "string-ref", 1, obj5);
                        }
                    }
                    boolean x6 = closureEnv.lambda19isEndOfFormat();
                    if (x6) {
                        if (x6) {
                            bool = Boolean.TRUE;
                        } else {
                            bool = Boolean.FALSE;
                        }
                        return bool;
                    } else if (Scheme.isEqv.apply2(Lit18, closureEnv.fc) != Boolean.FALSE) {
                        closureEnv.lambda18mustAdvance();
                        Object tmp = closureEnv.fc;
                        Object x7 = Scheme.isEqv.apply2(tmp, Lit19);
                        if (x7 == Boolean.FALSE ? Scheme.isEqv.apply2(tmp, Lit20) != Boolean.FALSE : x7 != Boolean.FALSE) {
                            x5 = Scheme.applyToArgs.apply2(closureEnv.out, Lit21);
                        } else {
                            Object x8 = Scheme.isEqv.apply2(tmp, Lit22);
                            if (x8 == Boolean.FALSE ? Scheme.isEqv.apply2(tmp, Lit23) != Boolean.FALSE : x8 != Boolean.FALSE) {
                                x5 = Scheme.applyToArgs.apply2(closureEnv.out, Lit24);
                            } else {
                                Object x9 = Scheme.isEqv.apply2(tmp, Lit25);
                                if (x9 == Boolean.FALSE ? Scheme.isEqv.apply2(tmp, Lit26) != Boolean.FALSE : x9 != Boolean.FALSE) {
                                    x5 = Scheme.applyToArgs.apply2(closureEnv.out, Lit27);
                                } else if (Scheme.isEqv.apply2(tmp, Lit21) != Boolean.FALSE) {
                                    x5 = Boolean.TRUE;
                                } else {
                                    x5 = Scheme.applyToArgs.apply2(closureEnv.out, closureEnv.fc);
                                }
                            }
                        }
                        if (x5 == Boolean.FALSE) {
                            return x5;
                        }
                        obj4 = frame102.args;
                    } else if (Scheme.isEqv.apply2(Lit28, closureEnv.fc) != Boolean.FALSE) {
                        closureEnv.lambda18mustAdvance();
                        Boolean bool2 = Boolean.FALSE;
                        Boolean bool3 = Boolean.FALSE;
                        Boolean bool4 = Boolean.FALSE;
                        Boolean bool5 = Boolean.FALSE;
                        Boolean bool6 = Boolean.FALSE;
                        IntNum intNum2 = Lit1;
                        frame102.precision = Lit17;
                        frame102.width = intNum2;
                        frame102.leading$Mn0s = bool6;
                        frame102.alternate$Mnform = bool5;
                        frame102.blank = bool4;
                        frame102.signed = bool3;
                        frame102.left$Mnadjust = bool2;
                        frame102.pad = frame102.pad;
                        while (true) {
                            Object tmp2 = closureEnv.fc;
                            if (Scheme.isEqv.apply2(tmp2, Lit5) == Boolean.FALSE) {
                                if (Scheme.isEqv.apply2(tmp2, Lit6) == Boolean.FALSE) {
                                    if (Scheme.isEqv.apply2(tmp2, Lit29) == Boolean.FALSE) {
                                        if (Scheme.isEqv.apply2(tmp2, Lit8) == Boolean.FALSE) {
                                            if (Scheme.isEqv.apply2(tmp2, Lit9) == Boolean.FALSE) {
                                                break;
                                            }
                                            frame102.leading$Mn0s = Boolean.TRUE;
                                        } else {
                                            frame102.alternate$Mnform = Boolean.TRUE;
                                        }
                                    } else {
                                        frame102.blank = Boolean.TRUE;
                                    }
                                } else {
                                    frame102.signed = Boolean.TRUE;
                                }
                            } else {
                                frame102.left$Mnadjust = Boolean.TRUE;
                            }
                            closureEnv.lambda18mustAdvance();
                        }
                        if (frame102.left$Mnadjust != Boolean.FALSE) {
                            frame102.leading$Mn0s = Boolean.FALSE;
                        }
                        if (frame102.signed != Boolean.FALSE) {
                            frame102.blank = Boolean.FALSE;
                        }
                        frame102.width = frame102.lambda22readFormatNumber();
                        Object obj7 = frame102.width;
                        try {
                            if (numbers.isNegative(LangObjType.coerceRealNum(obj7))) {
                                frame102.left$Mnadjust = Boolean.TRUE;
                                frame102.width = AddOp.$Mn.apply1(frame102.width);
                            }
                            if (Scheme.isEqv.apply2(Lit11, closureEnv.fc) != Boolean.FALSE) {
                                closureEnv.lambda18mustAdvance();
                                frame102.precision = frame102.lambda22readFormatNumber();
                            }
                            Object tmp3 = closureEnv.fc;
                            Object x10 = Scheme.isEqv.apply2(tmp3, Lit30);
                            if (x10 == Boolean.FALSE) {
                                Object x11 = Scheme.isEqv.apply2(tmp3, Lit31);
                                if (x11 == Boolean.FALSE) {
                                    if (lists.isNull(frame102.args)) {
                                        Object obj8 = closureEnv.fc;
                                        try {
                                            if (lists.memv(unicode.charDowncase((Char) obj8), Lit33) != Boolean.FALSE) {
                                                misc.error$V(Lit34, new Object[]{"wrong number of arguments", Integer.valueOf(lists.length(closureEnv.args)), closureEnv.format$Mnstring});
                                            }
                                        } catch (ClassCastException e3) {
                                            throw new WrongType(e3, "char-downcase", 1, obj8);
                                        }
                                    }
                                    Object tmp4 = closureEnv.fc;
                                    x = Scheme.isEqv.apply2(tmp4, Lit35);
                                    if (x == Boolean.FALSE ? Scheme.isEqv.apply2(tmp4, Lit36) != Boolean.FALSE : x != Boolean.FALSE) {
                                        ApplyToArgs applyToArgs = Scheme.applyToArgs;
                                        Object obj9 = closureEnv.out;
                                        Object apply1 = lists.car.apply1(frame102.args);
                                        x4 = applyToArgs.apply2(obj9, strings.$make$string$(apply1 instanceof Object[] ? (Object[]) apply1 : new Object[]{apply1}));
                                        if (x4 == Boolean.FALSE) {
                                            return x4;
                                        }
                                        obj4 = lists.cdr.apply1(frame102.args);
                                    } else {
                                        Object x12 = Scheme.isEqv.apply2(tmp4, Lit37);
                                        if (x12 == Boolean.FALSE ? Scheme.isEqv.apply2(tmp4, Lit38) != Boolean.FALSE : x12 != Boolean.FALSE) {
                                            if (misc.isSymbol(lists.car.apply1(frame102.args))) {
                                                Object apply12 = lists.car.apply1(frame102.args);
                                                try {
                                                    s = misc.symbol$To$String((Symbol) apply12);
                                                } catch (ClassCastException e4) {
                                                    throw new WrongType(e4, "symbol->string", 1, apply12);
                                                }
                                            } else if (lists.car.apply1(frame102.args) == Boolean.FALSE) {
                                                s = "(NULL)";
                                            } else {
                                                s = lists.car.apply1(frame102.args);
                                            }
                                            Object obj10 = frame102.precision;
                                            try {
                                                boolean x13 = numbers.isNegative(LangObjType.coerceRealNum(obj10));
                                                if (!x13) {
                                                    try {
                                                    } catch (ClassCastException e5) {
                                                        throw new WrongType(e5, "string-length", 1, s);
                                                    }
                                                }
                                                try {
                                                    CharSequence charSequence2 = (CharSequence) s;
                                                    Object obj11 = frame102.precision;
                                                    try {
                                                        s = strings.substring(charSequence2, 0, ((Number) obj11).intValue());
                                                        try {
                                                            if (Scheme.numLEq.apply2(frame102.width, Integer.valueOf(strings.stringLength((CharSequence) s))) == Boolean.FALSE) {
                                                                if (frame102.left$Mnadjust != Boolean.FALSE) {
                                                                    try {
                                                                        Object apply2 = AddOp.$Mn.apply2(frame102.width, Integer.valueOf(strings.stringLength((CharSequence) s)));
                                                                        try {
                                                                            s = LList.list2(s, strings.makeString(((Number) apply2).intValue(), Lit29));
                                                                        } catch (ClassCastException e6) {
                                                                            throw new WrongType(e6, "make-string", 1, apply2);
                                                                        }
                                                                    } catch (ClassCastException e7) {
                                                                        throw new WrongType(e7, "string-length", 1, s);
                                                                    }
                                                                } else {
                                                                    try {
                                                                        Object apply22 = AddOp.$Mn.apply2(frame102.width, Integer.valueOf(strings.stringLength((CharSequence) s)));
                                                                        try {
                                                                            int intValue = ((Number) apply22).intValue();
                                                                            if (frame102.leading$Mn0s != Boolean.FALSE) {
                                                                                charR = Lit9;
                                                                            } else {
                                                                                charR = Lit29;
                                                                            }
                                                                            s = LList.list2(strings.makeString(intValue, charR), s);
                                                                        } catch (ClassCastException e8) {
                                                                            throw new WrongType(e8, "make-string", 1, apply22);
                                                                        }
                                                                    } catch (ClassCastException e9) {
                                                                        throw new WrongType(e9, "string-length", 1, s);
                                                                    }
                                                                }
                                                            }
                                                            Object x14 = closureEnv.lambda21out$St(s);
                                                            if (x14 == Boolean.FALSE) {
                                                                return x14;
                                                            }
                                                            obj4 = lists.cdr.apply1(frame102.args);
                                                        } catch (ClassCastException e10) {
                                                            throw new WrongType(e10, "string-length", 1, s);
                                                        }
                                                    } catch (ClassCastException e11) {
                                                        throw new WrongType(e11, "substring", 3, obj11);
                                                    }
                                                } catch (ClassCastException e12) {
                                                    throw new WrongType(e12, "substring", 1, s);
                                                }
                                            } catch (ClassCastException e13) {
                                                throw new WrongType(e13, "negative?", 1, obj10);
                                            }
                                        } else {
                                            Object x15 = Scheme.isEqv.apply2(tmp4, Lit39);
                                            if (x15 == Boolean.FALSE ? Scheme.isEqv.apply2(tmp4, Lit40) != Boolean.FALSE : x15 != Boolean.FALSE) {
                                                frame102.pr = frame102.precision;
                                                frame102.os = "";
                                                Object apply13 = lists.car.apply1(frame102.args);
                                                Boolean bool7 = frame102.alternate$Mnform != Boolean.FALSE ? Boolean.FALSE : Boolean.TRUE;
                                                Boolean bool8 = Boolean.FALSE;
                                                Object x16 = frame102.left$Mnadjust;
                                                if (x16 != Boolean.FALSE) {
                                                    Object obj12 = frame102.pr;
                                                    try {
                                                    } catch (ClassCastException e14) {
                                                        throw new WrongType(e14, "negative?", 1, obj12);
                                                    }
                                                }
                                                frame102.pr = Lit1;
                                                ModuleMethod moduleMethod = frame102.lambda$Fn13;
                                                genwrite.genericWrite(apply13, bool7, bool8, moduleMethod);
                                                Object x17 = frame102.left$Mnadjust;
                                                if (x17 != Boolean.FALSE) {
                                                    Object obj13 = frame102.precision;
                                                    try {
                                                    } catch (ClassCastException e15) {
                                                        throw new WrongType(e15, "negative?", 1, obj13);
                                                    }
                                                }
                                                if (Scheme.numGrt.apply2(frame102.width, frame102.pr) != Boolean.FALSE) {
                                                    ApplyToArgs applyToArgs2 = Scheme.applyToArgs;
                                                    Object obj14 = closureEnv.out;
                                                    Object apply23 = AddOp.$Mn.apply2(frame102.width, frame102.pr);
                                                    try {
                                                        applyToArgs2.apply2(obj14, strings.makeString(((Number) apply23).intValue(), Lit29));
                                                    } catch (ClassCastException e16) {
                                                        throw new WrongType(e16, "make-string", 1, apply23);
                                                    }
                                                }
                                                obj4 = lists.cdr.apply1(frame102.args);
                                            } else {
                                                Object x18 = Scheme.isEqv.apply2(tmp4, Lit12);
                                                if (x18 == Boolean.FALSE) {
                                                    Object x19 = Scheme.isEqv.apply2(tmp4, Lit41);
                                                    if (x19 == Boolean.FALSE) {
                                                        Object x20 = Scheme.isEqv.apply2(tmp4, Lit3);
                                                        if (x20 == Boolean.FALSE) {
                                                            Object x21 = Scheme.isEqv.apply2(tmp4, Lit42);
                                                            if (x21 == Boolean.FALSE) {
                                                                Object x22 = Scheme.isEqv.apply2(tmp4, Lit43);
                                                                if (x22 == Boolean.FALSE) {
                                                                    x2 = Scheme.isEqv.apply2(tmp4, Lit46);
                                                                    if (x2 != Boolean.FALSE ? Scheme.isEqv.apply2(tmp4, Lit47) != Boolean.FALSE : x2 != Boolean.FALSE) {
                                                                        x3 = closureEnv.lambda21out$St(frame102.lambda24integerConvert(lists.car.apply1(frame102.args), Lit48, Boolean.FALSE));
                                                                        if (x3 != Boolean.FALSE) {
                                                                            return x3;
                                                                        }
                                                                        obj4 = lists.cdr.apply1(frame102.args);
                                                                    } else if (Scheme.isEqv.apply2(tmp4, Lit49) != Boolean.FALSE) {
                                                                        Object x23 = closureEnv.lambda21out$St(frame102.lambda24integerConvert(lists.car.apply1(frame102.args), Lit50, stdio$Clhex$Mnupper$Mncase$Qu ? unicode.string$Mndowncase : Boolean.FALSE));
                                                                        if (x23 == Boolean.FALSE) {
                                                                            return x23;
                                                                        }
                                                                        obj4 = lists.cdr.apply1(frame102.args);
                                                                    } else if (Scheme.isEqv.apply2(tmp4, Lit51) != Boolean.FALSE) {
                                                                        Object apply14 = lists.car.apply1(frame102.args);
                                                                        IntNum intNum3 = Lit50;
                                                                        if (stdio$Clhex$Mnupper$Mncase$Qu) {
                                                                            obj = Boolean.FALSE;
                                                                        } else {
                                                                            obj = unicode.string$Mnupcase;
                                                                        }
                                                                        Object x24 = closureEnv.lambda21out$St(frame102.lambda24integerConvert(apply14, intNum3, obj));
                                                                        if (x24 == Boolean.FALSE) {
                                                                            return x24;
                                                                        }
                                                                        obj4 = lists.cdr.apply1(frame102.args);
                                                                    } else {
                                                                        Object x25 = Scheme.isEqv.apply2(tmp4, Lit52);
                                                                        if (x25 == Boolean.FALSE ? Scheme.isEqv.apply2(tmp4, Lit53) != Boolean.FALSE : x25 != Boolean.FALSE) {
                                                                            Object x26 = closureEnv.lambda21out$St(frame102.lambda24integerConvert(lists.car.apply1(frame102.args), Lit14, Boolean.FALSE));
                                                                            if (x26 == Boolean.FALSE) {
                                                                                return x26;
                                                                            }
                                                                            obj4 = lists.cdr.apply1(frame102.args);
                                                                        } else if (Scheme.isEqv.apply2(tmp4, Lit28) != Boolean.FALSE) {
                                                                            Object x27 = Scheme.applyToArgs.apply2(closureEnv.out, Lit28);
                                                                            if (x27 == Boolean.FALSE) {
                                                                                return x27;
                                                                            }
                                                                            obj4 = frame102.args;
                                                                        } else {
                                                                            Object x28 = Scheme.isEqv.apply2(tmp4, Lit25);
                                                                            if (x28 == Boolean.FALSE) {
                                                                                Object x29 = Scheme.isEqv.apply2(tmp4, Lit26);
                                                                                if (x29 == Boolean.FALSE) {
                                                                                    Object x30 = Scheme.isEqv.apply2(tmp4, Lit13);
                                                                                    if (x30 == Boolean.FALSE) {
                                                                                        Object x31 = Scheme.isEqv.apply2(tmp4, Lit54);
                                                                                        if (x31 == Boolean.FALSE) {
                                                                                            Object x32 = Scheme.isEqv.apply2(tmp4, Lit55);
                                                                                            if (x32 == Boolean.FALSE) {
                                                                                                Object x33 = Scheme.isEqv.apply2(tmp4, Lit56);
                                                                                                if (x33 == Boolean.FALSE) {
                                                                                                    Object x34 = Scheme.isEqv.apply2(tmp4, Lit57);
                                                                                                    if (x34 == Boolean.FALSE) {
                                                                                                        if (!closureEnv.lambda19isEndOfFormat()) {
                                                                                                            return closureEnv.lambda20incomplete();
                                                                                                        }
                                                                                                        Object x35 = Scheme.applyToArgs.apply2(closureEnv.out, Lit28);
                                                                                                        if (x35 == Boolean.FALSE) {
                                                                                                            return x35;
                                                                                                        }
                                                                                                        Object x36 = Scheme.applyToArgs.apply2(closureEnv.out, closureEnv.fc);
                                                                                                        if (x36 == Boolean.FALSE) {
                                                                                                            return x36;
                                                                                                        }
                                                                                                        Object x37 = Scheme.applyToArgs.apply2(closureEnv.out, Lit65);
                                                                                                        if (x37 == Boolean.FALSE) {
                                                                                                            return x37;
                                                                                                        }
                                                                                                        obj4 = frame102.args;
                                                                                                    } else if (!closureEnv.lambda19isEndOfFormat()) {
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                            Object num = lists.car.apply1(frame102.args);
                                                                            Object obj15 = closureEnv.fc;
                                                                            frame11 frame112 = new frame11();
                                                                            frame112.staticLink = frame102;
                                                                            frame112.fc = obj15;
                                                                            Object obj16 = frame102.precision;
                                                                            try {
                                                                                if (numbers.isNegative(LangObjType.coerceRealNum(obj16))) {
                                                                                    frame102.precision = Lit59;
                                                                                } else {
                                                                                    Object obj17 = frame102.precision;
                                                                                    try {
                                                                                        boolean x38 = numbers.isZero((Number) obj17);
                                                                                        if (x38) {
                                                                                            Object obj18 = frame112.fc;
                                                                                            try {
                                                                                            } catch (ClassCastException e17) {
                                                                                                throw new WrongType(e17, "char-ci=?", 1, obj18);
                                                                                            }
                                                                                        }
                                                                                        frame102.precision = Lit7;
                                                                                    } catch (ClassCastException e18) {
                                                                                        throw new WrongType(e18, "zero?", 1, obj17);
                                                                                    }
                                                                                }
                                                                                if (numbers.isNumber(num)) {
                                                                                    try {
                                                                                        str = numbers.number$To$String(numbers.exact$To$Inexact((Number) num));
                                                                                    } catch (ClassCastException e19) {
                                                                                        throw new WrongType(e19, "exact->inexact", 1, num);
                                                                                    }
                                                                                } else if (strings.isString(num)) {
                                                                                    str = num;
                                                                                } else if (misc.isSymbol(num)) {
                                                                                    try {
                                                                                        str = misc.symbol$To$String((Symbol) num);
                                                                                    } catch (ClassCastException e20) {
                                                                                        throw new WrongType(e20, "symbol->string", 1, num);
                                                                                    }
                                                                                } else {
                                                                                    str = "???";
                                                                                }
                                                                                frame112.format$Mnreal = frame112.format$Mnreal;
                                                                                Object x39 = stdio$ClParseFloat(str, frame112.lambda$Fn17);
                                                                                if (x39 == Boolean.FALSE) {
                                                                                    x39 = frame102.lambda23pad$V("???", new Object[0]);
                                                                                }
                                                                                Object x40 = closureEnv.lambda21out$St(x39);
                                                                                if (x40 == Boolean.FALSE) {
                                                                                    return x40;
                                                                                }
                                                                                obj4 = lists.cdr.apply1(frame102.args);
                                                                            } catch (ClassCastException e21) {
                                                                                throw new WrongType(e21, "negative?", 1, obj16);
                                                                            }
                                                                        }
                                                                    }
                                                                } else {
                                                                    x2 = Scheme.isEqv.apply2(tmp4, Lit46);
                                                                    if (x2 != Boolean.FALSE) {
                                                                    }
                                                                    x3 = closureEnv.lambda21out$St(frame102.lambda24integerConvert(lists.car.apply1(frame102.args), Lit48, Boolean.FALSE));
                                                                    if (x3 != Boolean.FALSE) {
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                Object x41 = closureEnv.lambda21out$St(frame102.lambda24integerConvert(lists.car.apply1(frame102.args), Lit45, Boolean.FALSE));
                                                if (x41 == Boolean.FALSE) {
                                                    return x41;
                                                }
                                                obj4 = lists.cdr.apply1(frame102.args);
                                            }
                                        }
                                    }
                                } else {
                                    if (lists.isNull(frame102.args)) {
                                    }
                                    Object tmp42 = closureEnv.fc;
                                    x = Scheme.isEqv.apply2(tmp42, Lit35);
                                    if (x == Boolean.FALSE) {
                                    }
                                    ApplyToArgs applyToArgs3 = Scheme.applyToArgs;
                                    Object obj92 = closureEnv.out;
                                    Object apply15 = lists.car.apply1(frame102.args);
                                    x4 = applyToArgs3.apply2(obj92, strings.$make$string$(apply15 instanceof Object[] ? (Object[]) apply15 : new Object[]{apply15}));
                                    if (x4 == Boolean.FALSE) {
                                    }
                                }
                            }
                            closureEnv.lambda18mustAdvance();
                            if (lists.isNull(frame102.args)) {
                            }
                            Object tmp422 = closureEnv.fc;
                            x = Scheme.isEqv.apply2(tmp422, Lit35);
                            if (x == Boolean.FALSE) {
                            }
                            ApplyToArgs applyToArgs32 = Scheme.applyToArgs;
                            Object obj922 = closureEnv.out;
                            Object apply152 = lists.car.apply1(frame102.args);
                            x4 = applyToArgs32.apply2(obj922, strings.$make$string$(apply152 instanceof Object[] ? (Object[]) apply152 : new Object[]{apply152}));
                            if (x4 == Boolean.FALSE) {
                            }
                        } catch (ClassCastException e22) {
                            throw new WrongType(e22, "negative?", 1, obj7);
                        }
                    } else {
                        Object x42 = Scheme.applyToArgs.apply2(closureEnv.out, closureEnv.fc);
                        if (x42 == Boolean.FALSE) {
                            return x42;
                        }
                        obj4 = frame102.args;
                    }
                }
            } catch (ClassCastException e23) {
                throw new WrongType(e23, "string-ref", 1, obj3);
            }
        } catch (ClassCastException e24) {
            throw new WrongType(e24, "string-length", 1, obj2);
        }
    }

    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 24:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 25:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 26:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 27:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            default:
                return super.matchN(moduleMethod, objArr, callContext);
        }
    }

    public static Object fprintf$V(Object port, Object format, Object[] argsArray) {
        frame12 frame122 = new frame12();
        frame122.port = port;
        LList args = LList.makeList(argsArray, 0);
        frame122.cnt = Lit1;
        Scheme.apply.apply4(stdio$Cliprintf, frame122.lambda$Fn18, format, args);
        return frame122.cnt;
    }

    public static Object printf$V(Object format, Object[] argsArray) {
        return Scheme.apply.apply4(fprintf, ports.current$Mnoutput$Mnport.apply0(), format, LList.makeList(argsArray, 0));
    }

    public static Object sprintf$V(Object str, Object format, Object[] argsArray) {
        Object error$V;
        frame13 frame132 = new frame13();
        frame132.str = str;
        LList args = LList.makeList(argsArray, 0);
        frame132.cnt = Lit1;
        if (strings.isString(frame132.str)) {
            error$V = frame132.str;
        } else if (numbers.isNumber(frame132.str)) {
            Object obj = frame132.str;
            try {
                error$V = strings.makeString(((Number) obj).intValue());
            } catch (ClassCastException e) {
                throw new WrongType(e, "make-string", 1, obj);
            }
        } else if (frame132.str == Boolean.FALSE) {
            error$V = strings.makeString(100);
        } else {
            error$V = misc.error$V(Lit68, new Object[]{"first argument not understood", frame132.str});
        }
        frame132.s = error$V;
        Object obj2 = frame132.s;
        try {
            frame132.end = Integer.valueOf(strings.stringLength((CharSequence) obj2));
            Scheme.apply.apply4(stdio$Cliprintf, frame132.lambda$Fn19, format, args);
            if (strings.isString(frame132.str)) {
                return frame132.cnt;
            }
            if (Scheme.isEqv.apply2(frame132.end, frame132.cnt) != Boolean.FALSE) {
                return frame132.s;
            }
            Object obj3 = frame132.s;
            try {
                CharSequence charSequence = (CharSequence) obj3;
                Object obj4 = frame132.cnt;
                try {
                    return strings.substring(charSequence, 0, ((Number) obj4).intValue());
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "substring", 3, obj4);
                }
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "substring", 1, obj3);
            }
        } catch (ClassCastException e4) {
            throw new WrongType(e4, "string-length", 1, obj2);
        }
    }

    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        switch (moduleMethod.selector) {
            case 24:
                Object obj = objArr[0];
                Object obj2 = objArr[1];
                int length = objArr.length - 2;
                Object[] objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stdio$ClIprintf$V(obj, obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 25:
                Object obj3 = objArr[0];
                Object obj4 = objArr[1];
                int length2 = objArr.length - 2;
                Object[] objArr3 = new Object[length2];
                while (true) {
                    length2--;
                    if (length2 < 0) {
                        return fprintf$V(obj3, obj4, objArr3);
                    }
                    objArr3[length2] = objArr[length2 + 2];
                }
            case 26:
                Object obj5 = objArr[0];
                int length3 = objArr.length - 1;
                Object[] objArr4 = new Object[length3];
                while (true) {
                    length3--;
                    if (length3 < 0) {
                        return printf$V(obj5, objArr4);
                    }
                    objArr4[length3] = objArr[length3 + 1];
                }
            case 27:
                Object obj6 = objArr[0];
                Object obj7 = objArr[1];
                int length4 = objArr.length - 2;
                Object[] objArr5 = new Object[length4];
                while (true) {
                    length4--;
                    if (length4 < 0) {
                        return sprintf$V(obj6, obj7, objArr5);
                    }
                    objArr5[length4] = objArr[length4 + 2];
                }
            default:
                return super.applyN(moduleMethod, objArr);
        }
    }
}
