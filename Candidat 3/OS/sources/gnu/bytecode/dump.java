package gnu.bytecode;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

public class dump extends ClassFileInput {
    ClassTypeWriter writer;

    dump(InputStream str, ClassTypeWriter writer2) throws IOException, ClassFormatError {
        super(str);
        this.ctype = new ClassType();
        readFormatVersion();
        readConstants();
        readClassInfo();
        readFields();
        readMethods();
        readAttributes(this.ctype);
        writer2.print(this.ctype);
        writer2.flush();
    }

    public ConstantPool readConstants() throws IOException {
        this.ctype.constants = super.readConstants();
        return this.ctype.constants;
    }

    public Attribute readAttribute(String name, int length, AttrContainer container) throws IOException {
        return super.readAttribute(name, length, container);
    }

    static int readMagic(InputStream in) throws IOException {
        int magic = 0;
        for (int j = 0; j < 4; j++) {
            int b = in.read();
            if (b < 0) {
                break;
            }
            magic = (magic << 8) | (b & 255);
        }
        return magic;
    }

    public static void process(InputStream in, String filename, OutputStream out, int flags) throws IOException {
        process(in, filename, new ClassTypeWriter((ClassType) null, out, flags));
    }

    public static void process(InputStream in, String filename, Writer out, int flags) throws IOException {
        process(in, filename, new ClassTypeWriter((ClassType) null, out, flags));
    }

