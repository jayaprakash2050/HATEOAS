/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeals.representations;

import com.gradeappeals.activities.InvalidEmailException;
import com.gradeappeals.activities.InvalidImageException;
import com.gradeappeals.model.Image;
import static com.gradeappeals.representations.Representation.APPEALS_URI;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jayaprakashjayakumar
 */
@XmlRootElement(name = "image", namespace = Representation.APPEALS_NAMESPACE)
public class ImageRepresentation extends Representation{
    @XmlElement(name = "from", namespace = Representation.APPEALS_NAMESPACE)
    private String from;
    @XmlElement(name = "to", namespace = Representation.APPEALS_NAMESPACE)
    private String to;
    @XmlElement(name = "fileName", namespace = Representation.APPEALS_NAMESPACE)
    private String fileName;

    public ImageRepresentation() {
    }
    
    public ImageRepresentation(Image image, Link... links) {
        
        try {
            this.from = image.getFrom();
            this.to = image.getTo();
            this.fileName = image.getFileName();

            this.links = java.util.Arrays.asList(links);
        } catch (Exception ex) {
            throw new InvalidImageException(ex);
        }
    }
    
     public static ImageRepresentation fromXmlString(String xmlRepresentation) {
        ImageRepresentation imageRepresentation = null;     
        try {
            JAXBContext context = JAXBContext.newInstance(ImageRepresentation.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            imageRepresentation = (ImageRepresentation) unmarshaller.unmarshal(new ByteArrayInputStream(xmlRepresentation.getBytes()));
        } catch (Exception e) {
            throw new InvalidEmailException(e);
        }
        return imageRepresentation;
    }
    
    public static ImageRepresentation createResponseImageRepresentation(Image image, AppealsUri imageUri) {
        
        ImageRepresentation imageRepresentation;     
        
       AppealsUri responseUri = new AppealsUri(imageUri.getBaseUri() + "/image/" + imageUri.getId().toString());
        imageRepresentation=null;
        
            imageRepresentation = new ImageRepresentation(image, 
                    new Link(APPEALS_URI + "reply", responseUri), 
                    new Link(Representation.SELF_REL_VALUE, imageUri));

        
        return imageRepresentation;
    }
    
    @Override
    public String toString() {
        try {
            JAXBContext context = JAXBContext.newInstance(ImageRepresentation.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Image getImage() {
   
        Image image = new Image(from, to, fileName);
        return image;
    }

    public Link getImageLink() {
        return getLinkByName(APPEALS_URI + "image");
    }
       
    public Link getSelfLink() {
        return getLinkByName("self");
    }
   
}
