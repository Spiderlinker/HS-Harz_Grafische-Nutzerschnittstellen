package labor2;

import java.io.DataInputStream;
import java.io.IOException;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class MyGrfObject {

	protected Color c;
	protected int linewidth;
	protected int colorInt;

	void paint(GraphicsContext gc) {
		// default implementation
		gc.setLineWidth(this.linewidth);
		gc.setStroke(this.c);
	}

	abstract boolean loadFromData(DataInputStream din) throws IOException;

	protected void readColorAndLineWidth(DataInputStream din) throws IOException {
		this.c = convertColor(din.readInt());
		this.linewidth = din.readInt();
	}

	protected Color convertColor(int color) {
		this.colorInt = color;

		int red = (color & 0xFF0000) >> 16;
		int green = (color & 0xFF00) >> 8;
		int blue = (color & 0xFF);

		return Color.rgb(red, green, blue);
	}

	public int getColorInt() {
		return this.colorInt;
	}

	public Color getFarbe() {
		return this.c;
	}

	public int getStrichstaerke() {
		return this.linewidth;
	}

	public abstract String getTyp();

	public abstract int getMaxColumn();

	public abstract String getX(int column);

	public abstract String getY(int column);

} // MyGrfObject
