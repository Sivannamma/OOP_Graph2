package gameClient;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.Robot;
import dataStructure.node_data;

public class GameClient {
	private GameServer games;
	GameListener listener;
	game_service game;
	Graph_Algo graph;
	private HashMap<Integer, Robot> robot;

	public GameClient(int level) throws JSONException {
		game = Game_Server.getServer(level); // setting the level that the user choose
		game.addRobot(4);
	}

	public void addListener(GameListener listener) {
		this.listener = listener;
	}

	public void startGame() throws JSONException {
		ArrayList<node_data> list = new ArrayList<node_data>();
		games = listener.setGameServer(game, games);
		long start = System.currentTimeMillis(); // to massure how often we call the game.move function
		game.startGame();
		robot = listener.getRobot();
		listener.updateGUI(game.getRobots(), game.getFruits());
		while (game.isRunning()) {
			for (Integer i : robot.keySet()) {
				list = listener.auto_mode(game, i);
				for (node_data n : list) {
					node_data temp = n;
					int goTo = temp.getKey();
					robot.get(i).setLocation(temp.getLocation());
					robot.get(i).setSrc(temp.getKey());
					int id = robot.get(i).getId();
					game.chooseNextEdge(id, goTo);
					listener.setRobot(game.getRobots());
					robot = listener.getRobot();
					game.move();
					while (this.robot.get(i).getDest() != -1) {
						game.move();
						if (listener != null) {
							if (System.currentTimeMillis() - start >= (1000 / 110)) {
								listener.updateGUI(game.getRobots(), game.getFruits());
								start = System.currentTimeMillis();
							}
//			}
						}
					}
				}
			}
		}
		System.out.println("game ended : ");
	}

	public game_service getGame() {
		return game;
	}

	public void setGame(game_service game) {
		this.game = game;
	}

	public String getGraph() {
		return game.getGraph();
	}

}
