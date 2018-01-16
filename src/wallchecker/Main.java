package wallchecker;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.LinkedList;
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
		LinkedList<Brick> bricks = new LinkedList<Brick>();
		int kind_od_bricks = Integer.valueOf(scanner.nextLine());

		for (int i = 0; i < kind_od_bricks; i++) {
			String[] brick_line = scanner.nextLine().split(" ");
			for (int j = 0; j < Integer.valueOf(brick_line[2]); j++)
				bricks.add(new Brick(Integer.valueOf(brick_line[0]), Integer.valueOf(brick_line[1])));
		}

		bricks.sort(new Comparator<Brick>() {
			@Override
			public int compare(Brick brick, Brick t1) {
				return t1.getSquare() - brick.getSquare();
			}
		});
		
		Main main = new Main();
		main.findPlace(bricks, wall);
		System.out.println("no");
		wall.printMatrix();
	}

	private void findPlace(LinkedList<Brick> bricks, Wall wall) {
		if (bricks.isEmpty()) {
			return;
		}
		Brick brick = bricks.getFirst();
		LinkedList<Placeholder> placeholders = wall.getAllPlaceholdersForBrick(brick);
		wall.printMatrix();
		for (Placeholder placeholder : placeholders) {
			wall.insert(placeholder);
			bricks.removeFirst();
			if (wall.isMatrixEmpty()) {
				wall.printMatrix();
				System.out.println("success");
				System.exit(0);
			} else {
				findPlace(bricks, wall);
			}
			wall.takeOut(placeholder);
			bricks.addFirst(brick);
		}
	}
}
