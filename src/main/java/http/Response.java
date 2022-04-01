package http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Response {

    private final Map<String, String> header = new HashMap<>();
    private final Map<String, Object> model = new HashMap<>();
    private final List<Cookie> cookies = new ArrayList<>();

    private String viewName;
    private String responseBody;
    private HttpStatus httpStatus;

    public boolean hasResponseBody() {
        return responseBody != null && !responseBody.equals("");
    }

    // public methods
    public String findHeader(String key) {
        return header.get(key);
    }

    public Set<String> headerKeySet() {
        return header.keySet();
    }

    public void addModel(String name, Object value) {
        model.put(name, value);
    }

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public boolean hasViewName() {
        return viewName != null && !viewName.equals("");
    }

    public String getRedirectUrl() {
        return header.get(HttpHeaders.LOCATION.getName());
    }

    public void setRedirectUrl(String redirectUrl) {
        header.put(HttpHeaders.LOCATION.getName(), redirectUrl);
    }

    // getter setter
    public Map<String, Object> getModel() {
        return model;
    }

    public List<Cookie> getCookies() {
        return Collections.unmodifiableList(cookies);
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
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
}
