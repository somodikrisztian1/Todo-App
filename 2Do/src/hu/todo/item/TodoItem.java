package hu.todo.item;


// egy teendőt reprezentál
public class TodoItem {
	
	String content1stRow;
	String content2ndRow;
	String date1stRow;
	String date2ndRow;
	
	public TodoItem(String content1stRow,
			String content2ndRow, String date1stRow, String date2ndRow) {
		super();
		this.content1stRow = content1stRow;
		this.content2ndRow = content2ndRow;
		this.date1stRow = date1stRow;
		this.date2ndRow = date2ndRow;
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
