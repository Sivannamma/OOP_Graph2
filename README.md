### Project writes: Sivan namah azari, Elad vainbrand.
### project name : Robot collector Graph.

* Project description: this project is a follow up of the first project called graph. 
The project is about robots that can move through the graph, collecting two types of fruit (banana, apple) which each of them has a value, the point is to collect more fruits.
In the game we have 24 levels, that are numbered from 0-23 include.

### This project is mainly about two methods we can use thorugh graphical interface, using jframe window.
1. The first one- automatical game : in this option the robots start in the best position for them to collect all the fruits. And they move trought the graph using shortPath algorithm in order to move arounf through the nodes.
2. The second - manual game : in this option the user decides where to position the robots , and the nodes which he wants to move the robots to.

* For this project we use Jframe window, that implements several things which allows us to operate the game behind the scene.

### We added few more classes in addition to the previous project "Graph".
1. Fruit class - which represents all the data we need in order to place the fruits on the window, and to let each robot collect a certain fruit.
2. Robot class- which represent ech robot in the level.
3. GameServer- which represents the level itself and the data we can collect from it (total grade, the map, the number of fruits, the number of robots, etc'..)
4. GameClient, GameManager, GameListiner - those classes are implementation that help us run the automatical game.

### Graph project is about :

Node is a class that implements from an interface called node_data which includes the following function:
1. public int getKey();
2. public Point3D getLocation();
3. public void setLocation(Point3D p);
4. public double getWeight();
5. public void setWeight(double w);
6. public String getInfo();
7. public void setInfo(String s);
8. public int getTag();
9. public void setTag(int t);

Edge is a class that implements from an interface called edge_data , which includes the following functions:
1. public int getSrc();
2. public int getDest();
3. public double getWeight();
4. public String getInfo();
5. public void setInfo(String s);
6. public int getTag();
7. public void setTag(int t);

Graph_algo is a class   that implements from an interface called graph_algorithms,which includes the following functions:
1. public void init(graph g);
2. public graph copy();
3. public void init(String file_name);
4. public void save(String file_name);
5. public boolean isConnected();
6. public double shortestPathDist(int src, int dest);
7. public List<node_data> shortestPath(int src, int dest);
8. public List<node_data> TSP(List<Integer> targets);


Those functions implements Serializable which lets us read/save from a file.
In addition we created some inside private functions that helps us implements the override methods from the interface.

GUI_window -this class summaries all the classes and what we can perform on them.
This class has a variable (graph) that on it we can perform several functions.
In this class we can read functions from file into our gui window.
We can show  the graph on the gui window.

Exceptions in the project:
1. each node has key, we cannot create the same node with the same id.
2. when connecting two nodes with an edge, we must check that those nodes exsist in the graph, otherwise the connection wont be done.
3. each edge has a weight, which going on it means the cost of the path, the cost cannot be negative number.

For a diffrent input, the system will throw an exception.

### Operation- 
* Run the gui window through the main, chose one of a level, Automatical game, Manual game, then the user need to pick a level, afterwards the level will be present on the gui window.
* If u chose the automatical game, you just need press the run button, and the game will initialize itself, placing robots, and moving them torwads the fruits.
* If you chose the manual game, u'll need to place the amount of robots that the pop up window will tell. afterwards u'll need to press the start button, and follow the intruction, click a robot and then click a destination, in order to move them through the graph. this proccess might take a while, u need to make sure the robot in the place after clicking another robot.

Technologies- for this project we used eclipse editor, using java language.
