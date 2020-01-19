package gameClient;

import javax.swing.JOptionPane;

import org.json.JSONException;

import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.graph;

public class Game_Manual implements Runnable {

	int level;
	MyGameGUI gui;
	private game_service game;
	private GameListener listener;
	private GameServer games;

	public Game_Manual(int level) {
		this.level = level;
	}

	@Override
	public void run() {
		game = Game_Server.getServer(level);
		graph temp = new DGraph(game.getGraph());
		MyGameGUI gui = new MyGameGUI(temp);
		this.addListener(gui);
		gui.setVisible(true);
		try {
			games = listener.setGameServer(game, games);
			int count = games.getRobots();
			listener.upDateFruit(game.getFruits());
			JOptionPane.showMessageDialog(null, "Enter " + count + " Robots", "Robot", JOptionPane.DEFAULT_OPTION);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public void addListener(GameListener listener) {
		this.listener = listener;
	}

}
