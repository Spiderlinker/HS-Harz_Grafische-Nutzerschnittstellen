package labor2;

import java.io.*;

public class MyHeader {

	private byte k;
	private byte i;
	private short version;
	private int width;
	private int height;

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public boolean loadFromData(DataInputStream din) throws IOException {

		this.k = din.readByte();
		this.i = din.readByte();
		this.version = din.readShort();
		this.width = din.readInt();
		this.height = din.readInt();

		return areHeaderValuesValid();
	}

	private boolean areHeaderValuesValid() {
		boolean headerValid = true;

		if (k != 75 || i != 73) {
			headerValid = false;
			System.err.println("Fehlerhafte Dateikennung!");
			Basis.errorBox("Datei enthält falsche Kennung: K: " + k + " I: " + i, "Fehlerhafter Header!");
		}
		if (version != 1) {
			headerValid = false;
			System.err.println("Fehlerhafter Header! Version ist " + version);
			Basis.errorBox("Falsche Version: " + version, "Fehlerhafter Header!");
		}
		if (width <= 0 && height <= 0) {
			headerValid = false;
			System.err.println("Fehlerhafter Header! Negative Abmessungen: " + width + "," + width);
			Basis.errorBox("Negative Abmessungen: " + width + "," + width, "Fehlerhafter Header!");
		}

		return headerValid;
	}
}
