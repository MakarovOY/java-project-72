@import hexlet.code.dto.urls.UrlPage
@import hexlet.code.util.NamedRoots
@param UrlPage page

@template.layout.page(

content = @`
    <section>

        <div class="container-lg mt-5">
            <h1>Сайт: ${page.getUrl().getName()}</h1>

            <table class="table table-bordered table-hover mt-3">
                <tbody>

                <tr>
                    <td>ID</td>
                    <td>${page.getUrl().getId()}</td>
                </tr>
                <tr>
                    <td>Имя</td>
                    <td>${page.getUrl().getName()}</td>
                </tr>
                <tr>
                    <td>Дата создания</td>
                    <td>${page.getUrl().getCreatedAt().toLocalDateTime().format(page.getUrl().getDateTimeFormatter())}</td>
                </tr>
                </tbody>
            </table>

            <h2 class="mt-5">Проверки</h2>
            <form method="post" action="${NamedRoots.urlChecksPath(page.getUrl().getId())}">
                <button type="submit" class="btn btn-primary">Запустить проверку</button>
            </form>

            <table class="table table-bordered table-hover mt-3">
                <thead>
                <th class="col-1">ID</th>
                <th class="col-1">Код ответа</th>
                <th>title</th>
                <th>h1</th>
                <th>description</th>
                <th class="col-2">Дата проверки</th>
                </thead>
                <tbody>
    @for(var urlCheck : page.getUrlChecks())

                <tr>
                    <td>
                        ${urlCheck.getId()}
                    </td>
                    <td>
                        ${urlCheck.getStatusCode()}
                    </td>
                    <td>
                        ${urlCheck.getTitle()}
                    </td>
                    <td>
                        ${urlCheck.getH1()}
                    </td>
                    <td>
                        ${urlCheck.getDescription()}
                    </td>
                    <td>
                        ${urlCheck.getCreatedAt().toLocalDateTime().format(urlCheck.getDateTimeFormatter())}
                    </td>
                </tr>
    @endfor
                </tbody>
            </table>
        </div>

    </section>
`
)
