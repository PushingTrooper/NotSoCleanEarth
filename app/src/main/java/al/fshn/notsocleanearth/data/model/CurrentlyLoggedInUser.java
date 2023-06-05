package al.fshn.notsocleanearth.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CurrentlyLoggedInUser {
    public CurrentlyLoggedInUser(Long uid, String email, String password) {
        this.uid = uid;
        this.email = email;
        this.password = password;
    }

    @PrimaryKey
    public Long uid;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "password")
    public String password;
}

