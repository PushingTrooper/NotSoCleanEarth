package al.fshn.notsocleanearth.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserLogin {
    public UserLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "password")
    public String password;
}

