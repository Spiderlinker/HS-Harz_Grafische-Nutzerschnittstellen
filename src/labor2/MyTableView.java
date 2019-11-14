package labor2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.swing.text.TableView.TableRow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class MyTableView extends TableView<MyTableRow> {

	class ColorCell extends TableCell<MyTableRow, Integer> {
		@Override
		protected void updateItem(Integer item, boolean empty) {
			super.updateItem(item, empty);
			if (item != null && !empty) {
				int r = (item & 0xFF0000) >> 16;
				int g = (item & 0xFF00) >> 8;
				int b = (item & 0xFF);

				// Inverse Farben bestimmen: r2, g2, b2
				int r2 = 255 - r;
				int g2 = 255 - g;
				int b2 = 255 - b;

				Color color = Color.rgb(r, g, b);
				setBackground(
						new Background(new BackgroundFill(Color.rgb(r2, g2, b2), CornerRadii.EMPTY, Insets.EMPTY)));
				setText(r + ", " + g + ", " + b);
				setTextFill(color);
			} else {
				setText("--");
				setTextFill(Color.BLACK);
			}
		}
	}

	private Middleware middleware = null;
	private List<MyGrfObject> grafikListe;
	private ObservableList<MyTableRow> listeRows = FXCollections.observableArrayList();

	public MyTableView(String filename) {
		super();
		this.middleware = new Middleware();
		this.middleware.readFile(filename);
		readMiddlewareValues();

		this.setItems(listeRows);
		this.setStyle("-fx-font: 22px \"Serif\";");
		createColumns();
		createRows();
	}

	/**
	 * Holen der gelesenen Werte der Middleware
	 */
	private void readMiddlewareValues() {
		this.grafikListe = middleware.getGrafikliste();
	}

	public String getFilename() {
		return middleware.getFilename();
	}

	private void createColumns() {
		TableColumn<MyTableRow, String> colNr = new TableColumn<>("Nr");
		colNr.setMinWidth(20);
		colNr.setCellValueFactory(new PropertyValueFactory<MyTableRow, String>("Nr"));
		colNr.setStyle("-fx-alignment: CENTER-RIGHT");

		TableColumn<MyTableRow, String> colTyp = new TableColumn<>("Typ");
		colTyp.setMinWidth(100);
		colTyp.setCellValueFactory(new PropertyValueFactory<MyTableRow, String>("Typ"));
		colTyp.setStyle("-fx-alignment: CENTER-RIGHT");

		TableColumn<MyTableRow, Integer> colColor = new TableColumn<>("Farbe");
		colColor.setMinWidth(150);
		colColor.setCellValueFactory(new PropertyValueFactory<MyTableRow, Integer>("Color"));
		colColor.setCellFactory(new Callback<TableColumn<MyTableRow, Integer>, TableCell<MyTableRow, Integer>>() {
			@Override
			public TableCell<MyTableRow, Integer> call(TableColumn<MyTableRow, Integer> param) {
				return new ColorCell();
			}
		});
		colColor.setStyle("-fx-alignment: CENTER-RIGHT");

		TableColumn<MyTableRow, Integer> colLineWidth = new TableColumn<>("Strichstärke");
		colLineWidth.setMinWidth(150);
		colLineWidth.setCellValueFactory(new PropertyValueFactory<MyTableRow, Integer>("Strichstaerke"));
		colLineWidth.setStyle("-fx-alignment: CENTER");

		this.getColumns().addAll(colNr, colTyp, colColor, colLineWidth);

		int maxColumn = getMaxKoordinaten();
		for (int i = 0; i < maxColumn; i++) {
			TableColumn<MyTableRow, String> colX = new TableColumn<MyTableRow, String>("x" + (i + 1));
			TableColumn<MyTableRow, String> colY = new TableColumn<MyTableRow, String>("y" + (i + 1));
			colX.setCellValueFactory(new PropertyValueFactory<MyTableRow, String>("x" + (i + 1)));
			colY.setCellValueFactory(new PropertyValueFactory<MyTableRow, String>("y" + (i + 1)));
			colX.setStyle("-fx-alignment: CENTER");
			colY.setStyle("-fx-alignment: CENTER");
			colX.setMinWidth(50);
			colY.setMinWidth(50);
			getColumns().addAll(colX, colY);
		}
	}

	private void createRows() {

		int maxColumn = getMaxKoordinaten();
		for (int i = 0; i < grafikListe.size(); i++) {
			MyGrfObject myGrfObject = grafikListe.get(i);
			MyTableRow tableRow = new MyTableRow();
			tableRow.setNr(i + 1);
			tableRow.setTyp(myGrfObject.getTyp());
			tableRow.setColor(myGrfObject.getColorInt());
			tableRow.setStrichstaerke(myGrfObject.getStrichstaerke());

			for (int j = 0; j < maxColumn; j++) {

				String xValue = myGrfObject.getX(j);
				String yValue = myGrfObject.getY(j);
				try {
					MyTableRow.class.getMethod("setX" + (j + 1), String.class).invoke(tableRow, xValue);
					MyTableRow.class.getMethod("setY" + (j + 1), String.class).invoke(tableRow, yValue);
				} catch (Exception e) {
					e.printStackTrace();
				}

//				switch (j) {
//				case 0:
//					tableRow.setX1(xValue);
//					tableRow.setY1(yValue);
//					break;
//				case 1:
//					tableRow.setX2(xValue);
//					tableRow.setY2(yValue);
//					break;
//				case 2:
//					tableRow.setX3(xValue);
//					tableRow.setY3(yValue);
//					break;
//				case 3:
//					tableRow.setX4(xValue);
//					tableRow.setY4(yValue);
//					break;
//				case 4:
//					tableRow.setX5(xValue);
//					tableRow.setY5(yValue);
//					break;
//				case 5:
//					tableRow.setX6(xValue);
//					tableRow.setY6(yValue);
//					break;
//				case 6:
//					tableRow.setX7(xValue);
//					tableRow.setY7(yValue);
//					break;
//				case 7:
//					tableRow.setX8(xValue);
//					tableRow.setY8(yValue);
//					break;
//				case 8:
//					tableRow.setX9(xValue);
//					tableRow.setY9(yValue);
//					break;
//				case 9:
//					tableRow.setX10(xValue);
//					tableRow.setY10(yValue);
//					break;
//				case 10:
//					tableRow.setX11(xValue);
//					tableRow.setY11(yValue);
//					break;
//				}
			}

			listeRows.add(tableRow);
		}

	}

	private int getMaxKoordinaten() {
		return grafikListe.stream() // Liste durchgehen
				.mapToInt(MyGrfObject::getMaxColumn) // Maximalwerte von einzelnen Objekten sammeln
				.max() // Maximalwert aller Werte ermitteln
				.orElseGet(() -> 0); // Maximalwert holen, falls nicht vorhanden, dann 0
	}

}
