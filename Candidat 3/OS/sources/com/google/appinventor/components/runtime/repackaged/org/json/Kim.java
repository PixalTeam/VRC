package com.google.appinventor.components.runtime.repackaged.org.json;

import com.google.appinventor.components.runtime.util.Ev3Constants.Opcode;
import java.util.Arrays;

public class Kim {
    private byte[] bytes;
    private int hashcode;
    public int length;
    private String string;

    public Kim(byte[] bytes2, int from, int thru) {
        this.bytes = null;
        this.hashcode = 0;
        this.length = 0;
        this.string = null;
        int sum = 1;
        this.hashcode = 0;
        this.length = thru - from;
        if (this.length > 0) {
            this.bytes = new byte[this.length];
            for (int at = 0; at < this.length; at++) {
                int value = bytes2[at + from] & 255;
                sum += value;
                this.hashcode += sum;
                this.bytes[at] = (byte) value;
            }
            this.hashcode += sum << 16;
        }
    }

    public Kim(byte[] bytes2, int length2) {
        this(bytes2, 0, length2);
    }

    public Kim(Kim kim, int from, int thru) {
        this(kim.bytes, from, thru);
    }

    public Kim(String string2) throws JSONException {
        this.bytes = null;
        this.hashcode = 0;
        this.length = 0;
        this.string = null;
        int stringLength = string2.length();
        this.hashcode = 0;
        this.length = 0;
        if (stringLength > 0) {
            int i = 0;
            while (i < stringLength) {
                int c = string2.charAt(i);
                if (c <= 127) {
                    this.length++;
                } else if (c <= 16383) {
                    this.length += 2;
                } else {
                    if (c >= 55296 && c <= 57343) {
                        i++;
                        int d = string2.charAt(i);
                        if (c > 56319 || d < 56320 || d > 57343) {
                            throw new JSONException("Bad UTF16");
                        }
                    }
                    this.length += 3;
                }
                i++;
            }
            this.bytes = new byte[this.length];
            int at = 0;
            int sum = 1;
            int i2 = 0;
            while (i2 < stringLength) {
                int character = string2.charAt(i2);
                if (character <= 127) {
                    this.bytes[at] = (byte) character;
                    sum += character;
                    this.hashcode += sum;
                } else if (character <= 16383) {
                    int b = (character >>> 7) | 128;
                    this.bytes[at] = (byte) b;
                    int sum2 = sum + b;
                    this.hashcode += sum2;
                    at++;
                    int b2 = character & 127;
                    this.bytes[at] = (byte) b2;
                    sum = sum2 + b2;
                    this.hashcode += sum;
                } else {
                    if (character >= 55296 && character <= 56319) {
                        i2++;
                        character = (((character & 1023) << 10) | (string2.charAt(i2) & 1023)) + 0;
                    }
                    int b3 = (character >>> 14) | 128;
                    this.bytes[at] = (byte) b3;
                    int sum3 = sum + b3;
                    this.hashcode += sum3;
                    int at2 = at + 1;
                    int b4 = ((character >>> 7) & 255) | 128;
                    this.bytes[at2] = (byte) b4;
                    int sum4 = sum3 + b4;
                    this.hashcode += sum4;
                    at = at2 + 1;
                    int b5 = character & 127;
                    this.bytes[at] = (byte) b5;
                    sum = sum4 + b5;
                    this.hashcode += sum;
                }
                at++;
                i2++;
            }
            this.hashcode += sum << 16;
        }
    }

    public int characterAt(int at) throws JSONException {
        int c = get(at);
        if ((c & 128) == 0) {
            return c;
        }
        int c1 = get(at + 1);
        if ((c1 & 128) == 0) {
            int character = ((c & 127) << 7) | c1;
            if (character > 127) {
                return character;
            }
        } else {
            int c2 = get(at + 2);
            int character2 = ((c & 127) << 14) | ((c1 & 127) << 7) | c2;
            if ((c2 & 128) == 0 && character2 > 16383 && character2 <= 1114111 && (character2 < 55296 || character2 > 57343)) {
                return character2;
            }
        }
        throw new JSONException(new StringBuffer().append("Bad character at ").append(at).toString());
    }

    public static int characterSize(int character) throws JSONException {
        if (character < 0 || character > 1114111) {
            throw new JSONException(new StringBuffer().append("Bad character ").append(character).toString());
        } else if (character <= 127) {
            return 1;
        } else {
            return character <= 16383 ? 2 : 3;
        }
    }

    public int copy(byte[] bytes2, int at) {
        System.arraycopy(this.bytes, 0, bytes2, at, this.length);
        return this.length + at;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Kim)) {
            return false;
        }
        Kim that = (Kim) obj;
        if (this == that) {
            return true;
        }
        if (this.hashcode == that.hashcode) {
            return Arrays.equals(this.bytes, that.bytes);
        }
        return false;
    }

    public int get(int at) throws JSONException {
        if (at >= 0 && at <= this.length) {
            return this.bytes[at] & Opcode.TST;
        }
        throw new JSONException(new StringBuffer().append("Bad character at ").append(at).toString());
    }

    public int hashCode() {
        return this.hashcode;
    }

    public String toString() throws JSONException {
        if (this.string == null) {
            int length2 = 0;
            char[] chars = new char[this.length];
            int at = 0;
            while (at < this.length) {
                int c = characterAt(at);
                if (c < 65536) {
                    chars[length2] = (char) c;
                } else {
                    chars[length2] = (char) (55296 | ((c - 65536) >>> 10));
                    length2++;
                    chars[length2] = (char) (56320 | (c & 1023));
                }
                length2++;
                at += characterSize(c);
            }
            this.string = new String(chars, 0, length2);
        }
        return this.string;
    }
}
