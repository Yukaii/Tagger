package tendollars.tagger.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.TableUtils;
import tendollars.tagger.model.FileInfo;
import tendollars.tagger.model.User;

import java.io.File;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by yukai on 2015/1/14.
 */
public class DaoManager extends com.j256.ormlite.dao.DaoManager {
    private JdbcPooledConnectionSource connectionSource = null;
    private static Dao<User, String> userdao = null;

    public void DaoManager() throws SQLException {

        connectionSource = new JdbcPooledConnectionSource("jdbc:sqlite:test.db");
        try {
            this.connectionSource.setMaxConnectionAgeMillis(5 * 60* 1000);
            this.connectionSource.setCheckConnectionsEveryMillis(60 * 1000);
            this.connectionSource.setTestBeforeGet(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally {
            this.connectionSource.close();
        }
    }

    public static void saveFiles(Collection<File> fs) throws SQLException{
        JdbcPooledConnectionSource connectionSource =
                new JdbcPooledConnectionSource("jdbc:sqlite:test.db");
        try {
            TableUtils.createTable(connectionSource, FileInfo.class);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        FileDao fileDao = new FileDao(connectionSource);
//        QueryBuilder<FileInfo, Integer> queryBuilder = fileDao.queryBuilder();
//        Where<FileInfo, Integer> where = queryBuilder.where();
//        System.out.println(where.eq("path", f.getPath()).prepare());
        for (File f : fs) {
            try {
                fileDao.create(new FileInfo(f));
            }catch (SQLException e) {
                // File path already exist :p
                e.printStackTrace();
            }
        }
        connectionSource.close();
    }

    public static void saveFile(File f) {
    
    }

}
