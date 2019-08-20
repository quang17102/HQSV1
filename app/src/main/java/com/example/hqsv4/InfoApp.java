package com.example.hqsv4;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfoApp extends AppCompatActivity {

    CircleImageView profilePictureView;
    Button logOut,showInfor;
    TextView name_infor_app;
    String key,data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infor_app);
        showInfor = (Button)findViewById(R.id.showinfo);
        logOut = (Button)findViewById(R.id.btn_Logout);
        profilePictureView = (CircleImageView) findViewById(R.id.avata_infor_app);
        name_infor_app = (TextView)findViewById(R.id.name_infor_app);
        name_infor_app.setText(Infomation.profile.getName());

        Glide.with(this).load(Infomation.profile.getProfilePictureUri(500,500)).into(profilePictureView);

        key = getIntent().getStringExtra(Index.keyId);
        data = getIntent().getStringExtra(Index.dataCheck);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();

            }
        });

        showInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InforActivity();
            }
        });

    }


    private void Logout()
    {
        Index.getInstance().finish();
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        UpdateUI();
    }
    private void UpdateUI()
    {
        Intent intent = new Intent(InfoApp.this,LoginFacebook.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Quang1122","Detroy App");
    }
    private void InforActivity()
    {
        Intent intent = new Intent(InfoApp.this, Infomation.class);
        intent.putExtra(Index.keyId,key);
        intent.putExtra(Index.dataCheck,"data");
        startActivity(intent);
    }
}
