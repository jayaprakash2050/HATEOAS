/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.representations;

import com.gradeappeal.activities.InvalidEmailException;
import com.gradeappeal.model.AppealStatus;
import com.gradeappeal.model.Comment;
import com.gradeappeal.model.Email;
import com.gradeappeal.model.Grade;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jayaprakashjayakumar
 */
@XmlRootElement(name = "email", namespace = Representation.APPEALS_NAMESPACE)
public class EmailRepresentation extends Representation {

    @XmlElement(name = "from", namespace = Representation.APPEALS_NAMESPACE)
    private String from;
    @XmlElement(name = "to", namespace = Representation.APPEALS_NAMESPACE)
    private String to;
    @XmlElement(name = "subject", namespace = Representation.APPEALS_NAMESPACE)
    private String subject;
    @XmlElement(name = "comment", namespace = Representation.APPEALS_NAMESPACE)
    private List<Comment> comments;
    @XmlElement(name = "grade", namespace = Representation.APPEALS_NAMESPACE)
    private Grade grade;
    @XmlElement(name = "status", namespace = Representation.APPEALS_NAMESPACE)
    private AppealStatus status;
   
    
    EmailRepresentation() {
    }

   public static EmailRepresentation fromXmlString(String xmlRepresentation) {
        EmailRepresentation emailRepresentation = null;     
        try {
            JAXBContext context = JAXBContext.newInstance(EmailRepresentation.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            emailRepresentation = (EmailRepresentation) unmarshaller.unmarshal(new ByteArrayInputStream(xmlRepresentation.getBytes()));
        } catch (Exception e) {
            throw new InvalidEmailException(e);
        }
        return emailRepresentation;
    }
    
    public static EmailRepresentation createResponseEmailRepresentation(Email email, AppealsUri emailUri) {
        
        EmailRepresentation emailRepresentation;     
        
       AppealsUri responseUri = new AppealsUri(emailUri.getBaseUri() + "/reply/" + emailUri.getId().toString());
        emailRepresentation=null;
        if(email.getStatus() == AppealStatus.CREATED) {
            emailRepresentation = new EmailRepresentation(email, 
                    new Link(APPEALS_URI + "reply", responseUri), 
                    new Link(Representation.SELF_REL_VALUE, emailUri));
        }  else {
            throw new RuntimeException("Email status is unknown");
        }
        
        return emailRepresentation;
    }

    public EmailRepresentation(Email email, Link... links) {
        
        try {
            this.from = email.getFrom();
            this.to = email.getTo();
            this.subject = email.getSubject();
            this.comments=email.getComments();
            this.grade=email.getGrade();
            this.status=email.getStatus();
            this.links = java.util.Arrays.asList(links);
        } catch (Exception ex) {
            throw new InvalidEmailException(ex);
        }
    }

    @Override
    public String toString() {
        try {
            JAXBContext context = JAXBContext.newInstance(EmailRepresentation.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Email getEmail() {
   
        if (status == null) {
            System.out.println("INVALID EMAIL!!!");
            throw new InvalidEmailException();
        }
        Email email = new Email(from, to, subject, comments, grade, status);
        return email;
    }

    public Link getReplyLink() {
        return getLinkByName(APPEALS_URI + "reply");
    }
       
    public Link getSelfLink() {
        return getLinkByName("self");
    }
    
    
    public AppealStatus getStatus() {
        return status;
    }
}
