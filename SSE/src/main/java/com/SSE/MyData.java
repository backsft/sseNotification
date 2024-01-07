package com.SSE;

public class MyData {
	private String id;
	private String name;
	private String username;
	private String details;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public MyData(String id, String name, String username, String details) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.details = details;
	}

	public MyData() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "MyData [id=" + id + ", name=" + name + ", username=" + username + ", details=" + details + "]";
	}

}
