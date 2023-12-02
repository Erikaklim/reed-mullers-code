package com.a5coding.controllers;

import com.a5coding.data.ImageHandler;
import com.a5coding.io.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class ImageController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public ImageView notEncodedView, encodedView, selectedImageView;

    private String filepath;

    private ImageHandler imageHandler = new ImageHandler();

    public int m;
    public double p;

    //Opens a new window to select an image and after it is selected, the image is displayed
    public void selectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Bitmap Files (*.bmp)", "*.bmp"));

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            filepath = selectedFile.getAbsolutePath();

            Image image = new Image(filepath);
            selectedImageView.setImage(image);


        }
    }

    //Sends the selected image and displays received images
    public void send(){
        if(filepath != null){
            Image notEncodedImage = getImage(imageHandler.handleWithoutEncoding(filepath, p));
            Image encodedImage = getImage(imageHandler.handleWithEncoding(filepath, p, m));

            if(notEncodedImage != null && encodedImage != null){
                notEncodedView.setImage(notEncodedImage);
                encodedView.setImage(encodedImage);
            }else{
                Validator.errorAlert("Error while getting images!");
            }


        }else{
            Validator.invalidAlert("Select the image first!");
        }
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

    //Gets image from the filepath
    private Image getImage(String filepath) {
        try {
            File file = new File(filepath);
            String urlString = file.toURI().toURL().toExternalForm();
            Image image = new Image(urlString);
            return image;

        } catch (MalformedURLException e) {
            return null;
        }
    }
}
