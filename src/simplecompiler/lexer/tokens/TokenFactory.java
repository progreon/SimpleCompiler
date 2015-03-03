/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplecompiler.lexer.tokens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Marco
 */
public class TokenFactory {

    // TODO take this to another place! (and use Token instead of String?) (naar Token?)
    // Token names
    public static final String TOKEN_FUNCTION = "FUNCTION";
    public static final String TOKEN_RETURN = "RETURN";
    public static final String TOKEN_STRING = "STRING";
    public static final String TOKEN_DOUBLE = "DOUBLE";
    public static final String TOKEN_FLOAT = "FLOAT";
    public static final String TOKEN_PRINT = "PRINT"; // TODO temp!
    public static final String TOKEN_LONG = "LONG";
    public static final String TOKEN_CHAR = "CHAR";
    public static final String TOKEN_VOID = "VOID";
    public static final String TOKEN_INT = "INT";
    public static final String TOKEN_IF = "IF";
    public static final String TOKEN_COMMENT = "COMMENT";
    public static final String TOKEN_ID = "ID";
    public static final String TOKEN_BIGREAL = "BIGREAL";
    public static final String TOKEN_REAL = "REAL";
    public static final String TOKEN_BIGNUM = "BIGNUM";
    public static final String TOKEN_NUM = "NUM";
    public static final String TOKEN_CHARSEQ = "CHARSEQ";
    public static final String TOKEN_CHARACTER = "CHARACTER";
    public static final String TOKEN_ASSIGN = "ASSIGN";
    public static final String TOKEN_PLUSEQ = "PLUSEQ";
    public static final String TOKEN_MINEQ = "MINEQ";
    public static final String TOKEN_STAREQ = "STAREQ";
    public static final String TOKEN_DIVEQ = "DIVEQ";
    public static final String TOKEN_MODEQ = "MODEQ";
    public static final String TOKEN_LLESS = "LLESS";
    public static final String TOKEN_GGREAT = "GGREAT";
    public static final String TOKEN_EQUALS = "EQUALS";
    public static final String TOKEN_NEQ = "NEQ";
    public static final String TOKEN_LEQ = "LEQ";
    public static final String TOKEN_GEQ = "GEQ";
    public static final String TOKEN_AND = "AND";
    public static final String TOKEN_OR = "OR";
    public static final String TOKEN_PLUSPLUS = "PLUSPLUS";
    public static final String TOKEN_MINMIN = "MINMIN";
    public static final String TOKEN_PLUS = "PLUS";
    public static final String TOKEN_MINUS = "MINUS";
    public static final String TOKEN_STAR = "STAR";
    public static final String TOKEN_SLASH = "SLASH";
    public static final String TOKEN_PERC = "PERC";
    public static final String TOKEN_BANG = "BANG";
    public static final String TOKEN_AMP = "AMP";
    public static final String TOKEN_PIPE = "PIPE";
    public static final String TOKEN_CARET = "CARET";
    public static final String TOKEN_LESS = "LESS";
    public static final String TOKEN_GREAT = "GREAT";
    public static final String TOKEN_LPAREN = "LPAREN";
    public static final String TOKEN_RPAREN = "RPAREN";
    public static final String TOKEN_LBRACE = "LBRACE";
    public static final String TOKEN_RBRACE = "RBRACE";
    public static final String TOKEN_LBRACK = "LBRACK";
    public static final String TOKEN_RBRACK = "RBRACK";
    public static final String TOKEN_COMMA = "COMMA";
    public static final String TOKEN_SEMI = "SEMI";
    public static final String TOKEN_COLON = "COLON";
    public static final String TOKEN_EOF = "EOF";
    public static final String TOKEN_ERROR = "ERROR";
//    public static final String TOKEN_FUNCTION = "FUNCTION";

