package de.hsharz.labor1;

import java.io.DataInputStream;
import java.io.IOException;

import javafx.scene.canvas.GraphicsContext;

public class MyPolyline extends MyGrfObject {

	private int anzahlKoordinaten;
	private double[] x;
	private double[] y;

	@Override
	void paint(GraphicsContext gc) {
		super.paint(gc);
		gc.strokePolyline(x, y, anzahlKoordinaten);
	}

	@Override
	boolean loadFromData(DataInputStream din) throws IOException {

		anzahlKoordinaten = din.readInt();
		x = new double[anzahlKoordinaten];
		y = new double[anzahlKoordinaten];

		for (int i = 0; i < anzahlKoordinaten; i++) {
			x[i] = din.readInt();
			y[i] = din.readInt();
		}

		this.readColorAndLineWidth(din);
		return areValuesValid();
	}

	private boolean areValuesValid() {
		boolean valuesValid = true;

		for (int i = 0; i < anzahlKoordinaten; i++) {
			double x = this.x[i];
			double y = this.y[i];

			if (x < 0 || y < 0) {
				valuesValid = false;
				break;
			}
		}

		return valuesValid;
	}

} // class Polyline
