package dataStructure;

import utils.Point3D;

public class Fruit {
	private double value;
	private int type;
	private Point3D point;
	private String pos;
	private edge_data edge;
	private boolean isVisited;

	public boolean isVisited() {
		return isVisited;
	}

	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
		getLocation();
	}

	public edge_data getEdge() {
		return edge;
	}

	public void setEdge(edge_data edge) {
		this.edge = edge;
	}

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
		this.edge = null;
		getLocation();
	}

	public String toString() {
		return "value: " + value + " type: " + type + " location " + point.toString();
	}

	public Point3D getLocation() {

		String[] location = this.pos.split(",");
		Point3D p = new Point3D(Double.parseDouble(location[0]), Double.parseDouble(location[1]),
				Double.parseDouble(location[2]));
		setLocation(p);

		return this.point;
	}

	public void setLocation(Point3D p) {
		double x = p.x();
		double y = p.y();
		double z = p.z();

		this.point = new Point3D(x, y, z);
	}
}
