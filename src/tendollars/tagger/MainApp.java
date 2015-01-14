package tendollars.tagger;

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
import sun.rmi.runtime.Log;
import tendollars.tagger.model.FileInfo;
import tendollars.tagger.utils.TagUtil;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.beans.EventHandler;
import java.io.IOException;
import java.io.File;
import java.util.Collection;
import java.util.EventListener;

/**
 * Created by yukai on 2015/1/14.
 */
public class MainApp extends Application {

    private Stage primarySage;
    private BorderPane rootLayout;
    private ObservableList<FileInfo> fileInfos = FXCollections.observableArrayList();
    private File lastAccess;

    LoginViewController loginViewController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primarySage = primaryStage;
        this.primarySage.setTitle("Tagger");

        showLoginView();
//        ashowLoginView();
        this.lastAccess = new File("/Users/David/Desktop/");
        for (File file : TagUtil.scanFile(this.lastAccess)) {
            fileInfos.add(new FileInfo(file));
        }

        showFileOverview();
        this.primarySage.hide();

        this.primarySage.setOnCloseRequest(new javafx.event.EventHandler<javafx.stage.WindowEvent>() {
            @Override
            public void handle(javafx.stage.WindowEvent event) {
                primaryStage.hide();
                showLoginView();
            }
        });



    }


    public void showFileOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FileOverview.fxml"));
            AnchorPane fileOverview = (AnchorPane) loader.load();

            FileOverviewController fileOverviewController = loader.getController();

            fileOverviewController.setMainApp(this);
            fileOverviewController.setPrimaryStage(this.primarySage);

            Scene scene = new Scene(fileOverview);
            primarySage.setScene(scene);
            primarySage.setResizable(false);
            primarySage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showLoginView() {
        try {
            Stage sa = new Stage();
            sa.setTitle("Login");
            FXMLLoader l = new FXMLLoader();
            l.setLocation(MainApp.class.getResource("view/login.fxml"));
            AnchorPane login = (AnchorPane) l.load();

            // add controller
            this.loginViewController = l.getController();
            this.loginViewController.setMainApp(this);
            this.loginViewController.setPrimaryStage(this.primarySage);
            //

            Scene s = new Scene(login);
            sa.setResizable(false);
            sa.setScene(s);
            sa.show();

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
}
