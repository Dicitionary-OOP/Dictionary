package dictionary.graphic;

import java.io.*;
import java.util.Properties;

import dictionary.graphic.SettingsManager;
import java.util.Properties;


import dictionary.base.utils.Utils;

public class SettingsManager {
    private static final String SETTINGS_FILE = Utils.getResource("/settings.properties");

    public static void saveSettings(Properties properties) {
        try (OutputStream output = new FileOutputStream(SETTINGS_FILE)) {
            properties.store(output, "Application Settings");
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static Properties loadSettings() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(SETTINGS_FILE)) {
            properties.load(input);
        } catch (IOException | NullPointerException ignored) {
            // If the file doesn't exist or there's an issue reading it, ignore and return an empty properties object
        }
        return properties;
    }
}
