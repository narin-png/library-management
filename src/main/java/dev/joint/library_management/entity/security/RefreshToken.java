package dev.joint.library_management.entity.security;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;
    private String userName;
    private boolean valid;
    private Date isssueDate;
    private Date expiresDate;

    public RefreshToken(String token, String userName, boolean valid, Date isssueDate, Date expiresDate) {
        this.token = token;
        this.userName = userName;
        this.valid = valid;
        this.isssueDate = isssueDate;
        this.expiresDate = expiresDate;
    }
    public boolean getValid() {
        return valid;
    }
}
