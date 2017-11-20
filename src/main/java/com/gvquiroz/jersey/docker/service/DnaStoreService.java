package com.gvquiroz.jersey.docker.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;

/**
 * Created by gvquiroz on 19/11/17.
 */
public interface DnaStoreService {

    CreateTableResult createSchema(AmazonDynamoDB ddb);


}
