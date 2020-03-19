package gnu.kawa.functions;

import gnu.bytecode.Access;
import gnu.expr.Keyword;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.UntypedAtomic;
import gnu.kawa.xml.XmlNamespace;
import gnu.lists.AbstractFormat;
import gnu.lists.Array;
import gnu.lists.CharSeq;
import gnu.lists.Consumable;
import gnu.lists.Consumer;
import gnu.lists.ConsumerWriter;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.SimpleVector;
import gnu.lists.Strings;
import gnu.mapping.Namespace;
import gnu.mapping.OutPort;
import gnu.mapping.Symbol;
import gnu.mapping.ThreadLocation;
import gnu.mapping.Values;
import gnu.math.IntNum;
import gnu.math.RatNum;
import gnu.text.Char;
import gnu.text.Printable;
import gnu.xml.XMLPrinter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URI;
import java.util.regex.Pattern;

public class DisplayFormat extends AbstractFormat {
    public static final ThreadLocation outBase = new ThreadLocation("out-base");
    public static final ThreadLocation outRadix = new ThreadLocation("out-radix");
    static Pattern r5rsIdentifierMinusInteriorColons = Pattern.compile("(([a-zA-Z]|[!$%&*/:<=>?^_~])([a-zA-Z]|[!$%&*/<=>?^_~]|[0-9]|([-+.@]))*[:]?)|([-+]|[.][.][.])");
    char language;
    boolean readable;

    static {
        outBase.setGlobal(IntNum.ten());
    }

    public DisplayFormat(boolean readable2, char language2) {
        this.readable = readable2;
        this.language = language2;
    }

    public static DisplayFormat getEmacsLispFormat(boolean readable2) {
        return new DisplayFormat(readable2, 'E');
    }

    public static DisplayFormat getCommonLispFormat(boolean readable2) {
        return new DisplayFormat(readable2, Access.CLASS_CONTEXT);
    }

    public static DisplayFormat getSchemeFormat(boolean readable2) {
        return new DisplayFormat(readable2, 'S');
    }

    public boolean getReadableOutput() {
        return this.readable;
    }

    public void writeBoolean(boolean v, Consumer out) {
        String str = this.language == 'S' ? v ? "#t" : "#f" : v ? "t" : "nil";
        write(str, out);
    }

