package FullDroneGUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileFilter;

//import drone.DroneArena;
import javafx.animation.AnimationTimer;

public class DroneGUI extends Application{
	private MyCanvas mc;
	private AnimationTimer timer;
	private VBox rtPane;
	private DroneArena arena;
	
	private TargetItem t;

	
	FileFilter filter = new FileFilter() {
		@Override
		public boolean accept(File f) {
		if (f.getAbsolutePath().endsWith(".txt")) {return true;}
		if (f.isDirectory()) {return true;}
		return false;
		}
		@Override
		public String getDescription() {
			return "txt";
		}
	};
	
	/**
	 * function to show in a box ABout the program
	 */
	private void showAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);				// define what box is
	    alert.setTitle("About");									// say is About
	    alert.setHeaderText(null);
	    alert.setContentText("This is Ivy's JavaFX Demonstrator:\n"
	    		+ "You should try your best to get the highest score\n"
	    		+ "and kill all enemies as soon as possible");			// give text
	    alert.showAndWait();
	}
	/**
	 * 
	 * set up the mouse event - when mouse pressed, put item there
	 * @param canvas
	 */
	void setMouseEvents (Canvas canvas) {
	       canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 		// for MOUSE PRESSED event
	    	       new EventHandler<MouseEvent>() {
	    	           @Override
	    	           public void handle(MouseEvent e) {
	    	        	   	arena.setObstacle(e.getX(), e.getY());
	  		            	drawWorld();							// redraw world
	  		            	drawStatus();
	    	           }
	    	       }
	       );
	 }
	
	/**
	 * set up the menu of commands for the GUI
	 * @return the menu bar
	 */
	MenuBar setMenu() {
		MenuBar menuBar = new MenuBar();						// create main menu
	
		Menu mFile = new Menu("File");							// add File main menu
		MenuItem mExit = new MenuItem("Exit");					// whose sub menu has Exit
		mExit.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {					// action on exit is
	        	timer.stop();									// stop timer
		        System.exit(0);									// exit program
		    }
		});
		mFile.getItems().addAll(mExit);							// add exit to File menu
		
		MenuItem mSave = new MenuItem("Save");					// whose sub menu has Save
		mSave.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {					// action on save is
	        	JFrame frame = new JFrame();
				frame.setAlwaysOnTop(true);
				JFileChooser saveChooser = new JFileChooser();
				saveChooser.setFileFilter(filter);
				int saveReturnVal = saveChooser.showDialog(frame, null);
				if(saveReturnVal == JFileChooser.APPROVE_OPTION) {
					File saveFile = saveChooser.getSelectedFile();
					if(saveFile.isFile()) {
						System.out.println("You chose to save arena to file: "+saveFile.getName());
						try {
							FileOutputStream outFile = new FileOutputStream(saveFile);
							ObjectOutputStream outObject = new ObjectOutputStream(outFile);
							outObject.writeObject(arena);
							outObject.close();
							System.out.println("Save successfully!");
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} 
					}
				}
		        
		    }
		});
		mFile.getItems().addAll(mSave);							// add load to File menu
		
		
		MenuItem mLoad = new MenuItem("Load");					// whose sub menu has Load
		mLoad.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {					// action on load is
		    	JFileChooser loadChooser = new JFileChooser();
				int loadReturnVal = loadChooser.showOpenDialog(null);
				if(loadReturnVal == JFileChooser.APPROVE_OPTION) {
					File loadFile=loadChooser.getSelectedFile();
					File currDir=loadChooser.getCurrentDirectory();
					if(loadFile.isFile()) {
						System.out.println("You chose to load arena from file: "
								+loadFile.getName()+" in the dir "
								+currDir.getAbsolutePath());
						try {
							FileInputStream inFile = new FileInputStream(loadFile);
							ObjectInputStream inObject = new ObjectInputStream(inFile);
							arena = (DroneArena) inObject.readObject();
							System.out.println("Load successfully!");
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							System.err.println("The object is not founnd in <"+loadFile.getName()+">");
							e.printStackTrace();
						}
					} else System.out.println(loadFile.getName()+" is not a file, you should choose a file.");
				}
		    }
		});
		mFile.getItems().addAll(mLoad);
		
		Menu mHelp = new Menu("Help");							// create Help menu
		MenuItem mAbout = new MenuItem("About");				// add About sub menu item
		mAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	showAbout();									// and its action to print about
            }	
		});
		mHelp.getItems().addAll(mAbout);						// add About to Help main item
		
		
		
		menuBar.getMenus().addAll(mFile, mHelp);				// set main menu with File, Help
		return menuBar;											// return the menu
	}
	

	
	/**
	 * set up the horizontal box for the bottom with relevant buttons
	 * @return
	 */
	private HBox setButtons() {
	    Button btnStart = new Button("Start");					// create button for starting
	    btnStart.setOnAction(new EventHandler<ActionEvent>() {	// now define event when it is pressed
	        @Override
	        public void handle(ActionEvent event) {
	        	timer.start();									// its action is to start the timer
	       }
	    });

	    Button btnStop = new Button("Pause");					// now button for stop
	    btnStop.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	timer.stop();									// and its action to stop the timer
	       }
	    });

	    Button btnAddDrone = new Button("Another Drone");				// now button for stop
	    btnAddDrone.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	arena.addDrone();								// and its action to stop the timer
	          	drawWorld();
	       }
	    });
	    
	    Button btnAddObstacle = new Button("Another Obstacle");				// now button for stop
	    btnAddObstacle.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	arena.addObstacle();								// and its action to stop the timer
	          	drawWorld();
	       }
	    });
	    
	    Button btnAddAvoider = new Button("Another Avoider");				// now button for stop
	    btnAddAvoider.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	arena.addAvoider();								// and its action to stop the timer
	          	drawWorld();
	       }
	    });
	    														// now add these buttons + labels to a HBox
	    return new HBox(new Label("Run: "), btnStart, btnStop, new Label("Add: "),
	    		btnAddDrone, btnAddObstacle, btnAddAvoider);
	}
	
	/**
	 * Show the score .. by writing it at position x,y
	 * @param x
	 * @param y
	 * @param score
	 */
	//show how many times a item was hit
	public void showScore (double x, double y, int score) {
		mc.showText(x, y, Integer.toString(score));
	}
	/** 
	 * draw the world with ball in it
	 */
	public void drawWorld () {
	 	mc.clearCanvas();						// set beige colour
	 	arena.drawArena(mc);
	}
	
	/**
	 * show where item is, in pane on right
	 */
	public void drawStatus() {
		rtPane.getChildren().clear();					// clear rtpane
		ArrayList<String> allDs = arena.describeAll();
		for (String s : allDs) {
			Label l = new Label(s); 		// turn description into a label
			rtPane.getChildren().add(l);	// add label	
		}	
	}

	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Ivy's attempt at Moving Drone");
	    BorderPane dp = new BorderPane();
	    dp.setPadding(new Insets(10, 20, 10, 20));

	    dp.setTop(setMenu());											// put menu at the top
	    Group root = new Group();										// create group with canvas
	    Canvas canvas = new Canvas( 400, 500 );
	    root.getChildren().add( canvas );
	    dp.setLeft(root);												// load canvas to left area
	
	    mc = new MyCanvas(canvas.getGraphicsContext2D(), 400, 500);

	    setMouseEvents(canvas);											// set up mouse events

	    arena = new DroneArena(400, 500);								// set up arena
	    drawWorld();
	    
	    timer = new AnimationTimer() {									// set up timer
	        public void handle(long currentNanoTime) {					// and its action when on
	        		arena.checkDrones();									
		            arena.adjustDrones();								// move all 
		            drawWorld();										// redraw the world
		            drawStatus();										// indicate where itmes are
	        }
	    };

	    rtPane = new VBox();											// set vBox on right to list items
		rtPane.setAlignment(Pos.TOP_LEFT);								// set alignment
		rtPane.setPadding(new Insets(5, 75, 75, 5));					// padding
 		dp.setRight(rtPane);											// add rtPane to borderpane right
		 
	    dp.setBottom(setButtons());										// set bottom pane with buttons
	    
	    
	    Scene scene = new Scene(dp, 700, 600);							// set overall scene
        dp.prefHeightProperty().bind(scene.heightProperty());
        dp.prefWidthProperty().bind(scene.widthProperty());
                
        primaryStage.setScene(scene);
        primaryStage.show();

        
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    Application.launch(args);			// launch the GUI
	    
	}

}
