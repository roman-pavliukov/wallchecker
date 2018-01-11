package wallchecker;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Main main = new Main();
        Scanner scanner = null;

        try {
            scanner = new Scanner(new FileReader(args[0]));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String[] wall_props = scanner.nextLine().split(" ");
        Wall wall = new Wall(Integer.valueOf(wall_props[0]), Integer.valueOf(wall_props[1]));

        int matrix_rows = wall.getHeight();
        int[][] matrix = new int[wall.getHeight()][wall.getWidth()];

        for (int i = 0; i < matrix_rows; i++) {
            String[] row = scanner.nextLine().split("");
            for (int r = 0; r < row.length; r++) {
                matrix[i][r] = Integer.valueOf(row[r]);
            }
        }
        wall.setMatrix(matrix);
        wall.printMatrix();
        ArrayList<Brick> bricks = new ArrayList();

        int kind_od_bricks = Integer.valueOf(scanner.nextLine());

        for (int i = 0; i < kind_od_bricks; i++) {
            String[] brick_line = scanner.nextLine().split(" ");
            for (int j = 0; j < Integer.valueOf(brick_line[2]); j++) {
                bricks.add(new Brick(Integer.valueOf(brick_line[0]), Integer.valueOf(brick_line[1])));
            }
        }

        bricks.sort(new Comparator<Brick>() {
            @Override
            public int compare(Brick brick, Brick t1) {
                return (brick.getWidth() + brick.getHeight()) - (t1.getHeight() + t1.getWidth());
            }
        });

        wall.calculateNeededBricksCount();
        bricks = wall.getUsefulBricks(bricks);
        main.createWall(wall, bricks);
    }

    public void createWall(Wall wall, ArrayList<Brick> bricks) {
        wall.printMatrix();
        System.out.println(" ");
        for (Brick brick : bricks) {
            if (!wall.getSquareForSize(brick.getWidth(), brick.getHeight()))
                wall.getSquareForSize(brick.getHeight(), brick.getWidth());
            wall.printMatrix();
            System.out.println(" ");
        }
        if (wall.isMatrixEmpty()) {
            System.out.println("yes");
        } else {
            System.out.println("no");
            wall.retriveMatrix();
        }
    }
}
