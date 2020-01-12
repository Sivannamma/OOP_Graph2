package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dataStructure.Node;
import utils.Point3D;

class NodeTest {



	@Test
	void testCopy() {
		Point3D p = new Point3D(5, 10);
		Node n = new Node(4, p);
		Node n2 = n.copy();
		if(!n2.equals(n)) {
			fail();
		}
	}

	@Test
	void testGetKey() {
		Node n = new Node(4, new Point3D(23, 34));
		int temp = n.getKey();
		if (n.getKey() != temp) {
			fail();
		}
	}

	@Test
	void testGetLocation() {
		Node n = new Node(4, new Point3D(23, 34));
		Point3D temp = n.getLocation();
		if (n.getLocation() != temp) {
			fail();
		}
	}

	@Test
	void testSetLocation() {
		Node n = new Node(4, new Point3D(23, 34));
		n.setLocation(new Point3D(6,8));
		Point3D temp = new Point3D(6, 8);
		if (!n.getLocation().equalsXY(temp)) {
			fail();
		}
	}

	@Test
	void testGetWeight() {
		Node n = new Node(4, new Point3D(23, 34));
		double temp = n.getWeight();
		if (n.getWeight()!= temp) {
			fail();
		}
	}


}
