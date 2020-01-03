package vanek.pia.service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import vanek.pia.dao.ContactRepository;
import vanek.pia.domain.Contact;
import vanek.pia.domain.User;

@Service
@Slf4j
public class ContactManagerImpl implements ContactManager{

	private final ContactRepository contactRepo;

	@Autowired
	public ContactManagerImpl(ContactRepository contactRepo) {
		this.contactRepo = contactRepo;
	}

	
	@Override
	public List<Contact> getContacts() {
		List<Contact> retVal = new LinkedList<>();
		for (Contact contact : this.contactRepo.findAll()) {
			retVal.add(contact);
		}
		return Collections.unmodifiableList(retVal);
	}


	@Override
	public void addContact(@Valid Contact contactValues) {
		if (this.contactRepo.findByName(contactValues.getName()) != null) {
			throw new IllegalArgumentException("Contact already exists!");
		}
		Contact contact = new Contact(contactValues.getName(), contactValues.getResidence(), contactValues.getIdentificationNumber(), contactValues.getTaxIdentificationNumber(), contactValues.getPhone(), contactValues.getEmail(), contactValues.getBankAcc());
		this.contactRepo.save(contact);
	}


	@Override
	public Contact getById(Long id) {
		Contact contact = this.contactRepo.getById(id);
		return contact;
	}


	@Override
	public void updateContact(Contact contact, @Valid Contact contactValues) {
		System.out.println(contactValues.toString());
		System.out.println(contact.toString());
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
