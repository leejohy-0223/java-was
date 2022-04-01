package http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Response {
    private final static String LOCATION_HEADER = "Location";

    private final Map<String, String> header = new HashMap<>();
    private final List<Cookie> cookies = new ArrayList<>();
    private final Map<String, Object> model = new HashMap<>();

    private String viewName;
    private String responseBody;
    private HttpStatus httpStatus;

    public boolean hasResponseBody() {
        return responseBody != null && !responseBody.equals("");
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getRedirectUrl() {
        return header.get(LOCATION_HEADER);
    }

    public void setRedirectUrl(String redirectUrl) {
        header.put(LOCATION_HEADER, redirectUrl);
    }

    public String getViewName() {
        return viewName;
    }

    public void addModel(String name, Object value) {
        model.put(name, value);
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public void addHeader(String key, String value) {
        header.put(key, value);
    }

    public String findHeader(String key) {
        return header.get(key);
    }

    public Set<String> headerKeySet() {
        return header.keySet();
    }

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public List<Cookie> getCookies() {
        return Collections.unmodifiableList(cookies);
    }

    public boolean hasViewName() {
        return viewName != null && !viewName.equals("");
    }
}
