package bearmaps.proj2c;
import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import bearmaps.proj2ab.DoubleMapPQ;
import bearmaps.proj2ab.ExtrinsicMinPQ;
import edu.princeton.cs.algs4.Stopwatch;


public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private List<Vertex> solution;
    private double solutionWeight;
    private int numStatesExplored;
    private double explorationTime;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        solution = new LinkedList<>();
        HashMap<Vertex, Double> distTo = new HashMap<>();
        HashMap<Vertex, Vertex> edgeTo = new HashMap<>();
        ExtrinsicMinPQ<Vertex> fringe = new DoubleMapPQ();
        distTo.put(start, 0.0);
        fringe.add(start, distTo.get(start) + input.estimatedDistanceToGoal(start, end));
        while (fringe.size() > 0) {
            if (fringe.getSmallest().equals(end)) {
                outcome = SolverOutcome.SOLVED;
                Vertex p = end;
                while (!p.equals(start)) {
                    solution.add(0, p);
                    p = edgeTo.get(p);
                }
                solution.add(0, start);
                solutionWeight = distTo.get(end);
                explorationTime = sw.elapsedTime();
                return;
            }
            if (sw.elapsedTime() > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                explorationTime = sw.elapsedTime();
                return;
            }
            Vertex pos = fringe.removeSmallest();
            numStatesExplored += 1;
            for (WeightedEdge<Vertex> edge : input.neighbors(pos)) {
                Vertex q = edge.to();
                double w = edge.weight();
                if (!distTo.containsKey(q) || distTo.get(pos) + w < distTo.get(q)) {
                    distTo.put(q, distTo.get(pos) + w);
                    edgeTo.put(q, pos);
                    if (fringe.contains(q)) {
                        fringe.changePriority(q, distTo.get(q)
                               + input.estimatedDistanceToGoal(q, end));
                    } else {
                        fringe.add(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                    }
                }
            }
        }
        outcome = SolverOutcome.UNSOLVABLE;
        explorationTime = sw.elapsedTime();
        return;
    }

    public SolverOutcome outcome() {
        return outcome;
    }
    public List<Vertex> solution() {
        return solution;
    }
    public double solutionWeight() {
        if (outcome == SolverOutcome.SOLVED) {
            return solutionWeight;
        }
        return 0;
    }
    public int numStatesExplored() {
        return numStatesExplored;
    }
    public double explorationTime() {
        return explorationTime;
    }
}
