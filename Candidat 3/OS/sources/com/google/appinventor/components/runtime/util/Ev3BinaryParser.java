package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.runtime.util.Ev3Constants.Opcode;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;

public class Ev3BinaryParser {
    private static byte PRIMPAR_1_BYTE = 1;
    private static byte PRIMPAR_2_BYTES = 2;
    private static byte PRIMPAR_4_BYTES = 3;
    private static byte PRIMPAR_ADDR = 8;
    private static byte PRIMPAR_BYTES = 7;
    private static byte PRIMPAR_CONST = 0;
    private static byte PRIMPAR_CONST_SIGN = 32;
    private static byte PRIMPAR_GLOBAL = 32;
    private static byte PRIMPAR_HANDLE = 16;
    private static byte PRIMPAR_INDEX = 31;
    private static byte PRIMPAR_LOCAL = 0;
    private static byte PRIMPAR_LONG = Byte.MIN_VALUE;
    private static byte PRIMPAR_SHORT = 0;
    private static byte PRIMPAR_STRING = 4;
    private static byte PRIMPAR_STRING_OLD = 0;
    private static byte PRIMPAR_VALUE = Opcode.MOVEF_F;
    private static byte PRIMPAR_VARIABEL = Opcode.JR;

    private static class FormatLiteral {
        public int size;
        public char symbol;

        public FormatLiteral(char symbol2, int size2) {
            this.symbol = symbol2;
            this.size = size2;
        }
    }

