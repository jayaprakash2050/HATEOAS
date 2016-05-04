/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeals.activities;

/**
 *
 * @author jayaprakashjayakumar
 */
public class InvalidAppealException extends RuntimeException{
    public InvalidAppealException(Exception ex) {
        super(ex);
    }

    public InvalidAppealException() {}

    private static final long serialVersionUID = 2300194325533639524L;
}
