package gnu.lists;

import gnu.kawa.lispexpr.LispReader;
import gnu.kawa.servlet.HttpRequestContext;
import gnu.text.Char;
import java.io.PrintWriter;

public class TreeList extends AbstractSequence implements Appendable, XConsumer, PositionConsumer, Consumable {
    protected static final int BEGIN_ATTRIBUTE_LONG = 61705;
    public static final int BEGIN_ATTRIBUTE_LONG_SIZE = 5;
    protected static final int BEGIN_DOCUMENT = 61712;
    protected static final int BEGIN_ELEMENT_LONG = 61704;
    protected static final int BEGIN_ELEMENT_SHORT = 40960;
    protected static final int BEGIN_ELEMENT_SHORT_INDEX_MAX = 4095;
    public static final int BEGIN_ENTITY = 61714;
    public static final int BEGIN_ENTITY_SIZE = 5;
    static final char BOOL_FALSE = '';
    static final char BOOL_TRUE = '';
    static final int BYTE_PREFIX = 61440;
    static final int CDATA_SECTION = 61717;
    static final int CHAR_FOLLOWS = 61702;
    static final int COMMENT = 61719;
    protected static final int DOCUMENT_URI = 61720;
    static final int DOUBLE_FOLLOWS = 61701;
    static final int END_ATTRIBUTE = 61706;
    public static final int END_ATTRIBUTE_SIZE = 1;
    protected static final int END_DOCUMENT = 61713;
    protected static final int END_ELEMENT_LONG = 61708;
    protected static final int END_ELEMENT_SHORT = 61707;
    protected static final int END_ENTITY = 61715;
    static final int FLOAT_FOLLOWS = 61700;
    public static final int INT_FOLLOWS = 61698;
    static final int INT_SHORT_ZERO = 49152;
    static final int JOINER = 61718;
    static final int LONG_FOLLOWS = 61699;
    public static final int MAX_CHAR_SHORT = 40959;
    static final int MAX_INT_SHORT = 8191;
    static final int MIN_INT_SHORT = -4096;
    static final char OBJECT_REF_FOLLOWS = '';
    static final int OBJECT_REF_SHORT = 57344;
    static final int OBJECT_REF_SHORT_INDEX_MAX = 4095;
    protected static final char POSITION_PAIR_FOLLOWS = '';
    static final char POSITION_REF_FOLLOWS = '';
    protected static final int PROCESSING_INSTRUCTION = 61716;
    public int attrStart;
    int currentParent;
    public char[] data;
    public int docStart;
    public int gapEnd;
    public int gapStart;
    public Object[] objects;
    public int oindex;

    public TreeList() {
        this.currentParent = -1;
        resizeObjects();
        this.gapEnd = HttpRequestContext.HTTP_OK;
        this.data = new char[this.gapEnd];
    }

    public TreeList(TreeList list, int startPosition, int endPosition) {
        this();
        list.consumeIRange(startPosition, endPosition, this);
    }

    public TreeList(TreeList list) {
        this(list, 0, list.data.length);
    }

    public void clear() {
        this.gapStart = 0;
        this.gapEnd = this.data.length;
        this.attrStart = 0;
        if (this.gapEnd > 1500) {
            this.gapEnd = HttpRequestContext.HTTP_OK;
            this.data = new char[this.gapEnd];
        }
        this.objects = null;
        this.oindex = 0;
        resizeObjects();
    }

    public void ensureSpace(int needed) {
        int avail = this.gapEnd - this.gapStart;
        if (needed > avail) {
            int oldSize = this.data.length;
            int neededSize = (oldSize - avail) + needed;
            int newSize = oldSize * 2;
            if (newSize < neededSize) {
                newSize = neededSize;
            }
            char[] tmp = new char[newSize];
            if (this.gapStart > 0) {
                System.arraycopy(this.data, 0, tmp, 0, this.gapStart);
            }
            int afterGap = oldSize - this.gapEnd;
            if (afterGap > 0) {
                System.arraycopy(this.data, this.gapEnd, tmp, newSize - afterGap, afterGap);
            }
            this.gapEnd = newSize - afterGap;
            this.data = tmp;
        }
    }

    public final void resizeObjects() {
        Object[] tmp;
        if (this.objects == null) {
            tmp = new Object[100];
        } else {
            int oldLength = this.objects.length;
            tmp = new Object[(oldLength * 2)];
            System.arraycopy(this.objects, 0, tmp, 0, oldLength);
        }
        this.objects = tmp;
    }

    public int find(Object arg1) {
        if (this.oindex == this.objects.length) {
            resizeObjects();
        }
        this.objects[this.oindex] = arg1;
        int i = this.oindex;
        this.oindex = i + 1;
        return i;
    }

    /* access modifiers changed from: protected */
    public final int getIntN(int index) {
        return (this.data[index] << 16) | (this.data[index + 1] & LispReader.TOKEN_ESCAPE_CHAR);
    }

    /* access modifiers changed from: protected */
    public final long getLongN(int index) {
        char[] data2 = this.data;
        return ((((long) data2[index]) & 65535) << 48) | ((((long) data2[index + 1]) & 65535) << 32) | ((((long) data2[index + 2]) & 65535) << 16) | (((long) data2[index + 3]) & 65535);
    }

    public final void setIntN(int index, int i) {
        this.data[index] = (char) (i >> 16);
        this.data[index + 1] = (char) i;
    }

    public void consume(SeqPosition position) {
        ensureSpace(3);
        int index = find(position.copy());
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = POSITION_REF_FOLLOWS;
        setIntN(this.gapStart, index);
        this.gapStart += 2;
    }

    public void writePosition(AbstractSequence seq, int ipos) {
        ensureSpace(5);
        this.data[this.gapStart] = POSITION_PAIR_FOLLOWS;
        setIntN(this.gapStart + 1, find(seq));
        setIntN(this.gapStart + 3, ipos);
        this.gapStart += 5;
    }

    public void writeObject(Object v) {
        ensureSpace(3);
        int index = find(v);
        if (index < 4096) {
            char[] cArr = this.data;
            int i = this.gapStart;
            this.gapStart = i + 1;
            cArr[i] = (char) (OBJECT_REF_SHORT | index);
            return;
        }
        char[] cArr2 = this.data;
        int i2 = this.gapStart;
        this.gapStart = i2 + 1;
        cArr2[i2] = OBJECT_REF_FOLLOWS;
        setIntN(this.gapStart, index);
        this.gapStart += 2;
    }

    public void writeDocumentUri(Object uri) {
        ensureSpace(3);
        int index = find(uri);
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = 61720;
        setIntN(this.gapStart, index);
        this.gapStart += 2;
    }

    public void writeComment(char[] chars, int offset, int length) {
        ensureSpace(length + 3);
        int i = this.gapStart;
        int i2 = i + 1;
        this.data[i] = 61719;
        setIntN(i2, length);
        int i3 = i2 + 2;
        System.arraycopy(chars, offset, this.data, i3, length);
        this.gapStart = i3 + length;
    }

    public void writeComment(String comment, int offset, int length) {
        ensureSpace(length + 3);
        int i = this.gapStart;
        int i2 = i + 1;
        this.data[i] = 61719;
        setIntN(i2, length);
        int i3 = i2 + 2;
        comment.getChars(offset, offset + length, this.data, i3);
        this.gapStart = i3 + length;
    }

    public void writeProcessingInstruction(String target, char[] content, int offset, int length) {
        ensureSpace(length + 5);
        int i = this.gapStart;
        int i2 = i + 1;
        this.data[i] = 61716;
        setIntN(i2, find(target));
        setIntN(i2 + 2, length);
        int i3 = i2 + 4;
        System.arraycopy(content, offset, this.data, i3, length);
        this.gapStart = i3 + length;
    }

    public void writeProcessingInstruction(String target, String content, int offset, int length) {
        ensureSpace(length + 5);
        int i = this.gapStart;
        int i2 = i + 1;
        this.data[i] = 61716;
        setIntN(i2, find(target));
        setIntN(i2 + 2, length);
        int i3 = i2 + 4;
        content.getChars(offset, offset + length, this.data, i3);
        this.gapStart = i3 + length;
    }

    public void startElement(Object type) {
        startElement(find(type));
    }

    public void startDocument() {
        int i = -1;
        ensureSpace(6);
        this.gapEnd--;
        int p = this.gapStart;
        this.data[p] = 61712;
        if (this.docStart != 0) {
            throw new Error("nested document");
        }
        this.docStart = p + 1;
        setIntN(p + 1, this.gapEnd - this.data.length);
        int i2 = p + 3;
        if (this.currentParent != -1) {
            i = this.currentParent - p;
        }
        setIntN(i2, i);
        this.currentParent = p;
        this.gapStart = p + 5;
        this.currentParent = p;
        this.data[this.gapEnd] = 61713;
    }

    public void endDocument() {
        if (this.data[this.gapEnd] == END_DOCUMENT && this.docStart > 0 && this.data[this.currentParent] == BEGIN_DOCUMENT) {
            this.gapEnd++;
            setIntN(this.docStart, (this.gapStart - this.docStart) + 1);
            this.docStart = 0;
            char[] cArr = this.data;
            int i = this.gapStart;
            this.gapStart = i + 1;
            cArr[i] = 61713;
            int parent = getIntN(this.currentParent + 3);
            if (parent < -1) {
                parent += this.currentParent;
            }
            this.currentParent = parent;
            return;
        }
        throw new Error("unexpected endDocument");
    }

