package gnu.expr;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassFileInput;
import gnu.bytecode.ClassType;
import gnu.bytecode.ClassTypeWriter;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.kawa.functions.MakeList;
import gnu.kawa.lispexpr.LangObjType;
import gnu.lists.Consumer;
import gnu.lists.ConsumerWriter;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.WrongArguments;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.net.URL;

public class PrimProcedure extends MethodProc implements Inlineable {
    private static ClassLoader systemClassLoader = PrimProcedure.class.getClassLoader();
    Type[] argTypes;
    Member member;
    Method method;
    char mode;
    int op_code;
    Type retType;
    boolean sideEffectFree;
    LambdaExp source;

    public final int opcode() {
        return this.op_code;
    }

    public Type getReturnType() {
        return this.retType;
    }

    public void setReturnType(Type retType2) {
        this.retType = retType2;
    }

    public boolean isSpecial() {
        return this.mode == 'P';
    }

    public Type getReturnType(Expression[] args) {
        return this.retType;
    }

    public Method getMethod() {
        return this.method;
    }

    public boolean isSideEffectFree() {
        return this.sideEffectFree;
    }

    public void setSideEffectFree() {
        this.sideEffectFree = true;
    }

    public boolean takesVarArgs() {
        if (this.method == null) {
            return false;
        }
        if ((this.method.getModifiers() & 128) != 0) {
            return true;
        }
        String name = this.method.getName();
        if (name.endsWith("$V") || name.endsWith("$V$X")) {
            return true;
        }
        return false;
    }

    public boolean takesContext() {
        return this.method != null && takesContext(this.method);
    }

    public static boolean takesContext(Method method2) {
        return method2.getName().endsWith("$X");
    }

    public int isApplicable(Type[] argTypes2) {
        int app = super.isApplicable(argTypes2);
        int nargs = argTypes2.length;
        if (app != -1 || this.method == null || (this.method.getModifiers() & 128) == 0 || nargs <= 0 || !(argTypes2[nargs - 1] instanceof ArrayType)) {
            return app;
        }
        Type[] tmp = new Type[nargs];
        System.arraycopy(argTypes2, 0, tmp, 0, nargs - 1);
        tmp[nargs - 1] = argTypes2[nargs - 1].getComponentType();
        return super.isApplicable(tmp);
    }

    public final boolean isConstructor() {
        return opcode() == 183 && this.mode != 'P';
    }

    public boolean takesTarget() {
        return this.mode != 0;
    }

    public int numArgs() {
        int num = this.argTypes.length;
        if (takesTarget()) {
            num++;
        }
        if (takesContext()) {
            num--;
        }
        return takesVarArgs() ? (num - 1) - 4096 : (num << 12) + num;
    }

