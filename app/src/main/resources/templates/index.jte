@import hexlet.code.util.NamedRoots
@import hexlet.code.dto.urls.BasePage
@param BasePage page
@template.layout.page(page = page,
content = @`
    <a href="${NamedRoots.urlsPath()}">ссылка</a>
    <form action="${NamedRoots.urlsPath()}" method="post">
        <div>
            <h1>
                Анализатор страниц
            </h1>
        <label>url</label>
        <input  type="text"  name="url">
        <div id>
            некоторый сопроводительный текст
        </div>
        <button type="submit">Проверить</button>
        </div>
    </form>
`)
