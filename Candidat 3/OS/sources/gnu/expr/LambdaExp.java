package gnu.expr;

import gnu.bytecode.Access;
import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.ExceptionsAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Filter;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.PropertySet;
import gnu.mapping.Values;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongArguments;
import java.util.Set;
import java.util.Vector;

public class LambdaExp extends ScopeExp {
    public static final int ATTEMPT_INLINE = 4096;
    static final int CANNOT_INLINE = 32;
    static final int CAN_CALL = 4;
    static final int CAN_READ = 2;
    static final int CLASS_METHOD = 64;
    static final int DEFAULT_CAPTURES_ARG = 512;
    static final int IMPORTS_LEX_VARS = 8;
    static final int INLINE_ONLY = 8192;
    static final int METHODS_COMPILED = 128;
    static final int NEEDS_STATIC_LINK = 16;
    protected static final int NEXT_AVAIL_FLAG = 16384;
    public static final int NO_FIELD = 256;
    public static final int OVERLOADABLE_FIELD = 2048;
    public static final int SEQUENCE_RESULT = 1024;
    static Method searchForKeywordMethod3;
    static Method searchForKeywordMethod4;
    static final ApplyExp unknownContinuation = new ApplyExp((Expression) null, (Expression[]) null);
    Vector applyMethods;
    Variable argsArray;
    public Expression body;
    Declaration capturedVars;
    Variable closureEnv;
    public Field closureEnvField;
    public Expression[] defaultArgs;
    private Declaration firstArgsArrayArg;
    public LambdaExp firstChild;
    Variable heapFrame;
    Initializer initChain;
    public LambdaExp inlineHome;
    public Keyword[] keywords;
    public int max_args;
    public int min_args;
    public Declaration nameDecl;
    public LambdaExp nextSibling;
    Method[] primBodyMethods;
    Method[] primMethods;
    Object[] properties;
    public Expression returnContinuation;
    public Type returnType;
    int selectorValue;
    public Field staticLinkField;
    Set<LambdaExp> tailCallers;
    Procedure thisValue;
    Variable thisVariable;
    Expression[] throwsSpecification;
    ClassType type = Compilation.typeProcedure;

    public void capture(Declaration decl) {
        if (decl.isSimple()) {
            if (this.capturedVars == null && !decl.isStatic() && !(this instanceof ModuleExp) && !(this instanceof ClassExp)) {
                this.heapFrame = new Variable("heapFrame");
            }
            decl.setSimple(false);
            if (!decl.isPublic()) {
                decl.nextCapturedVar = this.capturedVars;
                this.capturedVars = decl;
            }
        }
    }

    public void setExceptions(Expression[] exceptions) {
        this.throwsSpecification = exceptions;
    }

    public final boolean getInlineOnly() {
        return (this.flags & 8192) != 0;
    }

    public final void setInlineOnly(boolean inlineOnly) {
        setFlag(inlineOnly, 8192);
    }

    public final boolean getNeedsClosureEnv() {
        return (this.flags & 24) != 0;
    }

    public final boolean getNeedsStaticLink() {
        return (this.flags & 16) != 0;
    }

    public final void setNeedsStaticLink(boolean needsStaticLink) {
        if (needsStaticLink) {
            this.flags |= 16;
        } else {
            this.flags &= -17;
        }
    }

    public final boolean getImportsLexVars() {
        return (this.flags & 8) != 0;
    }

    public final void setImportsLexVars(boolean importsLexVars) {
        if (importsLexVars) {
            this.flags |= 8;
        } else {
            this.flags &= -9;
        }
    }

    public final void setImportsLexVars() {
        int old = this.flags;
        this.flags |= 8;
        if ((old & 8) == 0 && this.nameDecl != null) {
            setCallersNeedStaticLink();
        }
    }

    public final void setNeedsStaticLink() {
        int old = this.flags;
        this.flags |= 16;
        if ((old & 16) == 0 && this.nameDecl != null) {
            setCallersNeedStaticLink();
        }
    }

    /* access modifiers changed from: 0000 */
    public void setCallersNeedStaticLink() {
        LambdaExp outer = outerLambda();
        for (ApplyExp app = this.nameDecl.firstCall; app != null; app = app.nextCall) {
            LambdaExp caller = app.context;
            while (caller != outer && !(caller instanceof ModuleExp)) {
                caller.setNeedsStaticLink();
                caller = caller.outerLambda();
            }
        }
    }

    public final boolean getCanRead() {
        return (this.flags & 2) != 0;
    }

    public final void setCanRead(boolean read) {
        if (read) {
            this.flags |= 2;
        } else {
            this.flags &= -3;
        }
    }

    public final boolean getCanCall() {
        return (this.flags & 4) != 0;
    }

    public final void setCanCall(boolean called) {
        if (called) {
            this.flags |= 4;
        } else {
            this.flags &= -5;
        }
    }

    public final boolean isClassMethod() {
        return (this.flags & 64) != 0;
    }

    public final void setClassMethod(boolean isMethod) {
        if (isMethod) {
            this.flags |= 64;
        } else {
            this.flags &= -65;
        }
    }

    public final boolean isModuleBody() {
        return this instanceof ModuleExp;
    }

    public final boolean isClassGenerated() {
        return isModuleBody() || (this instanceof ClassExp);
    }

    public boolean isAbstract() {
        return this.body == QuoteExp.abstractExp;
    }

    public int getCallConvention() {
        if (isModuleBody()) {
            if (Compilation.defaultCallConvention >= 2) {
                return Compilation.defaultCallConvention;
            }
            return 2;
        } else if (isClassMethod()) {
            return 1;
        } else {
            if (Compilation.defaultCallConvention != 0) {
                return Compilation.defaultCallConvention;
            }
            return 1;
        }
    }

    public final boolean isHandlingTailCalls() {
        return isModuleBody() || (Compilation.defaultCallConvention >= 3 && !isClassMethod());
    }

    public final boolean variable_args() {
        return this.max_args < 0;
    }

    /* access modifiers changed from: protected */
    public ClassType getCompiledClassType(Compilation comp) {
        if (this.type != Compilation.typeProcedure) {
            return this.type;
        }
        throw new Error("internal error: getCompiledClassType");
    }

    public Type getType() {
        return this.type;
    }

    public ClassType getClassType() {
        return this.type;
    }

    public void setType(ClassType type2) {
        this.type = type2;
    }

    public int incomingArgs() {
        if (this.min_args != this.max_args || this.max_args > 4 || this.max_args <= 0) {
            return 1;
        }
        return this.max_args;
    }

