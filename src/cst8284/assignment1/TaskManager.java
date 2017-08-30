
/**
 * Assignment 3
 * FileUtils.java
 * @author : Fatema Zohora
 * Created on: April 21, 2017
 * 
 * Bonus:
 * 1. Adding a listener so that whenever a  ListView title is selected, that element is automatically displayed in the center pane.
   2. Using a lambda expression within each Event handler to perform the actual sort, i.e. when a button is clicked, inside the lambda
      expression for the Event Handler, the sort() method is called. Thus you code appears inside the parenthesis in btnSort.setOnMouseClicked(ev->{…});
      as a single line of code for each of the 6 buttons—no need for 5 separate sorting classes.  
 *  
 *  site visited: 
 *  citizen conn (2014). how to convert java string to Date object [Webpage]. Retrieved from
 *  http://stackoverflow.com/questions/6510724/how-to-convert-java-string-to-date-object
 * 
 *  jewelsea (2015). Alert Box For When User Attempts to close application using setOnCloseRequest in JavaFx [Webpage]. Retrieved from
 *  http://stackoverflow.com/questions/31540500/alert-box-for-when-user-attempts-to-close-application-using-setoncloserequest-in 
 *  
 *   mkyong (2008).How to detect OS in Java [Webpage]. Retrieved from https://www.mkyong.com/java/how-to-detect-os-in-java-systemgetpropertyosname/
 *
 */

package cst8284.assignment1;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * Manages all ToDo object related actions (i.e create a new ToDo, display information, save, change, delete and sort)
 * @author fatema
 *
 */
public class TaskManager extends Application{

	/**
	 * Holds all tasks. This is the main data-structure
	 */
	private ArrayList<ToDo> toDoArray = new ArrayList<ToDo>();
	/**
	 * Index of the current displayed ToDo.
	 */
	private static int currentToDoElement;
	/**
	 * Stage object which display ToDo content.
	 */
	private Stage primaryStage;
	/**
	 * Button object to navigate to the first ToDo.
	 */
	private Button firstBtn = new Button("|<<");
	/**
	 * Button object to navigate to the previous ToDo.
	 */
	private Button prevBtn = new Button("<<");
	/**
	 * Button object to navigate to the next ToDo.
	 */
	private Button nextBtn = new Button(">>");
	/**
	 * Button object to navigate to the last ToDo.
	 */
	private Button lastBtn = new Button(">>|");
	/**
	 * UI object which represents currently selected ToDo's title.
	 */
	private TextField task;
	/**
	 * UI object which represents currently selected ToDo's subject.
	 */
	private TextArea subject;
	/**
	 * UI object which represents currently selected ToDo's due date.
	 */
	private TextField dueDate;
	/**
	 * UI object which represents currently selected ToDo's priority 1 value.
	 */
	private RadioButton prio1;
	/**
	 * UI object which represents currently selected ToDo's priority 2 value.
	 */
	private RadioButton prio2;
	/**
	 * UI object which represents currently selected ToDo's priority 3 value.
	 */
	private RadioButton prio3;
	/**
	 * File path of the currently loaded .todo file
	 */
	private String path = null;
	/**
	 * Default text to display at startup. 
	 */
	private final String defaultText = "click here to begin";
	
	/**
	 * Contains lit of titles of the tasks (ignoring empty taks)
	 */
	ObservableList<String> items;

	/**
	 * returns 	all titles of loaded tasks.
	 * @return: title list
	 */
	public 	ObservableList<String> getItems(){
		return items;
	}
	/**
	 * 
	 * return the path from the file loader to use it for the read/write purpose
	 * @return file path of .todo
	 */
	public String getPathFromFileLoader(){
		return path;
	}

	/**
	 * Return the index for the current toDo element
	 * @return index
	 */
	public int getCurrentToDoElement(){
		return currentToDoElement;
	}

	/**
	 * Set the index of the current ToDo.
	 * @param element current index
	 */
	public void setToDoElement(int element){
		currentToDoElement = element;
	}
	/**
	 * returns the primary stage
	 * @return Stage object
	 */

	public Stage getPrimaryStage(){
		return primaryStage;
	}

	/**
	 * Sets the primary stage.
	 * @param stage Stage object
	 */
	public void setStage(Stage stage){
		this.primaryStage = stage;
	}

	/**
	 * Returns UI title object of the currently selected ToDo.
	 * @return TextField object
	 */
	public TextField getTask(){
		return task;
	}

	/**
	 * Sets the UI title object of the currently selected ToDo.
	 * @param task TextField object
	 */
	public void setTask(TextField task){
		this.task = task;
	}
	
	/**
	 * Returns UI control which represents subject of the current ToDo.
	 * @return TextField object
	 */
	public TextArea getSubject(){
		return subject;
	}
	
