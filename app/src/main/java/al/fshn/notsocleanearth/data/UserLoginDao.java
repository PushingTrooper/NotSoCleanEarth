package al.fshn.notsocleanearth.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
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
    UserLogin findByName(String email, String password);

    @Insert
    void insertAll(UserLogin... users);

    @Delete
    void delete(UserLogin user);
}

