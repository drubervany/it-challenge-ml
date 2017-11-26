package com.gvquiroz.jersey.docker.api;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.gvquiroz.jersey.docker.utils.ConnectorUtils;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by gvquiroz on 24/11/17.
 */
public class StatsApiTest {
    private HttpServer server;
    private WebTarget target;
    private AmazonDynamoDB ddb;
    private DynamoDBProxyServer dynamoServer;

    @Before
    public void setUp() throws Exception {

        server = Main.startServer();
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);

        final String[] localArgs = {"-inMemory"};

        dynamoServer = ServerRunner.createServerFromCommandLineArgs(localArgs);
        dynamoServer.start();

        ddb = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                .build();

        ConnectorUtils.createDbSchema(new DynamoDB(ddb));

    }

    @After
    public void tearDown() throws Exception {
        dynamoServer.stop();
        server.stop();
    }

    @Test
    public void testGetStats() {

        String responseMsg = target.path("stats").request().get(String.class);

        String countHumanDna = "\"count_human_dna\":\"0\"";
        String countMutantDna = "\"count_mutant_dna\":\"0\"";
        String ratio = "\"ratio\":\"0:0\"";

        assertTrue(responseMsg.contains(countHumanDna));
        assertTrue(responseMsg.contains(countMutantDna));
        assertTrue(responseMsg.contains(ratio));


    }

}
