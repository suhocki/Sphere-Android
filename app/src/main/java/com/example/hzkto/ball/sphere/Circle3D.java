package com.example.hzkto.ball.sphere;


import java.util.List;

import static com.example.hzkto.ball.Constants.TYPE_X;
import static com.example.hzkto.ball.Constants.TYPE_Y;
import static com.example.hzkto.ball.Constants.TYPE_Z;
import static com.example.hzkto.ball.tools.MathTools.getApproxedCircle;
import static com.example.hzkto.ball.tools.MathTools.rotatePoints;


/**
 * Created by hzkto on 10/28/2016.
 */

public class Circle3D {
    List<Point3D> points;

    public Circle3D(Point3D center, double radius, int polygons, double angleX, double angleY, double angleZ) {
        points = getApproxedCircle(center, radius, polygons);
        rotatePoints(points, center, angleX, TYPE_X);
        rotatePoints(points, center, angleY, TYPE_Y);
        rotatePoints(points, center, angleZ, TYPE_Z);
    }

    public List<Point3D> getPoints() {
        return points;
    }
}
