package com.javarush.borisov.conrtoller;

import com.javarush.borisov.db.Service.newService.ReqService;
import com.javarush.borisov.db.Service.newService.UserService;
import com.javarush.borisov.entity.dto.RequestDto;
import com.javarush.borisov.entity.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RequestController {
    private final ReqService reqService;
    private final UserService userService;

    @GetMapping("/assigned-requests")
    public String assignedRequests (
            Model model,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        UserDto userDto = userService.getUserByEmail(userDetails.getUsername());

        List<RequestDto> requests = reqService.getAssignedRequests(userDto);
        model.addAttribute("requests", requests);
        return "assigned-requests";
    }

    @GetMapping("/request/tid")
    public String requestsWithTid (Model model, @RequestParam String tid){
        List<RequestDto> requests = reqService.getRequestWithTid(tid);
        model.addAttribute("requests", requests);
        return "info";
    }
}
