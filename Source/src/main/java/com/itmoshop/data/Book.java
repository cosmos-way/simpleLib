package com.itmoshop.data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "books")

@XmlRootElement
public class Book implements Serializable {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String publisher;
    private String author;
    private String isbn;
    private String imageURLlarg;
    private int yearOfPublication;

    @Temporal(TemporalType.DATE)
    private Date publishDate;
    private int numOfPages;
    private String language;

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BookReview> reviews = new ArrayList<>();

    @Column(columnDefinition = "text")
    private String description;
    private String coverPicFileName;
    private double price;

    public Book() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public void setPublishDate(String publishDate) {
        String[] dates = publishDate.split("-");
        int year = Integer.parseInt(dates[0]);
        int month = Integer.parseInt(dates[1]);
        int day = Integer.parseInt(dates[2]);

        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        this.publishDate = c.getTime();
    }

    public String getCoverPicFileName() {
        return "";
    }

    public void setCoverPicFileName(String coverPicFileName) {

    }

    public double getPrice() {
        return 0;
    }

    public void setPrice(double price) {

    }

    public List<BookReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<BookReview> reviews) {
        this.reviews = reviews;
    }

    public void addReview(BookReview bookReview) {
        reviews.add(bookReview);
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public void setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURLlarg() {
        return imageURLlarg;
    }

    public void setImageURLlarg(String imageURLlarg) {
        this.imageURLlarg = imageURLlarg;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }
}
