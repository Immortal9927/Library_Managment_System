package com.example.library_managment_system.book;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 150)
    private String author;

    @Column(unique = true, length = 20)
    private String isbn;

    private LocalDate publicationDate;

    @Column(nullable = false)
    private int totalCopies;

    @Column(nullable = false)
    private int availableCopies;

    /* JPA requires entity to have no argument constructor
    to create object when loading data from db1
    */
    protected Book() {}

    public Book(String title, String author, String isbn, LocalDate publicationDate, int totalCopies, int availableCopies) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void updateDetails (String title, String author, String isbn
    , LocalDate publicationDate, int totalCopies) {
        int borrowedCopies = this.totalCopies - this.availableCopies;
        if (totalCopies < borrowedCopies) {
            throw new IllegalArgumentException("Total copies cannot be less than the number of" +
                    "currently available copies");
        }
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.availableCopies = totalCopies - borrowedCopies;
    }

    public void borrowCopy() {
        if (availableCopies <= 0) {
            throw new IllegalStateException("No copy of this book is currently available");
        }
        this.availableCopies--;
    }

    public void returnCopy() {
        if (availableCopies >= totalCopies) {
            throw new IllegalStateException("All copies are in library already");
        }
        this.availableCopies++;
    }
}