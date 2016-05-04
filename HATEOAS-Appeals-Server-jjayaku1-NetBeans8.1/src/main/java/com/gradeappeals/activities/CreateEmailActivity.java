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
import com.gradeappeals.representations.Link;
import com.gradeappeals.representations.Representation;

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
