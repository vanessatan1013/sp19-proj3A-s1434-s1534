package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int dim;
    private int openSites;
    private WeightedQuickUnionUF u;
    private WeightedQuickUnionUF b;
    private int virtualTop;
    private int virtualBottom;

    public Percolation(int N) {
        if (N < 0) {
            throw new java.lang.IllegalArgumentException("Not a valid size.");
        }
        dim = N;
        grid = new boolean[N][N];
        openSites = 0;
        u = new WeightedQuickUnionUF((dim * dim) + 2);
        b = new WeightedQuickUnionUF((dim * dim) + 2);
        for (int i = 0; i < dim; i++) {
            virtualTop = dim * dim;
            virtualBottom = virtualTop + 1;
            int firstRow = xyToNum(0, i);
            u.union(virtualTop, firstRow);
            b.union(virtualTop, firstRow);
            int lastRow = xyToNum(dim - 1, i);
            u.union(virtualBottom, lastRow);
            for (int j = 0; j < dim; j++) {
                grid[i][j] = false;
            }
        }
    }

    private int xyToNum(int row, int col) {
        return (dim * row) + col;
    }

    private boolean validEntry(int row, int col) {
        return (row >= 0 && col >= 0 && row <= dim - 1 && col <= dim - 1);
    }

    private void validate(int row, int col) {
        if (row < 0 || col < 0 || row > dim - 1 || col > dim - 1) {
            throw new java.lang.IndexOutOfBoundsException("Index out of bounds.");
        }
    }

    public void open(int row, int col) {
        validate(row, col);
        int pos = xyToNum(row, col);
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            openSites++;
        }

        for (int i = -1; i < 2; i += 2) {
            if (validEntry(row + i, col)) {
                if (isOpen(row + i, col)) {
                    int neighbor = xyToNum(row + i, col);
                    u.union(neighbor, pos);
                    b.union(neighbor, pos);
                }
            }
        }

        for (int j = -1; j < 2; j += 2) {
            if (validEntry(row, col + j)) {
                if (isOpen(row, col + j)) {
                    int neighbor = xyToNum(row, col + j);
                    u.union(neighbor, pos);
                    b.union(neighbor, pos);
                }
            }
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        int pos = xyToNum(row, col);
        return isOpen(row, col) && b.connected(pos, virtualTop);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        if (dim < 2) {
            return u.connected(virtualTop, virtualBottom) && isOpen(0, 0);
        }
        return u.connected(virtualTop, virtualBottom);
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(1);
        System.out.println(p.percolates());
    }
}
