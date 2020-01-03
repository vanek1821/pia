package vanek.pia.service;

import java.util.List;

import vanek.pia.domain.Role;

public interface RoleManager {

	List<Role> getRoles();

	void addRole(String code, String name);

}
