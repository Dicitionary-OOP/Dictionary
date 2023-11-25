package dictionary.cli.commands;

import dictionary.base.Language;
import dictionary.base.api.SynonymAPI;
import dictionary.base.utils.Utils;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.ArrayList;

@Command(name = "synonym", description = "Get synonym")
public class DictionarySynonym implements Runnable {
    @CommandLine.Parameters(index = "0", description = "Text to be synonymed")
    private String sourseWord;

    @CommandLine.Option(names = { "-l",
            "--limit-word" }, description = "List synonym word with limit ")
    private int limitWord = 5;

    @Override
    public void run() {
        ArrayList<String> output = null;
        String result = "";
        try {
            output = new ArrayList<String>();
            output = SynonymAPI.getSynonyms(sourseWord, limitWord);
            for (String x : output) {
                result += x + "\n";
            }
            if (result == "") {
                result = "\t\t\t\t\t\t\t No word searched.\n \t\t\t\t\t\tPlease checking Word which you find synonym";
            }
            final String resultS = result;
            System.out.println(resultS);
        } catch (Exception e) {
        }
    }
}
