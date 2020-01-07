package vanek.pia.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import vanek.pia.domain.Invoice;
import vanek.pia.domain.Item;
import vanek.pia.domain.User;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
	
	Item getById(Long Id);
	
	Item findByName(String name);

	

}
