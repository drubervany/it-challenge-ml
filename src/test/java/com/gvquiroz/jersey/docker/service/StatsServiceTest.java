package com.gvquiroz.jersey.docker.service;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.gvquiroz.jersey.docker.entities.VerificationStats;
import com.gvquiroz.jersey.docker.utils.ConnectorUtils;
import com.gvquiroz.jersey.docker.utils.InvalidDnaException;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

/**
 * Created by gvquiroz on 24/11/17.
 */
public class StatsServiceTest {

    private AmazonDynamoDB ddb;
    private DynamoDBProxyServer dynamoServer;

    @Before
    public void setUp() throws Exception {

        final String[] localArgs = { "-inMemory" };

        dynamoServer = ServerRunner.createServerFromCommandLineArgs(localArgs);
        dynamoServer.start();

        ddb = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                .build();

        ConnectorUtils.createDbSchema(new DynamoDB(ddb));

    }

    @After
    public void tearDown() throws Exception {
        dynamoServer.stop();
    }

    @Test
    public void getMutantToHumanStats() throws ParseException, InvalidDnaException {

        MutantService mutantService = new MutantServiceImpl();
        StatsService statsService = new StatsServiceImpl();

        String firstHumanDna = "{\"dna\":[\"ACCAGA\",\"CAGAGA\",\"TTATGT\",\"AGAAGG\",\"CACCTA\",\"TCACTG\"]}";

        String secondMutantDna = "{\"dna\":[\"ATGCGA\",\"CAGTGC\",\"AAAAGA\",\"AAAAGA\",\"CACCTA\",\"TCACTG\"]}";

        String firstMutantDna = "{\"dna\":[\"AAAAGA\",\"AAAAGA\",\"TTATGT\",\"AGAAGG\",\"CACCTA\",\"TCACTG\"]}";

        mutantService.isAllowed(firstHumanDna);
        mutantService.isAllowed(secondMutantDna);
        mutantService.isAllowed(firstMutantDna);

        VerificationStats stats = statsService.getHumanToMutantStats();

        assertEquals("2", stats.getMutantCount());
        assertEquals("1", stats.getHumanCount());
        assertEquals("2.0", stats.getRatio());

    }

    @Test
    public void getRatioWhenHumanCountIsZero() throws ParseException, InvalidDnaException {

        MutantService mutantService = new MutantServiceImpl();
        StatsService statsService = new StatsServiceImpl();

        String firstMutantDna = "{\"dna\":[\"AAAAGA\",\"AAAAGA\",\"TTATGT\",\"AGAAGG\",\"CACCTA\",\"TCACTG\"]}";
        String secondMutantDna = "{\"dna\":[\"ATGCGA\",\"CAGTGC\",\"AAAAGA\",\"AAAAGA\",\"CACCTA\",\"TCACTG\"]}";

        mutantService.isAllowed(firstMutantDna);
        mutantService.isAllowed(secondMutantDna);

        VerificationStats stats = statsService.getHumanToMutantStats();

        assertEquals("2", stats.getMutantCount());
        assertEquals("0", stats.getHumanCount());
        assertEquals("2:0", stats.getRatio());
    }
}
