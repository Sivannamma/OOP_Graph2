package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.EOFException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.Node;
import dataStructure.node_data;
import utils.Point3D;

class DGraphTest {

	@Test
	void testGetNode() {
		DGraph d = new DGraph();

		for (int i = 1; i < 10; i++) {
			Node temp = new Node(i, new Point3D(i + 100, i + 50));
			d.addNode(temp);
			assertEquals(temp, d.getNode(i));
		}
	}

	@Test
	void testGetEdge() {
		DGraph d = new DGraph();
		for (int i = 1; i < 10; i++) {
			Node temp = new Node(i, new Point3D(i + 100, i + 50));
			d.addNode(temp);
		}
		for (int i = 1; i < 9; i++) {
			d.connect(i, i + 1, i);
		}

	}

	@Test
	void testAddNode() {
		DGraph d = new DGraph();
		int count = 0;
		for (int i = 1; i < 10; i++) {
			Node temp = new Node(i, new Point3D(i + 100, i + 50));
			d.addNode(temp);
			count++;
		}
		assertEquals(count, 9);
	}

	@Test
	void testConnect() {
		DGraph d = new DGraph();
		int count = 0;
		for (int i = 1; i < 10; i++) {
			Node temp = new Node(i, new Point3D(i + 100, i + 50));
			d.addNode(temp);
		}
		for (int i = 1; i < 10; i++) {
			d.removeNode(i);
			count++;
		}
		assertEquals(count, 9);

	}

	@Test
	void testRemoveNode() {
		DGraph d = new DGraph();
		int count = 0;
		for (int i = 1; i < 10; i++) {
			Node temp = new Node(i, new Point3D(i + 100, i + 50));
			d.addNode(temp);
			count++;
		}
		for (int i = 1; i < 10; i++) {
			d.removeNode(i);
			count--;
		}
		assertEquals(count, 0);
	}

	@Test
	void testRemoveEdge() {
		DGraph d = new DGraph();
		int count = 0;
		for (int i = 1; i < 10; i++) {
			Node temp = new Node(i, new Point3D(i + 100, i + 50));
			d.addNode(temp);
		}
		for (int i = 1; i < 9; i++) {
			d.connect(i, i + 1, i);
		}
		for (int i = 1; i < 9; i++) {

			if (d.removeEdge(i, i + 1) == null)
				count++;

		}
		assertEquals(count, 0);
	}

	@Test
	void testNodeSize() {
		DGraph d = new DGraph();
		int count = 0;
		for (int i = 1; i < 10; i++) {
			Node temp = new Node(i, new Point3D(i + 100, i + 50));
			d.addNode(temp);
			count++;
		}
		assertEquals(count, d.nodeSize());
	}

	@Test
	void testEdgeSize() {
		DGraph d = new DGraph();
		int count = 0;
		for (int i = 1; i < 10; i++) {
			Node temp = new Node(i, new Point3D(i + 100, i + 50));
			d.addNode(temp);
		}
		for (int i = 1; i < 9; i++) {
			d.connect(i, i + 1, i);
			count++;
		}
		assertEquals(count, d.edgeSize());
	}

	@Test
	void testGetMC() {
		int count = 0;
		DGraph d = new DGraph();
		for (int i = 0; i <= 10; i++) {
			Node temp = new Node(i, new Point3D(i + 100, i + 50));
			d.addNode(temp);
			count++;
		}
		for (int i = 0; i <= 9; i++) {
			d.connect(i, i + 1, i);
			count++;
		}
		d.removeNode(1);
		count++;
		assertEquals(count, d.getMC());
	}

}
