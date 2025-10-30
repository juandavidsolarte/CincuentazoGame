package com.example.cincuentazo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Load FXML from the SAME package as Main.class
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/menu.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 800, 800);
        stage.setTitle("Cincuentazo");

        stage.getIcons().add(
                new Image(getClass().getResourceAsStream("/com/example/cincuentazo/images/icono.png"))
        );

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}