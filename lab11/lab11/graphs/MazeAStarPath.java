package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;

/**
 * @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    private class Node implements Comparable {
        private int v;
        private int priority;

        public Node(int v, int dist) {
            this.v = v;
            this.priority = dist + h(v);
        }

        @Override
        public int compareTo(Object o) {
            Node n = (Node) o;
            return this.priority - n.priority;
        }
    }


    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /**
     * Estimate of the distance from v to the target.
     */
    private int h(int v) {
        int vx = maze.toX(v);
        int vy = maze.toY(v);
        int tx = maze.toX(t);
        int ty = maze.toY(t);
        return Math.abs(vx - tx) + Math.abs(vy - ty);
    }

    /**
     * Finds vertex estimated to be closest to target.
     */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /**
     * Performs an A star search from vertex s.
     */
    private void astar(int s) {
        MinPQ<Node> pq = new MinPQ<>();
        Node start = new Node(s, distTo[s]);
        pq.insert(start);
        while (!pq.isEmpty()) {
            Node curr = pq.delMin();
            marked[curr.v] = true;
            announce();
            if (curr.v == t) {
                return;
            }
            for (int adj : maze.adj(curr.v)) {
                if (distTo[curr.v] + 1 < distTo[adj]) {
                    distTo[adj] = distTo[curr.v] + 1;
                    edgeTo[adj] = curr.v;
                }
                if (!marked[adj]) {
                    pq.insert(new Node(adj, distTo[adj]));
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

