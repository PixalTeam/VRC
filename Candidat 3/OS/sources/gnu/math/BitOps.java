package gnu.math;

public class BitOps {
    static final byte[] bit4_count = {0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4};

    private BitOps() {
    }

    public static boolean bitValue(IntNum x, int bitno) {
        int i = x.ival;
        if (x.words != null) {
            int wordno = bitno >> 5;
            if (wordno >= i) {
                if (x.words[i - 1] >= 0) {
                    return false;
                }
                return true;
            } else if (((x.words[wordno] >> bitno) & 1) == 0) {
                return false;
            } else {
                return true;
            }
        } else if (bitno >= 32) {
            if (i < 0) {
                return true;
            }
            return false;
        } else if (((i >> bitno) & 1) == 0) {
            return false;
        } else {
            return true;
        }
    }

    static int[] dataBufferFor(IntNum x, int bitno) {
        int[] data;
        int i;
        int i2 = x.ival;
        int nwords = (bitno + 1) >> 5;
        if (x.words == null) {
            if (nwords == 0) {
                nwords = 1;
            }
            data = new int[nwords];
            data[0] = i2;
            if (i2 < 0) {
                for (int j = 1; j < nwords; j++) {
                    data[j] = -1;
                }
            }
        } else {
            int nwords2 = (bitno + 1) >> 5;
            if (nwords2 > i2) {
                i = nwords2;
            } else {
                i = i2;
            }
            data = new int[i];
            int j2 = i2;
            while (true) {
                j2--;
                if (j2 < 0) {
                    break;
                }
                data[j2] = x.words[j2];
            }
            if (data[i2 - 1] < 0) {
                for (int j3 = i2; j3 < nwords2; j3++) {
                    data[j3] = -1;
                }
            }
        }
        return data;
    }

    public static IntNum setBitValue(IntNum x, int bitno, int newValue) {
        int i = 31;
        int newValue2 = newValue & 1;
        int i2 = x.ival;
        if (x.words == null) {
            if (bitno < 31) {
                i = bitno;
            }
            if (((i2 >> i) & 1) == newValue2) {
                return x;
            }
            if (bitno < 63) {
                return IntNum.make(((long) (1 << bitno)) ^ ((long) i2));
            }
        } else {
            int wordno = bitno >> 5;
            int oldValue = wordno >= i2 ? x.words[i2 + -1] < 0 ? 1 : 0 : (x.words[wordno] >> bitno) & 1;
            if (oldValue == newValue2) {
                return x;
            }
        }
        int[] data = dataBufferFor(x, bitno);
        int i3 = bitno >> 5;
        data[i3] = (1 << (bitno & 31)) ^ data[i3];
        return IntNum.make(data, data.length);
    }

