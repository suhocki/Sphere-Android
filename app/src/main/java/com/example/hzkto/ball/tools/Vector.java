package com.example.hzkto.ball.tools;

import com.example.hzkto.ball.sphere.Point3D;

/**
 * Created by user on 12/3/2016.
 */

public class Vector extends Point3D {
    public double length;

    public Vector(double x, double y, double z) {
        super(x, y, z);
        length = Math.sqrt(x * x + y * y + z * z);
    }

    public Vector(Point3D start, Point3D end) {
        super(end.x - start.x, end.y - start.y, end.z - start.z);
        length = Math.sqrt(x * x + y * y + z * z);
    }
}
