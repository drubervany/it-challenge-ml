package com.gvquiroz.jersey.docker.utils;

import static org.junit.Assert.assertEquals;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.gvquiroz.jersey.docker.service.DnaStoreService;
import com.gvquiroz.jersey.docker.service.DnaStoreServiceImpl;
import org.junit.Test;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;

import java.math.BigDecimal;

/**
 * Created by gvquiroz on 19/11/17.
 */
public class DnaStoreServiceTest {

    @Test
    public void storeDnaResultTest() {
        AmazonDynamoDB ddb = DynamoDBEmbedded.create().amazonDynamoDB();

        DynamoDB connector = new DynamoDB(ddb);

        DnaStoreService storeService = new DnaStoreServiceImpl(connector);

        String dnaString = "{\"dna\";[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}";
        boolean result = true;

        try {

            ConnectorUtils.createDbSchema(connector);

            storeService.storeDna(dnaString,result);

            Table table = connector.getTable("PersonDna");
            Item item = table.getItem("DNA", dnaString.hashCode());

            assertEquals(dnaString,item.getString("DNAFull"));
            assertEquals(result,item.getBoolean("isMutant"));

        } finally {
            ddb.shutdown();
        }
    }

    @Test
    public void retrieveDnaResultTest() {
        AmazonDynamoDB ddb = DynamoDBEmbedded.create().amazonDynamoDB();

        DynamoDB connector = new DynamoDB(ddb);

        DnaStoreService storeService = new DnaStoreServiceImpl(connector);

        String dnaString = "{\"dna\";[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}";
        boolean result = true;

        try {

            ConnectorUtils.createDbSchema(connector);

            storeService.storeDna(dnaString,result);

            Table table = connector.getTable("PersonDna");
            Item item = storeService.getDnaResult(dnaString);

            assertEquals(dnaString,item.getString("DNAFull"));
            assertEquals(result,item.getBoolean("isMutant"));

        } finally {
            ddb.shutdown();
        }
    }

    @Test
    public void retrieveMutantStats() {
        AmazonDynamoDB ddb = DynamoDBEmbedded.create().amazonDynamoDB();

        DynamoDB connector = new DynamoDB(ddb);

        DnaStoreService storeService = new DnaStoreServiceImpl(connector);

        String dnaString = "{\"dna\";[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CACCTA\",\"TCACTG\"]}";
        boolean result = false;

        try {

            ConnectorUtils.createDbSchema(connector);
            storeService.storeDna(dnaString,result);

            assertEquals(BigDecimal.ZERO, storeService.getMutantCount());

        } finally {
            ddb.shutdown();
        }
    }

    @Test
    public void retrieveHumanStats() {
        AmazonDynamoDB ddb = DynamoDBEmbedded.create().amazonDynamoDB();

        DynamoDB connector = new DynamoDB(ddb);

        DnaStoreService storeService = new DnaStoreServiceImpl(connector);

        String dnaString = "{\"dna\";[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CACCTA\",\"TCACTG\"]}";
        boolean result = true;

        try {

            ConnectorUtils.createDbSchema(connector);
            storeService.storeDna(dnaString,result);

            assertEquals(BigDecimal.ZERO, storeService.getHumanCount());

        } finally {
            ddb.shutdown();
        }
    }

}
