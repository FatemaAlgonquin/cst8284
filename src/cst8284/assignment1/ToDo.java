package cst8284.assignment1;

import java.io.Serializable;
import java.util.Date;

public class ToDo extends Task implements Serializable {
	private int priority;
	private Date dueDate;
	private boolean completed, remove, empty;
	private String subject, title;
	// setDueDate(dueDate: Date): void

	/*
	 * default no arg constructor
	 */
	public ToDo(){}

	/*
	 * Parameterized constructor (subject, title, priority, dueDate, completed, remove, empty)
	 */
	public ToDo(String subject, String title, int priority, Date dueDate, boolean completed, boolean remove, boolean empty){
		setSubject(subject); setTitle(title); setPriority(priority); setDueDate(dueDate);
		setCompleted(completed); setRemove(remove); setEmpty(empty);
	}

	/*
	 * This method returns priority
	 */
	public int getPriority() {
		return priority;
	}

	/*
	 * This method sets the priority
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/*
	 * This method returns dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/*
	 * This method sets dueDate
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/*
	 * This method returns completed
	 */	
	public boolean isCompleted() {
		return completed;
	}

	/*
	 * This method sets completed
	 */
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	/*
	 * This method returns remove
	 */
	public boolean isRemoveSet() {
		return remove;
	}

	/*
	 * This method sets remove
	 */
	public void setRemove(boolean remove) {
		this.remove = remove;
	}

	/*
	 * This method returns empty
	 */
	public boolean isEmptySet() {
		return empty;
	}

	/*
	 * This method sets empty
	 */
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	/*
	 * This method returns subject
	 */
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	/*
	 * This method returns title
	 */
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString(){
		return (getDueDate().toString());
	}
}
