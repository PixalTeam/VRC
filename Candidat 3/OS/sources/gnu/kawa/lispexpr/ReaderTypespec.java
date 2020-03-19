package gnu.kawa.lispexpr;

import gnu.mapping.InPort;
import gnu.text.Lexer;
import gnu.text.LineBufferedReader;
import gnu.text.SyntaxException;
import java.io.IOException;

public class ReaderTypespec extends ReadTableEntry {
    public int getKind() {
        return 6;
    }

    /* JADX WARNING: type inference failed for: r13v0, types: [int] */
    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r1v1 */
    /* JADX WARNING: type inference failed for: r4v0 */
    /* JADX WARNING: type inference failed for: r1v2, types: [int] */
    /* JADX WARNING: type inference failed for: r1v3, types: [int] */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r1v5, types: [int] */
    /* JADX WARNING: type inference failed for: r1v6, types: [int] */
    /* JADX WARNING: type inference failed for: r8v14, types: [char[]] */
    /* JADX WARNING: type inference failed for: r1v7, types: [char] */
    /* JADX WARNING: type inference failed for: r1v8 */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: type inference failed for: r1v11 */
    /* JADX WARNING: type inference failed for: r1v12 */
    /* JADX WARNING: type inference failed for: r1v13 */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0057, code lost:
        if (1 != 1) goto L_0x0059;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0060, code lost:
        if (0 != 0) goto L_0x0062;
     */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r1v7, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char[], code=null, for r8v14, types: [char[]] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=int, code=null, for r13v0, types: [int] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v4
  assigns: []
  uses: []
  mth insns count: 75
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
    /* JADX WARNING: Unknown variable types count: 10 */
    public Object read(Lexer in, int r13, int count) throws IOException, SyntaxException {
        ? r1;
        ? r12;
        int startPos = in.tokenBufferLength;
        LineBufferedReader port = in.getPort();
        ReadTable rtable = ReadTable.getCurrent();
        char saveReadState = 0;
        in.tokenBufferAppend(r13);
        ? r14 = r13;
        if (port instanceof InPort) {
            saveReadState = ((InPort) port).readState;
            ((InPort) port).readState = (char) r13;
        }
        boolean got_open_square = false;
        while (true) {
            ? r4 = r14;
            try {
                if (port.pos >= port.limit || r4 == 10) {
                    r1 = port.read();
                } else {
                    ? r8 = port.buffer;
                    int i = port.pos;
                    port.pos = i + 1;
                    r1 = r8[i];
                }
                if (r1 != 92) {
                    if (!got_open_square && r1 == 91) {
                        got_open_square = true;
                    }
                    if (got_open_square && r1 == 93) {
                        got_open_square = false;
                    }
                    if (rtable.lookup(r1).getKind() != 2) {
                        break;
                    }
                    in.tokenBufferAppend(r1);
                    r12 = r1;
                } else if (in instanceof LispReader) {
                    r12 = ((LispReader) in).readEscape();
                } else {
                    r12 = port.read();
                }
                r14 = r12;
            } finally {
                in.tokenBufferLength = startPos;
                if (port instanceof InPort) {
                    ((InPort) port).readState = saveReadState;
                }
            }
        }
        in.unread(r1);
        return new String(in.tokenBuffer, startPos, in.tokenBufferLength - startPos).intern();
    }
}
