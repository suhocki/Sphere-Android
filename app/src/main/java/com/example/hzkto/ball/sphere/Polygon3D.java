package com.example.hzkto.ball.sphere;

import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

import static com.example.hzkto.ball.tools.MathFunctions.getLightCoeff;
import static com.example.hzkto.ball.tools.MathFunctions.getMassCenter;
import static com.example.hzkto.ball.tools.MathFunctions.isLightened;

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
        path.moveTo((float)points.get(0).x, (float)points.get(0).y);
        for (Point3D point : points) {
            path.lineTo((float)point.x, (float)point.y);
        }
        return path;
    }

    public Point3D getPolygonCenter() {
        if (polygonCenter == null) {
            polygonCenter = getMassCenter(points);
        }
        return polygonCenter;
    }

    public double getLightCoefficient(Point3D camPoint, double maxLigthDistance) {
        if (!isLightened(camPoint, polygonCenter, maxLigthDistance)) {
            return 0;
        }
        return getLightCoeff(points, camPoint, polygonCenter);
    }


}
