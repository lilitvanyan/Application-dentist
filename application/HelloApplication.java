package com.example.dentisst;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.spreadsheet.Grid;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HelloApplication extends Application {

    static Stage stage;

    @Override
    public void start(Stage st) throws Exception {
        stage=st;
        st.centerOnScreen();
        Menu.create_menu();
        Menu.set_menu();
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}