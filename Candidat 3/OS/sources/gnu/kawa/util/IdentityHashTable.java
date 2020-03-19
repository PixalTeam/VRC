package gnu.kawa.util;

public class IdentityHashTable<K, V> extends GeneralHashTable<K, V> {
    public IdentityHashTable() {
    }

    public IdentityHashTable(int capacity) {
        super(capacity);
    }

    public int hash(Object key) {
        return System.identityHashCode(key);
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Object, code=K, for r3v0, types: [java.lang.Object, K] */
    public boolean matches(K value1, K value2) {
        return value1 == value2;
    }
}
