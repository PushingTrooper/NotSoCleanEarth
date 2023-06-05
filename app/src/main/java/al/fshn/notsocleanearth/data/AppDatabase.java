package al.fshn.notsocleanearth.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import al.fshn.notsocleanearth.data.model.CurrentlyLoggedInUser;
import al.fshn.notsocleanearth.data.model.UserLogin;

@Database(entities = {UserLogin.class, CurrentlyLoggedInUser.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserLoginDao userDao();
    public abstract CurrentlyLoggedInUserDao currentlyLoggedInUserDao();
}

