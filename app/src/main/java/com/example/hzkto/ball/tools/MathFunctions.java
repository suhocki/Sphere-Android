package com.example.hzkto.ball.tools;

import android.graphics.Point;

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
    public static double getDistBetwTwoPoints2D(Point firstPoint, Point secondPoint) {
        return Math.sqrt((secondPoint.x - firstPoint.x) * (secondPoint.x - firstPoint.x) +
                (secondPoint.y - firstPoint.y) * (secondPoint.y - firstPoint.y));
    }
    
    public static double getDistBetwTwoPoints3D(Point3D firstPoint, Point3D secondPoint) {
        return Math.sqrt((secondPoint.x - firstPoint.x) * (secondPoint.x - firstPoint.x) +
                (secondPoint.y - firstPoint.y) * (secondPoint.y - firstPoint.y) +
                (secondPoint.z - firstPoint.z) * (secondPoint.z - firstPoint.z));
    }

    public static List<Point3D> getApproxedCircle(Point3D center, double radius, int polygons) {
        List<Point3D> points = new ArrayList<>();
        double step = 2 * Math.PI / polygons;
        double alpha = 0;
        do {
            points.add(new Point3D((center.x + radius * Math.cos(alpha)), center.y - radius * Math.sin(alpha), 0));
            alpha += step;
        } while (alpha < 2 * Math.PI);
//        points.add(new Point3D((float) (center.x + radius * Math.cos(0)), (float) (center.y - radius * Math.sin(0)), 0));
        return points;
    }

    public static void rotatePoints(List<Point3D> points, double angle, int type) {
        if (points != null) {
            switch (type) {
                case TYPE_X:
                    for (Point3D point : points) {
                        point.y -= center.y;
                        double y = point.y;
                        point.y = (point.z * sin(angle) + point.y * cos(angle));
                        point.z = (-y * sin(angle) + point.z * cos(angle));
                        point.y += center.y;
                    }
                    break;
                case TYPE_Y:
                    for (Point3D point : points) {
                        point.x -= center.x;
                        double x = point.x;
                        point.x = (point.x * cos(angle) + point.z * sin(angle));
                        point.z = (-x * sin(angle) + point.z * cos(angle));
                        point.x += center.x;
                    }
                    break;
                case TYPE_Z:
                    for (Point3D point : points) {
                        point.y -= center.y;
                        point.x -= center.x;
                        double x = point.x;
                        point.x = (point.x * cos(angle) - point.y * sin(angle));
                        point.y = (x * sin(angle) + point.y * cos(angle));
                        point.y += center.y;
                        point.x += center.x;
                    }
                    break;
            }
        }
    }

    public static Point3D getMassCenter(List<Point3D> points) {
        double xSum = 0, ySum = 0, zSum = 0;
        for (Point3D point : points) {
            xSum += point.x;
            ySum += point.y;
            zSum += point.z;
        }
        return new Point3D(xSum / points.size(), ySum / points.size(), zSum / points.size());
    }

    public static boolean isLightened(Point3D camPoint, Point3D polygonCenter, double maxLigthDistance) {
        double distanceFromCamPoint = MathFunctions.getDistBetwTwoPoints3D(camPoint, polygonCenter);
        if (distanceFromCamPoint <= maxLigthDistance) {
            return true;
        }
        return false;
    }

    public static double getLightCoeff(List<Point3D> points, Point3D camPoint, Point3D polygonCenter) {
        double X1 = points.get(0).x;
        double X2 = points.get(1).x;
        double X3 = points.get(2).x;
        double Y1 = points.get(0).y;
        double Y2 = points.get(1).y;
        double Y3 = points.get(2).y;
        double Z1 = points.get(0).z;
        double Z2 = points.get(1).z;
        double Z3 = points.get(2).z;
        double A = Y1 * (Z2 - Z3) + Y2 * (Z3 - Z1) + Y3 * (Z1 - Z2);
        double B = Z1 * (X2 - X3) + Z2 * (X3 - X1) + Z3 * (X1 - X2);
        double C = X1 * (Y2 - Y3) + X2 * (Y3 - Y1) + X3 * (Y1 - Y2);
        double x1 = polygonCenter.x;
        double x2 = camPoint.x;
        double y1 = polygonCenter.y;
        double y2 = camPoint.y;
        double z1 = polygonCenter.z;
        double z2 = camPoint.z;
        return abs((A * (x2 - x1) + B * (y2 - y1) + C * (z2 - z1)) /
                Math.sqrt(A * A + B * B + C * C) /
                Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) + (z2 - z1) * (z2 - z1)));
    }
}