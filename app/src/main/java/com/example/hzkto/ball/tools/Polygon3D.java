package com.example.hzkto.ball.tools;

import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Created by hzkto on 11/4/2016.
 */

public class Polygon3D {
    List<Point3D> points;
    Point3D polygonCenter;

    public Polygon3D() {
        this.points = new ArrayList<>();
    }

    public void addPoint(Point3D point) {
        this.points.add(point);
    }

    public Path getPath() {
        Path path = new Path();
        path.moveTo(points.get(0).x, points.get(0).y);
        for (Point3D point : points) {
            path.lineTo(point.x, point.y);
        }
        return path;
    }

    public Point3D getPolygonCenter() {
        if (polygonCenter == null) {
            float xSum = 0, ySum = 0, zSum = 0;
            for (Point3D point : points) {
                xSum += point.x;
                ySum += point.y;
                zSum += point.z;
            }
            polygonCenter = new Point3D(xSum / points.size(), ySum / points.size(), zSum / points.size());
        }
        return polygonCenter;
    }

    public double getLightCoefficient(Point3D camPoint, double maxLigthDistance) {
        float X1 = points.get(0).x;
        float X2 = points.get(1).x;
        float X3 = points.get(2).x;
        float Y1 = points.get(0).y;
        float Y2 = points.get(1).y;
        float Y3 = points.get(2).y;
        float Z1 = points.get(0).z;
        float Z2 = points.get(1).z;
        float Z3 = points.get(2).z;
        if (X1 == X2 && Y1 == Y2 && Z1 == Z2) {
            X1 = points.get(3).x;
            Y1 = points.get(3).y;
            Z1 = points.get(3).z;
        } else if (X1 == X3 && Y1 == Y3 && Z1 == Z3) {
            X1 = points.get(3).x;
            Y1 = points.get(3).y;
            Z1 = points.get(3).z;
        } else if (X3 == X2 && Y3 == Y2 && Z3 == Z2) {
            X2 = points.get(3).x;
            Y2 = points.get(3).y;
            Z2 = points.get(3).z;
        }
        float A = Y1 * (Z2 - Z3) + Y2 * (Z3 - Z1) + Y3 * (Z1 - Z2);
        float B = Z1 * (X2 - X3) + Z2 * (X3 - X1) + Z3 * (X1 - X2);
        float C = X1 * (Y2 - Y3) + X2 * (Y3 - Y1) + X3 * (Y1 - Y2);
        float x1 = polygonCenter.x;
        float x2 = camPoint.x;
        float y1 = polygonCenter.y;
        float y2 = camPoint.y;
        float z1 = polygonCenter.z;
        float z2 = camPoint.z;

        final double distanceFromCamPoint = getDistanceBetweenTwoPoints(camPoint, polygonCenter);
        if (distanceFromCamPoint > maxLigthDistance) {
            return 0;
        }
        return abs((A * (x2 - x1) + B * (y2 - y1) + C * (z2 - z1)) /
                Math.sqrt(A * A + B * B + C * C) /
                Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) + (z2 - z1) * (z2 - z1)));
    }

    public static double getDistanceBetweenTwoPoints(Point3D firstPoint, Point3D secondPoint) {
        return Math.sqrt((secondPoint.x - firstPoint.x) *(secondPoint.x - firstPoint.x) +
                (secondPoint.y - firstPoint.y) *(secondPoint.y - firstPoint.y) +
                (secondPoint.z - firstPoint.z) *(secondPoint.z - firstPoint.z));
    }
}
