/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeals.activities;

import com.gradeappeals.model.Appeal;
import com.gradeappeals.model.Identifier;
import com.gradeappeals.repositories.AppealRepository;
import com.gradeappeals.representations.AppealRepresentation;
import com.gradeappeals.representations.AppealsUri;

/**
 *
 * @author jayaprakashjayakumar
 */
public class ReadAppealActivity {
    public AppealRepresentation retrieveByUri(AppealsUri appealUri) {
        Identifier identifier  = appealUri.getId();
        
        Appeal appeal = AppealRepository.current().get(identifier);
        
        if(appeal == null) {
            throw new NoSuchAppealException();
        }
        
        return AppealRepresentation.createResponseAppealRepresentation(appeal, appealUri);
    }
}
