/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.model;
import static com.gradeappeal.model.CommentBuilder.comment;
import java.util.ArrayList;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author jayaprakashjayakumar
 */
public class AppealBuilder {
    
    private static final Logger LOG = LoggerFactory.getLogger(AppealBuilder.class);
    
    public static AppealBuilder appeal() {
        return new AppealBuilder();
    }
    
    private ArrayList<Comment> comments = null;
    private AppealStatus status = AppealStatus.CREATED;
    private Grade grade=null;
    
    private void defaultItems() {
        LOG.debug("Executing AppealBuilder.defaultItems");
        ArrayList<Comment> comments = new ArrayList<Comment>();
        comments.add(comment().build());
        this.comments = comments;
    }
    
    private void corruptItems() {
        LOG.debug("Executing AppealBuilder.corruptItems");
        ArrayList<Comment> comments = new ArrayList<Comment>();
        comments.add(null);
        comments.add(null);
        comments.add(null);
        comments.add(null);
        this.comments = comments;
    }
   
    
    public Appeal build() {
        LOG.debug("Executing OrderBuilder.build");
        if(comments == null) {
            defaultItems();
        }
        return new Appeal(status, comments,grade);
    }

    public AppealBuilder withItem(Comment comment) {
        LOG.debug("Executing AppealBuilder.withItem");
        if(comments == null) {
            comments = new ArrayList<Comment>();
        }
        comments.add(comment);
        return this;
    }


    public AppealBuilder withCorruptedValues() {
        LOG.debug("Executing AppealBuilder.withCorruptedValues");
        corruptItems();
        return this;
    }
    
    public AppealBuilder withStatus(AppealStatus status) {
        LOG.debug("Executing AppealBuilder.withRandomItems");
        this.status = status;
        return this;
    }

    public AppealBuilder withRandom() {
        Random r = new Random();
        LOG.debug("Executing AppealBuilder.withRandomItems");
        int numberOfItems = (int) (System.currentTimeMillis() % 5 + 1);
        this.comments = new ArrayList<Comment>();
        for(int i = 0; i < numberOfItems; i++) {
            comments.add(comment().random().build());
        }
        do
        {
        grade = Grade.values()[r.nextInt(Grade.values().length)];
        }while(grade==Grade.A);
        
        return this;
    }
    
    public AppealBuilder withRandomItems(Grade grade) {
        LOG.debug("Executing AppealBuilder.withRandomItems");
        int numberOfItems = (int) (System.currentTimeMillis() % 5 + 1);
        this.comments = new ArrayList<Comment>();
        for(int i = 0; i < numberOfItems; i++) {
            comments.add(comment().random().build());
        }   
        this.grade=grade;
        return this;
    }

}
