package service;

import static org.junit.Assert.*;

import com.gvquiroz.jersey.docker.service.MutantService;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by gvquiroz on 15/11/17.
 */
public class MutantServiceTest {

    private MutantService mutantService;

    @Before
    public void setup() {
        mutantService = new MutantService();
    }

    @Test
    public void testGetMessage() {
        assertTrue(mutantService.isMutant());
    }

}
