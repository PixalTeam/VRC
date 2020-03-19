package gnu.kawa.functions;

import gnu.lists.FString;
import gnu.mapping.CharArrayInPort;
import gnu.mapping.InPort;
import gnu.mapping.Procedure1;
import gnu.text.CompoundFormat;
import gnu.text.LineBufferedReader;
import gnu.text.LiteralFormat;
import gnu.text.PadFormat;
import gnu.text.ReportFormat;
import java.io.IOException;
import java.text.Format;
import java.text.ParseException;
import java.util.Vector;

public class ParseFormat extends Procedure1 {
    public static final int PARAM_FROM_LIST = -1610612736;
    public static final int PARAM_UNSPECIFIED = -1073741824;
    public static final int SEEN_HASH = 16;
    public static final int SEEN_MINUS = 1;
    public static final int SEEN_PLUS = 2;
    public static final int SEEN_SPACE = 4;
    public static final int SEEN_ZERO = 8;
    public static final ParseFormat parseFormat = new ParseFormat(false);
    boolean emacsStyle = true;

    public ParseFormat(boolean emacsStyle2) {
        this.emacsStyle = emacsStyle2;
    }

    public ReportFormat parseFormat(LineBufferedReader fmt) throws ParseException, IOException {
        return parseFormat(fmt, this.emacsStyle ? '?' : '~');
    }

