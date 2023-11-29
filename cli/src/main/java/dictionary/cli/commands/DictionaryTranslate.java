package dictionary.cli.commands;

import dictionary.base.api.GoogleTranslateAPI;
import dictionary.base.Language;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;



@Command(name = "translate", description = "Translate text from one language to another")
public class DictionaryTranslate implements Runnable {
    @Option(names = { "-s",
            "--source-language" }, description = "Source language for translation with default English")
    private String sourceLanguage = Language.ENGLISH.code;

    @Option(names = { "-t",
            "--target-language" }, description = "Target language for translation with default Vietnamese")
    private String targetLanguage = Language.VIETNAMESE.code;

    @Parameters(index = "0", description = "Text to be translated")
    private String textToTranslate;

    @Override
    public void run() {
        try {
            System.out.println(GoogleTranslateAPI.translate(sourceLanguage, targetLanguage, textToTranslate));
        } catch (Exception ex) {
        }
    }
}
