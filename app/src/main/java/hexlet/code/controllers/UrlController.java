package hexlet.code.controllers;

import hexlet.code.dto.urls.BasePage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoots;
import hexlet.code.util.UrlUtil;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import java.sql.SQLException;
import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlController {

    public static void create(Context ctx) throws SQLException {

        String urlString = ctx.formParam("url");
        try {
            String parsedUrlString = UrlUtil.parse(urlString);
            boolean urlIsAlreadyExistInDB = UrlRepository
                   .getEntities()
                   .stream()
                   .filter(u -> u.getName().equals(parsedUrlString)).findFirst().isEmpty();
            if (!urlIsAlreadyExistInDB) {
                ctx.sessionAttribute("flash", "Страница уже существует, Page is already added");
            } else {
                Url url = new Url(parsedUrlString);
                UrlRepository.save(url);
                ctx.sessionAttribute("flash", "Страница успешно добавлена, Page successfully added");
            }
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Некорректный URL,Incorrect URL");
            String flash = ctx.consumeSessionAttribute("flash");
            var basepage = new BasePage();
            basepage.setFlash(flash);
            ctx.render("index.jte", model("page", basepage));
            return;
        }
        ctx.redirect(NamedRoots.urlsPath());
    }

    public static void index(Context ctx) throws SQLException {
        String flash = ctx.consumeSessionAttribute("flash");
        List<Url> urls = UrlRepository.getEntities();
        UrlsPage page = new UrlsPage(urls);
        page.setFlash(flash);
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        long id = ctx.pathParamAsClass("id", Long.class).get();
        Url url = UrlRepository
                .find(id).orElseThrow(() -> new NotFoundResponse(String.format("Entity with id = %s not found", id)));
        UrlPage page = new UrlPage(url);
        ctx.render("urls/show.jte", model("page", page));
    }

}
