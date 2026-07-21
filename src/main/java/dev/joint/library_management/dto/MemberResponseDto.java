package dev.joint.library_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private Integer id;
    private String name;
    private String email;
    private String phone;
}
