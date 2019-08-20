package com.example.hqsv4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebViewClient;

import com.example.hqsv4.Adapter.RecyclerViewAdapterWeb;

public class WebView extends AppCompatActivity {

    android.webkit.WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        String url = getIntent().getStringExtra(RecyclerViewAdapterWeb.urlIntent);
        webView = findViewById(R.id.webView);
        if(url!= null)
        {
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(url);
        }
    }
}
