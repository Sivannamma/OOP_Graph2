package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import algorithms.Graph_Algo;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.node_data;
import utils.Point3D;
import dataStructure.*;

public class GUI_window extends JFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	int id;
	Point3D point;
	final static int Y_OFFSET = 200;
	final static int X_OFFSET = 50;
	Graph_Algo graph;
	JTextField text_src;
	JTextField text_dest;
	JTextField weight;
	JTextField input;
	JButton button;
	JLabel label;
	Dimension size;

	public GUI_window(Graph_Algo graph) {
		this.graph = graph;
		init_window();
	}

	public GUI_window(graph g) {
		this.graph = new Graph_Algo(g);
		init_window();
	}

	private void init_window() {
		// text
		weight = new JTextField();
		label = new JLabel();
		text_src = new JTextField();
		text_dest = new JTextField();
		input = new JTextField();

		button = new JButton("                             ");
		// prefered size for the button
		size = button.getPreferredSize();

		// setting visibility to false
		weight.setVisible(false);
		input.setVisible(false);
		text_src.setVisible(false);
		text_dest.setVisible(false);
		button.setVisible(false);
		label.setVisible(false);

		// the size of the window
		this.setSize(1000, 1200);
		// setting that the program is terminated when we close 'X' on the window as
		// well.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// not resizeable
		this.setResizable(false);
		// font
		Font f = new Font("Arial", Font.BOLD, 13);
		this.setFont(f);

		// menu bar
		MenuBar menuBar = new MenuBar();
		this.setMenuBar(menuBar);
		// categories
		Menu graphs = new Menu("Graph");
		graphs.addActionListener(this);
		// adding to the tag graphs
		MenuItem addNode = new MenuItem("Add node");
		addNode.addActionListener(this);
		MenuItem delNode = new MenuItem("Delete node");
		delNode.addActionListener(this);
		MenuItem connect = new MenuItem("Connect");
		connect.addActionListener(this);
		graphs.add(connect);
		graphs.add(addNode);
		graphs.add(delNode);

		// categories
		Menu files = new Menu("File");
		files.addActionListener(this);
		// adding to the tag files
		MenuItem saveFile = new MenuItem("Save graph");
		saveFile.addActionListener(this);
		MenuItem loadFile = new MenuItem("Load to graph");
		loadFile.addActionListener(this);
		files.add(saveFile);
		files.add(loadFile);

		// categories
		Menu algorithms = new Menu("Algorithms");
		algorithms.addActionListener(this);
		// adding to the tag algorithms
		MenuItem isConnected = new MenuItem("isConnected");
		isConnected.addActionListener(this);
		MenuItem shortPath = new MenuItem("shortestPath");
		shortPath.addActionListener(this);
		MenuItem shortPathWeight = new MenuItem("shortestPathWeight");
		shortPathWeight.addActionListener(this);
		MenuItem tsp = new MenuItem("TSP");
		tsp.addActionListener(this);
		algorithms.add(tsp);
		algorithms.add(shortPath);
		algorithms.add(shortPathWeight);
		algorithms.add(isConnected);

		// adding to the menu bar
		menuBar.add(files);
		menuBar.add(algorithms);
		menuBar.add(graphs);

		// setting the text field
		weight.setText("weight");
		weight.setBounds(320, 20, 45, 20);
		this.add(weight);
		text_src.setText("src");
		text_src.setBounds(380, 20, 40, 20);
		this.add(text_src);
		text_dest.setText("dest");
		text_dest.setBounds(440, 20, 40, 20);
		this.add(text_dest);
		// button
		this.button.setBounds(500, 20, 40, 20);
		this.button.addActionListener(this);
		this.add(button);
		this.add(input);

		// label of list targets
		this.add(label);
		this.addMouseListener(this);

		repaint();

	}

	public void paint(Graphics g1) {
		super.paint(g1);
		// Graphics g1 = panel.getGraphics();
		g1.setFont(new Font("David", 10, 14));
		if (this.graph.getGraph() != null) {
			utils.Point3D current = null;
			for (node_data key : this.graph.getGraph().getV()) {
				g1.setColor(Color.black);
				Collection<edge_data> col = this.graph.getGraph().getE(key.getKey());
				// drawing the current dot
				current = key.getLocation();
				g1.fillOval((int) current.ix() + X_OFFSET, (int) (current.iy() + Y_OFFSET), 8, 8);
				// drawing the id of the node
				g1.drawString(key.getKey() + "", (int) current.ix() + X_OFFSET, (int) (current.iy() + Y_OFFSET));
				// going over the nei of the key, to draw lines between them
				for (edge_data edge : col) {
					int dest = edge.getDest();
					node_data toBe = this.graph.getGraph().getNode(dest);
					utils.Point3D toBe_p = toBe.getLocation();
					g1.setColor(Color.lightGray);
					// line
					g1.drawLine((int) current.ix() + X_OFFSET, (int) (current.iy() + Y_OFFSET),
							(int) toBe_p.ix() + X_OFFSET, (int) (toBe_p.iy() + Y_OFFSET));
					// the weight
					g1.setColor(Color.blue);
					g1.setFont(new Font("David", 10, 14));
					g1.drawString(edge.getWeight() + "", (int) ((current.ix() + X_OFFSET + toBe_p.ix() + X_OFFSET) / 2),
							(int) (current.iy() + Y_OFFSET + toBe_p.iy() + Y_OFFSET) / 2);
				}
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String str = e.getActionCommand();
		setTextFalse();
		switch (str) {
		case "Connect": {
			button.setBounds(370, 60, size.width, size.height);
			button.setText("connect");
			button.setVisible(true);
			text_dest.setVisible(true);
			text_src.setVisible(true);
			this.weight.setVisible(true);
			break;
		}
		case "connect": {
			int src = 0;
			int dest = 0;
			int weight = 0;
			try {
				src = Integer.parseInt(text_src.getText());
				dest = Integer.parseInt(text_dest.getText());
				weight = Integer.parseInt(this.weight.getText());
				this.graph.getGraph().connect(src, dest, weight);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "src and dest suppoused to be an integer", "Error",
						JOptionPane.DEFAULT_OPTION);
			}
			setFalse();
			repaint();
			break;

		}
		case "Add node": {
			button.setText("Add id");
			text_src.setText("id");
			button.setBounds(370, 60, size.width, size.height);
			text_src.setVisible(true);
			button.setVisible(true);

			break;
		}
		case "Add id": {
			try {
				id = Integer.parseInt(text_src.getText());
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Nodes must be an integer", "Error", JOptionPane.DEFAULT_OPTION);
			}
			setFalse();
			button.setBounds(370, 60, size.width, size.height);
			button.setText("Add point");
			text_src.setVisible(true);
			button.setVisible(true);
			text_src.setText("x");
			text_dest.setText("y");
			text_dest.setVisible(true);

			break;
		}
		case "Add point": {
			double x = 0;
			double y = 0;
			try {
				x = Double.parseDouble(text_src.getText());
				y = Double.parseDouble(text_dest.getText());
				point = new Point3D(x, y);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "x and y suppoused to be a double", "Error",
						JOptionPane.DEFAULT_OPTION);
			}
			setTextFalse();
			setFalse();
			this.graph.getGraph().addNode(new Node(id, point));
			repaint();
			break;
		}
		case "Delete node": {
			label.setText("Enter the node you want to delete: (id) ");
			text_src.setVisible(true);
			button.setText("Delete");
			button.setBounds(370, 60, size.width, size.height);
			button.setVisible(true);
			break;
		}
		case "TSP": {
			// for the way it shows
			this.setLayout(null);
			label.setBounds(200, 30, 200, 30);
			label.setText("Please enter a list of targets: ");
			input.setBounds(200, 60, 150, 25);
			input.setText("");
			button.setText("submit list");
			label.setVisible(true);
			button.setBounds(370, 60, size.width, size.height);
			input.setVisible(true);
			button.setVisible(true);
			break;
		}
		case "Delete": {
			int src = 0;
			try {
				src = Integer.parseInt(text_src.getText());
				this.graph.getGraph().removeNode(src);

			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Nodes must be an integer", "Error", JOptionPane.DEFAULT_OPTION);
			}
			setFalse();
			repaint();
			break;
		}

		case "submit list": {
			String src = input.getText();
			List<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < src.length(); i++) {
				if (src.charAt(i) != ',') {
					list.add((int) src.charAt(i) - 48);
				}
			}
			List<node_data> newList = this.graph.TSP(list);
			setFalse();
			JOptionPane.showMessageDialog(null, newList, "TSP: ", JOptionPane.DEFAULT_OPTION);
			break;
		}
		case "Save graph": {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Specify a file to save");

			int userSelection = fileChooser.showSaveDialog(this);
			if (userSelection == JFileChooser.APPROVE_OPTION) {
				File fileToSave = fileChooser.getSelectedFile();
				graph.save(fileToSave.getAbsolutePath());
			}

			break;
		}
		case "Load to graph": {
			String ans = "";

			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int returnVal = chooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {

				ans = chooser.getSelectedFile().getAbsolutePath();
			}
			this.graph.init(ans);
			repaint();
			break;
		}
		case "isConnected": {
			JOptionPane.showMessageDialog(null, this.graph.isConnected(), "Is the graph connected?",
					JOptionPane.DEFAULT_OPTION);

			break;
		}
		case "shortestPath": {
			button.setText("submit(path)");
			button.setBounds(370, 60, size.width, size.height);
			setTrue(); // calling to set the button to visible
			break;
		}

		case "shortestPathWeight": {
			button.setText("submit(weight)");
			button.setBounds(370, 60, size.width, size.height);
			setTrue(); // calling to set the button to visible
			break;
		}

		case "submit(weight)": {
			int dest = 0, src = 0;
			try {
				src = Integer.parseInt(text_src.getText());
				dest = Integer.parseInt(text_dest.getText());
				JOptionPane.showMessageDialog(null, this.graph.shortestPathDist(src, dest), "The shortest cost:",
						JOptionPane.DEFAULT_OPTION);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Nodes must be an integer", "Error", JOptionPane.DEFAULT_OPTION);
			}
			setFalse();
			break;
		}
		case "submit(path)": {
			int dest = 0, src = 0;
			try {
				src = Integer.parseInt(text_src.getText());
				dest = Integer.parseInt(text_dest.getText());
				List<node_data> temp = this.graph.shortestPath(src, dest);
				if (temp != null) {
					this.graph.reverse(temp);
					JOptionPane.showMessageDialog(null, temp, "The shortest path:", JOptionPane.DEFAULT_OPTION);
				} else
					JOptionPane.showMessageDialog(null, "There is no path", "The shortest path:",
							JOptionPane.DEFAULT_OPTION);

			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Nodes must be an integer", "Error", JOptionPane.DEFAULT_OPTION);
			}
			setFalse();

		}
		}
	}

	private void setTextFalse() {
		weight.setVisible(false);
		label.setVisible(false);
		text_src.setVisible(false);
		text_dest.setVisible(false);
		button.setVisible(false);
		input.setVisible(false);

	}

	// function to set all the buttons and text fields to false;
	private void setFalse() {
		weight.setText("weight");
		text_src.setText("src");
		text_dest.setText("dest");
		weight.setVisible(false);
		label.setVisible(false);
		text_src.setVisible(false);
		text_dest.setVisible(false);
		button.setVisible(false);
		input.setVisible(false);
	}

	// function to set the parameters that we need to visible;
	private void setTrue() {
		button.setVisible(true);
		text_src.setVisible(true);
		text_dest.setVisible(true);
	}
}
