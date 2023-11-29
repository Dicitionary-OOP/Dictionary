package dictionary.graphic.controllers;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import dictionary.graphic.Theme;
import dictionary.graphic.Font;
import dictionary.graphic.SettingsManager;
import java.util.Properties;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;
import javafx.application.Platform;

import com.plexpt.chatgpt.ChatGPT;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class ChatbotController {
    private ChatGPT chatGPT; 
    Parser parserMarkdown;
    HtmlRenderer renderer;
    private StringBuilder chatHistory = new StringBuilder();


    @FXML
    private WebView output;

    @FXML
    private VBox vbox;

    @FXML
    private TextArea input;

    @FXML
    private void initialize() {
         chatGPT = ChatGPT.builder()
                .apiKey("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")
                .build()
                .init();
         parserMarkdown = Parser.builder().build();
         renderer = HtmlRenderer.builder().build();
    }

    @FXML
    private void chat() {
        output.getEngine().loadContent("Loading...");
        final String query = input.getText().trim();
        input.clear();

         Thread thread = new Thread(() -> {
            // Append user message to chat history
            String userMessage = "<div><b>You</b>:<br/>" + query + "</div></br>";
            chatHistory.append(userMessage);

            Platform.runLater(() -> {
                // Display chat history in WebView
                output.getEngine().loadContent(chatHistory.toString());
                output.getEngine().loadContent(chatHistory.toString() + "<div><b>Bot</b>:<br/>Typing...</div>");
            });

            // Fetch and append bot response to chat history
            final String botResponse = chatGPT.chat(query);
            final Node document = parserMarkdown.parse(botResponse);
            String botMessage = "<div><b>Bot</b>:" + renderer.render(document) + "</div><br/>";
            chatHistory.append(botMessage);

            Platform.runLater(() -> {
                // Display updated chat history in WebView
                output.getEngine().loadContent(chatHistory.toString());
            });
        });


        thread.setDaemon(true);
        thread.start();
    }
}
