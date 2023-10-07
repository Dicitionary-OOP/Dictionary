package dictionary.cli;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import dictionary.base.DictionaryManagement;
import dictionary.base.Word;
import dictionary.base.WordExplain;
import dictionary.cli.utils.CommandLineTable;

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
            table.addRow(Integer.toString(i), words.get(i).getWordTarget(), words.get(i).getWordExplain().getMeaning());
        }

        table.show();
    }

    DictionaryManagement getDictionaryManagement() {
        return dictionaryManagement;
    }

    public void dictionaryBasic() {
        insertFromCommandline();
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
        insertFromCommandline();
    }

    void handleRemove() {
        deleteFromCommandline();
    }

    void handleUpdate() {
        updateFromCommandline();
    }

    void handleDisplay() {
        showAllWords();
    }

    void handleLookup() {
        dictionaryLookup();
    }

    void handleSearch() {
        dictionarySearcher();
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

    /**
     * Insert number of words from commandline
     */
    public void insertFromCommandline() {
        String numberOfWords;
        do {
            System.out.print("Input number of words to insert: ");
            numberOfWords = inputScanner.next();
        } while (!numberOfWords.matches("[0-9].*"));

        for (int i = 0; i < Integer.parseInt(numberOfWords); ++i) {
            System.out.print("Input word: ");
            final String target = inputScanner.next();

            System.out.print("Input meaning of word: ");
            final String meaning = inputScanner.next();

            dictionaryManagement.getDictionary().add(new Word(target, new WordExplain(meaning)));
        }
    }

    /**
     * Read word index and delete it
     */
    public void deleteFromCommandline() {
        System.out.print("Input word index to delete: ");
        final int deleteIndex = inputScanner.nextInt();
        dictionaryManagement.getDictionary().removeWord(deleteIndex);
    }

    /**
     * Read word index and update its wordTarget and wordExplain
     */
    public void updateFromCommandline() {
        System.out.print("Input word index to edit: ");
        final int targetIndex = inputScanner.nextInt();
        final Word word = dictionaryManagement.getDictionary().getWords().get(targetIndex);

        System.out.print("New word target: ");
        final String target = inputScanner.next();
        System.out.print("New word meaning: ");
        final String meaning = inputScanner.next();

        if (target != null) {
            word.setWordTarget(target);
        }

        if (meaning != null) {
            word.setWordExplain(new WordExplain(meaning));
        }
    }

    /**
     * Show all words matched with input word
     */
    public void dictionaryLookup() {
        System.out.print("Input word to lookup: ");
        final String str = inputScanner.next();
        inputScanner.close();

        final CommandLineTable table = new CommandLineTable();
        table.setHeaders("English", "Vietnamese");
        final ArrayList<Word> words = dictionaryManagement.getDictionary().lookup(str);

        for (final Word word : words) {
            table.addRow(word.getWordTarget(), word.getWordExplain().getMeaning());
        }
        table.show();
    }

    /**
     * Show all words start with input
     */
    public void dictionarySearcher() {
        System.out.print("Input string to find word startwith: ");
        final String str = inputScanner.next();
        final CommandLineTable table = new CommandLineTable();
        table.setHeaders("No", "English", "Vietnamese");

        int index = 0;
        final ArrayList<Word> words = dictionaryManagement.getDictionary().getWords();
        for (int i = 0; i < words.size(); ++i) {
            if (words.get(i).getWordTarget().startsWith(str)) {
                table.addRow(Integer.toString(index++), words.get(i).getWordTarget(),
                        words.get(i).getWordExplain().getMeaning());
            }
        }
        table.show();
    }
}
