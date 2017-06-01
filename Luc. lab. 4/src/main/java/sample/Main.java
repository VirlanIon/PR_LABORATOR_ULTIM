package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Main extends Application {

    public static Stage window;
    public static Parent root;
    @Override
    public void start(Stage primaryStage) throws Exception{

        window = primaryStage;
        root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        window.setTitle("Email Client");
        window.setScene(new Scene(root, 1000, 1000));
        window.show();



    }
    @FXML
  /*  private void sceneHandler(ActionEvent event) throws IOException {
        System.out.println("Scene changing...");
        root = FXMLLoader.load(getClass().getResource("/send.fxml"));
        window.setScene(new Scene(root));
    }*/



    public static void main(String[] args) {
        launch(args);
    }
}
