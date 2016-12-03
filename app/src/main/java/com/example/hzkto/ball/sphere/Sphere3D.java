package com.example.hzkto.ball.sphere;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import static com.example.hzkto.ball.Constants.TYPE_X;
import static com.example.hzkto.ball.Constants.TYPE_Y;
import static com.example.hzkto.ball.Constants.TYPE_Z;
import static com.example.hzkto.ball.R.string.rotate;
import static com.example.hzkto.ball.tools.MathTools.rotatePoints;

/**
 * Created by hzkto on 10/30/2016.
 */

public class Sphere3D {
    List<Circle3D> circles;
    List<Polygon3D> polygons3D;
    Point3D lightPoint;
    Point3D center;
    double radius;
    int polygons;
    int color[];
    boolean reflect, invisLines;
    private boolean scale = true;
    double scaleX, scaleY, scaleZ;
    private double angleX, angleY, angleZ;

    public Sphere3D(Point3D center, double radius, double angleX,
                    double angleY, double angleZ, Point3D lightPoint,
                    int polygons, boolean reflect, boolean invisLines, int color[]) {
        this.center = center;
        this.reflect = reflect;
        this.invisLines = invisLines;
        this.polygons = polygons;
        this.color = color;
        this.circles = new ArrayList<>();
        this.polygons3D = new ArrayList<>();
        this.scaleX = 1;
        this.scaleY = 1;
        this.scaleZ = 1;
        update(center, radius, angleX, angleY, angleZ, lightPoint, scaleX, scaleY, scaleZ);
    }

    public void update(Point3D center, double radius, double angleX, double angleY, double angleZ, Point3D lightPoint,
                       double scaleX, double scaleY, double scaleZ) {
        this.radius = radius;
        this.center = center;
        this.lightPoint = lightPoint;
        this.angleX = angleX;
        this.angleY = angleY;
        this.angleZ = angleZ;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
    }

    public void draw(Canvas canvas) {
        fillCircles();
        changeScale(circles);
        rotateCircles(circles);
        fillPolygons();
//        changeScale(polygons3D);
//        rotatePolygons(polygons3D);
        Paint paint = getPaint();
        for (Polygon3D polygon : polygons3D) {
            if (invisLines) {
                if (polygon.isVisible) {
                    if (reflect) {
//                    if (false) {
                        final double lightCoefficient = polygon.getLightCoefficient(lightPoint, center, radius);
                        paint.setColor(Color.rgb(
                                (int) (color[0] * lightCoefficient),
                                (int) (color[1] * lightCoefficient),
                                (int) (color[2] * lightCoefficient))
                        );
                        canvas.drawPath(polygon.getPath(), paint);
                    } else {
                        canvas.drawPath(polygon.getPath(), paint);
                    }
                }
            } else {
                canvas.drawPath(polygon.getPath(), paint);
            }
        }
    }

    private void changeScale2(List<Polygon3D> polygons) {
        for (Polygon3D polygon : polygons) {
            for (Point3D point3D : polygon.points) {
                point3D.x -= center.x;
                point3D.y -= center.y;
                point3D.z -= center.z;
                point3D.x *= scaleX;
                point3D.y *= scaleY;
                point3D.z *= scaleZ;
                point3D.x += center.x;
                point3D.y += center.y;
                point3D.z += center.z;
            }
        }
    }

    private void changeScale(List<Circle3D> circles) {
        for (Circle3D circle : circles) {
            for (Point3D point3D : circle.points) {
                point3D.x -= center.x;
                point3D.y -= center.y;
                point3D.z -= center.z;
                point3D.x *= scaleX;
                point3D.y *= scaleY;
                point3D.z *= scaleZ;
                point3D.x += center.x;
                point3D.y += center.y;
                point3D.z += center.z;
            }
        }
    }

    private Paint getPaint() {
        Paint paint = new Paint();
        if (reflect) {
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setColor(Color.rgb(0, 0, 0));
        } else {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(2);
        }
        return paint;
    }

    private void fillPolygons() {
        polygons3D.clear();
        for (int iCircle = 0; iCircle < circles.size() - 1; iCircle++) {
            for (int iPoint = 0; iPoint < polygons; iPoint++) {
                List<Point3D> points = new ArrayList<>();
                if (iPoint == polygons / 2 - 1) {
                    points.add(circles.get(iCircle).getPoints().get(iPoint));
                    points.add(circles.get(iCircle).getPoints().get(iPoint + 1));
                    points.add(circles.get(iCircle + 1).getPoints().get(iPoint));
                } else if (iPoint == 0) {
                    points.add(circles.get(iCircle).getPoints().get(iPoint));
                    points.add(circles.get(iCircle).getPoints().get(iPoint + 1));
                    points.add(circles.get(iCircle + 1).getPoints().get(iPoint + 1));
                } else if (iPoint == (polygons - 1)) {
                    points.add(circles.get(iCircle).getPoints().get(iPoint + 1));
                    points.add(circles.get(iCircle).getPoints().get(iPoint));
                    points.add(circles.get(iCircle + 1).getPoints().get(iPoint));
                } else {
                    points.add(circles.get(iCircle).getPoints().get(iPoint));
                    points.add(circles.get(iCircle).getPoints().get(iPoint + 1));
                    points.add(circles.get(iCircle + 1).getPoints().get(iPoint + 1));
                    points.add(circles.get(iCircle + 1).getPoints().get(iPoint));
                }
                polygons3D.add(new Polygon3D(points));
            }
        }
    }

    private void fillCircles() {
        circles.clear();
        double angleFor360 = angleX;
        double buffX = angleX;
        double step = 2 * Math.PI / polygons;
        do {
            circles.add(new Circle3D(center, radius, polygons, buffX));
            buffX += step;
        } while (buffX < angleFor360 + Math.PI);
        circles.add(new Circle3D(center, radius, polygons, buffX));
    }

    private void rotatePolygons(List<Polygon3D> polygons) {
        for (Polygon3D polygon : polygons) {
            rotatePoints(polygon.points, center, angleX, TYPE_X);
            rotatePoints(polygon.points, center, angleY, TYPE_Y);
            rotatePoints(polygon.points, center, angleZ, TYPE_Z);
        }
    }

    private void rotateCircles(List<Circle3D> circles) {
        for (Circle3D circle : circles) {
            rotatePoints(circle.points, center, angleX, TYPE_X);
            rotatePoints(circle.points, center, angleY, TYPE_Y);
            rotatePoints(circle.points, center, angleZ, TYPE_Z);
        }
    }
}
