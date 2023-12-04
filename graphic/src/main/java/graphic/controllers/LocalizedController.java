package graphic.controllers;

import java.util.ResourceBundle;

public class LocalizedController extends Controller {
    protected final ResourceBundle bundle = ResourceBundle.getBundle("languages.language", SceneController.getInstance().getLocale());
}
