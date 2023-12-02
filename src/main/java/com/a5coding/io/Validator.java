package com.a5coding.io;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

public final class Validator {

    //Displays an invalid input window with a custom text parameter message
    public static void invalidAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setContentText(text);
        alert.initModality(Modality.APPLICATION_MODAL);

        alert.showAndWait();
    }

    //Displays error window if something goes wrong
    public static void errorAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(text);
        alert.initModality(Modality.APPLICATION_MODAL);

        alert.showAndWait();
    }

    //m - RM(1,M) has to be greater or equal to 1
    public static boolean checkM(int m){
        return m >= 1;
    }

    //The error of probability has to be in the interval [0,1]
    public static boolean checkP(double p){
        return p >= 0 && p <=1;
    }

    //Message - input vector has to contain only 1s and 0s and be of the required length
    public static boolean checkMessage(int[] message, int length){
        if(message.length != length){
            return false;
        }
        for(int i : message){
            if(i != 0 && i != 1){
                return false;
            }
        }
        return true;
    }
}
