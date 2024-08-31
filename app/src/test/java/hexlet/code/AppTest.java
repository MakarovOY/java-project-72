package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoots;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    private static Javalin app;
    private static MockWebServer mockWebServer;
    @BeforeAll
    public static void setUpMockWebServer() throws IOException, SQLException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }
    @AfterAll
    public static void afterAll() throws IOException {
        mockWebServer.shutdown();
    }
    @BeforeEach
    public void setUp() throws IOException, SQLException {
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
    public void testCreateIncorrectUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=someIncorrectString";
            var response = client.post(NamedRoots.urlsPath(), requestBody);
            assertThat(response.code()).isEqualTo(200);
            response = client.get(NamedRoots.urlsPath());
            assertThat(response.body().string()).doesNotContain("someIncorrectString");

        });
    }
    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoots.urlsPath());
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testShowUrl() throws SQLException {
        var url = new Url("https://showurl.com");
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoots.urlPath(url.getId()));
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://showurl.com");
        });
    }
    @Test
    void testUrlChecks() throws SQLException {

        mockWebServer.enqueue(new MockResponse().setResponseCode(200).
                setBody("<title>title test</title>"
                        + "<h1>h1 test</h1>"
                        + "<meta name=\"description\" content=\"description test\">"));
        String urlName = mockWebServer.url("/").toString().replaceAll("/$", "");
        Url url = new Url(urlName);
        UrlRepository.save(url);

        JavalinTest.test(app, (server1, client) -> {
            var response = client.post(NamedRoots.urlChecksPath(url.getId()));
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("h1 test").contains("description test")
                    .contains("title test");
        });
    }
}
