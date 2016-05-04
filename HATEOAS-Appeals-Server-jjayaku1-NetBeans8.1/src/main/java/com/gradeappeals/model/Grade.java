/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeals.model;
import javax.xml.bind.annotation.XmlEnumValue;
/**
 *
 * @author jayaprakashjayakumar
 */
public enum Grade {
    @XmlEnumValue(value="A")
    A,
    @XmlEnumValue(value="B")
    B,
    @XmlEnumValue(value="C")
    C,
    @XmlEnumValue(value="D")
    D,
    @XmlEnumValue(value="E")
    E,
    @XmlEnumValue(value="F")
    F;    
}
