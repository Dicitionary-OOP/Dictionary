package dictionary.cli.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "import", description = "Import dictionary from file")
public class DictionaryImport implements Runnable {
    @Option(names = { "-f", "--file" }, description = "File to import", required = true)
    private String filePath;

    @Override
    public void run() {
    }
}
