package gnu.xquery.util;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Declaration;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.InlineCalls;
import gnu.expr.LambdaExp;
import gnu.expr.LetExp;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.Target;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.ValuesMap;
import gnu.kawa.reflect.CompileReflect;
import gnu.kawa.reflect.OccurrenceType;
import gnu.kawa.xml.ChildAxis;
import gnu.kawa.xml.CoerceNodes;
import gnu.kawa.xml.DescendantAxis;
import gnu.kawa.xml.DescendantOrSelfAxis;
import gnu.kawa.xml.NodeSetType;
import gnu.kawa.xml.NodeType;
import gnu.kawa.xml.SortNodes;
import gnu.kawa.xml.XDataType;
import gnu.mapping.Procedure;
import gnu.math.IntNum;
import gnu.xquery.lang.XQuery;

public class CompileMisc {
    static final Method castMethod = typeXDataType.getDeclaredMethod("cast", 1);
    static final Method castableMethod = typeXDataType.getDeclaredMethod("castable", 1);
    static final ClassType typeTuples = ClassType.make("gnu.xquery.util.OrderedTuples");
    static final ClassType typeXDataType = ClassType.make("gnu.kawa.xml.XDataType");

    public static Expression validateCompare(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression folded = exp.inlineIfConstant(proc, visitor);
        if (folded != exp) {
            return folded;
        }
        Compare cproc = (Compare) proc;
        if ((cproc.flags & 32) == 0) {
            exp = new ApplyExp(ClassType.make("gnu.xquery.util.Compare").getDeclaredMethod("apply", 4), new QuoteExp(IntNum.make(cproc.flags)), exp.getArg(0), exp.getArg(1), QuoteExp.nullExp);
        }
        if (exp.getTypeRaw() == null) {
            exp.setType(XDataType.booleanType);
        }
        return exp;
    }

