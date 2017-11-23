package com.gvquiroz.jersey.docker.utils;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.Arrays;

/**
 * Created by gvquiroz on 20/11/17.
 */
public class ConnectorUtils {

    public static DynamoDB dbConnector;

    public static DynamoDB getDynamoConnector(){

        if (dbConnector == null){
            AmazonDynamoDB client = AmazonDynamoDBAsyncClientBuilder.standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                    .build();
            dbConnector = new DynamoDB(client);
        }

        return dbConnector;
    }

    public static void createDbSchema(DynamoDB connector){
        ConnectorUtils.createPersonDnaSchema(connector);
        ConnectorUtils.createPersonStatsSchema(connector);
    }

    public static Table createPersonDnaSchema(DynamoDB connector) {

        String tableName = "PersonDna";

        Table table = connector.createTable(tableName,
                Arrays.asList(new KeySchemaElement("DNA", KeyType.HASH)), // Sort key
                Arrays.asList(new AttributeDefinition("DNA", ScalarAttributeType.N)),
                new ProvisionedThroughput(10L, 10L));

        return table;
    }

    public static Table createPersonStatsSchema(DynamoDB connector){

        String tableName = "PersonStats";

        Table table = connector.createTable(tableName,
                Arrays.asList(new KeySchemaElement("Humans", KeyType.HASH)), // Sort key
                Arrays.asList(new AttributeDefinition("Humans", ScalarAttributeType.S)),
                new ProvisionedThroughput(10L, 10L));

        table.putItem(new Item().withPrimaryKey("Humans", "Person").withNumber("Founded", 0));
        table.putItem(new Item().withPrimaryKey("Humans", "Mutant").withNumber("Founded", 0));

        return table;
    }

}
