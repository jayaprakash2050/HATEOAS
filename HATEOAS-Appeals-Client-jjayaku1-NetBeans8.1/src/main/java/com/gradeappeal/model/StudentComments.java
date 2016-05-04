/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.model;

import javax.xml.bind.annotation.XmlEnumValue;

/**
 *
 * @author jayaprakashjayakumar
 */
public enum StudentComments {
    @XmlEnumValue(value="Answer is continued at the last page of the answersheet")
    ANSCONTINUED,
    @XmlEnumValue(value = "Comparisons are given at the back")
    COMPARISONSGIVEN,
    @XmlEnumValue(value = "Answer is written according to the slides given") 
    ANSCORRECT,
    @XmlEnumValue(value = "Answer is paritally correct, expecting partial grade")
    PARTIALCORRECT,
    @XmlEnumValue(value = "Explainations and justifications are given correctly")
    JUSTIFIED; 
    
}
