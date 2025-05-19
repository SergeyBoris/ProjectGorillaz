package com.javarush.borisov.db.Service;

import com.javarush.borisov.db.Dao.RequestDao;
import com.javarush.borisov.db.Dto.RequestDto;
import com.javarush.borisov.entity.Request;

import java.util.List;

public class RequestService {
    private final RequestDao requestDao = new RequestDao();

    public List<RequestDto> getAssignedRequestDtos() {
        List<Request> requests = requestDao.getAssignedRequests();
        return requests.stream()
                .map(RequestDto::new)
                .toList();
    }
}