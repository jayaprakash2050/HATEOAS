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
