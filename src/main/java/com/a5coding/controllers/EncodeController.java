package com.a5coding.controllers;

import com.a5coding.calculations.Channel;
import com.a5coding.calculations.Decoder;
import com.a5coding.calculations.Encoder;
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

public class EncodeController{

    private Stage stage;
    private Scene scene;
    private Parent root;

    public int m;
    public double p;

    @FXML
    public TextField messageField, enMessage;
    @FXML
    public Label enterMessageLabel;

    private int[] message, encoded;
    private Encoder encoder = new Encoder();
    private Channel channel;

    private Decoder decoder = new Decoder();

    //Encodes the user message
   public void encode(){
        try{
            message = Converter.stringToIntArray(messageField.getText());
            if(!Validator.checkMessage(message, m+1)){
                Validator.invalidAlert("Invalid input!");
                return;
            }
            encoded = encoder.encode(message, m);
            enMessage.setText(Converter.intArrayToString(encoded));
        }catch (Exception e) {
            Validator.invalidAlert("Invalid input!");
        }
   }

   //Switches to a new scene
   public void send(ActionEvent event){
        if(encoded != null){
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/a5coding/decodeScene.fxml"));

                root = loader.load();

                DecodeController decodeController = loader.getController();

                channel = new Channel(p);
                int[] received = channel.send(encoded);
                decodeController.receivedMessageField.setText(Converter.intArrayToString(received));

                List<Integer> errorPositions = decoder.getErrorPositions(received, encoded);
                decodeController.errorsLabel.setText(Integer.toString(errorPositions.size()));
                decodeController.positionsField.setText(Arrays.toString(errorPositions.toArray()));

                decodeController.received = received;
                decodeController.encoded = encoded;
                decodeController.m = m;
                decodeController.initialMessage = message;

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }catch (IOException e){
                Validator.errorAlert("Error while loading the scene!");
            }
        }else{
            Validator.invalidAlert("Encode the message first!");
        }

   }
}
