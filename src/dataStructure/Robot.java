package dataStructure;

import java.util.ArrayList;

import org.json.JSONObject;

import utils.Point3D;

public class Robot {
	private int id;
	private double value;
	private double value_p;
	private double speed;
	private int src, dest;
	private String pos;
	private Point3D point;
	private ArrayList<node_data> path;
	private String fruitKey;

	public Robot() {
		this.id = -1;
		this.value = 0;
		this.speed = 0;
		this.value_p = 0;
		this.src = 0;
		this.dest = 0;
		this.pos = "";
		this.point = null;
		this.path = null;
	}

	public double getValue_p() {
		return value_p;
	}

	public void setValue_p(double value_p) {
		this.value_p = value_p;
	}

	public Robot(int id, double value, double speed, int src, int dest, String pos) { // constructor
		this.src = src;
		this.dest = dest;
		this.value = value;
		this.value_p = value;
		this.id = id;
		this.pos = pos;
		this.speed = speed;
		path = new ArrayList<node_data>();
		getLocation();
	}
	// setters and getters :

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
		getLocation();
	}

	public Point3D getPoint() {
		return point;
	}

	public void setPoint(Point3D point) {
		this.point = point;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public ArrayList<node_data> getPath() {
		return path;
	}

	public void setPath(ArrayList<node_data> path) {
		this.path = path;
	}

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

	public String toString() {
		return "id: " + id + " value: " + value + "location: " + point.toString();
	}

	public void setFruitKey(String fru) {

		this.fruitKey = fru;
	}

	public String getFruitKey() {
		return fruitKey;
	}

}
