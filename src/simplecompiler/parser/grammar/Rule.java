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
        // TODO ??
        if (right == null) {
            this.right = new Symbol[0];
        } else {
            this.right = right;
        }
    }

    @Override
    public String toString() {
        String str = left.id + " ->";
        for (Symbol s : right) {
            str += " " + s.id;
        }
        return str; //To change body of generated methods, choose Tools | Templates.
    }

}
