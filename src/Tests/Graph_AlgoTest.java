package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import algorithms.Graph_Algo;
import algorithms.graph_algorithms;
import dataStructure.DGraph;
import dataStructure.Node;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

class Graph_AlgoTest {

	@Test
	void testInitString() {
		Graph_Algo temp = new Graph_Algo();
		try {
			temp.init("myGraph.txt");
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void testSave() {
		graph d = new DGraph();
		for (int i = 1; i < 10; i++) {
			Node temp = new Node(i, new Point3D(i + 100, i + 50));
			d.addNode(temp);
		}
		for (int i = 1; i < 9; i++) {
			d.connect(i, i + 1, i);
		}
		d.connect(1, 8, 4);
		d.connect(8, 6, 2);
		d.connect(9, 1, 2);
		Graph_Algo temp = new Graph_Algo(d);
		try {
			temp.save("TestingAlgoSave.txt");
			temp.init("TestingAlgoSave.txt");
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void testIsConnected() {
		graph d = new DGraph();
		for (int i = 1; i < 10; i++) {
			Node temp = new Node(i, new Point3D(i + 100, i + 50));
			d.addNode(temp);
		}
		for (int i = 1; i < 9; i++) {
			d.connect(i, i + 1, i);
		}
		d.connect(1, 8, 4);
		d.connect(8, 6, 2);
		d.connect(9, 1, 2);
		Graph_Algo temp = new Graph_Algo(d);
		assertEquals(temp.isConnected(), true);
	}

	@Test
	void testShortestPathDist() {
		graph d = new DGraph();
		for (int i = 1; i < 10; i++) {
			Node temp = new Node(i, new Point3D(i + 100, i + 50));
			d.addNode(temp);
		}
		for (int i = 1; i < 9; i++) {
			d.connect(i, i + 1, i);
		}
		d.connect(1, 8, 4);
		d.connect(8, 6, 2);
		Graph_Algo temp = new Graph_Algo(d);
		temp.shortestPathDist(1, 7);
		if (temp.shortestPathDist(1, 7) != 12)
			fail();
	}

	@Test
	void testShortestPath() {
		graph d = new DGraph();
		for (int i = 1; i < 10; i++) {
			Node temp = new Node(i, new Point3D(i + 100, i + 50));
			d.addNode(temp);
		}
		for (int i = 1; i < 9; i++) {
			d.connect(i, i + 1, i);
		}
		d.connect(1, 8, 4);
		d.connect(8, 6, 2);
		Graph_Algo temp = new Graph_Algo(d);
		ArrayList<node_data> list = new ArrayList<node_data>();
		list.add(new Node(1, new Point3D(1 + 100, 1 + 50)));
		list.add(new Node(8, new Point3D(8 + 100, 8 + 50)));
		list.add(new Node(6, new Point3D(6 + 100, 6 + 50)));
		list.add(new Node(7, new Point3D(7 + 100, 7 + 50)));
		temp.shortestPath(1, 7);
		temp.reverse(temp.getList());
		if (list.size() != temp.getList().size())
			fail();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getKey() != temp.getList().get(i).getKey())
				fail();
		}
	}

	@Test
	void testTSP() {
		graph d = new DGraph();
		for (int i = 1; i < 10; i++) {
			Node temp = new Node(i, new Point3D(i + 100, i + 50));
			d.addNode(temp);
		}
		for (int i = 1; i < 9; i++) {
			d.connect(i, i + 1, i);
		}
		d.connect(9, 3, 0);
		d.connect(8, 1, 0);
		Graph_Algo temp = new Graph_Algo(d);
		ArrayList<Integer> tar = new ArrayList<Integer>();
		tar.add(1);
		tar.add(6);
		tar.add(9);
		tar.add(3);
		tar.add(8);
		tar.add(2);

		List<node_data> current = temp.TSP(tar);
		ArrayList<node_data> list = new ArrayList<node_data>();
		list.add(new Node(1, new Point3D(1 + 100, 1 + 50)));
		list.add(new Node(2, new Point3D(8 + 100, 8 + 50)));
		list.add(new Node(3, new Point3D(6 + 100, 6 + 50)));
		list.add(new Node(4, new Point3D(7 + 100, 7 + 50)));
		list.add(new Node(5, new Point3D(7 + 100, 7 + 50)));
		list.add(new Node(6, new Point3D(7 + 100, 7 + 50)));
		list.add(new Node(7, new Point3D(7 + 100, 7 + 50)));
		list.add(new Node(8, new Point3D(7 + 100, 7 + 50)));
		list.add(new Node(9, new Point3D(7 + 100, 7 + 50)));
		list.add(new Node(3, new Point3D(6 + 100, 6 + 50)));
		list.add(new Node(4, new Point3D(7 + 100, 7 + 50)));
		list.add(new Node(5, new Point3D(7 + 100, 7 + 50)));
		list.add(new Node(6, new Point3D(7 + 100, 7 + 50)));
		list.add(new Node(7, new Point3D(7 + 100, 7 + 50)));
		list.add(new Node(8, new Point3D(7 + 100, 7 + 50)));
		list.add(new Node(1, new Point3D(1 + 100, 1 + 50)));
		list.add(new Node(2, new Point3D(8 + 100, 8 + 50)));
		if (list.size() != current.size())
			fail();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getKey() != current.get(i).getKey())
				fail();
		}

	}

	@Test
	void testCopy() {
		graph d = new DGraph();
		for (int i = 1; i < 10; i++) {
			Node temp = new Node(i, new Point3D(i + 100, i + 50));
			d.addNode(temp);
		}
		Graph_Algo d2 = new Graph_Algo(d);
		graph temp = d2.copy();
		Iterator<node_data> iter = temp.getV().iterator();
		for(node_data node : d.getV()) {
			if(node.getKey()!=iter.next().getKey()) {
				fail();
			}
		}
	}

}
