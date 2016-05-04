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
public class RemoveImageActivity {
    public ImageRepresentation delete(AppealsUri appealUri) {
      
        
        Identifier identifier = appealUri.getId();

        ImageRepository imageRepository = ImageRepository.current();

        if (imageRepository.imageNotPlaced(identifier)) {
            throw new NoSuchImageException();
        }

        Image image = imageRepository.get(identifier);

            imageRepository.remove(identifier);

        return new ImageRepresentation(image);
    }
}
