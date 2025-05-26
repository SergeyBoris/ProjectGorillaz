package com.javarush.borisov.db.Service;

import com.javarush.borisov.db.Dao.EquipmentDao;
import com.javarush.borisov.db.Dao.RequestDao;
import com.javarush.borisov.db.Dao.UserDao;
import com.javarush.borisov.db.Dto.EquipmentDto;
import com.javarush.borisov.db.Dto.RequestDto;
import com.javarush.borisov.db.Dto.UserDto;
import com.javarush.borisov.db.constants.ParamConstant;
import com.javarush.borisov.db.constants.RequestStatus;
import com.javarush.borisov.db.constants.UserRoles;
import com.javarush.borisov.entity.Equipment;
import com.javarush.borisov.entity.Request;
import com.javarush.borisov.entity.User;
import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Transactional
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

    public Boolean closeRequest(RequestDto requestDto) {

        Request request = requestDao.getById(requestDto.getId());


        requestDtoToRequest(requestDto, request);
        request.setStatus(RequestStatus.COMPLETED);

        return requestDao.update(request);
    }

    private void requestDtoToRequest(RequestDto requestDto, Request request) {
        request.setReqNumber(requestDto.getReqNumber());
        request.setCustomer(requestDto.getCustomer());
        request.setCustomerPhone(requestDto.getCustomerPhone());
        request.setAddress(requestDto.getAddress());
        request.setEquipmentsMontage(mapEquipmentDtos(requestDto.getEquipmentsMontage()));
        request.setEquipmentsUnmontage(mapEquipmentDtos(requestDto.getEquipmentsUnMontage()));
        request.setSla(requestDto.getSla());
        request.setCloseDate(requestDto.getCloseDate());
        request.setComment(requestDto.getComment());
        request.setRangeToAddress(requestDto.getRangeToAddress());
        request.setLinkToAktFile(ParamConstant.LINK_TO_ACT_FILE);
        request.setUser(getUserByName(requestDto.getUser()));
    }


    private List<RequestDto> getAssignedRequestsByUser(UserDto userDto) {
        List<Request> requests = requestDao.getAssignedUsersRequests(userDto.getId());
        return requests.stream()
                .map(RequestDto::new)
                .toList();
    }
    private Set<Equipment> mapEquipmentDtos(Set<EquipmentDto> equipmentDtos) {
        if (equipmentDtos == null) return Collections.emptySet();
        EquipmentDao equipmentDao = new EquipmentDao();
        Set<Equipment> equipments = new HashSet<>();
        for (EquipmentDto equipmentDto : equipmentDtos) {
            equipments.add(equipmentDao.getBySerialUnique(equipmentDto.getSerialNumber()));
        }

        return equipments;
    }
    private User getUserByName(String name) {
        UserDao userDao = new UserDao();
        return userDao.getUserByName(name);


    }
}