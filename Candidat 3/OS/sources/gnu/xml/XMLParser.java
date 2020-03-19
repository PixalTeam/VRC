package gnu.xml;

import gnu.lists.Consumer;
import gnu.text.LineBufferedReader;
import gnu.text.LineInputStreamReader;
import gnu.text.Path;
import gnu.text.SourceMessages;
import java.io.IOException;
import java.io.InputStream;

public class XMLParser {
    private static final int ATTRIBUTE_SEEN_EQ_STATE = 11;
    private static final int ATTRIBUTE_SEEN_NAME_STATE = 8;
    static final String BAD_ENCODING_SYNTAX = "bad 'encoding' declaration";
    static final String BAD_STANDALONE_SYNTAX = "bad 'standalone' declaration";
    private static final int BEGIN_ELEMENT_STATE = 2;
    private static final int DOCTYPE_NAME_SEEN_STATE = 16;
    private static final int DOCTYPE_SEEN_STATE = 13;
    private static final int END_ELEMENT_STATE = 4;
    private static final int EXPECT_NAME_MODIFIER = 1;
    private static final int EXPECT_RIGHT_STATE = 27;
    private static final int INIT_LEFT_QUEST_STATE = 30;
    private static final int INIT_LEFT_STATE = 34;
    private static final int INIT_STATE = 0;
    private static final int INIT_TEXT_STATE = 31;
    private static final int INVALID_VERSION_DECL = 35;
    private static final int MAYBE_ATTRIBUTE_STATE = 10;
    private static final int PREV_WAS_CR_STATE = 28;
    private static final int SAW_AMP_SHARP_STATE = 26;
    private static final int SAW_AMP_STATE = 25;
    private static final int SAW_ENTITY_REF = 6;
    private static final int SAW_EOF_ERROR = 37;
    private static final int SAW_ERROR = 36;
    private static final int SAW_LEFT_EXCL_MINUS_STATE = 22;
    private static final int SAW_LEFT_EXCL_STATE = 20;
    private static final int SAW_LEFT_QUEST_STATE = 21;
    private static final int SAW_LEFT_SLASH_STATE = 19;
    private static final int SAW_LEFT_STATE = 14;
    private static final int SKIP_SPACES_MODIFIER = 2;
    private static final int TEXT_STATE = 1;

    public static void parse(Object uri, SourceMessages messages, Consumer out) throws IOException {
        parse(Path.openInputStream(uri), uri, messages, out);
    }

    public static LineInputStreamReader XMLStreamReader(InputStream strm) throws IOException {
        int b4 = -1;
        LineInputStreamReader in = new LineInputStreamReader(strm);
        int b1 = in.getByte();
        int b2 = b1 < 0 ? -1 : in.getByte();
        int b3 = b2 < 0 ? -1 : in.getByte();
        if (b1 == 239 && b2 == 187 && b3 == 191) {
            in.resetStart(3);
            in.setCharset("UTF-8");
        } else if (b1 == 255 && b2 == 254 && b3 != 0) {
            in.resetStart(2);
            in.setCharset("UTF-16LE");
        } else if (b1 == 254 && b2 == 255 && b3 != 0) {
            in.resetStart(2);
            in.setCharset("UTF-16BE");
        } else {
            if (b3 >= 0) {
                b4 = in.getByte();
            }
            if (b1 == 76 && b2 == 111 && b3 == 167 && b4 == 148) {
                throw new RuntimeException("XMLParser: EBCDIC encodings not supported");
            }
            in.resetStart(0);
            if ((b1 == 60 && ((b2 == 63 && b3 == 120 && b4 == 109) || (b2 == 0 && b3 == 63 && b4 == 0))) || (b1 == 0 && b2 == 60 && b3 == 0 && b4 == 63)) {
                char[] buffer = in.buffer;
                if (buffer == null) {
                    buffer = new char[8192];
                    in.buffer = buffer;
                }
                int pos = 0;
                int quote = 0;
                while (true) {
                    int b = in.getByte();
                    if (b != 0) {
                        if (b < 0) {
                            break;
                        }
                        int pos2 = pos + 1;
                        buffer[pos] = (char) (b & 255);
                        if (quote == 0) {
                            if (b == 62) {
                                pos = pos2;
                                break;
                            } else if (b == 39 || b == 34) {
                                quote = b;
                            }
                        } else if (b == quote) {
                            quote = 0;
                        }
                        pos = pos2;
                    }
                }
                in.pos = 0;
                in.limit = pos;
            } else {
                in.setCharset("UTF-8");
            }
        }
        in.setKeepFullLines(false);
        return in;
    }

    public static void parse(InputStream strm, Object uri, SourceMessages messages, Consumer out) throws IOException {
        LineInputStreamReader in = XMLStreamReader(strm);
        if (uri != null) {
            in.setName(uri);
        }
        parse((LineBufferedReader) in, messages, out);
        in.close();
    }

    public static void parse(LineBufferedReader in, SourceMessages messages, Consumer out) throws IOException {
        XMLFilter filter = new XMLFilter(out);
        filter.setMessages(messages);
        filter.setSourceLocator(in);
        filter.startDocument();
        Path uri = in.getPath();
        if (uri != null) {
            filter.writeDocumentUri(uri);
        }
        parse(in, filter);
        filter.endDocument();
    }