    public void beginEntity(Object base) {
        int i = -1;
        if (this.gapStart == 0) {
            ensureSpace(6);
            this.gapEnd--;
            int p = this.gapStart;
            this.data[p] = 61714;
            setIntN(p + 1, find(base));
            int i2 = p + 3;
            if (this.currentParent != -1) {
                i = this.currentParent - p;
            }
            setIntN(i2, i);
            this.gapStart = p + 5;
            this.currentParent = p;
            this.data[this.gapEnd] = 61715;
        }
    }

    public void endEntity() {
        if (this.gapEnd + 1 != this.data.length || this.data[this.gapEnd] != END_ENTITY) {
            return;
        }
        if (this.data[this.currentParent] != 61714) {
            throw new Error("unexpected endEntity");
        }
        this.gapEnd++;
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = 61715;
        int parent = getIntN(this.currentParent + 3);
        if (parent < -1) {
            parent += this.currentParent;
        }
        this.currentParent = parent;
    }

    public void startElement(int index) {
        ensureSpace(10);
        this.gapEnd -= 7;
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = 61704;
        setIntN(this.gapStart, this.gapEnd - this.data.length);
        this.gapStart += 2;
        this.data[this.gapEnd] = 61708;
        setIntN(this.gapEnd + 1, index);
        setIntN(this.gapEnd + 3, this.gapStart - 3);
        setIntN(this.gapEnd + 5, this.currentParent);
        this.currentParent = this.gapStart - 3;
    }

    public void setElementName(int elementIndex, int nameIndex) {
        if (this.data[elementIndex] == BEGIN_ELEMENT_LONG) {
            int j = getIntN(elementIndex + 1);
            if (j < 0) {
                elementIndex = this.data.length;
            }
            elementIndex += j;
        }
        if (elementIndex < this.gapEnd) {
            throw new Error("setElementName before gapEnd");
        }
        setIntN(elementIndex + 1, nameIndex);
    }

    public void endElement() {
        if (this.data[this.gapEnd] != END_ELEMENT_LONG) {
            throw new Error("unexpected endElement");
        }
        int index = getIntN(this.gapEnd + 1);
        int begin = getIntN(this.gapEnd + 3);
        int parent = getIntN(this.gapEnd + 5);
        this.currentParent = parent;
        this.gapEnd += 7;
        int offset = this.gapStart - begin;
        int parentOffset = begin - parent;
        if (index >= 4095 || offset >= 65536 || parentOffset >= 65536) {
            this.data[begin] = 61704;
            setIntN(begin + 1, offset);
            this.data[this.gapStart] = 61708;
            setIntN(this.gapStart + 1, index);
            setIntN(this.gapStart + 3, -offset);
            if (parent >= this.gapStart || begin <= this.gapStart) {
                parent -= this.gapStart;
            }
            setIntN(this.gapStart + 5, parent);
            this.gapStart += 7;
            return;
        }
        this.data[begin] = (char) (BEGIN_ELEMENT_SHORT | index);
        this.data[begin + 1] = (char) offset;
        this.data[begin + 2] = (char) parentOffset;
        this.data[this.gapStart] = 61707;
        this.data[this.gapStart + 1] = (char) offset;
        this.gapStart += 2;
    }

    public void startAttribute(Object attrType) {
        startAttribute(find(attrType));
    }

    public void startAttribute(int index) {
        ensureSpace(6);
        this.gapEnd--;
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = 61705;
        if (this.attrStart != 0) {
            throw new Error("nested attribute");
        }
        this.attrStart = this.gapStart;
        setIntN(this.gapStart, index);
        setIntN(this.gapStart + 2, this.gapEnd - this.data.length);
        this.gapStart += 4;
        this.data[this.gapEnd] = 61706;
    }

    public void setAttributeName(int attrIndex, int nameIndex) {
        setIntN(attrIndex + 1, nameIndex);
    }

    public void endAttribute() {
        if (this.attrStart > 0) {
            if (this.data[this.gapEnd] != END_ATTRIBUTE) {
                throw new Error("unexpected endAttribute");
            }
            this.gapEnd++;
            setIntN(this.attrStart + 2, (this.gapStart - this.attrStart) + 1);
            this.attrStart = 0;
            char[] cArr = this.data;
            int i = this.gapStart;
            this.gapStart = i + 1;
            cArr[i] = 61706;
        }
    }

    public Consumer append(char c) {
        write((int) c);
        return this;
    }

    public void write(int c) {
        ensureSpace(3);
        if (c <= 40959) {
            char[] cArr = this.data;
            int i = this.gapStart;
            this.gapStart = i + 1;
            cArr[i] = (char) c;
        } else if (c < 65536) {
            char[] cArr2 = this.data;
            int i2 = this.gapStart;
            this.gapStart = i2 + 1;
            cArr2[i2] = 61702;
            char[] cArr3 = this.data;
            int i3 = this.gapStart;
            this.gapStart = i3 + 1;
            cArr3[i3] = (char) c;
        } else {
            Char.print(c, this);
        }
    }

    public void writeBoolean(boolean v) {
        ensureSpace(1);
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = v ? BOOL_TRUE : BOOL_FALSE;
    }

    public void writeByte(int v) {
        ensureSpace(1);
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = (char) (BYTE_PREFIX + (v & 255));
    }

    public void writeInt(int v) {
        ensureSpace(3);
        if (v < MIN_INT_SHORT || v > MAX_INT_SHORT) {
            char[] cArr = this.data;
            int i = this.gapStart;
            this.gapStart = i + 1;
            cArr[i] = 61698;
            setIntN(this.gapStart, v);
            this.gapStart += 2;
            return;
        }
        char[] cArr2 = this.data;
        int i2 = this.gapStart;
        this.gapStart = i2 + 1;
        cArr2[i2] = (char) (INT_SHORT_ZERO + v);
    }

    public void writeLong(long v) {
        ensureSpace(5);
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = 61699;
        char[] cArr2 = this.data;
        int i2 = this.gapStart;
        this.gapStart = i2 + 1;
        cArr2[i2] = (char) ((int) (v >>> 48));
        char[] cArr3 = this.data;
        int i3 = this.gapStart;
        this.gapStart = i3 + 1;
        cArr3[i3] = (char) ((int) (v >>> 32));
        char[] cArr4 = this.data;
        int i4 = this.gapStart;
        this.gapStart = i4 + 1;
        cArr4[i4] = (char) ((int) (v >>> 16));
        char[] cArr5 = this.data;
        int i5 = this.gapStart;
        this.gapStart = i5 + 1;
        cArr5[i5] = (char) ((int) v);
    }

    public void writeFloat(float v) {
        ensureSpace(3);
        int i = Float.floatToIntBits(v);
        char[] cArr = this.data;
        int i2 = this.gapStart;
        this.gapStart = i2 + 1;
        cArr[i2] = 61700;
        char[] cArr2 = this.data;
        int i3 = this.gapStart;
        this.gapStart = i3 + 1;
        cArr2[i3] = (char) (i >>> 16);
        char[] cArr3 = this.data;
        int i4 = this.gapStart;
        this.gapStart = i4 + 1;
        cArr3[i4] = (char) i;
    }

    public void writeDouble(double v) {
        ensureSpace(5);
        long l = Double.doubleToLongBits(v);
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = 61701;
        char[] cArr2 = this.data;
        int i2 = this.gapStart;
        this.gapStart = i2 + 1;
        cArr2[i2] = (char) ((int) (l >>> 48));
        char[] cArr3 = this.data;
        int i3 = this.gapStart;
        this.gapStart = i3 + 1;
        cArr3[i3] = (char) ((int) (l >>> 32));
        char[] cArr4 = this.data;
        int i4 = this.gapStart;
        this.gapStart = i4 + 1;
        cArr4[i4] = (char) ((int) (l >>> 16));
        char[] cArr5 = this.data;
        int i5 = this.gapStart;
        this.gapStart = i5 + 1;
        cArr5[i5] = (char) ((int) l);
    }

    public boolean ignoring() {
        return false;
    }

    public void writeJoiner() {
        ensureSpace(1);
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = 61718;
    }

    public void write(char[] buf, int off, int len) {
        if (len == 0) {
            writeJoiner();
        }
        ensureSpace(len);
        int off2 = off;
        while (len > 0) {
            int off3 = off2 + 1;
            char ch = buf[off2];
            len--;
            if (ch <= 40959) {
                char[] cArr = this.data;
                int i = this.gapStart;
                this.gapStart = i + 1;
                cArr[i] = ch;
            } else {
                write((int) ch);
                ensureSpace(len);
            }
            off2 = off3;
        }
    }

    public void write(String str) {
        write((CharSequence) str, 0, str.length());
    }

    public void write(CharSequence str, int start, int length) {
        if (length == 0) {
            writeJoiner();
        }
        ensureSpace(length);
        int start2 = start;
        while (length > 0) {
            int start3 = start2 + 1;
            char ch = str.charAt(start2);
            length--;
            if (ch <= 40959) {
                char[] cArr = this.data;
                int i = this.gapStart;
                this.gapStart = i + 1;
                cArr[i] = ch;
            } else {
                write((int) ch);
                ensureSpace(length);
            }
            start2 = start3;
        }
    }

