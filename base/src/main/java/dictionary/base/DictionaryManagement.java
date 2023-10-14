package dictionary.base;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DictionaryManagement {
    private final Dictionary dictionary;

    public DictionaryManagement() {
        this(new Dictionary());
    }

    public DictionaryManagement(final Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * Insert Words from a text file with line format "wordTarget\twordExplain".
     *
     * @param filepath
     * @throws IOException
     */
    public void insertFromFile(final String filepath) throws IOException {
        try {
            final BufferedReader buf = new BufferedReader(new FileReader(filepath));
            String line;
            while ((line = buf.readLine()) != null) {
                final String[] word = line.split("\t");
                if (word.length < 2) {
                    continue;
                }

                final String target = word[0];
                final String meaning = word[1];

                dictionary.add(new Word(target, new WordExplain(meaning)));
            }

            buf.close();
            System.out.println("INFO: Import sucess");
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File Not Found !!");
        }
    }

    /**
     * Save current dictionary to a text file with format "wordTarget\twordExplain"
     *
     * @param filepath
     * @throws IOException
     */
    public void dictionaryExportToFile(final String filepath) throws IOException {
        final FileWriter writer = new FileWriter(filepath);
        final String format = "%s\t%s\n";
        for (final Word word : getDictionary().getAllWords()) {
            writer.write(String.format(format, word.getWordTarget(), word.getWordExplain()));
        }
        writer.close();
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

}
