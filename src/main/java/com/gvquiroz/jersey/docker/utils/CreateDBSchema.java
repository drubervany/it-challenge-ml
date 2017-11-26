package com.gvquiroz.jersey.docker.utils;

import java.io.IOException;

/**
 * Created by gvquiroz on 26/11/17.
 */
public class CreateDBSchema {
    public static void main(String[] args) throws IOException {
        ConnectorUtils.createDbSchema(ConnectorUtils.getDynamoConnector());
    }
}
