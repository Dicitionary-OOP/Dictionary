package graphic.enums;

import atlantafx.base.theme.CupertinoDark;
import atlantafx.base.theme.CupertinoLight;
import atlantafx.base.theme.Dracula;
import atlantafx.base.theme.NordDark;
import atlantafx.base.theme.NordLight;
import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;

public enum Theme {
    PrimerDark(new PrimerDark().getUserAgentStylesheet()),
    PrimerLight(new PrimerLight().getUserAgentStylesheet()),
    Dracula(new Dracula().getUserAgentStylesheet()),
    NordDark(new NordDark().getUserAgentStylesheet()),
    NordLight(new NordLight().getUserAgentStylesheet()),
    CupertinoDark(new CupertinoDark().getUserAgentStylesheet()),
    CupertinoLight(new CupertinoLight().getUserAgentStylesheet());

    private final String style;

    private Theme(final String style) {
        this.style = style;
    }

    public static Theme getTheme(final String themeName) {
        for (final Theme theme : values()) {
            if (theme.name().equals(themeName)) {
                return theme;
            }
        }
        return null; 
    }

    public String style() {
        return this.style;
    }
}

