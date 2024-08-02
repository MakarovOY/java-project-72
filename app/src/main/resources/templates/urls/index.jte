@import hexlet.code.util.NamedRoots
@import hexlet.code.dto.urls.UrlsPage

@param UrlsPage page

@template.layout.page(page = page,
content = @`

    <h1>Urls List</h1>


        @for(var url : page.getUrls())
            <div>
                <h2>
                    ${url.getName()}
                </h2>
            </div>
        @endfor


`
)

