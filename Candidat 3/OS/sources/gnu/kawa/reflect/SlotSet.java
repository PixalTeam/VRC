package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Member;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.CheckedTarget;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Language;
import gnu.expr.QuoteExp;
import gnu.expr.Target;
import gnu.lists.FString;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure3;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrappedException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kawa.standard.Scheme;

public class SlotSet extends Procedure3 implements Inlineable {
    public static final SlotSet set$Mnfield$Ex = new SlotSet("set-field!", false);
    public static final SlotSet set$Mnstatic$Mnfield$Ex = new SlotSet("set-static-field!", true);
    public static final SlotSet setFieldReturnObject = new SlotSet("set-field-return-object!", false);
    static final Type[] type1Array = new Type[1];
    boolean isStatic;
    boolean returnSelf;

    static {
        setFieldReturnObject.returnSelf = true;
    }

    public SlotSet(String name, boolean isStatic2) {
        super(name);
        this.isStatic = isStatic2;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileReflect:validateApplySlotSet");
    }

    public static void setField(Object obj, String name, Object value) {
        apply(false, obj, name, value);
    }

    public static void setStaticField(Object obj, String name, Object value) {
        apply(true, obj, name, value);
    }

    /* JADX WARNING: Removed duplicated region for block: B:55:0x012a  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0145  */
    public static void apply(boolean isStatic2, Object obj, Object member, Object value) {
        String name;
        String fname;
        Class clas;
        String getName;
        Method getmethod;
        String getName2;
        Language language = Language.getDefaultLanguage();
        boolean illegalAccess = false;
        if ((member instanceof String) || (member instanceof FString) || (member instanceof Symbol)) {
            name = member.toString();
            fname = Compilation.mangleNameIfNeeded(name);
            clas = isStatic2 ? SlotGet.coerceToClass(obj) : obj.getClass();
        } else {
            name = ((Member) member).getName();
            fname = name;
            clas = null;
        }
        try {
            Field field = member instanceof gnu.bytecode.Field ? ((gnu.bytecode.Field) member).getReflectField() : clas.getField(fname);
            field.set(obj, language.coerceFromObject(field.getType(), value));
            return;
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e2) {
            illegalAccess = true;
        }
        try {
            boolean haveSetter = member instanceof gnu.bytecode.Method;
            String setName = haveSetter ? fname : ClassExp.slotToMethodName("set", name);
            if (haveSetter && !setName.startsWith("set")) {
                haveSetter = false;
            }
            if (haveSetter) {
                try {
                    getName2 = "get" + setName.substring(3);
                } catch (Exception e3) {
                    if (haveSetter) {
                        getName = "is" + setName.substring(3);
                    } else {
                        getName = ClassExp.slotToMethodName("is", name);
                    }
                    getmethod = clas.getMethod(getName, SlotGet.noClasses);
                    Class[] setArgTypes = {getmethod.getReturnType()};
                    clas.getMethod(setName, setArgTypes).invoke(obj, new Object[]{language.coerceFromObject(setArgTypes[0], value)});
                }
            } else {
                getName2 = ClassExp.slotToMethodName("get", name);
            }
            getmethod = clas.getMethod(getName2, SlotGet.noClasses);
            Class[] setArgTypes2 = {getmethod.getReturnType()};
            clas.getMethod(setName, setArgTypes2).invoke(obj, new Object[]{language.coerceFromObject(setArgTypes2[0], value)});
        } catch (InvocationTargetException ex) {
            throw WrappedException.wrapIfNeeded(ex.getTargetException());
        } catch (IllegalAccessException e4) {
            illegalAccess = true;
            if (!illegalAccess) {
                throw new RuntimeException("illegal access for field " + name);
            }
            throw new RuntimeException("no such field " + name + " in " + clas.getName());
        } catch (NoSuchMethodException e5) {
            if (!illegalAccess) {
            }
        }
    }

    public Object apply3(Object obj, Object fname, Object value) {
        apply(this.isStatic, obj, fname, value);
        return this.returnSelf ? obj : Values.empty;
    }

