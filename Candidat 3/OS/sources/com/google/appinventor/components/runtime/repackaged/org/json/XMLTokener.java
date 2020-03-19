package com.google.appinventor.components.runtime.repackaged.org.json;

import java.util.HashMap;

public class XMLTokener extends JSONTokener {
    public static final HashMap entity = new HashMap(8);

    static {
        entity.put("amp", XML.AMP);
        entity.put("apos", XML.APOS);
        entity.put("gt", XML.GT);
        entity.put("lt", XML.LT);
        entity.put("quot", XML.QUOT);
    }

    public XMLTokener(String s) {
        super(s);
    }

    public String nextCDATA() throws JSONException {
        StringBuffer sb = new StringBuffer();
        while (true) {
            char c = next();
            if (end()) {
                throw syntaxError("Unclosed CDATA");
            }
            sb.append(c);
            int i = sb.length() - 3;
            if (i >= 0 && sb.charAt(i) == ']' && sb.charAt(i + 1) == ']' && sb.charAt(i + 2) == '>') {
                sb.setLength(i);
                return sb.toString();
            }
        }
    }

    public Object nextContent() throws JSONException {
        char c;
        do {
            c = next();
        } while (Character.isWhitespace(c));
        if (c == 0) {
            return null;
        }
        if (c == '<') {
            return XML.LT;
        }
        StringBuffer sb = new StringBuffer();
        while (c != '<' && c != 0) {
            if (c == '&') {
                sb.append(nextEntity(c));
            } else {
                sb.append(c);
            }
            c = next();
        }
        back();
        return sb.toString().trim();
    }

    public Object nextEntity(char ampersand) throws JSONException {
        char c;
        StringBuffer sb = new StringBuffer();
        while (true) {
            c = next();
            if (!Character.isLetterOrDigit(c) && c != '#') {
                break;
            }
            sb.append(Character.toLowerCase(c));
        }
        if (c == ';') {
            String string = sb.toString();
            Object object = entity.get(string);
            return object != null ? object : new StringBuffer().append(ampersand).append(string).append(";").toString();
        }
        throw syntaxError(new StringBuffer().append("Missing ';' in XML entity: &").append(sb).toString());
    }

    /* JADX WARNING: Removed duplicated region for block: B:3:0x000d A[LOOP_START] */
    public Object nextMeta() throws JSONException {
        char c;
        char c2;
        do {
            c = next();
        } while (Character.isWhitespace(c));
        switch (c) {
            case 0:
                throw syntaxError("Misshaped meta tag");
            case '!':
                return XML.BANG;
            case '\"':
            case '\'':
                char q = c;
                do {
                    c2 = next();
                    if (c2 == 0) {
                        throw syntaxError("Unterminated string");
                    }
                } while (c2 != q);
                return Boolean.TRUE;
            case '/':
                return XML.SLASH;
            case '<':
                return XML.LT;
            case '=':
                return XML.EQ;
            case '>':
                return XML.GT;
            case '?':
                return XML.QUEST;
        }
        while (true) {
            char c3 = next();
            if (Character.isWhitespace(c3)) {
                return Boolean.TRUE;
            }
            switch (c3) {
                case 0:
                case '!':
                case '\"':
                case '\'':
                case '/':
                case '<':
                case '=':
                case '>':
                case '?':
                    break;
            }
            back();
            return Boolean.TRUE;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:4:0x0012 A[LOOP_START, PHI: r0 
  PHI: (r0v2 'c' char) = (r0v0 'c' char), (r0v3 'c' char) binds: [B:3:0x000d, B:27:0x006b] A[DONT_GENERATE, DONT_INLINE]] */
    public Object nextToken() throws JSONException {
        char c;
        do {
            c = next();
        } while (Character.isWhitespace(c));
        switch (c) {
            case 0:
                throw syntaxError("Misshaped element");
            case '!':
                return XML.BANG;
            case '\"':
            case '\'':
                char q = c;
                StringBuffer sb = new StringBuffer();
                while (true) {
                    char c2 = next();
                    if (c2 == 0) {
                        throw syntaxError("Unterminated string");
                    } else if (c2 == q) {
                        return sb.toString();
                    } else {
                        if (c2 == '&') {
                            sb.append(nextEntity(c2));
                        } else {
                            sb.append(c2);
                        }
                    }
                }
            case '/':
                return XML.SLASH;
            case '<':
                throw syntaxError("Misplaced '<'");
            case '=':
                return XML.EQ;
            case '>':
                return XML.GT;
            case '?':
                return XML.QUEST;
            default:
                StringBuffer sb2 = new StringBuffer();
                while (true) {
                    sb2.append(c);
                    c = next();
                    if (Character.isWhitespace(c)) {
                        return sb2.toString();
                    }
                    switch (c) {
                        case 0:
                            return sb2.toString();
                        case '!':
                        case '/':
                        case '=':
                        case '>':
                        case '?':
                        case '[':
                        case ']':
                            back();
                            return sb2.toString();
                        case '\"':
                        case '\'':
                        case '<':
                            throw syntaxError("Bad character in a name");
                    }
                }
        }
    }

    /* JADX WARNING: CFG modification limit reached, blocks count: 127 */
    public boolean skipPast(String to) throws JSONException {
        boolean z = false;
        int offset = 0;
        int length = to.length();
        char[] circle = new char[length];
        int i = 0;
        while (true) {
            if (i < length) {
                char c = next();
                if (c == 0) {
                    break;
                }
                circle[i] = c;
                i++;
            } else {
                do {
                    int j = offset;
                    boolean b = true;
                    int i2 = 0;
                    while (true) {
                        if (i2 < length) {
                            if (circle[j] != to.charAt(i2)) {
                                b = false;
                                break;
                            }
                            j++;
                            if (j >= length) {
                                j -= length;
                            }
                            i2++;
                        }
                    }
                    if (b) {
                        z = true;
                    } else {
                        char c2 = next();
                        if (c2 != 0) {
                            circle[offset] = c2;
                            offset++;
                        }
                    }
                } while (offset < length);
                offset -= length;
            }
        }
        return z;
    }
}
