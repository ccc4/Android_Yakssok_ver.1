package com.example.tje.yakssok.member;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.example.tje.yakssok.R;

public class DaumWebViewActivity extends AppCompatActivity {

    private static final String LOG_TAG= "Yakssok";
//    public static final String SERVER_ADDRESS = "http://172.30.1.59:8080/Yakssok";
    public static final String SERVER_ADDRESS = "http://192.168.10.132:8080/Yakssok";

    WebView daum_webView;
    TextView daum_result;
    Handler handler;
    Button btn_address_ok, btn_address_cancle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daum_web_view);

        daum_result = findViewById(R.id.daum_result);
        btn_address_cancle = findViewById(R.id.btn_address_cancel);
        btn_address_ok = findViewById(R.id.btn_address_ok);

        initWebView();
        handler = new Handler();
        setEvents();
    }

    public void setEvents(){
        final Intent intentB = new Intent(getApplicationContext(), JoinActivity.class);
        final Intent intentC = new Intent(getApplicationContext(), ProfileModifyActivity.class);
        if(daum_result.getText().equals("주소륵 선택해주세요.")){
            btn_address_ok.setEnabled(false);
        }
            btn_address_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intentB.putExtra("address", daum_result.getText().toString());
                    intentC.putExtra("address", daum_result.getText().toString());
                    Log.d(LOG_TAG, "웹뷰액티비티로그 주소값:" + daum_result.getText().toString());
                    setResult(RESULT_OK, intentB);
                    setResult(RESULT_OK,intentC);
                    finish();
                }
            });


        btn_address_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED,intentB);
                setResult(RESULT_CANCELED,intentC);
                finish();
            }
        });
    }

    public void initWebView(){
        //웹뷰 설정
        daum_webView = findViewById(R.id.daum_webView);
        //자바스크립트허용
        daum_webView.getSettings().setJavaScriptEnabled(true);
        //javascript의 window.open허용
        daum_webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        //javascript이벤트에 대응할 함수를 정의 한 클래스 붙이기
        daum_webView.addJavascriptInterface(new AndroidBridge(),"YakssokJoin");
        //web client를 chrome 으로 설정
        daum_webView.setWebChromeClient(new WebChromeClient());
        //webview url load.jsp 파일 주소
        daum_webView.loadUrl(SERVER_ADDRESS + "/mobileDaumApi.jsp");
    }


    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    daum_result.setText(String.format("(%s) %s %s", arg1, arg2, arg3));
                    btn_address_ok.setEnabled(true);
                    // WebView를 초기화 하지않으면 재사용할 수 없음
                    initWebView();
                }
            });
        }
    }
}

