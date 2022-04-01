package webserver;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.User;

class ViewResolverTest {

    private ViewResolver viewResolver;
    private Map<String, Object> model;

    @BeforeEach
    void setUp() {
        viewResolver = new ViewResolver("src/test/resources/");
        model = new HashMap<>();
    }

    @Test
    void resolveTest() throws IOException {
        // given
        List<User> users = List.of(new User("id1", "1111", "name1", "a@a"),
            new User("id2", "2222", "name2", "b@b")
        );
        model.put("users", users);

        // when
        String resolve = viewResolver.resolve("viewResolverTest.html", model);

        // then
        for (User user : users) {
            assertThat(resolve.contains(user.getUserId())).isTrue();
            assertThat(resolve.contains(user.getName())).isTrue();
            assertThat(resolve.contains(user.getEmail())).isTrue();
        }
    }
}
