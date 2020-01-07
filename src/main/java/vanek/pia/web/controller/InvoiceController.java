package vanek.pia.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vanek.pia.domain.Contact;
import vanek.pia.domain.Invoice;
import vanek.pia.domain.Item;
import vanek.pia.domain.User;
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
		ModelAndView mav = new ModelAndView("newInvoice");
		ModelMap modelMap = mav.getModelMap();
		modelMap.addAttribute("contacts", this.contactManager.getContacts());
		modelMap.addAttribute("invoice", new Invoice());
		
		return mav;
	}
	
	@PostMapping("/confirmInvoice")
	public ModelAndView confirmContact(@Valid @ModelAttribute("invoice") Invoice invoiceValues) {
		ModelAndView mav = new ModelAndView("newInvoice");
		ModelMap modelMap = mav.getModelMap();
		this.invoiceManager.addInvoice(invoiceValues);
		modelMap.addAttribute("message", "Invoice succesfully added");
		
		return mav;
	}
	
	@PostMapping("/invoiceList")
	public ModelAndView invoiceEdit() {
		ModelAndView mav = new ModelAndView("invoiceList");
		ModelMap modelMap = mav.getModelMap();
		modelMap.addAttribute("invoices", invoiceManager.getInvoices());
		
		return mav;
	}
	
	@GetMapping("/invoiceList")
	public ModelAndView invoiceEdit_() {
		ModelAndView mav = new ModelAndView("invoiceList");
		ModelMap modelMap = mav.getModelMap();
		modelMap.addAttribute("invoices", invoiceManager.getInvoices());
		
		return mav;
	}
	
	@GetMapping("/invoiceList/invoice")
	public ModelAndView editInvoice(@RequestParam("id") Long id) {
		Invoice invoice = invoiceManager.getById(id);
		ModelAndView mav = new ModelAndView("editInvoice");
		ModelMap modelMap = mav.getModelMap();
		if(invoice == null) {
			mav = new ModelAndView("invoiceList");
			modelMap = mav.getModelMap();
			modelMap.addAttribute("message", "Invoice Not Found");
			modelMap.addAttribute("invoices", invoiceManager.getInvoices());
			
		}
		else {
			List<Item> itemList = invoice.getItems();
			modelMap.addAttribute("contacts", contactManager.getContacts());
			modelMap.addAttribute("invoice", invoice);
			modelMap.addAttribute("itemList",itemList);
		}
		
		return mav;
	}
	
	@PostMapping("/confirmEditInvoice")
	public ModelAndView confirmEdit(@RequestParam("id") Long id, @Valid @ModelAttribute("invoice") Invoice invoiceValues) {
		ModelAndView mav = new ModelAndView("redirect:/invoiceList");
		ModelMap modelMap = mav.getModelMap();
		
		Invoice invoice = invoiceManager.getById(id);
		invoiceManager.updateInvoice(invoice, invoiceValues);
		return mav;
	}
}
