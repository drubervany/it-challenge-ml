package com.gvquiroz.jersey.docker.service;

import com.gvquiroz.jersey.docker.utils.ComparatorUtils;

/**
 * Created by gvquiroz on 15/11/17.
 */
public class MutantServiceImpl implements MutantService{

    public boolean isMutant(String[] dna) {

        if(!this.isValidDna(dna)){
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < dna.length; i++) {
            for (int j = 0; j < dna[i].length() - 4 ; j++) {
                // horizontal check
                if (ComparatorUtils.areEqual(dna[i].charAt(j), dna[i].charAt(j + 1), dna[i].charAt(j + 2), dna[i].charAt(j + 3))){
                    return true;
                }

                // Later to this point, vertical checks cant happend
                if (i < dna.length - 4){
                    // vertical check
                    if (ComparatorUtils.areEqual(dna[i].charAt(j),dna[i+1].charAt(j),dna[i+2].charAt(j),dna[i+3].charAt(j))){
                        return true;
                    }

                    // oblicuo desc
                    if(ComparatorUtils.areEqual(dna[i].charAt(j),dna[i+1].charAt(j+1),dna[i+2].charAt(j+2),dna[i+3].charAt(j+3))){
                        return true;
                    }

                }
            }
        }

        return false;
    }

    private boolean isValidDna(String[] dna){
        if (dna.length == 0){
            return false;
        }
        return true;
    }

}
