/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.repositories;

import com.gradeappeal.model.Email;
import com.gradeappeal.model.Identifier;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jayaprakashjayakumar
 */
public class EmailRepository {
    private static final Logger LOG = LoggerFactory.getLogger(EmailRepository.class);
    private static final EmailRepository theRepository = new EmailRepository();
    private HashMap<String, Email> backingStore = new HashMap<>();

    public static EmailRepository current() {
        return theRepository;
    }
    
    private EmailRepository(){
        LOG.debug("EmailRepository Constructor");
    }
    
    public Email get(Identifier identifier) {
        LOG.debug("Retrieving Email object for identifier {}", identifier);
        return backingStore.get(identifier.toString());
    }
    
    public Email take(Identifier identifier) {
        LOG.debug("Removing the Email object for identifier {}", identifier);
        Email email = backingStore.get(identifier.toString());
        remove(identifier);
        return email;
    }

    public Identifier store(Email email) {
        LOG.debug("Storing a new Email object");        
        Identifier id = new Identifier();     
        LOG.debug("New Email object id is {}", id);
        backingStore.put(id.toString(), email);
        return id;
    }
    
    public void store(Identifier emailIdentifier, Email email) {
        LOG.debug("Storing again the email object with id", emailIdentifier);
        backingStore.put(emailIdentifier.toString(), email);
    }

    public boolean has(Identifier identifier) {
        LOG.debug("Checking to see if there is an Email object associated with the id {} in the Order store", identifier);
        boolean result =  backingStore.containsKey(identifier.toString());
        LOG.debug("The result of the search is {}", result);
        return result;
    }

    public void remove(Identifier identifier) {
        LOG.debug("Removing from storage the Email object with id", identifier);
        backingStore.remove(identifier.toString());
    }
    
    public boolean emailPlaced(Identifier identifier) {
        LOG.debug("Checking to see if the email with id = {} has been sent", identifier);
        return EmailRepository.current().has(identifier);
    }
    
    public boolean emailNotPlaced(Identifier identifier) {
        LOG.debug("Checking to see if the email with id = {} has not been place", identifier);
        return !emailPlaced(identifier);
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, Email> entry : backingStore.entrySet()) {
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
