package com.datayes.cloud.openstack;

import com.datayes.cloud.openstack.access.Network;
import com.datayes.cloud.openstack.access.Subnet;
import com.datayes.cloud.util.DeleteUtil;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.SimpleType;

import java.io.IOException;
import java.util.List;

/**
 * User: changhai
 * Date: 13-8-12
 * Time: 下午3:43
 * DataYes
 */
public class NetworkManager {
    private final OpenstackContext ctx;

    public NetworkManager(OpenstackContext openstackContext) {
        this.ctx = openstackContext;
    }

    public Network createNetwork(String name, boolean adminStateUp) throws IOException {
        Network newNetwork = new Network(name, adminStateUp);
        return ctx.post(ctx.getNetworkUrl() + "v2.0/networks", "network", newNetwork, "network", Network.class);
    }

    public Subnet createSubnet(Network network) throws IOException {
        Subnet netSubnet = new Subnet(network.getId(), "10.0.0.0/24", "10.0.0.2", "10.0.0.254");
        return ctx.post(ctx.getNetworkUrl() + "v2.0/subnets", "subnet", netSubnet, "subnet", Subnet.class);
    }

    public List<Network> listNetworks() throws IOException {
        return ctx.get(ctx.getNetworkUrl() + "v2.0/networks", "networks", CollectionType.construct(List.class, SimpleType.construct(Network.class)));
    }

    public void deleteNetwork(final String networkId) throws IOException {
        ctx.delete(ctx.getNetworkUrl() + "v2.0/networks/" + networkId);
        DeleteUtil.waitStatus(new DeleteUtil.StatusHandler<Network>() {
                                  @Override
                                  public Network getStatus() throws IOException {
                                      return ctx.get(ctx.getNetworkUrl() + "/v2.0/networks/" + networkId, "network", Network.class);
                                  }
                              }, new DeleteUtil.StatusChecker<Network>() {
                                  @Override
                                  public boolean checkStatus(Network network) throws IOException {
                                      return network == null;
                                  }
                              }
        );
    }
}
