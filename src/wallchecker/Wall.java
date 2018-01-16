package wallchecker;

import java.util.LinkedList;

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
    
    public LinkedList<Placeholder> getAllPlaceholdersForBrick(Brick brick) {
    	LinkedList<Placeholder> placeholders = getPlaceholdersForBrick(brick);
    	
    	if (brick.getWidth() == brick.getHeight())
    		return placeholders;
    	
    	int height = brick.getHeight();
    	brick.setHeight(brick.getWidth());
    	brick.setWidth(height);
    	placeholders.addAll(getPlaceholdersForBrick(brick));
    	return placeholders;
    }

    private LinkedList<Placeholder> getPlaceholdersForBrick(Brick brick) {
    	
    	int width = brick.getWidth();
    	int height = brick.getHeight();
        int current_width = 0;
        LinkedList<Placeholder> placeholders = new LinkedList<>();
        int current_placeholders_capacity = -1;

        while (current_placeholders_capacity != placeholders.size()) {
        	current_placeholders_capacity = placeholders.size();
	        for (int row = 0; row < this.height; row++) {
	            current_width = 0;
	            for (int col = 0; col < this.width; col++) {
	            	if (checkPlaceholder(col, row, placeholders)) {
	            		current_width = 0;
	            		continue;
	            	}
	                if (matrix[row][col] == 1) {
	                    current_width++;
	                    if (current_width == width) {
	                        if (checkHeight(row, col - width + 1, width, height)) {
	                        	placeholders.add(new Placeholder(width, height, col - width + 1, row));
	                        }
	                    }
	                } else {
	                    current_width = 0;
	                }
	            }
	        }
        }
        return placeholders;
    }
    
    private boolean checkPlaceholder(int x, int y, LinkedList<Placeholder> placeholders) {
    	for (Placeholder placeholder : placeholders) {
    		if ((placeholder.getPosX() == x) && (placeholder.getPosY() == y))
    			return true;
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

    public void insert(Square square) {
    	int x = square.getPosX();
    	int y = square.getPosY();
    	int width = square.getWidth();
    	int height = square.getHeight();
    	
        for (int r = y; r < y + height; r++) {
            for (int c = x; c < x + width; c++) {
                matrix[r][c] = square.getSquare()+1;
            }
        }
    }
    
    public void takeOut(Square square) {
    	int x = square.getPosX();
    	int y = square.getPosY();
    	int width = square.getWidth();
    	int height = square.getHeight();
    	
        for (int r = y; r < y + height; r++) {
            for (int c = x; c < x + width; c++) {
                matrix[r][c] = 1;
            }
        }
    }

    public void printMatrix() {
        for (int row = 0; row < this.height; row++) {
            String row_matrix = "";
            for (int col = 0; col < this.width; col++) {
                row_matrix += matrix[row][col];
            }
            System.err.println(row_matrix);
        }
        System.err.println("------------------------------");
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

    public void retrieveMatrix() {
        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                if (matrix[row][col] == 2)
                    matrix[row][col] = 1;
            }
        }
    }

}
