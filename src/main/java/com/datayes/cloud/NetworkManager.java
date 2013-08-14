package com.datayes.cloud;

import com.datayes.cloud.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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

    public void createNetwork(String name, boolean adminStateUp) throws IOException {
        HttpPost post = new HttpPost("http://10.20.112.226:9696/v2.0/networks");
        Map<String, Network> model = new HashMap<String, Network>();
        Network network = new Network(name, adminStateUp);
        model.put("network", network);
        String json = JsonUtil.toJson(model);
        System.out.println(json);
        post.setEntity(new StringEntity(json));
        HttpResponse resp = openstackContext.execute(post);
        InputStream is = resp.getEntity().getContent();
        String content = IOUtils.toString(is);
        IOUtils.closeQuietly(is);
        System.out.println(content);
        Map<String, Network> map = JsonUtil.toObject(content, new TypeReference<Map<String, Network>>() {
        });
        System.out.println(map.get("network"));
    }
}
