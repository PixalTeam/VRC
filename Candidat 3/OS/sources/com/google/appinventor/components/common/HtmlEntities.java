package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlEntities {
    private static final Pattern HTML_ENTITY_PATTERN = Pattern.compile("&(#?[0-9a-zA-Z]+);");
    private static final Map<String, Character> lookup = new HashMap();

    static {
        lookup.put("Agrave", Character.valueOf(192));
        lookup.put("agrave", Character.valueOf(224));
        lookup.put("Aacute", Character.valueOf(193));
        lookup.put("aacute", Character.valueOf(225));
        lookup.put("Acirc", Character.valueOf(194));
        lookup.put("acirc", Character.valueOf(226));
        lookup.put("Atilde", Character.valueOf(195));
        lookup.put("atilde", Character.valueOf(227));
        lookup.put("Auml", Character.valueOf(196));
        lookup.put("auml", Character.valueOf(228));
        lookup.put("Aring", Character.valueOf(197));
        lookup.put("aring", Character.valueOf(229));
        lookup.put("AElig", Character.valueOf(198));
        lookup.put("aelig", Character.valueOf(230));
        lookup.put("Ccedil", Character.valueOf(199));
        lookup.put("ccedil", Character.valueOf(231));
        lookup.put("Egrave", Character.valueOf(200));
        lookup.put("egrave", Character.valueOf(232));
        lookup.put("Eacute", Character.valueOf(201));
        lookup.put("eacute", Character.valueOf(233));
        lookup.put("Ecirc", Character.valueOf(202));
        lookup.put("ecirc", Character.valueOf(234));
        lookup.put("Euml", Character.valueOf(203));
        lookup.put("euml", Character.valueOf(235));
        lookup.put("Igrave", Character.valueOf(204));
        lookup.put("igrave", Character.valueOf(236));
        lookup.put("Iacute", Character.valueOf(205));
        lookup.put("iacute", Character.valueOf(237));
        lookup.put("Icirc", Character.valueOf(206));
        lookup.put("icirc", Character.valueOf(238));
        lookup.put("Iuml", Character.valueOf(207));
        lookup.put("iuml", Character.valueOf(239));
        lookup.put("ETH", Character.valueOf(208));
        lookup.put("eth", Character.valueOf(240));
        lookup.put("Ntilde", Character.valueOf(209));
        lookup.put("ntilde", Character.valueOf(241));
        lookup.put("Ograve", Character.valueOf(210));
        lookup.put("ograve", Character.valueOf(242));
        lookup.put("Oacute", Character.valueOf(211));
        lookup.put("oacute", Character.valueOf(243));
        lookup.put("Ocirc", Character.valueOf(212));
        lookup.put("ocirc", Character.valueOf(244));
        lookup.put("Otilde", Character.valueOf(213));
        lookup.put("otilde", Character.valueOf(245));
        lookup.put("Ouml", Character.valueOf(214));
        lookup.put("ouml", Character.valueOf(246));
        lookup.put("Oslash", Character.valueOf(216));
        lookup.put("oslash", Character.valueOf(248));
        lookup.put("Ugrave", Character.valueOf(217));
        lookup.put("ugrave", Character.valueOf(249));
        lookup.put("Uacute", Character.valueOf(218));
        lookup.put("uacute", Character.valueOf(250));
        lookup.put("Ucirc", Character.valueOf(219));
        lookup.put("ucirc", Character.valueOf(251));
        lookup.put("Uuml", Character.valueOf(220));
        lookup.put("uuml", Character.valueOf(252));
        lookup.put("Yacute", Character.valueOf(221));
        lookup.put("yacute", Character.valueOf(253));
        lookup.put("THORN", Character.valueOf(222));
        lookup.put("thorn", Character.valueOf(254));
        lookup.put("szlig", Character.valueOf(223));
        lookup.put("yuml", Character.valueOf(255));
        lookup.put("Yuml", Character.valueOf(376));
        lookup.put("OElig", Character.valueOf(338));
        lookup.put("oelig", Character.valueOf(339));
        lookup.put("Scaron", Character.valueOf(352));
        lookup.put("scaron", Character.valueOf(353));
        lookup.put("Alpha", Character.valueOf(913));
        lookup.put("Beta", Character.valueOf(914));
        lookup.put("Gamma", Character.valueOf(915));
        lookup.put("Delta", Character.valueOf(916));
        lookup.put("Epsilon", Character.valueOf(917));
        lookup.put("Zeta", Character.valueOf(918));
        lookup.put("Eta", Character.valueOf(919));
        lookup.put("Theta", Character.valueOf(920));
        lookup.put("Iota", Character.valueOf(921));
        lookup.put("Kappa", Character.valueOf(922));
        lookup.put("Lambda", Character.valueOf(923));
        lookup.put("Mu", Character.valueOf(924));
        lookup.put("Nu", Character.valueOf(925));
        lookup.put("Xi", Character.valueOf(926));
        lookup.put("Omicron", Character.valueOf(927));
        lookup.put("Pi", Character.valueOf(928));
        lookup.put("Rho", Character.valueOf(929));
        lookup.put("Sigma", Character.valueOf(931));
        lookup.put("Tau", Character.valueOf(932));
        lookup.put("Upsilon", Character.valueOf(933));
        lookup.put("Phi", Character.valueOf(934));
        lookup.put("Chi", Character.valueOf(935));
        lookup.put("Psi", Character.valueOf(936));
        lookup.put("Omega", Character.valueOf(937));
        lookup.put("alpha", Character.valueOf(945));
        lookup.put("beta", Character.valueOf(946));
        lookup.put("gamma", Character.valueOf(947));
        lookup.put("delta", Character.valueOf(948));
        lookup.put("epsilon", Character.valueOf(949));
        lookup.put("zeta", Character.valueOf(950));
        lookup.put("eta", Character.valueOf(951));
        lookup.put("theta", Character.valueOf(952));
        lookup.put("iota", Character.valueOf(953));
        lookup.put("kappa", Character.valueOf(954));
        lookup.put("lambda", Character.valueOf(955));
        lookup.put("mu", Character.valueOf(956));
        lookup.put("nu", Character.valueOf(957));
        lookup.put("xi", Character.valueOf(958));
        lookup.put("omicron", Character.valueOf(959));
        lookup.put("pi", Character.valueOf(960));
        lookup.put("rho", Character.valueOf(961));
        lookup.put("sigmaf", Character.valueOf(962));
        lookup.put("sigma", Character.valueOf(963));
        lookup.put("tau", Character.valueOf(964));
        lookup.put("upsilon", Character.valueOf(965));
        lookup.put("phi", Character.valueOf(966));
        lookup.put("chi", Character.valueOf(967));
        lookup.put("psi", Character.valueOf(968));
        lookup.put("omega", Character.valueOf(969));
        lookup.put("thetasym", Character.valueOf(977));
        lookup.put("upsih", Character.valueOf(978));
        lookup.put("piv", Character.valueOf(982));
        lookup.put("iexcl", Character.valueOf(161));
        lookup.put("cent", Character.valueOf(162));
        lookup.put("pound", Character.valueOf(163));
        lookup.put("curren", Character.valueOf(164));
        lookup.put("yen", Character.valueOf(165));
        lookup.put("brvbar", Character.valueOf(166));
        lookup.put("sect", Character.valueOf(167));
        lookup.put("uml", Character.valueOf(168));
        lookup.put("copy", Character.valueOf(169));
        lookup.put("ordf", Character.valueOf(170));
        lookup.put("laquo", Character.valueOf(171));
        lookup.put("not", Character.valueOf(172));
        lookup.put("shy", Character.valueOf(173));
        lookup.put("reg", Character.valueOf(174));
        lookup.put("macr", Character.valueOf(175));
        lookup.put("deg", Character.valueOf(176));
        lookup.put("plusmn", Character.valueOf(177));
        lookup.put("sup2", Character.valueOf(178));
        lookup.put("sup3", Character.valueOf(179));
        lookup.put("acute", Character.valueOf(180));
        lookup.put("micro", Character.valueOf(181));
        lookup.put("para", Character.valueOf(182));
        lookup.put("middot", Character.valueOf(183));
        lookup.put("cedil", Character.valueOf(184));
        lookup.put("sup1", Character.valueOf(185));
        lookup.put("ordm", Character.valueOf(186));
        lookup.put("raquo", Character.valueOf(187));
        lookup.put("frac14", Character.valueOf(188));
        lookup.put("frac12", Character.valueOf(189));
        lookup.put("frac34", Character.valueOf(190));
        lookup.put("iquest", Character.valueOf(191));
        lookup.put("times", Character.valueOf(215));
        lookup.put("divide", Character.valueOf(247));
        lookup.put("fnof", Character.valueOf(402));
        lookup.put("circ", Character.valueOf(710));
        lookup.put("tilde", Character.valueOf(732));
        lookup.put("lrm", Character.valueOf(8206));
        lookup.put("rlm", Character.valueOf(8207));
        lookup.put("ndash", Character.valueOf(8211));
        lookup.put("endash", Character.valueOf(8211));
        lookup.put("mdash", Character.valueOf(8212));
        lookup.put("emdash", Character.valueOf(8212));
        lookup.put("lsquo", Character.valueOf(8216));
        lookup.put("rsquo", Character.valueOf(8217));
        lookup.put("sbquo", Character.valueOf(8218));
        lookup.put("ldquo", Character.valueOf(8220));
        lookup.put("rdquo", Character.valueOf(8221));
        lookup.put("bdquo", Character.valueOf(8222));
        lookup.put("dagger", Character.valueOf(8224));
        lookup.put("Dagger", Character.valueOf(8225));
        lookup.put("bull", Character.valueOf(8226));
        lookup.put("hellip", Character.valueOf(8230));
        lookup.put("permil", Character.valueOf(8240));
        lookup.put("prime", Character.valueOf(8242));
        lookup.put("Prime", Character.valueOf(8243));
        lookup.put("lsaquo", Character.valueOf(8249));
        lookup.put("rsaquo", Character.valueOf(8250));
        lookup.put("oline", Character.valueOf(8254));
        lookup.put("frasl", Character.valueOf(8260));
        lookup.put("euro", Character.valueOf(8364));
        lookup.put("image", Character.valueOf(8465));
        lookup.put("weierp", Character.valueOf(8472));
        lookup.put("real", Character.valueOf(8476));
        lookup.put("trade", Character.valueOf(8482));
        lookup.put("alefsym", Character.valueOf(8501));
        lookup.put("larr", Character.valueOf(8592));
        lookup.put("uarr", Character.valueOf(8593));
        lookup.put("rarr", Character.valueOf(8594));
        lookup.put("darr", Character.valueOf(8595));
        lookup.put("harr", Character.valueOf(8596));
        lookup.put("crarr", Character.valueOf(8629));
        lookup.put("lArr", Character.valueOf(8656));
        lookup.put("uArr", Character.valueOf(8657));
        lookup.put("rArr", Character.valueOf(8658));
        lookup.put("dArr", Character.valueOf(8659));
        lookup.put("hArr", Character.valueOf(8660));
        lookup.put("forall", Character.valueOf(8704));
        lookup.put("part", Character.valueOf(8706));
        lookup.put("exist", Character.valueOf(8707));
        lookup.put("empty", Character.valueOf(8709));
        lookup.put("nabla", Character.valueOf(8711));
        lookup.put("isin", Character.valueOf(8712));
        lookup.put("notin", Character.valueOf(8713));
        lookup.put("ni", Character.valueOf(8715));
        lookup.put("prod", Character.valueOf(8719));
        lookup.put("sum", Character.valueOf(8721));
        lookup.put("minus", Character.valueOf(8722));
        lookup.put("lowast", Character.valueOf(8727));
        lookup.put("radic", Character.valueOf(8730));
        lookup.put("prop", Character.valueOf(8733));
        lookup.put("infin", Character.valueOf(8734));
        lookup.put("ang", Character.valueOf(8736));
        lookup.put("and", Character.valueOf(8743));
        lookup.put("or", Character.valueOf(8744));
        lookup.put("cap", Character.valueOf(8745));
        lookup.put("cup", Character.valueOf(8746));
        lookup.put("int", Character.valueOf(8747));
        lookup.put("there4", Character.valueOf(8756));
        lookup.put("sim", Character.valueOf(8764));
        lookup.put("cong", Character.valueOf(8773));
        lookup.put("asymp", Character.valueOf(8776));
        lookup.put("ne", Character.valueOf(8800));
        lookup.put("equiv", Character.valueOf(8801));
        lookup.put("le", Character.valueOf(8804));
        lookup.put("ge", Character.valueOf(8805));
        lookup.put("sub", Character.valueOf(8834));
        lookup.put("sup", Character.valueOf(8835));
        lookup.put("nsub", Character.valueOf(8836));
        lookup.put("sube", Character.valueOf(8838));
        lookup.put("supe", Character.valueOf(8839));
        lookup.put("oplus", Character.valueOf(8853));
        lookup.put("otimes", Character.valueOf(8855));
        lookup.put("perp", Character.valueOf(8869));
        lookup.put("sdot", Character.valueOf(8901));
        lookup.put("lceil", Character.valueOf(8968));
        lookup.put("rceil", Character.valueOf(8969));
        lookup.put("lfloor", Character.valueOf(8970));
        lookup.put("rfloor", Character.valueOf(8971));
        lookup.put("lang", Character.valueOf(9001));
        lookup.put("rang", Character.valueOf(9002));
        lookup.put("loz", Character.valueOf(9674));
        lookup.put("spades", Character.valueOf(9824));
        lookup.put("clubs", Character.valueOf(9827));
        lookup.put("hearts", Character.valueOf(9829));
        lookup.put("diams", Character.valueOf(9830));
        lookup.put("gt", Character.valueOf('>'));
        lookup.put("GT", Character.valueOf('>'));
        lookup.put("lt", Character.valueOf('<'));
        lookup.put("LT", Character.valueOf('<'));
        lookup.put("quot", Character.valueOf('\"'));
        lookup.put("QUOT", Character.valueOf('\"'));
        lookup.put("amp", Character.valueOf('&'));
        lookup.put("AMP", Character.valueOf('&'));
        lookup.put("apos", Character.valueOf('\''));
        lookup.put("nbsp", Character.valueOf(160));
        lookup.put("ensp", Character.valueOf(8194));
        lookup.put("emsp", Character.valueOf(8195));
        lookup.put("thinsp", Character.valueOf(8201));
        lookup.put("zwnj", Character.valueOf(8204));
        lookup.put("zwj", Character.valueOf(8205));
    }

    public static Character toCharacter(String entityName) {
        return (Character) lookup.get(entityName);
    }

    public static String decodeHtmlText(String htmlText) {
        if (htmlText.length() == 0 || htmlText.indexOf(38) == -1) {
            return htmlText;
        }
        StringBuilder output = new StringBuilder();
        int lastMatchEnd = 0;
        Matcher matcher = HTML_ENTITY_PATTERN.matcher(htmlText);
        while (matcher.find()) {
            String entity = matcher.group(1);
            Character convertedEntity = null;
            if (entity.startsWith("#x")) {
                String hhhh = entity.substring(2);
                try {
                    System.out.println("hex number is " + hhhh);
                    convertedEntity = Character.valueOf((char) Integer.parseInt(hhhh, 16));
                } catch (NumberFormatException e) {
                }
            } else if (entity.startsWith("#")) {
                try {
                    convertedEntity = Character.valueOf((char) Integer.parseInt(entity.substring(1)));
                } catch (NumberFormatException e2) {
                }
            } else {
                convertedEntity = (Character) lookup.get(entity);
            }
            if (convertedEntity != null) {
                output.append(htmlText.substring(lastMatchEnd, matcher.start()));
                output.append(convertedEntity);
                lastMatchEnd = matcher.end();
            }
        }
        if (lastMatchEnd < htmlText.length()) {
            output.append(htmlText.substring(lastMatchEnd));
        }
        return output.toString();
    }
}
