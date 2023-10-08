package dictionary.cli.commands;

import picocli.CommandLine.Command;

@Command(name = "list", description = "Show all words")
public class DictionaryList implements Runnable {
    @Override
    public void run() {
    }
}
