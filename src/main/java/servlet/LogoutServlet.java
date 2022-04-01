package servlet;

import http.Cookie;
import http.HttpServlet;
import http.HttpStatus;
import http.Request;
import http.Response;
import http.Session;

public class LogoutServlet extends HttpServlet {

    @Override
    public Response doGet(Request request, Response response) {
        response.setHttpStatus(HttpStatus.FOUND);
        response.setRedirectUrl("/index.html");

        String sessionId = request.getSessionId();

        if (sessionId != null && Session.isSessionIdExist(sessionId)) {
            Session.invalidateSession(sessionId);
            Cookie sessionIdCookie = new Cookie("sessionId", sessionId);
            sessionIdCookie.setMaxAge(0);
            sessionIdCookie.setPath("/");
            response.addCookie(sessionIdCookie);
        }

        return response;
    }
}
