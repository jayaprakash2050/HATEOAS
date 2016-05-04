/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.client.main;

import com.gradeappeal.model.AppealStatus;
import com.gradeappeal.model.Grade;
import com.gradeappeal.model.InstructorComments;
import com.gradeappeal.model.Reply;
import com.gradeappeal.representations.Representation;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jayaprakashjayakumar
 */
@XmlRootElement(name = "email", namespace = Representation.APPEALS_NAMESPACE)
public class ReplyClient {
    private static final Logger LOG = LoggerFactory.getLogger(ReplyClient.class);
    
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
    
    private ReplyClient(){
    }
    
    public ReplyClient(Reply reply) {
        this.from=reply.getFrom();
        this.to=reply.getTo();
        this.subject=reply.getSubject();
        this.resp=reply.getResp();
        this.grade=reply.getGrade();
        this.status=reply.getStatus();
    }
    
    public Reply getReply() {
        LOG.debug("Executing ReplyClient.getReply");
        return new Reply(from, to, subject, resp, grade, status);
    }
    
    
    @Override
    public String toString() {
        LOG.debug("Executing ReplyClient.toString");
        try {
            JAXBContext context = JAXBContext.newInstance(ReplyClient.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public AppealStatus getStatus() {
        LOG.debug("Executing ReplyClient.getStatus");
        return status;
    }
}
