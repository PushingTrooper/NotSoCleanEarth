package al.fshn.notsocleanearth.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import al.fshn.notsocleanearth.data.model.UserLogin;

@Dao
public interface UserLoginDao {
    @Query("SELECT * FROM userlogin")
    List<UserLogin> getAll();

    @Query("SELECT * FROM userlogin WHERE uid IN (:userIds)")
    List<UserLogin> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM userlogin WHERE email LIKE :email AND " +
            "password LIKE :password LIMIT 1")
    UserLogin findByNameAndPassword(String email, String password);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(UserLogin... users);

    @Delete
    void delete(UserLogin user);
}

