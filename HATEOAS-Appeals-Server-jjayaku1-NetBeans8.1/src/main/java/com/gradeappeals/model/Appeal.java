/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeals.model;

import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jayaprakashjayakumar
 */
public class Appeal {

    private List<Comment> comments;

    @XmlTransient
    private Grade grade;
    @XmlTransient
    private AppealStatus status = AppealStatus.CREATED;

    public Appeal(List<Comment> comments, Grade grade) {
        this(AppealStatus.CREATED, comments, grade);
    }

    public Appeal(AppealStatus status, List<Comment> comments, Grade grade) {
        this.comments = comments;
        this.status = status;
        this.grade = grade;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setStatus(AppealStatus status) {
        this.status = status;
    }

    public AppealStatus getStatus() {
        return this.status;
    }

    public Grade getGrade() {
        return this.grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nAPPEAL\n");
        sb.append("Status: " + status + "\n");
        for (Comment c : comments) {
            sb.append("Comment: " + c.toString() + "\n");
        }
        sb.append("Grade: " + grade + "\n");
        return sb.toString();
    }

}
