package graphic.controllers;

import org.controlsfx.control.Notifications;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import base.core.Dictionary;
import base.core.Example;
import base.core.Explain;
import base.core.Word;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddWordController extends LocalizedController {
    @FXML
    private AnchorPane root;
    @FXML
    private TextField wordTextField, pronounTextField;
    @FXML
    private VBox editVbox, currentTypeParentVBox, currentExplainParentVBox;

    @FXML
    private void onClickAddTypeButton() {
        final VBox typeParentVBox = new VBox();
        currentTypeParentVBox = typeParentVBox;
        final HBox typeHbox = new HBox();

        final FontAwesomeIconView addIcon = new FontAwesomeIconView(FontAwesomeIcon.PLUS_CIRCLE);
        addIcon.setSize("25px");
        addIcon.getStyleClass().add("ikonli-font-icon");
        addIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onClickAddExplainButton(typeParentVBox));

        final FontAwesomeIconView removeIcon = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
        removeIcon.setSize("25px");
        removeIcon.getStyleClass().add("ikonli-font-icon");
        removeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> editVbox.getChildren().remove(typeParentVBox));

        final TextField typeTextField = new TextField();
        typeTextField.setPromptText(bundle.getString("type_prompt"));
        typeTextField.setPrefWidth(489);
        typeTextField.getStyleClass().add("rounded");

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

    private void onClickAddExplainButton(final VBox parentVBox) {
        final VBox explainParentVBox = new VBox();
        currentExplainParentVBox = explainParentVBox;
        final HBox explainHbox = new HBox();

        final FontAwesomeIconView addIcon = new FontAwesomeIconView(FontAwesomeIcon.PLUS_CIRCLE);
        addIcon.setSize("25px");
        addIcon.getStyleClass().add("ikonli-font-icon");
        addIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onClickAddExampleButton(explainParentVBox));

        final FontAwesomeIconView removeIcon = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
        removeIcon.setSize("25px");
        removeIcon.getStyleClass().add("ikonli-font-icon");
        removeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> parentVBox.getChildren().remove(explainParentVBox));

        final TextField explainTextField = new TextField();
        explainTextField.setPromptText(bundle.getString("explain_in_vietnamese"));
        explainTextField.setPrefWidth(401);
        explainTextField.getStyleClass().add("rounded");

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

    private void onClickAddExampleButton(final VBox parentVBox) {
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
    private void reset() {
        editVbox.getChildren().clear();
        wordTextField.clear();
        pronounTextField.clear();
    }

    @FXML
    private void save() {
        if (Dictionary.getInstance().isExistWord(wordTextField.getText())) {
            System.out.println("existed");
            System.out.println(wordTextField.getText());
            Notifications.create()
                    .owner(root)
                    .title("Dictionary")
                    .text("Word has been existed")
                    .showError();
            return;
        }

        try {
            final String wordID = Dictionary.getInstance()
                    .addWord(new Word(wordTextField.getText(), pronounTextField.getText()));

            for (int i = 0; i < editVbox.getChildren().size(); i++) {
                final VBox typeVBox = (VBox) editVbox.getChildren().get(i);
                final HBox typeHBox = (HBox) typeVBox.getChildren().get(0);

                final String type = ((TextField) typeHBox.getChildren().get(2)).getText();
                for (int j = 1; j < typeVBox.getChildren().size(); j++) {
                    final VBox explainVBox = (VBox) typeVBox.getChildren().get(j);
                    final HBox explainHBox = (HBox) explainVBox.getChildren().get(0);
                    final String meaning = ((TextField) explainHBox.getChildren().get(2)).getText();
                    final String explainID = Dictionary.getInstance().addExplain(new Explain(wordID, type, meaning));

                    for (int k = 1; k < explainVBox.getChildren().size(); k++) {
                        final VBox exampleVBox = (VBox) explainVBox.getChildren().get(k);
                        final HBox exampleHBox = (HBox) exampleVBox.getChildren().get(0);
                        final String example = ((TextField) exampleHBox.getChildren().get(1)).getText();
                        final String translate = ((TextField) exampleHBox.getChildren().get(2)).getText();
                        Dictionary.getInstance().addExample(new Example(explainID, example, translate));
                    }
                }
            }

            Notifications.create()
                    .owner(root)
                    .title("Dictionary")
                    .text("Word has been added")
                    .showInformation();

            reset();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
