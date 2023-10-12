package dictionary.cli.commands;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "dictionary", header = "Dictionary - A command-line dictionary tool", description = "Dictionary is a tool for translating, managing, and searching for words and phrases in different languages.", mixinStandardHelpOptions = true, subcommands = {
        CommandLine.HelpCommand.class,
        DictionaryList.class,
        DictionaryImport.class,
        DictionaryExport.class,
        DictionaryAdd.class,
        DictionaryUpdate.class,
        DictionaryDelete.class,
        DictionaryLookup.class,
        DictionaryMatch.class,
        DictionaryTranslate.class,
        DictionaryInteractive.class,
})
public class DictionaryCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("No valid command specified. Use -h or --help for usage information.");
    }
}
