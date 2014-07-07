package hu.todo.item;

import android.os.Parcel;
import android.os.Parcelable;


// egy teendőt reprezentál
public class TodoItem implements Parcelable {
	
	String title;
	String description;
	String day;
	String date;
	
	public TodoItem(String content1stRow,
			String content2ndRow, String date1stRow, String date2ndRow) {
		super();
		this.title = content1stRow;
		this.description = content2ndRow;
		this.day = date1stRow;
		this.date = date2ndRow;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getDay() {
		return day;
	}

	public String getDate() {
		return date;
	}
	
	//  to pass complex data from one activity to another activity
	/**
    * Storing the TodoItem data to Parcel object
    **/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(day);
        dest.writeString(date);
    }
    
    /**
     * Retrieving TodoItem data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     **/
     private TodoItem(Parcel in){
         this.title = in.readString();
         this.description = in.readString();
         this.day = in.readString();
         this.date = in.readString();
     }
  
     public static final Parcelable.Creator<TodoItem> CREATOR = new Parcelable.Creator<TodoItem>() {
  
         @Override
         public TodoItem createFromParcel(Parcel source) {
             return new TodoItem(source);
         }
  
         @Override
         public TodoItem[] newArray(int size) {
             return new TodoItem[size];
         }
     };

    // esetek 99.8% ban nem kell csinalni ezzel semmit
	@Override
	public int describeContents() {
		return 0;
	}
	
}
