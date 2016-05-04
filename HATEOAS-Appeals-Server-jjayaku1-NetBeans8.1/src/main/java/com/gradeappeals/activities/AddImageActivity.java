/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeals.activities;


import com.gradeappeals.model.Identifier;
import com.gradeappeals.model.Image;
import com.gradeappeals.repositories.ImageRepository;
import com.gradeappeals.representations.AppealsUri;
import com.gradeappeals.representations.ImageRepresentation;
import com.gradeappeals.representations.Link;
import com.gradeappeals.representations.Representation;

/**
 *
 * @author jayaprakashjayakumar
 */
public class AddImageActivity {
        public ImageRepresentation add(Image image, AppealsUri requestUri) {
        
        Identifier identifier = ImageRepository.current().store(image);

        
        AppealsUri emailUri = new AppealsUri(requestUri.getBaseUri() + "/image/" + identifier.toString());
        return new ImageRepresentation(image, 
                new Link(Representation.SELF_REL_VALUE, emailUri));
    }
}
