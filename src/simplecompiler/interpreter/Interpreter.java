/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplecompiler.interpreter;

import simplecompiler.datastructures.*;

/**
 *
 * @author Marco
 */
public abstract class Interpreter {

    public static void interpretate(Statement stm) {
        stm.interpretate(null);
    }

    public static void main(String[] args) {
        Statement prog1
                = new CompoundStatement(new AssignStatement("a",
                                new OpExpression(new NumExpression(5), Binop.plus, new NumExpression(3))),
                        new CompoundStatement(new AssignStatement("b",
                                        new EseqExpression(new PrintStatement(new ExpressionList(new IdExpression("a"),
                                                                new ExpressionList(new OpExpression(new IdExpression("a"), Binop.minus,
                                                                                new NumExpression(1)), null))),
                                                new OpExpression(new NumExpression(10), Binop.times, new IdExpression("a")))),
                                new PrintStatement(new ExpressionList(new IdExpression("b"), null))));
        Interpreter.interpretate(prog1);

        Statement prog2
                = new CompoundStatement(
                        new AssignStatement("a", new NumExpression(8)),
                        new CompoundStatement(
                                new AssignStatement("aa",
                                        new OpExpression(new OpExpression(new IdExpression("a"), Binop.times, new NumExpression(10)),
                                                Binop.plus, new IdExpression("a"))),
                                new CompoundStatement(
                                        new AssignStatement("aaa", new OpExpression(new OpExpression(new IdExpression("aa"), Binop.times,
                                                                new NumExpression(10)),
                                                        Binop.plus, new IdExpression("a"))),
                                        new CompoundStatement(
                                                new AssignStatement("aaaa",
                                                        new OpExpression(new OpExpression(new IdExpression("aaa"), Binop.times,
                                                                        new NumExpression(10)),
                                                                Binop.plus, new IdExpression("a"))),
                                                new CompoundStatement(
                                                        new AssignStatement("aaaaa",
                                                                new OpExpression(new OpExpression(new IdExpression("aaaa"),
                                                                                Binop.times, new NumExpression(10)),
                                                                        Binop.plus, new IdExpression("a"))),
                                                        new CompoundStatement(
                                                                /* line 1 */
                                                                new PrintStatement(new ExpressionList(
                                                                                new IdExpression("a"),
                                                                                new ExpressionList(
                                                                                        new IdExpression("a"),
                                                                                        new ExpressionList(
                                                                                                new IdExpression("a"),
                                                                                                new ExpressionList(
                                                                                                        new IdExpression("a"),
                                                                                                        new ExpressionList(
                                                                                                                new IdExpression("a"),
                                                                                                                new ExpressionList(
                                                                                                                        new IdExpression("a"), null))))))),
                                                                /* line 2 */
                                                                new CompoundStatement(
                                                                        new PrintStatement(new ExpressionList(
                                                                                        new IdExpression("aa"),
                                                                                        new ExpressionList(
                                                                                                new IdExpression("aa"),
                                                                                                new ExpressionList(new IdExpression("aa"),
                                                                                                        new ExpressionList(new IdExpression("aa"), null))))),
                                                                        /* line 3 */
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new ExpressionList(
                                                                                                new IdExpression("aaa"),
                                                                                                new ExpressionList(new IdExpression("aaa"),
                                                                                                        new ExpressionList(new IdExpression("aaa"), null)))),
                                                                                /* line 4 */
                                                                                new CompoundStatement(
                                                                                        new PrintStatement(new ExpressionList(
                                                                                                        new IdExpression("aaaaa"),
                                                                                                        new ExpressionList(
                                                                                                                new IdExpression("aaaaa"), null))),
                                                                                        /* line 3 */
                                                                                        new CompoundStatement(
                                                                                                new PrintStatement(new ExpressionList(
                                                                                                                new IdExpression("aaa"),
                                                                                                                new ExpressionList(
                                                                                                                        new IdExpression("aaa"),
                                                                                                                        new ExpressionList(
                                                                                                                                new IdExpression("aaa"), null)))),
                                                                                                /* line 2 */
                                                                                                new CompoundStatement(
                                                                                                        new PrintStatement(new ExpressionList(
                                                                                                                        new IdExpression("aa"),
                                                                                                                        new ExpressionList(
                                                                                                                                new IdExpression("aa"),
                                                                                                                                new ExpressionList(
                                                                                                                                        new IdExpression("aa"),
                                                                                                                                        new ExpressionList(
                                                                                                                                                new IdExpression("aa"), null))))),
                                                                                                        /* line 1 */
                                                                                                        new PrintStatement(new ExpressionList(
                                                                                                                        new IdExpression("a"),
                                                                                                                        new ExpressionList(
                                                                                                                                new IdExpression("a"),
                                                                                                                                new ExpressionList(
                                                                                                                                        new IdExpression("a"),
                                                                                                                                        new ExpressionList(
                                                                                                                                                new IdExpression("a"),
                                                                                                                                                new ExpressionList(
                                                                                                                                                        new IdExpression("a"),
                                                                                                                                                        new ExpressionList(new IdExpression("a"), null)))))))
                                                                                                /* all done */
                                                                                                )))))))))));

        System.out.println("\n---------------------------\n");
        Interpreter.interpretate(prog2);
    }

}
