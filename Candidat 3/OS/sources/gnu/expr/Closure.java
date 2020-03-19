package gnu.expr;

import gnu.bytecode.ArrayType;
import gnu.bytecode.Type;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import java.lang.reflect.Array;

/* compiled from: LambdaExp */
class Closure extends MethodProc {
    Object[][] evalFrames;
    LambdaExp lambda;

    public int numArgs() {
        return this.lambda.min_args | (this.lambda.max_args << 12);
    }

    public Closure(LambdaExp lexp, CallContext ctx) {
        this.lambda = lexp;
        Object[][] oldFrames = ctx.evalFrames;
        if (oldFrames != null) {
            int n = oldFrames.length;
            while (n > 0 && oldFrames[n - 1] == null) {
                n--;
            }
            this.evalFrames = new Object[n][];
            System.arraycopy(oldFrames, 0, this.evalFrames, 0, n);
        }
        setSymbol(this.lambda.getSymbol());
    }

    public int match0(CallContext ctx) {
        return matchN(new Object[0], ctx);
    }

    public int match1(Object arg1, CallContext ctx) {
        return matchN(new Object[]{arg1}, ctx);
    }

    public int match2(Object arg1, Object arg2, CallContext ctx) {
        return matchN(new Object[]{arg1, arg2}, ctx);
    }

    public int match3(Object arg1, Object arg2, Object arg3, CallContext ctx) {
        return matchN(new Object[]{arg1, arg2, arg3}, ctx);
    }

    public int match4(Object arg1, Object arg2, Object arg3, Object arg4, CallContext ctx) {
        return matchN(new Object[]{arg1, arg2, arg3, arg4}, ctx);
    }

