package wallchecker;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

import javax.print.attribute.ResolutionSyntax;

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
		Brick brick = bricks.removeFirst();
		LinkedList<Placeholder> placeholders = wall.getAllPlaceholdersForBrick(brick);
		main.findPlace(bricks, placeholders, wall, brick);
		System.out.println("no");
		wall.printMatrix();
	}

	private void findPlace(LinkedList<Brick> bricks, LinkedList<Placeholder> placeholders, Wall wall, Brick lastBrick) {

		for (Placeholder placeholder : placeholders) {
			boolean takedOut = false;
			wall.insert(placeholder);
			wall.printMatrix();
			System.out.println("------");
			if (!wall.isMatrixEmpty()) {
				if (!bricks.isEmpty()) {
					Brick nextBrick = bricks.removeFirst();
					LinkedList<Placeholder> newPlaceholders = wall.getAllPlaceholdersForBrick(nextBrick);
					if (newPlaceholders.isEmpty()) {
						wall.takeOut(placeholder);
						bricks.addFirst(lastBrick);
						takedOut = true;
						continue;
					}
					findPlace(bricks, newPlaceholders, wall, lastBrick);
				} else {
					wall.takeOut(placeholder);
					takedOut = true;
					bricks.addFirst(lastBrick);
					return;
				}
			} else {
				System.out.println("yes");
				wall.printMatrix();
				System.exit(0);
			}
			if (!takedOut)
				wall.takeOut(placeholder);
		}
	}
}
