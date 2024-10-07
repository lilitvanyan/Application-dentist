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


import javafx.scene.Scene;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class PatientList {

    static void set_patient()
    {
        VBox VBox = new VBox();
        Scene patientScene = new Scene(VBox, 500, 500);
        FlowPane flowPane = new FlowPane();
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

        Label header = new Label("Patients");
        HBox HBox2 = new HBox(header);
        HBox2.setAlignment(Pos.CENTER);
        header.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        VBox.getChildren().addAll(HBox2);

        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setAlignment(Pos.CENTER);

        DBconnector cnt = new DBconnector();
        Connection db = cnt.getDatabselink();
        Collection<Button> buttons = new ArrayList<Button>();
        try {
            Statement stat = db.createStatement();
            ResultSet res = stat.executeQuery("SELECT * FROM patients");
            int i = 0;
            while (res.next()) {
                Button s = new Button(res.getString("name")+" "+res.getString("surname"));
                buttons.add(s);
                flowPane.getChildren().add(s);
                int sm = res.getInt("id");
                s.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Patient.createPatient(sm);
                    }
                });
                i++;
            }

        }catch (Exception e) {System.out.println("Couldn't fetch patients from database");}

        VBox.getChildren().addAll(flowPane);

        Button add = new Button("Add PatientList");

        HBox HBox3 = new HBox(add);
        HBox3.setAlignment(Pos.CENTER);
        VBox.getChildren().addAll(HBox3);

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addPatient.createAddPatient();
                addPatient.setAddPatient();
            }
        });
        HelloApplication.stage.setScene(patientScene);
    }
}