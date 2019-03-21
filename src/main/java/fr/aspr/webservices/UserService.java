package fr.aspr.webservices;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.aspr.exceptions.ForbiddenException;
import fr.aspr.model.User;
import fr.aspr.repositories.UserRepository;

@RestController
@RequestMapping("/users")
public class UserService {

	@Autowired
	private UserRepository ur;
	
	@CrossOrigin
	@GetMapping("/")
	public Iterable<User> getAllUsers() {
		return ur.findAll();
	}
	
	@CrossOrigin
	@GetMapping("/{id}")
	public ResponseEntity<User> getById(
			@PathVariable("id") long id) {
		Optional<User> opt = ur.findById(id);
		if(opt.isPresent())
			return new ResponseEntity<User>(opt.get(), HttpStatus.OK);
		
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}
	
	@CrossOrigin
	@GetMapping
	public User getByName(
			@RequestParam(value="name", required=true) String name) {
		
		//this will not happen, it's an example if the name had required=false
		if(name ==null || name.isEmpty())
			throw new ForbiddenException();
		
		return ur.findByName(name);		
		
	}
	
}
