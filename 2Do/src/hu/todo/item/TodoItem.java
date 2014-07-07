package hu.todo.item;

import android.os.Parcel;
import android.os.Parcelable;


// egy teendőt reprezentál
public class TodoItem implements Parcelable {
	
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
	
	// type safety miatt nem lehetne castolni ha külön osztály lenne
	 private int mData;

	    /* everything below here is for implementing Parcelable */

	    // 99.9% of the time you can just ignore this
	    public int describeContents() {
	        return 0;
	    }

	    // write your object's data to the passed-in Parcel
	    public void writeToParcel(Parcel out, int flags) {
	        out.writeInt(mData);
	    }

	    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
	    public static final Parcelable.Creator<TodoItem> CREATOR = new Parcelable.Creator<TodoItem>() {
	        public TodoItem createFromParcel(Parcel in) {
	            return new TodoItem(in);
	        }

	        public TodoItem[] newArray(int size) {
	            return new TodoItem[size];
	        }
	    };

	    // example constructor that takes a Parcel and gives you an object populated with it's values
	    private TodoItem(Parcel in) {
	        mData = in.readInt();
	    }
	
}
