package gnu.bytecode;

import android.support.v4.internal.view.SupportMenu;
import com.google.appinventor.components.common.YaVersion;
import com.google.appinventor.components.runtime.util.Ev3Constants.Opcode;
import com.google.appinventor.components.runtime.util.FullScreenVideoUtil;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;

public class CodeAttr extends Attribute implements AttrContainer {
    public static final int DONT_USE_JSR = 2;
    static final int FIXUP_CASE = 3;
    static final int FIXUP_DEFINE = 1;
    static final int FIXUP_DELETE3 = 8;
    static final int FIXUP_GOTO = 4;
    static final int FIXUP_JSR = 5;
    static final int FIXUP_LINE_NUMBER = 15;
    static final int FIXUP_LINE_PC = 14;
    static final int FIXUP_MOVE = 9;
    static final int FIXUP_MOVE_TO_END = 10;
    static final int FIXUP_NONE = 0;
    static final int FIXUP_SWITCH = 2;
    static final int FIXUP_TRANSFER = 6;
    static final int FIXUP_TRANSFER2 = 7;
    static final int FIXUP_TRY = 11;
    static final int FIXUP_TRY_END = 12;
    static final int FIXUP_TRY_HANDLER = 13;
    public static final int GENERATE_STACK_MAP_TABLE = 1;
    public static boolean instructionLineMode = false;
    int PC;
    int SP;
    Attribute attributes;
    byte[] code;
    ExitableBlock currentExitableBlock;
    short[] exception_table;
    int exception_table_length;
    int exitableBlockLevel;
    int fixup_count;
    Label[] fixup_labels;
    int[] fixup_offsets;
    int flags;
    IfState if_stack;
    LineNumbersAttr lines;
    Type[] local_types;
    public LocalVarsAttr locals = this;
    private int max_locals;
    private int max_stack;
    Label previousLabel;
    SourceDebugExtAttr sourceDbgExt;
    public StackMapTableAttr stackMap;
    public Type[] stack_types;
    TryState try_stack = this;
    private boolean unreachable_here;
    boolean[] varsSetInCurrentBlock;

    public final Attribute getAttributes() {
        return this.attributes;
    }

    public final void setAttributes(Attribute attributes2) {
        this.attributes = attributes2;
    }

    /* access modifiers changed from: 0000 */
    public boolean useJsr() {
        return (this.flags & 2) == 0;
    }

    public final void fixupChain(Label here, Label target) {
        fixupAdd(9, 0, target);
        here.defineRaw(this);
    }

    public final void fixupAdd(int kind, Label label) {
        fixupAdd(kind, this.PC, label);
    }

    /* access modifiers changed from: 0000 */
    public final void fixupAdd(int kind, int offset, Label label) {
        if (!(label == null || kind == 1 || kind == 0 || kind == 2 || kind == 11)) {
            label.needsStackMapEntry = true;
        }
        int count = this.fixup_count;
        if (count == 0) {
            this.fixup_offsets = new int[30];
            this.fixup_labels = new Label[30];
        } else if (this.fixup_count == this.fixup_offsets.length) {
            int new_length = count * 2;
            Label[] new_labels = new Label[new_length];
            System.arraycopy(this.fixup_labels, 0, new_labels, 0, count);
            this.fixup_labels = new_labels;
            int[] new_offsets = new int[new_length];
            System.arraycopy(this.fixup_offsets, 0, new_offsets, 0, count);
            this.fixup_offsets = new_offsets;
        }
        this.fixup_offsets[count] = (offset << 4) | kind;
        this.fixup_labels[count] = label;
        this.fixup_count = count + 1;
    }

    private final int fixupOffset(int index) {
        return this.fixup_offsets[index] >> 4;
    }

    private final int fixupKind(int index) {
        return this.fixup_offsets[index] & 15;
    }

    public final Method getMethod() {
        return (Method) getContainer();
    }

    public final int getPC() {
        return this.PC;
    }

    public final int getSP() {
        return this.SP;
    }

    public final ConstantPool getConstants() {
        return getMethod().classfile.constants;
    }

    public final boolean reachableHere() {
        return !this.unreachable_here;
    }

    public final void setReachable(boolean val) {
        this.unreachable_here = !val;
    }

    public final void setUnreachable() {
        this.unreachable_here = true;
    }

    public int getMaxStack() {
        return this.max_stack;
    }

    public int getMaxLocals() {
        return this.max_locals;
    }

    public void setMaxStack(int n) {
        this.max_stack = n;
    }

    public void setMaxLocals(int n) {
        this.max_locals = n;
    }

    public byte[] getCode() {
        return this.code;
    }

    public void setCode(byte[] code2) {
        this.code = code2;
        this.PC = code2.length;
    }

    public void setCodeLength(int len) {
        this.PC = len;
    }

    public int getCodeLength() {
        return this.PC;
    }

    public CodeAttr(Method meth) {
        super("Code");
        addToFrontOf(meth);
        if (meth.getDeclaringClass().getClassfileMajorVersion() >= 50) {
            this.flags |= 3;
        }
    }

    public final void reserve(int bytes) {
        if (this.code == null) {
            this.code = new byte[(bytes + 100)];
        } else if (this.PC + bytes > this.code.length) {
            byte[] new_code = new byte[((this.code.length * 2) + bytes)];
            System.arraycopy(this.code, 0, new_code, 0, this.PC);
            this.code = new_code;
        }
    }

    /* access modifiers changed from: 0000 */
    public byte invert_opcode(byte opcode) {
        int iopcode = opcode & 255;
        if ((iopcode >= 153 && iopcode <= 166) || (iopcode >= 198 && iopcode <= 199)) {
            return (byte) (iopcode ^ 1);
        }
        throw new Error("unknown opcode to invert_opcode");
    }

    public final void put1(int i) {
        byte[] bArr = this.code;
        int i2 = this.PC;
        this.PC = i2 + 1;
        bArr[i2] = (byte) i;
        this.unreachable_here = false;
    }

    public final void put2(int i) {
        byte[] bArr = this.code;
        int i2 = this.PC;
        this.PC = i2 + 1;
        bArr[i2] = (byte) (i >> 8);
        byte[] bArr2 = this.code;
        int i3 = this.PC;
        this.PC = i3 + 1;
        bArr2[i3] = (byte) i;
        this.unreachable_here = false;
    }

    public final void put4(int i) {
        byte[] bArr = this.code;
        int i2 = this.PC;
        this.PC = i2 + 1;
        bArr[i2] = (byte) (i >> 24);
        byte[] bArr2 = this.code;
        int i3 = this.PC;
        this.PC = i3 + 1;
        bArr2[i3] = (byte) (i >> 16);
        byte[] bArr3 = this.code;
        int i4 = this.PC;
        this.PC = i4 + 1;
        bArr3[i4] = (byte) (i >> 8);
        byte[] bArr4 = this.code;
        int i5 = this.PC;
        this.PC = i5 + 1;
        bArr4[i5] = (byte) i;
        this.unreachable_here = false;
    }

    public final void putIndex2(CpoolEntry cnst) {
        put2(cnst.index);
    }

    public final void putLineNumber(String filename, int linenumber) {
        if (filename != null) {
            getMethod().classfile.setSourceFile(filename);
        }
        putLineNumber(linenumber);
    }

    public final void putLineNumber(int linenumber) {
        if (this.sourceDbgExt != null) {
            linenumber = this.sourceDbgExt.fixLine(linenumber);
        }
        fixupAdd(14, null);
        fixupAdd(15, linenumber, null);
    }

    /* access modifiers changed from: 0000 */
    public void noteParamTypes() {
        Method method = getMethod();
        int offset = 0;
        if ((method.access_flags & 8) == 0) {
            Type type = method.classfile;
            if ("<init>".equals(method.getName()) && !"java.lang.Object".equals(type.getName())) {
                type = UninitializedType.uninitializedThis((ClassType) type);
            }
            int offset2 = 0 + 1;
            noteVarType(0, type);
            offset = offset2;
        }
        int arg_count = method.arg_types.length;
        int i = 0;
        int offset3 = offset;
        while (i < arg_count) {
            Type type2 = method.arg_types[i];
            int offset4 = offset3 + 1;
            noteVarType(offset3, type2);
            int size = type2.getSizeInWords();
            while (true) {
                size--;
                if (size <= 0) {
                    break;
                }
                offset4++;
            }
            i++;
            offset3 = offset4;
        }
        if ((this.flags & 1) != 0) {
            this.stackMap = new StackMapTableAttr();
            int[] encodedLocals = new int[(offset3 + 20)];
            int i2 = 0;
            int count = 0;
            while (i2 < offset3) {
                int encoded = this.stackMap.encodeVerificationType(this.local_types[i2], this);
                int count2 = count + 1;
                encodedLocals[count] = encoded;
                int tag = encoded & 255;
                if (tag == 3 || tag == 4) {
                    i2++;
                }
                i2++;
                count = count2;
            }
            this.stackMap.encodedLocals = encodedLocals;
            this.stackMap.countLocals = count;
            this.stackMap.encodedStack = new int[10];
            this.stackMap.countStack = 0;
        }
    }

    public void noteVarType(int offset, Type type) {
        int size = type.getSizeInWords();
        if (this.local_types == null) {
            this.local_types = new Type[(offset + size + 20)];
        } else if (offset + size > this.local_types.length) {
            Type[] new_array = new Type[((offset + size) * 2)];
            System.arraycopy(this.local_types, 0, new_array, 0, this.local_types.length);
            this.local_types = new_array;
        }
        this.local_types[offset] = type;
        if (this.varsSetInCurrentBlock == null) {
            this.varsSetInCurrentBlock = new boolean[this.local_types.length];
        } else if (this.varsSetInCurrentBlock.length <= offset) {
            boolean[] tmp = new boolean[this.local_types.length];
            System.arraycopy(this.varsSetInCurrentBlock, 0, tmp, 0, this.varsSetInCurrentBlock.length);
            this.varsSetInCurrentBlock = tmp;
        }
        this.varsSetInCurrentBlock[offset] = true;
        if (offset > 0) {
            Type prev = this.local_types[offset - 1];
            if (prev != null && prev.getSizeInWords() == 2) {
                this.local_types[offset - 1] = null;
            }
        }
        while (true) {
            size--;
            if (size > 0) {
                offset++;
                this.local_types[offset] = null;
            } else {
                return;
            }
        }
    }

    public final void setTypes(Label label) {
        setTypes(label.localTypes, label.stackTypes);
    }

