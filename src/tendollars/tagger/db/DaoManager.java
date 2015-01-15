package tendollars.tagger.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.TableUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import tendollars.tagger.model.FileInfo;
import tendollars.tagger.model.User;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by yukai on 2015/1/14.
 */
public class DaoManager extends com.j256.ormlite.dao.DaoManager {
    private static final String DbUrl = "jdbc:sqlite:test.db";

    public static void saveFiles(Collection<File> fs) throws SQLException{
        JdbcPooledConnectionSource connectionSource =
                new JdbcPooledConnectionSource(DbUrl);
        try {
            TableUtils.createTable(connectionSource, FileInfo.class);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        FileDao fileDao = new FileDao(connectionSource);
        fileDao.createFromFileList(fs);
        connectionSource.close();
    }

    public static void saveFileInfo(FileInfo fileInfo) throws SQLException{
        JdbcPooledConnectionSource connectionSource =
                new JdbcPooledConnectionSource(DbUrl);

        FileDao fileDao = new FileDao(connectionSource);

        UpdateBuilder<FileInfo, Integer> updateBuilder = fileDao.updateBuilder();
        updateBuilder.where().eq(FileInfo.PATH_FIELD_NAME, fileInfo.getPath());
        updateBuilder.updateColumnValue(FileInfo.TAG_FIELD_NAME, fileInfo.getTag());
        updateBuilder.updateColumnValue(FileInfo.STATUS_FIELD_NAME, fileInfo.getStatus());
        updateBuilder.update();

        connectionSource.close();
    }

    public static void resetDatabase() throws SQLException{
        JdbcPooledConnectionSource connectionSource =
                new JdbcPooledConnectionSource(DbUrl);

        TableUtils.dropTable(connectionSource, FileInfo.class, true);

    }

    public static ObservableList<FileInfo> loadFiles(ArrayList<FileInfo> filelist) throws SQLException{
        JdbcPooledConnectionSource connectionSource =
                new JdbcPooledConnectionSource(DbUrl);
        try {
            TableUtils.createTable(connectionSource, FileInfo.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        FileDao fileDao = new FileDao(connectionSource);
        ObservableList<FileInfo> fileinfos = FXCollections.observableArrayList();

        for (FileInfo file : filelist) {
            System.out.println("the file: " + file.getPath());
            QueryBuilder<FileInfo, Integer> queryBuilder = fileDao.queryBuilder();
            try {
                FileInfo result = fileDao.queryForFirst(queryBuilder.where().eq(FileInfo.PATH_FIELD_NAME, file.getPath()).prepare());
                if (result != null) {
                    fileinfos.add(result);
                }
                else {
                    fileinfos.add(file);
                    try {
                        fileDao.create(file);
                    }
                    catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        connectionSource.close();
        return fileinfos;
    }
}
