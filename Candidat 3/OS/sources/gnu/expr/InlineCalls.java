package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.kawa.reflect.CompileReflect;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.util.IdentityHashTable;
import gnu.mapping.Procedure;
import gnu.mapping.Values;
import gnu.math.IntNum;
import java.lang.reflect.InvocationTargetException;

public class InlineCalls extends ExpExpVisitor<Type> {
    private static Class[] inlinerMethodArgTypes;

    public static Expression inlineCalls(Expression exp, Compilation comp) {
        return new InlineCalls(comp).visit(exp, (Type) null);
    }

    public InlineCalls(Compilation comp) {
        setContext(comp);
    }

    public Expression visit(Expression exp, Type required) {
        if (!exp.getFlag(1)) {
            exp.setFlag(1);
            exp = (Expression) super.visit(exp, required);
            exp.setFlag(1);
        }
        return checkType(exp, required);
    }

    public Expression checkType(Expression exp, Type required) {
        boolean incompatible = true;
        Type expType = exp.getType();
        if (!(required instanceof ClassType) || !((ClassType) required).isInterface() || !expType.isSubtype(Compilation.typeProcedure) || expType.isSubtype(required)) {
            if (expType == Type.toStringType) {
                expType = Type.javalangStringType;
            }
            if (required == null || required.compare(expType) != -3) {
                incompatible = false;
            }
            if (incompatible && (required instanceof TypeValue)) {
                Expression converted = ((TypeValue) required).convertValue(exp);
                if (converted != null) {
                    return converted;
                }
            }
        } else {
            if (exp instanceof LambdaExp) {
                Method amethod = ((ClassType) required).checkSingleAbstractMethod();
                if (amethod != null) {
                    LambdaExp lexp = (LambdaExp) exp;
                    ObjectExp oexp = new ObjectExp();
                    oexp.setLocation(exp);
                    oexp.supers = new Expression[]{new QuoteExp(required)};
                    oexp.setTypes(getCompilation());
                    String mname = amethod.getName();
                    oexp.addMethod(lexp, mname);
                    Declaration addDeclaration = oexp.addDeclaration(mname, Compilation.typeProcedure);
                    oexp.firstChild = lexp;
                    oexp.declareParts(this.comp);
                    return visit((Expression) oexp, required);
                }
            }
            incompatible = true;
        }
        if (incompatible) {
            Language language = this.comp.getLanguage();
            this.comp.error('w', "type " + language.formatType(expType) + " is incompatible with required type " + language.formatType(required), exp);
        }
        return exp;
    }

    /* access modifiers changed from: protected */
    public Expression visitApplyExp(ApplyExp exp, Type required) {
        Expression func = exp.func;
        if (func instanceof LambdaExp) {
            LambdaExp lambdaExp = (LambdaExp) func;
            Expression inlined = inlineCall((LambdaExp) func, exp.args, false);
            if (inlined != null) {
                return visit(inlined, required);
            }
        }
        Expression func2 = visit(func, (Type) null);
        exp.func = func2;
        return func2.validateApply(exp, this, required, null);
    }

    public final Expression visitApplyOnly(ApplyExp exp, Type required) {
        return exp.func.validateApply(exp, this, required, null);
    }

    public static Integer checkIntValue(Expression exp) {
        if (exp instanceof QuoteExp) {
            QuoteExp qarg = (QuoteExp) exp;
            Object value = qarg.getValue();
            if (!qarg.isExplicitlyTyped() && (value instanceof IntNum)) {
                IntNum ivalue = (IntNum) value;
                if (ivalue.inIntRange()) {
                    return Integer.valueOf(ivalue.intValue());
                }
            }
        }
        return null;
    }

    public static Long checkLongValue(Expression exp) {
        if (exp instanceof QuoteExp) {
            QuoteExp qarg = (QuoteExp) exp;
            Object value = qarg.getValue();
            if (!qarg.isExplicitlyTyped() && (value instanceof IntNum)) {
                IntNum ivalue = (IntNum) value;
                if (ivalue.inLongRange()) {
                    return Long.valueOf(ivalue.longValue());
                }
            }
        }
        return null;
    }

