package com.datayes.cloud;

import com.datayes.cloud.access.*;
import com.datayes.cloud.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.SimpleType;
import ognl.Ognl;
import ognl.OgnlException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: changhai
 * Date: 13-8-9
 * Time: 下午4:43
 * DataYes
 */
public class OpenstackContext {
    private static final Logger log = LoggerFactory.getLogger(OpenstackContext.class);
    public static final String IDENTITY = "identity";
    public static final String COMPUTE = "compute";
    public static final String NETWORK = "network";
    public static final String IMAGE = "image";
    public static final String VOLUME = "volume";
    private ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient client = new DefaultHttpClient();
    private String identityServiceUrl;
    private String identityAdminUrl;
    private String username;
    private String password;
    private String tenant;
    private String token;
    private String computeUrl;
    private String networkUrl;
    private String imageUrl;
    private String volumeUrl;

    public OpenstackContext(String identityServiceUrl, String username, String password, String tenant) throws OgnlException, IOException, URISyntaxException {
        this.identityServiceUrl = identityServiceUrl;
        this.username = username;
        this.password = password;
        this.tenant = tenant;
        init();
    }

    private void init() throws IOException, URISyntaxException, OgnlException {
        Access access = post(identityServiceUrl, "auth", new Auth(username, password, tenant), "access", Access.class);
        token = access.getToken().getId();
        List<ServiceCatalog> serviceCatalogs = access.getServiceCatalogs();
        for (ServiceCatalog serviceCatalog : serviceCatalogs) {
            if(typeIs(serviceCatalog, IDENTITY)) identityAdminUrl = getAdminURL(serviceCatalog);
            if(typeIs(serviceCatalog, COMPUTE)) computeUrl = getInternalURL(serviceCatalog);
            if(typeIs(serviceCatalog, NETWORK)) networkUrl = getInternalURL(serviceCatalog);
            if(typeIs(serviceCatalog, IMAGE)) imageUrl = getInternalURL(serviceCatalog);
            if(typeIs(serviceCatalog, VOLUME)) volumeUrl = getInternalURL(serviceCatalog);
        }
        log.debug("token = {}", token );
    }

    private String getInternalURL(ServiceCatalog serviceCatalog) {
        return serviceCatalog.getEndpoints().get(0).getInternalURL();
    }

    private String getAdminURL(ServiceCatalog serviceCatalog) {
        return serviceCatalog.getEndpoints().get(0).getAdminURL();
    }

    private boolean typeIs(ServiceCatalog serviceCatalog, String type) {
        return type.equals(serviceCatalog.getType());
    }

    private Object getValue(String expression, String json) throws IOException, OgnlException {
        Map result = objectMapper.readValue(json, Map.class);
        return Ognl.getValue(expression, result);
    }

    public HttpResponse execute(HttpUriRequest request) throws IOException {
        request.setHeader("X-Auth-Token", token);
        request.setHeader("Content-Type", "application/json");
        return client.execute(request);
    }

    private <T> T getResult(HttpResponse resp, TypeReference<T> t) throws IOException {
        InputStream is = null;
        try {
            is = resp.getEntity().getContent();
            String json = IOUtils.toString(is);
            System.out.println("json convert, json = " + json);
            return JsonUtil.toObject(json, t);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    private <T> T getResult(HttpResponse resp, String name, Class<T> c) throws IOException {
        InputStream is = null;
        try {
            MapType type = MapType.construct(Map.class, SimpleType.construct(String.class), SimpleType.construct(c));
            is = resp.getEntity().getContent();
            String json = IOUtils.toString(is);
            System.out.println("json convert, json = " + json);
            Map<String, T> map = JsonUtil.toObject(json, type);
            return map.get(name);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    private <T> T getResult(HttpResponse resp, String name, JavaType javaType) throws IOException {
        InputStream is = null;
        try {
            MapType type = MapType.construct(Map.class, SimpleType.construct(String.class), javaType);
            is = resp.getEntity().getContent();
            String json = IOUtils.toString(is);
            System.out.println("json convert, json = " + json);
            Map<String, T> map = JsonUtil.toObject(json, type);
            return map.get(name);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    public <T> T post(String url, String requestName, Object requestObject, String responseName, Class<T> responseType) throws IOException {
        HttpPost post = new HttpPost(url);
        Map<String, Object> model = new HashMap<String, Object>();
        model.put(requestName, requestObject);
        String json = JsonUtil.toJson(model);
        System.out.println("request json,url = \n" + url + "\njson = \n" + json + "\nX-Auth-Token = \n" + token);
        post.setEntity(new StringEntity(json));
        return getResult(execute(post), responseName, responseType);
    }

    public <T> T get(String url, TypeReference<T> t) throws IOException {
        HttpGet get = new HttpGet(url);
        HttpResponse resp = execute(get);
        return getResult(resp, t);
    }

    public void put(String url) throws IOException {
        HttpPut put = new HttpPut(url);
        HttpResponse resp = execute(put);
        IOUtils.closeQuietly(resp.getEntity().getContent());
    }

    public User getUser() throws IOException {
        return get(identityAdminUrl + "/users?name=" + username, "user", User.class);
    }

    public Role getRole(String roleName) throws IOException {
        Map<String, List<Role>> map = get(identityAdminUrl + "/OS-KSADM/roles", new TypeReference<Map<String, List<Role>>>() {
        });
        List<Role> roles = map.get("roles");
        for (Role role : roles) {
            if (roleName.equals(role.getName())) {
                return role;
            }
        }
        throw new RuntimeException("role not found, name = " + roleName);
    }

    public <T> T get(String url, String responseName, Class<T> responseType) throws IOException {
        HttpGet get = new HttpGet(url);
        HttpResponse resp = execute(get);
        return getResult(resp, responseName, responseType);
    }

    public <T> T get(String url, String responseName, JavaType javaType) throws IOException {
        HttpGet get = new HttpGet(url);
        HttpResponse resp = execute(get);
        return getResult(resp, responseName, javaType);
    }


    public List<Tenant> listTenants() throws IOException {
        return get(identityAdminUrl + "/tenants", "tenants", CollectionType.construct(List.class, SimpleType.construct(Tenant.class)));
    }

    public Tenant getTenant() throws IOException {
        return getTenant(tenant);
    }

    public void deleteTenant(String name) throws IOException {
        Tenant tenant = getTenant(name);
        if (tenant != null)
            delete(identityAdminUrl + "/tenants/" + tenant.getId());
    }

    private Tenant getTenant(String name) throws IOException {
        return get(identityAdminUrl + "/tenants?name=" + name, "tenant", Tenant.class);
    }

    private void delete(String url) throws IOException {
        HttpDelete delete = new HttpDelete(url);
        HttpResponse resp = execute(delete);
        HttpEntity entity = resp.getEntity();
        if (entity != null)
            IOUtils.closeQuietly(entity.getContent());
    }
}
