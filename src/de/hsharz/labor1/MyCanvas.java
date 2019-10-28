package de.hsharz.labor1;

import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// Dient als Leinwand, Eclipse Problem behoben
public class MyCanvas extends Canvas {

	private static int MAX = 700;
	private int width;
	private int height;
	private Middleware middleware = null;
	private List<MyGrfObject> grafikListe;

	public MyCanvas(String filename) {
		super(MAX, MAX);
		this.middleware = new Middleware();
		this.middleware.readFile(filename);
		readMiddlewareValues();
	}

	/**
	 * Holen der gelesenen Werte der Middleware
	 */
	private void readMiddlewareValues() {
		this.grafikListe = middleware.getGrafikliste();
		this.width = middleware.getWidth();
		this.height = middleware.getHeight();
	}

	public void paint() {
		// weiﬂer Hintergrund
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.beginPath();
		gc.setFill(Color.WHITE);
		gc.clearRect(0, 0, MAX, MAX);

		// Rahmen zeichnen
		gc.rect(0, 0, width, height);
		gc.stroke();
		gc.beginPath();

		// Grafikobjekte zeichnen
		for (MyGrfObject graficObject : grafikListe) {
			graficObject.paint(gc);
			gc.stroke();
		} // for

	} // paint

}