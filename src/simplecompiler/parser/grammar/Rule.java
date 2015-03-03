/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplecompiler.parser.grammar;

/**
 *
 * @author marco
 */
public class Rule {
    
    public final NonTerminal left;
    public final Symbol[] right;

    public Rule(NonTerminal left, Symbol[] right) {
        this.left = left;
        this.right = right;
    }
    
}
