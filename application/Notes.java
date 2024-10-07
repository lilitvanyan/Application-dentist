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
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.spreadsheet.Grid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Notes {
    static VBox VBox = new VBox();
    static Scene patientScene = new Scene(VBox, 500, 500);
    static boolean y=false;
    static void createNotes()
    {
        if(!y)
        {
            VBox.setSpacing(50);
            Button backToMenu = new Button("Back to Main Menu");
            backToMenu.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Menu.set_menu();
                }
            });
            HBox HBox1 = new HBox(backToMenu);
            VBox.getChildren().addAll(HBox1);

            Label header = new Label("Notes");
            HBox HBox2 = new HBox(header);
            HBox2.setAlignment(Pos.CENTER);
            header.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            VBox.getChildren().addAll(HBox2);

            TextArea notes = new TextArea();
            HBox HBox3 = new HBox(notes);
            HBox3.setPadding(new Insets(10, 40, 40, 20));
            HBox3.setAlignment(Pos.CENTER);
            VBox.getChildren().addAll(HBox3);

            Button submit = new Button("Save");
            HBox HBox4 = new HBox(submit);
            HBox4.setAlignment(Pos.CENTER);
            VBox.getChildren().addAll(HBox4);

            try
            {
                Connection db = new DBconnector().getDatabselink();
                Statement stat = db.createStatement();
                ResultSet res = stat.executeQuery("SELECT * FROM notes");
                while(res.next()) {notes.setText(res.getString(1));}
                db.close();
            }catch(Exception e) {System.out.println("error: couldn't fetch notes from database");};

            submit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try
                    {
                        Connection db = new DBconnector().getDatabselink();
                        PreparedStatement stat = db.prepareStatement("UPDATE notes SET text=? WHERE 1");
                        stat.setString(1,notes.getText());
                        stat.execute();
                        db.close();
                        Menu.set_menu();
                        System.out.println("Notes saved successfully");
                    }catch(Exception e) {System.out.println("error: couldn't saves notes to database");};
                }
            });

            y=true;
        }
    }
    static void setNotes()
    {

        HelloApplication.stage.setScene(patientScene);
    }
}