/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplecompiler.datastructures;

/**
 *
 * @author Marco
 */
public class PrintStatement extends Statement {

    final ExpressionList exps;

    public PrintStatement(ExpressionList exps) {
        this.exps = exps;
    }

    @Override
    public Result interpretate(VariableTable t) {
        Result res = new Result(0, t);
        ExpressionList expList = exps;
        while (expList != null) {
            res = expList.head.interpretate(res.t);
            System.out.print(res.i + " ");
            expList = expList.tail;
        }
        System.out.print("\n");

        return res;
    }
}
