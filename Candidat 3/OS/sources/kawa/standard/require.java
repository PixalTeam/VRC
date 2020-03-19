package kawa.standard;

import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import gnu.bytecode.ClassType;
import gnu.bytecode.Field;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Language;
import gnu.expr.ModuleExp;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleManager;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.InPort;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.text.Path;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class require extends Syntax {
    private static final String SLIB_PREFIX = "gnu.kawa.slib.";
    static Hashtable featureMap = new Hashtable();
    public static final require require = new require();

    static {
        require.setName("require");
        map("generic-write", "gnu.kawa.slib.genwrite");
        map("pretty-print", "gnu.kawa.slib.pp");
        map("pprint-file", "gnu.kawa.slib.ppfile");
        map("printf", "gnu.kawa.slib.printf");
        map("xml", "gnu.kawa.slib.XML");
        map("readtable", "gnu.kawa.slib.readtable");
        map("srfi-10", "gnu.kawa.slib.readtable");
        map("http", "gnu.kawa.servlet.HTTP");
        map("servlets", "gnu.kawa.servlet.servlets");
        map("srfi-1", "gnu.kawa.slib.srfi1");
        map("list-lib", "gnu.kawa.slib.srfi1");
        map("srfi-2", "gnu.kawa.slib.srfi2");
        map("and-let*", "gnu.kawa.slib.srfi2");
        map("srfi-13", "gnu.kawa.slib.srfi13");
        map("string-lib", "gnu.kawa.slib.srfi13");
        map("srfi-34", "gnu.kawa.slib.srfi34");
        map("srfi-35", "gnu.kawa.slib.conditions");
        map("condition", "gnu.kawa.slib.conditions");
        map("conditions", "gnu.kawa.slib.conditions");
        map("srfi-37", "gnu.kawa.slib.srfi37");
        map("args-fold", "gnu.kawa.slib.srfi37");
        map("srfi-64", "gnu.kawa.slib.testing");
        map("testing", "gnu.kawa.slib.testing");
        map("srfi-69", "gnu.kawa.slib.srfi69");
        map("hash-table", "gnu.kawa.slib.srfi69");
        map("basic-hash-tables", "gnu.kawa.slib.srfi69");
        map("srfi-95", "kawa.lib.srfi95");
        map("sorting-and-merging", "kawa.lib.srfi95");
        map("regex", "kawa.lib.kawa.regex");
        map("pregexp", "gnu.kawa.slib.pregexp");
        map("gui", "gnu.kawa.slib.gui");
        map("swing-gui", "gnu.kawa.slib.swing");
        map("android-defs", "gnu.kawa.android.defs");
        map("syntax-utils", "gnu.kawa.slib.syntaxutils");
    }

    static void map(String featureName, String className) {
        featureMap.put(featureName, className);
    }

    public static String mapFeature(String featureName) {
        return (String) featureMap.get(featureName);
    }

    public static Object find(String typeName) {
        return ModuleManager.getInstance().findWithClassName(typeName).getInstance();
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0099  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x014f  */
    public boolean scanForDefinitions(Pair st, Vector forms, ScopeExp defs, Translator tr) {
        if (tr.getState() == 1) {
            tr.setState(2);
            tr.pendingForm = st;
            return true;
        }
        Pair args = (Pair) st.getCdr();
        Object name = args.getCar();
        Type type = null;
        if (name instanceof Pair) {
            Pair p = (Pair) name;
            if (tr.matches(p.getCar(), LispLanguage.quote_sym)) {
                Object name2 = p.getCdr();
                if (name2 instanceof Pair) {
                    Pair p2 = (Pair) name2;
                    if (p2.getCdr() == LList.Empty && (p2.getCar() instanceof Symbol)) {
                        String name3 = mapFeature(p2.getCar().toString());
                        if (name3 == null) {
                            tr.error('e', "unknown feature name '" + p2.getCar() + "' for 'require'");
                            return false;
                        }
                        type = ClassType.make(name3);
                        if (type instanceof ClassType) {
                            tr.error('e', "invalid specifier for 'require'");
                            return false;
                        }
                        try {
                            importDefinitions(null, ModuleInfo.find((ClassType) type), null, forms, defs, tr);
                            return true;
                        } catch (Exception e) {
                            tr.error('e', "unknown class " + type.getName());
                            return false;
                        }
                    }
                }
                tr.error('e', "invalid quoted symbol for 'require'");
                return false;
            }
        }
        if (name instanceof CharSequence) {
            String sourceName = name.toString();
            ModuleInfo info = lookupModuleFromSourcePath(sourceName, defs);
            if (info != null) {
                return importDefinitions(null, info, null, forms, defs, tr);
            }
            tr.error('e', "malformed URL: " + sourceName);
            return false;
        }
        if ((name instanceof Symbol) && !tr.selfEvaluatingSymbol(name)) {
            type = tr.getLanguage().getTypeFor(tr.rewrite(name, false));
            if ((type instanceof ClassType) && (args.getCdr() instanceof Pair)) {
                Object name4 = ((Pair) args.getCdr()).getCar();
                if (name4 instanceof CharSequence) {
                    String sourceName2 = name4.toString();
                    ModuleInfo info2 = lookupModuleFromSourcePath(sourceName2, defs);
                    if (info2 != null) {
                        return importDefinitions(type.getName(), info2, null, forms, defs, tr);
                    }
                    tr.error('e', "malformed URL: " + sourceName2);
                    return false;
                }
            }
        }
        if (type instanceof ClassType) {
        }
    }

    public static ModuleInfo lookupModuleFromSourcePath(String sourceName, ScopeExp defs) {
        ModuleManager manager = ModuleManager.getInstance();
        String baseName = defs.getFileName();
        if (baseName != null) {
            sourceName = Path.valueOf(baseName).resolve(sourceName).toString();
        }
        return manager.findWithSourcePath(sourceName);
    }

    public static boolean importDefinitions(String className, ModuleInfo info, Procedure renamer, Vector forms, ScopeExp defs, Compilation tr) {
        boolean isRunnable;
        Declaration adecl;
        Object obj;
        ModuleManager manager = ModuleManager.getInstance();
        if ((info.getState() & 1) == 0 && info.getCompilation() == null && !info.checkCurrent(manager, System.currentTimeMillis())) {
            SourceMessages messages = tr.getMessages();
            Language language = Language.getDefaultLanguage();
            try {
                InPort fstream = InPort.openFile(info.getSourceAbsPath());
                info.clearClass();
                info.setClassName(className);
                int options = 8;
                if (tr.immediate) {
                    options = 8 | 1;
                }
                Compilation comp = language.parse(fstream, messages, options, info);
                info.setClassName(comp.getModule().classFor(comp).getName());
            } catch (FileNotFoundException ex) {
                tr.error('e', "not found: " + ex.getMessage());
                return false;
            } catch (IOException ex2) {
                tr.error('e', "caught " + ex2);
                return false;
            } catch (SyntaxException ex3) {
                if (ex3.getMessages() == messages) {
                    return false;
                }
                throw new RuntimeException("confussing syntax error: " + ex3);
            }
        }
        if (tr.minfo != null && tr.getState() < 4) {
            tr.minfo.addDependency(info);
            if (!info.loadEager(12) && info.getState() < 6) {
                tr.pushPendingImport(info, defs, forms.size());
                return true;
            }
        }
        ClassType type = info.getClassType();
        String tname = type.getName();
        boolean sharedModule = tr.sharedModuleDefs();
        if (info.getState() < 6) {
            isRunnable = info.getCompilation().makeRunnable();
        } else {
            isRunnable = type.isSubtype(Compilation.typeRunnable);
        }
        Declaration decl = null;
        ClassType thisType = ClassType.make("kawa.standard.require");
        QuoteExp quoteExp = new QuoteExp(tname);
        Expression dofind = Invoke.makeInvokeStatic(thisType, "find", new Expression[]{quoteExp});
        Field instanceField = null;
        Language language2 = tr.getLanguage();
        dofind.setLine(tr);
        int formsStart = forms.size();
        ModuleExp mod = info.setupModuleExp();
        Vector declPairs = new Vector();
        for (Declaration fdecl = mod.firstDecl(); fdecl != null; fdecl = fdecl.nextDecl()) {
            if (!fdecl.isPrivate()) {
                Symbol aname = (Symbol) fdecl.getSymbol();
                if (renamer != null) {
                    try {
                        obj = renamer.apply1(aname);
                    } catch (Throwable th) {
                        obj = th;
                    }
                    if (obj != null) {
                        if (!(obj instanceof Symbol)) {
                            tr.error('e', "internal error - import name mapper returned non-symbol: " + obj.getClass().getName());
                        } else {
                            aname = (Symbol) obj;
                        }
                    }
                }
                boolean isStatic = fdecl.getFlag(2048);
                if (!isStatic && decl == null) {
                    decl = new Declaration((Object) SimpleSymbol.valueOf(tname.replace('.', '$') + "$instance"), (Type) type);
                    decl.setPrivate(true);
                    decl.setFlag(1073758208);
                    defs.addDeclaration(decl);
                    decl.noteValue(dofind);
                    SetExp setExp = new SetExp(decl, dofind);
                    setExp.setLine(tr);
                    setExp.setDefining(true);
                    forms.addElement(setExp);
                    formsStart = forms.size();
                    decl.setFlag(536870912);
                    if (isRunnable) {
                        decl.setSimple(false);
                    }
                    decl.setFlag(8192);
                }
                if (fdecl.field != null) {
                    if (fdecl.field.getName().equals("$instance")) {
                        instanceField = fdecl.field;
                    }
                }
                boolean isImportedInstance = fdecl.field != null && fdecl.field.getName().endsWith("$instance");
                Declaration old = defs.lookup(aname, language2, language2.getNamespaceOf(fdecl));
                if (isImportedInstance) {
                    if (old == null) {
                        adecl = defs.addDeclaration((Object) aname);
                        adecl.setFlag(1073758208);
                        adecl.setType(fdecl.getType());
                        adecl.setFlag(8192);
                    }
                } else if (old == null || old.getFlag(512) || Declaration.followAliases(old) != Declaration.followAliases(fdecl)) {
                    if (old == null || !old.getFlag(66048)) {
                        adecl = defs.addDeclaration((Object) aname);
                        if (old != null) {
                            ScopeExp.duplicateDeclarationError(old, adecl, tr);
                        }
                    } else {
                        old.setFlag(false, 66048);
                        adecl = old;
                    }
                    adecl.setAlias(true);
                    adecl.setIndirectBinding(true);
                }
                adecl.setLocation(tr);
                ReferenceExp referenceExp = new ReferenceExp(fdecl);
                referenceExp.setContextDecl(decl);
                if (!isImportedInstance) {
                    referenceExp.setDontDereference(true);
                    if (!sharedModule) {
                        adecl.setPrivate(true);
                    }
                }
                adecl.setFlag(JSONzip.int14);
                if (fdecl.getFlag(32768)) {
                    adecl.setFlag(32768);
                }
                if (fdecl.isProcedureDecl()) {
                    adecl.setProcedureDecl(true);
                }
                if (isStatic) {
                    adecl.setFlag(2048);
                }
                SetExp setExp2 = new SetExp(adecl, (Expression) referenceExp);
                adecl.setFlag(536870912);
                setExp2.setDefining(true);
                if (isImportedInstance) {
                    forms.insertElementAt(setExp2, formsStart);
                    formsStart++;
                } else {
                    forms.addElement(setExp2);
                }
                declPairs.add(adecl);
                declPairs.add(fdecl);
                adecl.noteValue(referenceExp);
                adecl.setFlag(131072);
                tr.push(adecl);
            }
        }
        int ndecls = declPairs.size();
        for (int i = 0; i < ndecls; i += 2) {
            Declaration adecl2 = (Declaration) declPairs.elementAt(i);
            Declaration fdecl2 = (Declaration) declPairs.elementAt(i + 1);
            Expression fval = fdecl2.getValue();
            if (fdecl2.isIndirectBinding() && (fval instanceof ReferenceExp)) {
                ReferenceExp aref = (ReferenceExp) adecl2.getValue();
                Declaration xdecl = ((ReferenceExp) fval).getBinding();
                aref.setBinding(xdecl);
                if (xdecl.needsContext()) {
                    Declaration cdecl = defs.lookup(SimpleSymbol.valueOf(xdecl.field.getDeclaringClass().getName().replace('.', '$') + "$instance"));
                    cdecl.setFlag(1024);
                    aref.setContextDecl(cdecl);
                }
            }
        }
        if (isRunnable) {
            Method run = Compilation.typeRunnable.getDeclaredMethod("run", 0);
            if (decl != null) {
                dofind = new ReferenceExp(decl);
            } else if (instanceField != null) {
                QuoteExp quoteExp2 = new QuoteExp(type);
                dofind = new ApplyExp((Procedure) SlotGet.staticField, quoteExp2, new QuoteExp("$instance"));
            }
            ApplyExp applyExp = new ApplyExp(run, dofind);
            applyExp.setLine(tr);
            forms.addElement(applyExp);
            ApplyExp applyExp2 = applyExp;
        }
        return true;
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        return null;
    }
}
