package kawa.standard;

import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.LambdaExp;
import gnu.expr.LetExp;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import java.util.Stack;
import kawa.lang.Macro;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.TemplateScope;
import kawa.lang.Translator;

public class let_syntax extends Syntax {
    public static final let_syntax let_syntax = new let_syntax(false, "let-syntax");
    public static final let_syntax letrec_syntax = new let_syntax(true, "letrec-syntax");
    boolean recursive;

    public let_syntax(boolean recursive2, String name) {
        super(name);
        this.recursive = recursive2;
    }

    /* JADX WARNING: type inference failed for: r33v31, types: [gnu.expr.ScopeExp] */
    /* JADX WARNING: type inference failed for: r33v32 */
    /* JADX WARNING: type inference failed for: r33v33 */
    /* JADX WARNING: type inference failed for: r1v17, types: [gnu.expr.ScopeExp] */
    /* JADX WARNING: type inference failed for: r33v35, types: [kawa.lang.TemplateScope] */
    /* JADX WARNING: type inference failed for: r33v44 */
    /* JADX WARNING: type inference failed for: r33v45 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 4 */
    public Expression rewrite(Object obj, Translator tr) {
        if (!(obj instanceof Pair)) {
            return tr.syntaxError("missing let-syntax arguments");
        }
        Pair pair = (Pair) obj;
        Object bindings = pair.getCar();
        Object body = pair.getCdr();
        int decl_count = Translator.listLength(bindings);
        if (decl_count < 0) {
            return tr.syntaxError("bindings not a proper list");
        }
        Stack renamedAliases = null;
        int renamedAliasesCount = 0;
        Expression[] inits = new Expression[decl_count];
        Declaration[] decls = new Declaration[decl_count];
        Macro[] macros = new Macro[decl_count];
        Pair[] transformers = new Pair[decl_count];
        SyntaxForm[] trSyntax = new SyntaxForm[decl_count];
        LetExp letExp = new LetExp(inits);
        SyntaxForm listSyntax = null;
        for (int i = 0; i < decl_count; i++) {
            while (bindings instanceof SyntaxForm) {
                listSyntax = (SyntaxForm) bindings;
                bindings = listSyntax.getDatum();
            }
            SyntaxForm bindingSyntax = listSyntax;
            Pair bind_pair = (Pair) bindings;
            Object bind_pair_car = bind_pair.getCar();
            if (bind_pair_car instanceof SyntaxForm) {
                bindingSyntax = (SyntaxForm) bind_pair_car;
                bind_pair_car = bindingSyntax.getDatum();
            }
            if (!(bind_pair_car instanceof Pair)) {
                return tr.syntaxError(getName() + " binding is not a pair");
            }
            Pair binding = (Pair) bind_pair_car;
            Object name = binding.getCar();
            SyntaxForm nameSyntax = bindingSyntax;
            while (name instanceof SyntaxForm) {
                nameSyntax = (SyntaxForm) name;
                name = nameSyntax.getDatum();
            }
            if (!(name instanceof String) && !(name instanceof Symbol)) {
                return tr.syntaxError("variable in " + getName() + " binding is not a symbol");
            }
            Object binding_cdr = binding.getCdr();
            while (binding_cdr instanceof SyntaxForm) {
                bindingSyntax = (SyntaxForm) binding_cdr;
                binding_cdr = bindingSyntax.getDatum();
            }
            if (!(binding_cdr instanceof Pair)) {
                return tr.syntaxError(getName() + " has no value for '" + name + "'");
            }
            Pair binding2 = (Pair) binding_cdr;
            if (binding2.getCdr() != LList.Empty) {
                return tr.syntaxError("let binding for '" + name + "' is improper list");
            }
            Declaration decl = new Declaration(name);
            Macro macro = Macro.make(decl);
            macros[i] = macro;
            transformers[i] = binding2;
            trSyntax[i] = bindingSyntax;
            letExp.addDeclaration(decl);
            TemplateScope scope = nameSyntax == null ? null : nameSyntax.getScope();
            if (scope != null) {
                Declaration alias = tr.makeRenamedAlias(decl, scope);
                if (renamedAliases == null) {
                    renamedAliases = new Stack();
                }
                renamedAliases.push(alias);
                renamedAliasesCount++;
            }
            ? currentScope = bindingSyntax != null ? bindingSyntax.getScope() : this.recursive ? letExp : tr.currentScope();
            macro.setCapturedScope(currentScope);
            decls[i] = decl;
            inits[i] = QuoteExp.nullExp;
            bindings = bind_pair.getCdr();
        }
        if (this.recursive) {
            push(letExp, tr, renamedAliases);
        }
        Macro savedMacro = tr.currentMacroDefinition;
        for (int i2 = 0; i2 < decl_count; i2++) {
            Macro macro2 = macros[i2];
            tr.currentMacroDefinition = macro2;
            Expression value = tr.rewrite_car(transformers[i2], trSyntax[i2]);
            inits[i2] = value;
            Declaration decl2 = decls[i2];
            macro2.expander = value;
            QuoteExp quoteExp = new QuoteExp(macro2);
            decl2.noteValue(quoteExp);
            if (value instanceof LambdaExp) {
                LambdaExp lvalue = (LambdaExp) value;
                lvalue.nameDecl = decl2;
                lvalue.setSymbol(decl2.getSymbol());
            }
        }
        tr.currentMacroDefinition = savedMacro;
        if (!this.recursive) {
            push(letExp, tr, renamedAliases);
        }
        Expression rewrite_body = tr.rewrite_body(body);
        tr.pop(letExp);
        tr.popRenamedAlias(renamedAliasesCount);
        return rewrite_body;
    }

    private void push(LetExp let, Translator tr, Stack renamedAliases) {
        tr.push((ScopeExp) let);
        if (renamedAliases != null) {
            int i = renamedAliases.size();
            while (true) {
                i--;
                if (i >= 0) {
                    tr.pushRenamedAlias((Declaration) renamedAliases.pop());
                } else {
                    return;
                }
            }
        }
    }
}