    public final void setTypes(Type[] labelLocals, Type[] labelStack) {
        int usedStack = labelStack.length;
        int usedLocals = labelLocals.length;
        if (this.local_types != null) {
            if (usedLocals > 0) {
                System.arraycopy(labelLocals, 0, this.local_types, 0, usedLocals);
            }
            for (int i = usedLocals; i < this.local_types.length; i++) {
                this.local_types[i] = null;
            }
        }
        if (this.stack_types == null || usedStack > this.stack_types.length) {
            this.stack_types = new Type[usedStack];
        } else {
            for (int i2 = usedStack; i2 < this.stack_types.length; i2++) {
                this.stack_types[i2] = null;
            }
        }
        System.arraycopy(labelStack, 0, this.stack_types, 0, usedStack);
        this.SP = usedStack;
    }

    public final void pushType(Type type) {
        if (type.size == 0) {
            throw new Error("pushing void type onto stack");
        }
        if (this.stack_types == null || this.stack_types.length == 0) {
            this.stack_types = new Type[20];
        } else if (this.SP + 1 >= this.stack_types.length) {
            Type[] new_array = new Type[(this.stack_types.length * 2)];
            System.arraycopy(this.stack_types, 0, new_array, 0, this.SP);
            this.stack_types = new_array;
        }
        if (type.size == 8) {
            Type[] typeArr = this.stack_types;
            int i = this.SP;
            this.SP = i + 1;
            typeArr[i] = Type.voidType;
        }
        Type[] typeArr2 = this.stack_types;
        int i2 = this.SP;
        this.SP = i2 + 1;
        typeArr2[i2] = type;
        if (this.SP > this.max_stack) {
            this.max_stack = this.SP;
        }
    }

    public final Type popType() {
        if (this.SP <= 0) {
            throw new Error("popType called with empty stack " + getMethod());
        }
        Type[] typeArr = this.stack_types;
        int i = this.SP - 1;
        this.SP = i;
        Type type = typeArr[i];
        if (type.size != 8 || popType().isVoid()) {
            return type;
        }
        throw new Error("missing void type on stack");
    }

    public final Type topType() {
        return this.stack_types[this.SP - 1];
    }

    public void emitPop(int nvalues) {
        while (nvalues > 0) {
            reserve(1);
            if (popType().size > 4) {
                put1(88);
            } else if (nvalues > 1) {
                if (popType().size > 4) {
                    put1(87);
                    reserve(1);
                }
                put1(88);
                nvalues--;
            } else {
                put1(87);
            }
            nvalues--;
        }
    }

    public Label getLabel() {
        Label label = new Label();
        label.defineRaw(this);
        return label;
    }

    public void emitSwap() {
        reserve(1);
        Type type1 = popType();
        Type type2 = popType();
        if (type1.size > 4 || type2.size > 4) {
            pushType(type2);
            pushType(type1);
            emitDupX();
            emitPop(1);
            return;
        }
        pushType(type1);
        put1(95);
        pushType(type2);
    }

    public void emitDup() {
        reserve(1);
        Type type = topType();
        put1(type.size <= 4 ? 89 : 92);
        pushType(type);
    }

    public void emitDupX() {
        reserve(1);
        Type type = popType();
        Type skipedType = popType();
        if (skipedType.size <= 4) {
            put1(type.size <= 4 ? 90 : 93);
        } else {
            put1(type.size <= 4 ? 91 : 94);
        }
        pushType(type);
        pushType(skipedType);
        pushType(type);
    }

    public void emitDup(int size, int offset) {
        int kind;
        if (size != 0) {
            reserve(1);
            Type copied1 = popType();
            Type copied2 = null;
            if (size == 1) {
                if (copied1.size > 4) {
                    throw new Error("using dup for 2-word type");
                }
            } else if (size != 2) {
                throw new Error("invalid size to emitDup");
            } else if (copied1.size <= 4) {
                copied2 = popType();
                if (copied2.size > 4) {
                    throw new Error("dup will cause invalid types on stack");
                }
            }
            Type skipped1 = null;
            Type skipped2 = null;
            if (offset == 0) {
                kind = size == 1 ? 89 : 92;
            } else if (offset == 1) {
                kind = size == 1 ? 90 : 93;
                skipped1 = popType();
                if (skipped1.size > 4) {
                    throw new Error("dup will cause invalid types on stack");
                }
            } else if (offset == 2) {
                kind = size == 1 ? 91 : 94;
                skipped1 = popType();
                if (skipped1.size <= 4) {
                    skipped2 = popType();
                    if (skipped2.size > 4) {
                        throw new Error("dup will cause invalid types on stack");
                    }
                }
            } else {
                throw new Error("emitDup:  invalid offset");
            }
            put1(kind);
            if (copied2 != null) {
                pushType(copied2);
            }
            pushType(copied1);
            if (skipped2 != null) {
                pushType(skipped2);
            }
            if (skipped1 != null) {
                pushType(skipped1);
            }
            if (copied2 != null) {
                pushType(copied2);
            }
            pushType(copied1);
        }
    }

    public void emitDup(int size) {
        emitDup(size, 0);
    }

    public void emitDup(Type type) {
        emitDup(type.size > 4 ? 2 : 1, 0);
    }

    public void enterScope(Scope scope) {
        scope.setStartPC(this);
        this.locals.enterScope(scope);
    }

    public Scope pushScope() {
        Scope scope = new Scope();
        if (this.locals == null) {
            this.locals = new LocalVarsAttr(getMethod());
        }
        enterScope(scope);
        if (this.locals.parameter_scope == null) {
            this.locals.parameter_scope = scope;
        }
        return scope;
    }

    public Scope getCurrentScope() {
        return this.locals.current_scope;
    }

    public Scope popScope() {
        Scope scope = this.locals.current_scope;
        this.locals.current_scope = scope.parent;
        scope.freeLocals(this);
        scope.end = getLabel();
        return scope;
    }

    public Variable getArg(int index) {
        return this.locals.parameter_scope.getVariable(index);
    }

    public Variable lookup(String name) {
        for (Scope scope = this.locals.current_scope; scope != null; scope = scope.parent) {
            Variable var = scope.lookup(name);
            if (var != null) {
                return var;
            }
        }
        return null;
    }

    public Variable addLocal(Type type) {
        return this.locals.current_scope.addVariable(this, type, null);
    }

    public Variable addLocal(Type type, String name) {
        return this.locals.current_scope.addVariable(this, type, name);
    }

    public void addParamLocals() {
        Method method = getMethod();
        if ((method.access_flags & 8) == 0) {
            addLocal(method.classfile).setParameter(true);
        }
        for (Type addLocal : method.arg_types) {
            addLocal(addLocal).setParameter(true);
        }
    }

    public final void emitPushConstant(int val, Type type) {
        switch (type.getSignature().charAt(0)) {
            case 'B':
            case 'C':
            case 'I':
            case 'S':
            case 'Z':
                emitPushInt(val);
                return;
            case 'D':
                emitPushDouble((double) val);
                return;
            case 'F':
                emitPushFloat((float) val);
                return;
            case 'J':
                emitPushLong((long) val);
                return;
            default:
                throw new Error("bad type to emitPushConstant");
        }
    }

    public final void emitPushConstant(CpoolEntry cnst) {
        reserve(3);
        int index = cnst.index;
        if (cnst instanceof CpoolValue2) {
            put1(20);
            put2(index);
        } else if (index < 256) {
            put1(18);
            put1(index);
        } else {
            put1(19);
            put2(index);
        }
    }

    public final void emitPushInt(int i) {
        reserve(3);
        if (i >= -1 && i <= 5) {
            put1(i + 3);
        } else if (i >= -128 && i < 128) {
            put1(16);
            put1(i);
        } else if (i < -32768 || i >= 32768) {
            emitPushConstant(getConstants().addInt(i));
        } else {
            put1(17);
            put2(i);
        }
        pushType(Type.intType);
    }

    public void emitPushLong(long i) {
        if (i == 0 || i == 1) {
            reserve(1);
            put1(((int) i) + 9);
        } else if (((long) ((int) i)) == i) {
            emitPushInt((int) i);
            reserve(1);
            popType();
            put1(133);
        } else {
            emitPushConstant(getConstants().addLong(i));
        }
        pushType(Type.longType);
    }

    public void emitPushFloat(float x) {
        int xi = (int) x;
        if (((float) xi) != x || xi < -128 || xi >= 128) {
            emitPushConstant(getConstants().addFloat(x));
        } else if (xi < 0 || xi > 2) {
            emitPushInt(xi);
            reserve(1);
            popType();
            put1(134);
        } else {
            reserve(1);
            put1(xi + 11);
            if (xi == 0 && Float.floatToIntBits(x) != 0) {
                reserve(1);
                put1(118);
            }
        }
        pushType(Type.floatType);
    }

    public void emitPushDouble(double x) {
        int xi = (int) x;
        if (((double) xi) != x || xi < -128 || xi >= 128) {
            emitPushConstant(getConstants().addDouble(x));
        } else if (xi == 0 || xi == 1) {
            reserve(1);
            put1(xi + 14);
            if (xi == 0 && Double.doubleToLongBits(x) != 0) {
                reserve(1);
                put1(119);
            }
        } else {
            emitPushInt(xi);
            reserve(1);
            popType();
            put1(135);
        }
        pushType(Type.doubleType);
    }

    public static final String calculateSplit(String str) {
        int strLength = str.length();
        StringBuffer sbuf = new StringBuffer(20);
        int segmentStart = 0;
        int byteLength = 0;
        for (int i = 0; i < strLength; i++) {
            char ch = str.charAt(i);
            int bytes = ch >= 2048 ? 3 : (ch >= 128 || ch == 0) ? 2 : 1;
            if (byteLength + bytes > 65535) {
                sbuf.append((char) (i - segmentStart));
                segmentStart = i;
                byteLength = 0;
            }
            byteLength += bytes;
        }
        sbuf.append((char) (strLength - segmentStart));
        return sbuf.toString();
    }

    public final void emitPushString(String str) {
        if (str == null) {
            emitPushNull();
            return;
        }
        int length = str.length();
        String segments = calculateSplit(str);
        int numSegments = segments.length();
        if (numSegments <= 1) {
            emitPushConstant(getConstants().addString(str));
            pushType(Type.javalangStringType);
            return;
        }
        if (numSegments == 2) {
            int firstSegment = segments.charAt(0);
            emitPushString(str.substring(0, firstSegment));
            emitPushString(str.substring(firstSegment));
            emitInvokeVirtual(Type.javalangStringType.getDeclaredMethod("concat", 1));
        } else {
            ClassType sbufType = ClassType.make("java.lang.StringBuffer");
            emitNew(sbufType);
            emitDup((Type) sbufType);
            emitPushInt(length);
            emitInvokeSpecial(sbufType.getDeclaredMethod("<init>", new Type[]{Type.intType}));
            Method appendMethod = sbufType.getDeclaredMethod("append", new Type[]{Type.javalangStringType});
            int segStart = 0;
            for (int seg = 0; seg < numSegments; seg++) {
                emitDup((Type) sbufType);
                int segEnd = segStart + segments.charAt(seg);
                emitPushString(str.substring(segStart, segEnd));
                emitInvokeVirtual(appendMethod);
                segStart = segEnd;
            }
            emitInvokeVirtual(Type.toString_method);
        }
        if (str == str.intern()) {
            emitInvokeVirtual(Type.javalangStringType.getDeclaredMethod("intern", 0));
        }
    }

