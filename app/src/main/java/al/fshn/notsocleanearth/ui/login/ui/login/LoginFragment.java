package al.fshn.notsocleanearth.ui.login.ui.login;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import al.fshn.notsocleanearth.MainActivity;
import al.fshn.notsocleanearth.MyApplication;
import al.fshn.notsocleanearth.R;
import al.fshn.notsocleanearth.data.AppContainer;
import al.fshn.notsocleanearth.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity activity = (MainActivity) requireActivity();
        activity.hideBottomNavigation();
        /*if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayShowHomeEnabled(false);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }*/
        AppContainer appContainer = ((MyApplication) activity.getApplication()).appContainer;

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        loginViewModel.setAppRepository(appContainer.userRepository);

        final TextInputLayout usernameTextLayout = view.findViewById(R.id.usernameTextField);
        final TextInputEditText usernameEditText = view.findViewById(R.id.username);
        final Button loginButton = view.findViewById(R.id.login);
        final Button registerButton = view.findViewById(R.id.register);
        final TextInputLayout passwordTextLayout = view.findViewById(R.id.passwordTextField);
        final TextInputEditText passwordEditText = view.findViewById(R.id.password);
        final TextInputLayout confirmPasswordTextLayout = view.findViewById(R.id.confirmPasswordTextField);
        final TextInputEditText confirmPasswordEditText = view.findViewById(R.id.confirmPassword);
        final ProgressBar loadingProgressBar = view.findViewById(R.id.loading);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmPasswordTextLayout.getVisibility() == View.GONE) {
                    confirmPasswordTextLayout.setVisibility(View.VISIBLE);
                    loginButton.setEnabled(true);
                } else {
                    loginViewModel.register(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString(), confirmPasswordEditText.getText().toString());
                }
            }
        });

        loginViewModel.getLoginFormState().observe(getViewLifecycleOwner(), new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                if (confirmPasswordTextLayout.getVisibility() == View.GONE) {
                    loginButton.setEnabled(loginFormState.isDataValid());
                }
                if (loginFormState.getUsernameError() != null) {
                    usernameTextLayout.setErrorEnabled(true);
                    usernameTextLayout.setError(getString(loginFormState.getUsernameError()));
                } else {
                    usernameTextLayout.setErrorEnabled(false);
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordTextLayout.setErrorEnabled(true);
                    passwordTextLayout.setError(getString(loginFormState.getPasswordError()));
                } else {
                    passwordTextLayout.setErrorEnabled(false);
                }
            }
        });

        loginViewModel.getLoginResult().observe(getViewLifecycleOwner(), new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
            }
        });

        loginViewModel.getRegisterResult().observe(getViewLifecycleOwner(), new Observer<LoginResult>() {
            @Override
            public void onChanged(LoginResult registerResult) {
                if (registerResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (registerResult.getError() != null) {
                    showLoginFailed(registerResult.getError());
                }
                if (registerResult.getSuccess() != null) {
                    updateUiWithUser(registerResult.getSuccess());
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmPasswordTextLayout.getVisibility() == View.VISIBLE) {
                    confirmPasswordTextLayout.setVisibility(View.GONE);
                    if (loginViewModel.getLoginFormState().getValue() != null) {
                        loginButton.setEnabled(loginViewModel.getLoginFormState().getValue().isDataValid());
                    } else {
                        loginButton.setEnabled(false);
                    }
                } else {
                    loadingProgressBar.setVisibility(View.VISIBLE);
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_activity_main);
        navHostFragment.getNavController().navigate(R.id.action_loginFragment_to_navigation_home);
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(
                    getContext().getApplicationContext(),
                    errorString,
                    Toast.LENGTH_LONG).show();
        }
    }
}