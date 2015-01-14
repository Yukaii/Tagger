package tendollars.tagger.db;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import tendollars.tagger.model.FileInfo;

import java.sql.SQLException;

/**
 * Created by yukai on 2015/1/14.
 */
public class FileDao extends BaseDaoImpl<FileInfo, Integer> {
    public FileDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, FileInfo.class);
    }
}
