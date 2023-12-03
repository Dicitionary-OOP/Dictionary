package graphic.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import base.core.Dictionary;
import base.core.Example;
import base.core.Explain;
import base.core.Word;
import base.database.Database;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EditWordController {
    @FXML
    private AnchorPane root;

    @FXML
    private TextField wordTextField, pronounTextField;

    @FXML
    private VBox editVbox, currentTypeParentVBox, currentExplainParentVBox;

    private Word word;

    private ArrayList<Explain> explains;

    private final HashMap<Explain, ArrayList<Example>> examples;

    private Database db;

    private final Stage stage;

    private final ResourceBundle bundle = ResourceBundle.getBundle("languages.language",
            SceneController.getInstance().getLocale());

    public EditWordController(final Stage stage) {
        this.stage = stage;
        this.examples = new HashMap<>();
    }

    public void loadWord(final Word word) {
        this.word = word;

        wordTextField.setText(word.getWord());
        pronounTextField.setText(word.getPronunce());
        db = Dictionary.getInstance().getDatabase();

        try {
            explains = db.getExplainsByWordID(word.getWordID());
            for (final Explain explain : explains) {
                examples.put(explain, new ArrayList<>());
                final VBox explainVBox = addExplain(addType(explain), explain);
                for (final Example example : db.getExamples(explain.getExplainID())) {
                    examples.get(explain).add(example);
                    addExample(explainVBox, example);
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private VBox addType(final Explain explain) {
        final VBox typeParentVBox = new VBox();
        currentTypeParentVBox = typeParentVBox;
        final HBox typeHbox = new HBox();

        final FontAwesomeIconView addIcon = new FontAwesomeIconView(FontAwesomeIcon.PLUS_CIRCLE);
        addIcon.setSize("25px");
        addIcon.getStyleClass().add("ikonli-font-icon");
        addIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onClickAddExplainButton(typeParentVBox, explain));

        final FontAwesomeIconView removeIcon = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
        removeIcon.setSize("25px");
        removeIcon.getStyleClass().add("ikonli-font-icon");
        removeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> editVbox.getChildren().remove(typeParentVBox));

        final TextField typeTextField = new TextField();
        typeTextField.setText(explain.getType());
        typeTextField.setPromptText(bundle.getString("type_prompt"));
        typeTextField.setPrefWidth(489);
        typeTextField.getStyleClass().add("rounded");

        typeTextField.textProperty().addListener((obs, old, niu) -> {
            explain.setType(typeTextField.getText());
        });

        typeTextField.setText(explain.getType());

        typeHbox.getChildren().add(addIcon);
        typeHbox.getChildren().add(new Label(bundle.getString("word_type")));
        typeHbox.getChildren().add(typeTextField);
        typeHbox.getChildren().add(removeIcon);
        typeHbox.setSpacing(10);
        typeHbox.setAlignment(Pos.CENTER_LEFT);
        typeParentVBox.getChildren().add(typeHbox);
        typeParentVBox.setSpacing(5);
        editVbox.getChildren().add(typeParentVBox);

        return typeParentVBox;
    }

    private VBox addExplain(final VBox parentVBox, final Explain explain) {
        final VBox explainParentVBox = new VBox();
        currentExplainParentVBox = explainParentVBox;
        final HBox explainHbox = new HBox();

        final FontAwesomeIconView addIcon = new FontAwesomeIconView(FontAwesomeIcon.PLUS_CIRCLE);
        addIcon.setSize("25px");
        addIcon.getStyleClass().add("ikonli-font-icon");
        addIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onClickAddExampleButton(explainParentVBox, explain));

        final FontAwesomeIconView removeIcon = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
        removeIcon.setSize("25px");
        removeIcon.getStyleClass().add("ikonli-font-icon");
        removeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> parentVBox.getChildren().remove(explainParentVBox));

        final TextField explainTextField = new TextField();
        explainTextField.setPromptText(bundle.getString("explain_in_vietnamese"));
        explainTextField.setPrefWidth(401);
        explainTextField.getStyleClass().add("rounded");
        explainTextField.textProperty().addListener((obs, old, niu) -> {
            explain.setMeaning(explainTextField.getText());
        });

        explainTextField.setText(explain.getMeaning());

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
        return explainParentVBox;
    }

    private void addExample(final VBox parentVBox, final Example example) {
        final VBox exampleParentVbox = new VBox();
        final HBox exampleHbox = new HBox();
        final TextField exampleField1 = new TextField();
        exampleField1.setPromptText(bundle.getString("english_sentence"));
        exampleField1.setPrefWidth(193);
        exampleField1.getStyleClass().add("rounded");

        final TextField exampleField2 = new TextField();
        exampleField2.setPromptText(bundle.getString("vietnamese_sentence"));
        exampleField2.setPrefWidth(193);
        exampleField2.getStyleClass().add("rounded");

        exampleField1.textProperty().addListener((obs, old, niu) -> {
            example.setExample(exampleField1.getText());
        });
        exampleField2.textProperty().addListener((obs, old, niu) -> {
            example.setTranslate(exampleField2.getText());
        });

        exampleField1.setText(example.getExample());
        exampleField2.setText(example.getTranslate());

        final FontAwesomeIconView removeIcon = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
        removeIcon.setSize("25px");
        removeIcon.getStyleClass().add("ikonli-font-icon");
        removeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> parentVBox.getChildren().remove(exampleParentVbox));

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
    private void onClickAddTypeButton() {
        final Explain explain = new Explain(word.getWordID(), "", "");
        explains.add(explain);
        examples.put(explain, new ArrayList<Example>());
        addType(explain);
    }

    private void onClickAddExplainButton(final VBox parentVBox, final Explain explain) {
        addExplain(parentVBox, explain);
    }

    private void onClickAddExampleButton(final VBox parentVBox, final Explain explain) {
        final Example example = new Example(explain.getExplainID(), "", "");
        examples.get(explain).add(example);
        addExample(parentVBox, example);
    }

    @FXML
    private void cancel() {
        stage.close();
    }

    @FXML
    private void save() {
        try {
            db.updateWord(word);

            for (final Explain explain : explains) {
                if (explain.getExplainID() != null) {
                    db.updateExplain(explain);
                } else {
                    explain.setExplainID(db.addExplain(explain));
                }
            }

            for (final HashMap.Entry<Explain, ArrayList<Example>> entry : examples.entrySet()) {
                final Explain explain = entry.getKey();
                for (final Example example : entry.getValue()) {
                    if (example.getExplainID() == null) {
                        example.setExplainID(explain.getExplainID());
                    }

                    if (example.getExampleID() != null) {
                        db.updateExample(example);
                    } else {
                        db.addExample(example);
                    }
                }
            }
            stage.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
