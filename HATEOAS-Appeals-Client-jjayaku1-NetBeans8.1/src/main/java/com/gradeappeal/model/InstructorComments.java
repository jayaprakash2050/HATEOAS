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
public enum InstructorComments {
    @XmlEnumValue(value="Answer is completely irrelevant to the question asked")
    IRRELEVANTANSWER,
    @XmlEnumValue(value = "Comparisons are not proper")
    IMPROPERCOMPARISON,
    @XmlEnumValue(value = "Refer the slides for proper answer") 
    REFERSLIDES,
    @XmlEnumValue(value = "Answer is partially correct, so grade has been reduced accordingly")
    PARTIALGRADE,
    @XmlEnumValue(value = "Clear explanation is not given")
    NOEXPLANATION,
    @XmlEnumValue(value = "Grades given")
    GRADEGIVEN;
}
