/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeals.activities;

import com.gradeappeals.model.Appeal;
import com.gradeappeals.model.AppealStatus;
import com.gradeappeals.model.Identifier;
import com.gradeappeals.repositories.AppealRepository;
import com.gradeappeals.representations.AppealRepresentation;
import com.gradeappeals.representations.AppealsUri;
import com.gradeappeals.representations.Link;
import com.gradeappeals.representations.Representation;

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
