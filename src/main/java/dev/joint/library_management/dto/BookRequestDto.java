package dev.joint.library_management.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDto {

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Published year is required")
    private Integer publishedYear;

    @NotNull(message = "Author id is required")
    private Integer authorId;

    @NotNull(message = "Total copies is required")
    @Min(value = 0, message = "Total copies cannot be negative")
    private Integer totalCopies;

    private Set<Integer> categoryIds;
}

