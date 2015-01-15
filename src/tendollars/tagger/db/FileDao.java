package tendollars.tagger.db;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import tendollars.tagger.model.FileInfo;

import java.io.File;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by yukai on 2015/1/14.
 */
public class FileDao extends BaseDaoImpl<FileInfo, Integer> {
    public FileDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, FileInfo.class);
    }

    public void createFromFileList(Collection<File> files) {
        for (File f : files) {
            try {
                this.create(new FileInfo(f));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
