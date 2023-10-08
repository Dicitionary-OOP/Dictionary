
package dictionary.cli.commands;

import picocli.CommandLine.Command;

import dictionary.cli.DictionaryCommandline;

@Command(name = "match", description = "Show all word matched with format")
public class DictionaryMatch implements Runnable {
    @Override
    public void run() {
    }
}
