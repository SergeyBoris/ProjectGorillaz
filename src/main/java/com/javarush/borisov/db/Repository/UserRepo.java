package com.javarush.borisov.db.Repository;

import com.javarush.borisov.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

     Optional<User> findByMail(String email);


}
