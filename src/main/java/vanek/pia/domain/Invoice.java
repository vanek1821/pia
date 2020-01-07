package vanek.pia.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Invoice extends EntityParent {
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="supplier_contact_id")
	private Contact supplier;

	@Column(unique = true)
	private String documentSerialNumber;

	private String dateExposure;

	private String dateDue;

	private String dateExecution;

	private String symbolVariable;

	private String symbolConstant;
	
	private boolean cancelled;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="customer_contact_id")
	private Contact customer;


	//@ToString.Exclude
	@OneToMany(mappedBy = "invoice")
	private List<Item> items;

	public Invoice(String documentSerialNumber, String dateExposure, String dateDue, String dateExecution,
				   String symbolVariable, String symbolConstant){

		this.setDocumentSerialNumber(documentSerialNumber);
		this.setDateExposure(dateExposure);
		this.setDateDue(dateDue);
		this.setDateExecution(dateExecution);
		this.setSymbolVariable(symbolVariable);
		this.setSymbolConstant(symbolConstant);
		this.setCancelled(false);
	}

}