    public static void parse(LineBufferedReader in, SourceMessages messages, XMLFilter filter) throws IOException {
        filter.setMessages(messages);
        filter.setSourceLocator(in);
        filter.startDocument();
        Path uri = in.getPath();
        if (uri != null) {
            filter.writeDocumentUri(uri);
        }
        parse(in, filter);
        filter.endDocument();
        in.close();
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [char[]] */
    /* JADX WARNING: type inference failed for: r23v0 */
    /* JADX WARNING: type inference failed for: r9v0 */
    /* JADX WARNING: type inference failed for: r23v1 */
    /* JADX WARNING: type inference failed for: r9v1 */
    /* JADX WARNING: type inference failed for: r3v1, types: [char[]] */
    /* JADX WARNING: type inference failed for: r23v2 */
    /* JADX WARNING: type inference failed for: r9v2 */
    /* JADX WARNING: type inference failed for: r3v2 */
    /* JADX WARNING: type inference failed for: r23v3 */
    /* JADX WARNING: type inference failed for: r23v4 */
    /* JADX WARNING: type inference failed for: r23v5 */
    /* JADX WARNING: type inference failed for: r23v6 */
    /* JADX WARNING: type inference failed for: r9v3 */
    /* JADX WARNING: type inference failed for: r23v7 */
    /* JADX WARNING: type inference failed for: r9v4, types: [char] */
    /* JADX WARNING: type inference failed for: r0v15 */
    /* JADX WARNING: type inference failed for: r23v8 */
    /* JADX WARNING: type inference failed for: r23v9 */
    /* JADX WARNING: type inference failed for: r23v10 */
    /* JADX WARNING: type inference failed for: r23v11 */
    /* JADX WARNING: type inference failed for: r9v5 */
    /* JADX WARNING: type inference failed for: r2v31, types: [char] */
    /* JADX WARNING: type inference failed for: r2v33, types: [char] */
    /* JADX WARNING: type inference failed for: r2v35, types: [char] */
    /* JADX WARNING: type inference failed for: r2v37, types: [char] */
    /* JADX WARNING: type inference failed for: r2v39, types: [char] */
    /* JADX WARNING: type inference failed for: r2v41, types: [char] */
    /* JADX WARNING: type inference failed for: r2v46, types: [char] */
    /* JADX WARNING: type inference failed for: r2v48, types: [char] */
    /* JADX WARNING: type inference failed for: r2v50, types: [char] */
    /* JADX WARNING: type inference failed for: r2v52, types: [char] */
    /* JADX WARNING: type inference failed for: r2v54, types: [char] */
    /* JADX WARNING: type inference failed for: r2v56, types: [char] */
    /* JADX WARNING: type inference failed for: r2v58, types: [char] */
    /* JADX WARNING: type inference failed for: r2v60, types: [char] */
    /* JADX WARNING: type inference failed for: r2v62, types: [char] */
    /* JADX WARNING: type inference failed for: r2v64, types: [char] */
    /* JADX WARNING: type inference failed for: r2v66, types: [char] */
    /* JADX WARNING: type inference failed for: r2v68, types: [char] */
    /* JADX WARNING: type inference failed for: r2v70, types: [char] */
    /* JADX WARNING: type inference failed for: r9v6, types: [char] */
    /* JADX WARNING: type inference failed for: r9v7 */
    /* JADX WARNING: type inference failed for: r9v8, types: [char] */
    /* JADX WARNING: type inference failed for: r2v73, types: [char] */
    /* JADX WARNING: type inference failed for: r9v9 */
    /* JADX WARNING: type inference failed for: r2v76, types: [char] */
    /* JADX WARNING: type inference failed for: r2v78, types: [char] */
    /* JADX WARNING: type inference failed for: r2v80, types: [char] */
    /* JADX WARNING: type inference failed for: r2v83, types: [char] */
    /* JADX WARNING: type inference failed for: r2v85, types: [char] */
    /* JADX WARNING: type inference failed for: r2v87, types: [char] */
    /* JADX WARNING: type inference failed for: r2v89, types: [char] */
    /* JADX WARNING: type inference failed for: r2v91, types: [char] */
    /* JADX WARNING: type inference failed for: r2v93, types: [char] */
    /* JADX WARNING: type inference failed for: r2v95, types: [char] */
    /* JADX WARNING: type inference failed for: r9v10, types: [char] */
    /* JADX WARNING: type inference failed for: r9v11, types: [char] */
    /* JADX WARNING: type inference failed for: r9v12, types: [char] */
    /* JADX WARNING: type inference failed for: r9v13, types: [char] */
    /* JADX WARNING: type inference failed for: r20v0 */
    /* JADX WARNING: type inference failed for: r9v14 */
    /* JADX WARNING: type inference failed for: r9v15, types: [char] */
    /* JADX WARNING: type inference failed for: r0v26 */
    /* JADX WARNING: type inference failed for: r9v16 */
    /* JADX WARNING: type inference failed for: r9v17 */
    /* JADX WARNING: type inference failed for: r9v18 */
    /* JADX WARNING: type inference failed for: r2v103, types: [char] */
    /* JADX WARNING: type inference failed for: r2v105, types: [char] */
    /* JADX WARNING: type inference failed for: r2v107, types: [char] */
    /* JADX WARNING: type inference failed for: r2v109, types: [char] */
    /* JADX WARNING: type inference failed for: r2v111, types: [char] */
    /* JADX WARNING: type inference failed for: r2v113, types: [char] */
    /* JADX WARNING: type inference failed for: r2v115, types: [char] */
    /* JADX WARNING: type inference failed for: r2v117, types: [char] */
    /* JADX WARNING: type inference failed for: r2v119, types: [char] */
    /* JADX WARNING: type inference failed for: r2v121, types: [char] */
    /* JADX WARNING: type inference failed for: r9v19, types: [char] */
    /* JADX WARNING: type inference failed for: r9v20, types: [char] */
    /* JADX WARNING: type inference failed for: r9v21, types: [char] */
    /* JADX WARNING: type inference failed for: r9v22, types: [char] */
    /* JADX WARNING: type inference failed for: r20v1 */
    /* JADX WARNING: type inference failed for: r9v23 */
    /* JADX WARNING: type inference failed for: r9v24, types: [char] */
    /* JADX WARNING: type inference failed for: r0v27 */
    /* JADX WARNING: type inference failed for: r2v128, types: [char] */
    /* JADX WARNING: type inference failed for: r2v130, types: [char] */
    /* JADX WARNING: type inference failed for: r2v131, types: [char] */
    /* JADX WARNING: type inference failed for: r2v133, types: [char] */
    /* JADX WARNING: type inference failed for: r2v135, types: [char] */
    /* JADX WARNING: type inference failed for: r2v137, types: [char] */
    /* JADX WARNING: type inference failed for: r9v25, types: [char] */
    /* JADX WARNING: type inference failed for: r9v26, types: [char] */
    /* JADX WARNING: type inference failed for: r2v139, types: [char] */
    /* JADX WARNING: type inference failed for: r2v141, types: [char] */
    /* JADX WARNING: type inference failed for: r2v143, types: [char] */
    /* JADX WARNING: type inference failed for: r2v145, types: [char] */
    /* JADX WARNING: type inference failed for: r2v147, types: [char] */
    /* JADX WARNING: type inference failed for: r2v149, types: [char] */
    /* JADX WARNING: type inference failed for: r2v151, types: [char] */
    /* JADX WARNING: type inference failed for: r2v153, types: [char] */
    /* JADX WARNING: type inference failed for: r9v27, types: [char] */
    /* JADX WARNING: type inference failed for: r9v28, types: [char] */
    /* JADX WARNING: type inference failed for: r9v29, types: [char] */
    /* JADX WARNING: type inference failed for: r9v30, types: [char] */
    /* JADX WARNING: type inference failed for: r20v2 */
    /* JADX WARNING: type inference failed for: r9v31 */
    /* JADX WARNING: type inference failed for: r9v32, types: [char] */
    /* JADX WARNING: type inference failed for: r0v28 */
    /* JADX WARNING: type inference failed for: r2v160, types: [char] */
    /* JADX WARNING: type inference failed for: r9v33, types: [char] */
    /* JADX WARNING: type inference failed for: r9v34, types: [char] */
    /* JADX WARNING: type inference failed for: r2v165, types: [char] */
    /* JADX WARNING: type inference failed for: r9v35 */
    /* JADX WARNING: type inference failed for: r2v168, types: [char] */
    /* JADX WARNING: type inference failed for: r2v170, types: [char] */
    /* JADX WARNING: type inference failed for: r9v36, types: [char] */
    /* JADX WARNING: type inference failed for: r9v37, types: [char] */
    /* JADX WARNING: type inference failed for: r9v38, types: [char] */
    /* JADX WARNING: type inference failed for: r9v39, types: [char] */
    /* JADX WARNING: type inference failed for: r9v40, types: [char] */
    /* JADX WARNING: type inference failed for: r9v41 */
    /* JADX WARNING: type inference failed for: r9v42, types: [char] */
    /* JADX WARNING: type inference failed for: r9v43 */
    /* JADX WARNING: type inference failed for: r0v48 */
    /* JADX WARNING: type inference failed for: r9v44 */
    /* JADX WARNING: type inference failed for: r9v45 */
    /* JADX WARNING: type inference failed for: r9v46, types: [char] */
    /* JADX WARNING: type inference failed for: r9v47, types: [char] */
    /* JADX WARNING: type inference failed for: r9v48 */
    /* JADX WARNING: type inference failed for: r9v49, types: [char] */
    /* JADX WARNING: type inference failed for: r23v12 */
    /* JADX WARNING: type inference failed for: r9v50 */
    /* JADX WARNING: type inference failed for: r3v3, types: [char[]] */
    /* JADX WARNING: type inference failed for: r9v51, types: [char] */
    /* JADX WARNING: type inference failed for: r9v52, types: [char] */
    /* JADX WARNING: type inference failed for: r3v4 */
    /* JADX WARNING: type inference failed for: r23v13 */
    /* JADX WARNING: type inference failed for: r9v53 */
    /* JADX WARNING: type inference failed for: r23v14 */
    /* JADX WARNING: type inference failed for: r23v15 */
    /* JADX WARNING: type inference failed for: r23v16 */
    /* JADX WARNING: type inference failed for: r23v17 */
    /* JADX WARNING: type inference failed for: r23v18 */
    /* JADX WARNING: type inference failed for: r23v19 */
    /* JADX WARNING: type inference failed for: r23v20 */
    /* JADX WARNING: type inference failed for: r23v21 */
    /* JADX WARNING: type inference failed for: r23v22 */
    /* JADX WARNING: type inference failed for: r3v5 */
    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: type inference failed for: r3v7 */
    /* JADX WARNING: type inference failed for: r3v8 */
    /* JADX WARNING: type inference failed for: r3v9 */
    /* JADX WARNING: type inference failed for: r3v10 */
    /* JADX WARNING: type inference failed for: r3v11 */
    /* JADX WARNING: type inference failed for: r3v12 */
    /* JADX WARNING: type inference failed for: r3v13 */
    /* JADX WARNING: type inference failed for: r3v14 */
    /* JADX WARNING: type inference failed for: r3v15 */
    /* JADX WARNING: type inference failed for: r3v16 */
    /* JADX WARNING: type inference failed for: r3v17 */
    /* JADX WARNING: type inference failed for: r3v18 */
    /* JADX WARNING: type inference failed for: r3v19 */
    /* JADX WARNING: type inference failed for: r3v20 */
    /* JADX WARNING: type inference failed for: r3v21 */
    /* JADX WARNING: type inference failed for: r3v22 */
    /* JADX WARNING: type inference failed for: r3v23 */
    /* JADX WARNING: type inference failed for: r3v24 */
    /* JADX WARNING: type inference failed for: r3v25 */
    /* JADX WARNING: type inference failed for: r3v26 */
    /* JADX WARNING: type inference failed for: r3v27 */
    /* JADX WARNING: type inference failed for: r3v28 */
    /* JADX WARNING: type inference failed for: r3v29 */
    /* JADX WARNING: type inference failed for: r3v30 */
    /* JADX WARNING: type inference failed for: r3v31 */
    /* JADX WARNING: type inference failed for: r3v32 */
    /* JADX WARNING: type inference failed for: r3v33 */
    /* JADX WARNING: type inference failed for: r3v34 */
    /* JADX WARNING: type inference failed for: r3v35 */
    /* JADX WARNING: type inference failed for: r3v36 */
    /* JADX WARNING: type inference failed for: r23v23 */
    /* JADX WARNING: type inference failed for: r9v54 */
    /* JADX WARNING: type inference failed for: r3v37 */
    /* JADX WARNING: type inference failed for: r23v24 */
    /* JADX WARNING: type inference failed for: r23v25 */
    /* JADX WARNING: type inference failed for: r23v26 */
    /* JADX WARNING: type inference failed for: r23v27 */
    /* JADX WARNING: type inference failed for: r9v55 */
    /* JADX WARNING: type inference failed for: r9v56 */
    /* JADX WARNING: type inference failed for: r23v28 */
    /* JADX WARNING: type inference failed for: r23v29 */
    /* JADX WARNING: type inference failed for: r9v57 */
    /* JADX WARNING: type inference failed for: r23v30 */
    /* JADX WARNING: type inference failed for: r23v31 */
    /* JADX WARNING: type inference failed for: r23v32 */
    /* JADX WARNING: type inference failed for: r9v58 */
    /* JADX WARNING: type inference failed for: r9v59 */
    /* JADX WARNING: type inference failed for: r9v60 */
    /* JADX WARNING: type inference failed for: r9v61 */
    /* JADX WARNING: type inference failed for: r9v62 */
    /* JADX WARNING: type inference failed for: r9v63 */
    /* JADX WARNING: type inference failed for: r9v64 */
    /* JADX WARNING: type inference failed for: r9v65 */
    /* JADX WARNING: type inference failed for: r9v66 */
    /* JADX WARNING: type inference failed for: r9v67 */
    /* JADX WARNING: type inference failed for: r9v68 */
    /* JADX WARNING: type inference failed for: r9v69 */
    /* JADX WARNING: type inference failed for: r9v70 */
    /* JADX WARNING: type inference failed for: r9v71 */
    /* JADX WARNING: type inference failed for: r9v72 */
    /* JADX WARNING: type inference failed for: r9v73 */
    /* JADX WARNING: type inference failed for: r9v74 */
    /* JADX WARNING: type inference failed for: r9v75 */
    /* JADX WARNING: type inference failed for: r9v76 */
    /* JADX WARNING: type inference failed for: r9v77 */
    /* JADX WARNING: type inference failed for: r9v78 */
    /* JADX WARNING: type inference failed for: r9v79 */
    /* JADX WARNING: type inference failed for: r9v80 */
    /* JADX WARNING: type inference failed for: r9v81 */
    /* JADX WARNING: type inference failed for: r9v82 */
    /* JADX WARNING: type inference failed for: r9v83 */
    /* JADX WARNING: type inference failed for: r9v84 */
    /* JADX WARNING: type inference failed for: r9v85 */
    /* JADX WARNING: type inference failed for: r9v86 */
    /* JADX WARNING: type inference failed for: r9v87 */
    /* JADX WARNING: type inference failed for: r9v88 */
    /* JADX WARNING: type inference failed for: r9v89 */
    /* JADX WARNING: type inference failed for: r9v90 */
    /* JADX WARNING: type inference failed for: r9v91 */
    /* JADX WARNING: type inference failed for: r9v92 */
    /* JADX WARNING: type inference failed for: r9v93 */
    /* JADX WARNING: type inference failed for: r9v94 */
    /* JADX WARNING: type inference failed for: r9v95 */
    /* JADX WARNING: type inference failed for: r9v96 */
    /* JADX WARNING: type inference failed for: r9v97 */
    /* JADX WARNING: type inference failed for: r9v98 */
    /* JADX WARNING: type inference failed for: r9v99 */
    /* JADX WARNING: type inference failed for: r9v100 */
    /* JADX WARNING: type inference failed for: r9v101 */
    /* JADX WARNING: type inference failed for: r9v102 */
    /* JADX WARNING: type inference failed for: r9v103 */
    /* JADX WARNING: type inference failed for: r9v104 */
    /* JADX WARNING: type inference failed for: r9v105 */
    /* JADX WARNING: type inference failed for: r9v106 */
    /* JADX WARNING: type inference failed for: r9v107 */
    /* JADX WARNING: type inference failed for: r9v108 */
    /* JADX WARNING: type inference failed for: r9v109 */
    /* JADX WARNING: type inference failed for: r9v110 */
    /* JADX WARNING: type inference failed for: r9v111 */
    /* JADX WARNING: type inference failed for: r9v112 */
    /* JADX WARNING: type inference failed for: r9v113 */
    /* JADX WARNING: type inference failed for: r9v114 */
    /* JADX WARNING: type inference failed for: r9v115 */
    /* JADX WARNING: type inference failed for: r9v116 */
    /* JADX WARNING: type inference failed for: r23v33 */
    /* JADX WARNING: type inference failed for: r23v34 */
    /* JADX WARNING: type inference failed for: r23v35 */
    /* JADX WARNING: type inference failed for: r9v117 */
    /* JADX WARNING: type inference failed for: r3v38 */
    /* JADX WARNING: type inference failed for: r9v118 */
    /* JADX WARNING: type inference failed for: r9v119 */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x023d, code lost:
        if (r5 != 0) goto L_0x0878;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:162:0x0243, code lost:
        if (r22 != 8) goto L_0x024d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:163:0x0245, code lost:
        r17 = "missing or invalid attribute name";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:164:0x0247, code lost:
        r22 = 36;
        r18 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:166:0x0250, code lost:
        if (r22 == 2) goto L_0x0257;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:168:0x0255, code lost:
        if (r22 != 4) goto L_0x025a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:169:0x0257, code lost:
        r17 = "missing or invalid element name";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0053, code lost:
        r26.pos = r18;
        r27.error('e', r17);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:170:0x025a, code lost:
        r17 = "missing or invalid name";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:172:0x025f, code lost:
        if (r9 != 120(0x78)) goto L_0x0287;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x0261, code lost:
        if (r6 != 0) goto L_0x0287;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:174:0x0263, code lost:
        r6 = 16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:176:0x0269, code lost:
        if (r19 >= r16) goto L_0x0023;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:179:0x0273, code lost:
        if (r9 != 59) goto L_0x025d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0062, code lost:
        r19 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:180:0x0275, code lost:
        r26.pos = r19;
        r27.emitCharacterReference(r5, r3, r4, (r19 - 1) - r4);
        r22 = 1;
        r9 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:182:0x0289, code lost:
        if (r5 < 134217728) goto L_0x029e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:183:0x028b, code lost:
        r26.pos = r19;
        r27.error('e', "invalid character reference");
        r22 = 1;
        r9 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:184:0x029e, code lost:
        if (r6 != 0) goto L_0x02ad;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:185:0x02a0, code lost:
        r8 = 10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:186:0x02a2, code lost:
        r11 = java.lang.Character.digit(r9, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:187:0x02a6, code lost:
        if (r11 < 0) goto L_0x028b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:188:0x02a8, code lost:
        r5 = (r5 * r8) + r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:189:0x02ad, code lost:
        r8 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0068, code lost:
        if (r19 < r16) goto L_0x006d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x006a, code lost:
        r18 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x006d, code lost:
        r18 = r19 + 1;
        r9 = r3[r19];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0073, code lost:
        if (r9 != 62) goto L_0x0062;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0075, code lost:
        r22 = 1;
        r19 = r18;
        r9 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:275:0x03fc, code lost:
        if (r9 != 48) goto L_0x03fe;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0021, code lost:
        r19 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:414:0x05f2, code lost:
        if (r19 >= r16) goto L_0x0023;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:417:0x05fc, code lost:
        if (r9 != 62) goto L_0x0692;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:418:0x05fe, code lost:
        r5 = (r19 - 1) - r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:419:0x0603, code lost:
        if (r5 < 4) goto L_0x0638;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:421:0x0609, code lost:
        if (r3[r4] != 45) goto L_0x0638;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:423:0x0611, code lost:
        if (r3[r4 + 1] != 45) goto L_0x0638;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:425:0x0619, code lost:
        if (r3[r19 - 2] != 45) goto L_0x05ee;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:427:0x0621, code lost:
        if (r3[r19 - 3] != 45) goto L_0x05ee;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:428:0x0623, code lost:
        r26.pos = r19;
        r27.commentFromParser(r3, r4 + 2, r5 - 4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:429:0x0632, code lost:
        r4 = r16;
        r22 = 1;
        r9 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:431:0x0639, code lost:
        if (r5 < 6) goto L_0x0632;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:433:0x063f, code lost:
        if (r3[r4] != 91) goto L_0x0632;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:435:0x0647, code lost:
        if (r3[r4 + 1] != 67) goto L_0x0632;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:437:0x064f, code lost:
        if (r3[r4 + 2] != 68) goto L_0x0632;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:439:0x0657, code lost:
        if (r3[r4 + 3] != 65) goto L_0x0632;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:441:0x065f, code lost:
        if (r3[r4 + 4] != 84) goto L_0x0632;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:443:0x0667, code lost:
        if (r3[r4 + 5] != 65) goto L_0x0632;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:445:0x066f, code lost:
        if (r3[r4 + 6] != 91) goto L_0x0632;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:447:0x0677, code lost:
        if (r3[r19 - 2] != 93) goto L_0x05ee;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:449:0x067f, code lost:
        if (r3[r19 - 3] != 93) goto L_0x05ee;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:450:0x0681, code lost:
        r26.pos = r19;
        r27.writeCDATA(r3, r4 + 7, (r19 - 10) - r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:452:0x0696, code lost:
        if (r19 != (r4 + 7)) goto L_0x05ee;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:454:0x069c, code lost:
        if (r3[r4] != 68) goto L_0x05ee;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:456:0x06a4, code lost:
        if (r3[r4 + 1] != 79) goto L_0x05ee;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:458:0x06ac, code lost:
        if (r3[r4 + 2] != 67) goto L_0x05ee;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:460:0x06b4, code lost:
        if (r3[r4 + 3] != 84) goto L_0x05ee;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:462:0x06bc, code lost:
        if (r3[r4 + 4] != 89) goto L_0x05ee;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:464:0x06c4, code lost:
        if (r3[r4 + 5] != 80) goto L_0x05ee;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:466:0x06c8, code lost:
        if (r9 != 69) goto L_0x05ee;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:467:0x06ca, code lost:
        r4 = r16;
        r22 = 15;
        r9 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:569:0x0878, code lost:
        r18 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:580:0x001e, code lost:
        r3 = r3;
        r9 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:607:0x001e, code lost:
        r3 = r3;
        r9 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:621:0x0023, code lost:
        r9 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:642:0x04d5, code lost:
        r9 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:650:0x05c0, code lost:
        r9 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:661:0x0023, code lost:
        r9 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:666:?, code lost:
        return;
     */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v103, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v105, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v107, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v109, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v111, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v113, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v115, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v117, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v119, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v121, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v128, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v130, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v131, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v133, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v135, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v137, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v139, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v141, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v143, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v145, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v147, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v149, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v151, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v153, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v160, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v165, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v168, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v170, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v31, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v33, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v35, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v37, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v39, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v41, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v46, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v48, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v50, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v52, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v54, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v56, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v58, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v60, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v62, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v64, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v66, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v68, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v70, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v73, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v76, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v78, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v80, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v83, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v85, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v87, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v89, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v91, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v93, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r2v95, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v10, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v12, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v15, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v19, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v21, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v24, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v25, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v26, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v27, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v29, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v32, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v33, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v34, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v36, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v37, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v38, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v4, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v40, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v42, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v46, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v47, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v49, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v51, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v52, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v6, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v8, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char[], code=null, for r3v3, types: [char[]] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r23v2
  assigns: []
  uses: []
  mth insns count: 940
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
    /* JADX WARNING: Removed duplicated region for block: B:251:0x03ac  */
    /* JADX WARNING: Removed duplicated region for block: B:252:0x03b2  */
    /* JADX WARNING: Removed duplicated region for block: B:266:0x03de  */
    /* JADX WARNING: Removed duplicated region for block: B:311:0x0471  */
    /* JADX WARNING: Removed duplicated region for block: B:312:0x0479  */
    /* JADX WARNING: Removed duplicated region for block: B:326:0x04a9  */
    /* JADX WARNING: Removed duplicated region for block: B:367:0x053c  */
    /* JADX WARNING: Removed duplicated region for block: B:368:0x0544  */
    /* JADX WARNING: Removed duplicated region for block: B:382:0x0574  */
    /* JADX WARNING: Removed duplicated region for block: B:544:0x0806  */
    /* JADX WARNING: Removed duplicated region for block: B:5:0x0029  */
    /* JADX WARNING: Removed duplicated region for block: B:632:0x03d8 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:648:0x056c A[SYNTHETIC] */
    /* JADX WARNING: Unknown variable types count: 114 */
    public static void parse(LineBufferedReader in, XMLFilter out) {
        ? r23;
        ? r9;
        ? r3;
        int pos;
        ? r232;
        int i;
        int i2;
        ? r92;
        ? r93;
        int i3;
        boolean z;
        int pos2;
        ? r94;
        ? r95;
        int pos3;
        int start;
        ? r96;
        ? r32 = in.buffer;
        int pos4 = in.pos;
        int limit = in.limit;
        int state = 0;
        int continue_state = 14;
        int length = 0;
        int dstart = -1;
        String message = null;
        int start2 = limit;
        ? r33 = r32;
        ? r233 = 60;
        ? r97 = 32;
        while (true) {
            switch (state) {
                case 0:
                    state = 31;
                    pos = pos4;
                case 1:
                    start2 = pos4 - 1;
                    int length2 = pos4;
                    while (true) {
                        pos = pos4;
                        if (r9 == r23) {
                            state = continue_state;
                            pos2 = pos;
                            r95 = r9;
                        } else if (r9 == 38) {
                            state = 25;
                            pos2 = pos;
                            r95 = r9;
                        } else {
                            if (r9 == 13) {
                                length = pos - length2;
                                in.pos = pos;
                                if (length > 0) {
                                    out.textFromParser(r33, start2, length);
                                }
                                if (pos < limit) {
                                    ? r98 = r33[pos];
                                    if (r98 == 10) {
                                        start = pos;
                                        pos3 = pos + 1;
                                        length2 = pos3;
                                    } else {
                                        out.linefeedFromParser();
                                        if (r98 == 133) {
                                            pos3 = pos + 1;
                                            start = pos;
                                            length2 = pos3 + 1;
                                        } else {
                                            in.incrLineNumber(1, pos);
                                            start2 = pos;
                                            pos4 = pos + 1;
                                            length2 = pos4;
                                            r94 = r98;
                                            r9 = r94;
                                        }
                                    }
                                    in.incrLineNumber(1, pos3);
                                    pos = pos3;
                                    r9 = r98;
                                } else {
                                    out.linefeedFromParser();
                                    state = 28;
                                }
                            } else if (r9 == 133 || r9 == 8232) {
                                int length3 = pos - length2;
                                in.pos = pos - 1;
                                if (length3 > 0) {
                                    out.textFromParser(r33, start2, length3);
                                }
                                out.linefeedFromParser();
                                in.incrLineNumber(1, pos);
                                length2 = pos + 1;
                                start2 = pos;
                                r9 = r9;
                            } else if (r9 == 10) {
                                in.incrLineNumber(1, pos);
                            }
                            if (pos == limit) {
                                length2--;
                                pos2 = pos;
                                r95 = r9;
                            } else {
                                pos4 = pos + 1;
                                r94 = r33[pos];
                                r9 = r94;
                            }
                        }
                    }
                    length = pos2 - length2;
                    if (length > 0) {
                        in.pos = pos2;
                        out.textFromParser(r33, start2, length);
                    }
                    start2 = r33.length;
                    pos = pos2;
                    r9 = r96;
                    break;
                case 2:
                    in.pos = pos4 - length;
                    out.emitStartElement(r33, start2, length);
                    state = 12;
                    start2 = limit;
                    r3 = r33;
                    break;
                case 3:
                case 5:
                case 7:
                case 9:
                case 17:
                case 24:
                case 33:
                    length = start2 + 1;
                    while (true) {
                        pos = pos4;
                        if ((r9 < 97 || r9 > 122) && ((r9 < 65 || r9 > 90) && r9 != 95 && r9 != 58 && ((r9 < 192 || (r9 > 767 && (r9 < 880 || ((r9 > 8191 || r9 == 894) && (r9 < 8204 || (r9 > 8205 && ((r9 < 8304 || r9 > 8591) && ((r9 < 11264 || r9 > 12271) && ((r9 < 12289 || r9 > 55295) && (r9 < 63744 || r9 > 65533)))))))))) && !((pos > length && r9 >= 48 && r9 <= 57) || r9 == 46 || r9 == 45 || r9 == 183 || (r9 > 768 && (r9 <= 879 || (r9 >= 8255 && r9 <= 8256))))))) {
                            state--;
                            length = pos - length;
                            break;
                        } else if (pos < limit) {
                            pos4 = pos + 1;
                            r9 = r33[pos];
                        } else {
                            r9 = r9;
                        }
                    }
                    break;
                case 4:
                    in.pos = pos4;
                    out.emitEndElement(r33, start2, length);
                    start2 = limit;
                    state = 29;
                    r23 = r23;
                    r3 = r33;
                    break;
                case 6:
                    in.pos = pos4;
                    if (r9 != 59) {
                        out.error('w', "missing ';'");
                    }
                    out.emitEntityReference(r33, start2, length);
                    start2 = limit;
                    state = 1;
                    pos = pos4;
                    if (pos < limit) {
                        int saved = pos - start2;
                        if (saved > 0) {
                            try {
                                in.pos = start2;
                                in.mark(saved + 1);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex.getMessage());
                            }
                        }
                        in.pos = pos;
                        if (in.read() < 0) {
                            if (state != 1 && state != 28) {
                                state = 37;
                                pos4 = pos;
                                r3 = r33;
                                r23 = r23;
                                r9 = r9;
                                break;
                            } else {
                                int i4 = pos;
                                break;
                            }
                        } else {
                            if (saved > 0) {
                                in.reset();
                                in.skip(saved);
                            } else {
                                in.unread_quick();
                            }
                            int pos5 = in.pos;
                            ? r34 = in.buffer;
                            limit = in.limit;
                            if (saved > 0) {
                                start2 = pos5 - saved;
                            } else {
                                start2 = limit;
                            }
                            int pos6 = pos5 + 1;
                            ? r99 = r34[pos5];
                            pos4 = pos6;
                            r23 = r23;
                            r3 = r34;
                            r9 = r99;
                            break;
                        }
                    } else {
                        pos4 = pos + 1;
                        ? r910 = r33[pos];
                        r3 = r33;
                        r23 = r23;
                        r9 = r910;
                        break;
                    }
                    break;
                case 8:
                    if (!(r9 == 32 || r9 == 9 || r9 == 13 || r9 == 10 || r9 == 133)) {
                        if (r9 != 8232) {
                            in.pos = pos4 - length;
                            out.emitStartAttribute(r33, start2, length);
                            start2 = limit;
                            if (r9 != 61) {
                                out.emitEndAttributes();
                                message = "missing or misplaced '=' after attribute name";
                                state = 36;
                                r23 = r23;
                                r3 = r33;
                                break;
                            } else {
                                state = 11;
                                pos = pos4;
                                r23 = r23;
                            }
                        } else {
                            pos = pos4;
                            r23 = r23;
                        }
                        if (pos < limit) {
                        }
                    }
                    break;
                case 10:
                    ? r234 = 60;
                    continue_state = 14;
                    if (r9 != 47) {
                        if (r9 != 62) {
                            start2 = pos4 - 1;
                            state = 9;
                            r3 = r33;
                            r23 = r234;
                            break;
                        } else {
                            in.pos = pos4;
                            out.emitEndAttributes();
                            state = 1;
                            pos = pos4;
                            r23 = r234;
                        }
                    } else {
                        in.pos = pos4;
                        out.emitEndAttributes();
                        out.emitEndElement(null, 0, 0);
                        state = 27;
                        pos = pos4;
                        r23 = r234;
                    }
                case 11:
                    if (r9 != 39 && r9 != 34) {
                        if (!(r9 == 32 || r9 == 9 || r9 == 13 || r9 == 10 || r9 == 133)) {
                            if (r9 != 8232) {
                                message = "missing or unquoted attribute value";
                                state = 36;
                                r23 = r23;
                                r3 = r33;
                                break;
                            } else {
                                pos = pos4;
                                r23 = r23;
                                if (pos < limit) {
                                }
                            }
                        }
                    } else {
                        r23 = r9;
                        continue_state = 12;
                        state = 1;
                        pos = pos4;
                        if (pos < limit) {
                        }
                    }
                    break;
                case 12:
                case 15:
                case 23:
                case 29:
                case 32:
                    if (r9 != 32) {
                        if (r9 != 9) {
                            if (r9 != 10 && r9 != 13 && r9 != 133 && r9 != 8232) {
                                state -= 2;
                                r3 = r33;
                                break;
                            } else {
                                in.incrLineNumber(1, pos4);
                                pos = pos4;
                            }
                        } else {
                            pos = pos4;
                        }
                        if (pos < limit) {
                        }
                    }
                    break;
                case 13:
                    state = 17;
                    start2 = pos4 - 1;
                    r3 = r33;
                    break;
                case 14:
                    if (r9 != 47) {
                        if (r9 != 63) {
                            if (r9 != 33) {
                                start2 = pos4 - 1;
                                state = 3;
                                r3 = r33;
                                break;
                            } else {
                                state = 20;
                                start2 = pos4;
                                pos = pos4;
                            }
                        } else {
                            start2 = pos4;
                            state = 24;
                            pos = pos4;
                        }
                    } else {
                        state = 19;
                        pos = pos4;
                    }
                case 16:
                    if (dstart < 0) {
                        dstart = ((pos4 - 1) - start2) << 1;
                        r23 = 0;
                    }
                    ? r235 = r23;
                    while (true) {
                        pos = pos4;
                        if (r9 == 39 || r9 == 34) {
                            if (r232 == 0) {
                                r232 = r9;
                            } else if (r232 == r9) {
                                r232 = 0;
                            }
                        } else if (r232 == 0) {
                            if (r9 == 91) {
                                dstart |= 1;
                            } else if (r9 == 93) {
                                dstart &= -2;
                            } else if (r9 == 62 && (dstart & 1) == 0) {
                                in.pos = pos;
                                int dstart2 = (dstart >> 1) + start2;
                                out.emitDoctypeDecl(r33, start2, length, dstart2, (pos - 1) - dstart2);
                                start2 = limit;
                                dstart = -1;
                                state = 1;
                                r9 = r9;
                                r23 = 60;
                            }
                        }
                        if (pos < limit) {
                            pos4 = pos + 1;
                            r235 = r232;
                            r9 = r33[pos];
                        } else {
                            r9 = r9;
                            r23 = r232;
                        }
                    }
                    if (pos < limit) {
                    }
                    break;
                case 19:
                    start2 = pos4 - 1;
                    state = 5;
                    r23 = r23;
                    r3 = r33;
                    break;
                case 20:
                    while (true) {
                        pos = pos4;
                        pos4 = pos + 1;
                        r9 = r33[pos];
                        break;
                    }
                case 21:
                case 30:
                    if (dstart < 0) {
                        dstart = pos4 - 1;
                    }
                    while (true) {
                        pos = pos4;
                        if (r9 == 62) {
                            int end = pos - 2;
                            if (r33[end] == 63 && end >= dstart) {
                                in.pos = pos;
                                if (length == 3 && r33[start2] == 120 && r33[start2 + 1] == 109 && r33[start2 + 2] == 108) {
                                    if (state != 30) {
                                        message = "<?xml must be at start of file";
                                        state = 36;
                                        pos4 = pos;
                                        r3 = r33;
                                        r9 = r9;
                                        break;
                                    } else if (end <= dstart + 7 || r33[dstart] != 118 || r33[dstart + 1] != 101 || r33[dstart + 2] != 114 || r33[dstart + 3] != 115 || r33[dstart + 4] != 105 || r33[dstart + 5] != 111 || r33[dstart + 6] != 110) {
                                        pos4 = dstart;
                                        message = "xml declaration without version";
                                        state = 36;
                                        r3 = r33;
                                        r9 = r9;
                                        break;
                                    } else {
                                        int dstart3 = dstart + 7;
                                        ? r911 = r33[dstart3];
                                        while (Character.isWhitespace(r911)) {
                                            dstart3++;
                                            if (dstart3 < end) {
                                                r911 = r33[dstart3];
                                            } else if (r911 == 61) {
                                                state = 35;
                                                pos4 = pos;
                                                r3 = r33;
                                                r9 = r911;
                                                break;
                                            } else {
                                                int dstart4 = dstart + 1;
                                                ? r912 = r33[dstart4];
                                                while (Character.isWhitespace(r912)) {
                                                    dstart4++;
                                                    if (dstart4 >= end) {
                                                        if (r912 == 39 && r912 != 34) {
                                                            state = 35;
                                                            pos4 = pos;
                                                            r3 = r33;
                                                            r9 = r912;
                                                            break;
                                                        } else {
                                                            ? r20 = r912;
                                                            dstart++;
                                                            i = dstart;
                                                            r9 = r912;
                                                            while (true) {
                                                                if (i != end) {
                                                                    state = 35;
                                                                    pos4 = pos;
                                                                    r3 = r33;
                                                                    r9 = r9;
                                                                    break;
                                                                } else {
                                                                    r9 = r33[i];
                                                                    if (r9 == r20) {
                                                                        if (i == dstart + 3 && r33[dstart] == 49 && r33[dstart + 1] == 46) {
                                                                            r9 = r33[dstart + 2];
                                                                            break;
                                                                        }
                                                                        if (r9 != 49) {
                                                                            state = 35;
                                                                            pos4 = pos;
                                                                            r3 = r33;
                                                                            break;
                                                                        }
                                                                        int dstart5 = i + 1;
                                                                        while (dstart < end && Character.isWhitespace(r33[dstart])) {
                                                                            dstart5 = dstart + 1;
                                                                        }
                                                                        if (end > dstart + 7 && r33[dstart] == 101 && r33[dstart + 1] == 110 && r33[dstart + 2] == 99 && r33[dstart + 3] == 111 && r33[dstart + 4] == 100 && r33[dstart + 5] == 105 && r33[dstart + 6] == 110 && r33[dstart + 7] == 103) {
                                                                            int dstart6 = dstart + 8;
                                                                            ? r913 = r33[dstart6];
                                                                            while (Character.isWhitespace(r913)) {
                                                                                dstart6++;
                                                                                if (dstart6 < end) {
                                                                                    r913 = r33[dstart6];
                                                                                } else if (r913 == 61) {
                                                                                    message = BAD_ENCODING_SYNTAX;
                                                                                    state = 36;
                                                                                    pos4 = pos;
                                                                                    r3 = r33;
                                                                                    r9 = r913;
                                                                                    break;
                                                                                } else {
                                                                                    int dstart7 = dstart + 1;
                                                                                    ? r914 = r33[dstart7];
                                                                                    while (Character.isWhitespace(r914)) {
                                                                                        dstart7++;
                                                                                        if (dstart7 >= end) {
                                                                                            if (r914 == 39 && r914 != 34) {
                                                                                                message = BAD_ENCODING_SYNTAX;
                                                                                                state = 36;
                                                                                                pos4 = pos;
                                                                                                r3 = r33;
                                                                                                r9 = r914;
                                                                                                break;
                                                                                            } else {
                                                                                                ? r202 = r914;
                                                                                                dstart++;
                                                                                                i3 = dstart;
                                                                                                ? r915 = r914;
                                                                                                while (i3 != end) {
                                                                                                    ? r916 = r33[i3];
                                                                                                    if (r916 == r202) {
                                                                                                        String encoding = new String(r33, dstart, i3 - dstart);
                                                                                                        if (in instanceof LineInputStreamReader) {
                                                                                                            ((LineInputStreamReader) in).setCharset(encoding);
                                                                                                        }
                                                                                                        dstart = i3 + 1;
                                                                                                        while (dstart < end && Character.isWhitespace(r33[dstart])) {
                                                                                                            dstart++;
                                                                                                        }
                                                                                                    } else {
                                                                                                        i3++;
                                                                                                        r915 = r916;
                                                                                                    }
                                                                                                }
                                                                                                message = BAD_ENCODING_SYNTAX;
                                                                                                state = 36;
                                                                                                pos4 = pos;
                                                                                                r3 = r33;
                                                                                                r9 = r915;
                                                                                                break;
                                                                                            }
                                                                                        } else {
                                                                                            r914 = r33[dstart7];
                                                                                        }
                                                                                    }
                                                                                    if (r914 == 39) {
                                                                                    }
                                                                                    ? r2022 = r914;
                                                                                    dstart++;
                                                                                    i3 = dstart;
                                                                                    ? r9152 = r914;
                                                                                    while (i3 != end) {
                                                                                    }
                                                                                    message = BAD_ENCODING_SYNTAX;
                                                                                    state = 36;
                                                                                    pos4 = pos;
                                                                                    r3 = r33;
                                                                                    r9 = r9152;
                                                                                }
                                                                            }
                                                                            if (r913 == 61) {
                                                                            }
                                                                        }
                                                                        if (end > dstart + 9 && r33[dstart] == 115 && r33[dstart + 1] == 116 && r33[dstart + 2] == 97 && r33[dstart + 3] == 110 && r33[dstart + 4] == 100 && r33[dstart + 5] == 97 && r33[dstart + 6] == 108 && r33[dstart + 7] == 111 && r33[dstart + 8] == 110 && r33[dstart + 9] == 101) {
                                                                            int dstart8 = dstart + 10;
                                                                            ? r917 = r33[dstart8];
                                                                            while (Character.isWhitespace(r917)) {
                                                                                dstart8++;
                                                                                if (dstart8 < end) {
                                                                                    r917 = r33[dstart8];
                                                                                } else if (r917 == 61) {
                                                                                    message = BAD_STANDALONE_SYNTAX;
                                                                                    state = 36;
                                                                                    pos4 = pos;
                                                                                    r3 = r33;
                                                                                    r9 = r917;
                                                                                    break;
                                                                                } else {
                                                                                    int dstart9 = dstart + 1;
                                                                                    ? r918 = r33[dstart9];
                                                                                    while (Character.isWhitespace(r918)) {
                                                                                        dstart9++;
                                                                                        if (dstart9 >= end) {
                                                                                            if (r918 == 39 && r918 != 34) {
                                                                                                message = BAD_STANDALONE_SYNTAX;
                                                                                                state = 36;
                                                                                                pos4 = pos;
                                                                                                r3 = r33;
                                                                                                r9 = r918;
                                                                                                break;
                                                                                            } else {
                                                                                                ? r203 = r918;
                                                                                                dstart++;
                                                                                                i2 = dstart;
                                                                                                r92 = r918;
                                                                                                while (true) {
                                                                                                    if (i2 != end) {
                                                                                                        message = BAD_STANDALONE_SYNTAX;
                                                                                                        state = 36;
                                                                                                        pos4 = pos;
                                                                                                        r3 = r33;
                                                                                                        r9 = r92;
                                                                                                        break;
                                                                                                    } else {
                                                                                                        r93 = r33[i2];
                                                                                                        if (r93 != r203) {
                                                                                                            i2++;
                                                                                                            r92 = r93;
                                                                                                        } else if ((i2 != dstart + 3 || r33[dstart] != 121 || r33[dstart + 1] != 101 || r33[dstart + 2] != 115) && (i2 != dstart + 2 || r33[dstart] != 110 || r33[dstart + 1] != 111)) {
                                                                                                            message = BAD_STANDALONE_SYNTAX;
                                                                                                            state = 36;
                                                                                                            pos4 = pos;
                                                                                                            r3 = r33;
                                                                                                            r9 = r93;
                                                                                                            break;
                                                                                                        } else {
                                                                                                            int dstart10 = i2 + 1;
                                                                                                            while (dstart < end && Character.isWhitespace(r33[dstart])) {
                                                                                                                dstart10 = dstart + 1;
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        } else {
                                                                                            r918 = r33[dstart9];
                                                                                        }
                                                                                    }
                                                                                    if (r918 == 39) {
                                                                                    }
                                                                                    ? r2032 = r918;
                                                                                    dstart++;
                                                                                    i2 = dstart;
                                                                                    r92 = r918;
                                                                                    while (true) {
                                                                                        if (i2 != end) {
                                                                                        }
                                                                                        i2++;
                                                                                        r92 = r93;
                                                                                    }
                                                                                }
                                                                            }
                                                                            if (r917 == 61) {
                                                                            }
                                                                        }
                                                                        if (end != dstart) {
                                                                            message = "junk at end of xml declaration";
                                                                            pos4 = dstart;
                                                                            state = 36;
                                                                            r3 = r33;
                                                                            break;
                                                                        }
                                                                    } else {
                                                                        i++;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        r912 = r33[dstart4];
                                                    }
                                                }
                                                if (r912 == 39) {
                                                }
                                                ? r204 = r912;
                                                dstart++;
                                                i = dstart;
                                                r9 = r912;
                                                while (true) {
                                                    if (i != end) {
                                                    }
                                                    i++;
                                                }
                                            }
                                        }
                                        if (r911 == 61) {
                                        }
                                    }
                                } else {
                                    out.processingInstructionFromParser(r33, start2, length, dstart, end - dstart);
                                    r9 = r9;
                                }
                                start2 = limit;
                                dstart = -1;
                                state = 1;
                                r9 = r9;
                            }
                        }
                        if (pos < limit) {
                            pos4 = pos + 1;
                            r9 = r33[pos];
                        } else {
                            r9 = r9;
                        }
                    }
                    break;
                case 25:
                    if (r9 != 35) {
                        start2 = pos4 - 1;
                        state = 7;
                        r3 = r33;
                        break;
                    } else {
                        state = 26;
                        start2 = pos4;
                        length = 0;
                        dstart = 0;
                        pos = pos4;
                    }
                case 26:
                    while (true) {
                        pos = pos4;
                        pos4 = pos + 1;
                        r9 = r33[pos];
                        break;
                    }
                case 27:
                    if (r9 != 62) {
                        message = "missing '>'";
                        state = 36;
                        r23 = r23;
                        r3 = r33;
                        break;
                    } else {
                        state = 1;
                        pos = pos4;
                        r23 = r23;
                    }
                case 28:
                    state = 1;
                    if (!(r9 == 133) && !(r9 == 10)) {
                        in.incrLineNumber(1, pos4 - 1);
                        r3 = r33;
                        break;
                    } else {
                        in.incrLineNumber(1, pos4);
                        pos = pos4;
                    }
                case 31:
                    if (r9 != 60) {
                        state = 1;
                        r3 = r33;
                        break;
                    } else {
                        state = 34;
                        pos = pos4;
                    }
                case 34:
                    if (r9 != 63) {
                        state = 14;
                        r3 = r33;
                        break;
                    } else {
                        start2 = pos4;
                        state = 33;
                        pos = pos4;
                    }
                case 35:
                    pos4 = dstart;
                    message = "invalid xml version specifier";
                    break;
                case 36:
                    break;
                case 37:
                    in.pos = pos4;
                    out.error('f', "unexpected end-of-file");
                    return;
            }
            r233 = r23;
            r97 = r9;
            r33 = r3;
        }
        int i42 = pos;
    }
}
