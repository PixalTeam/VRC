package kawa.lang;

import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ModuleExp;
import gnu.expr.ScopeExp;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.OutPort;
import gnu.mapping.Symbol;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.PrintWriter;
import java.util.Vector;

public class SyntaxPattern extends Pattern implements Externalizable {
    static final int MATCH_ANY = 3;
    static final int MATCH_ANY_CAR = 7;
    static final int MATCH_EQUALS = 2;
    static final int MATCH_IGNORE = 24;
    static final int MATCH_LENGTH = 6;
    static final int MATCH_LREPEAT = 5;
    static final int MATCH_MISC = 0;
    static final int MATCH_NIL = 8;
    static final int MATCH_PAIR = 4;
    static final int MATCH_VECTOR = 16;
    static final int MATCH_WIDE = 1;
    Object[] literals;
    String program;
    int varCount;

    public int varCount() {
        return this.varCount;
    }

    public boolean match(Object obj, Object[] vars, int start_vars) {
        return match(obj, vars, start_vars, 0, null);
    }

    public SyntaxPattern(String program2, Object[] literals2, int varCount2) {
        this.program = program2;
        this.literals = literals2;
        this.varCount = varCount2;
    }

    public SyntaxPattern(Object pattern, Object[] literal_identifiers, Translator tr) {
        this(new StringBuffer(), pattern, null, literal_identifiers, tr);
    }

    SyntaxPattern(StringBuffer programbuf, Object pattern, SyntaxForm syntax, Object[] literal_identifiers, Translator tr) {
        Vector literalsbuf = new Vector();
        translate(pattern, programbuf, literal_identifiers, 0, literalsbuf, null, 0, tr);
        this.program = programbuf.toString();
        this.literals = new Object[literalsbuf.size()];
        literalsbuf.copyInto(this.literals);
        this.varCount = tr.patternScope.pattern_names.size();
    }

    public void disassemble() {
        disassemble(OutPort.errDefault(), (Translator) Compilation.getCurrent(), 0, this.program.length());
    }

    public void disassemble(PrintWriter ps, Translator tr) {
        disassemble(ps, tr, 0, this.program.length());
    }

    /* access modifiers changed from: 0000 */
    public void disassemble(PrintWriter ps, Translator tr, int start, int limit) {
        Vector pattern_names = null;
        if (!(tr == null || tr.patternScope == null)) {
            pattern_names = tr.patternScope.pattern_names;
        }
        int value = 0;
        int i = start;
        while (i < limit) {
            int ch = this.program.charAt(i);
            ps.print(" " + i + ": " + ch);
            i++;
            int opcode = ch & 7;
            value = (value << 13) | (ch >> 3);
            switch (opcode) {
                case 0:
                    ps.print("[misc ch:" + ch + " n:" + 8 + "]");
                    if (ch != 8) {
                        if (ch != 16) {
                            if (ch == 24) {
                                ps.println(" - IGNORE");
                                break;
                            }
                        } else {
                            ps.println(" - VECTOR");
                            break;
                        }
                    } else {
                        ps.println(" - NIL");
                        break;
                    }
                case 1:
                    ps.println(" - WIDE " + value);
                    continue;
                case 2:
                    ps.print(" - EQUALS[" + value + "]");
                    if (this.literals != null && value >= 0 && value < this.literals.length) {
                        ps.print(this.literals[value]);
                    }
                    ps.println();
                    break;
                case 3:
                case 7:
                    ps.print((opcode == 3 ? " - ANY[" : " - ANY_CAR[") + value + "]");
                    if (pattern_names != null && value >= 0 && value < pattern_names.size()) {
                        ps.print(pattern_names.elementAt(value));
                    }
                    ps.println();
                    break;
                case 4:
                    ps.println(" - PAIR[" + value + "]");
                    break;
                case 5:
                    ps.println(" - LREPEAT[" + value + "]");
                    disassemble(ps, tr, i, i + value);
                    int i2 = i + value;
                    int i3 = i2 + 1;
                    ps.println(" " + i2 + ": - repeat first var:" + (this.program.charAt(i2) >> 3));
                    i = i3 + 1;
                    ps.println(" " + i3 + ": - repeast nested vars:" + (this.program.charAt(i3) >> 3));
                    break;
                case 6:
                    ps.println(" - LENGTH " + (value >> 1) + " pairs. " + ((value & 1) == 0 ? "pure list" : "impure list"));
                    break;
                default:
                    ps.println(" - " + opcode + '/' + value);
                    break;
            }
            value = 0;
        }
    }

