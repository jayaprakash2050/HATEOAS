/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeals.activities;

import com.gradeappeals.model.AppealStatus;
import com.gradeappeals.model.Email;
import com.gradeappeals.model.Identifier;
import com.gradeappeals.repositories.EmailRepository;
import com.gradeappeals.representations.AppealsUri;
import com.gradeappeals.representations.EmailRepresentation;

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
