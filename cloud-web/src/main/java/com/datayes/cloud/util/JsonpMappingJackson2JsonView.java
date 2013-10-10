package com.datayes.cloud.util;

import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * User: changhai
 * Date: 13-8-20
 * Time: 下午1:53
 * DataYes
 */
public class JsonpMappingJackson2JsonView extends MappingJackson2JsonView {
    public static final String JSONP_CALLBACK_PARAM_NAME = "callback";
    private boolean updateContentLength;
    private String jsonPrefix;

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        OutputStream stream = (this.updateContentLength ? createTemporaryOutputStream() : response.getOutputStream());
        Object value = filterModel(model);
        String jsonpCallback = request.getParameter(JSONP_CALLBACK_PARAM_NAME);
        if (jsonpCallback != null) stream.write((jsonpCallback + "(").getBytes());
        writeContent(stream, value, this.jsonPrefix);
        if (jsonpCallback != null) stream.write((")").getBytes());
        if (this.updateContentLength) {
            writeToResponse(response, (ByteArrayOutputStream) stream);
        }
    }

    @Override
    public void setUpdateContentLength(boolean updateContentLength) {
        this.updateContentLength = updateContentLength;
        super.setUpdateContentLength(updateContentLength);
    }

    public void setJsonPrefix(String jsonPrefix) {
        this.jsonPrefix = jsonPrefix;
        super.setJsonPrefix(jsonPrefix);
    }

    public void setPrefixJson(boolean prefixJson) {
        this.jsonPrefix = (prefixJson ? "{} && " : null);
        super.setPrefixJson(prefixJson);
    }
}
