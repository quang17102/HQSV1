package com.example.hqsv4;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class LoginFacebook extends AppCompatActivity {

    private FirebaseAuth mAuth ;
    String TAG = "Quang1122";
    private CallbackManager callbackManager ;

    Button loginfb_btn;
    private ProgressBar pgsBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_fb);
//Khỏi tạo firebase
        callbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();

        loginfb_btn = (Button)findViewById(R.id.loginfb_btn);



        //        Button đăng nhâp bằng fabebook
        loginfb_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(LoginFacebook.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        setContentView(R.layout.activity_loading_index);
                        pgsBar = (ProgressBar) findViewById(R.id.pBar);
                        pgsBar.setVisibility(View.VISIBLE);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });
            }
        });

    }
    //Kiểm tra xem người này đẵ đăng nhập chưa
    @Override
    protected void onStart() {
        super.onStart();
        //Kiểm tra xem người này đẵ đăng nhập chưa
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        //  Log.d("Quang1122",accessToken.getUserId());
        if(isLoggedIn)
        {
            Log.d("Quang1122","Chưa logout");
            UpdateUI();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
//Hàm xử lí đă đăng nhập thành công
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            final FirebaseUser user = mAuth.getCurrentUser();
                            pgsBar.setVisibility(View.GONE);
                            UpdateUI();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//
                        }

                    }
                });

    }


    //Chuyên từ Loginfb sang Index
    private void UpdateUI()
    {
        Intent intent = new Intent(LoginFacebook.this, Index.class);
        startActivity(intent);
        finish();
    }
}
