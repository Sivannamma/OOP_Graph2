package dataStructure;

import utils.Point3D;

public class Robot {
	private int id;
	private double value;
	private double speed;
	private int src, dest;
	private String pos;
	private Point3D point;

	public Robot(int id, double value, double speed, int src, int dest, String pos) { // constructor
		this.src = src;
		this.dest = dest;
		this.value = value;
		this.id = id;
		this.pos = pos;
		this.speed = speed;
		getLocation();
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

	public int getId() {
		return id;
	}

	public double getSpeed() {
		return speed;
	}

	public Point3D getLocation() {
		if (this.point == null) {
			String[] location = this.pos.split(",");
			Point3D p = new Point3D(Double.parseDouble(location[0]), Double.parseDouble(location[1]),
					Double.parseDouble(location[2]));
			setLocation(p);
		}
		return this.point;
	}

	public void setLocation(Point3D p) {
		double x = p.x();
		double y = p.y();
		double z = p.z();

		this.point = new Point3D(x, y, z);
	}

	public String toString() {
		return "id: " + id + " value: " + value + "location: " + point.toString();
	}

}
