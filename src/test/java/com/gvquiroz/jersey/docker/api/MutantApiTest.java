package com.gvquiroz.jersey.docker.api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.json.simple.JSONArray;

import static org.junit.Assert.assertEquals;

public class MutantApiTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {

        server = Main.startServer();
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);

    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void testIsNotMutant() {

        JSONObject json = new JSONObject();

        JSONArray jsonArray = new JSONArray();
        jsonArray.add("AADAGA");
        jsonArray.add("CCGAGC");
        jsonArray.add("TAATGT");
        jsonArray.add("AADTGG");
        jsonArray.add("CTTATA");
        jsonArray.add("TCATTG");

        json.put("dna",jsonArray);

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
}
