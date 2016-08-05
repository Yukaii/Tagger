package tendollars.tagger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.controlsfx.dialog.DialogStyle;
import org.controlsfx.dialog.Dialogs;
import tendollars.tagger.db.DaoManager;
import tendollars.tagger.model.FileInfo;
import tendollars.tagger.utils.TagUtil;


import java.io.File;
import java.sql.SQLException;
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
    @FXML
    private MenuButton actionMenu;

    private MainApp mainApp;
    private Stage primaryStage;

    private ObservableList<FileInfo> currentDirectoryFiles;

    boolean editable;

    public FileOverviewController() {
    }


    @FXML
    private void initialize() {
        pathColumn.setCellValueFactory(cellData -> cellData.getValue().pathProperty());
        tagColumn.setCellValueFactory(cellData -> cellData.getValue().tagProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        // Table initialize
        fileInfoTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {showFileTags(newValue);}
        );

        fileInfoTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (editable == true) {
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
                            if (!f.getTag().equals(tag)) f.setStatus("modified");
                            f.setTag(tag);

                            try {
                                DaoManager.saveFileInfo(f);
                            } catch (SQLException e) {

                            }
                            showFileTags(f);
                        }
                    }
                }


            }
        });
        fileInfoTable.setPlaceholder(new Label("沒有結果"));

        // 一個隨打隨找的 style
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchBy(newValue);
        });
    }

    private void searchBy(String searchString) {
        ObservableList<FileInfo> searchResult = FXCollections.observableArrayList();

        if (searchString.equals("") || searchString.equals(" ")) {
            fileInfoTable.setItems(currentDirectoryFiles);
            return;
        }

        ArrayList<String> searchTags = TagUtil.uniqueArray(searchString);

        for (FileInfo f : currentDirectoryFiles) {
            for (String tag: searchTags) {
                if (f.getTag().toLowerCase().contains(tag.toLowerCase())) {
                    searchResult.add(f);
                }
            }

        }
        fileInfoTable.setItems(TagUtil.uniqueFileInfos(searchResult));
    }
    /*
       終極防呆，覺得小確幸
     */
    @FXML
    private void resetDatabase() throws SQLException{
        if (!editable) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Privilege not Allowed");
            alert1.setHeaderText(null);
            alert1.setContentText("You can't do this, NONONO");
            alert1.showAndWait();
            return;
        }

        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Delete Confirmation");
        alert1.setHeaderText(null);
        alert1.setContentText("Do you want to delete tags?");
        Optional<ButtonType> result = alert1.showAndWait();

        if (result.get() == ButtonType.OK) {
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert2.setTitle("Delete Confirmation");
            alert2.setHeaderText(null);
            alert2.setContentText("Do you really want to delete tags?");
            result = alert2.showAndWait();

            if (result.get() == ButtonType.OK) {
                Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION);
                alert3.setTitle("Delete Confirmation");
                alert3.setHeaderText(null);
                alert3.setContentText("Do you really really really reallllllly want to delete those f**king tags?");
                result = alert3.showAndWait();

                if (result.get() == ButtonType.OK) {
                    DaoManager.resetDatabase();
                    currentDirectoryFiles = TagUtil.openDirectory(mainApp, true);
                    fileInfoTable.setItems(currentDirectoryFiles);
                    mainApp.setFileInfos(currentDirectoryFiles);


                    Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
                    alert4.setTitle("Delete Done!");
                    alert4.setHeaderText(null);
                    alert4.setContentText("...........好吧你贏了，好棒棒");
                    alert4.show();
                }
            }
        }
    }

    private void showFileTags(FileInfo f) {
        tagBox.getChildren().clear();
        ArrayList<String> tags = f.getTags();

        if (tags == null || tags.size() == 0 || tags.size() == 1 && tags.get(0).equals("")) {
            return;
        }

        tagBox.getChildren().add(new Label(tags.size() + " tags"));

        for (String t : tags) {
            Button tagbtn = new Button(t);
            tagbtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    StringBuilder strbuilder = new StringBuilder();
                    strbuilder.append(searchField.getText());
                    strbuilder.append(tagbtn.getText()).append(" ");
                    searchField.setText(strbuilder.toString());
                }
            });
            tagBox.getChildren().add(tagbtn);
        }

        tagBox.setSpacing(7);
    }



    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void setTableItems() {
        currentDirectoryFiles = mainApp.getFileInfos();
        fileInfoTable.setItems(currentDirectoryFiles);
    }
    @FXML
    private void openDirectory() {
        File selectedDirectory = TagUtil.openDir(mainApp);

        if (selectedDirectory != null) {
            mainApp.setLastAccess(selectedDirectory);

            Collection<File> files = TagUtil.scanFile(selectedDirectory);
            ArrayList<FileInfo> filelist = new ArrayList<FileInfo>();
            for (File f : files) filelist.add(new FileInfo(f));

            ObservableList<FileInfo> fileInfos;
            try {
                fileInfos = DaoManager.loadFiles(filelist);
                currentDirectoryFiles = fileInfos;
                fileInfoTable.setItems(fileInfos);
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void clearText() {
        searchField.setText("");
    }

    public MainApp getMainApp() {
        return mainApp;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
        try {

            if (editable) {
                actionMenu.setText("Admin");
            }else {
                actionMenu.setText("Artist");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
