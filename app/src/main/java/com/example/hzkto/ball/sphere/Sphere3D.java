package com.example.hzkto.ball.sphere;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.hzkto.ball.tools.MathFunctions;

import java.util.ArrayList;
import java.util.List;

import static com.example.hzkto.ball.system.DrawThread.center;

/**
 * Created by hzkto on 10/30/2016.
 */

public class Sphere3D {
//    private double angleX;
//    private double angleY;
//    private double angleZ;
    List<Circle3D> circles;
    List<Polygon3D> polygons3D;
    Point3D camPoint;
    double radius;
    int polygons;

    public Sphere3D(double radius, double angleX, double angleY, double angleZ, Point3D lightPoint, int polygons) {
        circles = new ArrayList<>();
        polygons3D = new ArrayList<>();
//        this.angleX = angleX;
//        this.angleY = angleY;
//        this.angleZ = angleZ;
        this.radius = radius;
        if (this.radius < 10) {
            this.radius = 10;
        }
        this.radius = radius;
        this.polygons = polygons;
        update(radius, angleX, angleY, angleZ, lightPoint);
    }

    public void update(double radius, double angleX, double angleY, double angleZ, Point3D lightPoint) {
//        this.angleX = angleX;
//        this.angleY = angleY;
//        this.angleZ = angleZ;
        this.radius = radius;
        if (this.radius < 10) {
            this.radius = 10;
        }
        circles.clear();
        this.polygons3D.clear();
        this.camPoint = lightPoint;
        double angleFor360 = angleX;
        double step = 2 * Math.PI / polygons;
        do {
            circles.add(new Circle3D(center, radius, polygons, angleX, angleY, angleZ));
            angleX += step;
        } while (angleX < angleFor360 + 1 * Math.PI);
        circles.add(new Circle3D(center, radius, polygons, angleX, angleY, angleZ));
        fillPolygons();
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        canvas.drawRGB(225, 225, 255);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(2);
        double distBetwCamAndCenter = MathFunctions.getDistBetwTwoPoints3D(center, camPoint);
        double maxLigthDistance = Math.sqrt(distBetwCamAndCenter * distBetwCamAndCenter -
                radius * radius);
        for (Polygon3D polygon : polygons3D) {
            polygon.getPolygonCenter();
            if (polygon.getPolygonCenter().z >= 0) {
                final double lightCoefficient = polygon.getLightCoefficient(camPoint, maxLigthDistance);
                paint.setColor(Color.rgb(
                        (int) (255 * lightCoefficient),
                        (int) (255 * lightCoefficient),
                        0)
                );
                canvas.drawPath(polygon.getPath(), paint);
            }
        }
    }


    private void fillPolygons() {
        int pointsCount = circles.get(0).points.size();
        int circlesCount = circles.size();

        for (int iCircle = 1; iCircle < circlesCount; iCircle++) {
            for (int iPoint = 1; iPoint < pointsCount; iPoint++) {
                Polygon3D polygon = new Polygon3D();
                if (iPoint == pointsCount / 2 ) {
                    polygon.addPoint(circles.get(iCircle - 1).getPoints().get(iPoint - 1));
                    polygon.addPoint(circles.get(iCircle - 1).getPoints().get(iPoint));
                    polygon.addPoint(circles.get(iCircle).getPoints().get(iPoint - 1));
                } else if (iPoint == 1) {
                    polygon.addPoint(circles.get(iCircle - 1).getPoints().get(iPoint - 1));
                    polygon.addPoint(circles.get(iCircle - 1).getPoints().get(iPoint));
                    polygon.addPoint(circles.get(iCircle).getPoints().get(iPoint));
                } else if (iPoint == (circles.get(iCircle).points.size() - 1)) {
                    polygon.addPoint(circles.get(iCircle - 1).getPoints().get(iPoint));
                    polygon.addPoint(circles.get(iCircle - 1).getPoints().get(iPoint - 1));
                    polygon.addPoint(circles.get(iCircle).getPoints().get(iPoint - 1));
                } else {
                    polygon.addPoint(circles.get(iCircle - 1).getPoints().get(iPoint - 1));
                    polygon.addPoint(circles.get(iCircle - 1).getPoints().get(iPoint));
                    polygon.addPoint(circles.get(iCircle).getPoints().get(iPoint));
                    polygon.addPoint(circles.get(iCircle).getPoints().get(iPoint - 1));
                }
                polygons3D.add(polygon);
            }
        }
    }
//
//    public double getAngle(int type) {
//        double angle;
//        switch (type) {
//            case TYPE_X:
//                angle = Math.toDegrees(this.angleX);
//                if (angle > 360) {
//                    return angle -= 360;
//                } else if (angle < 0) {
//                    return angle += 360;
//                }
//                return angle;
//            case TYPE_Y:
//                angle = Math.toDegrees(this.angleY);
//                if (angle > 360) {
//                    return angle -= 360;
//                } else if (angle < 0) {
//                    return angle += 360;
//                }
//                return angle;
//            case TYPE_Z:
//                angle = Math.toDegrees(this.angleZ);
//                if (angle > 360) {
//                    return angle -= 360;
//                } else if (angle < 0) {
//                    return angle += 360;
//                }
//                return angle;
//            default:
//                return 0;
//        }
//    }

}
