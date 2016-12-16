package com.example.hzkto.ball.tools;

import android.graphics.Point;

import com.example.hzkto.ball.sphere.Point3D;
import com.example.hzkto.ball.sphere.Polygon3D;

import java.util.ArrayList;
import java.util.List;

import static com.example.hzkto.ball.Constants.TYPE_X;
import static com.example.hzkto.ball.Constants.TYPE_Y;
import static com.example.hzkto.ball.Constants.TYPE_Z;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by hzkto on 11/20/2016.
 */

public class MathTools {
    public static double getDistBetwTwoPoints2D(Point firstPoint, Point secondPoint) {
        return StrictMath.hypot((secondPoint.x - firstPoint.x), (secondPoint.y - firstPoint.y));
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

    public static void rotatePoints(List<Point3D> points, Point3D center, double angle, int type) {
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

    public static double getDotProduct(Vector v1, Vector v2) {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }

    public static Vector getCrossProduct(Vector v1, Vector v2) {
        return new Vector(
                v1.y * v2.z - v1.z * v2.y,
                v1.z * v2.x - v1.x * v2.z,
                v1.x * v2.y - v1.y * v2.x
        );
    }

    public static Point3D MultiplyMatrices(Point3D p, double m[][]) {
        return new Point3D(
                p.x * m[0][0] + p.y * m[1][0] + p.z * m[2][0] + m[3][0],
                p.x * m[0][1] + p.y * m[1][1] + p.z * m[2][1] + m[3][1],
                p.x * m[0][2] + p.y * m[1][2] + p.z * m[2][2] + m[3][2]
        );
    }
}