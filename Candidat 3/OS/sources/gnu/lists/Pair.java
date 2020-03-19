package gnu.lists;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

public class Pair extends LList implements Externalizable {
    protected Object car;
    protected Object cdr;

    public Pair(Object carval, Object cdrval) {
        this.car = carval;
        this.cdr = cdrval;
    }

    public Pair() {
    }

    public Object getCar() {
        return this.car;
    }

    public Object getCdr() {
        return this.cdr;
    }

    public void setCar(Object car2) {
        this.car = car2;
    }

    public void setCdr(Object cdr2) {
        this.cdr = cdr2;
    }

    public void setCdrBackdoor(Object cdr2) {
        this.cdr = cdr2;
    }

    public int size() {
        int n = listLength(this, true);
        if (n >= 0) {
            return n;
        }
        if (n == -1) {
            return ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        }
        throw new RuntimeException("not a true list");
    }

    public boolean isEmpty() {
        return false;
    }

    public int length() {
        int n = 0;
        Object obj = this;
        Object obj2 = this;
        while (obj != Empty) {
            if (obj instanceof Pair) {
                Pair fast_pair = (Pair) obj;
                if (fast_pair.cdr == Empty) {
                    return n + 1;
                }
                if (obj == obj2 && n > 0) {
                    return -1;
                }
                if (!(fast_pair.cdr instanceof Pair)) {
                    n++;
                    obj = fast_pair.cdr;
                } else if (!(obj2 instanceof Pair)) {
                    return -2;
                } else {
                    Object slow = ((Pair) obj2).cdr;
                    obj = ((Pair) fast_pair.cdr).cdr;
                    n += 2;
                    obj2 = slow;
                }
            } else if (!(obj instanceof Sequence)) {
                return -2;
            } else {
                int j = ((Sequence) obj).size();
                if (j >= 0) {
                    j += n;
                }
                return j;
            }
        }
        return n;
    }

    public boolean hasNext(int ipos) {
        if (ipos <= 0) {
            return ipos == 0;
        }
        return PositionManager.getPositionObject(ipos).hasNext();
    }

    public int nextPos(int ipos) {
        if (ipos > 0) {
            if (!((LListPosition) PositionManager.getPositionObject(ipos)).gotoNext()) {
                ipos = 0;
            }
            return ipos;
        } else if (ipos < 0) {
            return 0;
        } else {
            return PositionManager.manager.register(new LListPosition(this, 1, true));
        }
    }

    public Object getPosNext(int ipos) {
        if (ipos <= 0) {
            return ipos == 0 ? this.car : eofValue;
        }
        return PositionManager.getPositionObject(ipos).getNext();
    }

    public Object getPosPrevious(int ipos) {
        if (ipos <= 0) {
            return ipos == 0 ? eofValue : lastPair().car;
        }
        return PositionManager.getPositionObject(ipos).getPrevious();
    }

    public final Pair lastPair() {
        Pair pair = this;
        while (true) {
            Object next = pair.cdr;
            if (!(next instanceof Pair)) {
                return pair;
            }
            pair = (Pair) next;
        }
    }

    public int hashCode() {
        int hash = 1;
        Object obj = this;
        while (obj instanceof Pair) {
            Pair pair = (Pair) obj;
            Object obj2 = pair.car;
            hash = (hash * 31) + (obj2 == null ? 0 : obj2.hashCode());
            obj = pair.cdr;
        }
        if (obj == LList.Empty || obj == null) {
            return hash;
        }
        return hash ^ obj.hashCode();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        return r0.equals(r1);
     */
    public static boolean equals(Pair pair1, Pair pair2) {
        if (pair1 == pair2) {
            return true;
        }
        if (pair1 == null || pair2 == null) {
            return false;
        }
        while (true) {
            Object x1 = pair1.car;
            Object x2 = pair2.car;
            if (x1 == x2 || (x1 != null && x1.equals(x2))) {
                Object x12 = pair1.cdr;
                Object x22 = pair2.cdr;
                if (x12 == x22) {
                    return true;
                }
                if (x12 != null && x22 != null) {
                    if ((x12 instanceof Pair) && (x22 instanceof Pair)) {
                        pair1 = (Pair) x12;
                        pair2 = (Pair) x22;
                    }
                }
            }
        }
        return false;
    }

    public static int compareTo(Pair pair1, Pair pair2) {
        Object x1;
        Object x2;
        if (pair1 == pair2) {
            return 0;
        }
        if (pair1 == null) {
            return -1;
        }
        if (pair2 == null) {
            return 1;
        }
        while (true) {
            int d = ((Comparable) pair1.car).compareTo((Comparable) pair2.car);
            if (d != 0) {
                return d;
            }
            x1 = pair1.cdr;
            x2 = pair2.cdr;
            if (x1 == x2) {
                return 0;
            }
            if (x1 == null) {
                return -1;
            }
            if (x2 == null) {
                return 1;
            }
            if ((x1 instanceof Pair) && (x2 instanceof Pair)) {
                pair1 = (Pair) x1;
                pair2 = (Pair) x2;
            }
        }
        return ((Comparable) x1).compareTo((Comparable) x2);
    }

    public int compareTo(Object obj) {
        if (obj == Empty) {
            return 1;
        }
        return compareTo(this, (Pair) obj);
    }

    public Object get(int index) {
        Pair pair = this;
        int i = index;
        while (true) {
            if (i <= 0) {
                break;
            }
            i--;
            if (pair.cdr instanceof Pair) {
                pair = (Pair) pair.cdr;
            } else if (pair.cdr instanceof Sequence) {
                return ((Sequence) pair.cdr).get(i);
            }
        }
        if (i == 0) {
            return pair.car;
        }
        throw new IndexOutOfBoundsException();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Pair)) {
            return false;
        }
        return equals(this, (Pair) obj);
    }

    public static Pair make(Object car2, Object cdr2) {
        return new Pair(car2, cdr2);
    }

    public Object[] toArray() {
        int len = size();
        Object[] arr = new Object[len];
        int i = 0;
        Sequence rest = this;
        while (i < len && (rest instanceof Pair)) {
            Pair pair = (Pair) rest;
            arr[i] = pair.car;
            i++;
            rest = (Sequence) pair.cdr;
        }
        int prefix = i;
        while (i < len) {
            arr[i] = rest.get(i - prefix);
            i++;
        }
        return arr;
    }

    public Object[] toArray(Object[] arr) {
        int alen = arr.length;
        int len = length();
        if (len > alen) {
            arr = new Object[len];
            alen = len;
        }
        int i = 0;
        Sequence rest = this;
        while (i < len && (rest instanceof Pair)) {
            Pair pair = (Pair) rest;
            arr[i] = pair.car;
            i++;
            rest = (Sequence) pair.cdr;
        }
        int prefix = i;
        while (i < len) {
            arr[i] = rest.get(i - prefix);
            i++;
        }
        if (len < alen) {
            arr[len] = null;
        }
        return arr;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.car);
        out.writeObject(this.cdr);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.car = in.readObject();
        this.cdr = in.readObject();
    }

    public Object readResolve() throws ObjectStreamException {
        return this;
    }
}
