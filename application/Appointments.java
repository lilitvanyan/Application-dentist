package com.example.dentisst;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Appointments {
    static void setAppointment() {
        VBox VBox = new VBox();
        VBox.setPadding(new Insets(20, 20, 20, 20));
        Scene AppointmentScene = new Scene(VBox, 500, 500);
        FlowPane flowPane = new FlowPane();
        VBox.setSpacing(10);
        Button backToMenu = new Button("Back to Main Menu");
        backToMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Menu.set_menu();
            }
        });
        HBox HBox1 = new HBox(backToMenu);
        VBox.getChildren().addAll(HBox1);

        Label header = new Label("Appointments");
        HBox HBox2 = new HBox(header);
        HBox2.setAlignment(Pos.CENTER);
        header.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        VBox.getChildren().addAll(HBox2);

        flowPane.setAlignment(Pos.CENTER);

        Vector<String> appointmentList = new Vector<String>();
        DBconnector cnt = new DBconnector();
        Connection db = cnt.getDatabselink();
        try {
            Statement stat = db.createStatement();
            ResultSet res = stat.executeQuery("SELECT * FROM patients");
            while (res.next()) {
                if (res.getString("appointmentdate") != null) {
                    String s = res.getString("appointmentdate") + " " + res.getString("appointmenttime") +
                            "  " + res.getString("name") + " " + res.getString("surname") + "    " + res.getString("appointmentnote");
                    appointmentList.add(s);

                }
            }
        } catch (Exception e) {
            System.out.println("Couldn't fetch patients from database");
        }
        Collections.sort(appointmentList);
        if(!appointmentList.isEmpty()) {
            Label ss = new Label(appointmentList.get(0).substring(0, 10));
            HBox h = new HBox(ss);
            VBox.getChildren().addAll(h);
        }
        for (int i = 0; i < appointmentList.size(); i++) {
            String s = appointmentList.get(i);
            if (i > 0 && !s.substring(0, 10).equals(appointmentList.get(i - 1).substring(0, 10))) {
                Label ss = new Label(s.substring(0,10));
                HBox h = new HBox(ss);
                VBox.getChildren().addAll(h);
            }
            Label sm = new Label(s.substring(10));
            HBox hbox = new HBox(sm);
            VBox.getChildren().addAll(hbox);
        }
        VBox vbox1 = new VBox();

        HelloApplication.stage.setScene(AppointmentScene);
    }
    static int getHours(String t)
    {
        return 10*(t.charAt(0)-'0')+(t.charAt(1)-'0');
    }
    static int getMinutes(String t)
    {
        return 10*(t.charAt(3)-'0')+(t.charAt(4)-'0');
    }
    static int stringToInt(String t)
    {
        if(t.length()>3) return -1;
        int a = 0;
        for(int i = 0; i < t.length(); i++)
        {
            if(t.charAt(i)<'0'||t.charAt(i)>'9') return -1;
            a*=10;
            a+=(t.charAt(i)-'0');
        }
        return a;
    }
    static boolean checkTimeAvailable(int h, int m, int d, int h1, int m1, int d1)
    {
        int a = 60*h+m;
        int b = 60*h1+m1;
        if(b>=a&&a+d>b) return false;
        if(a>=b&&b+d1>a) return false;
        return true;
    }
    static boolean checkTimeFormat(String t)
    {
        if(t.length()!=5) return false;
        if(t.charAt(2) != ':') return false;
        for (int i = 0; i < 5; i++)
        {
            if(i==2) continue;
            if(t.charAt(i) > '9' || t.charAt(i) < '0') return false;
        }
        int hour = getHours(t);
        int minute = getMinutes(t);
        if(hour<9||hour>17||minute>60) return false;
        return true;
    }
    static void addAppointment(int id)
    {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        Scene AppointmentDate = new Scene(vbox, 300, 200);
        Stage AppointmentStage = new Stage();
        AppointmentStage.setScene(AppointmentDate);

        HBox hbox1 = new HBox();
        hbox1.setAlignment(Pos.CENTER);
        hbox1.setSpacing(5);
        vbox.getChildren().addAll(hbox1);

        Label date = new Label("Set date: ");
        DatePicker appointdate = new DatePicker();
        hbox1.getChildren().addAll(date, appointdate);

        HBox hbox2 = new HBox();
        hbox2.setAlignment(Pos.CENTER);
        hbox2.setSpacing(5);
        vbox.getChildren().addAll(hbox2);

        Label time = new Label("Set time(HH:MM):");
        TextField appointtime = new TextField();
        appointtime.setPrefWidth(100);
        hbox2.getChildren().addAll(time, appointtime);

        HBox hbox3 = new HBox();
        hbox3.setAlignment(Pos.CENTER);
        hbox3.setSpacing(5);
        vbox.getChildren().addAll(hbox3);

        Label notes = new Label("Notes:");
        TextField Note = new TextField();
        Note.setPrefWidth(100);
        hbox3.getChildren().addAll(notes, Note);

        HBox hbox4 = new HBox();
        hbox4.setAlignment(Pos.CENTER);
        hbox4.setSpacing(5);
        vbox.getChildren().addAll(hbox4);

        Label dur = new Label("Duration (Minutes):");
        TextField duration = new TextField();
        duration.setPrefWidth(100);
        hbox4.getChildren().addAll(dur, duration);

        Button addappoint = new Button("Add");
        vbox.getChildren().addAll(addappoint);

        AppointmentStage.show();

        addappoint.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(appointdate.getValue()==null)
                {
                    Alert error1 = new Alert(Alert.AlertType.WARNING);
                    error1.setContentText("Enter valid date");
                    error1.show();
                    return;
                }
                if(!checkTimeFormat(appointtime.getText()))
                {
                    Alert error1 = new Alert(Alert.AlertType.WARNING);
                    error1.setContentText("Enter valid time");
                    error1.show();
                    return;
                }
                if(notes.getText().length()>100)
                {
                    Alert error1 = new Alert(Alert.AlertType.WARNING);
                    error1.setContentText("Note is too long");
                    error1.show();
                    return;
                }
                if(stringToInt(duration.getText())==-1)
                {
                    Alert error1 = new Alert(Alert.AlertType.WARNING);
                    error1.setContentText("Enter valid duration");
                    error1.show();
                    return;
                }
                int h = getHours(appointtime.getText());
                int m = getMinutes(appointtime.getText());
                int d = stringToInt(duration.getText());
                DBconnector cnt = new DBconnector();
                Connection db = cnt.getDatabselink();
                try {
                    Statement stat1 = db.createStatement();
                    ResultSet res = stat1.executeQuery("SELECT * FROM patients");
                    while(res.next())
                    {
                        if(res.getInt(1)==id) continue;
                        if(!appointdate.getValue().toString().equals(res.getString("appointmentdate"))) continue;
                        int h1 = getHours(res.getString("appointmenttime"));
                        int m1 = getMinutes(res.getString("appointmenttime"));
                        int d1 = res.getInt("appointmentduration");
                        if(!checkTimeAvailable(h,m,d,h1,m1,d1))
                        {
                            Alert error1 = new Alert(Alert.AlertType.WARNING);
                            error1.setContentText("Unavailable");
                            error1.show();
                            return;
                        }
                    }
                    String sm = "update patients set appointmentdate=? where id=?";
                    PreparedStatement stat = db.prepareStatement(sm);
                    stat.setDate(1, Date.valueOf(appointdate.getValue()));
                    stat.setInt(2, id);
                    stat.execute();
                    sm = "update patients set appointmenttime=? where id=?";
                    stat = db.prepareStatement(sm);
                    stat.setString(1, appointtime.getText());
                    stat.setInt(2, id);
                    stat.execute();
                    sm = "update patients set appointmentnote=? where id=?";
                    stat = db.prepareStatement(sm);
                    stat.setString(1, Note.getText());
                    stat.setInt(2, id);
                    stat.execute();
                    sm = "update patients set appointmentduration=? where id=?";
                    stat = db.prepareStatement(sm);
                    stat.setInt(1, stringToInt(duration.getText()));
                    stat.setInt(2, id);
                    stat.execute();
                    AppointmentStage.close();
                    setAppointment();
                    System.out.println("Appointment added successfully");
                    }catch (Exception e) {System.out.println("Couldn't add appointment");}

            }
        });


    }
}
