/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.activities;

import com.gradeappeal.model.AppealStatus;
import com.gradeappeal.model.Email;
import com.gradeappeal.model.Identifier;
import com.gradeappeal.repositories.EmailRepository;
import com.gradeappeal.representations.AppealsUri;
import com.gradeappeal.representations.EmailRepresentation;

/**
 *
 * @author jayaprakashjayakumar
 */
public class RemoveEmailActivity {
    public EmailRepresentation delete(AppealsUri appealUri) {
      
        
        Identifier identifier = appealUri.getId();

        EmailRepository emailRepository = EmailRepository.current();

        if (emailRepository.emailNotPlaced(identifier)) {
            throw new NoSuchEmailException();
        }

        Email email = emailRepository.get(identifier);

        if(email.getStatus() == AppealStatus.CREATED) {  
            emailRepository.remove(identifier);
        }

        return new EmailRepresentation(email);
    }
}
