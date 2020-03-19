package gnu.kawa.slib;

import android.support.v4.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Char;
import kawa.lib.characters;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.numbers;
import kawa.lib.ports;
import kawa.lib.rnrs.unicode;
import kawa.lib.strings;
import kawa.standard.Scheme;
import kawa.standard.append;

/* compiled from: pregexp.scm */
public class pregexp extends ModuleBody {
    public static Char $Stpregexp$Mncomment$Mnchar$St;
    public static Object $Stpregexp$Mnnul$Mnchar$Mnint$St;
    public static Object $Stpregexp$Mnreturn$Mnchar$St;
    public static Object $Stpregexp$Mnspace$Mnsensitive$Qu$St;
    public static Object $Stpregexp$Mntab$Mnchar$St;
    public static IntNum $Stpregexp$Mnversion$St;
    public static final pregexp $instance = new pregexp();
    static final IntNum Lit0 = IntNum.make(20050502);
    static final Char Lit1 = Char.make(59);
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol(":bos").readResolve());
    static final SimpleSymbol Lit100 = ((SimpleSymbol) new SimpleSymbol(":sub").readResolve());
    static final SimpleSymbol Lit101 = ((SimpleSymbol) new SimpleSymbol("pregexp-match-positions-aux").readResolve());
    static final SimpleSymbol Lit102 = ((SimpleSymbol) new SimpleSymbol("non-existent-backref").readResolve());
    static final SimpleSymbol Lit103 = ((SimpleSymbol) new SimpleSymbol(":lookahead").readResolve());
    static final SimpleSymbol Lit104 = ((SimpleSymbol) new SimpleSymbol(":neg-lookahead").readResolve());
    static final SimpleSymbol Lit105 = ((SimpleSymbol) new SimpleSymbol(":lookbehind").readResolve());
    static final PairWithPosition Lit106 = PairWithPosition.make(Lit68, PairWithPosition.make(Boolean.FALSE, PairWithPosition.make(Lit73, PairWithPosition.make(Boolean.FALSE, PairWithPosition.make(Lit14, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2302017), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2302014), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2302012), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2302009), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2301999);
    static final SimpleSymbol Lit107 = ((SimpleSymbol) new SimpleSymbol(":neg-lookbehind").readResolve());
    static final PairWithPosition Lit108;
    static final SimpleSymbol Lit109 = ((SimpleSymbol) new SimpleSymbol(":no-backtrack").readResolve());
    static final Char Lit11;
    static final SimpleSymbol Lit110 = ((SimpleSymbol) new SimpleSymbol("greedy-quantifier-operand-could-be-empty").readResolve());
    static final SimpleSymbol Lit111 = ((SimpleSymbol) new SimpleSymbol("fk").readResolve());
    static final SimpleSymbol Lit112 = ((SimpleSymbol) new SimpleSymbol("identity").readResolve());
    static final Char Lit113 = Char.make(38);
    static final SimpleSymbol Lit114 = ((SimpleSymbol) new SimpleSymbol("pregexp-match-positions").readResolve());
    static final SimpleSymbol Lit115 = ((SimpleSymbol) new SimpleSymbol("pattern-must-be-compiled-or-string-regexp").readResolve());
    static final PairWithPosition Lit116;
    static final SimpleSymbol Lit117 = ((SimpleSymbol) new SimpleSymbol("pregexp-reverse!").readResolve());
    static final SimpleSymbol Lit118 = ((SimpleSymbol) new SimpleSymbol("pregexp-error").readResolve());
    static final SimpleSymbol Lit119 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-pattern").readResolve());
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol(":eos").readResolve());
    static final SimpleSymbol Lit120 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-branch").readResolve());
    static final SimpleSymbol Lit121 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-escaped-number").readResolve());
    static final SimpleSymbol Lit122 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-escaped-char").readResolve());
    static final SimpleSymbol Lit123 = ((SimpleSymbol) new SimpleSymbol("pregexp-invert-char-list").readResolve());
    static final SimpleSymbol Lit124 = ((SimpleSymbol) new SimpleSymbol("pregexp-string-match").readResolve());
    static final SimpleSymbol Lit125 = ((SimpleSymbol) new SimpleSymbol("pregexp-char-word?").readResolve());
    static final SimpleSymbol Lit126 = ((SimpleSymbol) new SimpleSymbol("pregexp-at-word-boundary?").readResolve());
    static final SimpleSymbol Lit127 = ((SimpleSymbol) new SimpleSymbol("pregexp-list-ref").readResolve());
    static final SimpleSymbol Lit128 = ((SimpleSymbol) new SimpleSymbol("pregexp-make-backref-list").readResolve());
    static final SimpleSymbol Lit129 = ((SimpleSymbol) new SimpleSymbol("pregexp-replace-aux").readResolve());
    static final Char Lit13;
    static final SimpleSymbol Lit130 = ((SimpleSymbol) new SimpleSymbol("pregexp").readResolve());
    static final SimpleSymbol Lit131 = ((SimpleSymbol) new SimpleSymbol("pregexp-match").readResolve());
    static final SimpleSymbol Lit132 = ((SimpleSymbol) new SimpleSymbol("pregexp-split").readResolve());
    static final SimpleSymbol Lit133 = ((SimpleSymbol) new SimpleSymbol("pregexp-replace").readResolve());
    static final SimpleSymbol Lit134 = ((SimpleSymbol) new SimpleSymbol("pregexp-replace*").readResolve());
    static final SimpleSymbol Lit135 = ((SimpleSymbol) new SimpleSymbol("pregexp-quote").readResolve());
    static final SimpleSymbol Lit14;
    static final Char Lit15;
    static final IntNum Lit16 = IntNum.make(2);
    static final SimpleSymbol Lit17;
    static final Char Lit18;
    static final Char Lit19;
    static final Char Lit2 = Char.make(97);
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol(":backref").readResolve());
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-piece").readResolve());
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol("backslash").readResolve());
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol(":empty").readResolve());
    static final Char Lit24 = Char.make(10);
    static final Char Lit25 = Char.make(98);
    static final SimpleSymbol Lit26 = ((SimpleSymbol) new SimpleSymbol(":wbdry").readResolve());
    static final Char Lit27 = Char.make(66);
    static final SimpleSymbol Lit28 = ((SimpleSymbol) new SimpleSymbol(":not-wbdry").readResolve());
    static final Char Lit29 = Char.make(100);
    static final Char Lit3 = Char.make(32);
    static final SimpleSymbol Lit30;
    static final Char Lit31 = Char.make(68);
    static final PairWithPosition Lit32;
    static final Char Lit33 = Char.make(110);
    static final Char Lit34 = Char.make(114);
    static final Char Lit35 = Char.make(115);
    static final SimpleSymbol Lit36;
    static final Char Lit37 = Char.make(83);
    static final PairWithPosition Lit38;
    static final Char Lit39 = Char.make(116);
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol(":or").readResolve());
    static final Char Lit40 = Char.make(119);
    static final SimpleSymbol Lit41;
    static final Char Lit42 = Char.make(87);
    static final PairWithPosition Lit43;
    static final Char Lit44 = Char.make(58);
    static final SimpleSymbol Lit45 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-posix-char-class").readResolve());
    static final Char Lit46;
    static final Char Lit47;
    static final Char Lit48 = Char.make(61);
    static final PairWithPosition Lit49 = PairWithPosition.make(Lit103, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 851996);
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol(":seq").readResolve());
    static final Char Lit50 = Char.make(33);
    static final PairWithPosition Lit51 = PairWithPosition.make(Lit104, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 856092);
    static final Char Lit52 = Char.make(62);
    static final PairWithPosition Lit53 = PairWithPosition.make(Lit109, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 860188);
    static final Char Lit54 = Char.make(60);
    static final PairWithPosition Lit55 = PairWithPosition.make(Lit105, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 872479);
    static final PairWithPosition Lit56 = PairWithPosition.make(Lit107, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 876575);
    static final SimpleSymbol Lit57 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-cluster-type").readResolve());
    static final Char Lit58 = Char.make(45);
    static final Char Lit59 = Char.make(105);
    static final Char Lit6;
    static final SimpleSymbol Lit60 = ((SimpleSymbol) new SimpleSymbol(":case-sensitive").readResolve());
    static final SimpleSymbol Lit61 = ((SimpleSymbol) new SimpleSymbol(":case-insensitive").readResolve());
    static final Char Lit62 = Char.make(120);
    static final PairWithPosition Lit63 = PairWithPosition.make(Lit100, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 942102);
    static final SimpleSymbol Lit64 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-subpattern").readResolve());
    static final Char Lit65;
    static final Char Lit66;
    static final Char Lit67;
    static final SimpleSymbol Lit68;
    static final SimpleSymbol Lit69 = ((SimpleSymbol) new SimpleSymbol("minimal?").readResolve());
    static final Char Lit7;
    static final SimpleSymbol Lit70 = ((SimpleSymbol) new SimpleSymbol("at-least").readResolve());
    static final SimpleSymbol Lit71 = ((SimpleSymbol) new SimpleSymbol("at-most").readResolve());
    static final SimpleSymbol Lit72 = ((SimpleSymbol) new SimpleSymbol("next-i").readResolve());
    static final IntNum Lit73;
    static final SimpleSymbol Lit74 = ((SimpleSymbol) new SimpleSymbol("pregexp-wrap-quantifier-if-any").readResolve());
    static final SimpleSymbol Lit75 = ((SimpleSymbol) new SimpleSymbol("left-brace-must-be-followed-by-number").readResolve());
    static final SimpleSymbol Lit76 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-nums").readResolve());
    static final Char Lit77 = Char.make(44);
    static final Char Lit78;
    static final SimpleSymbol Lit79 = ((SimpleSymbol) new SimpleSymbol(":none-of-chars").readResolve());
    static final IntNum Lit8 = IntNum.make(1);
    static final SimpleSymbol Lit80 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-char-list").readResolve());
    static final SimpleSymbol Lit81 = ((SimpleSymbol) new SimpleSymbol("character-class-ended-too-soon").readResolve());
    static final SimpleSymbol Lit82 = ((SimpleSymbol) new SimpleSymbol(":one-of-chars").readResolve());
    static final SimpleSymbol Lit83 = ((SimpleSymbol) new SimpleSymbol(":char-range").readResolve());
    static final Char Lit84 = Char.make(95);
    static final SimpleSymbol Lit85 = ((SimpleSymbol) new SimpleSymbol(":alnum").readResolve());
    static final SimpleSymbol Lit86 = ((SimpleSymbol) new SimpleSymbol(":alpha").readResolve());
    static final SimpleSymbol Lit87 = ((SimpleSymbol) new SimpleSymbol(":ascii").readResolve());
    static final SimpleSymbol Lit88 = ((SimpleSymbol) new SimpleSymbol(":blank").readResolve());
    static final SimpleSymbol Lit89 = ((SimpleSymbol) new SimpleSymbol(":cntrl").readResolve());
    static final Char Lit9;
    static final SimpleSymbol Lit90 = ((SimpleSymbol) new SimpleSymbol(":graph").readResolve());
    static final SimpleSymbol Lit91 = ((SimpleSymbol) new SimpleSymbol(":lower").readResolve());
    static final SimpleSymbol Lit92 = ((SimpleSymbol) new SimpleSymbol(":print").readResolve());
    static final SimpleSymbol Lit93 = ((SimpleSymbol) new SimpleSymbol(":punct").readResolve());
    static final SimpleSymbol Lit94 = ((SimpleSymbol) new SimpleSymbol(":upper").readResolve());
    static final SimpleSymbol Lit95 = ((SimpleSymbol) new SimpleSymbol(":xdigit").readResolve());
    static final Char Lit96 = Char.make(99);
    static final Char Lit97 = Char.make(101);
    static final Char Lit98 = Char.make(102);
    static final SimpleSymbol Lit99 = ((SimpleSymbol) new SimpleSymbol("pregexp-check-if-in-char-class?").readResolve());
    static final ModuleMethod lambda$Fn1;
    static final ModuleMethod lambda$Fn10;
    static final ModuleMethod lambda$Fn6;
    static final ModuleMethod lambda$Fn7;
    static final ModuleMethod lambda$Fn8;
    static final ModuleMethod lambda$Fn9;
    public static final ModuleMethod pregexp;
    public static final ModuleMethod pregexp$Mnat$Mnword$Mnboundary$Qu;
    public static final ModuleMethod pregexp$Mnchar$Mnword$Qu;
    public static final ModuleMethod pregexp$Mncheck$Mnif$Mnin$Mnchar$Mnclass$Qu;
    public static final ModuleMethod pregexp$Mnerror;
    public static final ModuleMethod pregexp$Mninvert$Mnchar$Mnlist;
    public static final ModuleMethod pregexp$Mnlist$Mnref;
    public static final ModuleMethod pregexp$Mnmake$Mnbackref$Mnlist;
    public static final ModuleMethod pregexp$Mnmatch;
    public static final ModuleMethod pregexp$Mnmatch$Mnpositions;
    public static final ModuleMethod pregexp$Mnmatch$Mnpositions$Mnaux;
    public static final ModuleMethod pregexp$Mnquote;
    public static final ModuleMethod pregexp$Mnread$Mnbranch;
    public static final ModuleMethod pregexp$Mnread$Mnchar$Mnlist;
    public static final ModuleMethod pregexp$Mnread$Mncluster$Mntype;
    public static final ModuleMethod pregexp$Mnread$Mnescaped$Mnchar;
    public static final ModuleMethod pregexp$Mnread$Mnescaped$Mnnumber;
    public static final ModuleMethod pregexp$Mnread$Mnnums;
    public static final ModuleMethod pregexp$Mnread$Mnpattern;
    public static final ModuleMethod pregexp$Mnread$Mnpiece;
    public static final ModuleMethod pregexp$Mnread$Mnposix$Mnchar$Mnclass;
    public static final ModuleMethod pregexp$Mnread$Mnsubpattern;
    public static final ModuleMethod pregexp$Mnreplace;
    public static final ModuleMethod pregexp$Mnreplace$Mnaux;
    public static final ModuleMethod pregexp$Mnreplace$St;
    public static final ModuleMethod pregexp$Mnreverse$Ex;
    public static final ModuleMethod pregexp$Mnsplit;
    public static final ModuleMethod pregexp$Mnstring$Mnmatch;
    public static final ModuleMethod pregexp$Mnwrap$Mnquantifier$Mnif$Mnany;

    /* compiled from: pregexp.scm */
    public class frame extends ModuleBody {
        Object backrefs;
        Object case$Mnsensitive$Qu;
        Procedure identity;
        Object n;
        Object s;
        Object sn;
        Object start;

        public frame() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 15, pregexp.Lit112, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:460");
            this.identity = moduleMethod;
        }

        public static Object lambda2identity(Object x) {
            return x;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 15 ? lambda2identity(obj) : super.apply1(moduleMethod, obj);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 15) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }

        static Boolean lambda4() {
            return Boolean.FALSE;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:103:0x02c9, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit17) == java.lang.Boolean.FALSE) goto L_0x02f7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:105:0x02d7, code lost:
            if (kawa.standard.Scheme.numGEq.apply2(r6.i, r15.n) == java.lang.Boolean.FALSE) goto L_0x02e3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:109:0x0301, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit5) == java.lang.Boolean.FALSE) goto L_0x0313;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:112:0x031d, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit4) == java.lang.Boolean.FALSE) goto L_0x032d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:115:0x0337, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit20) == java.lang.Boolean.FALSE) goto L_0x03ad;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:116:0x0339, code lost:
            r8 = gnu.kawa.slib.pregexp.pregexpListRef(r15.backrefs, kawa.lib.lists.cadr.apply1(r6.re$1));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:117:0x0349, code lost:
            if (r8 == java.lang.Boolean.FALSE) goto L_0x0389;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:118:0x034b, code lost:
            r7 = kawa.lib.lists.cdr.apply1(r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:120:0x0353, code lost:
            if (r7 == java.lang.Boolean.FALSE) goto L_0x03a1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:121:0x0355, code lost:
            r1 = r15.s;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:123:?, code lost:
            r1 = (java.lang.CharSequence) r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:124:0x0359, code lost:
            r3 = kawa.lib.lists.car.apply1(r7);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:126:?, code lost:
            r4 = ((java.lang.Number) r3).intValue();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:127:0x0366, code lost:
            r3 = kawa.lib.lists.cdr.apply1(r7);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:131:0x0389, code lost:
            gnu.kawa.slib.pregexp.pregexpError$V(new java.lang.Object[]{gnu.kawa.slib.pregexp.Lit101, gnu.kawa.slib.pregexp.Lit102, r6.re$1});
            r7 = java.lang.Boolean.FALSE;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:134:0x03b7, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit100) == java.lang.Boolean.FALSE) goto L_0x03cd;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:137:0x03d7, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit103) == java.lang.Boolean.FALSE) goto L_0x0405;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:139:0x03ed, code lost:
            if (lambda3sub(kawa.lib.lists.cadr.apply1(r6.re$1), r6.i, r15.identity, gnu.kawa.slib.pregexp.lambda$Fn6) == java.lang.Boolean.FALSE) goto L_0x03fb;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:143:0x040f, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit104) == java.lang.Boolean.FALSE) goto L_0x043d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:145:0x0425, code lost:
            if (lambda3sub(kawa.lib.lists.cadr.apply1(r6.re$1), r6.i, r15.identity, gnu.kawa.slib.pregexp.lambda$Fn7) == java.lang.Boolean.FALSE) goto L_0x0431;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:149:0x0447, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit105) == java.lang.Boolean.FALSE) goto L_0x048f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:150:0x0449, code lost:
            r11 = r15.n;
            r12 = r15.sn;
            r15.n = r6.i;
            r15.sn = r6.i;
            r10 = lambda3sub(gnu.lists.LList.list4(gnu.kawa.slib.pregexp.Lit5, gnu.kawa.slib.pregexp.Lit106, kawa.lib.lists.cadr.apply1(r6.re$1), gnu.kawa.slib.pregexp.Lit12), gnu.kawa.slib.pregexp.Lit73, r15.identity, gnu.kawa.slib.pregexp.lambda$Fn8);
            r15.n = r11;
            r15.sn = r12;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:151:0x0477, code lost:
            if (r10 == java.lang.Boolean.FALSE) goto L_0x0485;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:155:0x0499, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit107) == java.lang.Boolean.FALSE) goto L_0x04e1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:156:0x049b, code lost:
            r11 = r15.n;
            r12 = r15.sn;
            r15.n = r6.i;
            r15.sn = r6.i;
            r10 = lambda3sub(gnu.lists.LList.list4(gnu.kawa.slib.pregexp.Lit5, gnu.kawa.slib.pregexp.Lit108, kawa.lib.lists.cadr.apply1(r6.re$1), gnu.kawa.slib.pregexp.Lit12), gnu.kawa.slib.pregexp.Lit73, r15.identity, gnu.kawa.slib.pregexp.lambda$Fn9);
            r15.n = r11;
            r15.sn = r12;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:157:0x04c9, code lost:
            if (r10 == java.lang.Boolean.FALSE) goto L_0x04d5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:161:0x04eb, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit109) == java.lang.Boolean.FALSE) goto L_0x0517;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:162:0x04ed, code lost:
            r10 = lambda3sub(kawa.lib.lists.cadr.apply1(r6.re$1), r6.i, r15.identity, gnu.kawa.slib.pregexp.lambda$Fn10);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:163:0x0501, code lost:
            if (r10 == java.lang.Boolean.FALSE) goto L_0x050d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:166:0x0517, code lost:
            r14 = kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit60);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:167:0x0521, code lost:
            if (r14 == java.lang.Boolean.FALSE) goto L_0x0551;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:169:0x0525, code lost:
            if (r14 == java.lang.Boolean.FALSE) goto L_0x055d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:170:0x0527, code lost:
            r6.old = r15.case$Mnsensitive$Qu;
            r15.case$Mnsensitive$Qu = kawa.standard.Scheme.isEqv.apply2(kawa.lib.lists.car.apply1(r6.re$1), gnu.kawa.slib.pregexp.Lit60);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:172:0x055b, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit61) != java.lang.Boolean.FALSE) goto L_0x0527;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:174:0x0567, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit68) == java.lang.Boolean.FALSE) goto L_0x05c2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:175:0x0569, code lost:
            r1 = kawa.lib.lists.cadr.apply1(r6.re$1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:178:0x0573, code lost:
            if (r1 == java.lang.Boolean.FALSE) goto L_0x05bb;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:179:0x0575, code lost:
            r1 = 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:180:0x0576, code lost:
            r6.maximal$Qu = (r1 + 1) & true;
            r6.p = kawa.lib.lists.caddr.apply1(r6.re$1);
            r6.q = kawa.lib.lists.cadddr.apply1(r6.re$1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:181:0x0592, code lost:
            if (r6.maximal$Qu == false) goto L_0x05bf;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:182:0x0594, code lost:
            r1 = r6.q;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:185:0x0598, code lost:
            if (r1 == java.lang.Boolean.FALSE) goto L_0x05bd;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:186:0x059a, code lost:
            r1 = 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:187:0x059b, code lost:
            r1 = (r1 + 1) & true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:188:0x059f, code lost:
            r6.could$Mnloop$Mninfinitely$Qu = r1;
            r6.re = kawa.lib.lists.car.apply1(kawa.lib.lists.cddddr.apply1(r6.re$1));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:189:0x05bb, code lost:
            r1 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:190:0x05bd, code lost:
            r1 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:191:0x05bf, code lost:
            r1 = r6.maximal$Qu;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:194:0x05dc, code lost:
            if (kawa.standard.Scheme.numGEq.apply2(r6.i, r15.n) == java.lang.Boolean.FALSE) goto L_0x05e8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:215:0x0632, code lost:
            r2 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:217:0x063b, code lost:
            throw new gnu.mapping.WrongType(r2, "substring", 1, r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:218:0x063c, code lost:
            r1 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:220:0x0645, code lost:
            throw new gnu.mapping.WrongType(r1, "substring", 2, r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:221:0x0646, code lost:
            r1 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:223:0x064f, code lost:
            throw new gnu.mapping.WrongType(r1, "substring", 3, r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:224:0x0650, code lost:
            r2 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:226:0x0659, code lost:
            throw new gnu.mapping.WrongType(r2, "maximal?", -2, r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:227:0x065a, code lost:
            r2 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:229:0x0663, code lost:
            throw new gnu.mapping.WrongType(r2, "could-loop-infinitely?", -2, r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:244:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply1(r6.fk);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:246:?, code lost:
            return gnu.kawa.slib.pregexp.pregexpError$V(new java.lang.Object[]{gnu.kawa.slib.pregexp.Lit101});
         */
        /* JADX WARNING: Code restructure failed: missing block: B:247:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply1(r6.fk);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:248:?, code lost:
            return r6.lambda5loupOneOfChars(kawa.lib.lists.cdr.apply1(r6.re$1));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:249:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply1(r6.fk);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:250:?, code lost:
            return lambda3sub(kawa.lib.lists.cadr.apply1(r6.re$1), r6.i, r6.lambda$Fn2, r6.lambda$Fn3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:251:?, code lost:
            return r6.lambda6loupSeq(kawa.lib.lists.cdr.apply1(r6.re$1), r6.i);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:252:?, code lost:
            return r6.lambda7loupOr(kawa.lib.lists.cdr.apply1(r6.re$1));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:253:?, code lost:
            return gnu.kawa.slib.pregexp.pregexpStringMatch(kawa.lib.strings.substring(r1, r4, ((java.lang.Number) r3).intValue()), r15.s, r6.i, r15.n, r6.lambda$Fn4, r6.fk);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:254:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply2(r6.sk, r6.i);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:255:?, code lost:
            return lambda3sub(kawa.lib.lists.cadr.apply1(r6.re$1), r6.i, r6.lambda$Fn5, r6.fk);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:256:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply2(r6.sk, r6.i);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:257:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply1(r6.fk);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:258:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply1(r6.fk);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:259:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply2(r6.sk, r6.i);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:260:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply2(r6.sk, r6.i);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:261:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply1(r6.fk);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:262:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply1(r6.fk);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:263:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply2(r6.sk, r6.i);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:264:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply2(r6.sk, r10);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:265:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply1(r6.fk);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:266:?, code lost:
            return lambda3sub(kawa.lib.lists.cadr.apply1(r6.re$1), r6.i, r6.lambda$Fn11, r6.lambda$Fn12);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:267:?, code lost:
            return r6.lambda8loupP(gnu.kawa.slib.pregexp.Lit73, r6.i);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:268:?, code lost:
            return gnu.kawa.slib.pregexp.pregexpError$V(new java.lang.Object[]{gnu.kawa.slib.pregexp.Lit101});
         */
        /* JADX WARNING: Code restructure failed: missing block: B:269:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply1(r6.fk);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:270:?, code lost:
            return gnu.kawa.slib.pregexp.pregexpError$V(new java.lang.Object[]{gnu.kawa.slib.pregexp.Lit101});
         */
        /* JADX WARNING: Code restructure failed: missing block: B:84:0x023b, code lost:
            if (kawa.lib.lists.isPair(r6.re$1) == false) goto L_0x05d0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:85:0x023d, code lost:
            r13 = kawa.lib.lists.car.apply1(r6.re$1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:86:0x024f, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit83) == java.lang.Boolean.FALSE) goto L_0x028d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:88:0x025d, code lost:
            if (kawa.standard.Scheme.numGEq.apply2(r6.i, r15.n) == java.lang.Boolean.FALSE) goto L_0x027f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:90:0x0269, code lost:
            if (r14 != false) goto L_0x01d1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:97:0x0297, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit82) == java.lang.Boolean.FALSE) goto L_0x02bf;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:99:0x02a5, code lost:
            if (kawa.standard.Scheme.numGEq.apply2(r6.i, r15.n) == java.lang.Boolean.FALSE) goto L_0x02b1;
         */
        public Object lambda3sub(Object re, Object i, Object sk, Object fk) {
            frame0 frame0 = new frame0();
            frame0.staticLink = this;
            frame0.re$1 = re;
            frame0.i = i;
            frame0.sk = sk;
            frame0.fk = fk;
            if (Scheme.isEqv.apply2(frame0.re$1, pregexp.Lit10) != Boolean.FALSE) {
                if (Scheme.numEqu.apply2(frame0.i, this.start) != Boolean.FALSE) {
                    return Scheme.applyToArgs.apply2(frame0.sk, frame0.i);
                }
                return Scheme.applyToArgs.apply1(frame0.fk);
            } else if (Scheme.isEqv.apply2(frame0.re$1, pregexp.Lit12) != Boolean.FALSE) {
                return Scheme.numGEq.apply2(frame0.i, this.n) != Boolean.FALSE ? Scheme.applyToArgs.apply2(frame0.sk, frame0.i) : Scheme.applyToArgs.apply1(frame0.fk);
            } else {
                if (Scheme.isEqv.apply2(frame0.re$1, pregexp.Lit23) != Boolean.FALSE) {
                    return Scheme.applyToArgs.apply2(frame0.sk, frame0.i);
                }
                if (Scheme.isEqv.apply2(frame0.re$1, pregexp.Lit26) != Boolean.FALSE) {
                    if (pregexp.isPregexpAtWordBoundary(this.s, frame0.i, this.n) != Boolean.FALSE) {
                        return Scheme.applyToArgs.apply2(frame0.sk, frame0.i);
                    }
                    return Scheme.applyToArgs.apply1(frame0.fk);
                } else if (Scheme.isEqv.apply2(frame0.re$1, pregexp.Lit28) == Boolean.FALSE) {
                    boolean x = characters.isChar(frame0.re$1);
                    if (!x ? x : Scheme.numLss.apply2(frame0.i, this.n) != Boolean.FALSE) {
                        ModuleMethod moduleMethod = this.case$Mnsensitive$Qu != Boolean.FALSE ? characters.char$Eq$Qu : unicode.char$Mnci$Eq$Qu;
                        Object obj = this.s;
                        try {
                            CharSequence charSequence = (CharSequence) obj;
                            Object obj2 = frame0.i;
                            try {
                                return moduleMethod.apply2(Char.make(strings.stringRef(charSequence, ((Number) obj2).intValue())), frame0.re$1) != Boolean.FALSE ? Scheme.applyToArgs.apply2(frame0.sk, AddOp.$Pl.apply2(frame0.i, pregexp.Lit8)) : Scheme.applyToArgs.apply1(frame0.fk);
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "string-ref", 2, obj2);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "string-ref", 1, obj);
                        }
                    } else {
                        boolean x2 = ((lists.isPair(frame0.re$1) ? 1 : 0) + true) & true;
                        if (!x2 ? x2 : Scheme.numLss.apply2(frame0.i, this.n) != Boolean.FALSE) {
                            Object obj3 = this.s;
                            try {
                                CharSequence charSequence2 = (CharSequence) obj3;
                                Object obj4 = frame0.i;
                                try {
                                    if (pregexp.isPregexpCheckIfInCharClass(Char.make(strings.stringRef(charSequence2, ((Number) obj4).intValue())), frame0.re$1) != Boolean.FALSE) {
                                        return Scheme.applyToArgs.apply2(frame0.sk, AddOp.$Pl.apply2(frame0.i, pregexp.Lit8));
                                    }
                                    return Scheme.applyToArgs.apply1(frame0.fk);
                                } catch (ClassCastException e3) {
                                    throw new WrongType(e3, "string-ref", 2, obj4);
                                }
                            } catch (ClassCastException e4) {
                                throw new WrongType(e4, "string-ref", 1, obj3);
                            }
                        } else {
                            boolean x3 = lists.isPair(frame0.re$1);
                            if (x3) {
                                Object x4 = Scheme.isEqv.apply2(lists.car.apply1(frame0.re$1), pregexp.Lit83);
                                if (x4 == Boolean.FALSE) {
                                }
                            }
                            Object obj5 = this.s;
                            try {
                                CharSequence charSequence3 = (CharSequence) obj5;
                                Object obj6 = frame0.i;
                                try {
                                    char c = strings.stringRef(charSequence3, ((Number) obj6).intValue());
                                    ModuleMethod c$Ls = this.case$Mnsensitive$Qu != Boolean.FALSE ? characters.char$Ls$Eq$Qu : unicode.char$Mnci$Ls$Eq$Qu;
                                    Object x5 = c$Ls.apply2(lists.cadr.apply1(frame0.re$1), Char.make(c));
                                    if (x5 == Boolean.FALSE ? x5 != Boolean.FALSE : c$Ls.apply2(Char.make(c), lists.caddr.apply1(frame0.re$1)) != Boolean.FALSE) {
                                        return Scheme.applyToArgs.apply2(frame0.sk, AddOp.$Pl.apply2(frame0.i, pregexp.Lit8));
                                    }
                                    return Scheme.applyToArgs.apply1(frame0.fk);
                                } catch (ClassCastException e5) {
                                    throw new WrongType(e5, "string-ref", 2, obj6);
                                }
                            } catch (ClassCastException e6) {
                                throw new WrongType(e6, "string-ref", 1, obj5);
                            }
                        }
                    }
                } else if (pregexp.isPregexpAtWordBoundary(this.s, frame0.i, this.n) != Boolean.FALSE) {
                    return Scheme.applyToArgs.apply1(frame0.fk);
                } else {
                    return Scheme.applyToArgs.apply2(frame0.sk, frame0.i);
                }
            }
        }
    }

    /* compiled from: pregexp.scm */
    public class frame0 extends ModuleBody {
        boolean could$Mnloop$Mninfinitely$Qu;
        Object fk;
        Object i;
        final ModuleMethod lambda$Fn11;
        final ModuleMethod lambda$Fn12;
        final ModuleMethod lambda$Fn2;
        final ModuleMethod lambda$Fn3;
        final ModuleMethod lambda$Fn4;
        final ModuleMethod lambda$Fn5;
        boolean maximal$Qu;
        Object old;
        Object p;
        Object q;
        Object re;
        Object re$1;
        Object sk;
        frame staticLink;

        public frame0() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 9, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:513");
            this.lambda$Fn2 = moduleMethod;
            ModuleMethod moduleMethod2 = new ModuleMethod(this, 10, null, 0);
            moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:514");
            this.lambda$Fn3 = moduleMethod2;
            ModuleMethod moduleMethod3 = new ModuleMethod(this, 11, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod3.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:541");
            this.lambda$Fn4 = moduleMethod3;
            ModuleMethod moduleMethod4 = new ModuleMethod(this, 12, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod4.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:545");
            this.lambda$Fn5 = moduleMethod4;
            ModuleMethod moduleMethod5 = new ModuleMethod(this, 13, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod5.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:587");
            this.lambda$Fn11 = moduleMethod5;
            ModuleMethod moduleMethod6 = new ModuleMethod(this, 14, null, 0);
            moduleMethod6.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:590");
            this.lambda$Fn12 = moduleMethod6;
        }

        public Object lambda5loupOneOfChars(Object chars) {
            frame1 frame1 = new frame1();
            frame1.staticLink = this;
            frame1.chars = chars;
            if (lists.isNull(frame1.chars)) {
                return Scheme.applyToArgs.apply1(this.fk);
            }
            return this.staticLink.lambda3sub(lists.car.apply1(frame1.chars), this.i, this.sk, frame1.lambda$Fn13);
        }

        /* access modifiers changed from: 0000 */
        public Object lambda9(Object i1) {
            return Scheme.applyToArgs.apply1(this.fk);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 9:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 11:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 12:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 13:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                default:
                    return super.match1(moduleMethod, obj, callContext);
            }
        }

        /* access modifiers changed from: 0000 */
        public Object lambda10() {
            return Scheme.applyToArgs.apply2(this.sk, AddOp.$Pl.apply2(this.i, pregexp.Lit8));
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 10:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 14:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                default:
                    return super.match0(moduleMethod, callContext);
            }
        }

        public Object lambda6loupSeq(Object res, Object i2) {
            frame2 frame2 = new frame2();
            frame2.staticLink = this;
            frame2.res = res;
            if (lists.isNull(frame2.res)) {
                return Scheme.applyToArgs.apply2(this.sk, i2);
            }
            return this.staticLink.lambda3sub(lists.car.apply1(frame2.res), i2, frame2.lambda$Fn14, this.fk);
        }

        public Object lambda7loupOr(Object res) {
            frame3 frame3 = new frame3();
            frame3.staticLink = this;
            frame3.res = res;
            if (lists.isNull(frame3.res)) {
                return Scheme.applyToArgs.apply1(this.fk);
            }
            return this.staticLink.lambda3sub(lists.car.apply1(frame3.res), this.i, frame3.lambda$Fn15, frame3.lambda$Fn16);
        }

        /* access modifiers changed from: 0000 */
        public Object lambda11(Object i2) {
            return Scheme.applyToArgs.apply2(this.sk, i2);
        }

        /* access modifiers changed from: 0000 */
        public Object lambda12(Object i1) {
            Object assv = lists.assv(this.re$1, this.staticLink.backrefs);
            try {
                lists.setCdr$Ex((Pair) assv, lists.cons(this.i, i1));
                return Scheme.applyToArgs.apply2(this.sk, i1);
            } catch (ClassCastException e) {
                throw new WrongType(e, "set-cdr!", 1, assv);
            }
        }

        static Boolean lambda13() {
            return Boolean.FALSE;
        }

        static Boolean lambda14() {
            return Boolean.FALSE;
        }

        static Boolean lambda15() {
            return Boolean.FALSE;
        }

        static Boolean lambda16() {
            return Boolean.FALSE;
        }

        static Boolean lambda17() {
            return Boolean.FALSE;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            switch (moduleMethod.selector) {
                case 9:
                    return lambda9(obj);
                case 11:
                    return lambda11(obj);
                case 12:
                    return lambda12(obj);
                case 13:
                    return lambda18(obj);
                default:
                    return super.apply1(moduleMethod, obj);
            }
        }

        /* access modifiers changed from: 0000 */
        public Object lambda18(Object i1) {
            this.staticLink.case$Mnsensitive$Qu = this.old;
            return Scheme.applyToArgs.apply2(this.sk, i1);
        }

        public Object apply0(ModuleMethod moduleMethod) {
            switch (moduleMethod.selector) {
                case 10:
                    return lambda10();
                case 14:
                    return lambda19();
                default:
                    return super.apply0(moduleMethod);
            }
        }

        /* access modifiers changed from: 0000 */
        public Object lambda19() {
            this.staticLink.case$Mnsensitive$Qu = this.old;
            return Scheme.applyToArgs.apply1(this.fk);
        }

        public Object lambda8loupP(Object k, Object i2) {
            frame4 frame4 = new frame4();
            frame4.staticLink = this;
            frame4.k = k;
            frame4.i = i2;
            if (Scheme.numLss.apply2(frame4.k, this.p) != Boolean.FALSE) {
                return this.staticLink.lambda3sub(this.re, frame4.i, frame4.lambda$Fn17, this.fk);
            }
            frame4.q = this.q != Boolean.FALSE ? AddOp.$Mn.apply2(this.q, this.p) : this.q;
            return frame4.lambda24loupQ(pregexp.Lit73, frame4.i);
        }
    }

    /* compiled from: pregexp.scm */
    public class frame1 extends ModuleBody {
        Object chars;
        final ModuleMethod lambda$Fn13;
        frame0 staticLink;

        public frame1() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 1, null, 0);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:508");
            this.lambda$Fn13 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 1 ? lambda20() : super.apply0(moduleMethod);
        }

        /* access modifiers changed from: 0000 */
        public Object lambda20() {
            return this.staticLink.lambda5loupOneOfChars(lists.cdr.apply1(this.chars));
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 1) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }
    }

    /* compiled from: pregexp.scm */
    public class frame2 extends ModuleBody {
        final ModuleMethod lambda$Fn14;
        Object res;
        frame0 staticLink;

        public frame2() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 2, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:519");
            this.lambda$Fn14 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 2 ? lambda21(obj) : super.apply1(moduleMethod, obj);
        }

        /* access modifiers changed from: 0000 */
        public Object lambda21(Object i1) {
            return this.staticLink.lambda6loupSeq(lists.cdr.apply1(this.res), i1);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 2) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: pregexp.scm */
    public class frame3 extends ModuleBody {
        final ModuleMethod lambda$Fn15;
        final ModuleMethod lambda$Fn16;
        Object res;
        frame0 staticLink;

        public frame3() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 3, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:526");
            this.lambda$Fn15 = moduleMethod;
            ModuleMethod moduleMethod2 = new ModuleMethod(this, 4, null, 0);
            moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:529");
            this.lambda$Fn16 = moduleMethod2;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 3 ? lambda22(obj) : super.apply1(moduleMethod, obj);
        }

        /* access modifiers changed from: 0000 */
        public Object lambda22(Object i1) {
            Object x = Scheme.applyToArgs.apply2(this.staticLink.sk, i1);
            return x != Boolean.FALSE ? x : this.staticLink.lambda7loupOr(lists.cdr.apply1(this.res));
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 3) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 4 ? lambda23() : super.apply0(moduleMethod);
        }

        /* access modifiers changed from: 0000 */
        public Object lambda23() {
            return this.staticLink.lambda7loupOr(lists.cdr.apply1(this.res));
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 4) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }
    }

    /* compiled from: pregexp.scm */
    public class frame4 extends ModuleBody {
        Object i;
        Object k;
        final ModuleMethod lambda$Fn17;
        Object q;
        frame0 staticLink;

        public frame4() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 8, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:602");
            this.lambda$Fn17 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 8 ? lambda25(obj) : super.apply1(moduleMethod, obj);
        }

        /* access modifiers changed from: 0000 */
        public Object lambda25(Object i1) {
            if (!this.staticLink.could$Mnloop$Mninfinitely$Qu ? this.staticLink.could$Mnloop$Mninfinitely$Qu : Scheme.numEqu.apply2(i1, this.i) != Boolean.FALSE) {
                pregexp.pregexpError$V(new Object[]{pregexp.Lit101, pregexp.Lit110});
            }
            return this.staticLink.lambda8loupP(AddOp.$Pl.apply2(this.k, pregexp.Lit8), i1);
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

        public Object lambda24loupQ(Object k2, Object i2) {
            frame5 frame5 = new frame5();
            frame5.staticLink = this;
            frame5.k = k2;
            frame5.i = i2;
            frame5.fk = frame5.fk;
            if (this.q == Boolean.FALSE ? this.q != Boolean.FALSE : Scheme.numGEq.apply2(frame5.k, this.q) != Boolean.FALSE) {
                return frame5.lambda26fk();
            }
            if (this.staticLink.maximal$Qu) {
                return this.staticLink.staticLink.lambda3sub(this.staticLink.re, frame5.i, frame5.lambda$Fn18, frame5.fk);
            }
            Object x = frame5.lambda26fk();
            return x == Boolean.FALSE ? this.staticLink.staticLink.lambda3sub(this.staticLink.re, frame5.i, frame5.lambda$Fn19, frame5.fk) : x;
        }
    }

    /* compiled from: pregexp.scm */
    public class frame5 extends ModuleBody {
        Procedure fk;
        Object i;
        Object k;
        final ModuleMethod lambda$Fn18;
        final ModuleMethod lambda$Fn19;
        frame4 staticLink;

        public frame5() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 5, pregexp.Lit111, 0);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:612");
            this.fk = moduleMethod;
            ModuleMethod moduleMethod2 = new ModuleMethod(this, 6, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:617");
            this.lambda$Fn18 = moduleMethod2;
            ModuleMethod moduleMethod3 = new ModuleMethod(this, 7, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod3.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:628");
            this.lambda$Fn19 = moduleMethod3;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 5 ? lambda26fk() : super.apply0(moduleMethod);
        }

        public Object lambda26fk() {
            return Scheme.applyToArgs.apply2(this.staticLink.staticLink.sk, this.i);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 5) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        /* access modifiers changed from: 0000 */
        public Object lambda27(Object i1) {
            if (!this.staticLink.staticLink.could$Mnloop$Mninfinitely$Qu ? this.staticLink.staticLink.could$Mnloop$Mninfinitely$Qu : Scheme.numEqu.apply2(i1, this.i) != Boolean.FALSE) {
                pregexp.pregexpError$V(new Object[]{pregexp.Lit101, pregexp.Lit110});
            }
            Object x = this.staticLink.lambda24loupQ(AddOp.$Pl.apply2(this.k, pregexp.Lit8), i1);
            return x != Boolean.FALSE ? x : lambda26fk();
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 6:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 7:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                default:
                    return super.match1(moduleMethod, obj, callContext);
            }
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            switch (moduleMethod.selector) {
                case 6:
                    return lambda27(obj);
                case 7:
                    return lambda28(obj);
                default:
                    return super.apply1(moduleMethod, obj);
            }
        }

        /* access modifiers changed from: 0000 */
        public Object lambda28(Object i1) {
            return this.staticLink.lambda24loupQ(AddOp.$Pl.apply2(this.k, pregexp.Lit8), i1);
        }
    }

    static {
        Char make = Char.make(92);
        Lit19 = make;
        Char make2 = Char.make(46);
        Lit13 = make2;
        Char make3 = Char.make(63);
        Lit47 = make3;
        Char make4 = Char.make(42);
        Lit65 = make4;
        Char make5 = Char.make(43);
        Lit66 = make5;
        Char make6 = Char.make(124);
        Lit7 = make6;
        Char make7 = Char.make(94);
        Lit9 = make7;
        Char make8 = Char.make(36);
        Lit11 = make8;
        Char make9 = Char.make(91);
        Lit15 = make9;
        Char make10 = Char.make(93);
        Lit46 = make10;
        Char make11 = Char.make(123);
        Lit67 = make11;
        Char make12 = Char.make(125);
        Lit78 = make12;
        Char make13 = Char.make(40);
        Lit18 = make13;
        Char make14 = Char.make(41);
        Lit6 = make14;
        Lit116 = PairWithPosition.make(make, PairWithPosition.make(make2, PairWithPosition.make(make3, PairWithPosition.make(make4, PairWithPosition.make(make5, PairWithPosition.make(make6, PairWithPosition.make(make7, PairWithPosition.make(make8, PairWithPosition.make(make9, PairWithPosition.make(make10, PairWithPosition.make(make11, PairWithPosition.make(make12, PairWithPosition.make(make13, PairWithPosition.make(make14, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3153977), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3153973), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3153969), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3153965), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3153961), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3153957), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149885), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149881), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149877), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149873), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149869), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149865), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149861), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149856);
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol(":between").readResolve();
        Lit68 = simpleSymbol;
        Boolean bool = Boolean.FALSE;
        IntNum make15 = IntNum.make(0);
        Lit73 = make15;
        Boolean bool2 = Boolean.FALSE;
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol(":any").readResolve();
        Lit14 = simpleSymbol2;
        Lit108 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(bool, PairWithPosition.make(make15, PairWithPosition.make(bool2, PairWithPosition.make(simpleSymbol2, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2338881), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2338878), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2338876), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2338873), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2338863);
        SimpleSymbol simpleSymbol3 = (SimpleSymbol) new SimpleSymbol(":neg-char").readResolve();
        Lit17 = simpleSymbol3;
        SimpleSymbol simpleSymbol4 = (SimpleSymbol) new SimpleSymbol(":word").readResolve();
        Lit41 = simpleSymbol4;
        Lit43 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol4, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 696359), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 696348);
        SimpleSymbol simpleSymbol5 = Lit17;
        SimpleSymbol simpleSymbol6 = (SimpleSymbol) new SimpleSymbol(":space").readResolve();
        Lit36 = simpleSymbol6;
        Lit38 = PairWithPosition.make(simpleSymbol5, PairWithPosition.make(simpleSymbol6, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 684071), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 684060);
        SimpleSymbol simpleSymbol7 = Lit17;
        SimpleSymbol simpleSymbol8 = (SimpleSymbol) new SimpleSymbol(":digit").readResolve();
        Lit30 = simpleSymbol8;
        Lit32 = PairWithPosition.make(simpleSymbol7, PairWithPosition.make(simpleSymbol8, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 667687), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 667676);
        pregexp pregexp2 = $instance;
        ModuleMethod moduleMethod = new ModuleMethod(pregexp2, 16, Lit117, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:47");
        pregexp$Mnreverse$Ex = moduleMethod;
        ModuleMethod moduleMethod2 = new ModuleMethod(pregexp2, 17, Lit118, -4096);
        moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:57");
        pregexp$Mnerror = moduleMethod2;
        ModuleMethod moduleMethod3 = new ModuleMethod(pregexp2, 18, Lit119, 12291);
        moduleMethod3.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:65");
        pregexp$Mnread$Mnpattern = moduleMethod3;
        ModuleMethod moduleMethod4 = new ModuleMethod(pregexp2, 19, Lit120, 12291);
        moduleMethod4.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:79");
        pregexp$Mnread$Mnbranch = moduleMethod4;
        ModuleMethod moduleMethod5 = new ModuleMethod(pregexp2, 20, Lit21, 12291);
        moduleMethod5.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:91");
        pregexp$Mnread$Mnpiece = moduleMethod5;
        ModuleMethod moduleMethod6 = new ModuleMethod(pregexp2, 21, Lit121, 12291);
        moduleMethod6.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:138");
        pregexp$Mnread$Mnescaped$Mnnumber = moduleMethod6;
        ModuleMethod moduleMethod7 = new ModuleMethod(pregexp2, 22, Lit122, 12291);
        moduleMethod7.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:155");
        pregexp$Mnread$Mnescaped$Mnchar = moduleMethod7;
        ModuleMethod moduleMethod8 = new ModuleMethod(pregexp2, 23, Lit45, 12291);
        moduleMethod8.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:174");
        pregexp$Mnread$Mnposix$Mnchar$Mnclass = moduleMethod8;
        ModuleMethod moduleMethod9 = new ModuleMethod(pregexp2, 24, Lit57, 12291);
        moduleMethod9.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:200");
        pregexp$Mnread$Mncluster$Mntype = moduleMethod9;
        ModuleMethod moduleMethod10 = new ModuleMethod(pregexp2, 25, Lit64, 12291);
        moduleMethod10.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:233");
        pregexp$Mnread$Mnsubpattern = moduleMethod10;
        ModuleMethod moduleMethod11 = new ModuleMethod(pregexp2, 26, Lit74, 12291);
        moduleMethod11.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:254");
        pregexp$Mnwrap$Mnquantifier$Mnif$Mnany = moduleMethod11;
        ModuleMethod moduleMethod12 = new ModuleMethod(pregexp2, 27, Lit76, 12291);
        moduleMethod12.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:300");
        pregexp$Mnread$Mnnums = moduleMethod12;
        ModuleMethod moduleMethod13 = new ModuleMethod(pregexp2, 28, Lit123, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod13.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:323");
        pregexp$Mninvert$Mnchar$Mnlist = moduleMethod13;
        ModuleMethod moduleMethod14 = new ModuleMethod(pregexp2, 29, Lit80, 12291);
        moduleMethod14.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:330");
        pregexp$Mnread$Mnchar$Mnlist = moduleMethod14;
        ModuleMethod moduleMethod15 = new ModuleMethod(pregexp2, 30, Lit124, 24582);
        moduleMethod15.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:368");
        pregexp$Mnstring$Mnmatch = moduleMethod15;
        ModuleMethod moduleMethod16 = new ModuleMethod(pregexp2, 31, Lit125, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod16.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:379");
        pregexp$Mnchar$Mnword$Qu = moduleMethod16;
        ModuleMethod moduleMethod17 = new ModuleMethod(pregexp2, 32, Lit126, 12291);
        moduleMethod17.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:387");
        pregexp$Mnat$Mnword$Mnboundary$Qu = moduleMethod17;
        ModuleMethod moduleMethod18 = new ModuleMethod(pregexp2, 33, Lit99, 8194);
        moduleMethod18.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:399");
        pregexp$Mncheck$Mnif$Mnin$Mnchar$Mnclass$Qu = moduleMethod18;
        ModuleMethod moduleMethod19 = new ModuleMethod(pregexp2, 34, Lit127, 8194);
        moduleMethod19.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:429");
        pregexp$Mnlist$Mnref = moduleMethod19;
        ModuleMethod moduleMethod20 = new ModuleMethod(pregexp2, 35, Lit128, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod20.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:448");
        pregexp$Mnmake$Mnbackref$Mnlist = moduleMethod20;
        ModuleMethod moduleMethod21 = new ModuleMethod(pregexp2, 36, null, 0);
        moduleMethod21.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:463");
        lambda$Fn1 = moduleMethod21;
        ModuleMethod moduleMethod22 = new ModuleMethod(pregexp2, 37, null, 0);
        moduleMethod22.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:551");
        lambda$Fn6 = moduleMethod22;
        ModuleMethod moduleMethod23 = new ModuleMethod(pregexp2, 38, null, 0);
        moduleMethod23.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:556");
        lambda$Fn7 = moduleMethod23;
        ModuleMethod moduleMethod24 = new ModuleMethod(pregexp2, 39, null, 0);
        moduleMethod24.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:564");
        lambda$Fn8 = moduleMethod24;
        ModuleMethod moduleMethod25 = new ModuleMethod(pregexp2, 40, null, 0);
        moduleMethod25.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:573");
        lambda$Fn9 = moduleMethod25;
        ModuleMethod moduleMethod26 = new ModuleMethod(pregexp2, 41, null, 0);
        moduleMethod26.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:578");
        lambda$Fn10 = moduleMethod26;
        ModuleMethod moduleMethod27 = new ModuleMethod(pregexp2, 42, Lit101, 24582);
        moduleMethod27.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:459");
        pregexp$Mnmatch$Mnpositions$Mnaux = moduleMethod27;
        ModuleMethod moduleMethod28 = new ModuleMethod(pregexp2, 43, Lit129, 16388);
        moduleMethod28.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:639");
        pregexp$Mnreplace$Mnaux = moduleMethod28;
        ModuleMethod moduleMethod29 = new ModuleMethod(pregexp2, 44, Lit130, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod29.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:665");
        pregexp = moduleMethod29;
        ModuleMethod moduleMethod30 = new ModuleMethod(pregexp2, 45, Lit114, -4094);
        moduleMethod30.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:670");
        pregexp$Mnmatch$Mnpositions = moduleMethod30;
        ModuleMethod moduleMethod31 = new ModuleMethod(pregexp2, 46, Lit131, -4094);
        moduleMethod31.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:690");
        pregexp$Mnmatch = moduleMethod31;
        ModuleMethod moduleMethod32 = new ModuleMethod(pregexp2, 47, Lit132, 8194);
        moduleMethod32.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:700");
        pregexp$Mnsplit = moduleMethod32;
        ModuleMethod moduleMethod33 = new ModuleMethod(pregexp2, 48, Lit133, 12291);
        moduleMethod33.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:723");
        pregexp$Mnreplace = moduleMethod33;
        ModuleMethod moduleMethod34 = new ModuleMethod(pregexp2, 49, Lit134, 12291);
        moduleMethod34.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:736");
        pregexp$Mnreplace$St = moduleMethod34;
        ModuleMethod moduleMethod35 = new ModuleMethod(pregexp2, 50, Lit135, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod35.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:764");
        pregexp$Mnquote = moduleMethod35;
        $instance.run();
    }

    public pregexp() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer consumer = $ctx.consumer;
        $Stpregexp$Mnversion$St = Lit0;
        $Stpregexp$Mncomment$Mnchar$St = Lit1;
        $Stpregexp$Mnnul$Mnchar$Mnint$St = Integer.valueOf(characters.char$To$Integer(Lit2) - 97);
        $Stpregexp$Mnreturn$Mnchar$St = characters.integer$To$Char(((Number) $Stpregexp$Mnnul$Mnchar$Mnint$St).intValue() + 13);
        $Stpregexp$Mntab$Mnchar$St = characters.integer$To$Char(((Number) $Stpregexp$Mnnul$Mnchar$Mnint$St).intValue() + 9);
        $Stpregexp$Mnspace$Mnsensitive$Qu$St = Boolean.TRUE;
    }

    public static Object pregexpReverse$Ex(Object s) {
        Object obj = LList.Empty;
        while (!lists.isNull(s)) {
            Object d = lists.cdr.apply1(s);
            try {
                lists.setCdr$Ex((Pair) s, obj);
                obj = s;
                s = d;
            } catch (ClassCastException e) {
                throw new WrongType(e, "set-cdr!", 1, s);
            }
        }
        return obj;
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 16:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 28:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 31:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 35:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 44:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 50:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static Object pregexpError$V(Object[] argsArray) {
        Object makeList = LList.makeList(argsArray, 0);
        ports.display("Error:");
        Object obj = makeList;
        while (obj != LList.Empty) {
            try {
                Pair arg0 = (Pair) obj;
                Object x = arg0.getCar();
                ports.display(Lit3);
                ports.write(x);
                obj = arg0.getCdr();
            } catch (ClassCastException e) {
                throw new WrongType(e, "arg0", -2, obj);
            }
        }
        ports.newline();
        return misc.error$V("pregexp-error", new Object[0]);
    }

    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 17:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 30:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 42:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 45:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 46:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            default:
                return super.matchN(moduleMethod, objArr, callContext);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0078  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0080 A[SYNTHETIC] */
    public static Object pregexpReadPattern(Object s, Object i, Object n) {
        if (Scheme.numGEq.apply2(i, n) != Boolean.FALSE) {
            return LList.list2(LList.list2(Lit4, LList.list1(Lit5)), i);
        }
        Object obj = LList.Empty;
        while (true) {
            Object apply2 = Scheme.numGEq.apply2(i, n);
            try {
                boolean x = ((Boolean) apply2).booleanValue();
                if (x) {
                    if (x) {
                        break;
                    }
                    try {
                        try {
                            if (!characters.isChar$Eq(Char.make(strings.stringRef((CharSequence) s, ((Number) i).intValue())), Lit7)) {
                                i = AddOp.$Pl.apply2(i, Lit8);
                            }
                            Object vv = pregexpReadBranch(s, i, n);
                            obj = lists.cons(lists.car.apply1(vv), obj);
                            i = lists.cadr.apply1(vv);
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-ref", 2, i);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-ref", 1, s);
                    }
                } else {
                    try {
                        try {
                            if (characters.isChar$Eq(Char.make(strings.stringRef((CharSequence) s, ((Number) i).intValue())), Lit6)) {
                                break;
                            }
                            if (!characters.isChar$Eq(Char.make(strings.stringRef((CharSequence) s, ((Number) i).intValue())), Lit7)) {
                            }
                            Object vv2 = pregexpReadBranch(s, i, n);
                            obj = lists.cons(lists.car.apply1(vv2), obj);
                            i = lists.cadr.apply1(vv2);
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "string-ref", 2, i);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "string-ref", 1, s);
                    }
                }
            } catch (ClassCastException e5) {
                throw new WrongType(e5, "x", -2, apply2);
            }
        }
        return LList.list2(lists.cons(Lit4, pregexpReverse$Ex(obj)), i);
    }

    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 18:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 19:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 20:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 21:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 22:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 23:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 24:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 25:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 26:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 27:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 29:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 32:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 48:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 49:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            default:
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }
    }

    public static Object pregexpReadBranch(Object s, Object i, Object n) {
        Object obj = LList.Empty;
        while (Scheme.numGEq.apply2(i, n) == Boolean.FALSE) {
            try {
                try {
                    char c = strings.stringRef((CharSequence) s, ((Number) i).intValue());
                    boolean x = characters.isChar$Eq(Char.make(c), Lit7);
                    if (x) {
                        if (!x) {
                            Object vv = pregexpReadPiece(s, i, n);
                            obj = lists.cons(lists.car.apply1(vv), obj);
                            i = lists.cadr.apply1(vv);
                        }
                    } else if (!characters.isChar$Eq(Char.make(c), Lit6)) {
                        Object vv2 = pregexpReadPiece(s, i, n);
                        obj = lists.cons(lists.car.apply1(vv2), obj);
                        i = lists.cadr.apply1(vv2);
                    }
                    return LList.list2(lists.cons(Lit5, pregexpReverse$Ex(obj)), i);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-ref", 2, i);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-ref", 1, s);
            }
        }
        return LList.list2(lists.cons(Lit5, pregexpReverse$Ex(obj)), i);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0177, code lost:
        if (r7 != java.lang.Boolean.FALSE) goto L_0x0179;
     */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x01c0  */
    public static Object pregexpReadPiece(Object s, Object i, Object n) {
        Object pregexpError$V;
        Object tmp;
        Object pregexpReadCharList;
        try {
            try {
                char c = strings.stringRef((CharSequence) s, ((Number) i).intValue());
                if (Scheme.isEqv.apply2(Char.make(c), Lit9) != Boolean.FALSE) {
                    return LList.list2(Lit10, AddOp.$Pl.apply2(i, Lit8));
                }
                if (Scheme.isEqv.apply2(Char.make(c), Lit11) != Boolean.FALSE) {
                    return LList.list2(Lit12, AddOp.$Pl.apply2(i, Lit8));
                }
                if (Scheme.isEqv.apply2(Char.make(c), Lit13) != Boolean.FALSE) {
                    return pregexpWrapQuantifierIfAny(LList.list2(Lit14, AddOp.$Pl.apply2(i, Lit8)), s, n);
                }
                if (Scheme.isEqv.apply2(Char.make(c), Lit15) != Boolean.FALSE) {
                    Object i$Pl1 = AddOp.$Pl.apply2(i, Lit8);
                    Object apply2 = Scheme.numLss.apply2(i$Pl1, n);
                    try {
                        boolean x = ((Boolean) apply2).booleanValue();
                        if (x) {
                            try {
                                try {
                                    tmp = Char.make(strings.stringRef((CharSequence) s, ((Number) i$Pl1).intValue()));
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, i$Pl1);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, s);
                            }
                        } else {
                            tmp = x ? Boolean.TRUE : Boolean.FALSE;
                        }
                        if (Scheme.isEqv.apply2(tmp, Lit9) != Boolean.FALSE) {
                            Object vv = pregexpReadCharList(s, AddOp.$Pl.apply2(i, Lit16), n);
                            pregexpReadCharList = LList.list2(LList.list2(Lit17, lists.car.apply1(vv)), lists.cadr.apply1(vv));
                        } else {
                            pregexpReadCharList = pregexpReadCharList(s, i$Pl1, n);
                        }
                        return pregexpWrapQuantifierIfAny(pregexpReadCharList, s, n);
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "x", -2, apply2);
                    }
                } else if (Scheme.isEqv.apply2(Char.make(c), Lit18) != Boolean.FALSE) {
                    return pregexpWrapQuantifierIfAny(pregexpReadSubpattern(s, AddOp.$Pl.apply2(i, Lit8), n), s, n);
                } else {
                    if (Scheme.isEqv.apply2(Char.make(c), Lit19) != Boolean.FALSE) {
                        Object temp = pregexpReadEscapedNumber(s, i, n);
                        if (temp != Boolean.FALSE) {
                            pregexpError$V = LList.list2(LList.list2(Lit20, lists.car.apply1(temp)), lists.cadr.apply1(temp));
                        } else {
                            Object temp2 = pregexpReadEscapedChar(s, i, n);
                            pregexpError$V = temp2 != Boolean.FALSE ? LList.list2(lists.car.apply1(temp2), lists.cadr.apply1(temp2)) : pregexpError$V(new Object[]{Lit21, Lit22});
                        }
                        return pregexpWrapQuantifierIfAny(pregexpError$V, s, n);
                    }
                    Object x2 = $Stpregexp$Mnspace$Mnsensitive$Qu$St;
                    if (x2 == Boolean.FALSE) {
                        boolean x3 = ((unicode.isCharWhitespace(Char.make(c)) ? 1 : 0) + true) & true;
                        if (!x3) {
                            Boolean in$Mncomment$Qu = Boolean.FALSE;
                            while (Scheme.numGEq.apply2(i, n) == Boolean.FALSE) {
                                try {
                                    try {
                                        char c2 = strings.stringRef((CharSequence) s, ((Number) i).intValue());
                                        if (in$Mncomment$Qu != Boolean.FALSE) {
                                            i = AddOp.$Pl.apply2(i, Lit8);
                                            in$Mncomment$Qu = characters.isChar$Eq(Char.make(c2), Lit24) ? Boolean.FALSE : Boolean.TRUE;
                                        } else if (unicode.isCharWhitespace(Char.make(c2))) {
                                            i = AddOp.$Pl.apply2(i, Lit8);
                                            in$Mncomment$Qu = Boolean.FALSE;
                                        } else if (!characters.isChar$Eq(Char.make(c2), Lit1)) {
                                            return LList.list2(Lit23, i);
                                        } else {
                                            i = AddOp.$Pl.apply2(i, Lit8);
                                            in$Mncomment$Qu = Boolean.TRUE;
                                        }
                                    } catch (ClassCastException e4) {
                                        throw new WrongType(e4, "string-ref", 2, i);
                                    }
                                } catch (ClassCastException e5) {
                                    throw new WrongType(e5, "string-ref", 1, s);
                                }
                            }
                            return LList.list2(Lit23, i);
                        }
                        Boolean in$Mncomment$Qu2 = Boolean.FALSE;
                        while (Scheme.numGEq.apply2(i, n) == Boolean.FALSE) {
                        }
                        return LList.list2(Lit23, i);
                    }
                    return pregexpWrapQuantifierIfAny(LList.list2(Char.make(c), AddOp.$Pl.apply2(i, Lit8)), s, n);
                }
            } catch (ClassCastException e6) {
                throw new WrongType(e6, "string-ref", 2, i);
            }
        } catch (ClassCastException e7) {
            throw new WrongType(e7, "string-ref", 1, s);
        }
    }

    public static Object pregexpReadEscapedNumber(Object s, Object i, Object n) {
        Object apply2 = Scheme.numLss.apply2(AddOp.$Pl.apply2(i, Lit8), n);
        try {
            boolean x = ((Boolean) apply2).booleanValue();
            if (!x) {
                return x ? Boolean.TRUE : Boolean.FALSE;
            }
            try {
                CharSequence charSequence = (CharSequence) s;
                Object apply22 = AddOp.$Pl.apply2(i, Lit8);
                try {
                    char c = strings.stringRef(charSequence, ((Number) apply22).intValue());
                    boolean x2 = unicode.isCharNumeric(Char.make(c));
                    if (!x2) {
                        return x2 ? Boolean.TRUE : Boolean.FALSE;
                    }
                    Object i2 = AddOp.$Pl.apply2(i, Lit16);
                    Pair r = LList.list1(Char.make(c));
                    while (Scheme.numGEq.apply2(i2, n) == Boolean.FALSE) {
                        try {
                            try {
                                char c2 = strings.stringRef((CharSequence) s, ((Number) i2).intValue());
                                if (unicode.isCharNumeric(Char.make(c2))) {
                                    i2 = AddOp.$Pl.apply2(i2, Lit8);
                                    r = lists.cons(Char.make(c2), r);
                                } else {
                                    Object pregexpReverse$Ex = pregexpReverse$Ex(r);
                                    try {
                                        return LList.list2(numbers.string$To$Number(strings.list$To$String((LList) pregexpReverse$Ex)), i2);
                                    } catch (ClassCastException e) {
                                        throw new WrongType(e, "list->string", 1, pregexpReverse$Ex);
                                    }
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 2, i2);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "string-ref", 1, s);
                        }
                    }
                    Object pregexpReverse$Ex2 = pregexpReverse$Ex(r);
                    try {
                        return LList.list2(numbers.string$To$Number(strings.list$To$String((LList) pregexpReverse$Ex2)), i2);
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "list->string", 1, pregexpReverse$Ex2);
                    }
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "string-ref", 2, apply22);
                }
            } catch (ClassCastException e6) {
                throw new WrongType(e6, "string-ref", 1, s);
            }
        } catch (ClassCastException e7) {
            throw new WrongType(e7, "x", -2, apply2);
        }
    }

    public static Object pregexpReadEscapedChar(Object s, Object i, Object n) {
        Object apply2 = Scheme.numLss.apply2(AddOp.$Pl.apply2(i, Lit8), n);
        try {
            boolean x = ((Boolean) apply2).booleanValue();
            if (!x) {
                return x ? Boolean.TRUE : Boolean.FALSE;
            }
            try {
                CharSequence charSequence = (CharSequence) s;
                Object apply22 = AddOp.$Pl.apply2(i, Lit8);
                try {
                    char c = strings.stringRef(charSequence, ((Number) apply22).intValue());
                    if (Scheme.isEqv.apply2(Char.make(c), Lit25) != Boolean.FALSE) {
                        return LList.list2(Lit26, AddOp.$Pl.apply2(i, Lit16));
                    }
                    if (Scheme.isEqv.apply2(Char.make(c), Lit27) != Boolean.FALSE) {
                        return LList.list2(Lit28, AddOp.$Pl.apply2(i, Lit16));
                    }
                    if (Scheme.isEqv.apply2(Char.make(c), Lit29) != Boolean.FALSE) {
                        return LList.list2(Lit30, AddOp.$Pl.apply2(i, Lit16));
                    }
                    if (Scheme.isEqv.apply2(Char.make(c), Lit31) != Boolean.FALSE) {
                        return LList.list2(Lit32, AddOp.$Pl.apply2(i, Lit16));
                    }
                    if (Scheme.isEqv.apply2(Char.make(c), Lit33) != Boolean.FALSE) {
                        return LList.list2(Lit24, AddOp.$Pl.apply2(i, Lit16));
                    }
                    if (Scheme.isEqv.apply2(Char.make(c), Lit34) != Boolean.FALSE) {
                        return LList.list2($Stpregexp$Mnreturn$Mnchar$St, AddOp.$Pl.apply2(i, Lit16));
                    }
                    if (Scheme.isEqv.apply2(Char.make(c), Lit35) != Boolean.FALSE) {
                        return LList.list2(Lit36, AddOp.$Pl.apply2(i, Lit16));
                    }
                    if (Scheme.isEqv.apply2(Char.make(c), Lit37) != Boolean.FALSE) {
                        return LList.list2(Lit38, AddOp.$Pl.apply2(i, Lit16));
                    }
                    if (Scheme.isEqv.apply2(Char.make(c), Lit39) != Boolean.FALSE) {
                        return LList.list2($Stpregexp$Mntab$Mnchar$St, AddOp.$Pl.apply2(i, Lit16));
                    }
                    if (Scheme.isEqv.apply2(Char.make(c), Lit40) != Boolean.FALSE) {
                        return LList.list2(Lit41, AddOp.$Pl.apply2(i, Lit16));
                    }
                    if (Scheme.isEqv.apply2(Char.make(c), Lit42) != Boolean.FALSE) {
                        return LList.list2(Lit43, AddOp.$Pl.apply2(i, Lit16));
                    }
                    return LList.list2(Char.make(c), AddOp.$Pl.apply2(i, Lit16));
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-ref", 2, apply22);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-ref", 1, s);
            }
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "x", -2, apply2);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0086, code lost:
        if (r5 != false) goto L_0x0088;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00b3, code lost:
        if (kawa.lib.characters.isChar$Eq(gnu.text.Char.make(kawa.lib.strings.stringRef(r12, ((java.lang.Number) r7).intValue())), Lit46) != false) goto L_0x00b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00b5, code lost:
        r6 = pregexpReverse$Ex(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00bb, code lost:
        r3 = kawa.lib.misc.string$To$Symbol(kawa.lib.strings.list$To$String((gnu.lists.LList) r6));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00c5, code lost:
        if (r2 == java.lang.Boolean.FALSE) goto L_0x00cd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00c7, code lost:
        r3 = gnu.lists.LList.list2(Lit17, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0115, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x011d, code lost:
        throw new gnu.mapping.WrongType(r7, "list->string", 1, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:?, code lost:
        return gnu.lists.LList.list2(r3, gnu.kawa.functions.AddOp.$Pl.apply2(r13, Lit16));
     */
    public static Object pregexpReadPosixCharClass(Object s, Object i, Object n) {
        Boolean neg$Qu = Boolean.FALSE;
        Pair list1 = LList.list1(Lit44);
        while (Scheme.numGEq.apply2(i, n) == Boolean.FALSE) {
            try {
                try {
                    char c = strings.stringRef((CharSequence) s, ((Number) i).intValue());
                    if (characters.isChar$Eq(Char.make(c), Lit9)) {
                        neg$Qu = Boolean.TRUE;
                        i = AddOp.$Pl.apply2(i, Lit8);
                    } else if (unicode.isCharAlphabetic(Char.make(c))) {
                        i = AddOp.$Pl.apply2(i, Lit8);
                        list1 = lists.cons(Char.make(c), list1);
                    } else if (characters.isChar$Eq(Char.make(c), Lit44)) {
                        Object apply2 = Scheme.numGEq.apply2(AddOp.$Pl.apply2(i, Lit8), n);
                        try {
                            boolean x = ((Boolean) apply2).booleanValue();
                            if (!x) {
                                try {
                                    CharSequence charSequence = (CharSequence) s;
                                    Object apply22 = AddOp.$Pl.apply2(i, Lit8);
                                    try {
                                    } catch (ClassCastException e) {
                                        throw new WrongType(e, "string-ref", 2, apply22);
                                    }
                                } catch (ClassCastException e2) {
                                    throw new WrongType(e2, "string-ref", 1, s);
                                }
                            }
                            return pregexpError$V(new Object[]{Lit45});
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "x", -2, apply2);
                        }
                    } else {
                        return pregexpError$V(new Object[]{Lit45});
                    }
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "string-ref", 2, i);
                }
            } catch (ClassCastException e5) {
                throw new WrongType(e5, "string-ref", 1, s);
            }
        }
        return pregexpError$V(new Object[]{Lit45});
    }

    public static Object pregexpReadClusterType(Object s, Object i, Object n) {
        char c;
        try {
            try {
                if (Scheme.isEqv.apply2(Char.make(strings.stringRef((CharSequence) s, ((Number) i).intValue())), Lit47) == Boolean.FALSE) {
                    return LList.list2(Lit63, i);
                }
                Object i2 = AddOp.$Pl.apply2(i, Lit8);
                try {
                    try {
                        char tmp = strings.stringRef((CharSequence) s, ((Number) i2).intValue());
                        if (Scheme.isEqv.apply2(Char.make(tmp), Lit44) != Boolean.FALSE) {
                            return LList.list2(LList.Empty, AddOp.$Pl.apply2(i2, Lit8));
                        }
                        if (Scheme.isEqv.apply2(Char.make(tmp), Lit48) != Boolean.FALSE) {
                            return LList.list2(Lit49, AddOp.$Pl.apply2(i2, Lit8));
                        }
                        if (Scheme.isEqv.apply2(Char.make(tmp), Lit50) != Boolean.FALSE) {
                            return LList.list2(Lit51, AddOp.$Pl.apply2(i2, Lit8));
                        }
                        if (Scheme.isEqv.apply2(Char.make(tmp), Lit52) != Boolean.FALSE) {
                            return LList.list2(Lit53, AddOp.$Pl.apply2(i2, Lit8));
                        }
                        if (Scheme.isEqv.apply2(Char.make(tmp), Lit54) != Boolean.FALSE) {
                            try {
                                CharSequence charSequence = (CharSequence) s;
                                Object apply2 = AddOp.$Pl.apply2(i2, Lit8);
                                try {
                                    char tmp2 = strings.stringRef(charSequence, ((Number) apply2).intValue());
                                    Object pregexpError$V = Scheme.isEqv.apply2(Char.make(tmp2), Lit48) != Boolean.FALSE ? Lit55 : Scheme.isEqv.apply2(Char.make(tmp2), Lit50) != Boolean.FALSE ? Lit56 : pregexpError$V(new Object[]{Lit57});
                                    return LList.list2(pregexpError$V, AddOp.$Pl.apply2(i2, Lit16));
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, apply2);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, s);
                            }
                        } else {
                            LList lList = LList.Empty;
                            Boolean bool = Boolean.FALSE;
                            while (true) {
                                try {
                                    try {
                                        c = strings.stringRef((CharSequence) s, ((Number) i2).intValue());
                                        if (Scheme.isEqv.apply2(Char.make(c), Lit58) == Boolean.FALSE) {
                                            if (Scheme.isEqv.apply2(Char.make(c), Lit59) == Boolean.FALSE) {
                                                if (Scheme.isEqv.apply2(Char.make(c), Lit62) == Boolean.FALSE) {
                                                    break;
                                                }
                                                $Stpregexp$Mnspace$Mnsensitive$Qu$St = bool;
                                                i2 = AddOp.$Pl.apply2(i2, Lit8);
                                                bool = Boolean.FALSE;
                                            } else {
                                                i2 = AddOp.$Pl.apply2(i2, Lit8);
                                                lList = lists.cons(bool != Boolean.FALSE ? Lit60 : Lit61, lList);
                                                bool = Boolean.FALSE;
                                            }
                                        } else {
                                            i2 = AddOp.$Pl.apply2(i2, Lit8);
                                            bool = Boolean.TRUE;
                                        }
                                    } catch (ClassCastException e3) {
                                        throw new WrongType(e3, "string-ref", 2, i2);
                                    }
                                } catch (ClassCastException e4) {
                                    throw new WrongType(e4, "string-ref", 1, s);
                                }
                            }
                            if (Scheme.isEqv.apply2(Char.make(c), Lit44) != Boolean.FALSE) {
                                return LList.list2(lList, AddOp.$Pl.apply2(i2, Lit8));
                            }
                            return pregexpError$V(new Object[]{Lit57});
                        }
                    } catch (ClassCastException e5) {
                        throw new WrongType(e5, "string-ref", 2, i2);
                    }
                } catch (ClassCastException e6) {
                    throw new WrongType(e6, "string-ref", 1, s);
                }
            } catch (ClassCastException e7) {
                throw new WrongType(e7, "string-ref", 2, i);
            }
        } catch (ClassCastException e8) {
            throw new WrongType(e8, "string-ref", 1, s);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x004e, code lost:
        if (kawa.lib.characters.isChar$Eq(gnu.text.Char.make(kawa.lib.strings.stringRef((java.lang.CharSequence) r15, ((java.lang.Number) r7).intValue())), Lit6) != false) goto L_0x0050;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0054, code lost:
        if (kawa.lib.lists.isNull(r1) == false) goto L_0x0072;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0063, code lost:
        if (r9 == false) goto L_0x0065;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0072, code lost:
        r2 = kawa.lib.lists.cdr.apply1(r1);
        r8 = gnu.lists.LList.list2(kawa.lib.lists.car.apply1(r1), r8);
        r1 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        return gnu.lists.LList.list2(r8, gnu.kawa.functions.AddOp.$Pl.apply2(r7, Lit8));
     */
    public static Object pregexpReadSubpattern(Object s, Object i, Object n) {
        Object remember$Mnspace$Mnsensitive$Qu = $Stpregexp$Mnspace$Mnsensitive$Qu$St;
        Object ctyp$Mni = pregexpReadClusterType(s, i, n);
        Object ctyp = lists.car.apply1(ctyp$Mni);
        Object vv = pregexpReadPattern(s, lists.cadr.apply1(ctyp$Mni), n);
        $Stpregexp$Mnspace$Mnsensitive$Qu$St = remember$Mnspace$Mnsensitive$Qu;
        Object vv$Mnre = lists.car.apply1(vv);
        Object vv$Mni = lists.cadr.apply1(vv);
        Object apply2 = Scheme.numLss.apply2(vv$Mni, n);
        try {
            boolean x = ((Boolean) apply2).booleanValue();
            if (x) {
                try {
                    try {
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "string-ref", 2, vv$Mni);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "string-ref", 1, s);
                }
            }
            return pregexpError$V(new Object[]{Lit64});
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "x", -2, apply2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x020b  */
    /* JADX WARNING: Removed duplicated region for block: B:166:0x00a9 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x007d  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00c4  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0101  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x01b1  */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x01e9  */
    public static Object pregexpWrapQuantifierIfAny(Object vv, Object s, Object n) {
        Object x;
        Object i;
        char c;
        Object re = lists.car.apply1(vv);
        Object i2 = lists.cadr.apply1(vv);
        while (Scheme.numGEq.apply2(i2, n) == Boolean.FALSE) {
            try {
                try {
                    char c2 = strings.stringRef((CharSequence) s, ((Number) i2).intValue());
                    boolean x2 = unicode.isCharWhitespace(Char.make(c2));
                    if (x2) {
                        if ($Stpregexp$Mnspace$Mnsensitive$Qu$St != Boolean.FALSE) {
                            x = Scheme.isEqv.apply2(Char.make(c2), Lit65);
                            if (x != Boolean.FALSE) {
                                Object x3 = Scheme.isEqv.apply2(Char.make(c2), Lit66);
                                if (x3 == Boolean.FALSE) {
                                    Object x4 = Scheme.isEqv.apply2(Char.make(c2), Lit47);
                                    if (x4 != Boolean.FALSE) {
                                        if (x4 == Boolean.FALSE) {
                                            return vv;
                                        }
                                    } else if (Scheme.isEqv.apply2(Char.make(c2), Lit67) == Boolean.FALSE) {
                                        return vv;
                                    }
                                } else if (x3 == Boolean.FALSE) {
                                    return vv;
                                }
                            } else if (x == Boolean.FALSE) {
                                return vv;
                            }
                            Pair new$Mnre = LList.list1(Lit68);
                            LList.chain4(new$Mnre, Lit69, Lit70, Lit71, re);
                            Object new$Mnvv = LList.list2(new$Mnre, Lit72);
                            if (Scheme.isEqv.apply2(Char.make(c2), Lit65) == Boolean.FALSE) {
                                Object apply1 = lists.cddr.apply1(new$Mnre);
                                try {
                                    lists.setCar$Ex((Pair) apply1, Lit73);
                                    Object apply12 = lists.cdddr.apply1(new$Mnre);
                                    try {
                                        lists.setCar$Ex((Pair) apply12, Boolean.FALSE);
                                    } catch (ClassCastException e) {
                                        throw new WrongType(e, "set-car!", 1, apply12);
                                    }
                                } catch (ClassCastException e2) {
                                    throw new WrongType(e2, "set-car!", 1, apply1);
                                }
                            } else if (Scheme.isEqv.apply2(Char.make(c2), Lit66) != Boolean.FALSE) {
                                Object apply13 = lists.cddr.apply1(new$Mnre);
                                try {
                                    lists.setCar$Ex((Pair) apply13, Lit8);
                                    Object apply14 = lists.cdddr.apply1(new$Mnre);
                                    try {
                                        lists.setCar$Ex((Pair) apply14, Boolean.FALSE);
                                    } catch (ClassCastException e3) {
                                        throw new WrongType(e3, "set-car!", 1, apply14);
                                    }
                                } catch (ClassCastException e4) {
                                    throw new WrongType(e4, "set-car!", 1, apply13);
                                }
                            } else if (Scheme.isEqv.apply2(Char.make(c2), Lit47) != Boolean.FALSE) {
                                Object apply15 = lists.cddr.apply1(new$Mnre);
                                try {
                                    lists.setCar$Ex((Pair) apply15, Lit73);
                                    Object apply16 = lists.cdddr.apply1(new$Mnre);
                                    try {
                                        lists.setCar$Ex((Pair) apply16, Lit8);
                                    } catch (ClassCastException e5) {
                                        throw new WrongType(e5, "set-car!", 1, apply16);
                                    }
                                } catch (ClassCastException e6) {
                                    throw new WrongType(e6, "set-car!", 1, apply15);
                                }
                            } else if (Scheme.isEqv.apply2(Char.make(c2), Lit67) != Boolean.FALSE) {
                                Object pq = pregexpReadNums(s, AddOp.$Pl.apply2(i2, Lit8), n);
                                if (pq == Boolean.FALSE) {
                                    pregexpError$V(new Object[]{Lit74, Lit75});
                                }
                                Object apply17 = lists.cddr.apply1(new$Mnre);
                                try {
                                    lists.setCar$Ex((Pair) apply17, lists.car.apply1(pq));
                                    Object apply18 = lists.cdddr.apply1(new$Mnre);
                                    try {
                                        lists.setCar$Ex((Pair) apply18, lists.cadr.apply1(pq));
                                        i2 = lists.caddr.apply1(pq);
                                    } catch (ClassCastException e7) {
                                        throw new WrongType(e7, "set-car!", 1, apply18);
                                    }
                                } catch (ClassCastException e8) {
                                    throw new WrongType(e8, "set-car!", 1, apply17);
                                }
                            }
                            i = AddOp.$Pl.apply2(i2, Lit8);
                            while (true) {
                                if (Scheme.numGEq.apply2(i, n) == Boolean.FALSE) {
                                    Object apply19 = lists.cdr.apply1(new$Mnre);
                                    try {
                                        lists.setCar$Ex((Pair) apply19, Boolean.FALSE);
                                        Object apply110 = lists.cdr.apply1(new$Mnvv);
                                        try {
                                            lists.setCar$Ex((Pair) apply110, i);
                                            break;
                                        } catch (ClassCastException e9) {
                                            throw new WrongType(e9, "set-car!", 1, apply110);
                                        }
                                    } catch (ClassCastException e10) {
                                        throw new WrongType(e10, "set-car!", 1, apply19);
                                    }
                                } else {
                                    try {
                                        try {
                                            c = strings.stringRef((CharSequence) s, ((Number) i).intValue());
                                            boolean x5 = unicode.isCharWhitespace(Char.make(c));
                                            if (x5) {
                                                if ($Stpregexp$Mnspace$Mnsensitive$Qu$St != Boolean.FALSE) {
                                                    break;
                                                }
                                            } else if (!x5) {
                                                break;
                                            }
                                            i = AddOp.$Pl.apply2(i, Lit8);
                                        } catch (ClassCastException e11) {
                                            throw new WrongType(e11, "string-ref", 2, i);
                                        }
                                    } catch (ClassCastException e12) {
                                        throw new WrongType(e12, "string-ref", 1, s);
                                    }
                                }
                            }
                            if (!characters.isChar$Eq(Char.make(c), Lit47)) {
                                Object apply111 = lists.cdr.apply1(new$Mnre);
                                try {
                                    lists.setCar$Ex((Pair) apply111, Boolean.TRUE);
                                    Object apply112 = lists.cdr.apply1(new$Mnvv);
                                    try {
                                        lists.setCar$Ex((Pair) apply112, AddOp.$Pl.apply2(i, Lit8));
                                    } catch (ClassCastException e13) {
                                        throw new WrongType(e13, "set-car!", 1, apply112);
                                    }
                                } catch (ClassCastException e14) {
                                    throw new WrongType(e14, "set-car!", 1, apply111);
                                }
                            } else {
                                Object apply113 = lists.cdr.apply1(new$Mnre);
                                try {
                                    lists.setCar$Ex((Pair) apply113, Boolean.FALSE);
                                    Object apply114 = lists.cdr.apply1(new$Mnvv);
                                    try {
                                        lists.setCar$Ex((Pair) apply114, i);
                                    } catch (ClassCastException e15) {
                                        throw new WrongType(e15, "set-car!", 1, apply114);
                                    }
                                } catch (ClassCastException e16) {
                                    throw new WrongType(e16, "set-car!", 1, apply113);
                                }
                            }
                            return new$Mnvv;
                        }
                    } else if (!x2) {
                        x = Scheme.isEqv.apply2(Char.make(c2), Lit65);
                        if (x != Boolean.FALSE) {
                        }
                        Pair new$Mnre2 = LList.list1(Lit68);
                        LList.chain4(new$Mnre2, Lit69, Lit70, Lit71, re);
                        Object new$Mnvv2 = LList.list2(new$Mnre2, Lit72);
                        if (Scheme.isEqv.apply2(Char.make(c2), Lit65) == Boolean.FALSE) {
                        }
                        i = AddOp.$Pl.apply2(i2, Lit8);
                        while (true) {
                            if (Scheme.numGEq.apply2(i, n) == Boolean.FALSE) {
                            }
                            i = AddOp.$Pl.apply2(i, Lit8);
                        }
                        if (!characters.isChar$Eq(Char.make(c), Lit47)) {
                        }
                        return new$Mnvv2;
                    }
                    i2 = AddOp.$Pl.apply2(i2, Lit8);
                } catch (ClassCastException e17) {
                    throw new WrongType(e17, "string-ref", 2, i2);
                }
            } catch (ClassCastException e18) {
                throw new WrongType(e18, "string-ref", 1, s);
            }
        }
        return vv;
    }

    public static Object pregexpReadNums(Object s, Object i, Object n) {
        char c;
        int i2;
        LList p = LList.Empty;
        LList lList = LList.Empty;
        IntNum intNum = Lit8;
        Object obj = i;
        while (true) {
            if (Scheme.numGEq.apply2(obj, n) != Boolean.FALSE) {
                pregexpError$V(new Object[]{Lit76});
            }
            try {
                try {
                    c = strings.stringRef((CharSequence) s, ((Number) obj).intValue());
                    if (!unicode.isCharNumeric(Char.make(c))) {
                        boolean x = unicode.isCharWhitespace(Char.make(c));
                        if (!x ? x : $Stpregexp$Mnspace$Mnsensitive$Qu$St == Boolean.FALSE) {
                            obj = AddOp.$Pl.apply2(obj, Lit8);
                        } else {
                            boolean x2 = characters.isChar$Eq(Char.make(c), Lit77);
                            if (!x2) {
                                if (!x2) {
                                    break;
                                }
                            } else if (Scheme.numEqu.apply2(intNum, Lit8) == Boolean.FALSE) {
                                break;
                            }
                            Object k = AddOp.$Pl.apply2(obj, Lit8);
                            intNum = Lit16;
                            obj = k;
                        }
                    } else if (Scheme.numEqu.apply2(intNum, Lit8) != Boolean.FALSE) {
                        p = lists.cons(Char.make(c), p);
                        Object k2 = AddOp.$Pl.apply2(obj, Lit8);
                        intNum = Lit8;
                        obj = k2;
                    } else {
                        lList = lists.cons(Char.make(c), lList);
                        Object k3 = AddOp.$Pl.apply2(obj, Lit8);
                        intNum = Lit16;
                        obj = k3;
                    }
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-ref", 2, obj);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-ref", 1, s);
            }
        }
        if (!characters.isChar$Eq(Char.make(c), Lit78)) {
            return Boolean.FALSE;
        }
        Object pregexpReverse$Ex = pregexpReverse$Ex(p);
        try {
            Object p2 = numbers.string$To$Number(strings.list$To$String((LList) pregexpReverse$Ex));
            Object pregexpReverse$Ex2 = pregexpReverse$Ex(lList);
            try {
                Object q = numbers.string$To$Number(strings.list$To$String((LList) pregexpReverse$Ex2));
                try {
                    if (p2 != Boolean.FALSE) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    boolean x3 = (i2 + 1) & true;
                    if (!x3 ? x3 : Scheme.numEqu.apply2(intNum, Lit8) != Boolean.FALSE) {
                        return LList.list3(Lit73, Boolean.FALSE, obj);
                    }
                    return Scheme.numEqu.apply2(intNum, Lit8) != Boolean.FALSE ? LList.list3(p2, p2, obj) : LList.list3(p2, q, obj);
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "x", -2, p2);
                }
            } catch (ClassCastException e4) {
                throw new WrongType(e4, "list->string", 1, pregexpReverse$Ex2);
            }
        } catch (ClassCastException e5) {
            throw new WrongType(e5, "list->string", 1, pregexpReverse$Ex);
        }
    }

    public static Object pregexpInvertCharList(Object vv) {
        Object apply1 = lists.car.apply1(vv);
        try {
            lists.setCar$Ex((Pair) apply1, Lit79);
            return vv;
        } catch (ClassCastException e) {
            throw new WrongType(e, "set-car!", 1, apply1);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00bf, code lost:
        if (r7 != false) goto L_0x00c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0105, code lost:
        if (kawa.lib.characters.isChar$Eq(gnu.text.Char.make(kawa.lib.strings.stringRef((java.lang.CharSequence) r13, ((java.lang.Number) r4).intValue())), Lit46) == false) goto L_0x0107;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0107, code lost:
        r2 = kawa.lib.lists.car.apply1(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0111, code lost:
        if (kawa.lib.characters.isChar(r2) == false) goto L_0x014d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0113, code lost:
        r11 = Lit83;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        r8 = (java.lang.CharSequence) r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0119, code lost:
        r10 = gnu.kawa.functions.AddOp.$Pl.apply2(r14, Lit8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0128, code lost:
        r6 = kawa.lib.lists.cons(gnu.lists.LList.list3(r11, r2, gnu.text.Char.make(kawa.lib.strings.stringRef(r8, ((java.lang.Number) r10).intValue()))), kawa.lib.lists.cdr.apply1(r6));
        r14 = gnu.kawa.functions.AddOp.$Pl.apply2(r14, Lit16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0149, code lost:
        if (r7 != false) goto L_0x00c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x014d, code lost:
        r6 = kawa.lib.lists.cons(gnu.text.Char.make(r1), r6);
        r14 = gnu.kawa.functions.AddOp.$Pl.apply2(r14, Lit8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0207, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0210, code lost:
        throw new gnu.mapping.WrongType(r8, "string-ref", 1, r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0211, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x021a, code lost:
        throw new gnu.mapping.WrongType(r8, "string-ref", 2, r10);
     */
    public static Object pregexpReadCharList(Object s, Object i, Object n) {
        LList lList = LList.Empty;
        while (Scheme.numGEq.apply2(i, n) == Boolean.FALSE) {
            try {
                try {
                    char c = strings.stringRef((CharSequence) s, ((Number) i).intValue());
                    if (Scheme.isEqv.apply2(Char.make(c), Lit46) != Boolean.FALSE) {
                        if (!lists.isNull(lList)) {
                            return LList.list2(lists.cons(Lit82, pregexpReverse$Ex(lList)), AddOp.$Pl.apply2(i, Lit8));
                        }
                        lList = lists.cons(Char.make(c), lList);
                        i = AddOp.$Pl.apply2(i, Lit8);
                    } else if (Scheme.isEqv.apply2(Char.make(c), Lit19) != Boolean.FALSE) {
                        Object char$Mni = pregexpReadEscapedChar(s, i, n);
                        if (char$Mni != Boolean.FALSE) {
                            lList = lists.cons(lists.car.apply1(char$Mni), lList);
                            i = lists.cadr.apply1(char$Mni);
                        } else {
                            return pregexpError$V(new Object[]{Lit80, Lit22});
                        }
                    } else if (Scheme.isEqv.apply2(Char.make(c), Lit58) != Boolean.FALSE) {
                        boolean x = lists.isNull(lList);
                        if (!x) {
                            Object i$Pl1 = AddOp.$Pl.apply2(i, Lit8);
                            Object apply2 = Scheme.numLss.apply2(i$Pl1, n);
                            try {
                                boolean x2 = ((Boolean) apply2).booleanValue();
                                if (x2) {
                                    try {
                                        try {
                                        } catch (ClassCastException e) {
                                            throw new WrongType(e, "string-ref", 2, i$Pl1);
                                        }
                                    } catch (ClassCastException e2) {
                                        throw new WrongType(e2, "string-ref", 1, s);
                                    }
                                }
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "x", -2, apply2);
                            }
                        }
                        lList = lists.cons(Char.make(c), lList);
                        i = AddOp.$Pl.apply2(i, Lit8);
                    } else if (Scheme.isEqv.apply2(Char.make(c), Lit15) != Boolean.FALSE) {
                        try {
                            CharSequence charSequence = (CharSequence) s;
                            Object apply22 = AddOp.$Pl.apply2(i, Lit8);
                            try {
                                if (characters.isChar$Eq(Char.make(strings.stringRef(charSequence, ((Number) apply22).intValue())), Lit44)) {
                                    Object posix$Mnchar$Mnclass$Mni = pregexpReadPosixCharClass(s, AddOp.$Pl.apply2(i, Lit16), n);
                                    lList = lists.cons(lists.car.apply1(posix$Mnchar$Mnclass$Mni), lList);
                                    i = lists.cadr.apply1(posix$Mnchar$Mnclass$Mni);
                                } else {
                                    lList = lists.cons(Char.make(c), lList);
                                    i = AddOp.$Pl.apply2(i, Lit8);
                                }
                            } catch (ClassCastException e4) {
                                throw new WrongType(e4, "string-ref", 2, apply22);
                            }
                        } catch (ClassCastException e5) {
                            throw new WrongType(e5, "string-ref", 1, s);
                        }
                    } else {
                        lList = lists.cons(Char.make(c), lList);
                        i = AddOp.$Pl.apply2(i, Lit8);
                    }
                } catch (ClassCastException e6) {
                    throw new WrongType(e6, "string-ref", 2, i);
                }
            } catch (ClassCastException e7) {
                throw new WrongType(e7, "string-ref", 1, s);
            }
        }
        return pregexpError$V(new Object[]{Lit80, Lit81});
    }

    public static Object pregexpStringMatch(Object s1, Object s, Object i, Object n, Object sk, Object fk) {
        try {
            int n1 = strings.stringLength((CharSequence) s1);
            if (Scheme.numGrt.apply2(Integer.valueOf(n1), n) != Boolean.FALSE) {
                return Scheme.applyToArgs.apply1(fk);
            }
            Object obj = Lit73;
            Object obj2 = i;
            while (Scheme.numGEq.apply2(obj, Integer.valueOf(n1)) == Boolean.FALSE) {
                if (Scheme.numGEq.apply2(obj2, n) != Boolean.FALSE) {
                    return Scheme.applyToArgs.apply1(fk);
                }
                try {
                    try {
                        try {
                            try {
                                if (!characters.isChar$Eq(Char.make(strings.stringRef((CharSequence) s1, ((Number) obj).intValue())), Char.make(strings.stringRef((CharSequence) s, ((Number) obj2).intValue())))) {
                                    return Scheme.applyToArgs.apply1(fk);
                                }
                                obj = AddOp.$Pl.apply2(obj, Lit8);
                                obj2 = AddOp.$Pl.apply2(obj2, Lit8);
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "string-ref", 2, obj2);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "string-ref", 1, s);
                        }
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "string-ref", 2, obj);
                    }
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "string-ref", 1, s1);
                }
            }
            return Scheme.applyToArgs.apply2(sk, obj2);
        } catch (ClassCastException e5) {
            throw new WrongType(e5, "string-length", 1, s1);
        }
    }

    public static boolean isPregexpCharWord(Object c) {
        try {
            boolean x = unicode.isCharAlphabetic((Char) c);
            if (x) {
                return x;
            }
            try {
                boolean x2 = unicode.isCharNumeric((Char) c);
                if (x2) {
                    return x2;
                }
                try {
                    return characters.isChar$Eq((Char) c, Lit84);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "char=?", 1, c);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "char-numeric?", 1, c);
            }
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "char-alphabetic?", 1, c);
        }
    }

    public static Object isPregexpAtWordBoundary(Object s, Object i, Object n) {
        Object x;
        Object apply2 = Scheme.numEqu.apply2(i, Lit73);
        try {
            boolean x2 = ((Boolean) apply2).booleanValue();
            if (x2) {
                return x2 ? Boolean.TRUE : Boolean.FALSE;
            }
            Object apply22 = Scheme.numGEq.apply2(i, n);
            try {
                boolean x3 = ((Boolean) apply22).booleanValue();
                if (x3) {
                    return x3 ? Boolean.TRUE : Boolean.FALSE;
                }
                try {
                    try {
                        char c$Sli = strings.stringRef((CharSequence) s, ((Number) i).intValue());
                        try {
                            CharSequence charSequence = (CharSequence) s;
                            Object apply23 = AddOp.$Mn.apply2(i, Lit8);
                            try {
                                char c$Sli$Mn1 = strings.stringRef(charSequence, ((Number) apply23).intValue());
                                Object c$Sli$Slw$Qu = isPregexpCheckIfInCharClass(Char.make(c$Sli), Lit41);
                                Object c$Sli$Mn1$Slw$Qu = isPregexpCheckIfInCharClass(Char.make(c$Sli$Mn1), Lit41);
                                if (c$Sli$Slw$Qu != Boolean.FALSE) {
                                    x = c$Sli$Mn1$Slw$Qu != Boolean.FALSE ? Boolean.FALSE : Boolean.TRUE;
                                } else {
                                    x = c$Sli$Slw$Qu;
                                }
                                if (x != Boolean.FALSE) {
                                    return x;
                                }
                                try {
                                    boolean x4 = ((c$Sli$Slw$Qu != Boolean.FALSE ? 1 : 0) + 1) & true;
                                    if (x4) {
                                        return c$Sli$Mn1$Slw$Qu;
                                    }
                                    return x4 ? Boolean.TRUE : Boolean.FALSE;
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "x", -2, c$Sli$Slw$Qu);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 2, apply23);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "string-ref", 1, s);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "string-ref", 2, i);
                    }
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "string-ref", 1, s);
                }
            } catch (ClassCastException e6) {
                throw new WrongType(e6, "x", -2, apply22);
            }
        } catch (ClassCastException e7) {
            throw new WrongType(e7, "x", -2, apply2);
        }
    }

    public static Object isPregexpCheckIfInCharClass(Object c, Object char$Mnclass) {
        boolean x = false;
        if (Scheme.isEqv.apply2(char$Mnclass, Lit14) != Boolean.FALSE) {
            try {
                if (characters.isChar$Eq((Char) c, Lit24)) {
                    return Boolean.FALSE;
                }
                return Boolean.TRUE;
            } catch (ClassCastException e) {
                throw new WrongType(e, "char=?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit85) != Boolean.FALSE) {
            try {
                boolean x2 = unicode.isCharAlphabetic((Char) c);
                if (x2) {
                    return x2 ? Boolean.TRUE : Boolean.FALSE;
                }
                try {
                    return unicode.isCharNumeric((Char) c) ? Boolean.TRUE : Boolean.FALSE;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "char-numeric?", 1, c);
                }
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "char-alphabetic?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit86) != Boolean.FALSE) {
            try {
                return unicode.isCharAlphabetic((Char) c) ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e4) {
                throw new WrongType(e4, "char-alphabetic?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit87) != Boolean.FALSE) {
            try {
                return characters.char$To$Integer((Char) c) < 128 ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e5) {
                throw new WrongType(e5, "char->integer", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit88) != Boolean.FALSE) {
            try {
                boolean x3 = characters.isChar$Eq((Char) c, Lit3);
                if (x3) {
                    return x3 ? Boolean.TRUE : Boolean.FALSE;
                }
                try {
                    Char charR = (Char) c;
                    Object obj = $Stpregexp$Mntab$Mnchar$St;
                    try {
                        return characters.isChar$Eq(charR, (Char) obj) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e6) {
                        throw new WrongType(e6, "char=?", 2, obj);
                    }
                } catch (ClassCastException e7) {
                    throw new WrongType(e7, "char=?", 1, c);
                }
            } catch (ClassCastException e8) {
                throw new WrongType(e8, "char=?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit89) != Boolean.FALSE) {
            try {
                return characters.char$To$Integer((Char) c) < 32 ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e9) {
                throw new WrongType(e9, "char->integer", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit30) != Boolean.FALSE) {
            try {
                return unicode.isCharNumeric((Char) c) ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e10) {
                throw new WrongType(e10, "char-numeric?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit90) != Boolean.FALSE) {
            try {
                if (characters.char$To$Integer((Char) c) >= 32) {
                    x = true;
                }
                if (!x) {
                    return x ? Boolean.TRUE : Boolean.FALSE;
                }
                try {
                    return unicode.isCharWhitespace((Char) c) ? Boolean.FALSE : Boolean.TRUE;
                } catch (ClassCastException e11) {
                    throw new WrongType(e11, "char-whitespace?", 1, c);
                }
            } catch (ClassCastException e12) {
                throw new WrongType(e12, "char->integer", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit91) != Boolean.FALSE) {
            try {
                return unicode.isCharLowerCase((Char) c) ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e13) {
                throw new WrongType(e13, "char-lower-case?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit92) != Boolean.FALSE) {
            try {
                return characters.char$To$Integer((Char) c) >= 32 ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e14) {
                throw new WrongType(e14, "char->integer", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit93) != Boolean.FALSE) {
            try {
                if (characters.char$To$Integer((Char) c) >= 32) {
                    x = true;
                }
                if (!x) {
                    return x ? Boolean.TRUE : Boolean.FALSE;
                }
                try {
                    boolean x4 = ((unicode.isCharWhitespace((Char) c) ? 1 : 0) + true) & true;
                    if (!x4) {
                        return x4 ? Boolean.TRUE : Boolean.FALSE;
                    }
                    try {
                        boolean x5 = ((unicode.isCharAlphabetic((Char) c) ? 1 : 0) + true) & true;
                        if (!x5) {
                            return x5 ? Boolean.TRUE : Boolean.FALSE;
                        }
                        try {
                            return unicode.isCharNumeric((Char) c) ? Boolean.FALSE : Boolean.TRUE;
                        } catch (ClassCastException e15) {
                            throw new WrongType(e15, "char-numeric?", 1, c);
                        }
                    } catch (ClassCastException e16) {
                        throw new WrongType(e16, "char-alphabetic?", 1, c);
                    }
                } catch (ClassCastException e17) {
                    throw new WrongType(e17, "char-whitespace?", 1, c);
                }
            } catch (ClassCastException e18) {
                throw new WrongType(e18, "char->integer", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit36) != Boolean.FALSE) {
            try {
                return unicode.isCharWhitespace((Char) c) ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e19) {
                throw new WrongType(e19, "char-whitespace?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit94) != Boolean.FALSE) {
            try {
                return unicode.isCharUpperCase((Char) c) ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e20) {
                throw new WrongType(e20, "char-upper-case?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit41) != Boolean.FALSE) {
            try {
                boolean x6 = unicode.isCharAlphabetic((Char) c);
                if (x6) {
                    return x6 ? Boolean.TRUE : Boolean.FALSE;
                }
                try {
                    boolean x7 = unicode.isCharNumeric((Char) c);
                    if (x7) {
                        return x7 ? Boolean.TRUE : Boolean.FALSE;
                    }
                    try {
                        return characters.isChar$Eq((Char) c, Lit84) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e21) {
                        throw new WrongType(e21, "char=?", 1, c);
                    }
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "char-numeric?", 1, c);
                }
            } catch (ClassCastException e23) {
                throw new WrongType(e23, "char-alphabetic?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit95) != Boolean.FALSE) {
            try {
                boolean x8 = unicode.isCharNumeric((Char) c);
                if (x8) {
                    return x8 ? Boolean.TRUE : Boolean.FALSE;
                }
                try {
                    boolean x9 = unicode.isCharCi$Eq((Char) c, Lit2);
                    if (x9) {
                        return x9 ? Boolean.TRUE : Boolean.FALSE;
                    }
                    try {
                        boolean x10 = unicode.isCharCi$Eq((Char) c, Lit25);
                        if (x10) {
                            return x10 ? Boolean.TRUE : Boolean.FALSE;
                        }
                        try {
                            boolean x11 = unicode.isCharCi$Eq((Char) c, Lit96);
                            if (x11) {
                                return x11 ? Boolean.TRUE : Boolean.FALSE;
                            }
                            try {
                                boolean x12 = unicode.isCharCi$Eq((Char) c, Lit29);
                                if (x12) {
                                    return x12 ? Boolean.TRUE : Boolean.FALSE;
                                }
                                try {
                                    boolean x13 = unicode.isCharCi$Eq((Char) c, Lit97);
                                    if (x13) {
                                        return x13 ? Boolean.TRUE : Boolean.FALSE;
                                    }
                                    try {
                                        return unicode.isCharCi$Eq((Char) c, Lit98) ? Boolean.TRUE : Boolean.FALSE;
                                    } catch (ClassCastException e24) {
                                        throw new WrongType(e24, "char-ci=?", 1, c);
                                    }
                                } catch (ClassCastException e25) {
                                    throw new WrongType(e25, "char-ci=?", 1, c);
                                }
                            } catch (ClassCastException e26) {
                                throw new WrongType(e26, "char-ci=?", 1, c);
                            }
                        } catch (ClassCastException e27) {
                            throw new WrongType(e27, "char-ci=?", 1, c);
                        }
                    } catch (ClassCastException e28) {
                        throw new WrongType(e28, "char-ci=?", 1, c);
                    }
                } catch (ClassCastException e29) {
                    throw new WrongType(e29, "char-ci=?", 1, c);
                }
            } catch (ClassCastException e30) {
                throw new WrongType(e30, "char-numeric?", 1, c);
            }
        } else {
            return pregexpError$V(new Object[]{Lit99});
        }
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 33:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 34:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 47:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    public static Object pregexpListRef(Object s, Object i) {
        Object obj = Lit73;
        while (!lists.isNull(s)) {
            if (Scheme.numEqu.apply2(obj, i) != Boolean.FALSE) {
                return lists.car.apply1(s);
            }
            s = lists.cdr.apply1(s);
            obj = AddOp.$Pl.apply2(obj, Lit8);
        }
        return Boolean.FALSE;
    }

    public static Object pregexpMakeBackrefList(Object re) {
        return lambda1sub(re);
    }

    public static Object lambda1sub(Object re) {
        if (!lists.isPair(re)) {
            return LList.Empty;
        }
        Object car$Mnre = lists.car.apply1(re);
        Object sub$Mncdr$Mnre = lambda1sub(lists.cdr.apply1(re));
        if (Scheme.isEqv.apply2(car$Mnre, Lit100) != Boolean.FALSE) {
            return lists.cons(lists.cons(re, Boolean.FALSE), sub$Mncdr$Mnre);
        }
        return append.append$V(new Object[]{lambda1sub(car$Mnre), sub$Mncdr$Mnre});
    }

    public static Object pregexpMatchPositionsAux(Object re, Object s, Object sn, Object start, Object n, Object i) {
        frame frame6 = new frame();
        frame6.s = s;
        frame6.sn = sn;
        frame6.start = start;
        frame6.n = n;
        Procedure procedure = frame6.identity;
        Object pregexpMakeBackrefList = pregexpMakeBackrefList(re);
        frame6.case$Mnsensitive$Qu = Boolean.TRUE;
        frame6.backrefs = pregexpMakeBackrefList;
        frame6.identity = procedure;
        frame6.lambda3sub(re, i, frame6.identity, lambda$Fn1);
        Object arg0 = frame6.backrefs;
        Object obj = LList.Empty;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                Object arg03 = arg02.getCdr();
                obj = Pair.make(lists.cdr.apply1(arg02.getCar()), obj);
                arg0 = arg03;
            } catch (ClassCastException e) {
                throw new WrongType(e, "arg0", -2, arg0);
            }
        }
        LList backrefs = LList.reverseInPlace(obj);
        Object x = lists.car.apply1(backrefs);
        if (x != Boolean.FALSE) {
            return backrefs;
        }
        return x;
    }

    public int match0(ModuleMethod moduleMethod, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 36:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 37:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 38:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 39:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 40:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 41:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            default:
                return super.match0(moduleMethod, callContext);
        }
    }

    public Object apply0(ModuleMethod moduleMethod) {
        switch (moduleMethod.selector) {
            case 36:
                return frame.lambda4();
            case 37:
                return frame0.lambda13();
            case 38:
                return frame0.lambda14();
            case 39:
                return frame0.lambda15();
            case 40:
                return frame0.lambda16();
            case 41:
                return frame0.lambda17();
            default:
                return super.apply0(moduleMethod);
        }
    }

    public static Object pregexpReplaceAux(Object str, Object ins, Object n, Object backrefs) {
        Object br;
        Object i = Lit73;
        Object obj = "";
        while (Scheme.numGEq.apply2(i, n) == Boolean.FALSE) {
            try {
                try {
                    char c = strings.stringRef((CharSequence) ins, ((Number) i).intValue());
                    if (characters.isChar$Eq(Char.make(c), Lit19)) {
                        Object br$Mni = pregexpReadEscapedNumber(ins, i, n);
                        if (br$Mni != Boolean.FALSE) {
                            br = lists.car.apply1(br$Mni);
                        } else {
                            try {
                                CharSequence charSequence = (CharSequence) ins;
                                Object apply2 = AddOp.$Pl.apply2(i, Lit8);
                                try {
                                    br = characters.isChar$Eq(Char.make(strings.stringRef(charSequence, ((Number) apply2).intValue())), Lit113) ? Lit73 : Boolean.FALSE;
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, apply2);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, ins);
                            }
                        }
                        if (br$Mni != Boolean.FALSE) {
                            i = lists.cadr.apply1(br$Mni);
                        } else if (br != Boolean.FALSE) {
                            i = AddOp.$Pl.apply2(i, Lit16);
                        } else {
                            i = AddOp.$Pl.apply2(i, Lit8);
                        }
                        if (br == Boolean.FALSE) {
                            try {
                                try {
                                    char c2 = strings.stringRef((CharSequence) ins, ((Number) i).intValue());
                                    i = AddOp.$Pl.apply2(i, Lit8);
                                    if (!characters.isChar$Eq(Char.make(c2), Lit11)) {
                                        obj = strings.stringAppend(obj, strings.$make$string$(Char.make(c2)));
                                    }
                                } catch (ClassCastException e3) {
                                    throw new WrongType(e3, "string-ref", 2, i);
                                }
                            } catch (ClassCastException e4) {
                                throw new WrongType(e4, "string-ref", 1, ins);
                            }
                        } else {
                            Object backref = pregexpListRef(backrefs, br);
                            if (backref != Boolean.FALSE) {
                                Object[] objArr = new Object[2];
                                objArr[0] = obj;
                                try {
                                    CharSequence charSequence2 = (CharSequence) str;
                                    Object apply1 = lists.car.apply1(backref);
                                    try {
                                        int intValue = ((Number) apply1).intValue();
                                        Object apply12 = lists.cdr.apply1(backref);
                                        try {
                                            objArr[1] = strings.substring(charSequence2, intValue, ((Number) apply12).intValue());
                                            obj = strings.stringAppend(objArr);
                                        } catch (ClassCastException e5) {
                                            throw new WrongType(e5, "substring", 3, apply12);
                                        }
                                    } catch (ClassCastException e6) {
                                        throw new WrongType(e6, "substring", 2, apply1);
                                    }
                                } catch (ClassCastException e7) {
                                    throw new WrongType(e7, "substring", 1, str);
                                }
                            }
                        }
                    } else {
                        i = AddOp.$Pl.apply2(i, Lit8);
                        obj = strings.stringAppend(obj, strings.$make$string$(Char.make(c)));
                    }
                } catch (ClassCastException e8) {
                    throw new WrongType(e8, "string-ref", 2, i);
                }
            } catch (ClassCastException e9) {
                throw new WrongType(e9, "string-ref", 1, ins);
            }
        }
        return obj;
    }

    public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
        return moduleMethod.selector == 43 ? pregexpReplaceAux(obj, obj2, obj3, obj4) : super.apply4(moduleMethod, obj, obj2, obj3, obj4);
    }

    public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
        if (moduleMethod.selector != 43) {
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

    public static Pair pregexp(Object s) {
        $Stpregexp$Mnspace$Mnsensitive$Qu$St = Boolean.TRUE;
        try {
            return LList.list2(Lit100, lists.car.apply1(pregexpReadPattern(s, Lit73, Integer.valueOf(strings.stringLength((CharSequence) s)))));
        } catch (ClassCastException e) {
            throw new WrongType(e, "string-length", 1, s);
        }
    }

    public static Object pregexpMatchPositions$V(Object pat, Object str, Object[] argsArray) {
        Object start;
        Object end;
        LList opt$Mnargs = LList.makeList(argsArray, 0);
        if (strings.isString(pat)) {
            pat = pregexp(pat);
        } else if (!lists.isPair(pat)) {
            pregexpError$V(new Object[]{Lit114, Lit115, pat});
        }
        try {
            int str$Mnlen = strings.stringLength((CharSequence) str);
            if (lists.isNull(opt$Mnargs)) {
                start = Lit73;
            } else {
                start = lists.car.apply1(opt$Mnargs);
                Object apply1 = lists.cdr.apply1(opt$Mnargs);
                try {
                    opt$Mnargs = (LList) apply1;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "opt-args", -2, apply1);
                }
            }
            if (lists.isNull(opt$Mnargs)) {
                end = Integer.valueOf(str$Mnlen);
            } else {
                end = lists.car.apply1(opt$Mnargs);
            }
            Object i = start;
            while (true) {
                Object apply2 = Scheme.numLEq.apply2(i, end);
                try {
                    boolean x = ((Boolean) apply2).booleanValue();
                    if (!x) {
                        return x ? Boolean.TRUE : Boolean.FALSE;
                    }
                    Object x2 = pregexpMatchPositionsAux(pat, str, Integer.valueOf(str$Mnlen), start, end, i);
                    if (x2 != Boolean.FALSE) {
                        return x2;
                    }
                    i = AddOp.$Pl.apply2(i, Lit8);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "x", -2, apply2);
                }
            }
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "string-length", 1, str);
        }
    }

    public static Object pregexpMatch$V(Object pat, Object str, Object[] argsArray) {
        Object ix$Mnprs = Scheme.apply.apply4(pregexp$Mnmatch$Mnpositions, pat, str, LList.makeList(argsArray, 0));
        if (ix$Mnprs == Boolean.FALSE) {
            return ix$Mnprs;
        }
        Object obj = LList.Empty;
        Object arg0 = ix$Mnprs;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                Object arg03 = arg02.getCdr();
                Object ix$Mnpr = arg02.getCar();
                if (ix$Mnpr != Boolean.FALSE) {
                    try {
                        CharSequence charSequence = (CharSequence) str;
                        Object apply1 = lists.car.apply1(ix$Mnpr);
                        try {
                            int intValue = ((Number) apply1).intValue();
                            Object apply12 = lists.cdr.apply1(ix$Mnpr);
                            try {
                                ix$Mnpr = strings.substring(charSequence, intValue, ((Number) apply12).intValue());
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "substring", 3, apply12);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "substring", 2, apply1);
                        }
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "substring", 1, str);
                    }
                }
                obj = Pair.make(ix$Mnpr, obj);
                arg0 = arg03;
            } catch (ClassCastException e4) {
                throw new WrongType(e4, "arg0", -2, arg0);
            }
        }
        return LList.reverseInPlace(obj);
    }

    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        switch (moduleMethod.selector) {
            case 17:
                return pregexpError$V(objArr);
            case 30:
                return pregexpStringMatch(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            case 42:
                return pregexpMatchPositionsAux(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            case 45:
                Object obj = objArr[0];
                Object obj2 = objArr[1];
                int length = objArr.length - 2;
                Object[] objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return pregexpMatchPositions$V(obj, obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 46:
                Object obj3 = objArr[0];
                Object obj4 = objArr[1];
                int length2 = objArr.length - 2;
                Object[] objArr3 = new Object[length2];
                while (true) {
                    length2--;
                    if (length2 < 0) {
                        return pregexpMatch$V(obj3, obj4, objArr3);
                    }
                    objArr3[length2] = objArr[length2 + 2];
                }
            default:
                return super.applyN(moduleMethod, objArr);
        }
    }

    public static Object pregexpSplit(Object pat, Object str) {
        try {
            int n = strings.stringLength((CharSequence) str);
            Object obj = Lit73;
            LList lList = LList.Empty;
            Boolean bool = Boolean.FALSE;
            while (Scheme.numGEq.apply2(obj, Integer.valueOf(n)) == Boolean.FALSE) {
                Object temp = pregexpMatchPositions$V(pat, str, new Object[]{obj, Integer.valueOf(n)});
                if (temp != Boolean.FALSE) {
                    Object jk = lists.car.apply1(temp);
                    Object j = lists.car.apply1(jk);
                    Object k = lists.cdr.apply1(jk);
                    if (Scheme.numEqu.apply2(j, k) != Boolean.FALSE) {
                        Object i = AddOp.$Pl.apply2(k, Lit8);
                        try {
                            CharSequence charSequence = (CharSequence) str;
                            try {
                                int intValue = ((Number) obj).intValue();
                                Object apply2 = AddOp.$Pl.apply2(j, Lit8);
                                try {
                                    lList = lists.cons(strings.substring(charSequence, intValue, ((Number) apply2).intValue()), lList);
                                    bool = Boolean.TRUE;
                                    obj = i;
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "substring", 3, apply2);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "substring", 2, obj);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "substring", 1, str);
                        }
                    } else {
                        Object apply22 = Scheme.numEqu.apply2(j, obj);
                        try {
                            boolean x = ((Boolean) apply22).booleanValue();
                            if (!x ? x : bool != Boolean.FALSE) {
                                bool = Boolean.FALSE;
                                obj = k;
                            } else {
                                try {
                                    try {
                                        try {
                                            lList = lists.cons(strings.substring((CharSequence) str, ((Number) obj).intValue(), ((Number) j).intValue()), lList);
                                            bool = Boolean.FALSE;
                                            obj = k;
                                        } catch (ClassCastException e4) {
                                            throw new WrongType(e4, "substring", 3, j);
                                        }
                                    } catch (ClassCastException e5) {
                                        throw new WrongType(e5, "substring", 2, obj);
                                    }
                                } catch (ClassCastException e6) {
                                    throw new WrongType(e6, "substring", 1, str);
                                }
                            }
                        } catch (ClassCastException e7) {
                            throw new WrongType(e7, "x", -2, apply22);
                        }
                    }
                } else {
                    Object valueOf = Integer.valueOf(n);
                    try {
                        try {
                            lList = lists.cons(strings.substring((CharSequence) str, ((Number) obj).intValue(), n), lList);
                            bool = Boolean.FALSE;
                            obj = valueOf;
                        } catch (ClassCastException e8) {
                            throw new WrongType(e8, "substring", 2, obj);
                        }
                    } catch (ClassCastException e9) {
                        throw new WrongType(e9, "substring", 1, str);
                    }
                }
            }
            return pregexpReverse$Ex(lList);
        } catch (ClassCastException e10) {
            throw new WrongType(e10, "string-length", 1, str);
        }
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 33:
                return isPregexpCheckIfInCharClass(obj, obj2);
            case 34:
                return pregexpListRef(obj, obj2);
            case 47:
                return pregexpSplit(obj, obj2);
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    public static Object pregexpReplace(Object pat, Object str, Object ins) {
        try {
            int n = strings.stringLength((CharSequence) str);
            Object pp = pregexpMatchPositions$V(pat, str, new Object[]{Lit73, Integer.valueOf(n)});
            if (pp == Boolean.FALSE) {
                return str;
            }
            try {
                int ins$Mnlen = strings.stringLength((CharSequence) ins);
                Object m$Mni = lists.caar.apply1(pp);
                Object m$Mnn = lists.cdar.apply1(pp);
                Object[] objArr = new Object[3];
                try {
                    try {
                        objArr[0] = strings.substring((CharSequence) str, 0, ((Number) m$Mni).intValue());
                        objArr[1] = pregexpReplaceAux(str, ins, Integer.valueOf(ins$Mnlen), pp);
                        try {
                            try {
                                objArr[2] = strings.substring((CharSequence) str, ((Number) m$Mnn).intValue(), n);
                                return strings.stringAppend(objArr);
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "substring", 2, m$Mnn);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "substring", 1, str);
                        }
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "substring", 3, m$Mni);
                    }
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "substring", 1, str);
                }
            } catch (ClassCastException e5) {
                throw new WrongType(e5, "string-length", 1, ins);
            }
        } catch (ClassCastException e6) {
            throw new WrongType(e6, "string-length", 1, str);
        }
    }

    public static Object pregexpReplace$St(Object pat, Object str, Object ins) {
        if (strings.isString(pat)) {
            pat = pregexp(pat);
        }
        try {
            int n = strings.stringLength((CharSequence) str);
            try {
                int ins$Mnlen = strings.stringLength((CharSequence) ins);
                Object obj = Lit73;
                Object obj2 = "";
                while (Scheme.numGEq.apply2(obj, Integer.valueOf(n)) == Boolean.FALSE) {
                    Object pp = pregexpMatchPositions$V(pat, str, new Object[]{obj, Integer.valueOf(n)});
                    if (pp == Boolean.FALSE) {
                        if (Scheme.numEqu.apply2(obj, Lit73) == Boolean.FALSE) {
                            Object[] objArr = new Object[2];
                            objArr[0] = obj2;
                            try {
                                try {
                                    objArr[1] = strings.substring((CharSequence) str, ((Number) obj).intValue(), n);
                                    str = strings.stringAppend(objArr);
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "substring", 2, obj);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "substring", 1, str);
                            }
                        }
                        return str;
                    }
                    Object i = lists.cdar.apply1(pp);
                    Object[] objArr2 = new Object[3];
                    objArr2[0] = obj2;
                    try {
                        CharSequence charSequence = (CharSequence) str;
                        try {
                            int intValue = ((Number) obj).intValue();
                            Object apply1 = lists.caar.apply1(pp);
                            try {
                                objArr2[1] = strings.substring(charSequence, intValue, ((Number) apply1).intValue());
                                objArr2[2] = pregexpReplaceAux(str, ins, Integer.valueOf(ins$Mnlen), pp);
                                obj2 = strings.stringAppend(objArr2);
                                obj = i;
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "substring", 3, apply1);
                            }
                        } catch (ClassCastException e4) {
                            throw new WrongType(e4, "substring", 2, obj);
                        }
                    } catch (ClassCastException e5) {
                        throw new WrongType(e5, "substring", 1, str);
                    }
                }
                return obj2;
            } catch (ClassCastException e6) {
                throw new WrongType(e6, "string-length", 1, ins);
            }
        } catch (ClassCastException e7) {
            throw new WrongType(e7, "string-length", 1, str);
        }
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        switch (moduleMethod.selector) {
            case 18:
                return pregexpReadPattern(obj, obj2, obj3);
            case 19:
                return pregexpReadBranch(obj, obj2, obj3);
            case 20:
                return pregexpReadPiece(obj, obj2, obj3);
            case 21:
                return pregexpReadEscapedNumber(obj, obj2, obj3);
            case 22:
                return pregexpReadEscapedChar(obj, obj2, obj3);
            case 23:
                return pregexpReadPosixCharClass(obj, obj2, obj3);
            case 24:
                return pregexpReadClusterType(obj, obj2, obj3);
            case 25:
                return pregexpReadSubpattern(obj, obj2, obj3);
            case 26:
                return pregexpWrapQuantifierIfAny(obj, obj2, obj3);
            case 27:
                return pregexpReadNums(obj, obj2, obj3);
            case 29:
                return pregexpReadCharList(obj, obj2, obj3);
            case 32:
                return isPregexpAtWordBoundary(obj, obj2, obj3);
            case 48:
                return pregexpReplace(obj, obj2, obj3);
            case 49:
                return pregexpReplace$St(obj, obj2, obj3);
            default:
                return super.apply3(moduleMethod, obj, obj2, obj3);
        }
    }

    public static Object pregexpQuote(Object s) {
        LList cons;
        try {
            Object valueOf = Integer.valueOf(strings.stringLength((CharSequence) s) - 1);
            LList lList = LList.Empty;
            while (Scheme.numLss.apply2(valueOf, Lit73) == Boolean.FALSE) {
                Object i = AddOp.$Mn.apply2(valueOf, Lit8);
                try {
                    try {
                        char c = strings.stringRef((CharSequence) s, ((Number) valueOf).intValue());
                        if (lists.memv(Char.make(c), Lit116) != Boolean.FALSE) {
                            cons = lists.cons(Lit19, lists.cons(Char.make(c), lList));
                        } else {
                            cons = lists.cons(Char.make(c), lList);
                        }
                        lList = cons;
                        valueOf = i;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "string-ref", 2, valueOf);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "string-ref", 1, s);
                }
            }
            try {
                return strings.list$To$String(lList);
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "list->string", 1, (Object) lList);
            }
        } catch (ClassCastException e4) {
            throw new WrongType(e4, "string-length", 1, s);
        }
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 16:
                return pregexpReverse$Ex(obj);
            case 28:
                return pregexpInvertCharList(obj);
            case 31:
                return isPregexpCharWord(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 35:
                return pregexpMakeBackrefList(obj);
            case 44:
                return pregexp(obj);
            case 50:
                return pregexpQuote(obj);
            default:
                return super.apply1(moduleMethod, obj);
        }
    }
}
