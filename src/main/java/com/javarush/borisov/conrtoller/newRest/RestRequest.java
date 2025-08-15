package com.javarush.borisov.conrtoller.newRest;

import com.javarush.borisov.db.Service.newService.ReqService;
import com.javarush.borisov.entity.dto.RequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/request")
@AllArgsConstructor
public class RestRequest {
    private final ReqService reqService;

    @GetMapping("/available-dates")
    public Map<Integer, List<Integer>> getAvailableDates() {
        return reqService.getAvailableDates();
    }

    @PutMapping("/update")
    public ResponseEntity<RequestDto> update(@RequestParam Long id, @RequestBody RequestDto requestDto) {
        RequestDto updatedRequest = reqService.updateRequest(id, requestDto);
        return ResponseEntity.ok(updatedRequest);
    }

    @PostMapping("/close-req")
    public ResponseEntity<Void> closeRequest(@RequestParam Long id,@RequestBody RequestDto requestDto) {

        if (reqService.closeRequest(id,requestDto)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/add-equipment")
    public ResponseEntity<Void> addEquipment(
            @RequestParam Long requestId,
            @RequestParam Long equipmentId,
            @RequestParam String eqType
    ) {
        if(reqService.addEquipment(requestId,equipmentId,eqType)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/{requestId}/assign-user")
    public ResponseEntity<Void> assignUser(
            @PathVariable("requestId") Long requestId,
            @RequestParam("userId") Long userId ){
        reqService.assignUser(requestId,userId);
        return ResponseEntity.ok().build();
    }

}
