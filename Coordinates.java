/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

public class Coordinates {
    private int location;
    private int gridSize;
    private int col;
    private int row;


    public Coordinates(int location, int gridSize) {
        this.location = location;
        this.gridSize = gridSize;
        this.col = calculateCol();
        this.row = calculateRow();
    }

    // given an integer and a grid size, calculates the row it is in
    private int calculateRow() {
        int row;
        row = location / gridSize; // integer division
        row = row + 1; // row + 1 as integer division will start at zero e.g. 4 / 5 = 0
        return row;
    }

    private int calculateCol() {
        int col;
        col = (location + 1) % gridSize; // remainder
        // If it divides exactly then it is in the final column i.e. the gridsize
        if (col == 0) {
            col = gridSize;
        }
        return col;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public static void main(String[] args) {
        Coordinates test;
        test = new Coordinates(5, 5);
        System.out.println(
                String.format(
                        "(%s, %s)",
                        test.calculateRow(),
                        test.calculateCol()
                )); // should be (2, 1)

        test = new Coordinates(19, 5);
        System.out.println(
                String.format(
                        "(%s, %s)",
                        test.calculateRow(),
                        test.calculateCol()
                )); // should be (1, 2)
        test = new Coordinates(4, 5);
        System.out.println(
                String.format(
                        "(%s, %s)",
                        test.calculateRow(),
                        test.calculateCol()
                )); // should be (1, 1)
        test = new Coordinates(12, 5);
        System.out.println(
                String.format(
                        "(%s, %s)",
                        test.calculateRow(),
                        test.calculateCol()
                )); // should be (3, 3)
    }
}
