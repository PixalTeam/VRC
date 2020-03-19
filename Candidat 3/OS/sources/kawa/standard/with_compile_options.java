package kawa.standard;

import gnu.expr.BeginExp;
import gnu.expr.Expression;
import gnu.expr.Keyword;
import gnu.expr.ScopeExp;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.text.Options;
import java.util.Stack;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

public class with_compile_options extends Syntax {
    public static final with_compile_options with_compile_options = new with_compile_options();

    static {
        with_compile_options.setName("with-compile-options");
    }

    public void scanForm(Pair form, ScopeExp defs, Translator tr) {
        Stack stack = new Stack();
        Object rest = getOptions(form.getCdr(), stack, this, tr);
        if (rest != LList.Empty) {
            if (rest == form.getCdr()) {
                tr.scanBody(rest, defs, false);
                return;
            }
            Pair rest2 = new Pair(stack, tr.scanBody(rest, defs, true));
            tr.currentOptions.popOptionValues(stack);
            tr.formStack.add(Translator.makePair(form, form.getCar(), rest2));
        }
    }

    public static Object getOptions(Object form, Stack stack, Syntax command, Translator tr) {
        boolean seenKey = false;
        Options options = tr.currentOptions;
        SyntaxForm syntax = null;
        while (true) {
            if (!(form instanceof SyntaxForm)) {
                if (form instanceof Pair) {
                    Pair pair = (Pair) form;
                    Object pair_car = Translator.stripSyntax(pair.getCar());
                    if (!(pair_car instanceof Keyword)) {
                        break;
                    }
                    String key = ((Keyword) pair_car).getName();
                    seenKey = true;
                    Object savePos = tr.pushPositionOf(pair);
                    try {
                        Object form2 = pair.getCdr();
                        while (form2 instanceof SyntaxForm) {
                            syntax = (SyntaxForm) form2;
                            form2 = syntax.getDatum();
                        }
                        if (!(form2 instanceof Pair)) {
                            tr.error('e', "keyword " + key + " not followed by value");
                            LList lList = LList.Empty;
                            tr.popPositionOf(savePos);
                            return lList;
                        }
                        Pair pair2 = (Pair) form2;
                        Object value = Translator.stripSyntax(pair2.getCar());
                        form = pair2.getCdr();
                        Object oldValue = options.getLocal(key);
                        if (options.getInfo(key) == null) {
                            tr.error('w', "unknown compile option: " + key);
                        } else {
                            if (value instanceof FString) {
                                value = value.toString();
                            } else if (!(value instanceof Boolean) && !(value instanceof Number)) {
                                value = null;
                                tr.error('e', "invalid literal value for key " + key);
                            }
                            options.set(key, value, tr.getMessages());
                            if (stack != null) {
                                stack.push(key);
                                stack.push(oldValue);
                                stack.push(value);
                            }
                            tr.popPositionOf(savePos);
                        }
                    } finally {
                        tr.popPositionOf(savePos);
                    }
                } else {
                    break;
                }
            } else {
                syntax = (SyntaxForm) form;
                form = syntax.getDatum();
            }
        }
        if (!seenKey) {
            tr.error('e', "no option keyword in " + command.getName());
        }
        return Translator.wrapSyntax(form, syntax);
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0041 A[SYNTHETIC, Splitter:B:13:0x0041] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002a A[Catch:{ all -> 0x004d }] */
    public Expression rewriteForm(Pair form, Translator tr) {
        Stack stack;
        Object rest;
        Expression result;
        BeginExp bresult;
        Object obj = form.getCdr();
        if (obj instanceof Pair) {
            Pair p = (Pair) obj;
            if (p.getCar() instanceof Stack) {
                stack = (Stack) p.getCar();
                rest = p.getCdr();
                tr.currentOptions.pushOptionValues(stack);
                result = tr.rewrite_body(rest);
                if (!(result instanceof BeginExp)) {
                    bresult = (BeginExp) result;
                } else {
                    bresult = new BeginExp(new Expression[]{result});
                }
                bresult.setCompileOptions(stack);
                return bresult;
            }
        }
        stack = new Stack();
        rest = getOptions(obj, stack, this, tr);
        try {
            result = tr.rewrite_body(rest);
            if (!(result instanceof BeginExp)) {
            }
            bresult.setCompileOptions(stack);
            return bresult;
        } finally {
            tr.currentOptions.popOptionValues(stack);
        }
    }
}
