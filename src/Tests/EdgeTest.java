package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dataStructure.Edge;

class EdgeTest {

	@Test
	void testSetCost() {
		Edge e = new Edge(2, 5, 10);
		e.setCost(5);

		if (5 != e.getWeight()) {
			fail();
		}
	}

	@Test
	void testGetSrc() {
		Edge e = new Edge(2, 5, 10);
		int srcTemp = e.getSrc();

		if (srcTemp != 2) {
			fail();
		}
	}

	@Test
	void testGetDest() {
		Edge e = new Edge(2, 5, 10);
		int srcTemp = e.getDest();

		if (srcTemp != 5) {
			fail();
		}
	}

	@Test
	void testGetWeight() {
		Edge e = new Edge(2, 5, 10);
		double srcTemp = e.getWeight();

		if (srcTemp != 10) {
			fail();
		}
	}

}
