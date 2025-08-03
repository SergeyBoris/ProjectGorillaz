package com.javarush.borisov.db.Service.newService;

import com.javarush.borisov.db.Repository.YearMonthProjection;
import com.javarush.borisov.db.constants.RequestStatus;
import com.javarush.borisov.entity.dto.RequestDto;

import com.javarush.borisov.db.Repository.RequestRepo;
import com.javarush.borisov.entity.Request;
import com.javarush.borisov.entity.mapper.RequestMapper;
import jakarta.annotation.PostConstruct;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReqService {

    private final RequestMapper requestMapper;
    private final RequestRepo requestRepo;
    private Map<Integer, List<Integer>> cachedDates = new ConcurrentHashMap<>();

    @Transactional(readOnly = true)
    public List<RequestDto> getAssignedRequests(){
        return requestRepo.findByStatusIn(
                List.of(RequestStatus.ASSIGNED,RequestStatus.IN_PROGRESS,RequestStatus.CLOSED_BY_USER))
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
        System.out.println("\nИнитКэш сроботал\n");
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



}
