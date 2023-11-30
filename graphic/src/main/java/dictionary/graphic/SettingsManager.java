package dictionary.graphic;

import java.io.*;
import java.util.Properties;

import dictionary.graphic.SettingsManager;
import dictionary.base.utils.Utils;

public class SettingsManager {
    private static SettingsManager INSTANCE;
    private final String SETTINGS_FILE = Utils.getResource("/settings.properties");

    private Properties properties;

    private SettingsManager() {
        properties = new Properties();
        try (InputStream input = new FileInputStream(SETTINGS_FILE)) {
            properties.load(input);
        } catch (IOException | NullPointerException ignored) {
            // If the file doesn't exist or there's an issue reading it, ignore and return
            // an empty properties object
        }
    }

    public static SettingsManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SettingsManager();
        }

        return INSTANCE;
    }

    public void saveSettings() {
        try (OutputStream output = new FileOutputStream(SETTINGS_FILE)) {
            properties.store(output, "Application Settings");
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
}