    public void writeCDATA(char[] chars, int offset, int length) {
        ensureSpace(length + 3);
        int i = this.gapStart;
        int i2 = i + 1;
        this.data[i] = 61717;
        setIntN(i2, length);
        int i3 = i2 + 2;
        System.arraycopy(chars, offset, this.data, i3, length);
        this.gapStart = i3 + length;
    }

    public Consumer append(CharSequence csq) {
        if (csq == null) {
            csq = "null";
        }
        return append(csq, 0, csq.length());
    }

    public Consumer append(CharSequence csq, int start, int end) {
        if (csq == null) {
            csq = "null";
        }
        for (int i = start; i < end; i++) {
            append(csq.charAt(i));
        }
        return this;
    }

    public boolean isEmpty() {
        int pos;
        if (this.gapStart == 0) {
            pos = this.gapEnd;
        } else {
            pos = 0;
        }
        if (pos == this.data.length) {
            return true;
        }
        return false;
    }

    public int size() {
        int size = 0;
        int i = 0;
        while (true) {
            i = nextPos(i);
            if (i == 0) {
                return size;
            }
            size++;
        }
    }

    public int createPos(int index, boolean isAfter) {
        return createRelativePos(0, index, isAfter);
    }

    public final int posToDataIndex(int ipos) {
        if (ipos == -1) {
            return this.data.length;
        }
        int index = ipos >>> 1;
        if ((ipos & 1) != 0) {
            index--;
        }
        if (index >= this.gapStart) {
            index += this.gapEnd - this.gapStart;
        }
        if ((ipos & 1) == 0) {
            return index;
        }
        int index2 = nextDataIndex(index);
        if (index2 < 0) {
            return this.data.length;
        }
        if (index2 == this.gapStart) {
            return index2 + (this.gapEnd - this.gapStart);
        }
        return index2;
    }

    public int firstChildPos(int ipos) {
        int index = gotoChildrenStart(posToDataIndex(ipos));
        if (index < 0) {
            return 0;
        }
        return index << 1;
    }

    public final int gotoChildrenStart(int index) {
        int index2;
        int index3;
        if (index == this.data.length) {
            return -1;
        }
        char datum = this.data[index];
        if ((datum >= BEGIN_ELEMENT_SHORT && datum <= 45055) || datum == BEGIN_ELEMENT_LONG) {
            index2 = index + 3;
        } else if (datum != BEGIN_DOCUMENT && datum != 61714) {
            return -1;
        } else {
            index2 = index + 5;
        }
        while (true) {
            if (index2 >= this.gapStart) {
                index2 += this.gapEnd - this.gapStart;
            }
            char datum2 = this.data[index2];
            if (datum2 == BEGIN_ATTRIBUTE_LONG) {
                int end = getIntN(index2 + 3);
                if (end < 0) {
                    index2 = this.data.length;
                }
                index3 = index2 + end;
            } else if (datum2 == END_ATTRIBUTE || datum2 == JOINER) {
                index3 = index2 + 1;
            } else if (datum2 != DOCUMENT_URI) {
                return index2;
            } else {
                index3 = index2 + 3;
            }
        }
    }

    public int parentPos(int ipos) {
        int index = posToDataIndex(ipos);
        do {
            index = parentOrEntityI(index);
            if (index == -1) {
                return -1;
            }
        } while (this.data[index] == 61714);
        return index << 1;
    }

    public int parentOrEntityPos(int ipos) {
        int index = parentOrEntityI(posToDataIndex(ipos));
        if (index < 0) {
            return -1;
        }
        return index << 1;
    }

    /* JADX WARNING: CFG modification limit reached, blocks count: 150 */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x006f, code lost:
        if (r8 < 0) goto L_0x0006;
     */
    public int parentOrEntityI(int index) {
        int i;
        int i2 = -1;
        if (index != this.data.length) {
            char datum = this.data[index];
            if (datum == BEGIN_DOCUMENT || datum == 61714) {
                int parent_offset = getIntN(index + 3);
                i2 = parent_offset >= -1 ? parent_offset : index + parent_offset;
            } else if (datum >= BEGIN_ELEMENT_SHORT && datum <= 45055) {
                char parent_offset2 = this.data[index + 2];
                if (parent_offset2 != 0) {
                    i2 = index - parent_offset2;
                }
            } else if (datum == BEGIN_ELEMENT_LONG) {
                int end_offset = getIntN(index + 1);
                if (end_offset < 0) {
                    i = this.data.length;
                } else {
                    i = index;
                }
                int end_offset2 = end_offset + i;
                int parent_offset3 = getIntN(end_offset2 + 5);
                if (parent_offset3 != 0) {
                    if (parent_offset3 < 0) {
                        parent_offset3 += end_offset2;
                    }
                    i2 = parent_offset3;
                }
            } else {
                while (true) {
                    if (index == this.gapStart) {
                        index = this.gapEnd;
                    }
                    if (index != this.data.length) {
                        switch (this.data[index]) {
                            case END_ATTRIBUTE /*61706*/:
                                index++;
                            case END_ELEMENT_SHORT /*61707*/:
                                i2 = index - this.data[index + 1];
                                break;
                            case END_ELEMENT_LONG /*61708*/:
                                int begin_offset = getIntN(index + 3);
                                if (begin_offset < 0) {
                                    begin_offset += index;
                                }
                                i2 = begin_offset;
                                break;
                            case END_DOCUMENT /*61713*/:
                                break;
                            default:
                                index = nextDataIndex(index);
                                break;
                        }
                    }
                }
            }
        }
        return i2;
    }

    public int getAttributeCount(int parent) {
        int n = 0;
        int attr = firstAttributePos(parent);
        while (attr != 0 && getNextKind(attr) == 35) {
            n++;
            attr = nextPos(attr);
        }
        return n;
    }

    public boolean gotoAttributesStart(TreePosition pos) {
        int index = gotoAttributesStart(pos.ipos >> 1);
        if (index < 0) {
            return false;
        }
        pos.push(this, index << 1);
        return true;
    }

    public int firstAttributePos(int ipos) {
        int index = gotoAttributesStart(posToDataIndex(ipos));
        if (index < 0) {
            return 0;
        }
        return index << 1;
    }

    public int gotoAttributesStart(int index) {
        if (index >= this.gapStart) {
            index += this.gapEnd - this.gapStart;
        }
        if (index == this.data.length) {
            return -1;
        }
        char datum = this.data[index];
        if ((datum < BEGIN_ELEMENT_SHORT || datum > 45055) && datum != BEGIN_ELEMENT_LONG) {
            return -1;
        }
        return index + 3;
    }

    public Object get(int index) {
        int i = 0;
        do {
            index--;
            if (index < 0) {
                return getPosNext(i);
            }
            i = nextPos(i);
        } while (i != 0);
        throw new IndexOutOfBoundsException();
    }

