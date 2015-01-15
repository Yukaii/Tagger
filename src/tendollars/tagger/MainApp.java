package tendollars.tagger;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.sun.deploy.util.FXLoader;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import tendollars.tagger.db.DaoManager;
import tendollars.tagger.db.FileDao;
import tendollars.tagger.db.UserDao;
import sun.rmi.runtime.Log;
import tendollars.tagger.model.FileInfo;
import tendollars.tagger.utils.TagUtil;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.beans.EventHandler;
import java.io.IOException;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;

/**
 * Created by yukai on 2015/1/14.
 */
public class MainApp extends Application {

    private Stage primaryStage;
    private Stage loginStage;

    private BorderPane rootLayout;
    private ObservableList<FileInfo> fileInfos = FXCollections.observableArrayList();
    private File lastAccess;
    private boolean editable;


//    LoginViewController loginViewController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Tagger");

        //this.primaryStage.hide();

//        this.primaryStage.setOnCloseRequest(new javafx.event.EventHandler<javafx.stage.WindowEvent>() {
//            @Override
//            public void handle(javafx.stage.WindowEvent event) {
//                primaryStage.close();
//                showLoginView();
//                showFileOverview(true);
//            }
//        });

        showLoginView();
        showFileOverview(false);

    }

    public void showFileOverview(Boolean chooseDefault) {
        loginStage.close();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FileOverview.fxml"));
            AnchorPane fileOverview = (AnchorPane) loader.load();

            FileOverviewController fileOverviewController = loader.getController();

            fileOverviewController.setMainApp(this);
            fileOverviewController.setPrimaryStage(this.primaryStage);
            fileOverviewController.setEditable(editable);

            Scene scene = new Scene(fileOverview);
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.setResizable(false);
            primaryStage.show();
            firstLoadFiles(chooseDefault);
            fileOverviewController.setTableItems();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void firstLoadFiles(boolean chooseDefault) {
        this.lastAccess = new File(System.getProperty("user.home"), "Desktop");

        try {
            fileInfos = TagUtil.openDirectory(this, chooseDefault);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void showLoginView() {
        try {
            loginStage = new Stage();
            loginStage.setTitle("Login");
            FXMLLoader l = new FXMLLoader();
            l.setLocation(MainApp.class.getResource("view/login.fxml"));
            AnchorPane login = (AnchorPane) l.load();

            // add controller
            LoginViewController loginViewController = l.getController();
            loginViewController.setMainApp(this);
            loginViewController.setPrimaryStage(this.primaryStage);
            //

            Scene s = new Scene(login);
            loginStage.setResizable(false);
            loginStage.setScene(s);
            loginStage.centerOnScreen();
            loginStage.showAndWait();

        } catch (IOException ee) {
            ee.printStackTrace();
        }
    }


    public ObservableList<FileInfo> getFileInfos() {
        return fileInfos;
    }

    public void setLastAccess(File lastAccess) {
        this.lastAccess = lastAccess;
    }

    public File getLastAccess() {
        return lastAccess;
    }

    public Stage getPrimarySage() {
        return primaryStage;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
