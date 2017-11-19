package com.gvquiroz.jersey.docker.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by gvquiroz on 15/11/17.
 */
public class ComparatorUtils {

    public static boolean areEqual(char a, char b, char c, char d) {
        return a == b && b == c && c == d;
    }

}
