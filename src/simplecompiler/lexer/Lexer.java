/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplecompiler.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import simplecompiler.lexer.tokens.Token;
import simplecompiler.lexer.tokens.TokenFactory;
import simplecompiler.lexer.tokens.TokenWithData;

/**
 *
 * @author Marco
 */
public class Lexer {

    private final TokenFactory tf;
    private List<TokenWithData> lexedTokens;

    /**
     * TODO
     */
    public Lexer() {
        tf = new TokenFactory();
    }

    /**
     * TODO
     *
     * @param str
     */
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

        /*
         // Test with bigRegExp
         while (leftToLex.length() != 0) {
         Matcher m = tf.getBigPattern().matcher(leftToLex);
         m.find();
         System.out.println("bigRegExp:");
         System.out.println(tf.getBigPattern().pattern());
         //            System.out.println("(m.group(" + 0 + ")): " + m.group(0));
         //            System.out.println("(m.group(" + 1 + ")): " + m.group(1)); // Error token
         // Find next biggest token
         int tokenlength = 0;
         int tokenIndex = 1; // Error token
         for (int i = 2; i < m.groupCount(); i++) {
         String group = m.group(i);
         if (group != null && group.length() > tokenlength) {
         tokenlength = group.length();
         tokenIndex = i;
         }
         //                System.out.println(tokens.get(i - 2).NAME + " (m.group(" + i + ")): " + m.group(i));
         }
         if (tokenIndex > 1) { // NO ERROR
         TokenWithData token = new TokenWithData(tokens.get(tokenIndex - 2), m.group(tokenIndex), tokenlength);
         // TODO check ignore
         lexedTokens.add(token);
         //                System.out.println(token);
         // Trim
         leftToLex = leftToLex.substring(tokenlength);
         // Trim while keeping track of line numbering
         int s = tokenlength;
         while (s > 0) { // Add spaces
         errorArrow = errorArrow.substring(0, errorArrow.length() - 1) + " ^";
         s--;
         }
         } else { // ERROR
         String errorMessage = "An error occured on line " + currentLine + ", unexpected token '" + leftToLex.charAt(0) + "':\n";
         errorMessage += lines[currentLine - 1] + '\n';
         errorMessage += errorArrow + '\n';
         Logger.getLogger(Lexer.class.getName()).log(Level.SEVERE, errorMessage);
         leftToLex = leftToLex.substring(1);
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
         // End of test
         */
        while (leftToLex.length() != 0) {
            // TODO just check longest match during while loop
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
                    matches.add(new TokenWithData(tokens.get(i), m.group(1), m.group(1).length(), currentLine));
//                    System.out.println(tokens.get(i).NAME + ": " + m.group(1));
                }
                i++;
            }

            TokenWithData longestMatch = matches.peek();
            if (longestMatch == null || longestMatch.token.name.equals("ERROR")) { // ERROR!
                // TODO Exception?
                String errorMessage = "An error occured on line " + currentLine + ", unexpected token '" + leftToLex.charAt(0) + "':\n";
                errorMessage += lines[currentLine - 1] + '\n';
                errorMessage += errorArrow + '\n';
                Logger.getLogger(Lexer.class.getName()).log(Level.SEVERE, errorMessage);
                //                System.out.println(errorMessage);
                leftToLex = leftToLex.substring(1);
                errorArrow = errorArrow.substring(0, errorArrow.length() - 1) + " ^";
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

    }

    /**
     * TODO
     *
     * @return
     */
    public List<TokenWithData> getLexedTokens() {
        return this.lexedTokens;
    }

    private void printLexedString() {
        for (TokenWithData token : lexedTokens) {
            System.out.print(token + " ");
        }
        System.out.println("");
    }

    public static void main(String[] args) {
        Lexer lexer = new Lexer();
//        String prog = "a := 7 ; b := c + (d := 5 + 6 , d ) $";
        String prog = "float match0(char *s) /* some comment */\n"
                + "{if (!strncmp(s, \"0.0\", 3))\n"
                + "\treturn 0.0;\n"
                + "}$\n";
        System.out.println("Lexing program:\n--------------------------------------\n" + prog + "\n--------------------------------------\n");
        lexer.lexString(prog);
        lexer.printLexedString();
    }
}
