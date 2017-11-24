package com.gvquiroz.jersey.docker.api;

import com.gvquiroz.jersey.docker.entities.VerificationStats;
import com.gvquiroz.jersey.docker.service.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by gvquiroz on 24/11/17.
 */
@Path("stats")
public class StatisApi {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public VerificationStats stats() {

        StatsService statsService = new StatsServiceImpl();

        VerificationStats stats = statsService.getHumanToMutantStats();

        return stats;

    }
}
