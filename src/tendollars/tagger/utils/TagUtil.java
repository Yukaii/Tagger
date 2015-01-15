package tendollars.tagger.utils;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import tendollars.tagger.MainApp;
import tendollars.tagger.db.DaoManager;
import tendollars.tagger.model.FileInfo;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by yukai on 2015/1/14.
 */
public class TagUtil {

    static boolean isValid(String tagString) {
        return true;
    }

    static public Collection<File> scanFile(File file) {

        Collection<File> files = FileUtils.listFiles(
                file,
                new RegexFileFilter("^(.*?)"),
                DirectoryFileFilter.DIRECTORY
        );

        return files;
    }

    static public ArrayList<String> uniqueArray(String s) {
        ArrayList<String> tags = new ArrayList<String>(Arrays.asList(s.split("\\s+")));
        HashSet<String> hs = new HashSet<>();
        hs.addAll(tags);
        tags.clear();
        tags.addAll(hs);

        return tags;
    }

    static public ArrayList<String> uniqueArrayList(ArrayList<String> arr) {
        HashSet<String> hs = new HashSet<>();
        hs.addAll(arr);
        arr.clear();
        arr.addAll(hs);
        return arr;
    }
    static public ObservableList<FileInfo> uniqueFileInfos(ObservableList<FileInfo> arr) {
        HashSet<FileInfo> hs = new HashSet<>();
        hs.addAll(arr);
        arr.clear();
        arr.addAll(hs);
        return arr;
    }
    public static ObservableList<FileInfo> openDirectory(MainApp mainApp) throws SQLException {
        return openDirectory(mainApp, false);
    }

    public static ObservableList<FileInfo> openDirectory(MainApp mainApp, boolean chooseDefault) throws SQLException {
        if (!chooseDefault) {
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("Choose Start Directory");
            File defaultDirectory = mainApp.getLastAccess();
            chooser.setInitialDirectory(defaultDirectory);

            File chooseDir = null;
            while (chooseDir == null) {
                chooseDir = chooser.showDialog(mainApp.getPrimarySage());
                if (chooseDir == null) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Choose Directory Error");
                    alert.setHeaderText(null);
                    alert.setContentText("You Muse Choose a Directory!");
                    alert.showAndWait();
                }
            }
            mainApp.setLastAccess(chooseDir);
        }

        Collection<File> files = TagUtil.scanFile(mainApp.getLastAccess());
        ArrayList<FileInfo> filelist = new ArrayList<FileInfo>();

        for (File file : files) {
            filelist.add(new FileInfo(file));
        }
        return DaoManager.loadFiles(filelist);
    }

    public static File openDir(MainApp mainApp) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose Start Directory");
        File defaultDirectory = mainApp.getLastAccess();
        chooser.setInitialDirectory(defaultDirectory);
        return chooser.showDialog(mainApp.getPrimarySage());
    }

}
