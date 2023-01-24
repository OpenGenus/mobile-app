package com.example.opengenus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.gigamole.navigationtabstrip.NavigationTabStrip;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private Button homeButton, feedButton, QnAButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomWebViewClient client = new CustomWebViewClient(this);

        homeButton = findViewById(R.id.home);
        feedButton = findViewById(R.id.feed);
        QnAButton = findViewById(R.id.qna);
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(client);
        webView.getSettings().setJavaScriptEnabled(true);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDatabaseEnabled(true);
        String databasePath = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        settings.setDatabasePath(databasePath);
        webView.setWebChromeClient(new WebChromeClient(){
            public void onExceededDatabaseQuota(String url, String databaseIdentifier, long currentQuota, long estimatedSize, long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
                quotaUpdater.updateQuota(5 * 1024 * 1024);
            }
            });

        settings.setDomStorageEnabled(true);

        webView.loadUrl("https://iq.opengenus.org/path/");
        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("https://iq.opengenus.org/");
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("https://iq.opengenus.org/path/");
            }
        });

        QnAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("https://iq.opengenus.org/tag/questions/");
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK && this.webView.canGoBack()){
            this.webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

class CustomWebViewClient extends WebViewClient {
    private Activity activity;
    public CustomWebViewClient(Activity activity){
        this.activity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url){
        return false;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request){
        return false;
    }
}