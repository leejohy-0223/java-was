package config;

import servlet.LoginServlet;
import servlet.LogoutServlet;
import servlet.UserCreateServlet;
import servlet.UserListServlet;

public class RequestMappingFactory {
    private RequestMappingFactory() {}

    public static RequestMapping createRequestMapping() {
        return new RequestMapping.Builder()
            .addMapping("/user/create", new UserCreateServlet())
            .addMapping("/user/login", new LoginServlet())
            .addMapping("/user/logout", new LogoutServlet())
            .addMapping("/user/list", new UserListServlet())
            .build();
    }
}
