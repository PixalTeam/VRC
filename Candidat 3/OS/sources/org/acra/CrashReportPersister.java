package org.acra;

import android.content.Context;
import gnu.kawa.servlet.HttpRequestContext;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.Map.Entry;
import org.acra.collector.CrashReportData;

final class CrashReportPersister {
    private static final int CONTINUE = 3;
    private static final int IGNORE = 5;
    private static final int KEY_DONE = 4;
    private static final String LINE_SEPARATOR = "\n";
    private static final int NONE = 0;
    private static final int SLASH = 1;
    private static final int UNICODE = 2;
    private final Context context;

    CrashReportPersister(Context context2) {
        this.context = context2;
    }

    public CrashReportData load(String fileName) throws IOException {
        CrashReportData load;
        FileInputStream in = this.context.openFileInput(fileName);
        if (in == null) {
            throw new IllegalArgumentException("Invalid crash report fileName : " + fileName);
        }
        try {
            BufferedInputStream bis = new BufferedInputStream(in, 8192);
            bis.mark(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
            boolean isEbcdic = isEbcdic(bis);
            bis.reset();
            if (!isEbcdic) {
                load = load((Reader) new InputStreamReader(bis, "ISO8859-1"));
            } else {
                load = load((Reader) new InputStreamReader(bis));
                in.close();
            }
            return load;
        } finally {
            in.close();
        }
    }

    public void store(CrashReportData crashData, String fileName) throws IOException {
        OutputStream out = this.context.openFileOutput(fileName, 0);
        try {
            StringBuilder buffer = new StringBuilder(HttpRequestContext.HTTP_OK);
            OutputStreamWriter writer = new OutputStreamWriter(out, "ISO8859_1");
            for (Entry<ReportField, String> entry : crashData.entrySet()) {
                dumpString(buffer, ((ReportField) entry.getKey()).toString(), true);
                buffer.append('=');
                dumpString(buffer, (String) entry.getValue(), false);
                buffer.append(LINE_SEPARATOR);
                writer.write(buffer.toString());
                buffer.setLength(0);
            }
            writer.flush();
        } finally {
            out.close();
        }
    }

    private boolean isEbcdic(BufferedInputStream in) throws IOException {
        byte b;
        do {
            b = (byte) in.read();
            if (b == -1 || b == 35 || b == 10 || b == 61) {
                return false;
            }
        } while (b != 21);
        return true;
    }

    private synchronized CrashReportData load(Reader reader) throws IOException {
        CrashReportData crashData;
        int mode = 0;
        int unicode = 0;
        int count = 0;
        char[] buf = new char[40];
        int keyLength = -1;
        boolean firstChar = true;
        crashData = new CrashReportData();
        BufferedReader br = new BufferedReader(reader, 8192);
        int offset = 0;
        while (true) {
            int intVal = br.read();
            if (intVal != -1) {
                char nextChar = (char) intVal;
                if (offset == buf.length) {
                    char[] newBuf = new char[(buf.length * 2)];
                    System.arraycopy(buf, 0, newBuf, 0, offset);
                    buf = newBuf;
                }
                if (mode == 2) {
                    int digit = Character.digit(nextChar, 16);
                    if (digit >= 0) {
                        unicode = (unicode << 4) + digit;
                        count++;
                        if (count < 4) {
                        }
                    } else if (count <= 4) {
                        throw new IllegalArgumentException("luni.09");
                    }
                    mode = 0;
                    int offset2 = offset + 1;
                    buf[offset] = (char) unicode;
                    if (nextChar == 10 || nextChar == 133) {
                        offset = offset2;
                    } else {
                        offset = offset2;
                    }
                }
                if (mode != 1) {
                    switch (nextChar) {
                        case 10:
                            if (mode == 3) {
                                mode = 5;
                                continue;
                            }
                        case 13:
                        case 133:
                            mode = 0;
                            firstChar = true;
                            if (offset > 0 || (offset == 0 && keyLength == 0)) {
                                if (keyLength == -1) {
                                    keyLength = offset;
                                }
                                String str = new String(buf, 0, offset);
                                crashData.put(Enum.valueOf(ReportField.class, str.substring(0, keyLength)), str.substring(keyLength));
                            }
                            keyLength = -1;
                            offset = 0;
                            continue;
                        case '!':
                        case '#':
                            if (firstChar) {
                                while (true) {
                                    int intVal2 = br.read();
                                    if (intVal2 != -1) {
                                        char nextChar2 = (char) intVal2;
                                        if (!(nextChar2 == 13 || nextChar2 == 10)) {
                                            if (nextChar2 == 133) {
                                                break;
                                            }
                                        }
                                    } else {
                                        continue;
                                    }
                                }
                            }
                        case ':':
                        case '=':
                            if (keyLength == -1) {
                                mode = 0;
                                keyLength = offset;
                                continue;
                            }
                        case '\\':
                            if (mode == 4) {
                                keyLength = offset;
                            }
                            mode = 1;
                            continue;
                        default:
                            if (Character.isWhitespace(nextChar)) {
                                if (mode == 3) {
                                    mode = 5;
                                }
                                if (!(offset == 0 || offset == keyLength || mode == 5)) {
                                    if (keyLength == -1) {
                                        mode = 4;
                                        break;
                                    }
                                }
                            }
                            if (mode == 5 || mode == 3) {
                                mode = 0;
                                break;
                            }
                    }
                } else {
                    mode = 0;
                    switch (nextChar) {
                        case 10:
                        case 133:
                            mode = 5;
                            continue;
                        case 13:
                            mode = 3;
                            continue;
                        case 'b':
                            nextChar = 8;
                            break;
                        case 'f':
                            nextChar = 12;
                            break;
                        case 'n':
                            nextChar = 10;
                            break;
                        case 'r':
                            nextChar = 13;
                            break;
                        case 't':
                            nextChar = 9;
                            break;
                        case 'u':
                            mode = 2;
                            count = 0;
                            unicode = 0;
                            continue;
                    }
                }
                firstChar = false;
                if (mode == 4) {
                    keyLength = offset;
                    mode = 0;
                }
                int offset3 = offset + 1;
                buf[offset] = nextChar;
                offset = offset3;
            } else if (mode != 2 || count > 4) {
                if (keyLength == -1 && offset > 0) {
                    keyLength = offset;
                }
                if (keyLength >= 0) {
                    String str2 = new String(buf, 0, offset);
                    ReportField key = (ReportField) Enum.valueOf(ReportField.class, str2.substring(0, keyLength));
                    String value = str2.substring(keyLength);
                    if (mode == 1) {
                        value = value + "\u0000";
                    }
                    crashData.put(key, value);
                }
            } else {
                throw new IllegalArgumentException("luni.08");
            }
        }
        return crashData;
    }

    private void dumpString(StringBuilder buffer, String string, boolean key) {
        int i = 0;
        if (!key && 0 < string.length() && string.charAt(0) == ' ') {
            buffer.append("\\ ");
            i = 0 + 1;
        }
        while (i < string.length()) {
            char ch = string.charAt(i);
            switch (ch) {
                case 9:
                    buffer.append("\\t");
                    break;
                case 10:
                    buffer.append("\\n");
                    break;
                case 12:
                    buffer.append("\\f");
                    break;
                case 13:
                    buffer.append("\\r");
                    break;
                default:
                    if ("\\#!=:".indexOf(ch) >= 0 || (key && ch == ' ')) {
                        buffer.append('\\');
                    }
                    if (ch >= ' ' && ch <= '~') {
                        buffer.append(ch);
                        break;
                    } else {
                        String hex = Integer.toHexString(ch);
                        buffer.append("\\u");
                        for (int j = 0; j < 4 - hex.length(); j++) {
                            buffer.append("0");
                        }
                        buffer.append(hex);
                        break;
                    }
                    break;
            }
            i++;
        }
    }
}
