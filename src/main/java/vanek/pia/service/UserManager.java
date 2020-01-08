package vanek.pia.service;

import java.util.List;

import javax.validation.Valid;

import vanek.pia.domain.User;

public interface UserManager {

	List<User> getUsers();

	void addUser(String username, String password, String fullName, String personalIDNum, String adress, String email, String phone, String bankAcc);

	User getById(Long id);

	User findUserByName(String username);

	void updateUser(User user, @Valid User userValues);

	void deleteUser(Long id);

	boolean addUser(@Valid User userValues);

}
