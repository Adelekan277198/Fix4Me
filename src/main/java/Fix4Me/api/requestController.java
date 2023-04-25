package Fix4Me.api;

import Fix4Me.model.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class requestController {
	
	@Autowired  
    JdbcTemplate jdbc;
	
	@RequestMapping("/")  
    public String index(){
		return "Welcome Akeem Adedotun Adelekan To Fix4Me Java Application";   	
   }
	
		
	@PostMapping("/login")  
    public ResponseEntity<?> userslogin(@RequestBody members user){
		 String email = user.getemail();
		 String password = user.getpassword(); 	 
  	     String query = "SELECT * FROM `users_table` WHERE email ='"+email+"' AND password = '"+password+"'";
        List<Map<String, Object>> rows = jdbc.queryForList(query);   
        int count = rows.size();
        Map<String, String> msg = new HashMap<>();
        
        if (count > 0) {
        	       	 
        	    msg.put("message", "Successfully");
        	    return ResponseEntity.ok(msg);
            
        } else {

            msg.put("message", "Invalid User Login");
            return ResponseEntity.badRequest().body(msg);
        }

	}
	
	
	 @RequestMapping(value="member/{user_id}") 
	 public ResponseEntity<?> memberdetails(@PathVariable(value= "") int user_id) {	 
	        String query = "SELECT * FROM `users_table` WHERE user_id ="+user_id;	        
	        List<Map<String, Object>> rows = jdbc.queryForList(query);   
	        int count = rows.size();
	        Map<String, String> msg = new HashMap<>();
	       
	        if (count > 0) {        	       	 
	        	    return ResponseEntity.ok(rows);     
	        } else {
	            msg.put("message", "No Available Service");
	            return ResponseEntity.badRequest().body(msg);
	        }

	    }
	 
	 
	 @RequestMapping("/members")  
	    public List<Map<String, Object>> members(){	 
	     String query = "SELECT * FROM `users_table`";
   List<Map<String, Object>> rows = jdbc.queryForList(query);                 
   return rows;
	 }
	  
	 @RequestMapping(value="veiwService/{service_id}") 
	 public ResponseEntity<?> VeiwService(@PathVariable(value= "") int service_id) {	 
	        String query = "SELECT * FROM service_table WHERE service_id ='"+service_id+"'";	        
	        List<Map<String, Object>> rows = jdbc.queryForList(query);   
	        int count = rows.size();
	        Map<String, String> msg = new HashMap<>();
	       
	        if (count > 0) {        	       	 
	        	    return ResponseEntity.ok(rows);     
	        } else {
	            msg.put("message", "No Available Service");
	            return ResponseEntity.badRequest().body(msg);
	        }

	    }
	 
	 @RequestMapping("/postedServices")  
	    public List<Map<String, Object>> postedService() {
		    String query = "SELECT * FROM service_table WHERE service_status ='Pending'";
	        List<Map<String, Object>> rows = jdbc.queryForList(query);
			return rows;

	    }
	 
	 @RequestMapping(value="awardedService/{service_provider_id}")  
	 public ResponseEntity<?> awardedService(@PathVariable(value= "") int service_provider_id) {
		    String query = "SELECT * FROM service_table WHERE service_provider_id ="+service_provider_id;
		    List<Map<String, Object>> rows = jdbc.queryForList(query);   
	        int count = rows.size();
	        Map<String, String> msg = new HashMap<>();
	       
	        if (count > 0) {        	       	 
	        	    return ResponseEntity.ok(rows);     
	        } else {
	            msg.put("message", "No Service has been awarded to you");
	            return ResponseEntity.badRequest().body(msg);
	        }


	    }
	 
	 @RequestMapping(value="viewProvider/{user_id}")  
     public List<Map<String, Object>> viewUser(@PathVariable(value= "") int user_id){	 
	     String query = "SELECT * FROM `users_table` WHERE user_id ='"+user_id+"'";
      List<Map<String, Object>> rows = jdbc.queryForList(query);                 
      return rows;
 	 }
	 
	 @RequestMapping("/postedBids")  
	    public List<Map<String, Object>> postedBids() {	
	    	
			String query = "SELECT * FROM service_bid";
	        List<Map<String, Object>> rows = jdbc.queryForList(query);
			return rows;

	    }
	 
	 @RequestMapping(value="show/{msg}", method= RequestMethod.GET)
	    public String show(@PathVariable(value= "") String msg){

	        return "Hello World "+msg;
	    }
	 
}
