package com.gvquiroz.jersey.docker.utils;

import static org.junit.Assert.assertEquals;

import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.gvquiroz.jersey.docker.service.DnaStoreService;
import com.gvquiroz.jersey.docker.service.DnaStoreServiceImpl;
import org.junit.Test;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;

/**
 * Created by gvquiroz on 19/11/17.
 */
public class DnaStoreServiceTest {

    @Test
    public void createTableTest() {
        AmazonDynamoDB ddb = DynamoDBEmbedded.create().amazonDynamoDB();

        DnaStoreService storeService = new DnaStoreServiceImpl();

        String tableName = "PersonDna";

        try {

            CreateTableResult res = storeService.createSchema(ddb);

            TableDescription tableDesc = res.getTableDescription();
            assertEquals(tableName, tableDesc.getTableName());
                    tableDesc.getAttributeDefinitions().toString();
            assertEquals(Long.valueOf(1000L), tableDesc.getProvisionedThroughput().getReadCapacityUnits());
            assertEquals(Long.valueOf(1000L), tableDesc.getProvisionedThroughput().getWriteCapacityUnits());
            assertEquals("ACTIVE", tableDesc.getTableStatus());
            assertEquals("arn:aws:dynamodb:ddblocal:000000000000:table/PersonDna", tableDesc.getTableArn());

            ListTablesResult tables = ddb.listTables();
            assertEquals(1, tables.getTableNames().size());
        } finally {
            ddb.shutdown();
        }
    }

}
