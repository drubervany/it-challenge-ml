package com.gvquiroz.jersey.docker.service;

/**
 * Created by gvquiroz on 15/11/17.
 */
public interface MutantService {

    boolean isMutant(String[] dna);

    void storeDnaResult(String dna, boolean result);

    boolean isAllowed(String[] dna);
}
