package vanek.pia.service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import vanek.pia.dao.ContactRepository;
import vanek.pia.domain.Contact;
import vanek.pia.domain.Role;
import vanek.pia.domain.User;

@Service
@Slf4j
public class ContactManagerImpl implements ContactManager {

	@Autowired
	private ContactRepository contactRepo;

	@EventListener(classes = {
			ContextRefreshedEvent.class
	})
	@Order(3)
	@Transactional
	public void setup() {
		if (this.contactRepo.count() == 0) {
			log.info("No contact present, 2 contacts.");
			this.newContact("Supplier", "sup_residence", "0000000","1111111" , "123456789", "supplier@supplier.com", "000000000/0100");
			Contact contact = this.contactRepo.findByName("Supplier");
			this.contactRepo.save(contact);
			
			this.newContact("Recipient", "rec_residence", "0000000","1111111" , "123456789", "recipient@recipient.com", "000000000/0100");
			contact = this.contactRepo.findByName("Recipient");
			this.contactRepo.save(contact);
		}
	}
	/*
	@Autowired
	public ContactManagerImpl(ContactRepository contactRepo) {
		this.contactRepo = contactRepo;
	}*/

	@Override
	public List<Contact> getContacts() {
		List<Contact> retVal = new LinkedList<>();
		for (Contact contact : this.contactRepo.findAll()) {
			retVal.add(contact);
		}
		return Collections.unmodifiableList(retVal);
	}

	@Override
	public boolean addContact(@Valid Contact contactValues) {
		if (this.contactRepo.findByName(contactValues.getName()) != null) {
			return false;
		}
		Contact contact = new Contact(contactValues.getName(), contactValues.getResidence(),
				contactValues.getIdentificationNumber(), contactValues.getTaxIdentificationNumber(),
				contactValues.getPhone(), contactValues.getEmail(), contactValues.getBankAcc());
		this.contactRepo.save(contact);
		return true;
	}
	
	@Override
	public void newContact(String name, String residence, String identificationNumber, String taxIdentificationNumber, String phone, String email, String bankAcc) {
		Contact contact = new Contact(name, residence, identificationNumber, taxIdentificationNumber, phone, email, bankAcc);
		this.contactRepo.save(contact);
	}

	@Override
	public Contact getById(Long id) {
		Contact contact = this.contactRepo.getById(id);
		return contact;
	}

	@Override
	public void updateContact(Contact contact, @Valid Contact contactValues) {
		contact.setName(contactValues.getName());
		contact.setResidence(contactValues.getResidence());
		contact.setIdentificationNumber(contactValues.getIdentificationNumber());
		contact.setTaxIdentificationNumber(contactValues.getTaxIdentificationNumber());
		contact.setPhone(contactValues.getPhone());
		contact.setEmail(contactValues.getEmail());
		contact.setBankAcc(contactValues.getBankAcc());
		contactRepo.save(contact);

	}

	@Override
	public void deleteContact(Long id) {
		Contact contact = contactRepo.getById(id);
		contactRepo.delete(contact);
	}

}
