package com.example.hzkto.ball.sphere;

import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

import com.example.hzkto.ball.tools.Vector;

import static com.example.hzkto.ball.system.DrawThread.center;
import static com.example.hzkto.ball.system.DrawThread.lightPoint;
import static com.example.hzkto.ball.tools.MathTools.getCrossProduct;
import static com.example.hzkto.ball.tools.MathTools.getDistBetwTwoPoints3D;
import static com.example.hzkto.ball.tools.MathTools.getDotProduct;
import static com.example.hzkto.ball.tools.MathTools.getLightCoeff;
import static com.example.hzkto.ball.tools.MathTools.getMassCenter;
import static com.example.hzkto.ball.tools.MathTools.getMaxLightDist;
import static com.example.hzkto.ball.tools.MathTools.isLightened;

/**
 * Created by hzkto on 11/4/2016.
 */

public class Polygon3D {
    List<Point3D> points;
    Point3D polygonCenter;
    Point3D pointOnDist;
    Point3D pointOnDistHelp;
    boolean isVisible;
    boolean flagFirstCase;
    Vector vectorNormal;

    public Polygon3D(List<Point3D> points) {
        this.points = points;
        polygonCenter = getMassCenter(points);
        vectorNormal = getCrossProduct(new Vector(points.get(0), points.get(1)),
                new Vector(points.get(0), points.get(2)));
        pointOnDist = getPointOnDist();

        isVisible = (points.get(0).z < pointOnDist.z);
//        isVisible = (getDotProduct(new Vector(0,0,-1),vectorNormal) > 0);
    }

    public Path getPath() {
        Path path = new Path();
        path.moveTo((float) points.get(0).x, (float) points.get(0).y);
        for (Point3D point : points) {
            path.lineTo((float) point.x, (float) point.y);
        }
        return path;
    }

    public double getLightCoefficient(Point3D camPoint, Point3D center, double radius) {
//        if (!isLightened(camPoint, polygonCenter, getMaxLightDist(center, lightPoint, radius))) {
//            return 0;
//        }
        return getLightCoeff(points, camPoint, polygonCenter);
    }


    public Point3D getPointOnDist() {
        Point3D C = new Point3D(points.get(0).x + vectorNormal.x,
                points.get(0).y + vectorNormal.y,
                points.get(0).z + vectorNormal.z);
        Point3D H = points.get(0);
        Point3D C2 = new Point3D(2 * H.x - C.x,
                2 * H.y - C.y,
                2 * H.z - C.z);
        double first = getDistBetwTwoPoints3D(center, C);
        double second = getDistBetwTwoPoints3D(center, C2);
        if (first > second) {
            return C;
        } else {
            return C2;
        }
    }
}
