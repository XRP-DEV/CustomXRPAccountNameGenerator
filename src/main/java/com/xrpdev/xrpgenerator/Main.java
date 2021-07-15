package com.xrpdev.xrpgenerator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Pane pane = loader.load(Main.class.getResourceAsStream("/Main.fxml"));
        primaryStage.setOnCloseRequest(event -> {
            Controller.executorService.shutdownNow();
            Platform.exit();
            System.exit(0);
        });
        primaryStage.setTitle("Account Generator v1.0 inspired by @WietseWind");
        primaryStage.setScene(new Scene(pane));
        primaryStage.show();
    }
}
