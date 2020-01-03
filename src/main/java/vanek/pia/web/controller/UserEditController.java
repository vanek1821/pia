package vanek.pia.web.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vanek.pia.domain.User;
import vanek.pia.service.RoleManager;
import vanek.pia.service.UserManager;

@Controller
public class UserEditController {
	
private UserManager userManager;
private RoleManager roleManager;
	
	public UserEditController(UserManager userManager, RoleManager roleManager) {
		this.userManager = userManager;
		this.roleManager = roleManager;
	}
	
	@PostMapping("/userList")
	public String userEdit() {
		return "userList";
	}
	
	@GetMapping("/userList")
	public ModelAndView userEdit_() {
		ModelAndView modelAndView = new ModelAndView("userList");
		ModelMap modelMap = modelAndView.getModelMap();
		modelMap.addAttribute("users", userManager.getUsers());

		return modelAndView;
	}
	
	
	@GetMapping("/userList/user")
	public ModelAndView editUser(@RequestParam("id") Long id) {
		User user = userManager.getById(id);
		ModelAndView modelAndView = new ModelAndView("editUser");
		ModelMap modelMap = modelAndView.getModelMap();
		modelMap.addAttribute("user", user);
		modelMap.addAttribute("roles", roleManager.getRoles());
		
		return modelAndView;
		
	}
	
	@PostMapping("/confirmEdit")
	public ModelAndView confirmEdit(@RequestParam("id") Long id, @Valid @ModelAttribute("user") User userValues) {
		ModelAndView modelAndView = new ModelAndView("redirect:/userList");
		ModelMap modelMap = modelAndView.getModelMap();
		
		User user = userManager.getById(id);
		userManager.updateUser(user, userValues);
		return modelAndView;
	}
	
	@GetMapping("/delete")
	public ModelAndView deleteUser(@RequestParam("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("redirect:/userList");
		ModelMap modelMap = modelAndView.getModelMap();
		userManager.deleteUser(id);
		return modelAndView;
	}

}
