package com.gvquiroz.jersey.docker.api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.gvquiroz.jersey.docker.utils.ConnectorUtils;
import org.glassfish.grizzly.http.server.HttpServer;

import org.json.simple.JSONObject;
import org.junit.*;

import org.json.simple.JSONArray;

import static org.junit.Assert.assertEquals;

public class MutantApiTest {

    private static HttpServer server;
    private static WebTarget target;
    private static AmazonDynamoDB ddb;
    private static DynamoDBProxyServer dynamoServer;

    @BeforeClass
    public static void setUp() throws Exception {

        server = Main.startServer();
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);

        final String[] localArgs = { "-inMemory" };

        dynamoServer = ServerRunner.createServerFromCommandLineArgs(localArgs);
        dynamoServer.start();

        ddb = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                .build();

        ConnectorUtils.createPersonDnaSchema(new DynamoDB(ddb));

    }

    @AfterClass
    public static void tearDown() throws Exception {
        dynamoServer.stop();
        server.stop();
    }

    @Test
    public void testIsNotMutant() {

        JSONObject json = new JSONObject();

        JSONArray jsonArray = new JSONArray();
        jsonArray.add("ACAAGA");
        jsonArray.add("CAGTGC");
        jsonArray.add("TTATGT");
        jsonArray.add("AGAAGG");
        jsonArray.add("CCCATA");
        jsonArray.add("TCACTG");

        json.put("dna", jsonArray);

        Response responseMsg = target.path("mutant").request().post(Entity.json(json.toString()));
        assertEquals(403, responseMsg.getStatus());

    }

    @Test
    public void testIsMutant() {

        JSONObject json = new JSONObject();

        JSONArray jsonArray = new JSONArray();
        jsonArray.add("AAAAGA");
        jsonArray.add("CAGTGC");
        jsonArray.add("TTATGT");
        jsonArray.add("AGAAGG");
        jsonArray.add("CCCATA");
        jsonArray.add("TCACTG");

        json.put("dna",jsonArray);

        Response responseMsg = target.path("mutant").request().post(Entity.json(json.toString()));
        assertEquals(200, responseMsg.getStatus());

    }

    @Test
    public void testBadRequestMalformedJson() {

        String dnaString = "{\"bad-dna-json\";[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}";

        Response responseMsg = target.path("mutant").request().post(Entity.json(dnaString));
        assertEquals(400, responseMsg.getStatus());

    }

    @Test
    public void testBadRequestInvalidCharDNA() {

        JSONObject json = new JSONObject();

        JSONArray jsonArray = new JSONArray();
        jsonArray.add("ZZZZZZ");
        jsonArray.add("CAGTGC");
        jsonArray.add("TTATGT");
        jsonArray.add("AGAAGG");
        jsonArray.add("CCCATA");
        jsonArray.add("TCACTG");

        json.put("dna", jsonArray);

        Response responseMsg = target.path("mutant").request().post(Entity.json(json.toString()));
        assertEquals(400, responseMsg.getStatus());

    }


}
