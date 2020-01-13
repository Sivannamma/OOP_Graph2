package gameClient;

public class GameServer {
	private int fruits;
	private int moves;
	private int grade;
	private int robots;
	private String graph;

	public GameServer(int fruit, int moves, int grade, int robots, String graph) {
		this.fruits = fruit;
		this.moves = moves;
		this.robots = robots;
		this.grade = grade;
		this.graph = graph;
	}

	public int getFruit() {
		return fruits;
	}

	public String getGraph() {
		return graph;
	}

	public void setGraph(String graph) {
		this.graph = graph;
	}

	public void setFruit(int fruit) {
		this.fruits = fruit;
	}

	public int getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getRobots() {
		return robots;
	}

	public void setRobots(int robots) {
		this.robots = robots;
	}

}
