package com.granium.domain.psql.repository;

import com.granium.domain.psql.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Sasha.Chepurnoi on 06.12.16.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);

    @Query(value = "SELECT * FROM users ORDER BY json_array_length(cast(followers as JSON)) DESC LIMIT 5", nativeQuery = true)
    List<User> findTopFive();
}
