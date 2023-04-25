package Fix4Me.api;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.fasterxml.jackson.core.JsonParser;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Fix4Me.model.members;
import Fix4Me.model.services;
import Fix4Me.model.services_providers_bids;


@RestController
public class postController {
	
	@Autowired  
    JdbcTemplate jdbc; 
	 	
	 
	 @PostMapping("/signup")
	    public String createAccount(@RequestBody members user) {
	 
		 String email = user.getemail();
		 String password = user.getpassword();
		 String first_name = user.getfirst_name();
		 String last_name = user.getlast_name();
		 String phone = user.getphone();
		 String dob = user.getdob();
		 String gender_type = user.getgender_type();
		 String user_type = user.getuser_type();
		 String user_rating = user.getuser_rating();
		 String confirm_email = user.getconfirm_email();
		 String confirm_email_otp = user.getconfirm_email_otp();
	 
		 if(email!=null || password!=null || first_name!=null || last_name!=null || phone!=null || gender_type!=null || user_type!=null)
		 {
			 String sql = "INSERT INTO `users_table` (`user_id`, `first_name`, `last_name`, `phone`, `email`, `dob`, `password`, `gender_type`, `user_type`, `user_rating`, `confirm_email`, `confirm_email_otp`) VALUES (NULL, '"+first_name+"', '"+last_name+"', '"+phone+"', '"+email+"', '"+dob+"', '"+password+"', '"+gender_type+"', '"+user_type+"', '"+user_rating+"', '"+confirm_email+"', '"+confirm_email_otp+"')";		 
		    	jdbc.execute(sql); 
			 return "Signup successfully!";	
	    	 	 
		 }else {
			 return "one or more user's entry is empty";
		 }
	    	
	    }
	 
	 
	 @PostMapping("/postService")
	    public String postService(@RequestBody services service) {
	 
		 String service_type = service.getservice_type();
		 String service_title = service.getservice_title();
		 String service_description = service.getservice_description();
		 String service_picture = service.getservice_picture();
		 String service_location = service.getservice_location();
		 String service_request_date = service.getservice_request_date();
		 String service_post_date = service.getservice_post_date();
		 String client_id = service.getclient_id();
		 String client_name = service.getclient_name();
		 String client_budget = service.getclient_budget();
		 int service_provider_id = service.getservice_provider_id();
		 String service_provider_name = service.getservice_provider_name();
		 String service_status = "Pending";
		 String service_fee = service.getservice_fee();
		 
	 
		 String sql = "INSERT INTO `service_table` (`id`, `service_type`, `service_title`, `service_description`, `service_picture`, `service_location`, `service_request_date`, `service_post_date`, `client_id`, `client_name`, `client_budget`, `service_provider_id`, `service_provider_name`, `service_status`, `service_fee`) VALUES (NULL, '"+service_type+"', '"+service_title+"', '"+service_description+"', '"+service_picture+"', '"+service_location+"', '"+service_request_date+"', '"+service_post_date+"', '"+client_id+"', '"+client_name+"', '"+client_budget+"', '"+service_provider_id+"', '"+service_provider_name+"', '"+service_status+"', '"+service_fee+"')";		 
	    	jdbc.execute(sql); 
	    	
		 return "Service has been posted successfully!";
	    	
	    }
	 

	 
	 @DeleteMapping(value="cancelService/{service_id}") 
	 public ResponseEntity<?> cancelService(@PathVariable(value= "") int service_id) {
		 
		 String updateSql = "DELETE FROM `service_table` WHERE service_id ="+service_id;
		 int count = jdbc.update(updateSql);
	     Map<String, String> msg = new HashMap<>();
	       
	        if (count > 0) { 
	        	msg.put("message", "Your Service has been Cancel");
	      	    return ResponseEntity.ok(msg);     
	        } else {
	            msg.put("message", "No Available Service");
	            return ResponseEntity.badRequest().body(msg);
	        }

	    }
	 
	    @PostMapping("/postBid")
	    public String postBid(@RequestBody services_providers_bids bid) {
	 
		 String service_id = bid.getservice_id();
		 String service_type = bid.getservice_type();
		 String service_provider_id = bid.getservice_provider_id();
		 String service_provider_name = bid.getservice_provider_name();
		 String bid_amount = bid.getbid_amount();
		 String service_duration = bid.getservice_duration();
		 String client_id = bid.getclient_id();
	 	 String sql = "INSERT INTO `service_bid` (`bid_id`, `service_id`, `service_type`, `service_provider_id`, `service_provider_name`, `bid_amount`, `service_duration`, `client_id`) VALUES (NULL, '"+service_id+"', '"+service_type+"', '"+service_provider_id+"', '"+service_provider_name+"', '"+bid_amount+"', '"+service_duration+"', '"+client_id+"')";		 
	     jdbc.execute(sql); 
	    	
		 return "Bid has been posted successfully! ";
	    	
	    }
	    
	    @PutMapping(value="acceptBid/{bid_id}")
	    public String acceptBid(@PathVariable(value= "") int bid_id){	 
	 		 
	 	     String query = "SELECT * FROM `service_bid` WHERE bid_id ="+bid_id;
	    List<Map<String, Object>> rows = jdbc.queryForList(query);     
	    ObjectMapper objectMapper = new ObjectMapper();
	    
	    
	    try {
	   	 
	   	 String resultString = objectMapper.writeValueAsString(rows);
	        JsonNode rootNode = objectMapper.readTree(resultString);
	        
	        JsonNode service_idNode = rootNode.get(0).get("service_id");
	        String service_id = service_idNode.asText();

	        JsonNode service_provider_idNameNode = rootNode.get(0).get("service_provider_id");
	        String service_provider_id = service_provider_idNameNode.asText();
	        
	        JsonNode serviceProviderNameNode = rootNode.get(0).get("service_provider_name");
	        String serviceProviderName = serviceProviderNameNode.asText();

	        JsonNode bidAmountNode = rootNode.get(0).get("bid_amount");
	        String bidAmount = bidAmountNode.asText();

	        String sql = "UPDATE `service_table` SET `service_provider_id` = '"+service_provider_id+"', `service_provider_name` = '"+serviceProviderName+"', `service_status` = 'In Process', `service_fee` = '"+bidAmount+"' WHERE `service_id` ='"+service_id+"'";	 
	        jdbc.update(sql);
	        
	        String del_sql = "DELETE FROM `service_bid` WHERE `bid_id` = '"+service_id+"'";	 
	        jdbc.update(del_sql);
	        
	        return "You have accepted the bid of " + bidAmount + " and the task has been awarded to " + serviceProviderName + " with service ID " + service_id;
	        
	        //System notify the Service to Provider 

	        
	    } catch (Exception e) {
	   	 
	   	 return"Error parsing JSON string: " + e.getMessage();
	    }

	 }
	 	 
	 
	 
	 @PutMapping("unfollowAwardedServices/{service_id}")
	    public String unfollowAwardedServices(@PathVariable(value= "") String service_id){
		 
		 String sql = "UPDATE `service_table` SET `service_provider_id` = ' ', `service_provider_name` = ' ', `service_status` = 'Pending', `service_fee` = ' ' WHERE `service_id` ='"+service_id+"'";	 
         jdbc.update(sql);
         
		return "You Have Successfully Unfollow the Task awarded to you, with service ID " + service_id;
		
		//System notify the person that request for Service 
	 }
	
}
