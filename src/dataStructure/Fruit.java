package dataStructure;

import utils.Point3D;

public class Fruit {
	private double value;
	private int type;
	private Point3D p;

	public Fruit(double value, int type, Point3D p) { // constructor
		this.value = value;
		this.type = type;
		this.p = p;
	}

	public String toString() {
		return "value: " + value + " type: " + type + " location " + p.toString();
	}
}
