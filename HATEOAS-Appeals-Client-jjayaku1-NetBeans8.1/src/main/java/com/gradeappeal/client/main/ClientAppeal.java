/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.client.main;

import com.gradeappeal.model.Appeal;
import com.gradeappeal.model.AppealStatus;
import com.gradeappeal.model.Comment;
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
@XmlRootElement(name = "appeal", namespace = Representation.APPEALS_NAMESPACE)
public class ClientAppeal {
    
    
    @XmlElement(name = "comment", namespace = Representation.APPEALS_NAMESPACE)
    private List<Comment> comments;
    @XmlElement(name = "grade", namespace = Representation.APPEALS_NAMESPACE)
    private Grade grade;
    @XmlElement(name = "status", namespace = Representation.APPEALS_NAMESPACE)
    private AppealStatus status;
    
    private ClientAppeal(){}
    
    public ClientAppeal(Appeal appeal) {

        this.grade=appeal.getGrade();
        this.comments = appeal.getComments();
    }
    
    public Appeal getAppeal() {
       
        return new Appeal(status, comments, grade);
    }
    
    
    public List<Comment> getComments() {
 
        return comments;
    }

    public Grade getGrade() {
        return this.grade;
    }
   
    @Override
    public String toString() {
       
        try {
            JAXBContext context = JAXBContext.newInstance(ClientAppeal.class);
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