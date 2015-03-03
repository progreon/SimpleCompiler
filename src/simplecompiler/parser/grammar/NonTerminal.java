/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplecompiler.parser.grammar;

public class NonTerminal extends Symbol {
    
    public final String type;

    NonTerminal(String type) {
        super(type);
        this.type = type;
    }
}
