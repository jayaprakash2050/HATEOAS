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
import com.gradeappeal.representations.Link;
import com.gradeappeal.representations.Representation;

/**
 *
 * @author jayaprakashjayakumar
 */
public class CreateEmailActivity {
    public EmailRepresentation create(Email email, AppealsUri requestUri) {
        email.setStatus(AppealStatus.CREATED);
        
        Identifier identifier = EmailRepository.current().store(email);

        
        AppealsUri emailUri = new AppealsUri(requestUri.getBaseUri() + "/email/" + identifier.toString());
        AppealsUri replyUri = new AppealsUri(requestUri.getBaseUri() + "/reply/" + identifier.toString());
        return new EmailRepresentation(email, 
                new Link(Representation.APPEALS_URI + "reply", replyUri), 
                new Link(Representation.SELF_REL_VALUE, emailUri));
    }
}
