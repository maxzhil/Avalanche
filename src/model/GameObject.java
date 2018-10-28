package model;

public class GameObject {
	private int x;
	private int y;
	private int width;
	private int height;

	public GameObject() {
	}

	public GameObject(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void changeY(int value) {
		/*for (int i = 0; i < Math.abs(value); i++) {
			if (value < 0) {
				this.y += i;
			} else {
				this.y -= i;
			}
		}*/
		 this.y -= value;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
