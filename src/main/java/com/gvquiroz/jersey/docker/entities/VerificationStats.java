package com.gvquiroz.jersey.docker.entities;

import java.math.BigDecimal;

/**
 * Created by gvquiroz on 24/11/17.
 */
public class VerificationStats {

    private String ratio;
    private String mutantCount;
    private String humanCount;


    public VerificationStats(String mutantCount, String humanCount, String ratio){
        this.ratio = ratio;
        this.mutantCount = mutantCount;
        this.humanCount = humanCount;
    }

    public String getRatio() {
        return ratio;
    }

    public String getMutantCount() {
        return mutantCount;
    }
    public String getHumanCount() {
        return humanCount;
    }
}
