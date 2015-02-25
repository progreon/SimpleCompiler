/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplecompiler.lexer.tokens;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Marco
 */
public class TokenFactory {

    private final String separators;
    private final List<Token> tokens;

    private String bigRegExp = "";
    private Pattern bigPattern = null;

    public TokenFactory() {
        System.out.println("Initializing tokens ...");
        separators = "[\\s(),\\-+*/\\^\\$!]"; // operators,...
        System.out.println("Used separators:\n\t" + separators);
        System.out.println("Used tokens:");
        tokens = new ArrayList<>();

        // Tokens definiëren ...
        // TODO performantie verbeteren door maximum lengte bij te houden
        // en niet te lexen wanneer die lengte kleiner is dan de huidige grootste gelexte lengte?
        //    '-> Dan ook de tokens gesorteerd toevoegen van groot naar klein (de max lengte)
        // Keywords
//        addRegExpToken("FUNCTION", "function");
        addRegExpToken("RETURN", "return");
        addRegExpToken("STRING", "string");
        addRegExpToken("DOUBLE", "double");
        addRegExpToken("FLOAT", "float");
//        addRegExpToken("LONG", "long");
        addRegExpToken("CHAR", "char");
//        addRegExpToken("VOID", "void");
        addRegExpToken("INT", "int");
        addRegExpToken("IF", "if");
        // Comments
        addRegExpToken("COMMENT", "(?:/\\*(?:.|\\n)*\\*/)|(?://.*)", true);
        // Variables
        addRegExpToken("ID", "[a-zA-Z](?:[a-zA-Z0-9_])*", String.class);
        addRegExpToken("NUM", "\\d+", Integer.class);
//        addRegExpToken("BIGNUM", "\\d+l", Long.class);
        addRegExpToken("REAL", "(?:\\d+\\.\\d*d?)|(?:\\d*\\.\\d+d?)|(?:\\d+d)", Double.class);
        addRegExpToken("BIGREAL", "(?:\\d+\\.\\d*f)|(?:\\d*\\.\\d+f)|(?:\\d+f)", Float.class);
        addRegExpToken("STRING", "\"(?:[^\\\\]|\\\\\"|\\\\n|\\\\t|\\\\\\\\)*?\"", String.class); // TODO
        addRegExpToken("CHARACTER", "'.'", Character.class);
        // Operators
        addRegExpToken("ASSIGN", ":="); // ASSIGN
//        addRegExpToken("PLUSEQ", "\\+=");   // PLUSEQ
//        addRegExpToken("MINEQ", "-=");  // MINEQ
//        addRegExpToken("STAREQ", "\\*=");   // STAREQ
//        addRegExpToken("DIVEQ", "/=");  // DIVEQ
//        addRegExpToken("MODEQ", "%=");  // MODEQ
//        addRegExpToken("LLESS", "<<");  // LSHIFT
//        addRegExpToken("GGREAT", ">>"); // RSHIFT
//        addRegExpToken("EQUALS", "=="); // EQUALS
//        addRegExpToken("NEQ", "!=");    // NOTEQUALS
//        addRegExpToken("LEQ", "<=");    // LESSOREQUALS
//        addRegExpToken("GEQ", ">=");    // GREATEROREQUALS
//        addRegExpToken("AND", "&&");    // AND
//        addRegExpToken("OR", "\\|\\|"); // OR
//        addRegExpToken("PLUSPLUS", "\\+\\+");   // PLUS
//        addRegExpToken("MINMIN", "--"); // PLUS
        addRegExpToken("PLUS", "\\+");  // PLUS
        addRegExpToken("MINUS", "-");   // MINUS
        addRegExpToken("STAR", "\\*");  // TIMES
        addRegExpToken("SLASH", "/");   // DIV
//        addRegExpToken("PERC", "%");    // MOD
        addRegExpToken("BANG", "!");    // BITNOT
//        addRegExpToken("AMP", "&");     // BITAND
//        addRegExpToken("PIPE", "\\|");  // BITOR
//        addRegExpToken("CARET", "\\^"); // BITXOR
//        addRegExpToken("LESS", "<");    // LESSTHAN
//        addRegExpToken("GREAT", ">");   // GREATERTHAN
        // ...
        // Grouping
        addRegExpToken("LPAREN", "\\(");
        addRegExpToken("RPAREN", "\\)");
        addRegExpToken("LBRACE", "\\{");
        addRegExpToken("RBRACE", "\\}");
//        addRegExpToken("LBRACK", "\\[");
//        addRegExpToken("RBRACK", "\\]");
        // Other
        addRegExpToken("COMMA", ",");
        addRegExpToken("SEMI", ";");
//        addRegExpToken("COLON", ":");
        addRegExpToken("EOF", "\\$");
        addRegExpToken("ERROR", ".", String.class, false);
        
        bigPattern = Pattern.compile("^(" + bigRegExp + ")" + separators + "*?.*");
    }
    
    public Pattern getBigPattern() {
        return this.bigPattern;
    }

    private boolean addRegExpToken(String name, String regExp) {
        return addRegExpToken(name, regExp, null);
    }

    private boolean addRegExpToken(String name, String regExp, Class c) {
        return addRegExpToken(name, regExp, c, false);
    }

    private boolean addRegExpToken(String name, String regExp, boolean ignore) {
        return addRegExpToken(name, regExp, null, ignore);
    }

    private boolean addRegExpToken(String name, String regExp, Class c, boolean ignore) {
        return addRegExpToken(name, regExp, c != null, c, ignore);
    }

    private boolean addRegExpToken(String name, String regExp, boolean hasData, Class c, boolean ignore) {
        System.out.println("\t" + name + ": " + regExp);
        tokens.add(new RegExpToken(name, "^(" + regExp + ")" + separators + "*", hasData, c, ignore));
        if (bigRegExp.length() != 0) {
            bigRegExp += "|";
        }
        bigRegExp += "(" + regExp + ")";
        return true;
    }

    public List<Token> getTokens() {
        return tokens;
    }
}
