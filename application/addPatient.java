package com.example.dentisst;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract public class addPatient  extends Application{

    static GridPane gridPane = new GridPane();
    static Scene addPatientScene= new Scene (gridPane, 700,400);
    static boolean a = false;

    static void createAddPatient () {
        if(a) return;
        a = true;
        ColumnConstraints one = new ColumnConstraints(50, 200, Double.MAX_VALUE);
        ColumnConstraints two = new ColumnConstraints(50, 200, Double.MAX_VALUE);
        ColumnConstraints three = new ColumnConstraints(50, 200, Double.MAX_VALUE);
        ColumnConstraints four = new ColumnConstraints(50, 200, Double.MAX_VALUE);

        gridPane.getColumnConstraints().addAll(one, two, three,four);
        RowConstraints[] row = new RowConstraints[10];

        for (int i = 0; i < 10; i++) {
            row[i] = new RowConstraints(40, 0, Double.MAX_VALUE);
            gridPane.getRowConstraints().addAll(row[i]);
        }

        Button back = new Button("Back to Patients List ");
        gridPane.add(back, 0, 0);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PatientList.set_patient();
            }
        });

        Label addPatient1 = new Label("Add ");
        gridPane.add(addPatient1, 1, 1);
        addPatient1.setFont(Font.font("Arial", 24));
        gridPane.setHalignment(addPatient1, HPos.RIGHT);

        Label addPatient2 = new Label("Patient");
        gridPane.add(addPatient2, 2, 1);
        addPatient2.setFont(Font.font("Arial", 24));
        gridPane.setHalignment(addPatient2, HPos.LEFT);

        Label nameLabel = new Label("First Name : ");
        nameLabel.setFont(Font.font("Arial", 15));
        gridPane.add(nameLabel, 1, 2);

        TextField nameText = new TextField();
        nameText.setPrefHeight(20);
        gridPane.add(nameText, 2, 2);

        Label surnameLabel = new Label("Second Name : ");
        gridPane.add(surnameLabel, 1, 3);
        surnameLabel.setFont(Font.font("Arial", 15));

        TextField surnameText = new TextField();
        surnameText.setPrefHeight(20);
        gridPane.add(surnameText, 2, 3);

        Label birthLabel = new Label("Birth Date : ");
        gridPane.add(birthLabel, 1, 4);
        birthLabel.setFont(Font.font("Arial", 15));

        DatePicker birthPicker = new DatePicker();
        gridPane.add(birthPicker, 2, 4);

        Label genderLabel = new Label("Gender : ");
        gridPane.add(genderLabel, 1, 5);
        genderLabel.setFont(Font.font("Arial", 15));

        ChoiceBox genderChoice = new ChoiceBox((FXCollections.observableArrayList(
                "Female", "Male", "Non-binary", "Other")));
        gridPane.add(genderChoice, 2, 5);

        Label phoneLabel = new Label("Phone number : ");
        gridPane.add(phoneLabel, 1, 6);
        phoneLabel.setFont(Font.font("Arial", 15));

        TextField phoneText = new TextField();
        phoneText.setPrefHeight(20);
        gridPane.add(phoneText, 2, 6);

        Label emailLabel = new Label("Email : ");
        gridPane.add(emailLabel, 1, 7);
        emailLabel.setFont(Font.font("Arial", 15));

        TextField emailText = new TextField();
        emailText.setPrefHeight(20);
        gridPane.add(emailText, 2, 7);

        Button submit = new Button("Submit");
        gridPane.add(submit, 2, 8);
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (nameText.getText().isEmpty()) {
                    Alert error1 = new Alert(Alert.AlertType.WARNING);
                    error1.setContentText("Enter your name");
                    error1.show();
                    return;
                }
                if (surnameText.getText().isEmpty()) {
                    Alert error1 = new Alert(Alert.AlertType.WARNING);
                    error1.setContentText("Enter your surname");
                    error1.show();
                    return;
                }
                if(birthPicker.getValue()==null) {
                    Alert error1 = new Alert(Alert.AlertType.WARNING);
                    error1.setContentText("Enter your birth date");
                    error1.show();
                    return;

                }
                if (genderChoice.getValue()==null) {
                    Alert error1 = new Alert(Alert.AlertType.WARNING);
                    error1.setContentText("Select your gender");
                    error1.show();
                    return;

                }
                if (phoneText.getText().isEmpty()) {
                    Alert error1 = new Alert(Alert.AlertType.WARNING);
                    error1.setContentText("Enter your Phone number");
                    error1.show();
                    return;
                }
                if (emailText.getText().isEmpty()) {
                    Alert error1 = new Alert(Alert.AlertType.WARNING);
                    error1.setContentText("Enter your Email");
                    error1.show();
                    return;
                }
                for (int i=0; i<phoneText.getText().length(); i++)
                    if (phoneText.getText().charAt(i)<'0'|| phoneText.getText().charAt(i)>'9')
                    {
                        Alert error1 = new Alert(Alert.AlertType.WARNING);
                        error1.setContentText("Enter valid Phone number");
                        error1.show();
                        return;
                    }
                String b =new String();
                b=emailText.getText();
                /*   if (b.charAt(0)<'0'|| (b.charAt(0)>'9'&& b.charAt(0)<'A')||( b.charAt(0)>'Z'&& b.charAt(0)<'a')||( b.charAt(0)>'z'))
                {
                    Alert error1 = new Alert(Alert.AlertType.WARNING);
                    error1.setContentText("Enter valid Email");
                    error1.show();
                    return;
                }*/

                String reg ="^[A-Za-z0-9+_.-]+@(.+)$";
                Pattern pat = Pattern.compile(reg);
                Matcher match = pat.matcher(b);
                if(!match.matches())
                {
                    Alert error1 = new Alert(Alert.AlertType.WARNING);
                    error1.setContentText("Enter valid Email");
                    error1.show();
                    return;
                }


                try {
                    DBconnector dbc = new DBconnector();
                    Connection db = dbc.getDatabselink();
                    Statement stat = db.createStatement();
                    ResultSet res = stat.executeQuery("SELECT * FROM patients WHERE id=(SELECT max(id) FROM patients)");
                    int id = 0;
                    while(res.next()) {id=res.getInt(1);}
                    String query = "INSERT patients (id, name, surname, birth, gender, phone, email, appointmentnote) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = db.prepareStatement(query);
                    statement.setInt(1, id+1);
                    statement.setString(2, nameText.getText());
                    statement.setString(3, surnameText.getText());
                    statement.setDate(4, Date.valueOf(birthPicker.getValue()));
                    statement.setString(5, genderChoice.getValue().toString());
                    statement.setString(6, phoneText.getText());
                    statement.setString(7, emailText.getText());
                    statement.setString(8, "");
                    statement.execute();
                    query = "INSERT teeth (id) VALUES (?)";
                    statement = db.prepareStatement(query);
                    statement.setInt(1, id+1);
                    statement.execute();
                    db.close();
                    System.out.println("Patient added succefully");
                    Patient.createPatient(id+1);
                }
                catch (Exception e){System.out.println("error: couldn't add a patient");}
            }
        });
    }
    static void setAddPatient ()
    {
        HelloApplication.stage.setScene(addPatientScene);
    }
}