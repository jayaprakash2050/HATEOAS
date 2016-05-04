/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.activities;

import com.gradeappeal.model.Email;
import com.gradeappeal.model.Identifier;
import com.gradeappeal.repositories.EmailRepository;
import com.gradeappeal.representations.EmailRepresentation;
import com.gradeappeal.representations.AppealsUri;

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
