package al.fshn.notsocleanearth.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import al.fshn.notsocleanearth.data.model.CurrentlyLoggedInUser;
import al.fshn.notsocleanearth.data.model.UserLogin;

@Dao
public interface CurrentlyLoggedInUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCurrentUser(CurrentlyLoggedInUser userLogin);

    @Query("SELECT * FROM currentlyLoggedInUser")
    List<CurrentlyLoggedInUser> getAll();

    @Query("DELETE FROM currentlyLoggedInUser")
    void deleteAll();
}
