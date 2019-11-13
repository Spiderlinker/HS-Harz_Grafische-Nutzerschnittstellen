package labor2;

import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class MyTableView extends TableView {

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
				setText( r + ", " + g + ", " + b);
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
		colColor.setCellFactory(new Callback<TableColumn<MyTableRow,Integer>, TableCell<MyTableRow, Integer>>() {
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
			System.out.println("Maximale Koordinate: " + myGrfObject.getMaxColumn());

			for (int j = 0; j < maxColumn; j++) {
				switch (j) {
				case 0:
					tableRow.setX1(myGrfObject.getX(j));
					tableRow.setY1(myGrfObject.getY(j));
					break;
				case 1:
					tableRow.setX2(myGrfObject.getX(j));
					tableRow.setY2(myGrfObject.getY(j));
					break;
				case 2:
					tableRow.setX3(myGrfObject.getX(j));
					tableRow.setY3(myGrfObject.getY(j));
					break;
				case 3:
					tableRow.setX4(myGrfObject.getX(j));
					tableRow.setY4(myGrfObject.getY(j));
					break;
				case 4:
					tableRow.setX5(myGrfObject.getX(j));
					tableRow.setY5(myGrfObject.getY(j));
					break;
				case 5:
					tableRow.setX6(myGrfObject.getX(j));
					tableRow.setY6(myGrfObject.getY(j));
					break;
				case 6:
					tableRow.setX7(myGrfObject.getX(j));
					tableRow.setY7(myGrfObject.getY(j));
					break;
				case 7:
					tableRow.setX8(myGrfObject.getX(j));
					tableRow.setY8(myGrfObject.getY(j));
					break;
				case 8:
					tableRow.setX9(myGrfObject.getX(j));
					tableRow.setY9(myGrfObject.getY(j));
					break;
				case 9:
					tableRow.setX10(myGrfObject.getX(j));
					tableRow.setY10(myGrfObject.getY(j));
					break;
				case 10:
					tableRow.setX11(myGrfObject.getX(j));
					tableRow.setY11(myGrfObject.getY(j));
					break;
				}
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
