package gameClient;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.Fruit;
import dataStructure.Robot;
import dataStructure.node_data;

public interface GameListener {
	public void updateGUI(List<String> list, List<String> list2) throws JSONException;

	public void setGraphFromClient(Graph_Algo g);

	public ArrayList<node_data> auto_mode(game_service game, int i) throws JSONException;

	public HashMap<Integer, Robot> getRobot();

	public void setRobot(List<String> robots) throws JSONException;

	public GameServer setGameServer(game_service game, GameServer games) throws JSONException;

	public void manualDrawRobot(game_service game);

	public void mouseClicked(MouseEvent e);

	public void setKML(KML_Logger myKML);

	public HashMap<String, Fruit> getFruitss();

	public void setFruit(List<String> fruits) throws JSONException;

	public void setFalseFruit(String string);

	public void setFruitss(HashMap<String, Fruit> fruitss);

}
