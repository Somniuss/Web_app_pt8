package com.somniuss.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class News implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String title;
	private String content;
	private LocalDate date;
	private String author;

	public News() {

	}

	public News(int id, String title, String content, LocalDate date, String author) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.date = date;
		this.author = author;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public int hashCode() {
		return Objects.hash(author, content, date, id, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		News other = (News) obj;
		return Objects.equals(author, other.author) && Objects.equals(content, other.content)
				&& Objects.equals(date, other.date) && id == other.id && Objects.equals(title, other.title);
	}
	
	 @Override
	    public String toString() {
	        return "News [id=" + id + ", title=" + title + ", content=" + content +
	               ", date=" + date + ", author=" + author + "]";
	    }

}
