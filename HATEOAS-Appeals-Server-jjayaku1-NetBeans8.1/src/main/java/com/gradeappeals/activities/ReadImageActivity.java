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
