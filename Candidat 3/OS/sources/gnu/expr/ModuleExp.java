package gnu.expr;

import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import gnu.bytecode.ArrayClassLoader;
import gnu.bytecode.ClassType;
import gnu.bytecode.Field;
import gnu.kawa.reflect.ClassMemberLocation;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.Location;
import gnu.mapping.OutPort;
import gnu.mapping.Symbol;
import gnu.mapping.WrappedException;
import gnu.text.Path;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.Externalizable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.URL;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ModuleExp extends LambdaExp implements Externalizable {
    public static final int EXPORT_SPECIFIED = 16384;
    public static final int IMMEDIATE = 1048576;
    public static final int LAZY_DECLARATIONS = 524288;
    public static final int NONSTATIC_SPECIFIED = 65536;
    public static final int STATIC_RUN_SPECIFIED = 262144;
    public static final int STATIC_SPECIFIED = 32768;
    public static final int SUPERTYPE_SPECIFIED = 131072;
    public static boolean alwaysCompile = compilerAvailable;
    public static boolean compilerAvailable = true;
    public static String dumpZipPrefix;
    public static int interactiveCounter;
    static int lastZipCounter;
    public static boolean neverCompile = false;
    ModuleInfo info;
    ClassType[] interfaces;
    ClassType superType;

    public static Class evalToClass(Compilation comp, URL url) throws SyntaxException {
        ModuleExp module = comp.getModule();
        SourceMessages messages = comp.getMessages();
        try {
            comp.minfo.loadByStages(12);
            if (messages.seenErrors()) {
                return null;
            }
            ArrayClassLoader loader = comp.loader;
            if (url == null) {
                url = Path.currentPath().toURL();
            }
            loader.setResourceContext(url);
            ZipOutputStream zout = null;
            if (dumpZipPrefix != null) {
                StringBuffer stringBuffer = new StringBuffer(dumpZipPrefix);
                lastZipCounter++;
                if (interactiveCounter > lastZipCounter) {
                    lastZipCounter = interactiveCounter;
                }
                stringBuffer.append(lastZipCounter);
                stringBuffer.append(".zip");
                FileOutputStream fileOutputStream = new FileOutputStream(stringBuffer.toString());
                zout = new ZipOutputStream(fileOutputStream);
            }
            for (int iClass = 0; iClass < comp.numClasses; iClass++) {
                ClassType clas = comp.classes[iClass];
                String className = clas.getName();
                byte[] classBytes = clas.writeToArray();
                loader.addClass(className, classBytes);
                if (zout != null) {
                    ZipEntry zipEntry = new ZipEntry(className.replace('.', '/') + ".class");
                    zipEntry.setSize((long) classBytes.length);
                    CRC32 crc = new CRC32();
                    crc.update(classBytes);
                    zipEntry.setCrc(crc.getValue());
                    zipEntry.setMethod(0);
                    zout.putNextEntry(zipEntry);
                    zout.write(classBytes);
                }
            }
            if (zout != null) {
                zout.close();
            }
            Class clas2 = null;
            ArrayClassLoader context = loader;
            while (context.getParent() instanceof ArrayClassLoader) {
                context = (ArrayClassLoader) context.getParent();
            }
            for (int iClass2 = 0; iClass2 < comp.numClasses; iClass2++) {
                ClassType ctype = comp.classes[iClass2];
                Class cclass = loader.loadClass(ctype.getName());
                ctype.setReflectClass(cclass);
                ctype.setExisting(true);
                if (iClass2 == 0) {
                    clas2 = cclass;
                } else if (context != loader) {
                    context.addClass(cclass);
                }
            }
            ModuleInfo minfo = comp.minfo;
            minfo.setModuleClass(clas2);
            comp.cleanupAfterCompilation();
            int ndeps = minfo.numDependencies;
            for (int idep = 0; idep < ndeps; idep++) {
                ModuleInfo dep = minfo.dependencies[idep];
                Class dclass = dep.getModuleClassRaw();
                if (dclass == null) {
                    dclass = evalToClass(dep.comp, null);
                }
                comp.loader.addClass(dclass);
            }
            return clas2;
        } catch (IOException ex) {
            WrappedException wrappedException = new WrappedException("I/O error in lambda eval", ex);
            throw wrappedException;
        } catch (ClassNotFoundException ex2) {
            WrappedException wrappedException2 = new WrappedException("class not found in lambda eval", ex2);
            throw wrappedException2;
        } catch (Throwable ex3) {
            comp.getMessages().error('f', "internal compile error - caught " + ex3, ex3);
            SyntaxException syntaxException = new SyntaxException(messages);
            throw syntaxException;
        }
    }

    public static void mustNeverCompile() {
        alwaysCompile = false;
        neverCompile = true;
        compilerAvailable = false;
    }

    public static void mustAlwaysCompile() {
        alwaysCompile = true;
        neverCompile = false;
    }

    public static final boolean evalModule(Environment env, CallContext ctx, Compilation comp, URL url, OutPort msg) throws Throwable {
        ModuleExp mexp = comp.getModule();
        Language language = comp.getLanguage();
        Object inst = evalModule1(env, comp, url, msg);
        if (inst == null) {
            return false;
        }
        evalModule2(env, ctx, language, mexp, inst);
        return true;
    }

    /* JADX INFO: used method not loaded: gnu.text.SourceMessages.checkErrors(java.io.PrintWriter, int):null, types can be incorrect */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0041, code lost:
        if (r2.checkErrors((java.io.PrintWriter) r14, 20) != false) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0054, code lost:
        if (r2.seenErrors() == false) goto L_0x0056;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0058, code lost:
        if (r12.mustCompile != false) goto L_0x0099;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x005c, code lost:
        if (gnu.expr.Compilation.debugPrintFinalExpr == false) goto L_0x008b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x005e, code lost:
        if (r14 == null) goto L_0x008b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0060, code lost:
        r14.println("[Evaluating final module \"" + r3.getName() + "\":");
        r3.print(r14);
        r14.println(']');
        r14.flush();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x008b, code lost:
        r0 = java.lang.Boolean.TRUE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x008d, code lost:
        gnu.mapping.Environment.restoreCurrent(r5);
        gnu.expr.Compilation.restoreCurrent(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0093, code lost:
        if (r7 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0095, code lost:
        r7.setContextClassLoader(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        r0 = evalToClass(r12, r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x009d, code lost:
        if (r0 != null) goto L_0x00ac;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x009f, code lost:
        gnu.mapping.Environment.restoreCurrent(r5);
        gnu.expr.Compilation.restoreCurrent(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00a5, code lost:
        if (r7 == null) goto L_0x00aa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00a7, code lost:
        r7.setContextClassLoader(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        r7 = java.lang.Thread.currentThread();
        r6 = r7.getContextClassLoader();
        r7.setContextClassLoader(r0.getClassLoader());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00c9, code lost:
        if (r2.checkErrors((java.io.PrintWriter) r14, 20) != false) goto L_0x00cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00de, code lost:
        r7 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00e4, code lost:
        if (r2.seenErrors() == false) goto L_0x00e6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00e6, code lost:
        gnu.mapping.Environment.restoreCurrent(r5);
        gnu.expr.Compilation.restoreCurrent(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00ec, code lost:
        if (r7 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00ee, code lost:
        r7.setContextClassLoader(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:?, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:?, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:?, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:?, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:?, code lost:
        return r0;
     */
    public static final Object evalModule1(Environment env, Compilation comp, URL url, OutPort msg) throws SyntaxException {
        ModuleExp mexp = comp.getModule();
        mexp.info = comp.minfo;
        Environment orig_env = Environment.setSaveCurrent(env);
        Compilation orig_comp = Compilation.setSaveCurrent(comp);
        SourceMessages messages = comp.getMessages();
        ClassLoader savedLoader = null;
        Thread thread = null;
        if (!alwaysCompile || !neverCompile) {
            if (neverCompile) {
                comp.mustCompile = false;
            }
            try {
                comp.process(6);
                comp.minfo.loadByStages(8);
                if (msg != null) {
                }
                return null;
            } finally {
                Environment.restoreCurrent(orig_env);
                Compilation.restoreCurrent(orig_comp);
                if (thread != null) {
                    thread.setContextClassLoader(savedLoader);
                }
            }
        } else {
            throw new RuntimeException("alwaysCompile and neverCompile are both true!");
        }
        mexp.body = null;
        mexp.thisVariable = null;
        if (msg != null) {
        }
        Boolean valueOf = Boolean.valueOf(false);
        Environment.restoreCurrent(orig_env);
        Compilation.restoreCurrent(orig_comp);
        if (thread == null) {
            return valueOf;
        }
        thread.setContextClassLoader(savedLoader);
        return valueOf;
    }

    public static final void evalModule2(Environment env, CallContext ctx, Language language, ModuleExp mexp, Object inst) throws Throwable {
        Symbol sym;
        Object value;
        Environment orig_env = Environment.setSaveCurrent(env);
        Thread thread = null;
        try {
            if (inst == Boolean.TRUE) {
                mexp.body.apply(ctx);
            } else {
                if (inst instanceof Class) {
                    inst = ModuleContext.getContext().findInstance((Class) inst);
                }
                if (inst instanceof Runnable) {
                    if (inst instanceof ModuleBody) {
                        ModuleBody mb = (ModuleBody) inst;
                        if (!mb.runDone) {
                            mb.runDone = true;
                            mb.run(ctx);
                        }
                    } else {
                        ((Runnable) inst).run();
                    }
                }
                if (mexp == null) {
                    ClassMemberLocation.defineAll(inst, language, env);
                } else {
                    for (Declaration decl = mexp.firstDecl(); decl != null; decl = decl.nextDecl()) {
                        Object dname = decl.getSymbol();
                        if (!decl.isPrivate() && dname != null) {
                            Field fld = decl.field;
                            if (dname instanceof Symbol) {
                                sym = (Symbol) dname;
                            } else {
                                sym = Symbol.make("", dname.toString().intern());
                            }
                            Object property = language.getEnvPropertyFor(decl);
                            Expression dvalue = decl.getValue();
                            if ((decl.field.getModifiers() & 16) != 0) {
                                if (!(dvalue instanceof QuoteExp) || dvalue == QuoteExp.undefined_exp) {
                                    value = decl.field.getReflectField().get(null);
                                    if (!decl.isIndirectBinding()) {
                                        decl.setValue(QuoteExp.getInstance(value));
                                    } else if (!decl.isAlias() || !(dvalue instanceof ReferenceExp)) {
                                        decl.setValue(null);
                                    }
                                } else {
                                    value = ((QuoteExp) dvalue).getValue();
                                }
                                if (decl.isIndirectBinding()) {
                                    env.addLocation(sym, property, (Location) value);
                                } else {
                                    env.define(sym, property, value);
                                }
                            } else {
                                StaticFieldLocation loc = new StaticFieldLocation(fld.getDeclaringClass(), fld.getName());
                                loc.setDeclaration(decl);
                                env.addLocation(sym, property, loc);
                                decl.setValue(null);
                            }
                        }
                    }
                }
            }
            ctx.runUntilDone();
        } finally {
            Environment.restoreCurrent(orig_env);
            if (thread != null) {
                thread.setContextClassLoader(null);
            }
        }
    }

    public String getNamespaceUri() {
        return this.info.uri;
    }

    public final ClassType getSuperType() {
        return this.superType;
    }

    public final void setSuperType(ClassType s) {
        this.superType = s;
    }

    public final ClassType[] getInterfaces() {
        return this.interfaces;
    }

    public final void setInterfaces(ClassType[] s) {
        this.interfaces = s;
    }

    public final boolean isStatic() {
        return getFlag(32768) || ((Compilation.moduleStatic >= 0 || getFlag(1048576)) && !getFlag(131072) && !getFlag(65536));
    }

    public boolean staticInitRun() {
        return isStatic() && (getFlag(262144) || Compilation.moduleStatic == 2);
    }

    public void allocChildClasses(Compilation comp) {
        declareClosureEnv();
        if (comp.usingCPStyle()) {
            allocFrame(comp);
        }
    }

    /* access modifiers changed from: 0000 */
    public void allocFields(Compilation comp) {
        for (Declaration decl = firstDecl(); decl != null; decl = decl.nextDecl()) {
            if ((!decl.isSimple() || decl.isPublic()) && decl.field == null && decl.getFlag(65536) && decl.getFlag(6)) {
                decl.makeField(comp, null);
            }
        }
        for (Declaration decl2 = firstDecl(); decl2 != null; decl2 = decl2.nextDecl()) {
            if (decl2.field == null) {
                Expression value = decl2.getValue();
                if ((!decl2.isSimple() || decl2.isPublic() || decl2.isNamespaceDecl() || (decl2.getFlag(JSONzip.int14) && decl2.getFlag(6))) && !decl2.getFlag(65536)) {
                    if (!(value instanceof LambdaExp) || (value instanceof ModuleExp) || (value instanceof ClassExp)) {
                        if (!decl2.shouldEarlyInit() && !decl2.isAlias()) {
                            value = null;
                        }
                        decl2.makeField(comp, value);
                    } else {
                        ((LambdaExp) value).allocFieldFor(comp);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitModuleExp(this, d);
    }

    public void print(OutPort out) {
        out.startLogicalBlock("(Module/", ")", 2);
        Object sym = getSymbol();
        if (sym != null) {
            out.print(sym);
            out.print('/');
        }
        out.print(this.id);
        out.print('/');
        out.writeSpaceFill();
        out.startLogicalBlock("(", false, ")");
        Declaration decl = firstDecl();
        if (decl != null) {
            out.print("Declarations:");
            while (decl != null) {
                out.writeSpaceFill();
                decl.printInfo(out);
                decl = decl.nextDecl();
            }
        }
        out.endLogicalBlock(")");
        out.writeSpaceLinear();
        if (this.body == null) {
            out.print("<null body>");
        } else {
            this.body.print(out);
        }
        out.endLogicalBlock(")");
    }

    public Declaration firstDecl() {
        synchronized (this) {
            if (getFlag(524288)) {
                this.info.setupModuleExp();
            }
        }
        return this.decls;
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x007d  */
    /* JADX WARNING: Removed duplicated region for block: B:53:? A[RETURN, SYNTHETIC] */
    public ClassType classFor(Compilation comp) {
        String fileName;
        String className;
        if (this.type != null && this.type != Compilation.typeProcedure) {
            return this.type;
        }
        String fileName2 = getFileName();
        String mname = getName();
        Path path = null;
        if (mname != null) {
            fileName = mname;
        } else if (fileName2 == null) {
            fileName = getName();
            if (fileName == null) {
                fileName = "$unnamed_input_file$";
            }
        } else if (this.filename.equals("-") || this.filename.equals("/dev/stdin")) {
            fileName = getName();
            if (fileName == null) {
                fileName = "$stdin$";
            }
        } else {
            path = Path.valueOf(fileName2);
            fileName = path.getLast();
            int dotIndex = fileName.lastIndexOf(46);
            if (dotIndex > 0) {
                fileName = fileName.substring(0, dotIndex);
            }
        }
        if (getName() == null) {
            setName(fileName);
        }
        String fileName3 = Compilation.mangleNameIfNeeded(fileName);
        if (comp.classPrefix.length() == 0 && path != null && !path.isAbsolute()) {
            Path parentPath = path.getParent();
            if (parentPath != null) {
                String parent = parentPath.toString();
                if (parent.length() > 0 && parent.indexOf("..") < 0) {
                    String parent2 = parent.replaceAll(System.getProperty("file.separator"), "/");
                    if (parent2.startsWith("./")) {
                        parent2 = parent2.substring(2);
                    }
                    className = parent2.equals(".") ? fileName3 : Compilation.mangleURI(parent2) + "." + fileName3;
                    ClassType clas = new ClassType(className);
                    setType(clas);
                    if (comp.mainLambda == this) {
                        return clas;
                    }
                    if (comp.mainClass == null) {
                        comp.mainClass = clas;
                        return clas;
                    } else if (className.equals(comp.mainClass.getName())) {
                        return clas;
                    } else {
                        comp.error('e', "inconsistent main class name: " + className + " - old name: " + comp.mainClass.getName());
                        return clas;
                    }
                }
            }
        }
        className = comp.classPrefix + fileName3;
        ClassType clas2 = new ClassType(className);
        setType(clas2);
        if (comp.mainLambda == this) {
        }
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        String name = null;
        if (this.type == null || this.type == Compilation.typeProcedure || this.type.isExisting()) {
            if (0 == 0) {
                name = getName();
            }
            if (name == null) {
                name = getFileName();
            }
            out.writeObject(name);
            return;
        }
        out.writeObject(this.type);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        Object name = in.readObject();
        if (name instanceof ClassType) {
            this.type = (ClassType) name;
            setName(this.type.getName());
        } else {
            setName((String) name);
        }
        this.flags |= 524288;
    }
}
