/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplecompiler.lexer.tokens;

import java.util.regex.Pattern;

/**
 *
 * @author Marco
 */
public abstract class Token {

    public final String name;
    public final String id;
    public final Pattern pattern;
    public final boolean hasData;
    public final Class c;
    public final boolean ignore;

    Token(String name, String id, Pattern pattern, boolean hasValue, Class c, boolean ignore) {
        this.name = name;
        this.id = id;
        this.pattern = pattern;
        this.hasData = hasValue;
        this.c = c;
        this.ignore = ignore;
    }

}
