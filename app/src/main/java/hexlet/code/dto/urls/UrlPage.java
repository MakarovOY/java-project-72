package hexlet.code.dto.urls;

import hexlet.code.model.Url;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UrlPage extends BasePage {
    Url url;
}
