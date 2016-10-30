package com.example.hzkto.ball.system;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;

import com.example.hzkto.ball.tools.Ball;
import com.example.hzkto.ball.tools.Point3D;

/**
 * Created by hzkto on 10/26/2016.
 */

public class DrawThread extends Thread {
    public static final int POLYGONS = 60;
    public static final Point3D CENTER = new Point3D(MySurfaceView.screenWidth / 2, MySurfaceView.screenHeight / 2, 0);
    private boolean runFlag = false;
    private SurfaceHolder surfaceHolder;
    private long prevTime;
    Paint paint;
    Ball ball;

    public DrawThread(SurfaceHolder surfaceHolder, Resources resources) {
        this.surfaceHolder = surfaceHolder;
        prevTime = System.currentTimeMillis();
        int radius = 500;

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);

        ball = new Ball(500, 25);
    }

    public void setRunning(boolean run) {
        runFlag = run;
    }

    @Override
    public void run() {
        Canvas canvas;
        while (runFlag) {
            long now = System.currentTimeMillis();
            long elapsedTime = now - prevTime;
            if (elapsedTime > 30000000) {
                prevTime = now;
            }
            canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    if (canvas != null) {
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        canvas.drawPath(ball.getPath(), paint);
                    }
                }
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }


}
