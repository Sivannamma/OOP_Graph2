package gameClient;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONException;
import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.Fruit;
import dataStructure.Robot;
import dataStructure.edge_data;
import dataStructure.node_data;

public class GameClient {
	private HashMap<String, Fruit> fruitss;
	KML_Logger myKML;
	private ArrayList<node_data> list;
	private GameServer games;
	private GameListener listener;
	private game_service game;
	private Graph_Algo graph;
	private HashMap<Integer, Robot> robot;
	private node_data temp;
	int level;
	// private graph g;

	public GameClient(int level, KML_Logger myKML) throws JSONException {
		Game_Server.login(208479311);
		game = Game_Server.getServer(level); // setting the level that the user choose
		this.graph = new Graph_Algo(new DGraph(game.getGraph()));
		list = new ArrayList<node_data>();
		this.myKML = myKML;
		this.level = level;
	}

	public void addListener(GameListener listener) {
		this.listener = listener;
	}

	public void startGame() throws JSONException {
		myKML = new KML_Logger(level);
		listener.setKML(this.myKML);
		games = listener.setGameServer(game, games);
		listener.setGraphFromClient(graph);
		listener.setFruit(game.getFruits());
		setRobotSrc();
		// game.addRobot(4);
		listener.updateGUI(game.getRobots(), game.getFruits()); // paint the robots and the fruits
		listener.setKML(this.myKML);
		robot = listener.getRobot(); // first initiallize of the robots in the hash
		listener.setKML(this.myKML);
		long start = System.currentTimeMillis(); // to massure how often we call the game.move function
		game.startGame(); // start game
		boolean isFirst = true;
		while (game.isRunning()) {
			for (Integer i : robot.keySet()) // itearte through the robots in the current level
			{
				if (robot.get(i).getDest() != -1) {
					continue;
				}
				if (robot.get(i).getPath().isEmpty()) { // only if we finished the path
					if (!isFirst)
						listener.setFalseFruit(this.robot.get(i).getFruitKey());
					list = listener.auto_mode(game, i); // getting the path for the robot
					// list = listener.circle(i);
					this.robot.get(i).setPath(list);
				}
				if (list.isEmpty()) {
					continue;
				}
				temp = robot.get(i).getPath().get(0); // takes only the first node of the list
				robot.get(i).getPath().remove(0); // delete the node in the list we came from
				int goTo = temp.getKey(); // getting the key of the wanted node
				robot.get(i).setDest(goTo);
				robot.get(i).setLocation(temp.getLocation()); // update the location of the
				// current robot
				int id = robot.get(i).getId(); // takes the current robot id
				game.chooseNextEdge(id, goTo); // tell the robot to go to the wanted node
				isOnEdge(goTo, i);

				robot = listener.getRobot();// update the hash map after we moved the robot
			}
			isFirst = false;
			if (listener != null) {
				if (System.currentTimeMillis() - start >= (1000 / 8)) {
					game.move();
					listener.setRobot(game.getRobots()); // update the location after it moved
					robot = listener.getRobot();// update the hash map after we moved the robot
					listener.updateGUI(game.getRobots(), game.getFruits());
					listener.setKML(this.myKML);
					start = System.currentTimeMillis();
				}
			}
		}
		this.myKML.EndAndSave_KML();
		games = listener.setGameServer(game, games);
		System.out.println("game ended : " + games.getGrade());
		System.out.println("moves" + games.getMoves());
	}

	private void isOnEdge(int goTo, int i) {
		this.fruitss = listener.getFruitss();
		for (String fru : this.fruitss.keySet()) {
			edge_data e = this.fruitss.get(fru).getEdge();
			if (this.fruitss.get(fru).getType() == 1) { // apple
				if (e.getSrc() < e.getDest()) {
					if (this.robot.get(i).getSrc() == e.getSrc() && e.getDest() == goTo) {
						this.fruitss.get(fru).setToDraw(false);
						this.fruitss.get(fru).setVisited(true);
					}
				} else {
					if (this.robot.get(i).getSrc() == e.getDest() && e.getSrc() == goTo) {
						this.fruitss.get(fru).setToDraw(false);
						this.fruitss.get(fru).setVisited(true);
					}
				}
			} else {
				if (e.getSrc() > e.getDest()) {
					if (this.robot.get(i).getSrc() == e.getSrc() && e.getDest() == goTo) {
						this.fruitss.get(fru).setToDraw(false);
						this.fruitss.get(fru).setVisited(true);
					}
				} else {
					if (this.robot.get(i).getSrc() == e.getDest() && e.getSrc() == goTo) {
						this.fruitss.get(fru).setToDraw(false);
						this.fruitss.get(fru).setVisited(true);
					}
				}
			}
		}
		listener.setFruitss(this.fruitss);
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

	/**
	 * this function set the robots in the place where theres fruits, once we found
	 * a fruit we place the robot there and add it to the game (game.addRobot) if
	 * there arent enough fruits for all the robots in the game we randomly get a
	 * node from the graph and place them there
	 */

	public void setRobotSrc() throws JSONException {
		this.fruitss = listener.getFruitss();
		int count = 0;
		if (games.getRobots() <= games.getFruit()) {
			for (String r : this.fruitss.keySet()) {
				if (this.fruitss.get(r).getType() == 1) { // if its an apple means from low to high so means src is
					game.addRobot(this.fruitss.get(r).getEdge().getSrc());
					count++;
				} else { // banana from hight to low, so means the dest is the place
					game.addRobot(this.fruitss.get(r).getEdge().getDest());
					count++;
				}
				if (count == games.getRobots())
					break;
			}
		}
		// if theres robot we didnt place yet
		while (count < games.getRobots()) {
			int rand = (int) (Math.random() * this.graph.getGraph().getV().size());
			count++;
			game.addRobot(rand);
		}
		listener.updateGUI(game.getRobots(), game.getFruits()); // paint the robots and the fruits
	}
}
