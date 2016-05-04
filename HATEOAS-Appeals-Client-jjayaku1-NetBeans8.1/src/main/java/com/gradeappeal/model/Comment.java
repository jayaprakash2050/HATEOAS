/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.gradeappeal.representations.Representation;

/**
 *
 * @author jayaprakashjayakumar
 */
@XmlRootElement
public class Comment {

    @XmlElement(namespace = Representation.APPEALS_NAMESPACE)
    private InstructorComments instructorComments;
    @XmlElement(namespace = Representation.APPEALS_NAMESPACE)
    private StudentComments studentComments;

    public Comment() {
    }

    public Comment(InstructorComments instructorComments, StudentComments studentComments) {
        this.instructorComments = instructorComments;
        this.studentComments = studentComments;
    }

    public InstructorComments getInstructorComments() {
        return instructorComments;
    }

    public StudentComments getStudentComments() {
        return studentComments;
    }

    @Override
    public String toString() {
        return "Comment{" + "instructorComments=" + instructorComments + ", studentComments=" + studentComments + '}';
    }
    
    
    

}
