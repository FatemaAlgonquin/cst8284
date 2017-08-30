/*
 * Assignment 3
 * FileUtils.java
 * @author : Fatema Zohora
 * Created on: April 21, 2017
 */
package cst8284.assignment1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Reads and save .todo file
 * @author fatema
 *
 */
public class FileUtils {

	/**
	 * Default .todo file
	 */
	private static String relPath = "ToDoList.todo";

	/**
	 * Given a valid .todo file path, reads the file and construct an ArrayList of ToDo elements.
	 * @param fileName .todo file path
	 * @return list of ToDo elements contructed from .todo file
	 * @throws IOException when file reading fails
	 * @throws ClassNotFoundException due to incorrect information in the .todo file.
	 */
	public  ArrayList<ToDo> getToDoArray(String fileName) throws IOException, ClassNotFoundException {

		ArrayList<ToDo> toDoList = new ArrayList<ToDo>();
		FileInputStream fileInput = new FileInputStream(fileName);
		ObjectInputStream inputStream = new ObjectInputStream(fileInput);
		try{
			Object object = inputStream.readObject();

			while(object != null){
				toDoList.add((ToDo) object);

				object = inputStream.readObject();
			}
		}
		catch (IOException | ClassNotFoundException e) {
			//se.printStackTrace();
		} finally{
			inputStream.close();
		}
		return toDoList;
	}

	
	/**
	 * Persists a list of ToDo elements in the file. This method filters out any ToDo element. 
	 * @param originalToDoList list of ToDo element.
	 * @param path file path to save.
	 */
	public static void setToDoArrayListToFile(ArrayList<ToDo> originalToDoList, String path){
		FileOutputStream foStream;
		ObjectOutputStream ooStream = null;
		try {
			foStream = new FileOutputStream (path);
			ooStream = new ObjectOutputStream(foStream);

			ArrayList<ToDo> tempToDoList = new ArrayList<ToDo>();


			for(ToDo todo : originalToDoList){
				//(remove any empty elements (check the isEmpty() method for each ToDo); save only non-empty ToDos.
				if (todo.isEmptySet()){
					continue;
				}	
				// any ToDo element still listed as ‘dirty’ should be ‘sanitized’.
				else if(todo.isRemoveSet()){
					todo. setRemove(false);
				}
				tempToDoList.add(todo);
				ooStream.writeObject(todo);
			}
			originalToDoList.clear();
			originalToDoList.addAll(tempToDoList);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				ooStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

    /**
     * returns the .todo file path.
     * @return file path as String
     */
	public static String getAbsPath() {
		return relPath;
	}
    
	
	/**
	 * Given a File object, return its absolute path as String.
	 * @param f File object
	 * @return absolute path as String
	 */
	public static String getAbsPath(File f) {
		return f.getAbsolutePath();
	}
	
	
	/**
	 * Sets the absolute path.
	 * @param f File object
	 */
	private static void setAbsPath(File f) { 
		relPath = (fileExists(f))? f.getAbsolutePath():""; 
	}

	/**
	 * Checks if a given File exist.
	 * @param f File object
	 * @return true if exist, false otherwise
	 */
	public static Boolean fileExists(File f) {
		return (f != null && f.exists() && f.isFile() && f.canRead());
	}

}
