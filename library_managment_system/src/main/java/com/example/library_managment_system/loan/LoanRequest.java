package com.example.library_managment_system.loan;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record LoanRequest(
    @NotNull Long bookId,
    @NotNull Long memberId,
    @NotNull @FutureOrPresent LocalDate dueDate
){}
