package com.example.hzkto.ball.system;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;

import com.example.hzkto.ball.tools.Ball;
import com.example.hzkto.ball.tools.Point3D;

import static com.example.hzkto.ball.Constants.LINE_WIDTH;
import static com.example.hzkto.ball.Constants.POLYGONS;
import static com.example.hzkto.ball.Constants.RADIUS;

/**
 * Created by hzkto on 10/26/2016.
 */

public class DrawThread extends Thread {
    public static final Point3D CENTER = new Point3D(MySurfaceView.screenWidth / 2, MySurfaceView.screenHeight / 2, 0);
    private boolean runFlag = false;
    private SurfaceHolder surfaceHolder;
    private long prevTime;
    Paint paint;
    Ball ball;
    float angle;

    public DrawThread(SurfaceHolder surfaceHolder, Resources resources) {
        this.surfaceHolder = surfaceHolder;
        prevTime = System.currentTimeMillis();

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(LINE_WIDTH);
        angle = (float) Math.toRadians(0);
//        ball = new Ball(MainActivity.RADIUS, MainActivity.POLYGONS);
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
            if (elapsedTime > 30) {
                prevTime = now;
            }
            canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    if (canvas != null) {
                        angle += (float) Math.toRadians(5);
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
//                        canvas.drawPath((new Ball(RADIUS, POLYGONS, angle)).getPath(), paint);
//                        canvas.drawPath((new Ball(RADIUS, POLYGONS, angle)).getPath(), paint);
                        canvas.drawPath((new Ball(RADIUS, POLYGONS, angle)).getPathVisible(), paint);
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
