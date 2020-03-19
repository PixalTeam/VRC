package gnu.kawa.lispexpr;

import android.support.v4.internal.view.SupportMenu;
import gnu.bytecode.Access;
import gnu.expr.Keyword;
import gnu.expr.QuoteExp;
import gnu.expr.Special;
import gnu.kawa.util.GeneralHashTable;
import gnu.lists.F32Vector;
import gnu.lists.F64Vector;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.lists.S16Vector;
import gnu.lists.S32Vector;
import gnu.lists.S64Vector;
import gnu.lists.S8Vector;
import gnu.lists.Sequence;
import gnu.lists.SimpleVector;
import gnu.lists.U16Vector;
import gnu.lists.U32Vector;
import gnu.lists.U64Vector;
import gnu.lists.U8Vector;
import gnu.mapping.Environment;
import gnu.mapping.InPort;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.math.Complex;
import gnu.math.DComplex;
import gnu.math.IntNum;
import gnu.text.Char;
import gnu.text.Lexer;
import gnu.text.LineBufferedReader;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.IOException;

public class LispReader extends Lexer {
    static final int SCM_COMPLEX = 1;
    public static final int SCM_NUMBERS = 1;
    public static final char TOKEN_ESCAPE_CHAR = '￿';
    protected boolean seenEscapes;
    GeneralHashTable<Integer, Object> sharedStructureTable;

    public LispReader(LineBufferedReader port) {
        super(port);
    }

    public LispReader(LineBufferedReader port, SourceMessages messages) {
        super(port, messages);
    }

    public final void readNestedComment(char c1, char c2) throws IOException, SyntaxException {
        int commentNesting = 1;
        int startLine = this.port.getLineNumber();
        int startColumn = this.port.getColumnNumber();
        do {
            int c = read();
            if (c == 124) {
                c = read();
                if (c == c1) {
                    commentNesting--;
                }
            } else if (c == c1) {
                c = read();
                if (c == c2) {
                    commentNesting++;
                }
            }
            if (c < 0) {
                eofError("unexpected end-of-file in " + c1 + c2 + " comment starting here", startLine + 1, startColumn - 1);
                return;
            }
        } while (commentNesting > 0);
    }

    static char getReadCase() {
        try {
            char read_case = Environment.getCurrent().get("symbol-read-case", (Object) "P").toString().charAt(0);
            if (read_case == 'P') {
                return read_case;
            }
            if (read_case == 'u') {
                return 'U';
            }
            if (read_case == 'd' || read_case == 'l' || read_case == 'L') {
                return 'D';
            }
            if (read_case == 'i') {
                return Access.INNERCLASS_CONTEXT;
            }
            return read_case;
        } catch (Exception e) {
            return 'P';
        }
    }

    public Object readValues(int ch, ReadTable rtable) throws IOException, SyntaxException {
        return readValues(ch, rtable.lookup(ch), rtable);
    }

    public Object readValues(int ch, ReadTableEntry entry, ReadTable rtable) throws IOException, SyntaxException {
        int startPos = this.tokenBufferLength;
        this.seenEscapes = false;
        switch (entry.getKind()) {
            case 0:
                String err = "invalid character #\\" + ((char) ch);
                if (this.interactive) {
                    fatal(err);
                } else {
                    error(err);
                }
                return Values.empty;
            case 1:
                return Values.empty;
            case 5:
            case 6:
                return entry.read(this, ch, -1);
            default:
                return readAndHandleToken(ch, startPos, rtable);
        }
    }

