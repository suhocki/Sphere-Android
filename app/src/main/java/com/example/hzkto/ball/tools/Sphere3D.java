package com.example.hzkto.ball.tools;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.hzkto.ball.system.DrawThread;

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
    Point3D camPoint;
    float radius;

    public Sphere3D(Point3D camPoint, float radius, float angleX, float angleY) {
        circlesXY = new ArrayList<>();
        circlesXZ = new ArrayList<>();
        polygons = new ArrayList<>();
        this.radius = radius;
        Update(camPoint, radius, angleX, angleY);
    }

    public void Update(Point3D camPoint, float radius, float angleX, float angleY) {
        circlesXY.clear();
        circlesXZ.clear();
        polygons.clear();
        this.camPoint = camPoint;
        float angleFor360 = angleX;
        float step = (float) (2 * Math.PI / POLYGONS);
        do {
            circlesXY.add(new Circle3D(radius, POLYGONS, angleX, angleY));
            angleX += step;
        } while (angleX < angleFor360 + 2 * Math.PI);
        circlesXY.add(new Circle3D(radius, POLYGONS, angleX, angleY));
        mainCircle = circlesXY.get(0);
        for (int i = 0; i < mainCircle.points.size(); i++) {
            Circle3D circleBuff = new Circle3D();
            for (Circle3D circle : circlesXY) {
                circleBuff.addPoint(circle.points.get(i));
            }
            circlesXZ.add(circleBuff);
        }

        fillPolygons();
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        double distBetwCamAndCenter = Polygon3D.getDistanceBetweenTwoPoints(DrawThread.center, camPoint);
        double maxLigthDistance = Math.sqrt(distBetwCamAndCenter * distBetwCamAndCenter -
                radius * radius);

        for (Polygon3D polygon : polygons) {
            if (polygon.getPolygonCenter().z >= 0) {
                final double lightCoefficient = polygon.getLightCoefficient(camPoint, maxLigthDistance);
                paint.setColor(Color.rgb((int) (255 * lightCoefficient), (int) (255 * lightCoefficient), 0));
                canvas.drawPath(polygon.getPath(), paint);
            }
        }
    }

    private void fillPolygons() {
        for (int iCircle = 1; iCircle < circlesXY.size(); iCircle += 1) {
            for (int iPoint = 1; iPoint < circlesXY.get(iCircle).points.size(); iPoint++) {
                Polygon3D polygon = new Polygon3D();
                polygon.addPoint(circlesXY.get(iCircle - 1).getPoints().get(iPoint - 1));
                polygon.addPoint(circlesXY.get(iCircle - 1).getPoints().get(iPoint));
                polygon.addPoint(circlesXY.get(iCircle).getPoints().get(iPoint));
                polygon.addPoint(circlesXY.get(iCircle).getPoints().get(iPoint - 1));
                polygons.add(polygon);
            }
        }
    }
}
