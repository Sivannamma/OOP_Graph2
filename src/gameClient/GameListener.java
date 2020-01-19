package gameClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.Robot;
import dataStructure.node_data;

public interface GameListener {
	public void updateGUI(List<String> list, List<String> list2) throws JSONException;

	public void setGraphFromClient(Graph_Algo g);

	public ArrayList<node_data> auto_mode(game_service game, int i) throws JSONException;

	public HashMap<Integer, Robot> getRobot();

	public void setRobot(List<String> robots) throws JSONException;

	public void setRobotSrc();

	public GameServer setGameServer(game_service game, GameServer games) throws JSONException;

	public void upDateFruit(List<String> f) throws JSONException;
}
