package labor2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import javafx.scene.text.Font;


import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCombination;
import javafx.geometry.Insets;
import java.io.*;
import java.util.*;
import javafx.collections.*;  // "   ObservableList<T>    getItems()

import javafx.beans.value.*;  // ChangeListener


public class Labor2 extends Application {
	   private Stage stage = null;
	   
	    private  MenuItem menuOpen = new MenuItem("Open Canvas...");
	    private  MenuItem menuClose = new MenuItem("Close");
	    private  MenuItem menuOpenTableView = new MenuItem("Neue Tabelle");

	    private Button bnOpen = new Button("Open");
	    private Button bnOpenTableView = new Button("Open TableView");

	    private Label editorstatus= new Label("-");

	    private   TabPane tabpane = null;
	    
	     @Override 
	     public void start(Stage stage) {
	            this.stage = stage;
	            BorderPane root = new BorderPane();   
	            root.setTop(  setTopElements()  );
	            root.setCenter(  setCenterElements()  );
	            root.setBottom(  setBottomElements()  );

	            Scene scene= new Scene(root, 760, 800);
	            scene.getStylesheets().add("UILabor2.css");
	            stage.setTitle("UILabor2");        
	            stage.setScene(scene);        
	            stage.show();  
	    }

	    private Pane  setTopElements() {
	            VBox vbox = new VBox(22);       
	            vbox.setAlignment(Pos.CENTER);
	            vbox.setFillWidth(true);

	            MenuBar menuBar = new MenuBar();
	            Menu menuFile = new Menu("File");
	            menuBar.getMenus().add(menuFile);

	            menuOpen.setOnAction(e->mnOpenClick());
	            menuOpenTableView.setOnAction(e->mnOpenTableClick());
	            menuClose.setOnAction(e->mnCloseClick());

	            menuOpen.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));


	            menuFile.getItems().addAll(menuOpen,menuOpenTableView, menuClose);

	            vbox.getChildren().add(menuBar);


	            bnOpen.setFont(new Font(22));
	            bnOpenTableView.setFont(new Font(22));

	            bnOpen.setOnAction(e->mnOpenClick());
	            bnOpenTableView.setOnAction(e->mnOpenTableClick());

	            ToolBar toolBar = new ToolBar(
	               bnOpen, bnOpenTableView
	             );
	            vbox.getChildren().add(toolBar);

	            return vbox ;
	    }

	  private Pane setBottomElements() {            
	            FlowPane boxpane = new FlowPane(20,20);    
	            boxpane.setAlignment(Pos.BOTTOM_LEFT);
	            boxpane.setMaxWidth(Double.POSITIVE_INFINITY);

	            editorstatus.setFont(new Font("Courier New",22));            
	            editorstatus.setMaxWidth(Double.POSITIVE_INFINITY);
	            boxpane.getChildren().add(editorstatus);
	            boxpane.setMargin(editorstatus, new Insets(0, 10, 10, 10) );  // TRBL

	            return boxpane;
	   }

	            
	    private TabPane setCenterElements() {
	         tabpane = new TabPane();
	         tabpane.setTabMinHeight(42);
	         tabpane.setTabMaxHeight(44);

	         tabpane.getSelectionModel().selectedItemProperty().addListener(
	              new ChangeListener<Tab>() {
	                   @Override
	                  public void changed(ObservableValue<? extends Tab> ov, Tab told, Tab tnew) {
	                     if (tnew!=null) {
	                       Object obj = tnew.getContent();
	                       if (obj instanceof MyTableView) {
	                         MyTableView tableview = (MyTableView)  obj;
	                         editorstatus.setText(tableview.getFilename());
	                       }
	                       else {
	                         ScrollPane scrollpane = (ScrollPane)obj;
	                         MyCanvas canvas = (MyCanvas)  scrollpane.getContent();
	                         editorstatus.setText(canvas.getFilename());
	                       }
	                     }
	                  }
	              }
	        );

	         return  tabpane;
	     }

	     
        private void mnCloseClick() {
        	Platform.exit();
        }


  	  private void mnOpenClick() {
  	        FileChooser fileChooser = new FileChooser();
  	        fileChooser.setTitle("Open File");
  	        fileChooser.setInitialDirectory( new File("C:\\Users\\Oliver\\Daten\\Programmierung\\Workspaces\\workspace_hsharz\\Grafische Nutzerschnittstellen\\src\\labor2\\files")  );
  	        fileChooser.getExtensionFilters().addAll(
  	            new ExtensionFilter("Graphic Files", "*.grf"),
  	                  new ExtensionFilter("All Files", "*.*"));
  	         List<File> list = fileChooser.showOpenMultipleDialog(stage);
  	         if (list != null) {
  	             for (File file : list) {
  	            	insertTabCanvas(file.getPath());
  	             }
  	         }
  	     }


	  private void mnOpenTableClick() {
	        FileChooser fileChooser = new FileChooser();
	        fileChooser.setTitle("Open File");
	        fileChooser.setInitialDirectory( new File("C:\\Users\\Oliver\\Daten\\Programmierung\\Workspaces\\workspace_hsharz\\Grafische Nutzerschnittstellen\\src\\labor2\\files")  );
	        fileChooser.getExtensionFilters().addAll(
	            new ExtensionFilter("Graphic Files", "*.grf"),
	                  new ExtensionFilter("All Files", "*.*"));
	         List<File> list = fileChooser.showOpenMultipleDialog(stage);
	         if (list != null) {
	             for (File file : list) {
	                insertTabTableView(file.getPath());
	             }
	         }
	     }



		private void  insertTabCanvas(String filename) {
		    MyCanvas canvas = new MyCanvas(filename);
		    String caption="noname";
		    if (filename.length()>0) caption=getFileNameExt(filename);
		    Tab tab = new Tab(caption);  
		    tab.setTooltip(new Tooltip(filename));    // javafx.scene.control.Tooltip
		    tab.setContent(new ScrollPane(canvas)); 
		    tabpane.getTabs().add(tab);
		   canvas.paint();
		  }

		private void  insertTabTableView(String filename) {
			MyTableView tableview = new MyTableView(filename);
			tableview.setStyle("-fx-font: 22px \"Serif\";");
		    
			
		    String caption="noname";
		    if (filename.length()>0) caption=getFileNameExt(filename);
		    Tab tab = new Tab(caption);  
		    tab.setTooltip(new Tooltip(filename));    // javafx.scene.control.Tooltip
		    tab.setContent(tableview); 
		    tabpane.getTabs().add(tab);
		  }


	   private Tab getActualTab() {
	      ObservableList<Tab> tabs = tabpane.getTabs();   // javafx.collections.*;
	      if (tabs.size()>0) {
	        for (Tab tab : tabs) {
	           if (tab.isSelected()) {
	              return tab;
	           }
	        }
	        return null;
	      }
	      else {
	         return null;
	      }
	   }


	 

	/**
	    * GetFileNameExt
	    * description: holt den Namen und die Extention eines Dateinamens, ohne Pfad
	    * @return String Name+Ext
	    * @param String sFileName
	    * @exception none
	    * @version      1.1, 31.01.2006
	    * @author Michael Wilhelm HS Harz, FB AI
	    * @use Example
	    * @see #CheckFileName
	    * @see #ChangeFileExt
	    * @see #getFileName extrahiert den Dateinamen ohne Pfad
	    * @see #FileExists
	    */
	    public String getFileNameExt(String filename) {
	        int k, n;
	        n = filename.length();
	        k = filename.lastIndexOf("\\");
	        if (k == -1) {
	            // test mit /
	            k = filename.lastIndexOf("/");
	            if (k == -1)
	                return filename;
	            else {
	                  // beginIndex   endIndex
	                return filename.substring(k+1, n); // ??????  n-1 ist zu klein            
	            }
	        }
	        else {
	              // beginIndex   endIndex
	            return filename.substring(k+1, n); // ??????  n-1 ist zu klein                        
	        }
	    }  // getFileNameExt



	      public static void main(String[] argv) {       
	              launch(argv);   
	      } 
	}

