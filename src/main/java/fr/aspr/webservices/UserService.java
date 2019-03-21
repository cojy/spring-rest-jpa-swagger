package fr.aspr.webservices;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import fr.aspr.exceptions.ForbiddenException;
import fr.aspr.model.User;
import fr.aspr.repositories.UserRepository;

@RestController
@RequestMapping("/users")
public class UserService {

	@Autowired
	private UserRepository ur;
		
	@CrossOrigin
	@PostMapping
	public User saveUser(@RequestBody User user) {
		return ur.save(user);
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
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable("id") long id) {
		ur.deleteById(id);
	}	
	
	
	@CrossOrigin
	@GetMapping
	public List<User> getByName(
			@RequestParam(value="name", required=true) String name,
			@RequestParam(value="id", required=false) long id) {
		
		if(name ==null || name.isEmpty()) {
			Iterable<User> itU = ur.findAll();
			return Lists.newArrayList(itU);
		}
		
		//for reference on error throwing
		if(id!=0)
			throw new ForbiddenException();
		
		return ur.findByName(name);		
		
	}
	
}
