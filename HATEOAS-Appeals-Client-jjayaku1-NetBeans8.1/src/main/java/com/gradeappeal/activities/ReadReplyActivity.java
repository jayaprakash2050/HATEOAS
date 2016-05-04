/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.activities;

import com.gradeappeal.model.AppealStatus;
import com.gradeappeal.model.Email;
import com.gradeappeal.model.Grade;
import com.gradeappeal.model.Identifier;
import com.gradeappeal.model.InstructorComments;
import com.gradeappeal.model.Reply;
import com.gradeappeal.repositories.EmailRepository;
import com.gradeappeal.representations.AppealsUri;
import com.gradeappeal.representations.ReplyRepresentation;

/**
 *
 * @author jayaprakashjayakumar
 */
public class ReadReplyActivity {
    public ReplyRepresentation readReply(AppealsUri replyUri) {
    
        Identifier identifier  = replyUri.getId();
        
        Email email = EmailRepository.current().get(identifier);
        
        InstructorComments resp=null;
        AppealStatus status=null;
        do
        {
        int randno = (int)(Math.random()*AppealStatus.values().length);
        status = AppealStatus.values()[randno];
        }while(status!=AppealStatus.ACCEPT && status!=AppealStatus.REJECT);
        
        
        Grade grade=null;
        if(status==AppealStatus.ACCEPT)
        {
          do
           {
           grade = Grade.values()[(int)(Math.random()*Grade.values().length)];
           }while(grade.toString().compareTo(email.getGrade().toString())>-1);
           resp=InstructorComments.GRADEGIVEN;
        }
        else
        {
            grade=email.getGrade();
            int randno = (int)(Math.random()*InstructorComments.values().length);
            if(InstructorComments.GRADEGIVEN == InstructorComments.values()[randno]){
                resp = InstructorComments.IMPROPERCOMPARISON;
            }
            else{
                resp=InstructorComments.values()[randno];
            }
            
        }
        Reply reply=new Reply(email.getTo(),email.getFrom(),email.getSubject(),resp,grade,status);
                    
        return ReplyRepresentation.createResponseReplyRepresentation(reply, replyUri);
    }
}

