package hexlet.code.controllers;

import hexlet.code.dto.urls.BasePage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoots;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlController {

    public static void createUrl(Context ctx) {

        String urlString = ctx.formParam("url");
        try {
            String parsedUrlString = parse(urlString);
            boolean urlIsAlreadyExistInDB = UrlRepository
                   .getEntities()
                   .stream()
                   .filter(u -> u.getName().equals(parsedUrlString)).findFirst().isEmpty();
            if (!urlIsAlreadyExistInDB) {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.sessionAttribute("flashType", "alert-info");
            } else {
                Url url = new Url(parsedUrlString);
                UrlRepository.save(url);
                ctx.sessionAttribute("flash", "Страница успешно добавлена");
                ctx.sessionAttribute("flashType", "alert-success");
            }
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flashType", "alert-danger");
            String flash = ctx.consumeSessionAttribute("flash");
            String flashType = ctx.consumeSessionAttribute("flashType");
            var basepage = new BasePage();
            basepage.setFlash(flash);
            basepage.setFlashType(flashType);
            ctx.render("index.jte", model("page", basepage));
            return;
        }
        ctx.redirect(NamedRoots.urlsPath());
    }

    public static void createUrlCheck(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id).orElseThrow(() -> new NotFoundResponse());

        HttpResponse<String> response = Unirest.get(url.getName()).asString();
        Document doc = Jsoup.parse(response.getBody());

        int statusCode = response.getStatus();
        String title = doc.title();
        Element h1Element = doc.selectFirst("h1");
        String h1 = h1Element == null ? "" : h1Element.text();
        Element descriptionElement = doc.selectFirst("meta[name=description]");
        String description = descriptionElement == null ? "" : descriptionElement.attr("content");

        UrlCheck newUrlCheck = new UrlCheck(statusCode, title, h1, description);
        newUrlCheck.setUrlId(id);
        UrlCheckRepository.save(newUrlCheck);

        ctx.redirect(NamedRoots.urlPath(id));
    }


    public static void index(Context ctx) throws SQLException {
        String flash = ctx.consumeSessionAttribute("flash");
        String flashType = ctx.consumeSessionAttribute("flashType");
        List<Url> urls = UrlRepository.getEntities();
        Map<Long, UrlCheck> lastChecks = UrlCheckRepository.getLastChecks();
        UrlsPage page = new UrlsPage(urls, lastChecks);
        page.setFlash(flash);
        page.setFlashType(flashType);
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        long id = ctx.pathParamAsClass("id", Long.class).get();
        Url url = UrlRepository
                .find(id).orElseThrow(() -> new NotFoundResponse(String.format("Entity with id = %s not found", id)));
        var urlChecks = UrlCheckRepository.getEntities(id);
        UrlPage page = new UrlPage(url, urlChecks);
        ctx.render("urls/show.jte", model("page", page));
    }

    private static String parse(String urlString) throws URISyntaxException, MalformedURLException {
        URI uri = new URI(urlString);
        uri.toURL();
        return uri.getScheme() + "://" + uri.getAuthority();
    }
}
