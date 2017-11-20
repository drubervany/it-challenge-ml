package com.gvquiroz.jersey.docker.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.gvquiroz.jersey.docker.service.MutantService;
import com.gvquiroz.jersey.docker.service.MutantServiceImpl;
import com.gvquiroz.jersey.docker.utils.DnaParserUtils;
import com.gvquiroz.jersey.docker.utils.InvalidDnaException;
import org.json.simple.parser.ParseException;

@Path("mutant")
public class MutantApi {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response isMutant(String dna) {

        MutantService mutantService = new MutantServiceImpl();

        boolean isMutant;

        try {

            isMutant = mutantService.isAllowed(dna);

        } catch (ParseException | InvalidDnaException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (isMutant) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

    }
}
