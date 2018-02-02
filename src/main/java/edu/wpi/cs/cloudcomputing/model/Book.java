package edu.wpi.cs.cloudcomputing.model;

import java.util.List;

public class Book {
	private String title;
	private String author;
	private String ISBN;
	private String description;
	private String imageUrl;
	private String genre;
	private Float score;
	private List<Review> reviews;
	private Integer recommendationCount;
	
	public Book() {
		
	}

	public Book(String title, String author, String iSBN, String description,
			String imageUrl, String genre, Float score) {
		super();
		this.title = title;
		this.author = author;
		ISBN = iSBN;
		this.description = description;
		this.imageUrl = imageUrl;
		this.genre = genre;
		this.score = score;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float d) {
		this.score = d;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Integer getRecommendationCount() {
		return recommendationCount;
	}

	public void setRecommendationCount(Integer recommendationCount) {
		this.recommendationCount = recommendationCount;
	}

}
