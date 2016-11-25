package com.example.hzkto.ball.system;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by hzkto on 10/26/2016.
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private DrawThread drawThread;
    private Bundle bundle;

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode()) {
            getHolder().addCallback(this);
            setZOrderOnTop(true);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (bundle != null) {
            drawThread = new DrawThread(getContext(), this, bundle);
        } else {
            drawThread = new DrawThread(getContext(), this);
        }
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException ignored) {
            }
        }
    }

}