    public final void emitPushClass(ObjectType ctype) {
        emitPushConstant(getConstants().addClass(ctype));
        pushType(Type.javalangClassType);
    }

    public void emitPushNull() {
        reserve(1);
        put1(1);
        pushType(Type.nullType);
    }

    public void emitPushDefaultValue(Type type) {
        Type type2 = type.getImplementationType();
        if (type2 instanceof PrimType) {
            emitPushConstant(0, type2);
        } else {
            emitPushNull();
        }
    }

    public void emitStoreDefaultValue(Variable var) {
        emitPushDefaultValue(var.getType());
        emitStore(var);
    }

    public final void emitPushThis() {
        emitLoad(this.locals.used[0]);
    }

    public final void emitPushPrimArray(Object value, ArrayType arrayType) {
        Type elementType = arrayType.getComponentType();
        int len = Array.getLength(value);
        emitPushInt(len);
        emitNewArray(elementType);
        char sig = elementType.getSignature().charAt(0);
        for (int i = 0; i < len; i++) {
            long ival = 0;
            float fval = 0.0f;
            double dval = 0.0d;
            switch (sig) {
                case 'B':
                    ival = (long) ((byte[]) value)[i];
                    if (ival == 0) {
                        break;
                    }
                case 'C':
                    ival = (long) ((char[]) value)[i];
                    if (ival == 0) {
                        break;
                    }
                case 'D':
                    dval = ((double[]) value)[i];
                    if (dval == 0.0d) {
                        break;
                    }
                case 'F':
                    fval = ((float[]) value)[i];
                    if (((double) fval) == 0.0d) {
                        break;
                    }
                case 'I':
                    ival = (long) ((int[]) value)[i];
                    if (ival == 0) {
                        break;
                    }
                case 'J':
                    ival = ((long[]) value)[i];
                    if (ival == 0) {
                        break;
                    }
                case 'S':
                    ival = (long) ((short[]) value)[i];
                    if (ival == 0) {
                        break;
                    }
                case 'Z':
                    ival = ((boolean[]) value)[i] ? 1 : 0;
                    if (ival == 0) {
                        break;
                    }
                default:
                    emitDup((Type) arrayType);
                    emitPushInt(i);
                    switch (sig) {
                        case 'B':
                        case 'C':
                        case 'I':
                        case 'S':
                        case 'Z':
                            emitPushInt((int) ival);
                            break;
                        case 'D':
                            emitPushDouble(dval);
                            break;
                        case 'F':
                            emitPushFloat(fval);
                            break;
                        case 'J':
                            emitPushLong(ival);
                            break;
                    }
                    emitArrayStore(elementType);
                    break;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void emitNewArray(int type_code) {
        reserve(2);
        put1(188);
        put1(type_code);
    }

    public final void emitArrayLength() {
        if (!(popType() instanceof ArrayType)) {
            throw new Error("non-array type in emitArrayLength");
        }
        reserve(1);
        put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_SEEK);
        pushType(Type.intType);
    }

    private int adjustTypedOp(char sig) {
        switch (sig) {
            case 'B':
            case 'Z':
                return 5;
            case 'C':
                return 6;
            case 'D':
                return 3;
            case 'F':
                return 2;
            case 'I':
                return 0;
            case 'J':
                return 1;
            case 'S':
                return 7;
            default:
                return 4;
        }
    }

    private int adjustTypedOp(Type type) {
        return adjustTypedOp(type.getSignature().charAt(0));
    }

    private void emitTypedOp(int op, Type type) {
        reserve(1);
        put1(adjustTypedOp(type) + op);
    }

    private void emitTypedOp(int op, char sig) {
        reserve(1);
        put1(adjustTypedOp(sig) + op);
    }

    public void emitArrayStore(Type element_type) {
        popType();
        popType();
        popType();
        emitTypedOp(79, element_type);
    }

    public void emitArrayStore() {
        popType();
        popType();
        emitTypedOp(79, ((ArrayType) popType().getImplementationType()).getComponentType());
    }

    public void emitArrayLoad(Type element_type) {
        popType();
        popType();
        emitTypedOp(46, element_type);
        pushType(element_type);
    }

    public void emitArrayLoad() {
        popType();
        Type elementType = ((ArrayType) popType().getImplementationType()).getComponentType();
        emitTypedOp(46, elementType);
        pushType(elementType);
    }

    public void emitNew(ClassType type) {
        reserve(3);
        Label label = new Label(this);
        label.defineRaw(this);
        put1(187);
        putIndex2(getConstants().addClass((ObjectType) type));
        pushType(new UninitializedType(type, label));
    }

    public void emitNewArray(Type element_type, int dims) {
        int code2;
        if (popType().promote() != Type.intType) {
            throw new Error("non-int dim. spec. in emitNewArray");
        }
        if (element_type instanceof PrimType) {
            switch (element_type.getSignature().charAt(0)) {
                case 'B':
                    code2 = 8;
                    break;
                case 'C':
                    code2 = 5;
                    break;
                case 'D':
                    code2 = 7;
                    break;
                case 'F':
                    code2 = 6;
                    break;
                case 'I':
                    code2 = 10;
                    break;
                case 'J':
                    code2 = 11;
                    break;
                case 'S':
                    code2 = 9;
                    break;
                case 'Z':
                    code2 = 4;
                    break;
                default:
                    throw new Error("bad PrimType in emitNewArray");
            }
            emitNewArray(code2);
        } else if (element_type instanceof ObjectType) {
            reserve(3);
            put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_DIALOG_FLAG);
            putIndex2(getConstants().addClass((ObjectType) element_type));
        } else if (element_type instanceof ArrayType) {
            reserve(4);
            put1(YaVersion.YOUNG_ANDROID_VERSION);
            putIndex2(getConstants().addClass((ObjectType) new ArrayType(element_type)));
            if (dims < 1 || dims > 255) {
                throw new Error("dims out of range in emitNewArray");
            }
            put1(dims);
            do {
                dims--;
                if (dims > 0) {
                }
            } while (popType().promote() == Type.intType);
            throw new Error("non-int dim. spec. in emitNewArray");
        } else {
            throw new Error("unimplemented type in emitNewArray");
        }
        pushType(new ArrayType(element_type));
    }

    public void emitNewArray(Type element_type) {
        emitNewArray(element_type, 1);
    }

    private void emitBinop(int base_code) {
        Type type2 = popType().promote();
        Type type1_raw = popType();
        Type type1 = type1_raw.promote();
        if (type1 != type2 || !(type1 instanceof PrimType)) {
            throw new Error("non-matching or bad types in binary operation");
        }
        emitTypedOp(base_code, type1);
        pushType(type1_raw);
    }

    private void emitBinop(int base_code, char sig) {
        popType();
        popType();
        emitTypedOp(base_code, sig);
        pushType(Type.signatureToPrimitive(sig));
    }

    public void emitBinop(int base_code, Type type) {
        popType();
        popType();
        emitTypedOp(base_code, type);
        pushType(type);
    }

    public final void emitAdd(char sig) {
        emitBinop(96, sig);
    }

    public final void emitAdd(PrimType type) {
        emitBinop(96, (Type) type);
    }

    public final void emitAdd() {
        emitBinop(96);
    }

    public final void emitSub(char sig) {
        emitBinop(100, sig);
    }

    public final void emitSub(PrimType type) {
        emitBinop(100, (Type) type);
    }

    public final void emitSub() {
        emitBinop(100);
    }

    public final void emitMul() {
        emitBinop(104);
    }

    public final void emitDiv() {
        emitBinop(108);
    }

    public final void emitRem() {
        emitBinop(112);
    }

    public final void emitAnd() {
        emitBinop(126);
    }

    public final void emitIOr() {
        emitBinop(128);
    }

    public final void emitXOr() {
        emitBinop(130);
    }

    public final void emitShl() {
        emitShift(120);
    }

    public final void emitShr() {
        emitShift(122);
    }

    public final void emitUshr() {
        emitShift(124);
    }

    private void emitShift(int base_code) {
        Type type2 = popType().promote();
        Type type1_raw = popType();
        Type type1 = type1_raw.promote();
        if (type1 != Type.intType && type1 != Type.longType) {
            throw new Error("the value shifted must be an int or a long");
        } else if (type2 != Type.intType) {
            throw new Error("the amount of shift must be an int");
        } else {
            emitTypedOp(base_code, type1);
            pushType(type1_raw);
        }
    }

    public final void emitNot(Type type) {
        emitPushConstant(1, type);
        emitAdd();
        emitPushConstant(1, type);
        emitAnd();
    }

    public void emitPrimop(int opcode, int arg_count, Type retType) {
        reserve(1);
        while (true) {
            arg_count--;
            if (arg_count >= 0) {
                popType();
            } else {
                put1(opcode);
                pushType(retType);
                return;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void emitMaybeWide(int opcode, int index) {
        if (index >= 256) {
            put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_DURATION);
            put1(opcode);
            put2(index);
            return;
        }
        put1(opcode);
        put1(index);
    }

    public final void emitLoad(Variable var) {
        if (var.dead()) {
            throw new Error("attempting to push dead variable");
        }
        int offset = var.offset;
        if (offset < 0 || !var.isSimple()) {
            throw new Error("attempting to load from unassigned variable " + var + " simple:" + var.isSimple() + ", offset: " + offset);
        }
        Type type = var.getType().promote();
        reserve(4);
        int kind = adjustTypedOp(type);
        if (offset <= 3) {
            put1((kind * 4) + 26 + offset);
        } else {
            emitMaybeWide(kind + 21, offset);
        }
        pushType(var.getType());
    }

    public void emitStore(Variable var) {
        int offset = var.offset;
        if (offset < 0 || !var.isSimple()) {
            throw new Error("attempting to store in unassigned " + var + " simple:" + var.isSimple() + ", offset: " + offset);
        }
        Type type = var.getType().promote();
        noteVarType(offset, type);
        reserve(4);
        popType();
        int kind = adjustTypedOp(type);
        if (offset <= 3) {
            put1((kind * 4) + 59 + offset);
        } else {
            emitMaybeWide(kind + 54, offset);
        }
    }

    public void emitInc(Variable var, short inc) {
        if (var.dead()) {
            throw new Error("attempting to increment dead variable");
        }
        int offset = var.offset;
        if (offset < 0 || !var.isSimple()) {
            throw new Error("attempting to increment unassigned variable" + var.getName() + " simple:" + var.isSimple() + ", offset: " + offset);
        }
        reserve(6);
        if (var.getType().getImplementationType().promote() != Type.intType) {
            throw new Error("attempting to increment non-int variable");
        }
        if (offset > 255 || inc > 255 || inc < -256) {
            put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_DURATION);
            put1(132);
            put2(offset);
            put2(inc);
            return;
        }
        put1(132);
        put1(offset);
        put1(inc);
    }

    private final void emitFieldop(Field field, int opcode) {
        reserve(3);
        put1(opcode);
        putIndex2(getConstants().addFieldRef(field));
    }

    public final void emitGetStatic(Field field) {
        pushType(field.type);
        emitFieldop(field, 178);
    }

    public final void emitGetField(Field field) {
        popType();
        pushType(field.type);
        emitFieldop(field, 180);
    }

    public final void emitPutStatic(Field field) {
        popType();
        emitFieldop(field, 179);
    }

    public final void emitPutField(Field field) {
        popType();
        popType();
        emitFieldop(field, 181);
    }

    private int words(Type[] types) {
        int res = 0;
        int i = types.length;
        while (true) {
            i--;
            if (i < 0) {
                return res;
            }
            if (types[i].size > 4) {
                res += 2;
            } else {
                res++;
            }
        }
    }

    public void emitInvokeMethod(Method method, int opcode) {
        boolean is_invokestatic;
        boolean is_init;
        Type t;
        boolean z = true;
        reserve(opcode == 185 ? 5 : 3);
        int arg_count = method.arg_types.length;
        if (opcode == 184) {
            is_invokestatic = true;
        } else {
            is_invokestatic = false;
        }
        if (opcode != 183 || !"<init>".equals(method.getName())) {
            is_init = false;
        } else {
            is_init = true;
        }
        if ((method.access_flags & 8) == 0) {
            z = false;
        }
        if (is_invokestatic != z) {
            throw new Error("emitInvokeXxx static flag mis-match method.flags=" + method.access_flags);
        }
        if (!is_invokestatic && !is_init) {
            arg_count++;
        }
        put1(opcode);
        putIndex2(getConstants().addMethodRef(method));
        if (opcode == 185) {
            put1(words(method.arg_types) + 1);
            put1(0);
        }
        do {
            arg_count--;
            if (arg_count >= 0) {
                t = popType();
            } else {
                if (is_init) {
                    Type t2 = popType();
                    if (t2 instanceof UninitializedType) {
                        ClassType ctype = ((UninitializedType) t2).ctype;
                        for (int i = 0; i < this.SP; i++) {
                            if (this.stack_types[i] == t2) {
                                this.stack_types[i] = ctype;
                            }
                        }
                        Variable[] used = this.locals.used;
                        int i2 = used == null ? 0 : used.length;
                        while (true) {
                            i2--;
                            if (i2 < 0) {
                                break;
                            }
                            Variable var = used[i2];
                            if (var != null && var.type == t2) {
                                var.type = ctype;
                            }
                        }
                        int i3 = this.local_types == null ? 0 : this.local_types.length;
                        while (true) {
                            i3--;
                            if (i3 < 0) {
                                break;
                            } else if (this.local_types[i3] == t2) {
                                this.local_types[i3] = ctype;
                            }
                        }
                    } else {
                        throw new Error("calling <init> on already-initialized object");
                    }
                }
                if (method.return_type.size != 0) {
                    pushType(method.return_type);
                    return;
                }
                return;
            }
        } while (!(t instanceof UninitializedType));
        throw new Error("passing " + t + " as parameter");
    }

    public void emitInvoke(Method method) {
        int opcode;
        if ((method.access_flags & 8) != 0) {
            opcode = 184;
        } else if (method.classfile.isInterface()) {
            opcode = 185;
        } else if ("<init>".equals(method.getName())) {
            opcode = 183;
        } else {
            opcode = 182;
        }
        emitInvokeMethod(method, opcode);
    }

    public void emitInvokeVirtual(Method method) {
        emitInvokeMethod(method, 182);
    }

    public void emitInvokeSpecial(Method method) {
        emitInvokeMethod(method, 183);
    }

    public void emitInvokeStatic(Method method) {
        emitInvokeMethod(method, 184);
    }

    public void emitInvokeInterface(Method method) {
        emitInvokeMethod(method, 185);
    }

    /* access modifiers changed from: 0000 */
    public final void emitTransfer(Label label, int opcode) {
        label.setTypes(this);
        fixupAdd(6, label);
        put1(opcode);
        this.PC += 2;
    }

    public final void emitGoto(Label label) {
        label.setTypes(this);
        fixupAdd(4, label);
        reserve(3);
        put1(167);
        this.PC += 2;
        setUnreachable();
    }

    public final void emitJsr(Label label) {
        fixupAdd(5, label);
        reserve(3);
        put1(168);
        this.PC += 2;
    }

    public ExitableBlock startExitableBlock(Type resultType, boolean runFinallyBlocks) {
        ExitableBlock bl = new ExitableBlock(resultType, this, runFinallyBlocks);
        bl.outer = this.currentExitableBlock;
        this.currentExitableBlock = bl;
        return bl;
    }

    public void endExitableBlock() {
        ExitableBlock bl = this.currentExitableBlock;
        bl.finish();
        this.currentExitableBlock = bl.outer;
    }

    public final void emitGotoIfCompare1(Label label, int opcode) {
        popType();
        reserve(3);
        emitTransfer(label, opcode);
    }

    public final void emitGotoIfIntEqZero(Label label) {
        emitGotoIfCompare1(label, 153);
    }

    public final void emitGotoIfIntNeZero(Label label) {
        emitGotoIfCompare1(label, 154);
    }

    public final void emitGotoIfIntLtZero(Label label) {
        emitGotoIfCompare1(label, 155);
    }

    public final void emitGotoIfIntGeZero(Label label) {
        emitGotoIfCompare1(label, 156);
    }

    public final void emitGotoIfIntGtZero(Label label) {
        emitGotoIfCompare1(label, 157);
    }

    public final void emitGotoIfIntLeZero(Label label) {
        emitGotoIfCompare1(label, 158);
    }

    public final void emitGotoIfCompare2(Label label, int logop) {
        boolean cmpg = false;
        if (logop < 153 || logop > 158) {
            throw new Error("emitGotoIfCompare2: logop must be one of ifeq...ifle");
        }
        Type type2 = popType().promote();
        Type type1 = popType().promote();
        reserve(4);
        char sig1 = type1.getSignature().charAt(0);
        char sig2 = type2.getSignature().charAt(0);
        if (logop == 155 || logop == 158) {
            cmpg = true;
        }
        if (sig1 == 'I' && sig2 == 'I') {
            logop += 6;
        } else if (sig1 == 'J' && sig2 == 'J') {
            put1(148);
        } else if (sig1 == 'F' && sig2 == 'F') {
            put1(cmpg ? 149 : 150);
        } else if (sig1 == 'D' && sig2 == 'D') {
            put1(cmpg ? 151 : 152);
        } else if ((sig1 == 'L' || sig1 == '[') && ((sig2 == 'L' || sig2 == '[') && logop <= 154)) {
            logop += 12;
        } else {
            throw new Error("invalid types to emitGotoIfCompare2");
        }
        emitTransfer(label, logop);
    }

    public final void emitGotoIfEq(Label label, boolean invert) {
        emitGotoIfCompare2(label, invert ? 154 : 153);
    }

    public final void emitGotoIfEq(Label label) {
        emitGotoIfCompare2(label, 153);
    }

    public final void emitGotoIfNE(Label label) {
        emitGotoIfCompare2(label, 154);
    }

    public final void emitGotoIfLt(Label label) {
        emitGotoIfCompare2(label, 155);
    }

    public final void emitGotoIfGe(Label label) {
        emitGotoIfCompare2(label, 156);
    }

    public final void emitGotoIfGt(Label label) {
        emitGotoIfCompare2(label, 157);
    }

    public final void emitGotoIfLe(Label label) {
        emitGotoIfCompare2(label, 158);
    }

    public final void emitIfCompare1(int opcode) {
        IfState new_if = new IfState(this);
        if (popType().promote() != Type.intType) {
            throw new Error("non-int type to emitIfCompare1");
        }
        reserve(3);
        emitTransfer(new_if.end_label, opcode);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfIntNotZero() {
        emitIfCompare1(153);
    }

    public final void emitIfIntEqZero() {
        emitIfCompare1(154);
    }

    public final void emitIfIntLEqZero() {
        emitIfCompare1(157);
    }

    public final void emitIfRefCompare1(int opcode) {
        IfState new_if = new IfState(this);
        if (!(popType() instanceof ObjectType)) {
            throw new Error("non-ref type to emitIfRefCompare1");
        }
        reserve(3);
        emitTransfer(new_if.end_label, opcode);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfNotNull() {
        emitIfRefCompare1(198);
    }

    public final void emitIfNull() {
        emitIfRefCompare1(199);
    }

    public final void emitIfIntCompare(int opcode) {
        IfState new_if = new IfState(this);
        popType();
        popType();
        reserve(3);
        emitTransfer(new_if.end_label, opcode);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfIntLt() {
        emitIfIntCompare(162);
    }

    public final void emitIfNEq() {
        IfState new_if = new IfState(this);
        emitGotoIfEq(new_if.end_label);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfEq() {
        IfState new_if = new IfState(this);
        emitGotoIfNE(new_if.end_label);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfLt() {
        IfState new_if = new IfState(this);
        emitGotoIfGe(new_if.end_label);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfGe() {
        IfState new_if = new IfState(this);
        emitGotoIfLt(new_if.end_label);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfGt() {
        IfState new_if = new IfState(this);
        emitGotoIfLe(new_if.end_label);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfLe() {
        IfState new_if = new IfState(this);
        emitGotoIfGt(new_if.end_label);
        new_if.start_stack_size = this.SP;
    }

    public void emitRet(Variable var) {
        int offset = var.offset;
        if (offset < 256) {
            reserve(2);
            put1(169);
            put1(offset);
            return;
        }
        reserve(4);
        put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_DURATION);
        put1(169);
        put2(offset);
    }

    public final void emitThen() {
        this.if_stack.start_stack_size = this.SP;
    }

    public final void emitIfThen() {
        new IfState(this, null);
    }

    public final void emitElse() {
        Label else_label = this.if_stack.end_label;
        if (reachableHere()) {
            Label end_label = new Label(this);
            this.if_stack.end_label = end_label;
            int growth = this.SP - this.if_stack.start_stack_size;
            this.if_stack.stack_growth = growth;
            if (growth > 0) {
                this.if_stack.then_stacked_types = new Type[growth];
                System.arraycopy(this.stack_types, this.if_stack.start_stack_size, this.if_stack.then_stacked_types, 0, growth);
            } else {
                this.if_stack.then_stacked_types = new Type[0];
            }
            emitGoto(end_label);
        } else {
            this.if_stack.end_label = null;
        }
        while (this.SP > this.if_stack.start_stack_size) {
            popType();
        }
        this.SP = this.if_stack.start_stack_size;
        if (else_label != null) {
            else_label.define(this);
        }
        this.if_stack.doing_else = true;
    }

    public final void emitFi() {
        boolean make_unreachable = false;
        if (!this.if_stack.doing_else) {
            if (reachableHere() && this.SP != this.if_stack.start_stack_size) {
                throw new Error("at PC " + this.PC + " then clause grows stack with no else clause");
            }
        } else if (this.if_stack.then_stacked_types != null) {
            int then_clause_stack_size = this.if_stack.start_stack_size + this.if_stack.stack_growth;
            if (!reachableHere()) {
                if (this.if_stack.stack_growth > 0) {
                    System.arraycopy(this.if_stack.then_stacked_types, 0, this.stack_types, this.if_stack.start_stack_size, this.if_stack.stack_growth);
                }
                this.SP = then_clause_stack_size;
            } else if (this.SP != then_clause_stack_size) {
                throw new Error("at PC " + this.PC + ": SP at end of 'then' was " + then_clause_stack_size + " while SP at end of 'else' was " + this.SP);
            }
        } else if (this.unreachable_here) {
            make_unreachable = true;
        }
        if (this.if_stack.end_label != null) {
            this.if_stack.end_label.define(this);
        }
        if (make_unreachable) {
            setUnreachable();
        }
        this.if_stack = this.if_stack.previous;
    }

    public final void emitConvert(Type from, Type to) {
        String to_sig = to.getSignature();
        String from_sig = from.getSignature();
        int op = -1;
        if (to_sig.length() == 1 || from_sig.length() == 1) {
            char to_sig0 = to_sig.charAt(0);
            char from_sig0 = from_sig.charAt(0);
            if (from_sig0 != to_sig0) {
                if (from.size < 4) {
                    from_sig0 = Access.INNERCLASS_CONTEXT;
                }
                if (to.size < 4) {
                    emitConvert(from, Type.intType);
                    from_sig0 = Access.INNERCLASS_CONTEXT;
                }
                if (from_sig0 != to_sig0) {
                    switch (from_sig0) {
                        case 'D':
                            switch (to_sig0) {
                                case 'F':
                                    op = 144;
                                    break;
                                case 'I':
                                    op = 142;
                                    break;
                                case 'J':
                                    op = 143;
                                    break;
                            }
                        case 'F':
                            switch (to_sig0) {
                                case 'D':
                                    op = 141;
                                    break;
                                case 'I':
                                    op = 139;
                                    break;
                                case 'J':
                                    op = 140;
                                    break;
                            }
                        case 'I':
                            switch (to_sig0) {
                                case 'B':
                                    op = 145;
                                    break;
                                case 'C':
                                    op = 146;
                                    break;
                                case 'D':
                                    op = 135;
                                    break;
                                case 'F':
                                    op = 134;
                                    break;
                                case 'J':
                                    op = 133;
                                    break;
                                case 'S':
                                    op = 147;
                                    break;
                            }
                        case 'J':
                            switch (to_sig0) {
                                case 'D':
                                    op = 138;
                                    break;
                                case 'F':
                                    op = 137;
                                    break;
                                case 'I':
                                    op = 136;
                                    break;
                            }
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
        if (op < 0) {
            throw new Error("unsupported CodeAttr.emitConvert");
        }
        reserve(1);
        popType();
        put1(op);
        pushType(to);
    }

    private void emitCheckcast(Type type, int opcode) {
        reserve(3);
        popType();
        put1(opcode);
        if (type instanceof ObjectType) {
            putIndex2(getConstants().addClass((ObjectType) type));
            return;
        }
        throw new Error("unimplemented type " + type + " in emitCheckcast/emitInstanceof");
    }

    public static boolean castNeeded(Type top, Type required) {
        if (top instanceof UninitializedType) {
            top = ((UninitializedType) top).getImplementationType();
        }
        while (top != required) {
            if ((required instanceof ClassType) && (top instanceof ClassType) && ((ClassType) top).isSubclass((ClassType) required)) {
                return false;
            }
            if (!(required instanceof ArrayType) || !(top instanceof ArrayType)) {
                return true;
            }
            required = ((ArrayType) required).getComponentType();
            top = ((ArrayType) top).getComponentType();
        }
        return false;
    }

    public void emitCheckcast(Type type) {
        if (castNeeded(topType(), type)) {
            emitCheckcast(type, FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_PAUSE);
            pushType(type);
        }
    }

    public void emitInstanceof(Type type) {
        emitCheckcast(type, FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_STOP);
        pushType(Type.booleanType);
    }

    public final void emitThrow() {
        popType();
        reserve(1);
        put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_PLAY);
        setUnreachable();
    }

    public final void emitMonitorEnter() {
        popType();
        reserve(1);
        put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_SOURCE);
    }

    public final void emitMonitorExit() {
        popType();
        reserve(1);
        put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_FULLSCREEN);
    }

    public final void emitReturn() {
        if (this.try_stack != null) {
            new Error();
        }
        emitRawReturn();
    }

    /* access modifiers changed from: 0000 */
    public final void emitRawReturn() {
        if (getMethod().getReturnType().size == 0) {
            reserve(1);
            put1(177);
        } else {
            emitTypedOp(172, popType().promote());
        }
        setUnreachable();
    }

    public void addHandler(int start_pc, int end_pc, int handler_pc, int catch_type) {
        int index = this.exception_table_length * 4;
        if (this.exception_table == null) {
            this.exception_table = new short[20];
        } else if (this.exception_table.length <= index) {
            short[] new_table = new short[(this.exception_table.length * 2)];
            System.arraycopy(this.exception_table, 0, new_table, 0, index);
            this.exception_table = new_table;
        }
        int index2 = index + 1;
        this.exception_table[index] = (short) start_pc;
        int index3 = index2 + 1;
        this.exception_table[index2] = (short) end_pc;
        int index4 = index3 + 1;
        this.exception_table[index3] = (short) handler_pc;
        int i = index4 + 1;
        this.exception_table[index4] = (short) catch_type;
        this.exception_table_length++;
    }

    public void addHandler(Label start_try, Label end_try, ClassType catch_type) {
        int catch_type_index;
        ClassType classType;
        ConstantPool constants = getConstants();
        if (catch_type == null) {
            catch_type_index = 0;
        } else {
            catch_type_index = constants.addClass((ObjectType) catch_type).index;
        }
        fixupAdd(11, start_try);
        fixupAdd(12, catch_type_index, end_try);
        Label handler = new Label();
        handler.localTypes = start_try.localTypes;
        handler.stackTypes = new Type[1];
        if (catch_type == null) {
            classType = Type.javalangThrowableType;
        } else {
            classType = catch_type;
        }
        handler.stackTypes[0] = classType;
        setTypes(handler);
        fixupAdd(13, 0, handler);
    }

    public void emitWithCleanupStart() {
        int savedSP = this.SP;
        this.SP = 0;
        emitTryStart(false, null);
        this.SP = savedSP;
    }

    public void emitWithCleanupCatch(Variable catchVar) {
        Type[] savedTypes;
        emitTryEnd();
        if (this.SP > 0) {
            savedTypes = new Type[this.SP];
            System.arraycopy(this.stack_types, 0, savedTypes, 0, this.SP);
            this.SP = 0;
        } else {
            savedTypes = null;
        }
        this.try_stack.savedTypes = savedTypes;
        this.try_stack.saved_result = catchVar;
        int i = this.SP;
        emitCatchStart(catchVar);
    }

    public void emitWithCleanupDone() {
        Variable catchVar = this.try_stack.saved_result;
        this.try_stack.saved_result = null;
        if (catchVar != null) {
            emitLoad(catchVar);
        }
        emitThrow();
        emitCatchEnd();
        Type[] savedTypes = this.try_stack.savedTypes;
        emitTryCatchEnd();
        if (savedTypes != null) {
            this.SP = savedTypes.length;
            if (this.SP >= this.stack_types.length) {
                this.stack_types = savedTypes;
            } else {
                System.arraycopy(savedTypes, 0, this.stack_types, 0, this.SP);
            }
        } else {
            this.SP = 0;
        }
    }

    public void emitTryStart(boolean has_finally, Type result_type) {
        Type[] startLocals;
        if (result_type != null && result_type.isVoid()) {
            result_type = null;
        }
        Variable[] savedStack = null;
        if (result_type != null || this.SP > 0) {
            pushScope();
        }
        if (this.SP > 0) {
            savedStack = new Variable[this.SP];
            int i = 0;
            while (this.SP > 0) {
                Variable var = addLocal(topType());
                emitStore(var);
                int i2 = i + 1;
                savedStack[i] = var;
                i = i2;
            }
        }
        TryState try_state = new TryState(this);
        try_state.savedStack = savedStack;
        int usedLocals = this.local_types == null ? 0 : this.local_types.length;
        while (usedLocals > 0 && this.local_types[usedLocals - 1] == null) {
            usedLocals--;
        }
        if (usedLocals == 0) {
            startLocals = Type.typeArray0;
        } else {
            startLocals = new Type[usedLocals];
            System.arraycopy(this.local_types, 0, startLocals, 0, usedLocals);
        }
        try_state.start_try.localTypes = startLocals;
        if (result_type != null) {
            try_state.saved_result = addLocal(result_type);
        }
        if (has_finally) {
            try_state.finally_subr = new Label();
        }
    }

    public void emitTryEnd() {
        emitTryEnd(false);
    }

    private void emitTryEnd(boolean fromFinally) {
        if (!this.try_stack.tryClauseDone) {
            this.try_stack.tryClauseDone = true;
            if (this.try_stack.finally_subr != null) {
                this.try_stack.exception = addLocal(Type.javalangThrowableType);
            }
            gotoFinallyOrEnd(fromFinally);
            this.try_stack.end_try = getLabel();
        }
    }

    public void emitCatchStart(Variable var) {
        emitTryEnd(false);
        setTypes(this.try_stack.start_try.localTypes, Type.typeArray0);
        if (this.try_stack.try_type != null) {
            emitCatchEnd();
        }
        ClassType type = var == null ? null : (ClassType) var.getType();
        this.try_stack.try_type = type;
        addHandler(this.try_stack.start_try, this.try_stack.end_try, type);
        if (var != null) {
            emitStore(var);
        }
    }

    public void emitCatchEnd() {
        gotoFinallyOrEnd(false);
        this.try_stack.try_type = null;
    }

    private void gotoFinallyOrEnd(boolean fromFinally) {
        if (reachableHere()) {
            if (this.try_stack.saved_result != null) {
                emitStore(this.try_stack.saved_result);
            }
            if (this.try_stack.end_label == null) {
                this.try_stack.end_label = new Label();
            }
            if (this.try_stack.finally_subr == null || useJsr()) {
                if (this.try_stack.finally_subr != null) {
                    emitJsr(this.try_stack.finally_subr);
                }
                emitGoto(this.try_stack.end_label);
                return;
            }
            if (this.try_stack.exitCases != null) {
                emitPushInt(0);
            }
            emitPushNull();
            if (!fromFinally) {
                emitGoto(this.try_stack.finally_subr);
            }
        }
    }

    public void emitFinallyStart() {
        emitTryEnd(true);
        if (this.try_stack.try_type != null) {
            emitCatchEnd();
        }
        this.try_stack.end_try = getLabel();
        pushScope();
        if (useJsr()) {
            this.SP = 0;
            emitCatchStart(null);
            emitStore(this.try_stack.exception);
            emitJsr(this.try_stack.finally_subr);
            emitLoad(this.try_stack.exception);
            emitThrow();
        } else {
            setUnreachable();
            int fragment_cookie = beginFragment(new Label(this));
            addHandler(this.try_stack.start_try, this.try_stack.end_try, Type.javalangThrowableType);
            if (this.try_stack.saved_result != null) {
                emitStoreDefaultValue(this.try_stack.saved_result);
            }
            if (this.try_stack.exitCases != null) {
                emitPushInt(-1);
                emitSwap();
            }
            emitGoto(this.try_stack.finally_subr);
            endFragment(fragment_cookie);
        }
        this.try_stack.finally_subr.define(this);
        if (useJsr()) {
            Type ret_addr_type = Type.objectType;
            this.try_stack.finally_ret_addr = addLocal(ret_addr_type);
            pushType(ret_addr_type);
            emitStore(this.try_stack.finally_ret_addr);
        }
    }

    public void emitFinallyEnd() {
        if (useJsr()) {
            emitRet(this.try_stack.finally_ret_addr);
        } else if (this.try_stack.end_label == null && this.try_stack.exitCases == null) {
            emitThrow();
        } else {
            emitStore(this.try_stack.exception);
            emitLoad(this.try_stack.exception);
            emitIfNotNull();
            emitLoad(this.try_stack.exception);
            emitThrow();
            emitElse();
            ExitableBlock exit = this.try_stack.exitCases;
            if (exit != null) {
                SwitchState sw = startSwitch();
                while (exit != null) {
                    ExitableBlock next = exit.nextCase;
                    exit.nextCase = null;
                    exit.currentTryState = null;
                    TryState nextTry = TryState.outerHandler(this.try_stack.previous, exit.initialTryState);
                    if (nextTry == exit.initialTryState) {
                        sw.addCaseGoto(exit.switchCase, this, exit.endLabel);
                    } else {
                        sw.addCase(exit.switchCase, this);
                        exit.exit(nextTry);
                    }
                    exit = next;
                }
                this.try_stack.exitCases = null;
                sw.addDefault(this);
                sw.finish(this);
            }
            emitFi();
            setUnreachable();
        }
        popScope();
        this.try_stack.finally_subr = null;
    }

    public void emitTryCatchEnd() {
        if (this.try_stack.finally_subr != null) {
            emitFinallyEnd();
        }
        Variable[] vars = this.try_stack.savedStack;
        if (this.try_stack.end_label == null) {
            setUnreachable();
        } else {
            setTypes(this.try_stack.start_try.localTypes, Type.typeArray0);
            this.try_stack.end_label.define(this);
            if (vars != null) {
                int i = vars.length;
                while (true) {
                    i--;
                    if (i < 0) {
                        break;
                    }
                    Variable v = vars[i];
                    if (v != null) {
                        emitLoad(v);
                    }
                }
            }
            if (this.try_stack.saved_result != null) {
                emitLoad(this.try_stack.saved_result);
            }
        }
        if (!(this.try_stack.saved_result == null && vars == null)) {
            popScope();
        }
        this.try_stack = this.try_stack.previous;
    }

    public final TryState getCurrentTry() {
        return this.try_stack;
    }

    public final boolean isInTry() {
        return this.try_stack != null;
    }

    public SwitchState startSwitch() {
        SwitchState sw = new SwitchState(this);
        sw.switchValuePushed(this);
        return sw;
    }

    public void emitTailCall(boolean pop_args, Scope scope) {
        int arg_slots;
        int i;
        int i2;
        if (pop_args) {
            Method meth = getMethod();
            if ((meth.access_flags & 8) != 0) {
                arg_slots = 0;
            } else {
                arg_slots = 1;
            }
            int i3 = meth.arg_types.length;
            while (true) {
                i3--;
                if (i3 < 0) {
                    break;
                }
                if (meth.arg_types[i3].size > 4) {
                    i2 = 2;
                } else {
                    i2 = 1;
                }
                arg_slots += i2;
            }
            int i4 = meth.arg_types.length;
            while (true) {
                i4--;
                if (i4 < 0) {
                    break;
                }
                if (meth.arg_types[i4].size > 4) {
                    i = 2;
                } else {
                    i = 1;
                }
                arg_slots -= i;
                emitStore(this.locals.used[arg_slots]);
            }
        }
        emitGoto(scope.start);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:100:0x02db, code lost:
        r25 = mergeLabels(r25, r10);
        r21 = r22;
        r13 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x02e7, code lost:
        r21 = r22 + 3;
        r13 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x02eb, code lost:
        r5 = r10.position - r14;
        r13 = r14 + 1;
        r12[r14] = r32.code[r22];
        r14 = r13 + 1;
        r12[r13] = (byte) (r5 >> 8);
        r13 = r14 + 1;
        r12[r14] = (byte) (r5 & 255);
        r21 = r22 + 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x0318, code lost:
        r5 = r10.position - r14;
        r23 = r32.code[r22];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x032a, code lost:
        if (r9 != 6) goto L_0x037d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x032c, code lost:
        r13 = r14 + 1;
        r12[r14] = invert_opcode(r23);
        r14 = r13 + 1;
        r12[r13] = 0;
        r13 = r14 + 1;
        r12[r14] = 8;
        r23 = com.google.appinventor.components.runtime.util.Ev3Constants.Opcode.READ8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x0346, code lost:
        r14 = r13 + 1;
        r12[r13] = r23;
        r13 = r14 + 1;
        r12[r14] = (byte) (r5 >> 24);
        r14 = r13 + 1;
        r12[r13] = (byte) (r5 >> 16);
        r13 = r14 + 1;
        r12[r14] = (byte) (r5 >> 8);
        r14 = r13 + 1;
        r12[r13] = (byte) (r5 & 255);
        r21 = r22 + 3;
        r13 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x037d, code lost:
        r23 = (byte) (r23 + com.google.appinventor.components.runtime.util.Ev3Constants.Opcode.OR16);
        r13 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x0386, code lost:
        r24 = (3 - r14) & 3;
        r28 = r14;
        r13 = r14 + 1;
        r21 = r22 + 1;
        r12[r14] = r32.code[r22];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x039b, code lost:
        r14 = r13;
        r24 = r24 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x039d, code lost:
        if (r24 < 0) goto L_0x03e8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x039f, code lost:
        r13 = r14 + 1;
        r12[r14] = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x03a7, code lost:
        r5 = r32.fixup_labels[r16].position - r28;
        r13 = r14 + 1;
        r12[r14] = (byte) (r5 >> 24);
        r14 = r13 + 1;
        r12[r13] = (byte) (r5 >> 16);
        r13 = r14 + 1;
        r12[r14] = (byte) (r5 >> 8);
        r14 = r13 + 1;
        r12[r13] = (byte) (r5 & 255);
        r21 = r22 + 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x03e8, code lost:
        r13 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x03f2, code lost:
        if (r16 >= r32.fixup_count) goto L_0x02b6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x0404, code lost:
        if (fixupKind(r16 + 1) != 3) goto L_0x02b6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x0406, code lost:
        r16 = r16 + 1;
        r19 = fixupOffset(r16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x0413, code lost:
        r22 = r21;
        r14 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x0417, code lost:
        if (r22 >= r19) goto L_0x03a7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x0419, code lost:
        r13 = r14 + 1;
        r21 = r22 + 1;
        r12[r14] = r32.code[r22];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x042b, code lost:
        r10 = r32.fixup_labels[r16 + 2];
        r6 = fixupOffset(r16 + 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x0445, code lost:
        if (r32.stackMap == null) goto L_0x044f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x0447, code lost:
        r25 = mergeLabels(r25, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x044f, code lost:
        addHandler(r32.fixup_labels[r16].position, r32.fixup_labels[r16 + 1].position, r14, r6);
        r16 = r16 + 2;
        r21 = r22;
        r13 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x0483, code lost:
        if (r32.lines != null) goto L_0x0494;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x0485, code lost:
        r0 = new gnu.bytecode.LineNumbersAttr(r32);
        r32.lines = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x0494, code lost:
        r16 = r16 + 1;
        r11 = fixupOffset(r16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x04a0, code lost:
        if (r11 == r26) goto L_0x04ad;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:0x04a2, code lost:
        r32.lines.put(r11, r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:0x04ad, code lost:
        r26 = r11;
        r21 = r22;
        r13 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x04b4, code lost:
        if (r10 != null) goto L_0x04df;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x04b6, code lost:
        if (r15 == r14) goto L_0x04fd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:0x04de, code lost:
        throw new java.lang.Error("PC confusion new_pc:" + r14 + " new_size:" + r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x04df, code lost:
        r16 = r10.first_fixup;
        r21 = fixupOffset(r16);
        r17 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x04f3, code lost:
        if (r10.position == r14) goto L_0x051f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x04fc, code lost:
        throw new java.lang.Error("bad pc");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x04fd, code lost:
        r32.PC = r15;
        r32.code = r12;
        r32.fixup_count = 0;
        r32.fixup_labels = null;
        r32.fixup_offsets = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:141:0x051f, code lost:
        r22 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x0526, code lost:
        r21 = r22;
        r13 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:174:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00d0, code lost:
        if ((r7 + 1) < r32.fixup_count) goto L_0x0129;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00d2, code lost:
        r4 = r32.PC;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00d6, code lost:
        r32.fixup_offsets[r7] = (r4 << 4) | 9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00e2, code lost:
        if (r10 != null) goto L_0x0142;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00e4, code lost:
        r15 = r32.PC;
        r5 = 0;
        r7 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00f2, code lost:
        if (r7 >= r32.fixup_count) goto L_0x0225;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00f4, code lost:
        r19 = r32.fixup_offsets[r7];
        r9 = r19 & 15;
        r10 = r32.fixup_labels[r7];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0106, code lost:
        if (r10 == null) goto L_0x0150;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x010c, code lost:
        if (r10.position >= 0) goto L_0x0150;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0128, code lost:
        throw new java.lang.Error("undefined label " + r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0129, code lost:
        r4 = fixupOffset(r32.fixup_labels[r7 + 1].first_fixup);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0142, code lost:
        r7 = r10.first_fixup;
        r5 = (r4 + r5) - fixupOffset(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0150, code lost:
        if (r10 == null) goto L_0x01ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0156, code lost:
        if (r9 < 4) goto L_0x01ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x015c, code lost:
        if (r9 > 7) goto L_0x01ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x016e, code lost:
        if ((r10.first_fixup + 1) >= r32.fixup_count) goto L_0x01ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0192, code lost:
        if (r32.fixup_offsets[r10.first_fixup + 1] != ((r32.fixup_offsets[r10.first_fixup] & 15) | 4)) goto L_0x01ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0194, code lost:
        r10 = r32.fixup_labels[r10.first_fixup + 1];
        r32.fixup_labels[r7] = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x01ab, code lost:
        r19 = r19 >> 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x01ad, code lost:
        switch(r9) {
            case 0: goto L_0x01ca;
            case 1: goto L_0x01d6;
            case 2: goto L_0x01dd;
            case 3: goto L_0x01ca;
            case 4: goto L_0x01e8;
            case 5: goto L_0x01e8;
            case 6: goto L_0x01e8;
            case 7: goto L_0x01b0;
            case 8: goto L_0x01d1;
            case 9: goto L_0x0223;
            case 10: goto L_0x01b0;
            case 11: goto L_0x01b8;
            case 12: goto L_0x01b0;
            case 13: goto L_0x01b0;
            case 14: goto L_0x01ce;
            default: goto L_0x01b0;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x01b7, code lost:
        throw new java.lang.Error("unexpected fixup");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x01b8, code lost:
        r7 = r7 + 2;
        r32.fixup_labels[r7].position = r19 + r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x01ca, code lost:
        r7 = r7 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x01ce, code lost:
        r7 = r7 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x01d1, code lost:
        r5 = r5 - 3;
        r15 = r15 - 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x01d6, code lost:
        r10.position = r19 + r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x01dd, code lost:
        r24 = (3 - (r19 + r5)) & 3;
        r5 = r5 + r24;
        r15 = r15 + r24;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x01e8, code lost:
        r27 = r10.position - (r19 + r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x01f9, code lost:
        if (((short) r27) != r27) goto L_0x0208;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x01fb, code lost:
        r32.fixup_offsets[r7] = (r19 << 4) | 7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x020c, code lost:
        if (r9 != 6) goto L_0x021d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x020e, code lost:
        r29 = 5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0210, code lost:
        r5 = r5 + r29;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0216, code lost:
        if (r9 != 6) goto L_0x0220;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0218, code lost:
        r29 = 5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x021a, code lost:
        r15 = r15 + r29;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x021d, code lost:
        r29 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0220, code lost:
        r29 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0223, code lost:
        if (r10 != null) goto L_0x0257;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0225, code lost:
        r12 = new byte[r15];
        r26 = -1;
        r16 = 0;
        r17 = fixupOffset(0);
        r25 = null;
        r22 = 0;
        r14 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0243, code lost:
        if (r22 >= r17) goto L_0x0265;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0245, code lost:
        r13 = r14 + 1;
        r21 = r22 + 1;
        r12[r14] = r32.code[r22];
        r22 = r21;
        r14 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0257, code lost:
        r7 = r10.first_fixup;
        r5 = (r19 + r5) - fixupOffset(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0265, code lost:
        r9 = r32.fixup_offsets[r16] & 15;
        r10 = r32.fixup_labels[r16];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0277, code lost:
        if (r25 == null) goto L_0x0294;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0281, code lost:
        if (r25.position >= r14) goto L_0x0294;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0283, code lost:
        r32.stackMap.emitStackMapEntry(r25, r32);
        r25 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0294, code lost:
        if (r25 == null) goto L_0x02a8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x029e, code lost:
        if (r25.position <= r14) goto L_0x02a8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x02a7, code lost:
        throw new java.lang.Error("labels out of order");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x02a8, code lost:
        switch(r9) {
            case 0: goto L_0x02b3;
            case 1: goto L_0x02c5;
            case 2: goto L_0x0386;
            case 3: goto L_0x02ab;
            case 4: goto L_0x0318;
            case 5: goto L_0x0318;
            case 6: goto L_0x0318;
            case 7: goto L_0x02eb;
            case 8: goto L_0x02e7;
            case 9: goto L_0x04b4;
            case 10: goto L_0x02ab;
            case 11: goto L_0x042b;
            case 12: goto L_0x02ab;
            case 13: goto L_0x02ab;
            case 14: goto L_0x047d;
            default: goto L_0x02ab;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0041, code lost:
        r7 = r7 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x02b2, code lost:
        throw new java.lang.Error("unexpected fixup");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x02b3, code lost:
        r21 = r22;
        r13 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x02b6, code lost:
        r16 = r16 + 1;
        r17 = fixupOffset(r16);
        r22 = r21;
        r14 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x02cb, code lost:
        if (r32.stackMap == null) goto L_0x0526;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x02cd, code lost:
        if (r10 == null) goto L_0x0526;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x02d3, code lost:
        if (r10.stackTypes == null) goto L_0x0526;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x02d9, code lost:
        if (r10.needsStackMapEntry == false) goto L_0x0526;
     */
    public void processFixups() {
        if (this.fixup_count > 0) {
            int delta = 0;
            int instruction_tail = this.fixup_count;
            fixupAdd(9, 0, null);
            int i = 0;
            while (true) {
                int offset = this.fixup_offsets[i];
                int kind = offset & 15;
                int offset2 = offset >> 4;
                Label label = this.fixup_labels[i];
                switch (kind) {
                    case 0:
                    case 3:
                    case 8:
                        break;
                    case 1:
                        label.position += delta;
                        break;
                    case 2:
                        delta += 3;
                        break;
                    case 4:
                        if (label.first_fixup == i + 1 && fixupOffset(i + 1) == offset2 + 3) {
                            this.fixup_offsets[i] = (offset2 << 4) | 8;
                            this.fixup_labels[i] = null;
                            delta -= 3;
                            break;
                        }
                    case 5:
                        if (this.PC >= 32768) {
                            delta += 2;
                            break;
                        }
                        break;
                    case 6:
                        if (this.PC >= 32768) {
                            delta += 5;
                            break;
                        }
                        break;
                    case 9:
                        break;
                    case 10:
                        this.fixup_labels[instruction_tail] = this.fixup_labels[i + 1];
                        instruction_tail = offset2;
                        break;
                    case 11:
                        i += 2;
                        break;
                    case 14:
                        i++;
                        break;
                    default:
                        throw new Error("unexpected fixup");
                }
            }
        }
    }

    private Label mergeLabels(Label oldLabel, Label newLabel) {
        if (oldLabel != null) {
            newLabel.setTypes(oldLabel);
        }
        return newLabel;
    }

    public void assignConstants(ClassType cl) {
        if (this.locals != null && this.locals.container == null && !this.locals.isEmpty()) {
            this.locals.addToFrontOf(this);
        }
        processFixups();
        if (this.stackMap != null && this.stackMap.numEntries > 0) {
            this.stackMap.addToFrontOf(this);
        }
        if (instructionLineMode) {
            if (this.lines == null) {
                this.lines = new LineNumbersAttr(this);
            }
            this.lines.linenumber_count = 0;
            int codeLen = getCodeLength();
            for (int i = 0; i < codeLen; i++) {
                this.lines.put(i, i);
            }
        }
        super.assignConstants(cl);
        Attribute.assignConstants(this, cl);
    }

    public final int getLength() {
        return getCodeLength() + 12 + (this.exception_table_length * 8) + Attribute.getLengthAll(this);
    }

    public void write(DataOutputStream dstr) throws IOException {
        dstr.writeShort(this.max_stack);
        dstr.writeShort(this.max_locals);
        dstr.writeInt(this.PC);
        dstr.write(this.code, 0, this.PC);
        dstr.writeShort(this.exception_table_length);
        int count = this.exception_table_length;
        int i = 0;
        while (true) {
            count--;
            if (count >= 0) {
                dstr.writeShort(this.exception_table[i]);
                dstr.writeShort(this.exception_table[i + 1]);
                dstr.writeShort(this.exception_table[i + 2]);
                dstr.writeShort(this.exception_table[i + 3]);
                i += 4;
            } else {
                Attribute.writeAll(this, dstr);
                return;
            }
        }
    }

    public void print(ClassTypeWriter dst) {
        dst.print("Attribute \"");
        dst.print(getName());
        dst.print("\", length:");
        dst.print(getLength());
        dst.print(", max_stack:");
        dst.print(this.max_stack);
        dst.print(", max_locals:");
        dst.print(this.max_locals);
        dst.print(", code_length:");
        int length = getCodeLength();
        dst.println(length);
        disAssemble(dst, 0, length);
        if (this.exception_table_length > 0) {
            dst.print("Exceptions (count: ");
            dst.print(this.exception_table_length);
            dst.println("):");
            int count = this.exception_table_length;
            int i = 0;
            while (true) {
                count--;
                if (count < 0) {
                    break;
                }
                dst.print("  start: ");
                dst.print(this.exception_table[i] & 65535);
                dst.print(", end: ");
                dst.print(this.exception_table[i + 1] & 65535);
                dst.print(", handler: ");
                dst.print(this.exception_table[i + 2] & 65535);
                dst.print(", type: ");
                int catch_type_index = this.exception_table[i + 3] & SupportMenu.USER_MASK;
                if (catch_type_index == 0) {
                    dst.print("0 /* finally */");
                } else {
                    dst.printOptionalIndex(catch_type_index);
                    dst.printConstantTersely(catch_type_index, 7);
                }
                dst.println();
                i += 4;
            }
        }
        dst.printAttributes(this);
    }

    /* JADX WARNING: type inference failed for: r26v103, types: [byte[]] */
    /* JADX WARNING: type inference failed for: r26v140, types: [byte[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    public void disAssemble(ClassTypeWriter dst, int start, int limit) {
        int i;
        int i2;
        int index;
        int var_index;
        int i3;
        int constant;
        int index2;
        String load_or_store;
        int index3;
        int op;
        int index4;
        int i4;
        int constant2;
        char typ;
        int op2;
        boolean wide = false;
        int i5 = start;
        while (true) {
            int i6 = i5;
            if (i6 < limit) {
                int i7 = i6 + 1;
                int oldpc = i6;
                int op3 = this.code[oldpc] & Opcode.TST;
                String str = Integer.toString(oldpc);
                char c = 0;
                int j = str.length();
                while (true) {
                    j++;
                    if (j > 3) {
                        break;
                    }
                    dst.print(' ');
                }
                dst.print(str);
                dst.print(": ");
                if (op3 < 120) {
                    if (op3 < 87) {
                        if (op3 < 3) {
                            print("nop;aconst_null;iconst_m1;", op3, dst);
                            i = i7;
                        } else if (op3 < 9) {
                            dst.print("iconst_");
                            dst.print(op3 - 3);
                            i = i7;
                        } else if (op3 < 16) {
                            if (op3 < 11) {
                                typ = 'l';
                                op2 = op3 - 9;
                            } else if (op3 < 14) {
                                typ = 'f';
                                op2 = op3 - 11;
                            } else {
                                typ = 'd';
                                op2 = op3 - 14;
                            }
                            dst.print(typ);
                            dst.print("const_");
                            dst.print(op2);
                            i = i7;
                        } else if (op3 >= 21) {
                            if (op3 < 54) {
                                load_or_store = "load";
                            } else {
                                load_or_store = "store";
                                op3 -= 33;
                            }
                            if (op3 < 26) {
                                index3 = -1;
                                op = op3 - 21;
                            } else if (op3 < 46) {
                                int op4 = op3 - 26;
                                index3 = op4 % 4;
                                op = op4 >> 2;
                            } else {
                                index3 = -2;
                                op = op3 - 46;
                            }
                            dst.print("ilfdabcs".charAt(op));
                            if (index3 == -2) {
                                dst.write(97);
                            }
                            dst.print(load_or_store);
                            if (index3 >= 0) {
                                dst.write(95);
                                dst.print(index3);
                            } else if (index3 == -1) {
                                if (wide) {
                                    index4 = readUnsignedShort(i7);
                                    i7 += 2;
                                } else {
                                    index4 = this.code[i7] & Opcode.TST;
                                    i7++;
                                }
                                wide = false;
                                dst.print(' ');
                                dst.print(index4);
                            }
                            i = i7;
                        } else if (op3 < 18) {
                            print("bipush ;sipush ;", op3 - 16, dst);
                            if (op3 == 16) {
                                int i8 = i7 + 1;
                                byte b = this.code[i7];
                                i4 = i8;
                                constant2 = b;
                            } else {
                                i4 = i7 + 2;
                                constant2 = (short) readUnsignedShort(i7);
                            }
                            dst.print(constant2);
                            i = i4;
                        } else {
                            c = op3 == 18 ? (char) 1 : 2;
                            print("ldc;ldc_w;ldc2_w;", op3 - 18, dst);
                            i = i7;
                        }
                    } else if (op3 < 96) {
                        print("pop;pop2;dup;dup_x1;dup_x2;dup2;dup2_x1;dup2_x2;swap;", op3 - 87, dst);
                        i = i7;
                    } else {
                        dst.print("ilfda".charAt((op3 - 96) % 4));
                        print("add;sub;mul;div;rem;neg;", (op3 - 96) >> 2, dst);
                        i = i7;
                    }
                } else if (op3 < 170) {
                    if (op3 < 132) {
                        dst.print((op3 & 1) == 0 ? 'i' : 'l');
                        print("shl;shr;ushr;and;or;xor;", (op3 - 120) >> 1, dst);
                        i = i7;
                    } else if (op3 == 132) {
                        dst.print("iinc");
                        if (!wide) {
                            int i9 = i7 + 1;
                            var_index = this.code[i7] & Opcode.TST;
                            i3 = i9 + 1;
                            constant = this.code[i9];
                        } else {
                            var_index = readUnsignedShort(i7);
                            int i10 = i7 + 2;
                            i3 = i10 + 2;
                            wide = false;
                            constant = (short) readUnsignedShort(i10);
                        }
                        dst.print(' ');
                        dst.print(var_index);
                        dst.print(' ');
                        dst.print(constant);
                        i = i3;
                    } else if (op3 < 148) {
                        dst.print("ilfdi".charAt((op3 - 133) / 3));
                        dst.print('2');
                        dst.print("lfdifdildilfbcs".charAt(op3 - 133));
                        i = i7;
                    } else if (op3 < 153) {
                        print("lcmp;fcmpl;fcmpg;dcmpl;dcmpg;", op3 - 148, dst);
                        i = i7;
                    } else if (op3 < 169) {
                        if (op3 < 159) {
                            dst.print("if");
                            print("eq;ne;lt;ge;gt;le;", op3 - 153, dst);
                        } else if (op3 < 167) {
                            if (op3 < 165) {
                                dst.print("if_icmp");
                            } else {
                                dst.print("if_acmp");
                                op3 -= 6;
                            }
                            print("eq;ne;lt;ge;gt;le;", op3 - 159, dst);
                        } else {
                            print("goto;jsr;", op3 - 167, dst);
                        }
                        int delta = (short) readUnsignedShort(i7);
                        int i11 = i7 + 2;
                        dst.print(' ');
                        dst.print(oldpc + delta);
                        i = i11;
                    } else {
                        dst.print("ret ");
                        if (wide) {
                            index = readUnsignedShort(i7) + 2;
                        } else {
                            index = this.code[i7] & Opcode.TST;
                            i7++;
                        }
                        wide = false;
                        dst.print(index);
                        i = i7;
                    }
                } else if (op3 < 172) {
                    if (this.fixup_count <= 0) {
                        i7 = (i7 + 3) & -4;
                    }
                    int code_offset = readInt(i7);
                    int i12 = i7 + 4;
                    if (op3 != 170) {
                        dst.print("lookupswitch");
                        int npairs = readInt(i12);
                        int i13 = i12 + 4;
                        dst.print(" npairs: ");
                        dst.print(npairs);
                        dst.print(" default: ");
                        dst.print(oldpc + code_offset);
                        while (true) {
                            npairs--;
                            if (npairs < 0) {
                                break;
                            }
                            int match = readInt(i2);
                            int i14 = i2 + 4;
                            int code_offset2 = readInt(i14);
                            i13 = i14 + 4;
                            dst.println();
                            dst.print("  ");
                            dst.print(match);
                            dst.print(": ");
                            dst.print(oldpc + code_offset2);
                        }
                    } else {
                        dst.print("tableswitch");
                        int low = readInt(i12);
                        int i15 = i12 + 4;
                        int high = readInt(i15);
                        i2 = i15 + 4;
                        dst.print(" low: ");
                        dst.print(low);
                        dst.print(" high: ");
                        dst.print(high);
                        dst.print(" default: ");
                        dst.print(oldpc + code_offset);
                        while (low <= high) {
                            int code_offset3 = readInt(i2);
                            i2 += 4;
                            dst.println();
                            dst.print("  ");
                            dst.print(low);
                            dst.print(": ");
                            dst.print(oldpc + code_offset3);
                            low++;
                        }
                    }
                    i = i2;
                } else if (op3 < 178) {
                    if (op3 < 177) {
                        dst.print("ilfda".charAt(op3 - 172));
                    }
                    dst.print("return");
                    i = i7;
                } else if (op3 < 182) {
                    print("getstatic;putstatic;getfield;putfield;", op3 - 178, dst);
                    c = 2;
                    i = i7;
                } else if (op3 < 185) {
                    dst.print("invoke");
                    print("virtual;special;static;", op3 - 182, dst);
                    c = 2;
                    i = i7;
                } else if (op3 == 185) {
                    dst.print("invokeinterface (");
                    int index5 = readUnsignedShort(i7);
                    int i16 = i7 + 2;
                    int args = this.code[i16] & 255;
                    int i17 = i16 + 2;
                    dst.print(args + " args)");
                    dst.printConstantOperand(index5);
                    i = i17;
                } else {
                    if (op3 < 196) {
                        print("186;new;newarray;anewarray;arraylength;athrow;checkcast;instanceof;monitorenter;monitorexit;", op3 - 186, dst);
                        if (op3 == 187 || op3 == 189 || op3 == 192 || op3 == 193) {
                            c = 2;
                            i = i7;
                        } else if (op3 == 188) {
                            i = i7 + 1;
                            byte type = this.code[i7];
                            dst.print(' ');
                            if (type < 4 || type > 11) {
                                dst.print(type);
                            } else {
                                print("boolean;char;float;double;byte;short;int;long;", type - 4, dst);
                            }
                        }
                    } else if (op3 == 196) {
                        dst.print("wide");
                        wide = true;
                        i = i7;
                    } else if (op3 == 197) {
                        dst.print("multianewarray");
                        int index6 = readUnsignedShort(i7);
                        int i18 = i7 + 2;
                        dst.printConstantOperand(index6);
                        i = i18 + 1;
                        int dims = this.code[i18] & 255;
                        dst.print(' ');
                        dst.print(dims);
                    } else if (op3 < 200) {
                        print("ifnull;ifnonnull;", op3 - 198, dst);
                        int delta2 = (short) readUnsignedShort(i7);
                        int i19 = i7 + 2;
                        dst.print(' ');
                        dst.print(oldpc + delta2);
                        i = i19;
                    } else if (op3 < 202) {
                        print("goto_w;jsr_w;", op3 - 200, dst);
                        int delta3 = readInt(i7);
                        int i20 = i7 + 4;
                        dst.print(' ');
                        dst.print(oldpc + delta3);
                        i = i20;
                    } else {
                        dst.print(op3);
                    }
                    i = i7;
                }
                if (c > 0) {
                    if (c == 1) {
                        i5 = i + 1;
                        index2 = this.code[i] & Opcode.TST;
                    } else {
                        index2 = readUnsignedShort(i);
                        i5 = i + 2;
                    }
                    dst.printConstantOperand(index2);
                } else {
                    i5 = i;
                }
                dst.println();
            } else {
                return;
            }
        }
    }

    private int readUnsignedShort(int offset) {
        return ((this.code[offset] & Opcode.TST) << 8) | (this.code[offset + 1] & Opcode.TST);
    }

    private int readInt(int offset) {
        return (readUnsignedShort(offset) << 16) | readUnsignedShort(offset + 2);
    }

    private void print(String str, int i, PrintWriter dst) {
        int last = 0;
        int pos = -1;
        while (i >= 0) {
            last = pos + 1;
            pos = str.indexOf(59, last);
            i--;
        }
        dst.write(str, last, pos - last);
    }

    public int beginFragment(Label after) {
        return beginFragment(new Label(), after);
    }

    public int beginFragment(Label start, Label after) {
        int i = this.fixup_count;
        fixupAdd(10, after);
        start.define(this);
        return i;
    }

    public void endFragment(int cookie) {
        this.fixup_offsets[cookie] = (this.fixup_count << 4) | 10;
        Label after = this.fixup_labels[cookie];
        fixupAdd(9, 0, null);
        after.define(this);
    }
}
