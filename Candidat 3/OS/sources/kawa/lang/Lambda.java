package kawa.lang;

import gnu.bytecode.Type;
import gnu.expr.BeginExp;
import gnu.expr.Declaration;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.LangExp;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.ThisExp;
import gnu.kawa.lispexpr.LangObjType;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import kawa.standard.object;

public class Lambda extends Syntax {
    public static final Keyword nameKeyword = Keyword.make("name");
    public Expression defaultDefault = QuoteExp.falseExp;
    public Object keyKeyword;
    public Object optionalKeyword;
    public Object restKeyword;

    public void setKeywords(Object optional, Object rest, Object key) {
        this.optionalKeyword = optional;
        this.restKeyword = rest;
        this.keyKeyword = key;
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        Expression exp = rewrite(form.getCdr(), tr);
        Translator.setLine(exp, (Object) form);
        return exp;
    }

    public Expression rewrite(Object obj, Translator tr) {
        if (!(obj instanceof Pair)) {
            return tr.syntaxError("missing formals in lambda");
        }
        int old_errors = tr.getMessages().getErrorCount();
        LambdaExp lexp = new LambdaExp();
        Pair pair = (Pair) obj;
        Translator.setLine((Expression) lexp, (Object) pair);
        rewrite(lexp, pair.getCar(), pair.getCdr(), tr, null);
        if (tr.getMessages().getErrorCount() > old_errors) {
            return new ErrorExp("bad lambda expression");
        }
        return lexp;
    }

    public void rewrite(LambdaExp lexp, Object formals, Object body, Translator tr, TemplateScope templateScopeRest) {
        rewriteFormals(lexp, formals, tr, templateScopeRest);
        if (body instanceof PairWithPosition) {
            lexp.setFile(((PairWithPosition) body).getFileName());
        }
        rewriteBody(lexp, rewriteAttrs(lexp, body, tr), tr);
    }

