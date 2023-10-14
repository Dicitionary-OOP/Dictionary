package dictionary.base.utils;

public class Utils {
    public static String getResource(String path) {
        return Utils.class.getResource(path).getPath();
    }
}
