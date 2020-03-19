package kawa.standard;

import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ModuleExp;
import gnu.expr.ScopeExp;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class module_static extends Syntax {
    public static final module_static module_static = new module_static();

    static {
        module_static.setName("module-static");
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:52:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    public boolean scanForDefinitions(Pair st, Vector forms, ScopeExp defs, Translator tr) {
        Object list = st.getCdr();
        if (!(defs instanceof ModuleExp)) {
            tr.error('e', "'" + getName() + "' not at module level");
            return true;
        }
        ModuleExp mexp = (ModuleExp) defs;
        if (list instanceof Pair) {
            Pair st2 = (Pair) list;
            if (st2.getCdr() == LList.Empty && (st2.getCar() instanceof Boolean)) {
                if (st2.getCar() == Boolean.FALSE) {
                    mexp.setFlag(65536);
                } else {
                    mexp.setFlag(32768);
                }
                if (mexp.getFlag(65536) && mexp.getFlag(32768)) {
                    tr.error('e', "inconsistent module-static specifiers");
                    return true;
                }
            }
        }
        if (list instanceof Pair) {
            Pair st3 = (Pair) list;
            if (st3.getCdr() == LList.Empty && (st3.getCar() instanceof Pair)) {
                Pair st4 = (Pair) st3.getCar();
                if (tr.matches(st4.getCar(), LispLanguage.quote_sym)) {
                    Pair st5 = (Pair) st4.getCdr();
                    if (st5 == LList.Empty || !(st5.getCar() instanceof SimpleSymbol) || st5.getCar().toString() != "init-run") {
                        tr.error('e', "invalid quoted symbol for '" + getName() + '\'');
                        return false;
                    }
                    mexp.setFlag(32768);
                    mexp.setFlag(262144);
                    return mexp.getFlag(65536) ? true : true;
                }
            }
        }
        mexp.setFlag(65536);
        while (list != LList.Empty) {
            if (list instanceof Pair) {
                Pair st6 = (Pair) list;
                if (st6.getCar() instanceof Symbol) {
                    Declaration decl = defs.getNoDefine((Symbol) st6.getCar());
                    if (decl.getFlag(512)) {
                        Translator.setLine(decl, (Object) st6);
                    }
                    decl.setFlag(2048);
                    list = st6.getCdr();
                }
            }
            tr.error('e', "invalid syntax in '" + getName() + '\'');
            return false;
        }
        if (mexp.getFlag(65536)) {
        }
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        return null;
    }
}
