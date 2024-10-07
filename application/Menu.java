package com.example.dentisst;

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

public class Menu {
    static GridPane gp_menu = new GridPane();
    static Scene menu_scene = new Scene(gp_menu);
    static public void create_menu()
    {
        gp_menu.setAlignment(Pos.CENTER);
        gp_menu.setPadding(new Insets(10, 40, 40, 40));
        ColumnConstraints one = new ColumnConstraints(1, 200, Double.MAX_VALUE);
        one.setHalignment(HPos.CENTER);
        gp_menu.getColumnConstraints().addAll(one);
        RowConstraints rone = new RowConstraints(40, 100, Double.MAX_VALUE);
        rone.setValignment(VPos.CENTER);
        RowConstraints rtwo = new RowConstraints(40, 50, Double.MAX_VALUE);
        rtwo.setValignment(VPos.CENTER);
        RowConstraints rthree = new RowConstraints(40, 50, Double.MAX_VALUE);
        rthree.setValignment(VPos.CENTER);
        RowConstraints rfour = new RowConstraints(40, 50, Double.MAX_VALUE);
        rfour.setValignment(VPos.CENTER);
        gp_menu.getRowConstraints().addAll(rone,rtwo,rthree,rfour);

        Label header = new Label("Menu");
        header.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gp_menu.add(header, 0,0);

        Button pat = new Button("Patients");
        pat.setPrefHeight(40);
        pat.setPrefWidth(200);
        gp_menu.add(pat, 0, 1);
        pat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PatientList.set_patient();
            }
        });

        Button appo = new Button("Appointments");
        appo.setPrefHeight(40);
        appo.setPrefWidth(200);
        gp_menu.add(appo, 0, 2);
        appo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Appointments.setAppointment();
            }
        });

        Button note = new Button("Notes");
        note.setPrefHeight(40);
        note.setPrefWidth(200);
        gp_menu.add(note, 0, 3);
        note.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Notes.createNotes();
                Notes.setNotes();
            }
        });
    }
    static void set_menu()
    {
        HelloApplication.stage.setScene(menu_scene);
    }

}