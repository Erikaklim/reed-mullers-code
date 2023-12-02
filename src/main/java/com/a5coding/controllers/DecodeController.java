package com.a5coding.controllers;

import com.a5coding.calculations.Decoder;
import com.a5coding.io.Converter;
import com.a5coding.io.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class DecodeController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    public TextField receivedMessageField, decodedMessageField, positionsField;

    private Decoder decoder = new Decoder();
    public int[] decoded, encoded, initialMessage, received;

    public int m;

    @FXML
    public Label errorsLabel;

    //Decodes the received message
    public void decode (){
        decoded = decoder.decode(received, m);

        if(Arrays.equals(initialMessage, decoded)){
            decodedMessageField.setStyle("-fx-text-fill: green;");
        }else{
            decodedMessageField.setStyle("-fx-text-fill: red;");
        }

        decodedMessageField.setText(Converter.intArrayToString(decoded));

    }

    //Based on the edited text field changes the number of errors and positions, shows the
    //changes to the user
    public void edit (){
        try {
            int[] edited = Converter.stringToIntArray(receivedMessageField.getText());
            if (edited != null && Validator.checkMessage(edited, encoded.length)) {
                List<Integer> errorPositions = decoder.getErrorPositions(edited, encoded);
                errorsLabel.setText(Integer.toString(errorPositions.size()));
                positionsField.setText(Arrays.toString(errorPositions.toArray()));

                received = edited;
            } else {
                Validator.invalidAlert("Invalid input!");
            }
        }catch (Exception e){
            Validator.invalidAlert("Invalid input!");
        }

    }

    //Switches back to the main scene
    public void switchToMainScene(ActionEvent event){
        try{
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
