@import hexlet.code.util.NamedRoots

@template.layout.page(content = @`
<%--    <p>Hello World</p>--%>
    <form action="${NamedRoots.urlsPath()}" method="post">
<%--        <div>--%>
<%--            <input id="url-input" autofocus="" type="text" required name="url" aria-label="url"--%>
<%--                   class="form-control" placeholder="ссылка" autocomplete="off">--%>
<%--            <label for="url-input">Ссылка</label>--%>
<%--        </div>--%>
        <div>
        <label for="inputPassword5" class="form-label">url</label>
        <input id="url" class="form-control" type="url"  name="url" aria-describedby="passwordHelpBlock">
        <div id="passwordHelpBlock" class="form-text">
            некоторый сопроводительный текст
        </div>
        <button type="submit" class="btn btn-lg btn-primary mt-2">Проверить</button>
        </div>
    </form>
`)
