package tendollars.tagger.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.beans.property.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.persistence.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by yukai on 2015/1/14.
 */

@DatabaseTable(tableName = "files")
public class FileInfo {

    public final static String ID_FIELD_NAME = "id";
    public final static String NAME_FIELD_NAME = "name";
    public final static String PATH_FIELD_NAME = "path";
    public final static String EXT_FIELD_NAME = "extension";
    public final static String TAG_FIELD_NAME = "tag";
    public final static String STATUS_FIELD_NAME = "status";

    private IntegerProperty id;
    @DatabaseField(columnName = ID_FIELD_NAME, generatedId = true)
    private int _id ;

    private StringProperty _name;
    @DatabaseField(columnName = NAME_FIELD_NAME, dataType = DataType.STRING)
    private String name;

    private StringProperty _path;

    @DatabaseField(columnName = PATH_FIELD_NAME, dataType = DataType.STRING, unique = true)
    private String path;

    private StringProperty _extension;
    @DatabaseField(columnName = EXT_FIELD_NAME, dataType = DataType.STRING)
    private String extension;

    private StringProperty _status;
    @DatabaseField(columnName = STATUS_FIELD_NAME, dataType = DataType.STRING)
    private String status;

    // raw tag strings, eg. "hello world"
    private StringProperty _tag;
    // splitted tags, ["hello", "world"]
    private ArrayList<String> tags;

    @DatabaseField(columnName = TAG_FIELD_NAME, dataType = DataType.STRING)
    private String tag;

    public FileInfo() {

    }

    public FileInfo(File file) {
        this.name = file.getName();
        this.path = file.getAbsolutePath();
        this.tag = "";
        this.status = "new";
        this.extension = FilenameUtils.getExtension(file.getAbsolutePath());
    }

    public int getId() {
        if (id==null) {
            return _id ;
        } else {
            return id.get();
        }
    }
    public void setId(int id) {
        if (this.id==null) {
            _id=id ;
        } else {
            this.id.set(id);
        }
    }

    public IntegerProperty idProperty() {
        if (id==null) {
            id = new SimpleIntegerProperty(this, "id", _id);
        }
        return id ;
    }

    public String getName() {
        if (_name == null) {
            return name;
        }
        else {
            return _name.get();
        }
    }

    public void setName(String name) {
        if (this._name == null) {
            name = name;
        } else {
            this._name.set(name);
        }
    }

    public StringProperty nameProperty() {
        if (this._name == null) {
            _name = new SimpleStringProperty(this, "name", name);
        }
        return _name;
    }


    public String getPath() {
        if (_path == null) {
            return path;
        }
        else {
            return _path.get();
        }
    }

    public void setPath(String path) {
        if (this._path == null) {
            path = path;
        } else {
            this._path.set(path);
        }
    }

    public StringProperty pathProperty() {
        if (this._path == null) {
            this._path = new SimpleStringProperty(this, "path", path);
        }
        return _path;
    }


    public String getExtension() {
        if (_extension == null) {
            return extension;
        }
        else {
            return this._extension.get();
        }
    }

    public void setExtension(String extension) {
        if (this._extension == null) {
            extension = extension;
        } else {
            this._extension.set(extension);
        }
    }

    public StringProperty extensionProperty() {
        if (this._extension == null) {
            _extension = new SimpleStringProperty(this, "extension", extension);
        }
        return _extension;
    }

    public String getTag() {
        if (_tag == null) {
            return tag;
        }
        else {
            return _tag.get();
        }
    }

    public void setTag(String tag) {
        tags = new ArrayList<String>(Arrays.asList(tag.split("\\s+")));
        if (this._tag == null) {
            tag = tag;
        } else {
            this._tag.set(tag);
        }
    }

    public StringProperty tagProperty() {
        if (this._tag == null) {
            this._tag = new SimpleStringProperty(this, "tag", tag);
        }
        return _tag;
    }

    public ArrayList<String> getTags() {
        if (this.tags == null) {
            this.setTag(this.tag);
        }
        return tags;
    }


    public String getStatus() {
        if (_status == null) {
            return status;
        }
        else {
            return this._status.get();
        }
    }

    public void setStatus(String status) {
        if (this._status == null) {
            status = status;
        } else {
            this._status.set(status);
        }
    }

    public StringProperty statusProperty() {
        if (this._status == null) {
            _status = new SimpleStringProperty(this, "status", status);
        }
        return _status;
    }

}
