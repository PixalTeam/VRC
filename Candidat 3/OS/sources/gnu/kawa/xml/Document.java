package gnu.kawa.xml;

import gnu.lists.Consumer;
import gnu.lists.XConsumer;
import gnu.mapping.ThreadLocation;
import gnu.text.Path;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import gnu.xml.NodeTree;
import gnu.xml.XMLParser;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;

public class Document {
    private static HashMap cache = new HashMap();
    private static ThreadLocation docMapLocation = new ThreadLocation("document-map");
    public static final Document document = new Document();

    private static class DocReference extends SoftReference {
        static ReferenceQueue queue = new ReferenceQueue();
        Path key;

        public DocReference(Path key2, KDocument doc) {
            super(doc, queue);
            this.key = key2;
        }
    }

    public static void parse(Object name, Consumer out) throws Throwable {
        SourceMessages messages = new SourceMessages();
        if (out instanceof XConsumer) {
            ((XConsumer) out).beginEntity(name);
        }
        XMLParser.parse(name, messages, out);
        if (messages.seenErrors()) {
            throw new SyntaxException("document function read invalid XML", messages);
        } else if (out instanceof XConsumer) {
            ((XConsumer) out).endEntity();
        }
    }

    public static KDocument parse(Object uri) throws Throwable {
        NodeTree tree = new NodeTree();
        parse(uri, tree);
        return new KDocument(tree, 10);
    }

    public static void clearLocalCache() {
        docMapLocation.getLocation().set(null);
    }

    public static void clearSoftCache() {
        cache = new HashMap();
    }

    public static KDocument parseCached(Object uri) throws Throwable {
        return parseCached(Path.valueOf(uri));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002c, code lost:
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        r5 = (gnu.kawa.xml.Document.DocReference) cache.get(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0042, code lost:
        if (r5 == null) goto L_0x0051;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0044, code lost:
        r0 = (gnu.kawa.xml.KDocument) r5.get();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004a, code lost:
        if (r0 != null) goto L_0x0064;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004c, code lost:
        cache.remove(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0051, code lost:
        r0 = parse(r9);
        r3.put(r9, r0);
        cache.put(r9, new gnu.kawa.xml.Document.DocReference(r9, r0));
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0064, code lost:
        r3.put(r9, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0067, code lost:
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x000d, code lost:
        r2 = docMapLocation.getLocation();
        r3 = (java.util.Hashtable) r2.get(null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x001a, code lost:
        if (r3 != null) goto L_0x0024;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x001c, code lost:
        r3 = new java.util.Hashtable();
        r2.set(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0024, code lost:
        r0 = (gnu.kawa.xml.KDocument) r3.get(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x002a, code lost:
        if (r0 == null) goto L_0x003a;
     */
    public static synchronized KDocument parseCached(Path uri) throws Throwable {
        KDocument doc;
        synchronized (Document.class) {
            while (true) {
                DocReference oldref = (DocReference) DocReference.queue.poll();
                if (oldref == null) {
                    break;
                }
                cache.remove(oldref.key);
            }
        }
        return doc;
    }
}
