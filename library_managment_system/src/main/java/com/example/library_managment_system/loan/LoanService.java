package com.example.library_managment_system.loan;

import com.example.library_managment_system.book.Book;
import com.example.library_managment_system.book.BookService;
import com.example.library_managment_system.book.ResourceNotFoundException;
import com.example.library_managment_system.member.Member;
import com.example.library_managment_system.member.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate.*;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class LoanService {
    private final LoanRepository loans;
    private final BookService books;
    private final MemberService members;

    public LoanService(LoanRepository loans, BookService books, MemberService members) {
        this.loans = loans;
        this.books = books;
        this.members = members;
    }

    @Transactional(readOnly = true)
    public List<Loan> findAll(boolean activeOnly) {
        return activeOnly ? loans.findByStatus(LoanStatus.ACTIVE) : loans.findAll();
    }

    public Loan borrow(LoanRequest request) {
        LocalDate today = LocalDate.now();
        if (request.dueDate().isBefore(today)) {
            throw new IllegalArgumentException("Due date cannot be in the past.");
        }
        Book book = books.findById(request.memberId());
        Member member = members.findById(request.memberId());
        book.borrowCopy();
        return loans.save(new Loan(book, member, today, request.dueDate()));
    }

    public Loan returnBook(Long loanId) {
        Loan loan = loans.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan with id " + loanId + " not found"));
        if (loan.getStatus() == LoanStatus.RETURNED) {
            throw new IllegalStateException("This loan has already been returned.");
        }
        loan.getBook().returnCopy();
        loan.markReturned(LocalDate.now());
        return loan;

    }
}
