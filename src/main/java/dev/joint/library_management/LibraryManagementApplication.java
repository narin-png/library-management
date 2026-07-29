package dev.joint.library_management;

import dev.joint.library_management.entity.security.Authority;
import dev.joint.library_management.entity.security.User;
import dev.joint.library_management.repository.security.AuthorityRepository;
import dev.joint.library_management.repository.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


@SpringBootApplication
@RequiredArgsConstructor
public class LibraryManagementApplication implements CommandLineRunner {
	private final UserRepository userRepository;
	private final AuthorityRepository authorityRepository;
	private final PasswordEncoder passwordEncoder;
	@Override
	public void run(String... args) throws Exception {
//				Authority authorityUser = new Authority();
//		authorityUser.setAuthority("ROLE_USER");
//		authorityRepository.save(authorityUser);
//		Authority authorityAdmin = new Authority();
//		authorityAdmin.setAuthority("ROLE_ADMIN");
//		authorityRepository.save(authorityAdmin);
//		User user = new User();
//		user.setUsername("user");
//		user.setPassword(passwordEncoder.encode("1234"));
//		user.setAuthorities(List.of(authorityUser));
//		userRepository.save(user);
//		User userAdmin = new User();
//		userAdmin.setUsername("admin");
//		userAdmin.setPassword(passwordEncoder.encode("12345"));
//		userAdmin.setAuthorities(List.of(authorityUser,authorityAdmin));
//		userRepository.save(userAdmin);
	}

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementApplication.class, args);
	}

}
