package tendollars.tagger.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by yukai on 2015/1/14.
 */
public class FileInfo {
    private final StringProperty name;
    private final StringProperty path;
    private final StringProperty extension;

    // raw tag strings, eg. "hello world"
    private final StringProperty tag;

    // splitted tags, ["hello", "world"]
    ArrayList<String> tags;


    public FileInfo() {
        this(null);
    }

    public FileInfo(File file) {
        this.name = new SimpleStringProperty(file.getName());
        this.path = new SimpleStringProperty(file.getAbsolutePath());
        this.tag = new SimpleStringProperty("");
        this.extension = new SimpleStringProperty(FilenameUtils.getExtension(file.getAbsolutePath()));
        this.tags = new ArrayList<String>();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setPath(String path) {
        this.path.set(path);
    }

    public void setExtension(String extension) {
        this.extension.set(extension);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getPath() {
        return path.get();
    }

    public StringProperty pathProperty() {
        return path;
    }

    public String getExtension() {
        return extension.get();
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTag(String tag) {
        tags = new ArrayList<String>(Arrays.asList(tag.split("\\s+")));
        this.tag.set(tag);
    }

    public String getTag() {

        return tag.get();
    }

    public StringProperty tagProperty() {
        return tag;
    }

    public StringProperty extensionProperty() {
        return extension;
    }
}
