package dev.joint.library_management.dto.security;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenDto {
    private Integer id;
    private String token;
    private String userName;
    private boolean valid;
    private Date isssueDate;
    private Date expiresDate;
}
