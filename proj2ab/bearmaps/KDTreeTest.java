package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class KDTreeTest {
    private static Random rand = new Random(500);

    private static KDTree tree1() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);


        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
        return kd;
    }

    @Test
    public void testTree1() {
        KDTree kd = tree1();
        Point actual = kd.nearest(0, 7);
        Point expected = new Point(1, 5);
        assertEquals(expected, actual);
    }

    private List<Point> randomPoints(int N) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            points.add(randomPoint());
        }
        return points;
    }

    private Point randomPoint() {
        double x = rand.nextDouble();
        double y = rand.nextDouble();
        return new Point(x, y);
    }

    private void testgeneralNPointsandQQueries(int pointCount, int queryCount) {
        List<Point> points = randomPoints(pointCount);
        NaivePointSet nps = new NaivePointSet(points);
        KDTree kd = new KDTree(points);

        List<Point> queries = randomPoints(queryCount);
        for (Point p : queries) {
            Point expected = nps.nearest(p.getX(), p.getY());
            Point actual = kd.nearest(p.getX(), p.getY());
            assertEquals(expected, actual);
        }
    }

    @Test
    public void test1000Points200Queries() {
        testgeneralNPointsandQQueries(100, 200);
    }

    @Test
    public void test10000Points2000Queries() {
        testgeneralNPointsandQQueries(2000000, 2000);
    }


    private void timingTest(int pointCount, int queryCount) {
        List<Point> points = randomPoints(pointCount);
        PointSet nps = new NaivePointSet(points);
        PointSet kdTree = new KDTree(points);
        Stopwatch time = new Stopwatch();

        for (int i = 0; i < queryCount; i++) {
            double x = rand.nextDouble();
            double y = rand.nextDouble();
            nps.nearest(x, y);
        }
        System.out.println("The naive approach has taken " + time.elapsedTime() + " seconds");

        time = new Stopwatch();
        for (int i = 0; i < queryCount; i++) {
            double x = rand.nextDouble();
            double y = rand.nextDouble();
            kdTree.nearest(x, y);
        }
        System.out.println("The kd approach has taken " + time.elapsedTime() + " seconds");
    }

    @Test
    public void timeTest1000000Points2000Queries() {
        timingTest(1000000, 2000);
    }

}


