package gnu.text;

import gnu.lists.LList;
import gnu.mapping.ThreadLocation;
import java.io.IOException;
import java.io.Writer;

public class PrettyWriter extends Writer {
    private static final int BLOCK_PER_LINE_PREFIX_END = -3;
    private static final int BLOCK_PREFIX_LENGTH = -4;
    private static final int BLOCK_SECTION_COLUMN = -2;
    private static final int BLOCK_SECTION_START_LINE = -6;
    private static final int BLOCK_START_COLUMN = -1;
    private static final int BLOCK_SUFFIX_LENGTH = -5;
    private static final int LOGICAL_BLOCK_LENGTH = 6;
    public static final int NEWLINE_FILL = 70;
    public static final int NEWLINE_LINEAR = 78;
    public static final int NEWLINE_LITERAL = 76;
    public static final int NEWLINE_MANDATORY = 82;
    public static final int NEWLINE_MISER = 77;
    public static final int NEWLINE_SPACE = 83;
    static final int QITEM_BASE_SIZE = 2;
    static final int QITEM_BLOCK_END_SIZE = 2;
    static final int QITEM_BLOCK_END_TYPE = 5;
    static final int QITEM_BLOCK_START_BLOCK_END = 4;
    static final int QITEM_BLOCK_START_PREFIX = 5;
    static final int QITEM_BLOCK_START_SIZE = 7;
    static final int QITEM_BLOCK_START_SUFFIX = 6;
    static final int QITEM_BLOCK_START_TYPE = 4;
    static final int QITEM_INDENTATION_AMOUNT = 3;
    static final char QITEM_INDENTATION_BLOCK = 'B';
    static final char QITEM_INDENTATION_CURRENT = 'C';
    static final int QITEM_INDENTATION_KIND = 2;
    static final int QITEM_INDENTATION_SIZE = 4;
    static final int QITEM_INDENTATION_TYPE = 3;
    static final int QITEM_NEWLINE_KIND = 4;
    static final int QITEM_NEWLINE_SIZE = 5;
    static final int QITEM_NEWLINE_TYPE = 2;
    static final int QITEM_NOP_TYPE = 0;
    static final int QITEM_POSN = 1;
    static final int QITEM_SECTION_START_DEPTH = 2;
    static final int QITEM_SECTION_START_SECTION_END = 3;
    static final int QITEM_SECTION_START_SIZE = 4;
    static final int QITEM_TAB_COLINC = 4;
    static final int QITEM_TAB_COLNUM = 3;
    static final int QITEM_TAB_FLAGS = 2;
    static final int QITEM_TAB_IS_RELATIVE = 2;
    static final int QITEM_TAB_IS_SECTION = 1;
    static final int QITEM_TAB_SIZE = 5;
    static final int QITEM_TAB_TYPE = 6;
    static final int QITEM_TYPE_AND_SIZE = 0;
    static final int QUEUE_INIT_ALLOC_SIZE = 300;
    public static ThreadLocation indentLoc = new ThreadLocation("indent");
    public static int initialBufferSize = 126;
    public static ThreadLocation lineLengthLoc = new ThreadLocation("line-length");
    public static ThreadLocation miserWidthLoc = new ThreadLocation("miser-width");
    int blockDepth = 6;
    int[] blocks = new int[60];
    public char[] buffer = new char[initialBufferSize];
    public int bufferFillPointer;
    int bufferOffset;
    int bufferStartColumn;
    int currentBlock = -1;
    int lineLength = 80;
    int lineNumber;
    int miserWidth = 40;
    protected Writer out;
    public int pendingBlocksCount;
    char[] prefix = new char[initialBufferSize];
    int prettyPrintingMode;
    int[] queueInts = new int[QUEUE_INIT_ALLOC_SIZE];
    int queueSize;
    String[] queueStrings = new String[QUEUE_INIT_ALLOC_SIZE];
    int queueTail;
    char[] suffix = new char[initialBufferSize];
    boolean wordEndSeen;

    public PrettyWriter(Writer out2) {
        this.out = out2;
        this.prettyPrintingMode = 1;
    }

    public PrettyWriter(Writer out2, int lineLength2) {
        int i = 1;
        this.out = out2;
        this.lineLength = lineLength2;
        if (lineLength2 <= 1) {
            i = 0;
        }
        this.prettyPrintingMode = i;
    }

