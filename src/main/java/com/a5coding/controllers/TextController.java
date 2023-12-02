package com.a5coding.controllers;

import com.a5coding.data.TextHandler;
import com.a5coding.io.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class TextController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public int m;
    public double p;

    @FXML
    public TextArea textInputArea, notEncodedTextArea, encodedTextArea;

    private String text;

    private TextHandler textHandler = new TextHandler();

    //Sends the input text and displays received text
    public void send(){
        text = textInputArea.getText();

        notEncodedTextArea.setText(textHandler.handleWithoutEncoding(text, p));
        encodedTextArea.setText(textHandler.handleWithEncoding(text, p, m));

    }

    //Switches back to the main scene
    public void switchToMainScene(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/a5coding/mainScene.fxml"));

            root = loader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            Validator.errorAlert("Error while loading the scene!");
        }
    }
}
