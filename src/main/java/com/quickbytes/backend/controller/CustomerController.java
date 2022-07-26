package com.quickbytes.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quickbytes.backend.model.Customer;
import com.quickbytes.backend.repository.CustomerRepository;

@RestController
public class CustomerController {
	@Autowired
	private CustomerRepository customerRepository;

	
	// Post Customer \\
	@PostMapping("/customer")
	public void postCustomer(@RequestBody Customer customer){
		customerRepository.save(customer);
	}
	// Get All Customer in a List<Customer>  \\
	@GetMapping("/customer")
	public List<Customer>getAllCustomer(){
		return customerRepository.findAll();
	}
	// Get Specific Customer by CustomerID in Customer Obj \\
	@GetMapping("/customer/{cid}")
	public Customer getCustomerById(@PathVariable("cid")Long id) {
		Optional<Customer> optional = customerRepository.findById(id);
		if(!optional.isPresent())
			throw new RuntimeException ("Customer ID Doesn't Exist");
		return optional.get();
	}
	// Delete Specific Customer by CustomerID \\
	@DeleteMapping("/customer/{cid}")
	public void deleteCustomerById(@PathVariable("cid")Long id) {
		customerRepository.deleteById(id);
	}
	
	/*	ISSUE:
	 * Instead of updating, creates new record incrementing
	 * customer id.
	 * 
	 * FIXED: 08/01 - Jerry
	 * */
	
	// Update (Put) Customer By CustomerID\\
	@PutMapping("/customer/{cid}")
	public void updateCustomerById(@PathVariable("cid")Long cid,
			@RequestBody Customer updatedCustomer) {
		Optional<Customer> optional = customerRepository.findById(cid);
		if(!optional.isPresent())
			throw new RuntimeException ("Customer ID Doesn't Exist");
		Customer existingCustomer = optional.get();
		existingCustomer.setEmployeeId(updatedCustomer.getEmployeeId());
		existingCustomer.setFirstName(updatedCustomer.getFirstName());
		existingCustomer.setLastName(updatedCustomer.getLastName());
		existingCustomer.setBalance(updatedCustomer.getBalance());
		existingCustomer.setUserId(existingCustomer.getUserId());
		customerRepository.save(existingCustomer);
	}
	
	/* Extra Functionality*/
	
	/*ISSUE:
	 * Employee IDs are default 0 so this won't work as aspected.
	 * Generate random ID or force Admin to provide employee ID for Customers
	 * */
	
	// Get Specific Customer by Employee ID \\
	@GetMapping("/customer/employee/{eid}")
	public Customer getCustomerByEmployeeId(@PathVariable("eid") int eid){
		Customer user = customerRepository.getCustomerByEmployeeId(eid);
		return user;
	}
	
	/* REMOVED:
	 * Customers derive username from parent User
	 * */
	
	// Get Specific Customer by user name \\
	//@GetMapping("/customer/user/{username}")
	//public Customer getCustomerByUsername(@PathVariable("username") String username){
		//Customer user = customerRepository.getCustomerByUsername(username);
		//return user;
	//}
	
	// Get List <Customer> by First Name \\
	@GetMapping("/customer/fname/{firstName}")
	List<Customer> getListCustomerWithFirstName(@PathVariable("firstName") String firstName){
		List<Customer> list = customerRepository.getListCustomerWithFirstName(firstName);
		return list;
	}
	// Get List <Customer> by Last Name \\
	@GetMapping("/customer/lname/{lastName}")
	List<Customer> getListCustomerWithLastName(@PathVariable("lastName") String lastName){
		List<Customer> list = customerRepository.getListCustomerWithLastName(lastName);
		return list;
	}
	// Get List <Customer> by Balance greater than or equal to \\
	@GetMapping("/customer/balancegte/{balance}")
	List<Customer> getListCustomerWithBalanceGreaterThanOrEqual(@PathVariable("balance") Float balance){
		List<Customer> list = customerRepository.getListCustomerWithBalanceGreaterThanOrEqual(balance);
		return list;
	}
	// Get List <Customer> by Balance less than or equal to \\
	@GetMapping("/customer/balancelte/{balance}")
	List<Customer> getListCustomerWithBalanceLessThanOrEqual(@PathVariable("balance") Float balance){
		List<Customer> list = customerRepository.getListCustomerWithBalanceLessThanOrEqual(balance);
		return list;
	}
}
