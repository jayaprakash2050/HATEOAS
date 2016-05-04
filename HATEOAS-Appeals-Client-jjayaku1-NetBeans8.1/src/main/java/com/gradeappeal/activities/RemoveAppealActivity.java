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
public class RemoveAppealActivity {
    public AppealRepresentation delete(AppealsUri appealUri) {
      
        
        Identifier identifier = appealUri.getId();

        AppealRepository appealRepository = AppealRepository.current();

        if (appealRepository.appealNotPlaced(identifier)) {
            throw new NoSuchAppealException();
        }

        Appeal appeal = appealRepository.get(identifier);

      
        if (appeal.getStatus() == AppealStatus.CLOSED || appeal.getStatus() == AppealStatus.ACCEPT || appeal.getStatus() == AppealStatus.REJECT) {
            throw new AppealDeletionException();
        }

        if(appeal.getStatus() == AppealStatus.CREATED  || appeal.getStatus() == AppealStatus.CANCELLED) {  
            appealRepository.remove(identifier);
        }

        return new AppealRepresentation(appeal);
    }
}