    public static byte[] pack(String format, Object... values) throws IllegalArgumentException {
        String[] formatTokens = format.split("(?<=\\D)");
        FormatLiteral[] literals = new FormatLiteral[formatTokens.length];
        int index = 0;
        int bufferCapacity = 0;
        for (int i = 0; i < formatTokens.length; i++) {
            String token = formatTokens[i];
            char symbol = token.charAt(token.length() - 1);
            int size = 1;
            boolean sizeSpecified = false;
            if (token.length() != 1) {
                size = Integer.parseInt(token.substring(0, token.length() - 1));
                sizeSpecified = true;
                if (size < 1) {
                    throw new IllegalArgumentException("Illegal format string");
                }
            }
            switch (symbol) {
                case 'B':
                    bufferCapacity += size;
                    index++;
                    break;
                case 'F':
                    bufferCapacity += size * 4;
                    index++;
                    break;
                case 'H':
                    bufferCapacity += size * 2;
                    index++;
                    break;
                case 'I':
                    bufferCapacity += size * 4;
                    index++;
                    break;
                case 'L':
                    bufferCapacity += size * 8;
                    index++;
                    break;
                case 'S':
                    if (!sizeSpecified) {
                        bufferCapacity += values[index].length() + 1;
                        index++;
                        break;
                    } else {
                        throw new IllegalArgumentException("Illegal format string");
                    }
                case 'b':
                    bufferCapacity += size;
                    index += size;
                    break;
                case 'f':
                    bufferCapacity += size * 4;
                    index += size;
                    break;
                case 'h':
                    bufferCapacity += size * 2;
                    index += size;
                    break;
                case 'i':
                    bufferCapacity += size * 4;
                    index += size;
                    break;
                case 'l':
                    bufferCapacity += size * 8;
                    index += size;
                    break;
                case 's':
                    if (size == values[index].length()) {
                        bufferCapacity += size;
                        index++;
                        break;
                    } else {
                        throw new IllegalArgumentException("Illegal format string");
                    }
                case 'x':
                    bufferCapacity += size;
                    break;
                default:
                    throw new IllegalArgumentException("Illegal format string");
            }
            literals[i] = new FormatLiteral(symbol, size);
        }
        if (index != values.length) {
            throw new IllegalArgumentException("Illegal format string");
        }
        int index2 = 0;
        ByteBuffer buffer = ByteBuffer.allocate(bufferCapacity);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        int length = literals.length;
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 >= length) {
                return buffer.array();
            }
            FormatLiteral literal = literals[i3];
            switch (literal.symbol) {
                case 'B':
                    buffer.put(values[index2]);
                    index2++;
                    break;
                case 'F':
                    for (int i4 = 0; i4 < literal.size; i4++) {
                        buffer.putFloat(values[index2][i4]);
                    }
                    index2++;
                    break;
                case 'H':
                    for (int i5 = 0; i5 < literal.size; i5++) {
                        buffer.putShort(values[index2][i5]);
                    }
                    index2++;
                    break;
                case 'I':
                    for (int i6 = 0; i6 < literal.size; i6++) {
                        buffer.putInt(values[index2][i6]);
                    }
                    index2++;
                    break;
                case 'L':
                    for (int i7 = 0; i7 < literal.size; i7++) {
                        buffer.putLong(values[index2][i7]);
                    }
                    index2++;
                    break;
                case 'S':
                    try {
                        buffer.put(values[index2].getBytes("US-ASCII"));
                        buffer.put(0);
                        index2++;
                        break;
                    } catch (UnsupportedEncodingException e) {
                        throw new IllegalArgumentException();
                    }
                case 'b':
                    for (int i8 = 0; i8 < literal.size; i8++) {
                        buffer.put(values[index2].byteValue());
                        index2++;
                    }
                    break;
                case 'f':
                    for (int i9 = 0; i9 < literal.size; i9++) {
                        buffer.putFloat(values[index2].floatValue());
                        index2++;
                    }
                    break;
                case 'h':
                    for (int i10 = 0; i10 < literal.size; i10++) {
                        buffer.putShort(values[index2].shortValue());
                        index2++;
                    }
                    break;
                case 'i':
                    for (int i11 = 0; i11 < literal.size; i11++) {
                        buffer.putInt(values[index2].intValue());
                        index2++;
                    }
                    break;
                case 'l':
                    for (int i12 = 0; i12 < literal.size; i12++) {
                        buffer.putLong(values[index2].longValue());
                        index2++;
                    }
                    break;
                case 's':
                    try {
                        buffer.put(values[index2].getBytes("US-ASCII"));
                        index2++;
                        break;
                    } catch (UnsupportedEncodingException e2) {
                        throw new IllegalArgumentException();
                    }
                case 'x':
                    for (int i13 = 0; i13 < literal.size; i13++) {
                        buffer.put(0);
                    }
                    break;
            }
            i2 = i3 + 1;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00a8, code lost:
        r21 = r21 + 1;
     */
    public static Object[] unpack(String format, byte[] bytes) throws IllegalArgumentException {
        String[] formatTokens = format.split("(?<=\\D)");
        ArrayList<Object> decodedObjects = new ArrayList<>();
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        int length = formatTokens.length;
        int i = 0;
        while (i < length) {
            String token = formatTokens[i];
            boolean sizeSpecified = false;
            int size = 1;
            char symbol = token.charAt(token.length() - 1);
            if (token.length() > 1) {
                sizeSpecified = true;
                size = Integer.parseInt(token.substring(0, token.length() - 1));
                if (size < 1) {
                    throw new IllegalArgumentException("Illegal format string");
                }
            }
            switch (symbol) {
                case '$':
                    if (sizeSpecified) {
                        throw new IllegalArgumentException("Illegal format string");
                    } else if (buffer.hasRemaining()) {
                        throw new IllegalArgumentException("Illegal format string");
                    }
                    break;
                case 'B':
                    byte[] byteArray = new byte[size];
                    buffer.get(byteArray, 0, size);
                    decodedObjects.add(byteArray);
                    continue;
                case 'F':
                    float[] floats = new float[size];
                    for (int i2 = 0; i2 < size; i2++) {
                        floats[i2] = buffer.getFloat();
                    }
                    decodedObjects.add(floats);
                    continue;
                case 'H':
                    short[] shorts = new short[size];
                    for (short i3 = 0; i3 < size; i3 = (short) (i3 + 1)) {
                        shorts[i3] = buffer.getShort();
                    }
                    decodedObjects.add(shorts);
                    continue;
                case 'I':
                    int[] integers = new int[size];
                    for (int i4 = 0; i4 < size; i4++) {
                        integers[i4] = buffer.getInt();
                    }
                    decodedObjects.add(integers);
                    continue;
                case 'L':
                    long[] longs = new long[size];
                    for (int i5 = 0; i5 < size; i5++) {
                        longs[i5] = buffer.getLong();
                    }
                    decodedObjects.add(longs);
                    continue;
                case 'S':
                    if (sizeSpecified) {
                        throw new IllegalArgumentException("Illegal format string");
                    }
                    StringBuffer stringBuffer = new StringBuffer();
                    while (true) {
                        byte b = buffer.get();
                        if (b != 0) {
                            stringBuffer.append((char) b);
                        } else {
                            decodedObjects.add(stringBuffer.toString());
                            continue;
                        }
                    }
                case 'b':
                    for (int i6 = 0; i6 < size; i6++) {
                        decodedObjects.add(Byte.valueOf(buffer.get()));
                    }
                    continue;
                case 'f':
                    for (int i7 = 0; i7 < size; i7++) {
                        decodedObjects.add(Float.valueOf(buffer.getFloat()));
                    }
                    continue;
                case 'h':
                    for (int i8 = 0; i8 < size; i8++) {
                        decodedObjects.add(Short.valueOf(buffer.getShort()));
                    }
                    continue;
                case 'i':
                    for (int i9 = 0; i9 < size; i9++) {
                        decodedObjects.add(Integer.valueOf(buffer.getInt()));
                    }
                    continue;
                case 'l':
                    for (int i10 = 0; i10 < size; i10++) {
                        decodedObjects.add(Long.valueOf(buffer.getLong()));
                    }
                    continue;
                case 's':
                    byte[] byteString = new byte[size];
                    buffer.get(byteString, 0, size);
                    try {
                        String str = new String(byteString, "US-ASCII");
                        decodedObjects.add(str);
                        continue;
                    } catch (UnsupportedEncodingException e) {
                        throw new IllegalArgumentException();
                    }
                case 'x':
                    for (int i11 = 0; i11 < size; i11++) {
                        buffer.get();
                    }
                    continue;
            }
            throw new IllegalArgumentException("Illegal format string");
        }
        return decodedObjects.toArray();
    }