    public boolean consumeNext(int ipos, Consumer out) {
        if (!hasNext(ipos)) {
            return false;
        }
        int start = posToDataIndex(ipos);
        int end = nextNodeIndex(start, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        if (end == start) {
            end = nextDataIndex(start);
        }
        if (end >= 0) {
            consumeIRange(start, end, out);
        }
        return true;
    }

    public void consumePosRange(int startPos, int endPos, Consumer out) {
        consumeIRange(posToDataIndex(startPos), posToDataIndex(endPos), out);
    }

    public int consumeIRange(int startPosition, int endPosition, Consumer out) {
        int limit;
        int pos = startPosition;
        if (startPosition > this.gapStart || endPosition <= this.gapStart) {
            limit = endPosition;
        } else {
            limit = this.gapStart;
        }
        while (true) {
            if (pos >= limit) {
                if (pos != this.gapStart || endPosition <= this.gapEnd) {
                    return pos;
                }
                pos = this.gapEnd;
                limit = endPosition;
            }
            int pos2 = pos + 1;
            char datum = this.data[pos];
            if (datum <= 40959) {
                int start = pos2 - 1;
                int lim = limit;
                while (true) {
                    if (pos2 >= lim) {
                        pos = pos2;
                    } else {
                        int pos3 = pos2 + 1;
                        if (this.data[pos2] > 40959) {
                            pos = pos3 - 1;
                        } else {
                            pos2 = pos3;
                        }
                    }
                }
                out.write(this.data, start, pos - start);
            } else if (datum >= OBJECT_REF_SHORT && datum <= 61439) {
                out.writeObject(this.objects[datum - OBJECT_REF_SHORT]);
                pos = pos2;
            } else if (datum >= BEGIN_ELEMENT_SHORT && datum <= 45055) {
                out.startElement(this.objects[datum - BEGIN_ELEMENT_SHORT]);
                pos = pos2 + 2;
            } else if (datum < 45056 || datum > 57343) {
                switch (datum) {
                    case 61696:
                    case 61697:
                        out.writeBoolean(datum != 61696);
                        pos = pos2;
                        break;
                    case INT_FOLLOWS /*61698*/:
                        out.writeInt(getIntN(pos2));
                        pos = pos2 + 2;
                        break;
                    case LONG_FOLLOWS /*61699*/:
                        out.writeLong(getLongN(pos2));
                        pos = pos2 + 4;
                        break;
                    case FLOAT_FOLLOWS /*61700*/:
                        out.writeFloat(Float.intBitsToFloat(getIntN(pos2)));
                        pos = pos2 + 2;
                        break;
                    case DOUBLE_FOLLOWS /*61701*/:
                        out.writeDouble(Double.longBitsToDouble(getLongN(pos2)));
                        pos = pos2 + 4;
                        break;
                    case CHAR_FOLLOWS /*61702*/:
                        out.write(this.data, pos2, (datum + 1) - CHAR_FOLLOWS);
                        pos = pos2 + 1;
                        break;
                    case BEGIN_ELEMENT_LONG /*61704*/:
                        int index = getIntN(pos2);
                        pos = pos2 + 2;
                        out.startElement(this.objects[getIntN(index + (index >= 0 ? pos2 - 1 : this.data.length) + 1)]);
                        break;
                    case BEGIN_ATTRIBUTE_LONG /*61705*/:
                        out.startAttribute(this.objects[getIntN(pos2)]);
                        pos = pos2 + 4;
                        break;
                    case END_ATTRIBUTE /*61706*/:
                        out.endAttribute();
                        pos = pos2;
                        break;
                    case END_ELEMENT_SHORT /*61707*/:
                        pos = pos2 + 1;
                        out.endElement();
                        break;
                    case END_ELEMENT_LONG /*61708*/:
                        int intN = getIntN(pos2);
                        out.endElement();
                        pos = pos2 + 6;
                        break;
                    case 61710:
                        if (out instanceof PositionConsumer) {
                            ((PositionConsumer) out).consume((SeqPosition) this.objects[getIntN(pos2)]);
                            pos = pos2 + 2;
                            break;
                        }
                    case 61709:
                        out.writeObject(this.objects[getIntN(pos2)]);
                        pos = pos2 + 2;
                        break;
                    case 61711:
                        AbstractSequence seq = (AbstractSequence) this.objects[getIntN(pos2)];
                        int ipos = getIntN(pos2 + 2);
                        if (out instanceof PositionConsumer) {
                            ((PositionConsumer) out).writePosition(seq, ipos);
                        } else {
                            out.writeObject(seq.getIteratorAtPos(ipos));
                        }
                        pos = pos2 + 4;
                        break;
                    case BEGIN_DOCUMENT /*61712*/:
                        out.startDocument();
                        pos = pos2 + 4;
                        break;
                    case END_DOCUMENT /*61713*/:
                        out.endDocument();
                        pos = pos2;
                        break;
                    case BEGIN_ENTITY /*61714*/:
                        if (out instanceof TreeList) {
                            ((TreeList) out).beginEntity(this.objects[getIntN(pos2)]);
                        }
                        pos = pos2 + 4;
                        break;
                    case END_ENTITY /*61715*/:
                        if (!(out instanceof TreeList)) {
                            pos = pos2;
                            break;
                        } else {
                            ((TreeList) out).endEntity();
                            pos = pos2;
                            break;
                        }
                    case PROCESSING_INSTRUCTION /*61716*/:
                        String target = (String) this.objects[getIntN(pos2)];
                        int length = getIntN(pos2 + 2);
                        int pos4 = pos2 + 4;
                        if (out instanceof XConsumer) {
                            ((XConsumer) out).writeProcessingInstruction(target, this.data, pos4, length);
                        }
                        pos = pos4 + length;
                        break;
                    case CDATA_SECTION /*61717*/:
                        int length2 = getIntN(pos2);
                        int pos5 = pos2 + 2;
                        if (out instanceof XConsumer) {
                            ((XConsumer) out).writeCDATA(this.data, pos5, length2);
                        } else {
                            out.write(this.data, pos5, length2);
                        }
                        pos = pos5 + length2;
                        break;
                    case JOINER /*61718*/:
                        out.write("");
                        pos = pos2;
                        break;
                    case COMMENT /*61719*/:
                        int length3 = getIntN(pos2);
                        int pos6 = pos2 + 2;
                        if (out instanceof XConsumer) {
                            ((XConsumer) out).writeComment(this.data, pos6, length3);
                        }
                        pos = pos6 + length3;
                        break;
                    case DOCUMENT_URI /*61720*/:
                        if (out instanceof TreeList) {
                            ((TreeList) out).writeDocumentUri(this.objects[getIntN(pos2)]);
                        }
                        pos = pos2 + 2;
                        break;
                    default:
                        throw new Error("unknown code:" + datum);
                }
            } else {
                out.writeInt(datum - INT_SHORT_ZERO);
                pos = pos2;
            }
        }
        return pos;
    }

    public void toString(String sep, StringBuffer sbuf) {
        int index;
        int pos;
        int pos2 = 0;
        int limit = this.gapStart;
        boolean seen = false;
        boolean inStartTag = false;
        while (true) {
            if (pos2 >= limit) {
                if (pos2 == this.gapStart) {
                    pos2 = this.gapEnd;
                    limit = this.data.length;
                    if (pos2 == limit) {
                        return;
                    }
                } else {
                    return;
                }
            }
            int pos3 = pos2 + 1;
            char datum = this.data[pos2];
            if (datum <= 40959) {
                int start = pos3 - 1;
                int lim = limit;
                while (true) {
                    if (pos3 >= lim) {
                        pos2 = pos3;
                    } else {
                        int pos4 = pos3 + 1;
                        if (this.data[pos3] > 40959) {
                            pos2 = pos4 - 1;
                        } else {
                            pos3 = pos4;
                        }
                    }
                }
                if (inStartTag) {
                    sbuf.append('>');
                    inStartTag = false;
                }
                sbuf.append(this.data, start, pos2 - start);
                seen = false;
            } else if (datum >= OBJECT_REF_SHORT && datum <= 61439) {
                if (inStartTag) {
                    sbuf.append('>');
                    inStartTag = false;
                }
                if (seen) {
                    sbuf.append(sep);
                } else {
                    seen = true;
                }
                sbuf.append(this.objects[datum - OBJECT_REF_SHORT]);
                pos2 = pos3;
            } else if (datum >= BEGIN_ELEMENT_SHORT && datum <= 45055) {
                if (inStartTag) {
                    sbuf.append('>');
                }
                int index2 = datum - BEGIN_ELEMENT_SHORT;
                if (seen) {
                    sbuf.append(sep);
                }
                sbuf.append('<');
                sbuf.append(this.objects[index2].toString());
                pos2 = pos3 + 2;
                seen = false;
                inStartTag = true;
            } else if (datum < 45056 || datum > 57343) {
                switch (datum) {
                    case 61696:
                    case 61697:
                        if (inStartTag) {
                            sbuf.append('>');
                            inStartTag = false;
                        }
                        if (seen) {
                            sbuf.append(sep);
                        } else {
                            seen = true;
                        }
                        sbuf.append(datum != 61696);
                        pos2 = pos3;
                        break;
                    case INT_FOLLOWS /*61698*/:
                        if (inStartTag) {
                            sbuf.append('>');
                            inStartTag = false;
                        }
                        if (seen) {
                            sbuf.append(sep);
                        } else {
                            seen = true;
                        }
                        sbuf.append(getIntN(pos3));
                        pos2 = pos3 + 2;
                        break;
                    case LONG_FOLLOWS /*61699*/:
                        if (inStartTag) {
                            sbuf.append('>');
                            inStartTag = false;
                        }
                        if (seen) {
                            sbuf.append(sep);
                        } else {
                            seen = true;
                        }
                        sbuf.append(getLongN(pos3));
                        pos2 = pos3 + 4;
                        break;
                    case FLOAT_FOLLOWS /*61700*/:
                        if (inStartTag) {
                            sbuf.append('>');
                            inStartTag = false;
                        }
                        if (seen) {
                            sbuf.append(sep);
                        } else {
                            seen = true;
                        }
                        sbuf.append(Float.intBitsToFloat(getIntN(pos3)));
                        pos2 = pos3 + 2;
                        break;
                    case DOUBLE_FOLLOWS /*61701*/:
                        if (inStartTag) {
                            sbuf.append('>');
                            inStartTag = false;
                        }
                        if (seen) {
                            sbuf.append(sep);
                        } else {
                            seen = true;
                        }
                        sbuf.append(Double.longBitsToDouble(getLongN(pos3)));
                        pos2 = pos3 + 4;
                        break;
                    case CHAR_FOLLOWS /*61702*/:
                        if (inStartTag) {
                            sbuf.append('>');
                            inStartTag = false;
                        }
                        sbuf.append(this.data, pos3, (datum + 1) - CHAR_FOLLOWS);
                        seen = false;
                        pos2 = pos3 + 1;
                        break;
                    case BEGIN_ELEMENT_LONG /*61704*/:
                        int index3 = getIntN(pos3);
                        pos2 = pos3 + 2;
                        int index4 = getIntN(index3 + (index3 >= 0 ? pos3 - 1 : this.data.length) + 1);
                        if (inStartTag) {
                            sbuf.append('>');
                        } else if (seen) {
                            sbuf.append(sep);
                        }
                        sbuf.append('<');
                        sbuf.append(this.objects[index4]);
                        seen = false;
                        inStartTag = true;
                        break;
                    case BEGIN_ATTRIBUTE_LONG /*61705*/:
                        int index5 = getIntN(pos3);
                        sbuf.append(' ');
                        sbuf.append(this.objects[index5]);
                        sbuf.append("=\"");
                        inStartTag = false;
                        pos2 = pos3 + 4;
                        break;
                    case END_ATTRIBUTE /*61706*/:
                        sbuf.append('\"');
                        inStartTag = true;
                        seen = false;
                        pos2 = pos3;
                        break;
                    case END_ELEMENT_SHORT /*61707*/:
                    case END_ELEMENT_LONG /*61708*/:
                        if (datum == END_ELEMENT_SHORT) {
                            pos = pos3 + 1;
                            index = this.data[(pos - 2) - this.data[pos3]] - BEGIN_ELEMENT_SHORT;
                        } else {
                            index = getIntN(pos3);
                            pos = pos3 + 6;
                        }
                        if (inStartTag) {
                            sbuf.append("/>");
                        } else {
                            sbuf.append("</");
                            sbuf.append(this.objects[index]);
                            sbuf.append('>');
                        }
                        inStartTag = false;
                        seen = true;
                        break;
                    case 61709:
                    case 61710:
                        if (inStartTag) {
                            sbuf.append('>');
                            inStartTag = false;
                        }
                        if (seen) {
                            sbuf.append(sep);
                        } else {
                            seen = true;
                        }
                        sbuf.append(this.objects[getIntN(pos3)]);
                        pos2 = pos3 + 2;
                        break;
                    case 61711:
                        if (inStartTag) {
                            sbuf.append('>');
                            inStartTag = false;
                        }
                        if (seen) {
                            sbuf.append(sep);
                        } else {
                            seen = true;
                        }
                        sbuf.append(((AbstractSequence) this.objects[getIntN(pos3)]).getIteratorAtPos(getIntN(pos3 + 2)));
                        pos2 = pos3 + 4;
                        break;
                    case BEGIN_DOCUMENT /*61712*/:
                    case BEGIN_ENTITY /*61714*/:
                        pos2 = pos3 + 4;
                        break;
                    case END_DOCUMENT /*61713*/:
                    case END_ENTITY /*61715*/:
                        pos2 = pos3;
                        break;
                    case PROCESSING_INSTRUCTION /*61716*/:
                        if (inStartTag) {
                            sbuf.append('>');
                            inStartTag = false;
                        }
                        sbuf.append("<?");
                        int pos5 = pos3 + 2;
                        sbuf.append(this.objects[getIntN(pos3)]);
                        int index6 = getIntN(pos5);
                        int pos6 = pos5 + 2;
                        if (index6 > 0) {
                            sbuf.append(' ');
                            sbuf.append(this.data, pos6, index6);
                            pos6 += index6;
                        }
                        sbuf.append("?>");
                        break;
                    case CDATA_SECTION /*61717*/:
                        if (inStartTag) {
                            sbuf.append('>');
                            inStartTag = false;
                        }
                        int index7 = getIntN(pos3);
                        int pos7 = pos3 + 2;
                        sbuf.append("<![CDATA[");
                        sbuf.append(this.data, pos7, index7);
                        sbuf.append("]]>");
                        pos2 = pos7 + index7;
                        break;
                    case JOINER /*61718*/:
                        pos2 = pos3;
                        break;
                    case COMMENT /*61719*/:
                        if (inStartTag) {
                            sbuf.append('>');
                            inStartTag = false;
                        }
                        int index8 = getIntN(pos3);
                        int pos8 = pos3 + 2;
                        sbuf.append("<!--");
                        sbuf.append(this.data, pos8, index8);
                        sbuf.append("-->");
                        pos2 = pos8 + index8;
                        break;
                    case DOCUMENT_URI /*61720*/:
                        pos2 = pos3 + 2;
                        break;
                    default:
                        throw new Error("unknown code:" + datum);
                }
            } else {
                if (inStartTag) {
                    sbuf.append('>');
                    inStartTag = false;
                }
                if (seen) {
                    sbuf.append(sep);
                } else {
                    seen = true;
                }
                sbuf.append(datum - INT_SHORT_ZERO);
                pos2 = pos3;
            }
        }
    }

    public boolean hasNext(int ipos) {
        int index = posToDataIndex(ipos);
        if (index == this.data.length) {
            return false;
        }
        char ch = this.data[index];
        if (ch == END_ATTRIBUTE || ch == END_ELEMENT_SHORT || ch == END_ELEMENT_LONG || ch == END_DOCUMENT) {
            return false;
        }
        return true;
    }

    public int getNextKind(int ipos) {
        return getNextKindI(posToDataIndex(ipos));
    }

    public int getNextKindI(int index) {
        if (index == this.data.length) {
            return 0;
        }
        char datum = this.data[index];
        if (datum <= 40959) {
            return 29;
        }
        if (datum >= OBJECT_REF_SHORT && datum <= 61439) {
            return 32;
        }
        if (datum >= BEGIN_ELEMENT_SHORT && datum <= 45055) {
            return 33;
        }
        if ((65280 & datum) == BYTE_PREFIX) {
            return 28;
        }
        if (datum >= 45056 && datum <= 57343) {
            return 22;
        }
        switch (datum) {
            case 61696:
            case 61697:
                return 27;
            case INT_FOLLOWS /*61698*/:
                return 22;
            case LONG_FOLLOWS /*61699*/:
                return 24;
            case FLOAT_FOLLOWS /*61700*/:
                return 25;
            case DOUBLE_FOLLOWS /*61701*/:
                return 26;
            case CHAR_FOLLOWS /*61702*/:
            case BEGIN_DOCUMENT /*61712*/:
                return 34;
            case BEGIN_ELEMENT_LONG /*61704*/:
                return 33;
            case BEGIN_ATTRIBUTE_LONG /*61705*/:
                return 35;
            case END_ATTRIBUTE /*61706*/:
            case END_ELEMENT_SHORT /*61707*/:
            case END_ELEMENT_LONG /*61708*/:
            case END_DOCUMENT /*61713*/:
            case END_ENTITY /*61715*/:
                return 0;
            case BEGIN_ENTITY /*61714*/:
                return getNextKind((index + 5) << 1);
            case PROCESSING_INSTRUCTION /*61716*/:
                return 37;
            case CDATA_SECTION /*61717*/:
                return 31;
            case COMMENT /*61719*/:
                return 36;
            default:
                return 32;
        }
    }

    public Object getNextTypeObject(int ipos) {
        int index;
        int index2 = posToDataIndex(ipos);
        while (index2 != this.data.length) {
            char datum = this.data[index2];
            if (datum != 61714) {
                if (datum >= BEGIN_ELEMENT_SHORT && datum <= 45055) {
                    index = datum - BEGIN_ELEMENT_SHORT;
                } else if (datum == BEGIN_ELEMENT_LONG) {
                    int j = getIntN(index2 + 1);
                    if (j < 0) {
                        index2 = this.data.length;
                    }
                    index = getIntN(j + index2 + 1);
                } else if (datum == BEGIN_ATTRIBUTE_LONG) {
                    index = getIntN(index2 + 1);
                } else if (datum != PROCESSING_INSTRUCTION) {
                    return null;
                } else {
                    index = getIntN(index2 + 1);
                }
                if (index >= 0) {
                    return this.objects[index];
                }
                return null;
            }
            index2 += 5;
        }
        return null;
    }

    public String getNextTypeName(int ipos) {
        Object type = getNextTypeObject(ipos);
        if (type == null) {
            return null;
        }
        return type.toString();
    }

    public Object getPosPrevious(int ipos) {
        if ((ipos & 1) == 0 || ipos == -1) {
            return super.getPosPrevious(ipos);
        }
        return getPosNext(ipos - 3);
    }

    private Object copyToList(int startPosition, int endPosition) {
        return new TreeList(this, startPosition, endPosition);
    }

    public int getPosNextInt(int ipos) {
        int index = posToDataIndex(ipos);
        if (index < this.data.length) {
            char datum = this.data[index];
            if (datum >= 45056 && datum <= 57343) {
                return datum - INT_SHORT_ZERO;
            }
            if (datum == 61698) {
                return getIntN(index + 1);
            }
        }
        return ((Number) getPosNext(ipos)).intValue();
    }

    public Object getPosNext(int ipos) {
        int i;
        int i2;
        int index = posToDataIndex(ipos);
        if (index == this.data.length) {
            return Sequence.eofValue;
        }
        char datum = this.data[index];
        if (datum <= 40959) {
            return Convert.toObject(datum);
        }
        if (datum >= OBJECT_REF_SHORT && datum <= 61439) {
            return this.objects[datum - OBJECT_REF_SHORT];
        }
        if (datum >= BEGIN_ELEMENT_SHORT && datum <= 45055) {
            return copyToList(index, this.data[index + 1] + index + 2);
        }
        if (datum >= 45056 && datum <= 57343) {
            return Convert.toObject(datum - INT_SHORT_ZERO);
        }
        switch (datum) {
            case 61696:
            case 61697:
                return Convert.toObject(datum != 61696);
            case INT_FOLLOWS /*61698*/:
                return Convert.toObject(getIntN(index + 1));
            case LONG_FOLLOWS /*61699*/:
                return Convert.toObject(getLongN(index + 1));
            case FLOAT_FOLLOWS /*61700*/:
                return Convert.toObject(Float.intBitsToFloat(getIntN(index + 1)));
            case DOUBLE_FOLLOWS /*61701*/:
                return Convert.toObject(Double.longBitsToDouble(getLongN(index + 1)));
            case CHAR_FOLLOWS /*61702*/:
                return Convert.toObject(this.data[index + 1]);
            case BEGIN_ELEMENT_LONG /*61704*/:
                int end_offset = getIntN(index + 1);
                if (end_offset < 0) {
                    i = this.data.length;
                } else {
                    i = index;
                }
                return copyToList(index, end_offset + i + 7);
            case BEGIN_ATTRIBUTE_LONG /*61705*/:
                int end_offset2 = getIntN(index + 3);
                if (end_offset2 < 0) {
                    i2 = this.data.length;
                } else {
                    i2 = index;
                }
                return copyToList(index, end_offset2 + i2 + 1);
            case END_ATTRIBUTE /*61706*/:
            case END_ELEMENT_SHORT /*61707*/:
            case END_ELEMENT_LONG /*61708*/:
            case END_DOCUMENT /*61713*/:
                return Sequence.eofValue;
            case 61709:
            case 61710:
                return this.objects[getIntN(index + 1)];
            case 61711:
                return ((AbstractSequence) this.objects[getIntN(index + 1)]).getIteratorAtPos(getIntN(index + 3));
            case BEGIN_DOCUMENT /*61712*/:
                int end_offset3 = getIntN(index + 1);
                return copyToList(index, end_offset3 + (end_offset3 < 0 ? this.data.length : index) + 1);
            case JOINER /*61718*/:
                return "";
            default:
                throw unsupported("getPosNext, code=" + Integer.toHexString(datum));
        }
    }

    public void stringValue(int startIndex, int endIndex, StringBuffer sbuf) {
        int index = startIndex;
        while (index < endIndex && index >= 0) {
            index = stringValue(false, index, sbuf);
        }
    }

    public int stringValue(int index, StringBuffer sbuf) {
        int next = nextNodeIndex(index, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        if (next <= index) {
            return stringValue(false, index, sbuf);
        }
        stringValue(index, next, sbuf);
        return index;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00b7, code lost:
        r5 = getIntN(r14);
        r14 = r14 + 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00bd, code lost:
        if (r13 == false) goto L_0x00c4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00c2, code lost:
        if (r0 != CDATA_SECTION) goto L_0x00c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00c4, code lost:
        r15.append(r12.data, r14, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:?, code lost:
        return r14 + r5;
     */
    public int stringValue(boolean inElement, int index, StringBuffer sbuf) {
        Object value = null;
        int doChildren = 0;
        if (index >= this.gapStart) {
            index += this.gapEnd - this.gapStart;
        }
        if (index == this.data.length) {
            return -1;
        }
        char datum = this.data[index];
        int index2 = index + 1;
        if (datum <= 40959) {
            sbuf.append(datum);
            return index2;
        }
        if (datum >= OBJECT_REF_SHORT && datum <= 61439) {
            if (0 != 0) {
                sbuf.append(' ');
            }
            value = this.objects[datum - OBJECT_REF_SHORT];
        } else if (datum >= BEGIN_ELEMENT_SHORT && datum <= 45055) {
            doChildren = index2 + 2;
            index2 = this.data[index2] + index2 + 1;
        } else if ((65280 & datum) == BYTE_PREFIX) {
            sbuf.append(datum & 255);
            return index2;
        } else if (datum < 45056 || datum > 57343) {
            switch (datum) {
                case 61696:
                case 61697:
                    if (0 != 0) {
                        sbuf.append(' ');
                    }
                    sbuf.append(datum != 61696);
                    return index2;
                case INT_FOLLOWS /*61698*/:
                    if (0 != 0) {
                        sbuf.append(' ');
                    }
                    sbuf.append(getIntN(index2));
                    return index2 + 2;
                case LONG_FOLLOWS /*61699*/:
                    if (0 != 0) {
                        sbuf.append(' ');
                    }
                    sbuf.append(getLongN(index2));
                    return index2 + 4;
                case FLOAT_FOLLOWS /*61700*/:
                    if (0 != 0) {
                        sbuf.append(' ');
                    }
                    sbuf.append(Float.intBitsToFloat(getIntN(index2)));
                    return index2 + 2;
                case DOUBLE_FOLLOWS /*61701*/:
                    if (0 != 0) {
                        sbuf.append(' ');
                    }
                    sbuf.append(Double.longBitsToDouble(getLongN(index2)));
                    return index2 + 4;
                case CHAR_FOLLOWS /*61702*/:
                    sbuf.append(this.data[index2]);
                    return index2 + 1;
                case BEGIN_ELEMENT_LONG /*61704*/:
                    doChildren = index2 + 2;
                    int j = getIntN(index2);
                    index2 = j + (j < 0 ? this.data.length : index2 - 1) + 7;
                    break;
                case BEGIN_ATTRIBUTE_LONG /*61705*/:
                    if (!inElement) {
                        doChildren = index2 + 4;
                    }
                    int end = getIntN(index2 + 2);
                    if (end < 0) {
                        index2 = this.data.length + 1;
                    }
                    index2 += end;
                    break;
                case END_ATTRIBUTE /*61706*/:
                case END_ELEMENT_SHORT /*61707*/:
                case END_ELEMENT_LONG /*61708*/:
                case END_DOCUMENT /*61713*/:
                case END_ENTITY /*61715*/:
                    return -1;
                case 61711:
                    ((TreeList) ((AbstractSequence) this.objects[getIntN(index2)])).stringValue(inElement, getIntN(index2 + 2) >> 1, sbuf);
                    index2 += 4;
                    break;
                case BEGIN_DOCUMENT /*61712*/:
                case BEGIN_ENTITY /*61714*/:
                    doChildren = index2 + 4;
                    index2 = nextDataIndex(index2 - 1);
                    break;
                case PROCESSING_INSTRUCTION /*61716*/:
                    index2 += 2;
                    break;
                case CDATA_SECTION /*61717*/:
                case COMMENT /*61719*/:
                    break;
                case JOINER /*61718*/:
                    break;
                case DOCUMENT_URI /*61720*/:
                    return index2 + 2;
                default:
                    throw new Error("unimplemented: " + Integer.toHexString(datum) + " at:" + index2);
            }
        } else {
            sbuf.append(datum - INT_SHORT_ZERO);
            return index2;
        }
        if (value != null) {
            sbuf.append(value);
        }
        if (doChildren > 0) {
            do {
                doChildren = stringValue(true, doChildren, sbuf);
            } while (doChildren >= 0);
        }
        return index2;
    }

    public int createRelativePos(int istart, int offset, boolean isAfter) {
        if (isAfter) {
            if (offset == 0) {
                if ((istart & 1) != 0) {
                    return istart;
                }
                if (istart == 0) {
                    return 1;
                }
            }
            offset--;
        }
        if (offset < 0) {
            throw unsupported("backwards createRelativePos");
        }
        int pos = posToDataIndex(istart);
        do {
            offset--;
            if (offset >= 0) {
                pos = nextDataIndex(pos);
            } else {
                if (pos >= this.gapEnd) {
                    pos -= this.gapEnd - this.gapStart;
                }
                return isAfter ? ((pos + 1) << 1) | 1 : pos << 1;
            }
        } while (pos >= 0);
        throw new IndexOutOfBoundsException();
    }

    public final int nextNodeIndex(int pos, int limit) {
        if ((Integer.MIN_VALUE | limit) == -1) {
            limit = this.data.length;
        }
        while (true) {
            if (pos == this.gapStart) {
                pos = this.gapEnd;
            }
            if (pos < limit) {
                char datum = this.data[pos];
                if (datum <= 40959 || ((datum >= OBJECT_REF_SHORT && datum <= 61439) || ((datum >= 45056 && datum <= 57343) || (65280 & datum) == BYTE_PREFIX))) {
                    pos++;
                } else if (datum < BEGIN_ELEMENT_SHORT || datum > 45055) {
                    switch (datum) {
                        case BEGIN_ELEMENT_LONG /*61704*/:
                        case BEGIN_ATTRIBUTE_LONG /*61705*/:
                        case END_ATTRIBUTE /*61706*/:
                        case END_ELEMENT_SHORT /*61707*/:
                        case END_ELEMENT_LONG /*61708*/:
                        case BEGIN_DOCUMENT /*61712*/:
                        case END_DOCUMENT /*61713*/:
                        case END_ENTITY /*61715*/:
                        case PROCESSING_INSTRUCTION /*61716*/:
                        case COMMENT /*61719*/:
                            break;
                        case BEGIN_ENTITY /*61714*/:
                            pos += 5;
                            continue;
                        case JOINER /*61718*/:
                            pos++;
                            continue;
                        case DOCUMENT_URI /*61720*/:
                            pos += 3;
                            continue;
                        default:
                            pos = nextDataIndex(pos);
                            continue;
                    }
                }
            }
        }
        return pos;
    }

    public int nextMatching(int startPos, ItemPredicate predicate, int endPos, boolean descend) {
        boolean checkText;
        boolean checkNode;
        boolean checkElement;
        char datum;
        int next;
        int next2;
        int next3;
        int i;
        int start = posToDataIndex(startPos);
        int limit = posToDataIndex(endPos);
        int pos = start;
        if (predicate instanceof NodePredicate) {
            pos = nextNodeIndex(pos, limit);
        }
        if (predicate instanceof ElementPredicate) {
            checkNode = true;
            checkElement = true;
            checkText = false;
        } else if (predicate instanceof AttributePredicate) {
            checkNode = true;
            checkElement = false;
            checkText = false;
        } else {
            checkNode = true;
            checkElement = true;
            checkText = true;
        }
        while (true) {
            if (pos == this.gapStart) {
                pos = this.gapEnd;
            }
            if (pos >= limit && limit != -1) {
                return 0;
            }
            datum = this.data[pos];
            if (datum <= 40959 || ((datum >= OBJECT_REF_SHORT && datum <= 61439) || (datum >= 45056 && datum <= 57343))) {
                if (checkText) {
                    if (predicate.isInstancePos(this, pos << 1)) {
                        if (pos >= this.gapEnd) {
                            pos -= this.gapEnd - this.gapStart;
                        }
                        return pos << 1;
                    }
                }
                next = pos + 1;
            } else {
                switch (datum) {
                    case 61696:
                    case 61697:
                        next = pos + 1;
                        if (!checkText) {
                            continue;
                        }
                    case INT_FOLLOWS /*61698*/:
                    case FLOAT_FOLLOWS /*61700*/:
                    case 61709:
                    case 61710:
                        next = pos + 3;
                        if (!checkText) {
                            continue;
                        }
                    case LONG_FOLLOWS /*61699*/:
                    case DOUBLE_FOLLOWS /*61701*/:
                        next = pos + 5;
                        if (!checkText) {
                            continue;
                        }
                    case CHAR_FOLLOWS /*61702*/:
                        next = pos + 2;
                        continue;
                    case BEGIN_ELEMENT_LONG /*61704*/:
                        if (descend) {
                            next2 = pos + 3;
                        } else {
                            int j = getIntN(pos + 1);
                            next2 = (j < 0 ? this.data.length : pos) + j + 7;
                        }
                        if (!checkElement) {
                            continue;
                        }
                    case BEGIN_ATTRIBUTE_LONG /*61705*/:
                        if (checkNode) {
                            int j2 = getIntN(pos + 3);
                            int i2 = j2 + 1;
                            if (j2 < 0) {
                                i = this.data.length;
                            } else {
                                i = pos;
                            }
                            next3 = i2 + i;
                        } else {
                            next3 = pos + 5;
                        }
                        if (0 == 0) {
                            continue;
                        }
                    case END_ATTRIBUTE /*61706*/:
                    case END_DOCUMENT /*61713*/:
                        if (!descend) {
                            return 0;
                        }
                        break;
                    case END_ELEMENT_SHORT /*61707*/:
                        if (!descend) {
                            return 0;
                        }
                        next = pos + 2;
                        continue;
                    case END_ELEMENT_LONG /*61708*/:
                        if (!descend) {
                            return 0;
                        }
                        next = pos + 7;
                        continue;
                    case 61711:
                        next = pos + 5;
                        if (!checkText) {
                            continue;
                        }
                    case BEGIN_DOCUMENT /*61712*/:
                        next = pos + 5;
                        if (!checkNode) {
                            continue;
                        }
                    case BEGIN_ENTITY /*61714*/:
                        next = pos + 5;
                        continue;
                    case END_ENTITY /*61715*/:
                        break;
                    case PROCESSING_INSTRUCTION /*61716*/:
                        next = pos + 5 + getIntN(pos + 3);
                        if (!checkNode) {
                            continue;
                        }
                    case CDATA_SECTION /*61717*/:
                        next = pos + 3 + getIntN(pos + 1);
                        if (!checkText) {
                            continue;
                        }
                    case JOINER /*61718*/:
                        next = pos + 1;
                        continue;
                    case COMMENT /*61719*/:
                        next = pos + 3 + getIntN(pos + 1);
                        if (!checkNode) {
                            continue;
                        }
                    case DOCUMENT_URI /*61720*/:
                        next = pos + 3;
                        continue;
                    default:
                        if (datum < BEGIN_ELEMENT_SHORT || datum > 45055) {
                            break;
                        } else {
                            if (descend) {
                                next = pos + 3;
                            } else {
                                next = this.data[pos + 1] + pos + 2;
                            }
                            if (!checkElement) {
                                continue;
                            }
                            if (pos > start) {
                                if (!predicate.isInstancePos(this, pos << 1)) {
                                    break;
                                } else {
                                    if (pos >= this.gapEnd) {
                                        pos -= this.gapEnd - this.gapStart;
                                    }
                                    return pos << 1;
                                }
                            } else {
                                continue;
                            }
                        }
                }
                next = pos + 1;
            }
            pos = next;
        }
        throw new Error("unknown code:" + datum);
    }

    public int nextPos(int position) {
        int index = posToDataIndex(position);
        if (index == this.data.length) {
            return 0;
        }
        if (index >= this.gapEnd) {
            index -= this.gapEnd - this.gapStart;
        }
        return (index << 1) + 3;
    }

    public final int nextDataIndex(int pos) {
        int pos2;
        if (pos == this.gapStart) {
            pos = this.gapEnd;
        }
        if (pos == this.data.length) {
            return -1;
        }
        int pos3 = pos + 1;
        char datum = this.data[pos];
        if (datum <= 40959 || ((datum >= OBJECT_REF_SHORT && datum <= 61439) || (datum >= 45056 && datum <= 57343))) {
            int i = pos3;
            return pos3;
        } else if (datum < BEGIN_ELEMENT_SHORT || datum > 45055) {
            switch (datum) {
                case 61696:
                case 61697:
                case JOINER /*61718*/:
                    int i2 = pos3;
                    return pos3;
                case INT_FOLLOWS /*61698*/:
                case FLOAT_FOLLOWS /*61700*/:
                case 61709:
                case 61710:
                    int i3 = pos3;
                    return pos3 + 2;
                case LONG_FOLLOWS /*61699*/:
                case DOUBLE_FOLLOWS /*61701*/:
                    int i4 = pos3;
                    return pos3 + 4;
                case CHAR_FOLLOWS /*61702*/:
                    int i5 = pos3;
                    return pos3 + 1;
                case BEGIN_ELEMENT_LONG /*61704*/:
                    int j = getIntN(pos3);
                    int i6 = pos3;
                    return j + (j < 0 ? this.data.length : pos3 - 1) + 7;
                case BEGIN_ATTRIBUTE_LONG /*61705*/:
                    int j2 = getIntN(pos3 + 2);
                    int i7 = pos3;
                    return j2 + (j2 < 0 ? this.data.length : pos3 - 1) + 1;
                case END_ATTRIBUTE /*61706*/:
                case END_ELEMENT_SHORT /*61707*/:
                case END_ELEMENT_LONG /*61708*/:
                case END_DOCUMENT /*61713*/:
                case END_ENTITY /*61715*/:
                    return -1;
                case 61711:
                    int i8 = pos3;
                    return pos3 + 4;
                case BEGIN_DOCUMENT /*61712*/:
                    int j3 = getIntN(pos3);
                    int i9 = pos3;
                    return j3 + (j3 < 0 ? this.data.length : pos3 - 1) + 1;
                case BEGIN_ENTITY /*61714*/:
                    int j4 = pos3 + 4;
                    while (true) {
                        if (j4 == this.gapStart) {
                            j4 = this.gapEnd;
                        }
                        if (j4 == this.data.length) {
                            int i10 = pos3;
                            return -1;
                        } else if (this.data[j4] == END_ENTITY) {
                            int i11 = pos3;
                            return j4 + 1;
                        } else {
                            j4 = nextDataIndex(j4);
                        }
                    }
                case PROCESSING_INSTRUCTION /*61716*/:
                    pos2 = pos3 + 2;
                    break;
                case CDATA_SECTION /*61717*/:
                case COMMENT /*61719*/:
                    pos2 = pos3;
                    break;
                default:
                    throw new Error("unknown code:" + Integer.toHexString(datum));
            }
            return pos2 + 2 + getIntN(pos2);
        } else {
            int i12 = pos3;
            return this.data[pos3] + pos3 + 1;
        }
    }

    public Object documentUriOfPos(int pos) {
        int index = posToDataIndex(pos);
        if (index == this.data.length || this.data[index] != BEGIN_DOCUMENT) {
            return null;
        }
        int next = index + 5;
        if (next == this.gapStart) {
            next = this.gapEnd;
        }
        if (next >= this.data.length || this.data[next] != DOCUMENT_URI) {
            return null;
        }
        return this.objects[getIntN(next + 1)];
    }

    public int compare(int ipos1, int ipos2) {
        int i1 = posToDataIndex(ipos1);
        int i2 = posToDataIndex(ipos2);
        if (i1 < i2) {
            return -1;
        }
        return i1 > i2 ? 1 : 0;
    }

    /* access modifiers changed from: protected */
    public int getIndexDifference(int ipos1, int ipos0) {
        int i0 = posToDataIndex(ipos0);
        int i1 = posToDataIndex(ipos1);
        boolean negate = false;
        if (i0 > i1) {
            negate = true;
            int i = i1;
            i1 = i0;
            i0 = i;
        }
        int i2 = 0;
        while (i0 < i1) {
            i0 = nextDataIndex(i0);
            i2++;
        }
        return negate ? -i2 : i2;
    }

    public int hashCode() {
        return System.identityHashCode(this);
    }

    public void consume(Consumer out) {
        consumeIRange(0, this.data.length, out);
    }

    public void statistics() {
        PrintWriter out = new PrintWriter(System.out);
        statistics(out);
        out.flush();
    }

    public void statistics(PrintWriter out) {
        out.print("data array length: ");
        out.println(this.data.length);
        out.print("data array gap: ");
        out.println(this.gapEnd - this.gapStart);
        out.print("object array length: ");
        out.println(this.objects.length);
    }

    public void dump() {
        PrintWriter out = new PrintWriter(System.out);
        dump(out);
        out.flush();
    }

    public void dump(PrintWriter out) {
        out.println(getClass().getName() + " @" + Integer.toHexString(System.identityHashCode(this)) + " gapStart:" + this.gapStart + " gapEnd:" + this.gapEnd + " length:" + this.data.length);
        dump(out, 0, this.data.length);
    }

    public void dump(PrintWriter out, int start, int limit) {
        int i;
        int toskip = 0;
        int i2 = start;
        while (i2 < limit) {
            if (i2 < this.gapStart || i2 >= this.gapEnd) {
                char ch = this.data[i2];
                out.print("" + i2 + ": 0x" + Integer.toHexString(ch) + '=' + ((short) ch));
                toskip--;
                if (toskip < 0) {
                    if (ch <= 40959) {
                        if (ch >= ' ' && ch < 127) {
                            out.print("='" + ((char) ch) + "'");
                        } else if (ch == 10) {
                            out.print("='\\n'");
                        } else {
                            out.print("='\\u" + Integer.toHexString(ch) + "'");
                        }
                    } else if (ch >= OBJECT_REF_SHORT && ch <= 61439) {
                        int ch2 = ch - OBJECT_REF_SHORT;
                        Object obj = this.objects[ch2];
                        out.print("=Object#" + ch2 + '=' + obj + ':' + obj.getClass().getName() + '@' + Integer.toHexString(System.identityHashCode(obj)));
                    } else if (ch >= BEGIN_ELEMENT_SHORT && ch <= 45055) {
                        int ch3 = ch - BEGIN_ELEMENT_SHORT;
                        out.print("=BEGIN_ELEMENT_SHORT end:" + (this.data[i2 + 1] + i2) + " index#" + ch3 + "=<" + this.objects[ch3] + '>');
                        toskip = 2;
                    } else if (ch < 45056 || ch > 57343) {
                        switch (ch) {
                            case 61696:
                                out.print("= false");
                                break;
                            case 61697:
                                out.print("= true");
                                break;
                            case INT_FOLLOWS /*61698*/:
                                out.print("=INT_FOLLOWS value:" + getIntN(i2 + 1));
                                toskip = 2;
                                break;
                            case LONG_FOLLOWS /*61699*/:
                                out.print("=LONG_FOLLOWS value:" + getLongN(i2 + 1));
                                toskip = 4;
                                break;
                            case FLOAT_FOLLOWS /*61700*/:
                                out.write("=FLOAT_FOLLOWS value:" + Float.intBitsToFloat(getIntN(i2 + 1)));
                                toskip = 2;
                                break;
                            case DOUBLE_FOLLOWS /*61701*/:
                                out.print("=DOUBLE_FOLLOWS value:" + Double.longBitsToDouble(getLongN(i2 + 1)));
                                toskip = 4;
                                break;
                            case CHAR_FOLLOWS /*61702*/:
                                out.print("=CHAR_FOLLOWS");
                                toskip = 1;
                                break;
                            case BEGIN_ELEMENT_LONG /*61704*/:
                                int j = getIntN(i2 + 1);
                                int j2 = j + (j < 0 ? this.data.length : i2);
                                out.print("=BEGIN_ELEMENT_LONG end:");
                                out.print(j2);
                                int j3 = getIntN(j2 + 1);
                                out.print(" -> #");
                                out.print(j3);
                                if (j3 < 0 || j3 + 1 >= this.objects.length) {
                                    out.print("=<out-of-bounds>");
                                } else {
                                    out.print("=<" + this.objects[j3] + '>');
                                }
                                toskip = 2;
                                break;
                            case BEGIN_ATTRIBUTE_LONG /*61705*/:
                                int j4 = getIntN(i2 + 1);
                                out.print("=BEGIN_ATTRIBUTE name:" + j4 + "=" + this.objects[j4]);
                                int j5 = getIntN(i2 + 3);
                                if (j5 < 0) {
                                    i = this.data.length;
                                } else {
                                    i = i2;
                                }
                                out.print(" end:" + (j5 + i));
                                toskip = 4;
                                break;
                            case END_ATTRIBUTE /*61706*/:
                                out.print("=END_ATTRIBUTE");
                                break;
                            case END_ELEMENT_SHORT /*61707*/:
                                out.print("=END_ELEMENT_SHORT begin:");
                                int j6 = i2 - this.data[i2 + 1];
                                out.print(j6);
                                int j7 = this.data[j6] - BEGIN_ELEMENT_SHORT;
                                out.print(" -> #");
                                out.print(j7);
                                out.print("=<");
                                out.print(this.objects[j7]);
                                out.print('>');
                                toskip = 1;
                                break;
                            case END_ELEMENT_LONG /*61708*/:
                                int j8 = getIntN(i2 + 1);
                                out.print("=END_ELEMENT_LONG name:" + j8 + "=<" + this.objects[j8] + '>');
                                int j9 = getIntN(i2 + 3);
                                if (j9 < 0) {
                                    j9 += i2;
                                }
                                out.print(" begin:" + j9);
                                int j10 = getIntN(i2 + 5);
                                if (j10 < 0) {
                                    j10 += i2;
                                }
                                out.print(" parent:" + j10);
                                toskip = 6;
                                break;
                            case 61709:
                            case 61710:
                                toskip = 2;
                                break;
                            case 61711:
                                out.print("=POSITION_PAIR_FOLLOWS seq:");
                                int j11 = getIntN(i2 + 1);
                                out.print(j11);
                                out.print('=');
                                Object seq = this.objects[j11];
                                out.print(seq == null ? null : seq.getClass().getName());
                                out.print('@');
                                if (seq == null) {
                                    out.print("null");
                                } else {
                                    out.print(Integer.toHexString(System.identityHashCode(seq)));
                                }
                                out.print(" ipos:");
                                out.print(getIntN(i2 + 3));
                                toskip = 4;
                                break;
                            case BEGIN_DOCUMENT /*61712*/:
                                int j12 = getIntN(i2 + 1);
                                int j13 = j12 + (j12 < 0 ? this.data.length : i2);
                                out.print("=BEGIN_DOCUMENT end:");
                                out.print(j13);
                                out.print(" parent:");
                                out.print(getIntN(i2 + 3));
                                toskip = 4;
                                break;
                            case END_DOCUMENT /*61713*/:
                                out.print("=END_DOCUMENT");
                                break;
                            case BEGIN_ENTITY /*61714*/:
                                int j14 = getIntN(i2 + 1);
                                out.print("=BEGIN_ENTITY base:");
                                out.print(j14);
                                out.print(" parent:");
                                out.print(getIntN(i2 + 3));
                                toskip = 4;
                                break;
                            case END_ENTITY /*61715*/:
                                out.print("=END_ENTITY");
                                break;
                            case PROCESSING_INSTRUCTION /*61716*/:
                                out.print("=PROCESSING_INSTRUCTION: ");
                                out.print(this.objects[getIntN(i2 + 1)]);
                                out.print(" '");
                                int j15 = getIntN(i2 + 3);
                                out.write(this.data, i2 + 5, j15);
                                out.print('\'');
                                toskip = j15 + 4;
                                break;
                            case CDATA_SECTION /*61717*/:
                                out.print("=CDATA: '");
                                int j16 = getIntN(i2 + 1);
                                out.write(this.data, i2 + 3, j16);
                                out.print('\'');
                                toskip = j16 + 2;
                                break;
                            case JOINER /*61718*/:
                                out.print("= joiner");
                                break;
                            case COMMENT /*61719*/:
                                out.print("=COMMENT: '");
                                int j17 = getIntN(i2 + 1);
                                out.write(this.data, i2 + 3, j17);
                                out.print('\'');
                                toskip = j17 + 2;
                                break;
                            case DOCUMENT_URI /*61720*/:
                                out.print("=DOCUMENT_URI: ");
                                out.print(this.objects[getIntN(i2 + 1)]);
                                toskip = 2;
                                break;
                        }
                    } else {
                        out.print("= INT_SHORT:" + (ch - INT_SHORT_ZERO));
                    }
                }
                out.println();
                if (1 != 0 && toskip > 0) {
                    i2 += toskip;
                    toskip = 0;
                }
            }
            i2++;
        }
    }
}
