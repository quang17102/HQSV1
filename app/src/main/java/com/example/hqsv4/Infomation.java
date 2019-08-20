package com.example.hqsv4;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class Infomation extends AppCompatActivity {

    EditText Name_edit,birthDay_edit,numberPhone_edit,email_edit,address_edit,school_edit,branch_edit,HQSV_edit,dayParticipation_edit,whyParticipation_edit;
    ImageButton conFirm_infor;
    CircleImageView profilePictureView;
    RadioButton male_radio,female_radio;

    String Tag = "Quang1122";
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    String value;
    static Profile profile = Profile.getCurrentProfile();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);
//        keyidFb được gửi từ Index

        final String keyidFb = getIntent().getStringExtra("KeyId");
        database = FirebaseDatabase.getInstance();
//        Kiểm tra xem đã gửi idFb qua chưa
        if(keyidFb!= null)
        {
            Log.d("Quang1122",keyidFb);
            myRef = database.getReference(Index.keyUsers).child(keyidFb);
            //        Đọc dữ liệu từ Firebase gán vào editText
            myRef.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue()!= null) {
                        HashMap<String, String> hashMap1 = new HashMap<>();
                        hashMap1.clear();
                        //Lấy Key và Value đưa vào hashmap để đưa vào textview
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            String value = data.getValue().toString();
                            String key = data.getKey();
                            Log.d("Quang1122",value);
                            hashMap1.put(key, value);
                        }
                        Name_edit.setText(hashMap1.get("name"));
                        birthDay_edit.setText(hashMap1.get("birthday"));
                        numberPhone_edit.setText(hashMap1.get("phoneNumber"));
                        email_edit.setText(hashMap1.get("email"));
                        address_edit.setText(hashMap1.get("address"));
                        school_edit.setText(hashMap1.get("school"));
                        branch_edit.setText(hashMap1.get("branch"));
                        HQSV_edit.setText(hashMap1.get("hQSV"));
                        dayParticipation_edit.setText(hashMap1.get("dayParticipation"));
                        whyParticipation_edit.setText(hashMap1.get("whyParticipation"));

                        if(hashMap1.get("gender").equals("Nam"))
                        {
                            male_radio.setChecked(true);
                        }else
                        {
                            female_radio.setChecked(true);
                        }

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });

        }

//        Ánh xạ
        UI();
        //Đặt hình đại diện

        profilePictureView = (CircleImageView) findViewById(R.id.avatar_inofmation);
        Glide.with(this).load(Infomation.profile.getProfilePictureUri(500,500)).into(profilePictureView);
            Log.d("Quang1122",Infomation.profile.getProfilePictureUri(500,500).toString());


        conFirm_infor = (ImageButton) findViewById(R.id.conFirm_infor);

//        Kiểm tra va cập nhật dữ liệu
        conFirm_infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(     Name_edit.getText().toString().equals("")||
                        birthDay_edit.getText().toString().equals("")||
                        numberPhone_edit.getText().toString().equals("")||
                        email_edit.getText().toString().equals("")||
                        address_edit.getText().toString().equals("")||
                        school_edit.getText().toString().equals("")||
                        branch_edit.getText().toString().equals("")||
                        HQSV_edit.getText().toString().equals("")||
                        dayParticipation_edit.getText().toString().equals("")||
                        whyParticipation_edit.getText().toString().equals("")||
                        !male_radio.isChecked()&& !female_radio.isChecked())
                {
                    Toast.makeText(Infomation.this,"Bạn phải nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
                }else
                    {
                                HashMap<String,String> hashMap = new HashMap<>();
                                hashMap.put("id",keyidFb);
                                hashMap.put("name",Name_edit.getText().toString());
                                hashMap.put("phoneNumber",numberPhone_edit.getText().toString());
                                hashMap.put("birthday", birthDay_edit.getText().toString());
                                hashMap.put("email", email_edit.getText().toString());
                                hashMap.put("address",address_edit.getText().toString());
                                hashMap.put("school" ,school_edit.getText().toString());
                                hashMap.put("branch",branch_edit.getText().toString());
                                hashMap.put("hQSV",HQSV_edit.getText().toString());
                                hashMap.put("dayParticipation",dayParticipation_edit.getText().toString());
                                hashMap.put("whyParticipation",whyParticipation_edit.getText().toString());
                                hashMap.put("idFb",Infomation.profile.getId());
                                if(male_radio.isChecked())
                                {
                                    hashMap.put("gender","Nam");
                                }else
                                    {
                                        hashMap.put("gender","Nữ");
                                    }

                                //Đưa hashmap vào realtime
                                myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                    {
                                        if(getIntent().getStringExtra(Index.dataCheck)!=null)
                                        {
                                            finish();
                                            onBackPressed();

                                        }else
                                            {
                                                Intent intent = new Intent(Infomation.this,Index.class);
                                                startActivity(intent);
                                                finish();
                                            }
//                                        //Trở lui ra màn hình chính

                                    }
                                                                         }
                                 });
                    }
            }
        });

    }

//Ánh xạ
    private void UI()
    {
                Name_edit = (EditText)findViewById(R.id.Name_edit);
                birthDay_edit= (EditText)findViewById(R.id.birthDay_edit);
                numberPhone_edit = (EditText)findViewById(R.id.numberPhone_edit);
                email_edit = (EditText)findViewById(R.id.email_edit);
                address_edit= (EditText)findViewById(R.id.address_edit);
                school_edit = (EditText)findViewById(R.id.school_edit);
                branch_edit= (EditText)findViewById(R.id.branch_edit);
                HQSV_edit= (EditText)findViewById(R.id.HQSV_edit);
                dayParticipation_edit= (EditText)findViewById(R.id.dayParticipation_edit);
                whyParticipation_edit= (EditText)findViewById(R.id.whyParticipation_edit);
                male_radio = (RadioButton)findViewById(R.id.male_radio);
                female_radio = (RadioButton)findViewById(R.id.female_radio);
    }


}
