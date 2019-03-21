package fr.aspr.webservices;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserService {

	@CrossOrigin
	@GetMapping("/test")
	public String getTest() {
		return "OK";
	}
	
}
