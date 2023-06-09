package al.fshn.notsocleanearth.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class UserLogin {
    public UserLogin(Long uid, String email, String password) {
        this.email = email;
        this.password = password;
        this.uid = uid;
    }

    @PrimaryKey
    @NotNull
    public Long uid;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "password")
    public String password;
}

