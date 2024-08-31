package hexlet.code.controllers;

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
import java.sql.SQLException;

public class UrlCheckController {
    public static void create(Context ctx) throws SQLException {
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
}
