package hu.todo.entity;

import java.io.Serializable;
import java.util.Calendar;

import org.codehaus.jackson.annotate.JsonProperty;


//to convert the JSON _id property to our id bean property.
@SuppressWarnings("serial")
public class Task implements Serializable {
	
	@JsonProperty("id")
	private int id;
	private int user_id;
	private String title;
	private String description;
	private Calendar date;
	private Calendar created_at;
	private Calendar updated_at;

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
	
}
