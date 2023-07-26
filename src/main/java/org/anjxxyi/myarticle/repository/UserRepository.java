package org.anjxxyi.myarticle.repository;

import org.anjxxyi.myarticle.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    // findByEmail() => FROM users WHERE email = #{email}
}
