package com.pinnacle.library;

import com.pinnacle.library.controller.LibraryApplicationController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;

public class LibraryApplication extends Application {
//    @Override
//    public void start(Stage stage) throws IOException {
////        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
////        GridPane root = new GridPane();
//
//        VBox root = new VBox();
//        root.setAlignment(Pos.CENTER);
////        root.getScene().getHeight();
//        root.setAlignment(Pos.CENTER);
//        root.getBorder();
//
//
//
//        Label greetings = new Label("Welcome to JavaFX");
//        greetings.setTextFill(Color.DARKGREEN);
//        greetings.setFont(Font.font("Times New Roman", FontWeight.BOLD, 70));
//        root.getChildren().add(greetings);
//
//
////        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello! JavaFX!");
//        stage.setScene(new Scene(root, 700, 275));
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        launch();
//    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create the controller and initialize the UI
        LibraryApplicationController controller = new LibraryApplicationController();

        // Create the scene with the controller's root pane
        Scene scene = new Scene(controller.createUI(), 1000, 700);

        // Set up the stage
        primaryStage.setTitle("Library Management System");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);

        // Show the stage
        primaryStage.show();

        // Initialize the controller after the UI is displayed
        controller.initialize();
    }

    public static void main(String[] args) {
        launch(args);
    }
}