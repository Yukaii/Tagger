package tendollars.tagger;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.sun.deploy.util.FXLoader;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import tendollars.tagger.model.FileInfo;
import tendollars.tagger.utils.TagUtil;

import java.io.IOException;
import java.io.File;
import java.util.Collection;

/**
 * Created by yukai on 2015/1/14.
 */
public class MainApp extends Application {

    private Stage primarySage;
    private BorderPane rootLayout;
    private ObservableList<FileInfo> fileInfos = FXCollections.observableArrayList();
    private File lastAccess;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primarySage = primaryStage;
        this.primarySage.setTitle("Tagger");

        this.lastAccess = new File(System.getProperty("user.home"), "Desktop");
        Collection<File> files = TagUtil.scanFile(this.lastAccess);
        for (File file : files) {
            fileInfos.add(new FileInfo(file));
        }
        DaoManager.saveFiles(files);
        showFileOverview();
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
            primarySage.show();

        } catch (IOException e) {
            e.printStackTrace();
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
