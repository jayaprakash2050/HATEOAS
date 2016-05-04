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
public class UpdateAppealActivity extends Activity {
     private final URI updateUri;
    private AppealRepresentation updatedAppealRepresentation;

    public UpdateAppealActivity(URI updateUri) {
        this.updateUri = updateUri;
    }

    public void updateAppeal(Appeal appeal) {
        try {
            updatedAppealRepresentation = binding.updateAppeal(appeal, updateUri);
            actions = new RepresentationHypermediaProcessor().extractNextActionsFromAppealRepresentation(updatedAppealRepresentation);
       } catch (MalformedAppealException | ServiceFailureException e) {
            actions = retryCurrentActivity();
        } catch (NotFoundException e) {
            actions = noFurtherActivities();
        } catch (CannotUpdateAppealException e) {
            actions = noFurtherActivities();
        }
    }
    
    public ClientAppeal getAppeal() {
        return new ClientAppeal(updatedAppealRepresentation.getAppeal());
    }
}
