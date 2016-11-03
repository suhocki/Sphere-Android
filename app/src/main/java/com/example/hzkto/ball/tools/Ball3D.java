package com.example.hzkto.ball.tools;

import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzkto on 10/30/2016.
 */

public class Ball3D {
    List<Circle3D> circlesXY;
    List<Circle3D> circlesXZ;
    Circle3D mainCircle;

    public Ball3D(float radius, int polygons, float angleAlpha, int typeAlpha, float angleBeta, int typeBeta) {
        float angleFor360 = angleAlpha;
        circlesXY = new ArrayList<>();
        float step = (float) (2 * Math.PI / polygons);
        do {
            circlesXY.add(new Circle3D(radius, polygons, angleAlpha, typeAlpha, angleBeta, typeBeta));
            angleAlpha += step;
        } while (angleAlpha < angleFor360 + 2 * Math.PI);

        circlesXY.add(new Circle3D(radius, polygons, angleAlpha, typeAlpha, angleBeta, typeBeta));
        mainCircle = circlesXY.get(0);


        circlesXZ = new ArrayList<>();

        for (int i = 0; i < mainCircle.points.size(); i++) {
            Circle3D circleBuff = new Circle3D();
            for (Circle3D circle : circlesXY) {
                circleBuff.addPoint(circle.points.get(i));
            }
            circlesXZ.add(circleBuff);
        }
    }

//    public void rotate(float angle, int type) {
//        switch (type) {
//            case onX:
//                for (Circle3D circle : circlesXY) {
//                    Circle3D.staticRotate(circle.getPoints(), angle, onX);
//                }
//                for (Circle3D circle : circlesXZ) {
//                    Circle3D.staticRotate(circle.getPoints(), angle, onX);
//                }
//                break;
//            case onY:
//                for (Circle3D circle : circlesXY) {
//                    Circle3D.staticRotate(circle.getPoints(), angle, onY);
//                }
//                for (Circle3D circle : circlesXZ) {
//                    Circle3D.staticRotate(circle.getPoints(), angle, onY);
//                }
//                break;
//            case onZ:
//                for (Circle3D circle : circlesXY) {
//                    Circle3D.staticRotate(circle.getPoints(), angle, onZ);
//                }
//                for (Circle3D circle : circlesXZ) {
//                    Circle3D.staticRotate(circle.getPoints(), angle, onZ);
//                }
//                break;
//        }
//    }

    public Path getPathVisible() {
        Path path = new Path();
        for (Circle3D circle : circlesXY) {
            path.moveTo(circle.visiblePoints.get(0).x, circle.visiblePoints.get(0).y);
            for (Point3D point : circle.visiblePoints) {
                path.lineTo(point.x, point.y);
            }
        }
        return path;
    }

    public Path getPath() {
        Path path = new Path();
        for (Circle3D circle : circlesXY) {
            path.moveTo(circle.points.get(0).x, circle.points.get(0).y);
            for (Point3D point : circle.points) {
                path.lineTo(point.x, point.y);
            }
        }
        for (Circle3D circle : circlesXZ) {
            path.moveTo(circle.points.get(0).x, circle.points.get(0).y);
            for (Point3D point : circle.points) {
                path.lineTo(point.x, point.y);
            }
        }

        return path;
    }
}
