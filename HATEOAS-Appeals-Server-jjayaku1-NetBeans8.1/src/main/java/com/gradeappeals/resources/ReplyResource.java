/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gradeappeals.resources;

import com.gradeappeals.activities.ReadReplyActivity;
import com.gradeappeals.representations.AppealsUri;
import com.gradeappeals.representations.ReplyRepresentation;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author jayaprakashjayakumar
 */
@Path("/reply")
public class ReplyResource {
    
    private @Context UriInfo uriInfo;

    public ReplyResource() {
        
    }

    public ReplyResource(UriInfo uriInfo) {
        this.uriInfo = uriInfo;  
    }
    
    
    
    @GET
    @Path("/{Id}")
    @Produces("application/vnd-cse564-appeals+xml")
    public Response getReply() {
        System.out.println("Get Reply method called");
        Response response;
        try {
            ReplyRepresentation responseRepresentation = new ReadReplyActivity().readReply(new AppealsUri(uriInfo.getRequestUri()));
            
            response = Response.status(Response.Status.OK).entity(responseRepresentation).build();           
            System.out.println("Retrieved the Reply Email" + responseRepresentation.getReply().toString());
        } catch (Exception ex) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        
        return response;
    }
    
}

