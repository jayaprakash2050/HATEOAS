/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.activities;

import com.gradeappeal.model.Appeal;
import com.gradeappeal.model.AppealStatus;
import com.gradeappeal.model.Identifier;
import com.gradeappeal.repositories.AppealRepository;
import com.gradeappeal.representations.AppealRepresentation;
import com.gradeappeal.representations.AppealsUri;
import com.gradeappeal.representations.Link;
import com.gradeappeal.representations.Representation;

/**
 *
 * @author jayaprakashjayakumar
 */
public class CreateAppealActivity {
    public AppealRepresentation create(Appeal appeal, AppealsUri requestUri) {
        appeal.setStatus(AppealStatus.CREATED);
        Identifier identifier = AppealRepository.current().store(appeal);
      
        AppealsUri appealUri = new AppealsUri(requestUri.getBaseUri() + "/appeal/" + identifier.toString());
        AppealsUri emailUri = new AppealsUri(requestUri.getBaseUri() + "/email/" + identifier.toString());
        return new AppealRepresentation(appeal, 
                new Link(Representation.APPEALS_URI + "cancel", appealUri), 
                new Link(Representation.APPEALS_URI + "email", emailUri), 
                new Link(Representation.APPEALS_URI + "update", appealUri),
                new Link(Representation.APPEALS_URI + "close", appealUri),
                new Link(Representation.SELF_REL_VALUE, appealUri));
    }
    
}
