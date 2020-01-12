package dataStructure;

import java.util.HashMap;
import java.io.Serializable;

public class Edge implements edge_data, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int src; // where the edge comes from
	private int dest; // where the edge ends
	private double w;

	public Edge(int src, int dst, double cost) {
		if (src >= 0 && dst > 0 && cost >= 0) {
			this.src = src;
			this.dest = dst;
			this.w = cost;
		}
	
	}

	public void setCost(double cost) {
		this.w = cost;
	}

	@Override
	public int getSrc() {
		return this.src;
	}

	@Override
	public int getDest() {
		return this.dest;
	}

	@Override
	public double getWeight() {
		return this.w;
	}

	@Override
	public String getInfo() {
		return null;
	}

	@Override
	public void setInfo(String s) {
		return;
	}

	@Override
	public int getTag() {
		return 0;
	}

	@Override
	public void setTag(int t) {
		return;
	}

	public String toString() {
		return "[" + this.src + "-->" + this.w + "-->" + this.dest + "]";
	}

}
