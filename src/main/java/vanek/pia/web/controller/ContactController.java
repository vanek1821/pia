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
	
	@PostMapping("/newContact")
	public ModelAndView newContact() {
		ModelAndView mav = new ModelAndView("newContact");
		
		return mav;
	}
	
	@PostMapping("/confirmContact")
	public ModelAndView confirmContact(@Valid @ModelAttribute("contact") Contact contactValues) {
		ModelAndView mav = new ModelAndView("newContact");
		ModelMap modelMap = mav.getModelMap();
		this.contactManager.addContact(contactValues);
		
		modelMap.addAttribute("message", "New Contact Confirmed Successfull");
		
		return mav;
	}
	
	@PostMapping("/editContact")
	public ModelAndView editContact() {
		ModelAndView mav = new ModelAndView("editContact");
		
		return mav;
	}
	
	
	@PostMapping("/confirmEditContact")
	public ModelAndView confirmEditContact(@RequestParam("id") Long id, @Valid @ModelAttribute("contact") Contact contactValues) {
		ModelAndView mav = new ModelAndView("redirect:/contactList");
		ModelMap modelMap = mav.getModelMap();
		Contact contact = contactManager.getById(id);
		contactManager.updateContact(contact, contactValues);
		return mav;
	}

	@PostMapping("/contactList")
	public ModelAndView userEdit_() {
		ModelAndView mav = new ModelAndView("contactList");
		ModelMap modelMap = mav.getModelMap();
		modelMap.addAttribute("contacts", contactManager.getContacts());

		return mav;
	}
	
	@GetMapping("/contactList/contact")
	public ModelAndView editUser(@RequestParam("id") Long id) {
		Contact contact = contactManager.getById(id);
		ModelAndView mav = new ModelAndView("editContact");
		ModelMap modelMap = mav.getModelMap();
		if(contact == null) {
			mav = new ModelAndView("contactList");
			modelMap = mav.getModelMap();
			modelMap.addAttribute("message", "Contact Not Found");
			modelMap.addAttribute("contacts", contactManager.getContacts());
			
		}
		else {
			modelMap.addAttribute("contact", contact);
		}
		
		return mav;
	}
	@GetMapping("/contactList")
	public ModelAndView contactList_() {
		ModelAndView mav = new ModelAndView("contactList");
		ModelMap modelMap = mav.getModelMap();
		modelMap.addAttribute("contacts", contactManager.getContacts());

		return mav;
	}
	@GetMapping("/deleteContact")
	public ModelAndView deleteContact(@RequestParam("id") Long id) {
		ModelAndView mav = new ModelAndView("redirect:/contactList");
		ModelMap modelMap = mav.getModelMap();
		contactManager.deleteContact(id);
		return mav;
	}
	
	
}
