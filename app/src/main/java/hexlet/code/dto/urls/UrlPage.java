package hexlet.code.dto.urls;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@AllArgsConstructor
@Getter
public class UrlPage extends BasePage {
    Url url;
    private List<UrlCheck> urlChecks;
}
