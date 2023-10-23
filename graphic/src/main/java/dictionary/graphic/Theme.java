package dictionary.graphic;

import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import atlantafx.base.theme.Dracula;
import atlantafx.base.theme.NordDark;
import atlantafx.base.theme.NordLight;
import atlantafx.base.theme.CupertinoDark;
import atlantafx.base.theme.CupertinoLight;

public enum Theme {
    PrimerDark("PrimerDark", new PrimerDark().getUserAgentStylesheet()),
    PrimerLight("PrimerLight", new PrimerLight().getUserAgentStylesheet()),
    Dracula("Dracula", new Dracula().getUserAgentStylesheet()),
    NordDark("NordDark", new NordDark().getUserAgentStylesheet()),
    NordLight("NordLight", new NordLight().getUserAgentStylesheet()),
    CupertinoDark("CupertinoDark", new CupertinoDark().getUserAgentStylesheet()),
    CupertinoLight("CupertinoLight", new CupertinoLight().getUserAgentStylesheet());

    public final String name;
    public final String style;

    private Theme(final String name, final String style) {
        this.name = name;
        this.style = style;
    }

    public static Theme getTheme(final String themeName) {
        for (final Theme theme : values()) {
            if (theme.name.equals(themeName)) {
                return theme;
            }
        }
        return null; 
    }
}

