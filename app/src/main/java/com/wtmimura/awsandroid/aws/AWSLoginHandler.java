package com.wtmimura.awsandroid.aws;

public interface AWSLoginHandler {

    public void onRegisterSuccess(boolean mustConfirmToComplete);

    public void onRegisterConfirmed();

    public void onSignInSuccess();

    public void onFailure(Exception exception);

}
