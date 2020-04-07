package ua.alexd.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.alexd.security.User;

@Repository
@Transactional
public interface UserRepo extends CrudRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findByUsername(String username);

    User findByActivationCode(String code);
}