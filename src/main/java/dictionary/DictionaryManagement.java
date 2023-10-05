package dictionary;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement {
    private final Dictionary dictionary;
    private final Scanner inputScanner;

    DictionaryManagement() {
        this(new Dictionary());
    }

    DictionaryManagement(final Dictionary dictionary) {
        this.dictionary = dictionary;
        inputScanner = new Scanner(System.in);
    }

    /**
     * Insert Words from a text file with line format "wordTarget\twordExplain".
     * 
     * @param filepath
     * @throws IOException
     */
    void insertFromFile(final String filepath) throws IOException {
        try {
            final BufferedReader buf = new BufferedReader(new FileReader(filepath));
            String line;
            while ((line = buf.readLine()) != null) {
                final String[] word = line.split("\t");
                if (word.length < 2) {
                    continue;
                }

                final String wordTarget = word[0];
                final String wordExplain = word[1];

                dictionary.add(new Word(wordTarget, wordExplain));
            }

            buf.close();
            System.out.println("INFO: Import sucess");
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File Not Found !!");
        }
    }

    /**
     * Insert number of words from commandline
     */
    void insertFromCommandline() {
        String numberOfWords;
        do {
            System.out.print("Input number of words to insert: ");
            numberOfWords = inputScanner.next();
        } while (!numberOfWords.matches("[0-9].*"));

        for (int i = 0; i < Integer.parseInt(numberOfWords); ++i) {
            System.out.print("Input word: ");
            final String wordTarget = inputScanner.next();

            System.out.print("Input meaning of word: ");
            final String wordExplain = inputScanner.next();

            dictionary.add(new Word(wordTarget, wordExplain));
        }
    }

    /**
     * Read word index and delete it
     */
    void deleteFromCommandline() {
        System.out.print("Input word index to delete: ");
        final int deleteIndex = inputScanner.nextInt();
        dictionary.removeWord(deleteIndex);
    }

    /**
     * Read word index and update its wordTarget and wordExplain
     */
    void updateFromCommandline() {
        System.out.print("Input word index to edit: ");
        final int targetIndex = inputScanner.nextInt();
        final Word word = dictionary.getWords().get(targetIndex);

        System.out.print("New word target: ");
        final String wordTarget = inputScanner.next();
        System.out.print("New word explain: ");
        final String wordExplain = inputScanner.next();

        if (wordTarget != null) {
            word.setWordTarget(wordTarget);
        }

        if (wordExplain != null) {
            word.setWordExplain(wordExplain);
        }
    }

    /**
     * Show all words matched with input word
     */
    void dictionaryLookup() {
        System.out.print("Input word to lookup: ");
        final String str = inputScanner.next();
        inputScanner.close();

        final CommandLineTable table = new CommandLineTable();
        table.setHeaders("English", "Vietnamese");
        final ArrayList<Word> words = dictionary.lookup(str);

        for (final Word word : words) {
            table.addRow(word.getWordTarget(), word.getWordExplain());
        }
        table.show();
    }

    /**
     * Show all words start with input
     */
    void dictionarySearcher() {
        System.out.print("Input string to find word startwith: ");
        final String str = inputScanner.next();
        final CommandLineTable table = new CommandLineTable();
        table.setHeaders("No", "English", "Vietnamese");

        int index = 0;
        final ArrayList<Word> words = dictionary.getWords();
        for (int i = 0; i < words.size(); ++i) {
            if (words.get(i).getWordTarget().startsWith(str)) {
                table.addRow(Integer.toString(index++), words.get(i).getWordTarget(), words.get(i).getWordExplain());
            }
        }
        table.show();
    }

    /**
     * Save current dictionary to a text file with format "wordTarget\twordExplain"
     * 
     * @param filepath
     * @throws IOException
     */
    void dictionaryExportToFile(final String filepath) throws IOException {
        final FileWriter writer = new FileWriter(filepath);
        final String format = "%s\t%s\n";
        for (final Word word : getDictionary().getWords()) {
            writer.write(String.format(format, word.getWordTarget(), word.getWordExplain()));
        }
        writer.close();
    }

    Dictionary getDictionary() {
        return dictionary;
    }

}