    public QuoteExp fixIntValue(Expression exp) {
        Integer ival = checkIntValue(exp);
        if (ival != null) {
            return new QuoteExp(ival, this.comp.getLanguage().getTypeFor(Integer.TYPE));
        }
        return null;
    }

    public QuoteExp fixLongValue(Expression exp) {
        Long ival = checkLongValue(exp);
        if (ival != null) {
            return new QuoteExp(ival, this.comp.getLanguage().getTypeFor(Long.TYPE));
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public Expression visitQuoteExp(QuoteExp exp, Type required) {
        if (exp.getRawType() != null || exp.isSharedConstant()) {
            return exp;
        }
        Object value = exp.getValue();
        if (value == null) {
            return exp;
        }
        Type vtype = this.comp.getLanguage().getTypeFor(value.getClass());
        if (vtype == Type.toStringType) {
            vtype = Type.javalangStringType;
        }
        exp.type = vtype;
        if (!(required instanceof PrimType)) {
            return exp;
        }
        char sig1 = required.getSignature().charAt(0);
        QuoteExp ret = sig1 == 'I' ? fixIntValue(exp) : sig1 == 'J' ? fixLongValue(exp) : null;
        if (ret != null) {
            return ret;
        }
        return exp;
    }

    /* access modifiers changed from: protected */
    public Expression visitReferenceExp(ReferenceExp exp, Type required) {
        Declaration decl = exp.getBinding();
        if (decl != null && decl.field == null && !decl.getCanWrite()) {
            Expression dval = decl.getValue();
            if ((dval instanceof QuoteExp) && dval != QuoteExp.undefined_exp) {
                return visitQuoteExp((QuoteExp) dval, required);
            }
            if ((dval instanceof ReferenceExp) && !decl.isAlias()) {
                ReferenceExp rval = (ReferenceExp) dval;
                Declaration rdecl = rval.getBinding();
                Type dtype = decl.getType();
                if (rdecl != null && !rdecl.getCanWrite() && ((dtype == null || dtype == Type.pointer_type || dtype == rdecl.getType()) && !rval.getDontDereference())) {
                    return visitReferenceExp(rval, required);
                }
            }
            if (!exp.isProcedureName() && (decl.flags & 1048704) == 1048704) {
                this.comp.error('e', "unimplemented: reference to method " + decl.getName() + " as variable");
                this.comp.error('e', decl, "here is the definition of ", "");
            }
        }
        return (Expression) super.visitReferenceExp(exp, required);
    }

    /* access modifiers changed from: protected */
    public Expression visitIfExp(IfExp exp, Type required) {
        Expression test = (Expression) exp.test.visit(this, null);
        if (test instanceof ReferenceExp) {
            Declaration decl = ((ReferenceExp) test).getBinding();
            if (decl != null) {
                Expression value = decl.getValue();
                if ((value instanceof QuoteExp) && value != QuoteExp.undefined_exp) {
                    test = value;
                }
            }
        }
        exp.test = test;
        if (this.exitValue == null) {
            exp.then_clause = visit(exp.then_clause, required);
        }
        if (this.exitValue == null && exp.else_clause != null) {
            exp.else_clause = visit(exp.else_clause, required);
        }
        if (test instanceof QuoteExp) {
            return exp.select(this.comp.getLanguage().isTrue(((QuoteExp) test).getValue()));
        }
        if (!test.getType().isVoid()) {
            return exp;
        }
        boolean truth = this.comp.getLanguage().isTrue(Values.empty);
        this.comp.error('w', "void-valued condition is always " + truth);
        return new BeginExp(test, exp.select(truth));
    }

    /* access modifiers changed from: protected */
    public Expression visitBeginExp(BeginExp exp, Type required) {
        Type type;
        int last = exp.length - 1;
        for (int i = 0; i <= last; i++) {
            Expression[] expressionArr = exp.exps;
            Expression expression = exp.exps[i];
            if (i < last) {
                type = null;
            } else {
                type = required;
            }
            expressionArr[i] = visit(expression, type);
        }
        return exp;
    }

    /* access modifiers changed from: protected */
    public Expression visitScopeExp(ScopeExp exp, Type required) {
        Type type;
        exp.visitChildren(this, null);
        visitDeclarationTypes(exp);
        for (Declaration decl = exp.firstDecl(); decl != null; decl = decl.nextDecl()) {
            if (decl.type == null) {
                Expression val = decl.getValue();
                decl.type = Type.objectType;
                if (val == null || val == QuoteExp.undefined_exp) {
                    type = Type.objectType;
                } else {
                    type = val.getType();
                }
                decl.setType(type);
            }
        }
        return exp;
    }

    /* access modifiers changed from: protected */
    public Expression visitLetExp(LetExp exp, Type required) {
        Declaration decl = exp.firstDecl();
        int n = exp.inits.length;
        int i = 0;
        while (i < n) {
            Expression init0 = exp.inits[i];
            boolean typeSpecified = decl.getFlag(8192);
            Expression init = visit(init0, (!typeSpecified || init0 == QuoteExp.undefined_exp) ? null : decl.getType());
            exp.inits[i] = init;
            if (decl.value == init0) {
                Expression dvalue = init;
                decl.value = init;
                if (!typeSpecified) {
                    decl.setType(dvalue.getType());
                }
            }
            i++;
            decl = decl.nextDecl();
        }
        if (this.exitValue == null) {
            exp.body = visit(exp.body, required);
        }
        if (exp.body instanceof ReferenceExp) {
            ReferenceExp ref = (ReferenceExp) exp.body;
            Declaration d = ref.getBinding();
            if (d != null && d.context == exp && !ref.getDontDereference() && n == 1) {
                Expression init2 = exp.inits[0];
                Expression texp = d.getTypeExp();
                if (texp == QuoteExp.classObjectExp) {
                    return init2;
                }
                return visitApplyOnly(Compilation.makeCoercion(init2, texp), null);
            }
        }
        return exp;
    }

    /* access modifiers changed from: protected */
    public Expression visitLambdaExp(LambdaExp exp, Type required) {
        Declaration firstDecl = exp.firstDecl();
        if (firstDecl != null && firstDecl.isThisParameter() && !exp.isClassMethod() && firstDecl.type == null) {
            firstDecl.setType(this.comp.mainClass);
        }
        return visitScopeExp((ScopeExp) exp, required);
    }

    /* access modifiers changed from: protected */
    public Expression visitTryExp(TryExp exp, Type required) {
        if (exp.getCatchClauses() == null && exp.getFinallyClause() == null) {
            return visit(exp.try_clause, required);
        }
        return (Expression) super.visitTryExp(exp, required);
    }

    /* access modifiers changed from: protected */
    public Expression visitSetExpValue(Expression new_value, Type required, Declaration decl) {
        return visit(new_value, (decl == null || decl.isAlias()) ? null : decl.type);
    }

    /* access modifiers changed from: protected */
    public Expression visitSetExp(SetExp exp, Type required) {
        Declaration decl = exp.getBinding();
        super.visitSetExp(exp, required);
        if (!exp.isDefining() && decl != null && (decl.flags & 1048704) == 1048704) {
            this.comp.error('e', "can't assign to method " + decl.getName(), exp);
        }
        if (decl != null && decl.getFlag(8192) && CompileReflect.checkKnownClass(decl.getType(), this.comp) < 0) {
            decl.setType(Type.errorType);
        }
        return exp;
    }

    private static synchronized Class[] getInlinerMethodArgTypes() throws Exception {
        Class[] t;
        synchronized (InlineCalls.class) {
            t = inlinerMethodArgTypes;
            if (t == null) {
                t = new Class[]{Class.forName("gnu.expr.ApplyExp"), Class.forName("gnu.expr.InlineCalls"), Class.forName("gnu.bytecode.Type"), Class.forName("gnu.mapping.Procedure")};
                inlinerMethodArgTypes = t;
            }
        }
        return t;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0062, code lost:
        if (r5 == null) goto L_0x00b4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r9 = new java.lang.Object[]{r15, r14, r16, r17};
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0075, code lost:
        if ((r5 instanceof gnu.mapping.Procedure) == false) goto L_0x00b6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00b8, code lost:
        if ((r5 instanceof java.lang.reflect.Method) == false) goto L_0x00b4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        return (gnu.expr.Expression) ((gnu.mapping.Procedure) r5).applyN(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        return (gnu.expr.Expression) ((java.lang.reflect.Method) r5).invoke(null, r9);
     */
    public Expression maybeInline(ApplyExp exp, Type required, Procedure proc) {
        try {
            synchronized (proc) {
                Object inliner = proc.getProperty(Procedure.validateApplyKey, null);
                if (inliner instanceof String) {
                    String inliners = (String) inliner;
                    int colon = inliners.indexOf(58);
                    Object obj = null;
                    if (colon > 0) {
                        String cname = inliners.substring(0, colon);
                        obj = Class.forName(cname, true, proc.getClass().getClassLoader()).getDeclaredMethod(inliners.substring(colon + 1), getInlinerMethodArgTypes());
                    }
                    if (obj == null) {
                        error('e', "inliner property string for " + proc + " is not of the form CLASS:METHOD");
                        return null;
                    }
                    inliner = obj;
                }
            }
        } catch (Throwable th) {
            ex = th;
            if (ex instanceof InvocationTargetException) {
                ex = ((InvocationTargetException) ex).getTargetException();
            }
            this.messages.error('e', "caught exception in inliner for " + proc + " - " + ex, ex);
        }
        return null;
    }

    public static Expression inlineCall(LambdaExp lexp, Expression[] args, boolean makeCopy) {
        IdentityHashTable mapper;
        Expression[] cargs;
        if (lexp.keywords != null || (lexp.nameDecl != null && !makeCopy)) {
            return null;
        }
        boolean varArgs = lexp.max_args < 0;
        if ((lexp.min_args != lexp.max_args || lexp.min_args != args.length) && (!varArgs || lexp.min_args != 0)) {
            return null;
        }
        Declaration prev = null;
        int i = 0;
        if (makeCopy) {
            mapper = new IdentityHashTable();
            cargs = Expression.deepCopy(args, mapper);
            if (cargs == null && args != null) {
                return null;
            }
        } else {
            mapper = null;
            cargs = args;
        }
        if (varArgs) {
            Expression[] xargs = new Expression[(args.length + 1)];
            xargs[0] = QuoteExp.getInstance(lexp.firstDecl().type);
            System.arraycopy(args, 0, xargs, 1, args.length);
            cargs = new Expression[]{new ApplyExp((Procedure) Invoke.make, xargs)};
        }
        LetExp let = new LetExp(cargs);
        Declaration param = lexp.firstDecl();
        while (param != null) {
            Declaration next = param.nextDecl();
            if (makeCopy) {
                Declaration ldecl = let.addDeclaration(param.symbol, param.type);
                if (param.typeExp != null) {
                    ldecl.typeExp = Expression.deepCopy(param.typeExp);
                    if (ldecl.typeExp == null) {
                        return null;
                    }
                }
                mapper.put(param, ldecl);
            } else {
                lexp.remove(prev, param);
                let.add(prev, param);
            }
            if (!varArgs && !param.getCanWrite()) {
                param.setValue(cargs[i]);
            }
            prev = param;
            param = next;
            i++;
        }
        Expression body = lexp.body;
        if (makeCopy) {
            body = Expression.deepCopy(body, mapper);
            if (body == null && lexp.body != null) {
                return null;
            }
        }
        let.body = body;
        return let;
    }
}
