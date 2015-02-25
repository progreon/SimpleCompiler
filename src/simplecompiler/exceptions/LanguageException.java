/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplecompiler.exceptions;

/**
 *
 * @author Marco
 */
public class LanguageException extends RuntimeException {

    public LanguageException(String errorMessage) {
        super(errorMessage);
    }

}