    public void rewriteFormals(LambdaExp lexp, Object formals, Translator tr, TemplateScope templateScopeRest) {
        if (lexp.getSymbol() == null) {
            String filename = lexp.getFileName();
            int line = lexp.getLineNumber();
            if (filename != null && line > 0) {
                lexp.setSourceLocation(filename, line);
            }
        }
        Object bindings = formals;
        int opt_args = -1;
        int rest_args = -1;
        int key_args = -1;
        while (true) {
            if (bindings instanceof SyntaxForm) {
                bindings = ((SyntaxForm) bindings).getDatum();
            }
            if (!(bindings instanceof Pair)) {
                if (bindings instanceof Symbol) {
                    if (opt_args >= 0 || key_args >= 0 || rest_args >= 0) {
                        tr.syntaxError("dotted rest-arg after " + this.optionalKeyword + ", " + this.restKeyword + ", or " + this.keyKeyword);
                        return;
                    }
                    rest_args = 1;
                } else if (bindings != LList.Empty) {
                    tr.syntaxError("misformed formals in lambda");
                    return;
                }
                if (rest_args > 1) {
                    tr.syntaxError("multiple " + this.restKeyword + " parameters");
                    return;
                }
                if (opt_args < 0) {
                    opt_args = 0;
                }
                if (rest_args < 0) {
                    rest_args = 0;
                }
                if (key_args < 0) {
                    key_args = 0;
                }
                if (rest_args > 0) {
                    lexp.max_args = -1;
                } else {
                    lexp.max_args = lexp.min_args + opt_args + (key_args * 2);
                }
                if (opt_args + key_args > 0) {
                    lexp.defaultArgs = new Expression[(opt_args + key_args)];
                }
                if (key_args > 0) {
                    lexp.keywords = new Keyword[key_args];
                }
                Object bindings2 = formals;
                int opt_args2 = 0;
                int key_args2 = 0;
                Object obj = null;
                while (true) {
                    if (bindings2 instanceof SyntaxForm) {
                        SyntaxForm sf = (SyntaxForm) bindings2;
                        bindings2 = sf.getDatum();
                        templateScopeRest = sf.getScope();
                    }
                    TemplateScope templateScope = templateScopeRest;
                    if (!(bindings2 instanceof Pair)) {
                        if (bindings2 instanceof SyntaxForm) {
                            SyntaxForm sf2 = (SyntaxForm) bindings2;
                            bindings2 = sf2.getDatum();
                            templateScopeRest = sf2.getScope();
                        }
                        if (bindings2 instanceof Symbol) {
                            Declaration decl = new Declaration(bindings2);
                            decl.setType(LangObjType.listType);
                            decl.setFlag(262144);
                            decl.noteValue(null);
                            addParam(decl, templateScopeRest, lexp, tr);
                            return;
                        }
                        return;
                    }
                    Pair pair = (Pair) bindings2;
                    Object pair_car = pair.getCar();
                    if (pair_car instanceof SyntaxForm) {
                        SyntaxForm sf3 = (SyntaxForm) pair_car;
                        pair_car = sf3.getDatum();
                        templateScope = sf3.getScope();
                    }
                    if (pair_car == this.optionalKeyword || pair_car == this.restKeyword || pair_car == this.keyKeyword) {
                        obj = pair_car;
                    } else {
                        Object savePos = tr.pushPositionOf(pair);
                        Object name = null;
                        Object defaultValue = this.defaultDefault;
                        Pair typeSpecPair = null;
                        if (tr.matches(pair_car, "::")) {
                            tr.syntaxError("'::' must follow parameter name");
                            return;
                        }
                        Object pair_car2 = tr.namespaceResolve(pair_car);
                        if (pair_car2 instanceof Symbol) {
                            name = pair_car2;
                            if (pair.getCdr() instanceof Pair) {
                                Pair p = (Pair) pair.getCdr();
                                if (tr.matches(p.getCar(), "::")) {
                                    if (!(pair.getCdr() instanceof Pair)) {
                                        tr.syntaxError("'::' not followed by a type specifier (for parameter '" + name + "')");
                                        return;
                                    }
                                    Pair p2 = (Pair) p.getCdr();
                                    typeSpecPair = p2;
                                    pair = p2;
                                }
                            }
                        } else if (pair_car2 instanceof Pair) {
                            Pair p3 = (Pair) pair_car2;
                            Object pair_car3 = p3.getCar();
                            if (pair_car3 instanceof SyntaxForm) {
                                SyntaxForm sf4 = (SyntaxForm) pair_car3;
                                pair_car3 = sf4.getDatum();
                                templateScope = sf4.getScope();
                            }
                            Object pair_car4 = tr.namespaceResolve(pair_car3);
                            if ((pair_car4 instanceof Symbol) && (p3.getCdr() instanceof Pair)) {
                                name = pair_car4;
                                Pair p4 = (Pair) p3.getCdr();
                                if (tr.matches(p4.getCar(), "::")) {
                                    if (!(p4.getCdr() instanceof Pair)) {
                                        tr.syntaxError("'::' not followed by a type specifier (for parameter '" + name + "')");
                                        return;
                                    }
                                    Pair p5 = (Pair) p4.getCdr();
                                    typeSpecPair = p5;
                                    if (p5.getCdr() instanceof Pair) {
                                        p4 = (Pair) p5.getCdr();
                                    } else {
                                        if (p5.getCdr() == LList.Empty) {
                                            p4 = null;
                                        } else {
                                            tr.syntaxError("improper list in specifier for parameter '" + name + "')");
                                            return;
                                        }
                                    }
                                }
                                if (!(p4 == null || obj == null)) {
                                    defaultValue = p4.getCar();
                                    if (p4.getCdr() instanceof Pair) {
                                        p4 = (Pair) p4.getCdr();
                                    } else {
                                        if (p4.getCdr() == LList.Empty) {
                                            p4 = null;
                                        } else {
                                            tr.syntaxError("improper list in specifier for parameter '" + name + "')");
                                            return;
                                        }
                                    }
                                }
                                if (p4 != null) {
                                    if (typeSpecPair != null) {
                                        tr.syntaxError("duplicate type specifier for parameter '" + name + '\'');
                                        return;
                                    }
                                    typeSpecPair = p4;
                                    if (p4.getCdr() != LList.Empty) {
                                        tr.syntaxError("junk at end of specifier for parameter '" + name + '\'' + " after type " + p4.getCar());
                                        return;
                                    }
                                }
                            }
                        }
                        if (name == null) {
                            tr.syntaxError("parameter is neither name nor (name :: type) nor (name default): " + pair);
                            return;
                        }
                        if (obj == this.optionalKeyword || obj == this.keyKeyword) {
                            Expression[] expressionArr = lexp.defaultArgs;
                            int opt_args3 = opt_args2 + 1;
                            LangExp langExp = new LangExp(defaultValue);
                            expressionArr[opt_args2] = langExp;
                            opt_args2 = opt_args3;
                        }
                        if (obj == this.keyKeyword) {
                            int key_args3 = key_args2 + 1;
                            lexp.keywords[key_args2] = Keyword.make(name instanceof Symbol ? ((Symbol) name).getName() : name.toString());
                            key_args2 = key_args3;
                        }
                        Declaration decl2 = new Declaration(name);
                        Translator.setLine(decl2, bindings2);
                        if (typeSpecPair != null) {
                            LangExp langExp2 = new LangExp(typeSpecPair);
                            decl2.setTypeExp(langExp2);
                            decl2.setFlag(8192);
                        } else if (obj == this.restKeyword) {
                            decl2.setType(LangObjType.listType);
                        }
                        decl2.setFlag(262144);
                        decl2.noteValue(null);
                        addParam(decl2, templateScope, lexp, tr);
                        tr.popPositionOf(savePos);
                    }
                    bindings2 = pair.getCdr();
                }
            } else {
                Pair pair2 = (Pair) bindings;
                Object pair_car5 = pair2.getCar();
                if (pair_car5 instanceof SyntaxForm) {
                    pair_car5 = ((SyntaxForm) pair_car5).getDatum();
                }
                if (pair_car5 != this.optionalKeyword) {
                    if (pair_car5 != this.restKeyword) {
                        if (pair_car5 == this.keyKeyword) {
                            if (key_args >= 0) {
                                tr.syntaxError("multiple " + this.keyKeyword + " in parameter list");
                                return;
                            }
                            key_args = 0;
                        } else if (tr.matches(pair2.getCar(), "::") && (pair2.getCdr() instanceof Pair)) {
                            pair2 = (Pair) pair2.getCdr();
                        } else if (key_args >= 0) {
                            key_args++;
                        } else if (rest_args >= 0) {
                            rest_args++;
                        } else if (opt_args >= 0) {
                            opt_args++;
                        } else {
                            lexp.min_args++;
                        }
                    } else if (rest_args >= 0) {
                        tr.syntaxError("multiple " + this.restKeyword + " in parameter list");
                        return;
                    } else if (key_args >= 0) {
                        tr.syntaxError(this.restKeyword.toString() + " after " + this.keyKeyword);
                        return;
                    } else {
                        rest_args = 0;
                    }
                } else if (opt_args >= 0) {
                    tr.syntaxError("multiple " + this.optionalKeyword + " in parameter list");
                    return;
                } else if (rest_args >= 0 || key_args >= 0) {
                    tr.syntaxError(this.optionalKeyword.toString() + " after " + this.restKeyword + " or " + this.keyKeyword);
                } else {
                    opt_args = 0;
                }
                Object bindings3 = pair2.getCdr();
                bindings = pair2.getCdr();
            }
        }
        tr.syntaxError(this.optionalKeyword.toString() + " after " + this.restKeyword + " or " + this.keyKeyword);
    }

