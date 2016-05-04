package com.gradeappeal.client.main;


import com.gradeappeal.model.Appeal;
import static com.gradeappeal.model.AppealBuilder.appeal;
import com.gradeappeal.model.Email;
import static com.gradeappeal.model.EmailBuilder.email;
import com.gradeappeal.model.Image;
import static com.gradeappeal.model.ImageBuilder.image;
import com.gradeappeal.representations.AppealRepresentation;
import com.gradeappeal.representations.AppealsUri;
import com.gradeappeal.representations.EmailRepresentation;
import com.gradeappeal.representations.ImageRepresentation;
import com.gradeappeal.representations.Link;
import com.gradeappeal.representations.ReplyRepresentation;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import java.net.URI;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jayaprakashjayakumar
 */
public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static final String APPEALS_MEDIA_TYPE = "application/vnd-cse564-appeals+xml";
    private static final long ONE_MINUTE = 60000; 
    
    private static final String ENTRY_POINT_URI = "http://localhost:8080/HATEOAS-Appeals-Server-jjayaku1-NetBeans8.1/webresources/appeal";
    private static final String EMAIL_POINT_URI = "http://localhost:8080/HATEOAS-Appeals-Server-jjayaku1-NetBeans8.1/webresources/email";
    private static final String Image_POINT_URI = "http://localhost:8080/HATEOAS-Appeals-Server-jjayaku1-NetBeans8.1/webresources/image";
    
    public static void main(String[] args) throws Exception {
        URI appealUri = new URI(ENTRY_POINT_URI);
        Scanner sc=new Scanner(System.in);
        
        System.out.println("Running the happy case");
        happyPathTest(appealUri);
        
        System.out.println("Running the abandoned case");
        abandonedPathTest(appealUri);
        
        System.out.println("Running the forgotten case");
        forgotCaseTest(appealUri);
        
        System.out.println("Running the Bad Start case");
        badStartCaseTest(appealUri);
        
        System.out.println("Running the Bad id case");
        badIdTest(appealUri);
    }
    
     private static void badStartCaseTest(URI appealUri) throws Exception {
        
        System.out.println("STARTING BAD START CASE WITH APPEAL URI "+ appealUri +"\n");
        
        // Create the appeal
        System.out.println(String.format("Creating appeal at [%s] via POST\n\n", appealUri.toString()));
        Appeal appeal = appeal().withRandom().build();
        System.out.println("\nCreating the appeal " + appeal);
        Client client = Client.create();
        System.out.println("\nCreated appeal link -" + appealUri);
        AppealRepresentation appealRepresentation = client.resource(appealUri).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal));
        System.out.println("\nReceived response from server is:\n" + appealRepresentation.getAppeal());
        System.out.println("\nAppeal created at " + appealRepresentation.getSelfLink().getUri().toString());
         
         //Bad URI
        System.out.println("\nTrying a BAD UPDATE\n");
        System.out.println(String.format("About to update an order with bad URI [%s] via POST", appealRepresentation.getUpdateLink().getUri().toString() + "/bad-uri"));
        appeal = appeal().withRandom().build();
        System.out.println("\nCreating base appeal " + appeal);
        Link badUriLink = new Link("bad", new AppealsUri(appealRepresentation.getSelfLink().getUri().toString() + "/bad-uri"), APPEALS_MEDIA_TYPE);
        System.out.println("\nCreated bad URI Link "+ badUriLink);
        ClientResponse badResponse = client.resource(badUriLink.getUri()).accept(badUriLink.getMediaType()).type(badUriLink.getMediaType()).post(ClientResponse.class, new AppealRepresentation(appeal));
        System.out.println("\nBad Response Received from Server is" + badResponse);
        System.out.println("\nTried to update order with bad URI at "+ badUriLink.getUri().toString() +" via POST ");
        System.out.println("\n The status of the bad response was " +badResponse.getStatus());
     }
     
     private static void forgotCaseTest(URI appealUri) throws Exception {
        
        System.out.println("\n\n--------STARTING FORGOT CASE WITH APPEAL URI "+appealUri+" --------\n\n");
        
        // Create the appeal
        System.out.println(String.format("\nCreating appeal at [%s] via POST\n\n", appealUri.toString()));
        Appeal appeal = appeal().withRandom().build();
        System.out.println("Creating base appeal " + appeal);
        Client client = Client.create();
        System.out.println("Created appeal link -" + appealUri);
        AppealRepresentation appealRepresentation = client.resource(appealUri).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal));
        System.out.println("Response appeal received from server is:\n" + appealRepresentation.getAppeal());
        System.out.println(String.format("Appeal created at [%s]", appealRepresentation.getSelfLink().getUri().toString()));
    
        // Send the appeal as email
        URI emailUri=new URI(EMAIL_POINT_URI);
        System.out.println(String.format("\n\nSending the appeal created as an email at [%s] via POST\n\n", emailUri.toString()));
        Email email = email().build(appeal.getComments(),appeal.getGrade());
        System.out.println("Email content" + email);
        System.out.println("Sending email with URI - " + emailUri);
        EmailRepresentation emailRepresentation = client.resource(emailUri).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).post(EmailRepresentation.class, new EmailClient(email));
        System.out.println("Response email received from server is " + emailRepresentation.getEmail());
        System.out.println(String.format("Email sent at [%s]", emailRepresentation.getSelfLink().getUri().toString()));
        
      

        System.out.println("\nFollow up for the appeal sent\n\n");
        
        
        // Following up
        System.out.println("\n\nFollowing up for an appeal via POST\n");
        email.setSubject("Follow Up on APPEAL - Midterm");
        System.out.println("Email content" + email);
        System.out.println("Sending email with URI - " + emailUri);
        EmailRepresentation emailRepresentation2 = client.resource(emailUri).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).post(EmailRepresentation.class, new EmailClient(email));
        System.out.println("Received response mail from server is:\n " + emailRepresentation2.getEmail());
        System.out.println(String.format("Email sent at [%s]", emailRepresentation.getSelfLink().getUri().toString()));
        
        // Get the reply email
        System.out.println("\n\nGet the reply");
        System.out.println(String.format("\nRequesting the reply email from Professor via GET at [%s]", emailRepresentation.getReplyLink().getUri().toString()));
        Link replyLink = emailRepresentation.getReplyLink();
        System.out.println("Reply link  for the email is " + replyLink.getUri().toString());
        ReplyRepresentation replyRepresentation = client.resource(replyLink.getUri()).get(ReplyRepresentation.class);
        System.out.println("Received reply email from server is:\n " + replyRepresentation.getReply());
        
    }
    
    private static void badIdTest(URI appealUri) throws Exception {
        
        System.out.println("\n\n--------STARTING BAD ID CASE WITH APPEAL URI "+appealUri+" --------\n\n");
        
        // Create the appeal
        System.out.println(String.format("\nCreating appeal at [%s] via POST\n\n", appealUri.toString()));
        Appeal appeal = appeal().withRandom().build();
        System.out.println("Creating base appeal " + appeal);
        Client client = Client.create();
        System.out.println("Created appeal link -" + appealUri);
        AppealRepresentation appealRepresentation = client.resource(appealUri).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal));
        System.out.println("Response appeal received from server is:\n" + appealRepresentation.getAppeal());
        System.out.println(String.format("Appeal created at [%s]", appealRepresentation.getSelfLink().getUri().toString()));
        
        
        // Update the appeal
        System.out.println("\n\nUpdate appeal via PUT");
        appeal = appeal().withRandomItems(appeal.getGrade()).build();
        System.out.println("Updated appeal " + appeal);
        Link updateLink = appealRepresentation.getUpdateLink();
        System.out.println("Created appeal update link -" + updateLink.getUri());
        AppealRepresentation updatedRepresentation = client.resource(updateLink.getUri()).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).put(AppealRepresentation.class, new AppealRepresentation(appeal));
        System.out.println("Receivedresponse Updated Appeal from server is:\n " + updatedRepresentation.getAppeal());
        System.out.println(String.format("Appeal updated at [%s]", updatedRepresentation.getSelfLink().getUri().toString()));
    
        // Send the appeal email
        URI emailUri=new URI(EMAIL_POINT_URI);
        System.out.println(String.format("\n\nSending appeal email at [%s] via POST\n\n", emailUri.toString()));
        Email email = email().build(appeal.getComments(),appeal.getGrade());
        System.out.println("Email content" + email);
        System.out.println("Sending email with URI - " + emailUri);
        EmailRepresentation emailRepresentation = client.resource(emailUri).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).post(EmailRepresentation.class, new EmailClient(email));
        System.out.println("Received response email from server is:\n " + emailRepresentation.getEmail());
        System.out.println(String.format("Email sent at [%s]", emailRepresentation.getSelfLink().getUri().toString()));
        
        

        System.out.println("\n\nCreating a new appeal to send\n\n");
        
        
       // Create the appeal again
        System.out.println(String.format("\n\nCreating the same appeal as earlier at [%s] via POST\n\n", appealUri.toString()));
        System.out.println("nCreating base appeal " + appeal);
        System.out.println("Created appeal link -" + appealUri);
        appealRepresentation = client.resource(appealUri).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal));
        System.out.println("Received response appeal from server is:\n" + appealRepresentation.getAppeal());
        System.out.println(String.format("Appeal created at [%s]", appealRepresentation.getSelfLink().getUri().toString()));
            
        // Send the appeal as email
        System.out.println(String.format("\n\nSending the appeal again at [%s] via POST\n\n", emailUri.toString()));
        email = email().build(appeal.getComments(),appeal.getGrade());
        System.out.println("Email content" + email);
        System.out.println("Sending email with URI - " + emailUri);
        emailRepresentation = client.resource(emailUri).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).post(EmailRepresentation.class, new EmailClient(email));
        System.out.println("Received response email from server is:\n " + emailRepresentation.getEmail());
        System.out.println(String.format("Email sent at [%s]", emailRepresentation.getSelfLink().getUri().toString()));
        
        // Get the reply
        System.out.println("\nGet the reply email");
        System.out.println(String.format("\nAbout to request the reply email from Professor via GET at [%s]", emailRepresentation.getReplyLink().getUri().toString()));
        Link replyLink = emailRepresentation.getReplyLink();
        System.out.println("Reply link  for the email is " + replyLink.getUri().toString());
        ReplyRepresentation replyRepresentation = client.resource(replyLink.getUri()).get(ReplyRepresentation.class);
        System.out.println("Received reply Email from server is:\n " + replyRepresentation.getReply());
        
    }
    
    private static void abandonedPathTest(URI appealUri) throws Exception {
        
        System.out.println("\n\nSTARTING ABANDON CASE WITH APPEAL URI "+appealUri+"\n\n");
        
        // Create the appeal
        System.out.println(String.format("\n\nCreating the appeal at [%s] via POST------\n\n", appealUri.toString()));
        Appeal appeal = appeal().withRandom().build();
        System.out.println("nCreating base appeal " + appeal);
        Client client = Client.create();
        System.out.println("Created appeal link -" + appealUri);
        AppealRepresentation appealRepresentation = client.resource(appealUri).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal));
        System.out.println("Created appeal received from server is:\n" + appealRepresentation.getAppeal());
        System.out.println(String.format("Appeal created at [%s]", appealRepresentation.getSelfLink().getUri().toString()));
        
        // Update the appeal
        System.out.println("\n\nUpdate appeal via PUT");
        appeal = appeal().withRandomItems(appeal.getGrade()).build();
        System.out.println("Updated appeal " + appeal);
        Link updateLink = appealRepresentation.getUpdateLink();
        System.out.println("Created appeal update link -" + updateLink.getUri());
        AppealRepresentation updatedRepresentation = client.resource(updateLink.getUri()).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).put(AppealRepresentation.class, new AppealRepresentation(appeal));
        System.out.println("Received Updated Appeal from server is:\n" + updatedRepresentation.getAppeal());
        System.out.println(String.format("Appeal updated at [%s]", updatedRepresentation.getSelfLink().getUri().toString()));
    
        //Cancelling the Appeal
        System.out.println(String.format("\n\nCancelling the Appeal created before\n\n", appealUri.toString()));
        Link cancelLink = appealRepresentation.getCancelLink();
        System.out.println("Created appeal update link -" + cancelLink.getUri());
        ClientResponse finalResponse = client.resource(cancelLink.getUri()).delete(ClientResponse.class);
        System.out.println(String.format("Delete Response Status is [%d]", finalResponse.getStatus()));
        if(finalResponse.getStatus() == 200) {
        System.out.println(String.format("Appeal Created has been Deleted", finalResponse.getEntity(AppealRepresentation.class).getStatus()));
        }
    
    }

    private static void happyPathTest(URI appealUri) throws Exception {
        
       System.out.println("\n\nSTARTING HAPPY CASE WITH APPEAL URI "+appealUri+"\n\n");
        
        // Create the appeal
        System.out.println(String.format("\n\nCreating appeal at [%s] via POST\n\n", appealUri.toString()));
        Appeal appeal = appeal().withRandom().build();
        System.out.println("\nCreating base appeal " + appeal);
        Client client = Client.create();
        System.out.println("\nCreated appeal link -" + appealUri);
        AppealRepresentation appealRepresentation = client.resource(appealUri).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal));
        System.out.println("Created appeal received from server is:\n" + appealRepresentation.getAppeal());
        System.out.println(String.format("Appeal created at [%s]", appealRepresentation.getSelfLink().getUri().toString()));
        
        Image image = image().build();
        System.out.println("Created Image " + image);
        System.out.println("\nCreated Image link -" + Image_POINT_URI);
        ImageRepresentation imgRepresentation = client.resource(Image_POINT_URI).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).post(ImageRepresentation.class, new ImageClient(image));
        System.out.println("Created image received from server is:\n" + imgRepresentation.getImage());
        System.out.println(String.format("Image created at [%s]", imgRepresentation.getSelfLink().getUri().toString()));
            
        // Update the appeal
        System.out.println("\n\nUpdating appeal via PUT");
        appeal = appeal().withRandomItems(appeal.getGrade()).build();
        System.out.println("Updated appeal " + appeal);
        Link updateLink = appealRepresentation.getUpdateLink();
        System.out.println("Created appeal update link -" + updateLink.getUri());
        AppealRepresentation updatedRepresentation = client.resource(updateLink.getUri()).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).put(AppealRepresentation.class, new AppealRepresentation(appeal));
        System.out.println("Received Updated Appeal from server is:\n" + updatedRepresentation.getAppeal());
        System.out.println(String.format("Appeal updated at [%s]", updatedRepresentation.getSelfLink().getUri().toString()));
        
      
        
        URI emailUri=new URI(EMAIL_POINT_URI);
        System.out.println(String.format("\n\nSending an email at [%s] via POST\n\n", emailUri.toString()));
        Email email = email().build(appeal.getComments(),appeal.getGrade());
        System.out.println("Email content" + email);
        System.out.println("Sending email with URI - " + emailUri);
        EmailRepresentation emailRepresentation = client.resource(emailUri).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).post(EmailRepresentation.class, new EmailClient(email));
        System.out.println("Received Email from server is:\n " + emailRepresentation.getEmail());
        System.out.println(String.format("Email sent at [%s]", emailRepresentation.getSelfLink().getUri().toString()));
        
        System.out.println("\n\nGet the reply email");
        System.out.println(String.format("\nRequesting the reply email from Professor via GET at [%s]", emailRepresentation.getReplyLink().getUri().toString()));
        Link replyLink = emailRepresentation.getReplyLink();
        System.out.println("Retrieved the reply link " + replyLink.getUri().toString() + " for the email " );
        ReplyRepresentation replyRepresentation = client.resource(replyLink.getUri()).get(ReplyRepresentation.class);
        System.out.println("Reply Email received is:\n " + replyRepresentation.getReply());
    }
}
