package com.example.hzkto.ball.tools;

import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

import static com.example.hzkto.ball.Constants.POLYGONS;

/**
 * Created by hzkto on 10/30/2016.
 */

public class Sphere3D {
    List<Circle3D> circlesXY;
    List<Circle3D> circlesXZ;
    List<Polygon3D> polygons;
    Circle3D mainCircle;

    public Sphere3D(float radius, float angleAlpha, int typeAlpha, float angleBeta, int typeBeta) {
        circlesXY = new ArrayList<>();
        circlesXZ = new ArrayList<>();
        polygons = new ArrayList<>();
//        circlesXY_visible = new ArrayList<>();
//        circlesXZ_visible = new ArrayList<>();

        float angleFor360 = angleAlpha;
        float step = (float) (2 * Math.PI / POLYGONS);
        do {
            circlesXY.add(new Circle3D(radius, POLYGONS, angleAlpha, typeAlpha, angleBeta, typeBeta));
            angleAlpha += step;
        } while (angleAlpha < angleFor360 + 2 * Math.PI);

        circlesXY.add(new Circle3D(radius, POLYGONS, angleAlpha, typeAlpha, angleBeta, typeBeta));
        mainCircle = circlesXY.get(0);


        for (int i = 0; i < mainCircle.points.size(); i++) {
            Circle3D circleBuff = new Circle3D();
            for (Circle3D circle : circlesXY) {
                circleBuff.addPoint(circle.points.get(i));
            }
            circlesXZ.add(circleBuff);
        }

//        fillPolygons();
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


    public Path getPathVisible() {
        Path path = new Path();
        for (Circle3D circle : circlesXY) {
            path.moveTo(circle.points.get(0).x, circle.points.get(0).y);
            boolean continueDraw = true;
            for (Point3D point : circle.points) {
                if (point.z >= 0) {
                    if (continueDraw) {
                        path.lineTo(point.x, point.y);
                    } else {
                        path.moveTo(point.x, point.y);
                        continueDraw = true;
                    }
                } else {
                    continueDraw = false;
                }
            }
        }
        for (Circle3D circle : circlesXZ) {
            path.moveTo(circle.points.get(0).x, circle.points.get(0).y);
            boolean continueDraw = true;
            for (Point3D point : circle.points) {
                if (point.z >= 0) {
                    if (continueDraw) {
                        path.lineTo(point.x, point.y);
                    } else {
                        path.moveTo(point.x, point.y);
                        continueDraw = true;
                    }
                } else {
                    continueDraw = false;
                }
            }
        }

        return path;
    }

    private void fillPolygons() {
        for (Circle3D circle : circlesXY) {
            for (Point3D point : circle.points) {
                Polygon3D polygon = new Polygon3D();
            }
        }
    }
//    public Path getPathVisible() {
//        deleteInvisibleShape();
//        Path path = new Path();
//        for (Circle3D circle : circlesXY_visible) {
//            path.moveTo(circle.points.get(0).x, circle.points.get(0).y);
//            for (Point3D point : circle.points) {
//                path.lineTo(point.x, point.y);
//            }
//        }
//        for (Circle3D circle : circlesXZ_visible) {
//            path.moveTo(circle.points.get(0).x, circle.points.get(0).y);
//            for (Point3D point : circle.points) {
//                path.lineTo(point.x, point.y);
//            }
//        }
//
//        return path;
//    }

//    public void deleteInvisibleShape() {
//        for (Circle3D circle : circlesXY) {
//            Circle3D circleBuff = new Circle3D();
//            for (Point3D point : circle.points) {
//                if (point.z >= 0) {
//                    circleBuff.addPoint(point);
//                } else circleBuff.addPoint();
//            }
//            circlesXY_visible.add(circleBuff);
//        }
//        for (Circle3D circle : circlesXZ) {
//            Circle3D circleBuff = new Circle3D();
//            for (Point3D point : circle.points) {
//                if (point.z >= 0) {
//                    circleBuff.addPoint(point);
//                }
//            }
//            if (!circleBuff.points.isEmpty()) {
//                circlesXZ_visible.add(circleBuff);
//            }
//        }
//    }
}
