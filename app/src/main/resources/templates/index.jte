@import hexlet.code.util.NamedRoots

@template.layout.page(content = @`
<%--    <p>Hello World</p>--%>
    <a href="${NamedRoots.urlsPath()}">ссылка</a>
    <form action="${NamedRoots.urlsPath()}" method="post">




        <div>
        <label>url</label>
        <input  type="text"  name="url">
        <div id>
            некоторый сопроводительный текст
        </div>
        <button type="submit">Проверить</button>
        </div>
    </form>
`)
