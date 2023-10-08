package dictionary.cli;

import dictionary.cli.commands.DictionaryCommand;
import picocli.CommandLine;

public class Main {
    public static void main(final String[] args) {
        final CommandLine commandLine = new CommandLine(new DictionaryCommand());
        System.exit(commandLine.execute(args));
    }
}
