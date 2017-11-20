package com.gvquiroz.jersey.docker.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gvquiroz on 19/11/17.
 */
public class DnaStoreServiceImpl implements DnaStoreService {

    @Override
    public CreateTableResult createSchema(AmazonDynamoDB ddb) {
        List<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
        attributeDefinitions.add(new AttributeDefinition("DNA", ScalarAttributeType.N));

        List<KeySchemaElement> ks = new ArrayList<KeySchemaElement>();
        ks.add(new KeySchemaElement("DNA", KeyType.HASH));

        String tableName = "PersonDna";

        ProvisionedThroughput provisionedthroughput = new ProvisionedThroughput(1000L, 1000L);

        CreateTableRequest request =
                new CreateTableRequest()
                        .withTableName(tableName)
                        .withAttributeDefinitions(attributeDefinitions)
                        .withKeySchema(ks)
                        .withProvisionedThroughput(provisionedthroughput);

        return ddb.createTable(request);
    }
}
