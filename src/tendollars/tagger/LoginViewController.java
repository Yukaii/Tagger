package tendollars.tagger;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.PopupBuilder;
import javafx.stage.Stage;
import org.controlsfx.dialog.DialogStyle;
import org.controlsfx.dialog.Dialogs;
import tendollars.tagger.model.FileInfo;
import tendollars.tagger.utils.TagUtil;


import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;


/**
 * Created by David on 1/14/15.
 */
public class LoginViewController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;

    private MainApp mainApp;
    private Stage primaryStage;
    private State stage;

    public boolean islog = false;

    public LoginViewController() {

    }
    @FXML
    private void initialize() {
        loginButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() != 0)
                    if (username.getText().toString().equals("admin") && password.getText().equals("0000"))
                    {
                        mainApp.setEditable(true);
                        islog = true;
                        Stage s = (Stage) loginButton.getScene().getWindow();
                        s.close();
                        primaryStage.show();
                    }else if (username.getText().toString().equals("user") && password.getText().equals("0000")) {
                        mainApp.setEditable(false);
                        islog = true;
                        Stage s = (Stage) loginButton.getScene().getWindow();
                        s.close();
                        primaryStage.show();
                    }
                    else {
//                        showStage();
//                        alert();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Error: username or password not Found");
                        alert.showAndWait();
                    }
            }
        });
    }

    public static void showStage(){
        Stage newStage = new Stage();
        VBox comp = new VBox();
        Label alert = new Label("Name");
        comp.getChildren().add(alert);

        Scene stageScene = new Scene(comp, 300, 300);
        newStage.setScene(stageScene);
        newStage.initModality(Modality.WINDOW_MODAL);

        newStage.show();
    }



    public static void alert() {
        try {
            Stage newStage = new Stage();
            FXMLLoader l = new FXMLLoader();
            l.setLocation(MainApp.class.getResource("view/warn.fxml"));

            Pane warn = (Pane) l.load();

//            WarnViewController warnViewController = l.getController();


            Scene stageScene = new Scene(warn);
            newStage.setScene(stageScene);
            newStage.requestFocus();

            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.setResizable(false);

            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMainApp(MainApp mainapp) {
        this.mainApp = mainapp;
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }


}
