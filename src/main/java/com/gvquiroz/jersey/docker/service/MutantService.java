package com.gvquiroz.jersey.docker.service;

import com.gvquiroz.jersey.docker.utils.InvalidDnaException;
import org.json.simple.parser.ParseException;

/**
 * Created by gvquiroz on 15/11/17.
 */
public interface MutantService {

    boolean isMutant(String[] dna);

    boolean isAllowed(String dna) throws ParseException, InvalidDnaException;
}
