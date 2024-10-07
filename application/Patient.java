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
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


public class Patient {

    static private Scene patient;
    static private ImageView teeth;
    static private Image image, img;
    static private int[] a = {32640, 128, 8323200, 16711808, 8323073, 16744193, 32576, 4934708, 65536, 256, 8323328, 16711872, 16711681, 16744256, 8355648, 65281, 8372160, 32704, 16711936, 16744320, 16760704, 8355585, 8388544, 65408, 8388608, 32768, 16744448, 16744384, 16776961, 16777056, 8388480, 8388353};
    static private Map<Integer, Integer> colornumber=new HashMap<Integer, Integer>();
    static private void fill()
    {
        colornumber.put(1,0);
        colornumber.put(0,0);
        for (int i = 0; i < 32; i++) colornumber.put(a[i], i);
    }

    static public void createPatient(int id) {
        fill();
        Button BackToPatientList = new Button("Back to patients list"), delete = new Button("Delete"), addAppointment = new Button("Appointment");
        Label birthday = new Label(), email = new Label(), gender = new Label(), name = new Label(), number = new Label(), surname = new Label();
        Label Birthday = new Label("Birthday:");
        Label Email = new Label("Email:");
        Label Gender = new Label("Gender:");
        Label Number = new Label("Phone:");
        try {
            image = new Image(new FileInputStream("/Users/macbookpro/Desktop/CSIA/src/main/java/com/example/dentisst/png.png"));
            img = new Image(new FileInputStream("/Users/macbookpro/Desktop/CSIA/src/main/java/com/example/dentisst/png1.png"));
            teeth = new ImageView(image);
        } catch (Exception e) {
            System.out.println("arden chgitem");
        }


        Connection db = new DBconnector().getDatabselink();
        try {
            Statement stat = db.createStatement();
            String str = "SELECT * FROM patients WHERE id=" + String.valueOf(id);
            ResultSet res = stat.executeQuery(str);
            while (res.next()) {
                name.setText(res.getString("name"));
                surname.setText(res.getString("surname"));
                gender.setText(res.getString("gender"));
                number.setText(res.getString("phone"));
                email.setText(res.getString("email"));
                birthday.setText(res.getDate("birth").toString());
            }
        } catch (Exception e) {
            System.out.println("error: couldn't fetch data about patient");
        }

        SplitPane splitpane = new SplitPane();
        GridPane gridpane = new GridPane();
        gridpane.setAlignment(Pos.CENTER);
        ColumnConstraints one = new ColumnConstraints(10, 100, Double.MAX_VALUE);
        one.setHalignment(HPos.CENTER);
        one.setHgrow(Priority.SOMETIMES);
        ColumnConstraints two = new ColumnConstraints(10, 100, Double.MAX_VALUE);
        two.setHalignment(HPos.CENTER);
        two.setHgrow(Priority.SOMETIMES);
        ColumnConstraints three = new ColumnConstraints(10, 100, Double.MAX_VALUE);
        three.setHalignment(HPos.CENTER);
        three.setHgrow(Priority.SOMETIMES);
        gridpane.getColumnConstraints().addAll(one, two, three);
        RowConstraints[] rowconstraints = new RowConstraints[10];
        for (int i = 0; i < 10; i++) {
            rowconstraints[i] = new RowConstraints(10, 50, Double.MAX_VALUE);
            (rowconstraints[i]).setValignment(VPos.CENTER);
            (rowconstraints[i]).setVgrow(Priority.SOMETIMES);
            gridpane.getRowConstraints().add(rowconstraints[i]);
        }

        Label[] array;
        array = new Label[32];

        gridpane.setPadding(new Insets(10, 10, 10, 10));
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.TOP_LEFT);
        hbox.prefWidth(200);
        hbox.getChildren().add(BackToPatientList);
        gridpane.add(hbox, 0, 0, 2, 1);
        gridpane.add(addAppointment, 0, 9, 1, 1);
        gridpane.add(delete, 1, 9, 1, 1);
        Label ss = new Label(name.getText() + " " + surname.getText());
        gridpane.add(ss, 0, 3, 2, 1);
        gridpane.add(Birthday, 0, 4);
        gridpane.add(birthday, 1, 4);
        gridpane.add(Gender, 0, 5);
        gridpane.add(gender, 1, 5);
        gridpane.add(Number, 0, 6);
        gridpane.add(number, 1, 6);
        gridpane.add(Email, 0, 7);
        gridpane.add(email, 1, 7);