    public void write(int v, Consumer out) {
        if (!getReadableOutput()) {
            Char.print(v, out);
        } else if (this.language != 'E' || v <= 32) {
            write(Char.toScmReadableString(v), out);
        } else {
            out.write(63);
            Char.print(v, out);
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=gnu.lists.LList, code=java.lang.Object, for r6v0, types: [gnu.lists.LList, java.lang.Object] */
    public void writeList(Object value, OutPort out) {
        Object obj = value;
        out.startLogicalBlock("(", false, ")");
        while (obj instanceof Pair) {
            if (obj != value) {
                out.writeSpaceFill();
            }
            Pair pair = (Pair) obj;
            writeObject(pair.getCar(), out);
            obj = pair.getCdr();
        }
        if (obj != LList.Empty) {
            out.writeSpaceFill();
            out.write(". ");
            writeObject(LList.checkNonList(obj), out);
        }
        out.endLogicalBlock(")");
    }

    public void writeObject(Object obj, Consumer out) {
        boolean space = false;
        if ((out instanceof OutPort) && !(obj instanceof UntypedAtomic) && !(obj instanceof Values) && (getReadableOutput() || (!(obj instanceof Char) && !(obj instanceof CharSequence) && !(obj instanceof Character)))) {
            ((OutPort) out).writeWordStart();
            space = true;
        }
        writeObjectRaw(obj, out);
        if (space) {
            ((OutPort) out).writeWordEnd();
        }
    }

    /* JADX WARNING: type inference failed for: r22v1 */
    /* JADX WARNING: type inference failed for: r1v19, types: [java.io.Writer] */
    /* JADX WARNING: type inference failed for: r30v2, types: [java.io.Writer] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 2 */
    public void writeObjectRaw(Object obj, Consumer out) {
        String asString;
        ConsumerWriter consumerWriter;
        String start;
        String end;
        if (obj instanceof Boolean) {
            writeBoolean(((Boolean) obj).booleanValue(), out);
        } else if (obj instanceof Char) {
            write(((Char) obj).intValue(), out);
        } else if (obj instanceof Character) {
            write(((Character) obj).charValue(), out);
        } else if (obj instanceof Symbol) {
            Symbol sym = (Symbol) obj;
            if (sym.getNamespace() == XmlNamespace.HTML) {
                write("html:", out);
                write(sym.getLocalPart(), out);
                return;
            }
            writeSymbol(sym, out, this.readable);
        } else if ((obj instanceof URI) && getReadableOutput() && (out instanceof PrintWriter)) {
            write("#,(URI ", out);
            Strings.printQuoted(obj.toString(), (PrintWriter) out, 1);
            out.write(41);
        } else if (obj instanceof CharSequence) {
            CharSequence str = (CharSequence) obj;
            if (getReadableOutput() && (out instanceof PrintWriter)) {
                Strings.printQuoted(str, (PrintWriter) out, 1);
            } else if (obj instanceof String) {
                out.write((String) obj);
            } else if (obj instanceof CharSeq) {
                CharSeq seq = (CharSeq) obj;
                seq.consume(0, seq.size(), out);
            } else {
                int len = str.length();
                for (int i = 0; i < len; i++) {
                    out.write((int) str.charAt(i));
                }
            }
        } else if ((obj instanceof LList) && (out instanceof OutPort)) {
            writeList((LList) obj, (OutPort) out);
        } else if (obj instanceof SimpleVector) {
            SimpleVector vec = (SimpleVector) obj;
            String tag = vec.getTag();
            if (this.language == 'E') {
                start = "[";
                end = "]";
            } else {
                start = tag == null ? "#(" : "#" + tag + "(";
                end = ")";
            }
            if (out instanceof OutPort) {
                ((OutPort) out).startLogicalBlock(start, false, end);
            } else {
                write(start, out);
            }
            int endpos = vec.size() << 1;
            for (int ipos = 0; ipos < endpos; ipos += 2) {
                if (ipos > 0 && (out instanceof OutPort)) {
                    ((OutPort) out).writeSpaceFill();
                }
                if (!vec.consumeNext(ipos, out)) {
                    break;
                }
            }
            if (out instanceof OutPort) {
                ((OutPort) out).endLogicalBlock(end);
            } else {
                write(end, out);
            }
        } else if (obj instanceof Array) {
            write((Array) obj, 0, 0, out);
        } else if (obj instanceof KNode) {
            if (getReadableOutput()) {
                write("#", out);
            }
            if (out instanceof Writer) {
                consumerWriter = (Writer) out;
            } else {
                ConsumerWriter consumerWriter2 = new ConsumerWriter(out);
                consumerWriter = consumerWriter2;
            }
            XMLPrinter xMLPrinter = new XMLPrinter((Writer) consumerWriter);
            xMLPrinter.writeObject(obj);
            xMLPrinter.closeThis();
        } else if (obj == Values.empty && getReadableOutput()) {
            write("#!void", out);
        } else if (obj instanceof Consumable) {
            ((Consumable) obj).consume(out);
        } else if (obj instanceof Printable) {
            ((Printable) obj).print(out);
        } else if (obj instanceof RatNum) {
            int b = 10;
            boolean showRadix = false;
            Object base = outBase.get(null);
            Object printRadix = outRadix.get(null);
            if (printRadix != null && (printRadix == Boolean.TRUE || "yes".equals(printRadix.toString()))) {
                showRadix = true;
            }
            if (base instanceof Number) {
                b = ((IntNum) base).intValue();
            } else if (base != null) {
                b = Integer.parseInt(base.toString());
            }
            String asString2 = ((RatNum) obj).toString(b);
            if (showRadix) {
                if (b == 16) {
                    write("#x", out);
                } else if (b == 8) {
                    write("#o", out);
                } else if (b == 2) {
                    write("#b", out);
                } else if (b != 10 || !(obj instanceof IntNum)) {
                    write("#" + base + "r", out);
                }
            }
            write(asString2, out);
            if (showRadix && b == 10 && (obj instanceof IntNum)) {
                write(".", out);
            }
        } else if (!(obj instanceof Enum) || !getReadableOutput()) {
            if (obj == null) {
                asString = null;
            } else if (obj.getClass().isArray()) {
                int len2 = java.lang.reflect.Array.getLength(obj);
                if (out instanceof OutPort) {
                    ((OutPort) out).startLogicalBlock("[", false, "]");
                } else {
                    write("[", out);
                }
                for (int i2 = 0; i2 < len2; i2++) {
                    if (i2 > 0) {
                        write(" ", out);
                        if (out instanceof OutPort) {
                            ((OutPort) out).writeBreakFill();
                        }
                    }
                    writeObject(java.lang.reflect.Array.get(obj, i2), out);
                }
                if (out instanceof OutPort) {
                    ((OutPort) out).endLogicalBlock("]");
                    return;
                } else {
                    write("]", out);
                    return;
                }
            } else {
                asString = obj.toString();
            }
            if (asString == null) {
                write("#!null", out);
            } else {
                write(asString, out);
            }
        } else {
            write(obj.getClass().getName(), out);
            write(":", out);
            write(((Enum) obj).name(), out);
        }
    }

    /* access modifiers changed from: 0000 */
    public int write(Array array, int index, int level, Consumer out) {
        int step;
        int rank = array.rank();
        int count = 0;
        String start = level > 0 ? "(" : rank == 1 ? "#(" : "#" + rank + "a(";
        if (out instanceof OutPort) {
            ((OutPort) out).startLogicalBlock(start, false, ")");
        } else {
            write(start, out);
        }
        if (rank > 0) {
            int size = array.getSize(level);
            int level2 = level + 1;
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    write(" ", out);
                    if (out instanceof OutPort) {
                        ((OutPort) out).writeBreakFill();
                    }
                }
                if (level2 == rank) {
                    writeObject(array.getRowMajor(index), out);
                    step = 1;
                } else {
                    step = write(array, index, level2, out);
                }
                index += step;
                count += step;
            }
        }
        if (out instanceof OutPort) {
            ((OutPort) out).endLogicalBlock(")");
        } else {
            write(")", out);
        }
        return count;
    }

