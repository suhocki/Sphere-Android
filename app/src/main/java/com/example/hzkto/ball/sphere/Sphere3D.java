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
    List<Circle3D> circles;
    List<Polygon3D> polygons3D;
    Point3D camPoint;
    float radius;
    int polygons;

    public Sphere3D(float radius, float angleX, float angleY, float angleZ, Point3D lightPoint, int polygons) {
        circles = new ArrayList<>();
        polygons3D = new ArrayList<>();
        this.radius = radius;
        this.polygons = polygons;
        Update(radius, angleX, angleY, angleZ, lightPoint);
    }

    public void Update(float radius, float angleX, float angleY, float angleZ, Point3D lightPoint) {
        circles.clear();
        this.polygons3D.clear();
        this.camPoint = lightPoint;
        float angleFor360 = angleX;
        float step = (float) (2 * Math.PI / polygons);
        do {
            circles.add(new Circle3D(center, radius, polygons, angleX, angleY, angleZ));
            angleX += step;
        } while (angleX < angleFor360 + 2 * Math.PI);
        circles.add(new Circle3D(center, radius, polygons, angleX, angleY, angleZ));
        fillPolygons();
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        double distBetwCamAndCenter = MathFunctions.getDistanceBetweenTwoPoints(center, camPoint);
        double maxLigthDistance = Math.sqrt(distBetwCamAndCenter * distBetwCamAndCenter -
                radius * radius);

        for (Polygon3D polygon : polygons3D) {
            polygon.getPolygonCenter();
            if (polygon.getPolygonCenter().z >= 0) {
                final double lightCoefficient = polygon.getLightCoefficient(
                        camPoint,
                        maxLigthDistance
                );
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
        for (int iCircle = 1; iCircle < circles.size(); iCircle += 2) {
            for (int iPoint = 1; iPoint < circles.get(iCircle).points.size(); iPoint++) {
                Polygon3D polygon = new Polygon3D();
//                if (iPoint == 1) {
//                    polygon.addPoint(circles.get(iCircle - 1).getPoints().get(iPoint - 1));
//                    polygon.addPoint(circles.get(iCircle - 1).getPoints().get(iPoint));
//                    polygon.addPoint(circles.get(iCircle).getPoints().get(iPoint));
//                    polygon.addPoint(circles.get(iCircle).getPoints().get(iPoint - 1));
//                } else
                if (iPoint == (circles.get(iCircle).points.size() - 1)) {
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
