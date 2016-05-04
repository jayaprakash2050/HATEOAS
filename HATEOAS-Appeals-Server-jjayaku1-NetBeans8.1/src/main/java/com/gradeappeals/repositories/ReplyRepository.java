/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeals.repositories;

import com.gradeappeals.model.Identifier;
import com.gradeappeals.model.Reply;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jayaprakashjayakumar
 */
public class ReplyRepository {
    private static final Logger LOG = LoggerFactory.getLogger(ReplyRepository.class);
    private static final ReplyRepository theRepository = new ReplyRepository();
    private HashMap<String, Reply> backingStore = new HashMap<String, Reply>();

    public static ReplyRepository current() {
        return theRepository;
    }
    
    private ReplyRepository(){
     LOG.debug("ReplyRepository Constructor");
    }
    
    public Reply get(Identifier identifier) {
        LOG.debug("Retrieving Reply object for identifier {}", identifier);
        return backingStore.get(identifier.toString());
    }
    
    public Reply take(Identifier identifier) {
        LOG.debug("Removing the Reply object for identifier {}", identifier);
        Reply reply = backingStore.get(identifier.toString());
        remove(identifier);
        return reply;
    }

    public Identifier store(Reply reply) {
        LOG.debug("Storing a new Reply object");        
        Identifier id = new Identifier();     
        LOG.debug("New Reply object id is {}", id);
        backingStore.put(id.toString(), reply);
        return id;
    }
    
    public void store(Identifier identifier, Reply reply) {
        LOG.debug("Storing again the reply object with id", identifier);
        backingStore.put(identifier.toString(), reply);
    }

    public boolean has(Identifier identifier) {
        LOG.debug("Checking to see if there is an Reply object associated with the id {} in the Reply store", identifier);
        boolean result =  backingStore.containsKey(identifier.toString());
        LOG.debug("The result of the search is {}", result);
        return result;
    }

    public void remove(Identifier identifier) {
         LOG.debug("Removing from storage the Reply object with id", identifier);
        backingStore.remove(identifier.toString());
    }
    
    public boolean replyPlaced(Identifier identifier) {
        LOG.debug("Checking to see if the reply with id = {} has been place", identifier);
        return ReplyRepository.current().has(identifier);
    }
    
    public boolean replyNotPlaced(Identifier identifier) {
        LOG.debug("Checking to see if the reply with id = {} has not been place", identifier);
        return !replyPlaced(identifier);
    }
    
    public String toString() {
        LOG.info("ReplyRepository toString() method");
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, Reply> entry : backingStore.entrySet()) {
            sb.append(entry.getKey());
            sb.append("\t:\t");
            sb.append(entry.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }

    public synchronized void clear() {
        backingStore = new HashMap<String, Reply>();
    }
    
    public int size() {
        return backingStore.size();
    }
}