    public int match0(CallContext ctx) {
        return matchN(ProcedureN.noArgs, ctx);
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

    public int matchN(Object[] args, CallContext ctx) {
        Object obj;
        Type type;
        int nargs = args.length;
        boolean takesVarArgs = takesVarArgs();
        int fixArgs = minArgs();
        if (nargs < fixArgs) {
            return -983040 | fixArgs;
        }
        if (!takesVarArgs && nargs > fixArgs) {
            return -917504 | fixArgs;
        }
        int paramCount = this.argTypes.length;
        Type elementType = null;
        Object[] restArray = null;
        int extraCount = (takesTarget() || isConstructor()) ? 1 : 0;
        Object[] rargs = new Object[paramCount];
        if (takesContext()) {
            paramCount--;
            rargs[paramCount] = ctx;
        }
        if (takesVarArgs) {
            Type restType = this.argTypes[paramCount - 1];
            if (restType == Compilation.scmListType || restType == LangObjType.listType) {
                rargs[paramCount - 1] = LList.makeList(args, fixArgs);
                int nargs2 = fixArgs;
                elementType = Type.objectType;
            } else {
                elementType = ((ArrayType) restType).getComponentType();
                restArray = (Object[]) Array.newInstance(elementType.getReflectClass(), nargs - fixArgs);
                rargs[paramCount - 1] = restArray;
            }
        }
        if (isConstructor()) {
            obj = args[0];
        } else if (extraCount != 0) {
            try {
                obj = this.method.getDeclaringClass().coerceFromObject(args[0]);
            } catch (ClassCastException e) {
                return -786431;
            }
        } else {
            obj = null;
        }
        for (int i = extraCount; i < args.length; i++) {
            Object arg = args[i];
            if (i < fixArgs) {
                type = this.argTypes[i - extraCount];
            } else {
                type = elementType;
            }
            if (type != Type.objectType) {
                try {
                    arg = type.coerceFromObject(arg);
                } catch (ClassCastException e2) {
                    return -786432 | (i + 1);
                }
            }
            if (i < fixArgs) {
                rargs[i - extraCount] = arg;
            } else if (restArray != null) {
                restArray[i - fixArgs] = arg;
            }
        }
        ctx.value1 = obj;
        ctx.values = rargs;
        ctx.proc = this;
        return 0;
    }

    public void apply(CallContext ctx) throws Throwable {
        Object result;
        int i;
        int arg_count = this.argTypes.length;
        boolean is_constructor = isConstructor();
        boolean slink = is_constructor && this.method.getDeclaringClass().hasOuterLink();
        try {
            if (this.member == null) {
                Class clas = this.method.getDeclaringClass().getReflectClass();
                Class[] paramTypes = new Class[((slink ? 1 : 0) + arg_count)];
                int i2 = arg_count;
                while (true) {
                    i2--;
                    if (i2 < 0) {
                        break;
                    }
                    if (slink) {
                        i = 1;
                    } else {
                        i = 0;
                    }
                    paramTypes[i + i2] = this.argTypes[i2].getReflectClass();
                }
                if (slink) {
                    paramTypes[0] = this.method.getDeclaringClass().getOuterLinkType().getReflectClass();
                }
                if (is_constructor) {
                    this.member = clas.getConstructor(paramTypes);
                } else {
                    if (this.method != Type.clone_method) {
                        this.member = clas.getMethod(this.method.getName(), paramTypes);
                    }
                }
            }
            if (is_constructor) {
                Object[] args = ctx.values;
                if (slink) {
                    int nargs = args.length + 1;
                    Object[] xargs = new Object[nargs];
                    System.arraycopy(args, 0, xargs, 1, nargs - 1);
                    xargs[0] = ((PairClassType) ctx.value1).staticLink;
                    args = xargs;
                }
                result = ((Constructor) this.member).newInstance(args);
            } else {
                if (this.method == Type.clone_method) {
                    Object arr = ctx.value1;
                    Class elClass = arr.getClass().getComponentType();
                    int n = Array.getLength(arr);
                    result = Array.newInstance(elClass, n);
                    System.arraycopy(arr, 0, result, 0, n);
                } else {
                    result = this.retType.coerceToObject(((java.lang.reflect.Method) this.member).invoke(ctx.value1, ctx.values));
                }
            }
            if (!takesContext()) {
                ctx.consumer.writeObject(result);
            }
        } catch (InvocationTargetException ex) {
            throw ex.getTargetException();
        }
    }

    public PrimProcedure(String className, String methodName, int numArgs) {
        this(ClassType.make(className).getDeclaredMethod(methodName, numArgs));
    }

    public PrimProcedure(java.lang.reflect.Method method2, Language language) {
        this(((ClassType) language.getTypeFor(method2.getDeclaringClass())).getMethod(method2), language);
    }

    public PrimProcedure(Method method2) {
        init(method2);
        this.retType = method2.getName().endsWith("$X") ? Type.objectType : method2.getReturnType();
    }

    public PrimProcedure(Method method2, Language language) {
        this(method2, 0, language);
    }

    public PrimProcedure(Method method2, char mode2, Language language) {
        this.mode = mode2;
        init(method2);
        Type[] pTypes = this.argTypes;
        int nTypes = pTypes.length;
        this.argTypes = null;
        int i = nTypes;
        while (true) {
            i--;
            if (i < 0) {
                break;
            }
            Type javaType = pTypes[i];
            Type langType = language.getLangTypeFor(javaType);
            if (javaType != langType) {
                if (this.argTypes == null) {
                    this.argTypes = new Type[nTypes];
                    System.arraycopy(pTypes, 0, this.argTypes, 0, nTypes);
                }
                this.argTypes[i] = langType;
            }
        }
        if (this.argTypes == null) {
            this.argTypes = pTypes;
        }
        if (isConstructor()) {
            this.retType = method2.getDeclaringClass();
        } else if (method2.getName().endsWith("$X")) {
            this.retType = Type.objectType;
        } else {
            this.retType = language.getLangTypeFor(method2.getReturnType());
            if (this.retType == Type.toStringType) {
                this.retType = Type.javalangStringType;
            }
        }
    }

    private void init(Method method2) {
        this.method = method2;
        if ((method2.getModifiers() & 8) != 0) {
            this.op_code = 184;
        } else {
            ClassType mclass = method2.getDeclaringClass();
            if (this.mode == 'P') {
                this.op_code = 183;
            } else {
                this.mode = 'V';
                if ("<init>".equals(method2.getName())) {
                    this.op_code = 183;
                } else if ((mclass.getModifiers() & 512) != 0) {
                    this.op_code = 185;
                } else {
                    this.op_code = 182;
                }
            }
        }
        Type[] mtypes = method2.getParameterTypes();
        if (isConstructor() && method2.getDeclaringClass().hasOuterLink()) {
            int len = mtypes.length - 1;
            Type[] types = new Type[len];
            System.arraycopy(mtypes, 1, types, 0, len);
            mtypes = types;
        }
        this.argTypes = mtypes;
    }

    public PrimProcedure(Method method2, LambdaExp source2) {
        this(method2);
        this.retType = source2.getReturnType();
        this.source = source2;
    }

    public PrimProcedure(int opcode, Type retType2, Type[] argTypes2) {
        this.op_code = opcode;
        this.retType = retType2;
        this.argTypes = argTypes2;
    }

    public static PrimProcedure makeBuiltinUnary(int opcode, Type type) {
        return new PrimProcedure(opcode, type, new Type[]{type});
    }

    public static PrimProcedure makeBuiltinBinary(int opcode, Type type) {
        return new PrimProcedure(opcode, type, new Type[]{type, type});
    }

    public PrimProcedure(int op_code2, ClassType classtype, String name, Type retType2, Type[] argTypes2) {
        char c = 0;
        this.op_code = op_code2;
        this.method = classtype.addMethod(name, op_code2 == 184 ? 8 : 0, argTypes2, retType2);
        this.retType = retType2;
        this.argTypes = argTypes2;
        if (op_code2 != 184) {
            c = 'V';
        }
        this.mode = c;
    }

    public final boolean getStaticFlag() {
        return this.method == null || this.method.getStaticFlag() || isConstructor();
    }

    public final Type[] getParameterTypes() {
        return this.argTypes;
    }

    private void compileArgs(Expression[] args, int startArg, Type thisType, Compilation comp) {
        int fix_arg_count;
        Declaration argDecl;
        Type argTypeForTarget;
        boolean variable = takesVarArgs();
        String name = getName();
        Type arg_type = null;
        CodeAttr code = comp.getCode();
        int skipArg = thisType == Type.voidType ? 1 : 0;
        int arg_count = this.argTypes.length - skipArg;
        if (takesContext()) {
            arg_count--;
        }
        int nargs = args.length - startArg;
        boolean is_static = thisType == null || skipArg != 0;
        boolean createVarargsArrayIfNeeded = false;
        if (variable && (this.method.getModifiers() & 128) != 0 && nargs > 0 && this.argTypes.length > 0) {
            if (nargs == (is_static ? 0 : 1) + arg_count) {
                Type lastType = args[args.length - 1].getType();
                Type lastParam = this.argTypes[this.argTypes.length - 1];
                if ((lastType instanceof ObjectType) && (lastParam instanceof ArrayType) && !(((ArrayType) lastParam).getComponentType() instanceof ArrayType)) {
                    if (!(lastType instanceof ArrayType)) {
                        createVarargsArrayIfNeeded = true;
                    }
                    variable = false;
                }
            }
        }
        if (variable) {
            fix_arg_count = arg_count - (is_static ? 1 : 0);
        } else {
            fix_arg_count = args.length - startArg;
        }
        if (this.source == null) {
            argDecl = null;
        } else {
            argDecl = this.source.firstDecl();
        }
        if (argDecl != null && argDecl.isThisParameter()) {
            argDecl = argDecl.nextDecl();
        }
        int i = 0;
        while (true) {
            if (variable && i == fix_arg_count) {
                Type arg_type2 = this.argTypes[(arg_count - 1) + skipArg];
                if (arg_type2 == Compilation.scmListType || arg_type2 == LangObjType.listType) {
                    MakeList.compile(args, startArg + i, comp);
                } else {
                    code.emitPushInt((args.length - startArg) - fix_arg_count);
                    arg_type = ((ArrayType) arg_type2).getComponentType();
                    code.emitNewArray(arg_type);
                }
            }
            if (i < nargs) {
                boolean createVarargsNow = createVarargsArrayIfNeeded && i + 1 == nargs;
                if (i >= fix_arg_count) {
                    code.emitDup(1);
                    code.emitPushInt(i - fix_arg_count);
                } else {
                    arg_type = (argDecl == null || (!is_static && i <= 0)) ? is_static ? this.argTypes[i + skipArg] : i == 0 ? thisType : this.argTypes[i - 1] : argDecl.getType();
                }
                comp.usedClass(arg_type);
                if (createVarargsNow) {
                    argTypeForTarget = Type.objectType;
                } else {
                    argTypeForTarget = arg_type;
                }
                args[startArg + i].compileNotePosition(comp, this.source == null ? CheckedTarget.getInstance(argTypeForTarget, name, i + 1) : CheckedTarget.getInstance(argTypeForTarget, this.source, i), args[startArg + i]);
                if (createVarargsNow) {
                    Type eltype = ((ArrayType) arg_type).getComponentType();
                    code.emitDup();
                    code.emitInstanceof(arg_type);
                    code.emitIfIntNotZero();
                    code.emitCheckcast(arg_type);
                    code.emitElse();
                    code.emitPushInt(1);
                    code.emitNewArray(eltype);
                    code.emitDupX();
                    code.emitSwap();
                    code.emitPushInt(0);
                    code.emitSwap();
                    eltype.emitCoerceFromObject(code);
                    code.emitArrayStore(arg_type);
                    code.emitFi();
                }
                if (i >= fix_arg_count) {
                    code.emitArrayStore(arg_type);
                }
                if (argDecl != null && (is_static || i > 0)) {
                    argDecl = argDecl.nextDecl();
                }
                i++;
            } else {
                return;
            }
        }
        MakeList.compile(args, startArg + i, comp);
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        ClassType mclass;
        CodeAttr code = comp.getCode();
        if (this.method == null) {
            mclass = null;
        } else {
            mclass = this.method.getDeclaringClass();
        }
        Expression[] args = exp.getArgs();
        if (isConstructor()) {
            if (exp.getFlag(8)) {
                int nargs = args.length;
                comp.letStart();
                Expression[] xargs = new Expression[nargs];
                xargs[0] = args[0];
                for (int i = 1; i < nargs; i++) {
                    Expression argi = args[i];
                    Declaration d = comp.letVariable(null, argi.getType(), argi);
                    d.setCanRead(true);
                    xargs[i] = new ReferenceExp(d);
                }
                comp.letEnter();
                comp.letDone(new ApplyExp(exp.func, xargs)).compile(comp, target);
                return;
            }
            code.emitNew(mclass);
            code.emitDup((Type) mclass);
        }
        String arg_error = WrongArguments.checkArgCount(this, args.length);
        if (arg_error != null) {
            comp.error('e', arg_error);
        }
        if (getStaticFlag()) {
            mclass = null;
        }
        compile(mclass, exp, comp, target);
    }

    /* access modifiers changed from: 0000 */
    public void compile(Type thisType, ApplyExp exp, Compilation comp, Target target) {
        ClassType mclass = null;
        Expression[] args = exp.getArgs();
        CodeAttr code = comp.getCode();
        Type stackType = this.retType;
        int startArg = 0;
        if (isConstructor()) {
            if (this.method != null) {
                mclass = this.method.getDeclaringClass();
            }
            if (mclass.hasOuterLink()) {
                ClassExp.loadSuperStaticLink(args[0], mclass, comp);
            }
            thisType = null;
            startArg = 1;
        } else if (opcode() == 183 && this.mode == 'P' && "<init>".equals(this.method.getName())) {
            if (this.method != null) {
                mclass = this.method.getDeclaringClass();
            }
            if (mclass.hasOuterLink()) {
                code.emitPushThis();
                code.emitLoad(code.getCurrentScope().getVariable(1));
                thisType = null;
                startArg = 1;
            }
        } else if (takesTarget() && this.method.getStaticFlag()) {
            startArg = 1;
        }
        compileArgs(args, startArg, thisType, comp);
        if (this.method == null) {
            code.emitPrimop(opcode(), args.length, this.retType);
            target.compileFromStack(comp, stackType);
            return;
        }
        compileInvoke(comp, this.method, target, exp.isTailCall(), this.op_code, stackType);
    }

    public static void compileInvoke(Compilation comp, Method method2, Target target, boolean isTailCall, int op_code2, Type stackType) {
        CodeAttr code = comp.getCode();
        comp.usedClass(method2.getDeclaringClass());
        comp.usedClass(method2.getReturnType());
        if (!takesContext(method2)) {
            code.emitInvokeMethod(method2, op_code2);
        } else if ((target instanceof IgnoreTarget) || ((target instanceof ConsumerTarget) && ((ConsumerTarget) target).isContextTarget())) {
            Field consumerFld = null;
            Variable saveCallContext = null;
            comp.loadCallContext();
            if (target instanceof IgnoreTarget) {
                ClassType typeCallContext = Compilation.typeCallContext;
                consumerFld = typeCallContext.getDeclaredField("consumer");
                code.pushScope();
                saveCallContext = code.addLocal(typeCallContext);
                code.emitDup();
                code.emitGetField(consumerFld);
                code.emitStore(saveCallContext);
                code.emitDup();
                code.emitGetStatic(ClassType.make("gnu.lists.VoidConsumer").getDeclaredField("instance"));
                code.emitPutField(consumerFld);
            }
            code.emitInvokeMethod(method2, op_code2);
            if (isTailCall) {
                comp.loadCallContext();
                code.emitInvoke(Compilation.typeCallContext.getDeclaredMethod("runUntilDone", 0));
            }
            if (target instanceof IgnoreTarget) {
                comp.loadCallContext();
                code.emitLoad(saveCallContext);
                code.emitPutField(consumerFld);
                code.popScope();
                return;
            }
            return;
        } else {
            comp.loadCallContext();
            stackType = Type.objectType;
            code.pushScope();
            Variable saveIndex = code.addLocal(Type.intType);
            comp.loadCallContext();
            code.emitInvokeVirtual(Compilation.typeCallContext.getDeclaredMethod("startFromContext", 0));
            code.emitStore(saveIndex);
            code.emitWithCleanupStart();
            code.emitInvokeMethod(method2, op_code2);
            code.emitWithCleanupCatch(null);
            comp.loadCallContext();
            code.emitLoad(saveIndex);
            code.emitInvokeVirtual(Compilation.typeCallContext.getDeclaredMethod("cleanupFromContext", 1));
            code.emitWithCleanupDone();
            comp.loadCallContext();
            code.emitLoad(saveIndex);
            code.emitInvokeVirtual(Compilation.typeCallContext.getDeclaredMethod("getFromContext", 1));
            code.popScope();
        }
        target.compileFromStack(comp, stackType);
    }

    public Type getParameterType(int index) {
        if (takesTarget()) {
            if (index != 0) {
                index--;
            } else if (isConstructor()) {
                return Type.objectType;
            } else {
                return this.method.getDeclaringClass();
            }
        }
        int lenTypes = this.argTypes.length;
        if (index < lenTypes - 1) {
            return this.argTypes[index];
        }
        boolean varArgs = takesVarArgs();
        if (index < lenTypes && !varArgs) {
            return this.argTypes[index];
        }
        Type restType = this.argTypes[lenTypes - 1];
        if (restType instanceof ArrayType) {
            return ((ArrayType) restType).getComponentType();
        }
        return Type.objectType;
    }

    public static PrimProcedure getMethodFor(Procedure pproc, Expression[] args) {
        return getMethodFor(pproc, (Declaration) null, args, Language.getDefaultLanguage());
    }

    public static PrimProcedure getMethodFor(Procedure pproc, Declaration decl, Expression[] args, Language language) {
        int nargs = args.length;
        Type[] atypes = new Type[nargs];
        int i = nargs;
        while (true) {
            i--;
            if (i < 0) {
                return getMethodFor(pproc, decl, atypes, language);
            }
            atypes[i] = args[i].getType();
        }
    }

    /* JADX WARNING: type inference failed for: r8v0, types: [gnu.mapping.Procedure] */
    /* JADX WARNING: type inference failed for: r8v1, types: [java.lang.Object, gnu.mapping.Procedure] */
    /* JADX WARNING: type inference failed for: r5v2 */
    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r3v0, types: [gnu.mapping.MethodProc[]] */
    /* JADX WARNING: type inference failed for: r8v2 */
    /* JADX WARNING: type inference failed for: r8v3 */
    /* JADX WARNING: type inference failed for: r7v4, types: [gnu.mapping.MethodProc] */
    /* JADX WARNING: type inference failed for: r8v4 */
    /* JADX WARNING: type inference failed for: r8v5 */
    /* JADX WARNING: type inference failed for: r8v6 */
    /* JADX WARNING: type inference failed for: r8v7 */
    /* JADX WARNING: type inference failed for: r8v8 */
    /* JADX WARNING: Incorrect type for immutable var: ssa=gnu.mapping.Procedure, code=null, for r8v0, types: [gnu.mapping.Procedure] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r8v4
  assigns: []
  uses: []
  mth insns count: 35
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
    /* JADX WARNING: Unknown variable types count: 7 */
    public static PrimProcedure getMethodFor(Procedure r8, Declaration decl, Type[] atypes, Language language) {
        if (r8 instanceof GenericProc) {
            GenericProc gproc = (GenericProc) r8;
            ? r3 = gproc.methods;
            int i = gproc.count;
            r8 = 0;
            while (true) {
                i--;
                if (i >= 0) {
                    if (r3[i].isApplicable(atypes) >= 0) {
                        if (r8 != 0) {
                            return null;
                        }
                        r8 = r3[i];
                    }
                    r8 = r8;
                } else if (r8 == 0) {
                    return null;
                }
            }
        }
        if (r8 instanceof PrimProcedure) {
            PrimProcedure prproc = (PrimProcedure) r8;
            if (prproc.isApplicable(atypes) >= 0) {
                return prproc;
            }
        }
        Class pclass = getProcedureClass(r8);
        if (pclass == null) {
            return null;
        }
        return getMethodFor((ClassType) Type.make(pclass), r8.getName(), decl, atypes, language);
    }

    public static void disassemble$X(Procedure pproc, CallContext ctx) throws Exception {
        Consumer cons = ctx.consumer;
        disassemble(pproc, cons instanceof Writer ? (Writer) cons : new ConsumerWriter(cons));
    }

    public static void disassemble(Procedure proc, Writer out) throws Exception {
        disassemble(proc, new ClassTypeWriter((ClassType) null, out, 0));
    }

    public static void disassemble(Procedure proc, ClassTypeWriter cwriter) throws Exception {
        if (proc instanceof GenericProc) {
            GenericProc gproc = (GenericProc) proc;
            int n = gproc.getMethodCount();
            cwriter.print("Generic procedure with ");
            cwriter.print(n);
            cwriter.println(n == 1 ? " method." : "methods.");
            for (int i = 0; i < n; i++) {
                Procedure mproc = gproc.getMethod(i);
                if (mproc != null) {
                    cwriter.println();
                    disassemble(mproc, cwriter);
                }
            }
            return;
        }
        String pname = null;
        Class cl = proc.getClass();
        if (proc instanceof ModuleMethod) {
            cl = ((ModuleMethod) proc).module.getClass();
        } else if (proc instanceof PrimProcedure) {
            Method pmethod = ((PrimProcedure) proc).method;
            if (pmethod != null) {
                cl = pmethod.getDeclaringClass().getReflectClass();
                pname = pmethod.getName();
            }
        }
        ClassLoader loader = cl.getClassLoader();
        String cname = cl.getName();
        String rname = cname.replace('.', '/') + ".class";
        ClassType ctype = new ClassType();
        InputStream rin = loader.getResourceAsStream(rname);
        if (rin == null) {
            throw new RuntimeException("missing resource " + rname);
        }
        new ClassFileInput(ctype, rin);
        cwriter.setClass(ctype);
        URL resource = loader.getResource(rname);
        cwriter.print("In class ");
        cwriter.print(cname);
        if (resource != null) {
            cwriter.print(" at ");
            cwriter.print(resource);
        }
        cwriter.println();
        if (pname == null) {
            String pname2 = proc.getName();
            if (pname2 == null) {
                cwriter.println("Anonymous function - unknown method.");
                return;
            }
            pname = Compilation.mangleName(pname2);
        }
        for (Method method2 = ctype.getMethods(); method2 != null; method2 = method2.getNext()) {
            if (method2.getName().equals(pname)) {
                cwriter.printMethod(method2);
            }
        }
        cwriter.flush();
    }

    public static Class getProcedureClass(Object pproc) {
        Class procClass;
        if (pproc instanceof ModuleMethod) {
            procClass = ((ModuleMethod) pproc).module.getClass();
        } else {
            procClass = pproc.getClass();
        }
        try {
            if (procClass.getClassLoader() == systemClassLoader) {
                return procClass;
            }
        } catch (SecurityException e) {
        }
        return null;
    }

    public static PrimProcedure getMethodFor(Class procClass, String name, Declaration decl, Expression[] args, Language language) {
        return getMethodFor((ClassType) Type.make(procClass), name, decl, args, language);
    }

    public static PrimProcedure getMethodFor(ClassType procClass, String name, Declaration decl, Expression[] args, Language language) {
        int nargs = args.length;
        Type[] atypes = new Type[nargs];
        int i = nargs;
        while (true) {
            i--;
            if (i < 0) {
                return getMethodFor(procClass, name, decl, atypes, language);
            }
            atypes[i] = args[i].getType();
        }
    }

    public static PrimProcedure getMethodFor(ClassType procClass, String name, Declaration decl, Type[] atypes, Language language) {
        boolean isApply;
        PrimProcedure best = null;
        int bestCode = -1;
        boolean bestIsApply = false;
        if (name == null) {
            return null;
        }
        try {
            String mangledName = Compilation.mangleName(name);
            String mangledNameV = mangledName + "$V";
            String mangledNameVX = mangledName + "$V$X";
            String mangledNameX = mangledName + "$X";
            boolean applyOk = true;
            for (Method meth = procClass.getDeclaredMethods(); meth != null; meth = meth.getNext()) {
                if ((meth.getModifiers() & 9) == 9 || !(decl == null || decl.base == null)) {
                    String mname = meth.getName();
                    if (mname.equals(mangledName) || mname.equals(mangledNameV) || mname.equals(mangledNameX) || mname.equals(mangledNameVX)) {
                        isApply = false;
                    } else if (applyOk && (mname.equals("apply") || mname.equals("apply$V"))) {
                        isApply = true;
                    }
                    if (!isApply) {
                        applyOk = false;
                        if (bestIsApply) {
                            best = null;
                            bestCode = -1;
                            bestIsApply = false;
                        }
                    }
                    PrimProcedure prproc = new PrimProcedure(meth, language);
                    prproc.setName(name);
                    int code = prproc.isApplicable(atypes);
                    if (code >= 0 && code >= bestCode) {
                        if (code > bestCode) {
                            best = prproc;
                        } else if (best != null) {
                            best = (PrimProcedure) MethodProc.mostSpecific((MethodProc) best, (MethodProc) prproc);
                            if (best == null && bestCode > 0) {
                                return null;
                            }
                        }
                        bestCode = code;
                        bestIsApply = isApply;
                    }
                }
            }
        } catch (SecurityException e) {
        }
        return best;
    }

    public String getName() {
        String name = super.getName();
        if (name != null) {
            return name;
        }
        String name2 = getVerboseName();
        setName(name2);
        return name2;
    }

    public String getVerboseName() {
        StringBuffer buf = new StringBuffer(100);
        if (this.method == null) {
            buf.append("<op ");
            buf.append(this.op_code);
            buf.append('>');
        } else {
            buf.append(this.method.getDeclaringClass().getName());
            buf.append('.');
            buf.append(this.method.getName());
        }
        buf.append('(');
        for (int i = 0; i < this.argTypes.length; i++) {
            if (i > 0) {
                buf.append(',');
            }
            buf.append(this.argTypes[i].getName());
        }
        buf.append(')');
        return buf.toString();
    }

    public String toString() {
        StringBuffer buf = new StringBuffer(100);
        buf.append(this.retType == null ? "<unknown>" : this.retType.getName());
        buf.append(' ');
        buf.append(getVerboseName());
        return buf.toString();
    }

    public void print(PrintWriter ps) {
        ps.print("#<primitive procedure ");
        ps.print(toString());
        ps.print('>');
    }
}
