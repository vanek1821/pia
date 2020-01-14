package vanek.pia.web.controller;

import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
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
	public ModelAndView editUser(@RequestParam("id") String id) {
		
		ModelAndView mav = new ModelAndView("editUser");
		ModelMap modelMap = mav.getModelMap();
		Long idLong = null;
		try {
			idLong = Long.parseLong(id);
		} catch (Exception e){
			mav = new ModelAndView("userList");
			modelMap = mav.getModelMap();
			modelMap.addAttribute("message", "Wrong ID Format");
			modelMap.addAttribute("users", userManager.getUsers());
			return mav;
		}
		
		User user = userManager.getById(idLong);
		
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
	public ModelAndView confirmEdit(@RequestParam("id") String id, @Valid @ModelAttribute("user") User userValues) {
		ModelAndView mav = new ModelAndView("redirect:/userList");
		ModelMap modelMap = mav.getModelMap();
		
		Long idLong = null;
		try {
			idLong = Long.parseLong(id);
		} catch (Exception e){
			mav = new ModelAndView("userList");
			modelMap = mav.getModelMap();
			modelMap.addAttribute("message", "Wrong ID Format");
			modelMap.addAttribute("users", userManager.getUsers());
			return mav;
		}
		
		User user = userManager.getById(idLong);
		userManager.updateUser(user, userValues);
		return mav;
	}
	
	@GetMapping("/delete")
	public ModelAndView deleteUser(@RequestParam("id") String id) {
		ModelAndView mav = new ModelAndView("redirect:/userList");
		ModelMap modelMap = mav.getModelMap();
		
		Long idLong = null;
		try {
			idLong = Long.parseLong(id);
		} catch (Exception e){
			mav = new ModelAndView("userList");
			modelMap = mav.getModelMap();
			modelMap.addAttribute("message", "Wrong ID Format");
			modelMap.addAttribute("users", userManager.getUsers());
			return mav;
		}
		
		userManager.deleteUser(idLong);
		return mav;
	}
	
	@PostMapping("/confirmPasswordChange")
	public ModelAndView confirmPasswordChange(@RequestParam(value="currentPassword") String currentPassword, 
			@RequestParam(value="newPassword") String newPassword,
			@RequestParam(value="confirmPassword") String confirmPassword) {
		ModelAndView mav = new ModelAndView("changePassword");
		ModelMap modelMap = mav.getModelMap();
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		switch (userManager.changePassword(username ,currentPassword, newPassword, confirmPassword)) {
		case -1:
			modelMap.addAttribute("message", "Current Password not filled.");
			break;
		case -2:
			modelMap.addAttribute("message", "New Password not filled.");
			break;

		case -3:
			modelMap.addAttribute("message", "Confirm Password not filled.");
			break;

		case -4:
			modelMap.addAttribute("message", "New Password is same as Current Password.");
			break;

		case -5:
			modelMap.addAttribute("message", "New Password isn't same as Confirm Password");
			break;

		case -6:
			modelMap.addAttribute("message", "Wrong Current Password");
			break;

		default:
			modelMap.addAttribute("message", "Password successfully changed.");
			break;
		}
		modelMap.addAttribute("user", userManager.findUserByName(username));

		return mav;
	}
	
	
	@GetMapping("/passwordChange")
	public ModelAndView passwordChange_() { 
		ModelAndView mav = new ModelAndView("changePassword");
		ModelMap modelMap = mav.getModelMap();
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		modelMap.addAttribute("user", userManager.findUserByName(username));

		return mav;
	}

}
