package com.javarush.borisov.conrtoller.newRest;


import com.javarush.borisov.db.Service.newService.UserService;
import com.javarush.borisov.entity.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class RestUsers {
    private final UserService userService;

    @GetMapping("/all")
    public List<UserDto> getAll() {
        return userService.getAllUsersDto();
    }
}
