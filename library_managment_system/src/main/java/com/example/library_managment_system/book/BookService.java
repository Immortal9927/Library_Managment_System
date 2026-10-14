package com.example.library_managment_system.book;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BookService {
    private final BookRepository books;

    public BookService(BookRepository books) {
        this.books = books;
    }

    @Transactional(readOnly = true)
    public List<Book> findAll(String search) {
        if (search == null || search.isBlank()) {
            return books.findAll();
        }
        return books.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(search, search);
    }

    @Transactional(readOnly = true)
    public Book findById(Long id) {
        return books.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    public Book create(BookRequest request) {
        if (request.isbn() != null && !request.isbn().isBlank()
        && books.existByIsbn(request.isbn())) {
            throw new IllegalArgumentException("Isbn already exists");
        }
        return books.save(new Book(request.title(), request.author(), request.isbn(),
                request.publicationDate(), request.totalCopies()));
    }

    public Book update(Long id, BookRequest request) {
        Book book = findById(id);
        if (request.isbn() != null && !request.isbn().isBlank()
            && books.existByIsbn(request.isbn())
            && !request.isbn().equals(book.getIsbn())) {
            throw new IllegalArgumentException("Isbn already exists");
        }
        book.updateDetails(request.title(), request.author(), request.isbn(),
                request.publicationDate(), request.totalCopies());
        return book;
    }

    public void delete(Long id) {
        Book book = findById(id);
        books.delete(book);
    }

}
