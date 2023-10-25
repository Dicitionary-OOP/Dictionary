package dictionary.base.utils;

import java.io.IOException;
import java.net.InetAddress;

public class Utils {
    public static String getResource(String path) {
        return Utils.class.getResource(path).getPath();
    }

    public static boolean isNetworkConnected() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");

            return !address.equals("");
        } catch (IOException e) {
            return false;
        }
    }
}
