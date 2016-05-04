/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.client.activities;

import com.gradeappeal.representations.AppealRepresentation;

/**
 *
 * @author jayaprakashjayakumar
 */
public class RepresentationHypermediaProcessor {
    Actions extractNextActionsFromAppealRepresentation(AppealRepresentation representation) {
        Actions actions = new Actions();

        if (representation != null) {

            if (representation.getUpdateLink() != null) {
                actions.add(new UpdateAppealActivity(representation.getUpdateLink().getUri()));
            }

            if (representation.getSelfLink() != null) {
                actions.add(new ReadAppealActivity(representation.getSelfLink().getUri()));
            }

            if (representation.getCancelLink() != null) {
                actions.add(new CancelAppealActivity(representation.getCancelLink().getUri()));
            }
        }

        return actions;
    }
}