    public PrettyWriter(Writer out2, boolean prettyPrintingMode2) {
        this.out = out2;
        this.prettyPrintingMode = prettyPrintingMode2 ? 1 : 0;
    }

    public void setPrettyPrintingMode(int mode) {
        this.prettyPrintingMode = mode;
    }

    public int getPrettyPrintingMode() {
        return this.prettyPrintingMode;
    }

    public boolean isPrettyPrinting() {
        return this.prettyPrintingMode > 0;
    }

    public void setPrettyPrinting(boolean mode) {
        this.prettyPrintingMode = mode ? 0 : 1;
    }

    private int indexPosn(int index) {
        return this.bufferOffset + index;
    }

    private int posnIndex(int posn) {
        return posn - this.bufferOffset;
    }

    private int posnColumn(int posn) {
        return indexColumn(posnIndex(posn));
    }

    private int getQueueType(int index) {
        return this.queueInts[index] & 255;
    }

    private int getQueueSize(int index) {
        return this.queueInts[index] >> 16;
    }

    private int getSectionColumn() {
        return this.blocks[this.blockDepth - 2];
    }

    private int getStartColumn() {
        return this.blocks[this.blockDepth - 1];
    }

    private int getPerLinePrefixEnd() {
        return this.blocks[this.blockDepth - 3];
    }

    private int getPrefixLength() {
        return this.blocks[this.blockDepth - 4];
    }

    private int getSuffixLength() {
        return this.blocks[this.blockDepth - 5];
    }

    private int getSectionStartLine() {
        return this.blocks[this.blockDepth - 6];
    }

    public void writeWordEnd() {
        this.wordEndSeen = true;
    }

    public void writeWordStart() {
        if (this.wordEndSeen) {
            write(32);
        }
        this.wordEndSeen = false;
    }

    public void clearWordEnd() {
        this.wordEndSeen = false;
    }

    public void write(int ch) {
        this.wordEndSeen = false;
        if (ch != 10 || this.prettyPrintingMode <= 0) {
            ensureSpaceInBuffer(1);
            int fillPointer = this.bufferFillPointer;
            this.buffer[fillPointer] = (char) ch;
            this.bufferFillPointer = fillPointer + 1;
            if (ch == 32 && this.prettyPrintingMode > 1 && this.currentBlock < 0) {
                enqueueNewline(83);
                return;
            }
            return;
        }
        enqueueNewline(76);
    }

    public void write(String str) {
        write(str, 0, str.length());
    }

    public void write(String str, int start, int count) {
        int fillPointer;
        int start2;
        this.wordEndSeen = false;
        while (count > 0) {
            int cnt = count;
            int available = ensureSpaceInBuffer(count);
            if (cnt > available) {
                cnt = available;
            }
            int fillPointer2 = this.bufferFillPointer;
            count -= cnt;
            while (true) {
                fillPointer = fillPointer2;
                start2 = start;
                cnt--;
                if (cnt < 0) {
                    break;
                }
                start = start2 + 1;
                char ch = str.charAt(start2);
                if (ch != 10 || this.prettyPrintingMode <= 0) {
                    fillPointer2 = fillPointer + 1;
                    this.buffer[fillPointer] = ch;
                    if (ch == ' ' && this.prettyPrintingMode > 1 && this.currentBlock < 0) {
                        this.bufferFillPointer = fillPointer2;
                        enqueueNewline(83);
                        fillPointer2 = this.bufferFillPointer;
                    }
                } else {
                    this.bufferFillPointer = fillPointer;
                    enqueueNewline(76);
                    fillPointer2 = this.bufferFillPointer;
                }
            }
            this.bufferFillPointer = fillPointer;
            start = start2;
        }
    }

    public void write(char[] str) {
        write(str, 0, str.length);
    }

