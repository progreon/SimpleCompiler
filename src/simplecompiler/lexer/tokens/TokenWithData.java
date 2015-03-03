/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplecompiler.lexer.tokens;

/**
 * TODO
 *
 * @author marco
 */
public class TokenWithData {

    public final Token token;
    public final String data;
    public final int length; // TODO without this!

    public final int lineNo;

    /**
     * TODO
     *
     * @param token
     * @param data
     * @param length
     * @param lineNo
     */
    public TokenWithData(Token token, String data, int length, int lineNo) {
        this.token = token;
        this.data = data;
        this.length = length;
        this.lineNo = lineNo;
    }

    /**
     * TODO
     *
     * @return
     */
    public Object getData() {
        Object convertedData = null;
        if (token.hasData) {
            if (token.c == Integer.class) {
                convertedData = Integer.parseInt(data);
            } else if (token.c == Long.class) {
                if (data.endsWith("l")) {
                    convertedData = Long.parseLong(data.substring(0, data.length() - 1));
                } else {
                    convertedData = Long.parseLong(data);
                }
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
        return token == null ? "null" : token.name + (token.hasData ? "(" + getData() + ")" : "");
//            return token == null ? "null" : token.NAME + "(" + getData() + ")";
    }
}