    private static void addParam(Declaration decl, ScopeExp templateScope, LambdaExp lexp, Translator tr) {
        if (templateScope != null) {
            decl = tr.makeRenamedAlias(decl, templateScope);
        }
        lexp.addDeclaration(decl);
        if (templateScope != null) {
            decl.context = templateScope;
        }
    }

    public Object rewriteAttrs(LambdaExp lexp, Object body, Translator tr) {
        String accessFlagName = null;
        String allocationFlagName = null;
        int accessFlag = 0;
        int allocationFlag = 0;
        SyntaxForm syntax0 = null;
        while (true) {
            if (!(body instanceof SyntaxForm)) {
                if (body instanceof Pair) {
                    Pair pair1 = (Pair) body;
                    Object attrName = Translator.stripSyntax(pair1.getCar());
                    if (!tr.matches(attrName, "::")) {
                        if (!(attrName instanceof Keyword)) {
                            break;
                        }
                    } else {
                        attrName = null;
                    }
                    SyntaxForm syntax1 = syntax0;
                    Object pair1_cdr = pair1.getCdr();
                    while (pair1_cdr instanceof SyntaxForm) {
                        syntax1 = (SyntaxForm) pair1_cdr;
                        pair1_cdr = syntax1.getDatum();
                    }
                    if (!(pair1_cdr instanceof Pair)) {
                        break;
                    }
                    Pair pair2 = (Pair) pair1_cdr;
                    if (attrName == null) {
                        if (!lexp.isClassMethod() || !"*init*".equals(lexp.getName())) {
                            lexp.body = new LangExp(new Object[]{pair2, syntax1});
                        } else {
                            tr.error('e', "explicit return type for '*init*' method");
                        }
                    } else if (attrName == object.accessKeyword) {
                        Expression attrExpr = tr.rewrite_car(pair2, syntax1);
                        if (attrExpr instanceof QuoteExp) {
                            Object attrValue = ((QuoteExp) attrExpr).getValue();
                            if ((attrValue instanceof SimpleSymbol) || (attrValue instanceof CharSequence)) {
                                if (lexp.nameDecl == null) {
                                    tr.error('e', "access: not allowed for anonymous function");
                                } else {
                                    String value = attrValue.toString();
                                    if ("private".equals(value)) {
                                        accessFlag = 16777216;
                                    } else if ("protected".equals(value)) {
                                        accessFlag = Declaration.PROTECTED_ACCESS;
                                    } else if ("public".equals(value)) {
                                        accessFlag = Declaration.PUBLIC_ACCESS;
                                    } else if ("package".equals(value)) {
                                        accessFlag = Declaration.PACKAGE_ACCESS;
                                    } else {
                                        tr.error('e', "unknown access specifier");
                                    }
                                    if (!(accessFlagName == null || value == null)) {
                                        tr.error('e', "duplicate access specifiers - " + accessFlagName + " and " + value);
                                    }
                                    accessFlagName = value;
                                }
                            }
                        }
                        tr.error('e', "access: value not a constant symbol or string");
                    } else if (attrName == object.allocationKeyword) {
                        Expression attrExpr2 = tr.rewrite_car(pair2, syntax1);
                        if (attrExpr2 instanceof QuoteExp) {
                            Object attrValue2 = ((QuoteExp) attrExpr2).getValue();
                            if ((attrValue2 instanceof SimpleSymbol) || (attrValue2 instanceof CharSequence)) {
                                if (lexp.nameDecl == null) {
                                    tr.error('e', "allocation: not allowed for anonymous function");
                                } else {
                                    String value2 = attrValue2.toString();
                                    if ("class".equals(value2) || "static".equals(value2)) {
                                        allocationFlag = 2048;
                                    } else if ("instance".equals(value2)) {
                                        allocationFlag = 4096;
                                    } else {
                                        tr.error('e', "unknown allocation specifier");
                                    }
                                    if (!(allocationFlagName == null || value2 == null)) {
                                        tr.error('e', "duplicate allocation specifiers - " + allocationFlagName + " and " + value2);
                                    }
                                    allocationFlagName = value2;
                                }
                            }
                        }
                        tr.error('e', "allocation: value not a constant symbol or string");
                    } else if (attrName == object.throwsKeyword) {
                        Object attrValue3 = pair2.getCar();
                        int count = Translator.listLength(attrValue3);
                        if (count < 0) {
                            tr.error('e', "throws: not followed by a list");
                        } else {
                            Expression[] exps = new Expression[count];
                            SyntaxForm syntax2 = syntax1;
                            for (int i = 0; i < count; i++) {
                                while (attrValue3 instanceof SyntaxForm) {
                                    syntax2 = (SyntaxForm) attrValue3;
                                    attrValue3 = syntax2.getDatum();
                                }
                                Pair pair3 = (Pair) attrValue3;
                                exps[i] = tr.rewrite_car(pair3, syntax2);
                                Translator.setLine(exps[i], (Object) pair3);
                                attrValue3 = pair3.getCdr();
                            }
                            lexp.setExceptions(exps);
                        }
                    } else if (attrName == nameKeyword) {
                        Expression attrExpr3 = tr.rewrite_car(pair2, syntax1);
                        if (attrExpr3 instanceof QuoteExp) {
                            lexp.setName(((QuoteExp) attrExpr3).getValue().toString());
                        }
                    } else {
                        tr.error('w', "unknown procedure property " + attrName);
                    }
                    body = pair2.getCdr();
                } else {
                    break;
                }
            } else {
                syntax0 = (SyntaxForm) body;
                body = syntax0.getDatum();
            }
        }
        int accessFlag2 = accessFlag | allocationFlag;
        if (accessFlag2 != 0) {
            lexp.nameDecl.setFlag((long) accessFlag2);
        }
        if (syntax0 != null) {
            return SyntaxForms.fromDatumIfNeeded(body, syntax0);
        }
        return body;
    }

