package al.fshn.notsocleanearth.data;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import al.fshn.notsocleanearth.data.model.CurrentlyLoggedInUser;
import al.fshn.notsocleanearth.data.model.UserLogin;
import al.fshn.notsocleanearth.ui.login.data.Result;

public class AppRepository {
    private AppDatabase database;
    private UserLoginDao userLoginDao;
    private CurrentlyLoggedInUserDao currentlyLoggedInUserDao;

    public AppRepository(AppDatabase database) {
        this.database = database;
        this.userLoginDao = this.database.userDao();
        this.currentlyLoggedInUserDao = this.database.currentlyLoggedInUserDao();
    }

    public Result<UserLogin> registerNewUser(String email, String password) {
        String uuid = UUID.randomUUID().toString().replaceAll("\\D", "");
        UserLogin user = new UserLogin(Long.valueOf(uuid.substring(0, 10)), email, password);
        List<Long> ids = userLoginDao.insertAll(user);

        if (ids.isEmpty()) return new Result.Error(new IOException("Error registering"));
        else {
            currentlyLoggedInUserDao.deleteAll();
            currentlyLoggedInUserDao.insertCurrentUser(new CurrentlyLoggedInUser(user.uid, user.email, user.password));
            return new Result.Success<>(user);
        }
    }

    public Result<UserLogin> login(String email, String password) {
        UserLogin user = userLoginDao.findByNameAndPassword(email, password);

        if(user != null) {
            currentlyLoggedInUserDao.deleteAll();
            currentlyLoggedInUserDao.insertCurrentUser(new CurrentlyLoggedInUser(user.uid, user.email, user.password));
            return new Result.Success<>(user);
        } else {
            return new Result.Error(new IOException("User doesn't exist"));
        }
    }
}
