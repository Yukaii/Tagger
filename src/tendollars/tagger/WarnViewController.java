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



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by David on 1/14/15.
 */
public class WarnViewController {

    @FXML
    private Button confirm;

    @FXML
    private void initialize() {
        confirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage s = (Stage)confirm.getScene().getWindow();
                s.close();
            }
        });
    }


}