    /* access modifiers changed from: protected */
    public Object readAndHandleToken(int ch, int startPos, ReadTable rtable) throws IOException, SyntaxException {
        int j;
        readToken(ch, getReadCase(), rtable);
        int endPos = this.tokenBufferLength;
        if (!this.seenEscapes) {
            Object value = parseNumber(this.tokenBuffer, startPos, endPos - startPos, 0, 0, 1);
            if (value != null && !(value instanceof String)) {
                return value;
            }
        }
        char readCase = getReadCase();
        if (readCase == 'I') {
            int upperCount = 0;
            int lowerCount = 0;
            int i = startPos;
            while (i < endPos) {
                char ci = this.tokenBuffer[i];
                if (ci == 65535) {
                    i++;
                } else if (Character.isLowerCase(ci)) {
                    lowerCount++;
                } else if (Character.isUpperCase(ci)) {
                    upperCount++;
                }
                i++;
            }
            if (lowerCount == 0) {
                readCase = 'D';
            } else if (upperCount == 0) {
                readCase = 'U';
            } else {
                readCase = 'P';
            }
        }
        boolean handleUri = endPos >= startPos + 2 && this.tokenBuffer[endPos + -1] == '}' && this.tokenBuffer[endPos + -2] != 65535 && peek() == 58;
        int packageMarker = -1;
        int lbrace = -1;
        int rbrace = -1;
        int braceNesting = 0;
        int i2 = startPos;
        int j2 = startPos;
        while (i2 < endPos) {
            char ci2 = this.tokenBuffer[i2];
            if (ci2 == 65535) {
                i2++;
                if (i2 < endPos) {
                    j = j2 + 1;
                    this.tokenBuffer[j2] = this.tokenBuffer[i2];
                } else {
                    j = j2;
                }
            } else {
                if (handleUri) {
                    if (ci2 == '{') {
                        if (lbrace < 0) {
                            lbrace = j2;
                        } else if (braceNesting == 0) {
                        }
                        braceNesting++;
                    } else if (ci2 == '}') {
                        braceNesting--;
                        if (braceNesting >= 0) {
                            if (braceNesting == 0) {
                                if (rbrace < 0) {
                                    rbrace = j2;
                                }
                            }
                        }
                    }
                }
                if (braceNesting <= 0) {
                    if (ci2 == ':') {
                        packageMarker = packageMarker >= 0 ? -1 : j2;
                    } else if (readCase == 'U') {
                        ci2 = Character.toUpperCase(ci2);
                    } else if (readCase == 'D') {
                        ci2 = Character.toLowerCase(ci2);
                    }
                }
                j = j2 + 1;
                this.tokenBuffer[j2] = ci2;
            }
            i2++;
            j2 = j;
        }
        int endPos2 = j2;
        int len = endPos2 - startPos;
        if (lbrace >= 0 && rbrace > lbrace) {
            String prefix = lbrace > 0 ? new String(this.tokenBuffer, startPos, lbrace - startPos) : null;
            int lbrace2 = lbrace + 1;
            String str = new String(this.tokenBuffer, lbrace2, rbrace - lbrace2);
            int ch2 = read();
            int ch3 = read();
            Object rightOperand = readValues(ch3, rtable.lookup(ch3), rtable);
            if (!(rightOperand instanceof SimpleSymbol)) {
                error("expected identifier in symbol after '{URI}:'");
            }
            return Symbol.valueOf(rightOperand.toString(), str, prefix);
        } else if (rtable.initialColonIsKeyword && packageMarker == startPos && len > 1) {
            int startPos2 = startPos + 1;
            String str2 = new String(this.tokenBuffer, startPos2, endPos2 - startPos2);
            return Keyword.make(str2.intern());
        } else if (!rtable.finalColonIsKeyword || packageMarker != endPos2 - 1 || (len <= 1 && !this.seenEscapes)) {
            return rtable.makeSymbol(new String(this.tokenBuffer, startPos, len));
        } else {
            String str3 = new String(this.tokenBuffer, startPos, len - 1);
            return Keyword.make(str3.intern());
        }
    }

    /* access modifiers changed from: 0000 */
    public void readToken(int ch, char readCase, ReadTable rtable) throws IOException, SyntaxException {
        boolean inEscapes = false;
        int braceNesting = 0;
        while (true) {
            if (ch < 0) {
                if (inEscapes) {
                    eofError("unexpected EOF between escapes");
                } else {
                    return;
                }
            }
            ReadTableEntry entry = rtable.lookup(ch);
            int kind = entry.getKind();
            if (kind != 0) {
                if (ch == rtable.postfixLookupOperator && !inEscapes) {
                    int next = this.port.peek();
                    if (next == rtable.postfixLookupOperator) {
                        unread(ch);
                        return;
                    } else if (validPostfixLookupStart(next, rtable)) {
                        kind = 5;
                    }
                }
                if (kind == 3) {
                    int ch2 = read();
                    if (ch2 < 0) {
                        eofError("unexpected EOF after single escape");
                    }
                    if (rtable.hexEscapeAfterBackslash && (ch2 == 120 || ch2 == 88)) {
                        ch2 = readHexEscape();
                    }
                    tokenBufferAppend(SupportMenu.USER_MASK);
                    tokenBufferAppend(ch2);
                    this.seenEscapes = true;
                } else if (kind == 4) {
                    inEscapes = !inEscapes;
                    this.seenEscapes = true;
                } else if (inEscapes) {
                    tokenBufferAppend(SupportMenu.USER_MASK);
                    tokenBufferAppend(ch);
                } else {
                    switch (kind) {
                        case 1:
                            unread(ch);
                            return;
                        case 2:
                            if (ch == 123 && entry == ReadTableEntry.brace) {
                                braceNesting++;
                                break;
                            }
                        case 4:
                            inEscapes = true;
                            this.seenEscapes = true;
                            continue;
                        case 5:
                            unread(ch);
                            return;
                        case 6:
                            break;
                    }
                    tokenBufferAppend(ch);
                }
            } else if (inEscapes) {
                tokenBufferAppend(SupportMenu.USER_MASK);
                tokenBufferAppend(ch);
            } else if (ch == 125) {
                braceNesting--;
                if (braceNesting >= 0) {
                    tokenBufferAppend(ch);
                }
            }
            ch = read();
        }
        unread(ch);
    }

    public Object readObject() throws IOException, SyntaxException {
        int line;
        int column;
        Object value;
        char saveReadState = ((InPort) this.port).readState;
        int startPos = this.tokenBufferLength;
        ((InPort) this.port).readState = ' ';
        try {
            ReadTable rtable = ReadTable.getCurrent();
            do {
                line = this.port.getLineNumber();
                column = this.port.getColumnNumber();
                int ch = this.port.read();
                if (ch < 0) {
                    Object obj = Sequence.eofValue;
                    this.tokenBufferLength = startPos;
                    ((InPort) this.port).readState = saveReadState;
                    return obj;
                }
                value = readValues(ch, rtable);
            } while (value == Values.empty);
            Object handlePostfix = handlePostfix(value, rtable, line, column);
            this.tokenBufferLength = startPos;
            ((InPort) this.port).readState = saveReadState;
            return handlePostfix;
        } catch (Throwable th) {
            Throwable th2 = th;
            this.tokenBufferLength = startPos;
            ((InPort) this.port).readState = saveReadState;
            throw th2;
        }
    }

