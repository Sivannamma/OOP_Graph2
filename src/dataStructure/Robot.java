package dataStructure;

import utils.Point3D;

public class Robot {
	private int id;
	private double value;
	private double speed;
	private int src, dest;
	private Point3D p;

	public Robot(int id, double value, double speed, int src, int dest, Point3D p) { // constructor
		this.src = src;
		this.dest = dest;
		this.value = value;
		this.id = id;
		this.p = p;
		this.speed = speed;
	}
	// setters and getters :

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getSrc() {
		return src;
	}

	public void setSrc(int src) {
		this.src = src;
	}

	public int getDest() {
		return dest;
	}

	public void setDest(int dest) {
		this.dest = dest;
	}

	public Point3D getP() {
		return p;
	}

	public void setP(Point3D p) {
		this.p = p;
	}

	public int getId() {
		return id;
	}

	public double getSpeed() {
		return speed;
	}

	public String toString() {
		return "id: " + id + " value: " + value + "location: " + p.toString();
	}

}
