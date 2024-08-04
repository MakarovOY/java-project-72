package hexlet.code.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class UrlUtil {
    public static String parse(String urlString) throws URISyntaxException, MalformedURLException {
        URI uri = new URI(urlString);
        uri.toURL();
        return uri.getScheme() + "://" + uri.getAuthority();
    }
//    public static boolean uriIsChecked(String urlString) {
//        try {
//            URI uri = new URI(urlString);
//        } catch (URISyntaxException e) {
//            return false;
//        }
//        return true;
//    }
//
//    public static boolean urlIsChecked(URI uri) {
//       try {uri.toURL();
//       } catch (MalformedURLException e) {
//           return false;
//       }
//       return true;
//    }

}
