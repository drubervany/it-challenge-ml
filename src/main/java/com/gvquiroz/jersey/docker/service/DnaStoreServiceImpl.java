package com.gvquiroz.jersey.docker.service;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.Arrays;

/**
 * Created by gvquiroz on 19/11/17.
 */
public class DnaStoreServiceImpl implements DnaStoreService {

    private DynamoDB dynamoClient;

    public DnaStoreServiceImpl (DynamoDB ddb){
        this.dynamoClient = ddb;
    }

    @Override
    public void storeDna(String dna, boolean result) {
        Table table = this.dynamoClient.getTable("PersonDna");
        table.putItem(new Item().withPrimaryKey("DNA", dna.hashCode()).withBoolean("isMutant", result).withString("DNAFull",dna));
    }
}
