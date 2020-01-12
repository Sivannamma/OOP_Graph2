package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.Node;
import dataStructure.graph;
import gui.GUI_window;
import utils.Point3D;

class GUI_windowTest {

	@Test
	void Test() {
		Graph_Algo graph = new Graph_Algo();
		graph temp = new DGraph();
		for (int i = 0; i < 1000; i++) {
			double rundX = Math.random() * 600;
			double rundy = Math.random() * 600;
			temp.addNode(new Node(i, new Point3D(rundX, rundy)));
		}
		for (int i = 0; i < 999; i++) {
			temp.connect(i, i + 1, i);
		}
		graph.init(temp);
		GUI_window win = new GUI_window(graph);
		win.setVisible(true);
	}

}
