/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplecompiler.parser.grammar;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marco
 */
public class ContextFreeGrammar {

    private final List<Rule> rules;

    public ContextFreeGrammar() {
        this.rules = new ArrayList<>();
    }

    public void addRule(Rule rule) {
        rules.add(rule);
    }

    public void printGrammar() {
        for (int i = 0; i < rules.size(); i++) {
            Rule r = rules.get(i);
            System.out.print((i + 1) + ": " + r.left.id + " -> ");
            for (Symbol s : r.right) {
                System.out.print(s.id + " ");
            }
            System.out.println("");
        }
    }
}
