package vanek.pia.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import vanek.pia.domain.Contact;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {

	Contact findByName(String name);

	Contact getById(Long Id);


}