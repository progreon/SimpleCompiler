/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplecompiler.parser;

import java.util.List;
import simplecompiler.lexer.tokens.TokenFactory;
import simplecompiler.parser.grammar.ContextFreeGrammar;
import simplecompiler.parser.grammar.NonTerminal;
import simplecompiler.parser.grammar.Rule;
import simplecompiler.parser.grammar.Symbol;
import simplecompiler.parser.grammar.SymbolFactory;
import simplecompiler.parser.grammar.Terminal;

/**
 *
 * @author marco
 */
public class Parser {
    
    public final ContextFreeGrammar grammar;
    
    private final SymbolFactory sf;

    public Parser() {
        // Set up grammar:
        grammar = new ContextFreeGrammar();
        sf = new SymbolFactory(new TokenFactory());
        
        fillGrammar();
    }
    
    private void fillGrammar() {
        
        NonTerminal s = sf.getNonTerminal(SymbolFactory.NT_S);
        NonTerminal e = sf.getNonTerminal(SymbolFactory.NT_E);
        NonTerminal l = sf.getNonTerminal(SymbolFactory.NT_L);
        
        Terminal semi = sf.getTerminal(SymbolFactory.T_SEMI);
        Terminal id = sf.getTerminal(SymbolFactory.T_ID);
        Terminal assign = sf.getTerminal(SymbolFactory.T_ASSIGN);
        Terminal print = sf.getTerminal(SymbolFactory.T_PRINT);
        Terminal lparen = sf.getTerminal(SymbolFactory.T_LPAREN);
        Terminal rparen = sf.getTerminal(SymbolFactory.T_RPAREN);
        Terminal num = sf.getTerminal(SymbolFactory.T_NUM);
        Terminal plus = sf.getTerminal(SymbolFactory.T_PLUS);
        Terminal comma = sf.getTerminal(SymbolFactory.T_COMMA);
        
        grammar.addRule(new Rule(s, new Symbol[] {s, semi, s}));
        grammar.addRule(new Rule(s, new Symbol[] {id, assign, e}));
        grammar.addRule(new Rule(s, new Symbol[] {print, lparen, l, rparen}));
        grammar.addRule(new Rule(e, new Symbol[] {id}));
        grammar.addRule(new Rule(e, new Symbol[] {num}));
        grammar.addRule(new Rule(e, new Symbol[] {e, plus, e}));
        grammar.addRule(new Rule(e, new Symbol[] {lparen, s, comma, e, rparen}));
        grammar.addRule(new Rule(l, new Symbol[] {e}));
        grammar.addRule(new Rule(l, new Symbol[] {l, comma, e}));
    }
    
    public static void main(String[] args) {
        Parser parser = new Parser();
        parser.grammar.printGrammar();
        System.out.println("");
        parser.grammar.getNullableMap();
        System.out.println("Nullable set:");
        parser.grammar.printNullableMap();
        System.out.println("First set:");
        parser.grammar.printFirstMap();
        System.out.println("Follow set:");
        parser.grammar.printFollowMap();
    }
}
