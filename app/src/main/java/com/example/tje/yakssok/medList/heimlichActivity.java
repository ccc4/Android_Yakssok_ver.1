package com.example.tje.yakssok.medList;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.example.tje.yakssok.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailView;

public class heimlichActivity extends YouTubeBaseActivity {

    Button btn_back;

    YouTubePlayer.OnInitializedListener listener;

    YouTubePlayerView heimlich_1;
    YouTubeThumbnailView heimlich_11;
    YouTubePlayerView heimlich_2;
    YouTubeThumbnailView heimlich_22;

    public void setRefs(){

        heimlich_11 = (YouTubeThumbnailView) findViewById(R.id.heimlich_11);
        heimlich_1 = (YouTubePlayerView) findViewById(R.id.heimlich_1);
        heimlich_22 = (YouTubeThumbnailView) findViewById(R.id.heimlich_22);
        heimlich_2 = (YouTubePlayerView) findViewById(R.id.heimlich_2);

        btn_back = (Button)findViewById(R.id.btn_back);

    }

    public void setEvents() {

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void setheimlichvideo_1(){

            Glide.with(this).load("https://img.youtube.com/vi/mv3WLJMpGfI/mqdefault.jpg").into(heimlich_11);

            listener = new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    // 비디오 아이디
                    youTubePlayer.loadVideo("mv3WLJMpGfI");
                }
                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                }
            };

        heimlich_11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (heimlich_11.getVisibility() == View.VISIBLE) {
                        heimlich_1.setVisibility(View.VISIBLE);
                        heimlich_11.setVisibility(View.GONE);
                        heimlich_1.initialize("AIzaSyCSdDZ3qAZnEqG_fk19WSfobpdPOYjH50A", listener);
                        return;
                    }
                }
            });

        }


    public void setheimlichvideo_2(){

        Glide.with(this).load("https://img.youtube.com/vi/ZsHe2j0YKSI/mqdefault.jpg").into(heimlich_22);

        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                // 비디오 아이디
                youTubePlayer.loadVideo("ZsHe2j0YKSI");
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        };

        heimlich_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (heimlich_22.getVisibility() == View.VISIBLE) {
                    heimlich_2.setVisibility(View.VISIBLE);
                    heimlich_22.setVisibility(View.GONE);
                    heimlich_2.initialize("AIzaSyCSdDZ3qAZnEqG_fk19WSfobpdPOYjH50A", listener);
                    return;
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heimlich);

        setRefs();
        setEvents();
        setheimlichvideo_1();
        setheimlichvideo_2();
    }
}
