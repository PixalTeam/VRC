package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.GenericProc;
import gnu.expr.Language;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.lists.FString;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure2;
import gnu.mapping.Symbol;
import gnu.mapping.WrongType;
import java.util.Vector;

public class ClassMethods extends Procedure2 {
    public static final ClassMethods classMethods = new ClassMethods();

    static {
        classMethods.setName("class-methods");
    }

    public Object apply2(Object arg0, Object arg1) {
        return apply(this, arg0, arg1);
    }

    public static MethodProc apply(Procedure thisProc, Object arg0, Object arg1) {
        ClassType dtype;
        if (arg0 instanceof Class) {
            arg0 = Type.make((Class) arg0);
        }
        if (arg0 instanceof ClassType) {
            dtype = (ClassType) arg0;
        } else if ((arg0 instanceof String) || (arg0 instanceof FString) || (arg0 instanceof Symbol)) {
            dtype = ClassType.make(arg0.toString());
        } else {
            throw new WrongType(thisProc, 0, (ClassCastException) null);
        }
        if ((arg1 instanceof String) || (arg1 instanceof FString) || (arg1 instanceof Symbol)) {
            String mname = arg1.toString();
            if (!"<init>".equals(mname)) {
                mname = Compilation.mangleName(mname);
            }
            MethodProc result = apply(dtype, mname, 0, Language.getDefaultLanguage());
            if (result != null) {
                return result;
            }
            throw new RuntimeException("no applicable method named `" + mname + "' in " + dtype.getName());
        }
        throw new WrongType(thisProc, 1, (ClassCastException) null);
    }

    private static int removeRedundantMethods(Vector methods) {
        int mlength = methods.size();
        int i = 1;
        while (i < mlength) {
            Method method1 = (Method) methods.elementAt(i);
            ClassType class1 = method1.getDeclaringClass();
            Type[] types1 = method1.getParameterTypes();
            int tlen = types1.length;
            int j = 0;
            while (true) {
                if (j >= i) {
                    i++;
                    break;
                }
                Method method2 = (Method) methods.elementAt(j);
                Type[] types2 = method2.getParameterTypes();
                if (tlen == types2.length) {
                    int k = tlen;
                    do {
                        k--;
                        if (k < 0) {
                            break;
                        }
                    } while (types1[k] == types2[k]);
                    if (k < 0) {
                        if (class1.isSubtype(method2.getDeclaringClass())) {
                            methods.setElementAt(method1, j);
                        }
                        methods.setElementAt(methods.elementAt(mlength - 1), i);
                        mlength--;
                    }
                }
                j++;
            }
        }
        return mlength;
    }

    public static PrimProcedure[] getMethods(ObjectType dtype, String mname, char mode, ClassType caller, Language language) {
        ObjectType objectType;
        if (dtype == Type.tostring_type) {
            dtype = Type.string_type;
        }
        if (mode == 'P') {
            objectType = null;
        } else {
            objectType = dtype;
        }
        MethodFilter filter = new MethodFilter(mname, 0, 0, caller, objectType);
        boolean named_class_only = mode == 'P' || "<init>".equals(mname);
        Vector methods = new Vector();
        dtype.getMethods(filter, named_class_only ? 0 : 2, methods);
        if (!named_class_only && (!(dtype instanceof ClassType) || ((ClassType) dtype).isInterface())) {
            Type.pointer_type.getMethods(filter, 0, methods);
        }
        int mlength = named_class_only ? methods.size() : removeRedundantMethods(methods);
        PrimProcedure[] result = new PrimProcedure[mlength];
        int count = 0;
        int i = mlength;
        while (true) {
            int count2 = count;
            i--;
            if (i < 0) {
                return result;
            }
            Method method = (Method) methods.elementAt(i);
            if (!named_class_only && method.getDeclaringClass() != dtype) {
                Type itype = dtype.getImplementationType();
                if (itype instanceof ClassType) {
                    method = new Method(method, (ClassType) itype);
                }
            }
            PrimProcedure primProcedure = new PrimProcedure(method, mode, language);
            count = count2 + 1;
            result[count2] = primProcedure;
        }
    }

    public static long selectApplicable(PrimProcedure[] methods, Type[] atypes) {
        int limit = methods.length;
        int numDefApplicable = 0;
        int numPosApplicable = 0;
        int i = 0;
        while (i < limit) {
            int code = methods[i].isApplicable(atypes);
            if (code < 0) {
                PrimProcedure tmp = methods[limit - 1];
                methods[limit - 1] = methods[i];
                methods[i] = tmp;
                limit--;
            } else if (code > 0) {
                PrimProcedure tmp2 = methods[numDefApplicable];
                methods[numDefApplicable] = methods[i];
                methods[i] = tmp2;
                numDefApplicable++;
                i++;
            } else {
                numPosApplicable++;
                i++;
            }
        }
        return (((long) numDefApplicable) << 32) + ((long) numPosApplicable);
    }

    public static int selectApplicable(PrimProcedure[] methods, int numArgs) {
        int limit = methods.length;
        int numTooManyArgs = 0;
        int numTooFewArgs = 0;
        int numOk = 0;
        int i = 0;
        while (i < limit) {
            int num = methods[i].numArgs();
            int min = Procedure.minArgs(num);
            int max = Procedure.maxArgs(num);
            boolean ok = false;
            if (numArgs < min) {
                numTooFewArgs++;
            } else if (numArgs <= max || max < 0) {
                ok = true;
            } else {
                numTooManyArgs++;
            }
            if (ok) {
                numOk++;
                i++;
            } else {
                PrimProcedure tmp = methods[limit - 1];
                methods[limit - 1] = methods[i];
                methods[i] = tmp;
                limit--;
            }
        }
        if (numOk > 0) {
            return numOk;
        }
        if (numTooFewArgs > 0) {
            return MethodProc.NO_MATCH_TOO_FEW_ARGS;
        }
        if (numTooManyArgs > 0) {
            return MethodProc.NO_MATCH_TOO_MANY_ARGS;
        }
        return 0;
    }

    public static MethodProc apply(ObjectType dtype, String mname, char mode, Language language) {
        PrimProcedure[] methods = getMethods(dtype, mname, mode, null, language);
        GenericProc gproc = null;
        PrimProcedure pproc = null;
        for (PrimProcedure cur : methods) {
            if (pproc != null && gproc == null) {
                gproc = new GenericProc();
                gproc.add((MethodProc) pproc);
            }
            pproc = cur;
            if (gproc != null) {
                gproc.add((MethodProc) pproc);
            }
        }
        if (gproc == null) {
            return pproc;
        }
        gproc.setName(dtype.getName() + "." + mname);
        return gproc;
    }

    static String checkName(Expression exp, boolean reversible) {
        String nam;
        if (!(exp instanceof QuoteExp)) {
            return null;
        }
        Object name = ((QuoteExp) exp).getValue();
        if ((name instanceof FString) || (name instanceof String)) {
            nam = name.toString();
        } else if (!(name instanceof Symbol)) {
            return null;
        } else {
            nam = ((Symbol) name).getName();
        }
        if (Compilation.isValidJavaName(nam)) {
            return nam;
        }
        return Compilation.mangleName(nam, reversible);
    }

    static String checkName(Expression exp) {
        if (!(exp instanceof QuoteExp)) {
            return null;
        }
        Object name = ((QuoteExp) exp).getValue();
        if ((name instanceof FString) || (name instanceof String)) {
            return name.toString();
        }
        if (name instanceof Symbol) {
            return ((Symbol) name).getName();
        }
        return null;
    }
}
