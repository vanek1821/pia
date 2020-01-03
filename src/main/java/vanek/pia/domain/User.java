package vanek.pia.domain;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class User extends EntityParent{

	@Column(unique = true)
	private String username;

	private String password;
	
	private String fullName;
	
	private String personalIDNum;
	
	private String adress;
	
	private String email;
	
	private String phone;
	
	private String bankAcc;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private Role role;
	
	
	/*@ManyToMany
	@JoinTable(
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)*/

	public User(String username, String password, String fullName, String personalIDNum, String adress, String email, String phone, String bankAcc) {
		this.setUsername(username);
		this.setPassword(password);
		this.fullName = fullName;
		this.personalIDNum = personalIDNum;
		this.adress = adress;
		this.email = email;
		this.phone = phone;
		this.bankAcc = bankAcc;
	}

}
