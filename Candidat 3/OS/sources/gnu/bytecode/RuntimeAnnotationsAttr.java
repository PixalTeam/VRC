package gnu.bytecode;

public class RuntimeAnnotationsAttr extends MiscAttr {
    int numEntries = u2(0);

    public RuntimeAnnotationsAttr(String name, byte[] data, AttrContainer container) {
        super(name, data, 0, data.length);
        addToFrontOf(container);
    }

    public void print(ClassTypeWriter dst) {
        dst.print("Attribute \"");
        dst.print(getName());
        dst.print("\", length:");
        dst.print(getLength());
        dst.print(", number of entries: ");
        dst.println(this.numEntries);
        int saveOffset = this.offset;
        this.offset = saveOffset + 2;
        for (int i = 0; i < this.numEntries; i++) {
            printAnnotation(2, dst);
        }
        this.offset = saveOffset;
    }

    public void printAnnotation(int indentation, ClassTypeWriter dst) {
        int type_index = u2();
        dst.printSpaces(indentation);
        dst.printOptionalIndex(type_index);
        dst.print('@');
        dst.printContantUtf8AsClass(type_index);
        int num_element_value_pairs = u2();
        dst.println();
        int indentation2 = indentation + 2;
        for (int i = 0; i < num_element_value_pairs; i++) {
            int element_name_index = u2();
            dst.printSpaces(indentation2);
            dst.printOptionalIndex(element_name_index);
            dst.printConstantTersely(element_name_index, 1);
            dst.print(" => ");
            printAnnotationElementValue(indentation2, dst);
            dst.println();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002d, code lost:
        if (r5 != 0) goto L_0x0030;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002f, code lost:
        r5 = 5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0030, code lost:
        if (r5 != 0) goto L_0x0033;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0032, code lost:
        r5 = 6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0033, code lost:
        if (r5 != 0) goto L_0x0036;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0035, code lost:
        r5 = 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0036, code lost:
        if (r5 != 0) goto L_0x0039;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0038, code lost:
        r5 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0039, code lost:
        r3 = u2();
        r4 = r15.getCpoolEntry(r3);
        r15.printOptionalIndex(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0046, code lost:
        if (r8 != 90) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0048, code lost:
        if (r4 == null) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004f, code lost:
        if (r4.getTag() != 3) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0051, code lost:
        r0 = (gnu.bytecode.CpoolValue1) r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0056, code lost:
        if (r0.value == 0) goto L_0x005c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x005a, code lost:
        if (r0.value != 1) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x005e, code lost:
        if (r0.value != 0) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0060, code lost:
        r10 = "false";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0062, code lost:
        r15.print(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0066, code lost:
        r10 = "true";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0069, code lost:
        r15.printConstantTersely(r3, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        return;
     */
    public void printAnnotationElementValue(int indentation, ClassTypeWriter dst) {
        int tag = u1();
        if ((dst.flags & 8) != 0) {
            dst.print("[kind:");
            if (tag < 65 || tag > 122) {
                dst.print(tag);
            } else {
                dst.print((char) tag);
            }
            dst.print("] ");
        }
        int expected = 0;
        switch (tag) {
            case 64:
                dst.println();
                dst.printSpaces(indentation + 2);
                printAnnotation(indentation + 2, dst);
                return;
            case 66:
            case 67:
            case 73:
            case 83:
            case 90:
                if (0 == 0) {
                    expected = 3;
                    break;
                }
                break;
            case 68:
                break;
            case 70:
                break;
            case 74:
                break;
            case 91:
                int num_values = u2();
                dst.print("array length:");
                dst.print(num_values);
                for (int i = 0; i < num_values; i++) {
                    dst.println();
                    dst.printSpaces(indentation + 2);
                    dst.print(i);
                    dst.print(": ");
                    printAnnotationElementValue(indentation + 2, dst);
                }
                return;
            case 99:
                int class_info_index = u2();
                dst.printOptionalIndex(class_info_index);
                dst.printContantUtf8AsClass(class_info_index);
                return;
            case 101:
                int type_name_index = u2();
                int const_name_index = u2();
                dst.print("enum[");
                if ((dst.flags & 8) != 0) {
                    dst.print("type:");
                }
                dst.printOptionalIndex(type_name_index);
                dst.printContantUtf8AsClass(type_name_index);
                if ((dst.flags & 8) != 0) {
                    dst.print(" value:");
                } else {
                    dst.print(' ');
                }
                dst.printOptionalIndex(const_name_index);
                dst.printConstantTersely(const_name_index, 1);
                dst.print("]");
                return;
            case 115:
                break;
            default:
                return;
        }
    }
}
