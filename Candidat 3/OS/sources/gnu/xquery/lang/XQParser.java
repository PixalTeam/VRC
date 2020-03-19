package gnu.xquery.lang;

import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import gnu.bytecode.Access;
import gnu.bytecode.ClassType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.LambdaExp;
import gnu.expr.LetExp;
import gnu.expr.ModuleExp;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleManager;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.kawa.functions.Convert;
import gnu.kawa.lispexpr.LangPrimType;
import gnu.kawa.reflect.InstanceOf;
import gnu.kawa.reflect.OccurrenceType;
import gnu.kawa.reflect.SingletonType;
import gnu.kawa.xml.DescendantOrSelfAxis;
import gnu.kawa.xml.ElementType;
import gnu.kawa.xml.MakeAttribute;
import gnu.kawa.xml.MakeElement;
import gnu.kawa.xml.MakeWithBaseUri;
import gnu.kawa.xml.NodeType;
import gnu.kawa.xml.ParentAxis;
import gnu.kawa.xml.ProcessingInstructionType;
import gnu.kawa.xml.XDataType;
import gnu.mapping.CharArrayInPort;
import gnu.mapping.Environment;
import gnu.mapping.InPort;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.mapping.TtyInPort;
import gnu.mapping.WrappedException;
import gnu.math.IntNum;
import gnu.text.FilePath;
import gnu.text.Lexer;
import gnu.text.LineBufferedReader;
import gnu.text.Path;
import gnu.text.SourceError;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import gnu.text.URIPath;
import gnu.xml.NamespaceBinding;
import gnu.xml.TextUtils;
import gnu.xml.XName;
import gnu.xquery.util.CastableAs;
import gnu.xquery.util.NamedCollator;
import gnu.xquery.util.QNameUtils;
import gnu.xquery.util.RelativeStep;
import gnu.xquery.util.ValuesFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Stack;
import java.util.Vector;
import kawa.standard.require;

public class XQParser extends Lexer {
    static final int ARROW_TOKEN = 82;
    static final int ATTRIBUTE_TOKEN = 252;
    static final int AXIS_ANCESTOR = 0;
    static final int AXIS_ANCESTOR_OR_SELF = 1;
    static final int AXIS_ATTRIBUTE = 2;
    static final int AXIS_CHILD = 3;
    static final int AXIS_DESCENDANT = 4;
    static final int AXIS_DESCENDANT_OR_SELF = 5;
    static final int AXIS_FOLLOWING = 6;
    static final int AXIS_FOLLOWING_SIBLING = 7;
    static final int AXIS_NAMESPACE = 8;
    static final int AXIS_PARENT = 9;
    static final int AXIS_PRECEDING = 10;
    static final int AXIS_PRECEDING_SIBLING = 11;
    static final int AXIS_SELF = 12;
    static final int CASE_DOLLAR_TOKEN = 247;
    static final int COLON_COLON_TOKEN = 88;
    static final int COLON_EQUAL_TOKEN = 76;
    static final int COMMENT_TOKEN = 254;
    static final int COUNT_OP_AXIS = 13;
    static final char DECIMAL_TOKEN = '1';
    static final int DECLARE_BASE_URI_TOKEN = 66;
    static final int DECLARE_BOUNDARY_SPACE_TOKEN = 83;
    static final int DECLARE_CONSTRUCTION_TOKEN = 75;
    static final int DECLARE_COPY_NAMESPACES_TOKEN = 76;
    static final int DECLARE_FUNCTION_TOKEN = 80;
    static final int DECLARE_NAMESPACE_TOKEN = 78;
    static final int DECLARE_OPTION_TOKEN = 111;
    static final int DECLARE_ORDERING_TOKEN = 85;
    static final int DECLARE_VARIABLE_TOKEN = 86;
    static final int DEFAULT_COLLATION_TOKEN = 71;
    static final int DEFAULT_ELEMENT_TOKEN = 69;
    static final int DEFAULT_FUNCTION_TOKEN = 79;
    static final int DEFAULT_ORDER_TOKEN = 72;
    static final int DEFINE_QNAME_TOKEN = 87;
    static final int DOCUMENT_TOKEN = 256;
    static final int DOTDOT_TOKEN = 51;
    static final Symbol DOT_VARNAME = Symbol.makeUninterned("$dot$");
    static final char DOUBLE_TOKEN = '2';
    static final int ELEMENT_TOKEN = 251;
    static final int EOF_TOKEN = -1;
    static final int EOL_TOKEN = 10;
    static final int EVERY_DOLLAR_TOKEN = 246;
    static final int FNAME_TOKEN = 70;
    static final int FOR_DOLLAR_TOKEN = 243;
    static final int IF_LPAREN_TOKEN = 241;
    static final int IMPORT_MODULE_TOKEN = 73;
    static final int IMPORT_SCHEMA_TOKEN = 84;
    static final char INTEGER_TOKEN = '0';
    static final Symbol LAST_VARNAME = Symbol.makeUninterned("$last$");
    static final int LET_DOLLAR_TOKEN = 244;
    static final int MODULE_NAMESPACE_TOKEN = 77;
    static final int NCNAME_COLON_TOKEN = 67;
    static final int NCNAME_TOKEN = 65;
    static final int OP_ADD = 413;
    static final int OP_AND = 401;
    static final int OP_ATTRIBUTE = 236;
    static final int OP_AXIS_FIRST = 100;
    static final int OP_BASE = 400;
    static final int OP_CASTABLE_AS = 424;
    static final int OP_CAST_AS = 425;
    static final int OP_COMMENT = 232;
    static final int OP_DIV = 416;
    static final int OP_DOCUMENT = 234;
    static final int OP_ELEMENT = 235;
    static final int OP_EMPTY_SEQUENCE = 238;
    static final int OP_EQ = 426;
    static final int OP_EQU = 402;
    static final int OP_EXCEPT = 421;
    static final int OP_GE = 431;
    static final int OP_GEQ = 407;
    static final int OP_GRT = 405;
    static final int OP_GRTGRT = 410;
    static final int OP_GT = 430;
    static final int OP_IDIV = 417;
    static final int OP_INSTANCEOF = 422;
    static final int OP_INTERSECT = 420;
    static final int OP_IS = 408;
    static final int OP_ISNOT = 409;
    static final int OP_ITEM = 237;
    static final int OP_LE = 429;
    static final int OP_LEQ = 406;
    static final int OP_LSS = 404;
    static final int OP_LSSLSS = 411;
    static final int OP_LT = 428;
    static final int OP_MOD = 418;
    static final int OP_MUL = 415;
    static final int OP_NE = 427;
    static final int OP_NEQ = 403;
    static final int OP_NODE = 230;
    static final int OP_OR = 400;
    static final int OP_PI = 233;
    static final int OP_RANGE_TO = 412;
    static final int OP_SCHEMA_ATTRIBUTE = 239;
    static final int OP_SCHEMA_ELEMENT = 240;
    static final int OP_SUB = 414;
    static final int OP_TEXT = 231;
    static final int OP_TREAT_AS = 423;
    static final int OP_UNION = 419;
    static final int OP_WHERE = 196;
    static final int ORDERED_LBRACE_TOKEN = 249;
    static final int PI_TOKEN = 255;
    static final Symbol POSITION_VARNAME = Symbol.makeUninterned("$position$");
    static final int PRAGMA_START_TOKEN = 197;
    static final int QNAME_TOKEN = 81;
    static final int SLASHSLASH_TOKEN = 68;
    static final int SOME_DOLLAR_TOKEN = 245;
    static final int STRING_TOKEN = 34;
    static final int TEXT_TOKEN = 253;
    static final int TYPESWITCH_LPAREN_TOKEN = 242;
    static final int UNORDERED_LBRACE_TOKEN = 250;
    static final int VALIDATE_LBRACE_TOKEN = 248;
    static final int XQUERY_VERSION_TOKEN = 89;
    public static final String[] axisNames = new String[13];
    static NamespaceBinding builtinNamespaces = new NamespaceBinding("local", XQuery.LOCAL_NAMESPACE, new NamespaceBinding("qexo", XQuery.QEXO_FUNCTION_NAMESPACE, new NamespaceBinding("kawa", XQuery.KAWA_FUNCTION_NAMESPACE, new NamespaceBinding("html", "http://www.w3.org/1999/xhtml", new NamespaceBinding("fn", XQuery.XQUERY_FUNCTION_NAMESPACE, new NamespaceBinding("xsi", XQuery.SCHEMA_INSTANCE_NAMESPACE, new NamespaceBinding("xs", XQuery.SCHEMA_NAMESPACE, new NamespaceBinding("xml", NamespaceBinding.XML_NAMESPACE, NamespaceBinding.predefinedXML))))))));
    public static final CastableAs castableAs = CastableAs.castableAs;
    public static final QuoteExp getExternalFunction = QuoteExp.getInstance(new PrimProcedure("gnu.xquery.lang.XQuery", "getExternal", 2));
    public static final InstanceOf instanceOf = new InstanceOf(XQuery.getInstance(), "instance");
    static final Expression makeCDATA = makeFunctionExp("gnu.kawa.xml.MakeCDATA", "makeCDATA");
    public static QuoteExp makeChildAxisStep = QuoteExp.getInstance(new PrimProcedure("gnu.kawa.xml.ChildAxis", "make", 1));
    public static QuoteExp makeDescendantAxisStep = QuoteExp.getInstance(new PrimProcedure("gnu.kawa.xml.DescendantAxis", "make", 1));
    public static Expression makeText = makeFunctionExp("gnu.kawa.xml.MakeText", "makeText");
    static PrimProcedure proc_OccurrenceType_getInstance = new PrimProcedure(ClassType.make("gnu.kawa.reflect.OccurrenceType").getDeclaredMethod("getInstance", 3));
    public static final Convert treatAs = Convert.as;
    public static boolean warnHidePreviousDeclaration = false;
    public static boolean warnOldVersion = true;
    Path baseURI = null;
    boolean baseURIDeclarationSeen;
    boolean boundarySpaceDeclarationSeen;
    boolean boundarySpacePreserve;
    int commentCount;
    Compilation comp;
    boolean constructionModeDeclarationSeen;
    boolean constructionModeStrip;
    NamespaceBinding constructorNamespaces = NamespaceBinding.predefinedXML;
    boolean copyNamespacesDeclarationSeen;
    int copyNamespacesMode = 3;
    int curColumn;
    int curLine;
    int curToken;
    Object curValue;
    NamedCollator defaultCollator = null;
    String defaultElementNamespace = "";
    char defaultEmptyOrder = 'L';
    boolean emptyOrderDeclarationSeen;
    int enclosedExpressionsSeen;
    String errorIfComment;
    Declaration[] flworDecls;
    int flworDeclsCount;
    int flworDeclsFirst;
    public Namespace[] functionNamespacePath = XQuery.defaultFunctionNamespacePath;
    XQuery interpreter;
    String libraryModuleNamespace;
    boolean orderingModeSeen;
    boolean orderingModeUnordered;
    int parseContext;
    int parseCount;
    NamespaceBinding prologNamespaces;
    private int saveToken;
    private Object saveValue;
    boolean seenDeclaration;
    int seenLast;
    int seenPosition;
    private boolean warnedOldStyleKindTest;

    static {
        axisNames[0] = "ancestor";
        axisNames[1] = "ancestor-or-self";
        axisNames[2] = "attribute";
        axisNames[3] = "child";
        axisNames[4] = "descendant";
        axisNames[5] = "descendant-or-self";
        axisNames[6] = "following";
        axisNames[7] = "following-sibling";
        axisNames[8] = "namespace";
        axisNames[9] = "parent";
        axisNames[10] = "preceding";
        axisNames[11] = "preceding-sibling";
        axisNames[12] = "self";
    }

    public void setStaticBaseUri(String uri) {
        try {
            this.baseURI = fixupStaticBaseUri(URIPath.valueOf(uri));
        } catch (Throwable th) {
            ex = th;
            if (ex instanceof WrappedException) {
                ex = ((WrappedException) ex).getCause();
            }
            error('e', "invalid URI: " + ex.getMessage());
        }
    }

    static Path fixupStaticBaseUri(Path path) {
        Path path2 = path.getAbsolute();
        if (path2 instanceof FilePath) {
            return URIPath.valueOf(path2.toURI());
        }
        return path2;
    }

    public String getStaticBaseUri() {
        Path path = this.baseURI;
        if (path == null) {
            Object value = Environment.getCurrent().get(Symbol.make("", "base-uri"), null, null);
            if (value != null && !(value instanceof Path)) {
                path = URIPath.valueOf(value.toString());
            }
            if (path == null) {
                LineBufferedReader port = getPort();
                if (port != null) {
                    path = port.getPath();
                    if ((path instanceof FilePath) && (!path.exists() || (port instanceof TtyInPort) || (port instanceof CharArrayInPort))) {
                        path = null;
                    }
                }
            }
            if (path == null) {
                path = Path.currentPath();
            }
            path = fixupStaticBaseUri(path);
            this.baseURI = path;
        }
        return path.toString();
    }

    public String resolveAgainstBaseUri(String uri) {
        return Path.uriSchemeSpecified(uri) ? uri : Path.valueOf(getStaticBaseUri()).resolve(uri).toString();
    }

    /* access modifiers changed from: 0000 */
    public final int skipSpace() throws IOException, SyntaxException {
        return skipSpace(true);
    }

    /* access modifiers changed from: 0000 */
    public final int skipSpace(boolean verticalToo) throws IOException, SyntaxException {
        int ch;
        while (true) {
            ch = read();
            if (ch != 40) {
                if (ch != 123) {
                    if (!verticalToo) {
                        if (!(ch == 32 || ch == 9)) {
                            break;
                        }
                    } else if (ch < 0 || !Character.isWhitespace((char) ch)) {
                        break;
                    }
                } else {
                    int ch2 = read();
                    if (ch2 != 45) {
                        unread(ch2);
                        return 123;
                    }
                    int ch3 = read();
                    if (ch3 != 45) {
                        unread(ch3);
                        unread(45);
                        return 123;
                    }
                    skipOldComment();
                }
            } else if (!checkNext(':')) {
                return 40;
            } else {
                skipComment();
            }
        }
        return ch;
    }

    /* access modifiers changed from: 0000 */
    public final void skipToSemicolon() throws IOException {
        int next;
        do {
            next = read();
            if (next < 0) {
                return;
            }
        } while (next != 59);
    }