    public static Expression validateBooleanValue(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length == 1) {
            Expression arg = args[0];
            Type type = arg.getType();
            if (type == XDataType.booleanType) {
                return arg;
            }
            if (type == null) {
                exp.setType(XDataType.booleanType);
            }
            if (arg instanceof QuoteExp) {
                try {
                    return BooleanValue.booleanValue(((QuoteExp) arg).getValue()) ? XQuery.trueExp : XQuery.falseExp;
                } catch (Throwable th) {
                    String message = "cannot convert to a boolean";
                    visitor.getMessages().error('e', message);
                    return new ErrorExp(message);
                }
            }
        }
        return exp;
    }

    public static Expression validateArithOp(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        return exp;
    }

    /* JADX WARNING: type inference failed for: r7v0 */
    /* JADX WARNING: type inference failed for: r14v0 */
    /* JADX WARNING: type inference failed for: r21v0, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r21v1 */
    /* JADX WARNING: type inference failed for: r21v2 */
    /* JADX WARNING: type inference failed for: r2v1, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r0v19, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r18v0 */
    /* JADX WARNING: type inference failed for: r18v1 */
    /* JADX WARNING: type inference failed for: r18v2 */
    /* JADX WARNING: type inference failed for: r1v8, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r0v56, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r18v3 */
    /* JADX WARNING: type inference failed for: r18v4 */
    /* JADX WARNING: type inference failed for: r21v3 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 9 */
    public static Expression validateApplyValuesFilter(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        Type seqType;
        Method sizeMethod;
        ? r21;
        ValuesFilter vproc = (ValuesFilter) proc;
        exp.visitArgs(visitor);
        Object[] args = exp.getArgs();
        ? r7 = args[1];
        if (!(r7 instanceof LambdaExp)) {
            return exp;
        }
        LambdaExp lexp2 = (LambdaExp) r7;
        if (lexp2.min_args != 3 || lexp2.max_args != 3) {
            return exp;
        }
        exp.setType(args[0].getType());
        Compilation parser = visitor.getCompilation();
        Declaration dotArg = lexp2.firstDecl();
        Declaration posArg = dotArg.nextDecl();
        Declaration lastArg = posArg.nextDecl();
        lexp2.setInlineOnly(true);
        lexp2.returnContinuation = exp;
        lexp2.inlineHome = visitor.getCurrentLambda();
        lexp2.remove(posArg, lastArg);
        lexp2.min_args = 2;
        lexp2.max_args = 2;
        if (!lastArg.getCanRead() && vproc.kind != 'R') {
            return exp;
        }
        parser.letStart();
        ? r212 = args[0];
        if (vproc.kind == 'P') {
            seqType = r212.getType();
            sizeMethod = Compilation.typeValues.getDeclaredMethod("countValues", 1);
            r21 = r212;
        } else {
            seqType = SortNodes.typeSortedNodes;
            ApplyExp applyExp = new ApplyExp((Procedure) SortNodes.sortNodes, (Expression[]) new Expression[]{r212});
            sizeMethod = CoerceNodes.typeNodes.getDeclaredMethod("size", 0);
            r21 = applyExp;
        }
        Declaration sequence = parser.letVariable("sequence", seqType, r21);
        parser.letEnter();
        ? r18 = lexp2.body;
        if (lexp2.body.getType() != XDataType.booleanType) {
            Method method = ValuesFilter.matchesMethod;
            ReferenceExp referenceExp = new ReferenceExp(posArg);
            ApplyExp applyExp2 = new ApplyExp(method, (Expression[]) new Expression[]{r18, referenceExp});
            r18 = applyExp2;
        }
        if (vproc.kind == 'R') {
            Declaration declaration = new Declaration((Object) null, (Type) Type.intType);
            AddOp addOp = AddOp.$Mn;
            ReferenceExp referenceExp2 = new ReferenceExp(lastArg);
            ReferenceExp referenceExp3 = new ReferenceExp(declaration);
            Expression init = new ApplyExp((Procedure) addOp, referenceExp2, referenceExp3);
            LetExp let = new LetExp(new Expression[]{new ApplyExp((Procedure) AddOp.$Pl, init, new QuoteExp(IntNum.one()))});
            lexp2.replaceFollowing(dotArg, declaration);
            let.add(posArg);
            let.body = r18;
            r18 = let;
        }
        ReferenceExp referenceExp4 = new ReferenceExp(dotArg);
        IfExp ifExp = new IfExp(r18, referenceExp4, QuoteExp.voidExp);
        lexp2.body = ifExp;
        ValuesMap valuesMap = ValuesMap.valuesMapWithPos;
        ReferenceExp referenceExp5 = new ReferenceExp(sequence);
        ApplyExp doMap = new ApplyExp((Procedure) valuesMap, lexp2, referenceExp5);
        doMap.setType(dotArg.getType());
        lexp2.returnContinuation = doMap;
        ReferenceExp referenceExp6 = new ReferenceExp(sequence);
        LetExp let2 = new LetExp(new Expression[]{new ApplyExp(sizeMethod, referenceExp6)});
        let2.add(lastArg);
        let2.body = gnu.kawa.functions.CompileMisc.validateApplyValuesMap(doMap, visitor, required, ValuesMap.valuesMapWithPos);
        return parser.letDone(let2);
    }

    public static Expression validateApplyRelativeStep(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        Type rtype;
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        Expression exp1 = args[0];
        Expression exp2 = args[1];
        Compilation comp = visitor.getCompilation();
        if ((exp2 instanceof LambdaExp) && comp.mustCompile) {
            LambdaExp lexp2 = (LambdaExp) exp2;
            if (lexp2.min_args == 3 && lexp2.max_args == 3) {
                lexp2.setInlineOnly(true);
                lexp2.returnContinuation = exp;
                lexp2.inlineHome = visitor.getCurrentLambda();
                Expression exp22 = lexp2.body;
                Declaration posArg = lexp2.firstDecl().nextDecl();
                Declaration lastArg = posArg.nextDecl();
                posArg.setNext(lastArg.nextDecl());
                lastArg.setNext(null);
                lexp2.min_args = 2;
                lexp2.max_args = 2;
                Type type1 = exp1.getType();
                if (type1 == null || NodeType.anyNodeTest.compare(type1) != -3) {
                    Type rtype2 = exp.getTypeRaw();
                    if (rtype2 == null || rtype2 == Type.pointer_type) {
                        Type rtypePrime = OccurrenceType.itemPrimeType(exp22.getType());
                        if (NodeType.anyNodeTest.compare(rtypePrime) >= 0) {
                            rtype = NodeSetType.getInstance(rtypePrime);
                        } else {
                            rtype = OccurrenceType.getInstance(rtypePrime, 0, -1);
                        }
                        exp.setType(rtype);
                    }
                    if (lastArg.getCanRead()) {
                        ClassType typeNodes = CoerceNodes.typeNodes;
                        comp.letStart();
                        Declaration sequence = comp.letVariable(null, typeNodes, new ApplyExp((Procedure) CoerceNodes.coerceNodes, exp1));
                        comp.letEnter();
                        Method sizeMethod = typeNodes.getDeclaredMethod("size", 0);
                        ReferenceExp referenceExp = new ReferenceExp(sequence);
                        LetExp lastLet = new LetExp(new Expression[]{new ApplyExp(sizeMethod, referenceExp)});
                        lastLet.addDeclaration(lastArg);
                        Expression function = exp.getFunction();
                        ReferenceExp referenceExp2 = new ReferenceExp(sequence);
                        lastLet.body = new ApplyExp(function, referenceExp2, lexp2);
                        return comp.letDone(lastLet);
                    }
                    ApplyExp result = exp;
                    if (exp22 instanceof ApplyExp) {
                        ApplyExp aexp2 = (ApplyExp) exp22;
                        if (aexp2.getFunction().valueIfConstant() instanceof ValuesFilter) {
                            Expression vexp2 = aexp2.getArgs()[1];
                            if (vexp2 instanceof LambdaExp) {
                                LambdaExp lvexp2 = (LambdaExp) vexp2;
                                Declaration dot2 = lvexp2.firstDecl();
                                if (dot2 != null) {
                                    Declaration pos2 = dot2.nextDecl();
                                    if (pos2 != null && pos2.nextDecl() == null && !pos2.getCanRead() && ClassType.make("java.lang.Number").compare(lvexp2.body.getType()) == -3) {
                                        exp22 = aexp2.getArg(0);
                                        lexp2.body = exp22;
                                        aexp2.setArg(0, exp);
                                        result = aexp2;
                                    }
                                }
                            }
                        }
                    }
                    if (!(exp1 instanceof ApplyExp) || !(exp22 instanceof ApplyExp)) {
                        return result;
                    }
                    ApplyExp aexp1 = (ApplyExp) exp1;
                    ApplyExp aexp22 = (ApplyExp) exp22;
                    Object p1 = aexp1.getFunction().valueIfConstant();
                    Object p2 = aexp22.getFunction().valueIfConstant();
                    if (p1 != RelativeStep.relativeStep || !(p2 instanceof ChildAxis) || aexp1.getArgCount() != 2) {
                        return result;
                    }
                    Expression exp12 = aexp1.getArg(1);
                    if (!(exp12 instanceof LambdaExp)) {
                        return result;
                    }
                    LambdaExp lexp12 = (LambdaExp) exp12;
                    if (!(lexp12.body instanceof ApplyExp) || ((ApplyExp) lexp12.body).getFunction().valueIfConstant() != DescendantOrSelfAxis.anyNode) {
                        return result;
                    }
                    exp.setArg(0, aexp1.getArg(0));
                    aexp22.setFunction(new QuoteExp(DescendantAxis.make(((ChildAxis) p2).getNodePredicate())));
                    return result;
                }
                String message = "step input is " + visitor.getCompilation().getLanguage().formatType(type1) + " - not a node sequence";
                visitor.getMessages().error('e', message);
                ErrorExp errorExp = new ErrorExp(message);
                return errorExp;
            }
        }
        return exp;
    }

    public static Expression validateApplyOrderedMap(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length <= 2) {
            return exp;
        }
        Expression[] rargs = new Expression[(args.length - 1)];
        System.arraycopy(args, 1, rargs, 0, rargs.length);
        return new ApplyExp(proc, args[0], new ApplyExp(typeTuples.getDeclaredMethod("make$V", 2), rargs));
    }

    public static void compileOrderedMap(ApplyExp exp, Compilation comp, Target target, Procedure proc) {
        Expression[] args = exp.getArgs();
        if (args.length != 2) {
            ApplyExp.compile(exp, comp, target);
            return;
        }
        CodeAttr code = comp.getCode();
        Variable consumer = code.pushScope().addVariable(code, typeTuples, null);
        args[1].compile(comp, Target.pushValue(typeTuples));
        code.emitStore(consumer);
        args[0].compile(comp, (Target) new ConsumerTarget(consumer));
        Method mm = typeTuples.getDeclaredMethod("run$X", 1);
        code.emitLoad(consumer);
        PrimProcedure.compileInvoke(comp, mm, target, exp.isTailCall(), 182, Type.pointer_type);
        code.popScope();
    }

    public static Expression validateApplyCastAs(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        ApplyExp exp2 = CompileReflect.inlineClassName(exp, 0, visitor);
        Expression[] args = exp2.getArgs();
        if (args.length != 2 || !(args[0] instanceof QuoteExp) || !(((QuoteExp) args[0]).getValue() instanceof XDataType)) {
            return exp2;
        }
        return new ApplyExp(castMethod, args);
    }

    public static Expression validateApplyCastableAs(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        ApplyExp exp2 = CompileReflect.inlineClassName(exp, 1, visitor);
        Expression[] args = exp2.getArgs();
        if (args.length != 2 || !(args[1] instanceof QuoteExp) || !(((QuoteExp) args[1]).getValue() instanceof XDataType)) {
            return exp2;
        }
        return new ApplyExp(castableMethod, args[1], args[0]);
    }
}
