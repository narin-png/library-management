package dev.joint.library_management.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Table(name = "members")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String phone;
}
