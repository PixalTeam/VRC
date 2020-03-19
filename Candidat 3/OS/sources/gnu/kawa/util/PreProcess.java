package gnu.kawa.util;

import com.google.appinventor.components.runtime.util.Ev3Constants.Opcode;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class PreProcess {
    static final String JAVA4_FEATURES = "+JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio";
    static final String JAVA5_FEATURES = "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +use:javax.xml.transform +JAXP-1.3 -JAXP-QName";
    static final String NO_JAVA4_FEATURES = "-JAVA5 -use:java.util.IdentityHashMap -use:java.lang.CharSequence -use:java.lang.Throwable.getCause -use:java.net.URI -use:java.util.regex -use:org.w3c.dom.Node -JAXP-1.3 -use:javax.xml.transform -JAVA5 -JAVA6 -JAVA6COMPAT5 -JAXP-QName -use:java.text.Normalizer -SAX2 -use:java.nio -Android";
    static final String NO_JAVA6_FEATURES = "-JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer";
    static String[] version_features = {"java1", "-JAVA2 -JAVA5 -use:java.util.IdentityHashMap -use:java.lang.CharSequence -use:java.lang.Throwable.getCause -use:java.net.URI -use:java.util.regex -use:org.w3c.dom.Node -JAXP-1.3 -use:javax.xml.transform -JAVA5 -JAVA6 -JAVA6COMPAT5 -JAXP-QName -use:java.text.Normalizer -SAX2 -use:java.nio -Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer", "java2", "+JAVA2 -JAVA5 -use:java.util.IdentityHashMap -use:java.lang.CharSequence -use:java.lang.Throwable.getCause -use:java.net.URI -use:java.util.regex -use:org.w3c.dom.Node -JAXP-1.3 -use:javax.xml.transform -JAVA5 -JAVA6 -JAVA6COMPAT5 -JAXP-QName -use:java.text.Normalizer -SAX2 -use:java.nio -Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer", "java4", "-JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio -use:org.w3c.dom.Node -JAXP-1.3 -use:javax.xml.transform -JAXP-QName -JAVA6COMPAT5 -Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer", "java4x", "-JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +JAXP-1.3 +use:javax.xml.transform -JAXP-QName -JAVA6COMPAT5 -Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer", "java5", "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +use:javax.xml.transform +JAXP-1.3 -JAXP-QName -JAVA6COMPAT5 -Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer", "java6compat5", "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +use:javax.xml.transform +JAXP-1.3 -JAXP-QName -JAVA6 -JAVA7 +JAVA6COMPAT5 +use:java.text.Normalizer -use:java.dyn -Android", "java6", "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +use:javax.xml.transform +JAXP-1.3 -JAXP-QName +JAVA6 -JAVA7 -JAVA6COMPAT5 +use:java.text.Normalizer -use:java.dyn -Android", "java7", "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +use:javax.xml.transform +JAXP-1.3 -JAXP-QName +JAVA6 +JAVA7 -JAVA6COMPAT5 +use:java.text.Normalizer +use:java.dyn -Android", "android", "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +JAXP-1.3 -JAXP-QName -use:javax.xml.transform -JAVA6 -JAVA6COMPAT5 +Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer"};
    String filename;
    Hashtable keywords = new Hashtable();
    int lineno;
    byte[] resultBuffer;
    int resultLength;

    /* access modifiers changed from: 0000 */
    public void error(String msg) {
        System.err.println(this.filename + ':' + this.lineno + ": " + msg);
        System.exit(-1);
    }

    public void filter(String filename2) throws Throwable {
        if (filter(filename2, new BufferedInputStream(new FileInputStream(filename2)))) {
            FileOutputStream out = new FileOutputStream(filename2);
            out.write(this.resultBuffer, 0, this.resultLength);
            out.close();
            System.err.println("Pre-processed " + filename2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:113:0x0212  */
    /* JADX WARNING: Removed duplicated region for block: B:167:0x010c A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0138  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0165  */
    public boolean filter(String filename2, BufferedInputStream in) throws Throwable {
        int len;
        int len2;
        int firstNonSpace;
        int lastNonSpace;
        int i;
        int firstNonSpace2;
        int lastNonSpace2;
        String rest;
        Object obj;
        boolean doComment;
        int d;
        this.filename = filename2;
        boolean changed = false;
        byte[] buf = new byte[2000];
        int len3 = 0;
        int lineStart = 0;
        int dataStart = -1;
        int cmdLine = 0;
        this.lineno = 1;
        int commentAt = -1;
        int curIndent = 0;
        int nesting = 0;
        int skipNesting = 0;
        String cmd = null;
        int changedLine = 0;
        while (true) {
            int c = in.read();
            if (c < 0) {
                break;
            }
            if (len3 + 10 >= buf.length) {
                byte[] nbuf = new byte[(len3 * 2)];
                System.arraycopy(buf, 0, nbuf, 0, len3);
                buf = nbuf;
            }
            if (c == 10 && len3 > 0 && buf[len3 - 1] == 13) {
                int len4 = len3 + 1;
                buf[len3] = (byte) c;
                len3 = len4;
            } else {
                if (commentAt >= 0 && dataStart < 0 && changedLine <= 0 && c != 13 && c != 10 && (commentAt == curIndent || !(c == 32 || c == 9))) {
                    if (c == 47) {
                        in.mark(100);
                        int d2 = in.read();
                        if (d2 == 47) {
                            doComment = false;
                        } else if (d2 == 42) {
                            while (true) {
                                d = in.read();
                                if (d != 32 && d != 9) {
                                    break;
                                }
                            }
                            doComment = d != 35;
                        } else {
                            doComment = true;
                        }
                        in.reset();
                    } else {
                        doComment = true;
                    }
                    if (doComment) {
                        int len5 = len3 + 1;
                        buf[len3] = Opcode.INIT_BYTES;
                        int len6 = len5 + 1;
                        buf[len5] = Opcode.INIT_BYTES;
                        len = len6 + 1;
                        buf[len6] = 32;
                        changedLine = 1;
                        changed = true;
                        if (!(c == 32 || c == 9 || dataStart >= 0)) {
                            dataStart = len;
                            if (nesting > 0 && commentAt != curIndent && c == 47) {
                                c = in.read();
                                if (c >= 0) {
                                    len3 = len;
                                    break;
                                }
                                if (c != 47) {
                                    len2 = len + 1;
                                    buf[len] = Opcode.INIT_BYTES;
                                } else {
                                    c = in.read();
                                    if (c < 0) {
                                        len3 = len;
                                        break;
                                    }
                                    changedLine = -1;
                                    changed = true;
                                    if (c == 32) {
                                        c = in.read();
                                        if (c == 32 || c == 9) {
                                            dataStart = -1;
                                            len2 = len;
                                        }
                                    }
                                }
                                buf[len2] = (byte) c;
                                len3 = len2 + 1;
                                if (c != 13 || c == 10) {
                                    firstNonSpace = -1;
                                    lastNonSpace = 0;
                                    for (i = lineStart; i < len3 - 1; i++) {
                                        if (!(buf[i] == 32 || buf[i] == 9)) {
                                            lastNonSpace = i;
                                            if (firstNonSpace < 0) {
                                                firstNonSpace = i;
                                            }
                                        }
                                    }
                                    if (lastNonSpace - firstNonSpace >= 4 && buf[firstNonSpace] == 47 && buf[firstNonSpace + 1] == 42 && buf[lastNonSpace - 1] == 42 && buf[lastNonSpace] == 47) {
                                        firstNonSpace2 = firstNonSpace + 2;
                                        while (firstNonSpace2 < lastNonSpace && buf[firstNonSpace2] == 32) {
                                            firstNonSpace2++;
                                        }
                                        lastNonSpace2 = lastNonSpace - 2;
                                        while (lastNonSpace2 > firstNonSpace2 && buf[lastNonSpace2] == 32) {
                                            lastNonSpace2--;
                                        }
                                        if (buf[firstNonSpace2] == 35) {
                                            String cmnt = new String(buf, firstNonSpace2, (lastNonSpace2 - firstNonSpace2) + 1, "ISO-8859-1");
                                            int sp = cmnt.indexOf(32);
                                            cmdLine = this.lineno;
                                            if (sp > 0) {
                                                cmd = cmnt.substring(0, sp);
                                                rest = cmnt.substring(sp).trim();
                                                obj = this.keywords.get(rest);
                                            } else {
                                                cmd = cmnt;
                                                rest = "";
                                                obj = null;
                                            }
                                            if ("#ifdef".equals(cmd) || "#ifndef".equals(cmd)) {
                                                if (obj == null) {
                                                    System.err.println(filename2 + ":" + this.lineno + ": warning - undefined keyword: " + rest);
                                                    obj = Boolean.FALSE;
                                                }
                                                nesting++;
                                                if (skipNesting > 0) {
                                                    commentAt = curIndent;
                                                } else {
                                                    if ((cmd.charAt(3) == 'n') != (obj == Boolean.FALSE)) {
                                                        commentAt = curIndent;
                                                        skipNesting = nesting;
                                                    }
                                                }
                                            } else if ("#else".equals(cmd)) {
                                                if (nesting == 0) {
                                                    error("unexpected " + cmd);
                                                } else if (nesting == skipNesting) {
                                                    commentAt = -1;
                                                    skipNesting = 0;
                                                } else {
                                                    commentAt = curIndent;
                                                    if (skipNesting == 0) {
                                                        skipNesting = nesting;
                                                    }
                                                }
                                            } else if ("#endif".equals(cmd)) {
                                                if (nesting == 0) {
                                                    error("unexpected " + cmd);
                                                }
                                                if (nesting == skipNesting) {
                                                    skipNesting = 0;
                                                    commentAt = -1;
                                                } else if (skipNesting > 0) {
                                                    commentAt = curIndent;
                                                }
                                                nesting--;
                                            } else {
                                                error("unknown command: " + cmnt);
                                            }
                                        }
                                    }
                                    lineStart = len3;
                                    dataStart = -1;
                                    curIndent = 0;
                                    this.lineno++;
                                    changedLine = 0;
                                } else if (dataStart < 0) {
                                    curIndent = c == 9 ? (curIndent + 8) & -8 : curIndent + 1;
                                }
                            }
                        }
                        len2 = len;
                        buf[len2] = (byte) c;
                        len3 = len2 + 1;
                        if (c != 13) {
                        }
                        firstNonSpace = -1;
                        lastNonSpace = 0;
                        while (i < len3 - 1) {
                        }
                        firstNonSpace2 = firstNonSpace + 2;
                        while (firstNonSpace2 < lastNonSpace) {
                            firstNonSpace2++;
                        }
                        lastNonSpace2 = lastNonSpace - 2;
                        while (lastNonSpace2 > firstNonSpace2) {
                            lastNonSpace2--;
                        }
                        if (buf[firstNonSpace2] == 35) {
                        }
                        lineStart = len3;
                        dataStart = -1;
                        curIndent = 0;
                        this.lineno++;
                        changedLine = 0;
                    }
                }
                len = len3;
                dataStart = len;
                c = in.read();
                if (c >= 0) {
                }
            }
        }
        if (nesting != 0) {
            this.lineno = cmdLine;
            error("unterminated " + cmd);
        }
        this.resultBuffer = buf;
        this.resultLength = len3;
        return changed;
    }

    /* access modifiers changed from: 0000 */
    public void handleArg(String arg) {
        int i = 1;
        if (arg.charAt(0) == '%') {
            String arg2 = arg.substring(1);
            int i2 = 0;
            while (true) {
                if (i2 >= version_features.length) {
                    System.err.println("Unknown version: " + arg2);
                    System.exit(-1);
                }
                if (arg2.equals(version_features[i2])) {
                    break;
                }
                i2 += 2;
            }
            String features = version_features[i2 + 1];
            System.err.println("(variant " + arg2 + " maps to: " + features + ")");
            StringTokenizer tokenizer = new StringTokenizer(features);
            while (tokenizer.hasMoreTokens()) {
                handleArg(tokenizer.nextToken());
            }
        } else if (arg.charAt(0) == '+') {
            this.keywords.put(arg.substring(1), Boolean.TRUE);
        } else if (arg.charAt(0) == '-') {
            int eq = arg.indexOf(61);
            if (eq > 1) {
                if (arg.charAt(1) == '-') {
                    i = 2;
                }
                String keyword = arg.substring(i, eq);
                String value = arg.substring(eq + 1);
                Boolean b = Boolean.FALSE;
                if (value.equalsIgnoreCase("true")) {
                    b = Boolean.TRUE;
                } else if (!value.equalsIgnoreCase("false")) {
                    System.err.println("invalid value " + value + " for " + keyword);
                    System.exit(-1);
                }
                this.keywords.put(keyword, b);
                return;
            }
            this.keywords.put(arg.substring(1), Boolean.FALSE);
        } else {
            try {
                filter(arg);
            } catch (Throwable ex) {
                System.err.println("caught " + ex);
                ex.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public static void main(String[] args) {
        PreProcess pp = new PreProcess();
        pp.keywords.put("true", Boolean.TRUE);
        pp.keywords.put("false", Boolean.FALSE);
        for (String handleArg : args) {
            pp.handleArg(handleArg);
        }
    }
}
