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
	public ModelAndView registration_() {
		ModelAndView modelAndView = new ModelAndView("registration");
		ModelMap modelMap = modelAndView.getModelMap();
		modelMap.addAttribute("roles", roleManager.getRoles());
		modelMap.addAttribute("user", new User());
		return modelAndView;
	}
	
	@PostMapping("/register")
	public ModelAndView register(@Valid @ModelAttribute("user") User userValues) {
		ModelAndView modelAndView = new ModelAndView("registration");
		ModelMap modelMap = modelAndView.getModelMap();
		//this.userManager.addUser(userValues.getUsername(), userValues.getPassword(), userValues.getFullName(), userValues.getPersonalIDNum(), userValues.getAdress(), userValues.getEmail(), userValues.getPhone(), userValues.getBankAcc());
		if (this.userManager.addUser(userValues)){
			modelMap.addAttribute("roles", roleManager.getRoles());
			modelMap.addAttribute("class", "success");
			modelMap.addAttribute("message", "Registration Successfull");
			return modelAndView;
		}
		else {
			modelMap.addAttribute("roles", roleManager.getRoles());
			modelMap.addAttribute("class", "error");
			modelMap.addAttribute("message", "ERROR: user already exists");
			return modelAndView;
		}
	}

}
 