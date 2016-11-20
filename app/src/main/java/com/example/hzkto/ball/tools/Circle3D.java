package com.example.hzkto.ball.tools;


import java.util.ArrayList;
import java.util.List;

import static com.example.hzkto.ball.Constants.TYPE_X;
import static com.example.hzkto.ball.Constants.TYPE_Y;
import static com.example.hzkto.ball.Constants.TYPE_Z;
import static com.example.hzkto.ball.system.DrawThread.center;
import static java.lang.Math.cos;
import static java.lang.Math.sin;


/**
 * Created by hzkto on 10/28/2016.
 */

public class Circle3D {
    List<Point3D> points;
    List<Point3D> visiblePoints;

    public Circle3D(float radius, int polygons, float angleAlpha, float angleBeta) {
        points = new ArrayList<>();
        visiblePoints = new ArrayList<>();
        float step = (float) (2 * Math.PI / polygons);
        float alpha = 0;
        do {
            points.add(new Point3D((float) (center.x + radius * Math.cos(alpha)), (float) (center.y - radius * Math.sin(alpha)), 0));
            alpha += step;
        } while (alpha < 2 * Math.PI);
        points.add(new Point3D((float) (center.x + radius * Math.cos(0)), (float) (center.y - radius * Math.sin(0)), 0));
        rotate(points, angleAlpha, TYPE_X);
        rotate(points, angleBeta, TYPE_Y);

    }

    Circle3D() {
        this.points = new ArrayList<>();
    }

    void addPoint(Point3D point) {
        this.points.add(point);
    }

    private void rotate(List<Point3D> points, float angle, int type) {
        switch (type) {
            case TYPE_X:
                for (Point3D point : points) {
                    point.y -= center.y;
                    float y = point.y;
                    point.y = (float) (point.z * sin(angle) + point.y * cos(angle));
                    point.z = (float) (-y * sin(angle) + point.z * cos(angle));
                    point.y += center.y;
                }
                break;
            case TYPE_Y:
                for (Point3D point : points) {
                    point.x -= center.x;
                    float x = point.x;
                    point.x = (float) (point.x * cos(angle) + point.z * sin(angle));
                    point.z = (float) (-x * sin(angle) + point.z * cos(angle));
                    point.x += center.x;
                }
                break;
            case TYPE_Z:
                for (Point3D point : points) {
                    point.y -= center.y;
                    point.x -= center.x;
                    float x = point.x;
                    point.x = (float) (point.x * cos(angle) - point.y * sin(angle));
                    point.y = (float) (x * sin(angle) + point.y * cos(angle));
                    point.y += center.y;
                    point.x += center.x;
                }
                break;
        }

    }

    public List<Point3D> getPoints() {
        return points;
    }
}
