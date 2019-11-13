package labor1;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*; // javafx.scene.layout.Pane
import javafx.stage.Stage;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.scene.paint.Color;

import javafx.scene.control.Separator;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import javafx.scene.input.KeyCombination;

import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.Event;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

import javafx.collections.*; // "   ObservableList<T>    getItems()

import java.io.*;

import javafx.beans.value.*; // ChangeListener

import javafx.scene.paint.*;
import javafx.scene.canvas.*;

public class Labor1 extends Application implements EventHandler {

    private Stage stage = null;

    private MenuItem menuOpen = new MenuItem("Open File...");
    private MenuItem menuClose = new MenuItem("Close");

    private Button bnOpen = new Button("Open");

    private Label editorstatus = new Label("-");

    private TabPane tabpane = null;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        BorderPane root = new BorderPane();
        root.setTop(setTopElements());
        root.setCenter(setCenterElements());
        root.setBottom(setBottomElements());

        Scene scene = new Scene(root, 760, 800);
        scene.getStylesheets().add("Labor1.css");
        stage.setTitle("UILabor1");
        stage.setScene(scene);
        stage.show();
    }

    private Pane setTopElements() {
        VBox vbox = new VBox(22);
        vbox.setAlignment(Pos.CENTER);
        vbox.setFillWidth(true);

        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        menuBar.getMenus().add(menuFile);

        menuOpen.setOnAction(this);
        menuClose.setOnAction(this);

        menuOpen.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));

        menuFile.getItems().addAll(menuOpen, menuClose);

        vbox.getChildren().add(menuBar);

        bnOpen.setFont(new Font(22));

        bnOpen.setOnAction(this);

        ToolBar toolBar = new ToolBar(bnOpen);
        vbox.getChildren().add(toolBar);

        return vbox;
    }

    private Pane setBottomElements() {
        FlowPane boxpane = new FlowPane(20, 20);
        boxpane.setAlignment(Pos.BOTTOM_LEFT);
        boxpane.setMaxWidth(Double.POSITIVE_INFINITY);

        editorstatus.setFont(new Font("Courier New", 22));
        editorstatus.setMaxWidth(Double.POSITIVE_INFINITY);
        boxpane.getChildren().add(editorstatus);
        FlowPane.setMargin(editorstatus, new Insets(0, 10, 10, 10)); // TRBL

        return boxpane;
    }

    private TabPane setCenterElements() {
        tabpane = new TabPane();
        tabpane.setTabMinHeight(22);
        tabpane.setTabMaxHeight(44);

        tabpane.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab told,
                            Tab tnew) {
                        if (tnew != null) {
                            MyCanvas canvas = null;
                            // editorstatus.setText(???.getFilename());
                        }
                    }
                });
        return tabpane;
    }

    private void insertTab(String filename) {
        MyCanvas canvas = new MyCanvas(filename);
        String caption = "noname";
        if (filename.length() > 0)
            caption = Basis.getFileNameExt(filename);
        Tab tab = new Tab(caption);
        tab.setTooltip(new Tooltip(filename)); // javafx.scene.control.Tooltip
        tab.setContent(new ScrollPane(canvas));
        tabpane.getTabs().add(tab);
        canvas.paint();
    }

    @Override
    public void handle(Event e) {
        if (e.getSource() == menuOpen || e.getSource() == bnOpen) {
            openFile();
        }

        if (e.getSource() == menuClose) {
            Platform.exit();
        }

    }

    // setSide(Side value) javafx.geometry.Side
    // BOTTOM LEFT RIGHT TOP

    private void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.setInitialDirectory(new File(
                "C:\\Users\\Oliver\\Daten\\Uni\\HS Harz (Wernigerode)\\3. Semester\\Grafische Nutzerschnittstellen\\Labor 1"));
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Graphic Files", "*.grf"),
                new ExtensionFilter("All Files", "*.*"));
        List<File> list = fileChooser.showOpenMultipleDialog(stage);
        if (list != null) {
            for (File file : list) {
                insertTab(file.getPath());
            }
        }
    }

    private Tab getActualTab() {
        ObservableList<Tab> tabs = tabpane.getTabs(); // javafx.collections.*;
        if (tabs.size() > 0) {
            for (Tab tab : tabs) {
                if (tab.isSelected()) {
                    return tab;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    public static void main(String[] argv) {
        launch(argv);
    }
}