    public static void process(InputStream in, String filename, ClassTypeWriter out) throws IOException {
        InputStream inp = new BufferedInputStream(in);
        inp.mark(5);
        int magic = readMagic(inp);
        if (magic == -889275714) {
            out.print("Reading .class from ");
            out.print(filename);
            out.println('.');
            new dump(inp, out);
        } else if (magic == 1347093252) {
            inp.reset();
            out.print("Reading classes from archive ");
            out.print(filename);
            out.println('.');
            ZipInputStream zin = new ZipInputStream(inp);
            while (true) {
                ZipEntry zent = zin.getNextEntry();
                if (zent != null) {
                    String name = zent.getName();
                    if (zent.isDirectory()) {
                        out.print("Archive directory: ");
                        out.print(name);
                        out.println('.');
                    } else {
                        out.println();
                        if (readMagic(zin) == -889275714) {
                            out.print("Reading class member: ");
                            out.print(name);
                            out.println('.');
                            new dump(zin, out);
                        } else {
                            out.print("Skipping non-class member: ");
                            out.print(name);
                            out.println('.');
                        }
                    }
                } else {
                    System.exit(-1);
                    return;
                }
            }
        } else {
            System.err.println("File " + filename + " is not a valid .class file");
            System.exit(-1);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:53:?, code lost:
        java.lang.System.err.print("File for URL ");
        java.lang.System.err.print(r12);
        java.lang.System.err.println(" not found.");
        java.lang.System.exit(-1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0182, code lost:
        r15 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
        java.lang.System.err.print("Jar File for URL ");
        java.lang.System.err.print(r13);
        java.lang.System.err.println(" not found.");
        java.lang.System.exit(-1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x01a1, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:?, code lost:
        java.lang.System.err.print("Error opening zip archive ");
        java.lang.System.err.print(r12);
        java.lang.System.err.println(" not found.");
        r8.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x01be, code lost:
        if (r8.getCause() != null) goto L_0x01c0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x01c0, code lost:
        r8.getCause().printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x01c7, code lost:
        java.lang.System.exit(-1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x01cc, code lost:
        r15 = null;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0167 A[ExcHandler: FileNotFoundException (e java.io.FileNotFoundException), Splitter:B:27:0x00c0] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x01a1 A[ExcHandler: ZipException (r8v1 'e1' java.util.zip.ZipException A[CUSTOM_DECLARE]), Splitter:B:27:0x00c0] */
    public static void main(String[] args) {
        ClassLoader loader;
        InputStream in;
        URL url;
        int alen = args.length;
        ClassTypeWriter classTypeWriter = new ClassTypeWriter((ClassType) null, (OutputStream) System.out, 0);
        if (alen == 0) {
            usage(System.err);
        }
        for (int i = 0; i < alen; i++) {
            String filename = args[i];
            if (filename.equals("-verbose") || filename.equals("--verbose")) {
                classTypeWriter.setFlags(15);
            } else {
                if (uriSchemeSpecified(filename)) {
                    try {
                        boolean isJarURL = filename.startsWith("jar:");
                        if (isJarURL) {
                            String part = filename.substring(4);
                            if (!uriSchemeSpecified(part)) {
                                int excl = part.indexOf(33);
                                if (excl >= 0) {
                                    File file = new File(part.substring(0, excl));
                                    filename = "jar:" + file.toURI().toURL().toString() + part.substring(excl);
                                }
                            }
                            if (part.indexOf("!/") < 0) {
                                int excl2 = filename.lastIndexOf(33);
                                if (excl2 <= 0) {
                                    isJarURL = false;
                                } else if (filename.indexOf(47, excl2) < 0) {
                                    filename = filename.substring(0, excl2 + 1) + '/' + filename.substring(excl2 + 1).replace('.', '/') + ".class";
                                }
                            }
                        }
                        try {
                            url = new URL(filename);
                            in = url.openConnection().getInputStream();
                        } catch (ZipException e1) {
                            if (isJarURL) {
                                String filepart = url.getFile();
                                int sl = filepart.lastIndexOf(33);
                                if (sl > 0) {
                                    filepart = filepart.substring(0, sl);
                                }
                                URL url2 = new URL(filepart);
                                url2.openConnection().getInputStream();
                            }
                            throw e1;
                        } catch (FileNotFoundException e) {
                        } catch (ZipException e12) {
                        }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        System.err.println("caught ");
                        System.err.print(e2);
                        System.exit(-1);
                    }
                } else {
                    try {
                        in = new FileInputStream(filename);
                    } catch (FileNotFoundException e3) {
                        try {
                            loader = ObjectType.getContextClass(filename).getClassLoader();
                        } catch (NoClassDefFoundError e4) {
                            loader = ObjectType.getContextClassLoader();
                        } catch (Throwable th) {
                            System.err.print("File ");
                            System.err.print(filename);
                            System.err.println(" not found.");
                            System.exit(-1);
                            loader = null;
                        }
                        URL resource = loader.getResource(filename.replace('.', '/') + ".class");
                        in = resource.openConnection().getInputStream();
                        filename = resource.toString();
                    } catch (Throwable ex) {
                        System.err.print("Can't find .class file for class ");
                        System.err.print(filename);
                        System.err.print(" - ");
                        System.err.println(ex);
                        System.exit(-1);
                        in = null;
                    }
                }
                process(in, filename, classTypeWriter);
            }
        }
    }

    static int uriSchemeLength(String uri) {
        int len = uri.length();
        int i = 0;
        while (i < len) {
            char ch = uri.charAt(i);
            if (ch == ':') {
                return i;
            }
            if (i != 0) {
                if (!(Character.isLetterOrDigit(ch) || ch == '+' || ch == '-' || ch == '.')) {
                }
                i++;
            } else if (Character.isLetter(ch)) {
                i++;
            }
            return -1;
        }
        return -1;
    }

    static boolean uriSchemeSpecified(String name) {
        boolean z = true;
        int ulen = uriSchemeLength(name);
        if (ulen == 1 && File.separatorChar == '\\') {
            char drive = name.charAt(0);
            if (drive >= 'a' && drive <= 'z') {
                return false;
            }
            if (drive < 'A' || drive > 'Z') {
                return true;
            }
            return false;
        }
        if (ulen <= 0) {
            z = false;
        }
        return z;
    }

    public static void usage(PrintStream err) {
        err.println("Prints and dis-assembles the contents of JVM .class files.");
        err.println("Usage: [--verbose] class-or-jar ...");
        err.println("where a class-or-jar can be one of:");
        err.println("- a fully-qualified class name; or");
        err.println("- the name of a .class file, or a URL reference to one; or");
        err.println("- the name of a .jar or .zip archive file, or a URL reference to one.");
        err.println("If a .jar/.zip archive is named, all its.class file members are printed.");
        err.println();
        err.println("You can name a single .class member of an archive with a jar: URL,");
        err.println("which looks like: jar:jar-spec!/p1/p2/cl.class");
        err.println("The jar-spec can be a URL or the name of the .jar file.");
        err.println("You can also use the shorthand syntax: jar:jar-spec!p1.p2.cl");
        System.exit(-1);
    }
}
