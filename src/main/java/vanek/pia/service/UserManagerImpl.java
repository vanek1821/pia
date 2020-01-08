package vanek.pia.service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import vanek.pia.dao.RoleRepository;
import vanek.pia.dao.UserRepository;
import vanek.pia.domain.Role;
import vanek.pia.domain.User;
import vanek.pia.model.WebCredentials;

@Service
@Slf4j
public class UserManagerImpl implements UserManager, UserDetailsService {

	private static final String DEFAULT_USER = "admin";
	private static final String DEFAULT_PASSWORD = "default";

	private final PasswordEncoder encoder;

	private final UserRepository userRepo;
	private final RoleRepository roleRepo;

	@Autowired
	public UserManagerImpl(PasswordEncoder encoder, UserRepository userRepo, RoleRepository roleRepo) {
		this.encoder = encoder;
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
	}

	@Override
	public List<User> getUsers() {
		List<User> retVal = new LinkedList<>();
		for (User user : this.userRepo.findAll()) {
			retVal.add(user);
		}
		return Collections.unmodifiableList(retVal);
	}

	@Override
	//public void addUser(String username, String password, String fullName, String personalIDNum, String adress, String email, String phone, String bankAcc) {
	public boolean addUser(@Valid User userValues) {
		if (this.userRepo.findByUsername(userValues.getUsername()) != null) {
			return false;
		}

		String hashed = this.encoder.encode(userValues.getPassword());
		User user = new User(userValues.getUsername(), hashed, userValues.getFullName(), userValues.getPersonalIDNum(), userValues.getAdress(), userValues.getEmail(), userValues.getPhone(), userValues.getBankAcc());
		//System.out.println(userValues.toString());
		Role role = this.roleRepo.findByCode(userValues.getRole().getCode());
		//user.getRoles().add(role);
		user.setRole(role);
		this.userRepo.save(user);
		return true;
	}

	@EventListener(classes = {
			ContextRefreshedEvent.class
	})
	@Order(2)
	@Transactional
	public void setup() {
		if (this.userRepo.count() == 0) {
			log.info("No user present, creating admin.");
			this.addUser(DEFAULT_USER, DEFAULT_PASSWORD, "", "", "" ,"" ,"" ,"");
			User user = this.userRepo.findByUsername(DEFAULT_USER);
			Role role = this.roleRepo.findByCode("ADMIN");
			//user.getRoles().add(role);
			user.setRole(role);
			
			this.userRepo.save(user);
		}
	}

	private String toSpringRole(Role role) {
		return role.getCode();
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username!");
		}

		WebCredentials creds = new WebCredentials(user.getUsername(), user.getPassword());

		creds.addRole(user.getRole().getCode());
		/*user.getRoles()
		.stream()
		.map(this::toSpringRole)
		.forEach(creds::addRole);*/

		return creds;
	}

	@Override
	public User getById(Long id) {
		User user = userRepo.getById(id);
		
		return user;
	}

	@Override
	public User findUserByName(String username) {
		User user = userRepo.findByUsername(username);
		return user;
	}

	@Override
	public void updateUser(User user, @Valid User userValues) {
		user.setFullName(userValues.getFullName());
		user.setPersonalIDNum(userValues.getPersonalIDNum());
		user.setAdress(userValues.getAdress());
		user.setEmail(userValues.getEmail());
		user.setPhone(userValues.getPhone());
		user.setBankAcc(userValues.getBankAcc());
		Role role = roleRepo.findByCode(userValues.getRole().getCode());
		user.setRole(role);
		
		userRepo.save(user);
	}

	@Override
	public void deleteUser(Long id) {
		User user = userRepo.getById(id);
		userRepo.delete(user);
		
	}

	@Override
	public void addUser(String username, String password, String fullName, String personalIDNum, String adress,
			String email, String phone, String bankAcc) {
		String hashed = this.encoder.encode(password);
		User user = new User(username, hashed, fullName, personalIDNum, adress, email, phone, bankAcc);
		this.userRepo.save(user);
		
	}

}
