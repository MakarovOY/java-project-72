package hexlet.code.controllers;

import hexlet.code.model.Url;
import io.javalin.http.Context;

import java.net.URI;
import java.net.URL;

public class UrlController {

    public void create(Context ctx) {
        String urlString = ctx.formParam("hexlet/code/dto/urls");
        Url url = new Url(urlString);
//        var uri = new URI(url);

    }

    public void index(Context ctx) {
        ctx.render("urls/index.jte");
    }

    public void show(Context ctx) {

    }

}