    public void write(char[] str, int start, int count) {
        int cnt;
        int start2;
        char c;
        this.wordEndSeen = false;
        int end = start + count;
        while (count > 0) {
            int i = start;
            while (true) {
                if (i < end) {
                    if (this.prettyPrintingMode > 0) {
                        c = str[i];
                        if (c == 10 || (c == ' ' && this.currentBlock < 0)) {
                            write(str, start, i - start);
                            write((int) c);
                            start = i + 1;
                            count = end - start;
                        }
                    }
                    i++;
                } else {
                    while (true) {
                        int available = ensureSpaceInBuffer(count);
                        if (available < count) {
                            cnt = available;
                        } else {
                            cnt = count;
                        }
                        int fillPointer = this.bufferFillPointer;
                        int newFillPtr = fillPointer + cnt;
                        int i2 = fillPointer;
                        start2 = start;
                        while (i2 < newFillPtr) {
                            int start3 = start2 + 1;
                            this.buffer[i2] = str[start2];
                            i2++;
                            start2 = start3;
                        }
                        this.bufferFillPointer = newFillPtr;
                        count -= cnt;
                        if (count == 0) {
                            break;
                        }
                        start = start2;
                    }
                    start = start2;
                }
            }
            write(str, start, i - start);
            write((int) c);
            start = i + 1;
            count = end - start;
        }
    }

    private void pushLogicalBlock(int column, int perLineEnd, int prefixLength, int suffixLength, int sectionStartLine) {
        int newLength = this.blockDepth + 6;
        if (newLength >= this.blocks.length) {
            int[] newBlocks = new int[(this.blocks.length * 2)];
            System.arraycopy(this.blocks, 0, newBlocks, 0, this.blockDepth);
            this.blocks = newBlocks;
        }
        this.blockDepth = newLength;
        this.blocks[this.blockDepth - 1] = column;
        this.blocks[this.blockDepth - 2] = column;
        this.blocks[this.blockDepth - 3] = perLineEnd;
        this.blocks[this.blockDepth - 4] = prefixLength;
        this.blocks[this.blockDepth - 5] = suffixLength;
        this.blocks[this.blockDepth - 6] = sectionStartLine;
    }

    /* access modifiers changed from: 0000 */
    public void reallyStartLogicalBlock(int column, String prefix2, String suffix2) {
        int perLineEnd = getPerLinePrefixEnd();
        int prefixLength = getPrefixLength();
        int suffixLength = getSuffixLength();
        pushLogicalBlock(column, perLineEnd, prefixLength, suffixLength, this.lineNumber);
        setIndentation(column);
        if (prefix2 != null) {
            this.blocks[this.blockDepth - 3] = column;
            int plen = prefix2.length();
            prefix2.getChars(0, plen, this.suffix, column - plen);
        }
        if (suffix2 != null) {
            char[] totalSuffix = this.suffix;
            int totalSuffixLen = totalSuffix.length;
            int additional = suffix2.length();
            int newSuffixLen = suffixLength + additional;
            if (newSuffixLen > totalSuffixLen) {
                int newTotalSuffixLen = enoughSpace(totalSuffixLen, additional);
                this.suffix = new char[newTotalSuffixLen];
                System.arraycopy(totalSuffix, totalSuffixLen - suffixLength, this.suffix, newTotalSuffixLen - suffixLength, suffixLength);
                totalSuffixLen = newTotalSuffixLen;
            }
            suffix2.getChars(0, additional, totalSuffix, totalSuffixLen - newSuffixLen);
            this.blocks[this.blockDepth - 5] = newSuffixLen;
        }
    }

    /* access modifiers changed from: 0000 */
    public int enqueueTab(int flags, int colnum, int colinc) {
        int addr = enqueue(6, 5);
        this.queueInts[addr + 2] = flags;
        this.queueInts[addr + 3] = colnum;
        this.queueInts[addr + 4] = colinc;
        return addr;
    }

    private static int enoughSpace(int current, int want) {
        int doubled = current * 2;
        int enough = current + ((want * 5) >> 2);
        return doubled > enough ? doubled : enough;
    }

    public void setIndentation(int column) {
        char[] prefix2 = this.prefix;
        int prefixLen = prefix2.length;
        int current = getPrefixLength();
        int minimum = getPerLinePrefixEnd();
        if (minimum > column) {
            column = minimum;
        }
        if (column > prefixLen) {
            prefix2 = new char[enoughSpace(prefixLen, column - prefixLen)];
            System.arraycopy(this.prefix, 0, prefix2, 0, current);
            this.prefix = prefix2;
        }
        if (column > current) {
            for (int i = current; i < column; i++) {
                prefix2[i] = ' ';
            }
        }
        this.blocks[this.blockDepth - 4] = column;
    }

