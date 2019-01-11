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

public class Love_Activity extends YouTubeBaseActivity {

    Button btn_back;

    YouTubePlayer.OnInitializedListener listener;

    YouTubePlayerView love_1;
    YouTubeThumbnailView love_11;
    YouTubePlayerView love_2;
    YouTubeThumbnailView love_22;
    YouTubePlayerView love_3;
    YouTubeThumbnailView love_33;
    YouTubePlayerView love_4;
    YouTubeThumbnailView love_44;
    YouTubePlayerView love_5;
    YouTubeThumbnailView love_55;

    View love_layout;
    FloatingActionButton love_up_btn;

    public void setRefs(){

        love_layout = (View)findViewById(R.id.love_layout);
        love_up_btn = (FloatingActionButton)findViewById(R.id.love_up_btn);

        btn_back = (Button)findViewById(R.id.btn_back);

        love_1 = (YouTubePlayerView) findViewById(R.id.love_1);
        love_2 = (YouTubePlayerView) findViewById(R.id.love_2);
        love_3 = (YouTubePlayerView) findViewById(R.id.love_3);
        love_4 = (YouTubePlayerView) findViewById(R.id.love_4);
        love_5 = (YouTubePlayerView) findViewById(R.id.love_5);

        love_11 = (YouTubeThumbnailView)findViewById(R.id.love_11);
        love_22 = (YouTubeThumbnailView)findViewById(R.id.love_22);
        love_33 = (YouTubeThumbnailView)findViewById(R.id.love_33);
        love_44 = (YouTubeThumbnailView)findViewById(R.id.love_44);
        love_55 = (YouTubeThumbnailView)findViewById(R.id.love_55);

    }

    public void setEvents() {

        love_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                love_layout.post(new Runnable() {
                    @Override
                    public void run() {
                        love_layout.scrollTo(0,0);
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

    public void setlovevideo_1(){

        Glide.with(this).load("https://img.youtube.com/vi/G7weqwa3d4o/mqdefault.jpg").into(love_11);

        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                // 비디오 아이디
                youTubePlayer.loadVideo("G7weqwa3d4o");
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        };

        love_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (love_11.getVisibility() == View.VISIBLE) {
                    love_1.setVisibility(View.VISIBLE);
                    love_11.setVisibility(View.GONE);
                    love_1.initialize("AIzaSyCSdDZ3qAZnEqG_fk19WSfobpdPOYjH50A", listener);
                    return;
                }
            }
        });

    }

    public void setlovevideo_2(){

        Glide.with(this).load("https://img.youtube.com/vi/dklm3weX0s4/mqdefault.jpg").into(love_22);

        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                // 비디오 아이디
                youTubePlayer.loadVideo("dklm3weX0s4");
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        };

        love_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (love_22.getVisibility() == View.VISIBLE) {
                    love_2.setVisibility(View.VISIBLE);
                    love_22.setVisibility(View.GONE);
                    love_2.initialize("AIzaSyCSdDZ3qAZnEqG_fk19WSfobpdPOYjH50A", listener);
                    return;
                }
            }
        });

    }

    public void setlovevideo_3(){

        Glide.with(this).load("https://img.youtube.com/vi/pkdwixKmZu4/mqdefault.jpg").into(love_33);

        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                // 비디오 아이디
                youTubePlayer.loadVideo("pkdwixKmZu4");
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        };

        love_33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (love_33.getVisibility() == View.VISIBLE) {
                    love_3.setVisibility(View.VISIBLE);
                    love_33.setVisibility(View.GONE);
                    love_3.initialize("AIzaSyCSdDZ3qAZnEqG_fk19WSfobpdPOYjH50A", listener);
                    return;
                }
            }
        });

    }

    public void setlovevideo_4(){

        Glide.with(this).load("https://img.youtube.com/vi/G9YegpvgBe0/mqdefault.jpg").into(love_44);

        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                // 비디오 아이디
                youTubePlayer.loadVideo("G9YegpvgBe0");
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        };

        love_44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (love_44.getVisibility() == View.VISIBLE) {
                    love_4.setVisibility(View.VISIBLE);
                    love_44.setVisibility(View.GONE);
                    love_4.initialize("AIzaSyCSdDZ3qAZnEqG_fk19WSfobpdPOYjH50A", listener);
                    return;
                }
            }
        });

    }

    public void setlovevideo_5(){

        Glide.with(this).load("https://img.youtube.com/vi/N0u-amYZTqk/mqdefault.jpg").into(love_55);

        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                // 비디오 아이디
                youTubePlayer.loadVideo("N0u-amYZTqk");
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        };

        love_55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (love_55.getVisibility() == View.VISIBLE) {
                    love_5.setVisibility(View.VISIBLE);
                    love_55.setVisibility(View.GONE);
                    love_5.initialize("AIzaSyCSdDZ3qAZnEqG_fk19WSfobpdPOYjH50A", listener);
                    return;
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medlist_love);

        setRefs();
        setEvents();
        setlovevideo_1();
        setlovevideo_2();
        setlovevideo_3();
        setlovevideo_4();
        setlovevideo_5();
    }
}
