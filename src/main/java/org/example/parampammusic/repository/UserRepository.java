package org.example.parampammusic.repository;

import org.example.parampammusic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User save(User user);
    User findByLogin(String login);
    List<User> findAll();
    void deleteById(Integer id);
}