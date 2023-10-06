package dictionary.cli;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryCommandline {
    private final DictionaryManagement dictionaryManagement;
    private final Scanner inputScanner;

    DictionaryCommandline() {
        this(new DictionaryManagement());
    }

    DictionaryCommandline(final DictionaryManagement dictionaryManagement) {
        this.dictionaryManagement = dictionaryManagement;
        inputScanner = new Scanner(System.in);
    }

    /**
     * Show a table of words
     */
    void showAllWords() {
        final CommandLineTable table = new CommandLineTable();
        table.setHeaders("No", "English", "Vietnamese");

        final ArrayList<Word> words = dictionaryManagement.getDictionary().getWords();
        for (int i = 0; i < words.size(); ++i) {
            table.addRow(Integer.toString(i), words.get(i).getWordTarget(), words.get(i).getWordExplain());
        }

        table.show();
    }

    DictionaryManagement getDictionaryManagement() {
        return dictionaryManagement;
    }

    public void dictionaryBasic() {
        getDictionaryManagement().insertFromCommandline();
        showAllWords();
    }

    public void dictionaryAdvanced() {
        boolean isRunning = true;
        while (isRunning) {
            showMenu();
            isRunning = handleMenuAction();
        }
    }

    public boolean handleMenuAction() {
        System.out.print("Your action: ");
        String input = inputScanner.next();

        while (!input.matches("[0-9]")) {
            System.out.println("Action not supported");

            System.out.print("Your action: ");
            input = inputScanner.next();
        }

        final int action = Integer.parseInt(input);

        switch (action) {
            case 0:
                return false;
            case 1:
                handleAdd();
                break;
            case 2:
                handleRemove();
                break;
            case 3:
                handleUpdate();
                break;
            case 4:
                handleDisplay();
                break;
            case 5:
                handleLookup();
                break;
            case 6:
                handleSearch();
                break;
            case 7:
                handleGame();
                break;
            case 8:
                handleImport();
                break;
            case 9:
                handleExport();
                break;
        }
        return true;
    }

    void handleAdd() {
        getDictionaryManagement().insertFromCommandline();
    }

    void handleRemove() {
        getDictionaryManagement().deleteFromCommandline();
    }

    void handleUpdate() {
        getDictionaryManagement().updateFromCommandline();
    }

    void handleDisplay() {
        showAllWords();
    }

    void handleLookup() {
        getDictionaryManagement().dictionaryLookup();
    }

    void handleSearch() {
        getDictionaryManagement().dictionarySearcher();
    }

    void handleGame() {
    }

    void handleImport() {
        System.out.print("Input filepath to import: ");
        final String filepath = inputScanner.next();
        try {
            getDictionaryManagement().insertFromFile(filepath);
        } catch (final IOException e) {
        }
    }

    void handleExport() {
        System.out.print("Input filepath to export: ");
        final String filepath = inputScanner.next();
        try {
            getDictionaryManagement().dictionaryExportToFile(filepath);
        } catch (final IOException e) {
        }
    }

    public void showMenu() {
        System.out.println("Welcome to Dictionary!");
        System.out.println("[0] Exit");
        System.out.println("[1] Add");
        System.out.println("[2] Remove");
        System.out.println("[3] Update");
        System.out.println("[4] Display");
        System.out.println("[5] Lookup");
        System.out.println("[6] Search");
        System.out.println("[7] Game");
        System.out.println("[8] Import from file");
        System.out.println("[9] Export to file");
    }

    public static void main(final String[] args) {
        new DictionaryCommandline().dictionaryAdvanced();
    }
}
