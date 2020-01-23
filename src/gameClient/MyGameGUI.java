package gameClient;

import java.awt.Color;
import java.util.List;
import java.util.Set;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.Fruit;
import dataStructure.Robot;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

public class MyGameGUI extends JFrame implements ActionListener, MouseListener, GameListener {
	private static final double EPS = 0.001 * 0.001;
	private static final double RADIUS = 40;
	private static final long serialVersionUID = 1L;
	boolean flag = true;
	GameClient gameClient;
	private double y_min;
	private double y_max;
	private double x_min;
	private double x_max;
	boolean flagSrcDest = true;
	Graph_Algo graph;
	JTextField text;
	JButton button;
	int count = 1;
	boolean flagScore = false;
	boolean isManual = false;
	Label label;
	KML_Logger myKML;

	boolean flagFruit = true;
	private long start;
	Dimension size;

	public HashMap<String, Fruit> getFruitss() {
		return fruitss;
	}

	public void setFruitss(HashMap<String, Fruit> fruitss) {
		this.fruitss = fruitss;
	}

	private HashMap<String, Fruit> fruitss;
	private int gameMode = 2; // - one for manual, two for automatic
	private HashMap<Integer, Robot> robot; // hashmap to manage our robots in the movement
	private boolean setRobots; // when true, we activate the mouse lisiten so the user place the robots
	private GameServer games;
	private game_service game;
	boolean paintOnce;
	boolean flagRobot = true;
	private Robot src_R;
	private node_data dest_m;

	public MyGameGUI() {
		this.graph = null;
		init_window();
	}

	public MyGameGUI(Graph_Algo graph) {
		this.graph = graph;
		init_window();

	}

	public MyGameGUI(graph g) {
		this.graph = new Graph_Algo(g);
		init_window();
	}

	private void init_window() {

		setScale();
		paintOnce = true;
		setRobots = false;
		fruitss = new HashMap<String, Fruit>();
		gameMode = 2; // default setting
		robot = new HashMap<Integer, Robot>();
		// the size of the window
		this.setSize(1300, 1200);
		// setting that the program is terminated when we close 'X' on the window as
		// well.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// not resizeable
		this.setResizable(false);

		this.setLayout(null);
		// font
		Font f = new Font("Arial", Font.BOLD, 15);
		this.setFont(f);

		label = new Label("Enter a level:");
		text = new JTextField();
		button = new JButton("                             ");
		// prefered size for the button
		size = button.getPreferredSize();

		// menu bar
		MenuBar menuBar = new MenuBar();
		this.setMenuBar(menuBar);
		// categories
		Menu game = new Menu("Game");
		game.addActionListener(this);
		// adding to the tag graphs
		MenuItem a_game = new MenuItem("Automatic Game");
		a_game.addActionListener(this);
		MenuItem m_game = new MenuItem("Manual Game");
		m_game.addActionListener(this);
		MenuItem h_score = new MenuItem("High Score");
		h_score.addActionListener(this);

		game.add(m_game);
		game.add(a_game);
		game.add(h_score);
		// adding to the menu bar itself
		menuBar.add(game);

		// button
		button.setBounds(370, 60, size.width, size.height);
		this.button.addActionListener(this);
		this.add(button);

		// test
		text.setText("Level");
		text.setBounds(380, 20, 40, 20);
		this.add(text);

		this.button.setVisible(false);
		this.text.setVisible(false);

		// label of list targets
		this.add(label);
		label.setBounds(250, 5, 500, 50);
		this.label.setVisible(false);
		this.addMouseListener(this);
		label.setFont(f);

		repaint();
	}

	/**
	 * this two functions, scaleX and scaleY are returning for each data the
	 * location it needs to be on our jframe window.
	 */
	private double scaleX(double data) {
		int canvasX = 1110;
		int offSet = 90;
		double res = ((data - x_min) / (x_max - x_min)) * (canvasX) + offSet;
		return res;
	}

	private double scaleY(double data) {
		int canvasY = 500;
		int offSet = 150;
		double res = ((data - y_min) / (y_max - y_min)) * (canvasY) + offSet;
		return res;
	}

