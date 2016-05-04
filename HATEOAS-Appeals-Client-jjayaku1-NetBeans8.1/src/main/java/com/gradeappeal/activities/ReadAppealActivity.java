/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.activities;

import com.gradeappeal.model.Appeal;
import com.gradeappeal.model.Identifier;
import com.gradeappeal.repositories.AppealRepository;
import com.gradeappeal.representations.AppealRepresentation;
import com.gradeappeal.representations.AppealsUri;

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