    /* access modifiers changed from: 0000 */
    public void writeSymbol(Symbol sym, Consumer out, boolean readable2) {
        boolean hasUri;
        boolean hasPrefix = true;
        String prefix = sym.getPrefix();
        Namespace namespace = sym.getNamespace();
        String uri = namespace == null ? null : namespace.getName();
        if (uri == null || uri.length() <= 0) {
            hasUri = false;
        } else {
            hasUri = true;
        }
        if (prefix == null || prefix.length() <= 0) {
            hasPrefix = false;
        }
        boolean suffixColon = false;
        if (namespace == Keyword.keywordNamespace) {
            if (this.language == 'C' || this.language == 'E') {
                out.write(58);
            } else {
                suffixColon = true;
            }
        } else if (hasPrefix || hasUri) {
            if (hasPrefix) {
                writeSymbol(prefix, out, readable2);
            }
            if (hasUri && (readable2 || !hasPrefix)) {
                out.write(123);
                out.write(uri);
                out.write(125);
            }
            out.write(58);
        }
        writeSymbol(sym.getName(), out, readable2);
        if (suffixColon) {
            out.write(58);
        }
    }

    /* access modifiers changed from: 0000 */
    public void writeSymbol(String sym, Consumer out, boolean readable2) {
        if (!readable2 || r5rsIdentifierMinusInteriorColons.matcher(sym).matches()) {
            write(sym, out);
            return;
        }
        int len = sym.length();
        if (len == 0) {
            write("||", out);
            return;
        }
        boolean inVerticalBars = false;
        for (int i = 0; i < len; i++) {
            char ch = sym.charAt(i);
            if (ch == '|') {
                write(inVerticalBars ? "|\\" : "\\", out);
                inVerticalBars = false;
            } else if (!inVerticalBars) {
                out.write(124);
                inVerticalBars = true;
            }
            out.write((int) ch);
        }
        if (inVerticalBars) {
            out.write(124);
        }
    }
}
