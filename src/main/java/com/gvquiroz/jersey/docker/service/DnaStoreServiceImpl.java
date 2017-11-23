package com.gvquiroz.jersey.docker.service;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import java.math.BigDecimal;

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

    @Override
    public Item getDnaResult(String dna) {
        Table table = this.dynamoClient.getTable("PersonDna");
        return table.getItem("DNA", dna.hashCode());
    }

    @Override
    public BigDecimal getMutantCount() {
        Table table = this.dynamoClient.getTable("PersonStats");
        return table.getItem("Humans", "Mutant").getNumber("Founded");
    }

    @Override
    public BigDecimal getHumanCount() {
        Table table = this.dynamoClient.getTable("PersonStats");

        return table.getItem("Humans", "Person").getNumber("Founded");
    }
}
