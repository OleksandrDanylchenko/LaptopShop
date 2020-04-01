package ua.alexd.security;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AdminRepo extends CrudRepository<Admin, Integer> {
    Admin findByUsername(String username);
}