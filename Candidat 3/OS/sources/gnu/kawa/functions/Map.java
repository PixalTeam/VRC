package gnu.kawa.functions;

import gnu.expr.Declaration;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.Values;

public class Map extends ProcedureN {
    final Declaration applyFieldDecl;
    final ApplyToArgs applyToArgs;
    boolean collect;
    final IsEq isEq;

    public Map(boolean collect2, ApplyToArgs applyToArgs2, Declaration applyFieldDecl2, IsEq isEq2) {
        super(collect2 ? "map" : "for-each");
        this.collect = collect2;
        this.applyToArgs = applyToArgs2;
        this.applyFieldDecl = applyFieldDecl2;
        this.isEq = isEq2;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileMisc:validateApplyMap");
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [gnu.lists.LList] */
    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r3v1, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r0v1, types: [gnu.lists.Pair] */
    /* JADX WARNING: type inference failed for: r1v0, types: [gnu.lists.Pair, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r3v2 */
    /* JADX WARNING: type inference failed for: r0v2 */
    /* JADX WARNING: type inference failed for: r3v3 */
    /* JADX WARNING: type inference failed for: r3v4 */
    /* JADX WARNING: type inference failed for: r0v3 */
    /* JADX WARNING: type inference failed for: r3v5 */
    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r3v2
  assigns: []
  uses: []
  mth insns count: 20
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
    public static Object map1(Procedure proc, Object list) throws Throwable {
        ? r3;
        ? r32 = LList.Empty;
        ? r0 = 0;
        while (list != LList.Empty) {
            Pair pair = (Pair) list;
            ? pair2 = new Pair(proc.apply1(pair.getCar()), LList.Empty);
            if (r0 == 0) {
                r3 = pair2;
            } else {
                r0.setCdr(pair2);
                r3 = r32;
            }
            r0 = pair2;
            list = pair.getCdr();
            r32 = r3;
        }
        return r32;
    }

    public static void forEach1(Procedure proc, Object list) throws Throwable {
        while (list != LList.Empty) {
            Pair pair = (Pair) list;
            proc.apply1(pair.getCar());
            list = pair.getCdr();
        }
    }

    public Object apply2(Object arg1, Object arg2) throws Throwable {
        if (arg1 instanceof Procedure) {
            Procedure proc = (Procedure) arg1;
            if (this.collect) {
                return map1(proc, arg2);
            }
            forEach1(proc, arg2);
            return Values.empty;
        }
        return applyN(new Object[]{arg1, arg2});
    }

    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r10v0, types: [gnu.mapping.Values] */
    /* JADX WARNING: type inference failed for: r10v1 */
    /* JADX WARNING: type inference failed for: r10v2, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r3v1, types: [gnu.lists.Pair] */
    /* JADX WARNING: type inference failed for: r10v3 */
    /* JADX WARNING: type inference failed for: r3v2 */
    /* JADX WARNING: type inference failed for: r6v0, types: [gnu.lists.Pair, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r10v4 */
    /* JADX WARNING: type inference failed for: r3v3 */
    /* JADX WARNING: type inference failed for: r10v5 */
    /* JADX WARNING: type inference failed for: r10v6, types: [gnu.lists.LList] */
    /* JADX WARNING: type inference failed for: r3v4 */
    /* JADX WARNING: type inference failed for: r10v9 */
    /* JADX WARNING: type inference failed for: r10v10 */
    /* JADX WARNING: type inference failed for: r10v11 */
    /* JADX WARNING: type inference failed for: r10v12 */
    /* JADX WARNING: type inference failed for: r3v5 */
    /* JADX WARNING: type inference failed for: r10v13 */
    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: type inference failed for: r10v14 */
    /* JADX WARNING: type inference failed for: r10v15 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r10v1
  assigns: []
  uses: []
  mth insns count: 72
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
    /* JADX WARNING: Unknown variable types count: 10 */
    public Object applyN(Object[] args) throws Throwable {
        ? r10;
        Procedure proc;
        int need_apply;
        Object[] each_args;
        ? r102;
        ? r3;
        ? r103;
        int arity = args.length - 1;
        if (arity != 1 || !(args[0] instanceof Procedure)) {
            ? r32 = 0;
            if (this.collect) {
                r10 = LList.Empty;
            } else {
                r10 = Values.empty;
            }
            Object[] rest = new Object[arity];
            System.arraycopy(args, 1, rest, 0, arity);
            if (args[0] instanceof Procedure) {
                need_apply = 0;
                each_args = new Object[arity];
                proc = args[0];
            } else {
                need_apply = 1;
                each_args = new Object[(arity + 1)];
                each_args[0] = args[0];
                proc = this.applyToArgs;
            }
            ? r33 = r32;
            ? r104 = r10;
            while (true) {
                for (int i = 0; i < arity; i++) {
                    Object list = rest[i];
                    if (list == LList.Empty) {
                        return r104;
                    }
                    Pair pair = (Pair) list;
                    each_args[need_apply + i] = pair.getCar();
                    rest[i] = pair.getCdr();
                }
                Object value = proc.applyN(each_args);
                if (this.collect) {
                    ? pair2 = new Pair(value, LList.Empty);
                    if (r33 == 0) {
                        r103 = pair2;
                    } else {
                        r33.setCdr(pair2);
                        r103 = r104;
                    }
                    r3 = pair2;
                    r102 = r103;
                } else {
                    r102 = r104;
                    r3 = r33;
                }
                r104 = r102;
                r33 = r3;
            }
        } else {
            Procedure proc2 = args[0];
            if (this.collect) {
                return map1(proc2, args[1]);
            }
            forEach1(proc2, args[1]);
            return Values.empty;
        }
    }
}
