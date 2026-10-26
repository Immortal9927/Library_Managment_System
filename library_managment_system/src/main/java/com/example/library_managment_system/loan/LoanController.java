package com.example.library_managment_system.loan;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
    private final LoanService service;

    public LoanController(LoanService service) {
        this.service = service;
    }

    @GetMapping
    public List<Loan> getLoans(@RequestParam(defaultValue = "false") boolean activeOnly) {
        return service.findAll(activeOnly);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Loan borrowBook(@Valid @RequestBody LoanRequest request) {
        return service.borrow(request);
    }

    @PutMapping("/{id}/return")
    public Loan returnBook(@PathVariable Long id) {
        return service.returnBook(id);
    }
}
