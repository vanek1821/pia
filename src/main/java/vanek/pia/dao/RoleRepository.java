package vanek.pia.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import vanek.pia.domain.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByCode(String code);

}
