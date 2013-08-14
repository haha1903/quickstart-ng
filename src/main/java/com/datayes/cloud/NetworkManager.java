package com.datayes.cloud;

import com.datayes.cloud.access.Network;
import com.datayes.cloud.access.Subnet;

import java.io.IOException;

/**
 * User: changhai
 * Date: 13-8-12
 * Time: 下午3:43
 * DataYes
 */
public class NetworkManager {
    private final OpenstackContext openstackContext;

    public NetworkManager(OpenstackContext openstackContext) {
        this.openstackContext = openstackContext;
    }

    public Network createNetwork(String name, boolean adminStateUp) throws IOException {
        Network newNetwork = new Network(name, adminStateUp);
        return openstackContext.post("http://10.20.112.226:9696/v2.0/networks", "network", newNetwork, "network", Network.class);
    }

    public Subnet createSubnet(Network network) throws IOException {
        Subnet netSubnet = new Subnet(network.getId(), "10.0.0.0/24", "10.0.0.2", "10.0.0.254");
        return openstackContext.post("http://10.20.112.226:9696/v2.0/subnets", "subnet", netSubnet, "subnet", Subnet.class);
    }
}
