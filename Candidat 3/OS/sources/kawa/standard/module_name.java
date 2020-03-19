package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ModuleExp;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

public class module_name extends Syntax {
    public static final module_name module_name = new module_name();

    static {
        module_name.setName("module-name");
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x005a  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00b1  */
    public void scanForm(Pair form, ScopeExp defs, Translator tr) {
        Object form_cdr = form.getCdr();
        SyntaxForm nameSyntax = null;
        while (form_cdr instanceof SyntaxForm) {
            nameSyntax = (SyntaxForm) form_cdr;
            form_cdr = nameSyntax.getDatum();
        }
        Object arg = form_cdr instanceof Pair ? ((Pair) form_cdr).getCar() : null;
        while (arg instanceof SyntaxForm) {
            nameSyntax = (SyntaxForm) arg;
            arg = nameSyntax.getDatum();
        }
        String name = null;
        String err = null;
        Declaration decl = null;
        if (arg instanceof Pair) {
            Pair p = (Pair) arg;
            if (p.getCar() == LispLanguage.quote_sym) {
                Object arg2 = p.getCdr();
                if (arg2 instanceof Pair) {
                    Pair p2 = (Pair) arg2;
                    if (p2.getCdr() == LList.Empty && (p2.getCar() instanceof String)) {
                        name = (String) p2.getCar();
                        if (err == null) {
                            tr.formStack.add(tr.syntaxError(err));
                            return;
                        }
                        int index = name.lastIndexOf(46);
                        String className = name;
                        if (index >= 0) {
                            tr.classPrefix = name.substring(0, index + 1);
                        } else {
                            name = tr.classPrefix + name;
                            className = tr.classPrefix + Compilation.mangleName(name);
                        }
                        ModuleExp module = tr.getModule();
                        if (tr.mainClass == null) {
                            tr.mainClass = new ClassType(className);
                        } else {
                            String oldName = tr.mainClass.getName();
                            if (oldName == null) {
                                tr.mainClass.setName(className);
                            } else if (!oldName.equals(className)) {
                                tr.syntaxError("duplicate module-name: old name: " + oldName);
                            }
                        }
                        module.setType(tr.mainClass);
                        module.setName(name);
                        if (decl != null) {
                            decl.noteValue(new QuoteExp(tr.mainClass, Compilation.typeClass));
                            decl.setFlag(16793600);
                            if (module.outer == null) {
                                decl.setFlag(2048);
                            }
                            decl.setPrivate(true);
                            decl.setType(Compilation.typeClass);
                        }
                        tr.mustCompileHere();
                        return;
                    }
                }
                err = "invalid quoted symbol for 'module-name'";
                if (err == null) {
                }
            }
        }
        if ((arg instanceof FString) || (arg instanceof String)) {
            name = arg.toString();
            if (err == null) {
            }
        } else {
            if (arg instanceof Symbol) {
                name = arg.toString();
                int len = name.length();
                if (len > 2 && name.charAt(0) == '<' && name.charAt(len - 1) == '>') {
                    name = name.substring(1, len - 1);
                }
                decl = tr.define(arg, nameSyntax, defs);
            } else {
                err = "un-implemented expression in module-name";
            }
            if (err == null) {
            }
        }
    }
}
