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
public class ExpressionList {

    final Expression head;
    final ExpressionList tail;
//    final boolean isLast;

    public ExpressionList(Expression head, ExpressionList tail) {
        this.head = head;
        this.tail = tail;
//        this.isLast = tail == null;
    }
}