	/**
	 * Sets UI control which represents subject of the current ToDo.
	 * @param subject TextField object
	 */
	public void setSubject(TextArea subject){
		this.subject = subject;
	}

	
	/**
	 * Returns UI control which represents the due date of the current ToDo.
	 * @return TextField object
	 */
	public TextField getDueDate(){
		return dueDate;
	}

	/**
	 * Sets UI control which represents due date of the current ToDo.
	 * @param dueDate TextField object
	 */
	public void setDueDate(TextField dueDate){
		this.dueDate = dueDate;
	}

	/**
	 * returns the first radio button (prio1)
	 * @return RadioButton object
	 */
	public RadioButton getPiro1(){
		return prio1;
	}

	/**
	 * sets UI control which represents the first priority value of the current ToDo.
	 * @param prio1 RadioButton object
	 */
	public void setPrio1(RadioButton prio1){
		this.prio1 = prio1;
	}

	/**
	 * gets UI control which represents the second priority value (prio2) of the current ToDo.
	 * @return RadioButton object
	 */
	public RadioButton getPiro2(){
		return prio2;
	}

	/**
	 * sets UI control which represents the second priority value (prio2) of the current ToDo.
	 * @param prio2 RadioButton object
	 */
	public void setPrio2(RadioButton prio2){
		this.prio2 = prio2;
	}

	/**
	 * gets UI control which represents the second priority value (prio3) of the current ToDo.
	 * @return  RadioButton object
	 */
	public RadioButton getPiro3(){
		return prio3;
	}

	/**
	 * sets UI control which represents the second priority value (prio3) of the current ToDo.
	 * @param prio3 RadioButton object
	 */
	public void setPrio3(RadioButton prio3){
		this.prio3 = prio3;
	}

	/**
	 * return the ToDo ArrayList
	 * @return: toDoArray
	 */

	public ArrayList<ToDo> getToDoArray() {
		return toDoArray;
	}

	/**
	 * sets the ArrayList ToDo
	 * @param toDoArray ArrayList
	 */
	public void setToDoArray(ArrayList<ToDo> toDoArray){
		this.toDoArray = toDoArray;
	}


	/**
	 * creates a Scene object with a given ToDo
	 * @param td ToDo object
	 * @return Scene object
	 */
	
	public Scene getToDoScene(ToDo td) {
		Scene scene1 = new Scene(getToDoPane(td),Color.AZURE); 
		return scene1;
	}


	

	/**
	 * Returns list of titles from ToDo list. Title of an empty ToDo object is skipped.
	 * @param toDoList list of ToDo
	 * @return list of titles as ArrayList
	 */

	public ArrayList<String> getTitleList(ArrayList<ToDo> toDoList){
		ArrayList<String> titleList = new ArrayList<String>();
		for(ToDo toDo : toDoList){
			if(!toDo.isEmptySet()){
				titleList.add(toDo.getTitle());
			}
		}
		//System.out.println(titleList.toString());
		return titleList;


	}
	/**
	 * This method handles selection event of a title list view item. Once a title selected the center pane
	 * changes in order to reflect the corresponding information of that element. 
	 * This method takes two arguments as index of the element to display. To display the selected element, use the same value (currently displayed index) 
	 * for both argument.
	 * @param startIndex current element index
	 * @param destIndex destination element index
	 */

	public void showCenterPaneAfterSortButtonClick(int startIndex, int destIndex){
		int index = findNextIndex(startIndex, destIndex,  true);
		setToDoElement(index);
		enableDisableButton();

		// passing the toDo obj for showing it to the center pane
		Scene toDoScence = getToDoScene(getToDoArray().get(getCurrentToDoElement()));

		primaryStage.setScene(toDoScence);

	}

	/**
	 * Returns a button (Sort by title) and also deals with the mouse click event.
	 * It sorts the elements by their titles.
	 * @return Button taskBtn
	 * 
	 */
	public Button generateTaskButton(){
		Button taskBtn = new Button ("Sort By Title");
		taskBtn.setMaxWidth(Double.MAX_VALUE);
		taskBtn.setOnAction((event)->{			

			getToDoArray().sort((toDo1, toDo2) ->{
				if(toDo1.isEmptySet() && toDo2.isEmptySet()){
					return 0;
				}

				if(toDo1.isEmptySet()){
					return 1;
				}
				if(toDo2.isEmptySet()){
					return -1;
				}

				return toDo1.getTitle().compareTo(toDo2.getTitle());

			});

			// clear the item in order to reflect the change to the list view
			items.clear();
			items.addAll(FXCollections.observableList(getTitleList(this.getToDoArray())));
			showCenterPaneAfterSortButtonClick(this.getCurrentToDoElement(), this.getCurrentToDoElement());
		});

		return taskBtn;
	}

