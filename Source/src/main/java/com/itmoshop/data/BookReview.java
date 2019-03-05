package com.itmoshop.data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "book_reviews")

@XmlRootElement
public class BookReview implements Serializable {

    @Id
    @Column(name = "book_review_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;
    private String posterUsername;

    @Temporal(TemporalType.TIMESTAMP)
    private Date reviewDate;
    private String reviewText;

    public BookReview() {

    }

    public BookReview(Book book, String posterUsername, String reviewText, Date reviewDate) {
        this.book = book;
        this.posterUsername = posterUsername;
        this.reviewText = reviewText;
        this.reviewDate = reviewDate;
    }

    public long getId() {
        return id;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getPosterUsername() {
        return posterUsername;
    }

    public void setPosterUsername(String posterUsername) {
        this.posterUsername = posterUsername;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    @Override
    public String toString() {
        return "BookReview{" +
                "book=" + book +
                ", posterUsername='" + posterUsername + '\'' +
                ", reviewText='" + reviewText + '\'' +
                ", reviewDate=" + reviewDate +
                '}';
    }
}
