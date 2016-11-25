package com.example.hzkto.ball.sphere;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.hzkto.ball.tools.MathTools;

import java.util.ArrayList;
import java.util.List;

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
        update(center, radius, angleX, angleY, angleZ, lightPoint);
    }

    public void update(Point3D center, double radius, double angleX, double angleY, double angleZ, Point3D lightPoint) {
        this.radius = radius;
        this.circles.clear();
        this.polygons3D.clear();
        this.lightPoint = lightPoint;
        double angleFor360 = angleX;
        double step = 2 * Math.PI / polygons;
        do {
            circles.add(new Circle3D(center, radius, polygons, angleX, angleY, angleZ));
            angleX += step;
        } while (angleX < angleFor360 + 1 * Math.PI);
        this.circles.add(new Circle3D(center, radius, polygons, angleX, angleY, angleZ));
        fillPolygons();
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        if (reflect) {
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setColor(Color.rgb(0, 0, 0));
        } else {
            paint.setStyle(Paint.Style.STROKE);
        }
        paint.setStrokeWidth(2);
        canvas.drawRGB(255, 255, 255);
        double distBetwCamAndCenter = MathTools.getDistBetwTwoPoints3D(center, lightPoint);
        double maxLigthDistance = Math.sqrt(distBetwCamAndCenter * distBetwCamAndCenter -
                radius * radius);
        for (Polygon3D polygon : polygons3D) {
            polygon.getPolygonCenter();
            if (invisLines) {
                if (polygon.getPolygonCenter().z >= 0) {
                    if (reflect) {
                        final double lightCoefficient = polygon.getLightCoefficient(lightPoint, maxLigthDistance);
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

    private void fillPolygons() {
        int pointsCount = circles.get(0).points.size();
        int circlesCount = circles.size();

        for (int iCircle = 1; iCircle < circlesCount; iCircle++) {
            for (int iPoint = 1; iPoint < pointsCount; iPoint++) {
                Polygon3D polygon = new Polygon3D();
                if (iPoint == pointsCount / 2) {
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
}
