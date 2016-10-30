package com.example.hzkto.ball.tools;


import java.util.ArrayList;
import java.util.List;

import static com.example.hzkto.ball.system.DrawThread.CENTER;
import static java.lang.Math.cos;
import static java.lang.Math.sin;


/**
 * Created by hzkto on 10/28/2016.
 */

public class Circle3D {
    public static final int onX = 0;
    public static final int onY = 1;
    public static final int onZ = 2;

    List<Point3D> points;

    public Circle3D(float radius, int polygons, float angle, int rotation) {
        points = new ArrayList<>();
        float step = (float) (2 * Math.PI / polygons);
        float alpha = 0;
        do {
            points.add(new Point3D((float) (CENTER.x + radius * Math.cos(alpha)), (float) (CENTER.y - radius * Math.sin(alpha)), 0));
            alpha += step;
        } while (alpha < 2 * Math.PI);
        points.add(new Point3D((float) (CENTER.x + radius * Math.cos(0)), (float) (CENTER.y - radius * Math.sin(0)), 0));

        switch (rotation) {
            case onX:
                for (Point3D point3D : points) {
                    point3D.y -= CENTER.y;
                    point3D.y = (float) (point3D.y * cos(angle) + point3D.z * sin(angle));
                    point3D.z = (float) (-point3D.y * sin(angle) + point3D.z * cos(angle));
                    point3D.y += CENTER.y;
                }
                break;
            case onY:
                for (Point3D point3D : points) {
                    point3D.x -= CENTER.x;
                    point3D.x = (float) (point3D.x * cos(angle) + point3D.z * sin(angle));
                    point3D.z = (float) (-point3D.x * sin(angle) + point3D.z * cos(angle));
                    point3D.x += CENTER.x;
                }
                break;
            case onZ:
                for (Point3D point3D : points) {
                    point3D.y -= CENTER.y;
                    point3D.x -= CENTER.x;
                    float x = point3D.x;
                    point3D.x = (float) (point3D.x * cos(angle) - point3D.y * sin(angle));
                    point3D.y = (float) (x * sin(angle) + point3D.y * cos(angle));
                    point3D.y += CENTER.y;
                    point3D.x += CENTER.x;
                }
                break;
        }
    }

    public List<Point3D> getPoints() {
        return points;
    }
}
