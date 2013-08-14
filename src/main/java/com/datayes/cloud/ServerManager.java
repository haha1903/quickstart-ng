package com.datayes.cloud;

import java.io.IOException;

/**
 * User: changhai
 * Date: 13-8-13
 * Time: 上午11:15
 * DataYes
 */
public class ServerManager {
    private final OpenstackContext openstackContext;

    public ServerManager(OpenstackContext openstackContext) {
        this.openstackContext = openstackContext;
    }

    public void createServer(String tenantName, Server server) throws IOException {
        Tenant tenant = openstackContext.getTenant(tenantName);
    }
}
