package vanek.pia.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import vanek.pia.domain.Invoice;
import vanek.pia.domain.User;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

	Object findByDocumentSerialNumber(String documentSerialNumber);
	
	Invoice getById(Long Id);

	

}
