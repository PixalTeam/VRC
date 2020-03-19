package gnu.kawa.slib;

import android.support.v4.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;

/* compiled from: XStrings.scm */
public class XStrings extends ModuleBody {
    public static final XStrings $instance = new XStrings();
    static final IntNum Lit0 = IntNum.make((int) ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("substring").readResolve());
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("string-length").readResolve());
    public static final ModuleMethod string$Mnlength;
    public static final ModuleMethod substring;

    static {
        XStrings xStrings = $instance;
        substring = new ModuleMethod(xStrings, 1, Lit1, 12290);
        string$Mnlength = new ModuleMethod(xStrings, 3, Lit2, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $instance.run();
    }

    public XStrings() {
        ModuleInfo.register(this);
    }

    public static Object substring(Object obj, Object obj2) {
        return substring(obj, obj2, Lit0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000a, code lost:
        if (r8 != false) goto L_0x000c;
     */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0047  */
    public static Object substring(Object string, Object start, Object length) {
        boolean x;
        int len;
        int avail;
        int rlen;
        boolean x2;
        if (string == Values.empty) {
            x = true;
        } else {
            x = false;
        }
        if (!x) {
            if (start == Values.empty) {
                x2 = true;
            } else {
                x2 = false;
            }
            if (!x2) {
                try {
                    String s = (String) string;
                    int slen = s.length();
                    try {
                        int index = ((Number) start).intValue() - 1;
                        try {
                            len = ((Number) length).intValue();
                            avail = slen - index;
                            if (len <= avail) {
                                rlen = avail;
                            } else {
                                rlen = len;
                            }
                            return s.substring(index, index + rlen);
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "len", -2, length);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "sindex", -2, start);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "s", -2, string);
                }
            } else {
                String s2 = (String) string;
                int slen2 = s2.length();
                int index2 = ((Number) start).intValue() - 1;
                len = ((Number) length).intValue();
                avail = slen2 - index2;
                if (len <= avail) {
                }
                return s2.substring(index2, index2 + rlen);
            }
        }
        return Values.empty;
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        return moduleMethod.selector == 1 ? substring(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        return moduleMethod.selector == 1 ? substring(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        if (moduleMethod.selector != 1) {
            return super.match2(moduleMethod, obj, obj2, callContext);
        }
        callContext.value1 = obj;
        callContext.value2 = obj2;
        callContext.proc = moduleMethod;
        callContext.pc = 2;
        return 0;
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

    public final void run(CallContext $ctx) {
        Consumer consumer = $ctx.consumer;
    }

    public static Object stringLength(Object string) {
        if (string == Values.empty) {
            return Values.empty;
        }
        return Integer.valueOf(((String) string).length());
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        return moduleMethod.selector == 3 ? stringLength(obj) : super.apply1(moduleMethod, obj);
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
}
