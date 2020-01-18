package gameClient;

import org.json.JSONException;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.graph;

public class GameManager {
	public static void main(String[] args) throws JSONException {

		GameClient gameClient = new GameClient(23);
		String gg = gameClient.getGraph();
		graph temp = new DGraph(gg);
		MyGameGUI gui = new MyGameGUI(temp);
		gui.setVisible(true);
		gameClient.addListener(gui);
		gameClient.startGame();
	}
}
