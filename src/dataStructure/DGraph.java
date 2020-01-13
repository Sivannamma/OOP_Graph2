package dataStructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.io.Serializable;

import com.google.gson.Gson;

public class DGraph implements graph, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<Integer, node_data> map;
	private HashMap<node_data, HashMap<Integer, edge_data>> neighboors;
	private int MC;
	private int EdgeCount;
	private ArrayList<Edge> Edges;
	private ArrayList<Node> Nodes;

	public DGraph(String g) {
		this();
		Gson gson = new Gson();
		DGraph graph = gson.fromJson(g, DGraph.class);
		setGraph(graph);

	}

// initializing the variable according to the parameters given from json
	private void setGraph(DGraph graph) {
		// adding nodes to the main hash
		for (int i = 0; i < graph.Nodes.size(); i++) {
			this.addNode(graph.Nodes.get(i));
		}
		// adding the edges to the neighboors hash
		for (int i = 0; i < graph.Edges.size(); i++) {
			this.connect(graph.Edges.get(i).getSrc(), graph.Edges.get(i).getDest(), graph.Edges.get(i).getWeight());
		}
	}

	public DGraph() {
		this.map = new HashMap<Integer, node_data>();
		this.neighboors = new HashMap<node_data, HashMap<Integer, edge_data>>();
		MC = 0;
		EdgeCount = 0;
	}

	@Override
	public node_data getNode(int key) {
		return map.get(key);
	}

	@Override
	public edge_data getEdge(int src, int dest) {
		return this.neighboors.get(this.map.get(src)).get(dest);
	}

	@Override
	public void addNode(node_data n) {
		if (!this.map.containsKey(n.getKey())) {
			this.map.put(n.getKey(), n);
			// adding to the neighboors hashmap, initializing the inner hashmap
			this.neighboors.put(n, new HashMap<Integer, edge_data>());
			MC++; // because we performed a change in the graph
		}
	}

	@Override
	public void connect(int src, int dest, double w) {
		if (w < 0) // cant connect a edge with weight that is negative
			return;
		if (src == dest) // if the src and dest are equals we arent connecting an edge
			return;
		if (!this.map.containsKey(src) || !this.map.containsKey(dest))
			return;
		if (!(this.neighboors.get(this.map.get(src)).containsKey(dest)))
			this.EdgeCount++;
		this.neighboors.get(this.map.get(src)).put(dest, new Edge(src, dest, w));
		MC++; // because we performed a change in the graph
	}

	@Override
	public Collection<node_data> getV() {
		return this.map.values();
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		return this.neighboors.get(this.map.get(node_id)).values();
	}

	@Override
	public node_data removeNode(int key) {
		MC++; // because we performed a change in the graph
		deleteEdgeFromNei(key); // deleting all the edges where the sc is the dest from certain node
		return this.map.remove(key);
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		edge_data edge = this.neighboors.get(this.map.get(src)).get(dest);
		if (edge != null) {
			this.neighboors.get(this.map.get(src)).remove(dest);
			MC++; // because we performed a change in the graph
			this.EdgeCount--;
		}
		return edge;
	}

	private void deleteEdgeFromNei(int dest) {
		for (node_data n : this.getV()) {
			if (this.getE(n.getKey()) != null) {
				for (edge_data e : this.getE(n.getKey())) {
					if (e.getDest() == dest) {
						this.neighboors.get(n).remove(dest);
						break;
					}
				}
			}
		}
	}

	@Override
	public int nodeSize() {
		return this.map.size();
	}

	@Override
	public int edgeSize() {
		return this.EdgeCount;
	}

	@Override
	public int getMC() {
		return this.MC;
	}

	public HashMap<Integer, node_data> getMap() {
		return this.map;
	}

	public HashMap<node_data, HashMap<Integer, edge_data>> getNei() {
		return this.neighboors;
	}

	public String toString() {
		String ans = "";

		// Iterating over keys and using it to get the data from our hashmaps data
		// structure
		for (Integer key : this.map.keySet()) {
			ans += this.map.get(key).toString() + " ,neighboors: " + this.neighboors.get(this.map.get(key)).toString()
					+ " ";
			ans += "\n";
		}
		return ans;
	}

}