	/**
	 * this function setScale going over our graph with every location (x,y) and
	 * saving the x max\min value and the same for y. this function is used once for
	 * each graph, and so we can use the data taken from it in the two upper
	 * functions.
	 */
	private void setScale() {
		if (this.graph == null)
			return;
		boolean flag = true; // first time set only
		x_max = 0;
		y_max = 0;
		for (node_data node : this.graph.getGraph().getV()) {
			if (flag) {
				flag = false;
				x_min = node.getLocation().ix();
				y_min = node.getLocation().iy();
			}
			if (node.getLocation().ix() < x_min)
				x_min = node.getLocation().ix();
			if (node.getLocation().iy() < y_min)
				y_min = node.getLocation().iy();
			if (node.getLocation().ix() > x_max)
				x_max = (double) node.getLocation().ix();
			if (node.getLocation().iy() > y_max)
				y_max = (double) node.getLocation().iy();
		}
	}

	/**
	 * setFalse - function that sets the buttons, labels and test field to false.
	 * setUp - making visibilty to true, inorder to use them in the gui window.
	 * those function in use after user uses in the gui window.
	 */
	private void setFalse() {
		this.text.setText("Level");
		this.button.setText("Send");
		this.button.setVisible(false);
		this.label.setVisible(false);
		this.text.setVisible(false);
	}

	private void setUp() {
		this.button.setText("Send");
		this.button.setVisible(true);
		this.label.setVisible(true);
		this.text.setVisible(true);
	}

	public void paint(Graphics g1) {
		super.paint(g1);
		g1.setFont(new Font("David", 10, 14));
		drawGraph(g1); // drawing the graph
		drawFruits(g1); // drawing fruits
		drawRobots(g1); // drawing robots
	}

	/**
	 * this functions gets from each fruit the edge that it sits on. each edge has
	 * the src of the edge, and so we want to place the robots there. so we go to
	 * our graph, get the spesific node by the gey, and get its location, and then
	 * we place the robot there.
	 */
	private void drawRobots(Graphics g1) {
		// itreating through the hashmap of the robots, painting them in their src
		for (Integer r : this.robot.keySet()) {
			double x = scaleX(this.robot.get(r).getLocation().ix());
			double y = scaleY(this.robot.get(r).getLocation().iy());
			String img = "robot.png";
			ImageIcon icon = new ImageIcon(img);
			Image image = icon.getImage();
			g1.drawImage(image, (int) x, (int) y, 25, 25, this);
		}
	}

	private void drawGraph(Graphics g1) {
		if (this.graph == null) // if there is nothing to draw
			return;
		// drawing nodes and edges :
		if (this.graph.getGraph() != null) {
			utils.Point3D current = null;
			for (node_data key : this.graph.getGraph().getV()) {
				g1.setColor(Color.black);
				Collection<edge_data> col = this.graph.getGraph().getE(key.getKey());
				// drawing the current dot
				current = key.getLocation();
				g1.fillOval((int) scaleX(current.ix()), (int) scaleY(current.iy()), 8, 8);
				// drawing the id of the node
				g1.drawString(key.getKey() + "", (int) scaleX(current.ix()), (int) scaleY(current.iy()));
				// going over the nei of the key, to draw lines between them
				for (edge_data edge : col) {
					int dest = edge.getDest();
					node_data toBe = this.graph.getGraph().getNode(dest);
					utils.Point3D toBe_p = toBe.getLocation();
					g1.setColor(Color.lightGray);
					// line
					g1.drawLine((int) scaleX(current.ix()), (int) scaleY(current.iy()), (int) scaleX(toBe_p.ix()),
							(int) scaleY((toBe_p.iy())));
					// the weight
					g1.setColor(Color.blue);
					g1.setFont(new Font("David", 10, 14));
					g1.drawString(edge.getWeight() + "", (int) (scaleX(current.ix() + scaleX(toBe_p.ix()) / 2)),
							(int) (scaleY(current.iy() + scaleY((toBe_p.iy())) / 2)));
				}
			}
		}
	}

