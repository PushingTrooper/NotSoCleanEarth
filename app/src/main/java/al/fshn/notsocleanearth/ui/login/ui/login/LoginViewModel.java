package al.fshn.notsocleanearth.ui.login.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.util.Patterns;

import al.fshn.notsocleanearth.R;
import al.fshn.notsocleanearth.data.AppDatabase;
import al.fshn.notsocleanearth.data.AppRepository;
import al.fshn.notsocleanearth.data.model.UserLogin;
import al.fshn.notsocleanearth.ui.login.data.LoginRepository;
import al.fshn.notsocleanearth.ui.login.data.Result;
import al.fshn.notsocleanearth.ui.login.data.model.LoggedInUser;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private MutableLiveData<LoginResult> registerResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    private AppRepository appRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        AsyncTask.execute(() -> {
            Result<UserLogin> result = appRepository.login(username, password);

            if (result instanceof Result.Success) {
                UserLogin data = ((Result.Success<UserLogin>) result).getData();
                loginResult.postValue(new LoginResult(new LoggedInUserView(data.email)));
            } else {
                loginResult.postValue(new LoginResult(R.string.login_failed));
            }
        });
    }

    public void register(String username, String password, String confirmPassword) {
        // can be launched in a separate asynchronous job
        AsyncTask.execute(() -> {
            try {
                Result<UserLogin> result = appRepository.registerNewUser(username, password);

                if (result instanceof Result.Success) {
                    UserLogin data = ((Result.Success<UserLogin>) result).getData();
                    loginResult.postValue(new LoginResult(new LoggedInUserView(data.email)));
                } else {
                    loginResult.postValue(new LoginResult(R.string.login_failed));
                }
            } catch (SQLiteConstraintException e) {
                loginResult.postValue(new LoginResult(R.string.login_failed));
            }

        });
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    public void setAppRepository(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    public MutableLiveData<LoginResult> getRegisterResult() {
        return registerResult;
    }
}