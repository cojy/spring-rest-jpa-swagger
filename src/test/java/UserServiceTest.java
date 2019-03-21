import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import fr.aspr.model.User;

public class UserServiceTest {

	@Before
    public void setUp() throws Exception {
		
	}
	
	@Test
    public void testUser(){
		RestTemplate restTemplate = new RestTemplate();
		
		User request = new User("test123", 45);
		
		ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:8080/FireFighters/users", request, User.class);
		User result = response.getBody();
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotEquals(result.getId(), 0);
		
		ResponseEntity<User> response2 = restTemplate.getForEntity("http://localhost:8080/FireFighters/users/"+result.getId(), User.class);
		User result2 = response2.getBody();
		
		assertEquals(result2.getName(), "test123");
		
		restTemplate.delete("http://localhost:8080/FireFighters/users/"+result.getId());
		
		boolean caughtError = false;
		try {
			response2 = restTemplate.getForEntity("http://localhost:8080/FireFighters/users/"+result.getId(), User.class);
		}catch(Exception e) {
			if(e.getMessage().contains("404"))
				caughtError = true;
		}
		
		assertTrue(caughtError);
		
	}
	
}
