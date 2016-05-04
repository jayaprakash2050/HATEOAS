/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.model;

import java.util.Random;

/**
 *
 * @author jayaprakashjayakumar
 */
public class CommentBuilder {
    public static CommentBuilder comment() {
        return new CommentBuilder();
    }

   private InstructorComments insCom=InstructorComments.NOEXPLANATION;
   private StudentComments stuCom=StudentComments.COMPARISONSGIVEN;
    
    public Comment build() {
        return new Comment(insCom, stuCom);
    }
    

    public CommentBuilder withTeacherCom(InstructorComments insCom) {
        this.insCom = insCom;
        return this;
    }
    
    public CommentBuilder withStudentCom(StudentComments stuCom) {
        this.stuCom = stuCom;
        return this;
    }

    public CommentBuilder random() {
        Random r = new Random();
        insCom = InstructorComments.values()[r.nextInt(InstructorComments.values().length)];
        stuCom = StudentComments.values()[r.nextInt(StudentComments.values().length)];
        return this;
    }
}
