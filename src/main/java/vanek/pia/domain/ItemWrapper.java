package vanek.pia.domain;

import java.util.ArrayList;
import java.util.List;

public class ItemWrapper {
	private List<Item> items;
	
	public ItemWrapper() {
		this.items = new ArrayList<Item>();
	}
	
	public ItemWrapper(List<Item> items) {
		this.items = items;
	}
	

	public void addItem(Item item) {
		this.items.add(item);
	}
	
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
}
