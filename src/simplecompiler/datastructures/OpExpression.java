/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplecompiler.datastructures;

public class OpExpression extends Expression {

    final Expression left, right;
    final Binop oper;

    public OpExpression(Expression left, Binop oper, Expression right) {
        this.left = left;
        this.oper = oper;
        this.right = right;
    }

    @Override
    public Result interpretate(VariableTable t) {
        Result resLeft = left.interpretate(t);
        Result resRight = right.interpretate(t);
        int value;

        switch (oper) {
            case minus:
                value = resLeft.i - resRight.i;
                break;
            case plus:
                value = resLeft.i + resRight.i;
                break;
            case times:
                value = resLeft.i * resRight.i;
                break;
            case div:
                value = resLeft.i / resRight.i;
                break;
            default: // It should not come in here! TODO exception?
                value = 0;
                break;
        }

        return new Result(value, t);
    }
}
