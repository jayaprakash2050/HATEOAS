/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.representations;

import com.gradeappeal.activities.InvalidAppealException;
import com.gradeappeal.model.Appeal;
import com.gradeappeal.model.AppealStatus;
import com.gradeappeal.model.Comment;
import com.gradeappeal.model.Grade;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.List;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author jayaprakashjayakumar
 */
@XmlRootElement(name = "appeal", namespace = Representation.APPEALS_NAMESPACE)
public class AppealRepresentation extends Representation{
    private static final Logger LOG = LoggerFactory.getLogger(AppealRepresentation.class);
    
    @XmlElement(name = "comment", namespace = Representation.APPEALS_NAMESPACE)
    private List<Comment> comments;
    @XmlElement(name = "grade", namespace = Representation.APPEALS_NAMESPACE)
    private Grade grade;
    @XmlElement(name = "status", namespace = Representation.APPEALS_NAMESPACE)
    private AppealStatus status;
    
    AppealRepresentation() {
        LOG.info("In AppealRepresentation constructor");
    }
    
    public AppealRepresentation(Appeal appeal, Link... links) {
        LOG.info("Creating an Appeal Representation for order = {} and links = {}", appeal.toString(), links.toString());
        try {
            this.comments = appeal.getComments();
            this.grade=appeal.getGrade();
            this.status = appeal.getStatus();
            this.links = java.util.Arrays.asList(links);
        } catch (Exception ex) {
            throw new InvalidAppealException(ex);
        }
        
    }
    
    public static AppealRepresentation fromXmlString(String xmlRepresentation) {
        LOG.info("Creating an Appeal object from the XML = {}", xmlRepresentation);
                
        AppealRepresentation appealRepresentation = null;     
        try {
            JAXBContext context = JAXBContext.newInstance(AppealRepresentation.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            appealRepresentation = (AppealRepresentation) unmarshaller.unmarshal(new ByteArrayInputStream(xmlRepresentation.getBytes()));
        } catch (Exception e) {
            throw new InvalidAppealException(e);
        }
        
        LOG.debug("Generated the appeal object {}", xmlRepresentation);

        return appealRepresentation;
    }
    
    public static AppealRepresentation createResponseAppealRepresentation(Appeal appeal, AppealsUri appealUri) {
        LOG.info("Creating a Response Appeal for appeal = {} and appeal URI", appeal.toString(), appealUri.toString());

        AppealRepresentation appealRepresentation;     
        AppealsUri emailUri = new AppealsUri(appealUri.getBaseUri() + "/email");
        LOG.debug("Email URI = {}", emailUri);

        appealRepresentation=null;
        
        if(appeal.getStatus() == AppealStatus.CREATED) {
            LOG.debug("The appeal status is {}", AppealStatus.CREATED);
            appealRepresentation = new AppealRepresentation(appeal, 
                    new Link(APPEALS_URI + "cancel", appealUri), 
                    new Link(APPEALS_URI + "email", emailUri), 
                    new Link(APPEALS_URI + "update", appealUri),
                    new Link(APPEALS_URI + "close", appealUri),
                    new Link(Representation.SELF_REL_VALUE, appealUri));
        } else if(appeal.getStatus() == AppealStatus.ACCEPT) {
            LOG.debug("The appeal status is {}", AppealStatus.ACCEPT);
            appealRepresentation = new AppealRepresentation(appeal, new Link(APPEALS_URI + "close", appealUri),
                    new Link(Representation.SELF_REL_VALUE, appealUri));
        } else if(appeal.getStatus() == AppealStatus.CLOSED) {
            LOG.debug("The appeal status is {}", AppealStatus.CLOSED);
            appealRepresentation = new AppealRepresentation(appeal, new Link(APPEALS_URI + "appeal",appealUri /*UriExchange.receiptForPayment(paymentUri)*/));
        } else if(appeal.getStatus() == AppealStatus.CANCELLED) {
            LOG.debug("The appeal status is {}", AppealStatus.CANCELLED);
            appealRepresentation = new AppealRepresentation(appeal);            
        } else if(appeal.getStatus() == AppealStatus.REJECT){
            LOG.debug("The appeal status is {}", AppealStatus.REJECT);
            appealRepresentation = new AppealRepresentation(appeal);     
        } else {
            LOG.debug("The appeal status is in an unknown status");
            throw new RuntimeException("Unknown Appeal Status");
        }
        
        LOG.debug("The appeal representation created for the Create Response Appeal Representation is {}", appealRepresentation);
        
        return appealRepresentation;
        
    }
    
    @Override
    public String toString() {
         LOG.info("Converting appeal Representation object to string");
        try {
            JAXBContext context = JAXBContext.newInstance(AppealRepresentation.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public Appeal getAppeal() {
        LOG.info("Retrieving the appeal Representation");
        LOG.debug("status = {}", status);
        LOG.debug("comments = {}", comments);
        LOG.debug("grade = {}", grade);
        if (comments == null) {
            throw new InvalidAppealException();
        }
        for (Comment q : comments) {
            if (q == null) {
                throw new InvalidAppealException();
            }
        }
        
        Appeal appeal = new Appeal(status, comments, grade);
        return appeal;
    }
    
    public Link getCancelLink() {
        LOG.info("Retrieving the Cancel link ");
        return getLinkByName(APPEALS_URI + "cancel");
    }
    
     public Link getEmailLink() {
         LOG.info("Retrieving the Email link ");
        return getLinkByName(APPEALS_URI + "email");
    }
    
    
    public Link getUpdateLink() {
        LOG.info("Retrieving the Update link ");
        return getLinkByName(APPEALS_URI + "update");
    }
    
    public Link getAcceptLink() {
        LOG.info("Retrieving the Accept link ");
        return getLinkByName(APPEALS_URI + "accept");
    }
    
    public Link getRejectLink() {
        LOG.info("Retrieving the Reject link ");
        return getLinkByName(APPEALS_URI + "reject");
    }
    
    public Link getCloseLink() {
        LOG.info("Retrieving the Close link ");
        return getLinkByName(APPEALS_URI + "close");
    }

    public Link getSelfLink() {
        LOG.info("Retrieving the Self link ");
        return getLinkByName("self");
    }
    
    public AppealStatus getStatus() {
        LOG.info("Retrieving the status of appeal ");
        return status;
    }
    
}