    /* access modifiers changed from: protected */
    public boolean validPostfixLookupStart(int ch, ReadTable rtable) throws IOException {
        if (ch < 0 || ch == 58 || ch == rtable.postfixLookupOperator) {
            return false;
        }
        if (ch == 44) {
            return true;
        }
        int kind = rtable.lookup(ch).getKind();
        if (kind == 2 || kind == 6 || kind == 4 || kind == 3) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public Object handlePostfix(Object value, ReadTable rtable, int line, int column) throws IOException, SyntaxException {
        if (value == QuoteExp.voidExp) {
            value = Values.empty;
        }
        while (true) {
            int ch = this.port.peek();
            if (ch < 0 || ch != rtable.postfixLookupOperator) {
                break;
            }
            this.port.read();
            if (!validPostfixLookupStart(this.port.peek(), rtable)) {
                unread();
                break;
            }
            int ch2 = this.port.read();
            value = PairWithPosition.make(LispLanguage.lookup_sym, LList.list2(value, LList.list2(rtable.makeSymbol(LispLanguage.quasiquote_sym), readValues(ch2, rtable.lookup(ch2), rtable))), this.port.getName(), line + 1, column + 1);
        }
        return value;
    }

    private boolean isPotentialNumber(char[] buffer, int start, int end) {
        boolean z = true;
        int sawDigits = 0;
        for (int i = start; i < end; i++) {
            char ch = buffer[i];
            if (Character.isDigit(ch)) {
                sawDigits++;
            } else if (ch == '-' || ch == '+') {
                if (i + 1 == end) {
                    return false;
                }
            } else if (ch == '#') {
                return true;
            } else {
                if (Character.isLetter(ch) || ch == '/' || ch == '_' || ch == '^') {
                    if (i == start) {
                        return false;
                    }
                } else if (ch != '.') {
                    return false;
                }
            }
        }
        if (sawDigits <= 0) {
            z = false;
        }
        return z;
    }

    public static Object parseNumber(CharSequence str, int radix) {
        char[] buf;
        if (str instanceof FString) {
            buf = ((FString) str).data;
        } else {
            buf = str.toString().toCharArray();
        }
        return parseNumber(buf, 0, str.length(), 0, radix, 1);
    }

    /* JADX WARNING: type inference failed for: r38v2, types: [gnu.math.RatNum] */
    /* JADX WARNING: type inference failed for: r39v0 */
    /* JADX WARNING: type inference failed for: r38v3, types: [gnu.math.RatNum] */
    /* JADX WARNING: type inference failed for: r38v4 */
    /* JADX WARNING: type inference failed for: r39v1 */
    /* JADX WARNING: type inference failed for: r39v2, types: [gnu.math.RealNum] */
    /* JADX WARNING: type inference failed for: r38v8 */
    /* JADX WARNING: type inference failed for: r39v3 */
    /* JADX WARNING: type inference failed for: r38v9, types: [gnu.math.RealNum] */
    /* JADX WARNING: type inference failed for: r38v10, types: [gnu.math.RealNum] */
    /* JADX WARNING: type inference failed for: r0v30 */
    /* JADX WARNING: type inference failed for: r4v11, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r0v37, types: [gnu.math.RealNum] */
    /* JADX WARNING: type inference failed for: r0v44, types: [gnu.math.RealNum] */
    /* JADX WARNING: type inference failed for: r0v46, types: [gnu.math.RealNum] */
    /* JADX WARNING: type inference failed for: r38v11, types: [gnu.math.RatNum] */
    /* JADX WARNING: type inference failed for: r38v14 */
    /* JADX INFO: used method not loaded: gnu.math.Complex.polar(gnu.math.RealNum, gnu.math.RealNum):null, types can be incorrect */
    /* JADX INFO: used method not loaded: gnu.math.Complex.make(gnu.math.RealNum, gnu.math.RealNum):null, types can be incorrect */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x01aa, code lost:
        r34 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x01ac, code lost:
        if (r6 >= 0) goto L_0x0511;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x01ae, code lost:
        if (r50 == false) goto L_0x01de;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x01b4, code lost:
        if ((r13 + 4) >= r26) goto L_0x01de;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x01bc, code lost:
        if (r54[r13 + 3] != '.') goto L_0x01de;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x01c4, code lost:
        if (r54[r13 + 4] != '0') goto L_0x01de;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x01ca, code lost:
        if (r54[r13] != 'i') goto L_0x0296;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x01d2, code lost:
        if (r54[r13 + 1] != 'n') goto L_0x0296;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:0x01da, code lost:
        if (r54[r13 + 2] != 'f') goto L_0x0296;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x01dc, code lost:
        r34 = 'i';
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x01de, code lost:
        if (r34 != 0) goto L_0x02b0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:182:0x029a, code lost:
        if (r54[r13] != 'n') goto L_0x01de;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:184:0x02a2, code lost:
        if (r54[r13 + 1] != 'a') goto L_0x01de;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:186:0x02aa, code lost:
        if (r54[r13 + 2] != 'n') goto L_0x01de;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:187:0x02ac, code lost:
        r34 = 'n';
     */
    /* JADX WARNING: Code restructure failed: missing block: B:188:0x02b0, code lost:
        r43 = r13 + 5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:189:0x02b4, code lost:
        if (r30 != false) goto L_0x02b8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:190:0x02b6, code lost:
        if (r52 == false) goto L_0x02b8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:192:0x02bc, code lost:
        if (r57 == 'i') goto L_0x02cc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:194:0x02c2, code lost:
        if (r57 == 'I') goto L_0x02cc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:196:0x02c8, code lost:
        if (r57 != ' ') goto L_0x0328;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:197:0x02ca, code lost:
        if (r30 == false) goto L_0x0328;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:198:0x02cc, code lost:
        r33 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:199:0x02ce, code lost:
        r27 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:200:0x02d2, code lost:
        if (r34 == 0) goto L_0x032e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:202:0x02da, code lost:
        if (r34 != 'i') goto L_0x032b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:203:0x02dc, code lost:
        r22 = Double.POSITIVE_INFINITY;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:205:0x02e0, code lost:
        if (r9 == false) goto L_0x02e7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:206:0x02e2, code lost:
        r22 = -r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:207:0x02e7, code lost:
        r0 = new gnu.math.DFloNum(r22);
        r0 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:209:0x02f2, code lost:
        if (r57 == 'e') goto L_0x02fa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:211:0x02f8, code lost:
        if (r57 != 'E') goto L_0x02fe;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:212:0x02fa, code lost:
        r38 = r38.toExact();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:214:0x0302, code lost:
        if (r43 >= r26) goto L_0x04d7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:215:0x0304, code lost:
        r13 = r43 + 1;
        r18 = r54[r43];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:216:0x030c, code lost:
        if (r18 != '@') goto L_0x042c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:217:0x030e, code lost:
        r4 = parseNumber(r54, r13, r26 - r13, r57, 10, r59);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:218:0x031e, code lost:
        if ((r4 instanceof java.lang.String) != false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:220:0x0322, code lost:
        if ((r4 instanceof gnu.math.RealNum) != false) goto L_0x0409;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:222:0x0328, code lost:
        r33 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:223:0x032b, code lost:
        r22 = Double.NaN;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:224:0x032e, code lost:
        if (r29 >= 0) goto L_0x0332;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:225:0x0330, code lost:
        if (r21 < 0) goto L_0x039b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:227:0x0334, code lost:
        if (r6 <= r21) goto L_0x033a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:228:0x0336, code lost:
        if (r21 < 0) goto L_0x033a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:229:0x0338, code lost:
        r6 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:230:0x033a, code lost:
        if (r40 == null) goto L_0x0342;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:231:0x033c, code lost:
        r13 = r43;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:232:0x0342, code lost:
        r0 = new java.lang.String(r54, r6, r43 - r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:233:0x034d, code lost:
        if (r29 < 0) goto L_0x0385;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:234:0x034f, code lost:
        r27 = java.lang.Character.toLowerCase(r54[r29]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:235:0x0359, code lost:
        if (r27 == 'e') goto L_0x0385;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:236:0x035b, code lost:
        r44 = r29 - r6;
        r51 = r0.substring(0, r44) + 'e' + r0.substring(r44 + 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:237:0x0385, code lost:
        r22 = gnu.lists.Convert.parseDouble(r51);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:238:0x038b, code lost:
        if (r9 == false) goto L_0x0392;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:239:0x038d, code lost:
        r22 = -r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:240:0x0392, code lost:
        r0 = new gnu.math.DFloNum(r22);
        r0 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:241:0x039b, code lost:
        r35 = valueOf(r54, r6, r43 - r6, r58, r9, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:242:0x03a5, code lost:
        if (r40 != null) goto L_0x03c6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:243:0x03a7, code lost:
        r39 = r35;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:244:0x03ab, code lost:
        if (r33 == false) goto L_0x050d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:246:0x03b1, code lost:
        if (r39.isExact() == false) goto L_0x050d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:248:0x03b5, code lost:
        if (r41 == false) goto L_0x0404;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:250:0x03bb, code lost:
        if (r39.isZero() == false) goto L_0x0404;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:251:0x03bd, code lost:
        r14 = -0.0d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:252:0x03bf, code lost:
        r0 = new gnu.math.DFloNum(r14);
        r0 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:254:0x03ca, code lost:
        if (r35.isZero() == false) goto L_0x03f9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:255:0x03cc, code lost:
        r42 = r40.isZero();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:256:0x03d0, code lost:
        if (r33 == false) goto L_0x03e8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:258:0x03d4, code lost:
        if (r42 == false) goto L_0x03e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:259:0x03d6, code lost:
        r14 = Double.NaN;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:260:0x03d8, code lost:
        r0 = new gnu.math.DFloNum(r14);
        r0 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:261:0x03dd, code lost:
        r39 = r38;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:262:0x03e0, code lost:
        if (r41 == false) goto L_0x03e5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:263:0x03e2, code lost:
        r14 = Double.NEGATIVE_INFINITY;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:264:0x03e5, code lost:
        r14 = Double.POSITIVE_INFINITY;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:265:0x03e8, code lost:
        if (r42 == false) goto L_0x03f0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:266:0x03ea, code lost:
        r13 = r43;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:267:0x03f0, code lost:
        r38 = gnu.math.RatNum.make(r40, r35);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:268:0x03f9, code lost:
        r39 = gnu.math.RatNum.make(r40, r35);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:269:0x0404, code lost:
        r14 = r39.doubleValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:270:0x0409, code lost:
        r46 = (gnu.math.RealNum) r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:271:0x0411, code lost:
        if (r38.isZero() == false) goto L_0x0422;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:273:0x0417, code lost:
        if (r46.isExact() != false) goto L_0x0422;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:277:0x0430, code lost:
        if (r18 == '-') goto L_0x0438;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:279:0x0436, code lost:
        if (r18 != '+') goto L_0x0493;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:280:0x0438, code lost:
        r13 = r13 - 1;
        r32 = parseNumber(r54, r13, r26 - r13, r57, 10, r59);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:281:0x044c, code lost:
        if ((r32 instanceof java.lang.String) == false) goto L_0x0452;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:284:0x0456, code lost:
        if ((r32 instanceof gnu.math.Complex) != false) goto L_0x0475;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:286:0x0475, code lost:
        r19 = (gnu.math.Complex) r32;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:287:0x0481, code lost:
        if (r19.re().isZero() != false) goto L_0x0487;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:290:0x0493, code lost:
        r36 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:292:0x0499, code lost:
        if (java.lang.Character.isLetter(r18) != false) goto L_0x04ba;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:293:0x049b, code lost:
        r13 = r13 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:295:0x04a0, code lost:
        if (r36 != 1) goto L_0x04d3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:296:0x04a2, code lost:
        r45 = r54[r13 - 1];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:297:0x04aa, code lost:
        if (r45 == 'i') goto L_0x04b2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:299:0x04b0, code lost:
        if (r45 != 'I') goto L_0x04d3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:301:0x04b4, code lost:
        if (r13 >= r26) goto L_0x04c7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:303:0x04ba, code lost:
        r36 = r36 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:304:0x04be, code lost:
        if (r13 == r26) goto L_0x049d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:305:0x04c0, code lost:
        r43 = r13 + 1;
        r18 = r54[r13];
        r13 = r43;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:309:0x04db, code lost:
        if ((r38 instanceof gnu.math.DFloNum) == false) goto L_0x04ec;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:310:0x04dd, code lost:
        if (r27 <= 0) goto L_0x04ec;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:312:0x04e3, code lost:
        if (r27 == 'e') goto L_0x04ec;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:313:0x04e5, code lost:
        r22 = r38.doubleValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:314:0x04e9, code lost:
        switch(r27) {
            case 100: goto L_0x04fd;
            case 102: goto L_0x04f2;
            case 108: goto L_0x0505;
            case 115: goto L_0x04f2;
            default: goto L_0x04ec;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:315:0x04ec, code lost:
        r13 = r43;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:316:0x04f2, code lost:
        r13 = r43;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:317:0x04fd, code lost:
        r13 = r43;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:318:0x0505, code lost:
        r13 = r43;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:319:0x050d, code lost:
        r38 = r39;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:320:0x0511, code lost:
        r43 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:370:?, code lost:
        return "no digits";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:380:?, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:381:?, code lost:
        return "invalid complex polar constant";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:382:?, code lost:
        return "floating-point number after fraction symbol '/'";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:383:?, code lost:
        return "0/0 is undefined";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:384:?, code lost:
        return new gnu.math.DFloNum(0.0d);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:385:?, code lost:
        return gnu.math.Complex.polar((gnu.math.RealNum) r38, r46);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:386:?, code lost:
        return r32;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:387:?, code lost:
        return "invalid numeric constant (" + r32 + ")";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:388:?, code lost:
        return "invalid numeric constant";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:389:?, code lost:
        return gnu.math.Complex.make((gnu.math.RealNum) r38, r19.im());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:390:?, code lost:
        return "junk after imaginary suffix 'i'";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:391:?, code lost:
        return gnu.math.Complex.make((gnu.math.RealNum) gnu.math.IntNum.zero(), (gnu.math.RealNum) r38);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:392:?, code lost:
        return "excess junk after number";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:393:?, code lost:
        return r38;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:394:?, code lost:
        return java.lang.Float.valueOf((float) r22);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:395:?, code lost:
        return java.lang.Double.valueOf(r22);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:396:?, code lost:
        return java.math.BigDecimal.valueOf(r22);
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 9 */
    public static Object parseNumber(char[] buffer, int start, int count, char exactness, int radix, int flags) {
        int pos;
        int pos2;
        int end = start + count;
        int pos3 = start;
        if (pos3 >= end) {
            return "no digits";
        }
        int pos4 = pos3 + 1;
        char ch = buffer[pos3];
        while (ch == '#') {
            if (pos4 >= end) {
                int i = pos4;
                return "no digits";
            }
            int pos5 = pos4 + 1;
            char ch2 = buffer[pos4];
            switch (ch2) {
                case 'B':
                case 'b':
                    if (radix == 0) {
                        radix = 2;
                        pos2 = pos5;
                        break;
                    } else {
                        return "duplicate radix specifier";
                    }
                case 'D':
                case 'd':
                    if (radix == 0) {
                        radix = 10;
                        pos2 = pos5;
                        break;
                    } else {
                        return "duplicate radix specifier";
                    }
                case 'E':
                case 'I':
                case 'e':
                case 'i':
                    if (exactness == 0) {
                        exactness = ch2;
                        pos2 = pos5;
                        break;
                    } else if (exactness == ' ') {
                        return "non-prefix exactness specifier";
                    } else {
                        return "duplicate exactness specifier";
                    }
                case 'O':
                case 'o':
                    if (radix == 0) {
                        radix = 8;
                        pos2 = pos5;
                        break;
                    } else {
                        return "duplicate radix specifier";
                    }
                case 'X':
                case 'x':
                    if (radix == 0) {
                        radix = 16;
                        pos2 = pos5;
                        break;
                    } else {
                        return "duplicate radix specifier";
                    }
                default:
                    int value = 0;
                    while (true) {
                        int dig = Character.digit(ch2, 10);
                        if (dig >= 0) {
                            value = (value * 10) + dig;
                            if (pos5 >= end) {
                                return "missing letter after '#'";
                            }
                            int pos6 = pos5 + 1;
                            ch2 = buffer[pos5];
                            pos5 = pos6;
                        } else if (ch2 == 'R' || ch2 == 'r') {
                            if (radix == 0) {
                                if (value >= 2 && value <= 35) {
                                    radix = value;
                                    pos2 = pos5;
                                    break;
                                } else {
                                    return "invalid radix specifier";
                                }
                            } else {
                                return "duplicate radix specifier";
                            }
                        } else {
                            return "unknown modifier '#" + ch2 + '\'';
                        }
                    }
                    break;
            }
            if (pos2 >= end) {
                int i2 = pos2;
                return "no digits";
            }
            int pos7 = pos2 + 1;
            ch = buffer[pos2];
            pos4 = pos7;
        }
        if (exactness == 0) {
            exactness = ' ';
        }
        if (radix == 0) {
            int i3 = count;
            while (true) {
                i3--;
                if (i3 < 0) {
                    radix = 10;
                } else if (buffer[start + i3] == '.') {
                    radix = 10;
                }
            }
        }
        boolean negative = ch == '-';
        boolean numeratorNegative = negative;
        boolean sign_seen = ch == '-' || ch == '+';
        if (!sign_seen) {
            pos = pos4;
        } else if (pos4 >= end) {
            int i4 = pos4;
            return "no digits following sign";
        } else {
            pos = pos4 + 1;
            ch = buffer[pos4];
        }
        if ((ch == 'i' || ch == 'I') && pos == end && start == pos - 2 && (flags & 1) != 0) {
            char sign = buffer[start];
            if (sign != '+' && sign != '-') {
                return "no digits";
            }
            if (exactness == 'i' || exactness == 'I') {
                return new DComplex(0.0d, negative ? -1.0d : 1.0d);
            }
            return negative ? Complex.imMinusOne() : Complex.imOne();
        }
        int i5 = pos - 1;
        boolean hash_seen = false;
        int exp_seen = -1;
        int digits_start = -1;
        int decimal_point = -1;
        boolean underscore_seen = false;
        IntNum numerator = null;
        long lvalue = 0;
        while (true) {
            int digit = Character.digit(ch, radix);
            if (digit < 0) {
                switch (ch) {
                    case '.':
                        if (decimal_point < 0) {
                            if (radix == 10) {
                                decimal_point = pos - 1;
                                break;
                            } else {
                                return "'.' in non-decimal number";
                            }
                        } else {
                            return "duplicate '.' in number";
                        }
                    case '/':
                        if (numerator == null) {
                            if (digits_start >= 0) {
                                if (-1 < 0 && decimal_point < 0) {
                                    numerator = valueOf(buffer, digits_start, pos - digits_start, radix, negative, lvalue);
                                    digits_start = -1;
                                    lvalue = 0;
                                    negative = false;
                                    hash_seen = false;
                                    underscore_seen = false;
                                    break;
                                } else {
                                    break;
                                }
                            } else {
                                return "no digits before fraction symbol '/'";
                            }
                        } else {
                            return "multiple fraction symbol '/'";
                        }
                        break;
                    case 'D':
                    case 'E':
                    case 'F':
                    case 'L':
                    case 'S':
                    case 'd':
                    case 'e':
                    case 'f':
                    case 'l':
                    case 's':
                        if (pos != end && radix == 10) {
                            char next = buffer[pos];
                            int exp_pos = pos - 1;
                            if (next != '+' && next != '-') {
                                if (Character.digit(next, 10) < 0) {
                                    pos--;
                                    break;
                                }
                            } else {
                                pos++;
                                if (pos >= end || Character.digit(buffer[pos], 10) < 0) {
                                    return "missing exponent digits";
                                }
                            }
                            if (-1 < 0) {
                                if (radix == 10) {
                                    if (digits_start >= 0) {
                                        exp_seen = exp_pos;
                                        do {
                                            pos++;
                                            if (pos >= end) {
                                                break;
                                            }
                                        } while (Character.digit(buffer[pos], 10) >= 0);
                                        break;
                                    } else {
                                        return "mantissa with no digits";
                                    }
                                } else {
                                    return "exponent in non-decimal number";
                                }
                            } else {
                                return "duplicate exponent";
                            }
                        } else {
                            pos--;
                            break;
                        }
                        break;
                    default:
                        pos--;
                        break;
                }
            } else if (hash_seen && decimal_point < 0) {
                return "digit after '#' in number";
            } else {
                if (digits_start < 0) {
                    digits_start = pos - 1;
                }
                lvalue = (((long) radix) * lvalue) + ((long) digit);
            }
            if (pos != end) {
                int pos8 = pos + 1;
                ch = buffer[pos];
                pos = pos8;
            }
        }
        return "fraction symbol '/' following exponent or '.'";
    }

    private static IntNum valueOf(char[] buffer, int digits_start, int number_of_digits, int radix, boolean negative, long lvalue) {
        if (number_of_digits + radix > 28) {
            return IntNum.valueOf(buffer, digits_start, number_of_digits, radix, negative);
        }
        if (negative) {
            lvalue = -lvalue;
        }
        return IntNum.make(lvalue);
    }

    public int readEscape() throws IOException, SyntaxException {
        int c = read();
        if (c >= 0) {
            return readEscape(c);
        }
        eofError("unexpected EOF in character literal");
        return -1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0032, code lost:
        eofError("unexpected EOF in literal");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0097, code lost:
        r11 = read();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x009d, code lost:
        if (r11 != 92) goto L_0x00a3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x009f, code lost:
        r11 = readEscape();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00a3, code lost:
        if (r11 != 63) goto L_0x00a9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:?, code lost:
        return r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:?, code lost:
        return -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:?, code lost:
        return 127;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:?, code lost:
        return r11 & 159;
     */
    public final int readEscape(int c) throws IOException, SyntaxException {
        switch ((char) c) {
            case 9:
            case 10:
            case 13:
            case ' ':
                while (c >= 0) {
                    if (c != 10) {
                        if (c == 13) {
                            if (peek() == 10) {
                                skip();
                            }
                            c = 10;
                        } else if (c == 32 || c == 9) {
                            c = read();
                        } else {
                            unread(c);
                        }
                    }
                    if (c == 10) {
                        while (true) {
                            int c2 = read();
                            if (c2 < 0) {
                                eofError("unexpected EOF in literal");
                                return -1;
                            } else if (c2 != 32 && c2 != 9) {
                                unread(c2);
                                return -2;
                            }
                        }
                    }
                }
                break;
            case '\"':
                c = 34;
                break;
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
                c -= 48;
                int count = 0;
                while (true) {
                    count++;
                    if (count >= 3) {
                        break;
                    } else {
                        int d = read();
                        int v = Character.digit((char) d, 8);
                        if (v < 0) {
                            if (d >= 0) {
                                unread(d);
                                break;
                            }
                        } else {
                            c = (c << 3) + v;
                        }
                    }
                }
                break;
            case 'C':
                if (read() != 45) {
                    error("Invalid escape character syntax");
                    return 63;
                }
                break;
            case 'M':
                if (read() != 45) {
                    error("Invalid escape character syntax");
                    return 63;
                }
                int c3 = read();
                if (c3 == 92) {
                    c3 = readEscape();
                }
                return c3 | 128;
            case 'X':
            case 'x':
                return readHexEscape();
            case '\\':
                c = 92;
                break;
            case '^':
                break;
            case 'a':
                c = 7;
                break;
            case 'b':
                c = 8;
                break;
            case 'e':
                c = 27;
                break;
            case 'f':
                c = 12;
                break;
            case 'n':
                c = 10;
                break;
            case 'r':
                c = 13;
                break;
            case 't':
                c = 9;
                break;
            case 'u':
                c = 0;
                int i = 4;
                while (true) {
                    i--;
                    if (i < 0) {
                        break;
                    } else {
                        int d2 = read();
                        if (d2 < 0) {
                            eofError("premature EOF in \\u escape");
                        }
                        int v2 = Character.digit((char) d2, 16);
                        if (v2 < 0) {
                            error("non-hex character following \\u");
                        }
                        c = (c * 16) + v2;
                    }
                }
            case 'v':
                c = 11;
                break;
        }
    }

    public int readHexEscape() throws IOException, SyntaxException {
        int d;
        int c = 0;
        while (true) {
            d = read();
            int v = Character.digit((char) d, 16);
            if (v < 0) {
                break;
            }
            c = (c << 4) + v;
        }
        if (d != 59 && d >= 0) {
            unread(d);
        }
        return c;
    }

    public final Object readObject(int c) throws IOException, SyntaxException {
        unread(c);
        return readObject();
    }

    public Object readCommand() throws IOException, SyntaxException {
        return readObject();
    }

    /* access modifiers changed from: protected */
    public Object makeNil() {
        return LList.Empty;
    }

    /* access modifiers changed from: protected */
    public Pair makePair(Object car, int line, int column) {
        return makePair(car, LList.Empty, line, column);
    }

    /* access modifiers changed from: protected */
    public Pair makePair(Object car, Object cdr, int line, int column) {
        String pname = this.port.getName();
        if (pname == null || line < 0) {
            return Pair.make(car, cdr);
        }
        return PairWithPosition.make(car, cdr, pname, line + 1, column + 1);
    }

    /* access modifiers changed from: protected */
    public void setCdr(Object pair, Object cdr) {
        ((Pair) pair).setCdrBackdoor(cdr);
    }

    public static Object readNumberWithRadix(int previous, LispReader reader, int radix) throws IOException, SyntaxException {
        int startPos = reader.tokenBufferLength - previous;
        reader.readToken(reader.read(), 'P', ReadTable.getCurrent());
        int endPos = reader.tokenBufferLength;
        if (startPos == endPos) {
            reader.error("missing numeric token");
            return IntNum.zero();
        }
        Object result = parseNumber(reader.tokenBuffer, startPos, endPos - startPos, 0, radix, 0);
        if (result instanceof String) {
            reader.error((String) result);
            return IntNum.zero();
        } else if (result != null) {
            return result;
        } else {
            reader.error("invalid numeric constant");
            return IntNum.zero();
        }
    }

    public static Object readCharacter(LispReader reader) throws IOException, SyntaxException {
        int ch = reader.read();
        if (ch < 0) {
            reader.eofError("unexpected EOF in character literal");
        }
        int startPos = reader.tokenBufferLength;
        reader.tokenBufferAppend(ch);
        reader.readToken(reader.read(), 'D', ReadTable.getCurrent());
        char[] tokenBuffer = reader.tokenBuffer;
        int length = reader.tokenBufferLength - startPos;
        if (length == 1) {
            return Char.make(tokenBuffer[startPos]);
        }
        String name = new String(tokenBuffer, startPos, length);
        int ch2 = Char.nameToChar(name);
        if (ch2 >= 0) {
            return Char.make(ch2);
        }
        char ch3 = tokenBuffer[startPos];
        if (ch3 == 'x' || ch3 == 'X') {
            int value = 0;
            int i = 1;
            while (i != length) {
                int v = Character.digit(tokenBuffer[startPos + i], 16);
                if (v >= 0) {
                    value = (value * 16) + v;
                    if (value <= 1114111) {
                        i++;
                    }
                }
            }
            return Char.make(value);
        }
        int ch4 = Character.digit(ch3, 8);
        if (ch4 >= 0) {
            int value2 = ch4;
            int i2 = 1;
            while (i2 != length) {
                int ch5 = Character.digit(tokenBuffer[startPos + i2], 8);
                if (ch5 >= 0) {
                    value2 = (value2 * 8) + ch5;
                    i2++;
                }
            }
            return Char.make(value2);
        }
        reader.error("unknown character name: " + name);
        return Char.make(63);
    }

    public static Object readSpecial(LispReader reader) throws IOException, SyntaxException {
        int ch = reader.read();
        if (ch < 0) {
            reader.eofError("unexpected EOF in #! special form");
        }
        if (ch == 47 && reader.getLineNumber() == 0 && reader.getColumnNumber() == 3) {
            ReaderIgnoreRestOfLine.getInstance().read(reader, 35, 1);
            return Values.empty;
        }
        int startPos = reader.tokenBufferLength;
        reader.tokenBufferAppend(ch);
        reader.readToken(reader.read(), 'D', ReadTable.getCurrent());
        String name = new String(reader.tokenBuffer, startPos, reader.tokenBufferLength - startPos);
        if (name.equals("optional")) {
            return Special.optional;
        }
        if (name.equals("rest")) {
            return Special.rest;
        }
        if (name.equals("key")) {
            return Special.key;
        }
        if (name.equals("eof")) {
            return Special.eof;
        }
        if (name.equals("void")) {
            return QuoteExp.voidExp;
        }
        if (name.equals("default")) {
            return Special.dfault;
        }
        if (name.equals("undefined")) {
            return Special.undefined;
        }
        if (name.equals("abstract")) {
            return Special.abstractSpecial;
        }
        if (name.equals("null")) {
            return null;
        }
        reader.error("unknown named constant #!" + name);
        return null;
    }

    public static SimpleVector readSimpleVector(LispReader reader, char kind) throws IOException, SyntaxException {
        int ch;
        int size = 0;
        while (true) {
            ch = reader.read();
            if (ch < 0) {
                reader.eofError("unexpected EOF reading uniform vector");
            }
            int digit = Character.digit((char) ch, 10);
            if (digit < 0) {
                break;
            }
            size = (size * 10) + digit;
        }
        if ((size == 8 || size == 16 || size == 32 || size == 64) && ((kind != 'F' || size >= 32) && ch == 40)) {
            Object list = ReaderParens.readList(reader, 40, -1, 41);
            if (LList.listLength(list, false) < 0) {
                reader.error("invalid elements in uniform vector syntax");
                return null;
            }
            Sequence q = (Sequence) list;
            switch (kind) {
                case 'F':
                    switch (size) {
                        case 32:
                            return new F32Vector(q);
                        case 64:
                            return new F64Vector(q);
                    }
                case 'S':
                    break;
                case 'U':
                    break;
                default:
                    return null;
            }
            switch (size) {
                case 8:
                    return new S8Vector(q);
                case 16:
                    return new S16Vector(q);
                case 32:
                    return new S32Vector(q);
                case 64:
                    return new S64Vector(q);
            }
            switch (size) {
                case 8:
                    return new U8Vector(q);
                case 16:
                    return new U16Vector(q);
                case 32:
                    return new U32Vector(q);
                case 64:
                    return new U64Vector(q);
                default:
                    return null;
            }
        } else {
            reader.error("invalid uniform vector syntax");
            return null;
        }
    }
}
