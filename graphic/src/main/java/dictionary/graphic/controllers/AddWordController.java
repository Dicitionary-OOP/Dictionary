package dictionary.graphic.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;
import dictionary.base.Word;
import dictionary.base.Explain;
import dictionary.base.Example;
import dictionary.base.Dictionary;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

public class AddWordController {
    private String typeText = null;
    private String explainText = null;
    private String exampleText1 = null;
    private String exampleText2 = null;
    ResourceBundle bundle = ResourceBundle.getBundle("languages.language", SceneController.getInstance().getLocale());

    @FXML
    private TextField wordTextField, pronounTextField;
    @FXML
    private VBox editVbox, currentTypeParentVBox, currentExplainParentVBox;

    public void setWordTextField(String word) {
        wordTextField.setText(word);
    }

    public void setPronounTextField(String word) {
        pronounTextField.setText(word);
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

    public void setExplainText(String explainText) {
        this.explainText = explainText;
    }

    public void setExampleText1(String exampleText1) {
        this.exampleText1 = exampleText1;
    }

    public void setExampleText2(String exampleText2) {
        this.exampleText2 = exampleText2;
    }

    public TextField getWordTextField() {
        return wordTextField;
    }

    public VBox getCurrentTypeParentVBox() {
        return currentTypeParentVBox;
    }

    public VBox getCurrentExplainParentVBox() {
        return currentExplainParentVBox;
    }

        @FXML
    public void onClickAddTypeButton() {
        VBox typeParentVBox = new VBox();
        currentTypeParentVBox = typeParentVBox;
        HBox typeHbox = new HBox();

        FontAwesomeIconView addIcon = new FontAwesomeIconView(FontAwesomeIcon.PLUS_CIRCLE);
        addIcon.setSize("25px");
        addIcon.getStyleClass().add("ikonli-font-icon");
        addIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onClickAddExplainButton(typeParentVBox));

        FontAwesomeIconView removeIcon = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
        removeIcon.setSize("25px");
        removeIcon.getStyleClass().add("ikonli-font-icon");
        removeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> editVbox.getChildren().remove(typeParentVBox));

        TextField typeTextField = new TextField();
        if (typeText != null) {
            typeTextField.setText(typeText);
            typeText = null;
        }
        typeTextField.setPromptText(bundle.getString("type_prompt"));
        typeTextField.setPrefWidth(489);

        typeHbox.getChildren().add(addIcon);
        typeHbox.getChildren().add(new Label(bundle.getString("word_type")));
        typeHbox.getChildren().add(typeTextField);
        typeHbox.getChildren().add(removeIcon);
        typeHbox.setSpacing(10);
        typeHbox.setAlignment(Pos.CENTER_LEFT);
        typeParentVBox.getChildren().add(typeHbox);
        typeParentVBox.setSpacing(5);
        editVbox.getChildren().add(typeParentVBox);
    }

    public void onClickAddExplainButton(VBox parentVBox) {
        VBox explainParentVBox = new VBox();
        currentExplainParentVBox = explainParentVBox;
        HBox explainHbox = new HBox();

        FontAwesomeIconView addIcon = new FontAwesomeIconView(FontAwesomeIcon.PLUS_CIRCLE);
        addIcon.setSize("25px");
        addIcon.getStyleClass().add("ikonli-font-icon");
        addIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onClickAddExampleButton(explainParentVBox));

        FontAwesomeIconView removeIcon = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
        removeIcon.setSize("25px");
        removeIcon.getStyleClass().add("ikonli-font-icon");
        removeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> parentVBox.getChildren().remove(explainParentVBox));

        TextField explainTextField = new TextField();
        explainTextField.setPromptText(bundle.getString("explain_in_vietnamese"));
        if (explainText != null) {
            explainTextField.setText(explainText);
            explainText = null;
        }
        explainTextField.setPrefWidth(401);

        explainHbox.getChildren().add(addIcon);
        explainHbox.getChildren().add(new Label(bundle.getString("explain")));
        explainHbox.getChildren().add(explainTextField);
        explainHbox.getChildren().add(removeIcon);
        explainHbox.setSpacing(10);
        explainHbox.setPadding(new Insets(0, 0, 0, 70));
        explainHbox.setAlignment(Pos.CENTER_LEFT);
        explainParentVBox.getChildren().add(explainHbox);
        explainParentVBox.setSpacing(5);

        parentVBox.getChildren().add(explainParentVBox);
    }

    public void onClickAddExampleButton(VBox parentVBox) {
        VBox exampleParentVbox = new VBox();
        HBox exampleHbox = new HBox();
        TextField exampleField1 = new TextField();
        exampleField1.setPromptText(bundle.getString("english_sentence"));
        if (exampleText1 != null) {
            exampleField1.setText(exampleText1);
            exampleText1 = null;
        }
        exampleField1.setPrefWidth(193);
        TextField exampleField2 = new TextField();
        exampleField2.setPromptText(bundle.getString("vietnamese_sentence"));
        if (exampleText2 != null) {
            exampleField2.setText(exampleText2);
            exampleText2 = null;
        }
        exampleField2.setPrefWidth(193);

        FontAwesomeIconView removeIcon = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
        removeIcon.setSize("25px");
        removeIcon.getStyleClass().add("ikonli-font-icon");
        removeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> parentVBox.getChildren().remove(exampleParentVbox));

        exampleHbox.getChildren().add(new Label(bundle.getString("example")));
        exampleHbox.getChildren().add(exampleField1);
        exampleHbox.getChildren().add(exampleField2);
        exampleHbox.getChildren().add(removeIcon);
        exampleHbox.setSpacing(10);
        exampleHbox.setPadding(new Insets(0, 0, 0, 140));
        exampleHbox.setAlignment(Pos.CENTER_LEFT);
        exampleParentVbox.getChildren().add(exampleHbox);
        exampleParentVbox.setSpacing(5);
        parentVBox.getChildren().add(exampleParentVbox);
    }

    @FXML
    public void onClickSaveButton() {
        try {
            final String wordID = Dictionary.getInstance().addWord(new Word(wordTextField.getText(), pronounTextField.getText()));
            System.out.println(wordID);

            for (int i = 0; i < editVbox.getChildren().size(); i++) {
                VBox typeVBox = (VBox) editVbox.getChildren().get(i);
                HBox typeHBox = (HBox) typeVBox.getChildren().get(0);

                final String type = ((TextField) typeHBox.getChildren().get(2)).getText();
                for (int j = 1; j < typeVBox.getChildren().size(); j++) {
                    VBox explainVBox = (VBox) typeVBox.getChildren().get(j);
                    HBox explainHBox = (HBox) explainVBox.getChildren().get(0);
                    final String meaning = ((TextField) explainHBox.getChildren().get(2)).getText(); 
                    final String explainID = Dictionary.getInstance().addExplain(new Explain(wordID, type, meaning));

                    for (int k = 1; k < explainVBox.getChildren().size(); k++) {
                        VBox exampleVBox = (VBox) explainVBox.getChildren().get(k);
                        HBox exampleHBox = (HBox) exampleVBox.getChildren().get(0);
                        final String example = ((TextField) exampleHBox.getChildren().get(1)).getText();
                        final String translate = ((TextField) exampleHBox.getChildren().get(2)).getText();
                        Dictionary.getInstance().addExample(new Example(explainID, example, translate));
                    }
                }
            }
        } catch(Exception e ) {
            e.printStackTrace();
        }
    }
}

