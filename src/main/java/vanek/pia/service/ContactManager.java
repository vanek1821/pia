package vanek.pia.service;

import java.util.List;

import javax.validation.Valid;

import vanek.pia.domain.Contact;

public interface ContactManager{
	
	List<Contact> getContacts();

	boolean addContact(@Valid Contact contactValues);

	Contact getById(Long id);

	void updateContact(Contact contact, @Valid Contact contactValues);

	void deleteContact(Long id);

}
