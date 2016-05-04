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

/**
 *
 * @author jayaprakashjayakumar
 */
public class ReadImageActivity {
    public ImageRepresentation retrieveByUri(AppealsUri appealUri) {
        Identifier identifier  = appealUri.getId();
        
        Image image = ImageRepository.current().get(identifier);
        
        if(image == null) {
            throw new NoSuchImageException();
        }
        
        return ImageRepresentation.createResponseImageRepresentation(image, appealUri);
    }
}
