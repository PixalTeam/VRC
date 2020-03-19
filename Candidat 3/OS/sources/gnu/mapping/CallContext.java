package gnu.mapping;

import android.support.v4.internal.view.SupportMenu;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.math.IntNum;

public class CallContext {
    public static final int ARG_IN_IVALUE1 = 5;
    public static final int ARG_IN_IVALUE2 = 6;
    public static final int ARG_IN_VALUE1 = 1;
    public static final int ARG_IN_VALUE2 = 2;
    public static final int ARG_IN_VALUE3 = 3;
    public static final int ARG_IN_VALUE4 = 4;
    public static final int ARG_IN_VALUES_ARRAY = 0;
    static ThreadLocal currentContext = new ThreadLocal();
    public Consumer consumer = this.vstack;
    public int count;
    public Object[][] evalFrames;
    public int ivalue1;
    public int ivalue2;
    public int next;
    public int pc;
    public Procedure proc;
    public Object value1;
    public Object value2;
    public Object value3;
    public Object value4;
    public Object[] values;
    public ValueStack vstack = new ValueStack();
    public int where;

    public static void setInstance(CallContext ctx) {
        Thread currentThread = Thread.currentThread();
        currentContext.set(ctx);
    }

    public static CallContext getOnlyInstance() {
        return (CallContext) currentContext.get();
    }

    public static CallContext getInstance() {
        CallContext ctx = getOnlyInstance();
        if (ctx != null) {
            return ctx;
        }
        CallContext ctx2 = new CallContext();
        setInstance(ctx2);
        return ctx2;
    }

    /* access modifiers changed from: 0000 */
    public Object getArgAsObject(int i) {
        if (i < 8) {
            switch ((this.where >> (i * 4)) & 15) {
                case 1:
                    return this.value1;
                case 2:
                    return this.value2;
                case 3:
                    return this.value3;
                case 4:
                    return this.value4;
                case 5:
                    return IntNum.make(this.ivalue1);
                case 6:
                    return IntNum.make(this.ivalue2);
            }
        }
        return this.values[i];
    }

    public int getArgCount() {
        return this.count;
    }

    public Object getNextArg() {
        if (this.next >= this.count) {
            throw new WrongArguments(null, this.count);
        }
        int i = this.next;
        this.next = i + 1;
        return getArgAsObject(i);
    }

    public int getNextIntArg() {
        if (this.next >= this.count) {
            throw new WrongArguments(null, this.count);
        }
        int i = this.next;
        this.next = i + 1;
        return ((Number) getArgAsObject(i)).intValue();
    }

    public Object getNextArg(Object defaultValue) {
        if (this.next >= this.count) {
            return defaultValue;
        }
        int i = this.next;
        this.next = i + 1;
        return getArgAsObject(i);
    }

    public int getNextIntArg(int defaultValue) {
        if (this.next >= this.count) {
            return defaultValue;
        }
        int i = this.next;
        this.next = i + 1;
        return ((Number) getArgAsObject(i)).intValue();
    }

    public final Object[] getRestArgsArray(int next2) {
        Object[] args = new Object[(this.count - next2)];
        int i = 0;
        while (next2 < this.count) {
            int i2 = i + 1;
            int next3 = next2 + 1;
            args[i] = getArgAsObject(next2);
            i = i2;
            next2 = next3;
        }
        return args;
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [gnu.lists.LList, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r1v1, types: [gnu.lists.LList] */
    /* JADX WARNING: type inference failed for: r0v1, types: [gnu.lists.Pair] */
    /* JADX WARNING: type inference failed for: r4v0, types: [gnu.lists.Pair, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r0v2 */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r0v3 */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v2
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
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 6 */
    public final LList getRestArgsList(int next2) {
        ? r1;
        ? r3 = LList.Empty;
        ? r12 = r3;
        ? r0 = 0;
        while (next2 < this.count) {
            int next3 = next2 + 1;
            ? pair = new Pair(getArgAsObject(next2), r3);
            if (r0 == 0) {
                r1 = pair;
            } else {
                r0.setCdr(pair);
                r1 = r12;
            }
            r0 = pair;
            next2 = next3;
            r12 = r1;
        }
        return r12;
    }

    public void lastArg() {
        if (this.next < this.count) {
            throw new WrongArguments(null, this.count);
        }
        this.values = null;
    }

    public Object[] getArgs() {
        if (this.where == 0) {
            return this.values;
        }
        int n = this.count;
        this.next = 0;
        Object[] args = new Object[n];
        for (int i = 0; i < n; i++) {
            args[i] = getNextArg();
        }
        return args;
    }

    public void runUntilDone() throws Throwable {
        while (true) {
            Procedure proc2 = this.proc;
            if (proc2 != null) {
                this.proc = null;
                proc2.apply(this);
            } else {
                return;
            }
        }
    }

    public final int startFromContext() {
        ValueStack vst = this.vstack;
        int oindex = vst.find(this.consumer);
        vst.ensureSpace(3);
        int gapStart = vst.gapStart;
        int gapStart2 = gapStart + 1;
        vst.data[gapStart] = 61698;
        vst.setIntN(gapStart2, oindex);
        int gapStart3 = gapStart2 + 2;
        this.consumer = vst;
        vst.gapStart = gapStart3;
        return gapStart3;
    }

    public final Object getFromContext(int oldIndex) throws Throwable {
        runUntilDone();
        ValueStack vst = this.vstack;
        Object result = Values.make(vst, oldIndex, vst.gapStart);
        cleanupFromContext(oldIndex);
        return result;
    }

    public final void cleanupFromContext(int oldIndex) {
        ValueStack vst = this.vstack;
        char[] data = vst.data;
        int oindex = (data[oldIndex - 2] << 16) | (data[oldIndex - 1] & SupportMenu.USER_MASK);
        this.consumer = (Consumer) vst.objects[oindex];
        vst.objects[oindex] = null;
        vst.oindex = oindex;
        vst.gapStart = oldIndex - 3;
    }

    public final Object runUntilValue() throws Throwable {
        Consumer consumerSave = this.consumer;
        ValueStack vst = this.vstack;
        this.consumer = vst;
        int dindexSave = vst.gapStart;
        int oindexSave = vst.oindex;
        try {
            runUntilDone();
            return Values.make(vst, dindexSave, vst.gapStart);
        } finally {
            this.consumer = consumerSave;
            vst.gapStart = dindexSave;
            vst.oindex = oindexSave;
        }
    }

    public final void runUntilValue(Consumer out) throws Throwable {
        Consumer consumerSave = this.consumer;
        this.consumer = out;
        try {
            runUntilDone();
        } finally {
            this.consumer = consumerSave;
        }
    }

    public void writeValue(Object value) {
        Values.writeValues(value, this.consumer);
    }
}
