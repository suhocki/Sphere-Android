package com.example.hzkto.ball.sphere;

import android.graphics.Path;

import com.example.hzkto.ball.tools.Vector;

import java.util.List;

import static com.example.hzkto.ball.system.DrawThread.center;
import static com.example.hzkto.ball.system.DrawThread.lightPoint;
import static com.example.hzkto.ball.tools.MathTools.getCrossProduct;
import static com.example.hzkto.ball.tools.MathTools.getDistBetwTwoPoints3D;
import static com.example.hzkto.ball.tools.MathTools.getDotProduct;
import static com.example.hzkto.ball.tools.MathTools.getMassCenter;

/**
 * Created by hzkto on 11/4/2016.
 */

public class Polygon3D {
    List<Point3D> points;
    Point3D polygonCenter;
    boolean isVisible;
    Vector vectorNormal;
    Vector vectorObserver;
    Vector vectorLight;
    public boolean isLighted;
    public double lightCoefficient;

    public Polygon3D(List<Point3D> points) {
        this.points = points;
        polygonCenter = getMassCenter(points);
        vectorNormal = getVectorNormal();
        vectorObserver = new Vector(0, 0, 1);
        isVisible = (getDotProduct(vectorObserver, vectorNormal) > 0);
        vectorLight = new Vector(center, lightPoint);
        isLighted = (getDotProduct(vectorLight, vectorNormal) > 0);
        lightCoefficient = getLightCoefficient();
    }

    public Path getPath() {
        Path path = new Path();
        path.moveTo((float) points.get(0).x, (float) points.get(0).y);
        for (Point3D point : points) {
            path.lineTo((float) point.x, (float) point.y);
        }
        return path;
    }

    public double getLightCoefficient() {
        lightCoefficient = 0.5 + 0.9 * 0.6 * getDotProduct(vectorLight, vectorNormal) / (vectorLight.length * vectorNormal.length);
        lightCoefficient = lightCoefficient > 1 ? 1 : lightCoefficient;
        lightCoefficient = lightCoefficient < 0 ? 0 : lightCoefficient;
        return lightCoefficient;
    }


    public Vector getVectorNormal() {
        Vector prevVector = getCrossProduct(new Vector(points.get(0), points.get(1)), new Vector(points.get(0), points.get(2)));
        Point3D C = new Point3D(points.get(0).x + prevVector.x, points.get(0).y + prevVector.y, points.get(0).z + prevVector.z);
        Point3D H = points.get(0);
        Point3D C2 = new Point3D(2 * H.x - C.x, 2 * H.y - C.y, 2 * H.z - C.z);
        double first = getDistBetwTwoPoints3D(center, C);
        double second = getDistBetwTwoPoints3D(center, C2);
        if (first > second) {
            return new Vector(points.get(0), C);
        } else {
            return new Vector(points.get(0), C2);
        }
    }
}
