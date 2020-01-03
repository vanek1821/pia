package info.svetlik.pia.pia;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import vanek.pia.dao.RoleRepository;
import vanek.pia.domain.Role;

@SpringBootTest
@Slf4j
public class RoleRepoTest {

	@Autowired
	private RoleRepository roleRepo;

	@Test
	@Transactional
	public void testRoleManipulation() {
		log.info("Testing user manipulation.");
		Assertions.assertEquals(2, roleRepo.count());
		log.info("Looking for admin role.");
		Role adminRole = roleRepo.findByCode("ADMIN");
		Assertions.assertNotNull(adminRole);
		log.info("Found admin role.");
		log.info("Looking for user role.");
		Role userRole = roleRepo.findByCode("USER");
		Assertions.assertNotNull(userRole);
		log.info("Found admin user role.");
		//Assertions.assertEquals(1, user.getRoles().size());
		//log.info("Admin has one role, removing.");
		//user.getRoles().remove(0);
		//userRepo.save(user);
		//user = userRepo.findByUsername("admin");
		//Assertions.assertEquals(0, user.getRoles().size());
		//log.info("Admin has no role now, OK.");
	}

}
