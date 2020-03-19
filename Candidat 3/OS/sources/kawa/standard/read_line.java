package kawa.standard;

import gnu.bytecode.Access;
import gnu.expr.Special;
import gnu.lists.FString;
import gnu.mapping.Values;
import gnu.text.LineBufferedReader;
import java.io.IOException;

public class read_line {
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002e, code lost:
        if (r18 == "trim") goto L_0x0036;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0034, code lost:
        if (r18 != "peek") goto L_0x0062;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003a, code lost:
        if (r18 != "peek") goto L_0x003d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003c, code lost:
        r4 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003f, code lost:
        if (r2 != 10) goto L_0x0050;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0041, code lost:
        r4 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0042, code lost:
        r17.pos = r10 + r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0052, code lost:
        if ((r10 + 1) >= r8) goto L_0x0074;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005c, code lost:
        if (r1[r10 + 1] != 10) goto L_0x0060;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x005e, code lost:
        r4 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0060, code lost:
        r4 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0066, code lost:
        if (r18 != "concat") goto L_0x0074;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x006a, code lost:
        if (r2 != 10) goto L_0x0074;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006c, code lost:
        r10 = r10 + 1;
        r17.pos = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:?, code lost:
        return new gnu.lists.FString(r1, r13, r10 - r13);
     */
    public static Object apply(LineBufferedReader in, String handling) throws IOException {
        int pos;
        int delim;
        if (in.read() < 0) {
            return Special.eof;
        }
        int start = in.pos - 1;
        int pos2 = start;
        int limit = in.limit;
        char[] buffer = in.buffer;
        int delim2 = -1;
        int pos3 = pos2;
        while (true) {
            if (pos3 >= limit) {
                pos = pos3;
                break;
            }
            int pos4 = pos3 + 1;
            char ch = buffer[pos3];
            if (ch == 13 || ch == 10) {
                pos = pos4 - 1;
            } else {
                pos3 = pos4;
            }
        }
        StringBuffer sbuf = new StringBuffer(100);
        if (pos > start) {
            sbuf.append(buffer, start, pos - start);
        }
        in.pos = pos;
        char mode = handling == "peek" ? 'P' : (handling == "concat" || handling == "split") ? 'A' : Access.INNERCLASS_CONTEXT;
        in.readLine(sbuf, mode);
        int length = sbuf.length();
        if (handling == "split") {
            if (length == 0) {
                delim = 0;
            } else {
                char last = sbuf.charAt(length - 1);
                if (last == 13) {
                    delim = 1;
                } else if (last != 10) {
                    delim = 0;
                } else if (last <= 2 || sbuf.charAt(length - 2) != 13) {
                    delim = 1;
                } else {
                    delim = 2;
                }
                length -= delim;
            }
        }
        FString dataStr = new FString(sbuf, 0, length);
        if (handling != "split") {
            return dataStr;
        }
        return new Values(new Object[]{dataStr, new FString(sbuf, length - delim, delim)});
    }
}
