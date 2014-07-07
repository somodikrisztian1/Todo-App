package hu.todo.entity;

import java.util.Calendar;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import android.os.Parcel;
import android.os.Parcelable;


//to convert the JSON _id property to our id bean property.
public class Task implements Parcelable{
	
	@JsonProperty("id")
	private int id;
	private int user_id;
	private String title;
	private String description;
	private Calendar date;
	private Calendar created_at;
	private Calendar updated_at;
	
	// kell a feldolgozashoz
	public Task() {
		super();
	}

	@JsonProperty("errors")
	List<String> errors;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Calendar getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Calendar created_at) {
		this.created_at = created_at;
	}

	public Calendar getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Calendar updated_at) {
		this.updated_at = updated_at;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", user_id=" + user_id + ", title=" + title
				+ ", description=" + description + ", date=" + date
				+ ", created_at=" + created_at + ", updated_at=" + updated_at
				+ "]";
	}
	
//  to pass complex data from one activity to another activity
	/**
    * Storing the TodoItem data to Parcel object
    **/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(user_id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeSerializable(date);
        dest.writeSerializable(created_at);
        dest.writeSerializable(updated_at);
    }
    
    /**
     * Retrieving TodoItem data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     **/
     private Task(Parcel in){
    	 id = in.readInt();
    	 user_id = in.readInt();
         title = in.readString();
         description = in.readString();
         date = (Calendar) in.readSerializable();
         created_at = (Calendar) in.readSerializable();
         updated_at = (Calendar) in.readSerializable();
     }
  
     public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
  
         @Override
         public Task createFromParcel(Parcel source) {
             return new Task(source);
         }
  
         @Override
         public Task[] newArray(int size) {
             return new Task[size];
         }
     };

    // esetek 99.8% ban nem kell csinalni ezzel semmit
	@Override
	public int describeContents() {
		return 0;
	}
	
}
