package dev.joint.library_management.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private Integer id;
    private String username;
    private String password;
}
