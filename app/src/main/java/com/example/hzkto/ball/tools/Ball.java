package com.example.hzkto.ball.tools;

import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzkto on 10/30/2016.
 */

public class Ball {
    List<Circle3D> circles;

    public Ball(float radius, int polygons) {
        circles = new ArrayList<>();
        float step = (float) (2 * Math.PI / polygons);
        float angle = 0;
        do {
            circles.add(new Circle3D(radius, polygons, angle, Circle3D.onX));
            angle += step;
        } while (angle < 2 * Math.PI);
    }

    public Path getPath() {
        Path path = new Path();
        for (Circle3D circle : circles) {
            path.moveTo(circle.points.get(0).x, circle.points.get(0).y);
            for (Point3D point : circle.points) {
                path.lineTo(point.x, point.y);
            }
        }
        return path;
    }
}
