package tendollars.tagger.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import tendollars.tagger.db.UserDao;

import javax.persistence.Id;

/**
 * Created by yukai on 2015/1/14.
 */

@DatabaseTable(tableName = "users", daoClass = UserDao.class)
public class User {
    @Id
    private String username;
    @DatabaseField(canBeNull = false)
    private String password;
    @DatabaseField(columnName = "admin")
    private Boolean isAdmin;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.isAdmin = false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {

        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
