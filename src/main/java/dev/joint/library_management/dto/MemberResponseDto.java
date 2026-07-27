package dev.joint.library_management.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private Integer id;
    private String name;
    private String email;
    private String phone;
}
