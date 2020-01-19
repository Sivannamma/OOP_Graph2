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
import dataStructure.graph;
import dataStructure.node_data;

public class GameClient {
	private ArrayList<node_data> list;
	private GameServer games;
	private GameListener listener;
	private game_service game;
	private Graph_Algo graph;
	private HashMap<Integer, Robot> robot;
	private node_data temp;
	// private graph g;

	public GameClient(int level) throws JSONException {
		game = Game_Server.getServer(level); // setting the level that the user choose
		this.graph = new Graph_Algo(new DGraph(game.getGraph()));
		list = new ArrayList<node_data>();
		// this.g= temp;
	}

	public void addListener(GameListener listener) {
		this.listener = listener;
	}

	public void startGame() throws JSONException {
		games = listener.setGameServer(game, games);
		long start = System.currentTimeMillis(); // to massure how often we call the game.move function
		addRobot(); // adding the robot to the game
		listener.setRobotSrc();
		listener.setGraphFromClient(graph);
		listener.updateGUI(game.getRobots(), game.getFruits()); // paint the robots and the fruits
		robot = listener.getRobot(); // first initiallize of the robots in the hash
		game.startGame(); // start game
		listener.updateGUI(game.getRobots(), game.getFruits()); // paint the robots and the fruits
		while (game.isRunning()) {
			for (Integer i : robot.keySet()) { // itearte through the robots in the current level
				// robot = listener.getRobot();// update the hash map after we moved the robot
				if (robot.get(i).getDest() != -1)
					continue;
				if (robot.get(i).getPath().isEmpty()) { // only if we finished the path

					list = listener.auto_mode(game, i); // getting the path for the robot
					this.robot.get(i).setPath(list);
				}
				if (list.isEmpty()) {
					continue;
				}
				temp = list.get(0); // takes only the first node of the list
				robot.get(i).getPath().remove(0); // delete the node in the list we came from
				int goTo = temp.getKey(); // getting the key of the wanted node
				robot.get(i).setDest(goTo);
				robot.get(i).setLocation(temp.getLocation()); // update the location of the
				// current robot
				int id = robot.get(i).getId(); // takes the current robot id
				game.chooseNextEdge(id, goTo); // tell the robot to go to the wanted node
				// listener.updateGUI(game.getRobots(), game.getFruits());

				robot = listener.getRobot();// update the hash map after we moved the robot
			}
			game.move();
			if (listener != null) {
				if (System.currentTimeMillis() - start >= (1000 / 8)) {
					// listener.updateGUI(game.getRobots(), game.getFruits());
					listener.setRobot(game.getRobots()); // update the location after it moved
					robot = listener.getRobot();// update the hash map after we moved the robot
					listener.updateGUI(game.getRobots(), game.getFruits());
					start = System.currentTimeMillis();
				}
			}
		}
		games = listener.setGameServer(game, games);
		System.out.println("game ended : " + games.getGrade());
	}

	private void addRobot() {
		int count = games.getRobots();
		while (count > 0) {
			game.addRobot(count);
			count--;
		}

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
