package vanek.pia.service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import vanek.pia.dao.ContactRepository;
import vanek.pia.dao.InvoiceRepository;
import vanek.pia.dao.UserRepository;
import vanek.pia.domain.Contact;
import vanek.pia.domain.Invoice;
import vanek.pia.domain.User;

@Service
@Slf4j
public class InvoiceManagerImpl implements InvoiceManager {

	@Autowired
	private InvoiceRepository invoiceRepo;
	@Autowired
	private ContactRepository contactRepo;
	
	
	@Override
	public Invoice getById(Long id) {
		
		return null;
	}


	@Override
	public void addInvoice(@Valid Invoice invoiceValues) {
		if (this.invoiceRepo.findByDocumentSerialNumber(invoiceValues.getDocumentSerialNumber()) != null) {
			throw new IllegalArgumentException("Contact already exists!");
		}
		Invoice invoice = new Invoice(invoiceValues.getDocumentSerialNumber(), invoiceValues.getDateExposure(), invoiceValues.getDateDue(), invoiceValues.getDateExecution(), invoiceValues.getSymbolVariable(), invoiceValues.getSymbolConstant());
		System.out.println(invoiceValues.getSupplier());
		Contact supplier = this.contactRepo.findByName(invoiceValues.getSupplier().getName());
		invoice.setSupplier(supplier);
		Contact customer = this.contactRepo.findByName(invoiceValues.getCustomer().getName());
		invoice.setCustomer(customer);
	
		this.invoiceRepo.save(invoice);
		
	}


	@Override
	public List<Invoice> getInvoices() {
		List<Invoice> retVal = new LinkedList<>();
		for (Invoice invoice : this.invoiceRepo.findAll()) {
			retVal.add(invoice);
		}
		return Collections.unmodifiableList(retVal);
	}

}
