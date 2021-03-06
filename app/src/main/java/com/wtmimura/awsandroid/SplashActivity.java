package com.wtmimura.awsandroid;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.StartupAuthResult;
import com.amazonaws.mobile.auth.core.StartupAuthResultHandler;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AWSMobileClient.getInstance().initialize(SplashActivity.this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        IdentityManager identityManager = IdentityManager.getDefaultIdentityManager();
                        identityManager.resumeSession( SplashActivity.this, new StartupAuthResultHandler() {
                            @Override
                            public void onComplete(StartupAuthResult authResults) {
                                if (authResults.isUserSignedIn()) {
                                    startActivity(new Intent(SplashActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                } else {
                                    startActivity(new Intent(SplashActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                }
                            }
                        }, 3000);
                    }
                }, 3000);
            }
        }).execute();
    }
}
