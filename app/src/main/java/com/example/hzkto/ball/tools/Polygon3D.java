package com.example.hzkto.ball.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzkto on 11/4/2016.
 */

public class Polygon3D {
    List<Point3D> points;

    public Polygon3D() {
        this.points = new ArrayList<>();
    }

    public Polygon3D(List<Point3D> points) {
        this.points = points;
    }

    public void addPoint(Point3D point) {
        this.points.add(point);
    }
}
