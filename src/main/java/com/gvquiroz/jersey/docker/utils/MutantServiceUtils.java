package com.gvquiroz.jersey.docker.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by gvquiroz on 15/11/17.
 */
public class MutantServiceUtils {

    public static boolean areEqual(char a, char b, char c, char d){
        return a == b && b == c && c == d;
    }

    public static String [] parseJsonDNAStringArray(String stringToParse){

        JSONParser parser = new JSONParser();

        String[] parsedDNAArray = null;

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(stringToParse);

            JSONArray dnaArray = (JSONArray) jsonObject.get("dna");

            parsedDNAArray = new String[dnaArray.size()];

            for (int i = 0; i < dnaArray.size(); i++) {
                parsedDNAArray[i] = (String) dnaArray.get(i);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return parsedDNAArray;

    }

}
