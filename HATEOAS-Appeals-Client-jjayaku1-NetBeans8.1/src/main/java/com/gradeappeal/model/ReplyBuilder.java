/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.model;

/**
 *
 * @author jayaprakashjayakumar
 */
public class ReplyBuilder {
    
    private String from="instructor@asu.edu";
    private String to="jjayaku1@asu.edu";
    private String subject="Reply : APPEAL - Midterm";
    private InstructorComments resp=null;
    private Grade grade=null;
    private AppealStatus status = null;
    
    public static ReplyBuilder email() {
        return new ReplyBuilder();
    }
    
     public Reply build(InstructorComments resp, Grade grade) {
           return new Reply(from, to, subject, resp, grade, status);
    }
    
    public ReplyBuilder withStatus(AppealStatus status)
    {
        this.status=status;
        return this;
    }
    
    public ReplyBuilder withEval(InstructorComments resp)
    {
       this.resp=resp;
        return this;
    }
    
    public ReplyBuilder withToEmail(String name) {
        this.to = name;
        return this;
    }
    
    public ReplyBuilder withFromEmail(String name) {
        this.from = name;
        return this;
    }
    
    public ReplyBuilder withSubject(String subject) {
        this.subject = subject;
        return this;
    }
    
     public ReplyBuilder withGrade(Grade grade) {
        this.grade = grade;
        return this;
    }

}