    /* access modifiers changed from: 0000 */
    public void translate(Object pattern, StringBuffer program2, Object[] literal_identifiers, int nesting, Vector literals2, SyntaxForm syntax, char context, Translator tr) {
        int i;
        ScopeExp scope1;
        Object literal;
        ScopeExp scope2;
        Object next;
        int i2;
        int restLength;
        PatternScope patternScope = tr.patternScope;
        Vector patternNames = patternScope.pattern_names;
        while (true) {
            if (pattern instanceof SyntaxForm) {
                syntax = (SyntaxForm) pattern;
                pattern = syntax.getDatum();
            } else if (pattern instanceof Pair) {
                Object savePos = tr.pushPositionOf(pattern);
                try {
                    int start_pc = program2.length();
                    program2.append(4);
                    Pair pair = (Pair) pattern;
                    SyntaxForm car_syntax = syntax;
                    Object next2 = pair.getCdr();
                    while (next instanceof SyntaxForm) {
                        syntax = (SyntaxForm) next;
                        next2 = syntax.getDatum();
                    }
                    boolean repeat = false;
                    if (next instanceof Pair) {
                        if (tr.matches(((Pair) next).getCar(), "...")) {
                            repeat = true;
                            next = ((Pair) next).getCdr();
                            while (next instanceof SyntaxForm) {
                                syntax = (SyntaxForm) next;
                                next = syntax.getDatum();
                            }
                        }
                    }
                    int subvar0 = patternNames.size();
                    if (context == 'P') {
                        context = 0;
                    }
                    Object car = pair.getCar();
                    if (repeat) {
                        i2 = nesting + 1;
                    } else {
                        i2 = nesting;
                    }
                    translate(car, program2, literal_identifiers, i2, literals2, car_syntax, context == 'V' ? 0 : 'P', tr);
                    int subvarN = patternNames.size() - subvar0;
                    int width = (((program2.length() - start_pc) - 1) << 3) | (repeat ? 5 : 4);
                    if (width > 65535) {
                        start_pc += insertInt(start_pc, program2, (width >> 13) + 1);
                    }
                    program2.setCharAt(start_pc, (char) width);
                    int restLength2 = Translator.listLength(next);
                    if (restLength2 == Integer.MIN_VALUE) {
                        tr.syntaxError("cyclic pattern list");
                        tr.popPositionOf(savePos);
                        return;
                    }
                    if (repeat) {
                        addInt(program2, subvar0 << 3);
                        addInt(program2, subvarN << 3);
                        if (next == LList.Empty) {
                            program2.append(8);
                            tr.popPositionOf(savePos);
                            return;
                        }
                        if (restLength2 >= 0) {
                            restLength = restLength2 << 1;
                        } else {
                            restLength = ((-restLength2) << 1) - 1;
                        }
                        addInt(program2, (restLength << 3) | 6);
                    }
                    pattern = next;
                } finally {
                    tr.popPositionOf(savePos);
                }
            } else if (pattern instanceof Symbol) {
                int j = literal_identifiers.length;
                do {
                    j--;
                    if (j >= 0) {
                        ScopeExp current = tr.currentScope();
                        scope1 = syntax == null ? current : syntax.getScope();
                        literal = literal_identifiers[j];
                        if (literal instanceof SyntaxForm) {
                            SyntaxForm syntax2 = (SyntaxForm) literal;
                            literal = syntax2.getDatum();
                            scope2 = syntax2.getScope();
                        } else if (tr.currentMacroDefinition != null) {
                            scope2 = tr.currentMacroDefinition.getCapturedScope();
                        } else {
                            scope2 = current;
                        }
                    } else {
                        if (patternNames.contains(pattern)) {
                            tr.syntaxError("duplicated pattern variable " + pattern);
                        }
                        int i3 = patternNames.size();
                        patternNames.addElement(pattern);
                        boolean matchCar = context == 'P';
                        patternScope.patternNesting.append((char) ((nesting << 1) + (matchCar ? 1 : 0)));
                        Declaration decl = patternScope.addDeclaration(pattern);
                        decl.setLocation(tr);
                        tr.push(decl);
                        int i4 = i3 << 3;
                        if (matchCar) {
                            i = 7;
                        } else {
                            i = 3;
                        }
                        addInt(program2, i | i4);
                        return;
                    }
                } while (!literalIdentifierEq(pattern, scope1, literal, scope2));
                int i5 = SyntaxTemplate.indexOf(literals2, pattern);
                if (i5 < 0) {
                    i5 = literals2.size();
                    literals2.addElement(pattern);
                }
                addInt(program2, (i5 << 3) | 2);
                return;
            } else if (pattern == LList.Empty) {
                program2.append(8);
                return;
            } else if (pattern instanceof FVector) {
                program2.append(16);
                pattern = LList.makeList((FVector) pattern);
                context = 'V';
            } else {
                int i6 = SyntaxTemplate.indexOf(literals2, pattern);
                if (i6 < 0) {
                    i6 = literals2.size();
                    literals2.addElement(pattern);
                }
                addInt(program2, (i6 << 3) | 2);
                return;
            }
        }
    }

