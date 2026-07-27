package dev.joint.library_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
}

