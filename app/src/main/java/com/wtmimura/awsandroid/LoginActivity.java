package com.wtmimura.awsandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wtmimura.awsandroid.aws.AWSLoginHandler;
import com.wtmimura.awsandroid.aws.AWSLoginModel;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, AWSLoginHandler {

    AWSLoginModel awsLoginModel;

    // UI variables
    // login
    private EditText userLoginEditText, passwordLoginEditText;
    // register
    private EditText userNameRegisterEditText, userEmailRegisterEditText, passwordRegisterEditText;
    // confirm registration
    private EditText confirmationCodeEditText;
    // reset / forgot
    private EditText resetCodeEditText, newPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        awsLoginModel = new AWSLoginModel(this, this);

        // assigning UI variables
        userLoginEditText = findViewById(R.id.loginUser);
        passwordLoginEditText = findViewById(R.id.loginPassword);
        userNameRegisterEditText = findViewById(R.id.registerUsername);
        userEmailRegisterEditText = findViewById(R.id.registerEmail);
        passwordRegisterEditText = findViewById(R.id.registerPassword);
        confirmationCodeEditText = findViewById(R.id.confirmationCode);
        resetCodeEditText = findViewById(R.id.resetCode);
        newPasswordEditText = findViewById(R.id.newPassword);

        // setting listeners
        findViewById(R.id.registerButton).setOnClickListener(this);
        findViewById(R.id.loginButton).setOnClickListener(this);
        findViewById(R.id.confirmButton).setOnClickListener(this);
        findViewById(R.id.resendConfirmationButton).setOnClickListener(this);
        findViewById(R.id.resetButton).setOnClickListener(this);
        findViewById(R.id.forgotButton).setOnClickListener(this);
        findViewById(R.id.showLoginButton).setOnClickListener(this);
        findViewById(R.id.showRegisterButton).setOnClickListener(this);
    }

    @Override
    public void onRegisterSuccess(boolean mustConfirmToComplete) {
        if (mustConfirmToComplete) {
            Toast.makeText(LoginActivity.this, "Almost done! Confirm code to complete registration", Toast.LENGTH_LONG).show();
            showConfirm(true);
        } else {
            Toast.makeText(LoginActivity.this, "Registered! Login Now!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRegisterConfirmed() {
        Toast.makeText(LoginActivity.this, "Registered! Login Now!", Toast.LENGTH_LONG).show();
        showLoginAction(true);
    }

    @Override
    public void onSignInSuccess() {
        LoginActivity.this.startActivity(new Intent(LoginActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    @Override
    public void onResendConfirmationCodeSuccess(String destination) {
        Toast.makeText(LoginActivity.this, "Confirmation code sent! Destination:" + destination, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestResetUserPasswordSuccess(String destination) {
        Toast.makeText(LoginActivity.this, "Reset code sent! Destination:" + destination, Toast.LENGTH_LONG).show();
        showForgotAction(true);
    }

    @Override
    public void onResetUserPasswordSuccess() {
        Toast.makeText(LoginActivity.this, "Password reset! Login Now!", Toast.LENGTH_LONG).show();
        showLoginAction(true);
    }

    @Override
    public void onFailure(int process, Exception exception, int cause, String message) {
        Toast.makeText(LoginActivity.this,  message, Toast.LENGTH_LONG).show();
        if (cause != AWSLoginModel.CAUSE_MUST_CONFIRM_FIRST) {
            exception.printStackTrace();
        } else {
            showConfirm(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerButton:
                registerAction();
                break;
            case R.id.confirmButton:
                confirmAction();
                break;
            case R.id.resendConfirmationButton:
                resendConfirmationAction();
                break;
            case R.id.loginButton:
                loginAction();
                break;
            case R.id.resetButton:
                resetAction();
                break;
            case R.id.forgotButton:
                forgotPasswordAction();
                break;
            case R.id.showLoginButton:
                showLoginAction(true);
                break;
            case R.id.showRegisterButton:
                showRegisterAction(true);
                break;
        }
    }

    private void registerAction() {
        // do register and handles on interface
        awsLoginModel.registerUser(userNameRegisterEditText.getText().toString(), userEmailRegisterEditText.getText().toString(), passwordRegisterEditText.getText().toString());
    }

    private void confirmAction() {
        // do confirmation and handles on interface
        awsLoginModel.confirmRegistration(confirmationCodeEditText.getText().toString());
    }

    private void resendConfirmationAction() {
        // do resend confirmation code and handles on interface
        awsLoginModel.resendConfirmationCode();
    }

    private void loginAction() {
        // do sign in and handles on interface
        awsLoginModel.signInUser(userLoginEditText.getText().toString(), passwordLoginEditText.getText().toString());
    }

    private void forgotPasswordAction() {
        if (userLoginEditText.getText().toString().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Username required.", Toast.LENGTH_LONG).show();
        } else {
            awsLoginModel.requestResetUserPassword(userLoginEditText.getText().toString());
        }
    }

    private void resetAction() {
        // request reset password and handles on interface
        awsLoginModel.resetUserPasswordWithCode(resetCodeEditText.getText().toString(), newPasswordEditText.getText().toString());
    }

    private void showLoginAction(boolean show) {
        if (show) {
            showRegisterAction(false);
            showConfirm(false);
            showForgotAction(false);
            findViewById(R.id.loginContainer).setVisibility(View.VISIBLE);
            findViewById(R.id.showRegisterButton).setVisibility(View.VISIBLE);
            findViewById(R.id.showLoginButton).setVisibility(View.GONE);
        } else {
            findViewById(R.id.loginContainer).setVisibility(View.GONE);
            userLoginEditText.setText("");
            passwordLoginEditText.setText("");
        }
    }

    private void showRegisterAction(boolean show) {
        if (show) {
            showLoginAction(false);
            showConfirm(false);
            showForgotAction(false);
            findViewById(R.id.registerContainer).setVisibility(View.VISIBLE);
            findViewById(R.id.showRegisterButton).setVisibility(View.GONE);
            findViewById(R.id.showLoginButton).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.registerContainer).setVisibility(View.GONE);
            userNameRegisterEditText.setText("");
            userEmailRegisterEditText.setText("");
            passwordRegisterEditText.setText("");
        }
    }

    private void showConfirm(boolean show) {
        if (show) {
            showLoginAction(false);
            showRegisterAction(false);
            showForgotAction(false);
            findViewById(R.id.confirmContainer).setVisibility(View.VISIBLE);
            findViewById(R.id.showRegisterButton).setVisibility(View.GONE);
            findViewById(R.id.showLoginButton).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.confirmContainer).setVisibility(View.GONE);
            confirmationCodeEditText.setText("");
        }
    }

    private void showForgotAction(boolean show) {
        if (show) {
            showLoginAction(false);
            showRegisterAction(false);
            showConfirm(false);
            findViewById(R.id.forgotContainer).setVisibility(View.VISIBLE);
            findViewById(R.id.showRegisterButton).setVisibility(View.GONE);
            findViewById(R.id.showLoginButton).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.forgotContainer).setVisibility(View.GONE);
            resetCodeEditText.setText("");
            newPasswordEditText.setText("");
        }
    }

}
