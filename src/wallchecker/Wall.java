package wallchecker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class Wall {

    private int width;
    private int height;
    private int[][] matrix;
    private int neededBricksCount;

    public Wall(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int isFilled(int posy, int posx) {
        return matrix[posy][posx];
    }

    public void calculateNeededBricksCount() {
        int count = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (matrix[row][col] == 1) {
                    count++;
                }
            }
        }

        this.neededBricksCount = count;
    }

    public int getNeededBricksCount() {
        return neededBricksCount;
    }

    public ArrayList<Brick> getUsefulBricks(ArrayList<Brick> bricks) {

        ArrayList<Brick> usefulBricks = new ArrayList<>();
        int need = this.neededBricksCount;
        int current = 0;
        int excess_scores = 0;

        for (Brick brick : bricks) {
            if (current < need) {
                current += brick.getScores();
                usefulBricks.add(brick);
            }
        }

        usefulBricks.sort(new Comparator<Brick>() {
            @Override
            public int compare(Brick brick, Brick t1) {
                return (t1.getHeight() + t1.getWidth()) - (brick.getWidth() + brick.getHeight());
            }
        });

        excess_scores = current - need;

        if (excess_scores > 0) {
            Iterator iterator = usefulBricks.listIterator();

            while (iterator.hasNext()) {
                Brick useful_brick = (Brick) iterator.next();
                if (useful_brick.getScores() <= excess_scores) {
                    excess_scores -= useful_brick.getScores();
                    iterator.remove();
                }
            }
        }

        return usefulBricks;
    }

    public boolean getSquareForSize(int width, int height) {

        int current_width = 0;

        for (int row = 0; row < this.height; row++) {
            current_width = 0;
            for (int col = 0; col < this.width; col++) {
                if (matrix[row][col] == 1) {
                    current_width++;
                    if (current_width == width) {
//                        System.out.println(row + " " + (col - width + 1) +" "+width+" "+height);
                        if (checkHeight(row, col - width + 1, width, height)) {
                            //Probably will be changed:
                            bindBrickToPlace(row, col - width + 1, width, height);
                            return true;
                        }
                    }
                } else {
                    current_width = 0;
                }
            }
        }
        return false;
    }

    private boolean checkHeight(int row, int col, int width, int height) {

        try {
            for (int r = row; r < row + height; r++) {
                for (int c = col; c < col + width; c++) {
                    if (matrix[r][c] != 1) {
                        return false;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {return false;}

        return true;
    }

    private void bindBrickToPlace(int row, int col, int width, int height) {
        for (int r = row; r < row + height; r++) {
            for (int c = col; c < col + width; c++) {
//                System.out.println("R & C:" + r +" " + c);
                matrix[r][c] = 2;
            }
        }
    }

    public void printMatrix() {
        for (int row = 0; row < this.height; row++) {
            String row_matrix = "";
            for (int col = 0; col < this.width; col++) {
                row_matrix += matrix[row][col];
            }
            System.out.println(row_matrix);
        }
    }

    public boolean isMatrixEmpty() {
        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                if (matrix[row][col] == 1)
                    return false;
            }
        }
        return true;
    }

    public void retriveMatrix() {
        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                if (matrix[row][col] == 2)
                    matrix[row][col] = 1;
            }
        }
    }

}
