package com.example.hzkto.ball.system;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;

import com.example.hzkto.ball.tools.Point3D;
import com.example.hzkto.ball.tools.Sphere3D;

import static com.example.hzkto.ball.Constants.LINE_WIDTH;
import static com.example.hzkto.ball.Constants.RADIUS;
import static com.example.hzkto.ball.Constants.onX;
import static com.example.hzkto.ball.Constants.onY;

/**
 * Created by hzkto on 10/26/2016.
 */

public class DrawThread extends Thread {
    public static final Point3D CENTER = new Point3D(MySurfaceView.screenWidth / 2, MySurfaceView.screenHeight / 2, 0);
    private boolean runFlag = false;
    private SurfaceHolder surfaceHolder;
    private long prevTime;
    Paint paint;
    Sphere3D ball;
    float angle;

    public DrawThread(SurfaceHolder surfaceHolder, Resources resources) {
        this.surfaceHolder = surfaceHolder;
        prevTime = System.currentTimeMillis();

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(LINE_WIDTH);
        angle = (float) Math.toRadians(0);
        ball = new Sphere3D(RADIUS, 0, onX, 0, onY);
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
//                        angle += (float) Math.toRadians(0.1);
                        angle = (float) Math.toRadians(0);
//                        ball.rotate(angle, onX);
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
//                        canvas.drawPath((new Ball(RADIUS, POLYGONS, angle)).getPath(), paint);
//                        canvas.drawPath((new Ball3D(RADIUS, POLYGONS, angle, onX, angle, onX)).getPath(), paint);
                        canvas.drawPath((new Sphere3D(RADIUS, angle, onY, angle, onY)).getPathVisible(), paint);
//                        canvas.drawPath(ball.getPath(), paint);
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