	private synchronized void drawFruits(Graphics g1) {

		for (String i : fruitss.keySet()) {
			if (!(this.fruitss.get(i).isToDraw()))
				continue;
			double x = scaleX(fruitss.get(i).getLocation().ix());
			double y = scaleY(fruitss.get(i).getLocation().iy());
			String img = "";
			if (fruitss.get(i).getType() == 1)// if its an apple
				img = "apple.jpg";
			else // if its a banana
				img = "banana.png";
			// drawing the fruits:
			ImageIcon icon = new ImageIcon(img);
			Image image = icon.getImage();
			// adding to array of imgages, so we can delete them after changing levels
			g1.drawImage(image, (int) x, (int) y, 25, 25, this);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!isManual)
			return;
		double xR = e.getX();
		double yR = e.getY();

		if (setRobots) {
			for (node_data n : this.graph.getGraph().getV()) {
				Point3D src = n.getLocation();
				if (checkRadius(src, xR, yR)) {
					game.addRobot(n.getKey());
					try {
						setManualRobot(game.getRobots());
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
					repaint();
					break;
				}
			}
		}
		if (flagScore && !game.isRunning()) {
			try {
				games = setGameServer(game, games);
				JOptionPane.showMessageDialog(null, "Game Over, Score : " + games.getGrade(), "Robots",
						JOptionPane.DEFAULT_OPTION);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		if (flagSrcDest) {
			label.setText("Click a robot");

		}
		if (!setRobots) {
			for (node_data n : this.graph.getGraph().getV()) {
				Point3D src = n.getLocation();
				if (src_R == null) {
					if (checkRadius(src, xR, yR)) {
						for (Integer r : this.robot.keySet())
							if (robot.get(r).getSrc() == n.getKey()) {
								src_R = this.robot.get(r);
								flagSrcDest = false;
								label.setText("Click destination");
								break;
							}
					}
				}

				else {
					if (checkRadius(src, xR, yR)) {
						dest_m = n;
						break;
					}

				}
			}
			if (dest_m != null && src_R != null) {
				flagSrcDest = true;
				try {
					setMaualPath(src_R, dest_m);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				repaint();
				src_R = null;
				dest_m = null;

			}
		}
		if (count == 1) {
			if (robot.size() == games.getRobots()) {
				start = System.currentTimeMillis(); // to massure how often we call the game.move function
				setRobots = false;
				button.setText("Start");
				button.setVisible(true);
				count--;
			}
		}

	}

	private void setMaualPath(Robot src_R2, node_data dest_m2) throws JSONException {
		start = System.currentTimeMillis();
		ArrayList<node_data> list = (ArrayList<node_data>) this.graph.shortestPath(src_R2.getSrc(), dest_m2.getKey());
		this.graph.reverse(list); // reversing because we get it diffrently from the algorithm
		if (!list.isEmpty())
			list.remove(0); // deleting the first one because the robot is on it already
		while (!list.isEmpty()) {

			if (!list.isEmpty()) {

				game.chooseNextEdge(src_R2.getId(), list.get(0).getKey());
				updateGUI(game.getRobots(), game.getFruits());
				isOnTheWay(list.get(0).getKey(), src_R2.getSrc());
				game.move();
				updateGUI(game.getRobots(), game.getFruits());
				list.remove(0);
			}

			while (this.robot.get(src_R2.getId()).getDest() != -1) { // as long the robot didnt reach the next node
				game.move();
				if (System.currentTimeMillis() - start >= (1000 / 8)) {
					updateGUI(game.getRobots(), game.getFruits());
					repaint();
					start = System.currentTimeMillis();
				}
			}

		}
		label.setText("Click a robot");
	}

	private void isOnTheWay(int goTo, int i) {
		for (String fru : this.fruitss.keySet()) {
			edge_data e = this.fruitss.get(fru).getEdge();
			if (this.fruitss.get(fru).getType() == 1) { // apple
				if (e.getSrc() < e.getDest()) {
					if (i == e.getSrc() && e.getDest() == goTo) {
						this.fruitss.get(fru).setToDraw(false);
					}
				} else {
					if (i == e.getDest() && e.getSrc() == goTo) {
						this.fruitss.get(fru).setToDraw(false);
					}
				}
			} else {
				if (e.getSrc() > e.getDest()) {
					if (i == e.getSrc() && e.getDest() == goTo) {
						this.fruitss.get(fru).setToDraw(false);
					}
				} else {
					if (i == e.getDest() && e.getSrc() == goTo) {
						this.fruitss.get(fru).setToDraw(false);
					}
				}
			}
		}
	}

	private void setManualRobot(List<String> robots) throws JSONException {
		for (String robo : robots) {
			// creating json object
			JSONObject json = new JSONObject(robo);
			// getting from the json object the Robot object itself
			JSONObject roby = json.getJSONObject("Robot");
			int id = roby.getInt("id");
			double value = roby.getDouble("value");
			double speed = roby.getDouble("speed");
			int src = roby.getInt("src");
			int dest = roby.getInt("dest");
			String pos = roby.getString("pos");
			robot.put(id, new Robot(id, value, speed, src, dest, pos));
		}
		flagRobot = false;

	}

	private boolean checkRadius(Point3D src, double xR, double yR) {
		double a = scaleX(src.ix());
		double b = scaleY(src.iy());
		double x = Math.pow(a - xR, 2);
		double y = Math.pow(b - yR, 2);
		double ans = Math.sqrt(x + y);
		if (ans <= RADIUS)
			return true;
		return false;

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String str = e.getActionCommand();
		switch (str) {
		case "High Score": {
			DB db = new DB();
			db.setVisible(true);
			break;
		}
		case "Manual Game": {
			gameMode = 1; // setting game mode
			setUp();
			break;
		}
		case "Automatic Game": {
			gameMode = 2; // setting game mode
			setUp();
			break;
		}
		case "Send": {
			int level = 0;
			try {
				level = Integer.parseInt(text.getText());
				if ((level < 0 || level > 23) && level != -31) {
					JOptionPane.showMessageDialog(null, "Input must be between 0-23 (include)", "Error",
							JOptionPane.DEFAULT_OPTION);
				} else {
					setFalse();
					if (gameMode == 2) // automatic mode (we place the robots)
					{
						GameManager m = new GameManager(level, this.myKML);
						Thread t1 = new Thread(m);
						t1.start();

					} else // means manual game mode
					{
						isManual = true;
						setRobots = true; // we activate the mouse lisiten
						game = Game_Server.getServer(level);
						games = setGameServer(game, games);
						this.graph = new Graph_Algo(new DGraph(game.getGraph()));
						setScale();
						setFruit(game.getFruits());
						repaint();
						JOptionPane.showMessageDialog(null, "Please enter " + games.getRobots() + " robots", "Robots",
								JOptionPane.DEFAULT_OPTION);

					}
					repaint();
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Something went wrong", "Error", JOptionPane.DEFAULT_OPTION);
			}
			break;
		}
		case "Start": {
			JOptionPane.showMessageDialog(null,
					"You can start moving the robots. \n Click a robot, and then click the destination", "Play",
					JOptionPane.DEFAULT_OPTION);
			game.startGame();
			flagScore = true;
			button.setVisible(false);
			label.setVisible(true);

			break;
		}
		}
		repaint();
	}

	public GameServer setGameServer(game_service game, GameServer games) throws JSONException {
		String ans = game.toString();// creating json object
		JSONObject json = new JSONObject(ans);
		// getting from the json object the GameServer object itself

		JSONObject gamey = json.getJSONObject("GameServer");
		int fruit = gamey.getInt("fruits");
		int moves = gamey.getInt("moves");
		int grade = gamey.getInt("grade");
		int robots = gamey.getInt("robots");
		String graph = gamey.getString("graph");
		games = new GameServer(fruit, moves, grade, robots, graph);
		return games;

	}

	public void setRobot(List<String> robots) throws JSONException {

		for (String robo : robots) {
			// creating json object
			JSONObject json = new JSONObject(robo);
			// getting from the json object the Robot object itself
			JSONObject roby = json.getJSONObject("Robot");
			int id = roby.getInt("id");
			double value = roby.getDouble("value");
			double speed = roby.getDouble("speed");
			int src = roby.getInt("src");
			int dest = roby.getInt("dest");
			String pos = roby.getString("pos");
			if (flagRobot) {
				robot.put(id, new Robot(id, value, speed, src, dest, pos));
			} else // constructor with list,and update it.
			{
				robot.get(id).setDest(dest);
				robot.get(id).setSpeed(speed);
				robot.get(id).setSrc(src);
				robot.get(id).setPos(pos);
				robot.get(id).setValue(value);
				if (gameMode == 2)
					myKML.PlaceMark("robot", robot.get(id).getLocation());
			}
		}
		flagRobot = false;
	}

	public synchronized void setFruit(List<String> fruits) throws JSONException {

		for (String fru : fruits) {
			// creating json object
			JSONObject json = new JSONObject(fru);
			// getting from the json object the Fruit object itself
			JSONObject fruity = json.getJSONObject("Fruit");
			// getting the data needed for a fruit variable
			String pos = fruity.getString("pos");
			double value = fruity.getDouble("value");
			int type = fruity.getInt("type");

			if (!fruitss.containsKey(pos))
				fruitss.put(pos, new Fruit(value, type, pos));

			if (gameMode == 2) {
				if (fruitss.get(pos).getType() == 1) {
					myKML.PlaceMark("apple", fruitss.get(pos).getLocation());
				} else
					myKML.PlaceMark("banana", fruitss.get(pos).getLocation());
			}
		}

		connect_fruitsAndEdge(); // calling the function to connect the fruits to the right place

	}

	/**
	 * this function is for setting each edge with the amount of fruits it has going
	 * over all of the fruits and fiding the place for them in the edges of the
	 * graph
	 */

	private void connect_fruitsAndEdge() {
		boolean isFound = false;

		// iterating over the fruits
		for (String i : fruitss.keySet()) {
			isFound = false;
			// iterating over the nodes in the graph
			for (node_data node : this.graph.getGraph().getV()) {
				// iterating over spesific node neighboors
				for (edge_data edge : this.graph.getGraph().getE(node.getKey())) {

					if (isOnEdge(node, edge, fruitss.get(i))) {// if return true, means this edge connected to the fruit
						fruitss.get(i).setEdge(edge);
						isFound = true; // if we found the right edge for the fruit, we can continue
						// to the next fruit
					}
					if (isFound)
						break;
				}
				if (isFound)
					break;
			}
		}
	}

	/**
	 * this function is help function in order to determine if a fruit is sitting on
	 * a certain edge we check if its the right edge by simple distance using EPS if
	 * src.distance(dest) is about the same of src.distance(f) + f.distance(dest) we
	 * know its the right edge
	 */

	private boolean isOnEdge(node_data node, edge_data edge, Fruit f) {
		Point3D srcP = node.getLocation();
		Point3D desrP = this.graph.getGraph().getNode(edge.getDest()).getLocation();
		double dist = srcP.distance2D(desrP); // the distance between src--->dest
		// the distance between src--->fruit + fruit---->dest
		double ans = srcP.distance2D(f.getLocation()) + f.getLocation().distance2D(desrP);
		// if its in the right range, return true; else means this edge isnt the fruit
		// edge
		if (dist > ans - EPS)
			return true;
		return false;
	}

	public void setGameServer() throws JSONException {
		String ans = game.toString();// creating json object
		JSONObject json = new JSONObject(ans);
		// getting from the json object the GameServer object itself

		JSONObject gamey = json.getJSONObject("GameServer");
		int fruit = gamey.getInt("fruits");
		int moves = gamey.getInt("moves");
		int grade = gamey.getInt("grade");
		int robots = gamey.getInt("robots");
		String graph = gamey.getString("graph");
		games = new GameServer(fruit, moves, grade, robots, graph);

	}

	@Override
	public void updateGUI(List<String> robots, List<String> fruits) throws JSONException {
		setFruit(fruits);
		setRobot(robots);
		repaint();
		paintOnce = false;
	}

	@Override
	public ArrayList<node_data> auto_mode(game_service game, int i) throws JSONException {
		ArrayList<node_data> list = new ArrayList<node_data>();
		setFruit(game.getFruits());
		String fru = closesFruit(i);

		this.robot.get(i).setFruitKey(fru);
		this.fruitss.get(fru).setVisited(true);
		if (this.fruitss.get(fru).getType() == -1) { // banana
			edge_data e = this.fruitss.get(fru).getEdge();
			if (e.getSrc() < e.getDest()) {
				list = (ArrayList<node_data>) this.graph.shortestPath(this.robot.get(i).getSrc(),
						this.fruitss.get(fru).getEdge().getDest());
				list.remove(list.size() - 1);
				this.graph.reverse(list);
				list.add(this.graph.getGraph().getNode(e.getSrc()));
			} else { // dest<src
				list = (ArrayList<node_data>) this.graph.shortestPath(this.robot.get(i).getSrc(),
						this.fruitss.get(fru).getEdge().getSrc());
				list.remove(list.size() - 1);
				this.graph.reverse(list);
				list.add(this.graph.getGraph().getNode(e.getDest()));
			}

		} else { // apple

			edge_data e = this.fruitss.get(fru).getEdge();
			if (e.getSrc() > e.getDest()) {
				list = (ArrayList<node_data>) this.graph.shortestPath(this.robot.get(i).getSrc(),
						this.fruitss.get(fru).getEdge().getDest());
				list.remove(list.size() - 1);
				this.graph.reverse(list);
				list.add(this.graph.getGraph().getNode(e.getSrc()));
			} else { // dest>src
				list = (ArrayList<node_data>) this.graph.shortestPath(this.robot.get(i).getSrc(),
						this.fruitss.get(fru).getEdge().getSrc());
				list.remove(list.size() - 1);
				this.graph.reverse(list);
				list.add(this.graph.getGraph().getNode(e.getDest()));
			}

		}

		return list;

	}

	public void setGraphFromClient(Graph_Algo g) {
		this.graph = g;
	}

	public HashMap<Integer, Robot> getRobot() {
		return robot;
	}

	public void setRobot(HashMap<Integer, Robot> robot) {
		this.robot = robot;
	}

	public void manualDrawRobot(game_service game) {
		this.game = game;
	}

	public void setKML(KML_Logger myKML) {
		this.myKML = myKML;
	}

	public void setFalseFruit(String i) {
		this.fruitss.get(i).setToDraw(false);
	}

	private String closesFruit(int robot_src) {
		double min = Double.MAX_VALUE;
		String ans = null;
		double temp = 0;
		for (String fru : this.fruitss.keySet()) {
			if (this.fruitss.get(fru).isVisited()) {
				continue;
			}
//			if (robot.size() == 1)
//				return fru;
			edge_data e = this.fruitss.get(fru).getEdge();
			if (this.fruitss.get(fru).getType() == -1) {
				if (e.getSrc() < e.getDest())
					temp = this.graph.shortestPathDist(robot_src, this.fruitss.get(fru).getEdge().getDest());
				else
					temp = this.graph.shortestPathDist(robot_src, this.fruitss.get(fru).getEdge().getSrc());

			} else {
				if (e.getSrc() < e.getDest())
					temp = this.graph.shortestPathDist(robot_src, this.fruitss.get(fru).getEdge().getSrc());
				else
					temp = this.graph.shortestPathDist(robot_src, this.fruitss.get(fru).getEdge().getDest());
			}
			if (temp < min) {
				min = temp;
				ans = fru;
			}
		}
		return ans;
	}

	int county = 0;

	public ArrayList<node_data> circle(int i) {
		ArrayList<node_data> list = new ArrayList<node_data>();
		int temp = this.robot.get(i).getSrc();
		if (temp == 0) {
			temp = 10;
			county++;
		} else if (county == 3) {
			list.add(this.graph.getGraph().getNode(0));
			list.add(this.graph.getGraph().getNode(1));
			county = 0;
		} else
			temp--;
		list.add(this.graph.getGraph().getNode(temp));
		return list;

	}
}
