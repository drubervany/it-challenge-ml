package com.gvquiroz.jersey.docker.service;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.gvquiroz.jersey.docker.entities.VerificationStats;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
        this.incrementStatsCounter(result);
    }

    private void incrementStatsCounter(boolean result) {

        // is a muntat!
        if(result){
            this.incrementMutantCounter();
        } else {
            this.incrementHumanCounter();
        }

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

    @Override
    public VerificationStats getHumanToMutantRatio() {

        BigDecimal mutantCount = this.getMutantCount();
        BigDecimal humanCount = this.getHumanCount();
        BigDecimal ratio = mutantCount.divide(humanCount,1,BigDecimal.ROUND_HALF_EVEN);

        return new VerificationStats(mutantCount, humanCount, ratio);
    }

    private void incrementHumanCounter() {
        Table statsTable = this.dynamoClient.getTable("PersonStats");
        Map<String,String> expressionAttributeNames = new HashMap<>();
        expressionAttributeNames.put("#p", "Founded");

        Map<String,Object> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":val", 1);

        UpdateItemOutcome mutantOutcome = statsTable.updateItem(
                "Humans", "Person",
                "set #p = #p + :val",
                expressionAttributeNames,
                expressionAttributeValues);
    }

    private void incrementMutantCounter() {
        Table statsTable = this.dynamoClient.getTable("PersonStats");
        Map<String,String> expressionAttributeNames = new HashMap<>();
        expressionAttributeNames.put("#p", "Founded");

        Map<String,Object> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":val", 1);

        UpdateItemOutcome mutantOutcome = statsTable.updateItem(
                "Humans", "Mutant",
                "set #p = #p + :val",
                expressionAttributeNames,
                expressionAttributeValues);
    }



}
