package com.example.hqsv4;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.hqsv4.Adapter.RecyclerViewAdapterWeb;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class Index extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    static Index activity;
    public static String keyId = "KeyId";
    public static String dataCheck = "data";
    public static String keyUsers = "Tester";
    private ImageButton member_image,khoc_image,hbong_image,tnguyen_image,dhoc_image,love_image,choak_image,truong_image,profile_image_content_index;
    TextView name_index;
    private FirebaseUser firebaseUser;
    CircleImageView profilePictureView;
    private ProgressBar pgsBar;

    RecyclerView recyclerView;
    private ArrayList<String> nameImage_hoatDong = new ArrayList<>();
    private ArrayList<String> urlImage_hoatDong = new ArrayList<>();
    private ArrayList<String> urlWeb_hoatDong = new ArrayList<>();

    private ArrayList<String> nameImage_duHoc = new ArrayList<>();
    private ArrayList<String> urlImage_duHoc = new ArrayList<>();
    private ArrayList<String> urlWeb_duHoc = new ArrayList<>();

    public static final String MY_URL_hoat_Dong = "http://www.hqsv.org/category/hoat-dong/";
    public static final String MY_URL_du_Hoc = "http://www.hqsv.org/category/du-hoc/";
    public String st ="Permalink to: ";


   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseUser = auth.getCurrentUser();
        myRef = database.getReference(keyUsers).child(firebaseUser.getUid());
        //        Kiểm tra dữ liệu đã đầy đủ chưa. Nếu chưa thì đưa vào INFORMATION
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                Log.d("Quang1122","DataSnapshot");
                if(dataSnapshot.getValue()==null)
                {

                    Log.d("Quang1122","DataSnapshot KeyId");
                    Intent intent = new Intent(Index.this, Infomation.class);
                    intent.putExtra("KeyId",firebaseUser.getUid());
                    startActivity(intent);
                    finish();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        setContentView(R.layout.content_index);

        //Tắt activiti từ activiti khác
        activity = this;
        AnhXa();

        ///Lấy dữ liệu đưa vào listview Horizatol
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        final RecyclerView recyclerView = findViewById(R.id.RecyclerView);
        final RecyclerView recyclerView1 = findViewById(R.id.RecyclerView1);

        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(linearLayoutManager1);

        final RecyclerViewAdapterWeb recyclerViewAdapter = new RecyclerViewAdapterWeb(this,nameImage_hoatDong,urlImage_hoatDong,urlWeb_hoatDong);
        final RecyclerViewAdapterWeb recyclerViewAdapter1 = new RecyclerViewAdapterWeb(this,nameImage_duHoc,urlImage_duHoc,urlWeb_duHoc);

        //Lấy dữ liệu tư trang Sự kiên
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, MY_URL_hoat_Dong, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Document document = Jsoup.parse(response);
                Log.d("Quang1122",document.title());

                if(document!= null)
                {
                    Elements elements = document.getAllElements();
//                       Log.d("Quang1122",elements.className());
//                       Log.d("Quang1122",elements.getElementsByClass(elements.className()).first().toString());
                    Elements elements1 = document.getElementsByClass("row gutter k-equal-height");
                    //Log.d("Quang1122",elements1.toString());
                    for(Element element : elements1)
                    {
                        Elements elements2 = element.getElementsByTag("h1");
//                            Elements elements3 = element.getElementsByTag("a");
                        Elements elements3 = element.getElementsByClass("news-featured-image");

//
                        for (Element element3 : elements3)
                        {
                            nameImage_hoatDong.add(chuoi(element3.getElementsByTag("a").attr("title"),st));
                            urlWeb_hoatDong.add(element3.getElementsByTag("a").attr("href"));
//                            Log.d("Quang1122",chuoi(element3.getElementsByTag("a").attr("title"),st));
//                                Log.d("Quang1122",element3.getElementsByTag("img").attr("src").toString());
                            urlImage_hoatDong.add(element3.getElementsByTag("img").attr("src").toString());

                        }


                        recyclerView.setAdapter(recyclerViewAdapter);
                    }


                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(stringRequest);

//Lấy dữ liệu tư trang Du học
        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, MY_URL_du_Hoc, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Document document = Jsoup.parse(response);
                Log.d("Quang1122",document.title());

                if(document!= null)
                {
                    Elements elements = document.getAllElements();
//                       Log.d("Quang1122",elements.className());
//                       Log.d("Quang1122",elements.getElementsByClass(elements.className()).first().toString());
                    Elements elements1 = document.getElementsByClass("row gutter k-equal-height");
                    //Log.d("Quang1122",elements1.toString());
                    for(Element element : elements1)
                    {
                        Elements elements2 = element.getElementsByTag("h1");
//                            Elements elements3 = element.getElementsByTag("a");
                        Elements elements3 = element.getElementsByClass("news-featured-image");

//
                        for (Element element3 : elements3)
                        {
                            nameImage_duHoc.add(chuoi(element3.getElementsByTag("a").attr("title"),st));
//                            Log.d("Quang1122",chuoi(element3.getElementsByTag("a").attr("title"),st));
                            urlWeb_duHoc.add(element3.getElementsByTag("a").attr("href"));
//                            Log.d("Quang1122",chuoi(element3.getElementsByTag("a").attr("title"),st));
//                                Log.d("Quang1122",element3.getElementsByTag("img").attr("src").toString());
                            urlImage_duHoc.add(element3.getElementsByTag("img").attr("src").toString());

                        }


                          recyclerView1.setAdapter(recyclerViewAdapter1);
                    }


                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue1.add(stringRequest1);

        ///Hơi bị phiền phức


        //set Ảnh dại diện
//        profilePictureView.setProfileId(Infomation.profile.getId());

        Glide.with(this).load(Infomation.profile.getProfilePictureUri(200,200)).into(profilePictureView);

        Log.d("Quang1122",Infomation.profile.getProfilePictureUri(200,200).toString());
        //Set ten trong insdex
        name_index.setText(Infomation.profile.getFirstName());
        Log.d("Quang1122",Infomation.profile.getId());




        //Chỉnh sửa thông tin
        profilePictureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Quang1122","Hello");
                Intent intent = new Intent(Index.this, InfoApp.class);
                intent.putExtra(keyId,firebaseUser.getUid());
                intent.putExtra(dataCheck,"true");
                startActivity(intent);
            }
        });
        //Xem thành viên
        member_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(Index.this,Member_info.class);
                    startActivity(intent);
            }
        });


    }