    /* access modifiers changed from: 0000 */
    public final void skipOldComment() throws IOException, SyntaxException {
        int seenDashes = 0;
        int startLine = getLineNumber() + 1;
        int startColumn = getColumnNumber() - 2;
        warnOldVersion("use (: :) instead of old-style comment {-- --}");
        while (true) {
            int ch = read();
            if (ch == 45) {
                seenDashes++;
            } else if (ch == 125 && seenDashes >= 2) {
                return;
            } else {
                if (ch < 0) {
                    this.curLine = startLine;
                    this.curColumn = startColumn;
                    eofError("non-terminated comment starting here");
                } else {
                    seenDashes = 0;
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void skipComment() throws IOException, SyntaxException {
        this.commentCount++;
        int startLine = getLineNumber() + 1;
        int startColumn = getColumnNumber() - 1;
        if (this.errorIfComment != null) {
            this.curLine = startLine;
            this.curColumn = startColumn;
            error('e', this.errorIfComment);
        }
        int prev = 0;
        int commentNesting = 0;
        char saveReadState = pushNesting(':');
        while (true) {
            int ch = read();
            if (ch == 58) {
                if (prev == 40) {
                    commentNesting++;
                    ch = 0;
                }
            } else if (ch == 41 && prev == 58) {
                if (commentNesting == 0) {
                    popNesting(saveReadState);
                    return;
                }
                commentNesting--;
            } else if (ch < 0) {
                this.curLine = startLine;
                this.curColumn = startColumn;
                eofError("non-terminated comment starting here");
            }
            prev = ch;
        }
    }

    /* access modifiers changed from: 0000 */
    public final int peekNonSpace(String message) throws IOException, SyntaxException {
        int ch = skipSpace();
        if (ch < 0) {
            eofError(message);
        }
        unread(ch);
        return ch;
    }

    public void mark() throws IOException {
        super.mark();
        this.saveToken = this.curToken;
        this.saveValue = this.curValue;
    }

    public void reset() throws IOException {
        this.curToken = this.saveToken;
        this.curValue = this.saveValue;
        super.reset();
    }

    private int setToken(int token, int width) {
        this.curToken = token;
        this.curLine = this.port.getLineNumber() + 1;
        this.curColumn = (this.port.getColumnNumber() + 1) - width;
        return token;
    }

    /* access modifiers changed from: 0000 */
    public void checkSeparator(char ch) {
        if (XName.isNameStart(ch)) {
            error('e', "missing separator", "XPST0003");
        }
    }

    /* access modifiers changed from: 0000 */
    public int getRawToken() throws IOException, SyntaxException {
        int next;
        int next2;
        boolean seenDot = true;
        while (true) {
            int next3 = readUnicodeChar();
            if (next3 < 0) {
                return setToken(-1, 0);
            }
            if (next3 == 10 || next3 == 13) {
                if (this.nesting <= 0) {
                    return setToken(10, 0);
                }
            } else if (next3 == 40) {
                if (checkNext(':')) {
                    skipComment();
                } else if (checkNext('#')) {
                    return setToken(197, 2);
                } else {
                    return setToken(40, 1);
                }
            } else if (next3 == 123) {
                if (!checkNext('-')) {
                    return setToken(123, 1);
                }
                if (read() != 45) {
                    unread();
                    unread();
                    return setToken(123, 1);
                }
                skipOldComment();
            } else if (!(next3 == 32 || next3 == 9)) {
                this.tokenBufferLength = 0;
                this.curLine = this.port.getLineNumber() + 1;
                this.curColumn = this.port.getColumnNumber();
                char ch = (char) next3;
                switch (ch) {
                    case '!':
                        if (checkNext('=')) {
                            ch = 403;
                            break;
                        }
                        break;
                    case '\"':
                    case '\'':
                        char saveReadState = pushNesting((char) next3);
                        while (true) {
                            int next4 = readUnicodeChar();
                            if (next4 < 0) {
                                eofError("unexpected end-of-file in string starting here");
                            }
                            if (next4 == 38) {
                                parseEntityOrCharRef();
                            } else {
                                if (ch == next4) {
                                    if (ch != peek()) {
                                        popNesting(saveReadState);
                                        ch = '\"';
                                        break;
                                    } else {
                                        next4 = read();
                                    }
                                }
                                tokenBufferAppend(next4);
                            }
                        }
                    case '$':
                    case ')':
                    case ',':
                    case ';':
                    case '?':
                    case '@':
                    case '[':
                    case ']':
                    case '}':
                        break;
                    case '*':
                        ch = 415;
                        break;
                    case '+':
                        ch = 413;
                        break;
                    case '-':
                        ch = 414;
                        break;
                    case '/':
                        if (checkNext('/')) {
                            ch = 'D';
                            break;
                        }
                        break;
                    case ':':
                        if (!checkNext('=')) {
                            if (checkNext(':')) {
                                ch = 'X';
                                break;
                            }
                        } else {
                            ch = 'L';
                            break;
                        }
                        break;
                    case '<':
                        if (!checkNext('=')) {
                            if (!checkNext('<')) {
                                ch = 404;
                                break;
                            } else {
                                ch = 411;
                                break;
                            }
                        } else {
                            ch = 406;
                            break;
                        }
                    case '=':
                        if (checkNext('>')) {
                        }
                        ch = 402;
                        break;
                    case '>':
                        if (!checkNext('=')) {
                            if (!checkNext('>')) {
                                ch = 405;
                                break;
                            } else {
                                ch = 410;
                                break;
                            }
                        } else {
                            ch = 407;
                            break;
                        }
                    case '|':
                        ch = 419;
                        break;
                    default:
                        if (!Character.isDigit(ch) && (ch != '.' || !Character.isDigit((char) peek()))) {
                            if (ch != '.') {
                                if (!XName.isNameStart(ch)) {
                                    if (ch >= ' ' && ch < 127) {
                                        syntaxError("invalid character '" + ch + '\'');
                                        break;
                                    } else {
                                        syntaxError("invalid character '\\u" + Integer.toHexString(ch) + '\'');
                                        break;
                                    }
                                } else {
                                    do {
                                        tokenBufferAppend(ch);
                                        next2 = read();
                                        ch = (char) next2;
                                    } while (XName.isNamePart(ch));
                                    if (next2 >= 0) {
                                        if (next2 != 58) {
                                            ch = 'A';
                                        } else {
                                            next2 = read();
                                            if (next2 < 0) {
                                                eofError("unexpected end-of-file after NAME ':'");
                                            }
                                            char ch2 = (char) next2;
                                            if (XName.isNameStart(ch2)) {
                                                tokenBufferAppend(58);
                                                do {
                                                    tokenBufferAppend(ch2);
                                                    next2 = read();
                                                    ch2 = (char) next2;
                                                } while (XName.isNamePart(ch2));
                                                ch = 'Q';
                                            } else if (ch2 == '=') {
                                                unread(ch2);
                                                ch = 'A';
                                            } else {
                                                ch = Access.CLASS_CONTEXT;
                                            }
                                        }
                                        unread(next2);
                                        break;
                                    } else {
                                        ch = 'A';
                                        break;
                                    }
                                }
                            } else if (checkNext('.')) {
                                ch = '3';
                                break;
                            }
                        } else {
                            if (ch != '.') {
                                seenDot = false;
                            }
                            while (true) {
                                tokenBufferAppend(ch);
                                next = read();
                                if (next >= 0) {
                                    ch = (char) next;
                                    if (ch == '.') {
                                        if (!seenDot) {
                                            seenDot = true;
                                        }
                                    } else if (!Character.isDigit(ch)) {
                                    }
                                }
                            }
                            if (next != 101 && next != 69) {
                                ch = seenDot ? DECIMAL_TOKEN : INTEGER_TOKEN;
                                if (next >= 0) {
                                    checkSeparator((char) next);
                                    unread(next);
                                    break;
                                }
                            } else {
                                tokenBufferAppend((char) next);
                                int next5 = read();
                                if (next5 == 43 || next5 == 45) {
                                    tokenBufferAppend(next5);
                                    next5 = read();
                                }
                                int expDigits = 0;
                                while (true) {
                                    if (next5 >= 0) {
                                        char ch3 = (char) next5;
                                        if (!Character.isDigit(ch3)) {
                                            checkSeparator(ch3);
                                            unread();
                                        } else {
                                            tokenBufferAppend(ch3);
                                            next5 = read();
                                            expDigits++;
                                        }
                                    }
                                }
                                if (expDigits == 0) {
                                    error('e', "no digits following exponent", "XPST0003");
                                }
                                ch = DOUBLE_TOKEN;
                                break;
                            }
                        }
                        break;
                }
                this.curToken = ch;
                return ch;
            }
        }
    }

    public void getDelimited(String delimiter) throws IOException, SyntaxException {
        if (!readDelimited(delimiter)) {
            eofError("unexpected end-of-file looking for '" + delimiter + '\'');
        }
    }

    public void appendNamedEntity(String name) {
        String name2 = name.intern();
        char ch = '?';
        if (name2 == "lt") {
            ch = '<';
        } else if (name2 == "gt") {
            ch = '>';
        } else if (name2 == "amp") {
            ch = '&';
        } else if (name2 == "quot") {
            ch = '\"';
        } else if (name2 == "apos") {
            ch = '\'';
        } else {
            error("unknown enity reference: '" + name2 + "'");
        }
        tokenBufferAppend(ch);
    }

    /* access modifiers changed from: 0000 */
    public boolean match(String word1, String word2, boolean force) throws IOException, SyntaxException {
        if (match(word1)) {
            mark();
            getRawToken();
            if (match(word2)) {
                reset();
                getRawToken();
                return true;
            }
            reset();
            if (force) {
                error('e', "'" + word1 + "' must be followed by '" + word2 + "'", "XPST0003");
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public int peekOperator() throws IOException, SyntaxException {
        while (this.curToken == 10) {
            if (this.nesting == 0) {
                return 10;
            }
            getRawToken();
        }
        if (this.curToken == 65) {
            switch (this.tokenBufferLength) {
                case 2:
                    char c1 = this.tokenBuffer[0];
                    char c2 = this.tokenBuffer[1];
                    if (c1 != 'o' || c2 != 'r') {
                        if (c1 != 't' || c2 != 'o') {
                            if (c1 != 'i' || c2 != 's') {
                                if (c1 != 'e' || c2 != 'q') {
                                    if (c1 != 'n' || c2 != 'e') {
                                        if (c1 != 'g') {
                                            if (c1 == 'l') {
                                                if (c2 != 'e') {
                                                    if (c2 == 't') {
                                                        this.curToken = OP_LT;
                                                        break;
                                                    }
                                                } else {
                                                    this.curToken = OP_LE;
                                                    break;
                                                }
                                            }
                                        } else if (c2 != 'e') {
                                            if (c2 == 't') {
                                                this.curToken = OP_GT;
                                                break;
                                            }
                                        } else {
                                            this.curToken = OP_GE;
                                            break;
                                        }
                                    } else {
                                        this.curToken = OP_NE;
                                        break;
                                    }
                                } else {
                                    this.curToken = OP_EQ;
                                    break;
                                }
                            } else {
                                this.curToken = 408;
                                break;
                            }
                        } else {
                            this.curToken = 412;
                            break;
                        }
                    } else {
                        this.curToken = 400;
                        break;
                    }
                    break;
                case 3:
                    char c12 = this.tokenBuffer[0];
                    char c22 = this.tokenBuffer[1];
                    char c3 = this.tokenBuffer[2];
                    if (c12 != 'a') {
                        if (c12 != 'm') {
                            if (c12 == 'd' && c22 == 'i' && c3 == 'v') {
                                this.curToken = 416;
                                break;
                            }
                        } else {
                            if (c22 == 'u' && c3 == 'l') {
                                this.curToken = 415;
                            }
                            if (c22 == 'o' && c3 == 'd') {
                                this.curToken = 418;
                                break;
                            }
                        }
                    } else if (c22 == 'n' && c3 == 'd') {
                        this.curToken = 401;
                        break;
                    }
                case 4:
                    if (!match("idiv")) {
                        if (match("cast", "as", true)) {
                            this.curToken = OP_CAST_AS;
                            break;
                        }
                    } else {
                        this.curToken = 417;
                        break;
                    }
                    break;
                case 5:
                    if (!match("where")) {
                        if (!match("isnot")) {
                            if (!match("union")) {
                                if (match("treat", "as", true)) {
                                    this.curToken = OP_TREAT_AS;
                                    break;
                                }
                            } else {
                                this.curToken = 419;
                                break;
                            }
                        } else {
                            this.curToken = 409;
                            break;
                        }
                    } else {
                        this.curToken = 196;
                        break;
                    }
                    break;
                case 6:
                    if (match("except")) {
                        this.curToken = OP_EXCEPT;
                        break;
                    }
                    break;
                case 8:
                    if (!match("instance", "of", true)) {
                        if (match("castable", "as", true)) {
                            this.curToken = OP_CASTABLE_AS;
                            break;
                        }
                    } else {
                        this.curToken = OP_INSTANCEOF;
                        break;
                    }
                    break;
                case 9:
                    if (match("intersect")) {
                        this.curToken = OP_INTERSECT;
                        break;
                    }
                    break;
                case 10:
                    if (match("instanceof")) {
                        warnOldVersion("use 'instanceof of' (two words) instead of 'instanceof'");
                        this.curToken = OP_INSTANCEOF;
                        break;
                    }
                    break;
            }
        }
        return this.curToken;
    }

    private boolean lookingAt(String word0, String word1) throws IOException, SyntaxException {
        if (!word0.equals(this.curValue)) {
            return false;
        }
        int i = 0;
        int len = word1.length();
        while (true) {
            int ch = read();
            if (i != len) {
                if (ch < 0) {
                    break;
                }
                int i2 = i + 1;
                if (ch != word1.charAt(i)) {
                    i = i2;
                    break;
                }
                i = i2;
            } else if (ch < 0) {
                return true;
            } else {
                if (!XName.isNamePart((char) ch)) {
                    unread();
                    return true;
                }
                i++;
            }
        }
        this.port.skip(-i);
        return false;
    }

    /* access modifiers changed from: 0000 */
    public int getAxis() {
        String name = new String(this.tokenBuffer, 0, this.tokenBufferLength).intern();
        int i = 13;
        do {
            i--;
            if (i < 0) {
                break;
            }
        } while (axisNames[i] != name);
        if (i < 0 || i == 8) {
            error('e', "unknown axis name '" + name + '\'', "XPST0003");
            i = 3;
        }
        return (char) (i + 100);
    }

    /* access modifiers changed from: 0000 */
    public int peekOperand() throws IOException, SyntaxException {
        while (this.curToken == 10) {
            getRawToken();
        }
        if (this.curToken == 65 || this.curToken == 81) {
            int next = skipSpace(this.nesting != 0);
            switch (this.tokenBuffer[0]) {
                case 'a':
                    if (match("attribute")) {
                        if (next == 40) {
                            this.curToken = OP_ATTRIBUTE;
                            return OP_ATTRIBUTE;
                        } else if (next == 123 || XName.isNameStart((char) next)) {
                            unread();
                            this.curToken = 252;
                            return 252;
                        }
                    }
                    break;
                case 'c':
                    if (match("comment")) {
                        if (next == 40) {
                            this.curToken = OP_COMMENT;
                            return OP_COMMENT;
                        } else if (next == 123) {
                            unread();
                            this.curToken = 254;
                            return 254;
                        }
                    }
                    break;
                case 'd':
                    if (next == 123 && match("document")) {
                        unread();
                        this.curToken = 256;
                        return 256;
                    } else if (next == 40 && match("document-node")) {
                        this.curToken = OP_DOCUMENT;
                        return OP_DOCUMENT;
                    }
                    break;
                case 'e':
                    if (match("element")) {
                        if (next == 40) {
                            this.curToken = OP_ELEMENT;
                            return OP_ELEMENT;
                        } else if (next == 123 || XName.isNameStart((char) next)) {
                            unread();
                            this.curToken = 251;
                            return 251;
                        }
                    } else if (next == 40 && match("empty-sequence")) {
                        this.curToken = OP_EMPTY_SEQUENCE;
                        return OP_EMPTY_SEQUENCE;
                    } else if (next == 36 && match("every")) {
                        this.curToken = EVERY_DOLLAR_TOKEN;
                        return EVERY_DOLLAR_TOKEN;
                    }
                    break;
                case 'f':
                    if (next == 36 && match("for")) {
                        this.curToken = FOR_DOLLAR_TOKEN;
                        return FOR_DOLLAR_TOKEN;
                    }
                case 'i':
                    if (next == 40 && match("if")) {
                        this.curToken = 241;
                        return 241;
                    } else if (next == 40 && match("item")) {
                        this.curToken = OP_ITEM;
                        return OP_ITEM;
                    }
                    break;
                case 'l':
                    if (next == 36 && match("let")) {
                        this.curToken = LET_DOLLAR_TOKEN;
                        return LET_DOLLAR_TOKEN;
                    }
                case 'n':
                    if (next == 40 && match("node")) {
                        this.curToken = OP_NODE;
                        return OP_NODE;
                    }
                case 'o':
                    if (next == 123 && match("ordered")) {
                        this.curToken = ORDERED_LBRACE_TOKEN;
                        return ORDERED_LBRACE_TOKEN;
                    }
                case 'p':
                    if (match("processing-instruction")) {
                        if (next == 40) {
                            this.curToken = OP_PI;
                            return OP_PI;
                        } else if (next == 123 || XName.isNameStart((char) next)) {
                            unread();
                            this.curToken = 255;
                            return 255;
                        }
                    }
                    break;
                case 's':
                    if (next == 36 && match("some")) {
                        this.curToken = SOME_DOLLAR_TOKEN;
                        return SOME_DOLLAR_TOKEN;
                    } else if (next == 40 && match("schema-attribute")) {
                        this.curToken = OP_SCHEMA_ATTRIBUTE;
                        return OP_SCHEMA_ATTRIBUTE;
                    } else if (next == 40 && match("schema-element")) {
                        this.curToken = OP_SCHEMA_ELEMENT;
                        return OP_SCHEMA_ELEMENT;
                    }
                    break;
                case 't':
                    if (match(PropertyTypeConstants.PROPERTY_TYPE_TEXT)) {
                        if (next == 40) {
                            this.curToken = OP_TEXT;
                            return OP_TEXT;
                        } else if (next == 123) {
                            unread();
                            this.curToken = 253;
                            return 253;
                        }
                    }
                    if (next == 40 && match("typeswitch")) {
                        this.curToken = 242;
                        return 242;
                    }
                case 'u':
                    if (next == 123 && match("unordered")) {
                        this.curToken = UNORDERED_LBRACE_TOKEN;
                        return UNORDERED_LBRACE_TOKEN;
                    }
                case 'v':
                    if (next == 123 && match("validate")) {
                        this.curToken = VALIDATE_LBRACE_TOKEN;
                        return VALIDATE_LBRACE_TOKEN;
                    }
            }
            if (next == 40 && peek() != 58) {
                this.curToken = 70;
                return 70;
            } else if (next == 58 && peek() == 58) {
                int axis = getAxis();
                this.curToken = axis;
                return axis;
            } else {
                this.curValue = new String(this.tokenBuffer, 0, this.tokenBufferLength);
                switch (next) {
                    case 98:
                        if (lookingAt("declare", "ase-uri")) {
                            this.curToken = 66;
                            return 66;
                        } else if (lookingAt("declare", "oundary-space")) {
                            this.curToken = 83;
                            return 83;
                        }
                        break;
                    case 99:
                        if (lookingAt("declare", "onstruction")) {
                            this.curToken = 75;
                            return 75;
                        } else if (lookingAt("declare", "opy-namespaces")) {
                            this.curToken = 76;
                            return 76;
                        }
                        break;
                    case 100:
                        if (lookingAt("declare", "efault")) {
                            getRawToken();
                            if (match("function")) {
                                this.curToken = 79;
                                return 79;
                            } else if (match("element")) {
                                this.curToken = 69;
                                return 69;
                            } else if (match("collation")) {
                                this.curToken = 71;
                                return 71;
                            } else if (match("order")) {
                                this.curToken = 72;
                                return 72;
                            } else {
                                error("unrecognized/unimplemented 'declare default'");
                                skipToSemicolon();
                                return peekOperand();
                            }
                        }
                        break;
                    case 101:
                        break;
                    case 102:
                        if (lookingAt("declare", "unction")) {
                            this.curToken = 80;
                            return 80;
                        } else if (lookingAt("define", "unction")) {
                            warnOldVersion("replace 'define function' by 'declare function'");
                            this.curToken = 80;
                            return 80;
                        } else if (lookingAt("default", "unction")) {
                            warnOldVersion("replace 'default function' by 'declare default function namespace'");
                            this.curToken = 79;
                            return 79;
                        }
                        break;
                    case 109:
                        if (lookingAt("import", "odule")) {
                            this.curToken = 73;
                            return 73;
                        }
                        break;
                    case 110:
                        if (lookingAt("declare", "amespace")) {
                            this.curToken = 78;
                            return 78;
                        } else if (lookingAt("default", "amespace")) {
                            warnOldVersion("replace 'default namespace' by 'declare default element namespace'");
                            this.curToken = 69;
                            return 69;
                        } else if (lookingAt("module", "amespace")) {
                            this.curToken = 77;
                            return 77;
                        }
                        break;
                    case 111:
                        if (lookingAt("declare", "rdering")) {
                            this.curToken = 85;
                            return 85;
                        } else if (lookingAt("declare", "ption")) {
                            this.curToken = 111;
                            return 111;
                        }
                        break;
                    case 115:
                        if (lookingAt("import", "chema")) {
                            this.curToken = 84;
                            return 84;
                        }
                        break;
                    case 118:
                        if (lookingAt("declare", "ariable")) {
                            this.curToken = 86;
                            return 86;
                        } else if (lookingAt("define", "ariable")) {
                            warnOldVersion("replace 'define variable' by 'declare variable'");
                            this.curToken = 86;
                            return 86;
                        } else if (lookingAt("xquery", "ersion")) {
                            this.curToken = 89;
                            return 89;
                        }
                        break;
                    case 120:
                        if (lookingAt("declare", "mlspace")) {
                            warnOldVersion("replace 'define xmlspace' by 'declare boundary-space'");
                            this.curToken = 83;
                            return 83;
                        }
                        break;
                }
                if (lookingAt("default", "lement")) {
                    warnOldVersion("replace 'default element' by 'declare default element namespace'");
                    this.curToken = 69;
                    return 69;
                }
                if (next >= 0) {
                    unread();
                    if (XName.isNameStart((char) next) && this.curValue.equals("define")) {
                        getRawToken();
                        this.curToken = 87;
                    }
                }
                return this.curToken;
            }
        } else {
            if (this.curToken == 67) {
                int next2 = read();
                if (next2 == 58) {
                    this.curToken = getAxis();
                } else {
                    unread(next2);
                }
            }
            return this.curToken;
        }
    }

    /* access modifiers changed from: 0000 */
    public void checkAllowedNamespaceDeclaration(String prefix, String uri, boolean inConstructor) {
        boolean xmlPrefix = "xml".equals(prefix);
        if (NamespaceBinding.XML_NAMESPACE.equals(uri)) {
            if (!xmlPrefix || !inConstructor) {
                error('e', "namespace uri cannot be the same as the prefined xml namespace", "XQST0070");
            }
        } else if (xmlPrefix || "xmlns".equals(prefix)) {
            error('e', "namespace prefix cannot be 'xml' or 'xmlns'", "XQST0070");
        }
    }

    /* access modifiers changed from: 0000 */
    public void pushNamespace(String prefix, String uri) {
        if (uri.length() == 0) {
            uri = null;
        }
        this.prologNamespaces = new NamespaceBinding(prefix, uri, this.prologNamespaces);
    }

    public XQParser(InPort port, SourceMessages messages, XQuery interp) {
        super(port, messages);
        this.interpreter = interp;
        this.nesting = 1;
        this.prologNamespaces = builtinNamespaces;
    }

    public void setInteractive(boolean v) {
        if (this.interactive != v) {
            if (v) {
                this.nesting--;
            } else {
                this.nesting++;
            }
        }
        this.interactive = v;
    }

    private static final int priority(int opcode) {
        switch (opcode) {
            case 400:
                return 1;
            case 401:
                return 2;
            case 402:
            case 403:
            case 404:
            case 405:
            case 406:
            case 407:
            case 408:
            case 409:
            case 410:
            case 411:
            case OP_EQ /*426*/:
            case OP_NE /*427*/:
            case OP_LT /*428*/:
            case OP_LE /*429*/:
            case OP_GT /*430*/:
            case OP_GE /*431*/:
                return 3;
            case 412:
                return 4;
            case 413:
            case 414:
                return 5;
            case 415:
            case 416:
            case 417:
            case 418:
                return 6;
            case 419:
                return 7;
            case OP_INTERSECT /*420*/:
            case OP_EXCEPT /*421*/:
                return 8;
            case OP_INSTANCEOF /*422*/:
                return 9;
            case OP_TREAT_AS /*423*/:
                return 10;
            case OP_CASTABLE_AS /*424*/:
                return 11;
            case OP_CAST_AS /*425*/:
                return 12;
            default:
                return 0;
        }
    }

    static Expression makeBinary(Expression func, Expression exp1, Expression exp2) {
        return new ApplyExp(func, exp1, exp2);
    }

    static Expression makeExprSequence(Expression exp1, Expression exp2) {
        return makeBinary(makeFunctionExp("gnu.kawa.functions.AppendValues", "appendValues"), exp1, exp2);
    }

    /* access modifiers changed from: 0000 */
    public Expression makeBinary(int op, Expression exp1, Expression exp2) throws IOException, SyntaxException {
        Expression func;
        switch (op) {
            case 402:
                func = makeFunctionExp("gnu.xquery.util.Compare", "=");
                break;
            case 403:
                func = makeFunctionExp("gnu.xquery.util.Compare", "!=");
                break;
            case 404:
                func = makeFunctionExp("gnu.xquery.util.Compare", "<");
                break;
            case 405:
                func = makeFunctionExp("gnu.xquery.util.Compare", ">");
                break;
            case 406:
                func = makeFunctionExp("gnu.xquery.util.Compare", "<=");
                break;
            case 407:
                func = makeFunctionExp("gnu.xquery.util.Compare", ">=");
                break;
            case 408:
                func = makeFunctionExp("gnu.kawa.xml.NodeCompare", "$Eq", "is");
                break;
            case 409:
                func = makeFunctionExp("gnu.kawa.xml.NodeCompare", "$Ne", "isnot");
                break;
            case 410:
                func = makeFunctionExp("gnu.kawa.xml.NodeCompare", "$Gr", ">>");
                break;
            case 411:
                func = makeFunctionExp("gnu.kawa.xml.NodeCompare", "$Ls", "<<");
                break;
            case 412:
                func = makeFunctionExp("gnu.xquery.util.IntegerRange", "integerRange");
                break;
            case 413:
                func = makeFunctionExp("gnu.xquery.util.ArithOp", "add", "+");
                break;
            case 414:
                func = makeFunctionExp("gnu.xquery.util.ArithOp", "sub", "-");
                break;
            case 415:
                func = makeFunctionExp("gnu.xquery.util.ArithOp", "mul", "*");
                break;
            case 416:
                func = makeFunctionExp("gnu.xquery.util.ArithOp", "div", "div");
                break;
            case 417:
                func = makeFunctionExp("gnu.xquery.util.ArithOp", "idiv", "idiv");
                break;
            case 418:
                func = makeFunctionExp("gnu.xquery.util.ArithOp", "mod", "mod");
                break;
            case 419:
                func = makeFunctionExp("gnu.kawa.xml.UnionNodes", "unionNodes");
                break;
            case OP_INTERSECT /*420*/:
                func = makeFunctionExp("gnu.kawa.xml.IntersectNodes", "intersectNodes");
                break;
            case OP_EXCEPT /*421*/:
                func = makeFunctionExp("gnu.kawa.xml.IntersectNodes", "exceptNodes");
                break;
            case OP_EQ /*426*/:
                func = makeFunctionExp("gnu.xquery.util.Compare", "valEq", "eq");
                break;
            case OP_NE /*427*/:
                func = makeFunctionExp("gnu.xquery.util.Compare", "valNe", "ne");
                break;
            case OP_LT /*428*/:
                func = makeFunctionExp("gnu.xquery.util.Compare", "valLt", "lt");
                break;
            case OP_LE /*429*/:
                func = makeFunctionExp("gnu.xquery.util.Compare", "valLe", "le");
                break;
            case OP_GT /*430*/:
                func = makeFunctionExp("gnu.xquery.util.Compare", "valGt", "gt");
                break;
            case OP_GE /*431*/:
                func = makeFunctionExp("gnu.xquery.util.Compare", "valGe", "ge");
                break;
            default:
                return syntaxError("unimplemented binary op: " + op);
        }
        return makeBinary(func, exp1, exp2);
    }

    private void parseSimpleKindType() throws IOException, SyntaxException {
        getRawToken();
        if (this.curToken == 41) {
            getRawToken();
        } else {
            error("expected ')'");
        }
    }

    public Expression parseNamedNodeType(boolean attribute) throws IOException, SyntaxException {
        Expression qname;
        Expression tname = null;
        getRawToken();
        if (this.curToken == 41) {
            qname = QuoteExp.getInstance(ElementType.MATCH_ANY_QNAME);
            getRawToken();
        } else {
            if (this.curToken == 81 || this.curToken == 65) {
                qname = parseNameTest(attribute);
            } else {
                if (this.curToken != 415) {
                    syntaxError("expected QName or *");
                }
                qname = QuoteExp.getInstance(ElementType.MATCH_ANY_QNAME);
            }
            getRawToken();
            if (this.curToken == 44) {
                getRawToken();
                if (this.curToken == 81 || this.curToken == 65) {
                    tname = parseDataType();
                } else {
                    syntaxError("expected QName");
                }
            }
            if (this.curToken == 41) {
                getRawToken();
            } else {
                error("expected ')' after element");
            }
        }
        return makeNamedNodeType(attribute, qname, tname);
    }

    static Expression makeNamedNodeType(boolean attribute, Expression qname, Expression tname) {
        Expression[] expressionArr = new Expression[2];
        ApplyExp elt = new ApplyExp(ClassType.make(attribute ? "gnu.kawa.xml.AttributeType" : "gnu.kawa.xml.ElementType").getDeclaredMethod("make", 1), qname);
        elt.setFlag(4);
        return tname == null ? elt : new BeginExp(tname, elt);
    }

    private void warnOldStyleKindTest() {
        if (!this.warnedOldStyleKindTest) {
            error('w', "old-style KindTest - first one here");
            this.warnedOldStyleKindTest = true;
        }
    }

    public Expression parseOptionalTypeDeclaration() throws IOException, SyntaxException {
        if (!match("as")) {
            return null;
        }
        getRawToken();
        return parseDataType();
    }

    public Expression parseDataType() throws IOException, SyntaxException {
        int min;
        int max;
        Expression etype = parseItemType();
        if (etype == null) {
            if (this.curToken != OP_EMPTY_SEQUENCE) {
                return syntaxError("bad syntax - expected DataType");
            }
            parseSimpleKindType();
            if (this.curToken == 63 || this.curToken == 413 || this.curToken == 415) {
                getRawToken();
                return syntaxError("occurrence-indicator meaningless after empty-sequence()");
            }
            etype = QuoteExp.getInstance(OccurrenceType.emptySequenceType);
            min = 0;
            max = 0;
        } else if (this.curToken == 63) {
            min = 0;
            max = 1;
        } else if (this.curToken == 413) {
            min = 1;
            max = -1;
        } else if (this.curToken == 415) {
            min = 0;
            max = -1;
        } else {
            min = 1;
            max = 1;
        }
        if (this.parseContext == 67 && max != 1) {
            return syntaxError("type to 'cast as' or 'castable as' must be a 'SingleType'");
        }
        if (min == max) {
            return etype;
        }
        getRawToken();
        ApplyExp otype = new ApplyExp((Procedure) proc_OccurrenceType_getInstance, etype, QuoteExp.getInstance(IntNum.make(min)), QuoteExp.getInstance(IntNum.make(max)));
        otype.setFlag(4);
        return otype;
    }

    public Expression parseMaybeKindTest() throws IOException, SyntaxException {
        Type type;
        boolean z = false;
        switch (this.curToken) {
            case OP_NODE /*230*/:
                parseSimpleKindType();
                type = NodeType.anyNodeTest;
                break;
            case OP_TEXT /*231*/:
                parseSimpleKindType();
                type = NodeType.textNodeTest;
                break;
            case OP_COMMENT /*232*/:
                parseSimpleKindType();
                type = NodeType.commentNodeTest;
                break;
            case OP_PI /*233*/:
                getRawToken();
                String piTarget = null;
                if (this.curToken == 65 || this.curToken == 34) {
                    piTarget = new String(this.tokenBuffer, 0, this.tokenBufferLength);
                    getRawToken();
                }
                if (this.curToken == 41) {
                    getRawToken();
                } else {
                    error("expected ')'");
                }
                type = ProcessingInstructionType.getInstance(piTarget);
                break;
            case OP_DOCUMENT /*234*/:
                parseSimpleKindType();
                type = NodeType.documentNodeTest;
                break;
            case OP_ELEMENT /*235*/:
            case OP_ATTRIBUTE /*236*/:
                if (this.curToken == OP_ATTRIBUTE) {
                    z = true;
                }
                return parseNamedNodeType(z);
            default:
                return null;
        }
        return QuoteExp.getInstance(type);
    }

    public Expression parseItemType() throws IOException, SyntaxException {
        Type type;
        peekOperand();
        Expression etype = parseMaybeKindTest();
        if (etype != null) {
            if (this.parseContext != 67) {
                return etype;
            }
            type = XDataType.anyAtomicType;
        } else if (this.curToken == OP_ITEM) {
            parseSimpleKindType();
            type = SingletonType.getInstance();
        } else if (this.curToken != 65 && this.curToken != 81) {
            return null;
        } else {
            ReferenceExp rexp = new ReferenceExp((Object) new String(this.tokenBuffer, 0, this.tokenBufferLength));
            rexp.setFlag(16);
            maybeSetLine((Expression) rexp, this.curLine, this.curColumn);
            getRawToken();
            return rexp;
        }
        return QuoteExp.getInstance(type);
    }

    /* access modifiers changed from: 0000 */
    public Object parseURILiteral() throws IOException, SyntaxException {
        getRawToken();
        if (this.curToken != 34) {
            return declError("expected a URILiteral");
        }
        return TextUtils.replaceWhitespace(new String(this.tokenBuffer, 0, this.tokenBufferLength), true);
    }

    /* access modifiers changed from: 0000 */
    public Expression parseExpr() throws IOException, SyntaxException {
        return parseExprSingle();
    }

    /* access modifiers changed from: 0000 */
    public final Expression parseExprSingle() throws IOException, SyntaxException {
        int i = this.curLine;
        int i2 = this.curColumn;
        peekOperand();
        switch (this.curToken) {
            case 241:
                return parseIfExpr();
            case 242:
                return parseTypeSwitch();
            case FOR_DOLLAR_TOKEN /*243*/:
                return parseFLWRExpression(true);
            case LET_DOLLAR_TOKEN /*244*/:
                return parseFLWRExpression(false);
            case SOME_DOLLAR_TOKEN /*245*/:
                return parseQuantifiedExpr(false);
            case EVERY_DOLLAR_TOKEN /*246*/:
                return parseQuantifiedExpr(true);
            default:
                return parseBinaryExpr(priority(400));
        }
    }

    /* access modifiers changed from: 0000 */
    public Expression parseBinaryExpr(int prio) throws IOException, SyntaxException {
        Expression func;
        Expression exp = parseUnaryExpr();
        while (true) {
            int token = peekOperator();
            if (!(token == 10 || (token == 404 && peek() == 47))) {
                int tokPriority = priority(token);
                if (tokPriority >= prio) {
                    char saveReadState = pushNesting('%');
                    getRawToken();
                    popNesting(saveReadState);
                    if (token >= OP_INSTANCEOF && token <= OP_CAST_AS) {
                        if (token == OP_CAST_AS || token == OP_CASTABLE_AS) {
                            this.parseContext = 67;
                        }
                        Expression type = parseDataType();
                        this.parseContext = 0;
                        Expression[] args = new Expression[2];
                        switch (token) {
                            case OP_INSTANCEOF /*422*/:
                                args[0] = exp;
                                args[1] = type;
                                func = makeFunctionExp("gnu.xquery.lang.XQParser", "instanceOf");
                                break;
                            case OP_TREAT_AS /*423*/:
                                args[0] = type;
                                args[1] = exp;
                                func = makeFunctionExp("gnu.xquery.lang.XQParser", "treatAs");
                                break;
                            case OP_CASTABLE_AS /*424*/:
                                args[0] = exp;
                                args[1] = type;
                                func = new ReferenceExp(XQResolveNames.castableAsDecl);
                                break;
                            default:
                                args[0] = type;
                                args[1] = exp;
                                func = new ReferenceExp(XQResolveNames.castAsDecl);
                                break;
                        }
                        exp = new ApplyExp(func, args);
                    } else if (token == OP_INSTANCEOF) {
                        exp = new ApplyExp(makeFunctionExp("gnu.xquery.lang.XQParser", "instanceOf"), exp, parseDataType());
                    } else {
                        Expression exp2 = parseBinaryExpr(tokPriority + 1);
                        if (token == 401) {
                            exp = new IfExp(booleanValue(exp), booleanValue(exp2), XQuery.falseExp);
                        } else if (token == 400) {
                            exp = new IfExp(booleanValue(exp), XQuery.trueExp, booleanValue(exp2));
                        } else {
                            exp = makeBinary(token, exp, exp2);
                        }
                    }
                }
            }
        }
        return exp;
    }

    /* access modifiers changed from: 0000 */
    public Expression parseUnaryExpr() throws IOException, SyntaxException {
        if (this.curToken != 414 && this.curToken != 413) {
            return parseUnionExpr();
        }
        int op = this.curToken;
        getRawToken();
        return new ApplyExp(makeFunctionExp("gnu.xquery.util.ArithOp", op == 413 ? "plus" : "minus", op == 413 ? "+" : "-"), parseUnaryExpr());
    }

    /* access modifiers changed from: 0000 */
    public Expression parseUnionExpr() throws IOException, SyntaxException {
        Expression exp = parseIntersectExceptExpr();
        while (true) {
            int op = peekOperator();
            if (op != 419) {
                return exp;
            }
            getRawToken();
            exp = makeBinary(op, exp, parseIntersectExceptExpr());
        }
    }

    /* access modifiers changed from: 0000 */
    public Expression parseIntersectExceptExpr() throws IOException, SyntaxException {
        Expression exp = parsePathExpr();
        while (true) {
            int op = peekOperator();
            if (op != OP_INTERSECT && op != OP_EXCEPT) {
                return exp;
            }
            getRawToken();
            exp = makeBinary(op, exp, parsePathExpr());
        }
    }

    /* access modifiers changed from: 0000 */
    public Expression parsePathExpr() throws IOException, SyntaxException {
        Expression step1;
        Expression dot;
        boolean z = true;
        if (this.curToken == 47 || this.curToken == 68) {
            Declaration dotDecl = this.comp.lexical.lookup((Object) DOT_VARNAME, false);
            if (dotDecl == null) {
                dot = syntaxError("context item is undefined", "XPDY0002");
            } else {
                dot = new ReferenceExp(DOT_VARNAME, dotDecl);
            }
            step1 = new ApplyExp(ClassType.make("gnu.xquery.util.NodeUtils").getDeclaredMethod("rootDocument", 1), dot);
            if (this.nesting == 0) {
                z = false;
            }
            int next = skipSpace(z);
            unread(next);
            if (next < 0 || next == 41 || next == 125) {
                getRawToken();
                return step1;
            }
        } else {
            step1 = parseStepExpr();
        }
        return parseRelativePathExpr(step1);
    }

    /* access modifiers changed from: 0000 */
    public Expression parseNameTest(boolean attribute) throws IOException, SyntaxException {
        String str;
        String local = null;
        String prefix = null;
        if (this.curToken == 81) {
            int colon = this.tokenBufferLength;
            do {
                colon--;
            } while (this.tokenBuffer[colon] != ':');
            prefix = new String(this.tokenBuffer, 0, colon);
            int colon2 = colon + 1;
            local = new String(this.tokenBuffer, colon2, this.tokenBufferLength - colon2);
        } else if (this.curToken == 415) {
            int next = read();
            String local2 = "";
            if (next != 58) {
                unread(next);
            } else {
                int next2 = read();
                if (next2 < 0) {
                    eofError("unexpected end-of-file after '*:'");
                }
                if (XName.isNameStart((char) next2)) {
                    unread();
                    getRawToken();
                    if (this.curToken != 65) {
                        syntaxError("invalid name test");
                    } else {
                        local2 = new String(this.tokenBuffer, 0, this.tokenBufferLength).intern();
                    }
                } else if (next2 != 42) {
                    syntaxError("missing local-name after '*:'");
                }
            }
            return QuoteExp.getInstance(new Symbol(null, local2));
        } else if (this.curToken == 65) {
            local = new String(this.tokenBuffer, 0, this.tokenBufferLength);
            if (attribute) {
                return new QuoteExp(Namespace.EmptyNamespace.getSymbol(local.intern()));
            }
            prefix = null;
        } else if (this.curToken == 67) {
            prefix = new String(this.tokenBuffer, 0, this.tokenBufferLength);
            if (read() != 42) {
                syntaxError("invalid characters after 'NCName:'");
            }
            local = "";
        }
        if (prefix != null) {
            prefix = prefix.intern();
        }
        Expression[] args = new Expression[3];
        args[0] = new ApplyExp((Expression) new ReferenceExp(XQResolveNames.resolvePrefixDecl), QuoteExp.getInstance(prefix));
        if (local == null) {
            str = "";
        } else {
            str = local;
        }
        args[1] = new QuoteExp(str);
        args[2] = new QuoteExp(prefix);
        ApplyExp make = new ApplyExp(Compilation.typeSymbol.getDeclaredMethod("make", 3), args);
        make.setFlag(4);
        return make;
    }

    /* access modifiers changed from: 0000 */
    public Expression parseNodeTest(int axis) throws IOException, SyntaxException {
        Expression dot;
        Expression makeAxisStep;
        String axisName;
        int peekOperand = peekOperand();
        Expression[] args = new Expression[1];
        Expression etype = parseMaybeKindTest();
        if (etype != null) {
            args[0] = etype;
        } else if (this.curToken == 65 || this.curToken == 81 || this.curToken == 67 || this.curToken == 415) {
            args[0] = makeNamedNodeType(axis == 2, parseNameTest(axis == 2), null);
        } else if (axis >= 0) {
            return syntaxError("unsupported axis '" + axisNames[axis] + "::'");
        } else {
            return null;
        }
        Declaration dotDecl = this.comp.lexical.lookup((Object) DOT_VARNAME, false);
        if (dotDecl == null) {
            dot = syntaxError("node test when context item is undefined", "XPDY0002");
        } else {
            dot = new ReferenceExp(DOT_VARNAME, dotDecl);
        }
        if (etype == null) {
            getRawToken();
        }
        if (axis == 3 || axis == -1) {
            makeAxisStep = makeChildAxisStep;
        } else if (axis == 4) {
            makeAxisStep = makeDescendantAxisStep;
        } else {
            switch (axis) {
                case 0:
                    axisName = "Ancestor";
                    break;
                case 1:
                    axisName = "AncestorOrSelf";
                    break;
                case 2:
                    axisName = "Attribute";
                    break;
                case 5:
                    axisName = "DescendantOrSelf";
                    break;
                case 6:
                    axisName = "Following";
                    break;
                case 7:
                    axisName = "FollowingSibling";
                    break;
                case 9:
                    axisName = "Parent";
                    break;
                case 10:
                    axisName = "Preceding";
                    break;
                case 11:
                    axisName = "PrecedingSibling";
                    break;
                case 12:
                    axisName = "Self";
                    break;
                default:
                    throw new Error();
            }
            makeAxisStep = QuoteExp.getInstance(new PrimProcedure("gnu.kawa.xml." + axisName + "Axis", "make", 1));
        }
        ApplyExp mkAxis = new ApplyExp(makeAxisStep, args);
        mkAxis.setFlag(4);
        return new ApplyExp((Expression) mkAxis, dot);
    }

    /* access modifiers changed from: 0000 */
    public Expression parseRelativePathExpr(Expression exp) throws IOException, SyntaxException {
        Expression beforeSlashSlash = null;
        while (true) {
            if (this.curToken != 47 && this.curToken != 68) {
                return exp;
            }
            boolean descendants = this.curToken == 68;
            LambdaExp lexp = new LambdaExp(3);
            Declaration dotDecl = lexp.addDeclaration((Object) DOT_VARNAME);
            dotDecl.setFlag(262144);
            dotDecl.setType(NodeType.anyNodeTest);
            dotDecl.noteValue(null);
            lexp.addDeclaration(POSITION_VARNAME, LangPrimType.intType);
            lexp.addDeclaration(LAST_VARNAME, LangPrimType.intType);
            this.comp.push((ScopeExp) lexp);
            if (descendants) {
                this.curToken = 47;
                lexp.body = new ApplyExp((Procedure) DescendantOrSelfAxis.anyNode, new ReferenceExp(DOT_VARNAME, dotDecl));
                beforeSlashSlash = exp;
            } else {
                getRawToken();
                Expression exp2 = parseStepExpr();
                if (beforeSlashSlash != null && (exp2 instanceof ApplyExp)) {
                    Expression func = ((ApplyExp) exp2).getFunction();
                    if (func instanceof ApplyExp) {
                        ApplyExp aexp = (ApplyExp) func;
                        if (aexp.getFunction() == makeChildAxisStep) {
                            aexp.setFunction(makeDescendantAxisStep);
                            exp = beforeSlashSlash;
                        }
                    }
                }
                lexp.body = exp2;
                beforeSlashSlash = null;
            }
            this.comp.pop(lexp);
            exp = new ApplyExp((Procedure) RelativeStep.relativeStep, exp, lexp);
        }
    }

    /* access modifiers changed from: 0000 */
    public Expression parseStepExpr() throws IOException, SyntaxException {
        int axis;
        Expression exp;
        int i;
        Expression unqualifiedStep;
        if (this.curToken == 46 || this.curToken == 51) {
            if (this.curToken == 46) {
                axis = 12;
            } else {
                axis = 9;
            }
            getRawToken();
            Declaration dotDecl = this.comp.lexical.lookup((Object) DOT_VARNAME, false);
            if (dotDecl == null) {
                exp = syntaxError("context item is undefined", "XPDY0002");
            } else {
                exp = new ReferenceExp(DOT_VARNAME, dotDecl);
            }
            if (axis == 9) {
                exp = new ApplyExp((Procedure) ParentAxis.make(NodeType.anyNodeTest), exp);
            }
            if (axis == 12) {
                i = -1;
            } else {
                i = axis;
            }
            return parseStepQualifiers(exp, i);
        }
        int axis2 = peekOperand() - 100;
        if (axis2 >= 0 && axis2 < 13) {
            getRawToken();
            unqualifiedStep = parseNodeTest(axis2);
        } else if (this.curToken == 64) {
            getRawToken();
            axis2 = 2;
            unqualifiedStep = parseNodeTest(2);
        } else if (this.curToken == OP_ATTRIBUTE) {
            axis2 = 2;
            unqualifiedStep = parseNodeTest(2);
        } else {
            unqualifiedStep = parseNodeTest(-1);
            if (unqualifiedStep != null) {
                axis2 = 3;
            } else {
                axis2 = -1;
                unqualifiedStep = parsePrimaryExpr();
            }
        }
        return parseStepQualifiers(unqualifiedStep, axis2);
    }

    /* access modifiers changed from: 0000 */
    public Expression parseStepQualifiers(Expression exp, int axis) throws IOException, SyntaxException {
        Procedure valuesFilter;
        while (this.curToken == 91) {
            int startLine = getLineNumber() + 1;
            int startColumn = getColumnNumber() + 1;
            int i = this.seenPosition;
            int i2 = this.seenLast;
            getRawToken();
            LambdaExp lexp = new LambdaExp(3);
            maybeSetLine((Expression) lexp, startLine, startColumn);
            Declaration dot = lexp.addDeclaration((Object) DOT_VARNAME);
            if (axis >= 0) {
                dot.setType(NodeType.anyNodeTest);
            } else {
                dot.setType(SingletonType.getInstance());
            }
            lexp.addDeclaration(POSITION_VARNAME, Type.intType);
            lexp.addDeclaration(LAST_VARNAME, Type.intType);
            this.comp.push((ScopeExp) lexp);
            dot.noteValue(null);
            Expression cond = parseExprSequence(93, false);
            if (this.curToken == -1) {
                eofError("missing ']' - unexpected end-of-file");
            }
            if (axis < 0) {
                valuesFilter = ValuesFilter.exprFilter;
            } else if (axis == 0 || axis == 1 || axis == 9 || axis == 10 || axis == 11) {
                valuesFilter = ValuesFilter.reverseFilter;
            } else {
                valuesFilter = ValuesFilter.forwardFilter;
            }
            maybeSetLine(cond, startLine, startColumn);
            this.comp.pop(lexp);
            lexp.body = cond;
            getRawToken();
            exp = new ApplyExp(valuesFilter, exp, lexp);
        }
        return exp;
    }

    /* access modifiers changed from: 0000 */
    public Expression parsePrimaryExpr() throws IOException, SyntaxException {
        Expression exp = parseMaybePrimaryExpr();
        if (exp != null) {
            return exp;
        }
        Expression exp2 = syntaxError("missing expression");
        if (this.curToken != -1) {
            getRawToken();
        }
        return exp2;
    }

    /* access modifiers changed from: 0000 */
    public void parseEntityOrCharRef() throws IOException, SyntaxException {
        int base;
        int next = read();
        if (next == 35) {
            int next2 = read();
            if (next2 == 120) {
                base = 16;
                next2 = read();
            } else {
                base = 10;
            }
            int value = 0;
            while (next2 >= 0) {
                int digit = Character.digit((char) next2, base);
                if (digit < 0 || value >= 134217728) {
                    break;
                }
                value = (value * base) + digit;
                next2 = read();
            }
            if (next2 != 59) {
                unread();
                error("invalid character reference");
            } else if ((value <= 0 || value > 55295) && ((value < 57344 || value > 65533) && (value < 65536 || value > 1114111))) {
                error('e', "invalid character value " + value, "XQST0090");
            } else {
                tokenBufferAppend(value);
            }
        } else {
            int saveLength = this.tokenBufferLength;
            while (next >= 0) {
                char ch = (char) next;
                if (!XName.isNamePart(ch)) {
                    break;
                }
                tokenBufferAppend(ch);
                next = read();
            }
            if (next != 59) {
                unread();
                error("invalid entity reference");
                return;
            }
            String ref = new String(this.tokenBuffer, saveLength, this.tokenBufferLength - saveLength);
            this.tokenBufferLength = saveLength;
            appendNamedEntity(ref);
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0170  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x008c A[SYNTHETIC] */
    public void parseContent(char delimiter, Vector result) throws IOException, SyntaxException {
        String text;
        this.tokenBufferLength = 0;
        int prevEnclosed = result.size() - 1;
        boolean skipBoundarySpace = !this.boundarySpacePreserve && delimiter == '<';
        boolean skippable = skipBoundarySpace;
        while (true) {
            int next = read();
            if (next == delimiter) {
                if (delimiter == '<') {
                    int next2 = read();
                    ApplyExp applyExp = null;
                    if (this.tokenBufferLength > 0) {
                        QuoteExp quoteExp = new QuoteExp(new String(this.tokenBuffer, 0, this.tokenBufferLength));
                        applyExp = new ApplyExp(makeText, quoteExp);
                    }
                    this.tokenBufferLength = 0;
                    if (next2 != 47) {
                        Expression content = parseXMLConstructor(next2, true);
                        boolean isCDATA = false;
                        boolean emptyCDATA = false;
                        if (content instanceof ApplyExp) {
                            ApplyExp aexp = (ApplyExp) content;
                            if (aexp.getFunction() == makeCDATA) {
                                isCDATA = true;
                                String str = (String) aexp.getArg(0).valueIfConstant();
                                emptyCDATA = str != null && str.length() == 0;
                            }
                        }
                        if (applyExp != null && (!skippable || isCDATA)) {
                            result.addElement(applyExp);
                        }
                        if (isCDATA) {
                            skippable = false;
                        } else {
                            skippable = skipBoundarySpace;
                        }
                        if (!emptyCDATA) {
                            result.addElement(content);
                        }
                        this.tokenBufferLength = 0;
                    } else if (applyExp != null && !skippable) {
                        result.addElement(applyExp);
                        return;
                    } else {
                        return;
                    }
                } else if (checkNext(delimiter)) {
                    tokenBufferAppend(delimiter);
                }
            }
            if (next == delimiter || next < 0 || next == 123) {
                int postBrace = next == 123 ? read() : -1;
                if (postBrace == 123) {
                    tokenBufferAppend(123);
                    skippable = false;
                } else {
                    if (this.tokenBufferLength <= 0 || skippable) {
                        if (next == 123 && prevEnclosed == result.size()) {
                            text = "";
                        }
                        this.tokenBufferLength = 0;
                        if (next != delimiter) {
                            return;
                        }
                        if (next < 0) {
                            eofError("unexpected end-of-file");
                        } else {
                            unread(postBrace);
                            this.enclosedExpressionsSeen++;
                            result.addElement(parseEnclosedExpr());
                            this.tokenBufferLength = 0;
                            prevEnclosed = result.size();
                            skippable = skipBoundarySpace;
                        }
                    } else {
                        text = new String(this.tokenBuffer, 0, this.tokenBufferLength);
                    }
                    QuoteExp quoteExp2 = new QuoteExp(text);
                    ApplyExp applyExp2 = new ApplyExp(makeText, quoteExp2);
                    result.addElement(applyExp2);
                    this.tokenBufferLength = 0;
                    if (next != delimiter) {
                    }
                }
            } else if (next == 125) {
                int next3 = read();
                if (next3 == 125) {
                    tokenBufferAppend(125);
                    skippable = false;
                } else {
                    error("unexpected '}' in element content");
                    unread(next3);
                }
            } else if (next == 38) {
                parseEntityOrCharRef();
                skippable = false;
            } else {
                if (delimiter != '<' && (next == 9 || next == 10 || next == 13)) {
                    next = 32;
                }
                if (next == 60) {
                    error('e', "'<' must be quoted in a direct attribute value");
                }
                if (skippable) {
                    skippable = Character.isWhitespace((char) next);
                }
                tokenBufferAppend((char) next);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public Expression parseEnclosedExpr() throws IOException, SyntaxException {
        String saveErrorIfComment = this.errorIfComment;
        this.errorIfComment = null;
        char saveReadState = pushNesting('{');
        peekNonSpace("unexpected end-of-file after '{'");
        int startLine = getLineNumber() + 1;
        int startColumn = getColumnNumber() + 1;
        getRawToken();
        Expression exp = parseExpr();
        while (true) {
            if (this.curToken == 125) {
                break;
            } else if (this.curToken == -1 || this.curToken == 41 || this.curToken == 93) {
                exp = syntaxError("missing '}'");
            } else {
                if (this.curToken != 44) {
                    exp = syntaxError("missing '}' or ','");
                } else {
                    getRawToken();
                }
                exp = makeExprSequence(exp, parseExpr());
            }
        }
        exp = syntaxError("missing '}'");
        maybeSetLine(exp, startLine, startColumn);
        popNesting(saveReadState);
        this.errorIfComment = saveErrorIfComment;
        return exp;
    }

    public static Expression booleanValue(Expression exp) {
        return new ApplyExp(makeFunctionExp("gnu.xquery.util.BooleanValue", "booleanValue"), exp);
    }

    /* access modifiers changed from: 0000 */
    public Expression parseXMLConstructor(int next, boolean inElementContent) throws IOException, SyntaxException {
        if (next == 33) {
            int next2 = read();
            if (next2 == 45 && peek() == 45) {
                skip();
                getDelimited("-->");
                boolean bad = false;
                int i = this.tokenBufferLength;
                boolean sawHyphen = true;
                while (true) {
                    i--;
                    if (i >= 0) {
                        boolean curHyphen = this.tokenBuffer[i] == '-';
                        if (sawHyphen && curHyphen) {
                            bad = true;
                            break;
                        }
                        sawHyphen = curHyphen;
                    } else {
                        break;
                    }
                }
                if (bad) {
                    return syntaxError("consecutive or final hyphen in XML comment");
                }
                return new ApplyExp(makeFunctionExp("gnu.kawa.xml.CommentConstructor", "commentConstructor"), new QuoteExp(new String(this.tokenBuffer, 0, this.tokenBufferLength)));
            } else if (next2 == 91 && read() == 67 && read() == 68 && read() == 65 && read() == 84 && read() == 65 && read() == 91) {
                if (!inElementContent) {
                    error('e', "CDATA section must be in element content");
                }
                getDelimited("]]>");
                return new ApplyExp(makeCDATA, new QuoteExp(new String(this.tokenBuffer, 0, this.tokenBufferLength)));
            } else {
                return syntaxError("'<!' must be followed by '--' or '[CDATA['");
            }
        } else if (next == 63) {
            int next3 = peek();
            if (next3 < 0 || !XName.isNameStart((char) next3) || getRawToken() != 65) {
                syntaxError("missing target after '<?'");
            }
            String target = new String(this.tokenBuffer, 0, this.tokenBufferLength);
            int nspaces = 0;
            while (true) {
                int ch = read();
                if (ch < 0) {
                    break;
                } else if (!Character.isWhitespace((char) ch)) {
                    unread();
                    break;
                } else {
                    nspaces++;
                }
            }
            getDelimited("?>");
            if (nspaces == 0 && this.tokenBufferLength > 0) {
                syntaxError("target must be followed by space or '?>'");
            }
            return new ApplyExp(makeFunctionExp("gnu.kawa.xml.MakeProcInst", "makeProcInst"), new QuoteExp(target), new QuoteExp(new String(this.tokenBuffer, 0, this.tokenBufferLength)));
        } else if (next < 0 || !XName.isNameStart((char) next)) {
            return syntaxError("expected QName after '<'");
        } else {
            unread(next);
            getRawToken();
            char saveReadState = pushNesting('<');
            Expression exp = parseElementConstructor();
            if (!inElementContent) {
                exp = wrapWithBaseUri(exp);
            }
            popNesting(saveReadState);
            return exp;
        }
    }

    static ApplyExp castQName(Expression value, boolean element) {
        return new ApplyExp((Expression) new ReferenceExp(element ? XQResolveNames.xsQNameDecl : XQResolveNames.xsQNameIgnoreDefaultDecl), value);
    }

    /* access modifiers changed from: 0000 */
    public Expression parseElementConstructor() throws IOException, SyntaxException {
        int ch;
        String str = new String(this.tokenBuffer, 0, this.tokenBufferLength);
        Vector vec = new Vector();
        QuoteExp quoteExp = new QuoteExp(str);
        vec.addElement(castQName(quoteExp, true));
        this.errorIfComment = "comment not allowed in element start tag";
        NamespaceBinding nsBindings = null;
        while (true) {
            boolean sawSpace = false;
            ch = read();
            while (ch >= 0 && Character.isWhitespace((char) ch)) {
                ch = read();
                sawSpace = true;
            }
            if (ch >= 0 && ch != 62 && ch != 47) {
                if (!sawSpace) {
                    syntaxError("missing space before attribute");
                }
                unread(ch);
                getRawToken();
                int vecSize = vec.size();
                if (this.curToken != 65 && this.curToken != 81) {
                    break;
                }
                String attrName = new String(this.tokenBuffer, 0, this.tokenBufferLength);
                int startLine = getLineNumber() + 1;
                int startColumn = (getColumnNumber() + 1) - this.tokenBufferLength;
                String definingNamespace = null;
                if (this.curToken == 65) {
                    if (attrName.equals("xmlns")) {
                        definingNamespace = "";
                    }
                } else if (attrName.startsWith("xmlns:")) {
                    definingNamespace = attrName.substring(6).intern();
                }
                Expression makeAttr = definingNamespace != null ? null : MakeAttribute.makeAttributeExp;
                QuoteExp quoteExp2 = new QuoteExp(attrName);
                vec.addElement(castQName(quoteExp2, false));
                if (skipSpace() != 61) {
                    this.errorIfComment = null;
                    return syntaxError("missing '=' after attribute");
                }
                int ch2 = skipSpace();
                int enclosedExpressionsStart = this.enclosedExpressionsSeen;
                if (ch2 == 123) {
                    warnOldVersion("enclosed attribute value expression should be quoted");
                    vec.addElement(parseEnclosedExpr());
                } else {
                    parseContent((char) ch2, vec);
                }
                int n = vec.size() - vecSize;
                if (definingNamespace != null) {
                    String ns = "";
                    if (n == 1) {
                        ns = "";
                    } else if (this.enclosedExpressionsSeen > enclosedExpressionsStart) {
                        syntaxError("enclosed expression not allowed in namespace declaration");
                    } else {
                        Object x = vec.elementAt(vecSize + 1);
                        if (x instanceof ApplyExp) {
                            ApplyExp ax = (ApplyExp) x;
                            if (ax.getFunction() == makeText) {
                                x = ax.getArg(0);
                            }
                        }
                        ns = ((QuoteExp) x).getValue().toString().intern();
                    }
                    vec.setSize(vecSize);
                    checkAllowedNamespaceDeclaration(definingNamespace, ns, true);
                    if (definingNamespace == "") {
                        definingNamespace = null;
                    }
                    NamespaceBinding nsb = nsBindings;
                    while (true) {
                        if (nsb == null) {
                            break;
                        } else if (nsb.getPrefix() == definingNamespace) {
                            error('e', definingNamespace == null ? "duplicate default namespace declaration" : "duplicate namespace prefix '" + definingNamespace + '\'', "XQST0071");
                        } else {
                            nsb = nsb.getNext();
                        }
                    }
                    if (ns == "") {
                        ns = null;
                    }
                    NamespaceBinding namespaceBinding = new NamespaceBinding(definingNamespace, ns, nsBindings);
                    nsBindings = namespaceBinding;
                } else {
                    Expression[] args = new Expression[n];
                    int i = n;
                    while (true) {
                        i--;
                        if (i < 0) {
                            break;
                        }
                        args[i] = (Expression) vec.elementAt(vecSize + i);
                    }
                    vec.setSize(vecSize);
                    ApplyExp aexp = new ApplyExp(makeAttr, args);
                    maybeSetLine((Expression) aexp, startLine, startColumn);
                    vec.addElement(aexp);
                }
            } else {
                break;
            }
        }
        this.errorIfComment = null;
        boolean empty = false;
        if (ch == 47) {
            ch = read();
            if (ch == 62) {
                empty = true;
            } else {
                unread(ch);
            }
        }
        if (!empty) {
            if (ch != 62) {
                return syntaxError("missing '>' after start element");
            }
            parseContent('<', vec);
            int ch3 = peek();
            if (ch3 >= 0) {
                if (!XName.isNameStart((char) ch3)) {
                    return syntaxError("invalid tag syntax after '</'");
                }
                getRawToken();
                String str2 = new String(this.tokenBuffer, 0, this.tokenBufferLength);
                if (!str2.equals(str)) {
                    return syntaxError("'<" + str + ">' closed by '</" + str2 + ">'");
                }
                this.errorIfComment = "comment not allowed in element end tag";
                ch3 = skipSpace();
                this.errorIfComment = null;
            }
            if (ch3 != 62) {
                return syntaxError("missing '>' after end element");
            }
        }
        Expression[] args2 = new Expression[vec.size()];
        vec.copyInto(args2);
        MakeElement mkElement = new MakeElement();
        mkElement.copyNamespacesMode = this.copyNamespacesMode;
        mkElement.setNamespaceNodes(nsBindings);
        QuoteExp quoteExp3 = new QuoteExp(mkElement);
        ApplyExp applyExp = new ApplyExp((Expression) quoteExp3, args2);
        return applyExp;
    }

    /* access modifiers changed from: 0000 */
    public Expression wrapWithBaseUri(Expression exp) {
        if (getStaticBaseUri() == null) {
            return exp;
        }
        return new ApplyExp((Procedure) MakeWithBaseUri.makeWithBaseUri, new ApplyExp((Expression) new ReferenceExp(XQResolveNames.staticBaseUriDecl), Expression.noExpressions), exp).setLine(exp);
    }

    /* access modifiers changed from: 0000 */
    public Expression parseParenExpr() throws IOException, SyntaxException {
        getRawToken();
        char saveReadState = pushNesting('(');
        Expression exp = parseExprSequence(41, true);
        popNesting(saveReadState);
        if (this.curToken == -1) {
            eofError("missing ')' - unexpected end-of-file");
        }
        return exp;
    }

    /* access modifiers changed from: 0000 */
    public Expression parseExprSequence(int rightToken, boolean optional) throws IOException, SyntaxException {
        String str;
        if (this.curToken == rightToken || this.curToken == -1) {
            if (!optional) {
                syntaxError("missing expression");
            }
            return QuoteExp.voidExp;
        }
        Expression exp = null;
        while (true) {
            Expression exp1 = parseExprSingle();
            exp = exp == null ? exp1 : makeExprSequence(exp, exp1);
            if (this.curToken == rightToken || this.curToken == -1) {
                return exp;
            }
            if (this.nesting == 0 && this.curToken == 10) {
                return exp;
            }
            if (this.curToken != 44) {
                if (rightToken == 41) {
                    str = "expected ')'";
                } else {
                    str = "confused by syntax error";
                }
                return syntaxError(str);
            }
            getRawToken();
        }
    }

    /* access modifiers changed from: 0000 */
    public Expression parseTypeSwitch() throws IOException, SyntaxException {
        Declaration decl;
        Declaration decl2;
        char c = 'e';
        char save = pushNesting('t');
        Expression selector = parseParenExpr();
        getRawToken();
        Vector vec = new Vector();
        vec.addElement(selector);
        while (match("case")) {
            pushNesting('c');
            getRawToken();
            if (this.curToken == 36) {
                decl2 = parseVariableDeclaration();
                if (decl2 == null) {
                    return syntaxError("missing Variable after '$'");
                }
                getRawToken();
                if (match("as")) {
                    getRawToken();
                } else {
                    error('e', "missing 'as'");
                }
            } else {
                decl2 = new Declaration((Object) "(arg)");
            }
            decl2.setTypeExp(parseDataType());
            popNesting('t');
            LambdaExp lexp = new LambdaExp(1);
            lexp.addDeclaration(decl2);
            if (match("return")) {
                getRawToken();
            } else {
                error("missing 'return' after 'case'");
            }
            this.comp.push((ScopeExp) lexp);
            pushNesting('r');
            lexp.body = parseExpr();
            popNesting('t');
            this.comp.pop(lexp);
            vec.addElement(lexp);
        }
        if (match("default")) {
            LambdaExp lexp2 = new LambdaExp(1);
            getRawToken();
            if (this.curToken == 36) {
                decl = parseVariableDeclaration();
                if (decl == null) {
                    return syntaxError("missing Variable after '$'");
                }
                getRawToken();
            } else {
                decl = new Declaration((Object) "(arg)");
            }
            lexp2.addDeclaration(decl);
            if (match("return")) {
                getRawToken();
            } else {
                error("missing 'return' after 'default'");
            }
            this.comp.push((ScopeExp) lexp2);
            lexp2.body = parseExpr();
            this.comp.pop(lexp2);
            vec.addElement(lexp2);
        } else {
            if (!this.comp.isPedantic()) {
                c = 'w';
            }
            error(c, "no 'default' clause in 'typeswitch'", "XPST0003");
        }
        popNesting(save);
        Expression[] args = new Expression[vec.size()];
        vec.copyInto(args);
        return new ApplyExp(makeFunctionExp("gnu.kawa.reflect.TypeSwitch", "typeSwitch"), args);
    }

    /* JADX WARNING: type inference failed for: r25v0, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r9v6, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r25v1, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r25v2 */
    /* JADX WARNING: type inference failed for: r1v35, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r0v71, types: [gnu.expr.QuoteExp] */
    /* JADX WARNING: type inference failed for: r0v108, types: [java.lang.Double] */
    /* JADX WARNING: type inference failed for: r27v1 */
    /* JADX WARNING: type inference failed for: r0v109, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r0v112, types: [java.math.BigDecimal] */
    /* JADX WARNING: type inference failed for: r25v4 */
    /* JADX WARNING: type inference failed for: r0v192, types: [gnu.expr.QuoteExp] */
    /* JADX WARNING: type inference failed for: r0v193, types: [java.lang.Double] */
    /* JADX WARNING: type inference failed for: r0v194, types: [java.math.BigDecimal] */
    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r25v0, names: [target], types: [gnu.expr.Expression]
  assigns: [gnu.expr.Expression, gnu.expr.QuoteExp]
  uses: [?[OBJECT, ARRAY], gnu.expr.QuoteExp]
  mth insns count: 503
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
    /* JADX WARNING: Removed duplicated region for block: B:159:0x0090 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x007c  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0087  */
    /* JADX WARNING: Unknown variable types count: 9 */
    public Expression parseMaybePrimaryExpr() throws IOException, SyntaxException {
        Expression exp;
        Expression func;
        Expression element;
        ? syntaxError;
        ? r27;
        String msg;
        Expression qname;
        int ch;
        int startLine = this.curLine;
        int startColumn = this.curColumn;
        int token = peekOperand();
        switch (token) {
            case 34:
                exp = new QuoteExp(new String(this.tokenBuffer, 0, this.tokenBufferLength).intern());
                break;
            case 36:
                Object name = parseVariable();
                if (name != null) {
                    exp = new ReferenceExp(name);
                    maybeSetLine(exp, this.curLine, this.curColumn);
                    break;
                } else {
                    return syntaxError("missing Variable");
                }
            case 40:
                exp = parseParenExpr();
                break;
            case 48:
                exp = new QuoteExp(IntNum.valueOf(this.tokenBuffer, 0, this.tokenBufferLength, 10, false));
                break;
            case 49:
            case 50:
                String str = new String(this.tokenBuffer, 0, this.tokenBufferLength);
                if (token == 49) {
                    try {
                        ? bigDecimal = new BigDecimal(str);
                        r27 = bigDecimal;
                    } catch (Throwable th) {
                        exp = syntaxError("invalid decimal literal: '" + str + "'");
                        break;
                    }
                } else {
                    ? d = new Double(str);
                    r27 = d;
                }
                exp = new QuoteExp(r27);
                break;
            case 70:
                String name2 = new String(this.tokenBuffer, 0, this.tokenBufferLength);
                char save = pushNesting('(');
                getRawToken();
                Vector vec = new Vector(10);
                if (this.curToken != 41) {
                    while (true) {
                        vec.addElement(parseExpr());
                        if (this.curToken != 41) {
                            if (this.curToken != 44) {
                                return syntaxError("missing ')' after function call");
                            }
                            getRawToken();
                        }
                    }
                }
                Expression[] args = new Expression[vec.size()];
                vec.copyInto(args);
                ReferenceExp referenceExp = new ReferenceExp(name2, null);
                referenceExp.setProcedureName(true);
                exp = new ApplyExp((Expression) referenceExp, args);
                maybeSetLine(exp, startLine, startColumn);
                popNesting(save);
                break;
            case 123:
                exp = syntaxError("saw unexpected '{' - assume you meant '('");
                parseEnclosedExpr();
                break;
            case 197:
                Stack extArgs = new Stack();
                do {
                    getRawToken();
                    if (this.curToken == 81 || this.curToken == 65) {
                        qname = QuoteExp.getInstance(new String(this.tokenBuffer, 0, this.tokenBufferLength));
                    } else {
                        qname = syntaxError("missing pragma name");
                    }
                    extArgs.push(qname);
                    StringBuffer sbuf = new StringBuffer();
                    int spaces = -1;
                    do {
                        ch = read();
                        spaces++;
                        if (ch >= 0) {
                        }
                        while (true) {
                            if (ch == 35 || peek() != 41) {
                                if (ch < 0) {
                                    eofError("pragma ended by end-of-file");
                                }
                                if (spaces != 0) {
                                    error("missing space between pragma and extension content");
                                }
                                spaces = 1;
                                sbuf.append((char) ch);
                                ch = read();
                            } else {
                                read();
                                extArgs.push(QuoteExp.getInstance(sbuf.toString()));
                                getRawToken();
                            }
                        }
                    } while (Character.isWhitespace((char) ch));
                    while (true) {
                        if (ch == 35) {
                        }
                        if (ch < 0) {
                        }
                        if (spaces != 0) {
                        }
                        spaces = 1;
                        sbuf.append((char) ch);
                        ch = read();
                    }
                } while (this.curToken == 197);
                if (this.curToken != 123) {
                    exp = syntaxError("missing '{' after pragma");
                    break;
                } else {
                    getRawToken();
                    if (this.curToken != 125) {
                        char saveReadState = pushNesting('{');
                        extArgs.push(parseExprSequence(125, false));
                        popNesting(saveReadState);
                        if (this.curToken == -1) {
                            eofError("missing '}' - unexpected end-of-file");
                        }
                    }
                    Expression[] args2 = new Expression[extArgs.size()];
                    extArgs.copyInto(args2);
                    exp = new ApplyExp((Expression) new ReferenceExp(XQResolveNames.handleExtensionDecl), args2);
                    break;
                }
                break;
            case ORDERED_LBRACE_TOKEN /*249*/:
            case UNORDERED_LBRACE_TOKEN /*250*/:
                getRawToken();
                exp = parseExprSequence(125, false);
                break;
            case 251:
            case 252:
            case 253:
            case 254:
            case 255:
            case 256:
                getRawToken();
                Vector vec2 = new Vector();
                if (token == 251 || token == 252) {
                    if (this.curToken == 65 || this.curToken == 81) {
                        element = parseNameTest(token != 251);
                    } else if (this.curToken != 123) {
                        return syntaxError("missing element/attribute name");
                    } else {
                        element = parseEnclosedExpr();
                    }
                    vec2.addElement(castQName(element, token == 251));
                    if (token == 251) {
                        MakeElement mk = new MakeElement();
                        mk.copyNamespacesMode = this.copyNamespacesMode;
                        func = new QuoteExp(mk);
                    } else {
                        func = MakeAttribute.makeAttributeExp;
                    }
                    getRawToken();
                } else if (token == 256) {
                    func = makeFunctionExp("gnu.kawa.xml.DocumentConstructor", "documentConstructor");
                } else if (token == 254) {
                    func = makeFunctionExp("gnu.kawa.xml.CommentConstructor", "commentConstructor");
                } else if (token == 255) {
                    if (this.curToken == 65) {
                        ? quoteExp = new QuoteExp(new String(this.tokenBuffer, 0, this.tokenBufferLength).intern());
                        syntaxError = quoteExp;
                    } else if (this.curToken == 123) {
                        syntaxError = parseEnclosedExpr();
                    } else {
                        syntaxError = syntaxError("expected NCName or '{' after 'processing-instruction'");
                        if (this.curToken != 81) {
                            return syntaxError;
                        }
                    }
                    vec2.addElement(syntaxError);
                    func = makeFunctionExp("gnu.kawa.xml.MakeProcInst", "makeProcInst");
                    getRawToken();
                } else {
                    func = makeFunctionExp("gnu.kawa.xml.MakeText", "makeText");
                }
                char saveReadState2 = pushNesting('{');
                peekNonSpace("unexpected end-of-file after '{'");
                if (this.curToken == 123) {
                    getRawToken();
                    if (token == 253 || token == 254 || token == 255) {
                        vec2.addElement(parseExprSequence(125, token == 255));
                    } else if (this.curToken != 125) {
                        vec2.addElement(parseExpr());
                        while (this.curToken == 44) {
                            getRawToken();
                            vec2.addElement(parseExpr());
                        }
                    }
                    popNesting(saveReadState2);
                    if (this.curToken == 125) {
                        Expression[] args3 = new Expression[vec2.size()];
                        vec2.copyInto(args3);
                        exp = new ApplyExp(func, args3);
                        maybeSetLine(exp, startLine, startColumn);
                        if (token == 256 || token == 251) {
                            exp = wrapWithBaseUri(exp);
                            break;
                        }
                    } else {
                        return syntaxError("missing '}'");
                    }
                } else {
                    return syntaxError("missing '{'");
                }
                break;
            case 404:
                int next = read();
                if (next != 47) {
                    exp = parseXMLConstructor(next, false);
                    maybeSetLine(exp, startLine, startColumn);
                    break;
                } else {
                    getRawToken();
                    if (this.curToken == 65 || this.curToken == 81 || this.curToken == 67) {
                        msg = "saw end tag '</" + new String(this.tokenBuffer, 0, this.tokenBufferLength) + ">' not in an element constructor";
                    } else {
                        msg = "saw end tag '</' not in an element constructor";
                    }
                    this.curLine = startLine;
                    this.curColumn = startColumn;
                    Expression syntaxError2 = syntaxError(msg);
                    while (this.curToken != 405 && this.curToken != -1 && this.curToken != 10) {
                        getRawToken();
                    }
                    return syntaxError2;
                }
                break;
            default:
                return null;
        }
        getRawToken();
        return exp;
    }

    public Expression parseIfExpr() throws IOException, SyntaxException {
        char saveReadState1 = pushNesting('i');
        getRawToken();
        char saveReadState2 = pushNesting('(');
        Expression cond = parseExprSequence(41, false);
        popNesting(saveReadState2);
        if (this.curToken == -1) {
            eofError("missing ')' - unexpected end-of-file");
        }
        getRawToken();
        if (!match("then")) {
            syntaxError("missing 'then'");
        } else {
            getRawToken();
        }
        Expression thenPart = parseExpr();
        if (!match("else")) {
            syntaxError("missing 'else'");
        } else {
            getRawToken();
        }
        popNesting(saveReadState1);
        return new IfExp(booleanValue(cond), thenPart, parseExpr());
    }

    public boolean match(String word) {
        if (this.curToken != 65) {
            return false;
        }
        int len = word.length();
        if (this.tokenBufferLength != len) {
            return false;
        }
        int i = len;
        do {
            i--;
            if (i < 0) {
                return true;
            }
        } while (word.charAt(i) == this.tokenBuffer[i]);
        return false;
    }

    public Object parseVariable() throws IOException, SyntaxException {
        if (this.curToken == 36) {
            getRawToken();
        } else {
            syntaxError("missing '$' before variable name");
        }
        String str = new String(this.tokenBuffer, 0, this.tokenBufferLength);
        if (this.curToken == 81) {
            return str;
        }
        if (this.curToken == 65) {
            return Namespace.EmptyNamespace.getSymbol(str.intern());
        }
        return null;
    }

    public Declaration parseVariableDeclaration() throws IOException, SyntaxException {
        Object name = parseVariable();
        if (name == null) {
            return null;
        }
        Declaration decl = new Declaration(name);
        maybeSetLine(decl, getLineNumber() + 1, (getColumnNumber() + 1) - this.tokenBufferLength);
        return decl;
    }

    public Expression parseFLWRExpression(boolean isFor) throws IOException, SyntaxException {
        int flworDeclsSave = this.flworDeclsFirst;
        this.flworDeclsFirst = this.flworDeclsCount;
        Expression exp = parseFLWRInner(isFor);
        if (match("order")) {
            char saveNesting = pushNesting(isFor ? 'f' : 'l');
            getRawToken();
            if (match("by")) {
                getRawToken();
            } else {
                error("missing 'by' following 'order'");
            }
            Stack specs = new Stack();
            while (true) {
                boolean descending = false;
                char emptyOrder = this.defaultEmptyOrder;
                LambdaExp lexp = new LambdaExp(this.flworDeclsCount - this.flworDeclsFirst);
                for (int i = this.flworDeclsFirst; i < this.flworDeclsCount; i++) {
                    lexp.addDeclaration(this.flworDecls[i].getSymbol());
                }
                this.comp.push((ScopeExp) lexp);
                lexp.body = parseExprSingle();
                this.comp.pop(lexp);
                specs.push(lexp);
                if (match("ascending")) {
                    getRawToken();
                } else if (match("descending")) {
                    getRawToken();
                    descending = true;
                }
                if (match("empty")) {
                    getRawToken();
                    if (match("greatest")) {
                        getRawToken();
                        emptyOrder = 'G';
                    } else if (match("least")) {
                        getRawToken();
                        emptyOrder = 'L';
                    } else {
                        error("'empty' sequence order must be 'greatest' or 'least'");
                    }
                }
                QuoteExp quoteExp = new QuoteExp((descending ? "D" : "A") + emptyOrder);
                specs.push(quoteExp);
                NamedCollator collation = this.defaultCollator;
                if (match("collation")) {
                    Object uri = parseURILiteral();
                    if (uri instanceof String) {
                        try {
                            collation = NamedCollator.make(resolveAgainstBaseUri((String) uri));
                        } catch (Exception e) {
                            error('e', "unknown collation '" + uri + "'", "XQST0076");
                        }
                    }
                    getRawToken();
                }
                QuoteExp quoteExp2 = new QuoteExp(collation);
                specs.push(quoteExp2);
                if (this.curToken != 44) {
                    break;
                }
                getRawToken();
            }
            if (!match("return")) {
                return syntaxError("expected 'return' clause");
            }
            getRawToken();
            LambdaExp lexp2 = new LambdaExp(this.flworDeclsCount - this.flworDeclsFirst);
            for (int i2 = this.flworDeclsFirst; i2 < this.flworDeclsCount; i2++) {
                lexp2.addDeclaration(this.flworDecls[i2].getSymbol());
            }
            popNesting(saveNesting);
            this.comp.push((ScopeExp) lexp2);
            lexp2.body = parseExprSingle();
            this.comp.pop(lexp2);
            int nspecs = specs.size();
            Expression[] args = new Expression[(nspecs + 2)];
            args[0] = exp;
            args[1] = lexp2;
            for (int i3 = 0; i3 < nspecs; i3++) {
                args[i3 + 2] = (Expression) specs.elementAt(i3);
            }
            return new ApplyExp(makeFunctionExp("gnu.xquery.util.OrderedMap", "orderedMap"), args);
        }
        this.flworDeclsCount = this.flworDeclsFirst;
        this.flworDeclsFirst = flworDeclsSave;
        return exp;
    }

    /* JADX WARNING: type inference failed for: r25v7 */
    /* JADX WARNING: type inference failed for: r25v9 */
    /* JADX WARNING: type inference failed for: r25v10 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    public Expression parseFLWRInner(boolean isFor) throws IOException, SyntaxException {
        LetExp letExp;
        Expression cond;
        Expression body;
        Expression body2;
        String str;
        char saveNesting = pushNesting(isFor ? 'f' : 'l');
        this.curToken = 36;
        Declaration decl = parseVariableDeclaration();
        if (decl == null) {
            return syntaxError("missing Variable - saw " + tokenString());
        }
        if (this.flworDecls == null) {
            this.flworDecls = new Declaration[8];
        } else {
            if (this.flworDeclsCount >= this.flworDecls.length) {
                Declaration[] tmp = new Declaration[(this.flworDeclsCount * 2)];
                System.arraycopy(this.flworDecls, 0, tmp, 0, this.flworDeclsCount);
                this.flworDecls = tmp;
            }
        }
        Declaration[] declarationArr = this.flworDecls;
        int i = this.flworDeclsCount;
        this.flworDeclsCount = i + 1;
        declarationArr[i] = decl;
        getRawToken();
        Expression type = parseOptionalTypeDeclaration();
        Expression[] inits = new Expression[1];
        Declaration posDecl = null;
        if (isFor) {
            boolean sawAt = match("at");
            LambdaExp lambdaExp = new LambdaExp(sawAt ? 2 : 1);
            if (sawAt) {
                getRawToken();
                if (this.curToken == 36) {
                    posDecl = parseVariableDeclaration();
                    getRawToken();
                }
                if (posDecl == null) {
                    syntaxError("missing Variable after 'at'");
                }
            }
            ? r25 = lambdaExp;
            if (match("in")) {
                getRawToken();
                letExp = r25;
            } else {
                if (this.curToken == 76) {
                    getRawToken();
                }
                syntaxError("missing 'in' in 'for' clause");
                letExp = r25;
            }
        } else {
            if (this.curToken == 76) {
                getRawToken();
            } else {
                if (match("in")) {
                    getRawToken();
                }
                syntaxError("missing ':=' in 'let' clause");
            }
            letExp = new LetExp(inits);
        }
        inits[0] = parseExprSingle();
        if (type != null && !isFor) {
            inits[0] = Compilation.makeCoercion(inits[0], type);
        }
        popNesting(saveNesting);
        this.comp.push((ScopeExp) letExp);
        letExp.addDeclaration(decl);
        if (type != null) {
            decl.setTypeExp(type);
        }
        if (isFor) {
            decl.noteValue(null);
            decl.setFlag(262144);
        }
        if (posDecl != null) {
            letExp.addDeclaration(posDecl);
            posDecl.setType(LangPrimType.intType);
            posDecl.noteValue(null);
            posDecl.setFlag(262144);
        }
        if (this.curToken == 44) {
            getRawToken();
            if (this.curToken != 36) {
                return syntaxError("missing $NAME after ','");
            }
            body2 = parseFLWRInner(isFor);
        } else if (match("for")) {
            getRawToken();
            if (this.curToken != 36) {
                return syntaxError("missing $NAME after 'for'");
            }
            body2 = parseFLWRInner(true);
        } else if (match("let")) {
            getRawToken();
            if (this.curToken != 36) {
                return syntaxError("missing $NAME after 'let'");
            }
            body2 = parseFLWRInner(false);
        } else {
            char save = pushNesting('w');
            if (this.curToken == 196) {
                getRawToken();
                cond = parseExprSingle();
            } else if (match("where")) {
                cond = parseExprSingle();
            } else {
                cond = null;
            }
            popNesting(save);
            if (match("stable")) {
                getRawToken();
            }
            boolean sawReturn = match("return");
            boolean sawOrder = match("order");
            if (!sawReturn && !sawOrder && !match("let") && !match("for")) {
                return syntaxError("missing 'return' clause");
            }
            if (!sawOrder) {
                peekNonSpace("unexpected eof-of-file after 'return'");
            }
            int bodyLine = getLineNumber() + 1;
            int bodyColumn = getColumnNumber() + 1;
            if (sawReturn) {
                getRawToken();
            }
            if (sawOrder) {
                int ndecls = this.flworDeclsCount - this.flworDeclsFirst;
                Expression[] args = new Expression[ndecls];
                for (int i2 = 0; i2 < ndecls; i2++) {
                    args[i2] = new ReferenceExp(this.flworDecls[this.flworDeclsFirst + i2]);
                }
                body = new ApplyExp((Procedure) new PrimProcedure("gnu.xquery.util.OrderedMap", "makeTuple$V", 1), args);
            } else {
                body = parseExprSingle();
            }
            if (cond != null) {
                body2 = new IfExp(booleanValue(cond), body, QuoteExp.voidExp);
            } else {
                body2 = body;
            }
            maybeSetLine(body2, bodyLine, bodyColumn);
        }
        this.comp.pop(letExp);
        if (isFor) {
            LambdaExp lexp = (LambdaExp) letExp;
            lexp.body = body2;
            Expression[] args2 = {letExp, inits[0]};
            String str2 = "gnu.kawa.functions.ValuesMap";
            if (lexp.min_args == 1) {
                str = "valuesMap";
            } else {
                str = "valuesMapWithPos";
            }
            ApplyExp applyExp = new ApplyExp(makeFunctionExp(str2, str), args2);
            return applyExp;
        }
        letExp.setBody(body2);
        return letExp;
    }

    public Expression parseQuantifiedExpr(boolean isEvery) throws IOException, SyntaxException {
        Expression body;
        String str;
        char saveNesting = pushNesting(isEvery ? 'e' : 's');
        this.curToken = 36;
        Declaration decl = parseVariableDeclaration();
        if (decl == null) {
            return syntaxError("missing Variable token:" + this.curToken);
        }
        getRawToken();
        LambdaExp lexp = new LambdaExp(1);
        lexp.addDeclaration(decl);
        decl.noteValue(null);
        decl.setFlag(262144);
        decl.setTypeExp(parseOptionalTypeDeclaration());
        if (match("in")) {
            getRawToken();
        } else {
            if (this.curToken == 76) {
                getRawToken();
            }
            syntaxError("missing 'in' in QuantifiedExpr");
        }
        Expression[] inits = {parseExprSingle()};
        popNesting(saveNesting);
        this.comp.push((ScopeExp) lexp);
        if (this.curToken == 44) {
            getRawToken();
            if (this.curToken != 36) {
                return syntaxError("missing $NAME after ','");
            }
            body = parseQuantifiedExpr(isEvery);
        } else {
            boolean sawSatisfies = match("satisfies");
            if (!sawSatisfies && !match("every") && !match("some")) {
                return syntaxError("missing 'satisfies' clause");
            }
            peekNonSpace("unexpected eof-of-file after 'satisfies'");
            int bodyLine = getLineNumber() + 1;
            int bodyColumn = getColumnNumber() + 1;
            if (sawSatisfies) {
                getRawToken();
            }
            body = parseExprSingle();
            maybeSetLine(body, bodyLine, bodyColumn);
        }
        this.comp.pop(lexp);
        lexp.body = body;
        Expression[] args = {lexp, inits[0]};
        String str2 = "gnu.xquery.util.ValuesEvery";
        if (isEvery) {
            str = "every";
        } else {
            str = "some";
        }
        return new ApplyExp(makeFunctionExp(str2, str), args);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:74:?, code lost:
        return r1;
     */
    public Expression parseFunctionDefinition(int declLine, int declColumn) throws IOException, SyntaxException {
        if (this.curToken != 81 && this.curToken != 65) {
            return syntaxError("missing function name");
        }
        String name = new String(this.tokenBuffer, 0, this.tokenBufferLength);
        getMessages().setLine(this.port.getName(), this.curLine, this.curColumn);
        Symbol sym = namespaceResolve(name, true);
        String uri = sym.getNamespaceURI();
        if (uri == NamespaceBinding.XML_NAMESPACE || uri == XQuery.SCHEMA_NAMESPACE || uri == XQuery.SCHEMA_INSTANCE_NAMESPACE || uri == XQuery.XQUERY_FUNCTION_NAMESPACE) {
            error('e', "cannot declare function in standard namespace '" + uri + '\'', "XQST0045");
        } else if (uri == "") {
            error(this.comp.isPedantic() ? 'e' : 'w', "cannot declare function in empty namespace", "XQST0060");
        } else if (!(this.libraryModuleNamespace == null || uri == this.libraryModuleNamespace || (XQuery.LOCAL_NAMESPACE.equals(uri) && !this.comp.isPedantic()))) {
            error('e', "function not in namespace of library module", "XQST0048");
        }
        getRawToken();
        if (this.curToken != 40) {
            return syntaxError("missing parameter list:" + this.curToken);
        }
        getRawToken();
        LambdaExp lexp = new LambdaExp();
        maybeSetLine((Expression) lexp, declLine, declColumn);
        lexp.setName(name);
        Declaration decl = this.comp.currentScope().addDeclaration((Object) sym);
        if (this.comp.isStatic()) {
            decl.setFlag(2048);
        }
        lexp.setFlag(2048);
        decl.setCanRead(true);
        decl.setProcedureDecl(true);
        maybeSetLine(decl, declLine, declColumn);
        this.comp.push((ScopeExp) lexp);
        if (this.curToken != 41) {
            loop0:
            while (true) {
                Declaration param = parseVariableDeclaration();
                if (param == null) {
                    error("missing parameter name");
                } else {
                    lexp.addDeclaration(param);
                    getRawToken();
                    lexp.min_args++;
                    lexp.max_args++;
                    param.setTypeExp(parseOptionalTypeDeclaration());
                }
                if (this.curToken == 41) {
                    break;
                } else if (this.curToken != 44) {
                    Expression err = syntaxError("missing ',' in parameter list");
                    do {
                        getRawToken();
                        if (this.curToken >= 0 && this.curToken != 59 && this.curToken != 59) {
                            if (this.curToken == 41) {
                                break loop0;
                            }
                        }
                    } while (this.curToken != 44);
                } else {
                    getRawToken();
                }
            }
        }
        getRawToken();
        Expression retType = parseOptionalTypeDeclaration();
        lexp.body = parseEnclosedExpr();
        this.comp.pop(lexp);
        if (retType != null) {
            lexp.setCoercedReturnValue(retType, this.interpreter);
        }
        SetExp sexp = new SetExp(decl, (Expression) lexp);
        sexp.setDefining(true);
        decl.noteValue(lexp);
        return sexp;
    }

    public Object readObject() throws IOException, SyntaxException {
        return parse(null);
    }

    /* access modifiers changed from: protected */
    public Symbol namespaceResolve(String name, boolean function) {
        int colon = name.indexOf(58);
        String prefix = colon >= 0 ? name.substring(0, colon).intern() : function ? XQuery.DEFAULT_FUNCTION_PREFIX : XQuery.DEFAULT_ELEMENT_PREFIX;
        String uri = QNameUtils.lookupPrefix(prefix, this.constructorNamespaces, this.prologNamespaces);
        if (uri == null) {
            if (colon < 0) {
                uri = "";
            } else if (!this.comp.isPedantic()) {
                try {
                    Class cls = Class.forName(prefix);
                    uri = "class:" + prefix;
                } catch (Exception e) {
                    uri = null;
                }
            }
            if (uri == null) {
                getMessages().error('e', "unknown namespace prefix '" + prefix + "'", "XPST0081");
                uri = "(unknown namespace)";
            }
        }
        return Symbol.make(uri, colon < 0 ? name : name.substring(colon + 1), prefix);
    }

    /* access modifiers changed from: 0000 */
    public void parseSeparator() throws IOException, SyntaxException {
        int startLine = this.port.getLineNumber() + 1;
        int startColumn = this.port.getColumnNumber() + 1;
        int next = skipSpace(this.nesting != 0);
        if (next != 59) {
            if (warnOldVersion && next != 10) {
                this.curLine = startLine;
                this.curColumn = startColumn;
                error('w', "missing ';' after declaration");
            }
            if (next >= 0) {
                unread(next);
            }
        }
    }

    /* JADX WARNING: type inference failed for: r30v1, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r25v0 */
    /* JADX WARNING: type inference failed for: r25v1 */
    /* JADX WARNING: type inference failed for: r30v2 */
    /* JADX WARNING: type inference failed for: r25v2, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r0v252, types: [gnu.expr.ApplyExp] */
    /* JADX WARNING: type inference failed for: r1v21, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r30v4 */
    /* JADX WARNING: type inference failed for: r30v5 */
    /* JADX WARNING: type inference failed for: r1v22, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r1v23, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r0v260, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r30v6, types: [gnu.expr.ApplyExp] */
    /* JADX WARNING: type inference failed for: r30v7, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r25v3 */
    /* JADX WARNING: type inference failed for: r0v303, types: [gnu.expr.ApplyExp] */
    /* JADX WARNING: type inference failed for: r30v8 */
    /* JADX WARNING: type inference failed for: r30v9 */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0051, code lost:
        if (r18 != 47) goto L_0x0053;
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r30v1, names: [init], types: [gnu.expr.Expression]
  assigns: [gnu.expr.Expression, ?[OBJECT, ARRAY], gnu.expr.ApplyExp]
  uses: [?[int, boolean, OBJECT, ARRAY, byte, short, char], ?[OBJECT, ARRAY], gnu.expr.ApplyExp]
  mth insns count: 1130
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
    /* JADX WARNING: Removed duplicated region for block: B:373:0x0b46  */
    /* JADX WARNING: Unknown variable types count: 9 */
    public Expression parse(Compilation comp2) throws IOException, SyntaxException {
        int next;
        ? parseExpr;
        Expression expression;
        this.comp = comp2;
        int ch = skipSpace();
        if (ch < 0) {
            return null;
        }
        this.parseCount++;
        unread(ch);
        int startLine = getLineNumber() + 1;
        int startColumn = getColumnNumber() + 1;
        if (ch == 35 && startLine == 1 && startColumn == 1) {
            read();
            int ch2 = read();
            if (ch2 == 33) {
                ch2 = read();
            }
            error("'#' is only allowed in initial '#!/PROGRAM'");
            while (ch2 != 13 && ch2 != 10 && ch2 >= 0) {
                ch2 = read();
            }
        }
        if (getRawToken() == -1) {
            return null;
        }
        peekOperand();
        if (this.curToken == 65 && "namespace".equals((String) this.curValue)) {
            if (warnOldVersion) {
                error('w', "use 'declare namespace' instead of 'namespace'");
            }
            this.curToken = 78;
        }
        switch (this.curToken) {
            case 66:
                if (this.baseURIDeclarationSeen && !this.interactive) {
                    syntaxError("duplicate 'declare base-uri' seen", "XQST0032");
                }
                this.baseURIDeclarationSeen = true;
                Object val = parseURILiteral();
                if (val instanceof Expression) {
                    return (Expression) val;
                }
                parseSeparator();
                setStaticBaseUri((String) val);
                return QuoteExp.voidExp;
            case 69:
            case 79:
                boolean forFunctions = this.curToken == 79;
                String prefix = forFunctions ? XQuery.DEFAULT_FUNCTION_PREFIX : XQuery.DEFAULT_ELEMENT_PREFIX;
                if (this.prologNamespaces.resolve(prefix, builtinNamespaces) != null) {
                    error('e', "duplicate default namespace declaration", "XQST0066");
                } else if (this.seenDeclaration && !this.interactive) {
                    error('e', "default namespace declared after function/variable/option");
                }
                getRawToken();
                if (match("namespace")) {
                    getRawToken();
                } else {
                    String msg = "expected 'namespace' keyword";
                    if (this.curToken != 34 && this.curToken != 402) {
                        return declError(msg);
                    }
                    warnOldVersion(msg);
                }
                if (this.curToken == 402 || this.curToken == 76) {
                    warnOldVersion("extra '=' in default namespace declaration");
                    getRawToken();
                }
                if (this.curToken != 34) {
                    return declError("missing namespace uri");
                }
                String uri = new String(this.tokenBuffer, 0, this.tokenBufferLength).intern();
                if (forFunctions) {
                    this.functionNamespacePath = new Namespace[1];
                    this.functionNamespacePath[0] = Namespace.valueOf(uri);
                } else {
                    this.defaultElementNamespace = uri;
                }
                pushNamespace(prefix, uri);
                checkAllowedNamespaceDeclaration(prefix, uri, false);
                parseSeparator();
                return QuoteExp.voidExp;
            case 71:
                if (this.defaultCollator != null && !this.interactive) {
                    error('e', "duplicate default collation declaration", "XQST0038");
                }
                Object val2 = parseURILiteral();
                if (val2 instanceof Expression) {
                    return (Expression) val2;
                }
                String collation = (String) val2;
                try {
                    collation = resolveAgainstBaseUri(collation);
                    this.defaultCollator = NamedCollator.make(collation);
                } catch (Exception e) {
                    this.defaultCollator = NamedCollator.codepointCollation;
                    error('e', "unknown collation '" + collation + "'", "XQST0038");
                }
                parseSeparator();
                return QuoteExp.voidExp;
            case 72:
                getRawToken();
                boolean sawEmpty = match("empty");
                if (this.emptyOrderDeclarationSeen && !this.interactive) {
                    syntaxError("duplicate 'declare default empty order' seen", "XQST0069");
                }
                this.emptyOrderDeclarationSeen = true;
                if (sawEmpty) {
                    getRawToken();
                } else {
                    syntaxError("expected 'empty greatest' or 'empty least'");
                }
                if (match("greatest")) {
                    this.defaultEmptyOrder = 'G';
                } else {
                    if (match("least")) {
                        this.defaultEmptyOrder = 'L';
                    } else {
                        return syntaxError("expected 'empty greatest' or 'empty least'");
                    }
                }
                parseSeparator();
                return QuoteExp.voidExp;
            case 73:
                break;
            case 75:
                getRawToken();
                if (this.constructionModeDeclarationSeen && !this.interactive) {
                    syntaxError("duplicate 'declare construction' seen", "XQST0067");
                }
                this.constructionModeDeclarationSeen = true;
                if (match("strip")) {
                    this.constructionModeStrip = true;
                } else {
                    if (match("preserve")) {
                        this.constructionModeStrip = false;
                    } else {
                        return syntaxError("construction declaration must be strip or preserve");
                    }
                }
                parseSeparator();
                return QuoteExp.voidExp;
            case 76:
                getRawToken();
                if (this.copyNamespacesDeclarationSeen && !this.interactive) {
                    syntaxError("duplicate 'declare copy-namespaces' seen", "XQST0055");
                }
                this.copyNamespacesDeclarationSeen = true;
                if (match("preserve")) {
                    this.copyNamespacesMode |= 1;
                } else {
                    if (match("no-preserve")) {
                        this.copyNamespacesMode &= -2;
                    } else {
                        return syntaxError("expected 'preserve' or 'no-preserve' after 'declare copy-namespaces'");
                    }
                }
                getRawToken();
                if (this.curToken != 44) {
                    return syntaxError("missing ',' in copy-namespaces declaration");
                }
                getRawToken();
                if (match("inherit")) {
                    this.copyNamespacesMode |= 2;
                } else {
                    if (match("no-inherit")) {
                        this.copyNamespacesMode &= -3;
                    } else {
                        return syntaxError("expected 'inherit' or 'no-inherit' in copy-namespaces declaration");
                    }
                }
                parseSeparator();
                return QuoteExp.voidExp;
            case 77:
            case 78:
                int command = this.curToken;
                if (command == 77 && this.libraryModuleNamespace != null) {
                    error('e', "duplicate module declaration");
                } else if (this.seenDeclaration && !this.interactive) {
                    error('e', "namespace declared after function/variable/option");
                }
                int next2 = skipSpace(this.nesting != 0);
                if (next2 >= 0) {
                    unread();
                    if (XName.isNameStart((char) next2)) {
                        getRawToken();
                        if (this.curToken != 65) {
                            return syntaxError("missing namespace prefix");
                        }
                        String str = new String(this.tokenBuffer, 0, this.tokenBufferLength);
                        getRawToken();
                        if (this.curToken != 402) {
                            return syntaxError("missing '=' in namespace declaration");
                        }
                        getRawToken();
                        if (this.curToken != 34) {
                            return syntaxError("missing uri in namespace declaration");
                        }
                        String uri2 = new String(this.tokenBuffer, 0, this.tokenBufferLength).intern();
                        String prefix2 = str.intern();
                        NamespaceBinding ns = this.prologNamespaces;
                        while (true) {
                            if (ns != builtinNamespaces) {
                                if (ns.getPrefix() == prefix2) {
                                    error('e', "duplicate declarations for the same namespace prefix '" + prefix2 + "'", "XQST0033");
                                } else {
                                    ns = ns.getNext();
                                }
                            }
                        }
                        pushNamespace(prefix2, uri2);
                        checkAllowedNamespaceDeclaration(prefix2, uri2, false);
                        parseSeparator();
                        if (command == 77) {
                            ModuleExp module = comp2.getModule();
                            String className = Compilation.mangleURI(uri2) + '.' + XQuery.makeClassName(module.getFileName());
                            module.setName(className);
                            comp2.mainClass = new ClassType(className);
                            module.setType(comp2.mainClass);
                            ModuleManager.getInstance().find(comp2).setNamespaceUri(uri2);
                            module.setType(comp2.mainClass);
                            if (uri2.length() == 0) {
                                return syntaxError("zero-length module namespace", "XQST0088");
                            }
                            this.libraryModuleNamespace = uri2;
                        }
                        return QuoteExp.voidExp;
                    }
                }
                break;
            case 80:
                int declLine = getLineNumber() + 1;
                int declColumn = getColumnNumber() + 1;
                getRawToken();
                peekNonSpace("unexpected end-of-file after 'define function'");
                char save = pushNesting('d');
                Expression exp = parseFunctionDefinition(declLine, declColumn);
                popNesting(save);
                parseSeparator();
                maybeSetLine(exp, startLine, startColumn);
                this.seenDeclaration = true;
                return exp;
            case 83:
                getRawToken();
                if (this.curToken == 402) {
                    warnOldVersion("obsolate '=' in boundary-space declaration");
                    getRawToken();
                }
                if (this.boundarySpaceDeclarationSeen && !this.interactive) {
                    syntaxError("duplicate 'declare boundary-space' seen", "XQST0068");
                }
                this.boundarySpaceDeclarationSeen = true;
                if (match("preserve")) {
                    this.boundarySpacePreserve = true;
                } else {
                    if (match("strip")) {
                        this.boundarySpacePreserve = false;
                    } else {
                        if (match("skip")) {
                            warnOldVersion("update: declare boundary-space skip -> strip");
                            this.boundarySpacePreserve = false;
                        } else {
                            return syntaxError("boundary-space declaration must be preserve or strip");
                        }
                    }
                }
                parseSeparator();
                return QuoteExp.voidExp;
            case 84:
                break;
            case 85:
                if (this.orderingModeSeen && !this.interactive) {
                    syntaxError("duplicate 'declare ordering' seen", "XQST0065");
                }
                this.orderingModeSeen = true;
                getRawToken();
                if (match("ordered")) {
                    this.orderingModeUnordered = false;
                } else {
                    if (match("unordered")) {
                        this.orderingModeUnordered = true;
                    } else {
                        return syntaxError("ordering declaration must be ordered or unordered");
                    }
                }
                parseSeparator();
                return QuoteExp.voidExp;
            case 86:
                getRawToken();
                Declaration decl = parseVariableDeclaration();
                if (decl == null) {
                    return syntaxError("missing Variable");
                }
                Object name = decl.getSymbol();
                if (name instanceof String) {
                    getMessages().setLine(this.port.getName(), this.curLine, this.curColumn);
                    decl.setSymbol(namespaceResolve((String) name, false));
                }
                if (this.libraryModuleNamespace != null) {
                    String uri3 = ((Symbol) decl.getSymbol()).getNamespaceURI();
                    if (uri3 != this.libraryModuleNamespace && (!XQuery.LOCAL_NAMESPACE.equals(uri3) || comp2.isPedantic())) {
                        error('e', "variable not in namespace of library module", "XQST0048");
                    }
                }
                comp2.currentScope().addDeclaration(decl);
                getRawToken();
                Expression type = parseOptionalTypeDeclaration();
                decl.setCanRead(true);
                decl.setFlag(JSONzip.int14);
                boolean sawEq = false;
                if (this.curToken == 402 || this.curToken == 76) {
                    if (this.curToken == 402) {
                        error("declare variable contains '=' instead of ':='");
                    }
                    getRawToken();
                    sawEq = true;
                }
                if (this.curToken == 123) {
                    warnOldVersion("obsolete '{' in variable declaration");
                    ? makeCoercion = parseEnclosedExpr();
                    parseSeparator();
                    parseExpr = makeCoercion;
                } else {
                    if (match("external")) {
                        Expression[] args = new Expression[2];
                        args[0] = castQName(new QuoteExp(decl.getSymbol()), false);
                        if (type == null) {
                            expression = QuoteExp.nullExp;
                        } else {
                            expression = type;
                        }
                        args[1] = expression;
                        ? applyExp = new ApplyExp((Expression) getExternalFunction, args);
                        maybeSetLine((Expression) applyExp, this.curLine, this.curColumn);
                        getRawToken();
                        parseExpr = applyExp;
                    } else {
                        parseExpr = parseExpr();
                        ? r25 = 0;
                        if (!sawEq || parseExpr == 0) {
                            r25 = syntaxError("expected ':= init' or 'external'");
                        }
                        if (parseExpr == 0) {
                            parseExpr = r25;
                        }
                    }
                }
                if (type != null) {
                    parseExpr = Compilation.makeCoercion((Expression) parseExpr, type);
                }
                decl.noteValue(parseExpr);
                SetExp makeDefinition = SetExp.makeDefinition(decl, (Expression) parseExpr);
                maybeSetLine((Expression) makeDefinition, startLine, startColumn);
                this.seenDeclaration = true;
                return makeDefinition;
            case 87:
                int declLine2 = getLineNumber() + 1;
                int declColumn2 = getColumnNumber() + 1;
                if (peekNonSpace("unexpected end-of-file after 'define QName'") == 40) {
                    syntaxError("'missing 'function' after 'define'");
                    this.curToken = 65;
                    return parseFunctionDefinition(declLine2, declColumn2);
                }
                return syntaxError("missing keyword after 'define'");
            case 89:
                if (this.parseCount != 1) {
                    error('e', "'xquery version' does not start module");
                } else if (this.commentCount > 0) {
                    error('w', "comments should not precede 'xquery version'");
                }
                getRawToken();
                if (this.curToken == 34) {
                    String str2 = new String(this.tokenBuffer, 0, this.tokenBufferLength);
                    if (!str2.equals("1.0")) {
                        error('e', "unrecognized xquery version " + str2, "XQST0031");
                    }
                    getRawToken();
                    if (match("encoding")) {
                        getRawToken();
                        if (this.curToken != 34) {
                            return syntaxError("invalid encoding specification");
                        }
                        String str3 = new String(this.tokenBuffer, 0, this.tokenBufferLength);
                        int i = this.tokenBufferLength;
                        boolean bad = i == 0;
                        while (true) {
                            i--;
                            if (i >= 0 && !bad) {
                                char ch3 = this.tokenBuffer[i];
                                if ((ch3 < 'A' || ch3 > 'Z') && ((ch3 < 'a' || ch3 > 'z') && (i == 0 || !((ch3 >= '0' && ch3 <= '9') || ch3 == '.' || ch3 == '_' || ch3 == '-')))) {
                                    bad = true;
                                }
                            } else if (bad) {
                                error('e', "invalid encoding name syntax", "XQST0087");
                            }
                        }
                        if (bad) {
                        }
                        getRawToken();
                    }
                    if (this.curToken != 59) {
                        syntaxError("missing ';'");
                    }
                    return QuoteExp.voidExp;
                }
                return syntaxError("missing version string after 'xquery version'");
            case 111:
                getRawToken();
                if (this.curToken != 81) {
                    syntaxError("expected QName after 'declare option'");
                } else {
                    String str4 = new String(this.tokenBuffer, 0, this.tokenBufferLength);
                    getMessages().setLine(this.port.getName(), this.curLine, this.curColumn);
                    Symbol sym = namespaceResolve(str4, false);
                    getRawToken();
                    if (this.curToken != 34) {
                        syntaxError("expected string literal after 'declare option " + str4 + '\'');
                    } else {
                        handleOption(sym, new String(this.tokenBuffer, 0, this.tokenBufferLength));
                    }
                }
                parseSeparator();
                this.seenDeclaration = true;
                return QuoteExp.voidExp;
            default:
                Expression exp2 = parseExprSequence(-1, true);
                if (this.curToken == 10) {
                    unread(10);
                }
                maybeSetLine(exp2, startLine, startColumn);
                if (this.libraryModuleNamespace == null) {
                    return exp2;
                }
                error('e', "query body in a library module", "XPST0003");
                return exp2;
        }
        fatal("'import schema' not implemented", "XQST0009");
        getRawToken();
        String prefix3 = null;
        if (match("namespace")) {
            getRawToken();
            if (this.curToken != 65) {
                return syntaxError("missing namespace prefix");
            }
            prefix3 = new String(this.tokenBuffer, 0, this.tokenBufferLength);
            getRawToken();
            if (this.curToken != 402) {
                return syntaxError("missing '=' in namespace declaration");
            }
            getRawToken();
        }
        if (this.curToken != 34) {
            return syntaxError("missing uri in namespace declaration");
        } else if (this.tokenBufferLength == 0) {
            return syntaxError("zero-length target namespace", "XQST0088");
        } else {
            String uri4 = new String(this.tokenBuffer, 0, this.tokenBufferLength).intern();
            if (prefix3 != null) {
                checkAllowedNamespaceDeclaration(prefix3, uri4, false);
                pushNamespace(prefix3.intern(), uri4);
            }
            getRawToken();
            ModuleManager.getInstance().find(comp2);
            ModuleExp module2 = comp2.getModule();
            Vector forms = new Vector();
            String packageName = Compilation.mangleURI(uri4);
            comp2.setLine(this.port.getName(), startLine, startColumn);
            if (match("at")) {
                do {
                    getRawToken();
                    if (this.curToken != 34) {
                        return syntaxError("missing module location");
                    }
                    String str5 = new String(this.tokenBuffer, 0, this.tokenBufferLength);
                    String className2 = Compilation.mangleURI(uri4) + '.' + XQuery.makeClassName(str5);
                    ModuleInfo info = require.lookupModuleFromSourcePath(str5, module2);
                    if (info == null) {
                        comp2.error('e', "malformed URL: " + str5);
                    }
                    require.importDefinitions(className2, info, null, forms, module2, comp2);
                    next = skipSpace(this.nesting != 0);
                } while (next == 44);
                unread(next);
                parseSeparator();
            } else {
                ModuleManager manager = ModuleManager.getInstance();
                int n = 0;
                try {
                    manager.loadPackageInfo(packageName);
                } catch (ClassNotFoundException e2) {
                } catch (Throwable ex) {
                    error('e', "error loading map for " + uri4 + " - " + ex);
                }
                int i2 = 0;
                while (true) {
                    ModuleInfo info2 = manager.getModule(i2);
                    if (info2 == null) {
                        if (n == 0) {
                            error('e', "no module found for " + uri4);
                        }
                        if (this.curToken != 59) {
                            parseSeparator();
                        }
                    } else {
                        if (uri4.equals(info2.getNamespaceUri())) {
                            n++;
                            require.importDefinitions(info2.getClassName(), info2, null, forms, module2, comp2);
                        }
                        i2++;
                    }
                }
            }
            if (comp2.pendingImports != null && comp2.pendingImports.size() > 0) {
                error('e', "module import forms a cycle", "XQST0073");
            }
            Expression[] inits = new Expression[forms.size()];
            forms.toArray(inits);
            return BeginExp.canonicalize(inits);
        }
    }

    public void handleOption(Symbol name, String value) {
    }

    public static Expression makeFunctionExp(String className, String name) {
        return makeFunctionExp(className, Compilation.mangleNameIfNeeded(name), name);
    }

    public static Expression makeFunctionExp(String className, String fieldName, String name) {
        return new ReferenceExp(name, Declaration.getDeclarationValueFromStatic(className, fieldName, name));
    }

    /* access modifiers changed from: 0000 */
    public String tokenString() {
        switch (this.curToken) {
            case -1:
                return "<EOF>";
            case 34:
                StringBuffer sbuf = new StringBuffer();
                sbuf.append('\"');
                for (int i = 0; i < this.tokenBufferLength; i++) {
                    char ch = this.tokenBuffer[i];
                    if (ch == '\"') {
                        sbuf.append('\"');
                    }
                    sbuf.append(ch);
                }
                sbuf.append('\"');
                return sbuf.toString();
            case 65:
            case 81:
                return new String(this.tokenBuffer, 0, this.tokenBufferLength);
            case 70:
                return new String(this.tokenBuffer, 0, this.tokenBufferLength) + " + '('";
            default:
                if (this.curToken >= 100 && this.curToken - 100 < 13) {
                    return axisNames[this.curToken - 100] + "::-axis(" + this.curToken + ")";
                }
                if (this.curToken < 32 || this.curToken >= 127) {
                    return Integer.toString(this.curToken);
                }
                return Integer.toString(this.curToken) + "='" + ((char) this.curToken) + "'";
        }
    }

    public void error(char severity, String message, String code) {
        SourceMessages messages = getMessages();
        SourceError err = new SourceError(severity, this.port.getName(), this.curLine, this.curColumn, message);
        err.code = code;
        messages.error(err);
    }

    public void error(char severity, String message) {
        error(severity, message, null);
    }

    public Expression declError(String message) throws IOException, SyntaxException {
        if (this.interactive) {
            return syntaxError(message);
        }
        error(message);
        while (this.curToken != 59 && this.curToken != -1) {
            getRawToken();
        }
        return new ErrorExp(message);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0031, code lost:
        unread(r0);
     */
    public Expression syntaxError(String message, String code) throws IOException, SyntaxException {
        error('e', message, code);
        if (!this.interactive) {
            return new ErrorExp(message);
        }
        this.curToken = 0;
        this.curValue = null;
        this.nesting = 0;
        ((InPort) getPort()).readState = 10;
        while (true) {
            int ch = read();
            if (ch >= 0) {
                if (ch != 13) {
                    if (ch == 10) {
                        break;
                    }
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        throw new SyntaxException(getMessages());
    }

    public Expression syntaxError(String message) throws IOException, SyntaxException {
        return syntaxError(message, "XPST0003");
    }

    public void eofError(String msg) throws SyntaxException {
        fatal(msg, "XPST0003");
    }

    public void fatal(String msg, String code) throws SyntaxException {
        SourceMessages messages = getMessages();
        SourceError err = new SourceError('f', this.port.getName(), this.curLine, this.curColumn, msg);
        err.code = code;
        messages.error(err);
        throw new SyntaxException(messages);
    }

    /* access modifiers changed from: 0000 */
    public void warnOldVersion(String message) {
        if (warnOldVersion || this.comp.isPedantic()) {
            error(this.comp.isPedantic() ? 'e' : 'w', message);
        }
    }

    public void maybeSetLine(Expression exp, int line, int column) {
        String file = getName();
        if (file != null && exp.getFileName() == null && !(exp instanceof QuoteExp)) {
            exp.setFile(file);
            exp.setLine(line, column);
        }
    }

    public void maybeSetLine(Declaration decl, int line, int column) {
        String file = getName();
        if (file != null) {
            decl.setFile(file);
            decl.setLine(line, column);
        }
    }
}
