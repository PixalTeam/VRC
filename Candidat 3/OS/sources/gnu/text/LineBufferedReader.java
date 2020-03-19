package gnu.text;

import gnu.bytecode.Access;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class LineBufferedReader extends Reader {
    public static final int BUFFER_SIZE = 8192;
    private static final int CONVERT_CR = 1;
    private static final int DONT_KEEP_FULL_LINES = 8;
    private static final int PREV_WAS_CR = 4;
    private static final int USER_BUFFER = 2;
    public char[] buffer;
    private int flags;
    int highestPos;
    protected Reader in;
    public int limit;
    protected int lineNumber;
    private int lineStartPos;
    protected int markPos;
    Path path;
    public int pos;
    protected int readAheadLimit = 0;
    public char readState = 10;

    public void close() throws IOException {
        this.in.close();
    }

    public char getReadState() {
        return this.readState;
    }

    public void setKeepFullLines(boolean keep) {
        if (keep) {
            this.flags &= -9;
        } else {
            this.flags |= 8;
        }
    }

    public final boolean getConvertCR() {
        return (this.flags & 1) != 0;
    }

    public final void setConvertCR(boolean convertCR) {
        if (convertCR) {
            this.flags |= 1;
        } else {
            this.flags &= -2;
        }
    }

    public LineBufferedReader(InputStream in2) {
        this.in = new InputStreamReader(in2);
    }

    public LineBufferedReader(Reader in2) {
        this.in = in2;
    }

    public void lineStart(boolean revisited) throws IOException {
    }

    public int fill(int len) throws IOException {
        return this.in.read(this.buffer, this.pos, len);
    }

    private void clearMark() {
        int i = 0;
        this.readAheadLimit = 0;
        if (this.lineStartPos >= 0) {
            i = this.lineStartPos;
        }
        while (true) {
            i++;
            if (i < this.pos) {
                char ch = this.buffer[i - 1];
                if (ch == 10 || (ch == 13 && (!getConvertCR() || this.buffer[i] != 10))) {
                    this.lineNumber++;
                    this.lineStartPos = i;
                }
            } else {
                return;
            }
        }
    }

    public void setBuffer(char[] buffer2) throws IOException {
        if (buffer2 == null) {
            if (this.buffer != null) {
                char[] buffer3 = new char[this.buffer.length];
                System.arraycopy(this.buffer, 0, buffer3, 0, this.buffer.length);
                this.buffer = buffer3;
            }
            this.flags &= -3;
        } else if (this.limit - this.pos > buffer2.length) {
            throw new IOException("setBuffer - too short");
        } else {
            this.flags |= 2;
            reserve(buffer2, 0);
        }
    }

    private void reserve(char[] buffer2, int reserve) throws IOException {
        int saveStart;
        int reserve2 = reserve + this.limit;
        if (reserve2 <= buffer2.length) {
            saveStart = 0;
        } else {
            saveStart = this.pos;
            if (this.readAheadLimit > 0 && this.markPos < this.pos) {
                if (this.pos - this.markPos > this.readAheadLimit || ((this.flags & 2) != 0 && reserve2 - this.markPos > buffer2.length)) {
                    clearMark();
                } else {
                    saveStart = this.markPos;
                }
            }
            int reserve3 = reserve2 - buffer2.length;
            if (reserve3 > saveStart || (saveStart > this.lineStartPos && (this.flags & 8) == 0)) {
                if (reserve3 <= this.lineStartPos && saveStart > this.lineStartPos) {
                    saveStart = this.lineStartPos;
                } else if ((this.flags & 2) != 0) {
                    saveStart -= (saveStart - reserve3) >> 2;
                } else {
                    if (this.lineStartPos >= 0) {
                        saveStart = this.lineStartPos;
                    }
                    buffer2 = new char[(buffer2.length * 2)];
                }
            }
            this.lineStartPos -= saveStart;
            this.limit -= saveStart;
            this.markPos -= saveStart;
            this.pos -= saveStart;
            this.highestPos -= saveStart;
        }
        if (this.limit > 0) {
            System.arraycopy(this.buffer, saveStart, buffer2, 0, this.limit);
        }
        this.buffer = buffer2;
    }

    public int read() throws IOException {
        char prev;
        if (this.pos > 0) {
            prev = this.buffer[this.pos - 1];
        } else if ((this.flags & 4) != 0) {
            prev = 13;
        } else if (this.lineStartPos >= 0) {
            prev = 10;
        } else {
            prev = 0;
        }
        if (prev == 13 || prev == 10) {
            if (this.lineStartPos < this.pos && (this.readAheadLimit == 0 || this.pos <= this.markPos)) {
                this.lineStartPos = this.pos;
                this.lineNumber++;
            }
            boolean revisited = this.pos < this.highestPos;
            if (prev != 10 || (this.pos > 1 ? this.buffer[this.pos - 2] != 13 : (this.flags & 4) == 0)) {
                lineStart(revisited);
            }
            if (!revisited) {
                this.highestPos = this.pos + 1;
            }
        }
        if (this.pos >= this.limit) {
            if (this.buffer == null) {
                this.buffer = new char[8192];
            } else if (this.limit == this.buffer.length) {
                reserve(this.buffer, 1);
            }
            if (this.pos == 0) {
                if (prev == 13) {
                    this.flags |= 4;
                } else {
                    this.flags &= -5;
                }
            }
            int readCount = fill(this.buffer.length - this.pos);
            if (readCount <= 0) {
                return -1;
            }
            this.limit += readCount;
        }
        char[] cArr = this.buffer;
        int i = this.pos;
        this.pos = i + 1;
        char ch = cArr[i];
        if (ch == 10) {
            if (prev != 13) {
                return ch;
            }
            if (this.lineStartPos == this.pos - 1) {
                this.lineNumber--;
                this.lineStartPos--;
            }
            if (getConvertCR()) {
                return read();
            }
            return ch;
        } else if (ch != 13 || !getConvertCR()) {
            return ch;
        } else {
            return 10;
        }
    }

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r0v1 */
    /* JADX WARNING: type inference failed for: r5v5, types: [char[]] */
    /* JADX WARNING: type inference failed for: r0v2, types: [char] */
    /* JADX WARNING: type inference failed for: r0v3 */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: type inference failed for: r0v5 */
    /* JADX WARNING: type inference failed for: r0v6, types: [int] */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: type inference failed for: r0v8 */
    /* JADX WARNING: type inference failed for: r5v16, types: [char[]] */
    /* JADX WARNING: type inference failed for: r0v9, types: [int, char] */
    /* JADX WARNING: type inference failed for: r0v10 */
    /* JADX WARNING: type inference failed for: r0v11 */
    /* JADX WARNING: type inference failed for: r0v12 */
    /* JADX WARNING: type inference failed for: r0v13 */
    /* JADX WARNING: type inference failed for: r0v14 */
    /* JADX WARNING: type inference failed for: r0v15 */
    /* JADX WARNING: type inference failed for: r0v16 */
    /* JADX WARNING: type inference failed for: r0v17 */
    /* JADX WARNING: type inference failed for: r0v18 */
    /* JADX WARNING: type inference failed for: r0v19 */
    /* JADX WARNING: type inference failed for: r0v20 */
    /* JADX WARNING: type inference failed for: r0v21 */
    /* JADX WARNING: type inference failed for: r0v22 */
    /* JADX WARNING: type inference failed for: r0v23 */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r0v2, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r0v9, types: [int, char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char[], code=null, for r5v16, types: [char[]] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char[], code=null, for r5v5, types: [char[]] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v3
  assigns: []
  uses: []
  mth insns count: 77
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 13 */
    public int read(char[] cbuf, int off, int len) throws IOException {
        ? r0;
        ? r02;
        ? r03;
        if (this.pos >= this.limit) {
            r0 = 0;
        } else if (this.pos > 0) {
            r0 = this.buffer[this.pos - 1];
        } else {
            r0 = ((this.flags & 4) != 0 || this.lineStartPos >= 0) ? 10 : 0;
        }
        int to_do = len;
        int off2 = off;
        ? r04 = r0;
        while (to_do > 0) {
            if (this.pos < this.limit && r04 != 10 && r04 != 13) {
                int p = this.pos;
                int lim = this.limit;
                if (to_do < lim - p) {
                    lim = p + to_do;
                }
                ? r05 = r04;
                while (p < lim) {
                    ? r06 = this.buffer[p];
                    if (r06 == 10 || r06 == 13) {
                        r03 = r06;
                        break;
                    }
                    int off3 = off2 + 1;
                    cbuf[off2] = (char) r06;
                    p++;
                    off2 = off3;
                    r05 = r06;
                }
                r03 = r05;
                to_do -= p - this.pos;
                this.pos = p;
                r02 = r03;
            } else if (this.pos >= this.limit && to_do < len) {
                return len - to_do;
            } else {
                ? read = read();
                if (read < 0) {
                    int len2 = len - to_do;
                    if (len2 <= 0) {
                        return -1;
                    }
                    return len2;
                }
                int off4 = off2 + 1;
                cbuf[off2] = (char) read;
                to_do--;
                off2 = off4;
                r02 = read;
            }
            r04 = r02;
        }
        return len;
    }

    public Path getPath() {
        return this.path;
    }

    public void setPath(Path path2) {
        this.path = path2;
    }

    public String getName() {
        if (this.path == null) {
            return null;
        }
        return this.path.toString();
    }

    public void setName(Object name) {
        setPath(Path.valueOf(name));
    }

    public int getLineNumber() {
        int lineno = this.lineNumber;
        if (this.readAheadLimit != 0) {
            return lineno + countLines(this.buffer, this.lineStartPos < 0 ? 0 : this.lineStartPos, this.pos);
        } else if (this.pos <= 0 || this.pos <= this.lineStartPos) {
            return lineno;
        } else {
            char prev = this.buffer[this.pos - 1];
            if (prev == 10 || prev == 13) {
                return lineno + 1;
            }
            return lineno;
        }
    }

    public void setLineNumber(int lineNumber2) {
        this.lineNumber += lineNumber2 - getLineNumber();
    }

    public void incrLineNumber(int lineDelta, int lineStartPos2) {
        this.lineNumber += lineDelta;
        this.lineStartPos = lineStartPos2;
    }

    public int getColumnNumber() {
        int start = 0;
        if (this.pos > 0) {
            char prev = this.buffer[this.pos - 1];
            if (prev == 10 || prev == 13) {
                return 0;
            }
        }
        if (this.readAheadLimit <= 0) {
            return this.pos - this.lineStartPos;
        }
        if (this.lineStartPos >= 0) {
            start = this.lineStartPos;
        }
        int i = start;
        while (i < this.pos) {
            int i2 = i + 1;
            char ch = this.buffer[i];
            if (ch == 10 || ch == 13) {
                start = i2;
            }
            i = i2;
        }
        int col = this.pos - start;
        if (this.lineStartPos < 0) {
            col -= this.lineStartPos;
        }
        return col;
    }

    public boolean markSupported() {
        return true;
    }

    public synchronized void mark(int readAheadLimit2) {
        if (this.readAheadLimit > 0) {
            clearMark();
        }
        this.readAheadLimit = readAheadLimit2;
        this.markPos = this.pos;
    }

    public void reset() throws IOException {
        if (this.readAheadLimit <= 0) {
            throw new IOException("mark invalid");
        }
        if (this.pos > this.highestPos) {
            this.highestPos = this.pos;
        }
        this.pos = this.markPos;
        this.readAheadLimit = 0;
    }

    public void readLine(StringBuffer sbuf, char mode) throws IOException {
        while (read() >= 0) {
            int start = this.pos - 1;
            this.pos = start;
            while (this.pos < this.limit) {
                char[] cArr = this.buffer;
                int i = this.pos;
                this.pos = i + 1;
                char ch = cArr[i];
                if (ch != 13) {
                    if (ch == 10) {
                    }
                }
                sbuf.append(this.buffer, start, (this.pos - 1) - start);
                if (mode == 'P') {
                    this.pos--;
                    return;
                } else if (!getConvertCR() && ch != 10) {
                    if (mode != 'I') {
                        sbuf.append(13);
                    }
                    int ch2 = read();
                    if (ch2 == 10) {
                        if (mode != 'I') {
                            sbuf.append(10);
                            return;
                        }
                        return;
                    } else if (ch2 >= 0) {
                        unread_quick();
                        return;
                    } else {
                        return;
                    }
                } else if (mode != 'I') {
                    sbuf.append(10);
                    return;
                } else {
                    return;
                }
            }
            sbuf.append(this.buffer, start, this.pos - start);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002b, code lost:
        r1 = r9.pos - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002f, code lost:
        if (r0 == 10) goto L_0x006a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0035, code lost:
        if (getConvertCR() != false) goto L_0x006a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003b, code lost:
        if (r9.pos < r9.limit) goto L_0x005c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003d, code lost:
        r9.pos--;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0062, code lost:
        if (r9.buffer[r9.pos] != 10) goto L_0x006a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0064, code lost:
        r9.pos++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        return new java.lang.String(r9.buffer, r3, r1 - r3);
     */
    public String readLine() throws IOException {
        int ch = read();
        if (ch < 0) {
            return null;
        }
        if (ch == 13 || ch == 10) {
            return "";
        }
        int start = this.pos - 1;
        while (true) {
            if (this.pos >= this.limit) {
                break;
            }
            char[] cArr = this.buffer;
            int i = this.pos;
            this.pos = i + 1;
            char ch2 = cArr[i];
            if (ch2 != 13) {
                if (ch2 == 10) {
                    break;
                }
            } else {
                break;
            }
        }
        StringBuffer sbuf = new StringBuffer(100);
        sbuf.append(this.buffer, start, this.pos - start);
        readLine(sbuf, Access.INNERCLASS_CONTEXT);
        return sbuf.toString();
    }

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r0v1 */
    /* JADX WARNING: type inference failed for: r0v2 */
    /* JADX WARNING: type inference failed for: r0v3, types: [int] */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: type inference failed for: r0v5 */
    /* JADX WARNING: type inference failed for: r4v5, types: [char[]] */
    /* JADX WARNING: type inference failed for: r0v6, types: [char] */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: type inference failed for: r0v8 */
    /* JADX WARNING: type inference failed for: r4v10, types: [char[]] */
    /* JADX WARNING: type inference failed for: r0v9, types: [char] */
    /* JADX WARNING: type inference failed for: r0v10 */
    /* JADX WARNING: type inference failed for: r0v11 */
    /* JADX WARNING: type inference failed for: r0v12 */
    /* JADX WARNING: type inference failed for: r0v13 */
    /* JADX WARNING: type inference failed for: r0v14 */
    /* JADX WARNING: type inference failed for: r0v15 */
    /* JADX WARNING: type inference failed for: r0v16 */
    /* JADX WARNING: type inference failed for: r0v17 */
    /* JADX WARNING: type inference failed for: r0v18 */
    /* JADX WARNING: type inference failed for: r0v19 */
    /* JADX WARNING: type inference failed for: r0v20 */
    /* JADX WARNING: type inference failed for: r0v21 */
    /* JADX WARNING: type inference failed for: r0v22 */
    /* JADX WARNING: type inference failed for: r0v23 */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r0v6, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r0v9, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char[], code=null, for r4v10, types: [char[]] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char[], code=null, for r4v5, types: [char[]] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v0
  assigns: []
  uses: []
  mth insns count: 67
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 13 */
    public int skip(int n) throws IOException {
        ? r0;
        ? r02;
        ? r03;
        if (n < 0) {
            int to_do = -n;
            while (to_do > 0 && this.pos > 0) {
                unread();
                to_do--;
            }
            return n + to_do;
        }
        int to_do2 = n;
        if (this.pos >= this.limit) {
            r0 = 0;
        } else if (this.pos > 0) {
            r0 = this.buffer[this.pos - 1];
        } else {
            r0 = ((this.flags & 4) != 0 || this.lineStartPos >= 0) ? 10 : 0;
        }
        ? r04 = r0;
        while (to_do2 > 0) {
            if (r04 == 10 || r04 == 13 || this.pos >= this.limit) {
                ? read = read();
                if (read < 0) {
                    return n - to_do2;
                }
                to_do2--;
                r02 = read;
            } else {
                int p = this.pos;
                int lim = this.limit;
                if (to_do2 < lim - p) {
                    lim = p + to_do2;
                }
                ? r05 = r04;
                while (p < lim) {
                    ? r06 = this.buffer[p];
                    if (r06 == 10 || r06 == 13) {
                        r03 = r06;
                        break;
                    }
                    p++;
                    r05 = r06;
                }
                r03 = r05;
                to_do2 -= p - this.pos;
                this.pos = p;
                r02 = r03;
            }
            r04 = r02;
        }
        return n;
    }

    public boolean ready() throws IOException {
        return this.pos < this.limit || this.in.ready();
    }

    public final void skip_quick() throws IOException {
        this.pos++;
    }

    public void skip() throws IOException {
        read();
    }

    static int countLines(char[] buffer2, int start, int limit2) {
        int count = 0;
        char prev = 0;
        for (int i = start; i < limit2; i++) {
            char ch = buffer2[i];
            if ((ch == 10 && prev != 13) || ch == 13) {
                count++;
            }
            prev = ch;
        }
        return count;
    }

    public void skipRestOfLine() throws IOException {
        int c;
        do {
            c = read();
            if (c >= 0) {
                if (c == 13) {
                    int c2 = read();
                    if (c2 >= 0 && c2 != 10) {
                        unread();
                        return;
                    }
                    return;
                }
            } else {
                return;
            }
        } while (c != 10);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0056, code lost:
        r1 = r1 + 1;
     */
    public void unread() throws IOException {
        if (this.pos == 0) {
            throw new IOException("unread too much");
        }
        this.pos--;
        char ch = this.buffer[this.pos];
        if (ch == 10 || ch == 13) {
            if (this.pos > 0 && ch == 10 && getConvertCR() && this.buffer[this.pos - 1] == 13) {
                this.pos--;
            }
            if (this.pos < this.lineStartPos) {
                this.lineNumber--;
                int i = this.pos;
                while (true) {
                    if (i <= 0) {
                        break;
                    }
                    i--;
                    char ch2 = this.buffer[i];
                    if (ch2 != 13) {
                        if (ch2 == 10) {
                            break;
                        }
                    } else {
                        break;
                    }
                }
                this.lineStartPos = i;
            }
        }
    }

    public void unread_quick() {
        this.pos--;
    }

    public int peek() throws IOException {
        if (this.pos < this.limit && this.pos > 0) {
            char ch = this.buffer[this.pos - 1];
            if (!(ch == 10 || ch == 13)) {
                char ch2 = this.buffer[this.pos];
                if (ch2 != 13 || !getConvertCR()) {
                    return ch2;
                }
                return 10;
            }
        }
        int c = read();
        if (c >= 0) {
            unread_quick();
        }
        return c;
    }
}
