package com.javarush.borisov.db.Service;

import com.javarush.borisov.db.Dao.RequestDao;
import com.javarush.borisov.db.Dto.RequestDto;
import com.javarush.borisov.db.Dto.UserDto;
import com.javarush.borisov.db.constants.RequestStatus;
import com.javarush.borisov.db.constants.UserRoles;
import com.javarush.borisov.entity.Request;

import java.util.List;

public class RequestService {
    private final RequestDao requestDao = new RequestDao();

    public List<RequestDto> getAssignedRequestDtos(UserDto userDto) {

        if(userDto.getRole().equals(UserRoles.COORDINATOR) || userDto.getRole().equals(UserRoles.ADMIN)) {

        List<Request> requests = requestDao.getAssignedRequests();
        return requests.stream()
                .map(RequestDto::new)
                .toList();
        }else return getAssignedRequestsByUser(userDto);
    }

    public Boolean closeRequest(Long requestId) {
        Request requestDto = requestDao.getById(requestId);
        requestDto.setStatus(RequestStatus.IN_PROGRESS);
        return requestDao.update(requestDto);
    }


    private List<RequestDto> getAssignedRequestsByUser(UserDto userDto) {
        List<Request> requests = requestDao.getAssignedUsersRequests(userDto.getId());
        return requests.stream()
                .map(RequestDto::new)
                .toList();
    }
}