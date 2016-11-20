package com.example.hzkto.ball.system;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.text.TextPaint;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import com.example.hzkto.ball.tools.Point3D;
import com.example.hzkto.ball.tools.Sphere3D;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by hzkto on 10/26/2016.
 */

public class DrawThread extends Thread {
    public static Point3D center;
    private boolean runFlag = false;
    private SurfaceHolder surfaceHolder;
    private long prevTime;
    //    private Paint paint;
//    Sphere3D ball;
    private float angle;
    private Point3D lightPoint;
    private float radius;

    private SensorManager sensorManager;
    private Sensor sensorAccel;
    private Sensor sensorMagnet;

    public DrawThread(Context context, MySurfaceView surfaceView) {
        center = new Point3D(surfaceView.screenWidth / 2, surfaceView.screenHeight / 2, 0);
        lightPoint = new Point3D(surfaceView.screenWidth / 2, surfaceView.screenHeight / 2, 0);
        this.surfaceHolder = surfaceView.getHolder();
        prevTime = System.currentTimeMillis();

        angle = 0;
        lightPoint.z = 4000;
        radius = 500;

        initSensors(context);
    }


    float[] valuesAccel = new float[3];
    float[] valuesMagnet = new float[3];
    float[] valuesResult = new float[3];
    float[] valuesResult2 = new float[3];

    int rotation;

    private void initSensors(Context context) {
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagnet = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sensorManager.registerListener(listener, sensorAccel, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(listener, sensorMagnet, SensorManager.SENSOR_DELAY_UI);

        WindowManager windowManager = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
        Display display = windowManager.getDefaultDisplay();
        rotation = display.getRotation();
    }

    SensorEventListener listener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    for (int i = 0; i < 3; i++) {
                        valuesAccel[i] = event.values[i];
                    }
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    for (int i = 0; i < 3; i++) {
                        valuesMagnet[i] = event.values[i];
                    }
                    break;
            }
        }
    };

    float[] r = new float[9];
    float[] inR = new float[9];
    float[] outR = new float[9];

    void getActualDeviceOrientation() {
        SensorManager.getRotationMatrix(inR, null, valuesAccel, valuesMagnet);
        int x_axis = SensorManager.AXIS_X;
        int y_axis = SensorManager.AXIS_Y;
        switch (rotation) {
            case (Surface.ROTATION_0):
                break;
            case (Surface.ROTATION_90):
                x_axis = SensorManager.AXIS_Y;
                y_axis = SensorManager.AXIS_MINUS_X;
                break;
            case (Surface.ROTATION_180):
                y_axis = SensorManager.AXIS_MINUS_Y;
                break;
            case (Surface.ROTATION_270):
                x_axis = SensorManager.AXIS_MINUS_Y;
                y_axis = SensorManager.AXIS_X;
                break;
            default:
                break;
        }
        SensorManager.remapCoordinateSystem(inR, x_axis, y_axis, outR);
        SensorManager.getOrientation(outR, valuesResult2);
        valuesResult2[0] = (float) Math.toDegrees(valuesResult2[0]);
        valuesResult2[1] = (float) Math.toDegrees(valuesResult2[1]);
        valuesResult2[2] = (float) Math.toDegrees(valuesResult2[2]);
        return;
    }

    void getDeviceOrientation() {
        SensorManager.getRotationMatrix(r, null, valuesAccel, valuesMagnet);
        SensorManager.getOrientation(r, valuesResult);

        valuesResult[0] = (float) Math.toDegrees(valuesResult[0]);
        valuesResult[1] = (float) Math.toDegrees(valuesResult[1]);
        valuesResult[2] = (float) Math.toDegrees(valuesResult[2]);
        return;
    }

    public void setRunning(boolean run) {
        runFlag = run;
    }

    float angleqwe;

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

                        float[] valuesResultTemp = valuesResult2.clone();
                        getDeviceOrientation();
                        getActualDeviceOrientation();

                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        TextPaint paint = new TextPaint();
                        paint.setTextSize(50);
                        canvas.drawText(String.valueOf(elapsedTime), 50, 50, paint);
                        canvas.drawText(String.valueOf(format(valuesResult)), 50, 200, paint);
                        canvas.drawText(String.valueOf(format(valuesResult2)), 50, 300, paint);


                        valuesResultTemp[0] -= valuesResult2[0];
                        valuesResultTemp[1] -= valuesResult2[1];
                        valuesResultTemp[2] -= valuesResult2[2];
//                        if (Math.abs(valuesResultTemp[0]) < 20) center.x += valuesResultTemp[0] * 5;
//                        if (Math.abs(valuesResultTemp[1]) < 20) center.y += valuesResultTemp[1] * 5;
//                        if (Math.abs(valuesResultTemp[2]) < 20) center.z += valuesResultTemp[2] * 5;
                        lightPoint.z = 100000;
                        lightPoint.x = -200000;
//                        center.x = 400;
                        sphere.Update(lightPoint, 400, 1, 1);
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

    private String format(float values[]) {
        return String.format("%1$.1f\t\t%2$.1f\t\t%3$.1f", values[0], values[1], values[2]);
    }

}
