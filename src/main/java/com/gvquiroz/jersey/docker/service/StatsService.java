package com.gvquiroz.jersey.docker.service;

import com.gvquiroz.jersey.docker.entities.VerificationStats;

/**
 * Created by gvquiroz on 24/11/17.
 */
public interface StatsService {
    VerificationStats getHumanToMutantStats();
}
