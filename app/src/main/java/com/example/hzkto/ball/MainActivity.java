package com.example.hzkto.ball;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.widget.LinearLayout;
import com.example.hzkto.ball.system.MySurfaceView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        MySurfaceView sfvTrack = new MySurfaceView(getApplicationContext());
//        sfvTrack.setZOrderOnTop(true);
        SurfaceHolder sfhTrackHolder = sfvTrack.getHolder();
        sfhTrackHolder.setFormat(PixelFormat.TRANSPARENT);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.surfaceView);
        linearLayout.addView(sfvTrack);
    }
}
