/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplecompiler.parser.grammar;

import simplecompiler.lexer.tokens.Token;


public class Terminal extends Symbol {
    
    public final Token token;

    public Terminal(Token token) {
        super(token.id);
        this.token = token;
    }
}
