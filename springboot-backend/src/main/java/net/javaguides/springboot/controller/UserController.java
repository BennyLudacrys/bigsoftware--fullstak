package net.javaguides.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.user;
import net.javaguides.springboot.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:4202")
@RestController
@RequestMapping("/api/v1/")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	// get all employees
	@GetMapping("/user")
	public List<user> getAllEmployees(){
		return userRepository.findAll();
	}		
	
	// create employee rest api
	@PostMapping("/user")
	public user createEmployee(@RequestBody user user) {
		return userRepository.save(user);
	}
	
	// get employee by id rest api
	@GetMapping("/user/{id}")
	public ResponseEntity<user> getEmployeeById(@PathVariable Long id) {
		user user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		return ResponseEntity.ok(user);
	}
	
	// update employee rest api
	
	@PutMapping("/user/{id}")
	public ResponseEntity<user> updateEmployee(@PathVariable Long id, @RequestBody user userDetails){
		user user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		user.setEmailId(userDetails.getEmailId());
		
		user updatedUser = userRepository.save(user);
		return ResponseEntity.ok(updatedUser);
	}
	
	// delete employee rest api
	@DeleteMapping("/user/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		user user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nao existe usuario com id :" + id));
		
		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	
}
