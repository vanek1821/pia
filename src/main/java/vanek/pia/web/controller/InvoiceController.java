package vanek.pia.web.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import vanek.pia.domain.Contact;
import vanek.pia.domain.Invoice;
import vanek.pia.service.ContactManager;
import vanek.pia.service.InvoiceManager;

@Controller
public class InvoiceController {
	
	InvoiceManager invoiceManager;
	ContactManager contactManager;
	
	public InvoiceController(InvoiceManager invoiceManager, ContactManager contactManager) {
		this.invoiceManager = invoiceManager;
		this.contactManager = contactManager;
	}
	
	@PostMapping("/newInvoice")
	public ModelAndView newInvoce() {
		ModelAndView modelAndView = new ModelAndView("newInvoice");
		ModelMap modelMap = modelAndView.getModelMap();
		modelMap.addAttribute("contacts", this.contactManager.getContacts());
		modelMap.addAttribute("invoice", new Invoice());
		
		return modelAndView;
	}
	
	@PostMapping("/confirmInvoice")
	public ModelAndView confirmContact(@Valid @ModelAttribute("invoice") Invoice invoiceValues) {
		ModelAndView modelAndView = new ModelAndView("newInvoice");
		ModelMap modelMap = modelAndView.getModelMap();
		this.invoiceManager.addInvoice(invoiceValues);
		modelMap.addAttribute("message", invoiceValues.toString());
		
		return modelAndView;
	}
	
}
