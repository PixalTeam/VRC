package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.BlockExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ExitExp;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.Language;
import gnu.expr.LetExp;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Procedure;
import gnu.math.IntNum;
import kawa.lang.Pattern;
import kawa.lang.PatternScope;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.SyntaxPattern;
import kawa.lang.Translator;

public class syntax_case extends Syntax {
    public static final syntax_case syntax_case = new syntax_case();
    PrimProcedure call_error;

    static {
        syntax_case.setName("syntax-case");
    }

    /* JADX WARNING: type inference failed for: r17v1 */
    /* JADX WARNING: type inference failed for: r0v47, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r17v2, types: [gnu.expr.Expression] */
    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 2 */
    public Expression rewriteClauses(Object clauses, syntax_case_work work, Translator tr) {
        IfExp ifExp;
        Language language = tr.getLanguage();
        if (clauses == LList.Empty) {
            Expression[] args = {new QuoteExp("syntax-case"), new ReferenceExp(work.inputExpression)};
            if (this.call_error == null) {
                PrimProcedure primProcedure = new PrimProcedure(ClassType.make("kawa.standard.syntax_case").addMethod("error", new Type[]{Compilation.javaStringType, Type.objectType}, (Type) Type.objectType, 9), language);
                this.call_error = primProcedure;
            }
            return new ApplyExp((Procedure) this.call_error, args);
        }
        Object savePos = tr.pushPositionOf(clauses);
        try {
            if (clauses instanceof Pair) {
                Object clause = ((Pair) clauses).getCar();
                if (clause instanceof Pair) {
                    Pair pair = (Pair) clause;
                    PatternScope clauseScope = PatternScope.push(tr);
                    clauseScope.matchArray = tr.matchArray;
                    tr.push((ScopeExp) clauseScope);
                    SyntaxForm syntax = null;
                    Object tail = pair.getCdr();
                    while (tail instanceof SyntaxForm) {
                        syntax = (SyntaxForm) tail;
                        tail = syntax.getDatum();
                    }
                    if (!(tail instanceof Pair)) {
                        Expression syntaxError = tr.syntaxError("missing syntax-case output expression");
                        tr.popPositionOf(savePos);
                        return syntaxError;
                    }
                    int outerVarCount = clauseScope.pattern_names.size();
                    SyntaxPattern syntaxPattern = new SyntaxPattern(pair.getCar(), work.literal_identifiers, tr);
                    int varCount = syntaxPattern.varCount();
                    if (varCount > work.maxVars) {
                        work.maxVars = varCount;
                    }
                    BlockExp block = new BlockExp();
                    QuoteExp quoteExp = new QuoteExp(syntaxPattern);
                    Expression[] args2 = {quoteExp, new ReferenceExp(work.inputExpression), new ReferenceExp(tr.matchArray), new QuoteExp(IntNum.zero())};
                    PrimProcedure primProcedure2 = new PrimProcedure(Pattern.matchPatternMethod, language);
                    ApplyExp applyExp = new ApplyExp((Procedure) primProcedure2, args2);
                    int newVarCount = varCount - outerVarCount;
                    Expression[] inits = new Expression[newVarCount];
                    int i = newVarCount;
                    while (true) {
                        i--;
                        if (i < 0) {
                            break;
                        }
                        inits[i] = QuoteExp.undefined_exp;
                    }
                    clauseScope.inits = inits;
                    Pair pair2 = (Pair) tail;
                    if (pair2.getCdr() == LList.Empty) {
                        ifExp = tr.rewrite_car(pair2, syntax);
                    } else {
                        Expression fender = tr.rewrite_car(pair2, syntax);
                        if (pair2.getCdr() instanceof Pair) {
                            Pair pair3 = (Pair) pair2.getCdr();
                            if (pair3.getCdr() == LList.Empty) {
                                Expression rewrite_car = tr.rewrite_car(pair3, syntax);
                                ExitExp exitExp = new ExitExp(block);
                                IfExp ifExp2 = new IfExp(fender, rewrite_car, exitExp);
                                ifExp = ifExp2;
                            }
                        }
                        Expression syntaxError2 = tr.syntaxError("syntax-case:  bad clause");
                        tr.popPositionOf(savePos);
                        return syntaxError2;
                    }
                    clauseScope.setBody(ifExp);
                    tr.pop(clauseScope);
                    PatternScope.pop(tr);
                    ExitExp exitExp2 = new ExitExp(block);
                    IfExp ifExp3 = new IfExp(applyExp, clauseScope, exitExp2);
                    block.setBody(ifExp3, rewriteClauses(((Pair) clauses).getCdr(), work, tr));
                    tr.popPositionOf(savePos);
                    return block;
                }
            }
            return tr.syntaxError("syntax-case:  bad clause list");
        } finally {
            tr.popPositionOf(savePos);
        }
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        syntax_case_work work = new syntax_case_work();
        Object obj = form.getCdr();
        if (!(obj instanceof Pair) || !(((Pair) obj).getCdr() instanceof Pair)) {
            return tr.syntaxError("insufficiant arguments to syntax-case");
        }
        Expression[] linits = new Expression[2];
        LetExp let = new LetExp(linits);
        work.inputExpression = let.addDeclaration((Object) null);
        Declaration matchArrayOuter = tr.matchArray;
        Declaration matchArray = let.addDeclaration((Object) null);
        matchArray.setType(Compilation.objArrayType);
        matchArray.setCanRead(true);
        tr.matchArray = matchArray;
        work.inputExpression.setCanRead(true);
        tr.push((ScopeExp) let);
        Pair form2 = (Pair) obj;
        linits[0] = tr.rewrite(form2.getCar());
        work.inputExpression.noteValue(linits[0]);
        Pair form3 = (Pair) form2.getCdr();
        work.literal_identifiers = SyntaxPattern.getLiteralsList(form3.getCar(), null, tr);
        let.body = rewriteClauses(form3.getCdr(), work, tr);
        tr.pop(let);
        Method allocVars = ClassType.make("kawa.lang.SyntaxPattern").getDeclaredMethod("allocVars", 2);
        Expression[] args = new Expression[2];
        args[0] = new QuoteExp(IntNum.make(work.maxVars));
        if (matchArrayOuter == null) {
            args[1] = QuoteExp.nullExp;
        } else {
            args[1] = new ReferenceExp(matchArrayOuter);
        }
        linits[1] = new ApplyExp(allocVars, args);
        matchArray.noteValue(linits[1]);
        tr.matchArray = matchArrayOuter;
        return let;
    }

    public static Object error(String kind, Object arg) {
        Translator tr = (Translator) Compilation.getCurrent();
        if (tr == null) {
            throw new RuntimeException("no match in syntax-case");
        }
        Syntax syntax = tr.getCurrentSyntax();
        return tr.syntaxError("no matching case while expanding " + (syntax == null ? "some syntax" : syntax.getName()));
    }
}
