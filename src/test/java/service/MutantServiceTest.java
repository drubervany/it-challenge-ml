package service;

import static org.junit.Assert.*;

import com.gvquiroz.jersey.docker.service.MutantService;
import com.gvquiroz.jersey.docker.service.MutantServiceImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by gvquiroz on 15/11/17.
 */
public class MutantServiceTest {

    private MutantService mutantService;

    @Before
    public void setup() {
        mutantService = new com.gvquiroz.jersey.docker.service.MutantServiceImpl();
    }

    @Test
    public void mutantDnaVertical() {

        String[] dna = {
                "AADAGA",
                "CCGAGC",
                "TAATGT",
                "AADTGG",
                "CTTTTA",
                "TCATTG"
        };

        assertEquals(true,mutantService.isMutant(dna));
    }

}
