/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.client.activities;

import java.net.URI;

/**
 *
 * @author jayaprakashjayakumar
 */
public class CancelAppealActivity extends Activity {

    private final URI cancelUri;

    public CancelAppealActivity(URI cancelUri) {
        this.cancelUri = cancelUri;
    }

    public void cancelAppeal() {
        try {           
            binding.deleteAppeal(cancelUri);
            actions = noFurtherActivities();
      
        } catch (ServiceFailureException e) {
            actions = retryCurrentActivity();
        } catch (CannotCancelException | NotFoundException e) {
            actions = noFurtherActivities();
        }
    }
}
