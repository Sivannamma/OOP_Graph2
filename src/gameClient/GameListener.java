package gameClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import Server.game_service;
import dataStructure.Robot;
import dataStructure.node_data;

public interface GameListener {
	public void updateGUI(List<String> list, List<String> list2) throws JSONException;

	public ArrayList<node_data> auto_mode(game_service game, int i) throws JSONException;

	public HashMap<Integer, Robot> getRobot();

	public void setRobot(List<String> robots) throws JSONException;

	public GameServer setGameServer(game_service game, GameServer games) throws JSONException;
}
