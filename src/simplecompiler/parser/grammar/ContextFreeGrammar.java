/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplecompiler.parser.grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * TODO
 *
 * @author marco
 */
public class ContextFreeGrammar {

    private final List<Rule> rules;
    private final List<NonTerminal> nonTerminals;
    private final List<Terminal> terminals;
    private NonTerminal startSymbol = null;

    private Map<NonTerminal, Boolean> nullableMap = null;
    private Map<Symbol, Set<Terminal>> firstMap = null;
    private Map<Symbol, Set<Terminal>> followMap = null;

    public ContextFreeGrammar() {
        this.rules = new ArrayList<>();
        this.nonTerminals = new ArrayList<>();
        this.terminals = new ArrayList<>();
    }

    public void addRule(Rule rule) {
        if (startSymbol == null) { // Just to be safe => say so in Javadoc!
            startSymbol = rule.left;
        }
        rules.add(rule);
        if (!nonTerminals.contains(rule.left)) {
            nonTerminals.add(rule.left);
        }
        for (Symbol s : rule.right) {
            if (s instanceof Terminal) {
                if (!terminals.contains((Terminal) s)) {
                    terminals.add((Terminal) s);
                }
            }
        }

        nullableMap = null;
        firstMap = null;
        followMap = null;
    }

    public void setStartSymbol(NonTerminal startSymbol) {
        this.startSymbol = startSymbol;
    }

    public NonTerminal getStartSymbol() {
        return startSymbol;
    }

    public void printGrammar() {
        for (int i = 0; i < rules.size(); i++) {
            Rule r = rules.get(i);
            System.out.println((i + 1) + ": " + r);
        }
    }

    public List<NonTerminal> getNonTerminals() {
        return this.nonTerminals;
    }

    public Map<NonTerminal, Boolean> getNullableMap() {
        if (nullableMap == null) {
            fillMaps();
        }
        return this.nullableMap;
    }

    public Map<Symbol, Set<Terminal>> getFirstMap() {
        if (firstMap == null) {
            fillMaps();
        }
        return this.firstMap;
    }

    public Map<Symbol, Set<Terminal>> getFollowMap() {
        if (followMap == null) {
            fillMaps();
        }
        return this.followMap;
    }

    public void printNullableMap() {
        if (nullableMap == null) {
            fillMaps();
        }
        for (Map.Entry<NonTerminal, Boolean> e : nullableMap.entrySet()) {
            System.out.println(e.getKey().id + " : " + e.getValue());
        }
    }

    public void printFirstMap() {
        if (firstMap == null) {
            fillMaps();
        }
        for (Map.Entry<Symbol, Set<Terminal>> e : firstMap.entrySet()) {
            if (e.getKey() instanceof NonTerminal) {
                System.out.print(e.getKey().id + " : ");
                for (Terminal t : e.getValue()) {
                    System.out.print(t.id + " ");
                }
                System.out.println("");
            }
        }
    }

    public void printFollowMap() {
        if (followMap == null) {
            fillMaps();
        }
        for (Map.Entry<Symbol, Set<Terminal>> e : followMap.entrySet()) {
            if (e.getKey() instanceof NonTerminal) {
                System.out.print(e.getKey().id + " : ");
                for (Terminal t : e.getValue()) {
                    System.out.print(t.id + " ");
                }
                System.out.println("");
            }
        }
    }

    private void fillMaps() {
        nullableMap = new HashMap<>();
        firstMap = new HashMap<>();
        followMap = new HashMap<>();

        // init
        for (NonTerminal nt : nonTerminals) {
            nullableMap.put(nt, Boolean.FALSE);
            firstMap.put(nt, new HashSet<>());
            followMap.put(nt, new HashSet<>());
        }
        for (Terminal t : terminals) {
            firstMap.put(t, new HashSet<>());
            firstMap.get(t).add(t);
            followMap.put(t, new HashSet<>());
        }
        for (Rule r : rules) {
            if (r.right.length > 0 && (r.right[0] instanceof Terminal)) {
                firstMap.get(r.left).add((Terminal) r.right[0]);
            }
        }

        boolean hasChanged = true;
        while (hasChanged) { // repeat
            hasChanged = false;
            for (Rule r : rules) { // foreach production X -> Y0 Y1 ... Yk-1
                int k = r.right.length;
                boolean[] nullables = new boolean[k];
                for (int i = 0; i < k; i++) {
                    nullables[i] = ((r.right[i] instanceof NonTerminal) && nullableMap.get((NonTerminal) r.right[i]));
                }

                // If all the Yi are nullable:
                boolean allIsNullable = true;
                for (int i = 0; i < k; i++) {
                    if (!nullables[i]) {
                        allIsNullable = false;
                    }
                }
                if (allIsNullable) {
                    if (!nullableMap.get(r.left)) {
                        hasChanged = true;
                    }
                    nullableMap.put(r.left, Boolean.TRUE);
                }

                for (int i = 0; i < k; i++) {
                    // if Y0..Yi-1 are all nullable:
                    boolean allToIIsNullable = true;
                    for (int j = 0; j < i; j++) {
                        if (!nullables[j]) {
                            allToIIsNullable = false;
                        }
                    }
                    if (allToIIsNullable && (r.right[i] instanceof Symbol)) {
                        for (Terminal t : firstMap.get((Symbol) r.right[i])) {
                            if (firstMap.get(r.left).add(t)) {
                                hasChanged = true;
                            }
                        }
                    }

                    // if Yi+1 .. Yk are all nullable:
                    boolean allFromIIsNullable = true;
                    for (int j = i + 1; j < k; j++) {
                        if (!nullables[j]) {
                            allFromIIsNullable = false;
                        }
                    }
                    if (allFromIIsNullable && (r.right[i] instanceof Symbol)) {
                        for (Terminal t : followMap.get(r.left)) {
                            if (followMap.get((Symbol) r.right[i]).add(t)) {
                                hasChanged = true;
                            }
                        }
                    }

                    for (int j = i + 1; j < k; j++) {
                        // if Yi+1 .. Yk are all nullable:
                        boolean allFromIToJIsNullable = true;
                        for (int l = i + 1; l < j; l++) {
                            if (!nullables[l]) {
                                allFromIToJIsNullable = false;
                            }
                        }
                        if (allFromIToJIsNullable && (r.right[i] instanceof Symbol) && (r.right[j] instanceof Symbol)) {
                            for (Terminal t : firstMap.get((Symbol) r.right[j])) {
                                if (followMap.get((Symbol) r.right[i]).add(t)) {
                                    hasChanged = true;
                                }
                            }
                        }
                    }
                }
            } // until first, follow and nullable maps did not change in this iteration
        }
    }

    private void reduceLeftRecursion() {

    }
}
