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
	public ModelAndView userEdit() {
		ModelAndView mav = new ModelAndView("userList");
		ModelMap modelMap = mav.getModelMap();
		modelMap.addAttribute("users", userManager.getUsers());

		return mav;
	}
	
	@GetMapping("/userList")
	public ModelAndView userEdit_() {
		ModelAndView mav = new ModelAndView("userList");
		ModelMap modelMap = mav.getModelMap();
		modelMap.addAttribute("users", userManager.getUsers());

		return mav;
	}
	
	
	@GetMapping("/userList/user")
	public ModelAndView editUser(@RequestParam("id") Long id) {
		User user = userManager.getById(id);
		ModelAndView mav = new ModelAndView("editUser");
		ModelMap modelMap = mav.getModelMap();
		if (user == null) {
			mav = new ModelAndView("userList");
			modelMap = mav.getModelMap();
			modelMap.addAttribute("message", "User Not Found");
			modelMap.addAttribute("users", userManager.getUsers());
		}
		else {
			modelMap.addAttribute("user", user);
			modelMap.addAttribute("roles", roleManager.getRoles());
		}
		
		
		return mav;
		
	}
		
	@PostMapping("/confirmEditUser")
	public ModelAndView confirmEdit(@RequestParam("id") Long id, @Valid @ModelAttribute("user") User userValues) {
		ModelAndView mav = new ModelAndView("redirect:/userList");
		ModelMap modelMap = mav.getModelMap();
		
		User user = userManager.getById(id);
		userManager.updateUser(user, userValues);
		return mav;
	}
	
	@GetMapping("/delete")
	public ModelAndView deleteUser(@RequestParam("id") Long id) {
		ModelAndView mav = new ModelAndView("redirect:/userList");
		ModelMap modelMap = mav.getModelMap();
		userManager.deleteUser(id);
		return mav;
	}

}
