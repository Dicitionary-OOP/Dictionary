package dictionary.cli.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.IOException;

import dictionary.cli.DictionaryCommandline;

@Command(name = "export", description = "Export dictionary to file")
public class DictionaryExport implements Runnable {
    @Option(names = { "-f", "--file" }, description = "File to export", required = true)
    private String filePath;

    @Override
    public void run() {
        try {
            new DictionaryCommandline().getDictionaryManagement().dictionaryExportToFile(filePath);
        } catch (IOException ex) {
        }
    }
}
