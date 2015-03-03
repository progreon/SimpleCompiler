/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplecompiler.parser.grammar;

import java.util.HashMap;
import java.util.Map;
import simplecompiler.lexer.tokens.TokenFactory;

/**
 *
 * @author marco
 */
public class SymbolFactory {

    public static final String NT_S = "S";
    public static final String NT_E = "E";
    public static final String NT_L = "L";

    public static final String T_SEMI = TokenFactory.TOKEN_SEMI;
    public static final String T_ID = TokenFactory.TOKEN_ID;
    public static final String T_ASSIGN = TokenFactory.TOKEN_ASSIGN;
    public static final String T_PRINT = TokenFactory.TOKEN_PRINT; // TODO
    public static final String T_LPAREN = TokenFactory.TOKEN_LPAREN;
    public static final String T_RPAREN = TokenFactory.TOKEN_RPAREN;
    public static final String T_NUM = TokenFactory.TOKEN_NUM;
    public static final String T_PLUS = TokenFactory.TOKEN_PLUS;
    public static final String T_COMMA = TokenFactory.TOKEN_COMMA;

    private final Map<String, Terminal> terminals;
    private final Map<String, NonTerminal> nonTerminals;

    public SymbolFactory(TokenFactory tokenFactory) {
        this.terminals = new HashMap<>();
        this.nonTerminals = new HashMap<>();

        // Fill non-terminals:
        nonTerminals.put(NT_S, new NonTerminal(NT_S));
        nonTerminals.put(NT_E, new NonTerminal(NT_E));
        nonTerminals.put(NT_L, new NonTerminal(NT_L));

        // Fill terminals:
        terminals.put(T_SEMI, new Terminal(tokenFactory.getToken(T_SEMI)));
        terminals.put(T_ID, new Terminal(tokenFactory.getToken(T_ID)));
        terminals.put(T_ASSIGN, new Terminal(tokenFactory.getToken(T_ASSIGN)));
        terminals.put(T_PRINT, new Terminal(tokenFactory.getToken(T_PRINT)));
        terminals.put(T_LPAREN, new Terminal(tokenFactory.getToken(T_LPAREN)));
        terminals.put(T_RPAREN, new Terminal(tokenFactory.getToken(T_RPAREN)));
        terminals.put(T_NUM, new Terminal(tokenFactory.getToken(T_NUM)));
        terminals.put(T_PLUS, new Terminal(tokenFactory.getToken(T_PLUS)));
        terminals.put(T_COMMA, new Terminal(tokenFactory.getToken(T_COMMA)));
    }

    public Terminal getTerminal(String terminalId) {
        return terminals.get(terminalId);
    }

    public NonTerminal getNonTerminal(String nonTerminalId) {
        return nonTerminals.get(nonTerminalId);
    }

}
