package gnu.mapping;

import gnu.lists.LList;
import gnu.lists.Pair;

public class PropertyLocation extends Location {
    Pair pair;

    public final Object get(Object defaultValue) {
        return this.pair.getCar();
    }

    public boolean isBound() {
        return true;
    }

    public final void set(Object newValue) {
        this.pair.setCar(newValue);
    }

    public static Object getPropertyList(Object symbol, Environment env) {
        return env.get(Symbol.PLIST, symbol, LList.Empty);
    }

    public static Object getPropertyList(Object symbol) {
        return Environment.getCurrent().get(Symbol.PLIST, symbol, LList.Empty);
    }

    public static void setPropertyList(Object symbol, Object plist, Environment env) {
        PropertyLocation ploc;
        synchronized (env) {
            Location lloc = env.lookup(Symbol.PLIST, symbol);
            if (symbol instanceof Symbol) {
                Symbol sym = (Symbol) symbol;
                Object p = lloc.get(LList.Empty);
                while (p instanceof Pair) {
                    Pair pair2 = (Pair) p;
                    Object property = pair2.getCar();
                    if (plistGet(plist, property, null) != null) {
                        env.remove(sym, property);
                    }
                    p = ((Pair) pair2.getCdr()).getCdr();
                }
                Object p2 = plist;
                while (p2 instanceof Pair) {
                    Pair pair3 = (Pair) p2;
                    Object property2 = pair3.getCar();
                    Location loc = env.lookup(sym, property2);
                    if (loc != null) {
                        Location loc2 = loc.getBase();
                        if (loc2 instanceof PropertyLocation) {
                            ploc = (PropertyLocation) loc2;
                            Pair valuePair = (Pair) pair3.getCdr();
                            ploc.pair = valuePair;
                            p2 = valuePair.getCdr();
                        }
                    }
                    ploc = new PropertyLocation();
                    env.addLocation(sym, property2, ploc);
                    Pair valuePair2 = (Pair) pair3.getCdr();
                    ploc.pair = valuePair2;
                    p2 = valuePair2.getCdr();
                }
            }
            lloc.set(plist);
        }
    }

    public static void setPropertyList(Object symbol, Object plist) {
        setPropertyList(symbol, plist, Environment.getCurrent());
    }

    public static Object getProperty(Object symbol, Object property, Object defaultValue, Environment env) {
        if (!(symbol instanceof Symbol)) {
            if (!(symbol instanceof String)) {
                return plistGet(env.get(Symbol.PLIST, symbol, LList.Empty), property, defaultValue);
            }
            symbol = Namespace.getDefaultSymbol((String) symbol);
        }
        return env.get((Symbol) symbol, property, defaultValue);
    }

    public static Object getProperty(Object symbol, Object property, Object defaultValue) {
        return getProperty(symbol, property, defaultValue, Environment.getCurrent());
    }

    public static void putProperty(Object symbol, Object property, Object newValue, Environment env) {
        if (!(symbol instanceof Symbol)) {
            if (symbol instanceof String) {
                symbol = Namespace.getDefaultSymbol((String) symbol);
            } else {
                Location lloc = env.getLocation(Symbol.PLIST, symbol);
                lloc.set(plistPut(lloc.get(LList.Empty), property, newValue));
                return;
            }
        }
        Location loc = env.lookup((Symbol) symbol, property);
        if (loc != null) {
            Location loc2 = loc.getBase();
            if (loc2 instanceof PropertyLocation) {
                ((PropertyLocation) loc2).set(newValue);
                return;
            }
        }
        Location lloc2 = env.getLocation(Symbol.PLIST, symbol);
        Pair pair2 = new Pair(newValue, lloc2.get(LList.Empty));
        lloc2.set(new Pair(property, pair2));
        PropertyLocation ploc = new PropertyLocation();
        ploc.pair = pair2;
        env.addLocation((Symbol) symbol, property, ploc);
    }

    public static void putProperty(Object symbol, Object property, Object newValue) {
        putProperty(symbol, property, newValue, Environment.getCurrent());
    }

    public static boolean removeProperty(Object symbol, Object property, Environment env) {
        Location ploc = env.lookup(Symbol.PLIST, symbol);
        if (ploc == null) {
            return false;
        }
        Object plist = ploc.get(LList.Empty);
        if (!(plist instanceof Pair)) {
            return false;
        }
        Pair pair2 = (Pair) plist;
        Pair prev = null;
        while (pair2.getCar() != property) {
            Object next = pair2.getCdr();
            if (!(next instanceof Pair)) {
                return false;
            }
            prev = pair2;
            pair2 = (Pair) next;
        }
        Object tail = ((Pair) pair2.getCdr()).getCdr();
        if (prev == null) {
            ploc.set(tail);
        } else {
            prev.setCdr(tail);
        }
        if (symbol instanceof Symbol) {
            env.remove((Symbol) symbol, property);
        }
        return true;
    }

    public static boolean removeProperty(Object symbol, Object property) {
        return removeProperty(symbol, property, Environment.getCurrent());
    }

    public static Object plistGet(Object plist, Object prop, Object dfault) {
        while (plist instanceof Pair) {
            Pair pair2 = (Pair) plist;
            if (pair2.getCar() == prop) {
                return ((Pair) pair2.getCdr()).getCar();
            }
        }
        return dfault;
    }

    public static Object plistPut(Object plist, Object prop, Object value) {
        Object p = plist;
        while (p instanceof Pair) {
            Pair pair2 = (Pair) p;
            Pair next = (Pair) pair2.getCdr();
            if (pair2.getCar() == prop) {
                next.setCar(value);
                return plist;
            }
            p = next.getCdr();
        }
        return new Pair(prop, new Pair(value, plist));
    }

    public static Object plistRemove(Object plist, Object prop) {
        Pair prev = null;
        Object p = plist;
        while (p instanceof Pair) {
            Pair pair2 = (Pair) p;
            Pair next = (Pair) pair2.getCdr();
            p = next.getCdr();
            if (pair2.getCar() != prop) {
                prev = next;
            } else if (prev == null) {
                return p;
            } else {
                prev.setCdr(p);
                return plist;
            }
        }
        return plist;
    }
}
