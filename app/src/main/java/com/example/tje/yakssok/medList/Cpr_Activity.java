package com.example.tje.yakssok.medList;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.example.tje.yakssok.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailView;

public class Cpr_Activity extends YouTubeBaseActivity {

    Button btn_back;

    YouTubePlayerView cpr_1;
    YouTubeThumbnailView cpr_11;
    YouTubePlayerView cpr_2;
    YouTubeThumbnailView cpr_22;
    YouTubePlayerView cpr_3;
    YouTubeThumbnailView cpr_33;

    View cpr_layout;
    FloatingActionButton cpr_up_btn;

    public void setRefs(){

        cpr_layout = (View)findViewById(R.id.cpr_layout);
        cpr_up_btn = (FloatingActionButton)findViewById(R.id.cpr_up_btn);

        btn_back = (Button)findViewById(R.id.btn_back);

        cpr_1 = (YouTubePlayerView) findViewById(R.id.cpr_1);
        cpr_2 = (YouTubePlayerView) findViewById(R.id.cpr_2);
        cpr_3 = (YouTubePlayerView) findViewById(R.id.cpr_3);

        cpr_11 = (YouTubeThumbnailView)findViewById(R.id.cpr_11);
        cpr_22 = (YouTubeThumbnailView)findViewById(R.id.cpr_22);
        cpr_33 = (YouTubeThumbnailView)findViewById(R.id.cpr_33);
    }

    public void setEvents() {

        cpr_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cpr_layout.post(new Runnable() {
                    @Override
                    public void run() {
                        cpr_layout.scrollTo(0,0);
                    }
                });
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        

}

    public void setcprvideo_1(){

        Glide.with(this).load("https://img.youtube.com/vi/WneG3XhBayc/mqdefault.jpg").into(cpr_11);

        final YouTubePlayer.OnInitializedListener cpr1 = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                // 비디오 아이디
                youTubePlayer.loadVideo("WneG3XhBayc");
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        };

        cpr_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cpr_11.getVisibility() == View.VISIBLE) {
                    cpr_1.setVisibility(View.VISIBLE);
                    cpr_11.setVisibility(View.GONE);
                    cpr_1.initialize("AIzaSyCSdDZ3qAZnEqG_fk19WSfobpdPOYjH50A", cpr1);
                }
            }
        });

    }

    public void setcprvideo_2(){

        YouTubePlayer.OnInitializedListener listener;

        Glide.with(this).load("https://img.youtube.com/vi/Vh706Wb8xeU/mqdefault.jpg").into(cpr_22);

        final YouTubePlayer.OnInitializedListener cpr2 = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                // 비디오 아이디
                youTubePlayer.loadVideo("Vh706Wb8xeU");
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        };

        cpr_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cpr_22.getVisibility() == View.VISIBLE) {
                    cpr_2.setVisibility(View.VISIBLE);
                    cpr_22.setVisibility(View.GONE);
                    cpr_2.initialize("AIzaSyCSdDZ3qAZnEqG_fk19WSfobpdPOYjH50A", cpr2);
                }
            }
        });

    }

    public void setcprvideo_3(){

        Glide.with(this).load("https://img.youtube.com/vi/Kefi2kur9A8/mqdefault.jpg").into(cpr_33);

        final YouTubePlayer.OnInitializedListener cpr3 = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                // 비디오 아이디
                youTubePlayer.loadVideo("Kefi2kur9A8");
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        };

        cpr_33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cpr_33.getVisibility() == View.VISIBLE) {
                    cpr_3.setVisibility(View.VISIBLE);
                    cpr_33.setVisibility(View.GONE);
                    cpr_3.initialize("AIzaSyCSdDZ3qAZnEqG_fk19WSfobpdPOYjH50A", cpr3);
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medlist_cpr);

        setRefs();
        setEvents();
        setcprvideo_1();
        setcprvideo_2();
        setcprvideo_3();
    }
}
