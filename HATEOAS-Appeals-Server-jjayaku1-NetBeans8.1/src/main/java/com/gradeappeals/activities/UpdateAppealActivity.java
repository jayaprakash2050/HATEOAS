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

/**
 *
 * @author jayaprakashjayakumar
 */
public class UpdateAppealActivity {
    public AppealRepresentation update(Appeal appeal, AppealsUri appealUri) {
        Identifier appealIdentifier = appealUri.getId();
        AppealRepository repository = AppealRepository.current();
        if (AppealRepository.current().appealNotPlaced(appealIdentifier)) { 
            throw new NoSuchAppealException();
        }

        if (!appealCanBeChanged(appealIdentifier)) {
            throw new UpdateException();
        }

        Appeal storedAppeal = repository.get(appealIdentifier);
        
       
        storedAppeal.setStatus(storedAppeal.getStatus());
        storedAppeal.setComments(appeal.getComments());
        storedAppeal.setGrade(appeal.getGrade());
        return AppealRepresentation.createResponseAppealRepresentation(storedAppeal, appealUri); 
    }
    
    private boolean appealCanBeChanged(Identifier identifier) {
        return AppealRepository.current().get(identifier).getStatus() == AppealStatus.CREATED;
    }
}
