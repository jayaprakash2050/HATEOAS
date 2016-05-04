/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeal.client.main;

import com.gradeappeal.model.Image;
import com.gradeappeal.representations.Representation;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jayaprakashjayakumar
 */
@XmlRootElement(name = "image", namespace = Representation.APPEALS_NAMESPACE)
public class ImageClient {
    @XmlElement(name = "from", namespace = Representation.APPEALS_NAMESPACE)
    private String from;
    @XmlElement(name = "to", namespace = Representation.APPEALS_NAMESPACE)
    private String to;
    @XmlElement(name = "fileName", namespace = Representation.APPEALS_NAMESPACE)
    private String fileName;
    
    private ImageClient(){
    }
    
    public ImageClient(Image image) {
        this.from=image.getFrom();
        this.to=image.getTo();
        this.fileName=image.getFileName();
    }
    
    @Override
    public String toString() {
   
        try {
            JAXBContext context = JAXBContext.newInstance(ImageClient.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
