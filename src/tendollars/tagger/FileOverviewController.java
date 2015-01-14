package tendollars.tagger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.controlsfx.dialog.DialogStyle;
import org.controlsfx.dialog.Dialogs;
import tendollars.tagger.db.DaoManager;
import tendollars.tagger.model.FileInfo;
import tendollars.tagger.utils.TagUtil;


import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by yukai on 2015/1/14.
 */
public class FileOverviewController {
    @FXML
    private TableView<FileInfo> fileInfoTable;
    @FXML
    private TableColumn<FileInfo, String> pathColumn;
    @FXML
    private TableColumn<FileInfo, String> tagColumn;
    @FXML
    private TableColumn<FileInfo, String> statusColumn;
    @FXML
    private TableColumn<FileInfo, String> nameColumn;


    @FXML
    private TextField searchField;

    @FXML
    private VBox tagBox;

    private MainApp mainApp;
    private Stage primaryStage;

    public FileOverviewController() {}

    @FXML
    private void initialize() {
        pathColumn.setCellValueFactory(cellData -> cellData.getValue().pathProperty());
        tagColumn.setCellValueFactory(cellData -> cellData.getValue().tagProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        fileInfoTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showFileTags(newValue)
        );

        fileInfoTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileInfo f = fileInfoTable.getSelectionModel().getSelectedItem();
                // doubld click!
                if (event.getClickCount() > 1) {

                    Optional<String> response = Dialogs.create()
                             .title("Edit Tag")
                             .masthead("Split by spaces For example: hello world")
                             .style(DialogStyle.NATIVE)
                             .showTextInput(f.getTag());

                    if (response.isPresent()) {
                        String tag = response.get();
                        f.setTag(tag);

                        showFileTags(f);
                    }
                }


            }
        });
    }

    @FXML
    private void searchFile() {
        System.out.println("hit search button");
    }

    private void showFileTags(FileInfo f) {
        tagBox.getChildren().clear();
        ArrayList<String> tags = f.getTags();

        if (tags == null || tags.size() == 0 || tags.size() == 1 && tags.get(0).equals("")) {
            return;
        }

        tagBox.getChildren().add(new Label(tags.size() + " tags"));

        for (String t : tags) {
            tagBox.getChildren().add(new Button(t));
        }

        tagBox.setSpacing(7);
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        fileInfoTable.setItems(mainApp.getFileInfos());

    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }


    @FXML
    private void openDirectory() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose Start Directory");
        File defaultDirectory = mainApp.getLastAccess();
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(primaryStage);

        if (selectedDirectory != null) {
            mainApp.setLastAccess(selectedDirectory);

            Collection<File> files = TagUtil.scanFile(selectedDirectory);
            ObservableList<FileInfo> fileInfos = FXCollections.observableArrayList();
            for (File file : files) {
                fileInfos.add(new FileInfo(file));
            }

            fileInfoTable.setItems(fileInfos);
        }

    }
}
