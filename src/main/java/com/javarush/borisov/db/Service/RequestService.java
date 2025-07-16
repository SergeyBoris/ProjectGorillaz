package com.javarush.borisov.db.Service;

import com.javarush.borisov.db.Dao.EquipmentDao;
import com.javarush.borisov.db.Dao.RequestDao;
import com.javarush.borisov.db.Dao.UserDao;
import com.javarush.borisov.entity.dto.old.EquipmentDtoOld;
import com.javarush.borisov.entity.dto.old.RequestDtoOld;
import com.javarush.borisov.entity.dto.old.UserDtoOld;
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

@Transactional


public class RequestService  {
    private final RequestDao requestDao = new RequestDao();


    public List<RequestDtoOld> getAssignedRequestDtos(UserDtoOld userDtoOld) {

        if(userDtoOld.getRole().equals(UserRoles.COORDINATOR) || userDtoOld.getRole().equals(UserRoles.ADMIN)) {

        List<Request> requests = requestDao.getAssignedRequests();
        return requests.stream()
                .map(RequestDtoOld::new)
                .toList();
        }else return getAssignedRequestsByUser(userDtoOld);
    }

    public Boolean closeRequest(RequestDtoOld requestDtoOld) {

        Request request = requestDao.getById(requestDtoOld.getId());


        requestDtoToRequest(requestDtoOld, request);
        request.setStatus(RequestStatus.COMPLETED);

        return requestDao.update(request);
    }


    private void requestDtoToRequest(RequestDtoOld requestDtoOld, Request request) {
        request.setReqNumber(requestDtoOld.getReqNumber());
        request.setCustomer(requestDtoOld.getCustomer());
        request.setCustomerPhone(requestDtoOld.getCustomerPhone());
        request.setAddress(requestDtoOld.getAddress());
        request.setEquipmentsMontage(mapEquipmentDtos(requestDtoOld.getEquipmentsMontage()));
        request.setEquipmentsUnmontage(mapEquipmentDtos(requestDtoOld.getEquipmentsUnMontage()));
        request.setSla(requestDtoOld.getSla());
        request.setCloseDate(requestDtoOld.getCloseDate());
        request.setComment(requestDtoOld.getComment());
        request.setRangeToAddress(requestDtoOld.getRangeToAddress());
        request.setLinkToAktFile(ParamConstant.LINK_TO_ACT_FILE);
        request.setUser(getUserByName(requestDtoOld.getUser()));
    }


    private List<RequestDtoOld> getAssignedRequestsByUser(UserDtoOld userDtoOld) {
        List<Request> requests = requestDao.getAssignedUsersRequests(userDtoOld.getId());
        return requests.stream()
                .map(RequestDtoOld::new)
                .toList();
    }
    private Set<Equipment> mapEquipmentDtos(Set<EquipmentDtoOld> equipmentDtoOlds) {
        if (equipmentDtoOlds == null) return Collections.emptySet();
        EquipmentDao equipmentDao = new EquipmentDao();
        Set<Equipment> equipments = new HashSet<>();
        for (EquipmentDtoOld equipmentDtoOld : equipmentDtoOlds) {
            equipments.add(equipmentDao.getBySerialUnique(equipmentDtoOld.getSerialNumber()));
        }

        return equipments;
    }
    private User getUserByName(String name) {
        UserDao userDao = new UserDao();
        return userDao.getUserByName(name);


    }
}