    private final String separators;
    private final List<Token> tokens;
    private final Map<String, Token> tokenMap;

//    private String bigRegExp = "";
//    private Pattern bigPattern = null;
    public TokenFactory() {
        System.out.println("Initializing tokens ...");
        separators = "[\\s\\(\\)\\{\\},\\-+\\*/\\^\\$!;]"; // TODO operators,...
//        System.out.println("Used separators:\n\t" + separators);
//        System.out.println("Used tokens:");
        tokens = new ArrayList<>();
        tokenMap = new HashMap<>();

        // Tokens definiëren ...
        // TODO performantie verbeteren door maximum lengte bij te houden
        // en niet te lexen wanneer die lengte kleiner is dan de huidige grootste gelexte lengte?
        //    '-> Dan ook de tokens gesorteerd toevoegen van groot naar klein (de max lengte)
        // Keywords
//        addRegExpToken(TOKEN_FUNCTION, "FUNCTION", "function");
        addRegExpToken(TOKEN_RETURN, "RETURN", "return");
        addRegExpToken(TOKEN_STRING, "STRING", "string");
        addRegExpToken(TOKEN_DOUBLE, "DOUBLE", "double");
        addRegExpToken(TOKEN_FLOAT, "FLOAT", "float");
        addRegExpToken(TOKEN_PRINT, "print", "print"); // TODO temp!
//        addRegExpToken(TOKEN_LONG, "LONG", "long");
        addRegExpToken(TOKEN_CHAR, "CHAR", "char");
//        addRegExpToken(TOKEN_VOID, "VOID", "void");
        addRegExpToken(TOKEN_INT, "INT", "int");
        addRegExpToken(TOKEN_IF, "if", "if");
        // Comments
        addRegExpToken(TOKEN_COMMENT, "comment", "(/\\*(.|\\n)*\\*/)|(//.*)", true);
        // Variables
        addRegExpToken(TOKEN_ID, "id", "[a-zA-Z]([a-zA-Z0-9_])*", String.class);
        addRegExpToken(TOKEN_BIGREAL, "bigreal", "(\\d+\\.\\d*f)|(\\d*\\.\\d+f)|(\\d+f)", Float.class);
        addRegExpToken(TOKEN_REAL, "real", "(\\d+\\.\\d*d?)|(\\d*\\.\\d+d?)|(\\d+d)", Double.class);
        addRegExpToken(TOKEN_BIGNUM, "bignum", "\\d+l", Long.class);
        addRegExpToken(TOKEN_NUM, "num", "\\d+", Integer.class);
        addRegExpToken(TOKEN_CHARSEQ, "string", "\"([^\\\\]|\\\\\"|\\\\n|\\\\t|\\\\\\\\)*?\"", String.class); // TODO
        addRegExpToken(TOKEN_CHARACTER, "char", "'.'", Character.class);
        // Operators
        addRegExpToken(TOKEN_ASSIGN, ":=", ":="); // ASSIGN
//        addRegExpToken(TOKEN_PLUSEQ, "+=", "\\+=");   // PLUSEQ
//        addRegExpToken(TOKEN_MINEQ, "-=", "-=");  // MINEQ
//        addRegExpToken(TOKEN_STAREQ, "*=", "\\*=");   // STAREQ
//        addRegExpToken(TOKEN_DIVEQ, "/=", "/=");  // DIVEQ
//        addRegExpToken(TOKEN_MODEQ, "%=", "%=");  // MODEQ
//        addRegExpToken(TOKEN_LLESS, "<<", "<<");  // LSHIFT
//        addRegExpToken(TOKEN_GGREAT, ">>", ">>"); // RSHIFT
//        addRegExpToken(TOKEN_EQUALS, "==", "=="); // EQUALS
//        addRegExpToken(TOKEN_NEQ, "!=", "!=");    // NOTEQUALS
//        addRegExpToken(TOKEN_LEQ, "<=", "<=");    // LESSOREQUALS
//        addRegExpToken(TOKEN_GEQ, ">=", ">=");    // GREATEROREQUALS
//        addRegExpToken(TOKEN_AND, "&&", "&&");    // AND
//        addRegExpToken(TOKEN_OR, "||", "\\|\\|"); // OR
//        addRegExpToken(TOKEN_PLUSPLUS, "++", "\\+\\+");   // PLUSPLUS
//        addRegExpToken(TOKEN_MINMIN, "--", "--"); // MINMIN
        addRegExpToken(TOKEN_PLUS, "+", "\\+");  // PLUS
        addRegExpToken(TOKEN_MINUS, "-", "\\-");   // MINUS
        addRegExpToken(TOKEN_STAR, "*", "\\*");  // TIMES
        addRegExpToken(TOKEN_SLASH, "/", "/");   // DIV
//        addRegExpToken(TOKEN_PERC, "%", "%");    // MOD
        addRegExpToken(TOKEN_BANG, "!", "!");    // BITNOT
//        addRegExpToken(TOKEN_AMP, "&", "&");     // BITAND
//        addRegExpToken(TOKEN_PIPE, "|", "\\|");  // BITOR
//        addRegExpToken(TOKEN_CARET, "^", "\\^"); // BITXOR
//        addRegExpToken(TOKEN_LESS, "<", "<");    // LESSTHAN
//        addRegExpToken(TOKEN_GREAT, ">", ">");   // GREATERTHAN
        // ...
        // Grouping
        addRegExpToken(TOKEN_LPAREN, "(", "\\(");
        addRegExpToken(TOKEN_RPAREN, ")", "\\)");
        addRegExpToken(TOKEN_LBRACE, "{", "\\{");
        addRegExpToken(TOKEN_RBRACE, "}", "\\}");
//        addRegExpToken(TOKEN_LBRACK, "[", "\\[");
//        addRegExpToken(TOKEN_RBRACK, "]", "\\]");
        // Other
        addRegExpToken(TOKEN_COMMA, ",", ",");
        addRegExpToken(TOKEN_SEMI, ";", ";");
//        addRegExpToken(TOKEN_COLON, ":");
        addRegExpToken(TOKEN_EOF, "$", "\\$");
        addRegExpToken(TOKEN_ERROR, ".", String.class, false); // Make sure this is the last token to define!

//        bigPattern = Pattern.compile("^(" + bigRegExp + ")" + separators + "*");
    }