    public static byte[] encodeLC0(byte v) {
        if (v < -31 || v > 31) {
            throw new IllegalArgumentException("Encoded value must be in range [0, 127]");
        }
        return new byte[]{(byte) (PRIMPAR_VALUE & v)};
    }

    public static byte[] encodeLC1(byte v) {
        return new byte[]{(byte) (((byte) (PRIMPAR_LONG | PRIMPAR_CONST)) | PRIMPAR_1_BYTE), (byte) (v & Opcode.TST)};
    }

    public static byte[] encodeLC2(short v) {
        return new byte[]{(byte) (((byte) (PRIMPAR_LONG | PRIMPAR_CONST)) | PRIMPAR_2_BYTES), (byte) (v & 255), (byte) ((v >>> 8) & 255)};
    }

    public static byte[] encodeLC4(int v) {
        return new byte[]{(byte) (((byte) (PRIMPAR_LONG | PRIMPAR_CONST)) | PRIMPAR_4_BYTES), (byte) (v & 255), (byte) ((v >>> 8) & 255), (byte) ((v >>> 16) & 255), (byte) ((v >>> 24) & 255)};
    }

    public static byte[] encodeLV0(int i) {
        return new byte[]{(byte) ((PRIMPAR_INDEX & i) | PRIMPAR_SHORT | PRIMPAR_VARIABEL | PRIMPAR_LOCAL)};
    }

    public static byte[] encodeLV1(int i) {
        return new byte[]{(byte) (PRIMPAR_LONG | PRIMPAR_VARIABEL | PRIMPAR_LOCAL | PRIMPAR_1_BYTE), (byte) (i & 255)};
    }

    public static byte[] encodeLV2(int i) {
        return new byte[]{(byte) (PRIMPAR_LONG | PRIMPAR_VARIABEL | PRIMPAR_LOCAL | PRIMPAR_2_BYTES), (byte) (i & 255), (byte) ((i >>> 8) & 255)};
    }

    public static byte[] encodeLV4(int i) {
        return new byte[]{(byte) (PRIMPAR_LONG | PRIMPAR_VARIABEL | PRIMPAR_LOCAL | PRIMPAR_4_BYTES), (byte) (i & 255), (byte) ((i >>> 8) & 255), (byte) ((i >>> 16) & 255), (byte) ((i >>> 24) & 255)};
    }

    public static byte[] encodeGV0(int i) {
        return new byte[]{(byte) ((PRIMPAR_INDEX & i) | PRIMPAR_SHORT | PRIMPAR_VARIABEL | PRIMPAR_GLOBAL)};
    }

    public static byte[] encodeGV1(int i) {
        return new byte[]{(byte) (PRIMPAR_LONG | PRIMPAR_VARIABEL | PRIMPAR_GLOBAL | PRIMPAR_1_BYTE), (byte) (i & 255)};
    }

    public static byte[] encodeGV2(int i) {
        return new byte[]{(byte) (PRIMPAR_LONG | PRIMPAR_VARIABEL | PRIMPAR_GLOBAL | PRIMPAR_2_BYTES), (byte) (i & 255), (byte) ((i >>> 8) & 255)};
    }

    public static byte[] encodeGV4(int i) {
        return new byte[]{(byte) (PRIMPAR_LONG | PRIMPAR_VARIABEL | PRIMPAR_GLOBAL | PRIMPAR_4_BYTES), (byte) (i & 255), (byte) ((i >>> 8) & 255), (byte) ((i >>> 16) & 255), (byte) ((i >>> 24) & 255)};
    }