	/**
	 * Returns a button (Sort By Subject) and also deals with the mouse click event.
	 * It sorts the elements by their subject
	 * @return: Button subBtn
	 */

	public Button generateSubButton(){
		Button subBtn = new Button ("Sort By Subject");
		subBtn.setMaxWidth(Double.MAX_VALUE);
		subBtn.setOnAction((event)->{

			getToDoArray().sort((toDo1, toDo2) ->{
				if(toDo1.isEmptySet() && toDo2.isEmptySet()){
					return 0;
				}

				if(toDo1.isEmptySet()){
					return 1;
				}
				if(toDo2.isEmptySet()){
					return -1;
				}

				return toDo1.getSubject().toLowerCase().compareTo(toDo2.getSubject().toLowerCase());

			});
			items.clear();
			items.addAll(FXCollections.observableList(getTitleList(this.getToDoArray())));
			showCenterPaneAfterSortButtonClick(this.getCurrentToDoElement(), this.getCurrentToDoElement());

		});
		return subBtn;
	}

	/**
	 * Returns a button (Sort By Due Date) and also deals with the mouse click event.
	 * It sorts the elements by their due date
	 * @return: Button dueDateBtn
	 */

	public Button generateDueDateButton(){
		Button dueDateBtn = new Button ("Sort By Due Date");
		dueDateBtn.setMaxWidth(Double.MAX_VALUE);
		dueDateBtn.setOnAction((event)->{

			getToDoArray().sort((toDo1, toDo2) ->{
				if(toDo1.isEmptySet() && toDo2.isEmptySet()){
					return 0;
				}

				if(toDo1.isEmptySet()){
					return 1;
				}
				if(toDo2.isEmptySet()){
					return -1;
				}

				return toDo1.getDueDate().compareTo(toDo2.getDueDate());

			});
			items.clear();
			items.addAll(FXCollections.observableList(getTitleList(this.getToDoArray())));

			showCenterPaneAfterSortButtonClick(this.getCurrentToDoElement(), this.getCurrentToDoElement());

		});
		return dueDateBtn;
	}

	/**
	 * Returns a button (Sort By Priority) and also deals with the mouse click event.
	 * It sorts the elements by their priority
	 * @return: Button priorityBtn
	 */

	public Button generatePriorityButton(){
		Button priorityBtn = new Button ("Sort By Priority");
		priorityBtn.setMaxWidth(Double.MAX_VALUE);
		priorityBtn.setOnAction((event)->{
        // sorting is done in such a way so that the empty elements pushed back of the back and stays together.
	    // This will help remain sink both clicking the titles and clicking the sort buttons.
			getToDoArray().sort((toDo1, toDo2) ->{
				if(toDo1.isEmptySet() && toDo2.isEmptySet()){
					return 0;
				}

				if(toDo1.isEmptySet()){
					return 1;
				}
				if(toDo2.isEmptySet()){
					return -1;
				}

				return new Integer(toDo1.getPriority()).compareTo (new Integer(toDo2.getPriority()));

			});
			items.clear();
			items.addAll(FXCollections.observableList(getTitleList(this.getToDoArray())));
			showCenterPaneAfterSortButtonClick(this.getCurrentToDoElement(), this.getCurrentToDoElement());

		});
		return priorityBtn;
	}
	/**
	 * Returns a button (Sort By Completed) and also deals with the mouse click event.
	 * It sorts the elements by their priority
	 * @return: Button priorityBtn
	 * 
	 */
	public Button generateCompletedButton(){
		Button priorityBtn = new Button ("Sort By Completed");
		priorityBtn.setMaxWidth(Double.MAX_VALUE);
		priorityBtn.setOnAction((event)->{

			getToDoArray().sort((toDo1, toDo2) ->{
				if(toDo1.isEmptySet() && toDo2.isEmptySet()){
					return 0;
				}

				if(toDo1.isEmptySet()){
					return 1;
				}
				if(toDo2.isEmptySet()){
					return -1;
				}

				return new Boolean(toDo1.isCompleted()).compareTo (new Boolean(toDo2.isCompleted()));

			});
			items.clear();
			items.addAll(FXCollections.observableList(getTitleList(this.getToDoArray())));
			showCenterPaneAfterSortButtonClick(this.getCurrentToDoElement(), this.getCurrentToDoElement());

		});
		return priorityBtn;
	}

	/**
	 * Returns a button (Reverse) and also deals with the mouse click event.
	 * It reverse sort the elements 
	 * @return: Button reverseBtn
	 */
	public Button generateReverseButton(){
		Button reverseBtn = new Button ("Reverse");
		reverseBtn.setMaxWidth(Double.MAX_VALUE);
		reverseBtn.setOnAction((event)->{

			//Collections.reverse(items);
			Collections.reverse(getToDoArray());
			items.clear();
			items.addAll(FXCollections.observableList(getTitleList(this.getToDoArray())));
			showCenterPaneAfterSortButtonClick(this.getCurrentToDoElement(), this.getCurrentToDoElement());
			//System.out.println("in");
		});
		return reverseBtn;
	}

