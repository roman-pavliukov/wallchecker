package wallchecker;

public class Brick extends Square {

    public Brick(int width, int height) {
        super(width, height);
    }

    public int getScores() {
        return this.height * this.width;
    }
}
