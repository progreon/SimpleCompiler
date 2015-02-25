/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplecompiler.lexer;

import com.sun.istack.internal.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Level;
import java.util.regex.Matcher;
import simplecompiler.lexer.tokens.Token;
import simplecompiler.lexer.tokens.TokenFactory;

/**
 *
 * @author Marco
 */
public class Lexer {

    private final TokenFactory tf;
    private List<TokenWithData> lexedTokens;

    public Lexer() {
        tf = new TokenFactory();
    }

    public void lexString(String str) {
        lexedTokens = new ArrayList<>();
        List<Token> tokens = tf.getTokens();
        String[] lines = str.split("\n");

        //// For error handling. ////
        int currentLine = 1;
        String errorArrow = "^";
        String leftToLex = str;
        // Trim while keeping track of line numbering
        while (leftToLex.length() != 0 && leftToLex.substring(0, 1).matches("\\s")) {
            if (leftToLex.charAt(0) == '\n') { // new line
                currentLine++;
                errorArrow = "^";
            } else if (leftToLex.charAt(0) == '\t') {
                errorArrow = "\t" + errorArrow;
            } else if (leftToLex.charAt(0) == ' ') {
                errorArrow = " " + errorArrow;
            }
            leftToLex = leftToLex.substring(1);
        }
        //// End error handling. ////

        // Test with bigRegExp
//        while (leftToLex.length() != 0) {
        Matcher m = tf.getBigPattern().matcher(str);
        m.find();
        System.out.println("bigRegExp:");
        System.out.println(tf.getBigPattern().pattern());
        System.out.println("(m.group(" + 0 + ")): " + m.group(0));
        System.out.println("(m.group(" + 1 + ")): " + m.group(1));
        for (int i = 2; i < m.groupCount(); i++) {
            System.out.println(tokens.get(i - 2).NAME + " (m.group(" + i + ")): " + m.group(i));
        }
//        }
        // End of test

        /*
        while (leftToLex.length() != 0) {
            Queue<TokenWithData> matches = new PriorityQueue<>((TokenWithData o1, TokenWithData o2) -> {
                int l1 = o1.length;
                int l2 = o2.length;
                return (l1 == l2 ? 0 : (l1 > l2 ? -1 : 1));
            });

            int i = 0;
            Matcher m;
            while (i < tokens.size()) {
                m = tokens.get(i).pattern.matcher(leftToLex);
                if (m.find()) {
                    matches.add(new TokenWithData(tokens.get(i), m.group(1), m.group(1).length()));
                    System.out.println(tokens.get(i).NAME + ": " + m.group(1));
                }
                i++;
            }

            TokenWithData longestMatch = matches.peek();
            if (longestMatch == null || longestMatch.token.NAME.equals("ERROR")) { // ERROR!
                // TODO Exception?
                String errorMessage = "An error occured on line " + currentLine + ", unexpected token '" + leftToLex.charAt(0) + "':\n";
                errorMessage += lines[currentLine - 1] + '\n';
                errorMessage += errorArrow + '\n';
                Logger.getLogger(Lexer.class).log(Level.SEVERE, errorMessage);
//                System.out.println(errorMessage);
            }
            if (longestMatch != null) {
                if (!longestMatch.token.ignore) {
                    lexedTokens.add(longestMatch);
                }
                leftToLex = leftToLex.substring(longestMatch.length);

                //// For error handling. ////
                // Trim while keeping track of line numbering
                int s = longestMatch.length;
                while (s > 0) { // Add spaces
                    errorArrow = errorArrow.substring(0, errorArrow.length() - 1) + " ^";
                    s--;
                }
                //// End error handling. ////
            }
            //// For error handling. ////
            // Trim while keeping track of line numbering
            while (leftToLex.length() != 0 && leftToLex.substring(0, 1).matches("\\s")) {
                if (leftToLex.charAt(0) == '\n') { // new line
                    currentLine++;
                    errorArrow = "^";
                } else if (leftToLex.charAt(0) == '\t') {
                    errorArrow = errorArrow.substring(0, errorArrow.length() - 1) + "\t^";
                } else if (leftToLex.charAt(0) == ' ') {
                    errorArrow = errorArrow.substring(0, errorArrow.length() - 1) + " ^";
                }
                leftToLex = leftToLex.substring(1);
            }
            //// End error handling. ////
        }
        */
    }

    public void printLexedString() {
        for (TokenWithData token : lexedTokens) {
            System.out.print(token + " ");
        }
        System.out.println("");
    }

    public class TokenWithData {

        final Token token;
        final String data;
        final int length;

        public TokenWithData(Token token, String data, int length) {
            this.token = token;
            this.data = data;
            this.length = length;
        }

        public Object getData() {
            Object convertedData = null;
            if (token.hasData) {
                if (token.c == Integer.class) {
                    convertedData = Integer.parseInt(data);
                } else if (token.c == Long.class) {
                    convertedData = Long.parseLong(data);
                } else if (token.c == Double.class) {
                    convertedData = Double.parseDouble(data);
                } else if (token.c == Float.class) {
                    convertedData = Float.parseFloat(data);
                } else if (token.c == Character.class) { // TODO check for escaped characters
                    if (data.length() == 3 && data.charAt(0) == '\'' && data.charAt(2) == '\'') {
                        convertedData = data.charAt(1);
                    } else {
                        convertedData = data;
                    }
                } else { // Keep it as a string
                    if (data.length() > 1 && data.charAt(0) == '"' && data.charAt(data.length() - 1) == '"') {
                        convertedData = data.substring(1, data.length() - 1);
                    } else {
                        convertedData = data;
                    }
                }
            }
            return convertedData;
        }

        @Override
        public String toString() {
            return token == null ? "null" : token.NAME + (token.hasData ? "(" + getData() + ")" : "");
//            return token == null ? "null" : token.NAME + "(" + getData() + ")";
        }
    }

    public static void main(String[] args) {
        Lexer lexer = new Lexer();
        String prog = "%a := 7 ; b := c + (d := 5 + 6 , d ) $";
//        String prog = "float match0(char *s) /* some comment */\n"
//                + "{if (!strncmp(s, \"\\\"0.0\\\"\", 3))\n"
//                + "\treturn 0.f;\n"
//                + "}$";
        System.out.println("Lexing program:\n--------------------------------------\n" + prog + "\n--------------------------------------\n");
        lexer.lexString(prog);
//        lexer.printLexedString();
    }
}
