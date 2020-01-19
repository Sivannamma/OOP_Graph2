package gameClient;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import utils.Point3D;

/**
 * 
 * @authors Lee Fingerhut and Rapheal Gozlan
 *
 */
public class KML_Logger {

	private int level;
	private StringBuilder w;

	/**
	 * Constructors
	 */
	public KML_Logger() {
	}

	public KML_Logger(int level_game) {
		this.level = level_game;
		w = new StringBuilder();
		StartKML();
	}

	/**
	 * Sets a mark the all objects of the game to KML file
	 */
	public void StartKML() {
		w.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
				+ "<kml xmlns=\"http://earth.google.com/kml/2.2\">\r\n" + "  <Document>\r\n" + "    <name>"
				+ "Game Namber :" + this.level + "</name>" + "\r\n" + " <Style id=\"node\">\r\n"
				+ "      <IconStyle>\r\n" + "        <Icon>\r\n"
				+ "          <href>hhttp://maps.google.com/mapfiles/kml/pushpin/pink-pushpin.png</href>\r\n"
				+ "        </Icon>\r\n" + "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n"
				+ "      </IconStyle>\r\n" + "    </Style>" + " <Style id=\"banana\">\r\n" + "      <IconStyle>\r\n"
				+ "        <Icon>\r\n"
				+ "          <href>http://maps.google.com/mapfiles/kml/shapes/convenience.png</href>\r\n"
				+ "        </Icon>\r\n" + "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n"
				+ "      </IconStyle>\r\n" + "    </Style>" + " <Style id=\"apple\">\r\n" + "      <IconStyle>\r\n"
				+ "        <Icon>\r\n" + "          <href>http://maps.google.com/mapfiles/kml/shapes/poi.png</href>\r\n"
				+ "        </Icon>\r\n" + "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n"
				+ "      </IconStyle>\r\n" + "    </Style>" + " <Style id=\"robot\">\r\n" + "      <IconStyle>\r\n"
				+ "        <Icon>\r\n"
				+ "          <href>http://maps.google.com/mapfiles/kml/shapes/cycling.png></href>\r\n"
				+ "        </Icon>\r\n" + "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n"
				+ "      </IconStyle>\r\n" + "    </Style>");
	}

	/**
	 * Function to add placemark by id
	 * 
	 * @param id       - which type obj - fruit/banna/robot
	 * @param location - the location of the object
	 */
	public void PlaceMark(String id, Point3D location) {
		LocalDateTime time = LocalDateTime.now();
		w.append("    <Placemark>\r\n" + "      <TimeStamp>\r\n" + "        <when>" + time + "</when>\r\n"
				+ "      </TimeStamp>\r\n" + "      <styleUrl>#" + id + "</styleUrl>\r\n" + "      <Point>\r\n"
				+ "         <coordinates>" + location.x() + "," + location.y() + "</coordinates>\r\n"
				+ "      </Point>\r\n" + "    </Placemark>\r\n");
	}

	/**
	 * Function to close KML file and save it on data folder
	 */
	public void EndAndSave_KML() {
		w.append("  \r\n</Document>\r\n" + "</kml>");
		try {
			File file = new File(this.level + ".kml");
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(w.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}