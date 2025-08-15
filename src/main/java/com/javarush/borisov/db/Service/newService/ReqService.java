package com.javarush.borisov.db.Service.newService;

import com.javarush.borisov.db.Repository.*;
import com.javarush.borisov.db.constants.EquipmentStatus;
import com.javarush.borisov.db.constants.RequestStatus;
import com.javarush.borisov.entity.Contragent;
import com.javarush.borisov.entity.Equipment;
import com.javarush.borisov.entity.User;
import com.javarush.borisov.entity.dto.ContragentDto;
import com.javarush.borisov.entity.dto.EquipmentDto;
import com.javarush.borisov.entity.dto.RequestDto;

import com.javarush.borisov.entity.Request;
import com.javarush.borisov.entity.dto.UserDto;
import com.javarush.borisov.entity.mapper.RequestMapper;
import jakarta.annotation.PostConstruct;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReqService {

    private final RequestMapper requestMapper;
    private final RequestRepo requestRepo;
    private final ContragentRepo contragentRepo;
    private final UserRepo userRepo;
    private final EquipmentRepo equipmentRepo;
    private final EntityManager entityManager;
    private Map<Integer, List<Integer>> cachedDates = new ConcurrentHashMap<>();

    @Transactional(readOnly = true)
    public List<RequestDto> getAssignedRequests() {
        return requestRepo.findByStatusIn(
                        List.of(RequestStatus.ASSIGNED, RequestStatus.IN_PROGRESS, RequestStatus.CLOSED_BY_USER))
                .stream()
                .map(requestMapper::toDto)
                .toList();


    }


    public List<RequestDto> getRequestWithSerial(String serial) {
        List<Request> requests = requestRepo.findByEquipmentsMontage_SerialNumberOrEquipmentsUnmontage_SerialNumber(serial, serial);
        return requests.stream().map(requestMapper::toDto).toList();
    }

    @PostConstruct
    public void initCache() {
        List<YearMonthProjection> raw = requestRepo.findAvailableRequests_ClosedDatesGroupedByYearMonth();
        cachedDates = raw.stream()
                .collect(Collectors.groupingBy(
                        YearMonthProjection::getYear,
                        Collectors.mapping(
                                YearMonthProjection::getMonth,
                                Collectors.collectingAndThen(Collectors.toSet(),
                                        set -> set.stream().sorted().toList())
                        )
                ));
    }

    public Map<Integer, List<Integer>> getAvailableDates() {
        return cachedDates;
    }

    public void addClosedDateToCache(LocalDate closedDate) {
        if (closedDate == null) return;

        int year = closedDate.getYear();
        int month = closedDate.getMonthValue();

        cachedDates.compute(year, (y, months) -> {
            if (months == null) {
                months = new ArrayList<>();
            }
            if (!months.contains(month)) {
                months.add(month);
                months.sort(Integer::compareTo); // чтобы сохранить порядок
            }
            return months;
        });
    }

    @Transactional
    public RequestDto updateRequest(Long id, RequestDto requestDto) {
        Request request = requestRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Заявка с id " + id + " не найдена"));
        // Если есть зависимые объекты, их можно подставить отдельно
        if (requestDto.getContragent() != null && requestDto.getContragent().getId() != null) {
            Contragent contragent = contragentRepo.findById(requestDto.getContragent().getId())
                    .orElseThrow(() -> new RuntimeException("Контрагент не найден"));
            request.setContragent(contragent);
        }
        if (requestDto.getUser() != null && requestDto.getUser().getId() != null) {
            User user = userRepo.findById(requestDto.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User не найден"));
            request.setUser(user);
        }
        // MapStruct обновляет остальные поля, игнорируя null
        requestMapper.updateEntityFromDto(requestDto, request);

        return requestMapper.toDto(requestRepo.save(request));
    }

    @Transactional
    public boolean closeRequest(Long requestId, RequestDto dto) {

        Request request = requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));


        // Обновляем простые поля через mapper
        requestMapper.updateEntityFromDto(dto, request);
        changeEquipmentStatus(request.getEquipmentsMontage(),"montage");
        changeEquipmentStatus(request.getEquipmentsUnmontage(),"unmontage");
        request.setStatus(RequestStatus.COMPLETED);
        request.setCloseDate(dto.getCloseDate());
        request.setLastUpdate(LocalDateTime.now());
        return true;
    }
    @Transactional
    public boolean addEquipment(Long requestId, Long equipmentId, String eqType) {
        Request request = requestRepo.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));
        Equipment equipment = equipmentRepo.findById(equipmentId).orElseThrow(() -> new RuntimeException("Equipment not found"));
        switch (eqType.toLowerCase()) {
            case "montage" -> request.addMontageEquipment(equipment);
            case "unmontage" -> request.addUnmontageEquipment(equipment);
            default -> throw new RuntimeException("Не удалось определить тип оборудования (montage/unmontage)");
        }
        return true;
    }
    @Transactional
    public void assignUser(Long requestId, Long userId) {
        Request request = requestRepo.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        request.setUser(user);
        //отправка в бот ToDo
    }

    private void changeEquipmentStatus(Set<Equipment> equipments, String eqType){
        if (equipments == null) return;
        for (Equipment equipment : equipments) {
            switch (eqType.toLowerCase()) {
                case "montage" -> equipment.setEquipmentStatus(EquipmentStatus.ON_REQUEST);
                case "unmontage" -> equipment.setEquipmentStatus(EquipmentStatus.WAREHOUSE);
            }
        }
    }


}



