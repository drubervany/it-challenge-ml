package com.gvquiroz.jersey.docker.entities;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by gvquiroz on 24/11/17.
 */
public class VerificationStats implements Serializable {

    private String ratio;
    private String mutantCount;
    private String humanCount;

    public VerificationStats(){

    }

    public VerificationStats(String mutantCount, String humanCount, String ratio){
        this.ratio = ratio;
        this.mutantCount = mutantCount;
        this.humanCount = humanCount;
    }

    @JsonProperty("value")
    public String getRatio() {
        return ratio;
    }

    public String getMutantCount() {
        return mutantCount;
    }

    public String getHumanCount() {
        return humanCount;
    }


    public void setHumanCount(String humanCount){
        this.humanCount = humanCount;
    }

    public void setMutantCount(String mutantCount){
        this.mutantCount = mutantCount;
    }
    public void setRatio(String ratio){
        this.ratio = ratio;
    }
}
