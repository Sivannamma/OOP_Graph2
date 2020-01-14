package gameClient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
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

public class MyGameGUI extends JFrame implements ActionListener, MouseListener {

	private static final double EPS = 0.001 * 0.001;
	private static final long serialVersionUID = 1L;
	private double y_min;
	private double y_max;
	private double x_min;
	private double x_max;
	Graph_Algo graph;
	JTextField text;
	JButton button;
	Label label;
	Dimension size;
	private boolean setLevel;
	private ArrayList<Fruit> fruit;
	private int gameMode; // - one for manual, two for automatic
	HashMap<Integer, Robot> robot; // hashmap to manage our robots in the movement
	private boolean setRobots; // when true, we activate the mouse lisiten so the user place the robots
	private GameServer games;
	private game_service game;

	public MyGameGUI(Graph_Algo graph) {
		this.graph = graph;
		init_window();
	}

	public MyGameGUI(graph g) {
		this.graph = new Graph_Algo(g);
		init_window();
	}

	private void init_window() {
		setLevel = false;
		setScale();
		setRobots = false;
		gameMode = 0; // default setting
		robot = new HashMap<Integer, Robot>();
		fruit = new ArrayList<Fruit>();
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
		MenuItem m_game = new MenuItem("Menual Game");
		m_game.addActionListener(this);

		game.add(m_game);
		game.add(a_game);
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

	public void paint(Graphics g1) {
		super.paint(g1);
		g1.setFont(new Font("David", 10, 14));
		drawGraph(g1); // drawing the graph
		drawFruits(g1); // drawing fruits
		if (setLevel) {
			drawRobots(g1); // drawing robots
		}
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

	private void drawFruits(Graphics g1) {
		for (Fruit f : this.fruit) {
			double x = scaleX(f.getLocation().ix());
			double y = scaleY(f.getLocation().iy());
			String img = "";
			if (f.getType() == 1)// if its an apple
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
	public void mouseClicked(MouseEvent arg0) {

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
		case "Menual Game": {
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
				if (level < 0 || level > 23) {
					JOptionPane.showMessageDialog(null, "Input must be between 0-23 (include)", "Error",
							JOptionPane.DEFAULT_OPTION);
				} else {
					this.game = Game_Server.getServer(level);
					// init this graph to the toString of getGraph, tconstructor we have in DGraph
					this.graph = new Graph_Algo(new DGraph(game.getGraph()));
					setGame();
					setLevel = true; // for the paint of the robot
					setFalse();
					if (gameMode == 2) // automatic mode (we place the robots)
					{
						System.out.println("hi");
						this.button.setVisible(true);
						this.button.setText("Start");
					} else // means manual game mode
					{
						setRobots = true; // we activate the mouse lisiten
					}
					setFalse();
					repaint();
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Something went wrong", "Error", JOptionPane.DEFAULT_OPTION);
			}
			break;
		}
		case "Start": {
			setFalse();
			auto_mode();
			break;
		}
		}
	}

	private void setGame() throws JSONException {
		setGameServer(); // setting the main data
		setScale(); // scale of the pain on jframe
		setFruit(); // function to set the fruits
		for (int i = 0; i < games.getRobots(); i++) { // adding robots to the game server
			game.addRobot(i);
		}
		setRobot(); // setting robots
		setRobotSrc(); // setting the robots src's

	}

	private void auto_mode() {

	}

	private void setRobotSrc() {
		int i = 0;
		for (Integer r : this.robot.keySet()) {
			if (i < this.fruit.size()) {
				robot.get(r).setSrc(this.fruit.get(i).getEdge().getSrc());
				this.robot.get(r).setLocation(
						this.graph.getGraph().getNode(this.fruit.get(i++).getEdge().getSrc()).getLocation());
			} else {
				int rand = (int) (Math.random() * this.graph.getGraph().getV().size());
				robot.get(r).setSrc(rand);
				this.robot.get(r).setLocation(
						this.graph.getGraph().getNode(this.fruit.get(rand).getEdge().getSrc()).getLocation());
			}
		}
	}

	private void setGameServer() throws JSONException {
		// creating json object
		String ans = game.toString();

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

	private void setRobot() throws JSONException {
		// Robot initializing
		this.robot.clear(); // clearing the map so every level change the fruits changes as well

		for (String robo : game.getRobots()) {
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
	}

	private void setFruit() throws JSONException {
		// Fruit initializing
		this.fruit.clear(); // clearing the list so every level change the fruits changes as well

		for (String fru : game.getFruits()) {
			// creating json object
			JSONObject json = new JSONObject(fru);
			// getting from the json object the Fruit object itself
			JSONObject fruity = json.getJSONObject("Fruit");
			// getting the data needed for a fruit variable
			String pos = fruity.getString("pos");
			double value = fruity.getDouble("value");
			int type = fruity.getInt("type");
			// adding to the arraylist of fruits
			fruit.add(new Fruit(value, type, pos));
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
		for (Fruit f : this.fruit) {
			isFound = false;
			// iterating over the nodes in the graph
			for (node_data node : this.graph.getGraph().getV()) {
				// iterating over spesific node neighboors
				for (edge_data edge : this.graph.getGraph().getE(node.getKey())) {

					if (isOnEdge(node, edge, f)) {// if return true, means this edge connected to the fruit
						f.setEdge(edge);
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

}
