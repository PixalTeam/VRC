package kawa.standard;

import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.LambdaExp;
import gnu.expr.LangExp;
import gnu.expr.ModuleExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import kawa.lang.Lambda;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

public class define extends Syntax {
    public static final define defineRaw = new define(SchemeCompilation.lambda);
    Lambda lambda;

    /* access modifiers changed from: 0000 */
    public String getName(int options) {
        if ((options & 4) != 0) {
            return "define-private";
        }
        if ((options & 8) != 0) {
            return "define-constant";
        }
        return "define";
    }

    public define(Lambda lambda2) {
        this.lambda = lambda2;
    }

    public void scanForm(Pair st, ScopeExp defs, Translator tr) {
        Pair p1 = (Pair) st.getCdr();
        Pair p2 = (Pair) p1.getCdr();
        Pair p3 = (Pair) p2.getCdr();
        Pair p4 = (Pair) p3.getCdr();
        SyntaxForm nameSyntax = null;
        Object name = p1.getCar();
        while (name instanceof SyntaxForm) {
            nameSyntax = (SyntaxForm) name;
            name = nameSyntax.getDatum();
        }
        int options = ((Number) Translator.stripSyntax(p2.getCar())).intValue();
        boolean makePrivate = (options & 4) != 0;
        boolean makeConstant = (options & 8) != 0;
        ScopeExp currentScope = tr.currentScope();
        Object name2 = tr.namespaceResolve(name);
        if (!(name2 instanceof Symbol)) {
            tr.error('e', "'" + name2 + "' is not a valid identifier");
            name2 = null;
        }
        Object savePos = tr.pushPositionOf(p1);
        Declaration decl = tr.define(name2, nameSyntax, defs);
        tr.popPositionOf(savePos);
        Object name3 = decl.getSymbol();
        if (makePrivate) {
            decl.setFlag(16777216);
            decl.setPrivate(true);
        }
        if (makeConstant) {
            decl.setFlag(JSONzip.int14);
        }
        decl.setFlag(262144);
        if ((options & 2) != 0) {
            LambdaExp lexp = new LambdaExp();
            lexp.setSymbol(name3);
            if (Compilation.inlineOk) {
                decl.setProcedureDecl(true);
                decl.setType(Compilation.typeProcedure);
                lexp.nameDecl = decl;
            }
            Object formals = p4.getCar();
            Object body = p4.getCdr();
            Translator.setLine((Expression) lexp, (Object) p1);
            this.lambda.rewriteFormals(lexp, formals, tr, null);
            Object realBody = this.lambda.rewriteAttrs(lexp, body, tr);
            if (realBody != body) {
                Object car = p2.getCar();
                Object car2 = p3.getCar();
                Pair pair = new Pair(formals, realBody);
                p2 = new Pair(car, new Pair(car2, pair));
            }
            decl.noteValue(lexp);
        }
        if ((defs instanceof ModuleExp) && !makePrivate && (!Compilation.inlineOk || tr.sharedModuleDefs())) {
            decl.setCanWrite(true);
        }
        if ((options & 1) != 0) {
            LangExp langExp = new LangExp(p3);
            decl.setTypeExp(langExp);
            decl.setFlag(8192);
        }
        Pair st2 = Translator.makePair(st, this, Translator.makePair(p1, decl, p2));
        Translator.setLine(decl, (Object) p1);
        tr.formStack.addElement(st2);
    }

    /* JADX WARNING: type inference failed for: r17v0, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r18v12 */
    /* JADX WARNING: type inference failed for: r18v13 */
    /* JADX WARNING: type inference failed for: r0v6, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r18v15 */
    /* JADX WARNING: type inference failed for: r17v1 */
    /* JADX WARNING: type inference failed for: r0v7, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r17v2 */
    /* JADX WARNING: type inference failed for: r17v3 */
    /* JADX WARNING: type inference failed for: r18v29 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 6 */
    public Expression rewriteForm(Pair form, Translator tr) {
        ? r17;
        Pair p1 = (Pair) form.getCdr();
        Pair p2 = (Pair) p1.getCdr();
        Pair p4 = (Pair) ((Pair) p2.getCdr()).getCdr();
        Object name = Translator.stripSyntax(p1.getCar());
        int options = ((Number) Translator.stripSyntax(p2.getCar())).intValue();
        boolean makePrivate = (options & 4) != 0;
        if (!(name instanceof Declaration)) {
            return tr.syntaxError(getName(options) + " is only allowed in a <body>");
        }
        Declaration decl = (Declaration) name;
        if (decl.getFlag(8192)) {
            Expression texp = decl.getTypeExp();
            if (texp instanceof LangExp) {
                decl.setType(tr.exp2Type((Pair) ((LangExp) texp).getLangValue()));
            }
        }
        if ((options & 2) != 0) {
            LambdaExp lexp = (LambdaExp) decl.getValue();
            this.lambda.rewriteBody(lexp, p4.getCdr(), tr);
            r17 = lexp;
            if (!Compilation.inlineOk) {
                decl.noteValue(null);
            }
        } else {
            ? rewrite = tr.rewrite(p4.getCar());
            decl.noteValue((!(decl.context instanceof ModuleExp) || makePrivate || !decl.getCanWrite()) ? rewrite : 0);
            r17 = rewrite;
        }
        SetExp sexp = new SetExp(decl, (Expression) r17);
        sexp.setDefining(true);
        if (!makePrivate || (tr.currentScope() instanceof ModuleExp)) {
            return sexp;
        }
        tr.error('w', "define-private not at top level " + tr.currentScope());
        return sexp;
    }
}
