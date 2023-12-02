package com.a5coding.controllers;

import com.a5coding.data.ImageHandler;
import com.a5coding.io.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public TextField mTextField, pTextField;

    @FXML
    public Button nextButton;

    @FXML
    public RadioButton vectorRadioButton, textRadioButton;
    private int m;
    private double p;

    //Switches to the next scene
    public void switchToEncodeScene(ActionEvent event){
        try {

            m = Integer.parseInt(mTextField.getText());
            p = parseDoubleExtended(pTextField.getText());

            if(!Validator.checkP(p) || !Validator.checkM(m)){
                Validator.invalidAlert("Invalid input!");
            }else{
                if(vectorRadioButton.isSelected()){
                    switchToVectorEncode(event);
                }else if(textRadioButton.isSelected()){
                    switchToTextEncode(event);
                }else{
                    switchToImageEncode(event);
                }
            }

        }catch (Exception e){
            Validator.invalidAlert("Invalid input!");
        }
    }

    //Switches to the scene to encode a single vector
    private void switchToVectorEncode(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/a5coding/encodeScene.fxml"));

            root = loader.load();

            EncodeController encodeController = loader.getController();
            encodeController.m = m;
            encodeController.p = p;
            String enterMessage = encodeController.enterMessageLabel.getText();
            encodeController.enterMessageLabel.setText(enterMessage + " " + (m + 1) + ":");

            showScene(event);
        }catch (IOException e){
            Validator.errorAlert("Error while loading the scene!");
        }

    }

    //Switches to the scene to handle text
    private void switchToTextEncode(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/a5coding/textScene.fxml"));

            root = loader.load();

            TextController textController = loader.getController();
            textController.m = m;
            textController.p = p;


            showScene(event);
        }catch (IOException e){
            Validator.errorAlert("Error while loading the scene!");
        }
    }

    //Switches to the scene to handle image
    private void switchToImageEncode(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/a5coding/imageScene.fxml"));

            root = loader.load();

            ImageController imageController = loader.getController();
            imageController.m = m;
            imageController.p = p;


            showScene(event);
        }catch (IOException e){
            Validator.errorAlert("Error while loading the scene!");
        }
    }

    private void showScene(ActionEvent event){
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Handles input double values with a comma instead of a dot
    private double parseDoubleExtended(String text) {
        if (text.contains(",")) {
            text = text.replace(",", ".");
        }
        return Double.parseDouble(text);
    }

}


