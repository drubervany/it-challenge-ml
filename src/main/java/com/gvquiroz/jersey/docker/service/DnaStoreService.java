package com.gvquiroz.jersey.docker.service;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import java.math.BigDecimal;

/**
 * Created by gvquiroz on 19/11/17.
 */
public interface DnaStoreService {

    void storeDna(String dna, boolean result);

    Item getDnaResult(String dna);

    BigDecimal getMutantCount();

    BigDecimal getHumanCount();

    void incrementHumanCounter();

    void incrementMutantCounter();

}
