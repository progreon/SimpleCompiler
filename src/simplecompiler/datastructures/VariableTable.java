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
public class VariableTable {

    final String id;
    final int value;
    final VariableTable tail;

    public VariableTable(String id, int value, VariableTable tail) {
        this.id = id;
        this.value = value;
        this.tail = tail;
    }

    public int get(String id) {
        VariableTable t = this;
        while (t != null && !id.equals(t.id)) {
            t = t.tail;
        }
        if (t == null) {
            return 0;
        } else {
            return t.value;
        }
    }
}
