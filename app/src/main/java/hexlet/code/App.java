package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

    public static void main(String[] args) {
        getApp().start(getPort());
    }
    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.valueOf(port);
    }
    private static String getDBURL() {
        return  System.getenv()
                .getOrDefault("JDBC_DATABASE_URL",
                        "jdbc:h2:mem:project");

    }

    public static Javalin getApp() {
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");

        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.bundledPlugins.enableDevLogging();
            javalinConfig.fileRenderer(new JavalinJte());
        });
        app.get("/", ctx -> ctx.result("Hello World"));
        return app;

    }
}
