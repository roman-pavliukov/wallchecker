package wallchecker;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
    	
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
        //wall.printMatrix();
        ArrayList<Brick> bricks = new ArrayList<Brick>();

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
                return brick.getSquare() - t1.getSquare();
            }
        });
        
        for (Brick brick : bricks) {
        	System.out.println(brick.getSquare());
        }
        
        ArrayList<Placeholder> placeholders = wall.getAllPlaceholdersForBrick(bricks.get(2));
        
        for (Placeholder placeholder : placeholders) {
        	System.out.println("x:" + placeholder.getPosX() + " y:" + placeholder.getPosY() + " width:" + placeholder.getWidth() + " height:" + placeholder.getHeight());
        }
        System.out.println(placeholders.size());

        wall.calculateNeededBricksCount();
    }

    public void createWall(Wall wall, ArrayList<Brick> bricks) {
       
    }
}
