package http;

public enum HttpHeaders {
    LOCATION("Location"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    SET_COOKIE("Set-Cookie");

    private final String name;

    HttpHeaders(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
