package com.example.tje.yakssok.drugstore;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

import com.example.tje.yakssok.MainActivity;
import com.example.tje.yakssok.R;
import com.example.tje.yakssok.model.Member;

public class Drugstore_NearbyActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Yakssok";
    private String SERVER_ADDRESS;
    private Handler handler;

    private double longitude;
    private double latitude;

    Member loginMember;

    Button btn_d_go_main;
    Button btn_d_refresh_coords;
    WebView wv_d_nearby;


    private void setRefs() {
        btn_d_go_main = findViewById(R.id.btn_d_go_main);
        btn_d_refresh_coords = findViewById(R.id.btn_d_refresh_coords);
        wv_d_nearby = findViewById(R.id.wv_d_nearby);

        handler = new Handler();
    }

    private void setWebView() {
        getLocation();
        Log.d(LOG_TAG, "latitude => " + latitude);
        Log.d(LOG_TAG, "longitude => " + longitude);

        // Javascript 허용
        wv_d_nearby.getSettings().setJavaScriptEnabled(true);
        // Javascript window.open 허용
        wv_d_nearby.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        // web client 를 chrome 으로 설정
        wv_d_nearby.setWebChromeClient(new WebChromeClient());
        // url load
        wv_d_nearby.loadUrl(SERVER_ADDRESS + "/mobile/API_Daum_Map_Drugstore/" + latitude + "/" + longitude);
    }

    private void getLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        longitude = location.getLongitude();
        latitude = location.getLatitude();

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private void setEvents() {
        btn_d_go_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                if (loginMember != null) {
                    intent.putExtra("loginMember", loginMember);
                }
                startActivity(intent);
            }
        });

        btn_d_refresh_coords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drugstore__nearby);

        SERVER_ADDRESS = getString(R.string.SERVER_ADDRESS_STR);

        Intent intent = getIntent();
        loginMember = (Member) intent.getSerializableExtra("loginMember");

        setRefs();
        setWebView();
        setEvents();
    }
}