    /* JADX WARNING: type inference failed for: r23v0 */
    /* JADX WARNING: type inference failed for: r22v1, types: [java.text.Format] */
    /* JADX WARNING: type inference failed for: r23v1 */
    /* JADX WARNING: type inference failed for: r22v2, types: [java.text.Format] */
    /* JADX WARNING: type inference failed for: r23v2 */
    /* JADX WARNING: type inference failed for: r23v3 */
    /* JADX WARNING: type inference failed for: r22v4 */
    /* JADX WARNING: type inference failed for: r1v1, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r1v2, types: [java.text.Format] */
    /* JADX WARNING: type inference failed for: r23v4 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 5 */
    public static ReportFormat parseFormat(LineBufferedReader fmt, char magic) throws ParseException, IOException {
        ? r23;
        int base;
        PadFormat padFormat;
        int where;
        StringBuffer stringBuffer = new StringBuffer(100);
        int position = 0;
        Vector formats = new Vector();
        while (true) {
            int ch = fmt.read();
            if (ch >= 0) {
                if (ch != magic) {
                    stringBuffer.append((char) ch);
                } else {
                    ch = fmt.read();
                    if (ch == magic) {
                        stringBuffer.append((char) ch);
                    }
                }
            }
            int len = stringBuffer.length();
            if (len > 0) {
                char[] text = new char[len];
                stringBuffer.getChars(0, len, text, 0);
                stringBuffer.setLength(0);
                formats.addElement(new LiteralFormat(text));
            }
            if (ch < 0) {
                int fcount = formats.size();
                if (fcount == 1) {
                    Object f = formats.elementAt(0);
                    if (f instanceof ReportFormat) {
                        return (ReportFormat) f;
                    }
                }
                Format[] farray = new Format[fcount];
                formats.copyInto(farray);
                return new CompoundFormat(farray);
            }
            if (ch == 36) {
                int position2 = Character.digit((char) fmt.read(), 10);
                if (position2 < 0) {
                    throw new ParseException("missing number (position) after '%$'", -1);
                }
                while (true) {
                    ch = fmt.read();
                    int digit = Character.digit((char) ch, 10);
                    if (digit < 0) {
                        position = position2 - 1;
                    } else {
                        position2 = (position2 * 10) + digit;
                    }
                }
            }
            int flags = 0;
            while (true) {
                switch ((char) ch) {
                    case ' ':
                        flags |= 4;
                        break;
                    case '#':
                        flags |= 16;
                        break;
                    case '+':
                        flags |= 2;
                        break;
                    case '-':
                        flags |= 1;
                        break;
                    case '0':
                        flags |= 8;
                        break;
                    default:
                        int width = -1073741824;
                        int digit2 = Character.digit((char) ch, 10);
                        if (digit2 >= 0) {
                            width = digit2;
                            while (true) {
                                ch = fmt.read();
                                int digit3 = Character.digit((char) ch, 10);
                                if (digit3 >= 0) {
                                    width = (width * 10) + digit3;
                                }
                            }
                        } else if (ch == 42) {
                            width = -1610612736;
                        }
                        int precision = -1073741824;
                        if (ch == 46) {
                            if (ch == 42) {
                                precision = -1610612736;
                            } else {
                                precision = 0;
                                while (true) {
                                    ch = fmt.read();
                                    int digit4 = Character.digit((char) ch, 10);
                                    if (digit4 >= 0) {
                                        precision = (precision * 10) + digit4;
                                    }
                                }
                            }
                        }
                        switch (ch) {
                            case 83:
                            case 115:
                                ObjectFormat objectFormat = new ObjectFormat(ch == 83, precision);
                                r23 = objectFormat;
                                break;
                            case 88:
                            case 100:
                            case 105:
                            case 111:
                            case 120:
                                int fflags = 0;
                                if (ch == 100 || ch == 105) {
                                    base = 10;
                                } else if (ch == 111) {
                                    base = 8;
                                } else {
                                    base = 16;
                                    if (ch == 88) {
                                        fflags = 32;
                                    }
                                }
                                char padChar = (flags & 9) == 8 ? '0' : ' ';
                                if ((flags & 16) != 0) {
                                    fflags |= 8;
                                }
                                if ((flags & 2) != 0) {
                                    fflags |= 2;
                                }
                                if ((flags & 1) != 0) {
                                    fflags |= 16;
                                }
                                if ((flags & 4) != 0) {
                                    fflags |= 4;
                                }
                                if (precision == -1073741824) {
                                    r23 = IntegerFormat.getInstance(base, width, padChar, -1073741824, -1073741824, fflags);
                                    break;
                                } else {
                                    flags &= -9;
                                    r23 = IntegerFormat.getInstance(base, precision, 48, -1073741824, -1073741824, fflags | 64);
                                    break;
                                }
                                break;
                            case 101:
                            case 102:
                            case 103:
                                ObjectFormat objectFormat2 = new ObjectFormat(false);
                                r23 = objectFormat2;
                                break;
                            default:
                                throw new ParseException("unknown format character '" + ch + "'", -1);
                        }
                        if (width > 0) {
                            char padChar2 = (flags & 8) != 0 ? '0' : ' ';
                            if ((flags & 1) != 0) {
                                where = 100;
                            } else if (padChar2 == '0') {
                                where = -1;
                            } else {
                                where = 0;
                            }
                            PadFormat padFormat2 = new PadFormat(r23, width, padChar2, where);
                            padFormat = padFormat2;
                        } else {
                            padFormat = r23;
                        }
                        formats.addElement(padFormat);
                        position++;
                        continue;
                }
                ch = fmt.read();
            }
        }
    }

    public Object apply1(Object arg) {
        return asFormat(arg, this.emacsStyle ? '?' : '~');
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    public static ReportFormat asFormat(Object arg, char style) {
        InPort iport;
        try {
            if (arg instanceof ReportFormat) {
                return (ReportFormat) arg;
            }
            if (style == '~') {
                return new LispFormat(arg.toString());
            }
            if (arg instanceof FString) {
                FString str = (FString) arg;
                iport = new CharArrayInPort(str.data, str.size);
            } else {
                iport = new CharArrayInPort(arg.toString());
            }
            ReportFormat parseFormat2 = parseFormat(iport, style);
            iport.close();
            return parseFormat2;
        } catch (IOException ex) {
            throw new RuntimeException("Error parsing format (" + ex + ")");
        } catch (ParseException ex2) {
            throw new RuntimeException("Invalid format (" + ex2 + ")");
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("End while parsing format");
        } catch (Throwable th) {
            iport.close();
            throw th;
        }
    }
}
