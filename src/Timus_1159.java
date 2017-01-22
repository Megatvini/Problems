import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.PI;
import static java.lang.Math.asin;
import static java.lang.Math.sin;

/**
 * Created by Nika Doghonadze
 */
public class Timus_1159 {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        int nEdges = sc.nextInt();
        int[] edges = new int[nEdges];
        for (int i = 0; i < nEdges; i++) {
            edges[i] = sc.nextInt();
        }
        Arrays.sort(edges);

        if (!checkPolygon(edges)) {
            System.out.println("0.00");
            return;
        }

        double radius = findRadius(edges);
//        System.out.println(radius);

        double area = findArea(edges, radius);
        System.out.println(String.format( "%.2f", area));
    }

    private static double findArea(int[] edges, double radius) {
        double segmentArea = 0;
        double totalAngle = 0;
        for (int i = 0; i < edges.length; i++) {
            int edge = edges[i];
            double a = edge / (2.0 * radius);
            double theta = 2.0 * asin(a);
            if (i == edges.length - 1 && totalAngle + theta < 2 * PI) {
                theta = 2.0 * PI - theta;
            }
            totalAngle += theta;
            double area = radius * radius * (theta - sin(theta)) / 2.0;
            segmentArea += area;
        }

        double circleArea = radius * radius * PI;
        return circleArea - segmentArea;
    }

    private static double findRadius(int[] edges) {
        double maxRadius = sum(edges) * edges.length;
        double minRadius = arrayMax(edges)/2.0;
        int iterations = 100000;


        while (iterations-- > 0) {
            double checkRadius = (minRadius + maxRadius) / 2.0;
            double totalAngle = calculateTotalAngle(edges, checkRadius);
            if (totalAngle < 2.0 * PI) {
                maxRadius = checkRadius;
            } else {
                minRadius = checkRadius;
            }
        }
        return minRadius;
    }

    private static int arrayMax(int[] edges) {
        if (edges.length == 0)
            throw new RuntimeException();
        int res = edges[0];
        for (int i = 1; i < edges.length; i++) {
            res = Math.max(res, edges[i]);
        }
        return res;
    }

    private static double calculateTotalAngle(int[] edges, double checkRadius) {
        double res = 0;
        for (int i = 0; i < edges.length; i++) {
            int edge = edges[i];
            double a = edge / (2.0 * checkRadius);
            double inc = 2.0 * asin(a);
            if (i == edges.length - 1 && res < PI) {
                if (res + 2 * PI - inc > 2 * PI) {
                    return 0;
                } else {
                    return 10;
                }
            }
            res += inc;
        }
        return res;
    }

    private static boolean checkPolygon(int[] edges) {
        if (edges.length < 3)
            return false;

        int maxEdge = edges[edges.length - 1];
        long sumEdges = sum(edges) - maxEdge;
        return sumEdges > maxEdge;
    }

    private static long sum(int[] edges) {
        long res = 0;
        for (int edge : edges) {
            res += edge;
        }
        return res;
    }
}