    public List<Token> getTokens() {
        return tokens;
    }
    
    public Token getToken(String tokenName) {
        return tokenMap.get(tokenName);
    }

//    public Pattern getBigPattern() {
//        return this.bigPattern;
//    }
    private boolean addRegExpToken(String name, String regExp) {
        return addRegExpToken(name, null, regExp, null);
    }
    
    private boolean addRegExpToken(String name, String id, String regExp) {
        return addRegExpToken(name, id, regExp, null);
    }

    private boolean addRegExpToken(String name, String regExp, Class c) {
        return addRegExpToken(name, null, regExp, c, false);
    }

    private boolean addRegExpToken(String name, String id, String regExp, Class c) {
        return addRegExpToken(name, id, regExp, c, false);
    }

    private boolean addRegExpToken(String name, String regExp, boolean ignore) {
        return addRegExpToken(name, null, regExp, null, ignore);
    }

    private boolean addRegExpToken(String name, String id, String regExp, boolean ignore) {
        return addRegExpToken(name, id, regExp, null, ignore);
    }

    private boolean addRegExpToken(String name, String regExp, Class c, boolean ignore) {
        return addRegExpToken(name, null, regExp, c != null, c, ignore);
    }

    private boolean addRegExpToken(String name, String id, String regExp, Class c, boolean ignore) {
        return addRegExpToken(name, id, regExp, c != null, c, ignore);
    }

    private boolean addRegExpToken(String name, String id, String regExp, boolean hasData, Class c, boolean ignore) {
//        System.out.println("\t" + name + ": " + regExp);
        // Test to see if the token is a separator:
        Matcher m = Pattern.compile("^(" + separators + ")$").matcher(regExp.startsWith("\\") ? regExp.substring(1) : regExp);
        Token token;
        if (m.find()) { // The token is a separator!
            token = new RegExpToken(name, id, "^(" + regExp + ")", hasData, c, ignore);
        } else {
            token = new RegExpToken(name, id, "^(" + regExp + ")" + separators + "+", hasData, c, ignore);
        }
//        System.out.println("\t\t" + token.pattern.pattern());
        if (!tokenMap.containsKey(name)) {
            tokens.add(token);
            tokenMap.put(name, token);
            return true;
        } else {
            Logger.getLogger(TokenFactory.class.getName()).log(Level.WARNING, "Token {0} already exists!", name);
            return false;
        }
//        if (bigRegExp.length() != 0) {
//            bigRegExp += "|";
//        }
//        bigRegExp += "(" + regExp + ")";
    }
}