//    Tắt Activity này từ activity khác
    public static Index getInstance(){
        return   activity;
    }


    private void AutoNextSong() {

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                handler.postDelayed(this, 100);
                Log.d("Quang1122","Run");


            }
        });
    }







    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Quang1122","Pause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Quang1122","Detroy");
    }
    private void AnhXa() {
        member_image = (ImageButton)findViewById(R.id.member_image);
        khoc_image = (ImageButton)findViewById(R.id.khoc_image);
        hbong_image = (ImageButton)findViewById(R.id.hbong_image);
        tnguyen_image = (ImageButton)findViewById(R.id.tnguyen_image);
        truong_image = (ImageButton)findViewById(R.id.truong_image);
        dhoc_image = (ImageButton)findViewById(R.id.dhoc_image);
        love_image = (ImageButton)findViewById(R.id.love_image);
        choak_image = (ImageButton)findViewById(R.id.choak_image);
        profilePictureView = (CircleImageView) findViewById(R.id.avata_conten_index);
        name_index = (TextView)findViewById(R.id.name_index);

     //   profile_image_content_index = (ImageButton) findViewById(R.id.profile_image_conent_index);

    }
    private String chuoi(String url,String chuoi)
    {
        int a = chuoi.length();
        String xuli = url.substring(a-1,url.length());
        return xuli;
    }

}