    public static byte[] encodeSystemCommand(byte command, boolean needReply, Object... parameters) {
        int bufferCapacity = 2;
        for (Object obj : parameters) {
            if (obj instanceof Byte) {
                bufferCapacity++;
            } else if (obj instanceof Short) {
                bufferCapacity += 2;
            } else if (obj instanceof Integer) {
                bufferCapacity += 4;
            } else if (obj instanceof String) {
                bufferCapacity += ((String) obj).length() + 1;
            } else {
                throw new IllegalArgumentException("Parameters should be one of the class types: Byte, Short, Integer, String");
            }
        }
        ByteBuffer buffer = ByteBuffer.allocate(bufferCapacity);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(needReply ? (byte) 1 : -127);
        buffer.put(command);
        for (Object obj2 : parameters) {
            if (obj2 instanceof Byte) {
                buffer.put(((Byte) obj2).byteValue());
            } else if (obj2 instanceof Short) {
                buffer.putShort(((Short) obj2).shortValue());
            } else if (obj2 instanceof Integer) {
                buffer.putInt(((Integer) obj2).intValue());
            } else if (obj2 instanceof String) {
                try {
                    buffer.put(((String) obj2).getBytes("US-ASCII"));
                    buffer.put(0);
                } catch (UnsupportedEncodingException e) {
                    throw new IllegalArgumentException("Non-ASCII string encoding is not supported");
                }
            } else {
                throw new IllegalArgumentException("Parameters should be one of the class types: Byte, Short, Integer, String");
            }
        }
        return buffer.array();
    }

    public static byte[] encodeDirectCommand(byte opcode, boolean needReply, int globalAllocation, int localAllocation, String paramFormat, Object... parameters) {
        if (globalAllocation < 0 || globalAllocation > 1023 || localAllocation < 0 || localAllocation > 63 || paramFormat.length() != parameters.length) {
            throw new IllegalArgumentException();
        }
        ArrayList<byte[]> payloads = new ArrayList<>();
        for (int i = 0; i < paramFormat.length(); i++) {
            char letter = paramFormat.charAt(i);
            Byte b = parameters[i];
            switch (letter) {
                case 'c':
                    if (b instanceof Byte) {
                        if (b.byteValue() <= 31 && b.byteValue() >= -31) {
                            payloads.add(encodeLC0(b.byteValue()));
                            break;
                        } else {
                            payloads.add(encodeLC1(b.byteValue()));
                            break;
                        }
                    } else if (b instanceof Short) {
                        payloads.add(encodeLC2(((Short) b).shortValue()));
                        break;
                    } else if (b instanceof Integer) {
                        payloads.add(encodeLC4(((Integer) b).intValue()));
                        break;
                    } else {
                        throw new IllegalArgumentException();
                    }
                case 'g':
                    if (b instanceof Byte) {
                        if (b.byteValue() <= 31 && b.byteValue() >= -31) {
                            payloads.add(encodeGV0(b.byteValue()));
                            break;
                        } else {
                            payloads.add(encodeGV1(b.byteValue()));
                            break;
                        }
                    } else if (b instanceof Short) {
                        payloads.add(encodeGV2(((Short) b).shortValue()));
                        break;
                    } else if (b instanceof Integer) {
                        payloads.add(encodeGV4(((Integer) b).intValue()));
                        break;
                    } else {
                        throw new IllegalArgumentException();
                    }
                case 'l':
                    if (b instanceof Byte) {
                        if (b.byteValue() <= 31 && b.byteValue() >= -31) {
                            payloads.add(encodeLV0(b.byteValue()));
                            break;
                        } else {
                            payloads.add(encodeLV1(b.byteValue()));
                            break;
                        }
                    } else if (b instanceof Short) {
                        payloads.add(encodeLV2(((Short) b).shortValue()));
                        break;
                    } else if (b instanceof Integer) {
                        payloads.add(encodeLV4(((Integer) b).intValue()));
                        break;
                    } else {
                        throw new IllegalArgumentException();
                    }
                case 's':
                    if (!(b instanceof String)) {
                        throw new IllegalArgumentException();
                    }
                    try {
                        payloads.add((((String) b) + 0).getBytes("US-ASCII"));
                        break;
                    } catch (UnsupportedEncodingException e) {
                        throw new IllegalArgumentException();
                    }
                default:
                    throw new IllegalArgumentException("Illegal format string");
            }
        }
        int bufferCapacity = 4;
        Iterator it = payloads.iterator();
        while (it.hasNext()) {
            bufferCapacity += ((byte[]) it.next()).length;
        }
        ByteBuffer buffer = ByteBuffer.allocate(bufferCapacity);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(needReply ? 0 : Byte.MIN_VALUE);
        buffer.put(new byte[]{(byte) (globalAllocation & 255), (byte) (((globalAllocation >>> 8) & 3) | (localAllocation << 2))});
        buffer.put(opcode);
        Iterator it2 = payloads.iterator();
        while (it2.hasNext()) {
            buffer.put((byte[]) it2.next());
        }
        return buffer.array();
    }
}
