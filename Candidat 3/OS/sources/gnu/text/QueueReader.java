package gnu.text;

import gnu.lists.CharSeq;
import java.io.Reader;

public class QueueReader extends Reader implements Appendable {
    boolean EOFseen;
    char[] buffer;
    int limit;
    int mark;
    int pos;
    int readAheadLimit;

    public boolean markSupported() {
        return true;
    }

    public synchronized void mark(int readAheadLimit2) {
        this.readAheadLimit = readAheadLimit2;
        this.mark = this.pos;
    }

    public synchronized void reset() {
        if (this.readAheadLimit > 0) {
            this.pos = this.mark;
        }
    }

    /* access modifiers changed from: 0000 */
    public void resize(int len) {
        int cur_size = this.limit - this.pos;
        if (this.readAheadLimit <= 0 || this.pos - this.mark > this.readAheadLimit) {
            this.mark = this.pos;
        } else {
            cur_size = this.limit - this.mark;
        }
        char[] new_buffer = this.buffer.length < cur_size + len ? new char[((cur_size * 2) + len)] : this.buffer;
        System.arraycopy(this.buffer, this.mark, new_buffer, 0, cur_size);
        this.buffer = new_buffer;
        this.pos -= this.mark;
        this.mark = 0;
        this.limit = cur_size;
    }

    public QueueReader append(CharSequence csq) {
        if (csq == null) {
            csq = "null";
        }
        return append(csq, 0, csq.length());
    }

    public synchronized QueueReader append(CharSequence csq, int start, int end) {
        if (csq == null) {
            csq = "null";
        }
        int len = end - start;
        reserveSpace(len);
        int sz = this.limit;
        char[] d = this.buffer;
        if (csq instanceof String) {
            ((String) csq).getChars(start, end, d, sz);
        } else if (csq instanceof CharSeq) {
            ((CharSeq) csq).getChars(start, end, d, sz);
        } else {
            int i = start;
            int j = sz;
            while (i < end) {
                int j2 = j + 1;
                d[j] = csq.charAt(i);
                i++;
                j = j2;
            }
        }
        this.limit = sz + len;
        notifyAll();
        return this;
    }

    public void append(char[] chars) {
        append(chars, 0, chars.length);
    }

    public synchronized void append(char[] chars, int off, int len) {
        reserveSpace(len);
        System.arraycopy(chars, off, this.buffer, this.limit, len);
        this.limit += len;
        notifyAll();
    }

    public synchronized QueueReader append(char ch) {
        reserveSpace(1);
        char[] cArr = this.buffer;
        int i = this.limit;
        this.limit = i + 1;
        cArr[i] = ch;
        notifyAll();
        return this;
    }

    public synchronized void appendEOF() {
        this.EOFseen = true;
    }

    /* access modifiers changed from: protected */
    public void reserveSpace(int len) {
        if (this.buffer == null) {
            this.buffer = new char[(len + 100)];
        } else if (this.buffer.length < this.limit + len) {
            resize(len);
        }
    }

    public synchronized boolean ready() {
        return this.pos < this.limit || this.EOFseen;
    }

    public void checkAvailable() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r1 = r4.buffer;
        r2 = r4.pos;
        r4.pos = r2 + 1;
        r0 = r1[r2];
     */
    public synchronized int read() {
        char c;
        while (true) {
            if (this.pos < this.limit) {
                break;
            } else if (this.EOFseen) {
                c = 65535;
                break;
            } else {
                checkAvailable();
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
        }
        return c;
    }

    /* JADX WARNING: CFG modification limit reached, blocks count: 127 */
    public synchronized int read(char[] cbuf, int off, int len) {
        int i;
        if (len == 0) {
            i = 0;
        } else {
            loop1:
            while (true) {
                if (this.pos < this.limit) {
                    int avail = this.limit - this.pos;
                    if (len > avail) {
                        len = avail;
                    }
                    System.arraycopy(this.buffer, this.pos, cbuf, off, len);
                    this.pos += len;
                    i = len;
                } else if (this.EOFseen) {
                    i = -1;
                    break loop1;
                } else {
                    checkAvailable();
                    try {
                        wait();
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
        return i;
    }

    public synchronized void close() {
        this.pos = 0;
        this.limit = 0;
        this.mark = 0;
        this.EOFseen = true;
        this.buffer = null;
    }
}
