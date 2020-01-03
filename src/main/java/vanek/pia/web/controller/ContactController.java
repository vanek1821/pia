package vanek.pia.web.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vanek.pia.domain.Contact;
import vanek.pia.domain.User;
import vanek.pia.service.ContactManager;

@Controller
public class ContactController {
	
	ContactManager contactManager;
	
	public ContactController(ContactManager contactManager) {
		this.contactManager = contactManager;
	}
	
	@PostMapping("/contact")
	public ModelAndView newContact() {
		ModelAndView modelAndView = new ModelAndView("contact");
		
		return modelAndView;
	}
	
	@PostMapping("/confirmContact")
	public ModelAndView confirmContact(@Valid @ModelAttribute("contact") Contact contactValues) {
		ModelAndView modelAndView = new ModelAndView("contact");
		ModelMap modelMap = modelAndView.getModelMap();
		this.contactManager.addContact(contactValues);
		
		modelMap.addAttribute("message", "New Contact Confirmed Successfull");
		
		return modelAndView;
	}
	
	@PostMapping("/editContact")
	public ModelAndView editContact() {
		ModelAndView modelAndView = new ModelAndView("editContact");
		
		return modelAndView;
	}
	
	
	@PostMapping("/confirmEditContact")
	public ModelAndView confirmEditContact(@RequestParam("id") Long id, @Valid @ModelAttribute("contact") Contact contactValues) {
		ModelAndView modelAndView = new ModelAndView("redirect:/contactList");
		ModelMap modelMap = modelAndView.getModelMap();
		
		Contact contact = contactManager.getById(id);
		contactManager.updateContact(contact, contactValues);
		return modelAndView;
	}

	@PostMapping("/contactList")
	public ModelAndView userEdit_() {
		ModelAndView modelAndView = new ModelAndView("contactList");
		ModelMap modelMap = modelAndView.getModelMap();
		modelMap.addAttribute("contacts", contactManager.getContacts());

		return modelAndView;
	}
	
	@GetMapping("/contactList/contact")
	public ModelAndView editUser(@RequestParam("id") Long id) {
		Contact contact = contactManager.getById(id);
		ModelAndView modelAndView = new ModelAndView("editContact");
		ModelMap modelMap = modelAndView.getModelMap();
		modelMap.addAttribute("contact", contact);
		return modelAndView;
	}
	@GetMapping("/contactList")
	public ModelAndView contactList_() {
		ModelAndView modelAndView = new ModelAndView("contactList");
		ModelMap modelMap = modelAndView.getModelMap();
		modelMap.addAttribute("contacts", contactManager.getContacts());

		return modelAndView;
	}
	@GetMapping("/deleteContact")
	public ModelAndView deleteContact(@RequestParam("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("redirect:/contactList");
		ModelMap modelMap = modelAndView.getModelMap();
		contactManager.deleteContact(id);
		return modelAndView;
	}
	
	
}
