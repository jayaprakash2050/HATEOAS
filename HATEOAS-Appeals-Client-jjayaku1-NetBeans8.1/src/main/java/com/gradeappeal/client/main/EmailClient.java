/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.client.main;

import com.gradeappeal.model.AppealStatus;
import com.gradeappeal.model.Comment;
import com.gradeappeal.model.Email;
import com.gradeappeal.model.Grade;
import com.gradeappeal.representations.Representation;
import java.io.StringWriter;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jayaprakashjayakumar
 */
@XmlRootElement(name = "email", namespace = Representation.APPEALS_NAMESPACE)
public class EmailClient {
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
    
    private EmailClient(){
    }
    
    public EmailClient(Email email) {
        this.from=email.getFrom();
        this.to=email.getTo();
        this.subject=email.getSubject();
        this.comments=email.getComments();
        this.grade=email.getGrade();
        this.status=email.getStatus();
    }
    
    public Email getEmail() {

        return new Email(from, to, subject, comments, grade, status);
    }
    
    
    @Override
    public String toString() {
   
        try {
            JAXBContext context = JAXBContext.newInstance(EmailClient.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public AppealStatus getStatus() {

        return status;
    }
}
