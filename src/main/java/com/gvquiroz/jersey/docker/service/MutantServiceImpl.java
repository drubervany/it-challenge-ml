package com.gvquiroz.jersey.docker.service;

import com.gvquiroz.jersey.docker.utils.ComparatorUtils;

/**
 * Created by gvquiroz on 15/11/17.
 */
public class MutantServiceImpl implements MutantService{

    public boolean isMutant(String[] dna) {

        for (int i = 0; i < dna.length; i++) {
            for (int j = 0; j < dna[i].length() - 4 ; j++) {
                // horizontal check
                if (ComparatorUtils.areEqual(dna[i].charAt(j), dna[i].charAt(j + 1), dna[i].charAt(j + 2), dna[i].charAt(j + 3))){
                    return true;
                }
            }
        }

        return false;
    }

}
