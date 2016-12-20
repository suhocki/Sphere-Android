package com.example.hzkto.ball.tools;

import com.example.hzkto.ball.sphere.Point3D;

import static com.example.hzkto.ball.tools.MathTools.MultiplyMatrices;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

/**
 * Created by user on 12/11/2016.
 */

public class Matrix {
    public static double[][] getFrontal() {
        double m[][] = {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 1}
        };
        return m;
    }

    public static double[][] getHorizontal() {
        double m[][] = {
                {1, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
        return m;
    }

    public static double[][] getProfile() {
        double m[][] = {
                {0, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
        return m;
    }

    public static double[][] getAxonometric(double phi, double psi) {
        phi = toRadians(phi);
        psi = toRadians(psi);
        double m[][] = {
                {cos(psi), sin(phi) * sin(psi), 0, 0},
                {0, cos(phi), 0, 0},
                {sin(psi), -sin(phi) * cos(psi), 0, 0},
                {0, 0, 0, 1}
        };
        return m;
    }

    public static double[][] getOblique(double L, double alpha) {
        alpha = toRadians(alpha);
        double m[][] = {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {L * cos(alpha), L * sin(alpha), 0, 0},
                {0, 0, 0, 1}
        };
        return m;
    }

    public static Point3D getPerspective(Point3D before, double d, double q, double fi, double psi) {
        fi = toRadians(fi);
        psi = toRadians(psi);
        double Sp = sin(psi);
        double Cp = cos(psi);
        double Sf = sin(fi);
        double Cf = cos(fi);
        double matrixView[][] = {
                {-Sp, -Cf * Cp, -Sf * Cp, 0},
                {Cp, -Cf * Sp, -Sf * Sp, 0},
                {0, Sf, -Cf, 0},
                {0, 0, q, 1}};

//        double matrixView[][] = {
//                {1, 0, 0, 0},
//                {0, 1, 0, 0},
//                {0, 0, 0, 1 / d},
//                {0, 0, 0, 1}
//        };
        Point3D correctPoint = MultiplicateF(before, matrixView);
        return new Point3D(correctPoint.x / (correctPoint.z / d + 1), correctPoint.y / (correctPoint.z / d + 1), correctPoint.z / (correctPoint.z / d + 1));
    }

    public static Point3D MultiplicateF(Point3D vertex, double[][] ar) {
        double[][] result = new double[1][4];
        double[][] a = new double[1][4];
        double[][] b = ar;
        a[0][0] = vertex.x;
        a[0][1] = vertex.y;
        a[0][2] = vertex.z;
        a[0][3] = 1;

        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = 0;
                for (int q = 0; q < 4; q++)
                    result[i][j] += a[i][q] * b[q][j];
            }
        }
        return (new Point3D((result[0][0]), (result[0][1]), (result[0][2])));

    }
}
