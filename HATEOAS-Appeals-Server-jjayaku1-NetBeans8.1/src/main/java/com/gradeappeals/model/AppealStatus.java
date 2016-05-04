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
public enum AppealStatus {
    @XmlEnumValue(value="Created")
    CREATED,
    //@XmlEnumValue(value="Submitted")
    //SUBMIT, 
    @XmlEnumValue(value="Accepted")
    ACCEPT, 
    @XmlEnumValue(value="Rejected")
    REJECT, 
    @XmlEnumValue(value="Cancelled")
    CANCELLED,
    @XmlEnumValue(value="Closed")
    CLOSED;
}
