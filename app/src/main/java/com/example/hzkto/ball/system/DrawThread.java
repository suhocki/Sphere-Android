package com.example.hzkto.ball.system;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;
import com.example.hzkto.ball.tools.Point3D;
import com.example.hzkto.ball.tools.Sphere3D;

import static com.example.hzkto.ball.Constants.*;

/**
 * Created by hzkto on 10/26/2016.
 */

public class DrawThread extends Thread {
    public static Point3D CENTER;
    private boolean runFlag = false;
    private SurfaceHolder surfaceHolder;
    private long prevTime;
    Paint paint;
    Sphere3D ball;
    float angle;
    Point3D lightPoint;
    float radius;

    public DrawThread(SurfaceHolder surfaceHolder, float x, float y) {
        CENTER = new Point3D(x, y, 0);
        this.surfaceHolder = surfaceHolder;
        prevTime = System.currentTimeMillis();

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(LINE_WIDTH);
        angle = 0;
        lightPoint = CENTER;
        lightPoint.z = 4000;
        radius = 500;
//        ball = new Sphere3D(RADIUS, 0, TYPE_X, 0, TYPE_Y);
    }

    public void setRunning(boolean run) {
        runFlag = run;
    }

    @Override
    public void run() {
        Canvas canvas;
        Sphere3D sphere = new Sphere3D(lightPoint, radius, angle, angle);
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

//                            radius -= 1;
//                        lightPoint.z += 10;
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
//                        angle += (float) Math.toRadians(1);
                        sphere.Update(lightPoint, radius);
                        sphere.draw(canvas);
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
