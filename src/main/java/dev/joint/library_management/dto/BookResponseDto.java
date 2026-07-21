package dev.joint.library_management.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.joint.library_management.entity.Author;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDto {
    private Integer id;
    private String title;
    private Integer publishedYear;
}
