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
    public void mutantDnaHorizontalFound() {

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


    @Test
    public void mutantDnaVerticalFound() {

        String[] dna = {
                "AADAGA",
                "ACGAGC",
                "AAATGT",
                "AADTGG",
                "CTTATA",
                "TCATTG"
        };

        assertTrue(mutantService.isMutant(dna));
    }

    @Test
    public void mutantDnaVerticalFoundEdge() {

        String[] dna = {
                "ATAAGA",
                "TTATGC",
                "DTCAGT",
                "ATAAGA",
                "CTTATT",
                "TCATTT"
        };

        assertTrue(mutantService.isMutant(dna));
    }

    @Test
    public void mutantDnaVerticalFoundRightSide() {

        String[] dna = {
                "ATAAGA",
                "TTATGA",
                "DTCTGA",
                "ATATGA",
                "CTTATT",
                "TCATTT"
        };

        assertTrue(mutantService.isMutant(dna));
    }

    @Test(expected = IllegalArgumentException.class)
    public void humanWithEmptyDna() {

        String[] dna = {};

        assertTrue(mutantService.isMutant(dna));
    }

}
