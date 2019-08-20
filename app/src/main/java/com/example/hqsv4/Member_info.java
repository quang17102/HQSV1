package com.example.hqsv4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.hqsv4.Adapter.CustomAdapter;
import com.example.hqsv4.OOP.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Member_info extends AppCompatActivity {

    private ArrayList<Users> mUser;
    private ListView listView;
    CustomAdapter customAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info);
        listView = (ListView)findViewById(R.id.list_view_member);
        mUser = new ArrayList<Users>();
        customAdapter  = new CustomAdapter(Member_info.this,R.layout.layout_icon,mUser);
        listView.setAdapter(customAdapter);
        readUser();




    }
    private void readUser(){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Index.keyUsers);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUser.clear();
                for (DataSnapshot data: dataSnapshot.getChildren())
                {

                    Users users = data.getValue(Users.class);
                    Log.d("Quang1122",users.getName() );
                    assert users !=null;
                    assert  firebaseUser !=null;

                    if(!users.getId().equals(firebaseUser.getUid()))
                    {
                        mUser.add(users);
                        Log.d("Quang1122",users.getName());


                    }

                    customAdapter.notifyDataSetChanged();
                }
                Log.d("Quang1122",mUser.size()+"");
                customAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
