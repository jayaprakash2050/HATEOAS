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
public class InvalidImageException extends RuntimeException {
    
    public InvalidImageException(Exception ex) {
        super(ex);
    }

    public InvalidImageException() {
    }
    
    private static final long serialVersionUID = -9208425088487285272L;
    
}
