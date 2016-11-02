package com.example.hzkto.ball.tools;


import java.util.ArrayList;
import java.util.List;

import static com.example.hzkto.ball.Constants.onX;
import static com.example.hzkto.ball.Constants.onY;
import static com.example.hzkto.ball.Constants.onZ;
import static com.example.hzkto.ball.system.DrawThread.CENTER;
import static java.lang.Math.cos;
import static java.lang.Math.sin;


/**
 * Created by hzkto on 10/28/2016.
 */

public class Circle3D {


    List<Point3D> points;
    List<Point3D> visiblePoints;

    public Circle3D(float radius, int polygons, float angle, int rotation) {
        points = new ArrayList<>();
        visiblePoints = new ArrayList<>();
        float step = (float) (2 * Math.PI / polygons);
        float alpha = 0;
        do {
            points.add(new Point3D((float) (CENTER.x + radius * Math.cos(alpha)), (float) (CENTER.y - radius * Math.sin(alpha)), 0));
            alpha += step;
        } while (alpha < 2 * Math.PI);
        points.add(new Point3D((float) (CENTER.x + radius * Math.cos(0)), (float) (CENTER.y - radius * Math.sin(0)), 0));
        rotate(angle, rotation);

    }

    public void rotate(float angle, int type) {
        switch (type) {
            case onX:
                for (Point3D point : points) {
                    point.y -= CENTER.y;
                    point.y = (float) (point.z * sin(angle) + point.y * cos(angle));
                    point.z = (float) (point.z * cos(angle)+ -point.y * sin(angle) );
                    point.y += CENTER.y;
                }
                break;
            case onY:
                for (Point3D point : points) {
                    point.x -= CENTER.x;
                    point.x = (float) (point.x * cos(angle) + point.z * sin(angle));
                    point.z = (float) (-point.x * sin(angle) + point.z * cos(angle));
                    point.x += CENTER.x;
                }
                break;
            case onZ:
                for (Point3D point : points) {
                    point.y -= CENTER.y;
                    point.x -= CENTER.x;
                    float x = point.x;
                    point.x = (float) (point.x * cos(angle) - point.y * sin(angle));
                    point.y = (float) (x * sin(angle) + point.y * cos(angle));
                    point.y += CENTER.y;
                    point.x += CENTER.x;
                }
                break;
        }
        for (Point3D point : points) {
            if (point.z >= 0) {
                visiblePoints.add(point);
            }
        }
    }

    public List<Point3D> getPoints() {
        return points;
    }
}
