/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jayaprakashjayakumar
 */
public class EmailBuilder {
    
    private String from="jjayaku1@asu.edu";
    private String to="instructor@asu.edu";
    private String subject="APPEAL - Midterm1";
    private ArrayList<Comment> comments = null;
    private Grade grade=null;
    private AppealStatus status = AppealStatus.CREATED;
    
    public static EmailBuilder email() {
        return new EmailBuilder();
    }
    
     public Email build(List<Comment> comments, Grade grade) {
           return new Email(from, to, subject, comments,grade, status);
    }
    
    public EmailBuilder withStatus(AppealStatus status)
    {
        this.status=status;
        return this;
    }
    
    public EmailBuilder withQuestion(Comment comment)
    {
        if(comments == null) {
            comments = new ArrayList<Comment>();
        }
        comments.add(comment);
        return this;
    }
    
    public EmailBuilder withToEmail(String name) {
        this.to = name;
        return this;
    }
    
    public EmailBuilder withFromEmail(String name) {
        this.from = name;
        return this;
    }
    
    public EmailBuilder withSubject(String subject) {
        this.subject = subject;
        return this;
    }
    
     public EmailBuilder withGrade(Grade grade) {
        this.grade = grade;
        return this;
    }

}
