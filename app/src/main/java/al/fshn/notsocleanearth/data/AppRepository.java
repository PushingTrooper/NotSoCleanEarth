package al.fshn.notsocleanearth.data;

import al.fshn.notsocleanearth.data.model.UserLogin;

public class AppRepository {
    private AppDatabase database;
    private UserLoginDao userLoginDao;

    public AppRepository(AppDatabase database) {
        this.database = database;
        this.userLoginDao = this.database.userDao();
    }

    public void registerNewUser(String email, String password) {
        userLoginDao.insertAll(new UserLogin(email, password));
    }
}