	/**
	 * Creates and initializes a Pane with given ToDo object. This method also add action listener which display information about currently selected ToDo.
	 * @param td ToDo object
	 * @return Pane object
	 */
	public Pane getToDoPane(ToDo td) {

		BorderPane rootPane = new BorderPane();
		ToDo tdcenter = td;

		VBox vbLeft = new VBox();
		vbLeft.setMinWidth(120);

		VBox vbRight = new VBox();
		vbRight.setMinWidth(120);

		rootPane.setLeft(vbLeft); 
		rootPane.setRight(vbRight);

		MenuBar newMenu =  buildMenuBar();
		VBox vBox = new VBox();
		vBox.getChildren().addAll(newMenu);

		ListView<String> list = new ListView<String>();
		items =FXCollections.observableList(getTitleList(this.getToDoArray()));

		list.setItems(items);

		list.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {

				int selectedIndex = list.getSelectionModel().getSelectedIndex();
				showCenterPaneAfterSortButtonClick(selectedIndex, selectedIndex);
			}
		});


		vbLeft.getChildren().add(list);

		vbRight.setSpacing(10);
		vbRight.setPadding(new Insets(0, 20, 10, 20));
		vbRight.getChildren().addAll(generateTaskButton(), generateSubButton(), generateDueDateButton(), 
				generatePriorityButton(), generateCompletedButton(), generateReverseButton() );

		rootPane.setTop(vBox);

		Pane centerPane1 = generateCenterPaneContent(tdcenter); 
		Pane bottomPane = generateBottomPaneContent(tdcenter);

		rootPane.setCenter(centerPane1);
		rootPane.setBottom(bottomPane);

		return rootPane;
	}

	/**
	 * Adds listener to a given control (i.e subject or title control)
	 * @param control Control to add listener to
	 */
	private void addHandler(Control control){
		control.addEventHandler(KeyEvent.KEY_TYPED, e->  activityDetectedCenterPane()) ;
		control.addEventHandler(MouseEvent.MOUSE_CLICKED, e->  activityDetectedCenterPane()) ;
	}

	/** This method performs the follow operations.
	 * 1) Creates center pane.
	 * 2) Add labels and text fields and radio buttons
	 * 3) loads information using ToDo objects
	 * @param td ToDo object
	 * @return centerPane Pane object
	 */
	
	
	public Pane generateCenterPaneContent(ToDo td){
		GridPane centerPane = new GridPane();

		task = new TextField(td.getTitle());
		task.setMaxWidth(500);

		addHandler(task);

		subject = new TextArea(td.getSubject());
		subject.setMaxWidth(500);
		subject.setMaxHeight(140);

		addHandler(subject);

		dueDate = new TextField(td.getDueDate().toString());
		dueDate.setMaxWidth(500);

		addHandler(dueDate);

		ToggleGroup toogleGroup = new ToggleGroup();

		prio1 = new RadioButton ("1");
		prio1.setToggleGroup(toogleGroup);

		addHandler(prio1);

		prio2 = new RadioButton ("2");
		prio2.setToggleGroup(toogleGroup);

		addHandler(prio2);

		prio3 = new RadioButton ("3");
		prio3.setToggleGroup(toogleGroup);

		addHandler(prio3);

		if(td.getPriority() == 1){
			prio1.setSelected(true);	
		}
		else if(td.getPriority() == 2){
			prio2.setSelected(true);
		}
		else {
			prio3.setSelected(true);
		}

		HBox hbox = new HBox();
		HBox hbox1 = new HBox();

		hbox1.getChildren().addAll(prio1);
		hbox1.getChildren().addAll(prio2);
		hbox1.getChildren().addAll(prio3);

		hbox1.setSpacing(10);
		hbox.getChildren().add(hbox1);
		hbox.setSpacing(50);
		hbox.setPadding(new Insets(10, 10, 10, 10));

		Label taskLabel = new Label("Task");
		Label subjectLabel = new Label("Subject");
		Label dueDateLabel = new Label("Due date");
		Label priorityLabel = new Label("Priority"); 

		centerPane.setPadding(new Insets(10, 10, 10, 10));

		centerPane.setVgap(10);
		centerPane.setHgap (20);

		centerPane.add(taskLabel, 0, 0);
		centerPane.add(subjectLabel, 0, 1);
		centerPane.add(dueDateLabel, 0, 2);
		centerPane.add(priorityLabel, 0, 3);

		centerPane.add(task, 1, 0);
		centerPane.add(subject, 1, 1);
		centerPane.add(dueDate, 1, 2);
		centerPane.add(hbox, 1, 3);

		centerPane.setStyle("-fx-font: 12px Tahoma; -fx-stroke: black;-fx-stroke-width: 1; -fx-padding: 10;");

		centerPane.setAlignment(Pos.CENTER);
		centerPane.setCenterShape(true);

		return centerPane;
	}
   
	/**
	 * Displays a ToDo element.
	 * @param currentIndex current ToDo element index
	 * @param destIndex destination ToDo element index
	 * @param dir true means forward direction, false indicates reverse direction. 
	 */
	public void showBootomPane(int currentIndex, int destIndex, boolean dir){	
		setToDoElement(currentIndex);
		int index = findNextIndex(getCurrentToDoElement(), destIndex,  dir);
		setToDoElement(index);
		enableDisableButton();	
		ToDo tinfo = getToDoArray().get(getCurrentToDoElement()); 
		if(getCurrentToDoElement() >=0 && getCurrentToDoElement() <= getToDoArray().size() -1){

			Scene tScence = getToDoScene(tinfo); 
			Stage tStage = getPrimaryStage();
			tStage.setScene(tScence);
			tStage.setWidth(1000);
			tStage.setHeight(660);
			tStage.show();
		}
	}

	/**
	 * This method performs the following step:
	 * 1) Creates bottom pane.
	 * 2) Add four button 
	 * 3) Associates with action (a mouse click event is implemented for each of the button to load the todo content to the scene by 
	 * calling generateBottomPaneContent())
	 * @param td ToDo object
	 * @return bottom Pane object.
	 */
	public Pane generateBottomPaneContent(ToDo td){

		// implementing |<<
		firstBtn.setPrefSize(50, 10);
		firstBtn.setOnAction((event) -> {

			int  destIndex = 0;
			handleDirtyToDo();
			showBootomPane(getCurrentToDoElement(), destIndex, true);

		});

		// implementing <<
		prevBtn.setPrefSize(50, 10);
		prevBtn.setOnAction((event) -> {	
			handleDirtyToDo();
			showBootomPane(getCurrentToDoElement(), getCurrentToDoElement()-1, false);

		});

		// implementing >>
		nextBtn.setPrefSize(50, 10);
		nextBtn.setOnAction((event) -> {
			handleDirtyToDo();
			showBootomPane(getCurrentToDoElement(), getCurrentToDoElement()+1, true);
		});

        // implementing >>|
		lastBtn.setPrefSize(50, 10);
		lastBtn.setOnAction((event) -> {

			int dest = 0;
			dest = getToDoArray().size()-1;
			handleDirtyToDo();
			showBootomPane( getCurrentToDoElement() ,dest, false);

		});

		HBox bottomPane = new HBox(20);
		bottomPane.getChildren().addAll(firstBtn, prevBtn, nextBtn, lastBtn);
		bottomPane.setStyle("-fx-padding: 10; -fx-border-color: grey;-fx-background-color: darkgrey");
		bottomPane.setSpacing(10);
		bottomPane.setPadding(new Insets(10, 10, 10, 10));
		bottomPane.setAlignment(Pos.CENTER);

		return bottomPane;

	}

	/**
	 * This method returns the correct index skipping the empty elements and also decide which way it should move (forward or backward).
	 * @param currentIndex current index of the ToDo element.
	 * @param destIndex destination index
	 * @param rightDir true value indicates forward direction, otherwise backward direction.
	 * @return next available index
	 */

	public int findNextIndex(int currentIndex, int destIndex, boolean rightDir) {
		ArrayList<ToDo> tContents = getToDoArray();
		int prevIndex = currentIndex;


		if(destIndex > getToDoArray().size()-1 ||  destIndex < 0){ 
			// to handle the index out of bound exception
			return prevIndex;

		}

		if(destIndex <= getToDoArray().size()-1  && destIndex >= 0 && !tContents.get(destIndex).isEmptySet()){
			// destination is not empty return destination index
			return destIndex;	
		}

		if(rightDir){ 
			// if direction true it follow >> and >>|

			while (destIndex+1 <= getToDoArray().size()-1 &&  tContents.get(destIndex+1).isEmptySet() ){
				destIndex++;
			}
			if(destIndex < getToDoArray().size()-1){
				destIndex++;
			}
		}
		else{

			// if direction false it follow << and |<<
			while(destIndex-1 >= 0 &&  tContents.get(destIndex-1).isEmptySet()){
				destIndex--;
			}
			if(destIndex > 0){
				destIndex--;
			}
		}

		// regardless the direction 
		if(tContents.get(destIndex).isEmptySet()){ 
			return prevIndex;

		}
		else{
			return destIndex;
		}

	}

	/**
	 * This method enables the buttons based on the fact that whether there are other elements to move to backward or forward.
	 * If such move is not possible, it disable the corresponding button.
	 */
	public void enableDisableButton(){		
		int index = getCurrentToDoElement();

		if(hasValidNext(index, false)){
			// valid ToDo element exist in left therefore it can go left.
			firstBtn.setDisable(false);
			prevBtn.setDisable(false);
		}else{
			firstBtn.setDisable(true);
			prevBtn.setDisable(true);
		}

		if(hasValidNext(index, true)){
			// valid ToDo element exist in left therefore it can go left.
			nextBtn.setDisable(false);
			lastBtn.setDisable(false);
		}
		else{
			nextBtn.setDisable(true);
			lastBtn.setDisable(true);
		}
	}

	/**
	 * Searches if a valid ToDo (non empty) exist from the current position in either direction (forward or backward).
	 * @param currentIndex current ToDo object index
	 * @param rightDir : true value indicates forward direction and false value backward direction
	 * @return true if next a non empty ToDo found in the given direction
	 */
	private boolean hasValidNext(int currentIndex, boolean rightDir){
		ArrayList<ToDo> tContents = getToDoArray();

		if (rightDir){
			for(int i = currentIndex+1; i<= tContents.size() -1 ; i++){
				if(!tContents.get(i).isEmptySet()){
					return true;
				}
			}
		}
		else{
			// left direction
			for(int i = currentIndex-1; i>= 0 ; i--){
				if(!tContents.get(i).isEmptySet()){
					return true;
				}
			}

		}
		return false;
	}

	/**
	 *  Takes care of the default scene.
	 *  @param defaultText default text as String
	 *  @return Scene object with default text
	 */
	
	public Scene getSplashScene(String defaultText){
		Text text = new Text(defaultText);

		BorderPane rootPane = new BorderPane ();
		StackPane firstpane = new StackPane();

		DropShadow dropshadow = new DropShadow();
		dropshadow.setOffsetY(3.0f);
		dropshadow.setColor(Color.color(0.3f, 0.3f, 0.3f));

		text.setEffect(dropshadow);
		text.setCache(true);
		text.setX(9.0f);
		text.setY(250.0f);
		text.setFill(Color.DARKBLUE);

		text.setFont(Font.font(null, FontWeight.BOLD, 32));

		FadeTransition fadeTrans = new FadeTransition(Duration.millis(2000), firstpane);
		fadeTrans.setFromValue(0.2);
		fadeTrans.setToValue(1.0);
		fadeTrans.rateProperty();
		fadeTrans.play();

		final ScaleTransition sTransition = new ScaleTransition();
		sTransition.setNode(text);;
		sTransition.setFromX(1.5);
		sTransition.setFromY(1.5);
		sTransition.setToX(1.0);
		sTransition.setToY(1.0);
		sTransition.setDuration(new Duration(2000));

		sTransition.playFromStart();

		ParallelTransition parallel 
		= new ParallelTransition(text, fadeTrans, sTransition ); 
		parallel.play();

		firstpane.getChildren().add(text);
		firstpane.setAlignment(Pos.CENTER);

		VBox vbLeft = new VBox();
		vbLeft.setMinWidth(120);

		VBox vbRight = new VBox();
		vbRight.setMinWidth(120);


		rootPane.setLeft(vbLeft); 
		rootPane.setRight(vbRight);

		rootPane.setCenter(firstpane);

		rootPane.setStyle("-fx-padding: 5;" +
				"-fx-border-style: solid inside;" +
				"-fx-border-width: 2;" +
				"-fx-border-insets: 3;" +
				"-fx-border-radius: 3;" +
				"-fx-border-color: grey;");

		Scene newScene = new Scene(rootPane,1000,660);
		return newScene;

	}

	/**
	 *  This method lets the user to choose the file and returns the path of the chosen file.
	 *  @return: path file path selected by the user
	 */


	public String FileLoader(){
		FileChooser fc = new FileChooser();		
		fc.setTitle("Open ToDO File");

		// Reference: https://www.mkyong.com/java/how-to-detect-os-in-java-systemgetpropertyosname/


		String OS = System.getProperty("os.name").toLowerCase();
		if(OS.indexOf("win") >= 0){
			fc.setInitialDirectory(new File("D:\\"));  
		}


		fc.getExtensionFilters().addAll(
				new ExtensionFilter("TODO Files", "*.todo"),
				new ExtensionFilter("All Files", "*.*")
				);
		File todoFile = fc.showOpenDialog(primaryStage);

		if (FileUtils.fileExists(todoFile)){
			path = todoFile.getAbsolutePath();
		}

		else{

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("No Filename Entered");
			alert.setContentText("No filename entered. Do you want to exit (OK) or continue (Cancle)");
			Optional <ButtonType> btnValue = alert.showAndWait();

			if (btnValue.isPresent() &&  btnValue.get() == ButtonType.OK){
				Platform.exit();	
			}

			else if(btnValue.isPresent() && btnValue.get() == ButtonType.CANCEL){
				return FileLoader();
			}
		}
		return path;
	}

	/**
	 *  Builds a menu bar on the top of the BorderPane.
	 *  @return: MenuBar menuBar
	 */
	public MenuBar buildMenuBar(){

		MenuBar menuBar = new MenuBar();
		Menu file = new Menu("File");


		//Menu : Open
		MenuItem open = new MenuItem("Open");
		open.setOnAction((event)->{
			handleExitEvent(false);	
			displayFileContent();
		});

		file.getItems().add(open);

		//Menu: Save
		MenuItem save = new MenuItem("Save");
		save.setOnAction((event)->{
			saveTOCenterPaneAndWrite2File();

		});
		file.getItems().add(save);


		//Menu: Add ToDO
		MenuItem addToDo = new MenuItem("Add ToDo");
		addToDo.setOnAction((event)->{
			addToDoHandler();
		});
		file.getItems().add(addToDo);


		// Menu: Remove ToDo
		MenuItem remove = new MenuItem("Remove ToDo");
		remove.setOnAction((event)->{
			removeToDo();
		});
		file.getItems().add(remove);

		//Menu: Exit
		MenuItem exitItem = new MenuItem("Exit");
		exitItem.setOnAction((event)->{
			handleExitEvent(true);
			Platform.exit();
		});
		file.getItems().add(exitItem);

		menuBar.getMenus().add(file);
		return menuBar;

	}

	/**
	 * Primary purpose of this method to call saveCenterPaneContents2ToDo() and FileUtils.setToDoArrayListToFile(getToDoArray(), getPathFromFileLoader()
	 * method.
	 */
	public void saveTOCenterPaneAndWrite2File(){
		saveCenterPaneContents2ToDo();
		FileUtils.setToDoArrayListToFile(getToDoArray(), getPathFromFileLoader());
	}

	/**
	 * Handles exit condition.
	 * @param exit true value indication exit criteria
	 */
	private void handleExitEvent(boolean exit){
		boolean dirty = isToDoArrayListDirty();

		if(dirty){
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm exit?");
			alert.setContentText("File changes. Do you want save before exit? OK to save and Cancel to exit");
			Optional <ButtonType> btnValue = alert.showAndWait();

			if (btnValue.isPresent() &&  btnValue.get() == ButtonType.CANCEL && exit){
				Platform.exit();
			}

			else if(btnValue.isPresent() && btnValue.get() == ButtonType.OK){

				saveTOCenterPaneAndWrite2File();
				if(exit){
					Platform.exit();
				}
			}
		}
	}


	/** 
	 * If any mouse click detected to the control nodes of the centerPane settings then set the remove to true	
	 *
	 */
	public void activityDetectedCenterPane(){
		int index = getCurrentToDoElement();
		ArrayList<ToDo>toDoArray =  getToDoArray();
		toDoArray.get(index).setRemove(true);
	}

	/**
	 * Handles any unsaved changes on ToDo element. If the current ToDo is changed but not saved, it prompts the user to save or discard changes.
	 */
	public void handleDirtyToDo(){
		int index = getCurrentToDoElement();
		ArrayList<ToDo>toDoArray =  getToDoArray();
		if(toDoArray.get(index).isRemoveSet()){
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Content Changed");
			alert.setContentText("Content is changed. Do you want to save ?");
			Optional <ButtonType> btnValue = alert.showAndWait();

			if (btnValue.isPresent() &&  btnValue.get() == ButtonType.OK){

				saveTOCenterPaneAndWrite2File();
			}

			else if(btnValue.isPresent() && btnValue.get() == ButtonType.CANCEL){
				toDoArray.get(index).setRemove(false) ;
			}
		}
	}


	/**
	 * Saves user changes in the ToDo object. This method uses the getters just added—for the Textboxes, Textfield and radiobuttons— takes their 
	 * contents and loads them into a temporary ToDo object using the ToDo() constructor. Use this ToDo to replace the existing 
	 * ToDo in the ArrayList, which will now contain the revised information according to the new contents of the three center 
	 * pane text objects.
	 */
	public void saveCenterPaneContents2ToDo (){

		int priority = 1;

		if(prio2 != null && prio2.isSelected() == true){
			priority = 2;
		}
		else if(prio3 != null && prio3.isSelected() == true){
			priority = 3;
		}

		String dueDate = this.getDueDate().getText();
		DateFormat df = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy"); 
		Date newDueDate = null;
		try {
			newDueDate = df.parse(dueDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		ToDo updatedTodo = new ToDo(this.getSubject().getText(), this.getTask().getText(), priority, newDueDate,false,true, false);

		int index = getCurrentToDoElement();
		ArrayList<ToDo>toDoArrayList =  getToDoArray();
		toDoArrayList.set(index, updatedTodo);
	}


	/*Description: isToDoArrayListDirty() is used when  (a) about to exit, or (b) about to open a new
	 */

	/**
	 * This method is used when  (a) about to exit, or (b) about to open a new
	 * @return true indicates one of the ToDo element is dirty, false otherwise. 
	 */
	public boolean isToDoArrayListDirty(){
		boolean removeSet = false;
		ArrayList<ToDo>toDoArrayList =  getToDoArray();
		for(ToDo todo : toDoArrayList){
			if(todo.isRemoveSet()){
				removeSet = true;
				break;
			}
		}
		return removeSet;
	}

	/**
	 * Loads ToDo objects from user selected file. 
	 */
	private void displayFileContent(){
		FileUtils fs = new FileUtils();
		String fPath = FileLoader();

		// handles exception
		if(fPath == null){
			Platform.exit(); 
		}

		try{
			ArrayList<ToDo> newToDoList = fs.getToDoArray(fPath); 
			setToDoArray(newToDoList);
			if (newToDoList.get(getCurrentToDoElement()).isEmptySet()){
				setToDoElement( findNextIndex(getCurrentToDoElement(),0, true));	
			}

			Scene toDoScence = getToDoScene(newToDoList.get(getCurrentToDoElement()));
			enableDisableButton();

			primaryStage.setScene(toDoScence);
		}catch(Exception e){
			//e.printStackTrace();   
		}
	}

	/**
	 * Adds handler for the following cases: 
	 * 1) allows the user to insert a new (empty) ToDo after the current Todo
	 * 2) displays various fields for user input in the center pane. 
	 * 3) insert a new ToDo into the ArrayList, and load a center pane with empty boxes.
	 */

	public void addToDoHandler(){

		this.handleDirtyToDo();

		int currentIndex = getCurrentToDoElement();
		ArrayList<ToDo> currentArraylist = getToDoArray();

		ToDo addToDo = new ToDo();
		addToDo.setRemove(true);
		addToDo.setDueDate(new Date());
		int updatedIndex = currentIndex+1;
		if(currentIndex == currentArraylist.size()- 1 ){
			//for the last index todo element will be added at the end 
			currentArraylist.add( addToDo ); 
		}else{
			// todo element will be added after the current todo

			currentArraylist.add( updatedIndex, addToDo );
			//setting the current index in order to show the added todo	

		}
		this.setToDoElement(updatedIndex); 
		FileUtils.setToDoArrayListToFile(getToDoArray(), getPathFromFileLoader());

		
		Scene emptyScene = getToDoScene(addToDo); 
		enableDisableButton();
		primaryStage.setScene(emptyScene);
	}

/**
 * Remove ToDo… deletes the current ToDo element from the ArrayList.
 * prompt the user with a dialog window asking the user to confirm the deletion
 */
	public void removeToDo(){
		int currentIndex = getCurrentToDoElement();
		int startIndex;
		int destIndex = 0;
		ArrayList<ToDo> currentArraylist = getToDoArray();

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Remove content");
		alert.setContentText("Do you want to remove ?");
		Optional <ButtonType> btnValue = alert.showAndWait();


		if (btnValue.isPresent() &&  btnValue.get() == ButtonType.OK){
			if(currentArraylist.get(currentIndex) != null){
				currentArraylist.remove(currentIndex);	
			}
			if(currentIndex == 0){
				startIndex = 0;
				destIndex = findNextIndex(0, startIndex+1, true);
			}
			else{
				startIndex = currentIndex-1;
				destIndex = findNextIndex(startIndex, currentIndex, true);

			}
			setToDoElement(destIndex);
			Scene updatedScene = null;

			if (currentArraylist.size() == 0){
				// don't let the last element remove
				updatedScene = getDefaultScene();

			}
			
			else{
				updatedScene = getToDoScene(getToDoArray().get(destIndex));
				enableDisableButton();
			}
			primaryStage.setScene(updatedScene);

			FileUtils.setToDoArrayListToFile(getToDoArray(), getPathFromFileLoader());
		}
	}

	/**
	 * Return default Scene object.
	 * @return Scene object
	 */
	private Scene getDefaultScene(){
		Scene defaultScene = getSplashScene(defaultText);

		defaultScene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				displayFileContent();
			}
		});
		return defaultScene;
	}

	/**
	 * Start method to start the program
	 * @param primaryStage Stage object
	 */
	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("To Do List");
		Scene defaultScene = getDefaultScene();
		primaryStage.setScene(defaultScene);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				handleExitEvent(true);
			}
		});

		primaryStage.setWidth(1000);
		primaryStage.setHeight(660);
		setStage(primaryStage);
		primaryStage.show();
	}
	/**
	 * Program entry point.
	 * @param args user arguments.
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}
}
