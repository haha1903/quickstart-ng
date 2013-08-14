package com.datayes.cloud;

import com.datayes.cloud.access.Auth;
import com.datayes.cloud.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import ognl.Ognl;
import ognl.OgnlException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * User: changhai
 * Date: 13-8-9
 * Time: 下午4:43
 * DataYes
 */
public class OpenstackContext {
    private ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient client = new DefaultHttpClient();
    private String identityServiceUrl;
    private String username;
    private String password;
    private String tenant;
    private String token;

    public OpenstackContext(String username, String password, String tenant) {
        this.username = username;
        this.password = password;
        this.tenant = tenant;
    }

    public void init() throws IOException, URISyntaxException, OgnlException {
        HttpPost post = new HttpPost();
        post.setURI(new URI(identityServiceUrl + "/tokens"));
        post.setHeader("Content-Type", "application/json");
        Auth auth = new Auth(username, password, tenant);
        post.setEntity(new StringEntity(JsonUtil.toJson(auth)));
        HttpResponse response = client.execute(post);
        InputStream is = response.getEntity().getContent();
        token = (String) getValue("access.token.id", is);
        IOUtils.closeQuietly(is);
        System.out.println(token);
    }

    private Object getValue(String expression, InputStream is) throws IOException, OgnlException {
        Map result = objectMapper.readValue(is, Map.class);
        return Ognl.getValue(expression, result);
    }

    public HttpResponse execute(HttpUriRequest request) throws IOException {
        request.setHeader("X-Auth-Token", token);
        request.setHeader("Content-Type", "application/json");
        return client.execute(request);
    }

    public void setIdentityServiceUrl(String identityServiceUrl) {
        this.identityServiceUrl = identityServiceUrl;
    }
}
