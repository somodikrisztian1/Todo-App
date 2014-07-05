package hu.todo.entity;

import java.util.Date;

public class Task {
	
	private int id;
	private int user_id;
	private String title;
	private String description;
	private Date date;
	private Date created_at;
	private Date updated_at;
	
	public Task(int id, int user_id, String title, String description,
			Date date, Date created_at, Date updated_at) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.title = title;
		this.description = description;
		this.date = date;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	
}
