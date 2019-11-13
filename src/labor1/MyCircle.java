package labor1;

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

} // class MyCircle
