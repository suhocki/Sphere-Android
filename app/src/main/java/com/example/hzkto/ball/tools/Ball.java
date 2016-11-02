package com.example.hzkto.ball.tools;

import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

import static com.example.hzkto.ball.Constants.POLYGONS;
import static com.example.hzkto.ball.Constants.onX;
import static com.example.hzkto.ball.Constants.onY;

/**
 * Created by hzkto on 10/30/2016.
 */

public class Ball {
    List<Circle3D> circlesOnX;
    List<Circle3D> circlesOnY;
    float radius;

    public Ball(float radius, float angle) {
        circlesOnX = new ArrayList<>();
        circlesOnY = new ArrayList<>();
        this.radius = radius;
        float angleFor360 = angle;
        float step = (float) (2 * Math.PI / POLYGONS);

        do {
            circlesOnX.add(new Circle3D(radius, POLYGONS, angle, onX));
            circlesOnY.add(new Circle3D(radius, POLYGONS, angle, onY));
            angle += step;
        } while (angle < angleFor360 + 2 * Math.PI);
    }

    public void rotate(float angle) {
        circlesOnX.clear();
        circlesOnY.clear();
        float angleFor360 = angle;
        float step = (float) (2 * Math.PI / POLYGONS);
        do {
            circlesOnX.add(new Circle3D(radius, POLYGONS, angle, onX));
            circlesOnY.add(new Circle3D(radius, POLYGONS, angle, onY));
            angle += step;
        } while (angle < angleFor360 + 2 * Math.PI);
    }

    public Path getPath() {
        Path path = new Path();
        for (Circle3D circle : circlesOnX) {
            path.moveTo(circle.getPoints().get(0).x, circle.getPoints().get(0).y);
            for (Point3D point : circle.points) {
                path.lineTo(point.x, point.y);
            }
        }
        for (Circle3D circle : circlesOnY) {
            path.moveTo(circle.getPoints().get(0).x, circle.getPoints().get(0).y);
            for (Point3D point : circle.points) {
                path.lineTo(point.x, point.y);
            }
        }
        return path;
    }
}
