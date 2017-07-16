package pl.funnyqrz.utils.resource;

import com.google.common.base.Strings;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

public class PropertiesValidator {

    public static boolean isValidUrl(String urlProperty) {
        URL url = null;

        try {
            url = new URL(urlProperty);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        try {
            url.toURI();
        } catch (URISyntaxException e) {
            return false;
        }

        return true;
    }

    public static boolean isEmpty(String property) {
        return Strings.isNullOrEmpty(property);
    }
}