    private static void addInt(StringBuffer sbuf, int val) {
        if (val > 65535) {
            addInt(sbuf, (val << 13) + 1);
        }
        sbuf.append((char) val);
    }

    private static int insertInt(int offset, StringBuffer sbuf, int val) {
        if (val > 65535) {
            offset += insertInt(offset, sbuf, (val << 13) + 1);
        }
        sbuf.insert(offset, (char) val);
        return offset + 1;
    }

    /* access modifiers changed from: 0000 */
    public boolean match_car(Pair p, Object[] vars, int start_vars, int pc, SyntaxForm syntax) {
        int pc_start = pc;
        int pc2 = pc + 1;
        char ch = this.program.charAt(pc);
        int value = ch >> 3;
        while ((ch & 7) == 1) {
            int i = value << 13;
            int pc3 = pc2 + 1;
            ch = this.program.charAt(pc2);
            value = i | (ch >> 3);
            pc2 = pc3;
        }
        if ((ch & 7) == 7) {
            if (syntax != null && !(p.getCar() instanceof SyntaxForm)) {
                p = Translator.makePair(p, SyntaxForms.fromDatum(p.getCar(), syntax), p.getCdr());
            }
            vars[start_vars + value] = p;
            return true;
        }
        return match(p.getCar(), vars, start_vars, pc_start, syntax);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:171:?, code lost:
        return false;
     */
    public boolean match(Object obj, Object[] vars, int start_vars, int pc, SyntaxForm syntax) {
        int pc2;
        Object id1;
        ScopeExp sc1;
        Object id2;
        ScopeExp sc2;
        boolean z;
        int pairsRequired;
        boolean listValue;
        int value = 0;
        while (true) {
            if (obj instanceof SyntaxForm) {
                syntax = (SyntaxForm) obj;
                obj = syntax.getDatum();
            } else {
                pc2 = pc + 1;
                int ch = this.program.charAt(pc);
                value = (value << 13) | (ch >> 3);
                switch (ch & 7) {
                    case 0:
                        if (ch == 8) {
                            if (obj == LList.Empty) {
                                z = true;
                            } else {
                                z = false;
                            }
                            int i = pc2;
                            return z;
                        } else if (ch == 16) {
                            if (!(obj instanceof FVector)) {
                                int i2 = pc2;
                                return false;
                            }
                            int i3 = pc2;
                            return match(LList.makeList((FVector) obj), vars, start_vars, pc2, syntax);
                        } else if (ch == 24) {
                            int i4 = pc2;
                            return true;
                        } else {
                            throw new Error("unknwon pattern opcode");
                        }
                    case 1:
                        pc = pc2;
                        break;
                    case 2:
                        Object lit = this.literals[value];
                        Translator tr = (Translator) Compilation.getCurrent();
                        if (lit instanceof SyntaxForm) {
                            SyntaxForm sf = (SyntaxForm) lit;
                            id1 = sf.getDatum();
                            sc1 = sf.getScope();
                        } else {
                            id1 = lit;
                            Syntax curSyntax = tr.getCurrentSyntax();
                            sc1 = curSyntax instanceof Macro ? ((Macro) curSyntax).getCapturedScope() : null;
                        }
                        if (obj instanceof SyntaxForm) {
                            SyntaxForm sf2 = (SyntaxForm) obj;
                            id2 = sf2.getDatum();
                            sc2 = sf2.getScope();
                        } else {
                            id2 = obj;
                            sc2 = syntax == null ? tr.currentScope() : syntax.getScope();
                        }
                        int i5 = pc2;
                        return literalIdentifierEq(id1, sc1, id2, sc2);
                    case 3:
                        if (syntax != null) {
                            obj = SyntaxForms.fromDatum(obj, syntax);
                        }
                        vars[start_vars + value] = obj;
                        int i6 = pc2;
                        return true;
                    case 4:
                        if (obj instanceof Pair) {
                            Pair p = (Pair) obj;
                            if (match_car(p, vars, start_vars, pc2, syntax)) {
                                pc = pc2 + value;
                                value = 0;
                                obj = p.getCdr();
                                break;
                            } else {
                                int i7 = pc2;
                                return false;
                            }
                        } else {
                            int i8 = pc2;
                            return false;
                        }
                    case 5:
                        int repeat_pc = pc2;
                        int pc3 = pc2 + value;
                        int pc4 = pc3 + 1;
                        char ch2 = this.program.charAt(pc3);
                        int subvar0 = ch2 >> 3;
                        while ((ch2 & 7) == 1) {
                            int i9 = subvar0 << 13;
                            int pc5 = pc4 + 1;
                            ch2 = this.program.charAt(pc4);
                            subvar0 = i9 | (ch2 >> 3);
                            pc4 = pc5;
                        }
                        int subvar02 = subvar0 + start_vars;
                        int pc6 = pc4 + 1;
                        int subvarN = this.program.charAt(pc4) >> 3;
                        while (true) {
                            int pc7 = pc6;
                            if ((ch2 & 7) == 1) {
                                int i10 = subvarN << 13;
                                pc6 = pc7 + 1;
                                ch2 = this.program.charAt(pc7);
                                subvarN = i10 | (ch2 >> 3);
                            } else {
                                pc = pc7 + 1;
                                char ch3 = this.program.charAt(pc7);
                                boolean listRequired = true;
                                if (ch3 == 8) {
                                    pairsRequired = 0;
                                } else {
                                    int value2 = ch3 >> 3;
                                    while (true) {
                                        int pc8 = pc;
                                        if ((ch3 & 7) == 1) {
                                            int i11 = value2 << 13;
                                            pc = pc8 + 1;
                                            ch3 = this.program.charAt(pc8);
                                            value2 = i11 | (ch3 >> 3);
                                        } else {
                                            if ((value2 & 1) != 0) {
                                                listRequired = false;
                                            }
                                            pairsRequired = value2 >> 1;
                                            pc = pc8;
                                        }
                                    }
                                }
                                int pairsValue = Translator.listLength(obj);
                                if (pairsValue >= 0) {
                                    listValue = true;
                                } else {
                                    listValue = false;
                                    pairsValue = -1 - pairsValue;
                                }
                                if (pairsValue < pairsRequired || (listRequired && !listValue)) {
                                    break;
                                } else {
                                    int repeat_count = pairsValue - pairsRequired;
                                    Object[][] arrays = new Object[subvarN][];
                                    for (int j = 0; j < subvarN; j++) {
                                        arrays[j] = new Object[repeat_count];
                                    }
                                    for (int i12 = 0; i12 < repeat_count; i12++) {
                                        while (obj instanceof SyntaxForm) {
                                            syntax = (SyntaxForm) obj;
                                            obj = syntax.getDatum();
                                        }
                                        Pair p2 = (Pair) obj;
                                        if (!match_car(p2, vars, start_vars, repeat_pc, syntax)) {
                                            return false;
                                        }
                                        obj = p2.getCdr();
                                        for (int j2 = 0; j2 < subvarN; j2++) {
                                            arrays[j2][i12] = vars[subvar02 + j2];
                                        }
                                    }
                                    for (int j3 = 0; j3 < subvarN; j3++) {
                                        vars[subvar02 + j3] = arrays[j3];
                                    }
                                    value = 0;
                                    if (pairsRequired == 0 && listRequired) {
                                        return true;
                                    }
                                }
                            }
                        }
                        break;
                    case 6:
                        int npairs = value >> 1;
                        Object o = obj;
                        int i13 = 0;
                        while (true) {
                            if (o instanceof SyntaxForm) {
                                o = ((SyntaxForm) o).getDatum();
                            } else if (i13 == npairs) {
                                if ((value & 1) == 0) {
                                    if (o != LList.Empty) {
                                        break;
                                    }
                                    value = 0;
                                    pc = pc2;
                                    break;
                                } else {
                                    if (o instanceof Pair) {
                                        break;
                                    }
                                    value = 0;
                                    pc = pc2;
                                }
                            } else if (o instanceof Pair) {
                                o = ((Pair) o).getCdr();
                                i13++;
                            } else {
                                int i14 = pc2;
                                return false;
                            }
                        }
                    case 8:
                        int i15 = pc2;
                        return obj == LList.Empty;
                    default:
                        disassemble();
                        throw new Error("unrecognized pattern opcode @pc:" + pc2);
                }
            }
        }
        int i16 = pc2;
        return false;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.program);
        out.writeObject(this.literals);
        out.writeInt(this.varCount);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.literals = (Object[]) in.readObject();
        this.program = (String) in.readObject();
        this.varCount = in.readInt();
    }

