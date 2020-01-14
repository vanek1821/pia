package vanek.pia.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionMessage.ItemsBuilder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import vanek.pia.dao.ContactRepository;
import vanek.pia.dao.InvoiceRepository;
import vanek.pia.dao.ItemRepository;
import vanek.pia.dao.UserRepository;
import vanek.pia.domain.Contact;
import vanek.pia.domain.Invoice;
import vanek.pia.domain.Item;
import vanek.pia.domain.User;

@Service
@Slf4j
public class InvoiceManagerImpl implements InvoiceManager {

	@Autowired
	private InvoiceRepository invoiceRepo;
	@Autowired
	private ContactRepository contactRepo;
	@Autowired
	private ItemRepository itemRepo;
	
	
	@Override
	public Invoice getById(Long id) {
		Invoice invoice = this.invoiceRepo.getById(id);
		return invoice;
	}


	@Override
	public void addInvoice(@Valid Invoice invoiceValues) {
		if(invoiceValues==null) {
			log.error("invoiceValues are null");
			return;
		}
		if (this.invoiceRepo.findByDocumentSerialNumber(invoiceValues.getDocumentSerialNumber()) != null) {
			throw new IllegalArgumentException("Document already exists!");
		}
		Invoice invoice = new Invoice(invoiceValues.getDocumentSerialNumber(), invoiceValues.getDateExposure(), invoiceValues.getDateDue(), invoiceValues.getDateExecution(), invoiceValues.getSymbolVariable(), invoiceValues.getSymbolConstant());
		System.out.println(invoiceValues.getSupplier());
		Contact supplier = this.contactRepo.findByName(invoiceValues.getSupplier().getName());
		invoice.setSupplier(supplier);
		Contact customer = this.contactRepo.findByName(invoiceValues.getCustomer().getName());
		invoice.setCustomer(customer);
		
		this.invoiceRepo.save(invoice);
		
		if(invoiceValues.getItems()==null) {
			log.info("invoiceValues.getItems() are null");
			return;
		}
		
		for (Item item : invoiceValues.getItems()) {
			Item tmpItem = new Item(item.getName(), item.getQuantity(), item.getPrice());
			tmpItem.setInvoice(invoice);
			itemRepo.save(tmpItem);
		}
		
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


	@Override
	public void updateInvoice(Invoice invoice, @Valid Invoice invoiceValues) {
		invoice.setDocumentSerialNumber(invoiceValues.getDocumentSerialNumber());
		invoice.setDateExposure(invoiceValues.getDateExposure());
		invoice.setDateDue(invoiceValues.getDateDue());
		invoice.setDateExecution(invoice.getDateExecution());
		invoice.setSymbolVariable(invoiceValues.getSymbolVariable());
		invoice.setSymbolConstant(invoiceValues.getSymbolConstant());
		
		Contact customer = contactRepo.findByName(invoiceValues.getCustomer().getName());
		invoice.setCustomer(customer);
		Contact supplier = contactRepo.findByName(invoiceValues.getSupplier().getName());
		invoice.setSupplier(supplier);
		
		itemRepo.deleteAll(invoice.getItems());
		invoice.getItems().clear();
		
		this.invoiceRepo.save(invoice);
		
		if(invoiceValues.getItems()==null) {
			log.info("invoiceValues.getItems() are null");
			return;
		}
		List<Item> itemList = new ArrayList<Item>();
		for (Item item : invoiceValues.getItems()) {
			Item tmpItem = new Item(item.getName(), item.getQuantity(), item.getPrice());
			tmpItem.setInvoice(invoice);
			itemList.add(tmpItem);
			itemRepo.save(tmpItem);
		}
		
		invoiceRepo.save(invoice);
		
		
	}


	@Override
	public boolean updateInvoiceCancelled(Long idLong) {
		Invoice invoice = invoiceRepo.getById(idLong);
		if (invoice == null) {
			return false;
		}
		else {
			invoice.setCancelled(true);
			invoiceRepo.save(invoice);
			return true;
		}
		

		
	}

}
