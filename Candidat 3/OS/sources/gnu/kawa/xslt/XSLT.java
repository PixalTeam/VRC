package gnu.kawa.xslt;

import gnu.expr.ApplicationMainSupport;
import gnu.expr.Compilation;
import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.xml.Document;
import gnu.kawa.xml.Focus;
import gnu.kawa.xml.KDocument;
import gnu.lists.Consumer;
import gnu.lists.TreeList;
import gnu.mapping.CallContext;
import gnu.mapping.InPort;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.text.Lexer;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import gnu.xquery.lang.XQParser;
import gnu.xquery.lang.XQResolveNames;
import gnu.xquery.lang.XQuery;
import java.io.IOException;

public class XSLT extends XQuery {
    public static XSLT instance;
    public static Symbol nullMode = Symbol.make(null, "");

    public String getName() {
        return "XSLT";
    }

    public XSLT() {
        instance = this;
        ModuleBody.setMainPrintValues(true);
    }

    public static XSLT getXsltInstance() {
        if (instance == null) {
            new XSLT();
        }
        return instance;
    }

    public Lexer getLexer(InPort inp, SourceMessages messages) {
        return new XslTranslator(inp, messages, this);
    }

    public boolean parse(Compilation comp, int options) throws IOException, SyntaxException {
        Compilation.defaultCallConvention = 2;
        ((XslTranslator) comp.lexer).parse(comp);
        comp.setState(4);
        XQParser xqparser = new XQParser(null, comp.getMessages(), this);
        XQResolveNames resolver = new XQResolveNames(comp);
        resolver.functionNamespacePath = xqparser.functionNamespacePath;
        resolver.parser = xqparser;
        resolver.resolveModule(comp.mainLambda);
        return true;
    }

    public static void registerEnvironment() {
        Language.setDefaults(new XSLT());
    }

    public static void defineCallTemplate(Symbol name, double priority, Procedure template) {
    }

    public static void defineApplyTemplate(String pattern, double priority, Symbol mode, Procedure template) {
        if (mode == null) {
            mode = nullMode;
        }
        TemplateTable.getTemplateTable(mode).enter(pattern, priority, template);
    }

    public static void defineTemplate(Symbol name, String pattern, double priority, Symbol mode, Procedure template) {
        if (name != null) {
            defineCallTemplate(name, priority, template);
        }
        if (pattern != null) {
            defineApplyTemplate(pattern, priority, mode, template);
        }
    }

    public static void process(TreeList doc, Focus pos, CallContext ctx) throws Throwable {
        Consumer out = ctx.consumer;
        while (true) {
            int ipos = pos.ipos;
            switch (doc.getNextKind(ipos)) {
                case 29:
                    int ichild = ipos >>> 1;
                    int next = doc.nextNodeIndex(ichild, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
                    if (ichild == next) {
                        next = doc.nextDataIndex(ichild);
                    }
                    doc.consumeIRange(ichild, next, out);
                    ipos = next << 1;
                    break;
                case 33:
                    Object type = pos.getNextTypeObject();
                    Procedure proc = TemplateTable.nullModeTable.find(pos.getNextTypeName());
                    if (proc != null) {
                        proc.check0(ctx);
                        ctx.runUntilDone();
                    } else {
                        out.startElement(type);
                        int child = doc.firstAttributePos(ipos);
                        if (child == 0) {
                            child = doc.firstChildPos(ipos);
                        }
                        pos.push(doc, child);
                        process(doc, pos, ctx);
                        pos.pop();
                        out.endElement();
                    }
                    ipos = doc.nextDataIndex(ipos >>> 1) << 1;
                    pos.gotoNext();
                    break;
                case 34:
                    ipos = doc.firstChildPos(ipos);
                    break;
                case 35:
                    Object type2 = pos.getNextTypeObject();
                    Procedure proc2 = TemplateTable.nullModeTable.find(GetNamedPart.CAST_METHOD_NAME + pos.getNextTypeName());
                    if (proc2 != null) {
                        proc2.check0(ctx);
                        ctx.runUntilDone();
                        break;
                    }
                case 36:
                case 37:
                    ipos = doc.nextDataIndex(ipos >>> 1) << 1;
                    break;
                default:
                    return;
            }
            pos.ipos = ipos;
        }
    }

    public static void runStylesheet() throws Throwable {
        CallContext ctx = CallContext.getInstance();
        ApplicationMainSupport.processSetProperties();
        String[] args = ApplicationMainSupport.commandLineArgArray;
        for (String arg : args) {
            KDocument doc = Document.parse(arg);
            Focus pos = Focus.getCurrent();
            pos.push(doc.sequence, doc.ipos);
            process((TreeList) doc.sequence, pos, ctx);
        }
    }

    public static void applyTemplates(String select, Symbol mode) throws Throwable {
        if (mode == null) {
            mode = nullMode;
        }
        TemplateTable templateTable = TemplateTable.getTemplateTable(mode);
        CallContext ctx = CallContext.getInstance();
        Focus pos = Focus.getCurrent();
        TreeList doc = (TreeList) pos.sequence;
        pos.push(doc, doc.firstChildPos(pos.ipos));
        process(doc, pos, ctx);
        pos.pop();
    }
}
