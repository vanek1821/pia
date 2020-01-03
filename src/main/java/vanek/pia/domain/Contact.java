package vanek.pia.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Contact extends EntityParent {

	@Column(unique = true)
	private String name;

	private String residence;

	private String identificationNumber;

	private String taxIdentificationNumber;

	private String phone;

	private String email;

	private String bankAcc;
	

	public Contact(String name, String residence, String identificationNumber, String taxIdentificationNumber,
			   String phone, String email, String bankAcc) {
	this.setName(name);
	this.setResidence(residence);
	this.setIdentificationNumber(identificationNumber);
	this.setTaxIdentificationNumber(taxIdentificationNumber);
	this.setPhone(phone);
	this.setEmail(email);
	this.setBankAcc(bankAcc);
}

}
