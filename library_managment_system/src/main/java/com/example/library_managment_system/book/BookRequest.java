package com.example.library_managment_system.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record BookRequest (
    @NotBlank @Size(max = 200) String title,
    @NotBlank @Size(max = 150) String author,
    @Size(max = 20) String isbn,
    LocalDate publicationDate,
    @Min(1) int totalCopies
) {

}