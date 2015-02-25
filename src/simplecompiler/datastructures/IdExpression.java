/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplecompiler.datastructures;

public class IdExpression extends Expression {

    final String id;

    public IdExpression(String id) {
        this.id = id;
    }

    @Override
    public Result interpretate(VariableTable t) {
        int value = 0;
        if (t != null) {
            value = t.get(id);
        }

        return new Result(value, t);
    }
}
