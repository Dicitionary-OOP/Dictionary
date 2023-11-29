package dictionary.cli.commands;

import dictionary.base.api.TextToIpaAPI;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;


@Command(name = "ipa", description = "Get ipa")
public class DictionaryTextToIpa implements Runnable {

        @Parameters(index = "0", description = "Text to be created ipa")
        private String textToCreatedIpa;

        @Override
        public void run() {
            try {
                System.out.println(TextToIpaAPI.textToIPA(textToCreatedIpa));
            } catch (Exception e) {
            }
        }
    }

