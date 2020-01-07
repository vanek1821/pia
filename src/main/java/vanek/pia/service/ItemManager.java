package vanek.pia.service;

import java.util.List;

import javax.validation.Valid;

import vanek.pia.domain.Item;

public interface ItemManager {
	
	Item getById(Long id);

	void addItem(@Valid Item itemValues);

	List<Item> getItems();

	void updateItem(Item item, @Valid Item itemValues);

}
