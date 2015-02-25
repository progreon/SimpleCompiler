/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplecompiler.datastructures;

public class EseqExpression extends Expression {

    final Statement stm;
    final Expression exp;

    public EseqExpression(Statement stm, Expression exp) {
        this.stm = stm;
        this.exp = exp;
    }

    @Override
    public Result interpretate(VariableTable t) {
        Result res = stm.interpretate(t);
        res = exp.interpretate(res.t);

        return res;
    }
}
