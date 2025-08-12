package com.javarush.borisov.db.Service.newService;

import com.javarush.borisov.db.Repository.ContragentRepo;
import com.javarush.borisov.entity.dto.ContragentDto;
import com.javarush.borisov.entity.mapper.ContragentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContragentService {
    private final ContragentRepo contragentRepo;
    private final ContragentMapper contragentMapper;

    public List<ContragentDto> findAll() {
       return contragentRepo.findAll().stream()
                .map(contragentMapper::toDto)
                .toList();
    }
}
