package com.gvquiroz.jersey.docker.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by gvquiroz on 19/11/17.
 */
public class DnaParserUtils {

    public static String[] parseJsonDNAStringArray(String stringToParse) throws ParseException {

        JSONParser parser = new JSONParser();

        String[] parsedDNAArray = null;

        JSONObject jsonObject = (JSONObject) parser.parse(stringToParse);

        JSONArray dnaArray = (JSONArray) jsonObject.get("dna");

        if (dnaArray == null) {
            throw new IllegalArgumentException();
        }

        parsedDNAArray = new String[dnaArray.size()];
        for (int i = 0; i < dnaArray.size(); i++) {

            String dnaUnit = (String) dnaArray.get(i);

            if (dnaUnit.length() < 4) {
                throw new IllegalArgumentException();
            }

            if (dnaUnit.length() != dnaArray.size()) {
                throw new IllegalArgumentException();
            }

            if(!hasValidCharactersOnDnaUnit(dnaUnit)){
                throw new IllegalArgumentException();
            }

            parsedDNAArray[i] = dnaUnit;
        }

        return parsedDNAArray;

    }

    public static boolean hasValidCharactersOnDnaUnit(String dnaUnit){
        String regex = "[ATGC]+";
        return dnaUnit.matches(regex);
    }
}
