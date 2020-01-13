package gameClient;

import static org.junit.Assert.fail;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import dataStructure.Node;
import dataStructure.Robot;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import gui.GUI_window;
import utils.Point3D;

public class MyGameGUI extends JFrame implements ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final double EPS = 0.00001;
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
	private ArrayList<Fruit> fruit;
	private ArrayList<Image> image;
	private boolean isFruit;

	public MyGameGUI(Graph_Algo graph) {
		this.graph = graph;
		init_window();
	}

	public MyGameGUI(graph g) {
		this.graph = new Graph_Algo(g);
		init_window();
	}

	private void init_window() {
		isFruit = true;
		setScale();
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

		this.image = new ArrayList<Image>();
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
			this.image.add(image);
			g1.drawImage(image, (int) x, (int) y, 25, 25, this);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String str = e.getActionCommand();

		switch (str) {
		case "Menual Game": {
			setUp();

			break;
		}
		case "Automatic Game": {
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
					setFruit_Robot(level);
					setFalse();
					repaint();
					isFruit = false;
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Something went wrong", "Error", JOptionPane.DEFAULT_OPTION);
			}
			break;
		}
		}
	}

	private void setFruit_Robot(int level) throws JSONException {
		game_service game = Game_Server.getServer(level);
		this.graph = new Graph_Algo(new DGraph(game.getGraph()));

		// Fruit initializing
		this.fruit.clear();

		for (String fru : game.getFruits()) {
			// creating json object
			JSONObject json = new JSONObject(fru);
			// getting from the json object the Fruit object itself
			JSONObject fruity = json.getJSONObject("Fruit");
			// getting the data needed for a fruit variable
			String pos = fruity.getString("pos");
			double value = fruity.getDouble("value");
			int type = fruity.getInt("type");
			fruit.add(new Fruit(value, type, pos));
		}
	}

	private void setFalse() {
		this.text.setText("Level");
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
