package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;

@RestController
public class HomeController {

	@Autowired
	private UserRepository userRepo;
	
	
	@RequestMapping(value="/addUser",method=RequestMethod.POST)
	@ResponseBody
	public User addUser(@RequestBody User user){
		User u = userRepo.save(user);
		return u;
	}
	
	@RequestMapping(value="/private",method=RequestMethod.GET)
	@ResponseBody
	public String securedResource(){
		return "This was a protected resource";
	}
	
	@RequestMapping(value="/public",method=RequestMethod.GET)
	@ResponseBody
	public String generalResource(){
		return "This was a general resource";
	}
}
