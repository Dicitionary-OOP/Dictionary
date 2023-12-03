package graphic.enums;

import java.util.Objects;

public enum Font {
    JetBrainsMono(Objects.requireNonNull(Font.class.getResource("/font/JetBrainsMono.css"))
            .toExternalForm()),
    Inter(Objects.requireNonNull(Font.class.getResource("/font/Inter.css"))
            .toExternalForm()),
    Roboto(Objects.requireNonNull(Font.class.getResource("/font/Roboto.css"))
            .toExternalForm());

    private final String path;

    private Font(final String path) {
        this.path = path;
    }

    public static Font getFont(final String fontName) {
        for (final Font font : values()) {
            if (font.name().equals(fontName)) {
                return font;
            }
        }
        return null;
    }

    public String path() {
        return this.path;
    }
}