    /* access modifiers changed from: 0000 */
    public int getSelectorValue(Compilation comp) {
        int s = this.selectorValue;
        if (s != 0) {
            return s;
        }
        int s2 = comp.maxSelectorValue;
        comp.maxSelectorValue = this.primMethods.length + s2;
        int s3 = s2 + 1;
        this.selectorValue = s3;
        return s3;
    }

    public final Method getMethod(int argCount) {
        if (this.primMethods == null) {
            return null;
        }
        if (this.max_args >= 0 && argCount > this.max_args) {
            return null;
        }
        int index = argCount - this.min_args;
        if (index < 0) {
            return null;
        }
        int length = this.primMethods.length;
        Method[] methodArr = this.primMethods;
        if (index >= length) {
            index = length - 1;
        }
        return methodArr[index];
    }

    public final Method getMainMethod() {
        Method[] methods = this.primBodyMethods;
        if (methods == null) {
            return null;
        }
        return methods[methods.length - 1];
    }

    public final Type restArgType() {
        if (this.min_args == this.max_args) {
            return null;
        }
        if (this.primMethods == null) {
            throw new Error("internal error - restArgType");
        }
        Method[] methods = this.primMethods;
        if (this.max_args >= 0 && methods.length > this.max_args - this.min_args) {
            return null;
        }
        Method method = methods[methods.length - 1];
        Type[] types = method.getParameterTypes();
        int ilast = types.length - 1;
        if (method.getName().endsWith("$X")) {
            ilast--;
        }
        return types[ilast];
    }

    public LambdaExp outerLambda() {
        if (this.outer == null) {
            return null;
        }
        return this.outer.currentLambda();
    }

