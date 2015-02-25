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
public class Result {

    final int i;
    final VariableTable t;

    public Result(int i, VariableTable t) {
        this.i = i;
        this.t = t;
    }
}
