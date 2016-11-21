package com.example.hzkto.ball.tools;

import com.example.hzkto.ball.sphere.Point3D;

import java.util.ArrayList;
import java.util.List;

import static com.example.hzkto.ball.Constants.TYPE_X;
import static com.example.hzkto.ball.Constants.TYPE_Y;
import static com.example.hzkto.ball.Constants.TYPE_Z;
import static com.example.hzkto.ball.system.DrawThread.center;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by hzkto on 11/20/2016.
 */

public class MathFunctions {
    public static double getDistanceBetweenTwoPoints(Point3D firstPoint, Point3D secondPoint) {
        return Math.sqrt((secondPoint.x - firstPoint.x) * (secondPoint.x - firstPoint.x) +
                (secondPoint.y - firstPoint.y) * (secondPoint.y - firstPoint.y) +
                (secondPoint.z - firstPoint.z) * (secondPoint.z - firstPoint.z));
    }

    public static List<Point3D> getApproximatedCircle(Point3D center, float radius, int polygons) {
        List<Point3D> points = new ArrayList<>();
        float step = (float) (2 * Math.PI / polygons);
        float alpha = 0;
        do {
            points.add(new Point3D((float) (center.x + radius * Math.cos(alpha)), (float) (center.y - radius * Math.sin(alpha)), 0));
            alpha += step;
        } while (alpha < 2 * Math.PI);
//        points.add(new Point3D((float) (center.x + radius * Math.cos(0)), (float) (center.y - radius * Math.sin(0)), 0));
        return points;
    }

    public static void rotatePoints(List<Point3D> points, float angle, int type) {
        if (points != null) {
            switch (type) {
                case TYPE_X:
                    for (Point3D point : points) {
                        point.y -= center.y;
                        float y = point.y;
                        point.y = (float) (point.z * sin(angle) + point.y * cos(angle));
                        point.z = (float) (-y * sin(angle) + point.z * cos(angle));
                        point.y += center.y;
                    }
                    break;
                case TYPE_Y:
                    for (Point3D point : points) {
                        point.x -= center.x;
                        float x = point.x;
                        point.x = (float) (point.x * cos(angle) + point.z * sin(angle));
                        point.z = (float) (-x * sin(angle) + point.z * cos(angle));
                        point.x += center.x;
                    }
                    break;
                case TYPE_Z:
                    for (Point3D point : points) {
                        point.y -= center.y;
                        point.x -= center.x;
                        float x = point.x;
                        point.x = (float) (point.x * cos(angle) - point.y * sin(angle));
                        point.y = (float) (x * sin(angle) + point.y * cos(angle));
                        point.y += center.y;
                        point.x += center.x;
                    }
                    break;
            }
        }
    }

    public static Point3D getMassCenter(List<Point3D> points) {
        float xSum = 0, ySum = 0, zSum = 0;
        for (Point3D point : points) {
            xSum += point.x;
            ySum += point.y;
            zSum += point.z;
        }
        return new Point3D(xSum / points.size(), ySum / points.size(), zSum / points.size());
    }

    public static boolean isLightened(Point3D camPoint, Point3D polygonCenter, double maxLigthDistance) {
        double distanceFromCamPoint = MathFunctions.getDistanceBetweenTwoPoints(camPoint, polygonCenter);
        if (distanceFromCamPoint <= maxLigthDistance) {
            return true;
        }
        return false;
    }

    public static double getLightCoeff(List<Point3D> points, Point3D camPoint, Point3D polygonCenter) {
        float X1 = points.get(0).x;
        float X2 = points.get(1).x;
        float X3 = points.get(2).x;
        float Y1 = points.get(0).y;
        float Y2 = points.get(1).y;
        float Y3 = points.get(2).y;
        float Z1 = points.get(0).z;
        float Z2 = points.get(1).z;
        float Z3 = points.get(2).z;
        float A = Y1 * (Z2 - Z3) + Y2 * (Z3 - Z1) + Y3 * (Z1 - Z2);
        float B = Z1 * (X2 - X3) + Z2 * (X3 - X1) + Z3 * (X1 - X2);
        float C = X1 * (Y2 - Y3) + X2 * (Y3 - Y1) + X3 * (Y1 - Y2);
        float x1 = polygonCenter.x;
        float x2 = camPoint.x;
        float y1 = polygonCenter.y;
        float y2 = camPoint.y;
        float z1 = polygonCenter.z;
        float z2 = camPoint.z;
        return abs((A * (x2 - x1) + B * (y2 - y1) + C * (z2 - z1)) /
                Math.sqrt(A * A + B * B + C * C) /
                Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) + (z2 - z1) * (z2 - z1)));
    }
}