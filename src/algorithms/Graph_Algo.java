package algorithms;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.nio.file.Path;
import java.io.Serializable;
import java.io.*;
import dataStructure.DGraph;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;

/**
 * This empty class represents the set of graph-theory algorithms which should
 * be implemented as part of Ex2 - Do edit this class.
 * 
 * @author
 *
 */
public class Graph_Algo implements graph_algorithms, java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private graph current;
	private List<node_data> list;
	private double costAns = 0;

	public Graph_Algo(graph _graph) {
		init(_graph);
	}

	public Graph_Algo() {
		this.current = new DGraph();
	}

	@Override
	public void init(graph g) {
		this.current = g;
	}

	@Override
	public void init(String file_name) {

		try {
			FileInputStream file = new FileInputStream(file_name);
			ObjectInputStream in = new ObjectInputStream(file);

			this.current = (graph) in.readObject();

			in.close();
			file.close();
		}

		catch (IOException ex) {
			System.out.println("IOException is caught");
		}

		catch (ClassNotFoundException ex) {
			System.out.println("ClassNotFoundException is caught");
		}

	}

	@Override
	public void save(String file_name) {
		try {
			FileOutputStream file = new FileOutputStream(file_name);
			ObjectOutputStream out = new ObjectOutputStream(file);

			out.writeObject(this.current);
			
			out.close();
			file.close();

		} catch (IOException ex) {
			System.out.println("IOException is caught");
		}
	}

	@Override
	public boolean isConnected() {
		boolean nei = false;
		for (node_data key : this.current.getV()) { // itearting through the hashmap
			Collection<edge_data> col = this.current.getE(key.getKey()); // creating collection on neighbors of the
			// specific node
			for (node_data path : this.current.getV()) { // itearting again on nodes
				if (path != key) { // if we are not on the same node
					for (edge_data e : col) {

						if ((e.getDest() == (path.getKey()))) { // if its a nei- no need to search a path
							nei = true;
							break;
						}
					}
					if (!nei) {// if path is not neighbor of key, go find a path
						initVisited(); // initializing the tag to be not visited- working on the same graph
						list = null;
						if (shortestPathDist(key.getKey(), path.getKey()) == Double.POSITIVE_INFINITY) {
							return false;
						}
					}
				}
				nei = false;
			}
		}
		return true;
	}

	private void initVisited() {
		for (node_data current : this.current.getV()) {
			current.setTag(0);
		}
	}

	public graph getGraph() {
		return this.current;
	}

	// this function checks if we visited all the nodes in the graph,
	// once we found a place we didnt visit we return 0
	private boolean isVisited() {
		for (node_data key : this.current.getV()) {
			if (key.getTag() == 0)
				return true;
		}
		return false;
	}

	//
	private double sumPath() {
		if (list == null || list.isEmpty()) {
			return Double.POSITIVE_INFINITY;
		}
		double ans = 0;

		for (int j = list.size() - 1; j > 0; j--) {
			// getting the edges that are connected to the node_data in place j of the list
			Collection<edge_data> col = this.current.getE(list.get(j).getKey());
			for (edge_data e : col) { // iterating through the neighboors of this current node
				if (e.getDest() == list.get(j - 1).getKey())
					ans += e.getWeight();
			}
		}
		return ans;
	}

	// this function returns the next node with the minimum weight- the next node to
	// be dealing with
	private Integer isNextMin() {
		node_data ans = null;
		for (node_data key : this.current.getV()) {
			if (key.getTag() == 0 && key.getWeight() <= costAns) {
				costAns = key.getWeight();
				ans = key;
			}
		}

		this.current.getNode(ans.getKey()).setTag(1); // updating to visitedl
		for (node_data key : this.current.getV()) {
			if (key.getTag() == 0) {
				costAns = key.getWeight();
				break;
			}
		}
		return ans.getKey();
	}

	// we initialize the src weight to be 0, and the others to be infinity
	private void setWeight(int src) {
		for (node_data key : this.current.getV()) {
			if (key.getKey() != src)
				key.setWeight(Double.POSITIVE_INFINITY);
			else
				key.setWeight(0);
		}
	}

	private boolean isContained(int id) {
		for (node_data key : this.current.getV()) {
			if (key.getKey() == id)
				return true;
		}
		return false;

	}

	@Override
	public double shortestPathDist(int src, int dest) {
		list = null;
		shortestPath(src, dest);
		return sumPath();
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		initVisited();
		if (!isContained(src) || !isContained(dest)) {
			list = null;
			return list;
		}

		Collection<edge_data> nei = this.current.getE(src);
		if (nei.isEmpty())
			return null;

		setWeight(src); // setting the start point
		// while we have more nodes to go to- inner function
		while (isVisited()) {
			Integer id = isNextMin();

			Collection<edge_data> col = this.current.getE(id);
			for (edge_data e : col) { // iterating through the neighboors of this current node
				// setting the value of the node.src+the sum of the edge
				double check = this.current.getNode(e.getSrc()).getWeight() + e.getWeight();
				// checking if the value of check is lower --> updating the new sum
				if (check < this.current.getNode(e.getDest()).getWeight()) {
					this.current.getNode(e.getDest()).setWeight(check);// setting the new cheap weight
					this.current.getNode(e.getDest()).setInfo(String.valueOf(e.getSrc()));// setting the predesscor of
					// this node
				}
			}

		}

		list = new ArrayList<node_data>();

		node_data temp = this.current.getNode(dest);
		while (temp != this.current.getNode(src)) {
			list.add(temp);
			if (temp.getInfo().equals("-1")) {
				list.clear();
				return list;
			}
			temp = this.current.getNode(Integer.parseInt(temp.getInfo()));
		}
		list.add(temp);
		// reverse(list);
		return (list);
	}

	@Override
	public List<node_data> TSP(List<Integer> targets) {
		boolean flag = true;
		// first checking that all the keys in the targets list are contains in the
		// graph.nodes
		for (int key = targets.size() - 1; key >= 0; key--) {
			if (!isContained(targets.get(key))) // once a key in not contained - return null - there isnt a path
				return null;
		}
		List<node_data> path = new ArrayList<node_data>(); // creating new list

		for (int i = 0; i < targets.size() - 1; i++) {
			if (targets.get(i) == targets.get(i + 1)) {
				continue;
			}
			initVisited();
			List<node_data> temp = shortestPath(targets.get(i), targets.get(i + 1));
			// if one time we couldnt find a path there is no TSP
			if (temp != null) { // if there is a path between these 2 nodes
				reverse(temp);
				if (i > 0 && temp.size() > 0) { // if its not the first concat of the paths
					temp.remove(0);
				}
				path.addAll(temp);
			} else { // if there is no path, possible to happen once, move this node to last.
				if (flag) {
					int save = targets.get(i);
					targets.remove(0);
					targets.add(save);
					flag = false;
					i--;
				} else
					return null;
			}
		}

		return path;

	}

	public void reverse(List<node_data> temp) {
		if (temp == null) {
			return;
		}
		int i = 0;
		int j = temp.size() - 1;
		node_data num;
		while (i <= j) {
			num = temp.get(i);
			temp.add(i, temp.get(j));
			temp.remove(i + 1);
			temp.add(j, num);
			temp.remove(j + 1);
			i++;
			j--;

		}

	}

	@Override
	public graph copy() {

		graph g = new DGraph();
		Collection<node_data> col = this.current.getV();

		// for the main hashmap.
		for (node_data n : col) {
			node_data newNode = new Node(n.getKey(), n.getLocation());
			g.addNode(newNode);

		}
		for (node_data n : col) {
			Collection<edge_data> nei = this.current.getE(n.getKey());
			for (edge_data e : nei) {
				g.connect(e.getSrc(), e.getDest(), e.getWeight());
			}
		}
		return g;
	}

	public List<node_data> getList() {
		return this.list;
	}

}
