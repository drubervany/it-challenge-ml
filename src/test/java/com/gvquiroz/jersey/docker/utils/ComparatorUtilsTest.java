package com.gvquiroz.jersey.docker.utils;


import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Created by gvquiroz on 15/11/17.
 */
public class ComparatorUtilsTest {
    @Test
    public void comparingSameElements(){
        assertTrue(ComparatorUtils.areEqual('a','a','a','a'));
    }

    @Test
    public void comparingWithOneDifferentElement(){
        assertFalse(ComparatorUtils.areEqual('a','b','a','a'));
    }

    @Test
    public void comparingWithTwoDifferentElement(){
        assertFalse(ComparatorUtils.areEqual('a','a','c','d'));
    }

    @Test
    public void comparingWithLastDifferentElement(){
        assertFalse(ComparatorUtils.areEqual('a','a','a','d'));
    }
}
