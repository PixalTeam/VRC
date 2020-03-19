package kawa.standard;

import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.FluidLetExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

public class fluid_let extends Syntax {
    public static final fluid_let fluid_let = new fluid_let();
    Expression defaultInit;
    boolean star;

    static {
        fluid_let.setName("fluid-set");
    }

    public fluid_let(boolean star2, Expression defaultInit2) {
        this.star = star2;
        this.defaultInit = defaultInit2;
    }

    public fluid_let() {
        this.star = false;
    }

    public Expression rewrite(Object obj, Translator tr) {
        if (!(obj instanceof Pair)) {
            return tr.syntaxError("missing let arguments");
        }
        Pair pair = (Pair) obj;
        return rewrite(pair.getCar(), pair.getCdr(), tr);
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00f7, code lost:
        r9 = r18.syntaxError("invalid " + getName() + " syntax");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0119, code lost:
        r18.popPositionOf(r11);
        r9 = r9;
     */
    public Expression rewrite(Object bindings, Object body, Translator tr) {
        Expression expression;
        Object savePos;
        Object name;
        Expression value;
        int decl_count = this.star ? 1 : LList.length(bindings);
        Expression[] inits = new Expression[decl_count];
        FluidLetExp let = new FluidLetExp(inits);
        int i = 0;
        while (true) {
            if (i < decl_count) {
                Pair bind_pair = (Pair) bindings;
                savePos = tr.pushPositionOf(bind_pair);
                try {
                    name = bind_pair.getCar();
                    if (!(name instanceof String) && !(name instanceof Symbol)) {
                        if (!(name instanceof Pair)) {
                            break;
                        }
                        Pair binding = (Pair) name;
                        if (!(binding.getCar() instanceof String) && !(binding.getCar() instanceof Symbol) && !(binding.getCar() instanceof SyntaxForm)) {
                            break;
                        }
                        name = binding.getCar();
                        if (name instanceof SyntaxForm) {
                            name = ((SyntaxForm) name).getDatum();
                        }
                        if (binding.getCdr() != LList.Empty) {
                            if (!(binding.getCdr() instanceof Pair)) {
                                break;
                            }
                            Pair binding2 = (Pair) binding.getCdr();
                            if (binding2.getCdr() != LList.Empty) {
                                break;
                            }
                            value = tr.rewrite(binding2.getCar());
                        } else {
                            value = this.defaultInit;
                        }
                    } else {
                        value = this.defaultInit;
                    }
                    Declaration decl = let.addDeclaration(name);
                    Declaration found = tr.lexical.lookup(name, false);
                    if (found != null) {
                        found.maybeIndirectBinding(tr);
                        decl.base = found;
                        found.setFluid(true);
                        found.setCanWrite(true);
                    }
                    decl.setCanWrite(true);
                    decl.setFluid(true);
                    decl.setIndirectBinding(true);
                    if (value == null) {
                        value = new ReferenceExp(name);
                    }
                    inits[i] = value;
                    decl.noteValue(null);
                    bindings = bind_pair.getCdr();
                    tr.popPositionOf(savePos);
                    i++;
                } catch (Throwable th) {
                    tr.popPositionOf(savePos);
                    throw th;
                }
            } else {
                tr.push((ScopeExp) let);
                if (!this.star || bindings == LList.Empty) {
                    let.body = tr.rewrite_body(body);
                } else {
                    let.body = rewrite(bindings, body, tr);
                }
                tr.pop(let);
                expression = let;
            }
        }
        Expression syntaxError = tr.syntaxError("bad syntax for value of " + name + " in " + getName());
        tr.popPositionOf(savePos);
        expression = syntaxError;
        return expression;
    }
}
