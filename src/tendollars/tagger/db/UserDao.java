package tendollars.tagger.db;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import tendollars.tagger.model.User;

import java.sql.*;

/**
 * Created by yukai on 2015/1/14.
 */


public class UserDao extends BaseDaoImpl<User, String>{

    public UserDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, User.class);
    }
}

