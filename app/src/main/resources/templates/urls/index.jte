@import hexlet.code.util.NamedRoots
@import hexlet.code.dto.urls.UrlsPage

@param UrlsPage page

@template.layout.page(
content = @`
    <p>Hello World again</p>

    <h1>Cars List</h1>

<%--    @if(page.getUrls().isEmpty())--%>
<%--        <p>Urls list is empty</p>--%>
<%--    @else--%>
<%--        @for(var url : page.getUrls())--%>
<%--            <div>--%>
<%--                <h2>--%>
<%--                    ${url.getName()}--%>
<%--                </h2>--%>
<%--            </div>--%>
<%--        @endfor--%>
<%--    @endif--%>

        @for(var url : page.getUrls())
            <div>
                <h2>
                    ${url.getName()}
                </h2>
            </div>
        @endfor


`
)


<%--@template.layout.page(--%>

<%--content = @`--%>
<%--    <p>Hello World again</p>--%>
<%--`--%>
<%--)--%>
