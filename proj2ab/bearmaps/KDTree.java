package bearmaps;
import java.util.List;


public class KDTree implements PointSet {
    private static final boolean HORIZONTAL = false;
    private static final boolean VERTICAL = true;
    private Node root;

    private class Node {
        private Point p;
        private Node leftOrBottom;
        private Node rightOrUp;
        private boolean orientation;

        private Node(Point p, boolean orientation) {
            this.p = p;
            this.orientation = orientation;
        }
    }

    public KDTree(List<Point> points) {
        for (Point p : points) {
            root = add(root, p, false);
        }
    }

    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        Node best = nearestHelper(root.leftOrBottom, goal, root);
        best = nearestHelper(root.rightOrUp, goal, best);
        return best.p;
    }


    private Node nearestHelper(Node node, Point goalPoint, Node bestPoint) {
        if (node == null) {
            return bestPoint;
        }
        if (Point.distance(node.p, goalPoint) < Point.distance(bestPoint.p, goalPoint)) {
            bestPoint = node;
        }
        int comparison = compare(node.p, goalPoint, node.orientation);
        Node goodSide;
        Node badSide;
        if (comparison < 0) {
            goodSide = node.rightOrUp;
            badSide = node.leftOrBottom;
        } else {
            goodSide = node.leftOrBottom;
            badSide = node.rightOrUp;
        }
        bestPoint = nearestHelper(goodSide, goalPoint, bestPoint);
        if (pruneNotPossible(node, goalPoint, bestPoint.p)) {
            bestPoint = nearestHelper(badSide, goalPoint, bestPoint);
        }
        return bestPoint;
    }

    private Node add(Node n, Point p, boolean orientation) {
        if (n == null) {
            return new Node(p, orientation);
        }
        if (p.equals(n.p)) {
            return n;
        }
        int comparison = compare(p, n.p, n.orientation);
        if (comparison < 0) {
            n.leftOrBottom = add(n.leftOrBottom, p, !n.orientation);
        } else {
            n.rightOrUp = add(n.rightOrUp, p, !n.orientation);
        }
        return n;
    }

    private int compare(Point x, Point y, boolean h) {
        if (h) {
            return Double.compare(x.getY(), y.getY());
        } else {
            return Double.compare(x.getX(), y.getX());
        }
    }

    private boolean pruneNotPossible(Node node, Point goalPoint, Point bestPoint) {
        double bestDist = Point.distance(bestPoint, goalPoint);
        double worseDist;
        if (node.orientation) {
            worseDist = Point.distance(new Point(goalPoint.getX(), node.p.getY()), goalPoint);
        } else {
            worseDist = Point.distance(new Point(node.p.getX(), goalPoint.getY()), goalPoint);
        }
        return worseDist < bestDist;
    }


}
