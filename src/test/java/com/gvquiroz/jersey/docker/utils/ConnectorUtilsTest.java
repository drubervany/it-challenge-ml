package com.gvquiroz.jersey.docker.utils;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by gvquiroz on 20/11/17.
 */
public class ConnectorUtilsTest {

    @Test
    public void createSchemaTest() {
        AmazonDynamoDB ddb = DynamoDBEmbedded.create().amazonDynamoDB();

        DynamoDB connector = new DynamoDB(ddb);

        String tableName = "PersonDna";

        try {

            Table res = ConnectorUtils.createPersonDnaSchema(connector);

            TableDescription tableDesc = res.describe();
            assertEquals(tableName, tableDesc.getTableName());
            tableDesc.getAttributeDefinitions().toString();
            assertEquals(Long.valueOf(10L), tableDesc.getProvisionedThroughput().getReadCapacityUnits());
            assertEquals(Long.valueOf(10L), tableDesc.getProvisionedThroughput().getWriteCapacityUnits());
            assertEquals("ACTIVE", tableDesc.getTableStatus());
            assertEquals("arn:aws:dynamodb:ddblocal:000000000000:table/PersonDna", tableDesc.getTableArn());

            ListTablesResult tables = ddb.listTables();
            assertEquals(1, tables.getTableNames().size());
        } finally {
            ddb.shutdown();
        }
    }
}
