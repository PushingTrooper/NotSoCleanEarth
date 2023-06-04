package al.fshn.notsocleanearth.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import al.fshn.notsocleanearth.data.model.UserLogin;

@Database(entities = {UserLogin.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserLoginDao userDao();
}

