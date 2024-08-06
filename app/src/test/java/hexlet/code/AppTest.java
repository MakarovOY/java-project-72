package hexlet.code;

import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoots;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    Javalin app;
    @BeforeEach
    public final void setUp() throws IOException, SQLException {
        app = App.getApp();
    }
    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
        });
    }
    @Test
    public void testCreateUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://test.com";
            var response = client.post(NamedRoots.urlsPath(), requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://test.com");
            requestBody = "url=http://test.com:8080";
            response = client.post(NamedRoots.urlsPath(), requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("http://test.com:8080");
            requestBody = "url=http://test.com:8080/tail";
            response = client.post(NamedRoots.urlsPath(), requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("http://test.com:8080");

        });
    }
    @Test
    public void testCreateBadUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=someIncorrectString";
            var response = client.post(NamedRoots.urlsPath(), requestBody);
            assertThat(response.code()).isEqualTo(200);
            response = client.get(NamedRoots.urlsPath());
            assertThat(response.body().string()).doesNotContain("someIncorrectString");

        });
    }
}
