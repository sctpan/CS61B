package lab11.graphs;

import edu.princeton.cs.algs4.In;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /**
     * Conducts a breadth first search of the maze starting at the source.
     */
    private void bfs() {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        marked[s] = true;
        announce();
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int adj : maze.adj(curr)) {
                if (!marked[adj]) {
                    marked[adj] = true;
                    queue.add(adj);
                    distTo[adj] = distTo[curr] + 1;
                    edgeTo[adj] = curr;
                    announce();
                    if (adj == t) {
                        return;
                    }
                }
            }

        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

