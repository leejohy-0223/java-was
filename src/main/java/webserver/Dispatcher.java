package webserver;

import java.io.IOException;

import config.RequestMapping;
import http.HttpServlet;
import http.Request;
import http.Response;
import webserver.dto.HttpRequestData;
import webserver.dto.HttpRequestLine;

public class Dispatcher {

    private final RequestMapping requestMapping;
    private final ViewResolver viewResolver;

    public Dispatcher(RequestMapping requestMapping, ViewResolver viewResolver) {
        this.requestMapping = requestMapping;
        this.viewResolver = viewResolver;
    }

    public boolean isMappedUrl(String url) {
        return requestMapping.contains(url);
    }

    public Response handleRequest(HttpRequestData requestData) throws IOException {
        HttpRequestLine httpRequestLine = requestData.getHttpRequestLine();
        HttpServlet httpServlet = requestMapping.findHandlerMethod(httpRequestLine.getUrl())
            .orElseThrow(() -> new IllegalStateException("Mapped servlet could not be found"));

        Response response = httpServlet.service(Request.of(requestData), new Response());

        if (response.hasViewName()) {
            String result = viewResolver.resolve(response.getViewName(), response.getModel());
            response.setResponseBody(result);
        }

        return response;
    }
}