    public LambdaExp outerLambdaNotInline() {
        ScopeExp exp = this;
        while (true) {
            exp = exp.outer;
            if (exp == null) {
                return null;
            }
            if (exp instanceof LambdaExp) {
                LambdaExp result = (LambdaExp) exp;
                if (!result.getInlineOnly()) {
                    return result;
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean inlinedIn(LambdaExp outer) {
        for (LambdaExp exp = this; exp.getInlineOnly(); exp = exp.getCaller()) {
            if (exp == outer) {
                return true;
            }
        }
        return false;
    }

    public LambdaExp getCaller() {
        return this.inlineHome;
    }

    public Variable declareThis(ClassType clas) {
        if (this.thisVariable == null) {
            this.thisVariable = new Variable("this");
            getVarScope().addVariableAfter(null, this.thisVariable);
            this.thisVariable.setParameter(true);
        }
        if (this.thisVariable.getType() == null) {
            this.thisVariable.setType(clas);
        }
        if (this.decls != null && this.decls.isThisParameter()) {
            this.decls.var = this.thisVariable;
        }
        return this.thisVariable;
    }

    public Variable declareClosureEnv() {
        Variable prev;
        if (this.closureEnv == null && getNeedsClosureEnv()) {
            LambdaExp parent = outerLambda();
            if (parent instanceof ClassExp) {
                parent = parent.outerLambda();
            }
            Variable parentFrame = parent.heapFrame != null ? parent.heapFrame : parent.closureEnv;
            if (isClassMethod() && !"*init*".equals(getName())) {
                this.closureEnv = declareThis(this.type);
            } else if (parent.heapFrame == null && !parent.getNeedsStaticLink() && !(parent instanceof ModuleExp)) {
                this.closureEnv = null;
            } else if (!isClassGenerated() && !getInlineOnly()) {
                Method primMethod = getMainMethod();
                boolean isInit = "*init*".equals(getName());
                if (primMethod.getStaticFlag() || isInit) {
                    this.closureEnv = new Variable("closureEnv", primMethod.getParameterTypes()[0]);
                    if (isInit) {
                        prev = declareThis(primMethod.getDeclaringClass());
                    } else {
                        prev = null;
                    }
                    getVarScope().addVariableAfter(prev, this.closureEnv);
                    this.closureEnv.setParameter(true);
                } else {
                    this.closureEnv = declareThis(primMethod.getDeclaringClass());
                }
            } else if (inlinedIn(parent)) {
                this.closureEnv = parentFrame;
            } else {
                this.closureEnv = new Variable("closureEnv", parentFrame.getType());
                getVarScope().addVariable(this.closureEnv);
            }
        }
        return this.closureEnv;
    }

    public LambdaExp() {
    }

    public LambdaExp(int args) {
        this.min_args = args;
        this.max_args = args;
    }

    public LambdaExp(Expression body2) {
        this.body = body2;
    }

    public void loadHeapFrame(Compilation comp) {
        ClassType curType;
        LambdaExp curLambda = comp.curLambda;
        while (curLambda != this && curLambda.getInlineOnly()) {
            curLambda = curLambda.getCaller();
        }
        CodeAttr code = comp.getCode();
        if (curLambda.heapFrame == null || this != curLambda) {
            if (curLambda.closureEnv != null) {
                code.emitLoad(curLambda.closureEnv);
                curType = (ClassType) curLambda.closureEnv.getType();
            } else {
                code.emitPushThis();
                curType = comp.curClass;
            }
            while (curLambda != this) {
                Field link = curLambda.staticLinkField;
                if (link != null && link.getDeclaringClass() == curType) {
                    code.emitGetField(link);
                    curType = (ClassType) link.getType();
                }
                curLambda = curLambda.outerLambda();
            }
            return;
        }
        code.emitLoad(curLambda.heapFrame);
    }

    /* access modifiers changed from: 0000 */
    public Declaration getArg(int i) {
        for (Declaration var = firstDecl(); var != null; var = var.nextDecl()) {
            if (i == 0) {
                return var;
            }
            i--;
        }
        throw new Error("internal error - getArg");
    }

    public void compileEnd(Compilation comp) {
        CodeAttr code = comp.getCode();
        if (!getInlineOnly()) {
            if (comp.method.reachableHere() && (Compilation.defaultCallConvention < 3 || isModuleBody() || isClassMethod() || isHandlingTailCalls())) {
                code.emitReturn();
            }
            popScope(code);
            code.popScope();
        }
        for (LambdaExp child = this.firstChild; child != null; child = child.nextSibling) {
            if (!child.getCanRead() && !child.getInlineOnly()) {
                child.compileAsMethod(comp);
            }
        }
        if (this.heapFrame != null) {
            comp.generateConstructor(this);
        }
    }

    public void generateApplyMethods(Compilation comp) {
        comp.generateMatchMethods(this);
        if (Compilation.defaultCallConvention >= 2) {
            comp.generateApplyMethodsWithContext(this);
        } else {
            comp.generateApplyMethodsWithoutContext(this);
        }
    }

    /* access modifiers changed from: 0000 */
    public Field allocFieldFor(Compilation comp) {
        if (this.nameDecl != null && this.nameDecl.field != null) {
            return this.nameDecl.field;
        }
        boolean needsClosure = getNeedsClosureEnv();
        ClassType frameType = needsClosure ? getOwningLambda().getHeapFrameType() : comp.mainClass;
        String name = getName();
        String fname = name == null ? "lambda" : Compilation.mangleNameIfNeeded(name);
        int fflags = 16;
        if (this.nameDecl == null || !(this.nameDecl.context instanceof ModuleExp)) {
            StringBuilder append = new StringBuilder().append(fname).append("$Fn");
            int i = comp.localFieldIndex + 1;
            comp.localFieldIndex = i;
            fname = append.append(i).toString();
            if (!needsClosure) {
                fflags = 16 | 8;
            }
        } else {
            boolean external_access = this.nameDecl.needsExternalAccess();
            if (external_access) {
                fname = Declaration.PRIVATE_PREFIX + fname;
            }
            if (this.nameDecl.getFlag(2048)) {
                fflags = 16 | 8;
                if (!((ModuleExp) this.nameDecl.context).isStatic()) {
                    fflags &= -17;
                }
            }
            if (!this.nameDecl.isPrivate() || external_access || comp.immediate) {
                fflags |= 1;
            }
            if ((this.flags & 2048) != 0) {
                String fname0 = fname;
                int suffix = this.min_args == this.max_args ? this.min_args : 1;
                while (true) {
                    int suffix2 = suffix + 1;
                    fname = fname0 + '$' + suffix;
                    if (frameType.getDeclaredField(fname) == null) {
                        break;
                    }
                    suffix = suffix2;
                }
            }
        }
        Field field = frameType.addField(fname, Compilation.typeModuleMethod, fflags);
        if (this.nameDecl == null) {
            return field;
        }
        this.nameDecl.field = field;
        return field;
    }

    /* access modifiers changed from: 0000 */
    public final void addApplyMethod(Compilation comp, Field field) {
        LambdaExp owner = this;
        if (field == null || !field.getStaticFlag()) {
            do {
                owner = owner.outerLambda();
                if (owner instanceof ModuleExp) {
                    break;
                }
            } while (owner.heapFrame == null);
            if (!owner.getHeapFrameType().getSuperclass().isSubtype(Compilation.typeModuleBody)) {
                owner = comp.getModule();
            }
        } else {
            owner = comp.getModule();
        }
        if (owner.applyMethods == null) {
            owner.applyMethods = new Vector();
        }
        owner.applyMethods.addElement(this);
    }

    public Field compileSetField(Compilation comp) {
        if (this.primMethods == null) {
            allocMethod(outerLambda(), comp);
        }
        Field field = allocFieldFor(comp);
        if (comp.usingCPStyle()) {
            compile(comp, (Type) Type.objectType);
        } else {
            compileAsMethod(comp);
            addApplyMethod(comp, field);
        }
        return new ProcInitializer(this, comp, field).field;
    }

    public void compile(Compilation comp, Target target) {
        if (!(target instanceof IgnoreTarget)) {
            CodeAttr code = comp.getCode();
            LambdaExp outer = outerLambda();
            Type rtype = Compilation.typeModuleMethod;
            if ((this.flags & 256) != 0 || (comp.immediate && (outer instanceof ModuleExp))) {
                if (this.primMethods == null) {
                    allocMethod(outerLambda(), comp);
                }
                compileAsMethod(comp);
                addApplyMethod(comp, null);
                ProcInitializer.emitLoadModuleMethod(this, comp);
            } else {
                Field field = compileSetField(comp);
                if (field.getStaticFlag()) {
                    code.emitGetStatic(field);
                } else {
                    LambdaExp parent = comp.curLambda;
                    code.emitLoad(parent.heapFrame != null ? parent.heapFrame : parent.closureEnv);
                    code.emitGetField(field);
                }
            }
            target.compileFromStack(comp, rtype);
        }
    }

    public ClassType getHeapFrameType() {
        if ((this instanceof ModuleExp) || (this instanceof ClassExp)) {
            return (ClassType) getType();
        }
        return (ClassType) this.heapFrame.getType();
    }

    public LambdaExp getOwningLambda() {
        for (ScopeExp exp = this.outer; exp != null; exp = exp.outer) {
            if ((exp instanceof ModuleExp) || (((exp instanceof ClassExp) && getNeedsClosureEnv()) || ((exp instanceof LambdaExp) && ((LambdaExp) exp).heapFrame != null))) {
                return (LambdaExp) exp;
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void addMethodFor(Compilation comp, ObjectType closureEnvType) {
        ClassType ctype;
        ScopeExp sc = this;
        while (sc != 0 && !(sc instanceof ClassExp)) {
            sc = sc.outer;
        }
        if (sc != 0) {
            ctype = ((ClassExp) sc).instanceType;
        } else {
            ctype = getOwningLambda().getHeapFrameType();
        }
        addMethodFor(ctype, comp, closureEnvType);
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:158:0x0322  */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x0344  */
    /* JADX WARNING: Removed duplicated region for block: B:269:0x0362 A[SYNTHETIC] */
    public void addMethodFor(ClassType ctype, Compilation comp, ObjectType closureEnvType) {
        int numStubs;
        boolean isStatic;
        int i;
        String name = getName();
        LambdaExp outer = outerLambda();
        int key_args = this.keywords == null ? 0 : this.keywords.length;
        int opt_args = this.defaultArgs == null ? 0 : this.defaultArgs.length - key_args;
        if ((this.flags & 512) != 0) {
            numStubs = 0;
        } else {
            numStubs = opt_args;
        }
        boolean varArgs = this.max_args < 0 || this.min_args + numStubs < this.max_args;
        Method[] methods = new Method[(numStubs + 1)];
        this.primBodyMethods = methods;
        if (this.primMethods == null) {
            this.primMethods = methods;
        }
        char isInitMethod = 0;
        if (this.nameDecl != null && this.nameDecl.getFlag(4096)) {
            isStatic = false;
        } else if (this.nameDecl != null && this.nameDecl.getFlag(2048)) {
            isStatic = true;
        } else if (isClassMethod()) {
            if (outer instanceof ClassExp) {
                ClassExp cl = (ClassExp) outer;
                isStatic = cl.isMakingClassPair() && closureEnvType != null;
                if (this == cl.initMethod) {
                    isInitMethod = Access.INNERCLASS_CONTEXT;
                } else {
                    if (this == cl.clinitMethod) {
                        isInitMethod = Access.CLASS_CONTEXT;
                        isStatic = true;
                    }
                }
            } else {
                isStatic = false;
            }
        } else if (this.thisVariable != null || closureEnvType == ctype) {
            isStatic = false;
        } else if (this.nameDecl == null || !(this.nameDecl.context instanceof ModuleExp)) {
            isStatic = true;
        } else {
            ModuleExp mexp = (ModuleExp) this.nameDecl.context;
            isStatic = mexp.getSuperType() == null && mexp.getInterfaces() == null;
        }
        StringBuffer stringBuffer = new StringBuffer(60);
        int mflags = isStatic ? 8 : 0;
        if (this.nameDecl != null) {
            if (this.nameDecl.needsExternalAccess()) {
                mflags |= 1;
            } else {
                short defaultFlag = this.nameDecl.isPrivate() ? (short) 0 : 1;
                if (isClassMethod()) {
                    defaultFlag = this.nameDecl.getAccessFlags(defaultFlag);
                }
                mflags |= defaultFlag;
            }
        }
        if ((!outer.isModuleBody() && !(outer instanceof ClassExp)) || name == null) {
            stringBuffer.append("lambda");
            int i2 = comp.method_counter + 1;
            comp.method_counter = i2;
            stringBuffer.append(i2);
        }
        if (isInitMethod == 'C') {
            stringBuffer.append("<clinit>");
        } else if (getSymbol() != null) {
            stringBuffer.append(Compilation.mangleName(name));
        }
        if (getFlag(1024)) {
            stringBuffer.append("$C");
        }
        boolean withContext = getCallConvention() >= 2 && isInitMethod == 0;
        if (isInitMethod != 0) {
            if (isStatic) {
                mflags = (mflags & -3) + 1;
            } else {
                mflags = (mflags & 2) + 2;
            }
        }
        if (ctype.isInterface() || isAbstract()) {
            mflags |= 1024;
        }
        if (isClassMethod() && (outer instanceof ClassExp) && this.min_args == this.max_args) {
            Method[] inherited = null;
            int iarg = 0;
            Declaration param = firstDecl();
            while (true) {
                if (param == null) {
                    if (this.returnType != null) {
                        break;
                    }
                    if (inherited == null) {
                        final String stringBuffer2 = stringBuffer.toString();
                        inherited = ctype.getMethods(new Filter() {
                            public boolean select(Object value) {
                                Method method = (Method) value;
                                if (method.getName().equals(stringBuffer2) && method.getParameterTypes().length == LambdaExp.this.min_args) {
                                    return true;
                                }
                                return false;
                            }
                        }, 2);
                    }
                    Type type2 = null;
                    i = inherited.length;
                    while (true) {
                        i--;
                        if (i < 0) {
                            Method method = inherited[i];
                            Type ptype = param == null ? method.getReturnType() : method.getParameterTypes()[iarg];
                            if (type2 == null) {
                                type2 = ptype;
                            } else if (ptype != type2) {
                                if (param == null) {
                                    break;
                                }
                            }
                        } else {
                            if (type2 != null) {
                                if (param != null) {
                                    param.setType(type2);
                                } else {
                                    setCoercedReturnType(type2);
                                }
                            }
                            if (param == null) {
                                break;
                            }
                        }
                    }
                } else if (param.isThisParameter()) {
                    iarg--;
                } else {
                    if (param.getFlag(8192)) {
                        continue;
                    }
                    if (inherited == null) {
                    }
                    Type type22 = null;
                    i = inherited.length;
                    while (true) {
                        i--;
                        if (i < 0) {
                        }
                    }
                }
                param = param.nextDecl();
                iarg++;
            }
        }
        Type rtype = (getFlag(1024) || getCallConvention() >= 2) ? Type.voidType : getReturnType().getImplementationType();
        int extraArg = (closureEnvType == null || closureEnvType == ctype) ? 0 : 1;
        int ctxArg = 0;
        if (getCallConvention() >= 2 && isInitMethod == 0) {
            ctxArg = 1;
        }
        int nameBaseLength = stringBuffer.length();
        for (int i3 = 0; i3 <= numStubs; i3++) {
            stringBuffer.setLength(nameBaseLength);
            int plainArgs = this.min_args + i3;
            int numArgs = plainArgs;
            if (i3 == numStubs && varArgs) {
                numArgs++;
            }
            Type[] atypes = new Type[(extraArg + numArgs + ctxArg)];
            if (extraArg > 0) {
                atypes[0] = closureEnvType;
            }
            Declaration var = firstDecl();
            if (var != null && var.isThisParameter()) {
                var = var.nextDecl();
            }
            int itype = 0;
            while (true) {
                int itype2 = itype;
                if (itype2 >= plainArgs) {
                    break;
                }
                itype = itype2 + 1;
                atypes[extraArg + itype2] = var.getType().getImplementationType();
                var = var.nextDecl();
            }
            if (ctxArg != 0) {
                atypes[atypes.length - 1] = Compilation.typeCallContext;
            }
            if (plainArgs < numArgs) {
                Type lastType = var.getType();
                String lastTypeName = lastType.getName();
                if (ctype.getClassfileVersion() < 3211264 || !(lastType instanceof ArrayType)) {
                    stringBuffer.append("$V");
                } else {
                    mflags |= 128;
                }
                if (key_args > 0 || numStubs < opt_args || (!"gnu.lists.LList".equals(lastTypeName) && !(lastType instanceof ArrayType))) {
                    lastType = Compilation.objArrayType;
                    this.argsArray = new Variable("argsArray", Compilation.objArrayType);
                    this.argsArray.setParameter(true);
                }
                this.firstArgsArrayArg = var;
                atypes[atypes.length - (withContext ? 2 : 1)] = lastType;
            }
            if (withContext) {
                stringBuffer.append("$X");
            }
            boolean classSpecified = (outer instanceof ClassExp) || ((outer instanceof ModuleExp) && ((ModuleExp) outer).getFlag(131072));
            String name2 = stringBuffer.toString();
            int renameCount = 0;
            int len = stringBuffer.length();
            while (true) {
                ClassType t = ctype;
                while (t != null) {
                    if (t.getDeclaredMethod(name2, atypes) != null) {
                        stringBuffer.setLength(len);
                        stringBuffer.append('$');
                        renameCount++;
                        stringBuffer.append(renameCount);
                        name2 = stringBuffer.toString();
                    } else if (classSpecified) {
                        break;
                    } else {
                        t = t.getSuperclass();
                    }
                }
                break;
            }
            Method method2 = ctype.addMethod(name2, atypes, rtype, mflags);
            methods[i3] = method2;
            if (this.throwsSpecification != null && this.throwsSpecification.length > 0) {
                int n = this.throwsSpecification.length;
                ClassType[] exceptions = new ClassType[n];
                for (int j = 0; j < n; j++) {
                    ClassType exception = null;
                    Expression throwsExpr = this.throwsSpecification[j];
                    String msg = null;
                    if (throwsExpr instanceof ReferenceExp) {
                        ReferenceExp throwsRef = (ReferenceExp) throwsExpr;
                        Declaration decl = throwsRef.getBinding();
                        if (decl != null) {
                            Expression declValue = decl.getValue();
                            if (declValue instanceof ClassExp) {
                                exception = ((ClassExp) declValue).getCompiledClassType(comp);
                            } else {
                                msg = "throws specification " + decl.getName() + " has non-class lexical binding";
                            }
                        } else {
                            msg = "unknown class " + throwsRef.getName();
                        }
                    } else if (throwsExpr instanceof QuoteExp) {
                        Object value = ((QuoteExp) throwsExpr).getValue();
                        if (value instanceof Class) {
                            value = Type.make((Class) value);
                        }
                        if (value instanceof ClassType) {
                            exception = (ClassType) value;
                        }
                        if (exception != null && !exception.isSubtype(Type.javalangThrowableType)) {
                            msg = exception.getName() + " does not extend Throwable";
                        }
                    }
                    if (exception == null && msg == null) {
                        msg = "invalid throws specification";
                    }
                    if (msg != null) {
                        comp.error('e', msg, throwsExpr);
                        exception = Type.javalangThrowableType;
                    }
                    exceptions[j] = exception;
                }
                new ExceptionsAttr(method2).setExceptions(exceptions);
            }
        }
    }

    public void allocChildClasses(Compilation comp) {
        Method main = getMainMethod();
        if (main != null && !main.getStaticFlag()) {
            declareThis(main.getDeclaringClass());
        }
        Declaration decl = firstDecl();
        while (true) {
            if (decl == this.firstArgsArrayArg && this.argsArray != null) {
                getVarScope().addVariable(this.argsArray);
            }
            if (!getInlineOnly() && getCallConvention() >= 2 && (this.firstArgsArrayArg != null ? !(this.argsArray == null ? decl != this.firstArgsArrayArg.nextDecl() : decl != this.firstArgsArrayArg) : decl == null)) {
                getVarScope().addVariable(null, Compilation.typeCallContext, "$ctx").setParameter(true);
            }
            if (decl == null) {
                declareClosureEnv();
                allocFrame(comp);
                allocChildMethods(comp);
                return;
            }
            if (decl.var == null && (!getInlineOnly() || !decl.ignorable())) {
                if (!decl.isSimple() || decl.isIndirectBinding()) {
                    String vname = Compilation.mangleName(decl.getName()).intern();
                    Variable var = getVarScope().addVariable(null, decl.getType().getImplementationType(), vname);
                    decl.var = var;
                    var.setParameter(true);
                } else {
                    Variable var2 = decl.allocateVariable(null);
                }
            }
            decl = decl.nextDecl();
        }
    }

    /* access modifiers changed from: 0000 */
    public void allocMethod(LambdaExp outer, Compilation comp) {
        ObjectType closureEnvType;
        if (!getNeedsClosureEnv()) {
            closureEnvType = null;
        } else if ((outer instanceof ClassExp) || (outer instanceof ModuleExp)) {
            closureEnvType = outer.getCompiledClassType(comp);
        } else {
            LambdaExp owner = outer;
            while (owner.heapFrame == null) {
                owner = owner.outerLambda();
            }
            closureEnvType = (ClassType) owner.heapFrame.getType();
        }
        addMethodFor(comp, closureEnvType);
    }

    /* access modifiers changed from: 0000 */
    public void allocChildMethods(Compilation comp) {
        ClassType parentFrameType;
        for (LambdaExp child = this.firstChild; child != null; child = child.nextSibling) {
            if (!child.isClassGenerated() && !child.getInlineOnly() && child.nameDecl != null) {
                child.allocMethod(this, comp);
            }
            if (child instanceof ClassExp) {
                ClassExp cl = (ClassExp) child;
                if (cl.getNeedsClosureEnv()) {
                    if ((this instanceof ModuleExp) || (this instanceof ClassExp)) {
                        parentFrameType = (ClassType) getType();
                    } else {
                        parentFrameType = (ClassType) (this.heapFrame != null ? this.heapFrame : this.closureEnv).getType();
                    }
                    Field outerLink = cl.instanceType.setOuterLink(parentFrameType);
                    cl.staticLinkField = outerLink;
                    cl.closureEnvField = outerLink;
                }
            }
        }
    }

    public void allocFrame(Compilation comp) {
        ClassType frameType;
        if (this.heapFrame != null) {
            if ((this instanceof ModuleExp) || (this instanceof ClassExp)) {
                frameType = getCompiledClassType(comp);
            } else {
                frameType = new ClassType(comp.generateClassName("frame"));
                frameType.setSuper(comp.getModuleType());
                comp.addClass(frameType);
            }
            this.heapFrame.setType(frameType);
        }
    }

    /* access modifiers changed from: 0000 */
    public void allocParameters(Compilation comp) {
        CodeAttr code = comp.getCode();
        code.locals.enterScope(getVarScope());
        int line = getLineNumber();
        if (line > 0) {
            code.putLineNumber(getFileName(), line);
        }
        if (this.heapFrame != null) {
            this.heapFrame.allocateLocal(code);
        }
    }

    /* access modifiers changed from: 0000 */
    public void enterFunction(Compilation comp) {
        Variable lookup;
        int opt_i;
        int key_i;
        Type stackType;
        CodeAttr code = comp.getCode();
        getVarScope().noteStartFunction(code);
        if (this.closureEnv != null && !this.closureEnv.isParameter() && !comp.usingCPStyle()) {
            if (!getInlineOnly()) {
                code.emitPushThis();
                Field field = this.closureEnvField;
                if (field == null) {
                    field = outerLambda().closureEnvField;
                }
                code.emitGetField(field);
                code.emitStore(this.closureEnv);
            } else if (!inlinedIn(outerLambda())) {
                outerLambda().loadHeapFrame(comp);
                code.emitStore(this.closureEnv);
            }
        }
        if (!comp.usingCPStyle()) {
            ClassType frameType = this.heapFrame == null ? currentModule().getCompiledClassType(comp) : (ClassType) this.heapFrame.getType();
            for (Declaration decl = this.capturedVars; decl != null; decl = decl.nextCapturedVar) {
                if (decl.field == null) {
                    decl.makeField(frameType, comp, null);
                }
            }
        }
        if (this.heapFrame != null && !comp.usingCPStyle()) {
            ClassType frameType2 = (ClassType) this.heapFrame.getType();
            if (this.closureEnv != null && !(this instanceof ModuleExp)) {
                this.staticLinkField = frameType2.addField("staticLink", this.closureEnv.getType());
            }
            if (!(this instanceof ModuleExp) && !(this instanceof ClassExp)) {
                frameType2.setEnclosingMember(comp.method);
                code.emitNew(frameType2);
                code.emitDup((Type) frameType2);
                code.emitInvokeSpecial(Compilation.getConstructor(frameType2, this));
                if (this.staticLinkField != null) {
                    code.emitDup((Type) frameType2);
                    code.emitLoad(this.closureEnv);
                    code.emitPutField(this.staticLinkField);
                }
                code.emitStore(this.heapFrame);
            }
        }
        Variable argsArray2 = this.argsArray;
        if (this.min_args == this.max_args && this.primMethods == null && getCallConvention() < 2) {
            argsArray2 = null;
        }
        int i = 0;
        int opt_args = this.defaultArgs == null ? 0 : this.defaultArgs.length - (this.keywords == null ? 0 : this.keywords.length);
        if (!(this instanceof ModuleExp)) {
            int plainArgs = -1;
            int defaultStart = 0;
            Method mainMethod = getMainMethod();
            Variable callContextSave = comp.callContextVar;
            Declaration param = firstDecl();
            int key_i2 = 0;
            int opt_i2 = 0;
            while (param != null) {
                if (getCallConvention() < 2) {
                    lookup = null;
                } else {
                    lookup = getVarScope().lookup("$ctx");
                }
                comp.callContextVar = lookup;
                if (param == this.firstArgsArrayArg && argsArray2 != null) {
                    if (this.primMethods != null) {
                        plainArgs = i;
                        defaultStart = plainArgs - this.min_args;
                    } else {
                        plainArgs = 0;
                        defaultStart = 0;
                    }
                }
                if (plainArgs >= 0 || !param.isSimple() || param.isIndirectBinding()) {
                    Type paramType = param.getType();
                    if (plainArgs >= 0) {
                        stackType = Type.objectType;
                    } else {
                        stackType = paramType;
                    }
                    if (!param.isSimple()) {
                        param.loadOwningObject(null, comp);
                    }
                    if (plainArgs < 0) {
                        code.emitLoad(param.getVariable());
                        key_i = key_i2;
                        opt_i = opt_i2;
                    } else if (i < this.min_args) {
                        code.emitLoad(argsArray2);
                        code.emitPushInt(i);
                        code.emitArrayLoad(Type.objectType);
                        key_i = key_i2;
                        opt_i = opt_i2;
                    } else if (i < this.min_args + opt_args) {
                        code.emitPushInt(i - plainArgs);
                        code.emitLoad(argsArray2);
                        code.emitArrayLength();
                        code.emitIfIntLt();
                        code.emitLoad(argsArray2);
                        code.emitPushInt(i - plainArgs);
                        code.emitArrayLoad();
                        code.emitElse();
                        opt_i = opt_i2 + 1;
                        this.defaultArgs[defaultStart + opt_i2].compile(comp, paramType);
                        code.emitFi();
                        key_i = key_i2;
                    } else if (this.max_args >= 0 || i != this.min_args + opt_args) {
                        code.emitLoad(argsArray2);
                        code.emitPushInt((this.min_args + opt_args) - plainArgs);
                        key_i = key_i2 + 1;
                        comp.compileConstant(this.keywords[key_i2]);
                        opt_i = opt_i2 + 1;
                        Expression defaultArg = this.defaultArgs[defaultStart + opt_i2];
                        if (defaultArg instanceof QuoteExp) {
                            if (searchForKeywordMethod4 == null) {
                                searchForKeywordMethod4 = Compilation.scmKeywordType.addMethod("searchForKeyword", new Type[]{Compilation.objArrayType, Type.intType, Type.objectType, Type.objectType}, (Type) Type.objectType, 9);
                            }
                            defaultArg.compile(comp, paramType);
                            code.emitInvokeStatic(searchForKeywordMethod4);
                        } else {
                            if (searchForKeywordMethod3 == null) {
                                searchForKeywordMethod3 = Compilation.scmKeywordType.addMethod("searchForKeyword", new Type[]{Compilation.objArrayType, Type.intType, Type.objectType}, (Type) Type.objectType, 9);
                            }
                            code.emitInvokeStatic(searchForKeywordMethod3);
                            code.emitDup(1);
                            comp.compileConstant(Special.dfault);
                            code.emitIfEq();
                            code.emitPop(1);
                            defaultArg.compile(comp, paramType);
                            code.emitFi();
                        }
                    } else {
                        code.emitLoad(argsArray2);
                        code.emitPushInt(i - plainArgs);
                        code.emitInvokeStatic(Compilation.makeListMethod);
                        stackType = Compilation.scmListType;
                        key_i = key_i2;
                        opt_i = opt_i2;
                    }
                    if (paramType != stackType) {
                        CheckedTarget.emitCheckedCoerce(comp, this, i + 1, paramType);
                    }
                    if (param.isIndirectBinding()) {
                        param.pushIndirectBinding(comp);
                    }
                    if (param.isSimple()) {
                        Variable var = param.getVariable();
                        if (param.isIndirectBinding()) {
                            var.setType(Compilation.typeLocation);
                        }
                        code.emitStore(var);
                    } else {
                        code.emitPutField(param.field);
                    }
                } else {
                    key_i = key_i2;
                    opt_i = opt_i2;
                }
                i++;
                param = param.nextDecl();
                key_i2 = key_i;
                opt_i2 = opt_i;
            }
            comp.callContextVar = callContextSave;
            int i2 = key_i2;
            int i3 = opt_i2;
        }
    }

    /* access modifiers changed from: 0000 */
    public void compileAsMethod(Compilation comp) {
        Expression arg;
        if ((this.flags & 128) == 0 && !isAbstract()) {
            this.flags |= 128;
            if (this.primMethods != null) {
                Method save_method = comp.method;
                LambdaExp save_lambda = comp.curLambda;
                comp.curLambda = this;
                boolean isStatic = this.primMethods[0].getStaticFlag();
                int numStubs = this.primMethods.length - 1;
                Type restArgType = restArgType();
                long[] saveDeclFlags = null;
                if (numStubs > 0) {
                    saveDeclFlags = new long[(this.min_args + numStubs)];
                    int k = 0;
                    Declaration decl = firstDecl();
                    while (k < this.min_args + numStubs) {
                        int k2 = k + 1;
                        saveDeclFlags[k] = decl.flags;
                        decl = decl.nextDecl();
                        k = k2;
                    }
                }
                boolean ctxArg = getCallConvention() >= 2;
                for (int i = 0; i <= numStubs; i++) {
                    comp.method = this.primMethods[i];
                    if (i < numStubs) {
                        CodeAttr code = comp.method.startCode();
                        int toCall = i + 1;
                        while (toCall < numStubs && (this.defaultArgs[toCall] instanceof QuoteExp)) {
                            toCall++;
                        }
                        boolean varArgs = toCall == numStubs && restArgType != null;
                        Variable callContextSave = comp.callContextVar;
                        Variable var = code.getArg(0);
                        if (!isStatic) {
                            code.emitPushThis();
                            if (getNeedsClosureEnv()) {
                                this.closureEnv = var;
                            }
                            var = code.getArg(1);
                        }
                        Declaration decl2 = firstDecl();
                        int j = 0;
                        while (j < this.min_args + i) {
                            decl2.flags |= 64;
                            decl2.var = var;
                            code.emitLoad(var);
                            var = var.nextVar();
                            j++;
                            decl2 = decl2.nextDecl();
                        }
                        comp.callContextVar = ctxArg ? var : null;
                        int j2 = i;
                        while (j2 < toCall) {
                            this.defaultArgs[j2].compile(comp, StackTarget.getInstance(decl2.getType()));
                            j2++;
                            decl2 = decl2.nextDecl();
                        }
                        if (varArgs) {
                            String lastTypeName = restArgType.getName();
                            if ("gnu.lists.LList".equals(lastTypeName)) {
                                arg = new QuoteExp(LList.Empty);
                            } else if ("java.lang.Object[]".equals(lastTypeName)) {
                                arg = new QuoteExp(Values.noArgs);
                            } else {
                                throw new Error("unimplemented #!rest type " + lastTypeName);
                            }
                            arg.compile(comp, restArgType);
                        }
                        if (ctxArg) {
                            code.emitLoad(var);
                        }
                        if (isStatic) {
                            code.emitInvokeStatic(this.primMethods[toCall]);
                        } else {
                            code.emitInvokeVirtual(this.primMethods[toCall]);
                        }
                        code.emitReturn();
                        this.closureEnv = null;
                        comp.callContextVar = callContextSave;
                    } else {
                        if (saveDeclFlags != null) {
                            int k3 = 0;
                            Declaration decl3 = firstDecl();
                            while (k3 < this.min_args + numStubs) {
                                int k4 = k3 + 1;
                                decl3.flags = saveDeclFlags[k3];
                                decl3.var = null;
                                decl3 = decl3.nextDecl();
                                k3 = k4;
                            }
                        }
                        comp.method.initCode();
                        allocChildClasses(comp);
                        allocParameters(comp);
                        enterFunction(comp);
                        compileBody(comp);
                        compileEnd(comp);
                        generateApplyMethods(comp);
                    }
                }
                comp.method = save_method;
                comp.curLambda = save_lambda;
            }
        }
    }

    /* Debug info: failed to restart local var, previous not found, register: 5 */
    /* JADX WARNING: type inference failed for: r5v1, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r5v2, types: [gnu.expr.Expression] */
    /* JADX WARNING: Multi-variable type inference failed */
    public void compileBody(Compilation comp) {
        Target target;
        Variable callContextSave = comp.callContextVar;
        comp.callContextVar = null;
        if (getCallConvention() >= 2) {
            Variable var = getVarScope().lookup("$ctx");
            if (var != null && var.getType() == Compilation.typeCallContext) {
                comp.callContextVar = var;
            }
            target = ConsumerTarget.makeContextTarget(comp);
        } else {
            target = Target.pushValue(getReturnType());
        }
        Expression expression = this.body;
        if (this.body.getLineNumber() > 0) {
            this = this.body;
        }
        expression.compileWithPosition(comp, target, this);
        comp.callContextVar = callContextSave;
    }

    /* access modifiers changed from: protected */
    public <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        LambdaExp saveLambda;
        Compilation comp = visitor.getCompilation();
        if (comp == null) {
            saveLambda = null;
        } else {
            saveLambda = comp.curLambda;
            comp.curLambda = this;
        }
        try {
            return visitor.visitLambdaExp(this, d);
        } finally {
            if (comp != null) {
                comp.curLambda = saveLambda;
            }
        }
    }

    /* access modifiers changed from: protected */
    public <R, D> void visitChildren(ExpVisitor<R, D> visitor, D d) {
        visitChildrenOnly(visitor, d);
        visitProperties(visitor, d);
    }

    /* access modifiers changed from: protected */
    public final <R, D> void visitChildrenOnly(ExpVisitor<R, D> visitor, D d) {
        LambdaExp save = visitor.currentLambda;
        visitor.currentLambda = this;
        try {
            this.throwsSpecification = visitor.visitExps(this.throwsSpecification, d);
            visitor.visitDefaultArgs(this, d);
            if (visitor.exitValue == null && this.body != null) {
                this.body = visitor.update(this.body, visitor.visit(this.body, d));
            }
        } finally {
            visitor.currentLambda = save;
        }
    }

    /* access modifiers changed from: protected */
    public final <R, D> void visitProperties(ExpVisitor<R, D> visitor, D d) {
        if (this.properties != null) {
            int len = this.properties.length;
            for (int i = 1; i < len; i += 2) {
                Object val = this.properties[i];
                if (val instanceof Expression) {
                    this.properties[i] = visitor.visitAndUpdate((Expression) val, d);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean mustCompile() {
        if (this.keywords != null && this.keywords.length > 0) {
            return true;
        }
        if (this.defaultArgs != null) {
            int i = this.defaultArgs.length;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                Expression def = this.defaultArgs[i];
                if (def != null && !(def instanceof QuoteExp)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void apply(CallContext ctx) throws Throwable {
        setIndexes();
        ctx.writeValue(new Closure(this, ctx));
    }

    /* access modifiers changed from: 0000 */
    public Object evalDefaultArg(int index, CallContext ctx) {
        try {
            return this.defaultArgs[index].eval(ctx);
        } catch (Throwable ex) {
            throw new WrappedException("error evaluating default argument", ex);
        }
    }

    public Expression validateApply(ApplyExp exp, InlineCalls visitor, Type required, Declaration decl) {
        Expression[] margs;
        Expression[] args = exp.getArgs();
        if ((this.flags & 4096) != 0) {
            Expression inlined = InlineCalls.inlineCall(this, args, true);
            if (inlined != null) {
                return visitor.visit(inlined, required);
            }
        }
        exp.visitArgs(visitor);
        int args_length = exp.args.length;
        String msg = WrongArguments.checkArgCount(getName(), this.min_args, this.max_args, args_length);
        if (msg != null) {
            return visitor.noteError(msg);
        }
        int conv = getCallConvention();
        if (!visitor.getCompilation().inlineOk((Expression) this) || !isClassMethod()) {
            return exp;
        }
        if (conv > 2 && conv != 3) {
            return exp;
        }
        Method method = getMethod(args_length);
        if (method == null) {
            return exp;
        }
        boolean isStatic = this.nameDecl.isStatic();
        if (isStatic || !(this.outer instanceof ClassExp) || ((ClassExp) this.outer).isMakingClassPair()) {
        }
        PrimProcedure mproc = new PrimProcedure(method, this);
        if (isStatic) {
            margs = exp.args;
        } else {
            LambdaExp curLambda = visitor.getCurrentLambda();
            while (curLambda != null) {
                if (curLambda.outer == this.outer) {
                    Declaration d = curLambda.firstDecl();
                    if (d == null || !d.isThisParameter()) {
                        return visitor.noteError("calling non-static method " + getName() + " from static method " + curLambda.getName());
                    }
                    int nargs = exp.getArgCount();
                    margs = new Expression[(nargs + 1)];
                    System.arraycopy(exp.getArgs(), 0, margs, 1, nargs);
                    ThisExp thisExp = new ThisExp(d);
                    margs[0] = thisExp;
                } else {
                    curLambda = curLambda.outerLambda();
                }
            }
            return visitor.noteError("internal error: missing " + this);
        }
        ApplyExp applyExp = new ApplyExp((Procedure) mproc, margs);
        return applyExp.setLine((Expression) exp);
    }

    public void print(OutPort out) {
        int opt_i;
        Special mode;
        int opt_i2;
        out.startLogicalBlock("(Lambda/", ")", 2);
        Object sym = getSymbol();
        if (sym != null) {
            out.print(sym);
            out.print('/');
        }
        out.print(this.id);
        out.print('/');
        out.print("fl:");
        out.print(Integer.toHexString(this.flags));
        out.writeSpaceFill();
        printLineColumn(out);
        out.startLogicalBlock("(", false, ")");
        Special prevMode = null;
        int i = 0;
        int opt_args = this.defaultArgs == null ? 0 : this.defaultArgs.length - (this.keywords == null ? 0 : this.keywords.length);
        Declaration decl = firstDecl();
        if (decl == null || !decl.isThisParameter()) {
            opt_i = 0;
        } else {
            i = -1;
            opt_i = 0;
        }
        while (decl != null) {
            if (i < this.min_args) {
                mode = null;
            } else if (i < this.min_args + opt_args) {
                mode = Special.optional;
            } else if (this.max_args >= 0 || i != this.min_args + opt_args) {
                mode = Special.key;
            } else {
                mode = Special.rest;
            }
            if (decl != firstDecl()) {
                out.writeSpaceFill();
            }
            if (mode != prevMode) {
                out.print((Object) mode);
                out.writeSpaceFill();
            }
            Expression defaultArg = null;
            if (mode == Special.optional || mode == Special.key) {
                opt_i2 = opt_i + 1;
                defaultArg = this.defaultArgs[opt_i];
            } else {
                opt_i2 = opt_i;
            }
            if (defaultArg != null) {
                out.print('(');
            }
            decl.printInfo(out);
            if (!(defaultArg == null || defaultArg == QuoteExp.falseExp)) {
                out.print(' ');
                defaultArg.print(out);
                out.print(')');
            }
            i++;
            prevMode = mode;
            decl = decl.nextDecl();
            opt_i = opt_i2;
        }
        out.endLogicalBlock(")");
        out.writeSpaceLinear();
        if (this.body == null) {
            out.print("<null body>");
        } else {
            this.body.print(out);
        }
        out.endLogicalBlock(")");
    }

    /* access modifiers changed from: protected */
    public final String getExpClassName() {
        String cname = getClass().getName();
        int index = cname.lastIndexOf(46);
        if (index >= 0) {
            return cname.substring(index + 1);
        }
        return cname;
    }

    public boolean side_effects() {
        return false;
    }

    public String toString() {
        String str = getExpClassName() + ':' + getSymbol() + '/' + this.id + '/';
        int l = getLineNumber();
        if (l <= 0 && this.body != null) {
            l = this.body.getLineNumber();
        }
        if (l > 0) {
            return str + "l:" + l;
        }
        return str;
    }

    public Object getProperty(Object key, Object defaultValue) {
        if (this.properties == null) {
            return defaultValue;
        }
        int i = this.properties.length;
        do {
            i -= 2;
            if (i < 0) {
                return defaultValue;
            }
        } while (this.properties[i] != key);
        return this.properties[i + 1];
    }

    public synchronized void setProperty(Object key, Object value) {
        this.properties = PropertySet.setProperty(this.properties, key, value);
    }

    public final Type getReturnType() {
        if (this.returnType == null) {
            this.returnType = Type.objectType;
            if (this.body != null && !isAbstract()) {
                this.returnType = this.body.getType();
            }
        }
        return this.returnType;
    }

    public final void setReturnType(Type returnType2) {
        this.returnType = returnType2;
    }

    public final void setCoercedReturnType(Type returnType2) {
        this.returnType = returnType2;
        if (returnType2 != null && returnType2 != Type.objectType && returnType2 != Type.voidType && this.body != QuoteExp.abstractExp) {
            Expression value = this.body;
            this.body = Compilation.makeCoercion(value, returnType2);
            this.body.setLine(value);
        }
    }

    public final void setCoercedReturnValue(Expression type2, Language language) {
        if (!isAbstract()) {
            Expression value = this.body;
            this.body = Compilation.makeCoercion(value, type2);
            this.body.setLine(value);
        }
        Type rtype = language.getTypeFor(type2);
        if (rtype != null) {
            setReturnType(rtype);
        }
    }
}
