package vanek.pia.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Item extends EntityParent{

	private String name;

	private Long quantity;

	private Float price;

	private Float fullPrice;

	@ManyToOne
	@JoinColumn(name="invoice_id")
	private Invoice invoice;

	public Item(String name, Long quantity, Float price) {
		this.setName(name);
		this.setQuantity(quantity);
		this.setPrice(price);
		this.setFullPrice(price*quantity);
	}
	
}