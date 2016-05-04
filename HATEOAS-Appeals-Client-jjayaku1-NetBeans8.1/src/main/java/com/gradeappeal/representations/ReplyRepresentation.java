/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.representations;

import com.gradeappeal.activities.InvalidEmailException;
import com.gradeappeal.model.AppealStatus;
import com.gradeappeal.model.Grade;
import com.gradeappeal.model.InstructorComments;
import com.gradeappeal.model.Reply;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
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
@XmlRootElement(name = "reply", namespace = Representation.APPEALS_NAMESPACE)
public class ReplyRepresentation extends Representation {
    private static final Logger LOG = LoggerFactory.getLogger(ReplyRepresentation.class);
    @XmlElement(name = "from", namespace = Representation.APPEALS_NAMESPACE)
    private String from;
    @XmlElement(name = "to", namespace = Representation.APPEALS_NAMESPACE)
    private String to;
    @XmlElement(name = "subject", namespace = Representation.APPEALS_NAMESPACE)
    private String subject;
    @XmlElement(name = "resp", namespace = Representation.APPEALS_NAMESPACE)
    private InstructorComments resp;
    @XmlElement(name = "grade", namespace = Representation.APPEALS_NAMESPACE)
    private Grade grade;
    @XmlElement(name = "status", namespace = Representation.APPEALS_NAMESPACE)
    private AppealStatus status;
   
    ReplyRepresentation() {
        LOG.info("In ReplyRepresentation constructor");
    }

   public static ReplyRepresentation fromXmlString(String xmlRepresentation) {     
       LOG.info("Creating an Reply object from the XML = {}", xmlRepresentation);
        ReplyRepresentation replyRepresentation = null;     
        try {
            JAXBContext context = JAXBContext.newInstance(ReplyRepresentation.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            replyRepresentation = (ReplyRepresentation) unmarshaller.unmarshal(new ByteArrayInputStream(xmlRepresentation.getBytes()));
        } catch (Exception e) {
            throw new InvalidEmailException(e);
        }
        LOG.debug("Generated the reply object {}", xmlRepresentation);
        return replyRepresentation;
    }
    
    public static ReplyRepresentation createResponseReplyRepresentation(Reply reply, AppealsUri replyUri) {
       LOG.info("Creating a Response Appeal for appeal = {} and appeal URI", reply.toString(), replyUri.toString());
       ReplyRepresentation replyRepresentation;     
        
       AppealsUri responseUri = new AppealsUri(replyUri.getBaseUri() + "/reply/" + replyUri.getId().toString());
       LOG.debug("Reply URI = {}", responseUri);
        replyRepresentation=null;
        if((reply.getStatus() == AppealStatus.ACCEPT) || (reply.getStatus() == AppealStatus.REJECT)) {
            LOG.debug("The appeal status is {}", reply.getStatus());
            replyRepresentation = new ReplyRepresentation(reply,
                    new Link(Representation.SELF_REL_VALUE, replyUri));
        }  else {
            throw new RuntimeException("Status of email is not known");
        }
        LOG.debug("The reply representation created for the Create Response Reply Representation is {}", replyRepresentation);
        return replyRepresentation;
    }

    public ReplyRepresentation(Reply reply, Link... links) {
        LOG.info("Creating an Reply Representation for order = {} and links = {}", reply.toString(), links.toString());
        
        try {
            this.from = reply.getFrom();
            this.to = reply.getTo();
            this.subject = reply.getSubject();
            this.resp=reply.getResp();
            this.grade=reply.getGrade();
            this.status=reply.getStatus();
            this.links = java.util.Arrays.asList(links);
        } catch (Exception ex) {
            throw new InvalidEmailException(ex);
        }
        
       
    }

    @Override
    public String toString() {
     LOG.info("Converting reply Representation object to string");
        try {
            JAXBContext context = JAXBContext.newInstance(ReplyRepresentation.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Reply getReply() {
     
        LOG.info("Retrieving the reply Representation");
        LOG.debug("status = {}", status);
        LOG.debug("response = {}", resp);
        LOG.debug("grade = {}", grade);
        if (status == null) {
            LOG.debug("INVALID EMAIL!!!");
            throw new InvalidEmailException();
        }
        
        Reply reply = new Reply(from, to, subject, resp, grade, status);
        
      

        return reply;
    }

 
       
    public Link getSelfLink() {
        LOG.info("Retrieving the Self link ");
        return getLinkByName("self");
    }
    
    
    public AppealStatus getStatus() {
        LOG.info("Retrieving the status ");
        return status;
    }
}