package com.gvquiroz.jersey.docker.service;

import com.amazonaws.services.dynamodbv2.document.Table;

/**
 * Created by gvquiroz on 19/11/17.
 */
public interface DnaStoreService {

    void storeDna(String dna, boolean result);

}
