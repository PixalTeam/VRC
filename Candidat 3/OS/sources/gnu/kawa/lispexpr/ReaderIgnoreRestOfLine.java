package gnu.kawa.lispexpr;

import gnu.lists.Sequence;
import gnu.mapping.Values;
import gnu.text.Lexer;
import gnu.text.SyntaxException;
import java.io.IOException;

public class ReaderIgnoreRestOfLine extends ReadTableEntry {
    static ReaderIgnoreRestOfLine instance = new ReaderIgnoreRestOfLine();

    public static ReaderIgnoreRestOfLine getInstance() {
        return instance;
    }

    public Object read(Lexer in, int ch, int count) throws IOException, SyntaxException {
        int ch2;
        do {
            ch2 = in.read();
            if (ch2 >= 0) {
                if (ch2 == 10) {
                    break;
                }
            } else {
                return Sequence.eofValue;
            }
        } while (ch2 != 13);
        return Values.empty;
    }
}
