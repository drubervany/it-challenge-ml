package com.gvquiroz.jersey.docker.utils;


import static org.junit.Assert.*;

import org.json.simple.parser.ParseException;
import org.junit.Test;

/**
 * Created by gvquiroz on 15/11/17.
 */
public class MutantServiceUtilsTest {
    @Test
    public void comparingSameElements(){
        assertTrue(MutantServiceUtils.areEqual('a','a','a','a'));
    }

    @Test
    public void comparingWithOneDifferentElement(){
        assertFalse(MutantServiceUtils.areEqual('a','b','a','a'));
    }

    @Test
    public void comparingWithTwoDifferentElement(){
        assertFalse(MutantServiceUtils.areEqual('a','a','c','d'));
    }

    @Test
    public void comparingWithLastDifferentElement(){
        assertFalse(MutantServiceUtils.areEqual('a','a','a','d'));
    }

    @Test
    public void parseDNAJsonToStringArray() throws ParseException {

        String dnaString = "{\"dna\":[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}";

        String[] dnaArray = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"};

        String[] parsedDna = MutantServiceUtils.parseJsonDNAStringArray(dnaString);

        assertArrayEquals(dnaArray,parsedDna);

    }

    @Test(expected = IllegalArgumentException.class)
    public void dnaJsonWithoutDnaObject() throws ParseException {

        String dnaString = "{\"bad-dna-json\":[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}";

        MutantServiceUtils.parseJsonDNAStringArray(dnaString);

    }

    @Test(expected = IllegalArgumentException.class)
    public void dnaJsonWithDnaUnitWithLessThanFourLength() throws ParseException {

        String dnaString = "{\"dna\":[\"ATGCGA\",\"CAG\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}";

        MutantServiceUtils.parseJsonDNAStringArray(dnaString);

    }

    @Test(expected = ParseException.class)
    public void dnaWithMalformedJson() throws ParseException {

        String dnaString = "{\"bad-dna-json\";[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}";

        MutantServiceUtils.parseJsonDNAStringArray(dnaString);

    }

}