    /* access modifiers changed from: 0000 */
    public void reallyEndLogicalBlock() {
        int oldIndent = getPrefixLength();
        this.blockDepth -= 6;
        int newIndent = getPrefixLength();
        if (newIndent > oldIndent) {
            for (int i = oldIndent; i < newIndent; i++) {
                this.prefix[i] = ' ';
            }
        }
    }

    public int enqueue(int kind, int size) {
        int oldLength = this.queueInts.length;
        int endAvail = (oldLength - this.queueTail) - this.queueSize;
        if (endAvail > 0 && size > endAvail) {
            enqueue(0, endAvail);
        }
        if (this.queueSize + size > oldLength) {
            int newLength = enoughSpace(oldLength, size);
            int[] newInts = new int[newLength];
            String[] newStrings = new String[newLength];
            int queueHead = (this.queueTail + this.queueSize) - oldLength;
            if (queueHead > 0) {
                System.arraycopy(this.queueInts, 0, newInts, 0, queueHead);
                System.arraycopy(this.queueStrings, 0, newStrings, 0, queueHead);
            }
            int part1Len = oldLength - this.queueTail;
            int deltaLength = newLength - oldLength;
            System.arraycopy(this.queueInts, this.queueTail, newInts, this.queueTail + deltaLength, part1Len);
            System.arraycopy(this.queueStrings, this.queueTail, newStrings, this.queueTail + deltaLength, part1Len);
            this.queueInts = newInts;
            this.queueStrings = newStrings;
            if (this.currentBlock >= this.queueTail) {
                this.currentBlock += deltaLength;
            }
            this.queueTail += deltaLength;
        }
        int addr = this.queueTail + this.queueSize;
        if (addr >= this.queueInts.length) {
            addr -= this.queueInts.length;
        }
        this.queueInts[addr + 0] = (size << 16) | kind;
        if (size > 1) {
            this.queueInts[addr + 1] = indexPosn(this.bufferFillPointer);
        }
        this.queueSize += size;
        return addr;
    }

    public void enqueueNewline(int kind) {
        boolean z;
        this.wordEndSeen = false;
        int depth = this.pendingBlocksCount;
        int newline = enqueue(2, 5);
        this.queueInts[newline + 4] = kind;
        this.queueInts[newline + 2] = this.pendingBlocksCount;
        this.queueInts[newline + 3] = 0;
        int entry = this.queueTail;
        int todo = this.queueSize;
        while (todo > 0) {
            if (entry == this.queueInts.length) {
                entry = 0;
            }
            if (entry == newline) {
                break;
            }
            int type = getQueueType(entry);
            if ((type == 2 || type == 4) && this.queueInts[entry + 3] == 0 && depth <= this.queueInts[entry + 2]) {
                int delta = newline - entry;
                if (delta < 0) {
                    delta += this.queueInts.length;
                }
                this.queueInts[entry + 3] = delta;
            }
            int size = getQueueSize(entry);
            todo -= size;
            entry += size;
        }
        if (kind == 76 || kind == 82) {
            z = true;
        } else {
            z = false;
        }
        maybeOutput(z, false);
    }

