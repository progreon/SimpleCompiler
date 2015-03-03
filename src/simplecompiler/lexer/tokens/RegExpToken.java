/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplecompiler.lexer.tokens;

import java.util.regex.Pattern;

public class RegExpToken extends Token {

    public final String regExp;

    public RegExpToken(String name, String id, String regExp, boolean hasValue, boolean ignore) {
        super(name, id, Pattern.compile(regExp), hasValue, Object.class, ignore);
        this.regExp = regExp;
    }

    public RegExpToken(String name, String id, String regExp, boolean hasValue, Class c, boolean ignore) {
        super(name, id, Pattern.compile(regExp), hasValue, c, ignore);
        this.regExp = regExp;
    }

}
