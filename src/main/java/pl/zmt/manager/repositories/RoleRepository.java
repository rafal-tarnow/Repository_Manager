package pl.zmt.manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zmt.manager.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
    Role findByRole(String role);
}
