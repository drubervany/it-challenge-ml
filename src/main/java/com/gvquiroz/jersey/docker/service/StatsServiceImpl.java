package com.gvquiroz.jersey.docker.service;

import com.gvquiroz.jersey.docker.entities.VerificationStats;
import com.gvquiroz.jersey.docker.utils.ConnectorUtils;

import java.math.BigDecimal;

/**
 * Created by gvquiroz on 24/11/17.
 */
public class StatsServiceImpl implements StatsService {

    private DnaStoreService storeService;

    public StatsServiceImpl(){
         storeService = new DnaStoreServiceImpl(ConnectorUtils.getDynamoConnector());
    }

    @Override
    public VerificationStats getHumanToMutantStats() {

        BigDecimal mutantCount = storeService.getMutantCount();
        BigDecimal humanCount = storeService.getHumanCount();

        String ratio;

        if(humanCount.equals(BigDecimal.ZERO)){
            ratio = mutantCount + ":0";
        } else {
            ratio = mutantCount.divide(humanCount,1,BigDecimal.ROUND_HALF_EVEN).toString();
        }

        return new VerificationStats(mutantCount.toString(), humanCount.toString(), ratio);
    }
}
