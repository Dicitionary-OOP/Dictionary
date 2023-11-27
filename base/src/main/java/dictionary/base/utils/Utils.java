package dictionary.base.utils;

import java.io.IOException;
import java.net.InetAddress;

public class Utils {
    public static String getResource(final String path) {
        return Utils.class.getResource(path).getPath();
    }

    public static boolean isNetworkConnected() {
        try {
            final InetAddress address = InetAddress.getByName("www.google.com");

            return !address.equals("");
        } catch (final IOException e) {
            return false;
        }
    }
}
