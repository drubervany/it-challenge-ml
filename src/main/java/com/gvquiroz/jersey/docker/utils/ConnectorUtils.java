package com.gvquiroz.jersey.docker.utils;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

/**
 * Created by gvquiroz on 20/11/17.
 */
public class ConnectorUtils {

    public static DynamoDB dbConnector;

    public static DynamoDB getDynamoConnector(){

        if (dbConnector == null){

            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                    .build();
            dbConnector = new DynamoDB(client);
        }

        return dbConnector;
    }

}
