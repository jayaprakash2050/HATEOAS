/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeals.resources;

import com.gradeappeals.activities.AddImageActivity;
import com.gradeappeals.activities.InvalidImageException;
import com.gradeappeals.activities.NoSuchImageException;
import com.gradeappeals.activities.ReadImageActivity;
import com.gradeappeals.activities.RemoveImageActivity;
import com.gradeappeals.activities.UpdateException;
import com.gradeappeals.model.Identifier;
import com.gradeappeals.representations.AppealsUri;
import com.gradeappeals.representations.ImageRepresentation;
import com.gradeappeals.representations.Link;
import com.gradeappeals.representations.Representation;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jayaprakashjayakumar
 */
@Path("/image")
public class ImageResource {
    private static final Logger LOG = LoggerFactory.getLogger(ImageResource.class);
    
    private @Context UriInfo uriInfo;
    
    public ImageResource(){
       LOG.info("ImageResource constructor");
    }
   
    public ImageResource(UriInfo uriInfo) {
        LOG.info("ImageResource constructor with mock uriInfo {}", uriInfo);
        this.uriInfo = uriInfo;  
    }
    
    @POST
    @Consumes("application/vnd-cse564-appeals+xml")
    @Produces("application/vnd-cse564-appeals+xml")
    public Response image(String imageRepresentation) {
        
        LOG.info("Creating an Image Resource");
        Response response;
       try {
            ImageRepresentation responseRepresentation = new AddImageActivity().add(ImageRepresentation.fromXmlString(imageRepresentation).getImage(), new AppealsUri(uriInfo.getRequestUri()));
            response = Response.created(responseRepresentation.getSelfLink().getUri()).entity(responseRepresentation).build();
        } catch(UpdateException ex) {
            Identifier identifier = new AppealsUri(uriInfo.getRequestUri()).getId();
            Link link = new Link(Representation.SELF_REL_VALUE, new AppealsUri(uriInfo.getBaseUri().toString() + "image/" + identifier));
            response = Response.status(Response.Status.FORBIDDEN).entity(link).build();
        } catch(InvalidImageException iie) {
            response = Response.status(Response.Status.BAD_REQUEST).build();
        } catch(Exception e) {
            response = Response.serverError().build();
        }
        
        System.out.println("\n\nResponse object for create image request is " + response.getEntity().toString() +"\n\n");
        
        return response;
    }
    
    @GET
    @Path("/{imageIdentifier}")
    @Produces("application/vnd-cse564-appeals+xml")
    public Response readImage() {
        LOG.info("Retrieving an image");
        Response response;
        
        try {
            ImageRepresentation responseRepresentation = new ReadImageActivity().retrieveByUri(new AppealsUri(uriInfo.getRequestUri()));
            response = Response.status(Response.Status.OK).entity(responseRepresentation).build();
        } catch(NoSuchImageException ex) {     
            LOG.debug("No such image");
            response = Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception ex) {
            LOG.debug("Something went wrong retriveing the image");
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        LOG.debug("Retrieved the image resource", response);
        return response;
    }
    
    @DELETE
    @Path("/{imageIdentifier}")
    @Produces("application/vnd-cse564-appeals+xml")
    public Response removeImage() {

        LOG.info("\nReceived a Request to Delete an image using DELETE\n");
        Response response;
        
        try {
            ImageRepresentation removedImage = new RemoveImageActivity().delete(new AppealsUri(uriInfo.getRequestUri()));
            response = Response.status(Response.Status.OK).entity(removedImage).build();
        } catch (NoSuchImageException ex) {      
            LOG.debug("No such image resource to delete");
            response = Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception ex) {
            LOG.debug("Something went wrong deleting the image resource");
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        
        LOG.info("\nImage was deleted\n");
        return response;
    }
    
}
