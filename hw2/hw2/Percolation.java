package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.HashSet;
import java.util.Set;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private boolean[] status;
    private int openSiteNum;
    private int N;
    private Set<Integer> top = new HashSet();
    private Set<Integer> bottom = new HashSet();

    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        uf = new WeightedQuickUnionUF(N * N);
        status = new boolean[N * N];
        for (int i = 0; i < N * N; i++) {
            status[i] = false;
        }
        this.N = N;
    }

    private int getPos(int row, int col) {
        return N * row + col;
    }

    public void open(int row, int col) {
        check(row, col);
        if (isOpen(row, col)) {
            return;
        }
        openSiteNum++;
        int pos = getPos(row, col);
        status[pos] = true;
        int[] colDir = {-1, 1, 0, 0};
        int[] rowDir = {0, 0, -1, 1};
        for (int i = 0; i < 4; i++) {
            int newRow = row + rowDir[i];
            int newCol = col + colDir[i];
            if (!(newRow >= 0 && newRow < N) || !(newCol >= 0 && newCol < N)) {
                continue;
            }
            int newPos = getPos(newRow, newCol);
            if (isOpen(newRow, newCol)) {
                uf.union(newPos, pos);
                if (newRow == N - 1) {
                    bottom.add(newPos);
                } else if (newRow == 0) {
                    top.add(newPos);
                }
            }
        }
        if (row == N - 1) {
            bottom.add(pos);
        } else if (row == 0) {
            top.add(pos);
        }
    }

    public boolean isOpen(int row, int col) {
        check(row, col);
        return status[getPos(row, col)];
    }

    public boolean isFull(int row, int col) {
        check(row, col);
        if (!isOpen(row, col)) {
            return false;
        }
        int pos = getPos(row, col);
        for (int t : top) {
            if (uf.connected(pos, t)) {
                return true;
            }
        }
        return false;
    }

    private void check(int row, int col) {
        if (!(row >= 0 && row < N) || !(col >= 0 && col < N)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    public int numberOfOpenSites() {
        return openSiteNum;
    }

    public boolean percolates() {
        for (int b : bottom) {
            for (int t : top) {
                if (uf.connected(b, t)) {
                    return true;
                }
            }
        }
        return false;
    }

//    public static void main(String[] args) {
//        Percolation system = new Percolation(4);
//        system.open(0,0);
//        system.open(1,0);
//        system.open(1,1);
//        system.open(2,1);
//        system.open(3,1);
//        System.out.println(system.percolates());
//    }

}
