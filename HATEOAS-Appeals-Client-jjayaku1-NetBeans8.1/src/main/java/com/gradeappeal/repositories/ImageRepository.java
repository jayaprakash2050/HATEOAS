/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.repositories;

import com.gradeappeal.model.Identifier;
import com.gradeappeal.model.Image;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jayaprakashjayakumar
 */
public class ImageRepository {
    private static final Logger LOG = LoggerFactory.getLogger(ImageRepository.class);
    private static final ImageRepository theRepository = new ImageRepository();
    private HashMap<String, Image> backingStore = new HashMap<>();

    public static ImageRepository current() {
        return theRepository;
    }
    
    private ImageRepository(){
        LOG.debug("ImageRepository Constructor");
    }
    
    public Image get(Identifier identifier) {
        LOG.debug("Retrieving Image object for identifier {}", identifier);
        return backingStore.get(identifier.toString());
    }
    
    public Image take(Identifier identifier) {
        LOG.debug("Removing the Image object for identifier {}", identifier);
        Image image = backingStore.get(identifier.toString());
        remove(identifier);
        return image;
    }

    public Identifier store(Image image) {
        LOG.debug("Storing a new image object");        
        Identifier id = new Identifier();     
        LOG.debug("New image object id is {}", id);
        backingStore.put(id.toString(), image);
        return id;
    }
    
    public void store(Identifier imageIdentifier, Image image) {
        LOG.debug("Storing again the image object with id", imageIdentifier);
        backingStore.put(imageIdentifier.toString(), image);
    }

    public boolean has(Identifier identifier) {
        LOG.debug("Checking to see if there is an image object associated with the id {} in the Image store", identifier);
        boolean result =  backingStore.containsKey(identifier.toString());
        LOG.debug("The result of the search is {}", result);
        return result;
    }

    public void remove(Identifier identifier) {
        LOG.debug("Removing from storage the image object with id", identifier);
        backingStore.remove(identifier.toString());
    }
    
    public boolean imagePlaced(Identifier identifier) {
        LOG.debug("Checking to see if the image with id = {} has been sent", identifier);
        return ImageRepository.current().has(identifier);
    }
    
    public boolean imageNotPlaced(Identifier identifier) {
        LOG.debug("Checking to see if the image with id = {} has not been place", identifier);
        return !imagePlaced(identifier);
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, Image> entry : backingStore.entrySet()) {
            sb.append(entry.getKey());
            sb.append("\t:\t");
            sb.append(entry.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }

    public synchronized void clear() {
        backingStore = new HashMap<>();
    }
    
    public int size() {
        return backingStore.size();
    }
}
