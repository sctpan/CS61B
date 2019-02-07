package hw4.puzzle;

import java.util.*;

import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private List<WorldState> solution = new ArrayList();
    private MinPQ<Node> pq = new MinPQ();
    private Node finish;

    private class Node implements Comparable {
        private int M;
        private int E;
        private WorldState state;
        private Node prev;

        public Node(int m, WorldState state, Node prev) {
            M = m;
            E = state.estimatedDistanceToGoal();
            this.state = state;
            this.prev = prev;
        }

        @Override
        public int compareTo(Object o) {
            if (o.getClass() == this.getClass()) {
                Node n = (Node) o;
                return (this.M + this.E) - (n.M + n.E);
            }
            return -1;
        }
    }

    /**
     * Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists.
     */
    public Solver(WorldState initial) {
        Node first = new Node(0, initial, null);
        pq.insert(first);
        while (!pq.isEmpty()) {
            Node curr = pq.delMin();
            if (curr.E == 0) {
                finish = curr;
//                System.out.println(finish.state);
                return;
            }
            for (WorldState neighbor : curr.state.neighbors()) {
                if (curr.prev == null || !neighbor.equals(curr.prev.state)) {
                    Node n = new Node(curr.M + 1, neighbor, curr);
                    pq.insert(n);
                }
            }
        }
    }

    /**
     * Returns the minimum number of moves to solve the puzzle starting
     * at the initial WorldState.
     */
    public int moves() {
//        System.out.println(finish.M);
        return finish.M;
    }

    /**
     * Returns a sequence of WorldStates from the initial WorldState
     * to the solution.
     */
    public Iterable<WorldState> solution() {
        Node tmp = finish;
        while (tmp != null) {
            solution.add(tmp.state);
            tmp = tmp.prev;
        }
        Collections.reverse(solution);
        return solution;
    }

    private void printPQ() {
        System.out.print("pq: ");
        for (Iterator it = pq.iterator(); it.hasNext(); ) {
            Node n = (Node) it.next();
            System.out.print(n.state + " ");
        }
        System.out.println("");
    }
}