        splitpane.getItems().add(gridpane);

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(teeth);
        splitpane.getItems().add(vbox);
        splitpane.setDividerPositions(0.299);
        patient = new Scene(splitpane, 1000, 1000);
        HelloApplication.stage.setScene(patient);

        GridPane gridpane2 = new GridPane();

        //  gridpane2.setAlignment(Pos.CENTER);
        ColumnConstraints one2 = new ColumnConstraints(10, 100, Double.MAX_VALUE);

        RowConstraints[] rowconstraints2 = new RowConstraints[32];
        for (int i = 0; i < 32; i++) {
            rowconstraints2[i] = new RowConstraints(10, 50, Double.MAX_VALUE);
            (rowconstraints2[i]).setValignment(VPos.CENTER);
            (rowconstraints2[i]).setVgrow(Priority.SOMETIMES);
            gridpane2.getRowConstraints().add(rowconstraints2[i]);
        }
        for (int i = 0; i < 32; i++) {
            String s = new String();
            try {
                String sm = "SELECT * FROM teeth WHERE id=" + String.valueOf(id);
                Statement stat = db.createStatement();
                ResultSet res = stat.executeQuery(sm);
                while (res.next()) {
                    if(res.getString(i+1)==null) s="";
                    else s = res.getString(i + 1);
                }
            } catch (Exception e) {System.out.println("error:something went wrong with teeth");}
            array[i] = new Label("tooth number " + i + ":" + s);
            gridpane2.add(array[i], 0, i);
        }
        splitpane.getItems().add(gridpane2);

        BackToPatientList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PatientList.set_patient();
            }
        });
        addAppointment.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Appointments.addAppointment(id);
            }
        });
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String sm = "DELETE FROM patients WHERE id=?";
                    PreparedStatement stat = db.prepareStatement(sm);
                    stat.setInt(1, id);
                    stat.execute();
                    PatientList.set_patient();
                } catch (Exception e) {
                    System.out.println("error: couldn't delete patient");
                }
            }
        });
        PixelReader pixelReader = img.getPixelReader();
        teeth.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            int x = (int) event.getX();
            int y = (int) event.getY();
            int z = -pixelReader.getArgb(x, y);
            System.out.println(z);
            z = colornumber.get(z);
            System.out.println(z);
            if (z == 0) return;
            try {
                String sm = "SELECT * FROM teeth WHERE id=" + String.valueOf(id);
                Statement stat = db.createStatement();
                ResultSet res = stat.executeQuery(sm);
                TextArea note = new TextArea();
                while (res.next()) {
                    if (res.getString(z + 1) == null) break;
                    String str = res.getString(z + 1);
                    note.setText(str);
                }
                Scene s = new Scene(note, 200, 200);
                Stage st = new Stage();
                st.setScene(s);
                st.setTitle(String.valueOf(z));
                st.showAndWait();
                sm = "UPDATE teeth SET t" + String.valueOf(z) + " = ? WHERE id=" + String.valueOf(id);
                System.out.println(sm);
                PreparedStatement statt = db.prepareStatement(sm);
                statt.setString(1, note.getText());
                statt.execute();
                System.out.println("Note updated succesfuly");
            } catch (Exception e) {
                System.out.println("error: coulfn't fetch data about tooth");
            }

        });
    }
}
