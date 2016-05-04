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
public class ImageBuilder {
    private String from="jjayaku1@asu.edu";
    private String to="instructor@asu.edu";
    private String fileName="APPEAL Request - Midterm1.jpg";
    
    public static ImageBuilder image() {
        return new ImageBuilder();
    }
    
     public Image build() {
           return new Image(from, to, fileName);
    }
    
    
    
    public ImageBuilder withTo(String name) {
        this.to = name;
        return this;
    }
    
    public ImageBuilder withFrom(String name) {
        this.from = name;
        return this;
    }
    
    
     public ImageBuilder withFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }
}
