package gameClient;

import org.json.JSONException;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.graph;

public class GameManager implements Runnable {
	int level;
	GameClient gameClient;

	public GameManager(int level) {
		this.level = level;

	}

	@Override
	public void run() {
		try {
			gameClient = new GameClient(level);
			String gg = gameClient.getGraph();
			graph temp = new DGraph(gg);
			MyGameGUI gui = new MyGameGUI(temp);
			gui.setVisible(true);
			gameClient.addListener(gui);
			gameClient.startGame();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
