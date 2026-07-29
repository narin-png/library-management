package dev.joint.library_management.controller.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/hello")
    public String admin() {
        return "Hello Admin";
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/user/hello")
    public String user() {
        return "Hello User";
    }
}
