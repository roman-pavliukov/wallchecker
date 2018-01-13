package wallchecker;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;
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
		ArrayList<Brick> bricks = new ArrayList<Brick>();
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

		ListIterator<Brick> iterator = bricks.listIterator();
		Main main = new Main();
		LinkedList<Placeholder> placeholders = wall.getAllPlaceholdersForBrick(iterator.next());
		main.findPlace(iterator, placeholders, wall);
		
		LinkedList<Placeholder> test = wall.getAllPlaceholdersForBrick(new Brick(2, 1));
		wall.printMatrix();
		System.out.println(test.size());
		wall.printMatrix();
	}
	
	private void findPlace(ListIterator<Brick> iterator, LinkedList<Placeholder> placeholders, Wall wall) {

		for (Placeholder placeholder : placeholders) {
			if (iterator.hasNext()) {
				Brick brick = (Brick) iterator.next();
				wall.insert(placeholder);
				LinkedList<Placeholder> newPlaceholders = wall.getAllPlaceholdersForBrick(brick);
				if (newPlaceholders.isEmpty()) {
					placeholders.removeFirst();
					iterator.previous();
					findPlace(iterator, placeholders, wall);
				}
				if (!wall.isMatrixEmpty()) {
					findPlace(iterator, newPlaceholders, wall);
				} else {
					System.out.println("yes");
					return;
				}
			}
		}
	}

//	private void findPlace(ListIterator<Brick> iterator, Wall wall, int i) {
//
//		ArrayList<Placeholder> placeholders = null;
//		if (iterator.hasNext()) {
//			placeholders = wall.getAllPlaceholdersForBrick((Brick) iterator.next());
//			if (placeholders.isEmpty()) {
//				findPlace(iterator, wall, i);
//			} else {
//				wall.insert(placeholders.get(i));
//				findPlace(iterator, wall, i);
//			}
//		} else {
//			if (!wall.isMatrixEmpty()) {
//				iterator.previous();
//				findPlace(iterator, wall, ++i);
//			} else {
//				System.out.println("yes");
//				return;
//			}
//		}
//	}

	public void createWall(Wall wall, ArrayList<Brick> bricks) {

	}
}
