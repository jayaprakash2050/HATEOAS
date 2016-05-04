/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeals.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jayaprakashjayakumar
 */
public class Email {
    private String from;
    private String to;
    private String subject;
    private List<Comment> comments;
    private Grade grade;
    @XmlTransient
    private AppealStatus status = AppealStatus.CREATED;
    
    public Email(String from, String to, String subject, List<Comment> comments, Grade grade, AppealStatus status) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.comments=comments;
        this.grade=grade;
        this.status = status;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public AppealStatus getStatus() {
        return status;
    }
    
    public void setStatus(AppealStatus status) {
        this.status = status;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
    
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nEMAIL\n");
        sb.append("From Email: " + from + "\n");
        sb.append("To Email: " + to + "\n");
        sb.append("Subject: " + subject + "\n");
        for(Comment com : comments) {
            sb.append("Comments: " + com.toString()+ "\n");
        }
        sb.append("Grade:" + grade + "\n");
        sb.append("Status: " + status + "\n");
        return sb.toString();
    }
}
