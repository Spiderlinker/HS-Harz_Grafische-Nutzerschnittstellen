package labor2;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Middleware {

	private List<MyGrfObject> grafikliste = new ArrayList<>(); // alles richtig?
	private String filename;
	private int height = 0;
	private int width = 0;

	public Middleware() {
		// Default Konstruktor überschreiben
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return this.filename;
	}

	public List<MyGrfObject> getGrafikliste() {
		return grafikliste;
	}

	public void readFile(String filename) {
		setFilename(filename);

		try (FileInputStream fin = new FileInputStream(filename); //
				DataInputStream din = new DataInputStream(fin)) {

			// MyHeader erzeugen
			MyHeader myHeader = new MyHeader();
			boolean isHeaderValid = myHeader.loadFromData(din);

			// Wenn der Header gültig ist, sollen die Grafikobjekte eingelesen werden,
			// die noch in der restlichen Datei enthalten sind
			if (isHeaderValid) {
				// Headerwerte holen
				setValuesOfHeader(myHeader);
				// restlichen Grafikobjete lesen und erstellen
				readGraficObjects(din);
			}

		} catch (IOException e) {
			System.err.println("Fehler beim Einlesen der Datei: " + filename + "\n" + e);
			e.printStackTrace();
		}

	} // readFile

	/**
	 * Holen und Setzen der Breite und Höhe vom erzeugten MyHeader
	 * 
	 * @param myHeader eingelesener MyHeader mit Breite und Höhe
	 */
	private void setValuesOfHeader(MyHeader myHeader) {
		// Header ist gültig. Höhe und Breite holen und setzen
		this.width = myHeader.getWidth();
		this.height = myHeader.getHeight();
	}

	/**
	 * Einlesen von Grafikobjekten über den gegeben InputStream
	 * 
	 * @param din InputStream zum Einlesen von Grafikobjekten
	 * @throws IOException Fehler beim Lesen
	 */
	private void readGraficObjects(DataInputStream din) throws IOException {
		// Kennung des Grafikobjektes holen und
		// entsprechendes Objekt erstellen
		try {
			short kennung = 0;
			while ((kennung = din.readShort()) != 0) {
				createGraficObject(kennung, din);
			}
		} catch (IllegalArgumentException e) {
			Basis.errorBox(e.getMessage(), "Fehler beim erzeugen des Grafikobjektes!");
		}
	}

	/**
	 * Ergeugen eines Grafikobjektes anhand der gegebenen Kennung
	 * 
	 * @param kennung Kennung des zu erzeugenden Objektes
	 * @param din     InputStream zum Einlesen der Werte für das Grafikobjekt
	 * @throws IOException Fehler beim Lesen
	 */
	private void createGraficObject(short kennung, DataInputStream din) throws IOException {

		MyGrfObject graficObject = null;

		switch (kennung) {
		case Konstanten.C_LINE:
			graficObject = new MyLine();
			break;
		case Konstanten.C_RECT:
			graficObject = new MyRect();
			break;
		case Konstanten.C_CIRCLE:
			graficObject = new MyCircle();
			break;
		case Konstanten.C_TRIANGLE:
			graficObject = new MyTriangle();
			break;
		case Konstanten.C_POLYLINE:
			graficObject = new MyPolyline();
			break;
		default: // Unbekannte Kennung
			System.err.println("Falsche Kennung: " + kennung);
			throw new IllegalArgumentException("Unbekannte Kennung: " + kennung);
		}

		if (graficObject != null) {
			boolean validCoordinates = graficObject.loadFromData(din);

			if (!validCoordinates) {
				System.err.println("Negative Koordinaten! " + graficObject.getClass().getSimpleName());
				throw new IllegalArgumentException(
						"Negative Koordinaten beim Lesen von: " + graficObject.getClass().getSimpleName());
			}

			this.grafikliste.add(graficObject);
		}
	}

}
