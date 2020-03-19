package gnu.mapping;

public class KeyPair implements EnvironmentKey {
    Symbol name;
    Object property;

    public KeyPair(Symbol name2, Object property2) {
        this.name = name2;
        this.property = property2;
    }

    public Symbol getKeySymbol() {
        return this.name;
    }

    public Object getKeyProperty() {
        return this.property;
    }

    public final boolean matches(EnvironmentKey key) {
        return Symbol.equals(key.getKeySymbol(), this.name) && key.getKeyProperty() == this.property;
    }

    public final boolean matches(Symbol symbol, Object property2) {
        return Symbol.equals(symbol, this.name) && property2 == this.property;
    }

    public boolean equals(Object x) {
        if (!(x instanceof KeyPair)) {
            return false;
        }
        KeyPair e2 = (KeyPair) x;
        if (this.property != e2.property) {
            return false;
        }
        if (this.name == null) {
            if (e2.name != null) {
                return false;
            }
        } else if (!this.name.equals(e2.name)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.name.hashCode() ^ System.identityHashCode(this.property);
    }

    public String toString() {
        return "KeyPair[sym:" + this.name + " prop:" + this.property + "]";
    }
}
