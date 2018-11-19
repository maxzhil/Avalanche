package model;

import java.awt.Dimension;
import java.awt.Point;

public class GameObject {
	private Point location;
	private Dimension dimension;

	public GameObject() {
	}

	public GameObject(Point location, Dimension dimension) {
		this.location = location;
		this.dimension = dimension;
	}

	public void changeY(int value) {
		location.y -= value;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public Point getLocation() {
		return location;
	}
}
