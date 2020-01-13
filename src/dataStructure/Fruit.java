package dataStructure;

import java.util.ArrayList;
import java.util.List;

import utils.Point3D;

public class Fruit {
	private double value;
	private int type;
	private Point3D point;
	String pos;

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Point3D getPoint() {
		return point;
	}

	public void setPoint(Point3D point) {
		this.point = point;
	}

	public Fruit(double value, int type, String pos) { // constructor
		this.value = value;
		this.type = type;
		this.pos = pos;
		getLocation();
	}

	public String toString() {
		return "value: " + value + " type: " + type + " location " + point.toString();
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
}
