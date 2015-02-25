/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplecompiler.datastructures;

public class NumExpression extends Expression {

    final int num;

    public NumExpression(int num) {
        this.num = num;
    }

    @Override
    public Result interpretate(VariableTable t) {
        return new Result(num, t);
    }
}
