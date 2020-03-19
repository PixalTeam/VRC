package gnu.kawa.lispexpr;

import gnu.bytecode.Field;
import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Keyword;
import gnu.expr.Language;
import gnu.expr.ModuleExp;
import gnu.expr.NameLookup;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.lists.Sequence;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.InPort;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.text.Lexer;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.IOException;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public abstract class LispLanguage extends Language {
    public static final Symbol bracket_apply_sym = Namespace.EmptyNamespace.getSymbol("$bracket-apply$");
    public static final Symbol bracket_list_sym = Namespace.EmptyNamespace.getSymbol("$bracket-list$");
    public static StaticFieldLocation getNamedPartLocation = new StaticFieldLocation("gnu.kawa.functions.GetNamedPart", "getNamedPart");
    public static final Symbol lookup_sym = Namespace.EmptyNamespace.getSymbol("$lookup$");
    public static final String quasiquote_sym = "quasiquote";
    public static final String quote_sym = "quote";
    public static final String unquote_sym = "unquote";
    public static final String unquotesplicing_sym = "unquote-splicing";
    public ReadTable defaultReadTable = createReadTable();

    public abstract ReadTable createReadTable();

    static {
        getNamedPartLocation.setProcedure();
    }

    public Lexer getLexer(InPort inp, SourceMessages messages) {
        return new LispReader(inp, messages);
    }

    public Compilation getCompilation(Lexer lexer, SourceMessages messages, NameLookup lexical) {
        return new Translator(this, messages, lexical);
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0040, code lost:
        if (r7.getMessages().seenErrors() == false) goto L_0x0050;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0042, code lost:
        r0 = r4.peek();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0046, code lost:
        if (r0 < 0) goto L_0x0050;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004a, code lost:
        if (r0 == 13) goto L_0x0050;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x004e, code lost:
        if (r0 != 10) goto L_0x0070;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r4.skip();
     */
    public boolean parse(Compilation comp, int options) throws IOException, SyntaxException {
        Translator tr = (Translator) comp;
        Lexer lexer = tr.lexer;
        ModuleExp mexp = tr.mainLambda;
        new Values();
        LispReader reader = (LispReader) lexer;
        Compilation saveComp = Compilation.setSaveCurrent(tr);
        try {
            if (tr.pendingForm != null) {
                tr.scanForm(tr.pendingForm, mexp);
                tr.pendingForm = null;
            }
            while (true) {
                Object sexp = reader.readCommand();
                if (sexp != Sequence.eofValue) {
                    tr.scanForm(sexp, mexp);
                    if ((options & 4) != 0) {
                        break;
                    } else if ((options & 8) != 0) {
                        if (tr.getState() >= 2) {
                            Compilation.restoreCurrent(saveComp);
                            return true;
                        }
                    }
                } else if ((options & 4) != 0) {
                    Compilation.restoreCurrent(saveComp);
                    return false;
                }
            }
            if (lexer.peek() == 41) {
                lexer.fatal("An unexpected close paren was read.");
            }
            tr.finishModule(mexp);
            if ((options & 8) == 0) {
                tr.firstForm = 0;
            }
            tr.setState(4);
            Compilation.restoreCurrent(saveComp);
            return true;
        } catch (Throwable th) {
            Compilation.restoreCurrent(saveComp);
            throw th;
        }
    }

    public void resolve(Compilation comp) {
        Translator tr = (Translator) comp;
        tr.resolveModule(tr.getModule());
    }

    public Declaration declFromField(ModuleExp mod, Object fvalue, Field fld) {
        Declaration fdecl = super.declFromField(mod, fvalue, fld);
        if (((fld.getModifiers() & 16) != 0) && (fvalue instanceof Syntax)) {
            fdecl.setSyntax();
        }
        return fdecl;
    }

    /* access modifiers changed from: protected */
    public void defSntxStFld(String name, String cname, String fname) {
        StaticFieldLocation.define(this.environ, this.environ.getSymbol(name), hasSeparateFunctionNamespace() ? EnvironmentKey.FUNCTION : null, cname, fname).setSyntax();
    }

    /* access modifiers changed from: protected */
    public void defSntxStFld(String name, String cname) {
        defSntxStFld(name, cname, Compilation.mangleNameIfNeeded(name));
    }

    public Expression makeBody(Expression[] exps) {
        return new BeginExp(exps);
    }

    public Expression makeApply(Expression func, Expression[] args) {
        return new ApplyExp(func, args);
    }

    public boolean selfEvaluatingSymbol(Object obj) {
        return obj instanceof Keyword;
    }

    public static Symbol langSymbolToSymbol(Object sym) {
        return ((LispLanguage) Language.getDefaultLanguage()).fromLangSymbol(sym);
    }

    /* access modifiers changed from: protected */
    public Symbol fromLangSymbol(Object sym) {
        if (sym instanceof String) {
            return getSymbol((String) sym);
        }
        return (Symbol) sym;
    }

    public Expression checkDefaultBinding(Symbol name, Translator tr) {
        return null;
    }
}