    public static Object[] allocVars(int varCount2, Object[] outer) {
        Object[] vars = new Object[varCount2];
        if (outer != null) {
            System.arraycopy(outer, 0, vars, 0, outer.length);
        }
        return vars;
    }

    public static boolean literalIdentifierEq(Object id1, ScopeExp sc1, Object id2, ScopeExp sc2) {
        if (id1 != id2 && (id1 == null || id2 == null || !id1.equals(id2))) {
            return false;
        }
        if (sc1 == sc2) {
            return true;
        }
        Declaration d1 = null;
        Declaration d2 = null;
        while (sc1 != null && !(sc1 instanceof ModuleExp)) {
            d1 = sc1.lookup(id1);
            if (d1 != null) {
                break;
            }
            sc1 = sc1.outer;
        }
        while (sc2 != null && !(sc2 instanceof ModuleExp)) {
            d2 = sc2.lookup(id2);
            if (d2 != null) {
                break;
            }
            sc2 = sc2.outer;
        }
        if (d1 != d2) {
            return false;
        }
        return true;
    }

    public static Object[] getLiteralsList(Object list, SyntaxForm syntax, Translator tr) {
        Object wrapped;
        Object savePos = tr.pushPositionOf(list);
        int count = Translator.listLength(list);
        if (count < 0) {
            tr.error('e', "missing or malformed literals list");
            count = 0;
        }
        Object[] literals2 = new Object[(count + 1)];
        for (int i = 1; i <= count; i++) {
            while (list instanceof SyntaxForm) {
                list = ((SyntaxForm) list).getDatum();
            }
            Pair pair = (Pair) list;
            tr.pushPositionOf(pair);
            Object literal = pair.getCar();
            if (literal instanceof SyntaxForm) {
                wrapped = literal;
                literal = ((SyntaxForm) literal).getDatum();
            } else {
                wrapped = literal;
            }
            if (!(literal instanceof Symbol)) {
                tr.error('e', "non-symbol '" + literal + "' in literals list");
            }
            literals2[i] = wrapped;
            list = pair.getCdr();
        }
        tr.popPositionOf(savePos);
        return literals2;
    }

    public void print(Consumer out) {
        out.write("#<syntax-pattern>");
    }
}
