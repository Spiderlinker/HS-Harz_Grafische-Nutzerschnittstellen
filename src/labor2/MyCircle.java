package labor2;

import java.io.DataInputStream;
import java.io.IOException;

import javafx.scene.canvas.GraphicsContext;

public class MyCircle extends MyGrfObject {

	private int x1;
	private int y1;
	private int radius;

	@Override
	void paint(GraphicsContext gc) {
		super.paint(gc);
		gc.strokeOval(x1 - radius, y1 - radius, radius >> 1, radius * 2);
	}

	@Override
	boolean loadFromData(DataInputStream din) throws IOException {

		this.x1 = din.readInt();
		this.y1 = din.readInt();
		this.radius = din.readInt();

		this.readColorAndLineWidth(din);
		return areValuesValid();
	}

	private boolean areValuesValid() {
		return x1 >= 0 && y1 >= 0;
	}

	@Override
	public String getTyp() {
		return "Kreis";
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
			return Integer.toString(radius);
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
			return Integer.toString(radius);
		default:
			return "-";
		}
	}

} // class MyCircle
