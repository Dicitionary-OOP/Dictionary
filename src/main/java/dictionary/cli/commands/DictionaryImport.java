package dictionary.cli.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.IOException;

import dictionary.cli.DictionaryCommandline;

@Command(name = "import", description = "Import dictionary from file")
public class DictionaryImport implements Runnable {
    @Option(names = { "-f", "--file" }, description = "File to import", required = true)
    private String filePath;

    @Override
    public void run() {
        try {
            new DictionaryCommandline().getDictionaryManagement().insertFromFile(filePath);
        } catch (IOException ex) {
        }
    }
}
