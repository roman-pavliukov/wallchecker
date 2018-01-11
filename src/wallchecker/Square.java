package wallchecker;

public class Square {

    protected int width;
    protected int height;
    protected int posX;
    protected int posY;

    public Square(int width, int height) {
        this.height = height;
        this.width = width;
    }
    
    public Square(int width, int height, int posX, int posY) {
    	 this.height = height;
         this.width = width;
         this.posX = posX;
         this.posY = posY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void setHeight(int height) {
    	this.height = height;
    }
    
    public void setWidth(int width) {
    	this.width = width;
    }
    
    public int getSquare() {
    	return width * height;
    }
    
    public void setPosY(int posY) {
    	this.posY = posY;
    }
    
    public void setPosX(int posX) {
    	this.posX = posX;
    }
    
    public int getPosY() {
    	return this.posY;
    }
    
    public int getPosX() {
    	return this.posX;
    }
}
