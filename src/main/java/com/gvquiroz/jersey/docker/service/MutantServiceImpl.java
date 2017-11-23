package com.gvquiroz.jersey.docker.service;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.gvquiroz.jersey.docker.utils.ComparatorUtils;
import com.gvquiroz.jersey.docker.utils.ConnectorUtils;
import com.gvquiroz.jersey.docker.utils.DnaParserUtils;
import com.gvquiroz.jersey.docker.utils.InvalidDnaException;
import org.json.simple.parser.ParseException;

/**
 * Created by gvquiroz on 15/11/17.
 */
public class MutantServiceImpl implements MutantService {

    private DnaStoreService storeClient;

    public MutantServiceImpl(){
        this.storeClient = new DnaStoreServiceImpl(ConnectorUtils.getDynamoConnector());
    }

    public boolean isMutant(String[] dna) {

        int findedDna = 0;

        for (int i = 0; i < dna.length; i++) {
            for (int j = 0; j < dna[i].length() - 4; j++) {
                // horizontal check
                if (ComparatorUtils.areEqual(dna[i].charAt(j), dna[i].charAt(j + 1), dna[i].charAt(j + 2), dna[i].charAt(j + 3))) {
                    findedDna++;
                }

                // Later to this point, vertical checks cant happend
                if (i < dna.length - 4) {
                    // vertical check
                    if (ComparatorUtils.areEqual(dna[i].charAt(j), dna[i + 1].charAt(j), dna[i + 2].charAt(j), dna[i + 3].charAt(j))) {
                        findedDna++;
                    }

                    // oblicuo desc
                    if (ComparatorUtils.areEqual(dna[i].charAt(j), dna[i + 1].charAt(j + 1), dna[i + 2].charAt(j + 2), dna[i + 3].charAt(j + 3))) {
                        findedDna++;
                    }

                }

                if (findedDna >= 2) {
                    return true;
                }

            }
        }

        return false;
    }

    @Override
    public boolean isAllowed(String dna) throws ParseException, InvalidDnaException {

        String[] dnaChain = DnaParserUtils.parseJsonDNAStringArray(dna);

        Item storedDna = storeClient.getDnaResult(dna);

        boolean result;

        // Avoid storing twice the same DNA, retriving it if found
        if (storedDna != null){
            result = storedDna.getBoolean("isMutant");
        } else {
            result = this.isMutant(dnaChain);
            storeClient.storeDna(dna,result);
        }

        return result;

    }

}
