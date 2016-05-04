/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeals.resources;

import com.gradeappeals.activities.AppealDeletionException;
import com.gradeappeals.activities.CreateAppealActivity;
import com.gradeappeals.activities.InvalidAppealException;
import com.gradeappeals.activities.NoSuchAppealException;
import com.gradeappeals.activities.ReadAppealActivity;
import com.gradeappeals.activities.RemoveAppealActivity;
import com.gradeappeals.activities.UpdateAppealActivity;
import com.gradeappeals.activities.UpdateException;
import com.gradeappeals.representations.AppealRepresentation;
import com.gradeappeals.representations.AppealsUri;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
@Path("/appeal")
public class AppealResource {
    
    private static final Logger LOG = LoggerFactory.getLogger(AppealResource.class);
    
    private @Context UriInfo uriInfo;
    
    public AppealResource() {
        LOG.info("AppealResource constructor");
    }

    public AppealResource(UriInfo uriInfo) {
        LOG.info("OrderResource constructor with mock uriInfo {}", uriInfo);
        this.uriInfo = uriInfo;  
    }
    

    @POST
    @Consumes("application/vnd-cse564-appeals+xml")
    @Produces("application/vnd-cse564-appeals+xml")
    public Response createAppeal(String appealRepresentation) {
        
        LOG.info("Creating an Appeal Resource");
        System.out.println("Creating Appeal");
        Response response;
        
        try {
            AppealRepresentation responseRepresentation = new CreateAppealActivity().create(AppealRepresentation.fromXmlString(appealRepresentation).getAppeal(), new AppealsUri(uriInfo.getRequestUri()));
            System.out.println("Representation created");
            response = Response.created(responseRepresentation.getUpdateLink().getUri()).entity(responseRepresentation).build();
        } catch (InvalidAppealException ex) {  
            System.out.println(ex.getMessage());
            LOG.debug("Invalid Order - Problem with the appealsrepresentation {}", appealRepresentation);
            response = Response.status(Response.Status.BAD_REQUEST).build();
        } catch (Exception ex) {
            System.out.println("Exception ="+ex.getMessage());
            LOG.debug("Someting went wrong creating the order resource");
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        
        LOG.debug("Resulting response for creating the appeal resource is {}", response);
        return response;
    }
    
    @GET
    @Path("/{appealId}")
    @Produces("application/vnd-cse564-appeals+xml")
    public Response getAppeal() {
        LOG.info("Retrieving an Appeal Resource");
        Response response;
        
        try {
            AppealRepresentation responseRepresentation = new ReadAppealActivity().retrieveByUri(new AppealsUri(uriInfo.getRequestUri()));
            response = Response.status(Response.Status.OK).entity(responseRepresentation).build();
        } catch(NoSuchAppealException ex) {     
            LOG.debug("No such appeal");
            response = Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception ex) {
            LOG.debug("Something went wrong retriveing the Appeal");
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        LOG.debug("Retrieved the appeal resource", response);
        return response;
    }
    
    
    @PUT
    @Path("/{appealId}")
    @Consumes("application/vnd-cse564-appeals+xml")
    @Produces("application/vnd-cse564-appeals+xml")
    public Response updateAppeal(String appealRepresentation) {
        
        LOG.info("Updating an Appeal Resource");

        Response response;
        
        try {
            AppealRepresentation responseRepresentation = new UpdateAppealActivity().update(AppealRepresentation.fromXmlString(appealRepresentation).getAppeal(), new AppealsUri(uriInfo.getRequestUri()));
            response = Response.status(Response.Status.OK).entity(responseRepresentation).build();
        } catch (InvalidAppealException ioe) {
            LOG.debug("Invalid appeal in the XML representation {}", appealRepresentation);
            response = Response.status(Response.Status.BAD_REQUEST).build();
        } catch (NoSuchAppealException nsoe) {
            LOG.debug("No such appeal resource to update");
            response = Response.status(Response.Status.NOT_FOUND).build();
        } catch(UpdateException ex) {
           LOG.debug("Problem updating the appeal resource");
            response = Response.status(Response.Status.CONFLICT).build();
        } catch (Exception ex) {
            LOG.debug("Something went wrong updating the appeal resource");
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } 
        
       LOG.debug("Resulting response for updating the appeal resource is {}", response);
        
        return response;
     }
    
    
    @DELETE
    @Path("/{appealId}")
    @Produces("application/vnd-cse564-appeals+xml")
    public Response removeAppeal() {

        LOG.info("\nReceived a Request to Delete an Appeal using DELETE\n");
        Response response;
        
        try {
            AppealRepresentation removedAppeal = new RemoveAppealActivity().delete(new AppealsUri(uriInfo.getRequestUri()));
            response = Response.status(Response.Status.OK).entity(removedAppeal).build();
        } catch (NoSuchAppealException nsoe) {      
            LOG.debug("No such appeal resource to delete");
            response = Response.status(Response.Status.NOT_FOUND).build();
        } catch(AppealDeletionException ode) {
            LOG.debug("Problem deleting order resource");
            response = Response.status(Response.Status.METHOD_NOT_ALLOWED).header("Allow", "GET").build();
        } catch (Exception ex) {
            LOG.debug("Something went wrong deleting the appeal resource");
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        
        LOG.info("\nAppeal was deleted\n");
        return response;
    }
    
    
    
}