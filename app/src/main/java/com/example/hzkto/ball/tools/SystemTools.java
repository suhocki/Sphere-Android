package com.example.hzkto.ball.tools;

import android.view.View;

import com.example.hzkto.ball.sphere.Point3D;

/**
 * Created by user on 12/7/2016.
 */

public class SystemTools {
    public static Point3D getViewCenter(View view) {
        return new Point3D(view.getWidth() / 2,
                view.getHeight() / 2,
                0);
    }

    public static double getStandartRadius(Point3D center) {
        return StrictMath.min(center.x, center.y) * 0.8;
    }

    public static Point3D getStandartLightPoint(Point3D center) {
        return new Point3D(center.x+2000,
                center.y+0,
                center.z + 500);

    }
}
