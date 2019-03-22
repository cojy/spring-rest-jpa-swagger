import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class UserServiceTest {

    public static void main(String[] args){
		RestTemplate restTemplate = new RestTemplate();
		
		User request = new User("test123", 45);
		
		ResponseEntity<User> response = restTemplate.postForEntity("http://192.168.1.85:9080/FireFighters/users", request, User.class);
		User result = response.getBody();
		
		if(result==null)
			System.exit(-1);
		
		if(result.getId() == 0)
			System.exit(-1);
		else
			System.out.println("User ID is "+result.getId());
					
		ResponseEntity<User> response2 = restTemplate.getForEntity("http://192.168.1.85:9080/FireFighters/users/"+result.getId(), User.class);
		User result2 = response2.getBody();
		
		if(!"test123".equals(result2.getName())){
			System.exit(-1);
		}else
			System.out.println("User name is "+result.getName());
		
		restTemplate.delete("http://192.168.1.85:9080/FireFighters/users/"+result.getId());
		
		boolean caughtError = false;
		try {
			response2 = restTemplate.getForEntity("http://192.168.1.85:9080/FireFighters/users/"+result.getId(), User.class);
		}catch(Exception e) {
			if(e.getMessage().contains("404"))
				caughtError = true;
			
			System.out.println("Error message is "+e.getMessage());
		}
		
		if(!caughtError)
			System.exit(-1);
		
		return;
	}
	
}
