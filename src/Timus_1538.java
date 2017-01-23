import java.util.*;

/**
 * Created by Nika Doghonadze
 */
public class Timus_1538 {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        int nPoints = sc.nextInt();
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < nPoints; i++) {
            points.add(new Point(sc.nextInt(), sc.nextInt(), i + 1));
        }

        findConvexPentagon(points);
    }

    private static void findConvexPentagon(List<Point> points) {
        int iterationsLeft = 150000;
        while (iterationsLeft-- > 0) {
            List<Point> randomVertexes = getFiveRandomElems(points);
            randomVertexes.sort(Comparator.comparingInt(o -> o.x));
            List<Point> convexHull = getConvexHull(randomVertexes);
            if (convexHull.size() == 5) {
                printVertexes(convexHull);
                return;
            }
        }
        System.out.println("No");
    }

    private static List<Point> getConvexHull(List<Point> points) {
        int startIndex = 0;
        ArrayList<Point> res = new ArrayList<>();
        res.add(points.get(startIndex));
        while (true) {
            int rightMostPointIndex = findRightMostPoint(startIndex, points);
            if (rightMostPointIndex == 0)
                break;
            res.add(points.get(rightMostPointIndex));
            startIndex = rightMostPointIndex;
        }
        return res;
    }

    private static int findRightMostPoint(int fromIndex, List<Point> points) {
        int resIndex = (fromIndex + 1) % points.size();
        for (int i = 0; i < points.size(); i++) {
            if (checkIsBetter(points.get(i), points.get(resIndex), points.get(fromIndex)))
                resIndex = i;
        }
        return resIndex;
    }

    private static boolean checkIsBetter(Point newPoint, Point oldPoint, Point pointFrom) {
        long vx1 = newPoint.x - pointFrom.x;
        long vy1 = newPoint.y - pointFrom.y;

        long vx2 = oldPoint.x - pointFrom.x;
        long vy2 = oldPoint.y - pointFrom.y;

        long dir = vx1 * vy2 - vx2 * vy1;

        return dir > 0;
    }

    private static void printVertexes(List<Point> randomVertexes) {
        System.out.println("Yes");
        for (Point point : randomVertexes) {
            System.out.print(point.index + " ");
        }
    }

    private static List<Point> getFiveRandomElems(List<Point> points) {
        if (points.size() < 5)
            throw new RuntimeException("points less than five");

        List<Point> res = new ArrayList<>();
        Random random = new Random();
        Set<Integer> chosenIndexes = new HashSet<>();
        while (chosenIndexes.size() < 5) {
            int curIndex = random.nextInt(points.size());
            if (chosenIndexes.contains(curIndex))
                continue;

            res.add(points.get(curIndex));
            chosenIndexes.add(curIndex);
        }

        return res;
    }

    private static class Point {
        int x, y, index;

        Point(int x, int y, int index) {
            this.x = x;
            this.y = y;
            this.index = index;
        }

        @Override
        public String toString() {
            return index + ":  " +  x + " " + y;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;

            if (obj.getClass() != Point.class)
                return false;

            Point otherPoint = (Point) obj;

            return otherPoint.x == x && otherPoint.y == y;
        }
    }
}
