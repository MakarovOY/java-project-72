package hexlet.code.controllers;

import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoots;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlController {

    public static void create(Context ctx) throws SQLException {

        String urlString = ctx.formParam("url");
        Url url = new Url(urlString);
        UrlRepository.save(url);
        try {
            var uri = new URI(url.getName());
            System.out.println(uri.getHost());
            System.out.println(uri.getPath());
            System.out.println(uri);
        } catch (URISyntaxException e) {
            ctx.sessionAttribute("flash", "Некорректный URL Uncorrect URL");
            System.out.println("///////////////////блок try");
            ctx.redirect(NamedRoots.urlsPath());
            return;
        }
        ctx.sessionAttribute("flash", "Страница успешно добавлена, Page successfully added");
        ctx.redirect(NamedRoots.urlsPath());




    }

    public static void index(Context ctx) throws SQLException{
        String flash = ctx.consumeSessionAttribute("flash");
        List<Url> urls = UrlRepository.getEntities();
        UrlsPage page = new UrlsPage(urls);
        page.setFlash(flash);

        ctx.render("urls/index.jte", model("page", page));


    }

    public static void show(Context ctx) throws SQLException {
        long id = ctx.pathParamAsClass("id", Long.class).get();
        Url url = UrlRepository.find(id).orElseThrow(() -> new NotFoundResponse(String.format("Entity with id = %s not found", id)));
        UrlPage page = new UrlPage(url);
        ctx.render("urls/show.jte", model("page", page));

    }

}
