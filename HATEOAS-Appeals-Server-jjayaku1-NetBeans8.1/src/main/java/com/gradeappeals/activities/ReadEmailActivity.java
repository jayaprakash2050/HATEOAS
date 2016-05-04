/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeals.activities;

import com.gradeappeals.model.Email;
import com.gradeappeals.model.Identifier;
import com.gradeappeals.repositories.EmailRepository;
import com.gradeappeals.representations.EmailRepresentation;
import com.gradeappeals.representations.AppealsUri;

/**
 *
 * @author jayaprakashjayakumar
 */
public class ReadEmailActivity {
    public EmailRepresentation retrieveByUri(AppealsUri appealUri) {
        Identifier identifier  = appealUri.getId();
        
        Email email = EmailRepository.current().get(identifier);
        
        if(email == null) {
            throw new NoSuchAppealException();
        }
        
        return EmailRepresentation.createResponseEmailRepresentation(email, appealUri);
    }
}
