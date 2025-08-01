package com.javarush.borisov.db.Service.newService;

import com.javarush.borisov.db.Repository.UserRepo;
import com.javarush.borisov.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {
    private final UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Trying to load user with email: " + email);
        Optional<User> user = userRepo.findByMail(email);
        return user.orElseThrow(() -> new UsernameNotFoundException("Пользователь " + email + " ненайден"));
    }
}
