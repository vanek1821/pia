package vanek.pia.web.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import vanek.pia.domain.User;
import vanek.pia.service.RoleManager;
import vanek.pia.service.UserManager;

@Controller
public class RegistrationController {
	
	private UserManager userManager;
	private RoleManager roleManager;
	
	public RegistrationController(UserManager userManager, RoleManager roleManager) {
		this.userManager = userManager;
		this.roleManager = roleManager;
	}
	
	@PostMapping("/registration")
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView("registration");
		ModelMap modelMap = modelAndView.getModelMap();
		modelMap.addAttribute("roles", roleManager.getRoles());
		modelMap.addAttribute("user", new User());
		return modelAndView;
	}
	
	@GetMapping("/registration")
	public String registration_() {
		return "registration";
	}
	
	@PostMapping("/register")
	public ModelAndView register(@Valid @ModelAttribute("user") User userValues) {
		ModelAndView modelAndView = new ModelAndView("registration");
		ModelMap modelMap = modelAndView.getModelMap();
		//this.userManager.addUser(userValues.getUsername(), userValues.getPassword(), userValues.getFullName(), userValues.getPersonalIDNum(), userValues.getAdress(), userValues.getEmail(), userValues.getPhone(), userValues.getBankAcc());
		this.userManager.addUser(userValues);
		modelMap.addAttribute("message", "Registration Successfull");
		//modelMap.addAttribute("users", userManager.getUsers());

		return modelAndView;
		//return new ModelAndView("/registration");
		
	}

}
 