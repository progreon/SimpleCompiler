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
public class CompoundStatement extends Statement {

    final Statement stm1, stm2;

    public CompoundStatement(Statement stm1, Statement stm2) {
        this.stm1 = stm1;
        this.stm2 = stm2;
    }

    @Override
    public Result interpretate(VariableTable t) {
        Result res = stm1.interpretate(t);
        res = stm2.interpretate(res.t);

        return res;
    }
}
