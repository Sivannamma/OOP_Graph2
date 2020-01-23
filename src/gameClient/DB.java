package gameClient;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class DB extends JFrame {
	public static final String jdbcUrl = "jdbc:mysql://db-mysql-ams3-67328-do-user-4468260-0.db.ondigitalocean.com:25060/oop?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
	public static final String jdbcUser = "student";
	public static final String jdbcUserPassword = "OOP2020student";
	private int id;
	private JLabel lab;

	public DB() {
		this.id = 208479311;
		init();
		lab = new JLabel("--");

	}

	public void init() {
		this.setSize(800, 800);
		// setting that the program is terminated when we close 'X' on the window as
		// well.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// not resizeable
		this.setResizable(false);

		this.setLayout(new GridLayout(11, 4)); // font
		Font f = new Font("Arial", Font.BOLD, 15);
		this.setFont(f);
		// LEVEL- array that display the levels we need to check from the server
		int[] level = { 0, 1, 3, 5, 9, 11, 13, 16, 19, 20, 23 };
		// OPTIMAL MOVES- cant go above the optimal moves in order to pass the level
		int[] optimal_moves = { 290, 580, 580, 500, 580, 580, 580, 290, 580, 290, 1140 };

		for (int i = 0; i < 11; i++) {
			int[] arr = data(level[i], optimal_moves[i]);
			for (int j = 0; j < 5; j++) {
				lab = new JLabel("rr");
				switch (j) {
				case 0: {
					lab.setText("Level: " + level[i]);
					break;
				}
				case 1: {
					lab.setText("Grade: " + arr[j]);
					break;
				}
				case 2: {
					lab.setText("Moves: " + arr[j]);
					break;
				}
				case 3: {
					lab.setText("Rank in class: " + arr[j]);
					break;
				}
				case 4: {
					lab.setText("Games played: " + arr[j]);
				}
				}
				lab.setVisible(true);
				this.add(lab);

			}
		}

	}

	public int[] data(int level, int optinal_m) {
		int maxScore = 0;
		int maxMove = 0;
		int rank = 0;
		int gamePlayed = 0;
		int countPPL = 0;
		int[] arr = new int[5];
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
			Statement statement = connection.createStatement();
			String allCustomersQuery = "SELECT * FROM Logs WHERE UserID =" + id + " AND levelID = " + level + ";";
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);
			while (resultSet.next()) {
				if (resultSet.getInt("score") > maxScore && resultSet.getInt("moves") <= optinal_m) {
					maxScore = resultSet.getInt("score");
					maxMove = resultSet.getInt("moves");
				}
				gamePlayed++;
			}

			allCustomersQuery = "SELECT * FROM Logs WHERE levelID = " + level + ";";
			resultSet = statement.executeQuery(allCustomersQuery);
			while (resultSet.next()) {
				if (maxScore < resultSet.getInt("score") && resultSet.getInt("moves") >= optinal_m)
					map.put(resultSet.getInt("UserID"), resultSet.getInt("score"));
			}

			resultSet.close();
			statement.close();
			connection.close();
		}

		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		arr[0] = -1;
		arr[1] = maxScore;
		arr[2] = maxMove;
		arr[3] = map.size();
		arr[4] = gamePlayed;
		return arr;
	}

}
