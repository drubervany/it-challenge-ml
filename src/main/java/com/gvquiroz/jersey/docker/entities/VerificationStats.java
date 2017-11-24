package com.gvquiroz.jersey.docker.entities;

import java.math.BigDecimal;

/**
 * Created by gvquiroz on 24/11/17.
 */
public class VerificationStats {

    private BigDecimal ratio;
    private BigDecimal mutantCount;
    private BigDecimal humanCount;


    public VerificationStats(BigDecimal mutantCount, BigDecimal humanCount, BigDecimal ratio){
        this.ratio = ratio;
        this.mutantCount = mutantCount;
        this.humanCount = humanCount;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public BigDecimal getMutantCount() {
        return mutantCount;
    }
    public BigDecimal getHumanCount() {
        return humanCount;
    }
}
