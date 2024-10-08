package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.controllers.UrlController;
import hexlet.code.controllers.RootController;
import hexlet.code.repository.BaseRepository;
import hexlet.code.util.NamedRoots;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.stream.Collectors;

@Slf4j
public class App {

    public static void main(String[] args) throws SQLException {
        getApp().start(getPort());
    }
    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.valueOf(port);
    }
    private static String getDBURL() {
        return  System.getenv()
                .getOrDefault("JDBC_DATABASE_URL",
                        "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");
    }

    public static Javalin getApp() throws SQLException {
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(getDBURL());
        var dataSource = new HikariDataSource(hikariConfig);
        var url = App.class.getClassLoader().getResourceAsStream("schema.sql");
        var sql = new BufferedReader(new InputStreamReader(url))
                .lines().collect(Collectors.joining("\n"));
        log.info(sql);
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }
        BaseRepository.dataSource = dataSource;

        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.bundledPlugins.enableDevLogging();
            javalinConfig.fileRenderer(new JavalinJte(createTemplateEngine()));
        });
        app.get(NamedRoots.rootPath(), RootController::index);
        app.post(NamedRoots.urlsPath(), UrlController::createUrl);
        app.get(NamedRoots.urlsPath(), UrlController::index);
        app.get(NamedRoots.urlPath("{id}"), UrlController::show);
        app.post(NamedRoots.urlChecksPath("{id}"), UrlController::createUrlCheck);
        return app;
    }
    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);
        return templateEngine;
    }
}
