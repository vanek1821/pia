package vanek.pia.service;
import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import vanek.pia.dao.InvoiceRepository;
import vanek.pia.dao.ItemRepository;
import vanek.pia.domain.Contact;
import vanek.pia.domain.Invoice;
import vanek.pia.domain.Item;

@Service
@Slf4j
public class ItemManagerImpl implements ItemManager {

	@Autowired
	private ItemRepository itemRepo;
	@Autowired
	private InvoiceRepository invoiceRepo;

	@Override
	public Item getById(Long id) {
		Item item = itemRepo.getById(id);
		return item;
	}

	@Override
	public void addItem(@Valid Item itemValues) {
		Item item = new Item(itemValues.getName(), itemValues.getQuantity(), itemValues.getPrice());
		
		Invoice invoice = invoiceRepo.findByDocumentSerialNumber(itemValues.getInvoice().getDocumentSerialNumber());
		
		item.setInvoice(invoice);
		
		this.itemRepo.save(item);
		
	}

	@Override
	public List<Item> getItems() {
		List<Item> retVal = new LinkedList<>();
		for (Item item : this.itemRepo.findAll()) {
			retVal.add(item);
		}
		return retVal;
	}

	@Override
	public void updateItem(Item item, @Valid Item itemValues) {
		item.setName(itemValues.getName());
		item.setQuantity(itemValues.getQuantity());
		item.setPrice(itemValues.getPrice());
		item.setFullPrice(item.getPrice()*item.getQuantity());
		
		Invoice invoice = invoiceRepo.findByDocumentSerialNumber(itemValues.getInvoice().getDocumentSerialNumber());
		item.setInvoice(invoice);
		
		itemRepo.save(item);
		
	}

	
	

}
