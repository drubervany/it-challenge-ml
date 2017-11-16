package com.gvquiroz.jersey.docker.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by gvquiroz on 15/11/17.
 */
public class MutantServiceTest {

    private MutantService mutantService;

    @Before
    public void setup() {
        mutantService = new com.gvquiroz.jersey.docker.service.MutantServiceImpl();
    }

    @Test
    public void mutantDnaVertical() {

        String[] dna = {
                "AADAGA",
                "CCGAGC",
                "TAATGT",
                "AADTGG",
                "CTTTTA",
                "TCATTG"
        };

        assertTrue(mutantService.isMutant(dna));
    }

    @Test
    public void mutantNotFound() {

        String[] dna = {
                "AADAGA",
                "CCGAGC",
                "TAATGT",
                "AADTGG",
                "CTTATA",
                "TCATTG"
        };

        assertFalse(mutantService.isMutant(dna));
    }


}
