package com.example.tje.yakssok;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

import com.example.tje.yakssok.model.Member;

public class ChatActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Yakssok";
    String SERVER_ADDRESS;

    Button btn_chat_go_back;
    WebView wv_chat;

    Handler handler;

    private void setRefs() {
        SERVER_ADDRESS = getString(R.string.SERVER_ADDRESS_STR);

        btn_chat_go_back = findViewById(R.id.btn_chat_go_back);
        wv_chat = findViewById(R.id.wv_chat);

        handler = new Handler();
    }

    private void setWebView() {
        wv_chat.getSettings().setJavaScriptEnabled(true);
        wv_chat.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv_chat.setWebChromeClient(new WebChromeClient());
        wv_chat.loadUrl(SERVER_ADDRESS + "/mobile/API_Tawk");
    }

    private void setEvents() {
        btn_chat_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        setRefs();
        setWebView();
        setEvents();
    }
}