    /* JADX WARNING: type inference failed for: r32v0, types: [java.lang.Object[]] */
    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r8v0, types: [java.lang.Object[]] */
    /* JADX WARNING: type inference failed for: r0v50, types: [java.lang.Object[]] */
    /* JADX WARNING: type inference failed for: r28v0, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r0v51 */
    /* JADX WARNING: type inference failed for: r28v1 */
    /* JADX WARNING: type inference failed for: r28v2, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r0v59, types: [java.lang.Object[]] */
    /* JADX WARNING: type inference failed for: r28v3, types: [gnu.lists.LList] */
    /* JADX WARNING: type inference failed for: r28v4, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r29v32 */
    /* JADX WARNING: type inference failed for: r0v64, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r0v65, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r28v5 */
    /* JADX WARNING: type inference failed for: r0v67, types: [java.lang.Object[]] */
    /* JADX WARNING: type inference failed for: r27v0 */
    /* JADX WARNING: type inference failed for: r0v68, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r1v8, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r28v6 */
    /* JADX WARNING: type inference failed for: r28v7, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r28v8 */
    /* JADX WARNING: type inference failed for: r28v9 */
    /* JADX WARNING: type inference failed for: r28v10 */
    /* JADX WARNING: type inference failed for: r28v11 */
    /* JADX WARNING: type inference failed for: r28v12 */
    /* JADX WARNING: type inference failed for: r18v0, types: [gnu.mapping.Location] */
    /* JADX WARNING: type inference failed for: r0v75, types: [gnu.mapping.Location] */
    /* JADX WARNING: type inference failed for: r1v10, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r28v13 */
    /* JADX WARNING: type inference failed for: r1v11, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r28v14, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r28v15 */
    /* JADX WARNING: type inference failed for: r28v16 */
    /* JADX WARNING: type inference failed for: r28v17 */
    /* JADX WARNING: type inference failed for: r28v18 */
    /* JADX WARNING: type inference failed for: r28v19 */
    /* JADX WARNING: type inference failed for: r28v20 */
    /* JADX WARNING: type inference failed for: r28v21 */
    /* JADX WARNING: type inference failed for: r28v22 */
    /* JADX WARNING: type inference failed for: r28v23 */
    /* JADX WARNING: type inference failed for: r28v24 */
    /* JADX WARNING: type inference failed for: r28v25 */
    /* JADX WARNING: type inference failed for: r28v26 */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Object[], code=null, for r32v0, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r28v1
  assigns: []
  uses: []
  mth insns count: 223
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
    /* JADX WARNING: Unknown variable types count: 28 */
    public int matchN(Object[] r32, CallContext ctx) {
        int opt_args;
        int key_i;
        int i;
        ? r28;
        ? r282;
        ? r283;
        ? r284;
        int num = numArgs();
        int nargs = r32.length;
        int min = num & 4095;
        if (nargs < min) {
            return -983040 | min;
        }
        int max = num >> 12;
        if (nargs > max && max >= 0) {
            return -917504 | max;
        }
        ? r8 = new Object[this.lambda.frameSize];
        int key_args = this.lambda.keywords == null ? 0 : this.lambda.keywords.length;
        if (this.lambda.defaultArgs == null) {
            opt_args = 0;
        } else {
            opt_args = this.lambda.defaultArgs.length - key_args;
        }
        int opt_i = 0;
        int min_args = this.lambda.min_args;
        Declaration decl = this.lambda.firstDecl();
        int key_i2 = 0;
        int i2 = 0;
        while (decl != null) {
            if (i2 < min_args) {
                i = i2 + 1;
                key_i = key_i2;
                r283 = r32[i2];
            } else if (i2 < min_args + opt_args) {
                if (i2 < nargs) {
                    i = i2 + 1;
                    r282 = r32[i2];
                } else {
                    i = i2;
                    r282 = this.lambda.evalDefaultArg(opt_i, ctx);
                }
                opt_i++;
                key_i = key_i2;
                r283 = r282;
            } else if (this.lambda.max_args >= 0 || i2 != min_args + opt_args) {
                key_i = key_i2 + 1;
                ? searchForKeyword = Keyword.searchForKeyword(r32, min_args + opt_args, this.lambda.keywords[key_i2]);
                if (searchForKeyword == Special.dfault) {
                    searchForKeyword = this.lambda.evalDefaultArg(opt_i, ctx);
                }
                opt_i++;
                i = i2;
                r283 = searchForKeyword;
            } else if (decl.type instanceof ArrayType) {
                int rem = nargs - i2;
                Type elementType = ((ArrayType) decl.type).getComponentType();
                if (elementType == Type.objectType) {
                    ? r27 = new Object[rem];
                    System.arraycopy(r32, i2, r27, 0, rem);
                    r28 = r27;
                } else {
                    ? newInstance = Array.newInstance(elementType.getReflectClass(), rem);
                    int j = 0;
                    while (j < rem) {
                        try {
                            Array.set(newInstance, j, elementType.coerceFromObject(r32[i2 + j]));
                            j++;
                        } catch (ClassCastException e) {
                            return -786432 | (i2 + j);
                        }
                    }
                    r28 = newInstance;
                }
                key_i = key_i2;
                i = i2;
                r283 = r28;
            } else {
                key_i = key_i2;
                i = i2;
                r283 = LList.makeList(r32, i2);
            }
            if (decl.type != null) {
                try {
                    r283 = decl.type.coerceFromObject(r283);
                } catch (ClassCastException e2) {
                    return -786432 | i;
                }
            }
            if (decl.isIndirectBinding()) {
                ? makeIndirectLocationFor = decl.makeIndirectLocationFor();
                makeIndirectLocationFor.set(r283);
                r284 = makeIndirectLocationFor;
            } else {
                r284 = r283;
            }
            r8[decl.evalIndex] = r284;
            decl = decl.nextDecl();
            key_i2 = key_i;
            i2 = i;
        }
        ctx.values = r8;
        ctx.where = 0;
        ctx.next = 0;
        ctx.proc = this;
        return 0;
    }

    public void apply(CallContext ctx) throws Throwable {
        int numFrames;
        int level = ScopeExp.nesting(this.lambda);
        Object[] evalFrame = ctx.values;
        Object[][] saveFrames = ctx.evalFrames;
        if (this.evalFrames == null) {
            numFrames = 0;
        } else {
            numFrames = this.evalFrames.length;
        }
        if (level >= numFrames) {
            numFrames = level;
        }
        Object[][] newFrames = new Object[(numFrames + 10)][];
        if (this.evalFrames != null) {
            System.arraycopy(this.evalFrames, 0, newFrames, 0, this.evalFrames.length);
        }
        newFrames[level] = evalFrame;
        ctx.evalFrames = newFrames;
        try {
            if (this.lambda.body == null) {
                StringBuffer sbuf = new StringBuffer("procedure ");
                String name = this.lambda.getName();
                if (name == null) {
                    name = "<anonymous>";
                }
                sbuf.append(name);
                int line = this.lambda.getLineNumber();
                if (line > 0) {
                    sbuf.append(" at line ");
                    sbuf.append(line);
                }
                sbuf.append(" was called before it was expanded");
                throw new RuntimeException(sbuf.toString());
            }
            this.lambda.body.apply(ctx);
        } finally {
            ctx.evalFrames = saveFrames;
        }
    }

    public Object getProperty(Object key, Object defaultValue) {
        Object value = super.getProperty(key, defaultValue);
        if (value == null) {
            return this.lambda.getProperty(key, defaultValue);
        }
        return value;
    }
}
