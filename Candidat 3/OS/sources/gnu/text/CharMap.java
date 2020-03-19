package gnu.text;

import gnu.kawa.util.AbstractWeakHashTable;
import gnu.kawa.util.AbstractWeakHashTable.WEntry;

/* compiled from: Char */
class CharMap extends AbstractWeakHashTable<Char, Char> {
    CharMap() {
    }

    public Char get(int key) {
        cleanup();
        for (WEntry<Char, Char> node = ((WEntry[]) this.table)[hashToIndex(key)]; node != null; node = node.next) {
            Char val = (Char) node.getValue();
            if (val != null && val.intValue() == key) {
                return val;
            }
        }
        Char val2 = new Char(key);
        super.put(val2, val2);
        return val2;
    }

    /* access modifiers changed from: protected */
    public Char getKeyFromValue(Char ch) {
        return ch;
    }

    /* access modifiers changed from: protected */
    public boolean matches(Char oldValue, Char newValue) {
        return oldValue.intValue() == newValue.intValue();
    }
}
