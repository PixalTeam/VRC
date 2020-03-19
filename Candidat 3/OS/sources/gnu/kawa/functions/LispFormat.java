package gnu.kawa.functions;

import gnu.bytecode.Access;
import gnu.lists.Pair;
import gnu.lists.Sequence;
import gnu.math.IntNum;
import gnu.text.CaseConvertFormat;
import gnu.text.Char;
import gnu.text.CompoundFormat;
import gnu.text.FlushFormat;
import gnu.text.LiteralFormat;
import gnu.text.ReportFormat;
import java.text.Format;
import java.text.ParseException;
import java.util.Stack;
import java.util.Vector;

public class LispFormat extends CompoundFormat {
    public static final String paramFromCount = "<from count>";
    public static final String paramFromList = "<from list>";
    public static final String paramUnspecified = "<unspecified>";

    /* JADX WARNING: type inference failed for: r10v0, types: [gnu.kawa.functions.LispNewlineFormat] */
    /* JADX WARNING: type inference failed for: r10v1, types: [gnu.kawa.functions.LispCharacterFormat] */
    /* JADX WARNING: type inference failed for: r10v2, types: [gnu.kawa.functions.LispNewlineFormat] */
    /* JADX WARNING: type inference failed for: r10v3, types: [gnu.kawa.functions.LispIndentFormat] */
    /* JADX WARNING: type inference failed for: r10v4, types: [gnu.kawa.functions.LispFreshlineFormat] */
    /* JADX WARNING: type inference failed for: r10v5, types: [gnu.kawa.functions.LispTabulateFormat] */
    /* JADX WARNING: type inference failed for: r10v6, types: [gnu.text.FlushFormat] */
    /* JADX WARNING: type inference failed for: r10v7, types: [gnu.kawa.functions.LispEscapeFormat] */
    /* JADX WARNING: type inference failed for: r31v3, types: [gnu.kawa.functions.LispIterationFormat] */
    /* JADX WARNING: type inference failed for: r10v12 */
    /* JADX WARNING: type inference failed for: r10v13, types: [gnu.kawa.functions.LispRepositionFormat] */
    /* JADX WARNING: type inference failed for: r10v14, types: [gnu.kawa.functions.LispCharacterFormat] */
    /* JADX WARNING: type inference failed for: r10v15 */
    /* JADX WARNING: type inference failed for: r10v16, types: [gnu.kawa.functions.LispObjectFormat] */
    /* JADX WARNING: type inference failed for: r25v0, types: [gnu.kawa.functions.LispRealFormat] */
    /* JADX WARNING: type inference failed for: r10v17 */
    /* JADX WARNING: type inference failed for: r10v18, types: [java.text.Format] */
    /* JADX WARNING: type inference failed for: r10v19, types: [gnu.kawa.functions.LispPluralFormat] */
    /* JADX WARNING: type inference failed for: r10v21, types: [java.text.Format] */
    /* JADX WARNING: type inference failed for: r10v22 */
    /* JADX WARNING: type inference failed for: r10v23 */
    /* JADX WARNING: type inference failed for: r10v24 */
    /* JADX WARNING: type inference failed for: r10v25 */
    /* JADX WARNING: type inference failed for: r10v26 */
    /* JADX WARNING: type inference failed for: r10v27 */
    /* JADX WARNING: type inference failed for: r10v28 */
    /* JADX WARNING: type inference failed for: r10v29 */
    /* JADX WARNING: type inference failed for: r10v30 */
    /* JADX WARNING: type inference failed for: r10v31 */
    /* JADX WARNING: type inference failed for: r10v32 */
    /* JADX WARNING: type inference failed for: r10v33 */
    /* JADX WARNING: type inference failed for: r10v34 */
    /* JADX WARNING: type inference failed for: r10v35 */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x0344, code lost:
        throw new java.text.ParseException("saw ~) without matching ~(", r29);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x03d2, code lost:
        throw new java.text.ParseException("saw ~} without matching ~{", r29);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x0466, code lost:
        throw new java.text.ParseException("saw ~> without matching ~<", r29);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:202:0x0618, code lost:
        throw new java.text.ParseException("saw ~] without matching ~[", r29);
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 16 */
    public LispFormat(char[] format, int offset, int length) throws ParseException {
        int i;
        IntNum valueOf;
        int i2;
        int kind;
        Object obj;
        int base;
        super(null, 0);
        int start_nesting = -1;
        int choices_seen = 0;
        StringBuffer stringBuffer = new StringBuffer(100);
        Stack stack = new Stack();
        int limit = offset + length;
        int i3 = offset;
        while (true) {
            if ((i3 >= limit || format[i3] == '~') && stringBuffer.length() > 0) {
                stack.push(new LiteralFormat(stringBuffer));
                stringBuffer.setLength(0);
            }
            if (i3 < limit) {
                int i4 = i3 + 1;
                char ch = format[i3];
                if (ch != '~') {
                    stringBuffer.append(ch);
                    i3 = i4;
                } else {
                    int speci = stack.size();
                    int i5 = i4 + 1;
                    char ch2 = format[i4];
                    while (true) {
                        if (ch2 == '#') {
                            stack.push(paramFromCount);
                            int i6 = i5 + 1;
                            ch2 = format[i5];
                            i5 = i6;
                        } else if (ch2 == 'v' || ch2 == 'V') {
                            stack.push(paramFromList);
                            int i7 = i5 + 1;
                            ch2 = format[i5];
                            i5 = i7;
                        } else if (ch2 == '-' || Character.digit(ch2, 10) >= 0) {
                            boolean neg = ch2 == '-';
                            if (neg) {
                                i = i5 + 1;
                                ch2 = format[i5];
                            } else {
                                i = i5;
                            }
                            int val = 0;
                            int start = i;
                            while (true) {
                                int dig = Character.digit(ch2, 10);
                                if (dig < 0) {
                                    if (i - start < 8) {
                                        if (neg) {
                                            val = -val;
                                        }
                                        valueOf = IntNum.make(val);
                                    } else {
                                        valueOf = IntNum.valueOf(format, start, i - start, 10, neg);
                                    }
                                    stack.push(valueOf);
                                    i5 = i;
                                } else {
                                    val = (val * 10) + dig;
                                    int i8 = i + 1;
                                    ch2 = format[i];
                                    i = i8;
                                }
                            }
                        } else if (ch2 == '\'') {
                            int i9 = i5 + 1;
                            stack.push(Char.make(format[i5]));
                            i5 = i9 + 1;
                            ch2 = format[i9];
                        } else if (ch2 == ',') {
                            stack.push(paramUnspecified);
                        } else {
                            i2 = i5;
                        }
                        if (ch2 != ',') {
                            i2 = i5;
                        } else {
                            int i10 = i5 + 1;
                            ch2 = format[i5];
                            i5 = i10;
                        }
                    }
                    boolean seenColon = false;
                    boolean seenAt = false;
                    while (true) {
                        i3 = i2;
                        if (ch2 == ':') {
                            seenColon = true;
                        } else if (ch2 == '@') {
                            seenAt = true;
                        } else {
                            char ch3 = Character.toUpperCase(ch2);
                            int numParams = stack.size() - speci;
                            switch (ch3) {
                                case 10:
                                    if (seenAt) {
                                        stringBuffer.append(ch3);
                                    }
                                    if (!seenColon) {
                                        while (true) {
                                            if (i3 >= limit) {
                                                break;
                                            } else {
                                                int i11 = i3 + 1;
                                                if (!Character.isWhitespace(format[i3])) {
                                                    i3 = i11 - 1;
                                                    break;
                                                } else {
                                                    i3 = i11;
                                                }
                                            }
                                        }
                                    } else {
                                        continue;
                                    }
                                case '!':
                                    obj = FlushFormat.getInstance();
                                    break;
                                case '$':
                                case 'E':
                                case 'F':
                                case 'G':
                                    ? lispRealFormat = new LispRealFormat();
                                    lispRealFormat.op = ch3;
                                    lispRealFormat.arg1 = getParam(stack, speci);
                                    lispRealFormat.arg2 = getParam(stack, speci + 1);
                                    lispRealFormat.arg3 = getParam(stack, speci + 2);
                                    lispRealFormat.arg4 = getParam(stack, speci + 3);
                                    if (ch3 != '$') {
                                        lispRealFormat.arg5 = getParam(stack, speci + 4);
                                        if (ch3 == 'E' || ch3 == 'G') {
                                            lispRealFormat.arg6 = getParam(stack, speci + 5);
                                            lispRealFormat.arg7 = getParam(stack, speci + 6);
                                        }
                                    }
                                    lispRealFormat.showPlus = seenAt;
                                    lispRealFormat.internalPad = seenColon;
                                    if (lispRealFormat.argsUsed != 0) {
                                        obj = lispRealFormat;
                                        break;
                                    } else {
                                        obj = lispRealFormat.resolve(null, 0);
                                        break;
                                    }
                                case '%':
                                    int count = getParam(stack, speci);
                                    if (count == -1073741824) {
                                        count = 1;
                                    }
                                    obj = LispNewlineFormat.getInstance(count, 76);
                                    break;
                                case '&':
                                    obj = new LispFreshlineFormat(getParam(stack, speci));
                                    break;
                                case '(':
                                    char ch4 = seenColon ? seenAt ? 'U' : Access.CLASS_CONTEXT : seenAt ? 'T' : 'L';
                                    CaseConvertFormat caseConvertFormat = new CaseConvertFormat(null, ch4);
                                    stack.setSize(speci);
                                    stack.push(caseConvertFormat);
                                    stack.push(IntNum.make(start_nesting));
                                    start_nesting = speci;
                                    continue;
                                case ')':
                                    if (start_nesting < 0 || !(stack.elementAt(start_nesting) instanceof CaseConvertFormat)) {
                                        break;
                                    } else {
                                        ((CaseConvertFormat) stack.elementAt(start_nesting)).setBaseFormat(popFormats(stack, start_nesting + 2, speci));
                                        start_nesting = ((IntNum) stack.pop()).intValue();
                                        continue;
                                    }
                                case '*':
                                    obj = new LispRepositionFormat(getParam(stack, speci), seenColon, seenAt);
                                    break;
                                case ';':
                                    if (start_nesting >= 0) {
                                        if (!(stack.elementAt(start_nesting) instanceof LispChoiceFormat)) {
                                            if (!(stack.elementAt(start_nesting) instanceof LispPrettyFormat)) {
                                                break;
                                            } else {
                                                LispPrettyFormat pfmt = (LispPrettyFormat) stack.elementAt(start_nesting);
                                                if (seenAt) {
                                                    pfmt.perLine = true;
                                                }
                                                stack.push(popFormats(stack, start_nesting + 3 + choices_seen, speci));
                                                choices_seen++;
                                                break;
                                            }
                                        } else {
                                            LispChoiceFormat afmt = (LispChoiceFormat) stack.elementAt(start_nesting);
                                            if (seenColon) {
                                                afmt.lastIsDefault = true;
                                            }
                                            stack.push(popFormats(stack, start_nesting + 3 + choices_seen, speci));
                                            choices_seen++;
                                            continue;
                                        }
                                    } else {
                                        break;
                                    }
                                case '<':
                                    LispPrettyFormat pfmt2 = new LispPrettyFormat();
                                    pfmt2.seenAt = seenAt;
                                    if (seenColon) {
                                        pfmt2.prefix = "(";
                                        pfmt2.suffix = ")";
                                    } else {
                                        pfmt2.prefix = "";
                                        pfmt2.suffix = "";
                                    }
                                    stack.setSize(speci);
                                    stack.push(pfmt2);
                                    stack.push(IntNum.make(start_nesting));
                                    stack.push(IntNum.make(choices_seen));
                                    start_nesting = speci;
                                    choices_seen = 0;
                                    continue;
                                case '>':
                                    if (start_nesting < 0 || !(stack.elementAt(start_nesting) instanceof LispPrettyFormat)) {
                                        break;
                                    } else {
                                        stack.push(popFormats(stack, start_nesting + 3 + choices_seen, speci));
                                        LispPrettyFormat pfmt3 = (LispPrettyFormat) stack.elementAt(start_nesting);
                                        pfmt3.segments = getFormats(stack, start_nesting + 3, stack.size());
                                        stack.setSize(start_nesting + 3);
                                        int start_nesting2 = ((IntNum) stack.pop()).intValue();
                                        start_nesting = ((IntNum) stack.pop()).intValue();
                                        if (seenColon) {
                                            int nsegments = pfmt3.segments.length;
                                            if (nsegments <= 3) {
                                                if (nsegments < 2) {
                                                    pfmt3.body = pfmt3.segments[0];
                                                } else if (!(pfmt3.segments[0] instanceof LiteralFormat)) {
                                                    throw new ParseException("prefix segment is not literal", i3);
                                                } else {
                                                    pfmt3.prefix = ((LiteralFormat) pfmt3.segments[0]).content();
                                                    pfmt3.body = pfmt3.segments[1];
                                                }
                                                if (nsegments >= 3) {
                                                    if (pfmt3.segments[2] instanceof LiteralFormat) {
                                                        pfmt3.suffix = ((LiteralFormat) pfmt3.segments[2]).content();
                                                        break;
                                                    } else {
                                                        throw new ParseException("suffix segment is not literal", i3);
                                                    }
                                                } else {
                                                    continue;
                                                }
                                            } else {
                                                throw new ParseException("too many segments in Logical Block format", i3);
                                            }
                                        } else {
                                            throw new ParseException("not implemented: justfication i.e. ~<...~>", i3);
                                        }
                                    }
                                    break;
                                case '?':
                                    ? lispIterationFormat = new LispIterationFormat();
                                    lispIterationFormat.seenAt = seenAt;
                                    lispIterationFormat.maxIterations = 1;
                                    lispIterationFormat.atLeastOnce = true;
                                    obj = lispIterationFormat;
                                    break;
                                case 'A':
                                case 'S':
                                case 'W':
                                case 'Y':
                                    ReportFormat instance = ObjectFormat.getInstance(ch3 != 'A');
                                    if (numParams <= 0) {
                                        obj = instance;
                                        break;
                                    } else {
                                        obj = new LispObjectFormat(instance, getParam(stack, speci), getParam(stack, speci + 1), getParam(stack, speci + 2), getParam(stack, speci + 3), seenAt ? 0 : 100);
                                        break;
                                    }
                                case 'B':
                                case 'D':
                                case 'O':
                                case 'R':
                                case 'X':
                                    int argstart = speci;
                                    if (ch3 == 'R') {
                                        int argstart2 = argstart + 1;
                                        base = getParam(stack, argstart);
                                        argstart = argstart2;
                                    } else if (ch3 == 'D') {
                                        base = 10;
                                    } else if (ch3 == 'O') {
                                        base = 8;
                                    } else if (ch3 == 'X') {
                                        base = 16;
                                    } else {
                                        base = 2;
                                    }
                                    int minWidth = getParam(stack, argstart);
                                    int padChar = getParam(stack, argstart + 1);
                                    int commaChar = getParam(stack, argstart + 2);
                                    int commaInterval = getParam(stack, argstart + 3);
                                    int flags = 0;
                                    if (seenColon) {
                                        flags = 0 | 1;
                                    }
                                    if (seenAt) {
                                        flags |= 2;
                                    }
                                    obj = IntegerFormat.getInstance(base, minWidth, padChar, commaChar, commaInterval, flags);
                                    break;
                                case 'C':
                                    obj = LispCharacterFormat.getInstance(numParams > 0 ? getParam(stack, speci) : -1610612736, 1, seenAt, seenColon);
                                    break;
                                case 'I':
                                    int param1 = getParam(stack, speci);
                                    if (param1 == -1073741824) {
                                        param1 = 0;
                                    }
                                    obj = LispIndentFormat.getInstance(param1, seenColon);
                                    break;
                                case 'P':
                                    obj = LispPluralFormat.getInstance(seenColon, seenAt);
                                    break;
                                case 'T':
                                    obj = new LispTabulateFormat(getParam(stack, speci), getParam(stack, speci + 1), getParam(stack, speci + 2), seenAt);
                                    break;
                                case '[':
                                    LispChoiceFormat afmt2 = new LispChoiceFormat();
                                    afmt2.param = getParam(stack, speci);
                                    if (afmt2.param == -1073741824) {
                                        afmt2.param = -1610612736;
                                    }
                                    if (seenColon) {
                                        afmt2.testBoolean = true;
                                    }
                                    if (seenAt) {
                                        afmt2.skipIfFalse = true;
                                    }
                                    stack.setSize(speci);
                                    stack.push(afmt2);
                                    stack.push(IntNum.make(start_nesting));
                                    stack.push(IntNum.make(choices_seen));
                                    start_nesting = speci;
                                    choices_seen = 0;
                                    continue;
                                case ']':
                                    if (start_nesting < 0 || !(stack.elementAt(start_nesting) instanceof LispChoiceFormat)) {
                                        break;
                                    } else {
                                        stack.push(popFormats(stack, start_nesting + 3 + choices_seen, speci));
                                        ((LispChoiceFormat) stack.elementAt(start_nesting)).choices = getFormats(stack, start_nesting + 3, stack.size());
                                        stack.setSize(start_nesting + 3);
                                        choices_seen = ((IntNum) stack.pop()).intValue();
                                        start_nesting = ((IntNum) stack.pop()).intValue();
                                        continue;
                                    }
                                    break;
                                case '^':
                                    obj = new LispEscapeFormat(getParam(stack, speci), getParam(stack, speci + 1), getParam(stack, speci + 2));
                                    break;
                                case '_':
                                    int param12 = getParam(stack, speci);
                                    if (param12 == -1073741824) {
                                        param12 = 1;
                                    }
                                    if (!seenColon || !seenAt) {
                                    }
                                    if (seenAt && seenColon) {
                                        kind = 82;
                                    } else if (seenAt) {
                                        kind = 77;
                                    } else if (seenColon) {
                                        kind = 70;
                                    } else {
                                        kind = 78;
                                    }
                                    obj = LispNewlineFormat.getInstance(param12, kind);
                                    break;
                                case '{':
                                    LispIterationFormat lfmt = new LispIterationFormat();
                                    lfmt.seenAt = seenAt;
                                    lfmt.seenColon = seenColon;
                                    lfmt.maxIterations = getParam(stack, speci);
                                    stack.setSize(speci);
                                    stack.push(lfmt);
                                    stack.push(IntNum.make(start_nesting));
                                    start_nesting = speci;
                                    continue;
                                case '|':
                                    break;
                                case '}':
                                    if (start_nesting < 0 || !(stack.elementAt(start_nesting) instanceof LispIterationFormat)) {
                                        break;
                                    } else {
                                        LispIterationFormat lfmt2 = (LispIterationFormat) stack.elementAt(start_nesting);
                                        lfmt2.atLeastOnce = seenColon;
                                        if (speci > start_nesting + 2) {
                                            lfmt2.body = popFormats(stack, start_nesting + 2, speci);
                                        }
                                        start_nesting = ((IntNum) stack.pop()).intValue();
                                        continue;
                                    }
                                case '~':
                                    if (numParams == 0) {
                                        stringBuffer.append(ch3);
                                        continue;
                                    }
                                    break;
                                default:
                                    throw new ParseException("unrecognized format specifier ~" + ch3, i3);
                            }
                            int count2 = getParam(stack, speci);
                            if (count2 == -1073741824) {
                                count2 = 1;
                            }
                            int charVal = getParam(stack, speci + 1);
                            if (charVal == -1073741824) {
                                charVal = ch3 == '|' ? 12 : 126;
                            }
                            obj = LispCharacterFormat.getInstance(charVal, count2, false, false);
                            stack.setSize(speci);
                            stack.push(obj);
                        }
                        i2 = i3 + 1;
                        ch2 = format[i3];
                    }
                }
            } else if (i3 > limit) {
                throw new IndexOutOfBoundsException();
            } else if (start_nesting >= 0) {
                throw new ParseException("missing ~] or ~}", i3);
            } else {
                this.length = stack.size();
                this.formats = new Format[this.length];
                stack.copyInto(this.formats);
                return;
            }
        }
        throw new ParseException("saw ~; without matching ~[ or ~<", i3);
    }