    public Object skipAttrs(LambdaExp lexp, Object body, Translator tr) {
        while (body instanceof Pair) {
            Pair pair = (Pair) body;
            if (!(pair.getCdr() instanceof Pair)) {
                break;
            }
            Object attrName = pair.getCar();
            if (!tr.matches(attrName, "::")) {
                if (!(attrName instanceof Keyword)) {
                    break;
                }
            }
            body = ((Pair) pair.getCdr()).getCdr();
        }
        return body;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:50:0x01cc, code lost:
        if ((r22 instanceof java.lang.Class) == false) goto L_0x0230;
     */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x020f  */
    /* JADX WARNING: Removed duplicated region for block: B:65:? A[RETURN, SYNTHETIC] */
    public void rewriteBody(LambdaExp lexp, Object body, Translator tr) {
        int opt_args;
        int numRenamedAlias = 0;
        if (tr.curMethodLambda == null && lexp.nameDecl != null && tr.getModule().getFlag(131072)) {
            tr.curMethodLambda = lexp;
        }
        ScopeExp currentScope = tr.currentScope();
        tr.pushScope(lexp);
        Declaration prev = null;
        int key_args = lexp.keywords == null ? 0 : lexp.keywords.length;
        if (lexp.defaultArgs == null) {
            opt_args = 0;
        } else {
            opt_args = lexp.defaultArgs.length - key_args;
        }
        int arg_i = 0;
        int opt_i = 0;
        Declaration cur = lexp.firstDecl();
        while (cur != null) {
            if (cur.isAlias()) {
                Declaration param = Translator.getOriginalRef(cur).getBinding();
                lexp.replaceFollowing(prev, param);
                param.context = lexp;
                tr.pushRenamedAlias(cur);
                numRenamedAlias++;
                cur = param;
            }
            Expression texp = cur.getTypeExp();
            if (texp instanceof LangExp) {
                cur.setType(tr.exp2Type((Pair) ((LangExp) texp).getLangValue()));
            }
            prev = cur;
            if (arg_i >= lexp.min_args && (arg_i < lexp.min_args + opt_args || lexp.max_args >= 0 || arg_i != lexp.min_args + opt_args)) {
                lexp.defaultArgs[opt_i] = tr.rewrite(lexp.defaultArgs[opt_i]);
                opt_i++;
            }
            arg_i++;
            tr.lexical.push(cur);
            cur = cur.nextDecl();
        }
        if (lexp.isClassMethod() && !lexp.nameDecl.getFlag(2048)) {
            lexp.add(null, new Declaration((Object) ThisExp.THIS_NAME));
        }
        LambdaExp saveLambda = tr.curLambda;
        tr.curLambda = lexp;
        Type rtype = lexp.returnType;
        if (lexp.body instanceof LangExp) {
            Object[] tform = (Object[]) ((LangExp) lexp.body).getLangValue();
            rtype = tr.getLanguage().getTypeFor(tr.rewrite_car((Pair) tform[0], (SyntaxForm) tform[1]));
        }
        lexp.body = tr.rewrite_body(body);
        tr.curLambda = saveLambda;
        if (lexp.body instanceof BeginExp) {
            Expression[] exps = ((BeginExp) lexp.body).getExpressions();
            int len = exps.length;
            if (len > 1) {
                if (!(exps[0] instanceof ReferenceExp)) {
                    Object val = exps[0].valueIfConstant();
                    if (!(val instanceof Type)) {
                    }
                }
                Expression rexp = exps[0];
                int len2 = len - 1;
                if (len2 == 1) {
                    lexp.body = exps[1];
                } else {
                    Expression[] new_body = new Expression[len2];
                    System.arraycopy(exps, 1, new_body, 0, len2);
                    lexp.body = BeginExp.canonicalize(new_body);
                }
                lexp.setCoercedReturnValue(rexp, tr.getLanguage());
                tr.pop(lexp);
                lexp.countDecls();
                tr.popRenamedAlias(numRenamedAlias);
                lexp.countDecls();
                if (tr.curMethodLambda != lexp) {
                    tr.curMethodLambda = null;
                    return;
                }
                return;
            }
        }
        lexp.setCoercedReturnType(rtype);
        tr.pop(lexp);
        lexp.countDecls();
        tr.popRenamedAlias(numRenamedAlias);
        lexp.countDecls();
        if (tr.curMethodLambda != lexp) {
        }
    }

    public void print(Consumer out) {
        out.write("#<builtin lambda>");
    }
}
