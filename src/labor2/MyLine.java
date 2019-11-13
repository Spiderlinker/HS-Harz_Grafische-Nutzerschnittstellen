package labor2;

import java.io.DataInputStream;
import java.io.IOException;

import javafx.scene.canvas.GraphicsContext;

public class MyLine extends MyGrfObject {

	private int x1;
	private int y1;
	private int x2;
	private int y2;

	@Override
	void paint(GraphicsContext gc) {
		super.paint(gc);
		gc.strokeLine(x1, y1, x2, y2);
	}

	@Override
	boolean loadFromData(DataInputStream din) throws IOException {

		this.x1 = din.readInt();
		this.y1 = din.readInt();
		this.x2 = din.readInt();
		this.y2 = din.readInt();

		this.readColorAndLineWidth(din);
		return areValuesValid();
	}

	private boolean areValuesValid() {
		return x1 >= 0 && y1 >= 0 && x2 >= 0 && y2 >= 0;
	}

	@Override
	public String getTyp() {
		return "Linie";
	}

	@Override
	public int getMaxColumn() {
		return 2;
	}

	@Override
	public String getX(int column) {
		switch (column) {
		case 0:
			return Integer.toString(x1);
		case 1:
			return Integer.toString(x2);
		default:
			return "-";
		}
	}

	@Override
	public String getY(int column) {
		switch (column) {
		case 0:
			return Integer.toString(y1);
		case 1:
			return Integer.toString(y2);
		default:
			return "-";
		}
	}

} // class Line
