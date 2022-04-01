package servlet;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import http.HttpStatus;
import http.Request;
import http.Response;
import http.Session;

class UserListServletTest {
    private final UserListServlet userListServlet = new UserListServlet();

    @Nested
    @DisplayName("/user/list 요청이 들어왔을때")
    class UserListTest {

        @Nested
        @DisplayName("로그인 상태이면")
        class LoggedInTest {

            @Test
            void 회원_목록_페이지로_이동한다() {
                // given
                String expectedViewName = "/user/list.html";
                Request request = new Request();
                request.setSessionId(Session.generateSessionId());
                Response response = new Response();

                // when
                Response userListResult = userListServlet.doGet(request, response);

                // then
                assertThat(userListResult).isNotNull();
                assertThat(userListResult.getHttpStatus()).isEqualTo(HttpStatus.OK);
                assertThat(userListResult.hasViewName()).isTrue();
                assertThat(userListResult.getViewName()).isEqualTo(expectedViewName);
                assertThat(userListResult.getModel()).isNotNull();
                assertThat(userListResult.getModel().get("users")).isNotNull();
            }
        }

        @Nested
        @DisplayName("로그인 상태가 아니면")
        class NotLoggedInTest {

            @Test
            void 로그인_페이지로_이동한다() {
                // given
                String expectedRedirectUrl = "/user/login.html";
                Request request = new Request();
                Response response = new Response();

                // when
                Response userListResult = userListServlet.doGet(request, response);

                // then
                assertThat(userListResult).isNotNull();
                assertThat(userListResult.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
                assertThat(userListResult.getRedirectUrl()).isEqualTo(expectedRedirectUrl);
            }
        }
    }
}
