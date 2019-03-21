package fr.aspr.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import fr.aspr.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByName(String name);
}
