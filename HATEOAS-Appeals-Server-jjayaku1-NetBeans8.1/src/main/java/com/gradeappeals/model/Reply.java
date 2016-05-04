/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeals.model;

import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jayaprakashjayakumar
 */
public class Reply {
    private String from;
    private String to;
    private String subject;
    private InstructorComments resp;
    private Grade grade;
    @XmlTransient
    private AppealStatus status;
    
    public Reply(String from, String to, String subject, InstructorComments resp, Grade grade, AppealStatus status) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.resp=resp;
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

    public InstructorComments getResp() {
        return this.resp;
    }

    public void setResp(InstructorComments resp) {
        this.resp = resp;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String toEmail) {
        this.to = to;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    
     
    
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nREPLY\n");
        sb.append("From Email: " + from + "\n");
        sb.append("To Email: " + to + "\n");
        sb.append("Subject: " + subject + "\n");
        sb.append("Instructor Comments: "+resp+"\n");
        sb.append("Grade:" + grade + "\n");
        sb.append("Status: " + status + "\n");
        return sb.toString();
    }
}
