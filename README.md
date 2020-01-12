Project writes: Sivan namah azari, Elad vainbrand
project name : Graph

Project description: this project support creating Nodes that represents dots of the graph, Edges that represents the line connecting two nodes , Graph that is the combination of nodes and edges.
In this class you can use a gui window to navigate through the project, helping you to use the functions in an easy way.

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

Technologies- for this project we used eclipse editor, using java language.
