package com.gvquiroz.jersey.docker.utils;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by gvquiroz on 19/11/17.
 */
public class DnaParserUtilsTest {
    @Test
    public void parseDNAJsonToStringArray() throws ParseException, InvalidDnaException {

        String dnaString = "{\"dna\":[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}";

        String[] dnaArray = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"};

        String[] parsedDna = DnaParserUtils.parseJsonDNAStringArray(dnaString);

        assertArrayEquals(dnaArray, parsedDna);

    }

    @Test(expected = InvalidDnaException.class)
    public void dnaJsonWithoutDnaObject() throws ParseException, InvalidDnaException {

        String dnaString = "{\"bad-dna-json\":[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}";

        DnaParserUtils.parseJsonDNAStringArray(dnaString);

    }

    @Test(expected = InvalidDnaException.class)
    public void dnaJsonWithDnaUnitWithLessThanFourLength() throws ParseException, InvalidDnaException {

        String dnaString = "{\"dna\":[\"ATGCGA\",\"CAG\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}";

        DnaParserUtils.parseJsonDNAStringArray(dnaString);

    }

    @Test(expected = InvalidDnaException.class)
    public void dnaJsonNotSquareArray() throws ParseException, InvalidDnaException {

        String dnaString = "{\"dna\":[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\"]}";

        DnaParserUtils.parseJsonDNAStringArray(dnaString);

    }

    @Test(expected = ParseException.class)
    public void dnaWithMalformedJson() throws ParseException, InvalidDnaException {

        String dnaString = "{\"bad-dna-json\";[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}";

        DnaParserUtils.parseJsonDNAStringArray(dnaString);

    }

    @Test()
    public void dnaUnitWithInvalidChar() throws ParseException {

        String invalidDnaUnit = "AJGCGA";

        assertFalse(DnaParserUtils.hasValidCharactersOnDnaUnit(invalidDnaUnit));

    }

    @Test()
    public void dnaUnitWithValidChar() throws ParseException {

        String invalidDnaUnit = "ATGCGA";

        assertTrue(DnaParserUtils.hasValidCharactersOnDnaUnit(invalidDnaUnit));

    }

    @Test(expected = InvalidDnaException.class)
    public void parseDNAJsonWithWrongChar() throws ParseException, InvalidDnaException {

        String dnaString = "{\"dna\":[\"ATGCJA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}";

        DnaParserUtils.parseJsonDNAStringArray(dnaString);

    }

}
