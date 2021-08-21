package com.github.awsdemo.checkout.rest;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/checkout")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CheckoutResource {

    private static final Logger LOG = Logger.getLogger(CheckoutResource.class);

    private static final String QUEUE_NAME = "CheckoutQueue";

    private final AmazonSQS sqs = AmazonSQSClientBuilder.standard().withRegion("us-east-1").build();

    @POST
    public Response checkout(Product product) {
        LOG.info("POST: " + product.toString());
        String queueUrl = getQueueUrl();

        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(product.toString())
                .withDelaySeconds(5);
        sqs.sendMessage(sendMessageRequest);

        return Response.ok().build();
    }

    private String getQueueUrl() {
        List<String> queueUrls = sqs.listQueues(QUEUE_NAME).getQueueUrls();
        if (queueUrls.isEmpty()) {
            CreateQueueResult createQueueResult = sqs.createQueue(new CreateQueueRequest(QUEUE_NAME));
            return createQueueResult.getQueueUrl();
        } else {
            return queueUrls.get(0);
        }
    }

}