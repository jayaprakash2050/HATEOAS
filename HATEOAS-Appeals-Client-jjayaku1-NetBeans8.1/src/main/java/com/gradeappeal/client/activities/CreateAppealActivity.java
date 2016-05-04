/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.client.activities;

import com.gradeappeal.client.main.ClientAppeal;
import com.gradeappeal.model.Appeal;
import com.gradeappeal.representations.AppealRepresentation;
import java.net.URI;

/**
 *
 * @author jayaprakashjayakumar
 */
public class CreateAppealActivity extends Activity {
    private Appeal appeal;

    public void create(Appeal appeal, URI appealingUri) {        
        try {
            AppealRepresentation createdAppealRepresentation = binding.createAppeal(appeal, appealingUri);
            this.actions = new RepresentationHypermediaProcessor().extractNextActionsFromAppealRepresentation(createdAppealRepresentation);
            this.appeal = createdAppealRepresentation.getAppeal();
        } catch (MalformedAppealException | ServiceFailureException e) {
            this.actions = retryCurrentActivity();
        }
    }
    
    public ClientAppeal getAppeal() {
        return new ClientAppeal(appeal);
    }
}
