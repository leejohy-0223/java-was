package servlet;

import java.util.ArrayList;
import java.util.List;

import db.DataBase;
import http.HttpServlet;
import http.HttpStatus;
import http.Request;
import http.Response;
import http.Session;
import model.User;
import servlet.dto.UserListDto;

public class UserListServlet extends HttpServlet {

    @Override
    public Response doGet(Request request, Response response) {
        String sessionId = request.getSessionId();
        if (sessionId == null || !Session.isSessionIdExist(sessionId)) {
            return redirectLogin(response);
        }
        return forwardToUserList(response);
    }

    private Response redirectLogin(Response response) {
        response.setHttpStatus(HttpStatus.FOUND);
        response.setRedirectUrl("/user/login.html");
        return response;
    }

    private Response forwardToUserList(Response response) {
        List<User> users = DataBase.findAll();
        List<UserListDto> userDtoList = convertToDtoList(users);

        response.setHttpStatus(HttpStatus.OK);
        response.setViewName("/user/list.html");
        response.addModel("users", userDtoList);
        return response;
    }

    private List<UserListDto> convertToDtoList(List<User> users) {
        List<UserListDto> userDtoList = new ArrayList<>();
        int idx = 1;
        for (User user : users) {
            UserListDto userListDto = new UserListDto(user);
            userListDto.setIndex(idx++);
            userDtoList.add(userListDto);
        }
        return userDtoList;
    }
}
