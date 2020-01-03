package vanek.pia.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import vanek.pia.service.ContactManager;
import vanek.pia.service.UserManager;

@Controller
public class IndexController {
	
	UserManager userManager;
	ContactManager contactManager;
	
	public IndexController(UserManager userManager, ContactManager contactManager) {
		this.userManager = userManager;
		this.contactManager = contactManager;
	}

	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("index");
		ModelMap modelMap = modelAndView.getModelMap();
		modelMap.addAttribute("users", userManager.getUsers());
		modelMap.addAttribute("contacts", contactManager.getContacts());
		
		return modelAndView;
	}

}