    public static boolean test(IntNum x, int y) {
        boolean z = false;
        if (x.words != null) {
            if (y < 0 || (x.words[0] & y) != 0) {
                z = true;
            }
            return z;
        } else if ((x.ival & y) != 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean test(IntNum x, IntNum y) {
        if (y.words == null) {
            return test(x, y.ival);
        }
        if (x.words == null) {
            return test(y, x.ival);
        }
        if (x.ival < y.ival) {
            IntNum temp = x;
            x = y;
            y = temp;
        }
        for (int i = 0; i < y.ival; i++) {
            if ((x.words[i] & y.words[i]) != 0) {
                return true;
            }
        }
        return y.isNegative();
    }

    public static IntNum and(IntNum x, int y) {
        if (x.words == null) {
            return IntNum.make(x.ival & y);
        }
        if (y >= 0) {
            return IntNum.make(x.words[0] & y);
        }
        int len = x.ival;
        int[] words = new int[len];
        words[0] = x.words[0] & y;
        while (true) {
            len--;
            if (len <= 0) {
                return IntNum.make(words, x.ival);
            }
            words[len] = x.words[len];
        }
    }

    public static IntNum and(IntNum x, IntNum y) {
        if (y.words == null) {
            return and(x, y.ival);
        }
        if (x.words == null) {
            return and(y, x.ival);
        }
        if (x.ival < y.ival) {
            IntNum temp = x;
            x = y;
            y = temp;
        }
        int len = y.isNegative() ? x.ival : y.ival;
        int[] words = new int[len];
        int i = 0;
        while (i < y.ival) {
            words[i] = x.words[i] & y.words[i];
            i++;
        }
        while (i < len) {
            words[i] = x.words[i];
            i++;
        }
        return IntNum.make(words, len);
    }

    public static IntNum ior(IntNum x, IntNum y) {
        return bitOp(7, x, y);
    }

    public static IntNum xor(IntNum x, IntNum y) {
        return bitOp(6, x, y);
    }

    public static IntNum not(IntNum x) {
        return bitOp(12, x, IntNum.zero());
    }

    public static int swappedOp(int op) {
        return "\u0000\u0001\u0004\u0005\u0002\u0003\u0006\u0007\b\t\f\r\n\u000b\u000e\u000f".charAt(op);
    }

    public static IntNum bitOp(int op, IntNum x, IntNum y) {
        switch (op) {
            case 0:
                return IntNum.zero();
            case 1:
                return and(x, y);
            case 3:
                return x;
            case 5:
                return y;
            case 15:
                return IntNum.minusOne();
            default:
                IntNum result = new IntNum();
                setBitOp(result, op, x, y);
                return result.canonicalize();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0063, code lost:
        if ((r2 + 1) < r9) goto L_0x0052;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0065, code lost:
        if (r8 >= 0) goto L_0x0021;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0067, code lost:
        r0 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x007c, code lost:
        if ((r2 + 1) < r9) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x007e, code lost:
        if (r8 < 0) goto L_0x0021;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0080, code lost:
        r0 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0099, code lost:
        if ((r2 + 1) < r9) goto L_0x0086;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x009b, code lost:
        if (r8 >= 0) goto L_0x0021;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x009d, code lost:
        r0 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00af, code lost:
        if ((r2 + 1) < r9) goto L_0x009f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00c4, code lost:
        if ((r2 + 1) < r9) goto L_0x00b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00c6, code lost:
        if (r8 >= 0) goto L_0x00cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00c8, code lost:
        r0 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00cb, code lost:
        r0 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00de, code lost:
        if ((r2 + 1) < r9) goto L_0x00cd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00e0, code lost:
        if (r8 < 0) goto L_0x0021;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00e2, code lost:
        r0 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00f8, code lost:
        if ((r2 + 1) < r9) goto L_0x00e5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00fa, code lost:
        if (r8 < 0) goto L_0x0021;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00fc, code lost:
        r0 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0112, code lost:
        if ((r2 + 1) < r9) goto L_0x00ff;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0114, code lost:
        if (r8 < 0) goto L_0x0119;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0116, code lost:
        r0 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0119, code lost:
        r0 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x012c, code lost:
        if ((r2 + 1) < r9) goto L_0x011b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x0143, code lost:
        if ((r2 + 1) < r9) goto L_0x0130;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0145, code lost:
        if (r8 >= 0) goto L_0x0021;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0147, code lost:
        r0 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0163, code lost:
        if ((r2 + 1) < r9) goto L_0x0150;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0165, code lost:
        if (r8 < 0) goto L_0x0021;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0167, code lost:
        r0 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x017d, code lost:
        if ((r2 + 1) < r9) goto L_0x016a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x017f, code lost:
        if (r8 >= 0) goto L_0x0021;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x0181, code lost:
        r0 = 2;
     */
    public static void setBitOp(IntNum result, int op, IntNum x, IntNum y) {
        int yi;
        int ylen;
        int xi;
        int xlen;
        int i;
        int ni;
        int i2;
        if (y.words != null && (x.words == null || x.ival < y.ival)) {
            IntNum temp = x;
            x = y;
            y = temp;
            op = swappedOp(op);
        }
        if (y.words == null) {
            yi = y.ival;
            ylen = 1;
        } else {
            yi = y.words[0];
            ylen = y.ival;
        }
        if (x.words == null) {
            xi = x.ival;
            xlen = 1;
        } else {
            xi = x.words[0];
            xlen = x.ival;
        }
        if (xlen > 1) {
            result.realloc(xlen);
        }
        int[] w = result.words;
        int i3 = 0;
        int finish = 0;
        switch (op) {
            case 0:
                ni = 0;
                i = 0;
                break;
            case 1:
                while (true) {
                    i = i3;
                    ni = xi & yi;
                    i3 = i + 1;
                    w[i] = ni;
                    xi = x.words[i3];
                    yi = y.words[i3];
                    break;
                }
            case 2:
                while (true) {
                    i = i3;
                    ni = xi & (yi ^ -1);
                    i3 = i + 1;
                    w[i] = ni;
                    xi = x.words[i3];
                    yi = y.words[i3];
                    break;
                }
            case 3:
                ni = xi;
                finish = 1;
                i = 0;
                break;
            case 4:
                while (true) {
                    i = i3;
                    ni = (xi ^ -1) & yi;
                    i3 = i + 1;
                    w[i] = ni;
                    xi = x.words[i3];
                    yi = y.words[i3];
                    break;
                }
            case 5:
                while (true) {
                    i = i3;
                    ni = yi;
                    i3 = i + 1;
                    w[i] = ni;
                    int xi2 = x.words[i3];
                    yi = y.words[i3];
                    break;
                }
            case 6:
                while (true) {
                    i = i3;
                    ni = xi ^ yi;
                    i3 = i + 1;
                    w[i] = ni;
                    xi = x.words[i3];
                    yi = y.words[i3];
                    break;
                }
            case 7:
                while (true) {
                    i = i3;
                    ni = xi | yi;
                    i3 = i + 1;
                    w[i] = ni;
                    xi = x.words[i3];
                    yi = y.words[i3];
                    break;
                }
            case 8:
                while (true) {
                    i = i3;
                    ni = (xi | yi) ^ -1;
                    i3 = i + 1;
                    w[i] = ni;
                    xi = x.words[i3];
                    yi = y.words[i3];
                    break;
                }
            case 9:
                while (true) {
                    i = i3;
                    ni = (xi ^ yi) ^ -1;
                    i3 = i + 1;
                    w[i] = ni;
                    xi = x.words[i3];
                    yi = y.words[i3];
                    break;
                }
            case 10:
                while (true) {
                    i = i3;
                    ni = yi ^ -1;
                    i3 = i + 1;
                    w[i] = ni;
                    int xi3 = x.words[i3];
                    yi = y.words[i3];
                    break;
                }
            case 11:
                while (true) {
                    i = i3;
                    ni = xi | (yi ^ -1);
                    i3 = i + 1;
                    w[i] = ni;
                    xi = x.words[i3];
                    yi = y.words[i3];
                    break;
                }
            case 12:
                ni = xi ^ -1;
                finish = 2;
                i = 0;
                break;
            case 13:
                while (true) {
                    i = i3;
                    ni = (xi ^ -1) | yi;
                    i3 = i + 1;
                    w[i] = ni;
                    xi = x.words[i3];
                    yi = y.words[i3];
                    break;
                }
            case 14:
                while (true) {
                    i = i3;
                    ni = (xi & yi) ^ -1;
                    i3 = i + 1;
                    w[i] = ni;
                    xi = x.words[i3];
                    yi = y.words[i3];
                    break;
                }
            default:
                ni = -1;
                i = 0;
                break;
        }
        if (i + 1 == xlen) {
            finish = 0;
        }
        switch (finish) {
            case 0:
                if (i != 0 || w != null) {
                    i2 = i + 1;
                    w[i] = ni;
                    break;
                } else {
                    result.ival = ni;
                    int i4 = i;
                    return;
                }
            case 1:
                w[i] = ni;
                i2 = i;
                while (true) {
                    i2++;
                    if (i2 >= xlen) {
                        break;
                    } else {
                        w[i2] = x.words[i2];
                    }
                }
            case 2:
                w[i] = ni;
                i2 = i;
                while (true) {
                    i2++;
                    if (i2 >= xlen) {
                        break;
                    } else {
                        w[i2] = x.words[i2] ^ -1;
                    }
                }
            default:
                i2 = i;
                break;
        }
        result.ival = i2;
    }

    public static IntNum extract(IntNum x, int startBit, int endBit) {
        int x_len;
        long l;
        int i;
        if (endBit < 32) {
            return IntNum.make((((-1 << endBit) ^ -1) & (x.words == null ? x.ival : x.words[0])) >> startBit);
        }
        if (x.words != null) {
            x_len = x.ival;
        } else if (x.ival >= 0) {
            return IntNum.make(startBit >= 31 ? 0 : x.ival >> startBit);
        } else {
            x_len = 1;
        }
        boolean neg = x.isNegative();
        if (endBit > x_len * 32) {
            endBit = x_len * 32;
            if (!neg && startBit == 0) {
                return x;
            }
        } else {
            x_len = (endBit + 31) >> 5;
        }
        int length = endBit - startBit;
        if (length < 64) {
            if (x.words == null) {
                int i2 = x.ival;
                if (startBit >= 32) {
                    i = 31;
                } else {
                    i = startBit;
                }
                l = (long) (i2 >> i);
            } else {
                l = MPN.rshift_long(x.words, x_len, startBit);
            }
            return IntNum.make(((-1 << length) ^ -1) & l);
        }
        int startWord = startBit >> 5;
        int[] buf = new int[(((endBit >> 5) + 1) - startWord)];
        if (x.words == null) {
            buf[0] = startBit >= 32 ? -1 : x.ival >> startBit;
        } else {
            MPN.rshift0(buf, x.words, startWord, x_len - startWord, startBit & 31);
        }
        int x_len2 = length >> 5;
        buf[x_len2] = buf[x_len2] & ((-1 << length) ^ -1);
        return IntNum.make(buf, x_len2 + 1);
    }

    public static int lowestBitSet(int i) {
        if (i == 0) {
            return -1;
        }
        int index = 0;
        while ((i & 255) == 0) {
            i >>>= 8;
            index += 8;
        }
        while ((i & 3) == 0) {
            i >>>= 2;
            index += 2;
        }
        if ((i & 1) == 0) {
            return index + 1;
        }
        return index;
    }

    public static int lowestBitSet(IntNum x) {
        int[] x_words = x.words;
        if (x_words == null) {
            return lowestBitSet(x.ival);
        }
        int x_len = x.ival;
        while (0 < x_len) {
            int b = lowestBitSet(x_words[0]);
            if (b >= 0) {
                return b + 0;
            }
        }
        return -1;
    }

    public static int bitCount(int i) {
        int count = 0;
        while (i != 0) {
            count += bit4_count[i & 15];
            i >>>= 4;
        }
        return count;
    }

    public static int bitCount(int[] x, int len) {
        int count = 0;
        while (true) {
            len--;
            if (len < 0) {
                return count;
            }
            count += bitCount(x[len]);
        }
    }

    public static int bitCount(IntNum x) {
        int x_len;
        int i;
        int[] x_words = x.words;
        if (x_words == null) {
            x_len = 1;
            i = bitCount(x.ival);
        } else {
            x_len = x.ival;
            i = bitCount(x_words, x_len);
        }
        return x.isNegative() ? (x_len * 32) - i : i;
    }

    public static IntNum reverseBits(IntNum x, int start, int end) {
        int wi;
        int ival = x.ival;
        if (x.words != null || end >= 63) {
            int[] data = dataBufferFor(x, end - 1);
            int i = start;
            for (int j = end - 1; i < j; j--) {
                int ii = i >> 5;
                int jj = j >> 5;
                int wi2 = data[ii];
                int biti = (wi2 >> i) & 1;
                if (ii == jj) {
                    int i2 = biti << j;
                    wi = i2 | ((int) (((long) wi2) & (((1 << i) | (1 << j)) ^ -1))) | (((wi2 >> j) & 1) << i);
                } else {
                    int wj = data[jj];
                    int bitj = (wj >> (j & 31)) & 1;
                    wi = (wi2 & ((1 << (i & 31)) ^ -1)) | (bitj << (i & 31));
                    data[jj] = (wj & ((1 << (j & 31)) ^ -1)) | (biti << (j & 31));
                }
                data[ii] = wi;
                i++;
            }
            return IntNum.make(data, data.length);
        }
        long w = (long) ival;
        int i3 = start;
        for (int j2 = end - 1; i3 < j2; j2--) {
            long biti2 = (w >> i3) & 1;
            long bitj2 = (w >> j2) & 1;
            w = (biti2 << j2) | (w & (((1 << i3) | (1 << j2)) ^ -1)) | (bitj2 << i3);
            i3++;
        }
        return IntNum.make(w);
    }
}
