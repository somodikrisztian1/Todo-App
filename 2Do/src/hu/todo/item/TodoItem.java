package hu.todo.item;

import android.widget.CheckBox;

// egy teendőt reprezentál
public class TodoItem {
	
	CheckBox checkbox;
	String content1stRow;
	String content2ndRow;
	String date1stRow;
	String date2ndRow;
	
	public TodoItem(CheckBox checkbox, String content1stRow,
			String content2ndRow, String date1stRow, String date2ndRow) {
		super();
		this.checkbox = checkbox;
		this.content1stRow = content1stRow;
		this.content2ndRow = content2ndRow;
		this.date1stRow = date1stRow;
		this.date2ndRow = date2ndRow;
	}

	public CheckBox getCheckbox() {
		return checkbox;
	}

	public String getContent1stRow() {
		return content1stRow;
	}

	public String getContent2ndRow() {
		return content2ndRow;
	}

	public String getDate1stRow() {
		return date1stRow;
	}

	public String getDate2ndRow() {
		return date2ndRow;
	}
	
}
