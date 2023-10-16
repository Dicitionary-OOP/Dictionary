package dictionary.base;

import java.util.ArrayList;

public class WordExplain {
    private String type;
    private String meaning;
    private ArrayList<Example> examples;

    public WordExplain(String type, String meaning, ArrayList<Example> examples) {
        this.type = type;
        this.meaning = meaning;
        this.examples = examples;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Example> getExamples() {
        return examples;
    }

    public void setExamples(ArrayList<Example> examples) {
        this.examples = examples;
    }
}
