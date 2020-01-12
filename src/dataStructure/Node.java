package dataStructure;

import utils.Point3D;
import java.io.Serializable;

public class Node implements node_data, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isFirst = true;
	private int id; // the name of the node
	private int info; // the node we came from - the parent
	private int tag; // variable that we know if we have been in some node (isVisited)
	private double weight; // the cost from one vertext to another
	private Point3D point;
	private static int NIL = -1;

	public Node(int id, Point3D point) {
		this.id = id;
		this.weight = Double.POSITIVE_INFINITY;
		this.tag = 0;
		this.info = NIL;
		setLocation(point);

		if (this.isFirst) {
			this.weight = 0;
			this.isFirst = false;
		}
	}

	public boolean equals(Node other) {
		if(other.getKey()!= this.getKey()) {
			return false;
		}
		if(other.getLocation().x()!=this.getLocation().x()) {
			return false;
		}
		
		if(other.getLocation().y()!=this.getLocation().y()) {
			return false;
		}
		return true;
	}
	public Node copy() { // copy function
		Node n = new Node(this.id, this.point);
		n.weight = this.weight;
		n.tag = this.tag;
		n.info = this.info;
		return n;
	}

	@Override
	public int getKey() {
		return this.id;
	}

	@Override
	public Point3D getLocation() {
		return this.point;

	}

	@Override
	public void setLocation(Point3D p) {
		double x = p.x();
		double y = p.y();
		double z = p.z();

		this.point = new Point3D(x, y, z);
	}

	@Override
	public double getWeight() {
		return this.weight;
	}

	@Override
	public void setWeight(double w) {
		this.weight = w;
	}

	@Override
	public String getInfo() {
		return String.valueOf(info);

	}

	@Override
	public void setInfo(String s) {
		this.info = Integer.valueOf(s);

	}

	@Override
	public int getTag() {
		return this.tag;
	}

	@Override
	public void setTag(int t) {
		this.tag = t;
	}

	public String toString() {
		return "id: " + id;
	}

}