    public static Member lookupMember(ObjectType clas, String name, ClassType caller) {
        gnu.bytecode.Field field = clas.getField(Compilation.mangleNameIfNeeded(name), -1);
        if (field != null) {
            if (caller == null) {
                caller = Type.pointer_type;
            }
            if (caller.isAccessible(field, clas)) {
                return field;
            }
        }
        gnu.bytecode.Method method = clas.getMethod(ClassExp.slotToMethodName("set", name), type1Array);
        if (method != null) {
            return method;
        }
        return field;
    }

    static void compileSet(Procedure thisProc, ObjectType ctype, Expression valArg, Object part, Compilation comp) {
        CodeAttr code = comp.getCode();
        Language language = comp.getLanguage();
        boolean isStatic2 = (thisProc instanceof SlotSet) && ((SlotSet) thisProc).isStatic;
        if (part instanceof gnu.bytecode.Field) {
            gnu.bytecode.Field field = (gnu.bytecode.Field) part;
            boolean isStaticField = field.getStaticFlag();
            Type ftype = language.getLangTypeFor(field.getType());
            if (isStatic2 && !isStaticField) {
                comp.error('e', "cannot access non-static field `" + field.getName() + "' using `" + thisProc.getName() + '\'');
            }
            valArg.compile(comp, CheckedTarget.getInstance(ftype));
            if (isStaticField) {
                code.emitPutStatic(field);
            } else {
                code.emitPutField(field);
            }
        } else if (part instanceof gnu.bytecode.Method) {
            gnu.bytecode.Method method = (gnu.bytecode.Method) part;
            boolean isStaticMethod = method.getStaticFlag();
            if (isStatic2 && !isStaticMethod) {
                comp.error('e', "cannot call non-static getter method `" + method.getName() + "' using `" + thisProc.getName() + '\'');
            }
            valArg.compile(comp, CheckedTarget.getInstance(language.getLangTypeFor(method.getParameterTypes()[0])));
            if (isStaticMethod) {
                code.emitInvokeStatic(method);
            } else {
                code.emitInvoke(method);
            }
            if (!method.getReturnType().isVoid()) {
                code.emitPop(1);
            }
        }
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        ClassType caller;
        String name;
        Expression[] args = exp.getArgs();
        int nargs = args.length;
        if (nargs != 3) {
            comp.error('e', (nargs < 3 ? "too few" : "too many") + " arguments to `" + getName() + '\'');
            comp.compileConstant(null, target);
            return;
        }
        Expression arg0 = args[0];
        Expression arg1 = args[1];
        Expression expression = args[2];
        Type type = this.isStatic ? Scheme.exp2Type(arg0) : arg0.getType();
        Member part = null;
        if ((type instanceof ObjectType) && (arg1 instanceof QuoteExp)) {
            Object val1 = ((QuoteExp) arg1).getValue();
            ObjectType ctype = (ObjectType) type;
            if (comp.curClass != null) {
                caller = comp.curClass;
            } else {
                caller = comp.mainClass;
            }
            if ((val1 instanceof String) || (val1 instanceof FString) || (val1 instanceof Symbol)) {
                name = val1.toString();
                part = lookupMember(ctype, name, caller);
                if (part == null && type != Type.pointer_type && comp.warnUnknownMember()) {
                    comp.error('w', "no slot `" + name + "' in " + ctype.getName());
                }
            } else if (val1 instanceof Member) {
                part = (Member) val1;
                name = part.getName();
            } else {
                name = null;
            }
            if (part != null) {
                boolean isStaticField = (part.getModifiers() & 8) != 0;
                if (caller != null && !caller.isAccessible(part, ctype)) {
                    comp.error('e', "slot '" + name + "' in " + part.getDeclaringClass().getName() + " not accessible here");
                }
                args[0].compile(comp, isStaticField ? Target.Ignore : Target.pushValue(ctype));
                if (this.returnSelf) {
                    comp.getCode().emitDup(ctype.getImplementationType());
                }
                compileSet(this, ctype, args[2], part, comp);
                if (this.returnSelf) {
                    target.compileFromStack(comp, ctype);
                    return;
                } else {
                    comp.compileConstant(Values.empty, target);
                    return;
                }
            }
        }
        ApplyExp.compile(exp, comp, target);
    }
}
