package com.a5coding.io;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/a5coding/mainScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Reed Muller's code");
        stage.setScene(scene);
        stage.show();
    }

    //Starts the application with the main scene
    public static void main(String[] args) {
        launch();

    }
}