package fr.aspr.repositories;

import org.springframework.data.repository.CrudRepository;

import fr.aspr.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String name);
}
