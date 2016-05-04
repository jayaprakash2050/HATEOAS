/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeals.model;

/**
 *
 * @author jayaprakashjayakumar
 */
public class Image {
    private String from;
    private String to;
    private String fileName;

    public Image(String from, String to, String fileName) {
        this.from = from;
        this.to = to;
        this.fileName = fileName;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
        public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nIMAGE\n");
        sb.append("From: " + from + "\n");
        sb.append("To: " + to + "\n");
        sb.append("FileName: " + fileName + "\n");
        return sb.toString();
    }
    
}
