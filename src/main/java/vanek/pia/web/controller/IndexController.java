package vanek.pia.web.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import vanek.pia.domain.Invoice;
import vanek.pia.service.ContactManager;
import vanek.pia.service.InvoiceManager;
import vanek.pia.service.UserManager;

@Controller
public class IndexController {
	
	UserManager userManager;
	ContactManager contactManager;
	InvoiceManager invoiceManager;
	
	public IndexController(UserManager userManager, ContactManager contactManager, InvoiceManager invoiceManager) {
		this.userManager = userManager;
		this.contactManager = contactManager;
		this.invoiceManager = invoiceManager;
	}

	@GetMapping("/")
	public ModelAndView index() throws IOException {
		ModelAndView modelAndView = new ModelAndView("index");
		ModelMap modelMap = modelAndView.getModelMap();
		modelMap.addAttribute("users", userManager.getUsers());
		modelMap.addAttribute("contacts", contactManager.getContacts());
		modelMap.addAttribute("invoices", invoiceManager.getInvoices());
		
		return modelAndView;
	}

}
