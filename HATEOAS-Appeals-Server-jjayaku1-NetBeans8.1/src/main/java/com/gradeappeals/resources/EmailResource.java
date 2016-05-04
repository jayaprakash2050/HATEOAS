/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeals.resources;

import com.gradeappeals.activities.CreateEmailActivity;
import com.gradeappeals.activities.InvalidEmailException;
import com.gradeappeals.activities.NoSuchEmailException;
import com.gradeappeals.activities.ReadEmailActivity;
import com.gradeappeals.activities.RemoveEmailActivity;
import com.gradeappeals.activities.UpdateException;
import com.gradeappeals.model.Identifier;
import com.gradeappeals.representations.AppealsUri;
import com.gradeappeals.representations.EmailRepresentation;
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
@Path("/email")
public class EmailResource {
    
    private static final Logger LOG = LoggerFactory.getLogger(EmailResource.class);
    
    private @Context UriInfo uriInfo;
    
    public EmailResource(){
       LOG.info("EmailResource constructor");
    }
   
    public EmailResource(UriInfo uriInfo) {
        LOG.info("EmailResource constructor with mock uriInfo {}", uriInfo);
        this.uriInfo = uriInfo;  
    }
    
    @POST
    @Consumes("application/vnd-cse564-appeals+xml")
    @Produces("application/vnd-cse564-appeals+xml")
    public Response email(String emailRepresentation) {
        
        LOG.info("Creating an Email Resource");
        Response response;
       try {
            EmailRepresentation responseRepresentation = new CreateEmailActivity().create(EmailRepresentation.fromXmlString(emailRepresentation).getEmail(), new AppealsUri(uriInfo.getRequestUri()));
            response = Response.created(responseRepresentation.getReplyLink().getUri()).entity(responseRepresentation).build();
        } catch(NoSuchEmailException nsoe) {
            response = Response.status(Response.Status.NOT_FOUND).build();
        } catch(UpdateException ue) {
            Identifier identifier = new AppealsUri(uriInfo.getRequestUri()).getId();
            Link link = new Link(Representation.SELF_REL_VALUE, new AppealsUri(uriInfo.getBaseUri().toString() + "email/" + identifier));
            response = Response.status(Response.Status.FORBIDDEN).entity(link).build();
        } catch(InvalidEmailException ipe) {
            response = Response.status(Response.Status.BAD_REQUEST).build();
        } catch(Exception e) {
            response = Response.serverError().build();
        }
        
        System.out.println("\n\nResponse object for create email request is " + response.getEntity().toString() +"\n\n");
        
        return response;
    }
    
    @GET
    @Path("/{emailIdentifier}")
    @Produces("application/vnd-cse564-appeals+xml")
    public Response readEmail() {
        LOG.info("Retrieving an email");
        Response response;
        
        try {
            EmailRepresentation responseRepresentation = new ReadEmailActivity().retrieveByUri(new AppealsUri(uriInfo.getRequestUri()));
            response = Response.status(Response.Status.OK).entity(responseRepresentation).build();
        } catch(NoSuchEmailException ex) {     
            LOG.debug("No such email");
            response = Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception ex) {
            LOG.debug("Something went wrong retriveing the email");
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        LOG.debug("Retrieved the email resource", response);
        return response;
    }
    
    @DELETE
    @Path("/{emailIdentifier}")
    @Produces("application/vnd-cse564-appeals+xml")
    public Response removeAppeal() {

        LOG.info("\nReceived a Request to Delete an email using DELETE\n");
        Response response;
        
        try {
            EmailRepresentation removedEmail = new RemoveEmailActivity().delete(new AppealsUri(uriInfo.getRequestUri()));
            response = Response.status(Response.Status.OK).entity(removedEmail).build();
        } catch (NoSuchEmailException ex) {      
            LOG.debug("No such email resource to delete");
            response = Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception ex) {
            LOG.debug("Something went wrong deleting the email resource");
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        
        LOG.info("\nEmail was deleted\n");
        return response;
    }
    
}
