/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.client.activities;

/**
 *
 * @author jayaprakashjayakumar
 */
public class InvalidEmailException extends Exception {

    private static final long serialVersionUID = 5911896330951274617L;

    public InvalidEmailException(Exception ex) {
        super(ex);
    }

    public InvalidEmailException() {}

}