    public final void writeBreak(int kind) {
        if (this.prettyPrintingMode > 0) {
            enqueueNewline(kind);
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=int, for r4v0, types: [int, char] */
    public int enqueueIndent(int kind, int amount) {
        int result = enqueue(3, 4);
        this.queueInts[result + 2] = kind;
        this.queueInts[result + 3] = amount;
        return result;
    }

    public void addIndentation(int amount, boolean current) {
        if (this.prettyPrintingMode > 0) {
            enqueueIndent(current ? 'C' : QITEM_INDENTATION_BLOCK, amount);
        }
    }

    public void startLogicalBlock(String prefix2, boolean perLine, String suffix2) {
        int outerBlock;
        if (this.queueSize == 0 && this.bufferFillPointer == 0) {
            Object llen = lineLengthLoc.get(null);
            if (llen == null) {
                this.lineLength = 80;
            } else {
                this.lineLength = Integer.parseInt(llen.toString());
            }
            Object mwidth = miserWidthLoc.get(null);
            if (mwidth == null || mwidth == Boolean.FALSE || mwidth == LList.Empty) {
                this.miserWidth = -1;
            } else {
                this.miserWidth = Integer.parseInt(mwidth.toString());
            }
            indentLoc.get(null);
        }
        if (prefix2 != null) {
            write(prefix2);
        }
        if (this.prettyPrintingMode != 0) {
            int start = enqueue(4, 7);
            this.queueInts[start + 2] = this.pendingBlocksCount;
            String[] strArr = this.queueStrings;
            int i = start + 5;
            if (!perLine) {
                prefix2 = null;
            }
            strArr[i] = prefix2;
            this.queueStrings[start + 6] = suffix2;
            this.pendingBlocksCount++;
            int outerBlock2 = this.currentBlock;
            if (outerBlock2 < 0) {
                outerBlock = 0;
            } else {
                outerBlock = outerBlock2 - start;
                if (outerBlock > 0) {
                    outerBlock -= this.queueInts.length;
                }
            }
            this.queueInts[start + 4] = outerBlock;
            this.queueInts[start + 3] = 0;
            this.currentBlock = start;
        }
    }

    public void endLogicalBlock() {
        int end = enqueue(5, 2);
        this.pendingBlocksCount--;
        if (this.currentBlock < 0) {
            int suffixLength = this.blocks[this.blockDepth - 5];
            int suffixPreviousLength = this.blocks[(this.blockDepth - 6) - 5];
            if (suffixLength > suffixPreviousLength) {
                write(this.suffix, this.suffix.length - suffixLength, suffixLength - suffixPreviousLength);
            }
            this.currentBlock = -1;
            return;
        }
        int start = this.currentBlock;
        int outerBlock = this.queueInts[start + 4];
        if (outerBlock == 0) {
            this.currentBlock = -1;
        } else {
            int qtailFromStart = this.queueTail - start;
            if (qtailFromStart > 0) {
                qtailFromStart -= this.queueInts.length;
            }
            if (outerBlock < qtailFromStart) {
                this.currentBlock = -1;
            } else {
                int outerBlock2 = outerBlock + start;
                if (outerBlock2 < 0) {
                    outerBlock2 += this.queueInts.length;
                }
                this.currentBlock = outerBlock2;
            }
        }
        String suffix2 = this.queueStrings[start + 6];
        if (suffix2 != null) {
            write(suffix2);
        }
        int endFromStart = end - start;
        if (endFromStart < 0) {
            endFromStart += this.queueInts.length;
        }
        this.queueInts[start + 4] = endFromStart;
    }

    public void endLogicalBlock(String suffix2) {
        if (this.prettyPrintingMode > 0) {
            endLogicalBlock();
        } else if (suffix2 != null) {
            write(suffix2);
        }
    }

    /* access modifiers changed from: 0000 */
    public int computeTabSize(int tab, int sectionStart, int column) {
        boolean isSection;
        boolean isRelative;
        int origin = 0;
        int flags = this.queueInts[tab + 2];
        if ((flags & 1) != 0) {
            isSection = true;
        } else {
            isSection = false;
        }
        if ((flags & 2) != 0) {
            isRelative = true;
        } else {
            isRelative = false;
        }
        if (isSection) {
            origin = sectionStart;
        }
        int colnum = this.queueInts[tab + 3];
        int colinc = this.queueInts[tab + 4];
        if (isRelative) {
            if (colinc > 1) {
                int rem = (column + colnum) % colinc;
                if (rem != 0) {
                    int colinc2 = rem;
                    colnum += rem;
                }
            }
            return colnum;
        } else if (column <= colnum + origin) {
            return (column + origin) - column;
        } else {
            return colinc - ((column - origin) % colinc);
        }
    }

    /* access modifiers changed from: 0000 */
    public int indexColumn(int index) {
        int column = this.bufferStartColumn;
        int sectionStart = getSectionColumn();
        int endPosn = indexPosn(index);
        int op = this.queueTail;
        int todo = this.queueSize;
        while (todo > 0) {
            if (op >= this.queueInts.length) {
                op = 0;
            }
            int type = getQueueType(op);
            if (type != 0) {
                int posn = this.queueInts[op + 1];
                if (posn >= endPosn) {
                    break;
                } else if (type == 6) {
                    column += computeTabSize(op, sectionStart, posnIndex(posn) + column);
                } else if (type == 2 || type == 4) {
                    sectionStart = column + posnIndex(this.queueInts[op + 1]);
                }
            }
            int size = getQueueSize(op);
            todo -= size;
            op += size;
        }
        return column + index;
    }

    /* access modifiers changed from: 0000 */
    public void expandTabs(int through) {
        int numInsertions = 0;
        int additional = 0;
        int column = this.bufferStartColumn;
        int sectionStart = getSectionColumn();
        int op = this.queueTail;
        int todo = this.queueSize;
        int blocksUsed = this.pendingBlocksCount * 6;
        while (todo > 0) {
            if (op == this.queueInts.length) {
                op = 0;
            }
            if (op == through) {
                break;
            }
            if (getQueueType(op) == 6) {
                int index = posnIndex(this.queueInts[op + 1]);
                int tabsize = computeTabSize(op, sectionStart, column + index);
                if (tabsize != 0) {
                    if ((numInsertions * 2) + blocksUsed + 1 >= this.blocks.length) {
                        int[] newBlocks = new int[(this.blocks.length * 2)];
                        System.arraycopy(this.blocks, 0, newBlocks, 0, this.blocks.length);
                        this.blocks = newBlocks;
                    }
                    this.blocks[(numInsertions * 2) + blocksUsed] = index;
                    this.blocks[(numInsertions * 2) + blocksUsed + 1] = tabsize;
                    numInsertions++;
                    additional += tabsize;
                    column += tabsize;
                }
            } else if (op == 2 || op == 4) {
                sectionStart = column + posnIndex(this.queueInts[op + 1]);
            }
            int size = getQueueSize(op);
            todo -= size;
            op += size;
        }
        if (numInsertions > 0) {
            int fillPtr = this.bufferFillPointer;
            int newFillPtr = fillPtr + additional;
            char[] buffer2 = this.buffer;
            char[] newBuffer = buffer2;
            int end = fillPtr;
            if (newFillPtr > buffer2.length) {
                newBuffer = new char[enoughSpace(fillPtr, additional)];
                this.buffer = newBuffer;
            }
            this.bufferFillPointer = newFillPtr;
            this.bufferOffset -= additional;
            int i = numInsertions;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                int srcpos = this.blocks[(i * 2) + blocksUsed];
                int amount = this.blocks[(i * 2) + blocksUsed + 1];
                int dstpos = srcpos + additional;
                System.arraycopy(buffer2, srcpos, newBuffer, dstpos, end - srcpos);
                for (int j = dstpos - amount; j < dstpos; j++) {
                    newBuffer[j] = ' ';
                }
                additional -= amount;
                end = srcpos;
            }
            if (newBuffer != buffer2) {
                System.arraycopy(buffer2, 0, newBuffer, 0, end);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public int ensureSpaceInBuffer(int want) {
        char[] buffer2 = this.buffer;
        int length = buffer2.length;
        int fillPtr = this.bufferFillPointer;
        int available = length - fillPtr;
        if (available > 0) {
            return available;
        }
        if (this.prettyPrintingMode <= 0 || fillPtr <= this.lineLength) {
            int newLength = enoughSpace(length, want);
            char[] newBuffer = new char[newLength];
            this.buffer = newBuffer;
            int i = fillPtr;
            while (true) {
                i--;
                if (i < 0) {
                    return newLength - fillPtr;
                }
                newBuffer[i] = buffer2[i];
            }
        } else {
            if (!maybeOutput(false, false)) {
                outputPartialLine();
            }
            return ensureSpaceInBuffer(want);
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0069, code lost:
        if (r2 == false) goto L_0x0035;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x006b, code lost:
        r10 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x006c, code lost:
        if (r20 == false) goto L_0x00d7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x006e, code lost:
        if (r6 != 0) goto L_0x00d7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        outputPartialLine();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0074, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0075, code lost:
        r0 = new java.lang.RuntimeException(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x007c, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
        outputLine(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0035, code lost:
        continue;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0035, code lost:
        r12 = getQueueSize(r18.queueTail);
        r18.queueSize -= r12;
        r18.queueTail = r9 + r12;
     */
    public boolean maybeOutput(boolean forceNewlines, boolean flushing) {
        int indent;
        int end;
        boolean cond;
        boolean outputAnything = false;
        while (this.queueSize > 0) {
            if (this.queueTail >= this.queueInts.length) {
                this.queueTail = 0;
            }
            int next = this.queueTail;
            switch (getQueueType(next)) {
                case 2:
                    int fits = -1;
                    switch (this.queueInts[next + 4]) {
                        case 70:
                            if (isMisering() || this.lineNumber > getSectionStartLine()) {
                                cond = true;
                                break;
                            }
                        case 77:
                            cond = isMisering();
                            break;
                        case 83:
                            int end2 = this.queueInts[next + 3];
                            if (end2 == 0) {
                                end = -1;
                            } else {
                                end = end2 + next;
                                if (end >= this.queueInts.length) {
                                    end -= this.queueInts.length;
                                }
                            }
                            fits = fitsOnLine(end, forceNewlines);
                            if (fits <= 0) {
                                if (fits >= 0 && !flushing) {
                                    break;
                                } else {
                                    cond = true;
                                    break;
                                }
                            } else {
                                cond = false;
                                break;
                            }
                        default:
                            cond = true;
                            break;
                    }
                case 3:
                    if (!isMisering()) {
                        int kind = this.queueInts[next + 2];
                        int indent2 = this.queueInts[next + 3];
                        if (kind == 66) {
                            indent = indent2 + getStartColumn();
                        } else {
                            indent = indent2 + posnColumn(this.queueInts[next + 1]);
                        }
                        setIndentation(indent);
                        break;
                    } else {
                        continue;
                    }
                case 4:
                    int start = next;
                    int end3 = this.queueInts[next + 3];
                    int fits2 = fitsOnLine(end3 > 0 ? (end3 + next) % this.queueInts.length : -1, forceNewlines);
                    if (fits2 <= 0) {
                        if (fits2 >= 0 && !flushing) {
                            break;
                        } else {
                            reallyStartLogicalBlock(posnColumn(this.queueInts[next + 1]), this.queueStrings[next + 5], this.queueStrings[next + 6]);
                        }
                    } else {
                        int endr = this.queueInts[next + 4];
                        next = (endr + next) % this.queueInts.length;
                        expandTabs(next);
                        this.queueTail = next;
                        this.queueSize -= endr;
                    }
                    if (this.currentBlock == start) {
                        this.currentBlock = -1;
                        break;
                    } else {
                        continue;
                    }
                case 5:
                    reallyEndLogicalBlock();
                    continue;
                case 6:
                    expandTabs(next);
                    continue;
            }
            return outputAnything;
        }
        return outputAnything;
    }

    /* access modifiers changed from: protected */
    public int getMiserWidth() {
        return this.miserWidth;
    }

    /* access modifiers changed from: 0000 */
    public boolean isMisering() {
        int mwidth = getMiserWidth();
        return mwidth > 0 && this.lineLength - getStartColumn() <= mwidth;
    }

    /* access modifiers changed from: 0000 */
    public int getMaxLines() {
        return -1;
    }

    /* access modifiers changed from: 0000 */
    public boolean printReadably() {
        return true;
    }

    /* access modifiers changed from: 0000 */
    public int fitsOnLine(int sectionEnd, boolean forceNewlines) {
        int available = this.lineLength;
        if (!printReadably() && getMaxLines() == this.lineNumber) {
            available = (available - 3) - getSuffixLength();
        }
        if (sectionEnd >= 0) {
            if (posnColumn(this.queueInts[sectionEnd + 1]) <= available) {
                return 1;
            }
            return -1;
        } else if (forceNewlines || indexColumn(this.bufferFillPointer) > available) {
            return -1;
        } else {
            return 0;
        }
    }

    public void lineAbbreviationHappened() {
    }

    /* access modifiers changed from: 0000 */
    public void outputLine(int newline) throws IOException {
        int amountToPrint;
        char[] buffer2 = this.buffer;
        boolean isLiteral = this.queueInts[newline + 4] == 76;
        int amountToConsume = posnIndex(this.queueInts[newline + 1]);
        if (!isLiteral) {
            int i = amountToConsume;
            while (true) {
                i--;
                if (i >= 0) {
                    if (buffer2[i] != ' ') {
                        amountToPrint = i + 1;
                        break;
                    }
                } else {
                    amountToPrint = 0;
                    break;
                }
            }
        } else {
            amountToPrint = amountToConsume;
        }
        this.out.write(buffer2, 0, amountToPrint);
        int lineNumber2 = this.lineNumber + 1;
        if (!printReadably()) {
            int maxLines = getMaxLines();
            if (maxLines > 0 && lineNumber2 >= maxLines) {
                this.out.write(" ..");
                int suffixLength = getSuffixLength();
                if (suffixLength != 0) {
                    char[] suffix2 = this.suffix;
                    this.out.write(suffix2, suffix2.length - suffixLength, suffixLength);
                }
                lineAbbreviationHappened();
            }
        }
        this.lineNumber = lineNumber2;
        this.out.write(10);
        this.bufferStartColumn = 0;
        int fillPtr = this.bufferFillPointer;
        int prefixLen = isLiteral ? getPerLinePrefixEnd() : getPrefixLength();
        int shift = amountToConsume - prefixLen;
        int newFillPtr = fillPtr - shift;
        char[] newBuffer = buffer2;
        int bufferLength = buffer2.length;
        if (newFillPtr > bufferLength) {
            newBuffer = new char[enoughSpace(bufferLength, newFillPtr - bufferLength)];
            this.buffer = newBuffer;
        }
        System.arraycopy(buffer2, amountToConsume, newBuffer, prefixLen, fillPtr - amountToConsume);
        System.arraycopy(this.prefix, 0, newBuffer, 0, prefixLen);
        this.bufferFillPointer = newFillPtr;
        this.bufferOffset += shift;
        if (!isLiteral) {
            this.blocks[this.blockDepth - 2] = prefixLen;
            this.blocks[this.blockDepth - 6] = lineNumber2;
        }
    }

    /* access modifiers changed from: 0000 */
    public void outputPartialLine() {
        int count;
        int tail = this.queueTail;
        while (this.queueSize > 0 && getQueueType(tail) == 0) {
            int size = getQueueSize(tail);
            this.queueSize -= size;
            tail += size;
            if (tail == this.queueInts.length) {
                tail = 0;
            }
            this.queueTail = tail;
        }
        int fillPtr = this.bufferFillPointer;
        if (this.queueSize > 0) {
            count = posnIndex(this.queueInts[tail + 1]);
        } else {
            count = fillPtr;
        }
        int newFillPtr = fillPtr - count;
        if (count <= 0) {
            throw new Error("outputPartialLine called when nothing can be output.");
        }
        try {
            this.out.write(this.buffer, 0, count);
            this.bufferFillPointer = count;
            this.bufferStartColumn = getColumnNumber();
            System.arraycopy(this.buffer, count, this.buffer, 0, newFillPtr);
            this.bufferFillPointer = newFillPtr;
            this.bufferOffset += count;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void forcePrettyOutput() throws IOException {
        maybeOutput(false, true);
        if (this.bufferFillPointer > 0) {
            outputPartialLine();
        }
        expandTabs(-1);
        this.bufferStartColumn = getColumnNumber();
        this.out.write(this.buffer, 0, this.bufferFillPointer);
        this.bufferFillPointer = 0;
        this.queueSize = 0;
    }

    public void flush() {
        if (this.out != null) {
            try {
                forcePrettyOutput();
                this.out.flush();
            } catch (IOException ex) {
                throw new RuntimeException(ex.toString());
            }
        }
    }

    public void close() throws IOException {
        if (this.out != null) {
            forcePrettyOutput();
            this.out.close();
            this.out = null;
        }
        this.buffer = null;
    }

    public void closeThis() throws IOException {
        if (this.out != null) {
            forcePrettyOutput();
            this.out = null;
        }
        this.buffer = null;
    }

    public int getColumnNumber() {
        char ch;
        int i = this.bufferFillPointer;
        do {
            i--;
            if (i >= 0) {
                ch = this.buffer[i];
                if (ch == 10) {
                    break;
                }
            } else {
                return this.bufferStartColumn + this.bufferFillPointer;
            }
        } while (ch != 13);
        return this.bufferFillPointer - (i + 1);
    }

    public void setColumnNumber(int column) {
        this.bufferStartColumn += column - getColumnNumber();
    }

    public void clearBuffer() {
        this.bufferStartColumn = 0;
        this.bufferFillPointer = 0;
        this.lineNumber = 0;
        this.bufferOffset = 0;
        this.blockDepth = 6;
        this.queueTail = 0;
        this.queueSize = 0;
        this.pendingBlocksCount = 0;
    }
}
