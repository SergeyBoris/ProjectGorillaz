package com.javarush.borisov.conrtoller.newRest;

import com.javarush.borisov.db.Service.newService.ReqService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/request")
@AllArgsConstructor
public class RestRequest {
    private final ReqService reqService;

    @GetMapping("/available-dates")
    public Map<Integer, List<Integer>> getAvailableDates() {
        return reqService.getAvailableDates();
    }

}
