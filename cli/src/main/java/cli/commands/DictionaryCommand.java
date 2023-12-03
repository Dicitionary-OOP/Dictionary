package cli.commands;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "dictionary", header = "Dictionary - A command-line dictionary tool", mixinStandardHelpOptions = true, subcommands = {
        CommandLine.HelpCommand.class,
        TranslateCommand.class,
        TextToIpaCommand.class,
        SynonymCommand.class,
        SpeakCommand.class,
})
public class DictionaryCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("No valid command specified. Use -h or --help for usage information.");
    }
}
