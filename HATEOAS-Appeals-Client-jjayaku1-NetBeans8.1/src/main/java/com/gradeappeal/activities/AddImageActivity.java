/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.activities;


import com.gradeappeal.model.Identifier;
import com.gradeappeal.model.Image;
import com.gradeappeal.repositories.ImageRepository;
import com.gradeappeal.representations.AppealsUri;
import com.gradeappeal.representations.ImageRepresentation;
import com.gradeappeal.representations.Link;
import com.gradeappeal.representations.Representation;

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
