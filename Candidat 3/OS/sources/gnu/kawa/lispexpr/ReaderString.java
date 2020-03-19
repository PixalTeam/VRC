package gnu.kawa.lispexpr;

import gnu.mapping.InPort;
import gnu.text.Lexer;
import gnu.text.LineBufferedReader;
import gnu.text.SyntaxException;
import java.io.IOException;

public class ReaderString extends ReadTableEntry {
    public Object read(Lexer in, int ch, int count) throws IOException, SyntaxException {
        return readString(in, ch, count);
    }

    /* JADX WARNING: type inference failed for: r7v4, types: [char[]] */
    /* JADX WARNING: type inference failed for: r1v4, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=int, for r1v4, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char[], code=null, for r7v4, types: [char[]] */
    /* JADX WARNING: Unknown variable types count: 1 */
    public static String readString(Lexer in, int ch, int count) throws IOException, SyntaxException {
        int t;
        int startPos = in.tokenBufferLength;
        LineBufferedReader port = in.getPort();
        char saveReadState = 0;
        int c = ch;
        if (port instanceof InPort) {
            saveReadState = ((InPort) port).readState;
            ((InPort) port).readState = (char) ch;
        }
        while (true) {
            int prev = c;
            if (prev == 13) {
                try {
                    c = port.read();
                    if (c == 10) {
                        continue;
                    }
                } catch (Throwable th) {
                    in.tokenBufferLength = startPos;
                    if (port instanceof InPort) {
                        ((InPort) port).readState = saveReadState;
                    }
                    throw th;
                }
            } else if (port.pos >= port.limit || prev == 10) {
                c = port.read();
            } else {
                ? r7 = port.buffer;
                int i = port.pos;
                port.pos = i + 1;
                c = r7[i];
            }
            if (c != ch) {
                switch (c) {
                    case 13:
                        if (port.getConvertCR()) {
                            t = 10;
                        } else {
                            t = 13;
                            c = 32;
                        }
                        in.tokenBufferAppend(t);
                        break;
                    case 92:
                        if (in instanceof LispReader) {
                            c = ((LispReader) in).readEscape();
                        } else {
                            c = port.read();
                        }
                        if (c == -2) {
                            c = 10;
                            break;
                        }
                    default:
                        if (c < 0) {
                            in.eofError("unexpected EOF in string literal");
                        }
                        in.tokenBufferAppend(c);
                        break;
                }
            } else {
                String intern = new String(in.tokenBuffer, startPos, in.tokenBufferLength - startPos).intern();
                in.tokenBufferLength = startPos;
                if (port instanceof InPort) {
                    ((InPort) port).readState = saveReadState;
                }
                return intern;
            }
        }
    }
}
