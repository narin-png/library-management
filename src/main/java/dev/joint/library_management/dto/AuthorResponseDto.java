package dev.joint.library_management.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.joint.library_management.entity.Book;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponseDto {
    private Integer id;
    private String name;
    private String email;
}
