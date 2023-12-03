package cli.commands;

import java.util.ArrayList;

import base.api.SynonymAPI;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "synonym", description = "Get synonym")
public class SynonymCommand implements Runnable {
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