    static Format[] getFormats(Vector vector, int start, int end) {
        Format[] f = new Format[(end - start)];
        for (int i = start; i < end; i++) {
            f[i - start] = (Format) vector.elementAt(i);
        }
        return f;
    }

    static Format popFormats(Vector vector, int start, int end) {
        Format f;
        if (end == start + 1) {
            f = (Format) vector.elementAt(start);
        } else {
            f = new CompoundFormat(getFormats(vector, start, end));
        }
        vector.setSize(start);
        return f;
    }

    public LispFormat(String str) throws ParseException {
        this(str.toCharArray());
    }

    public LispFormat(char[] format) throws ParseException {
        this(format, 0, format.length);
    }

    public static int getParam(Vector vec, int index) {
        if (index >= vec.size()) {
            return -1073741824;
        }
        Object arg = vec.elementAt(index);
        if (arg == paramFromList) {
            return -1610612736;
        }
        if (arg == paramFromCount) {
            return ReportFormat.PARAM_FROM_COUNT;
        }
        if (arg != paramUnspecified) {
            return getParam(arg, -1073741824);
        }
        return -1073741824;
    }

    public static Object[] asArray(Object arg) {
        if (arg instanceof Object[]) {
            return (Object[]) arg;
        }
        if (!(arg instanceof Sequence)) {
            return null;
        }
        int count = ((Sequence) arg).size();
        Object[] arr = new Object[count];
        int i = 0;
        while (arg instanceof Pair) {
            Pair pair = (Pair) arg;
            int i2 = i + 1;
            arr[i] = pair.getCar();
            arg = pair.getCdr();
            i = i2;
        }
        if (i < count) {
            if (!(arg instanceof Sequence)) {
                return null;
            }
            int npairs = i;
            Sequence seq = (Sequence) arg;
            while (i < count) {
                arr[i] = seq.get(npairs + i);
                i++;
            }
        }
        return arr;
    }
}
