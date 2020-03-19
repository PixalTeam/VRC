package gnu.kawa.slib;

import android.support.v4.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.Apply;
import gnu.kawa.functions.IsEqual;
import gnu.kawa.functions.NumberCompare;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Char;
import kawa.lib.characters;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.strings;
import kawa.standard.Scheme;
import kawa.standard.call_with_values;

/* compiled from: srfi37.scm */
public class srfi37 extends ModuleBody {
    public static final srfi37 $instance = new srfi37();
    static final IntNum Lit0 = IntNum.make(1);
    static final Char Lit1 = Char.make(45);
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("option-processor").readResolve());
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("args-fold").readResolve());
    static final Char Lit2 = Char.make(61);
    static final IntNum Lit3 = IntNum.make(3);
    static final IntNum Lit4 = IntNum.make(0);
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("option?").readResolve());
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("option").readResolve());
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("option-names").readResolve());
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("option-required-arg?").readResolve());
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("option-optional-arg?").readResolve());
    public static final ModuleMethod args$Mnfold;
    public static final ModuleMethod option;
    public static final ModuleMethod option$Mnnames;
    public static final ModuleMethod option$Mnoptional$Mnarg$Qu;
    public static final ModuleMethod option$Mnprocessor;
    public static final ModuleMethod option$Mnrequired$Mnarg$Qu;
    static final Class option$Mntype = option$Mntype.class;
    public static final ModuleMethod option$Qu;

    /* compiled from: srfi37.scm */
    public class frame extends ModuleBody {
        Object operand$Mnproc;
        Object options;
        Object unrecognized$Mnoption$Mnproc;

        public static Object lambda1find(Object l, Object $Qu) {
            if (lists.isNull(l)) {
                return Boolean.FALSE;
            }
            if (Scheme.applyToArgs.apply2($Qu, lists.car.apply1(l)) != Boolean.FALSE) {
                return lists.car.apply1(l);
            }
            return lambda1find(lists.cdr.apply1(l), $Qu);
        }

        public Object lambda2findOption(Object name) {
            frame0 frame0 = new frame0();
            frame0.staticLink = this;
            frame0.name = name;
            return lambda1find(this.options, frame0.lambda$Fn1);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:27:0x008a, code lost:
            if (r1 != java.lang.Boolean.FALSE) goto L_0x008c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x00b4, code lost:
            if (gnu.kawa.slib.srfi37.isOptionOptionalArg((gnu.kawa.slib.option$Mntype) r2) == java.lang.Boolean.FALSE) goto L_0x00b6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x00b6, code lost:
            r2 = r5.option;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x00ba, code lost:
            r1 = gnu.kawa.slib.srfi37.isOptionRequiredArg((gnu.kawa.slib.option$Mntype) r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x00c0, code lost:
            if (r1 == java.lang.Boolean.FALSE) goto L_0x00d7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x00c8, code lost:
            if (kawa.lib.lists.isPair(r5.args) == false) goto L_0x00db;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x00d4, code lost:
            if (r1 != false) goto L_0x008c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x00d9, code lost:
            if (r1 != java.lang.Boolean.FALSE) goto L_0x00ca;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:68:0x0126, code lost:
            r3 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:70:0x012e, code lost:
            throw new gnu.mapping.WrongType(r3, "option-required-arg?", 0, r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:73:?, code lost:
            return kawa.standard.call_with_values.callWithValues(r5.lambda$Fn5, r5.lambda$Fn6);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:74:?, code lost:
            return kawa.standard.call_with_values.callWithValues(r5.lambda$Fn7, r5.lambda$Fn8);
         */
        public Object lambda3scanShortOptions(Object index, Object shorts, Object args, Object seeds) {
            frame1 frame1 = new frame1();
            frame1.staticLink = this;
            frame1.index = index;
            frame1.shorts = shorts;
            frame1.args = args;
            frame1.seeds = seeds;
            NumberCompare numberCompare = Scheme.numEqu;
            Object obj = frame1.index;
            Object obj2 = frame1.shorts;
            try {
                if (numberCompare.apply2(obj, Integer.valueOf(strings.stringLength((CharSequence) obj2))) != Boolean.FALSE) {
                    return lambda5scanArgs(frame1.args, frame1.seeds);
                }
                Object obj3 = frame1.shorts;
                try {
                    CharSequence charSequence = (CharSequence) obj3;
                    Object obj4 = frame1.index;
                    try {
                        frame1.name = strings.stringRef(charSequence, ((Number) obj4).intValue());
                        Object x = lambda2findOption(Char.make(frame1.name));
                        if (x == Boolean.FALSE) {
                            x = srfi37.option(LList.list1(Char.make(frame1.name)), Boolean.FALSE, Boolean.FALSE, this.unrecognized$Mnoption$Mnproc);
                        }
                        frame1.option = x;
                        NumberCompare numberCompare2 = Scheme.numLss;
                        Object apply2 = AddOp.$Pl.apply2(frame1.index, srfi37.Lit0);
                        Object obj5 = frame1.shorts;
                        try {
                            Object apply22 = numberCompare2.apply2(apply2, Integer.valueOf(strings.stringLength((CharSequence) obj5)));
                            try {
                                boolean x2 = ((Boolean) apply22).booleanValue();
                                if (x2) {
                                    Object obj6 = frame1.option;
                                    try {
                                        Object x3 = srfi37.isOptionRequiredArg((option$Mntype) obj6);
                                        if (x3 == Boolean.FALSE) {
                                            Object obj7 = frame1.option;
                                            try {
                                            } catch (ClassCastException e) {
                                                throw new WrongType(e, "option-optional-arg?", 0, obj7);
                                            }
                                        }
                                    } catch (ClassCastException e2) {
                                        throw new WrongType(e2, "option-required-arg?", 0, obj6);
                                    }
                                }
                                return call_with_values.callWithValues(frame1.lambda$Fn3, frame1.lambda$Fn4);
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "x", -2, apply22);
                            }
                        } catch (ClassCastException e4) {
                            throw new WrongType(e4, "string-length", 1, obj5);
                        }
                    } catch (ClassCastException e5) {
                        throw new WrongType(e5, "string-ref", 2, obj4);
                    }
                } catch (ClassCastException e6) {
                    throw new WrongType(e6, "string-ref", 1, obj3);
                }
            } catch (ClassCastException e7) {
                throw new WrongType(e7, "string-length", 1, obj2);
            }
        }

        public Object lambda4scanOperands(Object operands, Object seeds) {
            frame2 frame2 = new frame2();
            frame2.staticLink = this;
            frame2.operands = operands;
            frame2.seeds = seeds;
            if (lists.isNull(frame2.operands)) {
                return Scheme.apply.apply2(misc.values, frame2.seeds);
            }
            return call_with_values.callWithValues(frame2.lambda$Fn9, frame2.lambda$Fn10);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:100:0x0181, code lost:
            r3 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:101:0x0182, code lost:
            if (r3 == false) goto L_0x01d8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:102:0x0184, code lost:
            r5 = gnu.kawa.slib.srfi37.Lit1;
            r4 = r8.arg;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:106:0x0196, code lost:
            if (kawa.lib.characters.isChar$Eq(r5, gnu.text.Char.make(kawa.lib.strings.stringRef((java.lang.CharSequence) r4, 0))) == false) goto L_0x01da;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:107:0x0198, code lost:
            r4 = r8.arg;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:109:?, code lost:
            r4 = (java.lang.CharSequence) r4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:110:0x019c, code lost:
            r5 = r8.arg;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:114:0x01b4, code lost:
            if (r3 != false) goto L_0x0137;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:119:0x01d6, code lost:
            r3 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:120:0x01d8, code lost:
            if (r3 != false) goto L_0x0198;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:161:0x0259, code lost:
            r5 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:163:0x0261, code lost:
            throw new gnu.mapping.WrongType(r5, "string-length", 1, r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:164:0x0262, code lost:
            r5 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:166:0x026a, code lost:
            throw new gnu.mapping.WrongType(r5, "string-ref", 1, r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:167:0x026b, code lost:
            r5 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:169:0x0273, code lost:
            throw new gnu.mapping.WrongType(r5, "substring", 1, r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:170:0x0274, code lost:
            r4 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:172:0x027c, code lost:
            throw new gnu.mapping.WrongType(r4, "string-length", 1, r5);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:179:?, code lost:
            return lambda3scanShortOptions(gnu.kawa.slib.srfi37.Lit4, kawa.lib.strings.substring(r4, 1, kawa.lib.strings.stringLength((java.lang.CharSequence) r5)), r8.args, r8.seeds);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:181:?, code lost:
            return kawa.standard.call_with_values.callWithValues(r8.lambda$Fn23, r8.lambda$Fn24);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:76:0x0135, code lost:
            if (kawa.lib.characters.isChar$Eq(r5, gnu.text.Char.make(kawa.lib.strings.stringRef((java.lang.CharSequence) r4, 1))) != false) goto L_0x0137;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:94:0x0175, code lost:
            if (r3 == false) goto L_0x0177;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:95:0x0177, code lost:
            r4 = r8.arg;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:99:0x017f, code lost:
            if (kawa.lib.strings.stringLength((java.lang.CharSequence) r4) <= 1) goto L_0x01d6;
         */
        public Object lambda5scanArgs(Object args, Object seeds) {
            boolean x;
            Object obj;
            boolean x2;
            frame3 frame3 = new frame3();
            frame3.staticLink = this;
            frame3.seeds = seeds;
            if (lists.isNull(args)) {
                return Scheme.apply.apply2(misc.values, frame3.seeds);
            }
            Object apply1 = lists.car.apply1(args);
            frame3.args = lists.cdr.apply1(args);
            frame3.arg = apply1;
            if (strings.isString$Eq("--", frame3.arg)) {
                return lambda4scanOperands(frame3.args, frame3.seeds);
            }
            Object obj2 = frame3.arg;
            try {
                if (strings.stringLength((CharSequence) obj2) > 4) {
                    x = true;
                } else {
                    x = false;
                }
                if (x) {
                    Char charR = srfi37.Lit1;
                    Object obj3 = frame3.arg;
                    try {
                        boolean x3 = characters.isChar$Eq(charR, Char.make(strings.stringRef((CharSequence) obj3, 0)));
                        if (x3) {
                            Char charR2 = srfi37.Lit1;
                            Object obj4 = frame3.arg;
                            try {
                                boolean x4 = characters.isChar$Eq(charR2, Char.make(strings.stringRef((CharSequence) obj4, 1)));
                                if (x4) {
                                    Char charR3 = srfi37.Lit2;
                                    Object obj5 = frame3.arg;
                                    try {
                                        boolean x5 = ((characters.isChar$Eq(charR3, Char.make(strings.stringRef((CharSequence) obj5, 2))) ? 1 : 0) + true) & true;
                                        if (x5) {
                                            obj = srfi37.Lit3;
                                            while (true) {
                                                NumberCompare numberCompare = Scheme.numEqu;
                                                Object obj6 = frame3.arg;
                                                try {
                                                    if (numberCompare.apply2(obj, Integer.valueOf(strings.stringLength((CharSequence) obj6))) != Boolean.FALSE) {
                                                        obj = Boolean.FALSE;
                                                        break;
                                                    }
                                                    Char charR4 = srfi37.Lit2;
                                                    Object obj7 = frame3.arg;
                                                    try {
                                                        try {
                                                            if (characters.isChar$Eq(charR4, Char.make(strings.stringRef((CharSequence) obj7, ((Number) obj).intValue())))) {
                                                                break;
                                                            }
                                                            obj = AddOp.$Pl.apply2(srfi37.Lit0, obj);
                                                        } catch (ClassCastException e) {
                                                            throw new WrongType(e, "string-ref", 2, obj);
                                                        }
                                                    } catch (ClassCastException e2) {
                                                        throw new WrongType(e2, "string-ref", 1, obj7);
                                                    }
                                                } catch (ClassCastException e3) {
                                                    throw new WrongType(e3, "string-length", 1, obj6);
                                                }
                                            }
                                        } else {
                                            obj = x5 ? Boolean.TRUE : Boolean.FALSE;
                                        }
                                    } catch (ClassCastException e4) {
                                        throw new WrongType(e4, "string-ref", 1, obj5);
                                    }
                                } else {
                                    obj = x4 ? Boolean.TRUE : Boolean.FALSE;
                                }
                            } catch (ClassCastException e5) {
                                throw new WrongType(e5, "string-ref", 1, obj4);
                            }
                        } else {
                            obj = x3 ? Boolean.TRUE : Boolean.FALSE;
                        }
                    } catch (ClassCastException e6) {
                        throw new WrongType(e6, "string-ref", 1, obj3);
                    }
                } else {
                    obj = x ? Boolean.TRUE : Boolean.FALSE;
                }
                frame3.temp = obj;
                if (frame3.temp != Boolean.FALSE) {
                    return call_with_values.callWithValues(frame3.lambda$Fn11, frame3.lambda$Fn12);
                }
                Object obj8 = frame3.arg;
                try {
                    if (strings.stringLength((CharSequence) obj8) > 3) {
                        x2 = true;
                    } else {
                        x2 = false;
                    }
                    if (x2) {
                        Char charR5 = srfi37.Lit1;
                        Object obj9 = frame3.arg;
                        try {
                            boolean x6 = characters.isChar$Eq(charR5, Char.make(strings.stringRef((CharSequence) obj9, 0)));
                            if (x6) {
                                Char charR6 = srfi37.Lit1;
                                Object obj10 = frame3.arg;
                                try {
                                } catch (ClassCastException e7) {
                                    throw new WrongType(e7, "string-ref", 1, obj10);
                                }
                            }
                        } catch (ClassCastException e8) {
                            throw new WrongType(e8, "string-ref", 1, obj9);
                        }
                    }
                    Object obj11 = frame3.arg;
                    try {
                        CharSequence charSequence = (CharSequence) obj11;
                        Object obj12 = frame3.arg;
                        try {
                            frame3.name = strings.substring(charSequence, 2, strings.stringLength((CharSequence) obj12));
                            Object x7 = lambda2findOption(frame3.name);
                            if (x7 == Boolean.FALSE) {
                                x7 = srfi37.option(LList.list1(frame3.name), Boolean.FALSE, Boolean.FALSE, this.unrecognized$Mnoption$Mnproc);
                            }
                            frame3.option = x7;
                            Object obj13 = frame3.option;
                            try {
                                Object x8 = srfi37.isOptionRequiredArg((option$Mntype) obj13);
                                if (x8 == Boolean.FALSE ? x8 != Boolean.FALSE : lists.isPair(frame3.args)) {
                                    return call_with_values.callWithValues(frame3.lambda$Fn19, frame3.lambda$Fn20);
                                }
                                return call_with_values.callWithValues(frame3.lambda$Fn21, frame3.lambda$Fn22);
                            } catch (ClassCastException e9) {
                                throw new WrongType(e9, "option-required-arg?", 0, obj13);
                            }
                        } catch (ClassCastException e10) {
                            throw new WrongType(e10, "string-length", 1, obj12);
                        }
                    } catch (ClassCastException e11) {
                        throw new WrongType(e11, "substring", 1, obj11);
                    }
                } catch (ClassCastException e12) {
                    throw new WrongType(e12, "string-length", 1, obj8);
                }
            } catch (ClassCastException e13) {
                throw new WrongType(e13, "string-length", 1, obj2);
            }
        }
    }

    /* compiled from: srfi37.scm */
    public class frame0 extends ModuleBody {
        final ModuleMethod lambda$Fn1;
        final ModuleMethod lambda$Fn2;
        Object name;
        frame staticLink;

        public frame0() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 1, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi37.scm:75");
            this.lambda$Fn2 = moduleMethod;
            ModuleMethod moduleMethod2 = new ModuleMethod(this, 2, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi37.scm:72");
            this.lambda$Fn1 = moduleMethod2;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            switch (moduleMethod.selector) {
                case 1:
                    return lambda7(obj) ? Boolean.TRUE : Boolean.FALSE;
                case 2:
                    return lambda6(obj);
                default:
                    return super.apply1(moduleMethod, obj);
            }
        }

        /* access modifiers changed from: 0000 */
        public Object lambda6(Object option) {
            try {
                return frame.lambda1find(srfi37.optionNames((option$Mntype) option), this.lambda$Fn2);
            } catch (ClassCastException e) {
                throw new WrongType(e, "option-names", 0, option);
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean lambda7(Object test$Mnname) {
            return IsEqual.apply(this.name, test$Mnname);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 1:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 2:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                default:
                    return super.match1(moduleMethod, obj, callContext);
            }
        }
    }

    /* compiled from: srfi37.scm */
    public class frame1 extends ModuleBody {
        Object args;
        Object index;
        final ModuleMethod lambda$Fn3 = new ModuleMethod(this, 3, null, 0);
        final ModuleMethod lambda$Fn4 = new ModuleMethod(this, 4, null, -4096);
        final ModuleMethod lambda$Fn5 = new ModuleMethod(this, 5, null, 0);
        final ModuleMethod lambda$Fn6 = new ModuleMethod(this, 6, null, -4096);
        final ModuleMethod lambda$Fn7 = new ModuleMethod(this, 7, null, 0);
        final ModuleMethod lambda$Fn8 = new ModuleMethod(this, 8, null, -4096);
        char name;
        Object option;
        Object seeds;
        Object shorts;
        frame staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            switch (moduleMethod.selector) {
                case 3:
                    return lambda8();
                case 5:
                    return lambda10();
                case 7:
                    return lambda12();
                default:
                    return super.apply0(moduleMethod);
            }
        }

        public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
            switch (moduleMethod.selector) {
                case 4:
                    return lambda9$V(objArr);
                case 6:
                    return lambda11$V(objArr);
                case 8:
                    return lambda13$V(objArr);
                default:
                    return super.applyN(moduleMethod, objArr);
            }
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 3:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 5:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 7:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                default:
                    return super.match0(moduleMethod, callContext);
            }
        }

        public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 4:
                    callContext.values = objArr;
                    callContext.proc = moduleMethod;
                    callContext.pc = 5;
                    return 0;
                case 6:
                    callContext.values = objArr;
                    callContext.proc = moduleMethod;
                    callContext.pc = 5;
                    return 0;
                case 8:
                    callContext.values = objArr;
                    callContext.proc = moduleMethod;
                    callContext.pc = 5;
                    return 0;
                default:
                    return super.matchN(moduleMethod, objArr, callContext);
            }
        }

        /* access modifiers changed from: 0000 */
        public Object lambda9$V(Object[] argsArray) {
            return this.staticLink.lambda5scanArgs(this.args, LList.makeList(argsArray, 0));
        }

        /* access modifiers changed from: 0000 */
        public Object lambda8() {
            Apply apply = Scheme.apply;
            Object[] objArr = new Object[5];
            Object obj = this.option;
            try {
                objArr[0] = srfi37.optionProcessor((option$Mntype) obj);
                objArr[1] = this.option;
                objArr[2] = Char.make(this.name);
                Object obj2 = this.shorts;
                try {
                    CharSequence charSequence = (CharSequence) obj2;
                    Object apply2 = AddOp.$Pl.apply2(this.index, srfi37.Lit0);
                    try {
                        int intValue = ((Number) apply2).intValue();
                        Object obj3 = this.shorts;
                        try {
                            objArr[3] = strings.substring(charSequence, intValue, strings.stringLength((CharSequence) obj3));
                            objArr[4] = this.seeds;
                            return apply.applyN(objArr);
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-length", 1, obj3);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "substring", 2, apply2);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "substring", 1, obj2);
                }
            } catch (ClassCastException e4) {
                throw new WrongType(e4, "option-processor", 0, obj);
            }
        }

        /* access modifiers changed from: 0000 */
        public Object lambda11$V(Object[] argsArray) {
            return this.staticLink.lambda5scanArgs(lists.cdr.apply1(this.args), LList.makeList(argsArray, 0));
        }

        /* access modifiers changed from: 0000 */
        public Object lambda10() {
            Apply apply = Scheme.apply;
            Object[] objArr = new Object[5];
            Object obj = this.option;
            try {
                objArr[0] = srfi37.optionProcessor((option$Mntype) obj);
                objArr[1] = this.option;
                objArr[2] = Char.make(this.name);
                objArr[3] = lists.car.apply1(this.args);
                objArr[4] = this.seeds;
                return apply.applyN(objArr);
            } catch (ClassCastException e) {
                throw new WrongType(e, "option-processor", 0, obj);
            }
        }

        /* access modifiers changed from: 0000 */
        public Object lambda13$V(Object[] argsArray) {
            return this.staticLink.lambda3scanShortOptions(AddOp.$Pl.apply2(this.index, srfi37.Lit0), this.shorts, this.args, LList.makeList(argsArray, 0));
        }

        /* access modifiers changed from: 0000 */
        public Object lambda12() {
            Apply apply = Scheme.apply;
            Object[] objArr = new Object[5];
            Object obj = this.option;
            try {
                objArr[0] = srfi37.optionProcessor((option$Mntype) obj);
                objArr[1] = this.option;
                objArr[2] = Char.make(this.name);
                objArr[3] = Boolean.FALSE;
                objArr[4] = this.seeds;
                return apply.applyN(objArr);
            } catch (ClassCastException e) {
                throw new WrongType(e, "option-processor", 0, obj);
            }
        }
    }

    /* compiled from: srfi37.scm */
    public class frame2 extends ModuleBody {
        final ModuleMethod lambda$Fn10 = new ModuleMethod(this, 10, null, -4096);
        final ModuleMethod lambda$Fn9 = new ModuleMethod(this, 9, null, 0);
        Object operands;
        Object seeds;
        frame staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 9 ? lambda14() : super.apply0(moduleMethod);
        }

        public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
            return moduleMethod.selector == 10 ? lambda15$V(objArr) : super.applyN(moduleMethod, objArr);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 9) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
            if (moduleMethod.selector != 10) {
                return super.matchN(moduleMethod, objArr, callContext);
            }
            callContext.values = objArr;
            callContext.proc = moduleMethod;
            callContext.pc = 5;
            return 0;
        }

        /* access modifiers changed from: 0000 */
        public Object lambda14() {
            return Scheme.apply.apply3(this.staticLink.operand$Mnproc, lists.car.apply1(this.operands), this.seeds);
        }

        /* access modifiers changed from: 0000 */
        public Object lambda15$V(Object[] argsArray) {
            return this.staticLink.lambda4scanOperands(lists.cdr.apply1(this.operands), LList.makeList(argsArray, 0));
        }
    }

    /* compiled from: srfi37.scm */
    public class frame3 extends ModuleBody {
        Object arg;
        Object args;
        final ModuleMethod lambda$Fn11 = new ModuleMethod(this, 17, null, 0);
        final ModuleMethod lambda$Fn12 = new ModuleMethod(this, 18, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        final ModuleMethod lambda$Fn19 = new ModuleMethod(this, 19, null, 0);
        final ModuleMethod lambda$Fn20 = new ModuleMethod(this, 20, null, -4096);
        final ModuleMethod lambda$Fn21 = new ModuleMethod(this, 21, null, 0);
        final ModuleMethod lambda$Fn22 = new ModuleMethod(this, 22, null, -4096);
        final ModuleMethod lambda$Fn23 = new ModuleMethod(this, 23, null, 0);
        final ModuleMethod lambda$Fn24 = new ModuleMethod(this, 24, null, -4096);
        CharSequence name;
        Object option;
        Object seeds;
        frame staticLink;
        Object temp;

        public Object apply0(ModuleMethod moduleMethod) {
            switch (moduleMethod.selector) {
                case 17:
                    return lambda16();
                case 19:
                    return lambda24();
                case 21:
                    return lambda26();
                case 23:
                    return lambda28();
                default:
                    return super.apply0(moduleMethod);
            }
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 18 ? lambda17(obj) : super.apply1(moduleMethod, obj);
        }

        public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
            switch (moduleMethod.selector) {
                case 20:
                    return lambda25$V(objArr);
                case 22:
                    return lambda27$V(objArr);
                case 24:
                    return lambda29$V(objArr);
                default:
                    return super.applyN(moduleMethod, objArr);
            }
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 17:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 19:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 21:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 23:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                default:
                    return super.match0(moduleMethod, callContext);
            }
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 18) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }

        public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 20:
                    callContext.values = objArr;
                    callContext.proc = moduleMethod;
                    callContext.pc = 5;
                    return 0;
                case 22:
                    callContext.values = objArr;
                    callContext.proc = moduleMethod;
                    callContext.pc = 5;
                    return 0;
                case 24:
                    callContext.values = objArr;
                    callContext.proc = moduleMethod;
                    callContext.pc = 5;
                    return 0;
                default:
                    return super.matchN(moduleMethod, objArr, callContext);
            }
        }

        /* access modifiers changed from: 0000 */
        public Object lambda17(Object x) {
            frame4 frame4 = new frame4();
            frame4.staticLink = this;
            frame4.x = x;
            return call_with_values.callWithValues(frame4.lambda$Fn13, frame4.lambda$Fn14);
        }

        /* access modifiers changed from: 0000 */
        public CharSequence lambda16() {
            Object obj = this.arg;
            try {
                CharSequence charSequence = (CharSequence) obj;
                Object obj2 = this.temp;
                try {
                    return strings.substring(charSequence, 2, ((Number) obj2).intValue());
                } catch (ClassCastException e) {
                    throw new WrongType(e, "substring", 3, obj2);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "substring", 1, obj);
            }
        }

        /* access modifiers changed from: 0000 */
        public Object lambda25$V(Object[] argsArray) {
            return this.staticLink.lambda5scanArgs(lists.cdr.apply1(this.args), LList.makeList(argsArray, 0));
        }

        /* access modifiers changed from: 0000 */
        public Object lambda24() {
            Apply apply = Scheme.apply;
            Object[] objArr = new Object[5];
            Object obj = this.option;
            try {
                objArr[0] = srfi37.optionProcessor((option$Mntype) obj);
                objArr[1] = this.option;
                objArr[2] = this.name;
                objArr[3] = lists.car.apply1(this.args);
                objArr[4] = this.seeds;
                return apply.applyN(objArr);
            } catch (ClassCastException e) {
                throw new WrongType(e, "option-processor", 0, obj);
            }
        }

        /* access modifiers changed from: 0000 */
        public Object lambda27$V(Object[] argsArray) {
            return this.staticLink.lambda5scanArgs(this.args, LList.makeList(argsArray, 0));
        }

        /* access modifiers changed from: 0000 */
        public Object lambda26() {
            Apply apply = Scheme.apply;
            Object[] objArr = new Object[5];
            Object obj = this.option;
            try {
                objArr[0] = srfi37.optionProcessor((option$Mntype) obj);
                objArr[1] = this.option;
                objArr[2] = this.name;
                objArr[3] = Boolean.FALSE;
                objArr[4] = this.seeds;
                return apply.applyN(objArr);
            } catch (ClassCastException e) {
                throw new WrongType(e, "option-processor", 0, obj);
            }
        }

        /* access modifiers changed from: 0000 */
        public Object lambda28() {
            return Scheme.apply.apply3(this.staticLink.operand$Mnproc, this.arg, this.seeds);
        }

        /* access modifiers changed from: 0000 */
        public Object lambda29$V(Object[] argsArray) {
            return this.staticLink.lambda5scanArgs(this.args, LList.makeList(argsArray, 0));
        }
    }

    /* compiled from: srfi37.scm */
    public class frame4 extends ModuleBody {
        final ModuleMethod lambda$Fn13 = new ModuleMethod(this, 15, null, 0);
        final ModuleMethod lambda$Fn14 = new ModuleMethod(this, 16, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        frame3 staticLink;
        Object x;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 15 ? lambda18() : super.apply0(moduleMethod);
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 16 ? lambda19(obj) : super.apply1(moduleMethod, obj);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 15) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 16) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }

        /* access modifiers changed from: 0000 */
        public Object lambda19(Object x2) {
            frame5 frame5 = new frame5();
            frame5.staticLink = this;
            frame5.x = x2;
            return call_with_values.callWithValues(frame5.lambda$Fn15, frame5.lambda$Fn16);
        }

        /* access modifiers changed from: 0000 */
        public CharSequence lambda18() {
            Object obj = this.staticLink.arg;
            try {
                CharSequence charSequence = (CharSequence) obj;
                Object apply2 = AddOp.$Pl.apply2(this.staticLink.temp, srfi37.Lit0);
                try {
                    int intValue = ((Number) apply2).intValue();
                    Object obj2 = this.staticLink.arg;
                    try {
                        return strings.substring(charSequence, intValue, strings.stringLength((CharSequence) obj2));
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "string-length", 1, obj2);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "substring", 2, apply2);
                }
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "substring", 1, obj);
            }
        }
    }

    /* compiled from: srfi37.scm */
    public class frame5 extends ModuleBody {
        final ModuleMethod lambda$Fn15 = new ModuleMethod(this, 13, null, 0);
        final ModuleMethod lambda$Fn16 = new ModuleMethod(this, 14, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        frame4 staticLink;
        Object x;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 13 ? lambda20() : super.apply0(moduleMethod);
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 14 ? lambda21(obj) : super.apply1(moduleMethod, obj);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 13) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 14) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }

        /* access modifiers changed from: 0000 */
        public Object lambda21(Object x2) {
            frame6 frame6 = new frame6();
            frame6.staticLink = this;
            frame6.x = x2;
            return call_with_values.callWithValues(frame6.lambda$Fn17, frame6.lambda$Fn18);
        }

        /* access modifiers changed from: 0000 */
        public Object lambda20() {
            Object x2 = this.staticLink.staticLink.staticLink.lambda2findOption(this.staticLink.x);
            return x2 != Boolean.FALSE ? x2 : srfi37.option(LList.list1(this.staticLink.x), Boolean.TRUE, Boolean.FALSE, this.staticLink.staticLink.staticLink.unrecognized$Mnoption$Mnproc);
        }
    }

    /* compiled from: srfi37.scm */
    public class frame6 extends ModuleBody {
        final ModuleMethod lambda$Fn17 = new ModuleMethod(this, 11, null, 0);
        final ModuleMethod lambda$Fn18 = new ModuleMethod(this, 12, null, -4096);
        frame5 staticLink;
        Object x;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 11 ? lambda22() : super.apply0(moduleMethod);
        }

        public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
            return moduleMethod.selector == 12 ? lambda23$V(objArr) : super.applyN(moduleMethod, objArr);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 11) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
            if (moduleMethod.selector != 12) {
                return super.matchN(moduleMethod, objArr, callContext);
            }
            callContext.values = objArr;
            callContext.proc = moduleMethod;
            callContext.pc = 5;
            return 0;
        }

        /* access modifiers changed from: 0000 */
        public Object lambda23$V(Object[] argsArray) {
            return this.staticLink.staticLink.staticLink.staticLink.lambda5scanArgs(this.staticLink.staticLink.staticLink.args, LList.makeList(argsArray, 0));
        }

        /* access modifiers changed from: 0000 */
        public Object lambda22() {
            Apply apply = Scheme.apply;
            Object[] objArr = new Object[5];
            Object obj = this.x;
            try {
                objArr[0] = srfi37.optionProcessor((option$Mntype) obj);
                objArr[1] = this.x;
                objArr[2] = this.staticLink.staticLink.x;
                objArr[3] = this.staticLink.x;
                objArr[4] = this.staticLink.staticLink.staticLink.seeds;
                return apply.applyN(objArr);
            } catch (ClassCastException e) {
                throw new WrongType(e, "option-processor", 0, obj);
            }
        }
    }

    static {
        srfi37 srfi37 = $instance;
        option$Qu = new ModuleMethod(srfi37, 25, Lit5, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        option = new ModuleMethod(srfi37, 26, Lit6, 16388);
        option$Mnnames = new ModuleMethod(srfi37, 27, Lit7, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        option$Mnrequired$Mnarg$Qu = new ModuleMethod(srfi37, 28, Lit8, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        option$Mnoptional$Mnarg$Qu = new ModuleMethod(srfi37, 29, Lit9, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        option$Mnprocessor = new ModuleMethod(srfi37, 30, Lit10, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        args$Mnfold = new ModuleMethod(srfi37, 31, Lit11, -4092);
        $instance.run();
    }

    public srfi37() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer consumer = $ctx.consumer;
    }

    public static option$Mntype option(Object names, Object required$Mnarg$Qu, Object optional$Mnarg$Qu, Object processor) {
        option$Mntype tmp = new option$Mntype();
        tmp.names = names;
        tmp.required$Mnarg$Qu = required$Mnarg$Qu;
        tmp.optional$Mnarg$Qu = optional$Mnarg$Qu;
        tmp.processor = processor;
        return tmp;
    }

    public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
        return moduleMethod.selector == 26 ? option(obj, obj2, obj3, obj4) : super.apply4(moduleMethod, obj, obj2, obj3, obj4);
    }

    public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
        if (moduleMethod.selector != 26) {
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

    public static boolean isOption(Object obj) {
        return obj instanceof option$Mntype;
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 25:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 27:
                if (!(obj instanceof option$Mntype)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 28:
                if (!(obj instanceof option$Mntype)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 29:
                if (!(obj instanceof option$Mntype)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 30:
                if (!(obj instanceof option$Mntype)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static Object optionNames(option$Mntype obj) {
        return obj.names;
    }

    public static Object isOptionRequiredArg(option$Mntype obj) {
        return obj.required$Mnarg$Qu;
    }

    public static Object isOptionOptionalArg(option$Mntype obj) {
        return obj.optional$Mnarg$Qu;
    }

    public static Object optionProcessor(option$Mntype obj) {
        return obj.processor;
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 25:
                return isOption(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 27:
                try {
                    return optionNames((option$Mntype) obj);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "option-names", 1, obj);
                }
            case 28:
                try {
                    return isOptionRequiredArg((option$Mntype) obj);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "option-required-arg?", 1, obj);
                }
            case 29:
                try {
                    return isOptionOptionalArg((option$Mntype) obj);
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "option-optional-arg?", 1, obj);
                }
            case 30:
                try {
                    return optionProcessor((option$Mntype) obj);
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "option-processor", 1, obj);
                }
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    public static Object argsFold$V(Object args, Object options, Object unrecognizedOptionProc, Object operandProc, Object[] argsArray) {
        frame frame7 = new frame();
        frame7.options = options;
        frame7.unrecognized$Mnoption$Mnproc = unrecognizedOptionProc;
        frame7.operand$Mnproc = operandProc;
        return frame7.lambda5scanArgs(args, LList.makeList(argsArray, 0));
    }

    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        if (moduleMethod.selector != 31) {
            return super.applyN(moduleMethod, objArr);
        }
        Object obj = objArr[0];
        Object obj2 = objArr[1];
        Object obj3 = objArr[2];
        Object obj4 = objArr[3];
        int length = objArr.length - 4;
        Object[] objArr2 = new Object[length];
        while (true) {
            length--;
            if (length < 0) {
                return argsFold$V(obj, obj2, obj3, obj4, objArr2);
            }
            objArr2[length] = objArr[length + 4];
        }
    }

    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        if (moduleMethod.selector != 31) {
            return super.matchN(moduleMethod, objArr, callContext);
        }
        callContext.values = objArr;
        callContext.proc = moduleMethod;
        callContext.pc = 5;
        return 0;
    }
}
