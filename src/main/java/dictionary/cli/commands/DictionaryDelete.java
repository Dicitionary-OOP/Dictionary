package dictionary.cli.commands;

import picocli.CommandLine.Command;

import dictionary.cli.DictionaryCommandline;

@Command(name = "delete", description = "Delete word")
public class DictionaryDelete implements Runnable {
    @Override
    public void run() {
    }
}
