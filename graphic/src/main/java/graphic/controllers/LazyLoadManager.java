package graphic.controllers;

import java.util.ArrayList;

import org.apache.commons.math3.util.Pair;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;

public class LazyLoadManager {
    /**
     * If the number of suggestions is higher than this constant,
     * the suggestions will be lazy-loaded.
     */
    public static final int LAZY_LOAD_THRESHOLD = 50;

    private final ListView<Pair<String, String>> suggestedWords;

    private ArrayList<Pair<String, String>> allSuggestions;

    private boolean isLazyLoadInEffect;

    public LazyLoadManager(final ListView<Pair<String, String>> suggestedWords) {
        this.suggestedWords = suggestedWords;
        this.allSuggestions = new ArrayList<>();
        this.isLazyLoadInEffect = false;
    }

    /**
     * Call this function to update the suggestions,
     * and perform lazy load when necessary.
     *
     * @param _allSuggestions All suggestions looked up.
     */
    public void updateSuggestionsFromNonGUIThread(final ArrayList<Pair<String, String>> _allSuggestions) {
        Platform.runLater(() -> {
            updateSuggestionsFromGUIThread(_allSuggestions);
        });
    }

    /**
     * Force the suggestion list view to contain
     * all entries which was previously set by
     * `updateSuggestionsThreadSafe()`. This is
     * the "load" step in "lazy load".
     */
    public void loadAllSuggestionsFromNonGUIThread() {
        Platform.runLater(() -> {
            loadAllSuggestionsFromGUIThread();
        });
    }

    public void updateSuggestionsFromGUIThread(final ArrayList<Pair<String, String>> _allSuggestions) {
        allSuggestions = _allSuggestions;
        if (allSuggestions.size() > LAZY_LOAD_THRESHOLD) {
            isLazyLoadInEffect = true;
            updateSuggestionsInView(
                    new ArrayList<Pair<String, String>>(
                            // list.subList() create a VIEW in the list, not an actual new list.
                            allSuggestions.subList(0, LAZY_LOAD_THRESHOLD)));
        } else {
            loadAllSuggestionsFromGUIThread();
        }
    }

    public void loadAllSuggestionsFromGUIThread() {
        isLazyLoadInEffect = false;
        updateSuggestionsInView(allSuggestions);
    }

    private void updateSuggestionsInView(final ArrayList<Pair<String, String>> suggestions) {
        suggestedWords.setItems(
                FXCollections.observableList(suggestions));
    }
}
