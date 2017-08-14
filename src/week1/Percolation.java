import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private final int top;
    private final int bottom;
    private final boolean[][] grid;
    private final WeightedQuickUnionUF tbUf;
    private final WeightedQuickUnionUF tUf;

    private int opened = 0;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n)   {
        if (n <= 0) throw new IllegalArgumentException("Grid dimension can not be less or equal to zero");

        this.n = n;
        this.top = 0;
        this.bottom = n * n + 1;
        this.grid = new boolean[n][n];
        this.tbUf = new WeightedQuickUnionUF(n * n + 2); // 0, 1, ..., n * n, n * n + 1
        this.tUf = new WeightedQuickUnionUF(n * n + 2);
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        checkRange(row, col);

        if (isOpen(row, col))
            return;

        openCell(row, col);
        opened++;

        if (row == 1)
            unionWithTop(row, col);
        else if (row - 1 >= 1 && isOpen(row - 1, col))
            union(row, col, row - 1, col);

        if (col - 1 >= 1 && isOpen(row, col - 1))
            union(row, col, row, col - 1);

        if (col + 1 <= n && isOpen(row, col + 1))
            union(row, col, row, col + 1);

        // Avoid backwash: connect with virtual bottom only if there already exists connection with top
        if (row == n)
            unionWithBottom(row, col);
        else if (row + 1 <= n && isOpen(row + 1, col))
            union(row, col, row + 1, col);
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkRange(row, col);

        return readCell(row, col);
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        checkRange(row, col);

        return isOpen(row, col) && isConnectedWithTop(row, col);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return opened;
    }

    // does the system percolate?
    public boolean percolates()  {
        return tbUf.connected(top, bottom);
    }

    private boolean isConnectedWithTop(int row, int col) {
        return tUf.connected(cellToIdx(row, col), top);
    }

    private void union(int row1, int col1, int row2, int col2) {
        tbUf.union(cellToIdx(row1, col1), cellToIdx(row2, col2));
        tUf.union(cellToIdx(row1, col1), cellToIdx(row2, col2));
    }

    private void unionWithTop(int row1, int col1) {
        tbUf.union(cellToIdx(row1, col1), top);
        tUf.union(cellToIdx(row1, col1), top);
    }

    private void unionWithBottom(int row1, int col1) {
        tbUf.union(cellToIdx(row1, col1), bottom);
    }

    private boolean readCell(int row, int col) {
        return grid[row - 1][col - 1];
    }

    private void openCell(int row, int col) {
        grid[row - 1][col - 1] = true;
    }

    private int cellToIdx(int row, int col) {
        return (row - 1) * n + (col - 1) + 1;
    }

    private void checkRange(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n)
            throw new IllegalArgumentException("Cell (" + row + ", " + col + ") is outside grid with dimension " + n);
    }

    public static void main(String[] args) {
        // write your code here
    }
}
