package com.datayes.cloud;

import com.datayes.cloud.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import ognl.OgnlException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: changhai
 * Date: 13-8-9
 * Time: 下午4:13
 */
public class TenantManager {
    private final OpenstackContext openstackContext;

    public TenantManager(OpenstackContext openstackContext) throws OgnlException, IOException, URISyntaxException {
        this.openstackContext = openstackContext;
        openstackContext.init();
    }

    public Tenant createTenant(String name, String description, boolean enabled) throws IOException {
        Map<String, Tenant> json = new HashMap<String, Tenant>();
        Tenant tenant = new Tenant(name, description, enabled);
        json.put("tenant", tenant);
        HttpPost post = new HttpPost("http://10.20.112.226:35357/v2.0/tenants");
        post.setEntity(new StringEntity(JsonUtil.toJson(json)));
        HttpResponse resp = openstackContext.execute(post);
        InputStream is = resp.getEntity().getContent();
        Map<String, Tenant> map = JsonUtil.toObject(is, new TypeReference<Map<String, Tenant>>() {
        });
        IOUtils.closeQuietly(is);
        return map.get("tenant");
    }

    public List<Tenant> listTenants() throws IOException {
        HttpGet get = new HttpGet("http://10.20.112.226:35357/v2.0/tenants");
        HttpResponse response = openstackContext.execute(get);
        InputStream is = response.getEntity().getContent();
        Map<String, List<Tenant>> map = JsonUtil.toObject(is, new TypeReference<Map<String, List<Tenant>>>() {
        });
        List<Tenant> tenants = map.get("tenants");
        for (Tenant tenant : tenants) {
            System.out.println(tenant);
        }
        IOUtils.closeQuietly(is);
        return tenants;
    }

    public void deleteTenant(String name) throws IOException {
        HttpGet get = new HttpGet("http://10.20.112.226:35357/v2.0/tenants?name=" + name);
        HttpResponse response = openstackContext.execute(get);
        InputStream is = response.getEntity().getContent();
        String content = IOUtils.toString(is);
        System.out.println(content);
        try {
            Map<String, Tenant> map = JsonUtil.toObject(content, new TypeReference<Map<String, Tenant>>() {
            });
            Tenant tenant = map.get("tenant");
            IOUtils.closeQuietly(is);
            HttpDelete delete = new HttpDelete("http://10.20.112.226:35357/v2.0/tenants/" + tenant.getId());
            response = openstackContext.execute(delete);
        } catch (IOException e) {
            // tenant not found
        }
    }
}
