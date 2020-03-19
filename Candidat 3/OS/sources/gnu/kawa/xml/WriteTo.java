package gnu.kawa.xml;

import gnu.mapping.OutPort;
import gnu.mapping.Procedure2;
import gnu.mapping.Values;
import gnu.text.Path;
import gnu.xml.XMLPrinter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class WriteTo extends Procedure2 {
    public static final WriteTo writeTo = new WriteTo();
    public static final WriteTo writeToIfChanged = new WriteTo();
    boolean ifChanged;

    static {
        writeToIfChanged.ifChanged = true;
    }

    public static void writeTo(Object value, Path ppath, OutputStream outs) throws Throwable {
        OutPort out = new OutPort(outs, ppath);
        XMLPrinter consumer = new XMLPrinter(out, false);
        if ("html".equals(ppath.getExtension())) {
            consumer.setStyle("html");
        }
        Values.writeValues(value, consumer);
        out.close();
    }

    public static void writeTo(Object value, Object path) throws Throwable {
        Path ppath = Path.valueOf(path);
        writeTo(value, ppath, ppath.openOutputStream());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        r7.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0025, code lost:
        if (r0 != false) goto L_0x003d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0027, code lost:
        r5 = r6;
     */
    public static void writeToIfChanged(Object value, Object path) throws Throwable {
        Path ppath = Path.valueOf(path);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        writeTo(value, ppath, bout);
        byte[] bbuf = bout.toByteArray();
        try {
            InputStream ins = new BufferedInputStream(ppath.openInputStream());
            int i = 0;
            while (true) {
                int i2 = i;
                int b = ins.read();
                boolean atend = i2 == bbuf.length;
                if (b >= 0) {
                    if (atend) {
                        int i3 = i2;
                        break;
                    }
                    i = i2 + 1;
                    if (bbuf[i2] != b) {
                        break;
                    }
                } else {
                    break;
                }
            }
            ins.close();
        } catch (Throwable th) {
        }
        OutputStream fout = new BufferedOutputStream(ppath.openOutputStream());
        fout.write(bbuf);
        fout.close();
    }

    public Object apply2(Object value, Object fileName) throws Throwable {
        if (this.ifChanged) {
            writeToIfChanged(value, fileName.toString());
        } else {
            writeTo(value, fileName.toString());
        }
        return Values.empty;
    }
}
