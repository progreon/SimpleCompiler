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
public class AssignStatement extends Statement {

    final String id;
    final Expression exp;

    public AssignStatement(String id, Expression exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public Result interpretate(VariableTable t) {
        Result res = exp.interpretate(t);
        res = new Result(res.i, new VariableTable(id, res.i, res.t));

        return res;
    }
}
