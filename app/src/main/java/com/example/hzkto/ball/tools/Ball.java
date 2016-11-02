package com.example.hzkto.ball.tools;

import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

import static com.example.hzkto.ball.Constants.onY;

/**
 * Created by hzkto on 10/30/2016.
 */

public class Ball {
    List<Circle3D> circles;


    public Ball(float radius, int polygons, float angle) {
        float angleFor360 = angle;
        circles = new ArrayList<>();
        float step = (float) (2 * Math.PI / polygons);
//        do {
////            circles.add(new Circle3D(radius, polygons, angle, onX));
//            circles.add(new Circle3D(radius, polygons, angle, onY));
////            circles.add(new Circle3D(radius, polygons, angle, Circle3D.onZ));
//            angle += step;
//        } while (angle < angleFor360 + 2 * Math.PI);

        circles.add(new Circle3D(radius, polygons, angle, onY));
    }

    public void rotate(float angle, int type) {

    }

    public Path getPathVisible() {
        Path path = new Path();
        for (Circle3D circle : circles) {
            path.moveTo(circle.visiblePoints.get(0).x, circle.visiblePoints.get(0).y);
            for (Point3D point : circle.visiblePoints) {
                path.lineTo(point.x, point.y);
            }
        }
        return path;
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
